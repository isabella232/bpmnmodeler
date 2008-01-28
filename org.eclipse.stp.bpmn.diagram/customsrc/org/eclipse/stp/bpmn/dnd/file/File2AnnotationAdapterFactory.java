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
 * Nov 28, 2006      Antoine Toulm�   Creation
 */
package org.eclipse.stp.bpmn.dnd.file;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.stp.bpmn.dnd.IDnDHandler;

/**
 * This adapter factory adapts IFile objects into EAnnotations.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class File2AnnotationAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked") //$NON-NLS-1$
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType.equals(IDnDHandler.class)) {
			if (adaptableObject instanceof IResource) {
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
