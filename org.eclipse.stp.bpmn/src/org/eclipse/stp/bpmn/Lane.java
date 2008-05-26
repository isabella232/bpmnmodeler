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
 * A representation of the model object '<em><b>Lane</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.Lane#getActivities <em>Activities</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.Lane#getPool <em>Pool</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.stp.bpmn.BpmnPackage#getLane()
 * @model extendedMetaData="name='Lane' kind='elementOnly'"
 * @generated
 */
public interface Lane extends AssociationTarget, NamedBpmnObject {
    /**
     * Returns the value of the '<em><b>Activities</b></em>' reference list.
     * The list contents are of type {@link org.eclipse.stp.bpmn.Activity}.
     * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.Activity#getLane <em>Lane</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Activities</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Activities</em>' reference list.
     * @see org.eclipse.stp.bpmn.BpmnPackage#getLane_Activities()
     * @see org.eclipse.stp.bpmn.Activity#getLane
     * @model opposite="lane" resolveProxies="false"
     *        extendedMetaData="kind='element' name='activities'"
     * @generated
     */
    EList<Activity> getActivities();

    /**
     * Returns the value of the '<em><b>Pool</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.Pool#getLanes <em>Lanes</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Pool</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Pool</em>' container reference.
     * @see #setPool(Pool)
     * @see org.eclipse.stp.bpmn.BpmnPackage#getLane_Pool()
     * @see org.eclipse.stp.bpmn.Pool#getLanes
     * @model opposite="lanes" transient="false"
     *        extendedMetaData="kind='attribute' name='pool'"
     * @generated
     */
    Pool getPool();

    /**
     * Sets the value of the '{@link org.eclipse.stp.bpmn.Lane#getPool <em>Pool</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Pool</em>' container reference.
     * @see #getPool()
     * @generated
     */
    void setPool(Pool value);

} // Lane