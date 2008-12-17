/******************************************************************************
 * Copyright (c) 2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Date         Author                  Changes
 * 14 Feb 2008  Antoine Toulme      Created
 */
package org.eclipse.stp.bpmn;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Message Vertex</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.MessageVertex#getOrderedMessages <em>Ordered Messages</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.MessageVertex#getIncomingMessages <em>Incoming Messages</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.MessageVertex#getOutgoingMessages <em>Outgoing Messages</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.stp.bpmn.BpmnPackage#getMessageVertex()
 * @model extendedMetaData="name='MessageVertex' kind='elementOnly'"
 * @generated
 */
public interface MessageVertex extends NamedBpmnObject, Identifiable {
    /**
     * Returns the value of the '<em><b>Ordered Messages</b></em>' attribute list.
     * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Ordered Messages</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Ordered Messages</em>' attribute list.
     * @see org.eclipse.stp.bpmn.BpmnPackage#getMessageVertex_OrderedMessages()
     * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
     *        extendedMetaData="kind='group' name='orderedMessages:5'"
     * @generated
     */
    FeatureMap getOrderedMessages();

    /**
     * Returns the value of the '<em><b>Incoming Messages</b></em>' reference list.
     * The list contents are of type {@link org.eclipse.stp.bpmn.MessagingEdge}.
     * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.MessagingEdge#getTarget <em>Target</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Incoming Messages</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Incoming Messages</em>' reference list.
     * @see org.eclipse.stp.bpmn.BpmnPackage#getMessageVertex_IncomingMessages()
     * @see org.eclipse.stp.bpmn.MessagingEdge#getTarget
     * @model opposite="target" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='incomingMessages' group='#orderedMessages:5'"
     * @generated
     */
    EList<MessagingEdge> getIncomingMessages();

    /**
     * Returns the value of the '<em><b>Outgoing Messages</b></em>' reference list.
     * The list contents are of type {@link org.eclipse.stp.bpmn.MessagingEdge}.
     * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.MessagingEdge#getSource <em>Source</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Outgoing Messages</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Outgoing Messages</em>' reference list.
     * @see org.eclipse.stp.bpmn.BpmnPackage#getMessageVertex_OutgoingMessages()
     * @see org.eclipse.stp.bpmn.MessagingEdge#getSource
     * @model opposite="source" transient="true" volatile="true" derived="true"
     *        extendedMetaData="kind='element' name='outgoingMessages' group='#orderedMessages:5'"
     * @generated
     */
    EList<MessagingEdge> getOutgoingMessages();

} // MessageVertex
