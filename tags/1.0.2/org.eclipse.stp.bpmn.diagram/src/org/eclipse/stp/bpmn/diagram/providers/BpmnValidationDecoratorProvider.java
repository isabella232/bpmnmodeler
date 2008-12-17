/**
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 */
package org.eclipse.stp.bpmn.diagram.providers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.resources.FileChangeManager;
import org.eclipse.gmf.runtime.common.ui.resources.IFileObserver;
import org.eclipse.gmf.runtime.common.ui.util.OverlayImageDescriptor;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditDomain;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.AbstractDecorator;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.CreateDecoratorsOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecorator;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget.Direction;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.stp.bpmn.Identifiable;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnDiagramEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotation2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotationEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditor;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.IDE.SharedImages;

/** 
 * @generated
 */
public class BpmnValidationDecoratorProvider extends AbstractProvider implements
        IDecoratorProvider {
    /**
     * @generated
     */
    private static final String KEY = "validationStatus"; //$NON-NLS-1$

    private static final String TASK_KEY = "taskStatus"; //$NON-NLS-1$
    /**
     * @generated NOT
     */
    public static final String MARKER_TYPE =
        "org.eclipse.stp.bpmn.validation.diagnostic"; //$NON-NLS-1$ //$NON-NLS-2$

    public static final String BPMN_ID = "bpmnId"; //$NON-NLS-1$
    public static final String ELEMENT_ID =
        org.eclipse.gmf.runtime.common.ui.resources.IMarker.ELEMENT_ID;
    
    
    /**
     * @generated NOT
     */
    public static Image getProblemImage(IMarker marker, boolean addQuickfixDecoEventually) {
        int severity = marker.getAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO);
        if (addQuickfixDecoEventually && IDE.getMarkerHelpRegistry().hasResolutions(marker)) {
            return getProblemImage(severity, true);
        } else {
            return getProblemImage(severity, false);
        }
    }

    /**
     * @generated NOT
     */
    public static Image getProblemImage(int severity, boolean hasQuickFix) {
        //String imageName = ISharedImages.IMG_OBJS_ERROR_TSK;
        Image image = null;
        switch (severity) {
        case IMarker.SEVERITY_ERROR:
            image = BpmnDiagramEditorPlugin.getInstance().
               getBundledImage("icons/obj16/error.png"); //$NON-NLS-1$
            break;
        case IMarker.SEVERITY_WARNING:
            image = PlatformUI.getWorkbench().getSharedImages().getImage(
                    ISharedImages.IMG_OBJS_WARN_TSK);
            break;
        default:
            image = PlatformUI.getWorkbench().getSharedImages().getImage(
                    ISharedImages.IMG_OBJS_INFO_TSK);
        }
        if (hasQuickFix) {
            ImageRegistry reg = BpmnDiagramEditorPlugin.getInstance().getImageRegistry();
            String key = severity + "/quickfix"; //$NON-NLS-1$
            if (reg.getDescriptor(key) == null) {
                //put the image descriptor in an image resgitry so that we don't keep
                //creating the same image all the time.
                ImageDescriptor imgDesc = new OverlayImageDescriptor(image,
                        BpmnDiagramEditorPlugin.getBundledImageDescriptor(
                                "icons/small_quickfix.gif")//, //$NON-NLS-1$
                        /*20, 20*/);
                reg.put(key, imgDesc);
            }
            image = reg.get(key);

        }
        return image;
    }

    
    /**
     * @generated
     */
    private static MarkerObserver fileObserver = null;

    private static EObject resolveSemanticElement(IDecoratorTarget  decoratorTarget) {
        EditPart editPart = (EditPart)  decoratorTarget.getAdapter(EditPart.class);
        if (editPart != null && editPart instanceof IGraphicalEditPart) {
            EObject eobj = ((IGraphicalEditPart)editPart).resolveSemanticElement();
//            System.err.println("resolved " + editPart + " -> " + eobj);
            return eobj;
        }
        return null;
    }
    
    
    /**
     * @generated
     */
    public void createDecorators(IDecoratorTarget decoratorTarget) {
        EditPart editPart = (EditPart) decoratorTarget
                .getAdapter(EditPart.class);
        if (editPart instanceof IGraphicalEditPart) {
            Object model = editPart.getModel();
            if ((model instanceof View)) {
                View view = (View) model;
                if (!(view instanceof Edge) && !view.isSetElement()) {
                    return;
                }
            }
            EditDomain ed = editPart.getViewer().getEditDomain();
            if (!(ed instanceof DiagramEditDomain)) {
                return;
            }
            if (((DiagramEditDomain) ed).getEditorPart() instanceof BpmnDiagramEditor) {
                decoratorTarget.installDecorator(KEY,
                        new StatusDecorator(decoratorTarget));
                decoratorTarget.installDecorator(TASK_KEY,
                        new TaskDecorator(decoratorTarget));
            }
        }
    }

    /**
     * @generated
     */
    public boolean provides(IOperation operation) {
        if (!(operation instanceof CreateDecoratorsOperation)) {
            return false;
        }

        IDecoratorTarget decoratorTarget = ((CreateDecoratorsOperation) operation)
                .getDecoratorTarget();
        View view = (View) decoratorTarget.getAdapter(View.class);
        return view != null
                && BpmnDiagramEditPart.MODEL_ID.equals(BpmnVisualIDRegistry
                        .getModelID(view));
    }

    /**
     * @generated
     */
    public static class StatusDecorator extends AbstractDecorator {
        /**
         * @generated
         */
        private String viewId;
        /**
         * @notgenerated
         */
        private String bpmnId;

        /**
         * @generated
         */
        public StatusDecorator(IDecoratorTarget decoratorTarget) {
            super(decoratorTarget);
            try {
                final View view = (View) getDecoratorTarget().getAdapter(
                        View.class);
                final EObject semantic = resolveSemanticElement(decoratorTarget);
                TransactionUtil.getEditingDomain(view).runExclusive(
                        new Runnable() {
                            public void run() {
                                StatusDecorator.this.viewId = view != null ?
                                        ViewUtil.getIdStr(view) : null;
                                StatusDecorator.this.bpmnId = semantic != null 
                                    && semantic instanceof Identifiable ?
                                        ((Identifiable)semantic).getID() : null;
                            }
                        });
            } catch (Exception e) {
                BpmnDiagramEditorPlugin.getInstance().logError(
                        "ViewID access failure", e); //$NON-NLS-1$			
            }
        }
        /**
         * @notgenerated not changed the direction
         * for pools.
         * Also not adding the decorations in case that the 
         * preference to filter them is set to true.
         */
        public void refresh() {
            removeDecoration();

            if (BpmnDiagramEditorPlugin.getInstance().getPreferenceStore().
					getBoolean(BpmnDiagramPreferenceInitializer.FILTER_DECORATIONS)) {
				return;
			}
            
            View view = (View) getDecoratorTarget().getAdapter(View.class);
            EditPart editPart = (EditPart) getDecoratorTarget().getAdapter(
                    EditPart.class);
            if (view == null || view.eResource() == null) {
                return;
            }
            IResource resource = getResource(view);
            // make sure we have a resource and that it exists in an open project
            if (resource == null || !resource.exists()) {
                return;
            }

            // query for all the validation markers of the current resource
            IMarker[] markers = null;
            try {
                markers = resource.findMarkers(MARKER_TYPE, true,
                        IResource.DEPTH_INFINITE);
            } catch (CoreException e) {
                BpmnDiagramEditorPlugin.getInstance().logError(
                        "Validation marker refresh failure", e); //$NON-NLS-1$
            }
            if (markers == null || markers.length == 0) {
                return;
            }

            String elementId = ViewUtil.getIdStr(view);
            if (elementId == null) {
                return;
            }

            IMarker foundMarker = null;
            Label toolTip = null;
            int severity = IMarker.SEVERITY_INFO;
            boolean atLeastOneQuickfix = false;
            for (int i = 0; i < markers.length; i++) {
                IMarker marker = markers[i];
//              don't add a decoration if this marker type is filtered.
                
                String attribute = marker.getAttribute(ELEMENT_ID, null); //$NON-NLS-1$
                
                if (attribute == null || !attribute.equals(elementId)) {
                    attribute = marker.getAttribute(BPMN_ID, null);
                    if (attribute == null || !attribute.equals(bpmnId)) {
                        continue;
                    }
                }
                try {
    				if (BpmnDecorationFilterService.getInstance().
    						isMarkerFiltered(marker.getType(), 
    						marker.getAttribute("code"))) { //$NON-NLS-1$
    					continue;
    				}
    			} catch (CoreException e) {
    				// ignore the error and proceed.
    			}
                int nextSeverity = marker.getAttribute(IMarker.SEVERITY,
                        IMarker.SEVERITY_INFO);
                boolean hasQuickFix = IDE.getMarkerHelpRegistry().hasResolutions(marker);
                atLeastOneQuickfix = atLeastOneQuickfix || hasQuickFix;
                if (foundMarker == null) {
                    foundMarker = marker;
                    toolTip = createMarkerLabel(marker, nextSeverity, hasQuickFix,
                            editPart.getViewer().getControl().getShell());
                } else {
                    if (toolTip.getChildren().isEmpty()) {
                        Label comositeLabel = new Label();
                        FlowLayout fl = new FlowLayout(false);
                        fl.setMinorSpacing(0);
                        comositeLabel.setLayoutManager(fl);
                        comositeLabel.add(toolTip);
                        toolTip = comositeLabel;
                    }
                    toolTip.add(createMarkerLabel(marker, nextSeverity, hasQuickFix,
                            editPart.getViewer().getControl().getShell()));
                }
                severity = (nextSeverity > severity) ? nextSeverity : severity;
            }
            if (foundMarker == null) {
                return;
            }

            // add decoration
            if (editPart instanceof IGraphicalEditPart) {
                Image img = getProblemImage(severity, atLeastOneQuickfix);
                if (view instanceof Edge) {
                    setDecoration(getDecoratorTarget().addConnectionDecoration(
                            img, 50, true));
                } else {
                    int margin = -1;
                    if (editPart instanceof GraphicalEditPart) {
                        margin = MapModeUtil.getMapMode(
                        		((IGraphicalEditPart) editPart).getFigure())
                        		.DPtoLP(margin);
                    }
                    if (editPart instanceof PoolEditPart) {
                    	setDecoration(getDecoratorTarget()
                    			.addShapeDecoration(img,
                    					IDecoratorTarget.Direction.NORTH_WEST,
                    					margin, true));
                    } else {
                    	setDecoration(getDecoratorTarget()
                    			.addShapeDecoration(img,
                    					IDecoratorTarget.Direction.NORTH_EAST,
                    					margin, true));
                    }
                }
                getDecoration().setToolTip(toolTip);
            }
        }

        /**
         * Create a label for the marker.
         * Add a the bulb decoration on the top of it
         * and a push button it if there is a marker resolution for it.
         * 
         * @param marker
         * @return
         */
        private Label createMarkerLabel(final IMarker marker, int severity,
                boolean hasQuickfix, final Shell shell) {
            Image image = getProblemImage(marker, hasQuickfix);
            Label res = new Label(marker.getAttribute(
                    IMarker.MESSAGE, ""), image);//$NON-NLS-1$
            if (hasQuickfix) {
                res.setText(res.getText() + 
                        BpmnDiagramMessages.BpmnValidationDecoratorProvider_has_quickfix_suffix);
//hard to add mouselisteners to tooltips :(                
//                res.addMouseListener(new MouseListener.Stub() {
//                    public void mouseReleased(MouseEvent me) {
//                        MarkerResolutionSelectionDialog dialog =
//                            new MarkerResolutionSelectionDialog(
//                                shell, IDE.getMarkerHelpRegistry().getResolutions(marker));
//                        dialog.open();
//                    }
//                });
            }
            return res;
        }
        
        /**
         * @generated
         */
        private static IResource getResource(View view) {
            Resource model = view.eResource();
            if (model != null) {
                return WorkspaceSynchronizer.getFile(model);
            }
            return null;
        }

        /**
         * @generated
         */
        public void activate() {
            View view = (View) getDecoratorTarget().getAdapter(View.class);
            if (view == null)
                return;
            Diagram diagramView = view.getDiagram();
            if (diagramView == null)
                return;
            IFile file = WorkspaceSynchronizer.getFile(diagramView.eResource());
            if (file != null) {
                if (fileObserver == null) {
                    fileObserver = new MarkerObserver(diagramView);
                }

                fileObserver.registerDecorator(this);
            }
        }

        /**
         * @generated
         */
        public void deactivate() {
            if (fileObserver != null) {
                fileObserver.unregisterDecorator(this);
                if (!fileObserver.isRegistered()) {
                    fileObserver = null;
                }
            }

            super.deactivate();
        }

        /**
         * @generated
         */
        String getViewId() {
            return viewId;
        }
        /**
         * @notgenerated
         * @return The id of the bpmn object behind this or null.
         */
        String getBpmnId() {
            return bpmnId;
        }
    }

    /**
     * @generated
     */
    static class MarkerObserver implements IFileObserver {
        /**
         * @generated
         */
        private HashMap mapOfIdsToDecorators = null;
        private HashMap mapOfBpmnIdsToDecorators = null;

        /**
         * @generated
         */
        private boolean isRegistered = false;

        /**
         * @generated
         */
        private Diagram diagramView;

        /**
         * @generated
         */
        private MarkerObserver(Diagram diagramView) {
            this.diagramView = diagramView;
        }

        /**
         * @generated
         */
        private void registerDecorator(AbstractDecorator decorator) {
            if (decorator == null) {
                return;
            }

            if (mapOfIdsToDecorators == null) {
                mapOfIdsToDecorators = new HashMap();
                mapOfBpmnIdsToDecorators = new HashMap();
            }

            
            String decoratorViewId = null;
            if (decorator instanceof StatusDecorator) {
                decoratorViewId = ((StatusDecorator) decorator).getViewId();
            } else if (decorator instanceof TaskDecorator) {
                decoratorViewId = ((TaskDecorator) decorator).getViewId();
            }
            if (decoratorViewId == null) {
                return;
            }
            String bpmnId = null;
            if (decorator instanceof StatusDecorator) {
                bpmnId = ((StatusDecorator) decorator).getBpmnId();
            } else if (decorator instanceof TaskDecorator) {
                bpmnId = ((TaskDecorator) decorator).getBpmnId();
            }
            /* Add to the list */
            List list = (List) mapOfIdsToDecorators.get(decoratorViewId);
            if (list == null) {
                list = new ArrayList(2);
                list.add(decorator);
                mapOfIdsToDecorators.put(decoratorViewId, list);
                if (bpmnId != null) {
                    mapOfBpmnIdsToDecorators.put(bpmnId, list);
                }
                
                
            } else if (!list.contains(decorator)) {
                list.add(decorator);
            }

            /* Register with the file change manager */
            if (!isRegistered()) {
                FileChangeManager.getInstance().addFileObserver(this);
                isRegistered = true;
            }
        }

        /**
         * @generated
         */
        private void unregisterDecorator(AbstractDecorator decorator) {
            /* Return if invalid decorator */
            if (decorator == null) {
                return;
            }

            String decoratorViewId = null;
            if (decorator instanceof StatusDecorator) {
                decoratorViewId = ((StatusDecorator) decorator).getViewId();
            } else if (decorator instanceof TaskDecorator) {
                decoratorViewId = ((TaskDecorator) decorator).getViewId();
            }
            if (decoratorViewId == null) {
                return;
            }
            String bpmnId = null;
            if (decorator instanceof StatusDecorator) {
                bpmnId = ((StatusDecorator) decorator).getBpmnId();
            } else if (decorator instanceof TaskDecorator) {
                bpmnId = ((TaskDecorator) decorator).getBpmnId();
            }

            if (mapOfIdsToDecorators != null) {
                List list = (List) mapOfIdsToDecorators.get(decoratorViewId);
                if (list != null) {
                    list.remove(decorator);
                    if (list.isEmpty()) {
                        mapOfIdsToDecorators.remove(decoratorViewId);
                        if (bpmnId != null) {
                            mapOfBpmnIdsToDecorators.remove(bpmnId);
                        }
                    }
                }

                if (mapOfIdsToDecorators.isEmpty()) {
                    mapOfIdsToDecorators = null;
                    mapOfBpmnIdsToDecorators = null;
                }
            }

            if (mapOfIdsToDecorators == null) {
                /* Unregister with the file change manager */
                if (isRegistered()) {
                    FileChangeManager.getInstance().removeFileObserver(this);
                    isRegistered = false;
                }
            }
        }

        /**
         * @generated
         */
        public void handleFileRenamed(IFile oldFile, IFile file) { /* Empty Code */
        }

        /**
         * @generated
         */
        public void handleFileMoved(IFile oldFile, IFile file) { /* Empty Code */
        }

        /**
         * @generated
         */
        public void handleFileDeleted(IFile file) { /* Empty Code */
        }

        /**
         * @generated
         */
        public void handleFileChanged(IFile file) { /* Empty Code */
        }

        /**
         * @generated
         */
        public void handleMarkerAdded(IMarker marker) {
            if (marker.getAttribute(ELEMENT_ID, null) != null
                    || marker.getAttribute(BPMN_ID, null) != null) {
                handleMarkerChanged(marker);
            }
        }

        /**
         * @notgenerated
         */
        public void handleMarkerDeleted(IMarker marker, Map attributes) {
            if (mapOfIdsToDecorators == null) {
                return;
            }
            // Extract the element guid from the marker and retrieve
            // corresponding view
            String elementId = (String) attributes.get(ELEMENT_ID);
            List list = elementId != null ? (List) mapOfIdsToDecorators
                    .get(elementId) : null;
            if (list == null) {
                String bpmnId = (String) attributes.get(BPMN_ID);
                if (bpmnId != null) {
                    list = (List) mapOfBpmnIdsToDecorators.get(bpmnId);
                }
            }
            if (list != null && !list.isEmpty()) {
                refreshDecorators(list);
            }
        }

        /**
         * @notgenerated we display sub-types of the MARKER_TYPE type too.
         * if we don't find the gmf id but we find the bpmnId, we use it
         */
        public void handleMarkerChanged(IMarker marker) {
            try {
                if (mapOfIdsToDecorators == null
                        || (!marker.isSubtypeOf(MARKER_TYPE) && 
                        !marker.isSubtypeOf(IMarker.TASK))) {
                        //|| !MARKER_TYPE.equals(getType(marker))) {
                    return;
                }
                // Extract the element ID list from the marker and retrieve
                // corresponding view   
                String elementId = (String)marker.getAttribute(ELEMENT_ID); //$NON-NLS-1$
                List list = elementId != null ? (List) mapOfIdsToDecorators
                        .get(elementId) : null;
                        
                if (list == null) {
                    String bpmnId = (String)marker.getAttribute(BPMN_ID); //$NON-NLS-1$
                    if (bpmnId != null) {
                        list = (List) mapOfBpmnIdsToDecorators.get(bpmnId);
                    }
                }

                if (list != null && !list.isEmpty()) {
                    refreshDecorators(list);
                }
            } catch (CoreException e) {
                if (!MARKER_TYPE.equals(getType(marker)) && 
                        !IMarker.TASK.equals(getType(marker))) {
                    //whatever.
                    return;
                }
            
            }
        }

        /**
         * @generated
         */
        private void refreshDecorators(List decorators) {
            final List decoratorsToRefresh = decorators;
            PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
                public void run() {
                    try {
                    	if (TransactionUtil.getEditingDomain(diagramView) == null) {
                    		return;
                    	}
                        TransactionUtil.getEditingDomain(diagramView)
                            .runExclusive(new Runnable() {
                                public void run() {
                                    for (Iterator it = decoratorsToRefresh
                                            .iterator(); it.hasNext();) {
                                        IDecorator decorator = (IDecorator) it
                                                .next();
                                        if (decorator != null) {
                                            decorator.refresh();
                                        }
                                    }
                                }
                            });
                    } catch (Exception e) {
                        BpmnDiagramEditorPlugin.getInstance().logError(
                                "Decorator refresh failure", e); //$NON-NLS-1$
                    }
                }
            });
        }

        /**
         * @generated
         */
        private boolean isRegistered() {
            return isRegistered;
        }

        /**
         * @generated
         */
        private String getType(IMarker marker) {
            try {
                return marker.getType();
            } catch (CoreException e) {
                BpmnDiagramEditorPlugin.getInstance().logError(
                        "Validation marker refresh failure", e); //$NON-NLS-1$
                return ""; //$NON-NLS-1$
            }
        }
    }
    
    public static class TaskDecorator extends AbstractDecorator {

        private static IResource getResource(View view) {
            Resource model = view.eResource();
            if (model != null) {
                return WorkspaceSynchronizer.getFile(model);
            }
            return null;
        }
        
        // the two views
        private String viewId;
        private String bpmnId;
        
        public TaskDecorator(IDecoratorTarget decoratorTarget) {
            super(decoratorTarget);
            try {
                final View view = (View) getDecoratorTarget().getAdapter(
                        View.class);
                final EObject semantic = resolveSemanticElement(decoratorTarget);
                TransactionUtil.getEditingDomain(view).runExclusive(
                        new Runnable() {
                            public void run() {
                                TaskDecorator.this.viewId = view != null ?
                                        ViewUtil.getIdStr(view) : null;
                                        TaskDecorator.this.bpmnId = semantic != null 
                                    && semantic instanceof Identifiable ?
                                        ((Identifiable)semantic).getID() : null;
                            }
                        });
            } catch (Exception e) {
                BpmnDiagramEditorPlugin.getInstance().logError(
                        "ViewID access failure", e); //$NON-NLS-1$          
            }
        }
        
        String getViewId() {
            return viewId;
        }
        
        String getBpmnId() {
            return bpmnId;
        }

        public void activate() {
            View view = (View) getDecoratorTarget().getAdapter(View.class);
            if (view == null)
                return;
            Diagram diagramView = view.getDiagram();
            if (diagramView == null)
                return;
            IFile file = WorkspaceSynchronizer.getFile(diagramView.eResource());
            if (file != null) {
                if (fileObserver == null) {
                    fileObserver = new MarkerObserver(diagramView);
                }

                fileObserver.registerDecorator(this);
            }
            
        }

        public void deactivate() {
            if (fileObserver != null) {
                fileObserver.unregisterDecorator(this);
                if (!fileObserver.isRegistered()) {
                    fileObserver = null;
                }
            }

            super.deactivate();
        }
        
        public void refresh() {
            removeDecoration();

            if (BpmnDiagramEditorPlugin.getInstance().getPreferenceStore().
                    getBoolean(BpmnDiagramPreferenceInitializer.FILTER_DECORATIONS)) {
                return;
            }
            
            View view = (View) getDecoratorTarget().getAdapter(View.class);
            EditPart editPart = (EditPart) getDecoratorTarget().getAdapter(
                    EditPart.class);
            if (view == null || view.eResource() == null) {
                return;
            }
            IResource resource = getResource(view);
            // make sure we have a resource and that it exists in an open project
            if (resource == null || !resource.exists()) {
                return;
            }

            // query for all the validation markers of the current resource
            IMarker[] markers = null;
            try {
                markers = resource.findMarkers(IMarker.TASK, true,
                        IResource.DEPTH_INFINITE);
            } catch (CoreException e) {
                BpmnDiagramEditorPlugin.getInstance().logError(
                        "Validation marker refresh failure", e); //$NON-NLS-1$
            }
            if (markers == null || markers.length == 0) {
                return;
            }

            String elementId = ViewUtil.getIdStr(view);
            if (elementId == null) {
                return;
            }

            IMarker foundMarker = null;
            Label toolTip = null;
            for (int i = 0; i < markers.length; i++) {
                IMarker marker = markers[i];
//              don't add a decoration if this marker type is filtered.
                
                String attribute = marker.getAttribute(ELEMENT_ID, null); //$NON-NLS-1$
                
                if (attribute == null || !attribute.equals(elementId)) {
                    attribute = marker.getAttribute(BPMN_ID, null);
                    if (attribute == null || !attribute.equals(bpmnId)) {
                        continue;
                    }
                }
                if (foundMarker == null) {
                    foundMarker = marker;
                    toolTip = new Label(marker.getAttribute(
                            IMarker.MESSAGE, ""),  //$NON-NLS-1$
                            PlatformUI.getWorkbench().getSharedImages().getImage(SharedImages.IMG_OBJS_TASK_TSK)); //$NON-NLS-1$
                } else {
                    if (toolTip.getChildren().isEmpty()) {
                        Label comositeLabel = new Label();
                        FlowLayout fl = new FlowLayout(false);
                        fl.setMinorSpacing(0);
                        comositeLabel.setLayoutManager(fl);
                        comositeLabel.add(toolTip);
                        toolTip = comositeLabel;
                    }
                    toolTip.add(new Label(marker.getAttribute(
                            IMarker.MESSAGE, ""), PlatformUI.getWorkbench().getSharedImages().getImage(SharedImages.IMG_OBJS_TASK_TSK))); //$NON-NLS-1$
                }
            }
            if (foundMarker == null) {
                return;
            }

            // add decoration
            if (editPart instanceof IGraphicalEditPart) {
                Image img = PlatformUI.getWorkbench().getSharedImages().getImage(SharedImages.IMG_OBJS_TASK_TSK);
                if (view instanceof Edge) {
                    setDecoration(getDecoratorTarget().addConnectionDecoration(
                            img, 50, true));
                } else {
                    int margin = 0;
                    if (editPart instanceof GraphicalEditPart) {
                        if (editPart instanceof TextAnnotationEditPart
                                || editPart instanceof TextAnnotation2EditPart) {
                            margin = -4;
                        }
                        margin = MapModeUtil.getMapMode(
                                ((IGraphicalEditPart) editPart).getFigure())
                                .DPtoLP(margin);
                    }
                    
                    if (editPart instanceof TextAnnotationEditPart) {
                        ((TextAnnotationEditPart) editPart).setLabelImage(img, PositionConstants.NORTH_WEST, toolTip);
                    } else if (editPart instanceof TextAnnotation2EditPart) {
                        ((TextAnnotation2EditPart) editPart).setLabelImage(img, PositionConstants.NORTH_WEST, toolTip);
                    } else {
                        // default case, should not happen since we only do task markers on text annotations right now.
                        setDecoration(getDecoratorTarget()
                                .addShapeDecoration(img,
                                        Direction.NORTH_WEST, margin, true));
                    }
                    // this only works if the label edit part is already initialized.
//                    for (Object ep : editPart.getChildren()) {
//                        if (ep instanceof ITextAwareEditPart) {
//                            ((WrappingLabel) ((ITextAwareEditPart) ep).getFigure()).setIcon(img);
//                            ((WrappingLabel) ((ITextAwareEditPart) ep).getFigure()).setIconAlignment(PositionConstants.NORTH_WEST);
//                            ((WrappingLabel) ((ITextAwareEditPart) ep).getFigure()).setToolTip(toolTip);
//                            break;
//                        }
//                    }
                    
                }
                if (getDecoration() != null) {
                    getDecoration().setToolTip(toolTip);
                }
            }
            
        }
        
    }
}
