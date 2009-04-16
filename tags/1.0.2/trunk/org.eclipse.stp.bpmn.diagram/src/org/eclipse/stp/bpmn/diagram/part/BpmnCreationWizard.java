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
package org.eclipse.stp.bpmn.diagram.part;

import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.wizards.EditorCreationWizard;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;

/**
 * @generated
 */
public class BpmnCreationWizard extends EditorCreationWizard {

    /**
     * @generated
     */
    public void addPages() {
        super.addPages();
        if (page == null) {
            page = new BpmnCreationWizardPage(getWorkbench(), getSelection());
        }
        addPage(page);
    }

    /**
     * @generated
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        super.init(workbench, selection);
        setWindowTitle("New Bpmn Diagram"); //$NON-NLS-1$
        setDefaultPageImageDescriptor(BpmnDiagramEditorPlugin
                .getBundledImageDescriptor("icons/wizban/NewBpmnWizard.gif")); //$NON-NLS-1$
        setNeedsProgressMonitor(true);
    }
}
