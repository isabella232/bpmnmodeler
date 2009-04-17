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
package org.eclipse.stp.bpmn.validation.file;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.stp.bpmn.diagram.actions.DeleteFileLinkAction;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditor;
import org.eclipse.stp.bpmn.dnd.file.FileDnDConstants;
import org.eclipse.stp.bpmn.validation.BpmnValidationMessages;
import org.eclipse.stp.bpmn.validation.quickfixes.AbstractBpmnMarkerResolution;
import org.eclipse.stp.bpmn.validation.quickfixes.IBpmnMarkerResolutionProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * @author hmalphettes
 * @author Intalio Inc
 */
public class FileExistenceContraintQuickfixProvider implements IBpmnMarkerResolutionProvider {
    
    public static String ID = "FileExistenceContraintQuickfixProvider"; //$NON-NLS-1$
    static String MISSING_FILE_PROJ_RELATIVE_PATH_ATTRIBUTE = "missingFileProjectRelativePath"; //$NON-NLS-1$
    
    /**
     * @return The ID of the class according to its extension point.
     */
    public String getResolutionID() {
        return ID;
    }
    
    /**
     * Creates the resolution for this marker.
     * @param marker
     * @return
     */
    public Collection<IMarkerResolution> getMarkerResolution(IMarker marker, 
                                            String bpmnId, String elementId) {
        Collection<IMarkerResolution> r = new ArrayList<IMarkerResolution>(2);
        r.add(new ChangeReferenceOfMissingFileResolution(marker));
        r.add(new RemoveReferenceToMissingFileResolution(marker));
        return r;
    }
    
    /**
     * @param marker
     * @return
     */
    private static String getMissingFileHandle(IMarker marker) {
        return (String)marker.getAttribute(MISSING_FILE_PROJ_RELATIVE_PATH_ATTRIBUTE,(String)null);
    }

    /**
     * Resolution #1 for a missing dependency: change the reference to another file or folder.
     */
    class ChangeReferenceOfMissingFileResolution extends AbstractBpmnMarkerResolution {
        
        private IMarker _marker;
        
        public ChangeReferenceOfMissingFileResolution(IMarker marker) {
            _marker = marker;
        }
        
        /** 
         * Returns a short label indicating what the resolution will do. 
         * 
         * @return a short label for this resolution
         */
        public String getLabel() {
            return BpmnValidationMessages.bind(BpmnValidationMessages.FileExistenceContraintQuickfixProvider_change_reference, getMissingFileHandle(_marker));
        }

        /**
         * Runs this resolution.
         * 
         * @param marker the marker to resolve
         */
        public void run(IMarker marker, IProgressMonitor monitor) {
            WorkbenchLabelProvider labelProv = null;
            WorkbenchContentProvider contentProv = null;
            try {
                BpmnDiagramEditor ed = super.openEditor(marker, monitor);
                if (ed == null) {
                    return;//did not work
                }
                labelProv = new WorkbenchLabelProvider();
                contentProv = new WorkbenchContentProvider();
                WorkspaceResourceDialog selectNewFile = new WorkspaceResourceDialog(
                        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                        labelProv, contentProv);
                selectNewFile.setAllowMultiple(false);
                selectNewFile.setInput(marker.getResource().getProject());
                if (selectNewFile.open() != IStatus.OK) {
                    return;
                }
                final IFile newFile = selectNewFile.getFile();
                DeleteFileLinkAction changeFileAttached = new DeleteFileLinkAction(ed) {
                    /**
                     * Runs the deletion of the annotation with the source declared in
                     * the getAnnotationSource() method.
                     */
                    public void doRun(IProgressMonitor progressMonitor) {
                        if (getSelectedElt() == null) {
                            return;//humf
                        }
                        TransactionalEditingDomain domain = (TransactionalEditingDomain) 
                            AdapterFactoryEditingDomain.getEditingDomainFor(getSelectedElt());
                        RecordingCommand myCmd = new RecordingCommand(domain) {
                            @Override
                            protected void doExecute() {
                                EcoreUtil.setAnnotation(getSelectedElt(),
                                        getAnnotationSource(),
                                        FileDnDConstants.PROJECT_RELATIVE_PATH,
                                        newFile.getProjectRelativePath().toString());
                                getSelectedPart().refresh();
                            }
                        };
                        domain.getCommandStack().execute(myCmd);            
                    }

                };
                changeFileAttached.refresh();
                changeFileAttached.setup();
                changeFileAttached.doRun(monitor);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (labelProv != null) {
                    labelProv.dispose();
                }
            }
        }
        
        /**
         * Returns optional additional information about the resolution.
         * The additional information will be presented to assist the user
         * in deciding if the selected proposal is the desired choice.
         *
         * @return the additional information or <code>null</code>
         */
        public String getDescription() {
            return getLabel();
        }

        /**
         * Returns the image to be displayed in the list of resolutions.
         * The image would typically be shown to the left of the display string.
         *
         * @return the image to be shown or <code>null</code> if no image is desired
         */
        public Image getImage() {
            return null;
        }
    }
    
    /**
     * Resolution #2 for a missing dependency: remove the reference.
     */
    class RemoveReferenceToMissingFileResolution extends AbstractBpmnMarkerResolution {
        
        private IMarker _marker;
        
        public RemoveReferenceToMissingFileResolution(IMarker marker) {
            _marker = marker;
        }
        
        /** 
         * Returns a short label indicating what the resolution will do. 
         * 
         * @return a short label for this resolution
         */
        public String getLabel() {
            return BpmnValidationMessages.bind(BpmnValidationMessages.FileExistenceContraintQuickfixProvider_remove_reference, getMissingFileHandle(_marker));
        }

        /**
         * Runs this resolution.
         * 
         * @param marker the marker to resolve
         */
        public void run(IMarker marker, IProgressMonitor monitor) {
            try {
                BpmnDiagramEditor ed = super.openEditor(marker, monitor);
                if (ed == null) {
                    return;//did not work
                }
                if (ed.navigateTo(null, getBpmnId(marker))) {
                    DeleteFileLinkAction del = new DeleteFileLinkAction(ed);
                    del.refresh();
                    del.setup();
                    del.doRun(monitor);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        /**
         * Returns optional additional information about the resolution.
         * The additional information will be presented to assist the user
         * in deciding if the selected proposal is the desired choice.
         *
         * @return the additional information or <code>null</code>
         */
        public String getDescription() {
            return getLabel();
        }

        /**
         * Returns the image to be displayed in the list of resolutions.
         * The image would typically be shown to the left of the display string.
         *
         * @return the image to be shown or <code>null</code> if no image is desired
         */
        public Image getImage() {
            return null;
        }
    }
    
}
