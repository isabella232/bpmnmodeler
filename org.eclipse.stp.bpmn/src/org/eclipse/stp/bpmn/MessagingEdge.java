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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Messaging Edge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.MessagingEdge#getBpmnDiagram <em>Bpmn Diagram</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.MessagingEdge#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.MessagingEdge#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.stp.bpmn.BpmnPackage#getMessagingEdge()
 * @model extendedMetaData="name='MessagingEdge' kind='elementOnly'"
 * @generated
 */
public interface MessagingEdge extends Identifiable, NamedBpmnObject {
    /**
	 * Returns the value of the '<em><b>Bpmn Diagram</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.BpmnDiagram#getMessages <em>Messages</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Bpmn Diagram</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Bpmn Diagram</em>' container reference.
	 * @see #setBpmnDiagram(BpmnDiagram)
	 * @see org.eclipse.stp.bpmn.BpmnPackage#getMessagingEdge_BpmnDiagram()
	 * @see org.eclipse.stp.bpmn.BpmnDiagram#getMessages
	 * @model opposite="messages" transient="false"
	 *        extendedMetaData="kind='attribute' name='bpmnDiagram'"
	 * @generated
	 */
    BpmnDiagram getBpmnDiagram();

    /**
	 * Sets the value of the '{@link org.eclipse.stp.bpmn.MessagingEdge#getBpmnDiagram <em>Bpmn Diagram</em>}' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Bpmn Diagram</em>' container reference.
	 * @see #getBpmnDiagram()
	 * @generated
	 */
    void setBpmnDiagram(BpmnDiagram value);

    /**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.Activity#getOutgoingMessages <em>Outgoing Messages</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(Activity)
	 * @see org.eclipse.stp.bpmn.BpmnPackage#getMessagingEdge_Source()
	 * @see org.eclipse.stp.bpmn.Activity#getOutgoingMessages
	 * @model opposite="outgoingMessages"
	 *        extendedMetaData="kind='attribute' name='source'"
	 * @generated
	 */
    Activity getSource();

    /**
	 * Sets the value of the '{@link org.eclipse.stp.bpmn.MessagingEdge#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
    void setSource(Activity value);

    /**
	 * Returns the value of the '<em><b>Target</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.Activity#getIncomingMessages <em>Incoming Messages</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Target</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(Activity)
	 * @see org.eclipse.stp.bpmn.BpmnPackage#getMessagingEdge_Target()
	 * @see org.eclipse.stp.bpmn.Activity#getIncomingMessages
	 * @model opposite="incomingMessages"
	 *        extendedMetaData="kind='attribute' name='target'"
	 * @generated
	 */
    Activity getTarget();

    /**
	 * Sets the value of the '{@link org.eclipse.stp.bpmn.MessagingEdge#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
    void setTarget(Activity value);

} // MessagingEdge