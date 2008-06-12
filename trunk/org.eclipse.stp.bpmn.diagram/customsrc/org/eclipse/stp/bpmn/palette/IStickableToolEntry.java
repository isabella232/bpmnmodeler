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

package org.eclipse.stp.bpmn.palette;

/**
 * @author hmalphettes Support for setting the unloadWhenFinished
 * on the Tool returned by the ToolEntry#createTool() method.
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public interface IStickableToolEntry {

    /**
     * @return true if in sticky mode
     * where the tool created is not unloaded once it 
     * has been used.
     */
    public boolean isSticky();
    
    /**
     * @param isSticky True if in sticky mode
     * where the tool created is not unloaded once it 
     * has been used.
     */
    public void setIsSticky(boolean isSticky);
    
}
