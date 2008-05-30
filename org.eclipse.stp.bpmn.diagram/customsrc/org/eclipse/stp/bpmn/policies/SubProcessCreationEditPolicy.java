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
package org.eclipse.stp.bpmn.policies;

import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.Group;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;
import org.eclipse.stp.bpmn.figures.BpmnShapesDefaultSizes;
import org.eclipse.stp.bpmn.policies.ResizableActivityEditPolicy.SetGroupsCommand;
import org.eclipse.stp.bpmn.policies.ResizableActivityEditPolicy.SetLanesCommand;


/**
 * Set subprocess figure default size.
 * <p>
 * Make sure a non-executable command is returned when the container for the
 * element to create is not the host of this policy. (ie that an element created
 *  by this sub-process is indeed created in this same sub-process.
 * </p>
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class SubProcessCreationEditPolicy extends CreationEditPolicyEx {

    @Override
    protected Command getCreateElementAndViewCommand(
            CreateViewAndElementRequest request) {
        IElementType type = (IElementType) request.getViewAndElementDescriptor().
            getElementAdapter().getAdapter(IElementType.class);
        if (type != null && (
                BpmnElementTypes.Activity_2001.getId().equals(type.getId()) ||
                BpmnElementTypes.Activity_2003.getId().equals(type.getId()) ||
                BpmnElementTypes.SubProcess_2002.getId().equals(type.getId()))) {
            Rectangle rect = new Rectangle();
            rect.setLocation(request.getLocation());
            if (request.getSize() != null) {
                rect.setSize(request.getSize());
            }
            if (rect.getSize().width == -1 ||
                    rect.getSize().height == -1) {
                rect.setSize(BpmnShapesDefaultSizes.getDefaultSize(type));
            }
            List<Group> groups = ResizableActivityEditPolicy.findContainingGroups(
                    rect, getHost().getViewer());
            SetGroupsCommand command = new ResizableActivityEditPolicy.SetGroupsCommand(
                    groups, request, ((IGraphicalEditPart) getHost()).resolveSemanticElement());
            SetLanesCommand lanesCommand = new ResizableActivityEditPolicy.SetLanesCommand(
                    ResizableActivityEditPolicy.findContainingLanes(rect, getHost().getViewer()), 
                    request,
                    ((IGraphicalEditPart) getHost()).resolveSemanticElement());
            CompoundCommand compound = new CompoundCommand();
            compound.add(super.getCreateElementAndViewCommand(request));
            compound.add(new ICommandProxy(command));
            compound.add(new ICommandProxy(lanesCommand));
            return compound;
        } else if (type != null 
                && BpmnElementTypes.Lane_2007.getId().equals(type.getId())) {
            Rectangle rect = new Rectangle();
            rect.setLocation(request.getLocation());
            if (request.getSize() != null) {
                rect.setSize(request.getSize());
            }
            if (rect.getSize().width == -1 
                    || rect.getSize().height == -1 
                    || rect.getSize().width == 0 
                    || rect.getSize().height == 0) {
                rect.setSize(BpmnShapesDefaultSizes.getDefaultSize(type));
                // since that won't work, as the layout disposes the lanes automatically:
                Pool host = (Pool) ((IGraphicalEditPart) getHost()).resolveSemanticElement();
                int height = ((IGraphicalEditPart) getHost()).getFigure().getSize().height;
                rect.height = host.getLanes().size() != 0 ? height/host.getLanes().size() : height;
            }
            
            List<Activity> activities = ResizableLaneEditPolicy.findContainedActivities(
                    rect, getHost().getViewer());
            CompoundCommand compound = new CompoundCommand();
            compound.add(super.getCreateElementAndViewCommand(request));
            compound.add(new ICommandProxy(new ResizableLaneEditPolicy.SetActivitiesCommand(
                    activities, 
                    request, 
                    ((IGraphicalEditPart) getHost()).resolveSemanticElement())));
            return compound;
        }
        return super.getCreateElementAndViewCommand(request);
    }
}
