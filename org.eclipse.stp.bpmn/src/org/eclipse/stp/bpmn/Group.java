/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.stp.bpmn;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Group</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.Group#getActivities <em>Activities</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.stp.bpmn.BpmnPackage#getGroup()
 * @model extendedMetaData="name='Group' kind='elementOnly'"
 * @generated
 */
public interface Group extends Artifact {
    /**
     * Returns the value of the '<em><b>Activities</b></em>' reference list.
     * The list contents are of type {@link org.eclipse.stp.bpmn.Activity}.
     * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.Activity#getGroups <em>Groups</em>}'.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Activities</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Activities</em>' reference list.
     * @see org.eclipse.stp.bpmn.BpmnPackage#getGroup_Activities()
     * @see org.eclipse.stp.bpmn.Activity#getGroups
     * @model opposite="groups" resolveProxies="false"
     *        extendedMetaData="kind='element' name='activities'"
     * @generated
     */
	EList<Activity> getActivities();

} // Group