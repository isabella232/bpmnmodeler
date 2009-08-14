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
 * Aug 14, 2009      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.validation.specification;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.stp.bpmn.SubProcess;

/**
 * A constraint to make sure adhoc subprocesses don't contain
 * sequence flows since tasks in a adhoc subprocess are supposed to
 * execute in no particular order.
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class AdhocSubprocessConstraint extends AbstractModelConstraint {

    @Override
    public IStatus validate(IValidationContext ctx) {
        if (ctx.getTarget() instanceof SubProcess && ((SubProcess) ctx.getTarget()).isAdhoc()) {
            SubProcess sp = (SubProcess) ctx.getTarget();
            if (!sp.getSequenceEdges().isEmpty()) {
                return ctx.createFailureStatus(new Object[] {sp.getName() == null ? "" : sp.getName()});
            }
        }
        return ctx.createSuccessStatus();
    }

}
