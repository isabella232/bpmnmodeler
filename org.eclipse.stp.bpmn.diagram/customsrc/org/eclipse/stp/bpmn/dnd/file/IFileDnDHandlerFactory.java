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
 * Date         Author             Changes
 * Sep 4, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.dnd.file;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;

/**
 * The interface defining factories that may override the default FileDnDHandler
 * through the FileDnDHandlers extension point.
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public interface IFileDnDHandlerFactory extends IAdapterFactory {

    /**
     * @param resource
     * @return the priority of this factory for this particular resource
     */
    public int getPriority(IResource resource);
    
    /**
     * @param resource
     * @return true if the factories with a lesser priority should be polled.
     */
    public boolean keepIteratingIfNull(IResource resource);

    /**
     * @param resource
     * @return true if a FileDnDHandler default instance should be returned
     * in case this factory returns null.
     * 
     * If this method returns true, the next factories won't be polled.
     */
    public boolean useDefaultIfNull(IResource resource);
    
    /**
     * @param resource
     * @return true if the factory should register as an interested party
     * and be part of the factories over which we will iterate.
     */
    public boolean registers(IResource resource);
    
}
