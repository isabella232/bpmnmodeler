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
 * 11 ��� 2006   	MPeleshchyshyn  	Created 
 **/

package org.eclipse.stp.bpmn.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.gef.handles.NonResizableHandleKit;
import org.eclipse.gef.handles.RelativeHandleLocator;
import org.eclipse.gef.handles.ResizableHandleKit;
import org.eclipse.gef.handles.SquareHandle;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableShapeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.commands.ElementTypeEx;
import org.eclipse.stp.bpmn.commands.SetBoundsCommandEx;
import org.eclipse.stp.bpmn.figures.BpmnShapesDefaultSizes;

/**
 * Abstract class for resize edit policies.
 * 
 * @author MPeleshchyshyn
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
abstract class ResizableShapeEditPolicyEx extends ResizableShapeEditPolicy {
    
    

    
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
