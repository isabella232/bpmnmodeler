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
package org.eclipse.stp.bpmn;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activity</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.Activity#getGroups <em>Groups</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.Activity#getActivityType <em>Activity Type</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.Activity#getEventHandlerFor <em>Event Handler For</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.Activity#getLane <em>Lane</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.Activity#isLooping <em>Looping</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.stp.bpmn.BpmnPackage#getActivity()
 * @model extendedMetaData="name='Activity' kind='elementOnly'"
 * @generated
 */
public interface Activity extends Vertex, MessageVertex {
    /**
     * Returns the value of the '<em><b>Groups</b></em>' reference list.
     * The list contents are of type {@link org.eclipse.stp.bpmn.Group}.
     * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.Group#getActivities <em>Activities</em>}'.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Groups</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Groups</em>' reference list.
     * @see org.eclipse.stp.bpmn.BpmnPackage#getActivity_Groups()
     * @see org.eclipse.stp.bpmn.Group#getActivities
     * @model opposite="activities"
     *        extendedMetaData="kind='element' name='groups'"
     * @generated
     */
	EList<Group> getGroups();

    /**
     * Returns the value of the '<em><b>Activity Type</b></em>' attribute.
     * The default value is <code>"Task"</code>.
     * The literals are from the enumeration {@link org.eclipse.stp.bpmn.ActivityType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Activity Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Activity Type</em>' attribute.
     * @see org.eclipse.stp.bpmn.ActivityType
     * @see #isSetActivityType()
     * @see #unsetActivityType()
     * @see #setActivityType(ActivityType)
     * @see org.eclipse.stp.bpmn.BpmnPackage#getActivity_ActivityType()
     * @model default="Task" unsettable="true"
     *        extendedMetaData="kind='attribute' name='activityType'"
     * @generated
     */
    ActivityType getActivityType();

    /**
     * Sets the value of the '{@link org.eclipse.stp.bpmn.Activity#getActivityType <em>Activity Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activity Type</em>' attribute.
     * @see org.eclipse.stp.bpmn.ActivityType
     * @see #isSetActivityType()
     * @see #unsetActivityType()
     * @see #getActivityType()
     * @generated
     */
    void setActivityType(ActivityType value);

    /**
     * Unsets the value of the '{@link org.eclipse.stp.bpmn.Activity#getActivityType <em>Activity Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetActivityType()
     * @see #getActivityType()
     * @see #setActivityType(ActivityType)
     * @generated
     */
    void unsetActivityType();

    /**
     * Returns whether the value of the '{@link org.eclipse.stp.bpmn.Activity#getActivityType <em>Activity Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Activity Type</em>' attribute is set.
     * @see #unsetActivityType()
     * @see #getActivityType()
     * @see #setActivityType(ActivityType)
     * @generated
     */
    boolean isSetActivityType();

    /**
     * Returns the value of the '<em><b>Event Handler For</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.SubProcess#getEventHandlers <em>Event Handlers</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Event Handler For</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Event Handler For</em>' container reference.
     * @see #setEventHandlerFor(SubProcess)
     * @see org.eclipse.stp.bpmn.BpmnPackage#getActivity_EventHandlerFor()
     * @see org.eclipse.stp.bpmn.SubProcess#getEventHandlers
     * @model opposite="eventHandlers" transient="false"
     *        extendedMetaData="kind='attribute' name='eventHandlerFor'"
     * @generated
     */
    SubProcess getEventHandlerFor();

    /**
     * Sets the value of the '{@link org.eclipse.stp.bpmn.Activity#getEventHandlerFor <em>Event Handler For</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Event Handler For</em>' container reference.
     * @see #getEventHandlerFor()
     * @generated
     */
    void setEventHandlerFor(SubProcess value);

    /**
     * Returns the value of the '<em><b>Lane</b></em>' reference.
     * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.Lane#getActivities <em>Activities</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Lane</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Lane</em>' reference.
     * @see #setLane(Lane)
     * @see org.eclipse.stp.bpmn.BpmnPackage#getActivity_Lane()
     * @see org.eclipse.stp.bpmn.Lane#getActivities
     * @model opposite="activities"
     *        extendedMetaData="kind='attribute' name='lane'"
     * @generated
     */
    Lane getLane();

    /**
     * Sets the value of the '{@link org.eclipse.stp.bpmn.Activity#getLane <em>Lane</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Lane</em>' reference.
     * @see #getLane()
     * @generated
     */
    void setLane(Lane value);

    /**
     * Returns the value of the '<em><b>Looping</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Looping</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Looping</em>' attribute.
     * @see #isSetLooping()
     * @see #unsetLooping()
     * @see #setLooping(boolean)
     * @see org.eclipse.stp.bpmn.BpmnPackage#getActivity_Looping()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='looping'"
     * @generated
     */
    boolean isLooping();

    /**
     * Sets the value of the '{@link org.eclipse.stp.bpmn.Activity#isLooping <em>Looping</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Looping</em>' attribute.
     * @see #isSetLooping()
     * @see #unsetLooping()
     * @see #isLooping()
     * @generated
     */
    void setLooping(boolean value);

    /**
     * Unsets the value of the '{@link org.eclipse.stp.bpmn.Activity#isLooping <em>Looping</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetLooping()
     * @see #isLooping()
     * @see #setLooping(boolean)
     * @generated
     */
    void unsetLooping();

    /**
     * Returns whether the value of the '{@link org.eclipse.stp.bpmn.Activity#isLooping <em>Looping</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Looping</em>' attribute is set.
     * @see #unsetLooping()
     * @see #isLooping()
     * @see #setLooping(boolean)
     * @generated
     */
    boolean isSetLooping();

} // Activity