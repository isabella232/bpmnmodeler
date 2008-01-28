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
 * A representation of the model object '<em><b>Vertex</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.Vertex#getOutgoingEdges <em>Outgoing Edges</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.Vertex#getIncomingEdges <em>Incoming Edges</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.Vertex#getGraph <em>Graph</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.stp.bpmn.BpmnPackage#getVertex()
 * @model extendedMetaData="name='Vertex' kind='elementOnly'"
 * @generated
 */
public interface Vertex extends IdentifiableNode {
    /**
	 * Returns the value of the '<em><b>Outgoing Edges</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.stp.bpmn.SequenceEdge}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.SequenceEdge#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Outgoing Edges</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing Edges</em>' reference list.
	 * @see org.eclipse.stp.bpmn.BpmnPackage#getVertex_OutgoingEdges()
	 * @see org.eclipse.stp.bpmn.SequenceEdge#getSource
	 * @model opposite="source"
	 *        extendedMetaData="kind='element' name='outgoingEdges'"
	 * @generated
	 */
    EList<SequenceEdge> getOutgoingEdges();

    /**
	 * Returns the value of the '<em><b>Incoming Edges</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.stp.bpmn.SequenceEdge}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.SequenceEdge#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Incoming Edges</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming Edges</em>' reference list.
	 * @see org.eclipse.stp.bpmn.BpmnPackage#getVertex_IncomingEdges()
	 * @see org.eclipse.stp.bpmn.SequenceEdge#getTarget
	 * @model opposite="target"
	 *        extendedMetaData="kind='element' name='incomingEdges'"
	 * @generated
	 */
    EList<SequenceEdge> getIncomingEdges();

    /**
	 * Returns the value of the '<em><b>Graph</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.Graph#getVertices <em>Vertices</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Graph</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Graph</em>' container reference.
	 * @see #setGraph(Graph)
	 * @see org.eclipse.stp.bpmn.BpmnPackage#getVertex_Graph()
	 * @see org.eclipse.stp.bpmn.Graph#getVertices
	 * @model opposite="vertices" transient="false"
	 *        extendedMetaData="kind='attribute' name='graph'"
	 * @generated
	 */
    Graph getGraph();

    /**
	 * Sets the value of the '{@link org.eclipse.stp.bpmn.Vertex#getGraph <em>Graph</em>}' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Graph</em>' container reference.
	 * @see #getGraph()
	 * @generated
	 */
    void setGraph(Graph value);

} // Vertex