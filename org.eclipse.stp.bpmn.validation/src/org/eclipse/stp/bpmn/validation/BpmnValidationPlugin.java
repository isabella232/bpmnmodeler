/******************************************************************************
 * Copyright (c) 2006, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.stp.bpmn.validation;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.Map.Entry;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ISaveContext;
import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.stp.bpmn.validation.builder.BatchValidationBuildAbleNature;
import org.eclipse.stp.bpmn.validation.builder.BatchValidationBuilder;
import org.eclipse.stp.bpmn.validation.builder.ResourceImportersRegistry;
import org.eclipse.stp.bpmn.validation.builder.ValidationMarkerCustomAttributes;
import org.eclipse.stp.bpmn.validation.quickfixes.IBpmnMarkerResolutionProvider;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;

/**
 * The Bpmn Diagram Editor activator.
 */
public class BpmnValidationPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.stp.bpmn.validation"; //$NON-NLS-1$

    public static final String BATCH_VALIDATION_BUILD_ABLE_NATURE_ID =
        BatchValidationBuildAbleNature.NATURE_ID;
    
    /** category of imports managed by the batch validation builder */
    public static final String GENERIC_FILE_IMPORT_INDEX_CATEGORY_ID = 
        BatchValidationBuilder.GENERIC_FILE_IMPORT_INDEX_CATEGORY_ID;
    
    public static final String CREATION_MARKER_HOOK_EXTENSION_POINT = PLUGIN_ID + 
        ".ValidationMarkerCreationHook";//$NON-NLS-1$
    
    public static Map<String,Object> createMarkerAttributesMap(Diagnostic diagnostic) {
        return ValidationMarkerCustomAttributes.createMarkerAttributesMap(diagnostic);
    }
    
    /**
     * Cast or wraps an IConstraintStatus into an IConstraintStatusEx
     * @param st
     * @param bpmnQuickfixResolutionID The id of the bpmn resolution
     * provider that will be set on the custom attributes. null if no such thing
     * is to be done.
     * @return The same object or the same object wrapped or null
     * if it was not an IConstraintStatus.
     */
    public static IConstraintStatusEx asConstraintStatusEx(IStatus st, String bpmnQuickfixResolutionID) {
        IConstraintStatusEx cons = null;
        if (st instanceof IConstraintStatusEx) {
            cons = (IConstraintStatusEx)st;
        } else if (st instanceof IConstraintStatus) {
            cons = new ConstraintStatusEx((IConstraintStatus)st);
        } else {
            return null;//we should no be here really.
        }
        if (bpmnQuickfixResolutionID != null) {
            cons.addMarkerAttribute(
                    IBpmnMarkerResolutionProvider.MARKER_ATTRIBUTE_BPMN_QUICK_FIXABLE,
                    Boolean.TRUE.toString());
            cons.addMarkerAttribute(
                    IBpmnMarkerResolutionProvider.MARKER_ATTRIBUTE_QUICK_FIX_RESOLUTION_ID,
                    bpmnQuickfixResolutionID);
        }
        return cons;
    }
    
    /**
     * Creates a new IConstraintStatusEx
     * @param ctxt
     * @param messageArguments
     * @return
     */
    public static IConstraintStatusEx createFailureStatus(IValidationContext ctxt,
            EObject target, Object[] messageArguments) {
        IStatus st = ctxt.createFailureStatus(messageArguments);
        IConstraintStatusEx cons = asConstraintStatusEx(st, null);
        if (target != null) {
            ((ConstraintStatusEx)cons).setTarget(target);
        }
        return cons;
    }
    
    /**
     * Helper method used during the execution of the validation of the diagram.
     * Adds the markers attributes that BpmnQuickfixes requires
     * Calls the abstract method where attributes specific to this
     * particular issue are created.
     * 
     * @param constrainedStatus
     */
    
    // The shared instance
	private static BpmnValidationPlugin plugin;
	
    //the cache of resource importers index
    private static WeakHashMap<IProject, Map<String,IResourceImportersRegistry>> CACHE = null;
        
    
    //in charge of saving the resource importers index
    private ISaveParticipant _saveParticipant;
    private Set<IValidationMarkerCreationHook> _creationMarkerCallBacks;
    
	/**
	 * The constructor
	 */
	public BpmnValidationPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
        
		// start the CACHE
		CACHE = new WeakHashMap<IProject, Map<String,IResourceImportersRegistry>>();
        //trying to save the index of imports.
        //not sure this is working.
        //it is saved at the end of each build when it is modified anyways.
        _saveParticipant = new ISaveParticipant() {
            public void doneSaving(ISaveContext context) {
            }

            public void prepareToSave(ISaveContext context) throws CoreException {
            }

            public void rollback(ISaveContext context) {
            }

            public void saving(ISaveContext context) throws CoreException {
            	IProject[] projects = context.getProject() == null
            			? ResourcesPlugin.getWorkspace().getRoot().getProjects()
            			: new IProject[] {context.getProject()};
            	
            	for (IProject proj : projects) {
            		saveImportersRegistries(proj);
            	}
            }
        };
        ResourcesPlugin.getWorkspace().addSaveParticipant(this, _saveParticipant);
        
        if (PlatformUI.isWorkbenchRunning()) {
        //this is a workaround for the stack trace documented below:
        //we are careful to do the work outside of this class so that the loading
        //of this class does not provoke the loading of the gmf.common.ui plugin
        //that would crash in headless mode.
        org.eclipse.stp.bpmn.validation.internal.InternalHelper.loadAndDisposeUIModificationValidator();
        //here is the stack trace when we notice the first validation via the builder takes a verylong time:
        /*
!ENTRY org.eclipse.osgi 4 0 2006-12-18 14:29:22.810
!MESSAGE While loading class "org.eclipse.gmf.runtime.common.ui.util.UIModificationValidator$WindowListener", thread "main" timed out waiting (5000ms) for thread "Worker-0" to finish starting bundle "org.eclipse.gmf.runtime.common.ui". To avoid deadlock, thread "main" is proceeding but "org.eclipse.gmf.runtime.common.ui.util.UIModificationValidator$WindowListener" may not be fully initialized.
!STACK 0
java.lang.Exception: Generated exception.
    at org.eclipse.core.runtime.internal.adaptor.EclipseLazyStarter.preFindLocalClass(EclipseLazyStarter.java:75)
    at org.eclipse.osgi.baseadaptor.loader.ClasspathManager.findLocalClass(ClasspathManager.java:409)
    at org.eclipse.osgi.internal.baseadaptor.DefaultClassLoader.findLocalClass(DefaultClassLoader.java:188)
    at org.eclipse.osgi.framework.internal.core.BundleLoader.findLocalClass(BundleLoader.java:339)
    at org.eclipse.osgi.framework.internal.core.BundleLoader.findClass(BundleLoader.java:391)
    at org.eclipse.osgi.framework.internal.core.BundleLoader.findClass(BundleLoader.java:352)
    at org.eclipse.osgi.internal.baseadaptor.DefaultClassLoader.loadClass(DefaultClassLoader.java:83)
    at java.lang.ClassLoader.loadClass(Unknown Source)
    at java.lang.ClassLoader.loadClassInternal(Unknown Source)
    at org.eclipse.gmf.runtime.common.ui.util.UIModificationValidator$1.run(UIModificationValidator.java:115)

	    */
        
        }
    }

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
        CACHE = null;
        ResourcesPlugin.getWorkspace().removeSaveParticipant(this);
		super.stop(context);
	}
	
	/**
	 * @param proj Save the importers registries for a given project.
	 */
	public void saveImportersRegistries(IProject proj) {
		if (!proj.isAccessible()) {
			return;
		}
        Map<String,IResourceImportersRegistry> importIndexes =
            CACHE.get(proj);
        
        if (importIndexes != null) {
            for (Entry<String,IResourceImportersRegistry> entry : 
                        importIndexes.entrySet()) {
            	if (entry.getValue().isDirty()) {
            		try {
						entry.getValue().save(
						    proj, entry.getKey(),
						    new NullProgressMonitor());
					} catch (CoreException e) {
						getLog().log(e.getStatus());
					}
            	}
            }
        }
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static BpmnValidationPlugin getDefault() {
        return plugin;
	}
 
    /**
     * @param project The project for which the index of importers is needed
     * @param importersCategoryID The id of the type of imports.
     * @param monitor
     * @return The resource importers index for this project
     */
	public static synchronized IResourceImportersRegistry
	        getResourceImportersRegistry(IProject project,
                    String importersCategoryID,
                    IProgressMonitor monitor) {
	    if (CACHE == null) { // the bundle is now stopped.
	        return null;
	    }
        Map<String,IResourceImportersRegistry> importIndexes = CACHE.get(project);
        
        if (importIndexes == null) {
            importIndexes = new HashMap<String, IResourceImportersRegistry>();
            CACHE.put(project, importIndexes);
        }
	    IResourceImportersRegistry res = importIndexes.get(importersCategoryID);
	    if (res != null) {
	        return res;
	    }
	    res = new ResourceImportersRegistry();
	    res.load(project, importersCategoryID, monitor);
        importIndexes.put(importersCategoryID, res);
	    return res;
	}

	/**
	 * @return the validation hooks for marker creation
	 */
	public Collection<IValidationMarkerCreationHook> getCreationMarkerCallBacks() {
	    if (_creationMarkerCallBacks == null) {
	        _creationMarkerCallBacks = new HashSet<IValidationMarkerCreationHook>();
	        IConfigurationElement[] configElems = Platform.getExtensionRegistry()
	        .getConfigurationElementsFor(CREATION_MARKER_HOOK_EXTENSION_POINT);
	        for (int j = configElems.length-1; j>=0; j--) {
	            try {
	                IConfigurationElement el = configElems[j];
	                _creationMarkerCallBacks.add((IValidationMarkerCreationHook)
	                        el.createExecutableExtension("class")); //$NON-NLS-1$
	            } catch (Exception e) {
	                getLog().log(new Status(IStatus.ERROR, PLUGIN_ID, 0, 
	                        e.getMessage(), e));
	            }
	        }
	    }
	    return _creationMarkerCallBacks;
	}
	
}
