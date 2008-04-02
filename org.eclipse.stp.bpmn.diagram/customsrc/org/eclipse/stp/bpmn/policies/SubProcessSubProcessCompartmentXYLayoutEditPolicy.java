/**
 * Copyright (C) 2000-2007, Intalio Inc.
 *
 * The program(s) herein may be used and/or copied only with the
 * written permission of Intalio Inc. or in accordance with the terms
 * and conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *
 * Dates       		 Author              Changes
 * Feb 21, 2007      Antoine Toulm&eacute;   Creation
 */
package org.eclipse.stp.bpmn.policies;

import java.util.List;

import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.LabelEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.diagram.edit.parts.Group2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.GroupEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.MessagingEdgeNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SequenceEdgeNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.figures.BpmnShapesDefaultSizes;

/**
 * Does the big job of having the compartment been set to the size of the 
 * children.
 * Methods were copied from the PoolPoolCompartmentXYLayoutEditPolicy
 * and adapted to sub processes. That might be a source of problems.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class SubProcessSubProcessCompartmentXYLayoutEditPolicy extends XYLayoutEditPolicy {

	private static final Dimension MIN_DIMENSION = SubProcessEditPart.EXPANDED_SIZE.getCopy().expand(0, -SubProcessEditPart.BORDER_HEIGHT/2);

    /**
     * if set to true negative moves are chunked.
     */
    public static final boolean RESIZE_COMPARTMENT_ON = false;
    
//    private Point divideByZoom(Point p) {
//        ZoomManager zoom = ((DiagramRootEditPart) getHost().getRoot()).getZoomManager();
//        p.x = (int) Math.floor(p.x/zoom.getZoom());
//        p.y = (int) Math.floor(p.y/zoom.getZoom());
//        return p;
//    }
    
    private double getZoom() {
        return ((DiagramRootEditPart) getHost().getRoot()).getZoomManager().getZoom();
    }
    
//    private Dimension divideByZoom(Dimension p) {
//        ZoomManager zoom = ((DiagramRootEditPart) getHost().getRoot()).getZoomManager();
//        p.width = (int) Math.floor(p.width/zoom.getZoom());
//        p.height = (int) Math.floor(p.height/zoom.getZoom());
//        return p;
//    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#getResizeChildrenCommand(org.eclipse.gef.requests.ChangeBoundsRequest)
     */
    protected Command getResizeChildrenCommand(ChangeBoundsRequest request) {
        if (request.getMoveDelta().x == 0 && request.getMoveDelta().y == 0 &&
                request.getSizeDelta().width == 0 && request.getSizeDelta().height == 0) {
            return null;
        }
        
     // let's skip groups, they do not resize the subprocess.
        boolean onlyContainsGroups = true;
        for (Object o : request.getEditParts()) {
            onlyContainsGroups = onlyContainsGroups && (
                    o instanceof GroupEditPart || o instanceof Group2EditPart);
        }
        if (onlyContainsGroups) {
            return super.getResizeChildrenCommand(request);
        }
        CompoundCommand command = new CompoundCommand();
        if (RESIZE_COMPARTMENT_ON) {
//            List<Request> requests = chunkNegativeMove(request);
//            if (requests != null && (!requests.isEmpty())) {
//                for (Request negativeReq : requests) {
//                    Command co = getHost().getParent().getParent().
//                    getCommand(negativeReq);
//                    command.add(co);
//                }
//            }
        } else {
            if (negativeMoveDetected(request)) {
                return UnexecutableCommand.INSTANCE;
            }
        }
        command.add(super.getResizeChildrenCommand(request));
    	boolean unexec = avoidOverlap(request, command);
        if (unexec) {
            return UnexecutableCommand.INSTANCE;
        }
  /*      if (RESIZE_COMPARTMENT_ON) {
    //      have the subprocess grow bigger
            ChangeBoundsRequest req = new ChangeBoundsRequest(
                    RequestConstants.REQ_RESIZE_CHILDREN);
            req.setEditParts(getHost().getParent());
            req.setCenteredResize(true);
            req.setSizeDelta(getMinSizeForSP(request));
            req.setMoveDelta(new Point(0, 0));
            if (req.getSizeDelta().height != 0 || req.getSizeDelta().width != 0) {
                Command parentCo = getHost().getParent().getParent().getCommand(req);
                command.add(parentCo);
            }
        }*/
        // if the subprocess is resized in the northern direction, shapes should be moved south as an opposite
        // so that the user thinks he is resizing the subprocess by adding space at the top.
        Point moveChildren = new Point(0, 0);
        if ((request.getResizeDirection() & PositionConstants.NORTH) != 0) {
            moveChildren.y = request.getSizeDelta().height;
        }
        // same thing here: if the subprocess is being resized by the left
        // the user must have the impression he is adding space.
        if ((request.getResizeDirection() & PositionConstants.WEST) != 0) {
            moveChildren.x = request.getSizeDelta().width;
        }
        if ((moveChildren.x != 0 || moveChildren.y != 0) 
                && request.getEditParts() != null 
                && !request.getEditParts().isEmpty()) {
            for (Object part : request.getEditParts()) {
                if (part instanceof SubProcessEditPart) {
                    final IGraphicalEditPart comp = ((SubProcessEditPart) part).
                        getChildBySemanticHint(BpmnVisualIDRegistry.getType(
                            SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID));
                    if (comp != null) { // if comp is null this wasn't a subprocess in the first place
                        ChangeBoundsRequest move = new ChangeBoundsRequest(org.eclipse.gef.RequestConstants.REQ_MOVE_CHILDREN);
                        move.setMoveDelta(moveChildren);
                        move.setEditParts(comp.getChildren());
                        Command c = comp.getCommand(move);
                        if (c != null && c.canExecute()) {
                            command.add(c);
                            System.err.println("able to move the children.");
                        } else {
                            System.err.println("Unable to move the children " + c);
                        }
                    }
                }
            }
        }
        
    	// we want to have labels snap back to their original places
    	// when a sequence edge or a messaging edge is moved.
    	if (BpmnDiagramEditorPlugin.getInstance().getPreferenceStore().
                getBoolean(BpmnDiagramPreferenceInitializer.PREF_SNAP_BACK_ON_MOVE)) {
            makeSnapBackCommands(command, request);
        }
    	return command;
    }

    /**
     * Creates snap back commands by iterating over the source and target
     * connections of the interesting parts
     * @param compound the compound command to populate
     * @param request the current request
     */
    private void makeSnapBackCommands(CompoundCommand compound, ChangeBoundsRequest request) {
        for (Object epObj : request.getEditParts()) {
            if (epObj instanceof AbstractGraphicalEditPart) {
                iterateOverConnections(
                        ((AbstractGraphicalEditPart) epObj).getSourceConnections(), 
                        compound);
                iterateOverConnections(
                        ((AbstractGraphicalEditPart) epObj).getTargetConnections(), 
                        compound);
            }
        }
    }
    
    /**
     * Iterates over connections, and gets their labels, then asks them for a snap back command.
     * @param connections the list of connections.
     * @param compound the compound command populated
     */
    private void iterateOverConnections(List connections, CompoundCommand compound) {
        Request snapBack = new Request(RequestConstants.REQ_SNAP_BACK);
        for (Object con : connections) {
            if (con instanceof ConnectionEditPart) {
                LabelEditPart label = (LabelEditPart) ((ConnectionEditPart) con).getChildBySemanticHint(
                        BpmnVisualIDRegistry.getType(SequenceEdgeNameEditPart.VISUAL_ID));
                if (label == null) {
                    label = (LabelEditPart) ((ConnectionEditPart) con).getChildBySemanticHint(
                            BpmnVisualIDRegistry.getType(MessagingEdgeNameEditPart.VISUAL_ID));
                }
                if (label != null) {
                    compound.add(label.getCommand(snapBack));
                }
            }
        }
    }
    
    /**
     * if a negative move is detected, this method returns true.
     * @param request
     * @return true if a negative move is detected
     */
    private boolean negativeMoveDetected(ChangeBoundsRequest request) {
        if (request == null) {
            return false;
        }
        boolean isNegative = false;
        for (Object ep : request.getEditParts()) {
            Point loc = ((GraphicalEditPart) ep).getFigure().getBounds().
                getLocation().getCopy();
            int xmove = (int) (loc.x + request.getMoveDelta().x/getZoom());
            if (xmove < 0) {
                isNegative = true;
            }
            int ymove = (int) (loc.y + request.getMoveDelta().y/getZoom());
            if (ymove < 0) {
                isNegative = true;
            }
//            if (isNegative) {
//                System.err.println("moving " + ep + " " + request.getMoveDelta().y + " to " + ymove
//                        + " xmove=" + xmove);
//            }
        }
        return isNegative;
    }
    /**
     * This mysterious method checks that the move of the element in the
     * subprocess wouldn't place a shape at negative coordinates, which would
     * mess up pretty much everything.
     * It resizes the parent in the direction of the move,
     * and it moves the children, so that they do not seem to have moved.
     * @param request
     */
//    private List<Request> chunkNegativeMove(
//    		ChangeBoundsRequest request) {
//    	if (request == null) {
//    		return null;
//    	}
//    	boolean isNegative = false;
//		Point maxMove = divideByZoom(request.getMoveDelta().getCopy());
//		for (Object ep : request.getEditParts()) {
//			Point loc = ((GraphicalEditPart) ep).getFigure().getBounds().
//				getLocation().getCopy();
//			int xmove = (int) (loc.x + request.getMoveDelta().x/getZoom() - SubProcessEditPart.INSETS.left);
//			if (xmove <= 0) {
//				maxMove.x =  -loc.x + SubProcessEditPart.INSETS.left ;
//				isNegative = true;
//			}
//			int ymove = (int) (loc.y + request.getMoveDelta().y/getZoom() - SubProcessEditPart.INSETS.top);
//			if (ymove <= 0) {
//				maxMove.y = -loc.y + SubProcessEditPart.INSETS.top;
//				isNegative = true;
//			}
//		}
//		Point p = new Point(request.getMoveDelta().x/getZoom() - maxMove.x, 
//				request.getMoveDelta().y/getZoom() - maxMove.y).scale(getZoom());
//		
//		request.setMoveDelta(maxMove.scale(getZoom()));
////		 one last test. Maybe the move is negative and going too far.
//		// we have modified the request already
//		if (isNegative) {
//			// test this case :
//			// |
//			// |    /
//			// |   /
//			// | |/_
//			// |___________________
//			
//			int toobigy = request.getMoveDelta().y + request.getSizeDelta().height;
//			toobigy /= getZoom();
//			int maxHeight = 0;
//			for (Object ep : request.getEditParts()) {
//				maxHeight = Math.max(maxHeight, ((GraphicalEditPart) ep).
//						getFigure().getBounds().height +((GraphicalEditPart) ep).
//						getFigure().getBounds().y);
//			}
//			if (getHostFigure().getBounds().height < (toobigy + maxHeight)) {
//				isNegative = false;
//			}
//		}
//		if (isNegative) {
////			now test this case :
//			/*
//			 * _______________
//			 * 				   /  |
//			 *                   /   |
//			 *                  /    |
//			 * 
//			 */
//			
//			int toobigx = request.getMoveDelta().x + request.getSizeDelta().width;
//			toobigx /= getZoom();
//			int maxWidth = 0;
//			for (Object ep : request.getEditParts()) {
//				maxWidth = Math.max(maxWidth, ((GraphicalEditPart) ep).
//						getFigure().getBounds().width +((GraphicalEditPart) ep).
//						getFigure().getBounds().x);
//			}
//			if (getHostFigure().getBounds().width < (toobigx + maxWidth)) {
//				isNegative = false;
//			}
//		}
//		if (!isNegative) {
//			return null;
//		}
//		
//		ChangeBoundsRequest compartmentRequest = 
//			new ChangeBoundsRequest(RequestConstants.REQ_RESIZE_CHILDREN);
//		
//		Dimension d = new Dimension(Math.abs(p.x), Math.abs(p.y));
//		compartmentRequest.setMoveDelta(p);
//		compartmentRequest.setSizeDelta(d);
//		compartmentRequest.setEditParts(getHost().getParent());
//		
//		
//		
//		ChangeBoundsRequest childrenRequest = 
//			new ChangeBoundsRequest(RequestConstants.REQ_RESIZE_CHILDREN);
//		Point childrenMove = new Point(-p.x, -p.y);
//		childrenRequest.setMoveDelta(childrenMove);
//		childrenRequest.setEditParts(new ArrayList(getHost().getChildren()));
//		childrenRequest.getEditParts().removeAll(request.getEditParts());
//		// we use a copy of the array so that the new edit part will not be moved
//		
//		List<Request> requests = new ArrayList<Request>();
//		requests.add(compartmentRequest);
//		if (!childrenRequest.getEditParts().isEmpty()) {
//			requests.add(childrenRequest);
//		}
//		return requests;
//	}

	/**
     * Gets the minimum size when creating a new element in the pool.
     * If the element is created on a border, it will
     * add some width and/or length to have it in the compartment.
     * @param request
     * @return
     *
    private Dimension getMinSizeForSP(CreateRequest request) {
    	ChangeBoundsRequest req = new ChangeBoundsRequest(
    			RequestConstants.REQ_RESIZE_CHILDREN);
    	req.setEditParts(Collections.EMPTY_LIST);
    	Dimension dim = getMinSizeForSP(req);
    	Dimension requestSize = request.getSize() == null ? null :
    		request.getSize().getCopy();
    	if (requestSize == null || requestSize.width == -1 && requestSize.height == -1) {
    		// default minimum size for adding a new shape.
//    		Rectangle r = (Rectangle) getConstraintFor(request);
    		requestSize = new Dimension(150, 100);
    		if (request instanceof CreateViewRequest) {
    			List descriptors = ((CreateViewRequest) request).getViewDescriptors();
    			if (!descriptors.isEmpty()) {
    				ViewDescriptor desc = (ViewDescriptor) descriptors.get(0);
    				IElementType type = (IElementType) desc.getElementAdapter().
    					getAdapter(IElementType.class);
    				requestSize = BpmnShapesDefaultSizes.getDefaultSize(type).getCopy();
    			}
    		}
    	}
    	Dimension initialdim = ((GraphicalEditPart) getHost()).getFigure().getSize().getCopy();
    	if (initialdim.height == 0 && initialdim.width == 0) {
    		return initialdim; // meaning no resize, the subprocess is 
    		//being initialized at the same time.
    	}
    	
    	Point loc = request.getLocation().getCopy();
    	getHostFigure().translateToAbsolute(loc);
    	getHostFigure().translateToRelative(loc);
    	Rectangle rect = getHostFigure().getBounds().getCopy();
    	getHostFigure().translateToAbsolute(rect);
    	loc.x = loc.x - rect.x;
    	loc.y = loc.y - rect.y;
    	dim.width = Math.max(dim.width, loc.x + requestSize.width 
    			 + SubProcessEditPart.INSETS.getWidth());
    	dim.height = Math.max(dim.height, loc.y + requestSize.height 
    			+ SubProcessEditPart.INSETS.getHeight());
    	
    	dim.width = dim.width - initialdim.width + SubProcessEditPart.INSETS.getWidth();
    	dim.height = dim.height - initialdim.height + SubProcessEditPart.INSETS.getHeight();
    	if (dim.width < 0) {
    		dim.width = 0;
    	}
    	if (dim.height < 0) {
    		dim.height =  0;
    	}
    	return dim;
    }*/
    
    /**
     * Customize the computation of the minimum size of the sub-process.
     * This method can be overridden by specialized editors.
     * <p>
     * By default dataobjects, group and texts are not taken into account to
     * compute the minimum size.
     * </p>
     * @param childEditPart
     * @return true to take the figure created by this part and make sure that
     * the minimum size of the suprocess will take into account that
     * the shape must be visible. false to discount it.
     */
    protected boolean considerPartForMinimumSizeComputations(IGraphicalEditPart childEditPart) {
        EObject domainObject = childEditPart.resolveSemanticElement();
        if (domainObject != null && domainObject instanceof Artifact) {
            //don't take into account the dataobject, group and text 
            //shapes for the minimum size.
            return false;
        }
        return true;
    }
    
    /**
     * Finds the minimum size for the subprocess by examining the children,
     * if they are modified by the request, then they are resized and or moved, 
     * so that the min size is changed accordingly.
     * 
     * Returns a delta, not the actual size for the subprocess.
     * @param request
     * @return
     *
    private Dimension getMinSizeForSP(ChangeBoundsRequest request) {
    	Dimension initialdim = ((GraphicalEditPart) getHost().getParent()).getFigure().getSize().getCopy();
    	Dimension dim = MIN_DIMENSION.getCopy();
    	for (Object child : getHost().getChildren()) {
    		IGraphicalEditPart part = (IGraphicalEditPart) child;
    		if (!considerPartForMinimumSizeComputations(part)) {
    		    continue;
    		}
    		
    		Rectangle r = part.getFigure().getBounds().getCopy();
    		int w = r.x + r.width;
    		if (request.getEditParts().contains(part) || 
    		        getHost().getViewer().getSelectedEditParts().contains(part)) {
    			w += (request.getSizeDelta().width + request.getMoveDelta().x)/getZoom();
    		} 
    		dim.width = Math.max(dim.width, w);
    		int h = r.y + r.height;
    		if (request.getEditParts().contains(part) || 
                    getHost().getViewer().getSelectedEditParts().contains(part)) {
    			h += (request.getSizeDelta().height + 
    			    request.getMoveDelta().y)/getZoom();
    		}
    		dim.height = Math.max(dim.height, h);
    	}
    	
    	SubProcessFigure spFigure = ((SubProcessEditPart) getHost().getParent()).getPrimaryShape();
    	// now calculate the delta.
    	dim.width = dim.width - initialdim.width + SubProcessEditPart.INSETS.getWidth();
    	dim.height = dim.height - initialdim.height + 
    	    spFigure.getFigureSubProcessBorderFigure().getBorderHeight() + 
    	    spFigure.getFigureSubProcessNameFigure().getBounds().height  + SubProcessEditPart.INSETS.getHeight();
    	
    	dim.width = Math.max(dim.width, 0);
    	dim.height = Math.max(dim.height, 0);
    	
    	return dim;
    }*/
    
    /**
     * When creating something, create some place for it.
     */
    @Override
    protected Command getCreateCommand(CreateRequest request) {
    	//  negative location on creation.
        SubProcessSubProcessBodyCompartmentEditPart spCompartment =
            (SubProcessSubProcessBodyCompartmentEditPart)getHost();
        IFigure compartmentFigure = spCompartment.getFigure();
        
        IFigure currentFigure = getHostFigure().getParent();
        while (currentFigure != null && currentFigure != spCompartment) {
            if (currentFigure instanceof FreeformViewport) {
                compartmentFigure = currentFigure;
                break;
            }
            currentFigure = currentFigure.getParent();
        }
    	Point loc = request.getLocation().getCopy();
    	Rectangle here = compartmentFigure.getBounds().getCopy();
    	if (here.x == 0 && here.y == 0) {
    		// just initializing the subprocess itself !
    		// we shouldn't change the location of the task,
    		// as the location of the subprocess is subject to change
    	} else {
    		compartmentFigure.translateToAbsolute(here);
    		getHostFigure().translateToAbsolute(loc);
    		getHostFigure().translateToRelative(loc);
    		loc.x = loc.x - here.x;
    		loc.y = loc.y - here.y;
    		//find out the size of the object to be created
    		Dimension requestSize = request.getSize() == null ? 
                    null : 
                request.getSize().getCopy().scale(1/getZoom());
    		
            if (requestSize == null) {
                // default minimum size for adding a new shape.
                requestSize = new Dimension(300, 100);
                
                if (request instanceof CreateViewRequest) {
                    List descriptors = ((CreateViewRequest) request).getViewDescriptors();
                    if (!descriptors.isEmpty()) {
                        ViewDescriptor desc = (ViewDescriptor) descriptors.get(0);
                        if (desc.getElementAdapter() != null) {
                            IElementType type = (IElementType) desc.getElementAdapter().
                            getAdapter(IElementType.class);
                            requestSize = BpmnShapesDefaultSizes.getDefaultSize(type).getCopy();
                        }
                    }
                }
            } 
            //if the object is too much on the right
            if (loc.x + requestSize.width*getZoom() > here.width) {
                request.getLocation().x -= loc.x + Math.round(requestSize.width*getZoom()) - here.width +1 + 1*getZoom();
            }
            // if the object is created too low
            if (loc.y + requestSize.height*getZoom() > here.height) {
                request.getLocation().y -= loc.y + Math.round(requestSize.height*getZoom()) - here.height +1 + 1*getZoom();
            }
            // if the object is created too much on the left 
    		if (loc.x < 0) {
    			request.getLocation().x += - loc.x + 1;
    		}
    		// if the object is created too much at the top
    		if (loc.y < 0) {
    			request.getLocation().y += - loc.y + 1;
    		}
    		
    		
    	}
    	
  //  	if (!RESIZE_COMPARTMENT_ON) {
    	    return super.getCreateCommand(request);
  //  	}
    	
  /*   	CompoundCommand command = new CompoundCommand();
        command.add(super.getCreateCommand(request));
        ChangeBoundsRequest thisSizeReq = new ChangeBoundsRequest(
    			RequestConstants.REQ_RESIZE_CHILDREN);
        thisSizeReq.setCenteredResize(true);
        thisSizeReq.setEditParts(getHost().getParent());
        thisSizeReq.setSizeDelta(getMinSizeForSP(request));
        if (thisSizeReq.getSizeDelta().height == 0 
                && thisSizeReq.getSizeDelta().width == 0) {
        	return command;
        } else {
            Command co = getHost().getParent().getParent().getCommand(thisSizeReq);
            command.add(co);
            return command;
        }*/
    }
    
    /**
     * This methods determines if the request can execute or not depending
     * on the overlap with other shapes. By default it returns false if
     * an overlap is detected.
     * <p>
     * Currently we rely on the ResizableShapeEditPolicyEx to have added data
     * to the request if it is overlapping.
     * </p>
     * @param request the current request
     * @param compound the compound command.
     * @return true if an overlap is detected
     */
    private boolean avoidOverlap(ChangeBoundsRequest request, 
            CompoundCommand compound) {
        return ResizableShapeEditPolicyEx.isOverlappingRequest(request);
    }
    


}