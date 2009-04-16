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

/** 
 * Date         	Author             Changes 
 * Sep 19, 2006   	mpeleshchyshyn  	Created 
 */
package org.eclipse.stp.bpmn.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableCompartmentEditPolicy;
import org.eclipse.stp.bpmn.handles.CompartmentCollapseHandleEx;

/**
 * Edit policy for subprocess body compartment (creates new compartment collapse
 * handle).
 * 
 * @author mpeleshchyshyn
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ResizableCompartmentEditPolicyEx extends
        ResizableCompartmentEditPolicy {

    /**
     * This method is used to get the collapse handle(s). Subclasses can
     * override to provide different collapse handles
     * 
     * @return a list of collapse handles
     */
    protected List createCollapseHandles() {
        IGraphicalEditPart part = (IGraphicalEditPart) getHost();

        List collapseHandles = new ArrayList();
        collapseHandles.add(new CompartmentCollapseHandleEx(part));
        return collapseHandles;
    }
}
