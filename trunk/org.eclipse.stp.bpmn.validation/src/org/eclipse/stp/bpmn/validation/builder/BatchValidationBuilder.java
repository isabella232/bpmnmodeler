/******************************************************************************
 * Copyright (c) 2006-2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.stp.bpmn.validation.builder;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.core.resources.GMFResourceFactory;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.TextAnnotation;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;
import org.eclipse.stp.bpmn.validation.BpmnValidationMessages;
import org.eclipse.stp.bpmn.validation.BpmnValidationPlugin;
import org.eclipse.stp.bpmn.validation.IResourceImportersRegistry;
import org.eclipse.stp.bpmn.validation.providers.BpmnValidationProvider;

/**
 * Builder that calls the validation constraints.
 * Also forces the build of diagram files listed as looking for missing resources
 * thanks to the ResourceImportersRegistry.
 * 
 * @author hmalphettes
 * @author Intalio Inc
 */
public class BatchValidationBuilder extends IncrementalProjectBuilder {
    
    /** category of imports managed by the batch validation builder */
    public static String GENERIC_FILE_IMPORT_INDEX_CATEGORY_ID = "attachedFile"; //$NON-NLS-1$
    
    /**
     * Mask for the delta flags that will just make sure something else than
     * the markers were changed.
     * When only the markers are changed we assume we don't need to 
     * validate the resource.
     */
    public static int DELTA_CONTENT_MASK = 
        IResourceDelta.CONTENT | IResourceDelta.REPLACED | IResourceDelta.SYNC |
        IResourceDelta.MOVED_TO | IResourceDelta.MOVED_FROM;
    
	class BpmnDiagramResourceDeltaVisitor implements IResourceDeltaVisitor {
        
        IProgressMonitor _monitor;
        
        //collects the resources that are not directly in the resource-delta but that
        //needs to be rebuilt because they do 'import' a file that has been changed/added/removed
        Set<IResource> _extraResourcesToBuild = new HashSet<IResource>();
        Set<IResource> _alreadyBuilt = new HashSet<IResource>();
        
        BpmnDiagramResourceDeltaVisitor(IProgressMonitor monitor) {
            _monitor = monitor;
        }
        /**
         * @return The set of resources that need to be validated even though
         * they were not inside the ChangedDelta. These are resources affected by
         * the change of another resource.
         */
        public Set<IResource> getExtraFilesToBuild() {
            _extraResourcesToBuild.removeAll(_alreadyBuilt);
            return _extraResourcesToBuild;
        }
        
        public boolean isAlreadyBuilt(IResource tobuild) {
            return _alreadyBuilt != null && !_alreadyBuilt.contains(tobuild);
        }
        
		/* 
		 * @see org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse.core.resources.IResourceDelta)
		 */
		public boolean visit(IResourceDelta delta) throws CoreException {
            IResource resource = delta.getResource();
            if (resource.getFullPath().toString().indexOf("/.") != -1) { //$NON-NLS-1$
                return false;//no building the descendants of a folder
                //that starts with a '.'
            }
            //don't rebuild when only the markers are changing.
            //we don't care about that
            if (delta.getKind() == IResourceDelta.CHANGED &&
                    (delta.getFlags() & DELTA_CONTENT_MASK) == 0) {
                return true;
            }
            _alreadyBuilt.add(resource);
			switch (delta.getKind()) {
			case IResourceDelta.ADDED:
			case IResourceDelta.REMOVED:
			case IResourceDelta.CHANGED:
				// handle changed resource
                checkBpmnResource(resource, _extraResourcesToBuild, _monitor);
				break;
			}
			//return true to continue visiting children.
			return true;
		}
	}

	class BpmnDiagramResourceVisitor implements IResourceVisitor {
        
        IProgressMonitor _monitor;
        
        BpmnDiagramResourceVisitor(IProgressMonitor monitor) {
            _monitor = monitor;
        }
        
        public boolean visit(IResource resource) {
            checkBpmnResource(resource, null, _monitor);
			//return true to continue visiting children.
			return true;
		}
	}

	public static final String BUILDER_ID = 
        "org.eclipse.stp.bpmn.validation.BatchValidationBuilder"; //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.internal.events.InternalBuilder#build(int,
	 *      java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		if (kind == FULL_BUILD) {
			fullBuild(monitor);
		} else {
			IResourceDelta delta = getDelta(getProject());
			if (delta == null) {
				fullBuild(monitor);
			} else {
				incrementalBuild(delta, monitor);
			}
		}
        IResourceImportersRegistry rep = BpmnValidationPlugin
            .getResourceImportersRegistry(getProject(),
                    GENERIC_FILE_IMPORT_INDEX_CATEGORY_ID, monitor);
        if (rep.isDirty()) {
            rep.save(getProject(), GENERIC_FILE_IMPORT_INDEX_CATEGORY_ID, monitor);
        }
		return null;
	}
    
    private static GMFResourceFactory RESOURCE_FACTORY = new GMFResourceFactory();

    /**
     * 
     * @param resource
     * @param collectExtraFilesToBuild Set of files that will need to be built if they
     * were not part of the delta of files. if a file is not changed but depends on a file
     * that is changed, it is added there so that it is built at the end of the visit.
     * @param monitor
     */
	void checkBpmnResource(IResource resource,
            Set<IResource> collectExtraFilesToBuild,
            IProgressMonitor monitor) {
        if (resource instanceof IFile &&
                resource.getName().endsWith(".bpmn_diagram")) { //$NON-NLS-1$
			IFile file = (IFile) resource;
            try {
                BpmnValidationPlugin.getResourceImportersRegistry(getProject(),
                        GENERIC_FILE_IMPORT_INDEX_CATEGORY_ID, monitor)
                            .clearImports(file);
                file.deleteMarkers(BpmnValidationProvider.MARKER_TYPE, 
                    false, IResource.DEPTH_ZERO);
                
            } catch (CoreException ce) {
            }
            if (file.exists()) {
                try {
                    validateDiagramFile(file, monitor);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
        if (collectExtraFilesToBuild != null) {
            Set<IResource> importers =
                BpmnValidationPlugin.getResourceImportersRegistry(getProject(),
                    GENERIC_FILE_IMPORT_INDEX_CATEGORY_ID, monitor)
                        .getImporters(resource);
            if (importers != null) {
                collectExtraFilesToBuild.addAll(importers);
            }
        }
	}

	protected void fullBuild(final IProgressMonitor monitor)
			throws CoreException {
		try {
            clean(monitor);
			getProject().accept(new BpmnDiagramResourceVisitor(monitor));
		} catch (CoreException e) {
		}
	}

	protected void incrementalBuild(IResourceDelta delta,
			IProgressMonitor monitor) throws CoreException {
        BpmnDiagramResourceDeltaVisitor visitor =
            new BpmnDiagramResourceDeltaVisitor(monitor);
		delta.accept(visitor);
        for (IResource r : visitor.getExtraFilesToBuild()) {
            if (r.exists()) {
                //at least in the case of the generic attached files,
                //the import is registered on the .bpmn (semantic model)
                //because the constraint is made on a semantic element.
                
                //but our builder only builds associated .bpmn_diagram.
                //this hacks seems reasonable:
                if (r.getName().endsWith(".bpmn")) { //$NON-NLS-1$
                    IResource r_diagram = getProject().findMember(
                            r.getProjectRelativePath().removeFileExtension()
                                .addFileExtension("bpmn_diagram")); //$NON-NLS-1$
                    if (r_diagram != null && !visitor.isAlreadyBuilt(r_diagram)) {
                        r = r_diagram;
                    }
                }
                checkBpmnResource(r, null, monitor);
            }
        }
	}
    
    
    @Override
    protected void clean(IProgressMonitor monitor) throws CoreException {
        if (getProject() == null || !getProject().isAccessible()) {
            return;
        }
        BpmnValidationPlugin.getResourceImportersRegistry(getProject(),
                GENERIC_FILE_IMPORT_INDEX_CATEGORY_ID, monitor).clearAll();
        getProject().deleteMarkers(BpmnValidationProvider.MARKER_TYPE, 
                false, IResource.DEPTH_INFINITE);
    }

    /**
     * Calls the batch validation on a diagram file.
     * Used by builders.
     * 
     * @param diagramFile
     * @param monitor
     * @throws IOException
     */
    public static final void validateDiagramFile(IFile diagramFile,
            IProgressMonitor monitor) throws IOException {
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.getResourceFactoryRegistry()
            .getExtensionToFactoryMap().put("bpmn", RESOURCE_FACTORY); //$NON-NLS-1$
        resourceSet.getResourceFactoryRegistry()
            .getExtensionToFactoryMap().put("bpmn_diagram", RESOURCE_FACTORY); //$NON-NLS-1$
        resourceSet.getPackageRegistry().put(NotationPackage.eNS_URI,
                NotationPackage.eINSTANCE);
        resourceSet.getPackageRegistry().put(BpmnPackage.eNS_URI,
                BpmnPackage.eINSTANCE);
        
        //make sure we can load the bpmnResource:
        IPath bpmnPath =
            diagramFile.getFullPath()
                .removeFileExtension().addFileExtension("bpmn"); //$NON-NLS-1$
        
        if (ResourcesPlugin.getWorkspace().getRoot().findMember(bpmnPath) == null) {
            BpmnValidationPlugin.getDefault().getLog().log(
                    new Status(IStatus.WARNING,
                            BpmnValidationPlugin.PLUGIN_ID,
                            IStatus.WARNING,
                            BpmnValidationMessages.bind(
                            		BpmnValidationMessages.BatchValidationBuilder_diagram_has_no_model,
                            		diagramFile.getFullPath().toPortableString(), 
                            		bpmnPath), null));
            return;
        }
        
        URI uriBpmn = URI.createURI("platform:/resource" + //$NON-NLS-1$
                diagramFile.getFullPath().removeFileExtension().toPortableString()
                + ".bpmn"); //$NON-NLS-1$
        
        URI uriBpmnDiagram = URI.createURI("platform:/resource" + //$NON-NLS-1$
                diagramFile.getFullPath().toPortableString());
        
        Resource resBpmn = resourceSet.createResource(uriBpmn);
        Resource resBpmnDiag = resourceSet.createResource(uriBpmnDiagram);
        
        resBpmnDiag.load(GMFResourceFactory.getDefaultLoadOptions());
        resBpmn.load(GMFResourceFactory.getDefaultLoadOptions());
        
        Collection<EObject> selectedEObjects = resBpmnDiag.getContents();
        if (selectedEObjects.isEmpty()) {
            BpmnValidationPlugin.getDefault().getLog().log(
                    new Status(IStatus.WARNING,
                    		BpmnValidationPlugin.PLUGIN_ID,
                    		IStatus.WARNING,
                    		BpmnValidationMessages.bind(
                    				BpmnValidationMessages.BatchValidationBuilder_diagram_is_empty, 
                    				diagramFile.getFullPath().toPortableString()), 
                    				null));
            return;
        }
        monitor.setTaskName(BpmnValidationMessages.bind(
        		BpmnValidationMessages.BatchValidationBuilder_taskValidateDiagram, 
        		diagramFile.getFullPath().toPortableString()));
        for (EObject eobj : selectedEObjects) {
            if (eobj instanceof Diagram) {
                final Diagram diagram = (Diagram)eobj;
                try {
                    BpmnValidationProvider.ValidateAction.runValidation(diagram);
                } catch (Exception e) {
                    if (BpmnValidationPlugin.getDefault() != null) {
                    BpmnValidationPlugin.getDefault().getLog().log(
                            new Status(IStatus.ERROR,
                                    BpmnValidationPlugin.PLUGIN_ID,
                                    IStatus.ERROR,
                                    BpmnValidationMessages.BatchValidationBuilder_taskValidateActionFailed, e));
                    } else {
                        e.printStackTrace();
                    }
                }
            } else {
            }
        }
        
        monitor.done();
        
    }

}
