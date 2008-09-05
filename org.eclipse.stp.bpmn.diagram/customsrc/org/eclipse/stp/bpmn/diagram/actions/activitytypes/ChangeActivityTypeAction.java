/******************************************************************************
 * Copyright (c) 2006, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Dates       		 Author              Changes
 * Dec 18, 2006      Antoine Toulm&eacute;   Creation
 */
package org.eclipse.stp.bpmn.diagram.actions.activitytypes;

import java.net.URL;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.ui.action.AbstractActionHandler;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramCommandStack;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.BpmnFactory;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.policies.ActivityItemSemanticEditPolicy;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.provider.ActivityItemProvider;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Abstract action to change the activity type of the selected
 * object into the one specified by the action.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ChangeActivityTypeAction extends AbstractActionHandler {

	/**
	 * The abstract constant which is completed by the name of the type
	 */
	public static final String ABSTRACT_ID = "setActivityTypeTo" ; //$NON-NLS-1$
	/**
	 * the type that this action represents.
	 */
	private ActivityType _type;
	/**
	 * Default constructor
	 * @param workbenchPart
	 */
	public ChangeActivityTypeAction(IWorkbenchPart workbenchPart) {
		super(workbenchPart);
	}
	/**
	 * Default constructor
	 * @param workbenchPage
	 */
	public ChangeActivityTypeAction(IWorkbenchPage workbenchPage,String name) {
		super(workbenchPage);
		_type = ActivityType.get(name.substring(ABSTRACT_ID.length()));
	}
	
	/**
	 * Initializes the action.
	 */
	@Override
	public void init() {
		super.init();
		setId(ABSTRACT_ID + getActivityType().getLiteral());
		setText(getActivityType().getName());
		setImageDescriptor(ImageDescriptor.createFromURL(
				(URL) new ActivityItemProvider(null).
				getImage(getActivityType())));
	}
	/**
	 * Runs the action through a command.
	 */
	@Override
	protected void doRun(IProgressMonitor progressMonitor) {

	    IGraphicalEditPart part = getGraphicalEditPart();
	    Command co = getCommand();

	    if (part == null || co == null) {
	        return;
	    }
	    DiagramCommandStack stack = part.getDiagramEditDomain().getDiagramCommandStack();
	    stack.execute(co, progressMonitor);
	}

    private IGraphicalEditPart getGraphicalEditPart() {
        IStructuredSelection selection = getStructuredSelection();
        if (selection == null || (selection.isEmpty())) {
            return null;
        }
        if (selection.getFirstElement() instanceof IGraphicalEditPart) {
            IGraphicalEditPart part = (IGraphicalEditPart) selection.
                    getFirstElement();
            return part;
        }
        return null;
    }
    
	private Command getCommand() {
	    IGraphicalEditPart part = getGraphicalEditPart();
	    if (part != null) {
            Request request = new Request(ActivityItemSemanticEditPolicy.ACTIVITY_TYPE_CHANGE_REQUEST);
            request.getExtendedData().put(ActivityItemSemanticEditPolicy.ACTIVITY_TYPE_EXTENDED_DATA, _type);
            Command co = part.getCommand(request);
            return co;
        }
        return null;
	}
	/**
	 * Refreshing the action, setting it enabled if the selection is an Activity
	 * and the activity type is different of its activity type.
	 */
	public void refresh() {
		boolean forbidEnablement = false;
		
		Object selected = getGraphicalEditPart();
		if (selected instanceof IGraphicalEditPart) {
			selected = ((IGraphicalEditPart) selected).
				resolveSemanticElement();
		}
		if (!(selected instanceof Activity)) {
			forbidEnablement = true;
		}
		
		if (forbidEnablement) {
            setEnabled(false);
            return;
        }
 		
		Command co = getCommand();
		setEnabled(co != null && co.canExecute());
	} 

	public ActivityType getActivityType() {
		return _type;
	}
}
