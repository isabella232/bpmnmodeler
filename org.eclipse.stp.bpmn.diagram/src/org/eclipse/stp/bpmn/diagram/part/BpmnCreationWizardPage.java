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

import java.io.InputStream;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.wizards.EditorWizardPage;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.util.DiagramFileCreator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnDiagramEditPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * @generated
 */
public class BpmnCreationWizardPage extends EditorWizardPage {

    /**
     * @generated
     */
    public BpmnCreationWizardPage(IWorkbench workbench,
            IStructuredSelection selection) {
        super("CreationWizardPage", workbench, selection); //$NON-NLS-1$
        setTitle(BpmnDiagramMessages.BpmnCreationWizardPage_title);
        setDescription(BpmnDiagramMessages.BpmnCreationWizardPage_description);
    }

    /**
     * @notgenerated
     */
    public IFile createAndOpenDiagram(IPath containerPath, String fileName,
            InputStream initialContents, String kind, IWorkbenchWindow dWindow,
            IProgressMonitor progressMonitor, boolean saveDiagram) {
        setupBpmnValidationBuildableNature(containerPath);
        return BpmnDiagramEditorUtil.createAndOpenDiagram(
                getDiagramFileCreator(), containerPath, fileName,
                initialContents, kind, dWindow, progressMonitor,
                isOpenNewlyCreatedDiagramEditor(), saveDiagram);
    }
    
    /**
     * The nature for the emft validation builder
     * define in org.eclipse.stp.bpmn.validation
     */
    public static final String NATURE_ID =
        "org.eclipse.stp.bpmn.validation.BatchValidationBuildAbleNature"; //$NON-NLS-1$
    /**
     * The nature for the emft validation builder
     * define in org.eclipse.stp.bpmn.validation
     */
    public static final String BUILDER_ID =
        "org.eclipse.stp.bpmn.validation.BatchValidationBuilder"; //$NON-NLS-1$
    
    /**
     * @return The nature id to set on the project containing the new diagram
     * or null if it should not be done. By default the nature and builder
     * defined in the plugin org.eclipse.stp.bpmn.validation
     */
    protected String getNatureId() {
        return NATURE_ID;
    }
    
    /**
     * @return The builder id to be added on the project containing the new diagram
     * or null if it should not be done. By default the nature and builder
     * defined in the plugin org.eclipse.stp.bpmn.validation
     */
    protected String getBuilderId() {
        return BUILDER_ID;
    }
    
    /**
     * @param containerPath
     */
    protected void setupBpmnValidationBuildableNature(IPath containerPath) {
        
        String natureId = getNatureId();
        String builderId = getBuilderId();
        
        setupBpmnValidationBuildableNature(containerPath, natureId, builderId);
        
    }
    
    /**
     * @param containerPath
     */
    public static final void setupBpmnValidationBuildableNature(IPath containerPath,
            String natureId, String builderId) {

        
        if (natureId == null && builderId == null) {
            return;//nothing to configure.
        }
        
        if (containerPath.segmentCount() < 1) {
            return;//something went wrong?
        }
        IResource res = 
            ResourcesPlugin.getWorkspace().getRoot()
                .findMember(containerPath);
        
        if (res == null) {
            return; // don't insist.
        }
        IProject project = res.getProject();
        try {
            IProjectDescription description = project.getDescription();
            
            if (natureId != null) {
            
                String[] natures = description.getNatureIds();
                boolean foundTheNature = false;
                for (int i = 0; i < natures.length; ++i) {
                    if (NATURE_ID.equals(natures[i])) {
                        foundTheNature = true;
                        break;//nothing to change it has the nature already
                    }
                }
                if (!foundTheNature) {
                    // Add the nature
                    String[] newNatures = new String[natures.length + 1];
                    System.arraycopy(natures, 0, newNatures, 0, natures.length);
                    newNatures[natures.length] = NATURE_ID;
                    description.setNatureIds(newNatures);
                    project.setDescription(description, null);
                }
            }
            
            if (builderId != null) {
                // Configure the nature: add the builder
                ICommand[] commands = description.getBuildSpec();
    
                for (int i = 0; i < commands.length; ++i) {
                    if (commands[i].getBuilderName().equals(
                            BUILDER_ID)) {
                        return;
                    }
                }
    
                ICommand[] newCommands = new ICommand[commands.length + 1];
                System.arraycopy(commands, 0, newCommands, 0, commands.length);
                ICommand command = description.newCommand();
                command.setBuilderName(BUILDER_ID);
                newCommands[newCommands.length - 1] = command;
                description.setBuildSpec(newCommands);
                project.setDescription(description, null);
            }
            
        } catch (CoreException e) {
            BpmnDiagramEditorPlugin.getInstance().
                getLog().log(
                		new Status(IStatus.ERROR, BpmnDiagramEditorPlugin.ID,
                				e.getMessage(), e));
        }
    }    

    /**
     * @generated
     */
    protected String getDefaultFileName() {
        return "default"; //$NON-NLS-1$
    }

    /**
     * @generated
     */
    public DiagramFileCreator getDiagramFileCreator() {
        return BpmnDiagramFileCreator.getInstance();
    }

    /**
     * @generated
     */
    protected String getDiagramKind() {
        return BpmnDiagramEditPart.MODEL_ID;
    }

    /**
     * @generated
     */
    protected boolean validatePage() {
        if (super.validatePage()) {
            String fileName = getFileName();
            if (fileName == null) {
                return false;
            }
            // appending file extension to correctly process file names including "." symbol
            IPath path = getContainerFullPath()
                    .append(
                            getDiagramFileCreator().appendExtensionToFileName(
                                    fileName));
            path = path.removeFileExtension().addFileExtension("bpmn"); //$NON-NLS-1$
            if (ResourcesPlugin.getWorkspace().getRoot().exists(path)) {
                setErrorMessage(BpmnDiagramMessages.bind(
                		BpmnDiagramMessages.BpmnCreationWizardPage_error_already_exists,
                		path.lastSegment()));
                return false;
            }
            return true;
        }
        return false;
    }

}
