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

import java.util.Collection;

import org.eclipse.core.resources.IMarker;
import org.eclipse.stp.bpmn.validation.quickfixes.internal.BpmnQuickfixes;
import org.eclipse.ui.IMarkerResolution;

/**
 * 
 * @author hmalphettes
 * @author Intalio Inc
 */
public interface IBpmnMarkerResolutionProvider {

    /**
     * The marker attribute that stores the id of the resolution marker to use.
     */
    public static final String MARKER_ATTRIBUTE_QUICK_FIX_RESOLUTION_ID = "BPMN_QUICK_FIX_RESOLUTION_ID"; //$NON-NLS-1$
    
    /** The marker attribute which value is true when there are resolution
     * provided by the {@link BpmnQuickfixes} for it. 
     * the name of this attribute is specified in the plugin.xml */
    public static final String MARKER_ATTRIBUTE_BPMN_QUICK_FIXABLE = "bpmnquickfixable"; //$NON-NLS-1$
    
    /**
     * @return The ID of the class according to its extension point.
     */
    public String getResolutionID();
    
    /**
     * Creates the resolution for this marker.
     * @param marker
     * @param elementID The GMF View ID as read from the marker.
     * Or null if it is not known on this marker.
     * @param bpmnID The id of the BPMN Identifiable or null if it is not known
     * @return The list of marker resolution. Eventually aware of those arguments.
     */
    public Collection<IMarkerResolution> getMarkerResolution(IMarker marker, String bpmnID, String elementID);
    
}
