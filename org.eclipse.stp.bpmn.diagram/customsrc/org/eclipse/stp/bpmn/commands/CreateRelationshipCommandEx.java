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

/**
 * Date             Author              Changes
 * Jul 17, 2006     hmalphettes         Created
 **/

package org.eclipse.stp.bpmn.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateRelationshipCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;

/**
 * Support for the {@link IElementTypeEx} to call back ISecondarySemanticHintProcessors.
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class CreateRelationshipCommandEx extends CreateRelationshipCommand {

    /**
     * @param request
     */
    public CreateRelationshipCommandEx(CreateRelationshipRequest request) {
        super(request);
    }

    /**
     * @return the new model element that has been created
     */
    protected EObject doDefaultElementCreation() {
        EObject res = super.doDefaultElementCreation();
        CreateElementCommandEx.processSecondarySemanticHint(res, getElementType());
        return res;
    }
    
}
