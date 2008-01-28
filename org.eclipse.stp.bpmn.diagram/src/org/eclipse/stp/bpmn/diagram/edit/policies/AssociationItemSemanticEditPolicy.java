/*
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 */
package org.eclipse.stp.bpmn.diagram.edit.policies;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.IdentifiableNode;

/**
 * @generated
 */
public class AssociationItemSemanticEditPolicy extends
        BpmnBaseItemSemanticEditPolicy {

    /**
     * @generated NOT we needed to fix issues with the target of the association
     * that would still reference the removed association after it has been destroyed.
     */
    protected Command getDestroyElementCommand(DestroyElementRequest req) {
        return getMSLWrapper(new DestroyElementCommand(req) {
            /**
             * @generated NOT: fix issues with association references not correctly
             * remmoved from the target.
             */
            @Override
            protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
                Association el = (Association) super.getElementToDestroy();
                IdentifiableNode a = null;
                if (el.getTarget() != null) {
                    a = el.getTarget();
                }
                CommandResult r = super.doExecuteWithResult(monitor, info);
                if (a != null && a.getAssociations().contains(el)) {
                    a.getAssociations().remove(el);
                }
                return r;
            }
            
        });
    }
}
