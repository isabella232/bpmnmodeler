/******************************************************************************
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.stp.bpmn.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * Creates a blank task and add it to the creating subprocess
 * 
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 * @author vlevytskyy
 * @author Antoine Toulme
 */
public class CreateSubProcessCommand extends CreateElementCommandEx {

	/**
	 * Special parameter that can be set on the request to avoid 
	 * creating the child.
	 */
	public static final String CREATE_CHILD_PARAMETER = "createChild"; //$NON-NLS-1$
    public CreateSubProcessCommand(CreateElementRequest req) {
        super(req);
    }

    @Override
    /**
     * Create and add a task to the subprocess
     */
    protected EObject doDefaultElementCreation() {
        EObject object = super.doDefaultElementCreation();
        if (getRequest().getParameter(CREATE_CHILD_PARAMETER) != null && 
        		getRequest().getParameter(CREATE_CHILD_PARAMETER).
        		equals(Boolean.FALSE)) {
        	return object;
        }
        EReference containment = getContainmentFeature();
        IElementType type = BpmnElementTypes.Activity_2001;
        EClass eClass = type.getEClass();

        if (containment != null) {
            EMFCoreUtil.create(object, containment, eClass);
        }

        return object;
    }
}
