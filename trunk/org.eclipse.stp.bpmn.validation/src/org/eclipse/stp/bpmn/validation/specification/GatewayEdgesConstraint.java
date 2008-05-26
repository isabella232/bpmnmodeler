/**
 *  Copyright (C) 2007, Intalio Inc.
 *
 *  The program(s) herein may be used and/or copied only with the
 *  written permission of Intalio Inc. or in accordance with the terms
 *  and conditions stipulated in the agreement/contract under which the
 *  program(s) have been supplied.
 *
 * Date         Author             Changes
 * Jun 18, 2007      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.validation.specification;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.validation.BpmnValidationMessages;

/**
 * This validation rule states that the gateway tasks
 * should either be used as merges, in which case they 
 * whould have more than one incoming edge, 
 * or more than one outgoing edge, in which case they
 * are used as forks. 
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class GatewayEdgesConstraint extends AbstractModelConstraint {

    @Override
    public IStatus validate(IValidationContext ctx) {
        Activity gateway = (Activity) ctx.getTarget();
        if (ActivityType.VALUES_GATEWAYS.contains(gateway.getActivityType())) {
            if (gateway.getOutgoingEdges().size() < 2 && 
                    gateway.getIncomingEdges().size() < 2) {
                String named = gateway.getName() == null ? "" : //$NON-NLS-1$
                	BpmnValidationMessages.bind(BpmnValidationMessages.GatewayEdgesConstraint_named,
                        gateway.getName());
                return ctx.createFailureStatus(new String[] {named});
            }
        }
        return ctx.createSuccessStatus();
    }

}
