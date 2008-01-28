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
 * A representation of the model object '<em><b>Sub Process</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.SubProcess#getEventHandlers <em>Event Handlers</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.SubProcess#isIsTransaction <em>Is Transaction</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.stp.bpmn.BpmnPackage#getSubProcess()
 * @model extendedMetaData="name='SubProcess' kind='elementOnly'"
 * @generated
 */
public interface SubProcess extends Activity, Graph {
    /**
	 * Returns the value of the '<em><b>Event Handlers</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.stp.bpmn.Activity}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.Activity#getEventHandlerFor <em>Event Handler For</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Event Handlers</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Handlers</em>' containment reference list.
	 * @see org.eclipse.stp.bpmn.BpmnPackage#getSubProcess_EventHandlers()
	 * @see org.eclipse.stp.bpmn.Activity#getEventHandlerFor
	 * @model opposite="eventHandlerFor" containment="true"
	 *        extendedMetaData="kind='element' name='eventHandlers'"
	 * @generated
	 */
    EList<Activity> getEventHandlers();

    /**
	 * Returns the value of the '<em><b>Is Transaction</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Is Transaction</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Transaction</em>' attribute.
	 * @see #isSetIsTransaction()
	 * @see #unsetIsTransaction()
	 * @see #setIsTransaction(boolean)
	 * @see org.eclipse.stp.bpmn.BpmnPackage#getSubProcess_IsTransaction()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='isTransaction'"
	 * @generated
	 */
    boolean isIsTransaction();

    /**
	 * Sets the value of the '{@link org.eclipse.stp.bpmn.SubProcess#isIsTransaction <em>Is Transaction</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Transaction</em>' attribute.
	 * @see #isSetIsTransaction()
	 * @see #unsetIsTransaction()
	 * @see #isIsTransaction()
	 * @generated
	 */
    void setIsTransaction(boolean value);

    /**
	 * Unsets the value of the '{@link org.eclipse.stp.bpmn.SubProcess#isIsTransaction <em>Is Transaction</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #isSetIsTransaction()
	 * @see #isIsTransaction()
	 * @see #setIsTransaction(boolean)
	 * @generated
	 */
    void unsetIsTransaction();

    /**
	 * Returns whether the value of the '{@link org.eclipse.stp.bpmn.SubProcess#isIsTransaction <em>Is Transaction</em>}' attribute is set.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Is Transaction</em>' attribute is set.
	 * @see #unsetIsTransaction()
	 * @see #isIsTransaction()
	 * @see #setIsTransaction(boolean)
	 * @generated
	 */
    boolean isSetIsTransaction();

} // SubProcess