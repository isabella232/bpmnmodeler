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
 * Date           	Author              Changes 
 * 12 Sep 2006   	MPeleshchyshyn  	Created 
 **/

package org.eclipse.stp.bpmn.policies;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.handles.AbstractHandle;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.tools.DragEditPartsTrackerEx;
import org.eclipse.stp.bpmn.diagram.edit.parts.LaneEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;
import org.eclipse.stp.bpmn.tools.PoolResizeTracker;

/**
 * Resize edit policy for pools.
 * 
 * @author MPeleshchyshyn
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ResizablePoolEditPolicy extends ResizableShapeEditPolicyEx {
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.stp.bpmn.policies.ResizableShapeEditPolicyEx#createHandle(org.eclipse.gef.GraphicalEditPart,
     *      int)
     */
    protected Handle createHandle(GraphicalEditPart owner, int direction) {
        return new PoolResizeHandle(owner, direction);
    }

    /**
     * Resize handle class.
     */
    protected static class PoolResizeHandle extends ResizeHandleEx {
        /**
         * Creates new instance of handle
         * 
         * @param owner
         *            the owner
         * @param direction
         *            handle direction
         */
        public PoolResizeHandle(GraphicalEditPart owner, int direction) {
            super(owner, direction);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.stp.bpmn.policies.ResizableShapeEditPolicyEx.ResizeHandleEx#createDragTracker()
         */
        @Override
        protected DragTracker createDragTracker() {
            return new PoolResizeTracker(getOwner(), cursorDirection);
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

        req.setMoveDelta(request.getMoveDelta());
        req.setSizeDelta(request.getSizeDelta());
        req.setLocation(request.getLocation());
        req.setExtendedData(request.getExtendedData());
        return getHost().getParent().getCommand(req);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.ResizableEditPolicy#getResizeCommand(org.eclipse.gef.requests.ChangeBoundsRequest)
     */
    @Override
    protected Command getResizeCommand(ChangeBoundsRequest request) {
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
    
    /**
	 * Creates a new AutoSize command, setting the pool size
     * to the minimum (200, 200) or to the minimum size occupied
     * by its children.
	 * 
	 * @param request
	 * @return command
	 */
    @Override
	protected Command getAutoSizeCommand(Request request) {
        TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
        Dimension size = new Dimension(200, 
                PoolEditPart.POOL_HEIGHT);
        Dimension minSize = getMinPoolSize(getHost().getParent().getChildren());
        size.width = Math.max(size.width, minSize.width);
        size.height = Math.max(size.height, minSize.height);
        
        ChangeBoundsRequest req = new ChangeBoundsRequest();
        req.setEditParts(Collections.singletonList(getHost()));
        req.setMoveDelta(new Point(0, 0));
        Dimension currentSize = getHostFigure().getSize().getCopy();
        req.setSizeDelta(new Dimension(size.width - currentSize.width, size.height - currentSize.height));
        return getResizeCommand(req);
	}
    
    /**
     * Copied from PoolResizeTracker, but this time we care about lanes.
     * @param parts
     * @return the minimal dimension for the pool
     */
    private Dimension getMinPoolSize(List parts) {
        Dimension result = new Dimension(200, 50);

        if (parts == null) {
            return result;
        }

        for (int i = 0; i < parts.size(); i++) {
            if (parts.get(i) instanceof PoolEditPart) {
                PoolEditPart pool = (PoolEditPart) parts.get(i);
                IGraphicalEditPart poolCompartment = pool
                        .getChildBySemanticHint(Integer
                                .toString(PoolPoolCompartmentEditPart.VISUAL_ID));
                if (poolCompartment == null) {
                    continue;
                }
                int nameWidth = poolCompartment.getFigure().getBounds().x + 1;
                
                // now take in account the shapes in the pool
                Dimension maxRoomOfChildren = new Dimension(0, 0);
                for (Object ep : poolCompartment.getChildren()) {
                	if (ep instanceof IGraphicalEditPart) {
                		// we use the figure as width and lengths may be 
                		// not initialized on the views objects
                		IFigure figure = ((IGraphicalEditPart) ep).getFigure();
                		Rectangle bounds = figure.getBounds();
                		maxRoomOfChildren.height = Math.max(bounds.y + 
                				bounds.height, maxRoomOfChildren.height);
                		maxRoomOfChildren.width = Math.max(bounds.x + 
                				bounds.width, maxRoomOfChildren.width);
                	}
                }
                maxRoomOfChildren.expand(PoolPoolCompartmentEditPart.INSETS.
                		getWidth(), PoolPoolCompartmentEditPart.INSETS.getHeight() + 2);
                
                if (getHost().equals(parts.get(i))) {
                	result.height = maxRoomOfChildren.height;
                }
                result.width = Math.max(maxRoomOfChildren.width + nameWidth, result.width);
                
            }
        }

        return result;
    }
}
