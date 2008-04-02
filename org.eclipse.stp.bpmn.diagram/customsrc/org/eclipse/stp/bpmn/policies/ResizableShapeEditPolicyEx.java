/******************************************************************************
 * Copyright (c) 2006-2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************/

/** 
 * Date           	Author              Changes 
 * 11 August 2006   MPeleshchyshyn  	Created 
 **/

package org.eclipse.stp.bpmn.policies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.gef.handles.NonResizableHandleKit;
import org.eclipse.gef.handles.RelativeHandleLocator;
import org.eclipse.gef.handles.ResizableHandleKit;
import org.eclipse.gef.handles.SquareHandle;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableShapeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.commands.ElementTypeEx;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.figures.BpmnShapesDefaultSizes;
import org.eclipse.stp.bpmn.figures.FeedbackShape;
import org.eclipse.stp.bpmn.tools.TaskDragEditPartsTrackerEx;

/**
 * Abstract class for resize edit policies.
 * 
 * @author MPeleshchyshyn
 * @author Hugues Malphettes add the feedback for the overlapping areas.
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public abstract class ResizableShapeEditPolicyEx extends ResizableShapeEditPolicy {
    
    protected ZoomManager _zoom;
    
    @Override
    public void activate() {
        super.activate();
        _zoom = ((DiagramRootEditPart) getHost().getRoot()).getZoomManager();
    }
    
    public void deactivate() {
        super.deactivate();
        _zoom = null;
    }
    
    /**
     * Same than the super but does not forget to apply the zoom
     * to the request deltas before computing things.
     * <br/>
     * Also fixes the bounds of the feedback figure when resizing from the 
     * WEST or the NORTH below the minimum size of the figure.
     * <p>
     * Also computes the area of the feedback figure that overlaps with
     * another shape and color it in red to display what is preventing the move
     * from executing.
     * </p>
     */
    @Override
    protected void showChangeBoundsFeedback(ChangeBoundsRequest request) {
        IFigure f = getHostFigure();
        IFigure feedback = getDragSourceFeedbackFigure();
        
        PrecisionRectangle rect = new PrecisionRectangle(getInitialFeedbackBounds().getCopy());
        f.translateToAbsolute(rect);
        
        rect.translate(request.getMoveDelta());
        
        //this condition was not in the original code.
        if (request.getSizeDelta().height != 0
                || request.getSizeDelta().width != 0) {
            
            //if there is a resize happening then do by all mean change the rectangle
            //of the feedback too. otherwise leave it unchanged.
            
            rect.resize(request.getSizeDelta());
            
            Dimension max = f.getMaximumSize().getCopy();
            Dimension min = f.getMinimumSize().getCopy();//BpmnShapesDefaultSizes.getSubProcessMinSize((SubProcessEditPart)getHost(), true); //
            min.height -= SubProcessEditPart.BORDER_HEIGHT;
            
            //experiment: for some reason this works.
            //it does not seem to appear in the GMF code.
            //it is similar to the code in the PoolResizeTracker
            double zoom = _zoom.getZoom();
            min.scale(zoom);
            max.scale(zoom);
            
            IMapMode mmode = MapModeUtil.getMapMode(f);
            min.height = mmode.LPtoDP(min.height);
            min.width = mmode.LPtoDP(min.width);
            max.height = mmode.LPtoDP(max.height);
            max.width = mmode.LPtoDP(max.width);
            
            //also the check for north and west and the corresponding 
            //corrections were not in the original GMF code (@see ResizableEditPolicyEx
            if (min.width > rect.width) {
                if ((request.getResizeDirection() & PositionConstants.WEST) != 0) {
                    rect.x += (int) Math.floor((rect.width - min.width)*zoom);
                }
                rect.width = min.width;
            } else if (max.width < rect.width) {
                if ((request.getResizeDirection() & PositionConstants.WEST) != 0) {
                    rect.x -= (int) Math.floor((rect.width - max.width)*zoom);
                }
                rect.width = max.width;
            }
            
            if (min.height>rect.height) {
                if ((request.getResizeDirection() & PositionConstants.NORTH) != 0) {
                    rect.y += (int) Math.floor((rect.height - min.height)*zoom);
                }
                rect.height = min.height;
            } else if (max.height < rect.height) {
                if ((request.getResizeDirection() & PositionConstants.NORTH) != 0) {
                    rect.y -= (int) Math.floor((rect.height - max.height)*zoom);
                }
                rect.height = max.height;
            }

        }
        feedback.translateToRelative(rect);
        feedback.setBounds(rect);
        
        updateFeedbackWithOverlappingAreas((FeedbackShape)feedback, request);
    }
    
    /**
     * Creates the figure used for feedback.
     * This figure can paint overlapping area differently.
     * 
     * @return the new feedback figure
     */
    @Override
    protected IFigure createDragSourceFeedbackFigure() {
        // Use a ghost rectangle for feedback
        RectangleFigure r = new FeedbackShape();
//        FigureUtilities.makeGhostShape(r);
//        r.setForegroundColor(ColorConstants.white);
        r.setBounds(getInitialFeedbackBounds());
        addFeedback(r);
        return r;
    }
    
    /**
     * @return The dragsource feedback figure on which overlapping areas can be set.
     */
    protected FeedbackShape getDragSourceFeedbackFigureCasted() {
        return (FeedbackShape) super.getDragSourceFeedbackFigure();
    }
    
    /**
     * Computes the areas of the feedback figure that overlap with other shapes
     * and that the user should be warn about.
     * 
     * @param feedback The feedback figure with its bounds already updated to
     * match the current figure.
     * @param request The current request
     */
    protected void updateFeedbackWithOverlappingAreas(FeedbackShape feedback, ChangeBoundsRequest request) {
        Rectangle absBounds = feedback.getBounds().getCopy();
        feedback.translateToAbsolute(absBounds);
        
        Rectangle sibAbsBounds = new Rectangle();
        Collection<Rectangle> overlaps = null;
        
        Set selectedEditParts = new HashSet(request.getEditParts());
        Collection<?extends EditPart> otherEPs = getOtherSelectedEditParts(request);
        if (otherEPs != null) {
            selectedEditParts.addAll(otherEPs);
        }
        //add a couple of edit parts
        //for example for a multiple shape move made with the insert space
        //we add the subrpcoess corresponding to the subprocess compartement being resized
        for (Object ep : request.getEditParts()) {
            if (ep instanceof SubProcessSubProcessBodyCompartmentEditPart) {
                selectedEditParts.add(((SubProcessSubProcessBodyCompartmentEditPart)ep).getParent());
            }
        }

        //if we are in a sub-process we don't want to resize or move out of it either.
        //unless this is a re-parenting:
        boolean isChangingScope = isChangingScopeRequest(request);
        
        if (!isChangingScope
                && getHost().getParent() instanceof SubProcessSubProcessBodyCompartmentEditPart
                && !selectedEditParts.contains(getHost().getParent())
                && !selectedEditParts.contains(((SubProcessSubProcessBodyCompartmentEditPart)getHost().getParent()).getParent())) {
            //compute the area of the feedback figure that is not contained in the
            //compartment.
            SubProcessSubProcessBodyCompartmentEditPart spCompartment =
                (SubProcessSubProcessBodyCompartmentEditPart)getHost().getParent();
            IFigure compartmentFigure = spCompartment.getFigure();
            
            IFigure currentFigure = getHostFigure().getParent();
            while (currentFigure != null && currentFigure != spCompartment) {
                if (currentFigure instanceof FreeformViewport) {
                    compartmentFigure = currentFigure;
                    break;
                }
                currentFigure = currentFigure.getParent();
            }
            sibAbsBounds.setBounds(compartmentFigure.getBounds());
            spCompartment.getCompartmentFigure().translateToAbsolute(sibAbsBounds);
            sibAbsBounds.intersect(absBounds);
            if (sibAbsBounds.height != absBounds.height
                        || sibAbsBounds.width != absBounds.width) {
                //the moved shape is not included inside the sub-process compartment:
                //show the area that is outside.
                if (overlaps == null) {
                    overlaps = new ArrayList<Rectangle>();
                }
                //it could be up to 8 rectangles but we compute using "columns"
                //left and right of the container, top and bottom of the container
                
                if (sibAbsBounds.height == 0 || sibAbsBounds.width == 0) {
                    //trivial case: it does not intersect at all the entire shape
                    //must not be moved or resized here.
                    overlaps.clear();
                    overlaps.add(feedback.getBounds().getCopy());
                    feedback.setOverlappingBounds(overlaps);
                    setIsOverlapping(request, overlaps != null);
                    return;
                }
                
              //we know we intersects as trivial case was taken care of.
                if (absBounds.x < sibAbsBounds.x) {
                    //we are too much on the left
                    Rectangle leftColumn = new Rectangle(absBounds.x, absBounds.y,
                            sibAbsBounds.x - absBounds.x,
                            absBounds.height);
                    feedback.translateToRelative(leftColumn);
                    overlaps.add(leftColumn);
                }
                if (absBounds.y < sibAbsBounds.y) {
                    //we are too much on the top.
                    Rectangle topColumn = new Rectangle(absBounds.x, absBounds.y,
                            absBounds.width,
                            sibAbsBounds.y - absBounds.y);
                    feedback.translateToRelative(topColumn);
                    overlaps.add(topColumn);
                }
                if (absBounds.x + absBounds.width > sibAbsBounds.x + sibAbsBounds.width) {
                    //we are too much on the right.
                    Rectangle rightColumn = new Rectangle(
                            sibAbsBounds.x + sibAbsBounds.width, absBounds.y,
                            absBounds.x + absBounds.width - sibAbsBounds.x - sibAbsBounds.width,
                            absBounds.height);
                    feedback.translateToRelative(rightColumn);
                    overlaps.add(rightColumn);
                }
                if (absBounds.y + absBounds.height > sibAbsBounds.y + sibAbsBounds.height) {
                    //we are too much at the bottom
                    Rectangle bottomColumn = new Rectangle(
                            absBounds.x, sibAbsBounds.y + sibAbsBounds.height,
                            absBounds.width,
                            absBounds.y + absBounds.height - sibAbsBounds.y - sibAbsBounds.height);
                    feedback.translateToRelative(bottomColumn);
                    overlaps.add(bottomColumn);
                }
            }
        }
        
        List children = getHost().getParent().getChildren();
        //now go through the siblings of the container for the host and compute the overlapping areas:
        for (Object child : children) {
            if (selectedEditParts.contains(child)) {
                //only compute overlapps with shapes that are not modified by this command.
                continue;
            }
            if (!(child instanceof ActivityEditPart || child instanceof SubProcessEditPart)) {
                continue;//we only take care of overlapping with othe activities and
                //sub-processes.
            }
            if (child instanceof SubProcessEditPart && isChangingScope) {
                continue;//if we are chaning scope it is ok to overlap as
                //it might be the new destination.
            }
            TaskDragEditPartsTrackerEx.setBoundsForOverlapComputation(
                    (IGraphicalEditPart)child, sibAbsBounds);
            sibAbsBounds.intersect(absBounds);
            if (sibAbsBounds.width != 0 && sibAbsBounds.height != 0) {
                if (overlaps == null) {
                    overlaps = new ArrayList<Rectangle>();
                }
                feedback.translateToRelative(sibAbsBounds);
                overlaps.add(sibAbsBounds.getCopy());
            }
        }
        //if we are a subprocess and this is a resize (not a move),
        //we must not overlap with the children:
        if (request.getSizeDelta() != null  && 
                (request.getSizeDelta().height != 0 || request.getSizeDelta().width != 0)
                && getHost() instanceof SubProcessEditPart 
                && !((SubProcessEditPart) getHost()).getPrimaryShape().isCollapsed()) {
            absBounds.crop(SubProcessEditPart.INSETS);
            //absBounds.crop(SubProcessEditPart.INSETS);//twice
            SubProcessSubProcessBodyCompartmentEditPart body =
                (SubProcessSubProcessBodyCompartmentEditPart)
                ((IGraphicalEditPart)getHost()).getChildBySemanticHint(
                        SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID+"");
            if (body != null) {
                //children.addAll(getHost().getChildren());
                for (Object child : body.getChildren()) {
                    //this time: we take the child bounds in the absolute
                    //coords and see if they are contained within the feedback bounds:
                    if (selectedEditParts.contains(child)) {
                        //only compute overlapps with shapes that are not modified by this command.
                        continue;
                    }
                    if (!(child instanceof ActivityEditPart || child instanceof SubProcessEditPart)) {
                        continue;//we only take care of overlapping with othe activities and
                        //sub-processes.
                    }
                    TaskDragEditPartsTrackerEx.setBoundsForOverlapComputation(
                            (IGraphicalEditPart)child, sibAbsBounds);
                    if (!absBounds.contains(sibAbsBounds)) {
                    //the moved shape is not included inside the sub-process compartment:
                    //show the area that is outside.
                        if (overlaps == null) {
                            overlaps = new ArrayList<Rectangle>();
                        }
                        feedback.translateToRelative(sibAbsBounds);
                        overlaps.add(sibAbsBounds.getCopy());
                    }
                }
            }
        }

        //set the feedback during the move.
        feedback.setOverlappingBounds(overlaps);
        //add data to the request so other policies know that an overlap was detected
        //or not.
        setIsOverlapping(request, overlaps != null && !overlaps.isEmpty());
    }
    
    /** key in the request extended data to store
     * if the current move/resize is creating an overlap */
    private static final String IS_OVER_LAPPING = "IsOverLapping";
    /** key in the request extended data to store
     * if the current move/resize is creating an overlap */
    private static final String OTHER_SELECTED_EDITPARTS = "OtherSelectedEditParts";
    
    /** key in the request extended data to store
     * if the current move/resize is used during a scope change of the moved activity */
    private static final String IS_CHANGING_SCOPE = "ChangingScope";
    
    /**
     * @param request
     * @return true if the request is overlapping.
     */
    public static boolean isOverlappingRequest(Request request) {
        return request.getExtendedData() != null
            && request.getExtendedData().containsKey(IS_OVER_LAPPING);
    }
    
    /**
     * @param request
     * @return true if the request is overlapping.
     */
    public static boolean isChangingScopeRequest(Request request) {
        return request.getExtendedData() != null
            && Boolean.TRUE.equals(request.getExtendedData().get(IS_CHANGING_SCOPE));
    }
    
    @SuppressWarnings("unchecked")
    public static void setIsChangingScope(boolean changingScope, Request request) {
        Map data = request.getExtendedData();
        if (changingScope) {
            if (data == null) {
                data = new HashMap();
            }
            data.put(IS_CHANGING_SCOPE, Boolean.valueOf(changingScope));
        } else {
            if (data != null) {
                data.remove(IS_CHANGING_SCOPE);
            }
        }

    }
    
    /**
     * 
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Collection<?extends EditPart> getOtherSelectedEditParts(Request request) {
        return request.getExtendedData() != null
            ? (Collection<?extends EditPart>) request.getExtendedData().get(OTHER_SELECTED_EDITPARTS)
            : null;
    }
    /**
     * Set in the extended data of the request a collection of editparts that 
     * must not be takn into acount for the overlap computation.
     */
    @SuppressWarnings("unchecked")
    public static void setOtherSelectedEditParts(Collection<?extends EditPart> otherSelectedEPs, Request request) {
        Map data = request.getExtendedData();
        if (otherSelectedEPs != null && !otherSelectedEPs.isEmpty()) {
            if (data == null) {
                data = new HashMap();
            }
            data.put(OTHER_SELECTED_EDITPARTS, otherSelectedEPs);
        } else {
            if (data != null) {
                data.remove(OTHER_SELECTED_EDITPARTS);
            }
        }
    }

    
    @SuppressWarnings("unchecked")
    protected final void setIsOverlapping(ChangeBoundsRequest request, boolean isOverlapping) {
        Map extendedData = request.getExtendedData();
        if (isOverlapping) {
            if (extendedData == null) {
                extendedData = new HashMap();
            }
            extendedData.put(IS_OVER_LAPPING, Boolean.TRUE);
        } else {
            if (extendedData != null) {
                extendedData.remove(IS_OVER_LAPPING);
            }
        }
    }
    
        
    @Override
    protected List createSelectionHandles() {

        List<Handle> list = new ArrayList<Handle>();
        int directions = getResizeDirections();
        
        if (directions == 0) {
            NonResizableHandleKit.addHandles((GraphicalEditPart) getHost(),
                    list);
        } else if (directions != -1) {
            ResizableHandleKit.addMoveHandle((GraphicalEditPart) getHost(),
                    list);
            if ((directions & PositionConstants.EAST) != 0)
                ResizableHandleKit.addHandle((GraphicalEditPart) getHost(),
                        list, PositionConstants.EAST);
            else
                NonResizableHandleKit.addHandle((GraphicalEditPart) getHost(),
                        list, PositionConstants.EAST);
            if ((directions & PositionConstants.SOUTH_EAST) == PositionConstants.SOUTH_EAST)
                ResizableHandleKit.addHandle((GraphicalEditPart) getHost(),
                        list, PositionConstants.SOUTH_EAST);
            else
                NonResizableHandleKit.addHandle((GraphicalEditPart) getHost(),
                        list, PositionConstants.SOUTH_EAST);
            if ((directions & PositionConstants.SOUTH) != 0)
                ResizableHandleKit.addHandle((GraphicalEditPart) getHost(),
                        list, PositionConstants.SOUTH);
            else
                NonResizableHandleKit.addHandle((GraphicalEditPart) getHost(),
                        list, PositionConstants.SOUTH);
            if ((directions & PositionConstants.SOUTH_WEST) == PositionConstants.SOUTH_WEST)
                ResizableHandleKit.addHandle((GraphicalEditPart) getHost(),
                        list, PositionConstants.SOUTH_WEST);
            else
                NonResizableHandleKit.addHandle((GraphicalEditPart) getHost(),
                        list, PositionConstants.SOUTH_WEST);
            if ((directions & PositionConstants.WEST) != 0)
                ResizableHandleKit.addHandle((GraphicalEditPart) getHost(),
                        list, PositionConstants.WEST);
            else
                NonResizableHandleKit.addHandle((GraphicalEditPart) getHost(),
                        list, PositionConstants.WEST);
            if ((directions & PositionConstants.NORTH_WEST) == PositionConstants.NORTH_WEST)
                ResizableHandleKit.addHandle((GraphicalEditPart) getHost(),
                        list, PositionConstants.NORTH_WEST);
            else
                NonResizableHandleKit.addHandle((GraphicalEditPart) getHost(),
                        list, PositionConstants.NORTH_WEST);
            if ((directions & PositionConstants.NORTH) != 0)
                ResizableHandleKit.addHandle((GraphicalEditPart) getHost(),
                        list, PositionConstants.NORTH);
            else
                NonResizableHandleKit.addHandle((GraphicalEditPart) getHost(),
                        list, PositionConstants.NORTH);
            if ((directions & PositionConstants.NORTH_EAST) == PositionConstants.NORTH_EAST)
                ResizableHandleKit.addHandle((GraphicalEditPart) getHost(),
                        list, PositionConstants.NORTH_EAST);
            else
                NonResizableHandleKit.addHandle((GraphicalEditPart) getHost(),
                        list, PositionConstants.NORTH_EAST);
        } else {
            addHandles((GraphicalEditPart) getHost(), list);
        }

        return list;
    }

    /**
     * Fills the given List with handles at each corner and the north, south,
     * east, and west of the GraphicalEditPart.
     * 
     * @param part
     *            the owner GraphicalEditPart of the handles
     * @param handles
     *            the List to add the handles to
     */
    private void addHandles(GraphicalEditPart part, List<Handle> handles) {
        ResizableHandleKit.addMoveHandle(part, handles);
        MoveHandle moveHandle = (MoveHandle)handles.get(0);
        moveHandle.setBorder(null);
        
        safeAddHandle(handles, createHandle(part, PositionConstants.EAST));
        safeAddHandle(handles, createHandle(part, PositionConstants.SOUTH_EAST));
        safeAddHandle(handles, createHandle(part, PositionConstants.SOUTH));
        safeAddHandle(handles, createHandle(part, PositionConstants.SOUTH_WEST));
        safeAddHandle(handles, createHandle(part, PositionConstants.WEST));
        safeAddHandle(handles, createHandle(part, PositionConstants.NORTH_WEST));
        safeAddHandle(handles, createHandle(part, PositionConstants.NORTH));
        safeAddHandle(handles, createHandle(part, PositionConstants.NORTH_EAST));
    }
    
    private void safeAddHandle(List<Handle> handles, Handle handleOrNull) {
        if (handleOrNull != null) {
            handles.add(handleOrNull);
        }
    }

    /**
     * Creates new handle for the specified edito part.
     * 
     * @param owner
     *            the owner edit part
     * @param direction
     *            handle direction
     * @return newly cxreated handle.
     */
    protected abstract Handle createHandle(GraphicalEditPart owner,
            int direction);

    /**
     * Base class for resize handles.
     */
    protected abstract static class ResizeHandleEx extends SquareHandle {
        protected int cursorDirection;

        /**
         * Creates new resize handle for the specified edit part.
         * 
         * @param owner
         *            the owner edit part.
         * @param direction
         *            handle direction.
         */
        public ResizeHandleEx(GraphicalEditPart owner, int direction) {
            setOwner(owner);
            setLocator(new RelativeHandleLocator(owner.getFigure(), direction));
            setCursor(Cursors.getDirectionalCursor(direction, owner.getFigure()
                    .isMirrored()));
            cursorDirection = direction;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.gef.handles.AbstractHandle#createDragTracker()
         */
        protected abstract DragTracker createDragTracker();
        
        /**
         * Draws the handle with fill color and outline color dependent 
         * on the primary selection status of the owner editpart.
         *
         * @param g The graphics used to paint the figure.
         */
        public void paintFigure(Graphics g) {
            Rectangle r = getBounds();
            r.shrink(1, 1);
            try {
                g.setBackgroundColor(ColorConstants.white);//getFillColor());
                g.fillOval(r.x, r.y, r.width, r.height);
                g.setForegroundColor(ColorConstants.black);//getBorderColor()); 
                g.drawOval(r.x, r.y, r.width, r.height);
            } finally {
                //We don't really own rect 'r', so fix it.
                r.expand(1, 1);
            }
        }
        
        
    }
    
    /**
     * Creates a new AutoSize command
     * 
     * @param request
     * @return command
     */
    @Override
    protected Command getAutoSizeCommand(Request request) {
    	EObject semantic = ((IGraphicalEditPart) getHost()).resolveSemanticElement();
    	if (semantic == null) {
    	    return super.getAutoSizeCommand(request);
    	}
        IElementType type = ElementTypeRegistry.getInstance().
            getElementType(semantic);
        if (semantic instanceof Activity) {
        	type = ElementTypeEx.wrap(type, 
        			((Activity) semantic).getActivityType().getName());
        }
        Dimension size = BpmnShapesDefaultSizes.getDefaultSize(type).getCopy();
        TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
        Rectangle rect = new Rectangle(getHostFigure().getBounds().getLocation().getCopy(), size);
        ICommand resizeCommand = new SetBoundsCommand(editingDomain, 
            DiagramUIMessages.SetAutoSizeCommand_Label,
            (org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart) getHost(), rect);
        return new ICommandProxy(resizeCommand);
    }
}
