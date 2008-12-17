/*******************************************************************************
 ** Copyright (c) 2008, Intalio Inc.
 ** All rights reserved. This program and the accompanying materials
 ** are made available under the terms of the Eclipse Public License v1.0
 ** which accompanies this distribution, and is available at
 ** http://www.eclipse.org/legal/epl-v10.html
 ** 
 ** Contributors:
 **     Intalio Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.stp.bpmn.validation.builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;

/**
 * Extra piece of data added to the {@link Diagnostic}'s getData() list.
 * It is interpreted as custom attributes for the generated marker.
 * 
 * @author hmalphettes
 * @author Intalio Inc
 */
public class ValidationMarkerCustomAttributes extends HashMap<String, Object> {

    /** Generated */
    private static final long serialVersionUID = 7003629136856377478L;

    private ValidationMarkerCustomAttributes() {
    }
    
    public static Map<String,Object> createMarkerAttributesMap() {
        return new ValidationMarkerCustomAttributes();
    }
    
    /**
     * Create a new map to collect in the diagnostic object what will
     * become the custom marker attributes
     * 
     * @param diagnostic
     * @return
     */
    public static Map<String, Object> createMarkerAttributesMap(Diagnostic diagnostic) {
        ValidationMarkerCustomAttributes va = new ValidationMarkerCustomAttributes();
        ((List)diagnostic.getData()).add(va);
        return va;
    }
    
    /**
     * @param diagnostic
     * @return
     */
    public static HashMap<String, Object> getMarkerAttributesMap(Diagnostic diagnostic) {
        for (Object o : diagnostic.getData()) {
            if (o instanceof ValidationMarkerCustomAttributes) {
                return (ValidationMarkerCustomAttributes)o;
            }
        }
        return null;
    }
    
    
}
