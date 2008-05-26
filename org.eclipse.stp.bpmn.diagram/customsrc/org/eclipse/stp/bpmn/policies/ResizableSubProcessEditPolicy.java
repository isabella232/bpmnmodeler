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
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.handles.AbstractHandle;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.figures.BpmnShapesDefaultSizes;
import org.eclipse.stp.bpmn.figures.FeedbackShape;
import org.eclipse.stp.bpmn.policies.ResizableActivityEditPolicy.SetGroupsCommand;
import org.eclipse.stp.bpmn.tools.SubProcessResizeTracker;
import org.eclipse.stp.bpmn.tools.TaskDragEditPartsTrackerEx;

/**
 * Resize edit policy for subprocesses.
 * 
 * Handles groups.
 * 
 * @author Antoine Toulme
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
	 * Creates a new AutoSize command
	 * 
	 * @param request
	 * @return command
	 */
    @Override
	protected Command getAutoSizeCommand(Request request) {
        TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) 
        		getHost()).getEditingDomain();
        Dimension size = SubProcessEditPart.EXPANDED_SIZE.getCopy();
        Dimension minSize = BpmnShapesDefaultSizes.getSubProcessMinSize(
                                (SubProcessEditPart) getHost());
        size.width = Math.max(size.width, minSize.width);
        size.height = Math.max(size.height, minSize.height);
        
		ICommand resizeCommand = new SetBoundsCommand(editingDomain, 
			DiagramUIMessages.SetAutoSizeCommand_Label,
			new EObjectAdapter((View) getHost().getModel()), 
			size); // by default expanded
		return new ICommandProxy(resizeCommand);
	}
    
    /** 
     * Overridden to set the groups on the subprocess
     */
    @Override
    protected Command getMoveCommand(ChangeBoundsRequest request) {
        CompoundCommand compound = new CompoundCommand();
        compound.add(super.getMoveCommand(request));
        compound.add(new ICommandProxy(
                new SetGroupsCommand(ResizableActivityEditPolicy.
                        findContainingGroups(request, (IGraphicalEditPart) getHost()), 
                        (Activity) ((IGraphicalEditPart) getHost()).resolveSemanticElement())));
        return compound;
    }
    
    /**
     * Overridden to set the groups on the subprocess
     */
    @Override
    public Command getResizeCommand(final ChangeBoundsRequest request) {
        CompoundCommand compound = new CompoundCommand();
        Command resizeCmd = super.getResizeCommand(request);
        ICommandProxy iSetGroupCmd = new ICommandProxy(
                new SetGroupsCommand(ResizableActivityEditPolicy.
                        findContainingGroups(request, (IGraphicalEditPart) getHost()), 
                        (Activity) ((IGraphicalEditPart) getHost()).resolveSemanticElement()));
        compound.add(resizeCmd);
        // resizing the body edit part figure directly, to help with the layout.
        compound.add(iSetGroupCmd);
        return compound;
    }
    
}
