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
 * 12 Oct 2006   	ASerhiychuk         Created 
 **/

package org.eclipse.stp.bpmn.tools;

import java.util.List;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.SharedCursors;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.gef.tools.SelectEditPartTracker;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.tools.DragEditPartsTrackerEx;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.MessageVertex;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.diagram.edit.parts.DataObjectEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.Group2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.GroupEditPart;
import org.eclipse.swt.graphics.Cursor;

/**
 * @author Anton Serhiychuk
 * @author Mykola Peleshchyshyn Ability to press SHIFT 
 * during the drag to be able to move the shape from one container to another
 * @author hmalphettes Compute the nearest none-overlapping location
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class TaskDragEditPartsTrackerEx extends DragEditPartsTrackerEx {
    
    //set to false to deactivate the overlapping position
    private static final boolean DO_FIX_LOCATION_TO_AVOID_OVERLAP = true;
    //set to false to deactivate the use of the most recent executable command
    private static final boolean DO_CACHE_LAST_EXECUTABLE_COMMAND = true;
    
    private Command _mostRecentExecutableCommand;
    private ChangeBoundsRequest _mostRecentTargetForExecutableCommand;
    private Point _mostRecentExecutableLocation;
    private Point _mostRecentExecutableMoveDelta;
    private Dimension _mostRecentExecutableSizeDelta;
    private Object _mostExecutableRecentType;
    private int _srcHalfHeight = 0, _srcHalfWidth = 0;
    
    private ChangeBoundsRequest clonedLastExecutableRequest;
    private int dLeft = -1, dRight, dTop, dBottom;

    private EObject _sourceModel;
    
    //rectangles used during computations.
    private Rectangle _tmp = new Rectangle();
    private Rectangle _tmp2 = new Rectangle();
    
    /**
     * If the current command is not executable and the current location is
     * further than 32 pixels on x and y axis from the position where the command was executable
     * then don't even use the last executable command.
     */
    private static int MAX_DELTA_FROM_LAST_EXECUTABLE_POSITION = 32;
    
    /**
     * @param sourceEditPart The edit part for which the drag tracker is created.
     */
    public TaskDragEditPartsTrackerEx(IGraphicalEditPart sourceEditPart) {
        super(sourceEditPart);
        Rectangle srcBounds = 
            sourceEditPart.getFigure().getBounds();
        _sourceModel = sourceEditPart.getPrimaryView().getElement();
        if (srcBounds.height > 0) {
            _srcHalfHeight = srcBounds.height / 2;
        }
        if (srcBounds.width > 0) {
            _srcHalfWidth = srcBounds.width / 2;
        }
    }


    /**
     * Determines wheter reparent is enabled. Returns <code>true</code> in
     * case if SHIFT is pressed and source edit part has Pool Compartment parent
     * and has no connections to other edit parts.
     * 
     * @return <code>true</code> in case if SHIFT is pressed and source edit
     *         part has Pool Compartment parent, <code>false</code> otherwise.
     */
    private boolean isReparentEnabled() {
    	if (getTargetEditPart() == null) {
    		return false;
    	}
        IGraphicalEditPart source = (IGraphicalEditPart) getSourceEditPart();
        if (source.resolveSemanticElement() instanceof Activity) {
        	Activity a = ((Activity) source.resolveSemanticElement());
        	if (!a.getIncomingEdges().isEmpty() || !a.getOutgoingEdges().isEmpty()) {
        		return false;
        	}
        	
        	Pool targetPool = getPool(((IGraphicalEditPart) getTargetEditPart()).
        			resolveSemanticElement());
        	if (targetPool == null) {
        		return false;
        	}
        	for (Object msg : a.getOutgoingMessages()) {
        	    MessageVertex target = ((MessagingEdge) msg).getTarget();
        		if (targetPool.equals(getPool(target))) {
        			// you cannot move your activity to a pool
        			// on which you have a connection
        			return false;
        		}
        	}
        		
        	for (Object msg : a.getIncomingMessages()) {
        	    MessageVertex src = ((MessagingEdge) msg).getSource();
        		if (targetPool.equals(getPool(src))) {
        			// you cannot move your activity to a pool
        			// on which you have a connection
        			return false;
        		}
        	}
        } else if (source.resolveSemanticElement() instanceof Artifact) {
            if (getTargetEditPart() == getSourceEditPart().getParent() ||
                    getTargetEditPart() == null) {
                return false;
            }
        	return true;
        }
        return getCurrentInput().isShiftKeyDown();
//                && (source.getParent() instanceof PoolPoolCompartmentEditPart || 
//                		source.getParent() instanceof SubProcessSubProcessBodyCompartmentEditPart)
//                && source.getSourceConnections().size() == 0
//                && source.getTargetConnections().size() == 0;
    }

    private Pool getPool(Object elt) {
    	if (elt instanceof Pool) {
    		return (Pool) elt;
    	}
    	if (elt instanceof Activity) {
    		if (((Activity) elt).getEventHandlerFor() != null) {
    			return getPool(((Activity) elt).getEventHandlerFor());
    		}
    	}
    	if (elt instanceof Vertex) {
    		return getPool(((Vertex) elt).getGraph());
    	}
    	return null;
    }
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.diagram.ui.tools.DragEditPartsTrackerEx#isMove()
     */
    @Override
    protected boolean isMove() {
        boolean isMove;
        if (isReparentEnabled()) {
            isMove = false;
        } else {
            isMove = true;
        }
        return isMove;
    }
    
    @Override
    protected boolean isCloneActive() {
    	return false;
    }
    
    protected Command getCommand() {
        if (isReparentEnabled()) {
            _mostRecentExecutableCommand = null;
            Request request = getTargetRequest();
            if (getTargetEditPart() != null) {
                request.setType(REQ_ADD);
                Command command = getTargetEditPart().getCommand(request);
                return command;
            } else {
                return UnexecutableCommand.INSTANCE;
    		}
    	} else {
    	    Command res = super.getCommand();
    	    if (!DO_CACHE_LAST_EXECUTABLE_COMMAND) {
    	        return res;
    	    }
    		if (res.canExecute()) {
    		    //store the most recent executable command.
    		    _mostRecentExecutableCommand = res;
    		    _mostRecentTargetForExecutableCommand = (ChangeBoundsRequest)getTargetRequest();
    		    _mostRecentExecutableLocation = _mostRecentTargetForExecutableCommand.getLocation();
    		    _mostRecentExecutableSizeDelta = _mostRecentTargetForExecutableCommand.getSizeDelta();
    		    _mostRecentExecutableMoveDelta = _mostRecentTargetForExecutableCommand.getMoveDelta();
    		    _mostExecutableRecentType = _mostRecentTargetForExecutableCommand.getType();
    		    clonedLastExecutableRequest = null;
            } else if (_mostRecentExecutableCommand != null) {
                //look if we are very far away from the last executable position or not
                //it is not very subtle but it is fairly straight forward to code this:
                if (Math.abs(_mostRecentExecutableLocation.x - getLocation().x) >
                                MAX_DELTA_FROM_LAST_EXECUTABLE_POSITION + _srcHalfWidth
                        || Math.abs(_mostRecentExecutableLocation.y - getLocation().y) >
                                MAX_DELTA_FROM_LAST_EXECUTABLE_POSITION + _srcHalfHeight) {
                    //the current location is too far away
                    //from the last location where the command is executable.
                    //return the unexecutbale command.
                    return res;
                }
                return _mostRecentExecutableCommand;
    		}
    		return res;
    	}
    }
        
    /**
     * @return The nearest position for the cursor such that the dragged shape
     * does not overlap with the shape of the that the mouse is currently over.
     * 
     * TODO: something smarter so that when the cursor is not yet over a different shape but the
     * dragged part is over such other shape it finds it too.
     * 
     */
    @Override
    protected Point getLocation() {
        if (isMove() && DO_FIX_LOCATION_TO_AVOID_OVERLAP &&
                getTargetEditPart() != null && getTargetEditPart() instanceof IGraphicalEditPart) {
            IGraphicalEditPart currTarget = (IGraphicalEditPart) getTargetEditPart();
            if (currTarget instanceof DataObjectEditPart || 
                    currTarget instanceof GroupEditPart || 
                    currTarget instanceof Group2EditPart) {
                //nevermind overlaps with dataobjecteditpart
                //act as if they are transparent
                currTarget = (IGraphicalEditPart)currTarget.getParent();
            }
            if (currTarget.getPrimaryView() == null || 
                    currTarget.getPrimaryView().getElement() == null) {
                return super.getLocation();//nevermind things are not ready yet.
            }
            if (!currTarget.getPrimaryView().getElement().equals(_sourceModel.eContainer())) {
                //this means the cursor is over another shape that would be overlapping.
            } else {
                //let's see if the dragged shape is intersecting with other shapes.
                //we only look at one shape at a time.
                //as soon as it intersects we stop looking.
                IGraphicalEditPart srcEp = (IGraphicalEditPart)getSourceEditPart();
                _tmp2.setBounds(srcEp.getFigure().getBounds());
                srcEp.getFigure().translateToAbsolute(_tmp2);
                for (Object ep : currTarget.getChildren()) {
                    if (ep != srcEp && ep instanceof IGraphicalEditPart &&
                            !((ep instanceof DataObjectEditPart) || 
                                    (ep instanceof GroupEditPart) || 
                                    (ep instanceof Group2EditPart))) {
                        IGraphicalEditPart sibling = (IGraphicalEditPart)ep;
                        _tmp.setBounds(sibling.getFigure().getBounds());
                        sibling.getFigure().translateToAbsolute(_tmp);
                       // divideByZoom(_tmp);
                        //_tmp.scale(_zoom.getZoom());
                        if (_tmp.intersects(_tmp2)) {
                            Point currLocation = super.getLocation(); 
                            _tmp.intersect(_tmp2);
                            //let's see if we need to move vertically or horizontally:
                            //the smallest is the one chosen:
                            if (_tmp.width > _tmp.height) {
                                //up or down:
                                //the cursor position cannot be in the interesection so
                                //it is used as reference point:
                                if (currLocation.y > _tmp.y) {//down:
                                    return new Point(currLocation.x, currLocation.y+_tmp.height);
                                } else {//up:
                                    return new Point(currLocation.x, currLocation.y-_tmp.height);
                                }
                            } else {
                                //left or right:
                                //the cursor position cannot be in the interesection so
                                //it is used as reference point:
                                if (currLocation.x > _tmp.x) {//right:
                                    return new Point(currLocation.x+_tmp.width, currLocation.y);
                                } else {//left:
                                    return new Point(currLocation.x-_tmp.width, currLocation.y);
                                }
                            }
                        }
                    }
                }
                
                currTarget = null;
            }
            
            if (currTarget != null) {
                //look for the closest point outside of the bounds of the targetEditPart:
                _tmp.setBounds(currTarget.getFigure().getBounds());
                currTarget.getFigure().translateToAbsolute(_tmp);
                
                Point currLocation = super.getLocation();
                
                //compute the "nearest" vertical xor horizontal location on the
                //border on the border of the shape.
                int dx = 0, dy = 0;
                computeSourceDistanceFromBorders();
                //compute the potential snap on the x-axis
                if (_tmp.x + _tmp.width / 2 > currLocation.x) {
                    //snap to _tmp.x that is the left of the overlap shape
                    dx = currLocation.x - _tmp.x;
                    //also do as if the cursor on the source shape was exactly on
                    //the right of the source shape:
                    dx += dRight;
                } else {
                    //snap to _tmp.x+_tmp.width (right)
                    dx = currLocation.x - _tmp.x - _tmp.width;
                    //translate the cursor so it is as if it is on the left border of 
                    //the source shape.
                    dx -= dLeft;
                }
                //compute the potential snap on the y axis
                if (_tmp.y + _tmp.height / 2 > currLocation.y) {
                    //snap to _tmp.y that is the top of the overlap shape
                    dy = currLocation.y - _tmp.y;
                    //also do as if the cursor on the source shape was exactly on
                    //the right of the source shape:
                    dy += dBottom;
                } else {
                    //snap to _tmp.y+_tmp.height (bottom)
                    dy = currLocation.y - _tmp.y - _tmp.height;
                    //translate the cursor so it is as if it is on the top border of 
                    //the source shape.
                    dy -= dTop;
                }
                //snap to the closest one.
                //System.err.println("dx=" + dx + "  dy=" + dy);
                if (Math.abs(dx) > Math.abs(dy)) {
                    return new Point(currLocation.x, currLocation.y - dy);
                } else {
                    return new Point(currLocation.x - dx, currLocation.y);
                }
            }
        }
        return super.getLocation();
    }
    
    /**
     * Compute the distances between the cursor's position and the 4 borders
     * of the shaped being moved.
     */
    private void computeSourceDistanceFromBorders() {
        if (dLeft != -1) {
            return;
        }
        _tmp.setBounds(((IGraphicalEditPart) getSourceEditPart()).getFigure().getBounds());
        ((IGraphicalEditPart) getSourceEditPart()).getFigure().translateToAbsolute(_tmp);
        
        Point start = getStartLocation();
        dLeft = start.x - _tmp.x;
        dRight = _tmp.x + _tmp.width - start.x;
        dTop = start.y - _tmp.y;
        dBottom = _tmp.y + _tmp.height - start.y;
    }


    //copy of the constant declared in the super. but that is not visible
    private static final int _FLAG_SOURCE_FEEDBACK = SelectEditPartTracker.MAX_FLAG << 1;
    
    /**
     * Asks the edit parts in the {@link AbstractTool#getOperationSet() operation set} to 
     * show source feedback.
     * 
     * If the current command is not executable, make sure the displayed feedback is for
     * the last executable command.
     * 
     */
    @Override
    protected void showSourceFeedback() {
        List editParts = getOperationSet();
        if (_mostRecentExecutableCommand != null) {
            if (clonedLastExecutableRequest == null) {
                clonedLastExecutableRequest = new ChangeBoundsRequest(_mostExecutableRecentType);
                if (_mostRecentExecutableSizeDelta != null)
                    clonedLastExecutableRequest.setSizeDelta(_mostRecentExecutableSizeDelta);
                if (_mostRecentExecutableMoveDelta != null)
                    clonedLastExecutableRequest.setMoveDelta(_mostRecentExecutableMoveDelta);
                if (_mostRecentExecutableLocation != null)
                    clonedLastExecutableRequest.setLocation(_mostRecentExecutableLocation);
                clonedLastExecutableRequest.setEditParts(_mostRecentTargetForExecutableCommand.getEditParts());
            }
            for (int i = 0; i < editParts.size(); i++) {
                EditPart editPart = (EditPart) editParts.get(i);
                    editPart.showSourceFeedback(clonedLastExecutableRequest);
            }
        } else {
            for (int i = 0; i < editParts.size(); i++) {
                EditPart editPart = (EditPart) editParts.get(i);
                editPart.showSourceFeedback(getTargetRequest());
            }
        }
        setFlag(_FLAG_SOURCE_FEEDBACK, true);
    }

    
    /**
     * Update the cursor to let the user know in adavnce if the move
     * is allowed or not.
     */
    protected Cursor calculateCursor() {
        Command command = getCurrentCommand();
        if (command == null || !command.canExecute())
            return getDisabledCursor();
        if (isInState(STATE_DRAG_IN_PROGRESS)
                || isInState(STATE_ACCESSIBLE_DRAG_IN_PROGRESS)) {
            if (isMove()) {
                return SharedCursors.HAND;
            } else if (isReparentEnabled()) {
                return SharedCursors.CURSOR_TREE_MOVE;
            }
        }
        return super.calculateCursor();
    }
    
    /**
     * Execute the currently active command or the most recently executable one if such thing.
     */
    protected void executeCurrentCommand() {
        Command curCommand = getCurrentCommand();
        if (curCommand != null && curCommand.canExecute()) {
            executeCommand(curCommand);
        }
        setCurrentCommand(null);
    }

}
