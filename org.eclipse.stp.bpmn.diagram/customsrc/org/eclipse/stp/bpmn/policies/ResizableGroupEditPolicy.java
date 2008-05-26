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
package org.eclipse.stp.bpmn.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.handles.AbstractHandle;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.Group;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.Activity2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.tools.GroupDragTracker;
import org.eclipse.stp.bpmn.tools.GroupResizeTracker;

/**
 * 
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class ResizableGroupEditPolicy extends ResizableArtifactEditPolicy {

    protected Handle createHandle(GraphicalEditPart owner, int direction) {
        return new GroupResizeHandle(owner, direction);
        
    }

    /**
     * Creates new handle for activity.
     */
    protected static class GroupResizeHandle extends ResizeHandleEx {
        public GroupResizeHandle(GraphicalEditPart owner, int direction) {
            super(owner, direction);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.stp.bpmn.policies.ResizableShapeEditPolicyEx.ResizeHandleEx#createDragTracker()
         */
        @Override
        protected DragTracker createDragTracker() {
            return new GroupResizeTracker(getOwner(), cursorDirection);
        }
    }

    @Override
    protected void replaceHandleDragEditPartsTracker(Handle handle) {
        if (handle instanceof AbstractHandle) {
            AbstractHandle h = (AbstractHandle) handle;
            h.setDragTracker(new GroupDragTracker((IGraphicalEditPart)getHost()));
        }
    }
    
    /**
     * This command sets the activities on the group.
     *
     * @author <a href="http://www.intalio.com">Intalio Inc.</a>
     * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
     */
    public static class SetActivitiesCommand extends AbstractTransactionalCommand {

        private Group _group;
        
        private List<Activity> _activities;
        
        public SetActivitiesCommand(Group elt, List<Activity> activities) {
            super((TransactionalEditingDomain) AdapterFactoryEditingDomain.
                    getEditingDomainFor(elt),
                    BpmnDiagramMessages.ResizableGroupEditPolicy_command_name, 
                    getWorkspaceFiles(elt));
            _group = elt;
            _activities = activities;
        }

        @Override
        protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
                IAdaptable info) throws ExecutionException {
            List<Activity> toRemove = new ArrayList<Activity>();
            List<Activity> activities = new ArrayList<Activity>(_activities);
            for (Activity a : _group.getActivities()) {
                if (!activities.remove(a)) {
                    toRemove.add(a);
                }
            }
            
            _group.getActivities().removeAll(toRemove);
            _group.getActivities().addAll(activities);
            return CommandResult.newOKCommandResult();
        }
        
    }
    
    private List<Activity> findContainedActivities(ChangeBoundsRequest request) {
        Rectangle rect = ((IGraphicalEditPart) getHost()).getFigure().getBounds().getCopy();
        ((IGraphicalEditPart) getHost()).getFigure().translateToAbsolute(rect);
        rect.translate(request.getMoveDelta());
        rect.resize(request.getSizeDelta());
        List<Activity> activities = new ArrayList<Activity>();
        for (Object fig : getHost().getViewer().getVisualPartMap().keySet()) {
            Object part = getHost().getViewer().getVisualPartMap().get(fig);
            if (part instanceof ActivityEditPart ||
                    part instanceof Activity2EditPart ||
                    part instanceof SubProcessEditPart) {
                Rectangle bounds = ((IGraphicalEditPart) part).getFigure().getBounds().getCopy();
                ((IGraphicalEditPart) part).getFigure().translateToAbsolute(bounds);
                if (rect.contains(bounds)) {
                    activities.add((Activity) ((IGraphicalEditPart) part).resolveSemanticElement());
                }
            }
        }
        return activities;
    }
    
    @Override
    protected Command getResizeCommand(ChangeBoundsRequest request) {
        CompoundCommand compound = new CompoundCommand();
        compound.add(super.getResizeCommand(request));
        
        List<Activity> activities = findContainedActivities(request);
        SetActivitiesCommand command = new SetActivitiesCommand(
                (Group) ((IGraphicalEditPart) getHost()).resolveSemanticElement(), 
                activities);
        compound.add(new ICommandProxy(command));
        return compound;
    }
    
    @Override
    protected Command getMoveCommand(ChangeBoundsRequest request) {
        CompoundCommand compound = new CompoundCommand();
        compound.add(super.getMoveCommand(request));
        
        List<Activity> activities = findContainedActivities(request);
        SetActivitiesCommand command = new SetActivitiesCommand(
                (Group) ((IGraphicalEditPart) getHost()).resolveSemanticElement(), 
                activities);
        compound.add(new ICommandProxy(command));
        return compound;
    }
}
