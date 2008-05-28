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
 * Date           	Author          Changes 
 * 04 dec 2006   	hmalphettes  	Created 
 **/

package org.eclipse.stp.bpmn.policies;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.handles.AbstractHandle;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.ResizeTracker;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.tools.DragEditPartsTrackerEx;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;

/**
 * Resize edit policy for lanes: only the bottom lane and if it is not
 * the lane at the bottom.
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ResizableLaneEditPolicy extends ResizableShapeEditPolicyEx {
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.stp.bpmn.policies.ResizableShapeEditPolicyEx#createHandle(org.eclipse.gef.GraphicalEditPart,
     *      int)
     */
    protected Handle createHandle(GraphicalEditPart owner, int direction) {
        return new LaneResizeHandle(owner, direction);
    }

    /**
     * Resize handle class.
     */
    protected static class LaneResizeHandle extends ResizeHandleEx {
        /**
         * Creates new instance of handle
         * 
         * @param owner
         *            the owner
         * @param direction
         *            handle direction
         */
        public LaneResizeHandle(GraphicalEditPart owner, int direction) {
            super(owner, direction);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.stp.bpmn.policies.ResizableShapeEditPolicyEx.ResizeHandleEx#createDragTracker()
         */
        @Override
        protected DragTracker createDragTracker() {
            return new ResizeTracker(getOwner(), cursorDirection);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.NonResizableEditPolicy#getMoveCommand(org.eclipse.gef.requests.ChangeBoundsRequest)
     */
    @Override
    protected Command getMoveCommand(ChangeBoundsRequest request) {
        ChangeBoundsRequest req = new ChangeBoundsRequest(REQ_MOVE_CHILDREN);
        req.setEditParts(request.getEditParts());
        req.setMoveDelta(new Point(0,req.getMoveDelta().y));
        req.setSizeDelta(new Dimension(0,req.getSizeDelta().height));
        req.setLocation(request.getLocation());
        req.setExtendedData(request.getExtendedData());
        req.setResizeDirection(PositionConstants.NORTH_SOUTH);
        return getHost().getParent().getCommand(req);
//        return UnexecutableCommand.INSTANCE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.ResizableEditPolicy#getResizeCommand(org.eclipse.gef.requests.ChangeBoundsRequest)
     */
    @Override
    protected Command getResizeCommand(ChangeBoundsRequest request) {
        // if you want a lane to be expanded to the north and the lane happens to be the first lane,
        // and there is no space above it, forbid the resize.
        if (request.getMoveDelta().y < 0 && 
                getHostFigure().getBounds().y == PoolPoolCompartmentEditPart.INSETS.top) {
            return UnexecutableCommand.INSTANCE;
        }
        
        ChangeBoundsRequest req = new ChangeBoundsRequest(REQ_RESIZE_CHILDREN);
        req.setEditParts(request.getEditParts());

        req.setMoveDelta(request.getMoveDelta());
        req.setSizeDelta(request.getSizeDelta());
        req.setLocation(request.getLocation());
        req.setExtendedData(request.getExtendedData());
        req.setResizeDirection(request.getResizeDirection());
        return getHost().getParent().getCommand(req);
    }

    protected void replaceHandleDragEditPartsTracker(Handle handle) {
        if (handle instanceof AbstractHandle) {
            AbstractHandle h = (AbstractHandle) handle;
            h.setDragTracker(new DragEditPartsTrackerEx(getHost()) {
                @Override
                protected void addSourceCommands(boolean isMove,
                        CompoundCommand command) {

                    if (!isCloneActive()) {
                        Request request = getTargetRequest();
                        request.setType(isMove ? REQ_MOVE
                                : RequestConstants.REQ_DRAG);
                        command.add(getHost().getCommand(request));
                        // Iterator iter = getOperationSet().iterator();
                        // while (iter.hasNext()) {
                        // EditPart editPart = (EditPart) iter.next();
                        // command.add(editPart.getCommand(request));
                        // }
                        request.setType(RequestConstants.REQ_DROP);
                    } else {
                        super.addSourceCommands(isMove, command);
                    }
                }
            });
        }
    }
}
