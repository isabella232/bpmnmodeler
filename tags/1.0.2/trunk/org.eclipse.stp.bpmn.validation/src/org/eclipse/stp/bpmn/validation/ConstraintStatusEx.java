/******************************************************************************
 * Copyright (c) 2006-2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *
 * Dates       		 Author              Changes
 * Jan 31, 2007      Antoine Toulme   Creation
 */
package org.eclipse.stp.bpmn.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.stp.bpmn.validation.builder.ValidationMarkerCustomAttributes;

/**
 * Implementation of an {@link IConstraintStatus} that expose a lot more of its
 * field.
 * Can carry information that will be used if an {@link IMarker} is generated
 * from this object.
 * The extra attributes added to the marker are used for quickfixes and other
 * parts of eclipse.
 * 
 * @author atoulme
 * @author Intalio Inc
 */
public class ConstraintStatusEx extends Status implements IConstraintStatusEx {

	private IModelConstraint constraint;
	private EObject target;
	
	private Set resultLocus;
	
	private Map<String,Object> customMarkerAttributes;
	
	private List<IConstraintStatusEx> childrenStatuses;

	/**
	 * Initializes me as a failure of the specified <code>constraint</code>
	 * with a <code>message</code> to be displayed somehow to the user.
	 * 
	 * @param constraint the constraint that failed
	 * @param target the target of the failed validation
	 * @param message the message describing the failure
	 * @param resultLocus the objects which caused the constraint to fail (at
	 *     least the original target should be among these).  May be
	 *     <code>null</code> if there really is no result locus
	 */
	public ConstraintStatusEx(IModelConstraint constraint,
							EObject target,
							String message,
							Set resultLocus) {
		this(
			constraint,
			target,
			constraint.getDescriptor().getSeverity().toIStatusSeverity(),
			constraint.getDescriptor().getStatusCode(),
			message,
			resultLocus);
	}

	/**
	 * Clones a constraintStatus to give access to its fields.
	 * @param status
	 */
	public ConstraintStatusEx(IConstraintStatus status) {
		this(status.getConstraint(),
				status.getTarget(),
				status.getSeverity(),
				status.getCode(),
				status.getMessage(),
				status.getResultLocus());
	}
	/**
	 * Initializes me as a successful execution of the specified
	 * <code>constraint</code>.
	 * 
	 * @param constraint the constraint that succeeded
	 * @param target the target of the successful validation
	 */
	public ConstraintStatusEx(IModelConstraint constraint, EObject target) {
		this(
			constraint,
			target,
			IStatus.OK,
			IModelConstraint.STATUS_CODE_SUCCESS,
			EMFModelValidationStatusCodes.CONSTRAINT_SUCCESS_MSG,
			null);
	}
	
	/**
	 * <p>
	 * Constructor that explicitly initializes all of my parts.
	 * </p>
	 * <p>
	 * This constructor should not be used outside of the validation framework.
	 * </p>
	 * 
	 * @param constraint the constraint that was evaluated
	 * @param target the object on which validation was performed
	 * @param status the status of the constraint evaluation
	 * @param code the error code (if the constraint failed)
	 * @param message the error message (if the constraint failed)
	 * @param resultLocus the result locus (if the constraint failed)
	 */
	public ConstraintStatusEx(IModelConstraint constraint,
							EObject target,
							int status,
							int code,
							String message,
							Set resultLocus) {
		super(status, constraint.getDescriptor().getPluginId(), code, message, null);
		
		assert constraint != null;
		assert target != null;
		assert message != null;
		
		this.constraint = constraint;
		this.target = target;
		
		// unmodifiable defensive copy
		this.resultLocus = (resultLocus != null)
			? Collections.unmodifiableSet(new java.util.HashSet(resultLocus))
			: Collections.EMPTY_SET;
	}

	/**
	 * Obtains the constraint which either succeeded or failed, according to
	 * what I have to say.
	 * 
	 * @return my constraint
	 */
	public final IModelConstraint getConstraint() {
		return constraint;
	}
	
	/**
	 * Obtains the target object, on which the constraint was evaluated.
	 * 
	 * @return the target of the validation operation
	 */
	public final EObject getTarget() {
		return target;
	}
	
	/**
	 * Obtains the objects which are involved in the failure of the constraint.
	 * These are objects which caused the constraint to fail, and would be
	 * useful to link to from some display of the error message.
	 * 
	 * @return the objects which caused the constraint to fail.  In cases of
	 *     successful validation, the result is an empty collection.  The result
	 *     is never <code>null</code>
	 */
	public final Set getResultLocus() {
		return resultLocus;
	}
	
	@Override
	public void setCode(int code) {
		super.setCode(code);
	}
	
	@Override
	public void setSeverity(int severity) {
		super.setSeverity(severity);
	}
	
    /**
     * Ability to set this status from failure to success.
     * @param statusCode status code or OK.
     */
    public void setIsOK(int statusCode) {
        super.setCode(statusCode);
    }
	
	@Override
	public void setException(Throwable exception) {
		super.setException(exception);
	}
	
	@Override
	public void setMessage(String message) {
		super.setMessage(message);
	}
	
	@Override
	public void setPlugin(String pluginId) {
		super.setPlugin(pluginId);
	}

	/**
	 * @param constraint the constraint to set
	 */
	public void setConstraint(IModelConstraint constraint) {
		this.constraint = constraint;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(EObject target) {
		this.target = target;
	}

	/**
	 * @param resultLocus the resultLocus to set
	 */
	public void setResultLocus(Set resultLocus) {
		this.resultLocus = resultLocus;
	}
	
    /**
     * @param attributeName The name of the IMarker attribute
     * @param value The string value
     */
    public void addMarkerAttribute(String attributeName, String value) {
        if (customMarkerAttributes == null) {
            customMarkerAttributes =
                ValidationMarkerCustomAttributes.createMarkerAttributesMap();
        }
        customMarkerAttributes.put(attributeName, value);
    }
    /**
     * @param attributeName The name of the IMarker attribute
     * @param value The integer value
     */
    public void addMarkerAttribute(String attributeName, int value) {
        if (customMarkerAttributes == null) {
            customMarkerAttributes =
                ValidationMarkerCustomAttributes.createMarkerAttributesMap();
        }
        customMarkerAttributes.put(attributeName, value);
    }
    /**
     * @param attributeName The name of the IMarker attribute
     * @param value The boolean value
     */
    public void addMarkerAttribute(String attributeName, boolean value) {
        if (customMarkerAttributes == null) {
            customMarkerAttributes =
                ValidationMarkerCustomAttributes.createMarkerAttributesMap();
        }
        customMarkerAttributes.put(attributeName, value);
    }

	
	/**
	 * @param customAttributes The attributes that will be placed in the IMarker
	 * generated by the batch builder.
	 */
	public void setMarkerCustomAttributes(Map<String,Object> customAttributes) {
	    this.customMarkerAttributes = customAttributes;
	}
	
	/**
	 * @return The attributes that will be placed in the IMarker
     * generated by the batch builder or null if no such thing.
	 */
	public Map<String,Object> getMarkerCustomAttributes() {
	    return this.customMarkerAttributes;
	}
	
	/**
	 * @return true if it is a multi-status.
	 */
	@Override
	public boolean isMultiStatus() {
	    return childrenStatuses != null;
	}
    /**
     * @param cs Another constraintStatus that is interpreted as a marker during
     * the build.
     */
    public void addChildStatus(IConstraintStatusEx cs) {
        if (childrenStatuses == null) {
            childrenStatuses = new ArrayList<IConstraintStatusEx>();
        }
        childrenStatuses.add(cs);
    }
    
    /**
     * @param cses Other constraintStatuses that are interpreted as a markers
     * during the build.
     */
    public void addChildStatuses(Iterable<IConstraintStatusEx> cses) {
        if (childrenStatuses == null) {
            childrenStatuses = new ArrayList<IConstraintStatusEx>();
        }
        for (IConstraintStatusEx cs : cses) {
            childrenStatuses.add(cs);
        }
    }
    
	/**
	 * Children statuses. Interpeted into markers.
	 */
	public IStatus[] getChildren() {
	    if (childrenStatuses == null) {
	        return super.getChildren();
	    }
	    return childrenStatuses.toArray(new IStatus[childrenStatuses.size()]);
	}
	
}
