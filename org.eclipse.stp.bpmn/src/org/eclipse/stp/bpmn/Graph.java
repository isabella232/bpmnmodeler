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
 * A representation of the model object '<em><b>Graph</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.Graph#getVertices <em>Vertices</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.Graph#getSequenceEdges <em>Sequence Edges</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.stp.bpmn.BpmnPackage#getGraph()
 * @model extendedMetaData="name='Graph' kind='elementOnly'"
 * @generated
 */
public interface Graph extends IdentifiableNode, ArtifactsContainer {
    /**
	 * Returns the value of the '<em><b>Vertices</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.stp.bpmn.Vertex}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.Vertex#getGraph <em>Graph</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Vertices</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Vertices</em>' containment reference list.
	 * @see org.eclipse.stp.bpmn.BpmnPackage#getGraph_Vertices()
	 * @see org.eclipse.stp.bpmn.Vertex#getGraph
	 * @model opposite="graph" containment="true"
	 *        extendedMetaData="kind='element' name='vertices'"
	 * @generated
	 */
    EList<Vertex> getVertices();

    /**
	 * Returns the value of the '<em><b>Sequence Edges</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.stp.bpmn.SequenceEdge}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.SequenceEdge#getGraph <em>Graph</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Sequence Edges</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Sequence Edges</em>' containment reference list.
	 * @see org.eclipse.stp.bpmn.BpmnPackage#getGraph_SequenceEdges()
	 * @see org.eclipse.stp.bpmn.SequenceEdge#getGraph
	 * @model opposite="graph" containment="true"
	 *        extendedMetaData="kind='element' name='sequenceEdges'"
	 * @generated
	 */
    EList<SequenceEdge> getSequenceEdges();

} // Graph