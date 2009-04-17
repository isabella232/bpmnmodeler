/******************************************************************************
 * Copyright (c) 2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.stp.bpmn.validation;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;

/**
 * Interface that is called back at the end of the creation of a marker.
 * Enable custom application to eventually add more attributes into the marker.
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author hmalphettes
 */
public interface IValidationMarkerCreationHook {

    /**
     * @param createdMarker The marker that was created.
     * @param poolName The name of the pool.
     */
    public void validationMarkerCreated(IMarker createdMarker, EObject bpmnObject);
}
