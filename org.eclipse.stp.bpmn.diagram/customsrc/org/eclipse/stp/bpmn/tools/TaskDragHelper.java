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
 * 03 Oct 2006   	MPeleshchyshyn         Created 
 **/

package org.eclipse.stp.bpmn.tools;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.ICompositeCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.stp.bpmn.commands.SetBoundsCommandEx;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;

/**
 * Helper class to be used by SubProcess and Pool compartments to handle drag
 * and resize operations.
 * 
 * @author MPeleshchyshyn
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 * @deprecated the work of changing the size of the subprocesses and pools
 * according to their children is now done in the XYLayout edit policies.
 */
final public class TaskDragHelper {
    public static final String UPDATE_CHILDREN_LOCATION_LABEL = BpmnDiagramMessages.TaskDragHelper_label;

    private static final String CONTAINER_BOUNDSCOMMAND_RESIZE_LABEL = BpmnDiagramMessages.TaskDragHelper_container_resize_label;

    public static final String PROPERTY_CHILD_RESIZED = "childResized"; //$NON-NLS-1$

    /**
     * Handles <code>PropertyChangeEvent</code> for the specified compartment
     * edit part. The event with name &quot;childResized&quot; holds bounds of
     * child figure. Resized parent of the specified edit part if necessary (if
     * bounds of child exceeds bounds of its container). Does nothing for
     * properties with names other than &quot;childResized&quot;.
     * 
     * @param event
     *            property change event to be handled.
     * @param editPart
     *            the edit part
     */
    public static void handlePropertyChangeEvent(PropertyChangeEvent event,
            ShapeCompartmentEditPart editPart) {
        if (PROPERTY_CHILD_RESIZED.equals(event.getPropertyName())) {
            Rectangle newContainerBounds = ((Rectangle) event.getNewValue())
                    .getCopy();
            GraphicalEditPart container = (GraphicalEditPart) event.getSource();
            updateContainerBounds(container, newContainerBounds, false);
        }
    }

    /**
     * Updates bounds of the specified edit part's container.
     * 
     * @param editPart
     *            source edit part which bounds were changed
     * @param sourceBounds
     *            new bounds of the source edit part
     * @param allowSourceRelocation
     *            allows specified source edit part relocation
     */
    public static void updateContainerBounds(GraphicalEditPart editPart,
            Rectangle sourceBounds, boolean allowSourceRelocation) {
        IFigure source = editPart.getFigure();
        Rectangle sourceBoundsAbsolute = sourceBounds.getCopy();
        source.translateToAbsolute(sourceBoundsAbsolute);

        // parent
        GraphicalEditPart parent = (GraphicalEditPart) editPart.getParent();
        // container
        GraphicalEditPart container = (GraphicalEditPart) parent.getParent();
        Rectangle containerBounds = container.getFigure().getBounds().getCopy();
        Rectangle containerBoundsAbsolute = containerBounds.getCopy();
        container.getFigure().translateToAbsolute(containerBoundsAbsolute);

        if (parent instanceof PoolPoolCompartmentEditPart) {
            
            if (((Boolean) parent.getStructuralFeatureValue(NotationPackage.eINSTANCE
                            .getDrawerStyle_Collapsed())).booleanValue()) {
                //when the pool is collapsed we must not update locations.
                return;
            }
        }
        Rectangle parentBounds = parent.getFigure().getBounds().getCopy();
        if (container instanceof PoolEditPart) {
            parentBounds.shrink(0, 5);
            parentBounds.width = parentBounds.width - 11;
            parentBounds.x = parentBounds.x + 6;
        } else {
            parentBounds.shrink(5, 5);
        }

        // Nothing can be beneath SubProcess collapse\expand button
        // if (parent instanceof SubProcessSubProcessBodyCompartmentEditPart) {
        // Rectangle collapseHandleBounds = ((SubProcessEditPart) container)
        // .getAbsCollapseHandleBounds();
        //
        // source.translateToRelative(collapseHandleBounds);
        // parentBounds.height = collapseHandleBounds.y - 1;
        // }
        Rectangle parentBoundsAbsolute = parentBounds.getCopy();
        parent.getFigure().translateToAbsolute(parentBoundsAbsolute);

        if ((parent instanceof SubProcessSubProcessBodyCompartmentEditPart 
                || parent instanceof PoolPoolCompartmentEditPart)
                && parent.getFigure().getBounds().width != 0
                && parent.getFigure().getBounds().height != 0) {
            if (!parentBoundsAbsolute.contains(sourceBoundsAbsolute)) {
                Rectangle newParentBounds = parentBoundsAbsolute.getUnion(
                        sourceBoundsAbsolute).getCopy();
                parent.getFigure().translateToRelative(newParentBounds);

                int deltaX = newParentBounds.x - parentBounds.x;
                int deltaY = newParentBounds.y - parentBounds.y;

                int deltaWidth = newParentBounds.width - parentBounds.width;
                int deltaHeight = newParentBounds.height - parentBounds.height;

                Rectangle newContainerBounds = containerBounds.resize(
                        deltaWidth, deltaHeight);
                newContainerBounds.translate(deltaX, deltaY);

                ICompositeCommand compositeCommand = new CompositeCommand(""); //$NON-NLS-1$
                ICommand containerBoundsCommand = new SetBoundsCommandEx(
                        container.getEditingDomain(), "", container, //$NON-NLS-1$
                        newContainerBounds);

                if (deltaX != 0 || deltaY != 0) {
                    for (Object child : parent.getChildren()) {
                        if (!allowSourceRelocation && child == editPart) {
                            continue;
                        }
                        Rectangle childBounds = ((GraphicalEditPart) child)
                                .getFigure().getBounds();
                        childBounds.translate(-deltaX, -deltaY);

                        if (childBounds.x < 0) {
                            childBounds.x = 0;
                        }

                        if (childBounds.y < 0) {
                            childBounds.y = 0;
                        }

                        ICommand childBoundsCommand = new SetBoundsCommand(
                                container.getEditingDomain(), "", //$NON-NLS-1$
                                (GraphicalEditPart) child, childBounds);
                        compositeCommand.add(childBoundsCommand);
                    }
                }
                compositeCommand.add(containerBoundsCommand);

                try {
                    compositeCommand.execute(new NullProgressMonitor(), null);
                } catch (ExecutionException e) {
                }
            }
        }
    }

    /**
     * Calculates new container bounds
     * 
     * @param source
     *            the source edit part
     * @param selectedObjects
     *            the list of selected edit parts
     * @param selectionBounds
     *            the union of selected edit parts
     * @param container
     *            container that holds selected edit parts
     * @param parentBounds
     *            source's parent bounds
     * @return command that refreshes container bounds
     */
    public static Command getNewContainerBoundsCommand(
            GraphicalEditPart source, List<GraphicalEditPart> selectedObjects,
            Rectangle selectionBounds, GraphicalEditPart container,
            Rectangle parentBounds, Dimension moveDelta) {
        Rectangle containerBounds = container.getFigure().getBounds().getCopy();
        final Insets SUBPROCESS_COMPARTMENT_INSETS = new Insets(5, 5, 5, 5);
        final Insets POOL_COMPARTMENT_INSETS = new Insets(5, 6, 5, 5);

        Insets containerInsets;
        if (container instanceof PoolEditPart) {
            containerInsets = POOL_COMPARTMENT_INSETS;
        } else {
            containerInsets = SUBPROCESS_COMPARTMENT_INSETS;
        }
        // Get zoom size
        double zoom = 1;
        RootEditPart root = source.getRoot();

        if (root instanceof ScalableFreeformRootEditPart) {
            zoom = ((ScalableFreeformRootEditPart) root).getZoomManager()
                    .getZoom();
        }

        // Apply zoom
        moveDelta.scale(1 / zoom);

        // Up direction
        int dy = moveDelta.height;
        Dimension childrenMoveDelta = new Dimension(0, 0);
        if (selectionBounds.y + moveDelta.height < 0) {
            containerBounds.translate(0, selectionBounds.y + moveDelta.height);
            childrenMoveDelta
                    .expand(0, -(selectionBounds.y + moveDelta.height));
            containerBounds.resize(childrenMoveDelta);
            dy = -selectionBounds.y;
        } else if (selectionBounds.y + selectionBounds.height
                + moveDelta.height > parentBounds.height
                - containerInsets.getHeight()) {
            // Down direction
            containerBounds.resize(0, selectionBounds.y
                    + selectionBounds.height + moveDelta.height
                    - parentBounds.height + containerInsets.getHeight());
        }

        int dx = moveDelta.width;

        // Left direction
        if (selectionBounds.x + moveDelta.width < 0) {
            containerBounds.translate(selectionBounds.x + moveDelta.width, 0);
            Dimension delta = new Dimension(
                    -(selectionBounds.x + moveDelta.width), 0);
            childrenMoveDelta.expand(delta);
            containerBounds.resize(delta);
            dx = -selectionBounds.x;
        } else if (selectionBounds.x + selectionBounds.width + moveDelta.width > parentBounds.width
                - containerInsets.getWidth()) {
            // Right direction
            containerBounds.resize(selectionBounds.x + selectionBounds.width
                    + moveDelta.width - parentBounds.width
                    + containerInsets.getWidth(), 0);
        }

        ICommand updateChildrenCommand = getUpdateChildrenLocationCommand(
                source, selectedObjects, container, new Dimension(dx, dy),
                childrenMoveDelta);

        Dimension containerResizeDelta = containerBounds.getSize()
                .getDifference(container.getFigure().getBounds().getSize());

        ChangeBoundsRequest setContainerBoundsRequest = new ChangeBoundsRequest(
                RequestConstants.REQ_RESIZE);
        setContainerBoundsRequest.setSizeDelta(containerResizeDelta);
        setContainerBoundsRequest.setEditParts(container);

        Command setContainerBoundsCommand = container
                .getCommand(setContainerBoundsRequest);

        Command command;
        if (updateChildrenCommand.canExecute()) {
            CompoundCommand compositeCommand = new CompoundCommand(
                    BpmnDiagramMessages.TaskDragHelper_command_name);
            if (setContainerBoundsCommand != null && 
            		setContainerBoundsCommand.canExecute()) {
                compositeCommand.add(setContainerBoundsCommand);
            }

            compositeCommand.add(new ICommandProxy(updateChildrenCommand));
            command = compositeCommand;
        } else {
            command = setContainerBoundsCommand;
        }

        return command;
    }

    /**
     * Calculates new container's CompartmentEditPart children location
     * 
     * @param source
     *            the source edit part
     * @param selectedObjects
     *            the list of selected edit parts
     * @param container
     *            container that holds selected edit parts
     * @param moveDelta
     *            the number of pixels that the mouse has been moved since that
     *            drag was started
     * @param boundsDelta
     *            the number of pixels container should be resized by
     * @param direction
     *            movement direction
     * @param childLocationCommand
     *            command to refresh children bounds
     * @return command that refreshes children bounds
     */
    private static ICommand getUpdateChildrenLocationCommand(
            GraphicalEditPart source, List<GraphicalEditPart> selectedObjects,
            GraphicalEditPart container, Dimension allowedMoveDelta,
            Dimension boundsDelta) {

        ICompositeCommand childLocationCommand = new CompositeCommand(
                UPDATE_CHILDREN_LOCATION_LABEL);
        for (Object child : source.getParent().getChildren()) {
            Rectangle childBounds = ((GraphicalEditPart) child).getFigure()
                    .getBounds().getCopy();

            if (selectedObjects.contains(child)) {
                childBounds.translate(allowedMoveDelta.width,
                        allowedMoveDelta.height);
            } else if (boundsDelta.width != 0 || boundsDelta.height != 0) {
                childBounds.translate(boundsDelta.width, boundsDelta.height);
            } else {
                continue;
            }

            ((GraphicalEditPart) child).getFigure().setBounds(childBounds);
            childLocationCommand.add(new SetBoundsCommand(container
                    .getEditingDomain(), UPDATE_CHILDREN_LOCATION_LABEL,
                    (GraphicalEditPart) child, childBounds));
        }

        return childLocationCommand;
    }
}
