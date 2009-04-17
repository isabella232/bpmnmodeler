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
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.util.IDEEditorUtil;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.util.DiagramFileCreator;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.BpmnFactory;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.generation.impl.BPMNVisual2ProcessGenerator;
import org.eclipse.stp.bpmn.policies.BpmnDiagramXYLayoutEditPolicy;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * @generated
 */
public class BpmnDiagramEditorUtil extends IDEEditorUtil {

    /**
     * @notgenerated
     */
    public static IFile createAndOpenDiagram(
            DiagramFileCreator diagramFileCreator, IPath containerPath,
            String fileName, InputStream initialContents, String kind,
            IWorkbenchWindow window, IProgressMonitor progressMonitor,
            boolean openEditor, boolean saveDiagram) {
        IFile diagramFile = BpmnDiagramEditorUtil.createNewDiagramFile(
                diagramFileCreator, containerPath, fileName, initialContents,
                kind, window.getShell(), progressMonitor);
        return diagramFile;
    }

    /**
     * <p>
     * This method should be called within a workspace modify operation since it
     * creates resources.
     * </p>
     * 
     * @notgenerated
     * @return the created file resource, or <code>null</code> if the file was
     *         not created
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
	public static IFile createNewDiagramFile(
            DiagramFileCreator diagramFileCreator, IPath containerFullPath,
            String fileName, InputStream initialContents, String kind,
            Shell shell, IProgressMonitor progressMonitor) {
        TransactionalEditingDomain editingDomain = GMFEditingDomainFactory.INSTANCE
                .createEditingDomain();
        ResourceSet resourceSet = editingDomain.getResourceSet();
        progressMonitor.beginTask("Creating diagram and model files", 4); //$NON-NLS-1$
        final IProgressMonitor subProgressMonitor = new SubProgressMonitor(
                progressMonitor, 1);
        final IFile diagramFile = diagramFileCreator.createNewFile(
                containerFullPath, fileName, initialContents, shell,
                new IRunnableContext() {
                    public void run(boolean fork, boolean cancelable,
                            IRunnableWithProgress runnable)
                            throws InvocationTargetException,
                            InterruptedException {
                        runnable.run(subProgressMonitor);
                    }
                });
        final Resource diagramResource = resourceSet
                .createResource(URI.createPlatformResourceURI(diagramFile
                        .getFullPath().toString()));
        List affectedFiles = new ArrayList();
        affectedFiles.add(diagramFile);

        IPath modelFileRelativePath = diagramFile.getFullPath()
                .removeFileExtension().addFileExtension("bpmn"); //$NON-NLS-1$
        IFile modelFile = diagramFile.getParent().getFile(
                new Path(modelFileRelativePath.lastSegment()));
        final Resource modelResource = resourceSet.createResource(URI
                .createPlatformResourceURI(modelFile.getFullPath().toString()));
        affectedFiles.add(modelFile);

        final String kindParam = kind;
        AbstractTransactionalCommand command = new AbstractTransactionalCommand(
                editingDomain, "Creating diagram and model", affectedFiles) { //$NON-NLS-1$
            protected CommandResult doExecuteWithResult(
                    IProgressMonitor monitor, IAdaptable info)
                    throws ExecutionException {
            	
            	BpmnDiagram model = createInitialModelGen();
            	modelResource.getContents().add(model);
            	Diagram diagram = ViewService.createDiagram(model, kindParam,
                        BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
            	diagramResource.getContents().add(diagram);
                diagram.setName(diagramFile.getName());
                diagram.setElement(model);
            	BPMNVisual2ProcessGenerator generator = 
            		new BPMNVisual2ProcessGenerator(diagram);
                Pool p = generator.addPool(BpmnDiagramMessages.BpmnDiagramEditorUtil_pool_default_name, 
                		BpmnDiagramXYLayoutEditPolicy.DEFAULT_POOL_X_COORD, 
                		BpmnDiagramXYLayoutEditPolicy.DEFAULT_POOL_X_COORD, 
                		PoolEditPart.POOL_WIDTH, 
                		PoolEditPart.POOL_HEIGHT);
                generator.addActivity(p, BpmnDiagramMessages.BpmnDiagramEditorUtil_task_default_name, ActivityType.TASK, 
                		ActivityEditPart.ACTIVITY_FIGURE_SIZE.width / 2, 
                		PoolEditPart.POOL_HEIGHT / 2 - 
                			ActivityEditPart.ACTIVITY_FIGURE_SIZE.height / 2, 
                			ActivityEditPart.ACTIVITY_FIGURE_SIZE.width,
                			ActivityEditPart.ACTIVITY_FIGURE_SIZE.height);
                generator.generateViews();
                
                try {
                    Map options = new HashMap();
                    options.put(XMIResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
                    modelResource.save(options);
                    diagramResource.save(Collections.EMPTY_MAP);
                } catch (IOException e) {

                    BpmnDiagramEditorPlugin.getInstance().logError(
                            "Unable to store model and diagram resources", e); //$NON-NLS-1$
                }
                return CommandResult.newOKCommandResult();
            }
        };

        try {
            OperationHistoryFactory.getOperationHistory().execute(command,
                    new SubProgressMonitor(progressMonitor, 1), null);
        } catch (ExecutionException e) {
            BpmnDiagramEditorPlugin.getInstance().logError(
                    "Unable to create model and diagram", e); //$NON-NLS-1$
        }

        try {
            modelFile.setCharset(
                    "UTF-8", new SubProgressMonitor(progressMonitor, 1)); //$NON-NLS-1$
        } catch (CoreException e) {
            BpmnDiagramEditorPlugin.getInstance().logError(
                    "Unable to set charset for model file", e); //$NON-NLS-1$
        }
        try {
            diagramFile.setCharset(
                    "UTF-8", new SubProgressMonitor(progressMonitor, 1)); //$NON-NLS-1$
        } catch (CoreException e) {
            BpmnDiagramEditorPlugin.getInstance().logError(
                    "Unable to set charset for diagram file", e); //$NON-NLS-1$
        }

        return diagramFile;
    }

    /**
     * Create a new instance of domain element associated with canvas. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected static BpmnDiagram createInitialModelGen() {
        return BpmnFactory.eINSTANCE.createBpmnDiagram();
    }

    /**
     * @notgenerated
     * well we use the generation API now, so this not used any longer.
     * @deprecated
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
	protected static BpmnDiagram createInitialModel() {
        BpmnDiagram diagram = createInitialModelGen();
        
        String author = BpmnDiagramEditorPlugin.getInstance().getPreferenceStore()
            .getString(BpmnDiagramPreferenceInitializer.PREF_AUTHOR);
        if (author != null) {
            diagram.setAuthor(author);
        }

        Pool pool = BpmnFactory.eINSTANCE.createPool();
        pool.setName(BpmnDiagramMessages.BpmnDiagramEditorUtil_pool_default_name);
        diagram.getPools().add(pool);
        
        Activity activity = BpmnFactory.eINSTANCE.createActivity();
        activity.setName(BpmnDiagramMessages.BpmnDiagramEditorUtil_task_default_name);
        pool.getVertices().add(activity);

        return diagram;
    }

    /**
     * Not used anymore, the generation API does the job
     * Kept in case.
     * @deprecated
     * @param diagram
     */
    private static void createPoolView(Diagram diagram) {
        BpmnDiagram model = (BpmnDiagram) diagram.getElement();
        
        String semanticHints = BpmnVisualIDRegistry
                .getType(org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart.VISUAL_ID);

        EList pools = model.getPools();

        //build the pool view
        if (!pools.isEmpty()) {
            Pool pool = (Pool ) pools.iterator().next();
            Node poolNotationNode = 
                ViewService.getInstance().createNode(new EObjectAdapter(pool),
                    diagram, semanticHints, ViewUtil.APPEND,
                    BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
            Bounds bounds = NotationFactory.eINSTANCE.createBounds();
            bounds.setWidth(PoolEditPart.POOL_WIDTH);
            bounds.setHeight(PoolEditPart.POOL_HEIGHT);
            bounds.setX(BpmnDiagramXYLayoutEditPolicy.DEFAULT_POOL_X_COORD);
            bounds.setY(BpmnDiagramXYLayoutEditPolicy.DEFAULT_POOL_X_COORD);
            poolNotationNode.setLayoutConstraint(bounds);
            
            if (!pool.getVertices().isEmpty()) {
                //build the activity view
                for (Object  child : poolNotationNode.getChildren()) {
                    if (child instanceof Node &&
                            String.valueOf(PoolPoolCompartmentEditPart.VISUAL_ID).equals(
                                    String.valueOf(((Node)child).getType()))) {
                        String semanticHints2 = BpmnVisualIDRegistry.getType(
                            ActivityEditPart.VISUAL_ID);
                        Activity activity = (Activity)pool.getVertices().get(0);
                        Node aNode =
                            ViewService.getInstance().createNode(new EObjectAdapter(activity),
                                (Node)child, semanticHints2, ViewUtil.APPEND,
                                BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
                        Location loc = NotationFactory.eINSTANCE.createLocation();
                        loc.setX(ActivityEditPart.ACTIVITY_FIGURE_SIZE.width / 2);
                        loc.setY(PoolEditPart.POOL_HEIGHT / 2 - ActivityEditPart.ACTIVITY_FIGURE_SIZE.height / 2);
                        aNode.setLayoutConstraint(loc);
                        break;
                    }
                }
            
            }
            
        }
    }

    /**
     * @generated
     */
    protected static EObject createInitialRoot(BpmnDiagram model) {
        return model;
    }
}
