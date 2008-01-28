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
 * A representation of the model object '<em><b>Diagram</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.BpmnDiagram#getPools <em>Pools</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.BpmnDiagram#getMessages <em>Messages</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.BpmnDiagram#getAuthor <em>Author</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.BpmnDiagram#getTitle <em>Title</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.stp.bpmn.BpmnPackage#getBpmnDiagram()
 * @model extendedMetaData="name='BpmnDiagram' kind='elementOnly'"
 * @generated
 */
public interface BpmnDiagram extends Identifiable, ArtifactsContainer {
    /**
	 * Returns the value of the '<em><b>Pools</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.stp.bpmn.Pool}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.Pool#getBpmnDiagram <em>Bpmn Diagram</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Pools</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Pools</em>' containment reference list.
	 * @see org.eclipse.stp.bpmn.BpmnPackage#getBpmnDiagram_Pools()
	 * @see org.eclipse.stp.bpmn.Pool#getBpmnDiagram
	 * @model opposite="bpmnDiagram" containment="true"
	 *        extendedMetaData="kind='element' name='pools'"
	 * @generated
	 */
    EList<Pool> getPools();

    /**
	 * Returns the value of the '<em><b>Messages</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.stp.bpmn.MessagingEdge}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.MessagingEdge#getBpmnDiagram <em>Bpmn Diagram</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Messages</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Messages</em>' containment reference list.
	 * @see org.eclipse.stp.bpmn.BpmnPackage#getBpmnDiagram_Messages()
	 * @see org.eclipse.stp.bpmn.MessagingEdge#getBpmnDiagram
	 * @model opposite="bpmnDiagram" containment="true"
	 *        extendedMetaData="kind='element' name='messages'"
	 * @generated
	 */
    EList<MessagingEdge> getMessages();

    /**
	 * Returns the value of the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Author</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Author</em>' attribute.
	 * @see #setAuthor(String)
	 * @see org.eclipse.stp.bpmn.BpmnPackage#getBpmnDiagram_Author()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='author'"
	 * @generated
	 */
    String getAuthor();

    /**
	 * Sets the value of the '{@link org.eclipse.stp.bpmn.BpmnDiagram#getAuthor <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Author</em>' attribute.
	 * @see #getAuthor()
	 * @generated
	 */
    void setAuthor(String value);

    /**
	 * Returns the value of the '<em><b>Title</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Title</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Title</em>' attribute.
	 * @see #setTitle(String)
	 * @see org.eclipse.stp.bpmn.BpmnPackage#getBpmnDiagram_Title()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='title'"
	 * @generated
	 */
    String getTitle();

    /**
	 * Sets the value of the '{@link org.eclipse.stp.bpmn.BpmnDiagram#getTitle <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Title</em>' attribute.
	 * @see #getTitle()
	 * @generated
	 */
    void setTitle(String value);

} // BpmnDiagram