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
 * Mar 6, 2008	Antoine Toulme		Created
 */
package org.eclipse.stp.bpmn.sample.bugDnD.dnd;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.stp.bpmn.dnd.IDnDHandler;
import org.eclipse.stp.bpmn.sample.bugView.bug.view.IBug;

/**
 * The adapter factory that creates a handler to drop bugs on the diagram.
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
@SuppressWarnings("unchecked")
public class BugAdapterFactory implements IAdapterFactory {

    public Object getAdapter(Object adaptableObject, Class adapterType) {
        if (adaptableObject instanceof IBug && 
                IDnDHandler.class.equals(adapterType)) {
            return new BugDnDHandler((IBug) adaptableObject);
        }
        return null;
    }

    public Class[] getAdapterList() {
        return new Class[] {IDnDHandler.class};
    }

}
