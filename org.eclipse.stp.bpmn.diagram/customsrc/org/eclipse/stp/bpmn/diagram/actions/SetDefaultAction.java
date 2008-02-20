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
 * Dec 12, 2006      Antoine Toulm�   Creation
 */
package org.eclipse.stp.bpmn.diagram.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EModelElement;
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
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

/**
 * This action sets the sequence edge as default,
 * if the sequence edge is located after a XOR gateway.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class SetDefaultAction extends AbstractActionHandler {
	
	public static final String ID = "setDefaultEdgeAction"; //$NON-NLS-1$
	
	
	public SetDefaultAction(IWorkbenchPart workbenchPart) {
		super(workbenchPart);
	}
	
	public SetDefaultAction(IWorkbenchPage workbenchPage) {
		super(workbenchPage);
	}
	/**
	 * Inits the action with its id, description, tooltip and image.
	 */
	@Override
	public void init() {
		super.init();
		setId(ID);
		setDescription(BpmnDiagramMessages.SetDefaultAction_label);
		setToolTipText(getDescription());
		setImageDescriptor(BpmnDiagramEditorPlugin.
				getBundledImageDescriptor("icons/obj16/default.gif")); //$NON-NLS-1$
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
			if (object instanceof SequenceEdge) {
				part.getDiagramEditDomain().getDiagramCommandStack().
					execute(new ICommandProxy(new MyCommand(object) {

						@Override
						protected CommandResult doExecuteWithResult(
								IProgressMonitor monitor, IAdaptable info)
								throws ExecutionException {
							((SequenceEdge) object).
							setIsDefault(!((SequenceEdge) object).
										isIsDefault());
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
			if (object instanceof SequenceEdge) {
				Vertex src = ((SequenceEdge) object).getSource();
				if (src != null && (src instanceof Activity)) {
					ActivityType type = ((Activity) src).getActivityType();
					if (type.getValue() == 
						ActivityType.GATEWAY_DATA_BASED_INCLUSIVE||
						type.getValue() == 
							ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE) {
						if (((Activity) src).getIncomingEdges().size() <= 1) {
							if (((SequenceEdge) object).isIsDefault()) {
								setText(BpmnDiagramMessages.SetDefaultAction_label_non_default);
							} else {
								setText(BpmnDiagramMessages.SetDefaultAction_label);
							}
							setEnabled(true);
							return;
						}
					}
				}
			}
		}
		setText(BpmnDiagramMessages.SetDefaultAction_description);
		setEnabled(false);
	}

	/**
	 * Utility class used to simplify the use of AbstracTransactionalCommand.
	 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
	 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
	 */
	static abstract class MyCommand extends AbstractTransactionalCommand {
		public MyCommand(EObject elt) {
			super((TransactionalEditingDomain) AdapterFactoryEditingDomain.
					getEditingDomainFor(elt),
					BpmnDiagramMessages.SetDefaultAction_command,
					getWorkspaceFiles(elt));
		}
	}
}
