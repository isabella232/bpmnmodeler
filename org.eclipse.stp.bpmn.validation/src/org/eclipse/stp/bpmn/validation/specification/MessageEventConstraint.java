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
package org.eclipse.stp.bpmn.validation.specification;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.stp.bpmn.validation.BpmnValidationMessages;

/**
 * Constraint on message events, stating that they should be the target
 * of a messaging edge for the start and intermediate messaging event
 * and that an end message event should be sending a message.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class MessageEventConstraint extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
	    boolean isBpmn11On = BpmnDiagramEditorPlugin.getInstance().
	        getPreferenceStore().getBoolean(
	                BpmnDiagramPreferenceInitializer.PREF_BPMN1_1_STYLE);
		if (ctx.getTarget() instanceof Activity && !isBpmn11On) {
			Activity act = (Activity) ctx.getTarget();
			switch (act.getActivityType().getValue()) {
			case ActivityType.EVENT_INTERMEDIATE_MESSAGE:
			case ActivityType.EVENT_START_MESSAGE:
				if (act.getIncomingMessages().isEmpty()) {
					return ctx.createFailureStatus(new Object[] {
							act.getName() == null ? "" :  //$NON-NLS-1$
							    BpmnValidationMessages.bind(BpmnValidationMessages.MessageEventConstraint_named, act.getName()), BpmnValidationMessages.MessageEventConstraint_target});
				}
				break;
			case ActivityType.EVENT_END_MESSAGE:
				if (act.getOutgoingMessages().isEmpty()) {
					return ctx.createFailureStatus(new Object[] {
							act.getName() == null ? "" :  //$NON-NLS-1$
							    BpmnValidationMessages.bind(BpmnValidationMessages.MessageEventConstraint_named, act.getName()), BpmnValidationMessages.MessageEventConstraint_source});
				}
			}
		}
		return ctx.createSuccessStatus();
	}

}
