/******************************************************************************
 * Copyright (c) 2006, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Dates       		 Author              Changes
 * Nov 28, 2006      Antoine Toulm&eacute;   Creation
 */
package org.eclipse.stp.bpmn.dnd.file;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.dnd.IDnDHandler;

/**
 * This adapter factory adapts IFile objects into EAnnotations.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class File2AnnotationAdapterFactory implements IAdapterFactory {

    
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType.equals(IDnDHandler.class)) {
			if (adaptableObject instanceof IResource) {
			    List<IFileDnDHandlerFactory> factories = 
			        BpmnDiagramEditorPlugin.getInstance().getFileDnDHandlerFactories();
			    List<IFileDnDHandlerFactory> filtered = new ArrayList<IFileDnDHandlerFactory>();
			    final IResource r = (IResource) adaptableObject;
			    for (IFileDnDHandlerFactory factory : factories) {
			        if (factory.registers(r)) {
			            filtered.add(factory);
			        }
			    }
			    Collections.sort(filtered, new Comparator<IFileDnDHandlerFactory>() {

                    public int compare(IFileDnDHandlerFactory o1,
                            IFileDnDHandlerFactory o2) {
                        if (o1.getPriority(r) < o2.getPriority(r)) {
                            return -1;
                        }
                        if (o1.getPriority(r) > o2.getPriority(r)) {
                            return 1;
                        }
                        return 0;
                    }});
			    
			    for (IFileDnDHandlerFactory factory : filtered) {
			        IDnDHandler handler = (IDnDHandler) factory.getAdapter(adaptableObject, adapterType);
			        if (handler == null) {
			            if (!factory.keepIteratingIfNull(r)) {
			                if (factory.useDefaultIfNull(r)) {
			                    break;
			                } else {
			                    return null;
			                }
			            }
			        } else {
			            return handler;
			        }
			    }
				return new FileDnDHandler((IResource) adaptableObject);
			}
		}
		return null;
	}

	
	/**
     * Returns the collection of adapter types handled by this
     * factory.
     * <p>
     * This method is generally used by an adapter manager
     * to discover which adapter types are supported, in advance
     * of dispatching any actual <code>getAdapter</code> requests.
     * </p>
     *
     * @return the collection of adapter types
     */
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	public Class[] getAdapterList() {
		return new Class[] {IDnDHandler.class};
	}
}
