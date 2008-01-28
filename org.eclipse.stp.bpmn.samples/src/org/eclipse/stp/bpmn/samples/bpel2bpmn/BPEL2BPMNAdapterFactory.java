/******************************************************************************
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Dates       		 Author              Changes
 * Jan 22, 2007      Antoine Toulm�   Creation
 */
package org.eclipse.stp.bpmn.samples.bpel2bpmn;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.stp.bpmn.dnd.IDnDHandler;

/**
 * This adapter factory is creating the DnD handler
 * that generates BPMN shapes out of a BPEL file.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BPEL2BPMNAdapterFactory implements IAdapterFactory {

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType.equals(IDnDHandler.class)) {
			if (adaptableObject instanceof IFile && 
					((IFile) adaptableObject).getFileExtension() != null &&
				((IFile) adaptableObject).getFileExtension().equals("bpel")) {
				return new BPELGenerationDnDHandler((IFile) adaptableObject);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	public Class[] getAdapterList() {
		return new Class[] { IDnDHandler.class };
	}

}
