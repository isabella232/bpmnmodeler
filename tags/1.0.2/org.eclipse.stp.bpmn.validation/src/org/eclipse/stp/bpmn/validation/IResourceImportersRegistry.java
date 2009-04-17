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
package org.eclipse.stp.bpmn.validation;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Registers dependencies between a resource and another resource.
 * Used by the builder to build a diagram file if a dependency of the diagram is modified.
 * <p>
 * The validation plugin is in charge of saving and loading the object associated to each 
 * project. The validation plugin requires a cateogry so the kind of imports handled by
 * different type of builders are kept separated.
 * </p>
 * <p>
 * This is used by the FileExistenceConstraint where the import of an attached file is 
 * stored and then inside the BatchValidationBuilder where a if a file attached to a diagram(s)
 * is removed or added the diagram(s) are re-built even when they were not part of the resource delta
 * of the incremental build.
 * </p>
 * @author hmalphettes
 * @author Intalio Inc
 */
public interface IResourceImportersRegistry {
    
    /**
     * @param importer The resource for which dependencies are registered.
     * @return The collection of resources registered as a dependency.
     * The resource might or might not exist.
     */
    public Set<IResource> getImports(IResource importer);
    
    /**
     * @return all the importer resources for the project
     * associated with this registry.
     */
    public Set<IResource> getAllImporters();
    
    /**
     * @param resource The resource that is a potential dependency
     * @return The collection of resources interested in this.
     */
    public Set<IResource> getImporters(IResource imported);

    /**
     * Note: there is no addImporter: it is always the importer file that decides what are
     * the imports.
     * @param importer The importer the file that depends on the imported
     * @param imported The imported (might or might not exist).
     */
    public void addImport(IResource importer, IResource imported);
    
    /**
     * Remove this object from the importers index:
     * Unregister all the imports for this importer
     * <p>
     * This is called when an importer resource is built.
     * </p>
     * Note: there is no such things as a clearImporters.
     * 
     * @param importer
     */
    public void clearImports(IResource importer);
    
    /**
     * Load this object: just read the _importsIndexedByImporters as properties file
     * @param project
     * @param importsCategory The id of the type of imports Imports of the same category
     * are saved and built at the same time.
     * @throws CoreException 
     */
    public void load(IProject project, String importsCategory, IProgressMonitor monitor);
    
    /**
     * Save this object: just serialize the _importsIndexedByImporters as properties file
     * @param project
     * @param importsCategory The id of the type of imports Imports of the same category
     * are saved and built at the same time.
     * @throws CoreException 
     */
    public void save(IProject project, String importsCategory,
            IProgressMonitor monitor) throws CoreException;

    /**
     * Called when a project is cleaned
     */
    public void clearAll();
    
    /**
     * @return true when a save is required
     */
    public boolean isDirty();
    
    /**
     * Compute the transitive chain of object importing each other.
     * @param imported
     * @return
     */
    public ITransitiveImporters computeTransitiveImporters(IResource imported);
    
    /**
	 * adds the listener passed as parameter from the 
	 * listener list associated with this registry.
	 * @param listener
	 */
	public void addListener(IResourceImportersRegistryListener listener);

	/**
	 * removes the listener passed as parameter from the 
	 * listener list associated with this registry.
	 * @param listener
	 */
	public void removeListener(IResourceImportersRegistryListener listener);

	/**
     * Just an object that contains the result of computing the importers transitively
     * 
     * @author hmalphettes
     * @author Intalio Inc
     */
    public interface ITransitiveImporters {
        Set<IResource> getTransitiveImporters();
        Set<IResource> getCycleIntroducers();
    }
        
    /**
	 * This class listens to the changes on the registry.
	 * 
	 * It can be listening to the changes of a particular importer
	 * by specifiying a particular behaviour in the matches method.
	 * 
	 * 
	 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
	 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
	 */
	public interface IResourceImportersRegistryListener {
		
		/**
		 * @param importer the importer that was just
		 * changed.
		 * @return true if the listener listens to 
		 * the changes regarding this importer.
		 */
		public boolean matches(IResource importer);
		
		/**
		 * Fires an event notifying the listener that an importer
		 * is adding an import.
		 * @param importer the modified importer
		 * @param imported the added import
		 */
		public void importAdded(IResource importer, IResource imported);
		
		/**
		 * Fires an event notfiying the listener that an importer
		 * is not importing anymore a resource
		 * @param importer the importer
		 * @param imported the resources that are not imported anymore
		 */
		public void importsCleared(IResource importer, Set<IResource> imported);
	}
}
