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
package org.eclipse.stp.bpmn.validation.quickfixes.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.stp.bpmn.validation.BpmnValidationPlugin;
import org.eclipse.stp.bpmn.validation.quickfixes.IBpmnMarkerResolutionProvider;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;

/**
 * 
 * @author hmalphettes
 * @author Intalio Inc
 */
public class BpmnQuickfixes implements IMarkerResolutionGenerator2 {
    
    private static Map<String,Collection<IBpmnMarkerResolutionProvider>>
        BPMN_DATA_QUICK_FIXES = null;
        
    public static IMarkerResolution[] getTheResolutions(IMarker marker) {
        String quickfixID = null, elementId = null, bpmnId = null;
        try {
            quickfixID = (String) marker
                .getAttribute(IBpmnMarkerResolutionProvider.MARKER_ATTRIBUTE_QUICK_FIX_RESOLUTION_ID);
            bpmnId = (String) marker.getAttribute("bpmnId"); //$NON-NLS-1$
            elementId = (String) marker.getAttribute("elementId"); //$NON-NLS-1$
        } catch (CoreException e) {
            return new IMarkerResolution[0];
        }
        if (quickfixID == null) {
            return new IMarkerResolution[0];
        }
        Collection<IBpmnMarkerResolutionProvider> providers =
            getBpmnQuickfixesProviders().get(quickfixID);
        ArrayList<IMarkerResolution> list = new ArrayList<IMarkerResolution>();
        for (IBpmnMarkerResolutionProvider prov : providers) {
            try {
                Collection<IMarkerResolution> providedResolutions =
                    prov.getMarkerResolution(marker, bpmnId, elementId);
                if (providedResolutions != null) {
                    for (IMarkerResolution res : providedResolutions) {
                        try {
                            if (!(res instanceof IMarkerResolutionGenerator2) ||
                                    ((IMarkerResolutionGenerator2)res).hasResolutions(marker)) {
                                list.add(res);
                            }
                        } catch (Exception e) {
                            BpmnValidationPlugin.getDefault().getLog()
                                .log(new Status(IStatus.ERROR, BpmnValidationPlugin.PLUGIN_ID,
                                    "Execution failure of " + prov + //$NON-NLS-1$
                                    " for marker resolution " + res, e)); //$NON-NLS-1$
                        }
                    }
                }
            } catch (Exception e) {
                BpmnValidationPlugin.getDefault().getLog()
                    .log(new Status(IStatus.ERROR, BpmnValidationPlugin.PLUGIN_ID,
                            "Execution failure of " + prov, e)); //$NON-NLS-1$
            }
        }
        return list.toArray(new IMarkerResolution[list.size()]);
    }
    
    public static IMarker[] findOtherMarkers(IMarker[] markers, IMarkerResolution currentResolution) {
        ArrayList<IMarker> otherMarkers = new ArrayList<IMarker>();
        for (IMarker m : markers) {
             for (IMarkerResolution res :
                     BpmnQuickfixes.getTheResolutions(m)) {
                 if (res == currentResolution) {
                     otherMarkers.add(m);
                 }
             }
        }
        return otherMarkers.toArray(new IMarker[otherMarkers.size()]);
    }

    
    /** 
     * Returns whether there are any resolutions for the given marker.
     * 
     * @return <code>true</code> if there are resolutions for the given marker,
     *   <code>false</code> if not
     */
    public boolean hasResolutions(IMarker marker) {
        String quickfixID;
        try {
            quickfixID = (String) marker
                .getAttribute(IBpmnMarkerResolutionProvider.MARKER_ATTRIBUTE_QUICK_FIX_RESOLUTION_ID);
        } catch (CoreException e) {
//            e.printStackTrace();
            return false;
        }
        if (quickfixID == null) {
            return false;
        }
        return getBpmnQuickfixesProviders().containsKey(quickfixID);
    }
    
    
    /** 
     * Returns resolutions for the given marker (may
     * be empty). 
     * 
     * @return resolutions for the given marker
     */
    public IMarkerResolution[] getResolutions(IMarker marker) {
        return getTheResolutions(marker);
    }

    /** ID of the extension point for the BPMN quickfix providers */
    private static final String BPMN_QUICKFIX_EXTENSION_POINT_ID =
        "org.eclipse.stp.bpmn.validation.BpmnMarkerResolutionProvider"; //$NON-NLS-1$
    
    /**
     * Caches the declared extension points for the bpmn quick fixes.
     */
    private static Map<String,Collection<IBpmnMarkerResolutionProvider>> getBpmnQuickfixesProviders() {
        synchronized (Platform.getExtensionRegistry()) {
            if (BPMN_DATA_QUICK_FIXES != null) {
                return BPMN_DATA_QUICK_FIXES;
            }
            BPMN_DATA_QUICK_FIXES = new HashMap<String,Collection<IBpmnMarkerResolutionProvider>>();
            IConfigurationElement[] configElems = Platform.getExtensionRegistry()
                    .getConfigurationElementsFor(
                            BPMN_QUICKFIX_EXTENSION_POINT_ID);
            for (int j = configElems.length-1; j>=0; j--) {
                try {
                    IConfigurationElement el = configElems[j];
                    IBpmnMarkerResolutionProvider resProv = (IBpmnMarkerResolutionProvider)
                        el.createExecutableExtension("class"); //$NON-NLS-1$
                    String id = el.getAttribute("id"); //$NON-NLS-1$
                    Collection<IBpmnMarkerResolutionProvider> coll = BPMN_DATA_QUICK_FIXES.get(id);
                    if (coll == null) {
                        coll = new HashSet<IBpmnMarkerResolutionProvider>();
                        BPMN_DATA_QUICK_FIXES.put(id, coll);
                    }
                    coll.add(resProv);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return BPMN_DATA_QUICK_FIXES;
        }
       
    }
    
}