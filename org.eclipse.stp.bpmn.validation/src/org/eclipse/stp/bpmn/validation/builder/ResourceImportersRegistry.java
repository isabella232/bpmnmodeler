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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.stp.bpmn.validation.BpmnValidationPlugin;
import org.eclipse.stp.bpmn.validation.IResourceImportersRegistry;
import org.eclipse.stp.bpmn.validation.IResourceImportersRegistry.ITransitiveImporters;

/**
 * Static classes that enables registring that a diagram misses a resource (file/folder) it
 * depends on or requires it. The BatchValidationBuilder will watch the addition/removal
 * of those files and will make sure that the diagram file is built when that occurrs.
 * <p>
 * Used by the FileExistenceConstraint. Can be re-used by other client applications.
 * A Graph of dependencies can be built from this.
 * A topological sort can also be created out of this once the cycles are taken care of.
 * </p>
 * <p>
 * Persisted for each project.
 * </p>
 * @author hmalphettes
 * @author Intalio Inc
 */
public class ResourceImportersRegistry implements IResourceImportersRegistry {
    
	/**
	 * the thread-safe listener list that is used to contain
	 * the listeners over this registry.
	 */
	public ListenerList changeListeners = new ListenerList();
	
    /** separator character between 2 projects relative paths. */
    private static String SEPARATOR = ":"; //$NON-NLS-1$
    
    private boolean _isDirty;
        
    /**
     * the files that are depending on other files.
     * the value of the map is the set of those files.
     * The importer file always exist.
     */
    private Map<IResource,Set<IResource>> _importsIndexedByImporters =
        new LinkedHashMap<IResource, Set<IResource>>();
    
    /**
     * the files that are imported by other files.
     * the value of the map is the list of file importing the key.
     * the imported files might or might not exist.
     * <br/>
     * the reverse of _importsIndexedByImporters.
     */
    private Map<IResource,Set<IResource>> _importersIndexedByImports =
        new LinkedHashMap<IResource, Set<IResource>>();
    
    /**
     * @param importer The resource for which dependencies are registered.
     * @return The collection of resources registered as a dependency.
     * The resource might or might not exist.
     */
    public Set<IResource> getImports(IResource importer) {
        return _importsIndexedByImporters.get(importer);
    }
    
    /**
     * @param resource The resource that is a potential dependency
     * @return The collection of resources interested in this.
     */
    public Set<IResource> getImporters(IResource imported) {
        return _importersIndexedByImports.get(imported);
    }

    /**
     * Note: there is no addImporter: it is always the importer file that decides what are
     * the imports.
     * @param importer The importer the file that depends on the imported
     * @param imported The imported (might or might not exist).
     */
    public void addImport(IResource importer, IResource imported) {
        Set<IResource> imports = _importsIndexedByImporters.get(importer);
        if (imports == null) {
            imports = new LinkedHashSet<IResource>();
            _importsIndexedByImporters.put(importer, imports);
        }
        imports.add(imported);
        
        //and the other index:
        Set<IResource> importers = _importersIndexedByImports.get(imported);
        if (importers == null) {
            importers = new LinkedHashSet<IResource>();
            _importersIndexedByImports.put(imported, importers);
        }
        importers.add(importer);
        _isDirty = true;
        // fire an event to notify all listeners of the change
        fireImportAdded(importer, imported);
    }
    
    /**
     * Remove this object from the imports index:
     * Unregister all the imports for this importer
     * <p>
     * This is called when an importer resource is built.
     * </p>
     * Note: there is no such things as a clearImporters.
     * 
     * @param importer
     */
    public void clearImports(IResource importer) {
        Set<IResource> imports = _importsIndexedByImporters.remove(importer);
        if (imports != null) {
            for (IResource imported : imports) {
                Set<IResource> importers = _importersIndexedByImports.get(imported);
                if (importers != null) {
                    importers.remove(importer);
                }
            }
        }
        _isDirty = true;
        fireImportsCleared(importer, imports);
    }
    
   /**
    * Load the state of the dependencies for a given import category ID
    */
    public void load(IProject project, String importCategoryID, IProgressMonitor monitor) {
        clearAll();
        IFile dagFile = (IFile)project.findMember(getStorageLocation(importCategoryID));
        if (dagFile == null) {
            return;//nothing to load.
        }
        InputStream contents = null;
        try {
            contents = dagFile.getContents();
            //the file is a properties file.
            Properties props = new Properties();
            props.load(contents);
            for (Entry<Object,Object> kvp : props.entrySet()) {
                Set<IResource> imports = new LinkedHashSet<IResource>();
                String simporter = (String)kvp.getKey();
                String simports = (String)kvp.getValue();
                IResource importer = project.findMember(new Path(simporter));
                if (importer == null) {
                    continue;//importer does not exist, discard.
                }
                StringTokenizer tokenizer =
                    new StringTokenizer(simports, String.valueOf(SEPARATOR), false);
                while (tokenizer.hasMoreTokens()) {
                	String spath = tokenizer.nextToken();
                    Path path = new Path(spath);
                    //it does not matter if the resource imported exists or not:
                    IResource imported = spath.endsWith("/") //$NON-NLS-1$
                    		? project.getFolder(spath) : project.getFile(path);
                    imports.add(imported);
                    
                    //and the mirror index:
                    Set<IResource> importers = _importersIndexedByImports.get(imported);
                    if (importers == null) {
                        importers = new LinkedHashSet<IResource>();
                        _importersIndexedByImports.put(imported, importers);
                    }
                    importers.add(importer);
                }
                _importsIndexedByImporters.put(importer, imports);
            }
            
        } catch (Throwable t) {
            //mark this object as a clean build required?
        	t.printStackTrace();
        } finally {
            if (contents != null) {
                try {
                    contents.close();
                } catch (IOException e) {
                }
            }
        }
    }
    
   /**
    * Save this object: just serialize the _importsIndexedByImporters as properties file
    * @param project
    * @param importsCategory The id of the type of imports Imports of the same category
    * are saved and built at the same time.
    * @throws CoreException 
    */
   public void save(IProject project, String importsCategory,
               IProgressMonitor monitor) throws CoreException {
	   //keep things in the same order.
        Properties props = new Properties() {
        	private static final long serialVersionUID = 1L;
        	private LinkedHashMap<Object, Object> _orderedKeys = new LinkedHashMap<Object, Object>();
        	@Override
        	public Object put(Object key, Object value) {
        		return _orderedKeys.put(key, value);
        	}
        	@Override
        	public Object get(Object key) {
        		return _orderedKeys.get(key);
        	}
        	@SuppressWarnings("unchecked")
        	@Override
        	public Enumeration<Object> keys() {
        		return new IteratorWrapper(_orderedKeys.keySet().iterator());
        	}
        	@Override
        	public Set<Entry<Object,Object>> entrySet() {
        		return (Set<Entry<Object,Object>>) _orderedKeys.entrySet();
        	}
        };
        for (Entry<IResource, Set<IResource>> entry : _importsIndexedByImporters.entrySet()) {
            Set<IResource> imports = entry.getValue();
            if (!imports.isEmpty()) {
                Iterator<IResource> it = imports.iterator();
                IResource r = null;
                StringBuilder buf = null;
                while (r == null && it.hasNext()) {
                    r = it.next();
                    IPath rPath = r.getProjectRelativePath();
                    if (rPath != null) {
                        buf = new StringBuilder();
                        buf.append(r.getProjectRelativePath().toString());
                        if (r.getType() == IResource.FOLDER) {
                        	//EDGE-3097: make sure we can make the difference
                        	//between folders and files.
                        	buf.append(IPath.SEPARATOR);
                        }
                        break;
                    }
                }
                if (buf != null) {
                    while (it.hasNext()) {
                        IPath rpath = it.next().getProjectRelativePath();
                        if (rpath != null) {
                            buf.append(SEPARATOR + rpath.toString());
                        }
                    }
                    props.put(entry.getKey().getProjectRelativePath().toString(),
                            buf.toString());
                }
            }
        }
        InputStream closeMe = null;
        try {
            //StringWriter writer = new StringWriter();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            String topComment = "# index of importer -> set(imports)\n"; //$NON-NLS-1$
            props.store(out, null); //$NON-NLS-1$
            
            //now remove the timestamp that java inserted at the top:
            //latin1 is the encoding for properties.
            String content = out.toString("ISO-8859-1"); //$NON-NLS-1$
            int index = content.indexOf('\n');
            int index2 = content.indexOf('\r');
            if (index2 != -1 && index2 < index) {
            	index = index2;
            }
            if (content.charAt(0) == '#' && index != -1) {
            	content = topComment + content.substring(index+1);
            } else {
            	content = topComment + content.substring(index+1);
            }
            
            IFile fileHandle = project.getFile(getStorageLocation(importsCategory));
            
            closeMe = new ByteArrayInputStream(/*out.toByteArray()*/content.getBytes("ISO-8859-1")); //$NON-NLS-1$
            if (!fileHandle.exists()) {
                if (!fileHandle.getParent().exists() &&
                        fileHandle.getParent() instanceof IFolder) {
                    //creates the .settings folder
                    ((IFolder)fileHandle.getParent()).create(true, true, monitor);
                }
                fileHandle.create(closeMe, true, monitor);
            } else {
                fileHandle.setContents(closeMe, true, false, monitor);
            }
        } catch (IOException e) {
            throw new CoreException(new Status(IStatus.WARNING,
                    BpmnValidationPlugin.PLUGIN_ID, IStatus.WARNING,
                    "Unable to write ", e)); //$NON-NLS-1$
        } finally {
            _isDirty = false;
            if (closeMe != null) {
                try {
                    closeMe.close();
                } catch (IOException e) {
                }
            }
        }
    }
   
   protected IPath getStorageLocation(String categoryId) {
       return new Path(".settings/" + categoryId + ".properties"); //$NON-NLS-1$ //$NON-NLS-2$
   }

    /**
     * Called when a project is cleaned
     */
    public void clearAll() {
    	// removing explicitly each importer 
    	// to have all the events fired properly
        // [hugues] please do make sure we don't get ConcurrentModificationsExceptions....
    	for (IResource importer : new ArrayList<IResource>(getAllImporters())) {
    		clearImports(importer);
    	}
    	// just to make sure that the maps are empty
        _importersIndexedByImports.clear();
        _importsIndexedByImporters.clear();
        _isDirty = true;
    }
    
    public boolean isDirty() {
        return _isDirty;
    }
    
    private void collectImporters(IResource current, ITransitiveImporters res) {
        if (res.getTransitiveImporters().add(current)) {
            for (IResource r : getImporters(current)) {
                collectImporters(r, res);
            }
        } else {
            res.getCycleIntroducers().add(current);
        }
    }
    
    /**
     * Compute the transitive chain of object importing each other.
     * @param imported
     * @return
     */
    public ITransitiveImporters computeTransitiveImporters(IResource imported) {
        TransitiveImporters res = new TransitiveImporters();
        collectImporters(imported, res);
        
        return res;
    }

    /**
     * @return the set of IResource objects that import
     * IResources referenced by this registry.
     */
    public Set<IResource> getAllImporters() {
        return _importsIndexedByImporters.keySet();
    }

    /**
	 * adds the listener passed as parameter from the 
	 * listener list associated with this registry.
	 * @param listener
	 */
	public void addListener(IResourceImportersRegistryListener listener) {
		changeListeners.add(listener);
	}

	/**
	 * removes the listener passed as parameter from the 
	 * listener list associated with this registry.
	 * @param listener
	 */
	public void removeListener(IResourceImportersRegistryListener listener) {
		changeListeners.remove(listener);
	}
    
	/**
	 * Fires an event to notify the listener that an import
	 * has been added to an importer.
	 * @param importer the importer on which an import is added
	 * @param imported the resource added to the dependencies
	 * of the importer.
	 */
	protected final void fireImportAdded(IResource importer, IResource imported) {
		for (Object listener : changeListeners.getListeners()) {
			if (listener instanceof IResourceImportersRegistryListener) {
				IResourceImportersRegistryListener resListener = 
					(IResourceImportersRegistryListener) listener;
				if (resListener.matches(importer)) {
					resListener.importAdded(importer, imported);
				}
			}
		}
	}
	
	/**
	 * Fires an event to notify the listener that all the imports
	 * of an importer have been cleared
	 * @param importer the importer which imports are cleared
	 * @param imported the set of resources that were previously
	 * associated as dependencies of the importer.
	 */
	protected final void fireImportsCleared(IResource importer, Set<IResource> imported) {
		for (Object listener : changeListeners.getListeners()) {
			if (listener instanceof IResourceImportersRegistryListener) {
				IResourceImportersRegistryListener resListener = 
					(IResourceImportersRegistryListener) listener;
				if (resListener.matches(importer)) {
					resListener.importsCleared(importer, imported);
				}
			}
		}
	}
}
/**
 * Just an object that contains the result of computing the importers transitively
 * TODO: code a real toplogical sort.
 * 
 * @author hmalphettes
 * @author Intalio Inc
 */
class TransitiveImporters implements ITransitiveImporters {
    
    private Set<IResource> topo = new HashSet<IResource>();
    private Set<IResource> cycleIntroducers = new HashSet<IResource>();
    public Set<IResource> getTransitiveImporters() {
        return topo;
    }
    public Set<IResource> getCycleIntroducers() {
        return cycleIntroducers;
    }
    
}
@SuppressWarnings("unchecked")
class IteratorWrapper implements Enumeration {
	Iterator iterator;

	public IteratorWrapper(Iterator iterator) {
		this.iterator = iterator;
	}

	public boolean hasMoreElements() {
		return iterator.hasNext();
	}

	public Object nextElement() {
		return iterator.next();
	}
}

