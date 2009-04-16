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
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.validation.BpmnValidationMessages;

/**
 * Sequence edges constraints computed from the BPMN specification.
 * See BPMN 1.0 paragraph 8.4.1
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class SequenceEdgeConstraint extends AbstractModelConstraint {

    
	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
	 */
	@Override
	public IStatus validate(IValidationContext ctx) {
		if (ctx.getTarget() instanceof SequenceEdge) {
			if (ctx.getFeature() == null || // batch mode
					ctx.getFeature(). // live mode
					getFeatureID() == BpmnPackage.SEQUENCE_EDGE) {
				SequenceEdge edge = (SequenceEdge) ctx.getTarget();
				Vertex source = edge.getSource();
				Vertex target = edge.getTarget();
				if (source == null || target == null) { // invalid edge.
					return ctx.createSuccessStatus();
				}
				if (source instanceof Activity) {
					switch (((Activity) source).getActivityType().getValue()) {
					case ActivityType.EVENT_END_COMPENSATION:
					case ActivityType.EVENT_END_EMPTY:
					case ActivityType.EVENT_END_ERROR:
					case ActivityType.EVENT_END_MESSAGE:
                    case ActivityType.EVENT_END_TERMINATE:
                    case ActivityType.EVENT_END_CANCEL:
                    case ActivityType.EVENT_END_LINK:
                    case ActivityType.EVENT_END_MULTIPLE:
						return ctx.createFailureStatus(new Object[]{
						        BpmnValidationMessages.SequenceEdgeConstraint_endEventStartOfEdge,
						});
					case ActivityType.EVENT_START_EMPTY:
					case ActivityType.EVENT_START_MESSAGE:
					case ActivityType.EVENT_START_RULE:
					}
				}
				if (target instanceof Activity){
					switch (((Activity) target).getActivityType().getValue()) {	
					case ActivityType.EVENT_START_EMPTY:
					case ActivityType.EVENT_START_MESSAGE:
                    case ActivityType.EVENT_START_RULE:
                    case ActivityType.EVENT_START_LINK:
                    case ActivityType.EVENT_START_MULTIPLE:
                    case ActivityType.EVENT_START_TIMER:
						return ctx.createFailureStatus(new Object[]{BpmnValidationMessages.SequenceEdgeConstraint_StartEventEndOfEdge});
					}
				}
			}

		}
		return ctx.createSuccessStatus();
	}

}
