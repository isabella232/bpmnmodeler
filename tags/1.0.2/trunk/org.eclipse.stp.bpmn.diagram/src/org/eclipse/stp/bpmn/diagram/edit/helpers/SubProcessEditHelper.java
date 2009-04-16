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
package org.eclipse.stp.bpmn.diagram.edit.helpers;

import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;

/**
 * @generated
 */
public class SubProcessEditHelper extends BpmnBaseEditHelper {
    /**
     * This is a way to hardcode an adviser without having to declare it a
     * plugin.xml.
     * 
     * @param req
     *            the edit request
     * @return the edit helper advice, or <code>null</code> if there is none
     * @notgenerated
     */
    protected IEditHelperAdvice[] getEditHelperAdvice(IEditCommandRequest req) {
        Object editHelperContext = req.getEditHelperContext();
        IEditHelperAdvice[] advice = ElementTypeRegistry.getInstance()
                .getEditHelperAdvice(editHelperContext);
        if (advice != null && advice.length > 0) {
            IEditHelperAdvice[] newAdvice = new IEditHelperAdvice[advice.length
                    + SequenceEdgeEditHelper.INSTANCE.length + 1];
            System.arraycopy(SequenceEdgeEditHelper.INSTANCE, 0, newAdvice, 0,
                    SequenceEdgeEditHelper.INSTANCE.length);
            System.arraycopy(advice, 0, newAdvice,
                    SequenceEdgeEditHelper.INSTANCE.length, advice.length);
            newAdvice[newAdvice.length - 1] = ActivityEditHelperAdvice.THE_INSTANCE;
            return newAdvice;
        }
        return SequenceEdgeEditHelper.INSTANCE;
    }
}