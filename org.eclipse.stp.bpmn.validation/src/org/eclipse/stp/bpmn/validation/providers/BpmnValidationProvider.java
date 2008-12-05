/*
 *******************************************************************************
 ** Copyright (c) 2006, Intalio Inc.
 ** All rights reserved. This program and the accompanying materials
 ** are made available under the terms of the Eclipse Public License v1.0
 ** which accompanies this distribution, and is available at
 ** http://www.eclipse.org/legal/epl-v10.html
 ** 
 ** Contributors:
 **     Intalio Inc. - initial API and implementation
 ********************************************************************************
 */
package org.eclipse.stp.bpmn.validation.providers;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.AbstractContributionItemProvider;
import org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.stp.bpmn.validation.BpmnValidationMessages;
import org.eclipse.stp.bpmn.validation.BpmnValidationPlugin;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation;

/**
 * Most of the code generated here was moved to {@link HeadlessBpmnValidationProvider}
 * Which is independent from gmf.common.ui to have better chances to run the validation
 * in a headless environment.
 * @generated
 */
public class BpmnValidationProvider extends AbstractContributionItemProvider {

	/**
	 * @generated
	 */
	protected IAction createAction(String actionId,
			IWorkbenchPartDescriptor partDescriptor) {
		if (ValidateAction.VALIDATE_ACTION_KEY.equals(actionId)) {
			return new ValidateAction(partDescriptor);
		}
		return super.createAction(actionId, partDescriptor);
	}

	/**
	 * @generated
	 */
	public static class ValidateAction extends Action {

		/**
		 * @generated
		 */
		public static final String VALIDATE_ACTION_KEY = "validateAction"; //$NON-NLS-1$

		/**
		 * @generated
		 */
		private IWorkbenchPartDescriptor workbenchPartDescriptor;

		/**
		 * @generated
		 */
		public ValidateAction(IWorkbenchPartDescriptor workbenchPartDescriptor) {
			setId(VALIDATE_ACTION_KEY);
			setText(BpmnValidationMessages.BpmnValidationProvider_validate);
			this.workbenchPartDescriptor = workbenchPartDescriptor;
		}

		/**
		 * @generated
		 */
		public void run() {
			IWorkbenchPart workbenchPart = workbenchPartDescriptor
					.getPartPage().getActivePart();
			if (workbenchPart instanceof IDiagramWorkbenchPart) {
				final IDiagramWorkbenchPart part = (IDiagramWorkbenchPart) workbenchPart;
				try {
					new WorkspaceModifyDelegatingOperation(
							new IRunnableWithProgress() {
								public void run(IProgressMonitor monitor)
										throws InterruptedException,
										InvocationTargetException {
									HeadlessBpmnValidationProvider.runValidation(part.getDiagram());
								}
							}).run(new NullProgressMonitor());
				} catch (Exception e) {
                    BpmnValidationPlugin.getDefault().getLog().log(
                            new Status(IStatus.ERROR,
                                    BpmnValidationPlugin.PLUGIN_ID,
                                    IStatus.ERROR,
                                    BpmnValidationMessages.BpmnValidationProvider_validateActionFailed, e));
				}
			}
		}

	}

} //BpmnValidationProvider
