/******************************************************************************
* Copyright (c) 2008, Intalio Inc.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
* 
* Contributors:
*     Intalio Inc. - initial API and implementation
*******************************************************************************
 * Date         Author             Changes
 * Aug 14, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.validation.specification;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.validation.BpmnValidationMessages;

/**
 * This constraint checks that there is a default path for the OR and XOR gateways,
 * and warns the user if none is checked.
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class GatewayGatesConstraint extends AbstractModelConstraint {

    /* (non-Javadoc)
     * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
     */
    @Override
    public IStatus validate(IValidationContext ctxt) {
        if (ctxt.getTarget() instanceof Activity) {
            Activity act = (Activity) ctxt.getTarget();
            if ((act.getActivityType().getValue() == ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE) 
                    || (act.getActivityType().getValue() == ActivityType.GATEWAY_DATA_BASED_INCLUSIVE)) {
                if (act.getOutgoingEdges().size() > 1 && act.getIncomingEdges().size() <= 1) {
                    boolean isDefault = false;
                    for (SequenceEdge edge : act.getOutgoingEdges()) {
                        isDefault |= edge.isIsDefault();
                    }
                    if (!isDefault) {
                        return ctxt.createFailureStatus(new String[] {BpmnValidationMessages.GatewayGatesConstraint_defaultGateway_constraint});
                    }
                }
            }
        }
        return ctxt.createSuccessStatus();
    }

}
