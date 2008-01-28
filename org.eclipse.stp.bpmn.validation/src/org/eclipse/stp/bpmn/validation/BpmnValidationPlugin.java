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

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.Map.Entry;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ISaveContext;
import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gmf.runtime.common.ui.util.UIModificationValidator;
import org.eclipse.stp.bpmn.validation.builder.BatchValidationBuildAbleNature;
import org.eclipse.stp.bpmn.validation.builder.BatchValidationBuilder;
import org.eclipse.stp.bpmn.validation.builder.ResourceImportersRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The Bpmn Diagram Editor activator.
 */
public class BpmnValidationPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.stp.bpmn.validation"; //$NON-NLS-1$

    public static final String BATCH_VALIDATION_BUILD_ABLE_NATURE_ID =
        BatchValidationBuildAbleNature.NATURE_ID;
    
    /** category of imports managed by the batch validation builder */
    public static String GENERIC_FILE_IMPORT_INDEX_CATEGORY_ID = 
        BatchValidationBuilder.GENERIC_FILE_IMPORT_INDEX_CATEGORY_ID;

    // The shared instance
	private static BpmnValidationPlugin plugin;
	
    //the cache of resource importers index
    private static WeakHashMap<IProject, Map<String,IResourceImportersRegistry>> CACHE = null;
        
    
    //in cahrge of saving the resource importers index
    private ISaveParticipant _saveParticiapnt;
    
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
        _saveParticiapnt = new ISaveParticipant() {
                    public void doneSaving(ISaveContext context) {
                    }

                    public void prepareToSave(ISaveContext context) throws CoreException {
                    }

                    public void rollback(ISaveContext context) {
                    }

                    public void saving(ISaveContext context) throws CoreException {
                        Map<String,IResourceImportersRegistry> importIndexes =
                            CACHE.get(context.getProject());
                        
                        if (importIndexes != null) {
                            for (Entry<String,IResourceImportersRegistry> entry : 
                                        importIndexes.entrySet()) {
                                entry.getValue().save(
                                        context.getProject(), entry.getKey(),
                                        new NullProgressMonitor());
                            }
                        }
                    }
        };
        ResourcesPlugin.getWorkspace().addSaveParticipant(this, _saveParticiapnt);
        
        
        //this is a workaround for the stack trace documented below:
        new UIModificationValidator().dispose();
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


}
