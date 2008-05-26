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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.stp.bpmn.BpmnPackage
 * @generated
 */
public interface BpmnFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    BpmnFactory eINSTANCE = org.eclipse.stp.bpmn.impl.BpmnFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Activity</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Activity</em>'.
     * @generated
     */
    Activity createActivity();

    /**
     * Returns a new object of class '<em>Artifact</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Artifact</em>'.
     * @generated
     */
	Artifact createArtifact();

    /**
     * Returns a new object of class '<em>Artifacts Container</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Artifacts Container</em>'.
     * @generated
     */
    ArtifactsContainer createArtifactsContainer();

    /**
     * Returns a new object of class '<em>Association</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Association</em>'.
     * @generated
     */
	Association createAssociation();

    /**
     * Returns a new object of class '<em>Association Target</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Association Target</em>'.
     * @generated
     */
    AssociationTarget createAssociationTarget();

    /**
     * Returns a new object of class '<em>Diagram</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Diagram</em>'.
     * @generated
     */
    BpmnDiagram createBpmnDiagram();

    /**
     * Returns a new object of class '<em>Data Object</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Data Object</em>'.
     * @generated
     */
	DataObject createDataObject();

    /**
     * Returns a new object of class '<em>Graph</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Graph</em>'.
     * @generated
     */
    Graph createGraph();

    /**
     * Returns a new object of class '<em>Group</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Group</em>'.
     * @generated
     */
	Group createGroup();

    /**
     * Returns a new object of class '<em>Identifiable</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Identifiable</em>'.
     * @generated
     */
    Identifiable createIdentifiable();

    /**
     * Returns a new object of class '<em>Lane</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Lane</em>'.
     * @generated
     */
    Lane createLane();

    /**
     * Returns a new object of class '<em>Message Vertex</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Message Vertex</em>'.
     * @generated
     */
    MessageVertex createMessageVertex();

    /**
     * Returns a new object of class '<em>Messaging Edge</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Messaging Edge</em>'.
     * @generated
     */
    MessagingEdge createMessagingEdge();

    /**
     * Returns a new object of class '<em>Named Bpmn Object</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Named Bpmn Object</em>'.
     * @generated
     */
    NamedBpmnObject createNamedBpmnObject();

    /**
     * Returns a new object of class '<em>Pool</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Pool</em>'.
     * @generated
     */
    Pool createPool();

    /**
     * Returns a new object of class '<em>Sequence Edge</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Sequence Edge</em>'.
     * @generated
     */
    SequenceEdge createSequenceEdge();

    /**
     * Returns a new object of class '<em>Sub Process</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Sub Process</em>'.
     * @generated
     */
    SubProcess createSubProcess();

    /**
     * Returns a new object of class '<em>Text Annotation</em>'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return a new object of class '<em>Text Annotation</em>'.
     * @generated
     */
	TextAnnotation createTextAnnotation();

    /**
     * Returns a new object of class '<em>Vertex</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Vertex</em>'.
     * @generated
     */
    Vertex createVertex();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    BpmnPackage getBpmnPackage();

} //BpmnFactory
