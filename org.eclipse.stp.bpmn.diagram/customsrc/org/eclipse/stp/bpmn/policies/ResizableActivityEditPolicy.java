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

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.handles.AbstractHandle;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.commands.ElementTypeEx;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityName2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityNameEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.figures.BpmnShapesDefaultSizes;
import org.eclipse.stp.bpmn.tools.ActivityResizeTracker;
import org.eclipse.stp.bpmn.tools.TaskDragEditPartsTrackerEx;

/**
 * Edit policy for activities and other shapes.
 * 
 * @author MPeleshchyshyn
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ResizableActivityEditPolicy extends ResizableShapeEditPolicyEx {
    
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.stp.bpmn.policies.ResizableShapeEditPolicyEx#createHandle(org.eclipse.gef.GraphicalEditPart,
     *      int)
     */
    protected Handle createHandle(GraphicalEditPart owner, int direction) {
        return new ActivityResizeHandle(owner, direction);
    }

    /**
     * Creates new handle for activity.
     */
    protected static class ActivityResizeHandle extends ResizeHandleEx {
        public ActivityResizeHandle(GraphicalEditPart owner, int direction) {
            super(owner, direction);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.stp.bpmn.policies.ResizableShapeEditPolicyEx.ResizeHandleEx#createDragTracker()
         */
        @Override
        protected DragTracker createDragTracker() {
            return new ActivityResizeTracker(getOwner(), cursorDirection);
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
     * Overidden the default behaviour to have the name edit part
     * being excluded from the computation of the size, when 
     * the activity is a gateway or an event.
     * 
     */
    @Override
    protected Command getAutoSizeCommand(Request request) {
    	EObject semantic = ((IGraphicalEditPart) getHost()).resolveSemanticElement();
        IElementType type = ElementTypeRegistry.getInstance().
            getElementType(semantic);
        if (semantic instanceof Activity) {
        	type = ElementTypeEx.wrap(type, 
        			((Activity) semantic).getActivityType().getName());
        }
        Dimension size = BpmnShapesDefaultSizes.getDefaultSize(type).getCopy();
        TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
        Rectangle rect = new Rectangle(getHostFigure().getBounds().getLocation().getCopy(), size);
        if (ActivityType.VALUES_EVENTS.contains(((Activity) semantic).getActivityType()) ||
        		ActivityType.VALUES_GATEWAYS.contains(((Activity) semantic).getActivityType())) {
        	IGraphicalEditPart nameEp = ((IGraphicalEditPart) getHost()).
        		getChildBySemanticHint(BpmnVisualIDRegistry.getType(
        				ActivityNameEditPart.VISUAL_ID));
        	if (nameEp == null) {
        		nameEp = ((IGraphicalEditPart) getHost()).
        		getChildBySemanticHint(BpmnVisualIDRegistry.getType(
        				ActivityName2EditPart.VISUAL_ID));
        	}
//        	if (nameEp != null && nameEp.getFigure() != null) {
//        		Rectangle textBounds = nameEp.getFigure().getBounds().getCopy();
//        		if (nameEp.getFigure() instanceof WrapLabel) {
//        			textBounds = ((WrapLabel) nameEp.getFigure()).getTextBounds();
//        		}
//        		rect.width = Math.max(rect.width, textBounds.width);
//        		// even if there is nothing in the label
//        		// the height is not null, which causes problems
//        		if (((Activity) semantic).getName() != null && 
//        				((Activity) semantic).getName().length() != 0) {
//        			rect.height = rect.height + textBounds.height;
//        		}
//        	}
        }
        ICommand resizeCommand = new SetBoundsCommand(editingDomain, 
            "", //$NON-NLS-1$
            (org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart) getHost(), rect);
        return new ICommandProxy(resizeCommand);
    }
    
    /**
     * Try handling the resize of the label.
     */
    @Override
    public Command getCommand(Request request) {
        if (RequestConstants.REQ_RESIZE_CHILDREN.equals(request.getType())) {
            TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) 
                    getHost()).getEditingDomain();
            final ChangeBoundsRequest change = (ChangeBoundsRequest) request;
            
            final IGraphicalEditPart nameEp = (IGraphicalEditPart) change.getEditParts().iterator().next();
            SetBoundsCommand resize  = new SetBoundsCommand(editingDomain,
                    BpmnDiagramMessages.ResizableActivityEditPolicy_command_name, nameEp, 
                    change.getSizeDelta()) {
                @Override
                protected CommandResult doExecuteWithResult(
                        IProgressMonitor monitor, IAdaptable info)
                        throws ExecutionException {
                    
                    // doing this explicitly to circumvene the problem
                    // of accessing the view in the layout
                    // could also be done from the notify in the NameEditPart
                    nameEp.getFigure().setSize(nameEp.getFigure().getSize().
                            getCopy().expand(change.getSizeDelta()));
                    return super.doExecuteWithResult(monitor, info);
                }
            };
            
            ChangeBoundsRequest selfResize = 
                new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
            selfResize.setEditParts(getHost());
            selfResize.setSizeDelta(change.getSizeDelta().getCopy());
            CompoundCommand  compound = new CompoundCommand();
            compound.add(new ICommandProxy(resize));
            compound.add(super.getCommand(selfResize));
            return compound;
        }
        return super.getCommand(request);
    }
}
