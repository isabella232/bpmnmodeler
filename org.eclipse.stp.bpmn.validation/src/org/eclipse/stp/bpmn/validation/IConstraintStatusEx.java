/******************************************************************************
 * Copyright (c) 2006-2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.stp.bpmn.validation;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.model.IConstraintStatus;

/**
 * The result of the evaluation of a model constrain.
 * Can carry information that will be used if an {@link IMarker} is generated
 * from this object.
 * The extra attributes added to the marker are used for quickfixes and other
 * parts of eclipse.
 * The value of the attributes must be a String, an Integer or a Boolean
 * as imposed by the restriction on the type of the IMarker's attributes.
 * <p>
 * An implementation is provided as {@link ConstraintStatusEx}.
 * </p>
 * 
 * @author hmalphettes
 * @author Intalio Inc
 */
public interface IConstraintStatusEx extends IConstraintStatus {
    
    /**
     * @param attributeName The name of the IMarker attribute
     * @param value The string value
     */
    public void addMarkerAttribute(String attributeName, String value);
    /**
     * @param attributeName The name of the IMarker attribute
     * @param value The integer value
     */
    public void addMarkerAttribute(String attributeName, int value);
    /**
     * @param attributeName The name of the IMarker attribute
     * @param value The boolean value
     */
    public void addMarkerAttribute(String attributeName, boolean value);
    
    /**
     * @return The map of the attributes for the marker generated from this IConstraintStatus
     */
    public Map<String,Object> getMarkerCustomAttributes();
    
    /**
     * Ability to set this status from failure to success.
     * @param statusCode status code or OK.
     */
    public void setIsOK(int statusCode);
    
    /**
     * @param target the target to set
     */
    public void setTarget(EObject target);
    
    /**
     * Sets the main message
     * @param message The message
     */
    public void setMessage(String message);
    
    /**
     * @param cs Another constraintStatus that is interpreted as a marker during
     * the build.
     */
    public void addChildStatus(IConstraintStatusEx cs);
    
    /**
     * @param cses Other constraintStatuses that are interpreted as a markers
     * during the build.
     */
    public void addChildStatuses(Iterable<IConstraintStatusEx> cses);
}
