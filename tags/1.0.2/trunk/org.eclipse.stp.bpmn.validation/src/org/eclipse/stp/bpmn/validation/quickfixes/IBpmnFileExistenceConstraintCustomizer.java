/******************************************************************************
* Copyright (c) 2008, Intalio Inc.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
* 
* Contributors:
*     Intalio Inc. - initial API and implementation
*******************************************************************************/
package org.eclipse.stp.bpmn.validation.quickfixes;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.stp.bpmn.validation.IConstraintStatusEx;

/**
 * Instances of this interfaces are called during the execution of the file
 * existence constraint.
 * <p>
 * This lets those extensions override the standard "missing documentation"
 * error message. It also enables extensions to plug their own custom attributes
 * for a specific quickfix and to not setup the standard missing documentation
 * quickfixes.
 * </p>
 * <p>
 * This is useful for products that extend the bpmn editor where file annotations
 * are not always used for documentation.
 * </p>
 * @author hmalphettes
 * @author Intalio Inc
 */
public interface IBpmnFileExistenceConstraintCustomizer {

    /**
     * @param bpmnElement The element validated
     * @param missingDocumentation The reference to a a file that is missing 
     * @param currentStatus The status on which the error message is set
     * as well as the attrributes for the custom marker to be able to prepare
     * data for the quickfixes.
     * @return false if no other file existence validator should be called after
     * this one.
     */
    public boolean validates(EModelElement bpmnElement,
            IResource missingDocumentation,
            IConstraintStatusEx currentStatus);
    
    /**
     * @return The priority to influence the order in which the validators are called.
     */
    public int getPriority();

    
}
