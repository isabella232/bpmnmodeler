/**
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 */
package org.eclipse.stp.bpmn.sample.participantView.participant.properties;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.sample.participantView.ParticipantViewPlugin;
import org.eclipse.stp.bpmn.sample.participantView.participant.ParticipantConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * This class defines a property section for the participants.
 * @author <a href="mailto:hmalphettes@intalio.com">Hugues Malphettes</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ParticipantPropertySection extends AbstractPropertySection {

	/**
	 * the text that represents the name of the participant
	 */
	private Text nameText;
	/**
	 * the text that represents the role of the participant
	 */
	private Text roleText;
	/**
	 * the activity that holds or will hold the participant, may be null.
	 */
	private Activity activity;

	/**
	 * Creates the UI of the section.
	 */
	@Override
	public void createControls(Composite parent, 
			TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);
		GridLayout layout = new GridLayout(2, false);
		parent.setLayout(layout);
		
		GridData gd = new GridData(SWT.FILL);
		gd.minimumWidth = 500;
		gd.widthHint = 500;
		getWidgetFactory().createCLabel(parent, "Name");
		nameText = getWidgetFactory().createText(parent, "");
		nameText.setLayoutData(gd);
		getWidgetFactory().createCLabel(parent, "Role");
		roleText = getWidgetFactory().createText(parent, "");
		roleText.setLayoutData(gd);
		
		nameText.addModifyListener(new ModifyParticipantInformation(
				ParticipantConstants.PARTICIPANT_NAME, nameText));
		roleText.addModifyListener(new ModifyParticipantInformation(
				ParticipantConstants.PARTICIPANT_ROLE, roleText));
	}
	
	/**
	 * Manages the input.
	 */
	@Override
	public void setInput(IWorkbenchPart part, ISelection selection) {
		
		super.setInput(part, selection);
		if(selection instanceof IStructuredSelection) {
			Object unknownInput = 
				((IStructuredSelection) selection).getFirstElement();
			if (unknownInput instanceof IGraphicalEditPart && 
				(((IGraphicalEditPart) unknownInput).
						resolveSemanticElement() != null)) {
				unknownInput = ((IGraphicalEditPart) unknownInput).
					resolveSemanticElement();
			}
			if (unknownInput
					instanceof Activity) {
				Activity elt = (Activity) unknownInput;
					EAnnotation ea = elt.getEAnnotation(ParticipantConstants.
							PARTICIPANT_ANNOTATION);
					if (ea != null) {
					nameText.setText((String) ea.getDetails().get(
							ParticipantConstants.PARTICIPANT_NAME));
					roleText.setText((String) ea.getDetails().get(
							ParticipantConstants.PARTICIPANT_ROLE));
					}
					activity = (Activity) elt;
					nameText.setEnabled(true);
					roleText.setEnabled(true);
					return;
			}
		}
		activity = null;
		nameText.setText("");
		roleText.setText("");
		nameText.setEnabled(false);
		roleText.setEnabled(false);
	}
	
	/**
	 * Tracks the change occuring on the text field.
	 * @author <a href="mailto:hmalphettes@intalio.com">Hugues Malphettes</a>
	 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
	 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
	 */
	private class ModifyParticipantInformation implements ModifyListener {
		private String key;
		private Text field;
		
		public ModifyParticipantInformation(String k, Text field) {
			key = k;
			this.field = field;
		}

		public void modifyText(ModifyEvent e) {
			if (activity == null) { // the value was just initialized
				return;
			}
			ModifyParticipantEAnnotationCommand command = 
				new ModifyParticipantEAnnotationCommand(
						activity, "Modifying participant") {

				@Override
				protected CommandResult doExecuteWithResult(
						IProgressMonitor arg0, 
						IAdaptable arg1) throws ExecutionException {
					EAnnotation annotation  = activity.getEAnnotation(
							ParticipantConstants.PARTICIPANT_ANNOTATION);
					if (annotation == null) {
						annotation = EcoreFactory.eINSTANCE.createEAnnotation();
						annotation.setSource(
									ParticipantConstants.PARTICIPANT_ANNOTATION);
						annotation.setEModelElement(activity);
						annotation.getDetails().put(
								ParticipantConstants.PARTICIPANT_NAME, "");
						annotation.getDetails().put(
								ParticipantConstants.PARTICIPANT_ROLE, "");
					}
					annotation.getDetails().put(key, field.getText());
					
					return CommandResult.newOKCommandResult();
				}};
				try {
					command.execute(new NullProgressMonitor(), null);
				} catch (ExecutionException exception) {
					ParticipantViewPlugin.
					getDefault().getLog().log(new Status(
							IStatus.ERROR,
							ParticipantViewPlugin.PLUGIN_ID,
							IStatus.ERROR,
							exception.getMessage(),
							exception));
				}
		}
		
	}
	
	/**
	 * Utility class that finds the files and the editing domain easily,
	 * Abstractas the doExecuteWithResult method needs to be implemented.
	 * @author <a href="mailto:hmalphettes@intalio.com">Hugues Malphettes</a>
	 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
	 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
	 */
	private abstract class ModifyParticipantEAnnotationCommand 
	extends AbstractTransactionalCommand {

	public ModifyParticipantEAnnotationCommand(Activity ann, String label) {
		super((TransactionalEditingDomain) AdapterFactoryEditingDomain.
				getEditingDomainFor(ann), label, getWorkspaceFiles(ann));
	}

}
}
