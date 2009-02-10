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

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnDiagramEditPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @generated
 */
public class BpmnInitDiagramFileAction implements IObjectActionDelegate {

    /**
     * @generated
     */
    private IWorkbenchPart myPart;

    /**
     * @generated
     */
    private IFile mySelectedModelFile;

    /**
     * @generated
     */
    private IStructuredSelection mySelection;

    /**
     * @generated
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        myPart = targetPart;
    }

    /**
     * @generated
     */
    public void selectionChanged(IAction action, ISelection selection) {
        mySelectedModelFile = null;
        mySelection = StructuredSelection.EMPTY;
        action.setEnabled(false);
        if (selection instanceof IStructuredSelection == false
                || selection.isEmpty()) {
            return;
        }
        mySelection = (IStructuredSelection) selection;
        mySelectedModelFile = (IFile) ((IStructuredSelection) selection)
                .getFirstElement();
        action.setEnabled(true);
    }

    /**
     * @generated
     */
    public void run(IAction action) {
        TransactionalEditingDomain editingDomain = GMFEditingDomainFactory.INSTANCE
                .createEditingDomain();
        ResourceSet resourceSet = editingDomain.getResourceSet();
        EObject diagramRoot = null;
        try {
            Resource resource = resourceSet.getResource(URI
                    .createPlatformResourceURI(mySelectedModelFile
                            .getFullPath().toString()), true);
            diagramRoot = (EObject) resource.getContents().get(0);
        } catch (WrappedException ex) {
            BpmnDiagramEditorPlugin
                    .getInstance()
                    .logError(
                            "Unable to load resource: " + mySelectedModelFile.getFullPath().toString(), ex); //$NON-NLS-1$
        }
        if (diagramRoot == null) {
            MessageDialog.openError(myPart.getSite().getShell(), "Error", //$NON-NLS-1$
                    BpmnDiagramMessages.BpmnInitDiagramFileAction_model_loading_failed);
            return;
        }
        Wizard wizard = new BpmnNewDiagramFileWizard(mySelectedModelFile,
                myPart.getSite().getPage(), mySelection, diagramRoot,
                editingDomain);
        IDialogSettings pluginDialogSettings = BpmnDiagramEditorPlugin
                .getInstance().getDialogSettings();
        IDialogSettings initDiagramFileSettings = pluginDialogSettings
                .getSection("InisDiagramFile"); //$NON-NLS-1$
        if (initDiagramFileSettings == null) {
            initDiagramFileSettings = pluginDialogSettings
                    .addNewSection("InisDiagramFile"); //$NON-NLS-1$
        }
        wizard.setDialogSettings(initDiagramFileSettings);
        wizard.setForcePreviousAndNextButtons(false);
        wizard.setWindowTitle(BpmnDiagramMessages.bind(
        		BpmnDiagramMessages.BpmnInitDiagramFileAction_title, 
        		BpmnDiagramEditPart.MODEL_ID));

        WizardDialog dialog = new WizardDialog(myPart.getSite().getShell(),
                wizard);
        dialog.create();
        dialog.getShell().setSize(Math.max(500, dialog.getShell().getSize().x),
                500);
        dialog.open();
    }

}