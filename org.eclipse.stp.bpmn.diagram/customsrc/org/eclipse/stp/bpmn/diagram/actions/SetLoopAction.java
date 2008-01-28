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
 * Dec 13, 2006      Antoine Toulm�   Creation
 */
package org.eclipse.stp.bpmn.diagram.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.ui.action.AbstractActionHandler;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Sets the selected task or sub process as a looping task
 * or subprocess.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class SetLoopAction extends AbstractActionHandler {
	
	public static final String ID = "setLoopAction"; //$NON-NLS-1$
	
	public SetLoopAction(IWorkbenchPart workbenchPart) {
		super(workbenchPart);
	}
	
	public SetLoopAction(IWorkbenchPage workbenchPage) {
		super(workbenchPage);
	}
	/**
	 * Inits the action with its id, description, tooltip and image.
	 */
	@Override
	public void init() {
		super.init();
		setId(ID);
		setDescription(BpmnDiagramMessages.SetLoopAction_description);
		setToolTipText(getDescription());
		setImageDescriptor(BpmnDiagramEditorPlugin.
				getBundledImageDescriptor("icons/obj16/loop.gif")); //$NON-NLS-1$
		// explicitly calling refresh to have a text for the action.
		refresh();
	}
	/**
	 * Runs the operation, basically invert the value of isDefault 
	 * property on the sequence edge.
	 */
	@Override
	protected void doRun(IProgressMonitor progressMonitor) {
		IStructuredSelection selection = getStructuredSelection();
		if (selection == null || (selection.isEmpty())) {
			return;
		}
		if (selection.getFirstElement() instanceof IGraphicalEditPart) {
			IGraphicalEditPart part = (IGraphicalEditPart) selection.
				getFirstElement();
			final EObject object = ((IGraphicalEditPart) selection.
					getFirstElement()).getNotationView().getElement();
			if (object instanceof Activity) {
				part.getDiagramEditDomain().getDiagramCommandStack().
					execute(new ICommandProxy(new MyCommand(object) {

						@Override
						protected CommandResult doExecuteWithResult(
								IProgressMonitor monitor, IAdaptable info)
								throws ExecutionException {
							if (object instanceof Activity) {
								((Activity) object).setLooping(!
										(((Activity) object).isLooping()));
							}
							return CommandResult.newOKCommandResult();
						}}), progressMonitor);
				
			}
		}
	}

	/**
	 * Refreshes the action when the selection changes
	 * Only sets the action enabled if
	 * find the first element to be a sequence edge placed after an inclusive
	 * gateway.
	 */
	public void refresh() {
		IStructuredSelection selection = getStructuredSelection();
		if (selection == null || (selection.isEmpty())) {
			setEnabled(false);
			return;
		}
		if (selection.getFirstElement() instanceof IGraphicalEditPart) {
			EObject object = ((IGraphicalEditPart) selection.getFirstElement()).
			getNotationView().getElement();
			if (object instanceof Activity) {
				if (((Activity) object).getActivityType().getValue() == 
						ActivityType.TASK) {
					String name = ((Activity) object).getName() == null ? BpmnDiagramMessages.SetLoopAction_task_default
							: ((Activity) object).getName();
					if (((Activity) object).isLooping()) {
						setText(BpmnDiagramMessages.bind(BpmnDiagramMessages.SetLoopAction_non_looping, name));
					} else {
						setText(BpmnDiagramMessages.bind(BpmnDiagramMessages.SetLoopAction_looping, name));
					}
					setEnabled(true);
					return;
				}
			}
			if (object instanceof SubProcess) {
				String name = ((SubProcess) object).getName() == null ? 
						BpmnDiagramMessages.SetLoopAction_subprocess_default
						: ((SubProcess) object).getName();
				if (((SubProcess) object).isLooping()) {
					setText(BpmnDiagramMessages.bind(BpmnDiagramMessages.SetLoopAction_non_looping_sp, name)); 
				} else {
					setText(BpmnDiagramMessages.bind(BpmnDiagramMessages.SetLoopAction_looping_sp, name)); 
				}
				setEnabled(true);
				return;
			}
		}
		setText(BpmnDiagramMessages.SetLoopAction_disabled);
		setEnabled(false);
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
					BpmnDiagramMessages.SetLoopAction_command_name,
					getWorkspaceFiles(elt));
		}
	}
}
