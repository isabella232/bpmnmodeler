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

import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import java.util.LinkedList;
import java.util.List;

/**
 * @generated
 */
public class DataObject2CanonicalEditPolicy extends CanonicalEditPolicy {

    /**
     * @generated
     */
    protected List getSemanticChildrenList() {
        List result = new LinkedList();
        return result;
    }

    /**
     * @generated
     */
    protected boolean shouldDeleteView(View view) {
        return view.isSetElement() && view.getElement() != null
                && view.getElement().eIsProxy();
    }

    /**
     * @generated
     */
    protected String getDefaultFactoryHint() {
        return null;
    }

}
