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
 * Dec 18, 2006      Antoine Toulm�   Creation
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
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.provider.ActivityItemProvider;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Abstract action to change the activity type of the selected
 * object into the one specified by the action.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
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
		IStructuredSelection selection = getStructuredSelection();
		if (selection == null || (selection.isEmpty())) {
			return;
		}
		if (selection.getFirstElement() instanceof IGraphicalEditPart) {
			final IGraphicalEditPart part = (IGraphicalEditPart) selection.
				getFirstElement();
			final EObject object = ((IGraphicalEditPart) selection.
					getFirstElement()).resolveSemanticElement();
			if (object instanceof Activity) {
//				testFigure(part);
                CompoundCommand compound = new CompoundCommand();
				if (ActivityType.SUB_PROCESS_LITERAL.equals(_type)) {
					compound.add(new ICommandProxy(new MyCommand(object) {

						@Override
						protected CommandResult doExecuteWithResult(
								IProgressMonitor monitor, IAdaptable info)
								throws ExecutionException {
							Activity act = (Activity) object;
							Node actnode = (Node) part.getNotationView();
							SubProcess sp = BpmnFactory.eINSTANCE.createSubProcess();
							sp.setActivityType(ActivityType.SUB_PROCESS_LITERAL);
							sp.setName(act.getName());
							sp.setNcname(act.getNcname());
							sp.setDocumentation(act.getDocumentation());
							sp.setGraph(act.getGraph());
							sp.setID(act.getID());
							sp.setLane(act.getLane());
							sp.setLooping(act.isLooping());
							sp.getIncomingEdges().addAll(act.getIncomingEdges());
							sp.getOutgoingEdges().addAll(act.getOutgoingEdges());
							Node node = ViewService.createNode((View) part.getNotationView().eContainer(), 
									sp,BpmnVisualIDRegistry.getType(SubProcessEditPart.VISUAL_ID) , 
									BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
							node.setLayoutConstraint(actnode.getLayoutConstraint());
							node.getTargetEdges().addAll(actnode.getTargetEdges());
							node.getSourceEdges().addAll(actnode.getSourceEdges());
							Command co = part.getCommand(
									new EditCommandRequestWrapper(
											new DestroyElementRequest(act, false)));
							
							co.execute();
							Command autosize = 
			                	part.getCommand(new Request(RequestConstants.REQ_AUTOSIZE));
							autosize.execute();
							return CommandResult.newOKCommandResult();
						}
						
					}));
				} else {
				compound.add(new ICommandProxy(new MyCommand(object) {

						@Override
						protected CommandResult doExecuteWithResult(
								IProgressMonitor monitor, IAdaptable info)
								throws ExecutionException {
							Activity act = (Activity) object;
							if (getActivityType().getValue() != 
								ActivityType.TASK && 
								(getActivityType().getValue() != 
									ActivityType.SUB_PROCESS)) {
								act.setLooping(false);
							}
							act.setActivityType(getActivityType());
                            part.deactivate();
                            part.activate();
							Command autosize = 
			                	part.getCommand(new Request(RequestConstants.REQ_AUTOSIZE));
							autosize.execute();
							return CommandResult.newOKCommandResult();
						}}));
				}
				DiagramCommandStack stack = part.getDiagramEditDomain().getDiagramCommandStack();
//				stack.execute(compound, progressMonitor);
                // finally try autosizing the activity to the right size
                stack.execute(compound, progressMonitor);
			}
		}
	}

	/**
	 * Refreshing the action, setting it enabled if the selection is an Activity
	 * and the activity type is different of its activity type.
	 */
	public void refresh() {
		boolean forbidEnablement = false;
		if (getSelection() == null|| getSelection().isEmpty()) {
			forbidEnablement = true;
		}
		if (!(getSelection() instanceof IStructuredSelection)) {
			forbidEnablement = true;
		}
		if (forbidEnablement) {
			setEnabled(false);
			return;
		}
		Object selected = ((IStructuredSelection) getSelection()).
			getFirstElement();
		if (selected instanceof IGraphicalEditPart) {
			selected = ((IGraphicalEditPart) selected).
				resolveSemanticElement();
		}
		if (!(selected instanceof Activity)) {
			forbidEnablement = true;
		}
		if (selected instanceof SubProcess) {// changing a subprocess into an 
			// activity is too complicated for now.
			forbidEnablement = true;
		}
		// if the activity has messages, then don't allow it
		// to be transformed into a gateway
		// to avoid having something illegal here.
		if (!((Activity) selected).getOrderedMessages().isEmpty() &&
		        ActivityType.VALUES_GATEWAYS.contains(_type)) {
		    forbidEnablement = true;
		}
		// empty events are not authorized to be associated
		// with messages
		if (!((Activity) selected).getOrderedMessages().isEmpty() &&
                ActivityType.EVENT_END_EMPTY_LITERAL.equals(_type)) {
            forbidEnablement = true;
        }
		
		if (!((Activity) selected).getOrderedMessages().isEmpty() &&
                ActivityType.EVENT_INTERMEDIATE_EMPTY_LITERAL.equals(_type)) {
            forbidEnablement = true;
        }
		
		if (!((Activity) selected).getOrderedMessages().isEmpty() &&
                ActivityType.EVENT_START_EMPTY_LITERAL.equals(_type)) {
            forbidEnablement = true;
        }
		
		if (forbidEnablement) {
			setEnabled(false);
			return;
		}
		if (((Activity) selected).getActivityType().getValue() 
				== getActivityType().getValue()) {
			forbidEnablement = true;
		}
		
		// only accepting to change an activity into a subprocess if
		// the activity doesn't have messages and is of type TASK.
		if (_type.getValue() == ActivityType.SUB_PROCESS && 
				(((Activity) selected).getActivityType().getValue() != ActivityType.TASK || 
						(!((Activity) selected).getOrderedMessages().isEmpty()))) {
			forbidEnablement = true;
		}
 		
		setEnabled(!forbidEnablement);
	} 

	public ActivityType getActivityType() {
		return _type;
	}
	
	
	/**
	 * Utility class used to simplify the use of AbstracTransactionalCommand.
	 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
	 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
	 */
	private abstract class MyCommand extends AbstractTransactionalCommand {
		public MyCommand(EObject elt) {
			super((TransactionalEditingDomain) AdapterFactoryEditingDomain.
					getEditingDomainFor(elt),
					BpmnDiagramMessages.ChangeActivityTypeAction_settingActivityTypeCommand,
					getWorkspaceFiles(elt));
		}
	}
}
