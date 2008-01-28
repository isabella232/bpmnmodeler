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
package org.eclipse.stp.bpmn.validation;

import org.eclipse.emf.validation.model.IClientSelector;


/**
 * This class is used to filter objects that should trigger a constraint.
 * For now the class returns true for any object.
 * 
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ValidationDelegateClientSelector implements IClientSelector {

    /* (non-Javadoc)
     * @see org.eclipse.emf.validation.model.IClientSelector#selects(java.lang.Object)
     */
    public boolean selects(Object object) {
        return true;
    }
}
