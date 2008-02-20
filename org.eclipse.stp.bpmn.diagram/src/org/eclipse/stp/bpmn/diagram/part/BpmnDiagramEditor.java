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
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditDomain;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.document.StorageDiagramDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.editor.FileDiagramEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnEditPartFactory;
import org.eclipse.stp.bpmn.dnd.BpmnDropTargetListener;
import org.eclipse.stp.bpmn.palette.BpmnPaletteViewer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ide.IGotoMarker;

/**
 * @generated
 */
public class BpmnDiagramEditor extends FileDiagramEditor implements IGotoMarker {

    /**
     * @generated
     */
    public static final String ID = "org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorID"; //$NON-NLS-1$

    private PreferenceStoreListener _prefListener;
    
    /**
     * @generated
     */
    public BpmnDiagramEditor() {
        super(true);
    }

    
    /**
     * @generated
     */
    protected String getEditingDomainID() {
        return "org.eclipse.stp.bpmn.diagram.EditingDomain"; //$NON-NLS-1$
    }

    /**
     * @generated
     */
    protected TransactionalEditingDomain createEditingDomain() {
        TransactionalEditingDomain domain = super.createEditingDomain();
        domain.setID(getEditingDomainID());
        return domain;
    }

    /**
     * @generated
     */
    protected void setDocumentProvider(IEditorInput input) {
        IFile fileInput = (IFile)input.getAdapter(IFile.class);
        if (fileInput != null) {
            setDocumentProvider(new BpmnDocumentProvider());
            setupBpmnValidationBuildAbleNature(fileInput);
        } else {
            setDocumentProvider(new StorageDiagramDocumentProvider());
        }
    }
    
    /**
     * make sure the validation nature is set
     * @param fileInput
     */
    protected void setupBpmnValidationBuildAbleNature(IResource fileInput) {
        //make sure the validation nature is set
        BpmnCreationWizardPage.setupBpmnValidationBuildableNature(
                fileInput.getFullPath(),
                BpmnCreationWizardPage.NATURE_ID,
                BpmnCreationWizardPage.BUILDER_ID);
    }

    /**
     * @generated not
     * -replaced the default connection layer by our own layer
     * -added the drop target listener necessary to do DnD
     */
    protected void configureGraphicalViewer() {
        super.configureGraphicalViewer();
        DiagramRootEditPart root = (DiagramRootEditPart) getDiagramGraphicalViewer()
                                        .getRootEditPart();
        
        BpmnEditPartFactory.setupConnectionLayerExEx(root);
        
        setupSnapToGeometry(root);
        
        // adding the Drop target listener for the annotations.
        getGraphicalViewer().addDropTargetListener(
        		new BpmnDropTargetListener(getGraphicalViewer()));
    }
    
    /**
     * Creates a diagram edit domain cutomized with the default selection tool.
     */
    @Override
    protected void createDiagramEditDomain() {
        DiagramEditDomain editDomain = new BpmnDiagramEditDomain(this);
        editDomain.setActionManager(createActionManager());
        setEditDomain(editDomain);
    }

    /**
     * Helper to read the preference and set of not 
     * the SnapHelper based on geometry.
     * @param root
     */
    private void setupSnapToGeometry(DiagramRootEditPart root) {
        boolean enableSnapToGeometry =
            BpmnDiagramEditorPlugin.getInstance().getPreferenceStore()
                .getBoolean(
                      BpmnDiagramPreferenceInitializer.PREF_SNAP_TO_GEOMETRY);
        root.getViewer().setProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED,
                                    enableSnapToGeometry);
        if (_prefListener != null) {
            _prefListener.dispose();
        }
        _prefListener = new PreferenceStoreListener(root);
    }
    
    
    
    @Override
    public void dispose() {
        if (_prefListener != null) {
            _prefListener.dispose();
        }
        super.dispose();
    }



    /**
     * @notgenerated Listener for the workspace preference store.
     */
    private class PreferenceStoreListener implements IPropertyChangeListener {
        private final DiagramRootEditPart _root;
        private final IPreferenceStore _prefStore;
        PreferenceStoreListener(DiagramRootEditPart root) {
            _root = root;
            _prefStore = (IPreferenceStore)
                        root.getPreferencesHint().getPreferenceStore();
            _prefStore.addPropertyChangeListener(this);
        }
        
        public void propertyChange(org.eclipse.jface.util.PropertyChangeEvent event) {
            if (BpmnDiagramPreferenceInitializer.PREF_SNAP_TO_GEOMETRY.equals(event.getProperty())) {         
                // Set the state of the Snap to Grid Property
                _root.getViewer().setProperty(
                     SnapToGeometry.PROPERTY_SNAP_ENABLED, event.getNewValue());
            }
        }
        
        public void dispose() {
            _prefStore.removePropertyChangeListener(this);
        }
    }

    /**
     * @notgenerated
     */
    protected PreferencesHint getPreferencesHint() {
        return new PreferencesHint(BpmnDiagramEditorPlugin.ID);
    }

    /**
     * @notgenerated plug our custom palette viewer.
     */
    @Override
    protected PaletteViewerProvider createPaletteViewerProvider() {
        getEditDomain().setPaletteRoot(createPaletteRoot(null));
        return BpmnPaletteViewer.getBpmnPaletteViewerProvider(getEditDomain());
    }
    
    
}
