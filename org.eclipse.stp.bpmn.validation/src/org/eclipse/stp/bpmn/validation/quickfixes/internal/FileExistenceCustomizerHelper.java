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
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.stp.bpmn.validation.BpmnValidationPlugin;
import org.eclipse.stp.bpmn.validation.IConstraintStatusEx;
import org.eclipse.stp.bpmn.validation.quickfixes.IBpmnFileExistenceConstraintCustomizer;

/**
 * Helper class to deal with the instances of {@link IBpmnFileExistenceConstraintCustomizer}
 * 
 * @author hmalphettes
 * @author Intalio Inc
 */
public class FileExistenceCustomizerHelper {

    /** ID of the extension point for the BPMN quickfix providers */
    private static final String FILE_EXISTENCE_CUSTOMIZER_QUICKFIX_EXTENSION_POINT_ID =
        "org.eclipse.stp.bpmn.validation.BpmnFileExistenceConstraintCustomizer"; //$NON-NLS-1$
    
    private static List<IBpmnFileExistenceConstraintCustomizer> INDEX = null;
    
    /**
     * Caches the declared extension points for the bpmn quick fixes.
     */
    public static List<IBpmnFileExistenceConstraintCustomizer> getFileExistenceCustomizers() {
        synchronized (Platform.getExtensionRegistry()) {
            if (INDEX != null) {
                return INDEX;
            } 
            INDEX = new ArrayList<IBpmnFileExistenceConstraintCustomizer>();
            IConfigurationElement[] configElems = Platform.getExtensionRegistry()
                    .getConfigurationElementsFor(
                            FILE_EXISTENCE_CUSTOMIZER_QUICKFIX_EXTENSION_POINT_ID);
            for (int j = configElems.length-1; j>=0; j--) {
                try {
                    IConfigurationElement el = configElems[j];
                    BpmnFileExistenceConstraintCustomizerProxy resProv =
                        new BpmnFileExistenceConstraintCustomizerProxy(el);
                    INDEX.add(resProv);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return INDEX;
        }
    }
        
}
/**
 * Proxy to avoid loading the file existence contraint until it is required.
 */
class BpmnFileExistenceConstraintCustomizerProxy implements IBpmnFileExistenceConstraintCustomizer {
    
    private IConfigurationElement _conf;
    private IBpmnFileExistenceConstraintCustomizer _cached;
    private boolean _resolveFailed = false;
    
    BpmnFileExistenceConstraintCustomizerProxy(IConfigurationElement el) {
        _conf = el;
    }
    
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
            IConstraintStatusEx currentStatus) {
        if (getProxied() == null) {
            return true;
        }
        return getProxied().validates(bpmnElement, missingDocumentation, currentStatus);
    }
    
    /**
     * @return The priority to influence the order in which the validators are called.
     */
    public int getPriority() {
        if (getProxied() == null) {
            return -1;//never.
        }
        return getProxied().getPriority();
    }

    private IBpmnFileExistenceConstraintCustomizer getProxied() {
        if (_cached != null || _resolveFailed) {
            return _cached;
        }
        try {
            _cached = (IBpmnFileExistenceConstraintCustomizer)
                    _conf.createExecutableExtension("class"); //$NON-NLS-1$
        } catch (CoreException e) {
            _resolveFailed = true;
            BpmnValidationPlugin.getDefault().getLog()
                .log(new Status(IStatus.ERROR, BpmnValidationPlugin.PLUGIN_ID,
                        "Unable to load the IBPMNFileExistenceConstraintCustomizer: " //$NON-NLS-1$
                        + _conf.getAttribute("class"), e)); //$NON-NLS-1$
        }
        return _cached;
    }
    
}
