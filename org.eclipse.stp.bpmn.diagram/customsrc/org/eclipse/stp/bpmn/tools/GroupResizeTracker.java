/**
 *  Copyright (C) 2007, Intalio Inc.
 *
 *  The program(s) herein may be used and/or copied only with the
 *  written permission of Intalio Inc. or in accordance with the terms
 *  and conditions stipulated in the agreement/contract under which the
 *  program(s) have been supplied.
 *
 * Date         Author             Changes
 * Oct 2, 2007      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.tools;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnDiagramEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.GroupEditPart;

/**
 * This tool is in charge of dragging and resizing
 * the group edit parts.
 * 
 * When resizing out of the bounds of the current pool,
 * the resize operation becomes a reparent operation.
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class GroupResizeTracker extends ActivityResizeTracker {

    public GroupResizeTracker(GraphicalEditPart owner, int direction) {
        super(owner, direction);
    }

    private Command getResizeCommand() {
        if (getOwner() instanceof GroupEditPart) {
            Rectangle rect = ((GraphicalEditPart) getOwner().getParent()).getFigure().getBounds().getCopy();
            Rectangle source = getOwner().getFigure().getBounds().getCopy();
            source.resize(((ChangeBoundsRequest) getTargetRequest()).getSizeDelta());
            source.translate(((ChangeBoundsRequest) getTargetRequest()).getMoveDelta());
            if (source.bottom() > rect.getBottom().y  || 
                    source.getTop().y < rect.getTop().y ||
                    source.getRight().x > rect.getRight().x ||
                    source.getLeft().x < rect.getLeft().x) {
                getTargetRequest().setType(REQ_ADD);
                return getDiagramEditPart((IGraphicalEditPart) getOwner()).getCommand(getTargetRequest());
            }
        }
        return super.getCommand();
    }
    
    private BpmnDiagramEditPart getDiagramEditPart(IGraphicalEditPart part) {
        if (part instanceof BpmnDiagramEditPart) {
            return (BpmnDiagramEditPart) part;
        } else if (part == null) {
            throw new IllegalArgumentException("Invalid hierarchy"); //$NON-NLS-1$
        }
        return getDiagramEditPart((IGraphicalEditPart) part.getParent());
    }
}
