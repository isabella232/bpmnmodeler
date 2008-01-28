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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.handles.AbstractHandle;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBorderCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.tools.SubProcessResizeTracker;
import org.eclipse.stp.bpmn.tools.TaskDragEditPartsTrackerEx;

/**
 * Resize edit policy for subprocesses.
 * 
 * @author MPeleshchyshyn
 * 
 */
public class ResizableSubProcessEditPolicy extends ResizableShapeEditPolicyEx {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.stp.bpmn.policies.ResizableShapeEditPolicyEx#createHandle(org.eclipse.gef.GraphicalEditPart,
     *      int)
     */
    @Override
    protected Handle createHandle(GraphicalEditPart owner, int direction) {
        return new SubProcessResizeHandle(owner, direction);
    }

    protected static class SubProcessResizeHandle extends ResizeHandleEx {

        public SubProcessResizeHandle(GraphicalEditPart owner, int direction) {
            super(owner, direction);
        }

        @Override
        protected DragTracker createDragTracker() {
            return new SubProcessResizeTracker(getOwner(), cursorDirection);
        }
    }

    @Override
    protected void replaceHandleDragEditPartsTracker(Handle handle) {
        if (handle instanceof AbstractHandle) {
            AbstractHandle h = (AbstractHandle) handle;
            h.setDragTracker(new TaskDragEditPartsTrackerEx((IGraphicalEditPart)getHost()));
        }
    }
    
    /**
	 * Cfreates a new AutoSize comamnd
	 * 
	 * @param request
	 * @return command
	 */
    @Override
	protected Command getAutoSizeCommand(Request request) {
        TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) 
        		getHost()).getEditingDomain();
        Dimension size = SubProcessEditPart.EXPANDED_SIZE.getCopy();
        Dimension minSize = getSubProcessMinSize((SubProcessEditPart) getHost());
        size.width = Math.max(size.width, minSize.width);
        size.height = Math.max(size.height, minSize.height);
        
		ICommand resizeCommand = new SetBoundsCommand(editingDomain, 
			DiagramUIMessages.SetAutoSizeCommand_Label,
			new EObjectAdapter((View) getHost().getModel()), 
			size); // by default expanded
		return new ICommandProxy(resizeCommand);
	}
    
    /**
     * Copied from SubProcessResizeTracker
     * Calculates subprocess' minimal size.
     * 
     * @param subprocessEditPart
     *            the subprocess edit part
     * @return calculated minimal size of the s
     */
    public Dimension getSubProcessMinSize(SubProcessEditPart subprocessEditPart) {
    	SubProcessSubProcessBodyCompartmentEditPart body = 
            (SubProcessSubProcessBodyCompartmentEditPart) subprocessEditPart.
            getChildBySemanticHint(BpmnVisualIDRegistry.getType(
                    SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID));
    	SubProcessSubProcessBorderCompartmentEditPart border = 
            (SubProcessSubProcessBorderCompartmentEditPart) subprocessEditPart.
            getChildBySemanticHint(BpmnVisualIDRegistry.getType(
                    SubProcessSubProcessBorderCompartmentEditPart.VISUAL_ID));
    	if (body == null) {
    		return new Dimension(0, 0);
    	}
        // now take in account the shapes in the pool
        Dimension maxRoomOfChildren = new Dimension(0, 0);
        for (Object ep : body.getChildren()) {
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
        maxRoomOfChildren.expand(SubProcessEditPart.INSETS.
        		getWidth(), SubProcessEditPart.INSETS.getHeight() + 2);
        maxRoomOfChildren.height += border.getFigure().getBounds().height;
        maxRoomOfChildren.height += subprocessEditPart.getAbsCollapseHandleBounds().height;
        return maxRoomOfChildren;
//        return ((SubProcessEditPart) subprocessEditPart).calcMinSize();
    }
}
