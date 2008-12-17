/********************************************************************************
 ** Copyright (c) 2008, Intalio Inc.
 ** All rights reserved. This program and the accompanying materials
 ** are made available under the terms of the Eclipse Public License v1.0
 ** which accompanies this distribution, and is available at
 ** http://www.eclipse.org/legal/epl-v10.html
 ** 
 ** Contributors:
 **     Intalio Inc. - initial API and implementation
 ********************************************************************************
 * Date         Author             Changes
 * Nov 28, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.validation.specification;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SequenceFlowConditionType;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.validation.BpmnValidationMessages;

/**
 * This constraint applies to default and conditional flows. 
 * Default flows only may be used when conditional flows are present, 
 * and conditional flows may only be shown when their source isn't a 
 * gateway and that there is more than one flow going out of their source.
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class ControlledFlowsConstraint extends AbstractModelConstraint {

    @Override
    public IStatus validate(IValidationContext ctx) {
        SequenceEdge edge = (SequenceEdge) ctx.getTarget();
        if (edge.getSource() == null) {
            return ctx.createSuccessStatus();
        }
        
        boolean moreThanOneEdgeOut = edge.getSource().getOutgoingEdges().size() > 1;
        boolean atLeastOneConditional = false;
        boolean atLeastOneParallel = false;
        for (SequenceEdge e : edge.getSource().getOutgoingEdges()) {
            atLeastOneConditional |= e.getConditionType() 
                == SequenceFlowConditionType.EXPRESSION_LITERAL;
            atLeastOneParallel |= e.getConditionType() 
                == SequenceFlowConditionType.NONE_LITERAL;
        }
        int actype = ((Activity) edge.getSource()).getActivityType().getValue();
        if (ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE == actype 
                || ActivityType.GATEWAY_DATA_BASED_INCLUSIVE == actype
                || ActivityType.GATEWAY_COMPLEX == actype) {
            atLeastOneConditional = moreThanOneEdgeOut;
            atLeastOneParallel = false;
        }
        
        switch (edge.getConditionType().getValue()) {
        case SequenceFlowConditionType.DEFAULT:
            if (!moreThanOneEdgeOut) {
                return ctx.createFailureStatus(BpmnValidationMessages.ControlledFlowsConstraint_default_and_no_other_edges);
            }
            if (!atLeastOneConditional) {
                return ctx.createFailureStatus(BpmnValidationMessages.ControlledFlowsConstraint_default_and_no_controlled_flows);
            }
            if (atLeastOneParallel) {
                return ctx.createFailureStatus(BpmnValidationMessages.ControlledFlowsConstraint_default_never_executed);
            }
            break;
        case SequenceFlowConditionType.EXPRESSION:
            if (!moreThanOneEdgeOut) {
                return ctx.createFailureStatus(BpmnValidationMessages.ControlledFlowsConstraint_unique_conditional_edge);
            }
            Vertex src = edge.getSource();
            if (src != null && (src instanceof Activity)) {
                ActivityType type = ((Activity) src).getActivityType();
                if (ActivityType.VALUES_GATEWAYS.contains(type)) {
                    return ctx.createFailureStatus(BpmnValidationMessages.ControlledFlowsConstraint_unique_conditional_flow);
                }
            }
        }
        return ctx.createSuccessStatus();
    }

}
