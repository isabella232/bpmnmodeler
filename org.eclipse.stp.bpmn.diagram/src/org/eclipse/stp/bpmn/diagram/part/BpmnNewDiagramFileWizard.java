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

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.edit.provider.IWrapperItemProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateDiagramViewOperation;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnDiagramEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;

/**
 * @generated
 */
public class BpmnNewDiagramFileWizard extends Wizard {

    /**
     * @generated
     */
    private TransactionalEditingDomain myEditingDomain;

    /**
     * @generated
     */
    private WizardNewFileCreationPage myFileCreationPage;

    /**
     * @generated
     */
    private IFile mySelectedModelFile;

    /**
     * @generated
     */
    private IWorkbenchPage myWorkbenchPage;

    /**
     * @generated
     */
    private IStructuredSelection mySelection;

    /**
     * @generated
     */
    private EObject myDiagramRoot;

    /**
     * @generated
     */
    public BpmnNewDiagramFileWizard(IFile selectedModelFile,
            IWorkbenchPage workbenchPage, IStructuredSelection selection,
            EObject diagramRoot, TransactionalEditingDomain editingDomain) {
        assert selectedModelFile != null : "Null selectedModelFile in BpmnNewDiagramFileWizard constructor"; //$NON-NLS-1$
        assert workbenchPage != null : "Null workbenchPage in BpmnNewDiagramFileWizard constructor"; //$NON-NLS-1$
        assert selection != null : "Null selection in BpmnNewDiagramFileWizard constructor"; //$NON-NLS-1$
        assert diagramRoot != null : "Null diagramRoot in BpmnNewDiagramFileWizard constructor"; //$NON-NLS-1$
        assert editingDomain != null : "Null editingDomain in BpmnNewDiagramFileWizard constructor"; //$NON-NLS-1$

        mySelectedModelFile = selectedModelFile;
        myWorkbenchPage = workbenchPage;
        mySelection = selection;
        myDiagramRoot = diagramRoot;
        myEditingDomain = editingDomain;
    }

    /**
     * @generated
     */
    public void addPages() {
        myFileCreationPage = new WizardNewFileCreationPage(
                BpmnDiagramMessages.BpmnNewDiagramFileWizard_new_wizard_title, mySelection) {

            public void createControl(Composite parent) {
                super.createControl(parent);

                IContainer parentContainer = mySelectedModelFile.getParent();
                String originalFileName = mySelectedModelFile
                        .getProjectRelativePath().removeFileExtension()
                        .lastSegment();
                String fileExtension = ".bpmn_diagram"; //$NON-NLS-1$
                String fileName = originalFileName + fileExtension;
                for (int i = 1; parentContainer.getFile(new Path(fileName))
                        .exists(); i++) {
                    fileName = originalFileName + i + fileExtension;
                }
                setFileName(fileName);
            }

        };
        myFileCreationPage.setTitle(BpmnDiagramMessages.BpmnNewDiagramFileWizard_page_title);
        myFileCreationPage.setDescription(BpmnDiagramMessages.bind(
        		BpmnDiagramMessages.BpmnNewDiagramFileWizard_page_description_1,
        		BpmnDiagramEditPart.MODEL_ID));
        addPage(myFileCreationPage);
        addPage(new RootElementSelectorPage());
    }

    /**
     * @generated
     */
    public boolean performFinish() {
        IFile diagramFile = myFileCreationPage.createNewFile();
        try {
            diagramFile.setCharset("UTF-8", new NullProgressMonitor()); //$NON-NLS-1$
        } catch (CoreException e) {
            BpmnDiagramEditorPlugin.getInstance().logError(
                    "Unable to set charset for diagram file", e); //$NON-NLS-1$
        }

        ResourceSet resourceSet = myEditingDomain.getResourceSet();
        final Resource diagramResource = resourceSet
                .createResource(URI.createPlatformResourceURI(diagramFile
                        .getFullPath().toString()));

        List affectedFiles = new LinkedList();
        affectedFiles.add(mySelectedModelFile);
        affectedFiles.add(diagramFile);

        AbstractTransactionalCommand command = new AbstractTransactionalCommand(
                myEditingDomain, "Initializing diagram contents", affectedFiles) { //$NON-NLS-1$
            protected CommandResult doExecuteWithResult(
                    IProgressMonitor monitor, IAdaptable info)
                    throws ExecutionException {
                int diagramVID = BpmnVisualIDRegistry
                        .getDiagramVisualID(myDiagramRoot);
                if (diagramVID != BpmnDiagramEditPart.VISUAL_ID) {
                    return CommandResult
                            .newErrorCommandResult("Incorrect model object stored as a root resource object"); //$NON-NLS-1$
                }
                Diagram diagram = ViewService.createDiagram(myDiagramRoot,
                        BpmnDiagramEditPart.MODEL_ID,
                        BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
                diagramResource.getContents().add(diagram);
                return CommandResult.newOKCommandResult();
            }
        };

        try {
            OperationHistoryFactory.getOperationHistory().execute(command,
                    new NullProgressMonitor(), null);
            diagramResource.save(Collections.EMPTY_MAP);
            IDE.openEditor(myWorkbenchPage, diagramFile);
        } catch (ExecutionException e) {
            BpmnDiagramEditorPlugin.getInstance().logError(
                    "Unable to create model and diagram", e); //$NON-NLS-1$
        } catch (IOException ex) {
            BpmnDiagramEditorPlugin
                    .getInstance()
                    .logError(
                            "Save operation failed for: " + diagramFile.getFullPath().toString(), ex); //$NON-NLS-1$
        } catch (PartInitException ex) {
            BpmnDiagramEditorPlugin.getInstance().logError(
                    "Unable to open editor", ex); //$NON-NLS-1$
        }
        return true;
    }

    /**
     * @generated
     */
    private class RootElementSelectorPage extends WizardPage implements
            ISelectionChangedListener {

        /**
         * @generated
         */
        protected RootElementSelectorPage() {
            super("Select diagram root element"); //$NON-NLS-1$
            setTitle(BpmnDiagramMessages.BpmnNewDiagramFileWizard_page_2_title);
            setDescription(BpmnDiagramMessages.BpmnNewDiagramFileWizard_page_2_description);
        }

        /**
         * @generated
         */
        public void createControl(Composite parent) {
            initializeDialogUnits(parent);
            Composite topLevel = new Composite(parent, SWT.NONE);
            topLevel.setLayout(new GridLayout());
            topLevel.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
                    | GridData.HORIZONTAL_ALIGN_FILL));
            topLevel.setFont(parent.getFont());
            setControl(topLevel);
            createModelBrowser(topLevel);
            setPageComplete(validatePage());
        }

        /**
         * @generated
         */
        private void createModelBrowser(Composite parent) {
            Composite panel = new Composite(parent, SWT.NONE);
            panel.setLayoutData(new GridData(GridData.FILL_BOTH));
            GridLayout layout = new GridLayout();
            layout.marginWidth = 0;
            panel.setLayout(layout);

            Label label = new Label(panel, SWT.NONE);
            label.setText(BpmnDiagramMessages.BpmnNewDiagramFileWizard_diagram_root_select);
            label.setLayoutData(new GridData(
                    GridData.HORIZONTAL_ALIGN_BEGINNING));

            TreeViewer treeViewer = new TreeViewer(panel, SWT.SINGLE
                    | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
            GridData layoutData = new GridData(GridData.FILL_BOTH);
            layoutData.heightHint = 300;
            layoutData.widthHint = 300;
            treeViewer.getTree().setLayoutData(layoutData);
            treeViewer.setContentProvider(new AdapterFactoryContentProvider(
                    BpmnDiagramEditorPlugin.getInstance()
                            .getItemProvidersAdapterFactory()));
            treeViewer.setLabelProvider(new AdapterFactoryLabelProvider(
                    BpmnDiagramEditorPlugin.getInstance()
                            .getItemProvidersAdapterFactory()));
            treeViewer.setInput(myDiagramRoot.eResource());
            treeViewer.setSelection(new StructuredSelection(myDiagramRoot));
            treeViewer.addSelectionChangedListener(this);
        }

        /**
         * @generated
         */
        public void selectionChanged(SelectionChangedEvent event) {
            myDiagramRoot = null;
            if (event.getSelection() instanceof IStructuredSelection) {
                IStructuredSelection selection = (IStructuredSelection) event
                        .getSelection();
                if (selection.size() == 1) {
                    Object selectedElement = selection.getFirstElement();
                    if (selectedElement instanceof IWrapperItemProvider) {
                        selectedElement = ((IWrapperItemProvider) selectedElement)
                                .getValue();
                    }
                    if (selectedElement instanceof FeatureMap.Entry) {
                        selectedElement = ((FeatureMap.Entry) selectedElement)
                                .getValue();
                    }
                    if (selectedElement instanceof EObject) {
                        myDiagramRoot = (EObject) selectedElement;
                    }
                }
            }
            setPageComplete(validatePage());
        }

        /**
         * @generated
         */
        private boolean validatePage() {
            if (myDiagramRoot == null) {
                setErrorMessage(BpmnDiagramMessages.BpmnNewDiagramFileWizard_error_no_root_selected);
                return false;
            }
            boolean result = ViewService.getInstance().provides(
                    new CreateDiagramViewOperation(new EObjectAdapter(
                            myDiagramRoot), BpmnDiagramEditPart.MODEL_ID,
                            BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT));
            setErrorMessage(result ? null
                    : BpmnDiagramMessages.BpmnNewDiagramFileWizard_error_invalid_root_selected);
            return result;
        }

    }
}