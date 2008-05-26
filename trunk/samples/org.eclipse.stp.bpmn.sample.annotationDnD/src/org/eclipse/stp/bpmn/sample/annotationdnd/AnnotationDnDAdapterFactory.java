/******************************************************************************
 * Copyright (c) 2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Date			Author					Changes
 * Feb 22, 2008	Antoine Toulme		Created
 */
package org.eclipse.stp.bpmn.sample.annotationdnd;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.stp.bpmn.dnd.IDnDHandler;

/**
 * The adapter factory that produces the DnDHandler.
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class AnnotationDnDAdapterFactory implements IAdapterFactory {

    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
     */
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        System.err.println("wowow " + adaptableObject + " " + adapterType); //REMOVE
        if (adapterType.equals(IDnDHandler.class)) {
            if (adaptableObject instanceof IFile && 
                    ((IFile) adaptableObject).getFileExtension() != null &&
                ((IFile) adaptableObject).getFileExtension().equals("txt")) {
                System.err.println("returning"); //REMOVE
                return new AnnotationDnDHandler((IFile) adaptableObject);
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
     */
    public Class[] getAdapterList() {
        return new Class[] {IDnDHandler.class};
    }

}
