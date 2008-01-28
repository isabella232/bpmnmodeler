/**
 * Copyright (C) 2000-2007, Intalio Inc.
 *
 * The program(s) herein may be used and/or copied only with the
 * written permission of Intalio Inc. or in accordance with the terms
 * and conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *
 * Dates       		 Author              Changes
 * Mar 20, 2007      Antoine Toulmé   Creation
 */
package org.eclipse.stp.bpmn.validation.specification;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;

/**
 * This constraint is enforcing the rule of forbidding the messaging edges
 * on gateways.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class MessagesOnGatewaysConstraint extends AbstractModelConstraint {

	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
	 */
	@Override
	public IStatus validate(IValidationContext ctx) {
		if (ctx.getTarget() instanceof Activity) {
			Activity act = (Activity) ctx.getTarget();
			if (ActivityType.VALUES_GATEWAYS.contains(act.getActivityType())) {
				if (!act.getOrderedMessages().isEmpty()) {
					return ctx.createFailureStatus(new String[] {});
				}
			}
		}
			
		return ctx.createSuccessStatus();
	}

}
