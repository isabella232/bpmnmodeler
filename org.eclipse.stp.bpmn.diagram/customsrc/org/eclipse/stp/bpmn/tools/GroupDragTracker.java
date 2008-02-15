/******************************************************************************
 * Copyright (c) 2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Date         Author             Changes
 * Feb 6, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.tools;

import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.SharedCursors;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.tools.DragEditPartsTrackerEx;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnDiagramEditPart;
import org.eclipse.swt.graphics.Cursor;

/**
 * The group drag tracker is taking care of reparenting the group if it is too big
 * to fit in the target edit part.
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class GroupDragTracker extends DragEditPartsTrackerEx {

    public GroupDragTracker(IGraphicalEditPart sourceEditPart) {
        super(sourceEditPart);
    }

    private BpmnDiagramEditPart getDiagramEditPart(IGraphicalEditPart part) {
        if (part instanceof BpmnDiagramEditPart) {
            return (BpmnDiagramEditPart) part;
        } else if (part == null) {
            throw new IllegalArgumentException("Invalid hierarchy");
        }
        return getDiagramEditPart((IGraphicalEditPart) part.getParent());
    }
    
    
    @Override
    protected boolean updateTargetUnderMouse() {
        boolean updated = super.updateTargetUnderMouse();
        EditPart targetEP = super.getTargetEditPart();
        if (((GraphicalEditPart) targetEP) != null && targetEP == getSourceEditPart().getParent()) { 
            Rectangle target = ((GraphicalEditPart) targetEP).getFigure().getBounds().getCopy();
            Rectangle source = ((GraphicalEditPart) getSourceEditPart()).getFigure().getBounds().getCopy();
            Point loc = getLocation().getCopy();
            Viewport vp =  ((ScalableFreeformRootEditPart) ((GraphicalEditPart) getSourceEditPart()).
                    getViewer().getRootEditPart()).getZoomManager().getViewport();
//            vp.translateToAbsolute(loc);
            source.setLocation(loc);
            vp.translateToAbsolute(source);
//            vp.translateToAbsolute(target);
            System.err.println(target + " " + source + "TODO, have those coordinates expressed in the same referential"); //REMOVE
            if (!target.contains(source)) {
                System.err.println("target does not contain source"); //REMOVE
                setTargetEditPart(getDiagramEditPart((IGraphicalEditPart) super.getTargetEditPart()));
                updated = true;
            }
        }
        return updated;
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
            } else {
                return SharedCursors.CURSOR_TREE_MOVE;
            }
        }
        return super.calculateCursor();
    }
    
    @Override
    protected Command getCommand() {
        return super.getCommand();
    }
}
