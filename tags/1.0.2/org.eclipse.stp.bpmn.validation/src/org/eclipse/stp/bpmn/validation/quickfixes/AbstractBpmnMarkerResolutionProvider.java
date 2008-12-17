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

import org.eclipse.core.resources.IMarker;


/**
 * Some convenience methods
 * 
 * @author hmalphettes
 * @author Intalio Inc
 */
public abstract class AbstractBpmnMarkerResolutionProvider implements IBpmnMarkerResolutionProvider {

    /**
     * @param marker
     */
    protected AbstractBpmnMarkerResolutionProvider() {
    }
    /**
     * @return The id of the affected bpmn object or null.
     */
    public String getBpmnId(IMarker marker) {
        return (String)marker.getAttribute("bpmnId", (String)null); //$NON-NLS-1$
    }
    /**
     * @return The id of the affected gmf notation view object or null.
     */
    public String getElementId(IMarker marker) {
        return (String)marker.getAttribute(
                org.eclipse.gmf.runtime.common.ui.resources.IMarker.ELEMENT_ID,
                (String)null);
    }
    
    
}
