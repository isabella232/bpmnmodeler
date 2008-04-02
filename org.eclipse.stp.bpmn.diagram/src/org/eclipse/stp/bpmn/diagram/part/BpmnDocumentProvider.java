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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.DemultiplexingListener;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.DiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.DiagramModificationListener;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.document.FileDiagramDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.document.FileDiagramModificationListener;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.ui.IFileEditorInput;

/**
 * @generated
 */
public class BpmnDocumentProvider extends FileDiagramDocumentProvider {

    /**
     * @generated not
     * supporting SaveAs
     * First set the semantic resource to its new URI.
     * Then save it.
     * Second set it to a short relative URI
     * Then save the diagram resource
     * Third set the semantic resource to its new URI and stay that 
     * way.
     */
    protected void saveDocumentToFile(IDocument document, IFile file,
            boolean overwrite, IProgressMonitor monitor) throws CoreException {
        final Diagram diagram = (Diagram) document.getContent();
        
        IDiagramDocument diagramDocument = (IDiagramDocument) document;
        TransactionalEditingDomain domain = diagramDocument.getEditingDomain();
        List resources = domain.getResourceSet().getResources();
        
        monitor.beginTask("Saving diagram", resources.size() + 1); //$NON-NLS-1$
        try {
            URI semanticURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
            if (!semanticURI.equals(diagram.getElement().eResource().getURI())) {
                handleElementMoved(diagram, file.getFullPath());
            }
            diagram.getElement().eResource().save(Collections.EMPTY_MAP);
            diagram.eResource().save(Collections.EMPTY_MAP);
            
        } catch (Exception e) {
            BpmnDiagramEditorPlugin.getInstance().logError(
                    "Unable to save resource: " + diagram.eResource().getURI(), e); //$NON-NLS-1$
        }
        monitor.done();
    }
    
    /**
     * @generated
     */
    protected ISchedulingRule getSaveRule(Object element) {
        IDiagramDocument diagramDocument = getDiagramDocument(element);
        if (diagramDocument != null) {
            Diagram diagram = diagramDocument.getDiagram();
            if (diagram != null) {
                Collection rules = new ArrayList();
                for (Iterator it = diagramDocument.getEditingDomain()
                        .getResourceSet().getResources().iterator(); it
                        .hasNext();) {
                    IFile nextFile = WorkspaceSynchronizer
                            .getFile((Resource) it.next());
                    if (nextFile != null) {
                        rules.add(computeSaveSchedulingRule(nextFile));
                    }
                }
                return new MultiRule((ISchedulingRule[]) rules
                        .toArray(new ISchedulingRule[rules.size()]));
            }
        }
        return super.getSaveRule(element);
    }

    /**
     * @generated
     */
    protected FileInfo createFileInfo(IDocument document,
            FileSynchronizer synchronizer, IFileEditorInput input) {
        assert document instanceof DiagramDocument;

        DiagramModificationListener diagramListener = new CustomModificationListener(
                this, (DiagramDocument) document, input);
        DiagramFileInfo info = new DiagramFileInfo(document, synchronizer,
                diagramListener);

        diagramListener.startListening();
        return info;
    }

    /**
     * @generated
     */
    private ISchedulingRule computeSaveSchedulingRule(IResource toCreateOrModify) {
        if (toCreateOrModify.exists()
                && toCreateOrModify.isSynchronized(IResource.DEPTH_ZERO))
            return fResourceRuleFactory.modifyRule(toCreateOrModify);

        IResource parent = toCreateOrModify;
        do {
            /*
             * XXX This is a workaround for https://bugs.eclipse.org/bugs/show_bug.cgi?id=67601
             * IResourceRuleFactory.createRule should iterate the hierarchy itself.
             */
            toCreateOrModify = parent;
            parent = toCreateOrModify.getParent();
        } while (parent != null && !parent.exists()
                && !parent.isSynchronized(IResource.DEPTH_ZERO));

        return fResourceRuleFactory.createRule(toCreateOrModify);
    }

    /**
     * CustomModificationListener listen to changes in the semantic model
     * and marks the editor dirty accordingly.
     * As most changes are handled graphically, the model only listens
     * to SET operations that could be operated from the properties  view.
     * However, we reimplement some actions, like Dnd operations, that 
     * force us to listen to NOT_TOUCH operations.
     */
    private class CustomModificationListener extends
            FileDiagramModificationListener {

        
        private DemultiplexingListener myListener = null;

       
        public CustomModificationListener(
                BpmnDocumentProvider documentProviderParameter,
                DiagramDocument documentParameter,
                IFileEditorInput inputParameter) {
            super(documentProviderParameter, documentParameter, inputParameter);
            final DiagramDocument document = documentParameter;
            
            // CHANGE : Listen to NOT_TOUCH events.
            myListener = new DemultiplexingListener(
                    NotificationFilter.NOT_TOUCH) {
                protected void handleNotification(
                        TransactionalEditingDomain domain,
                        Notification notification) {
                    if (notification.getNotifier() instanceof EObject) {
                        Resource modifiedResource = ((EObject) notification
                                .getNotifier()).eResource();
                        if (modifiedResource != document.getDiagram()
                                .eResource()) {
                            document.setContent(document.getContent());
                        }
                    }

                }
            };
        }
        
        public void startListening() {
            super.startListening();
            getEditingDomain().addResourceSetListener(myListener);
        }
        
        public void stopListening() {
            getEditingDomain().removeResourceSetListener(myListener);
            super.stopListening();
        }

    }

    /**
     * Override this method to change the semantic URI too.
     */
    @Override
    protected void handleElementMoved(IFileEditorInput fileEditorInput,
            IPath path) {
        if (path != null) {
            IDiagramDocument diagramDocument = getDiagramDocument(fileEditorInput);
            Diagram diagram = null;
            if (diagramDocument != null) {
                diagram = diagramDocument.getDiagram();
            }
            if (diagram != null) {
                //not to os string!
                diagram.getElement().eResource().setURI(URI.createPlatformResourceURI(path.removeFileExtension().addFileExtension("bpmn").toString())); //$NON-NLS-1$
            }
        }
        super.handleElementMoved(fileEditorInput, path);
    }
    
    /**
     * Override this method to change the semantic URI too,
     * with a diagram instead of the editor input.
     * 
     * Does the same thing.
     */
    protected void handleElementMoved(Diagram diagram,
            IPath path) {
        if (path != null) {
            if (diagram != null) {
                //not to os string!
                diagram.getElement().eResource().setURI(URI.createPlatformResourceURI(path.removeFileExtension().addFileExtension("bpmn").toString())); //$NON-NLS-1$
                diagram.eResource().setURI(URI.createPlatformResourceURI(path.toString()));
            }
        }
    }
}
