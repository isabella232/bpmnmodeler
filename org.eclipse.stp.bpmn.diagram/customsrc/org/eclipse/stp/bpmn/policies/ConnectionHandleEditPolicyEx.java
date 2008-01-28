/******************************************************************************
 * Copyright (c) 2006, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************/

/** 
 * Date          	Author             Changes 
 * 26 Oct 2006   	BIlchyshyn         Created
 **/

package org.eclipse.stp.bpmn.policies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Tool;
import org.eclipse.gef.tools.SelectionTool;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramAssistantEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.handles.ConnectionHandle;
import org.eclipse.gmf.runtime.diagram.ui.handles.ConnectionHandleLocator;
import org.eclipse.gmf.runtime.diagram.ui.handles.ConnectionHandle.HandleDirection;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.ModelingAssistantService;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;
import org.eclipse.stp.bpmn.handles.ConnectionHandleEx;
import org.eclipse.stp.bpmn.handles.ConnectionHandleForAssociation;

/**
 * Derived from org.eclipse.gmf.runtime.diagram.ui.editpolicies.
 * ConnectionHandlerEditPolicy.
 * There was issues overriding enough things.
 * 
 * @author Bohdan Ilchyshyn
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ConnectionHandleEditPolicyEx extends DiagramAssistantEditPolicy {

	/**
	 * Listens to the owner figure being moved so the handles can be removed
	 * when this occurs.
	 */
	private class OwnerMovedListener implements FigureListener {

		/**
		 * @see org.eclipse.draw2d.FigureListener#figureMoved(org.eclipse.draw2d.IFigure)
		 */
		public void figureMoved(IFigure source) {
			hideDiagramAssistant();
		}
	}

	/** listener for owner shape movement */
	private OwnerMovedListener ownerMovedListener = new OwnerMovedListener();

	/** list of connection handles currently being displayed */
	private List handles = null;

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramAssistantEditPolicy#isDiagramAssistant(java.lang.Object)
	 */
	protected boolean isDiagramAssistant(Object object) {
		return object instanceof ConnectionHandle;
	}

	/**
	 * Gets the two connection handle figures to be added to this shape if they
	 * support user gestures.
	 * @param borderSide the border from which the cursor
     * is the nearest.
     * @return a list of <code>ConnectionHandle</code> objects
     * 
     * This method creates the connection handles
	 */
	@SuppressWarnings("unchecked") //$NON-NLS-1$
    protected List<ConnectionHandle> getHandleFigures(int borderSide) {
		List<ConnectionHandle> list = new ArrayList<ConnectionHandle>();
		
        List<IElementType> srcEltTypes = getSourceElementTypes();
        List<IElementType> tgtEltTypes = getTargetElementTypes();
        
        // only add handles according to the position
        // of the handle, depending on which type is in the selection.
		switch (borderSide) {
		case PositionConstants.WEST:
            if (tgtEltTypes.contains(BpmnElementTypes.SequenceEdge_3001)) {
                list.add(new ConnectionHandleEx((IGraphicalEditPart) getHost(),
                        HandleDirection.INCOMING, 
                        buildTooltip(HandleDirection.INCOMING)));
            }
            break;
		case PositionConstants.EAST:
            if (srcEltTypes.contains(BpmnElementTypes.SequenceEdge_3001)) {
                list.add(new ConnectionHandleEx((IGraphicalEditPart) getHost(),
                        HandleDirection.OUTGOING, 
                        buildTooltip(HandleDirection.OUTGOING)));
            }
            break;
		}
		setMessagingEdgeHandleFigures(borderSide, list, srcEltTypes, tgtEltTypes);
		setAssociationHandleFigures(borderSide, list, srcEltTypes, tgtEltTypes);
		return list;
	}
	
    /**
     * By default relies on the GMF ModelingAssistantService to return the appropriate types
     * This can be overriden by extension of the modeler.
     * 
     * @return The list of element types that could be target of a new relationship
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
    protected List<IElementType> getTargetElementTypes() {
        return ModelingAssistantService.getInstance().getRelTypesOnTarget(getHost());
    }
    
    /**
     * By default relies on the GMF ModelingAssistantService to return the appropriate types
     * This can be overriden by extension of the modeler.
     * 
     * @return The list of element types that could be source of a new relationship
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
    protected List<IElementType> getSourceElementTypes() {
        return ModelingAssistantService.getInstance().getRelTypesOnSource(getHost());
    }
    
    /**
     * Adds to the list of handles the handles for the different types of associations
     * This method is made tobe overridden by a different implement 
     * should the need arise to have more or less and different types of
     * association handles.
     * 
     * @param borderSide
     * @param list
     * @param srcEltTypes
     * @param tgtEltTypes
     */
    protected void setMessagingEdgeHandleFigures(int borderSide, List<ConnectionHandle> list,
            List<IElementType> srcEltTypes, List<IElementType> tgtEltTypes) {
        switch (borderSide) {
        case PositionConstants.SOUTH:
        case PositionConstants.NORTH:
            if (tgtEltTypes.contains(BpmnElementTypes.MessagingEdge_3002)) {
                list.add(new ConnectionHandleEx((IGraphicalEditPart) getHost(),
                        HandleDirection.INCOMING, 
                        buildTooltip(HandleDirection.INCOMING)));
            }
            if (srcEltTypes.contains(BpmnElementTypes.MessagingEdge_3002)) {
                list.add(new ConnectionHandleEx((IGraphicalEditPart) getHost(),
                        HandleDirection.OUTGOING, 
                        buildTooltip(HandleDirection.OUTGOING)));
            }
        }
    }
    
	/**
	 * Adds to the list of handles the handles for the different types of associations
	 * This method is made tobe overridden by a different implement 
	 * should the need arise to have more or less and different types of
	 * association handles.
	 * 
	 * @param borderSide
	 * @param list
	 * @param srcEltTypes
	 * @param tgtEltTypes
	 */
	protected void setAssociationHandleFigures(int borderSide, List<ConnectionHandle> list,
	        List<IElementType> srcEltTypes, List<IElementType> tgtEltTypes) {
        
        // only add one of the two to avoid having
        // two similar handles on the associations.
        if (srcEltTypes.contains(BpmnElementTypes.Association_3003)) {
            //add the outgoing association
            list.add(new ConnectionHandleForAssociation(
                    (IGraphicalEditPart) getHost(),
                    HandleDirection.OUTGOING, "Association")); //$NON-NLS-1$
        } else if (tgtEltTypes.contains(BpmnElementTypes.Association_3003)) {
            //add the incoming association
            list.add(new ConnectionHandleForAssociation(
                    (IGraphicalEditPart) getHost(),
                    HandleDirection.INCOMING, "Association")); //$NON-NLS-1$
        }
	}

	/**
	 * Builds the applicable tooltip string based on whether the Modeling
	 * Assistant Service supports handle gestures on this element. If no
	 * gestures are supported, the tooltip returned will be null.
	 * 
     * For now we assume that creation is supported when calling this
     * method.
	 * @param direction
	 *            the handle direction.
	 * @return tooltip the tooltip string; if null, the handle should be not be
	 *         displayed
	 */
	protected String buildTooltip(HandleDirection direction) {
//		ModelingAssistantService service = ModelingAssistantService
//			.getInstance();

//		boolean supportsCreation = (direction == HandleDirection.OUTGOING) ? !service
//			.getRelTypesOnSource(getHost()).isEmpty()
//			: !service.getRelTypesOnTarget(getHost()).isEmpty();

//		boolean supportsSRE = (direction == HandleDirection.OUTGOING) ? !service
//			.getRelTypesForSREOnSource(getHost()).isEmpty()
//			: !service.getRelTypesForSREOnTarget(getHost()).isEmpty();

//		if (supportsSRE) {
//			if (supportsCreation) {
				return ""; //$NON-NLS-1$
//			} else {
//				return DiagramUIMessages.ConnectionHandle_ToolTip_ShowRelatedElementsOnly;
//			}
//		} else if (supportsCreation) {
//			return DiagramUIMessages.ConnectionHandle_ToolTip_CreateRelationshipOnly;
//		}
//		return null;
	}

	public void activate() {
		super.activate();
		
		((IGraphicalEditPart) getHost()).getFigure().addFigureListener(
			ownerMovedListener);
	}

	public void deactivate() {
		((IGraphicalEditPart) getHost()).getFigure().removeFigureListener(
			ownerMovedListener);

		super.deactivate();
	}
	
	@SuppressWarnings("unchecked") //$NON-NLS-1$
    protected void showDiagramAssistant(Point referencePoint) {
		if (referencePoint == null) {
			referencePoint = getHostFigure().getBounds().getRight();
		}

		ConnectionHandleLocator locator = getConnectionHandleLocator(referencePoint);
        
        //set handle position to host
        EditPart host = getHost();
        
		handles = getHandleFigures(locator.getBorderSide());
		if (handles == null) {
			return;
		}

		
		
		
		IFigure layer = getLayer(LayerConstants.HANDLE_LAYER);
		for (Iterator iter = handles.iterator(); iter.hasNext();) {
			ConnectionHandle handle = (ConnectionHandle) iter.next();
			
			handle.setLocator(locator);
			locator.addHandle(handle);

			handle.addMouseMotionListener(this);
			layer.add(handle);

			// Register this figure with it's host editpart so mouse events
			// will be propagated to it's host.
			getHost().getViewer().getVisualPartMap().put(handle, getHost());
		}
		
		if(!shouldAvoidHidingDiagramAssistant()) {
			// dismiss the handles after a delay
			hideDiagramAssistantAfterDelay(getDisappearanceDelay());
		}
	}
	
    /**
     * Copied from super as we cannot override the getPreferenceName() method
     * Returns true if the preference to show this diagram assistant is on or if
     * there is no applicable preference; false otherwise.
     */
    protected boolean isPreferenceOn() {
        String prefName = getThePreferenceName();
        if (prefName == null) {
            return true;
        }
        IPreferenceStore preferenceStore = (IPreferenceStore) ((IGraphicalEditPart) getHost())
            .getDiagramPreferencesHint().getPreferenceStore();
        return preferenceStore.getBoolean(prefName);
    }

    
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramAssistantEditPolicy#getPreferenceName()
	 */
	protected String getThePreferenceName() {
		return IPreferenceConstants.PREF_SHOW_CONNECTION_HANDLES;
	}

	/**
	 * Removes the connection handles.
	 */
	protected void hideDiagramAssistant() {
		if (handles == null) {
			return;
		}
		IFigure layer = getLayer(LayerConstants.HANDLE_LAYER);
		for (Iterator iter = handles.iterator(); iter.hasNext();) {
			IFigure handle = (IFigure) iter.next();
			handle.removeMouseMotionListener(this);
			layer.remove(handle);
			getHost().getViewer().getVisualPartMap().remove(handle);
		}
		handles = null;
		
		//unset handle position to host
		EditPart host = getHost();
	}
		
	private boolean isSelectionToolActive()
	{
		// getViewer calls getParent so check for null
		if(getHost().getParent() != null) {
			Tool theTool = getHost().getViewer().getEditDomain().getActiveTool();
			if((theTool != null) && theTool instanceof SelectionTool) {
				return true;
			}
		}
		return false;		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramAssistantEditPolicy#shouldShowDiagramAssistant()
	 */
	protected boolean shouldShowDiagramAssistant(){
        if (getAppearanceDelay() < 0) {
            return false;
        }
		if (!super.shouldShowDiagramAssistant()) {
			return false;
		}
		if (handles != null || !isSelectionToolActive()) {
			return false;
		}
		return true;
	}
	
	/**
	 * get the connection handle locator using the host and the passed reference
	 * point
	 * @author atoulme added a tweak to always rturn the center
	 * of the shape when the host represents an activity
	 * @param referencePoint
	 * @return <code>ConnectionHandleLocator</code>
	 */
	protected ConnectionHandleLocator getConnectionHandleLocator(Point referencePoint){
	    // only non events tasks may benefit from this for now,
	    // until we rework the handles and their positioning 
	    // according to the shape without its label
	    if (getHost() instanceof ActivityEditPart) {
	        Activity activity = (Activity) ((IGraphicalEditPart) getHost()).
                resolveSemanticElement();
	        Rectangle bounds = ((IGraphicalEditPart) getHost()).
                getFigure().getBounds().getCopy();
	        // if it is an event or a gateway we want the
	        // bounds of the shape only, not including the label.
            if (ActivityType.VALUES_EVENTS.contains(activity.getActivityType()) ||
                    ActivityType.VALUES_EVENTS.contains(activity.getActivityType())) {
                bounds = ((IFigure) ((IFigure) ((IGraphicalEditPart) getHost()).
                getFigure().getChildren().get(0)).getChildren().get(0)).getBounds().getCopy();
            }
	        if (bounds.width != 0 && bounds.height != 0) {
	         // we create the two equations representing the diagonals of the rectangle
	            // first the one starting from the top left and going to
	            // the bottom right
	            boolean overFirstDiag = isOverALine( 
	                    bounds.getBottomRight(), bounds.getTopLeft(), referencePoint);
	            boolean overSecondDiag = isOverALine( 
	                    bounds.getTopRight(), bounds.getBottomLeft(), referencePoint);
	            if (overFirstDiag) {
	                if (overSecondDiag) {
	                    referencePoint = bounds.getTop().getCopy();
	                } else {
	                    referencePoint = bounds.getRight().getCopy();
	                }
	            } else {
	                if (overSecondDiag) {
	                    referencePoint = bounds.getLeft().getCopy();
	                } else {
	                    referencePoint = bounds.getBottom().getCopy();
	                }
	            }
	        }
	    }
		return new ConnectionHandleLocator(getHostFigure(), referencePoint);		
	}

	/**
	 * Evaluates wether a point is above a line
	 * formed by two other points
	 * @param firstPoint the first point to form the line
	 * @param secondPoint the second point to form the line
	 * @param toEvaluate the point to evaluate
	 * @return true if the point is above the line, false
	 * otherwise
	 */
	private boolean isOverALine(Point firstPoint, Point secondPoint, 
	        Point toEvaluate) {
	    int xa = firstPoint.x;
        int ya = firstPoint.y;
        int xb = secondPoint.x;
        int yb = secondPoint.y;
        double coeff = (double) (yb - ya)/(xb - xa);
        double constant = (xb*ya - yb*xa)/(xb - xa);
        return toEvaluate.y < coeff*toEvaluate.x + constant;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramAssistantEditPolicy#isDiagramAssistantShowing()
	 */
	protected boolean isDiagramAssistantShowing() {
		return handles != null;
	}
    
    /**
     * Gets the amount of time to wait before showing the diagram assistant.
     * Read from preferences.
     * @return the time to wait in milliseconds
     */
    protected int getAppearanceDelay() {
        IPreferenceStore preferenceStore = (IPreferenceStore) 
        ((IGraphicalEditPart) getHost())
            .getDiagramPreferencesHint().getPreferenceStore();
        int delay = preferenceStore.getInt(
                 BpmnDiagramPreferenceInitializer
                     .PREF_CONN_DIAG_ASSISTANT_DELAY_MS);
        return delay > -1 ? delay : 60;
    }

}
