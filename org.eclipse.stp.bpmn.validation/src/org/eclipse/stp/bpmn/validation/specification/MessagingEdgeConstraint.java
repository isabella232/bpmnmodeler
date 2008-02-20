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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.MessageVertex;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.validation.BpmnValidationMessages;

/**
 * Messaging edges constraints computed from the BPMN specification.
 * See BPMN 1.0 paragraph 8.4.2
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class MessagingEdgeConstraint extends AbstractModelConstraint {

    @Override
    public IStatus validate(IValidationContext ctx) {
        if (ctx.getTarget() instanceof MessagingEdge) {
            if (ctx.getFeature() == null || // batch mode
                    ctx.getFeature(). // live mode
                    getFeatureID() == BpmnPackage.MESSAGING_EDGE) {
                MessagingEdge edge = (MessagingEdge) ctx.getTarget();
                MessageVertex source = edge.getSource();
                MessageVertex target = edge.getTarget();
                if (source == null || target == null) { // invalid message.
                    return ctx.createSuccessStatus();
                }
                if (source instanceof Activity) {
                    switch (((Activity) source).getActivityType().getValue()) {
                    case ActivityType.EVENT_START_MESSAGE:
                    case ActivityType.EVENT_INTERMEDIATE_MESSAGE:
                    case ActivityType.EVENT_START_MULTIPLE:
                    case ActivityType.EVENT_INTERMEDIATE_MULTIPLE:
                        FeatureMap.Entry fentry = (FeatureMap.Entry) source.
                        getOrderedMessages().get(0);
                        MessagingEdge firstMsgOfSource =
                            (MessagingEdge) fentry.getValue();
                        if (fentry.getEStructuralFeature().getFeatureID() !=
                            BpmnPackage.MESSAGE_VERTEX__INCOMING_MESSAGES ||
                            firstMsgOfSource.getSource() !=
                                edge.getTarget()) {
                            // a little bent to the spec
                            // let's let the events be able to reply
                            return ctx.createFailureStatus(new Object[]{
                                    BpmnValidationMessages.MessagingEdgeConstraint_intermediateEventShouldNotHaveIncomingMessages});
                        }
                        break;
                    case ActivityType.EVENT_INTERMEDIATE_COMPENSATION:
                    case ActivityType.EVENT_INTERMEDIATE_EMPTY:
                    case ActivityType.EVENT_INTERMEDIATE_CANCEL:
                    case ActivityType.EVENT_INTERMEDIATE_LINK:
                    case ActivityType.EVENT_INTERMEDIATE_SIGNAL:
                    case ActivityType.EVENT_INTERMEDIATE_ERROR:
                    case ActivityType.EVENT_INTERMEDIATE_RULE:
                    case ActivityType.EVENT_INTERMEDIATE_TIMER:
                        return ctx.createFailureStatus(new Object[]{
                                BpmnValidationMessages.MessagingEdgeConstraint_IntermediateEventMustNotSendMessages});

                    case ActivityType.EVENT_START_EMPTY:
                    case ActivityType.EVENT_START_LINK:
                    case ActivityType.EVENT_START_RULE:
                    case ActivityType.EVENT_START_TIMER:
                    case ActivityType.EVENT_START_SIGNAL:
                        return ctx.createFailureStatus(new Object[]{
                                BpmnValidationMessages.MessagingEdgeConstraint_StartEventMustNotSendMessage
                        });
                    }
                }
                if (target instanceof Activity) {
                    switch (((Activity) target).getActivityType().getValue()) {	
                    case ActivityType.EVENT_END_MESSAGE:
                    case ActivityType.EVENT_END_MULTIPLE:
                        if (source.getOutgoingMessages().contains(
                                source.getOrderedMessages().get(0).getValue())) {
                            return ctx.createFailureStatus(new Object[]{
                                    BpmnValidationMessages.MessagingEdgeConstraint_EndEventMustNotReceiveAMessage
                            });
                        }
                        break;
                    case ActivityType.EVENT_END_COMPENSATION:
                    case ActivityType.EVENT_END_EMPTY:
                    case ActivityType.EVENT_END_ERROR:
                    case ActivityType.EVENT_END_TERMINATE:
                    case ActivityType.EVENT_END_CANCEL:
                    case ActivityType.EVENT_END_SIGNAL:
                    case ActivityType.EVENT_END_LINK:
                        return ctx.createFailureStatus(new Object[]{
                                BpmnValidationMessages.MessagingEdgeConstraint_EndEventMustNotReceiveMessage
                        });
                    }
                }
                //check that the source and the target do not have a container in common
                Pool src = getPool(source);
                Pool tgt = getPool(target);
                if (src != null && src.equals(tgt)) {
                    return ctx.createFailureStatus(new Object[]{
                            BpmnValidationMessages.MessagingEdgeConstraint_MessageBetweenSamePoolElements
                    });
                }
            }
        }

        return ctx.createSuccessStatus();
    }

    /**
     * Recursively looks for the pool of the element
     * passed as an argument
     * @param elt the element for which we want a pool
     * @return the pool of the element
     */
    private Pool getPool(EObject elt) {
        if (elt instanceof BpmnDiagram || elt.eResource() == null) {
            return null;
        } else {
            if (elt instanceof Pool) {
                return (Pool) elt;
            }
        }
        return getPool(elt.eContainer());
    }
}
