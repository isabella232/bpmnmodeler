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

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditDomain;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.document.StorageDiagramDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.editor.FileDiagramEditor;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnEditPartFactory;
import org.eclipse.stp.bpmn.diagram.providers.BpmnMarkerNavigationProvider;
import org.eclipse.stp.bpmn.diagram.providers.QuickfixResolutionMenuManager;
import org.eclipse.stp.bpmn.dnd.BpmnDropTargetListener;
import org.eclipse.stp.bpmn.menu.internal.ProxiedBpmnDiagramContextMenuListener;
import org.eclipse.stp.bpmn.palette.BpmnPaletteViewer;
import org.eclipse.stp.bpmn.provider.ActivityItemProvider;
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
        getGraphicalViewer().getContextMenu().addMenuListener(
                new QuickfixResolutionMenuManager(getGraphicalViewer()));
        
        getGraphicalViewer().getContextMenu().addMenuListener(
                new BpmnContextMenuProvider());
        
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
            } else if (event.getProperty().equals(
                            BpmnDiagramPreferenceInitializer.PREF_SEQ_LINE_ALPHA)
                       || event.getProperty().equals(
                            BpmnDiagramPreferenceInitializer.PREF_MSG_LINE_ALPHA)) {
                //repaint the connection layer after the transparency has been changed.
                //transparency applies to all connections.
                IFigure connLayer = _root.getLayer(LayerConstants.CONNECTION_LAYER);
                if (connLayer != null) {
                    //System.err.println("invalidating the connection layer");
                    connLayer.repaint();                    
                }
            } else if (event.getProperty().equals(
                            BpmnDiagramPreferenceInitializer.PREF_BPMN1_1_STYLE)) {
                ActivityItemProvider.IS_BPMN_11_STYLE = "true".equals( //$NON-NLS-1$
                        String.valueOf(event.getNewValue()));
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
    
    /**
     * Override to make it public.
     * Returns the graphical viewer.
     * @return the graphical viewer
     */
    @Override
    public GraphicalViewer getGraphicalViewer() {
        return super.getGraphicalViewer();
    }
    
    /**
     * Navigates to the given bpmn shape defined either by its view id either by
     * it bpmn id.
     * @param gmfElementId
     * @param bpmnId
     * @return true if the shape was located false otherwise.
     */
    public boolean navigateTo(String gmfElementId, String bpmnId) {
        return BpmnMarkerNavigationProvider.navigateTo(this, gmfElementId, bpmnId);
    }

    
    
    /**
     * Calls the IBpmnDiagramContextMenuListener extensions declared in other plugins.
     */
    private class BpmnContextMenuProvider implements IMenuListener {
        /**
         * Notifies this listener that the menu is about to be shown by
         * the given menu manager.
         *
         * @param manager the menu manager
         */
        public void menuAboutToShow(IMenuManager menuManager) {
            List<ProxiedBpmnDiagramContextMenuListener> menuListeners =
                BpmnDiagramEditorPlugin.getInstance().getContextMenuListeners(
                    BpmnDiagramEditor.this.getEditorSite().getId());
            if (menuListeners == null) {
                return;//nothing for this editor.
            }
            for (ProxiedBpmnDiagramContextMenuListener listener : menuListeners) {
                listener.menuAboutToShow(menuManager, BpmnDiagramEditor.this);
            }
        }
    }
    
}
