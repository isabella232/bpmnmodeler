/*
 * Copyright (c) 2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *
 * Date           Author             Changes
 * Aug 12, 2008   Hugues Malphettes  Created
 */
package org.eclipse.stp.bpmn.menu.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditor;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.menu.IBpmnDiagramContextMenuListener;

/**
 * Called back when a context menu for a bpmn diagram editor
 * is created. Declared as extension points.
 * <p>
 * This is used internally to avoid loading the class and the plugins where the class
 * is declared until the 
 * </p>
 * 
 * @author hmalphettes
 * @author Intalio Inc
 */
public class ProxiedBpmnDiagramContextMenuListener implements IBpmnDiagramContextMenuListener {

    private String _errorWhenLoading = null;
    private IConfigurationElement _proxied;
    private IBpmnDiagramContextMenuListener _resolved;
    
    private int _priority;
    
    /**
     * 
     * @param proxiedConfiguration
     * @param priority
     */
    public ProxiedBpmnDiagramContextMenuListener(
            IConfigurationElement proxiedConfiguration, int priority) {
        _proxied = proxiedConfiguration;
        _priority = priority;
    }
    
    /**
     * @param menuManager The menu that is currently created.
     * @param currentEditor Gives access to the current selection as well as the entire
     * diagram currently displayed. For example to get access to the current selection:
     * currentEditor.getGraphicalViewer().getSelection()
     */
    public void menuAboutToShow(IMenuManager menuManager,
            BpmnDiagramEditor currentEditor) {
        if (_resolved == null && _errorWhenLoading == null) {
            try {
                _resolved = (IBpmnDiagramContextMenuListener)
                    _proxied.createExecutableExtension("class"); //$NON-NLS-1$
            } catch (CoreException e) {
                _errorWhenLoading = "Unable to create an IBpmnDiagramContextMenuListener"; //$NON-NLS-1$
                BpmnDiagramEditorPlugin.getInstance().getLog().log(
                        new Status(IStatus.ERROR, BpmnDiagramEditorPlugin.ID,
                                "Unable to create an IBpmnDiagramContextMenuListener", e)); //$NON-NLS-1$
            }
        }
        if (_resolved != null) {
            _resolved.menuAboutToShow(menuManager, currentEditor);
        }
        
    }
    
    /**
     * Ordering the call backs.
     * @return
     */
    public int getPriority() {
        return _priority;
    }
}
