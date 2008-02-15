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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.stp.bpmn.BpmnFactory
 * @model kind="package"
 * @generated
 */
public interface BpmnPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	String eNAME = "bpmn"; //$NON-NLS-1$

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	String eNS_URI = "http://stp.eclipse.org/bpmn"; //$NON-NLS-1$

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "bpmn"; //$NON-NLS-1$

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    BpmnPackage eINSTANCE = org.eclipse.stp.bpmn.impl.BpmnPackageImpl.init();

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.IdentifiableImpl <em>Identifiable</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.IdentifiableImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getIdentifiable()
     * @generated
     */
    int IDENTIFIABLE = 9;

    /**
     * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IDENTIFIABLE__EANNOTATIONS = EcorePackage.EMODEL_ELEMENT__EANNOTATIONS;

    /**
     * The feature id for the '<em><b>ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IDENTIFIABLE__ID = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Identifiable</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int IDENTIFIABLE_FEATURE_COUNT = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.AssociationTargetImpl <em>Association Target</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.AssociationTargetImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getAssociationTarget()
     * @generated
     */
    int ASSOCIATION_TARGET = 4;

    /**
     * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATION_TARGET__EANNOTATIONS = IDENTIFIABLE__EANNOTATIONS;

    /**
     * The feature id for the '<em><b>ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATION_TARGET__ID = IDENTIFIABLE__ID;

    /**
     * The feature id for the '<em><b>Associations</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATION_TARGET__ASSOCIATIONS = IDENTIFIABLE_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Association Target</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ASSOCIATION_TARGET_FEATURE_COUNT = IDENTIFIABLE_FEATURE_COUNT + 1;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.VertexImpl <em>Vertex</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.VertexImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getVertex()
     * @generated
     */
    int VERTEX = 18;

    /**
     * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERTEX__EANNOTATIONS = ASSOCIATION_TARGET__EANNOTATIONS;

    /**
     * The feature id for the '<em><b>ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERTEX__ID = ASSOCIATION_TARGET__ID;

    /**
     * The feature id for the '<em><b>Associations</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERTEX__ASSOCIATIONS = ASSOCIATION_TARGET__ASSOCIATIONS;

    /**
     * The feature id for the '<em><b>Outgoing Edges</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERTEX__OUTGOING_EDGES = ASSOCIATION_TARGET_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Incoming Edges</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERTEX__INCOMING_EDGES = ASSOCIATION_TARGET_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Graph</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERTEX__GRAPH = ASSOCIATION_TARGET_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Vertex</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VERTEX_FEATURE_COUNT = ASSOCIATION_TARGET_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.ActivityImpl <em>Activity</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.ActivityImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getActivity()
     * @generated
     */
    int ACTIVITY = 0;

    /**
     * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__EANNOTATIONS = VERTEX__EANNOTATIONS;

    /**
     * The feature id for the '<em><b>ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__ID = VERTEX__ID;

    /**
     * The feature id for the '<em><b>Associations</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__ASSOCIATIONS = VERTEX__ASSOCIATIONS;

    /**
     * The feature id for the '<em><b>Outgoing Edges</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__OUTGOING_EDGES = VERTEX__OUTGOING_EDGES;

    /**
     * The feature id for the '<em><b>Incoming Edges</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__INCOMING_EDGES = VERTEX__INCOMING_EDGES;

    /**
     * The feature id for the '<em><b>Graph</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__GRAPH = VERTEX__GRAPH;

    /**
     * The feature id for the '<em><b>Documentation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__DOCUMENTATION = VERTEX_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__NAME = VERTEX_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Ncname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__NCNAME = VERTEX_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Ordered Messages</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__ORDERED_MESSAGES = VERTEX_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Incoming Messages</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__INCOMING_MESSAGES = VERTEX_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Outgoing Messages</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__OUTGOING_MESSAGES = VERTEX_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Groups</b></em>' reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int ACTIVITY__GROUPS = VERTEX_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Activity Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__ACTIVITY_TYPE = VERTEX_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Event Handler For</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__EVENT_HANDLER_FOR = VERTEX_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Lane</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__LANE = VERTEX_FEATURE_COUNT + 9;

    /**
     * The feature id for the '<em><b>Looping</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY__LOOPING = VERTEX_FEATURE_COUNT + 10;

    /**
     * The number of structural features of the '<em>Activity</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ACTIVITY_FEATURE_COUNT = VERTEX_FEATURE_COUNT + 11;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.ArtifactImpl <em>Artifact</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.ArtifactImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getArtifact()
     * @generated
     */
	int ARTIFACT = 1;

    /**
     * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int ARTIFACT__EANNOTATIONS = IDENTIFIABLE__EANNOTATIONS;

    /**
     * The feature id for the '<em><b>ID</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int ARTIFACT__ID = IDENTIFIABLE__ID;

    /**
     * The feature id for the '<em><b>Documentation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT__DOCUMENTATION = IDENTIFIABLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT__NAME = IDENTIFIABLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Ncname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT__NCNAME = IDENTIFIABLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Associations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT__ASSOCIATIONS = IDENTIFIABLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Artifacts Container</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACT__ARTIFACTS_CONTAINER = IDENTIFIABLE_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Artifact</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int ARTIFACT_FEATURE_COUNT = IDENTIFIABLE_FEATURE_COUNT + 5;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.ArtifactsContainerImpl <em>Artifacts Container</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.ArtifactsContainerImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getArtifactsContainer()
     * @generated
     */
    int ARTIFACTS_CONTAINER = 2;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.AssociationImpl <em>Association</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.AssociationImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getAssociation()
     * @generated
     */
	int ASSOCIATION = 3;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.BpmnDiagramImpl <em>Diagram</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.BpmnDiagramImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getBpmnDiagram()
     * @generated
     */
    int BPMN_DIAGRAM = 5;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.DataObjectImpl <em>Data Object</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.DataObjectImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getDataObject()
     * @generated
     */
	int DATA_OBJECT = 6;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.GraphImpl <em>Graph</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.GraphImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getGraph()
     * @generated
     */
    int GRAPH = 7;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.GroupImpl <em>Group</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.GroupImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getGroup()
     * @generated
     */
	int GROUP = 8;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.LaneImpl <em>Lane</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.LaneImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getLane()
     * @generated
     */
    int LANE = 10;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.MessagingEdgeImpl <em>Messaging Edge</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.MessagingEdgeImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getMessagingEdge()
     * @generated
     */
    int MESSAGING_EDGE = 12;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.NamedBpmnObjectImpl <em>Named Bpmn Object</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.NamedBpmnObjectImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getNamedBpmnObject()
     * @generated
     */
    int NAMED_BPMN_OBJECT = 13;

    /**
     * The feature id for the '<em><b>Documentation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_BPMN_OBJECT__DOCUMENTATION = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_BPMN_OBJECT__NAME = 1;

    /**
     * The feature id for the '<em><b>Ncname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_BPMN_OBJECT__NCNAME = 2;

    /**
     * The number of structural features of the '<em>Named Bpmn Object</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int NAMED_BPMN_OBJECT_FEATURE_COUNT = 3;

    /**
     * The feature id for the '<em><b>Documentation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACTS_CONTAINER__DOCUMENTATION = NAMED_BPMN_OBJECT__DOCUMENTATION;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACTS_CONTAINER__NAME = NAMED_BPMN_OBJECT__NAME;

    /**
     * The feature id for the '<em><b>Ncname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACTS_CONTAINER__NCNAME = NAMED_BPMN_OBJECT__NCNAME;

    /**
     * The feature id for the '<em><b>Artifacts</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACTS_CONTAINER__ARTIFACTS = NAMED_BPMN_OBJECT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Artifacts Container</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ARTIFACTS_CONTAINER_FEATURE_COUNT = NAMED_BPMN_OBJECT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int ASSOCIATION__EANNOTATIONS = EcorePackage.EMODEL_ELEMENT__EANNOTATIONS;

    /**
     * The feature id for the '<em><b>Direction</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int ASSOCIATION__DIRECTION = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Source</b></em>' container reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int ASSOCIATION__SOURCE = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Target</b></em>' reference.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int ASSOCIATION__TARGET = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Association</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int ASSOCIATION_FEATURE_COUNT = EcorePackage.EMODEL_ELEMENT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BPMN_DIAGRAM__EANNOTATIONS = IDENTIFIABLE__EANNOTATIONS;

    /**
     * The feature id for the '<em><b>ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BPMN_DIAGRAM__ID = IDENTIFIABLE__ID;

    /**
     * The feature id for the '<em><b>Documentation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BPMN_DIAGRAM__DOCUMENTATION = IDENTIFIABLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BPMN_DIAGRAM__NAME = IDENTIFIABLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Ncname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BPMN_DIAGRAM__NCNAME = IDENTIFIABLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Artifacts</b></em>' containment reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int BPMN_DIAGRAM__ARTIFACTS = IDENTIFIABLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Pools</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BPMN_DIAGRAM__POOLS = IDENTIFIABLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Messages</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BPMN_DIAGRAM__MESSAGES = IDENTIFIABLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Author</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BPMN_DIAGRAM__AUTHOR = IDENTIFIABLE_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Title</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BPMN_DIAGRAM__TITLE = IDENTIFIABLE_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Diagram</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int BPMN_DIAGRAM_FEATURE_COUNT = IDENTIFIABLE_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DATA_OBJECT__EANNOTATIONS = ARTIFACT__EANNOTATIONS;

    /**
     * The feature id for the '<em><b>ID</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DATA_OBJECT__ID = ARTIFACT__ID;

    /**
     * The feature id for the '<em><b>Documentation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_OBJECT__DOCUMENTATION = ARTIFACT__DOCUMENTATION;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DATA_OBJECT__NAME = ARTIFACT__NAME;

    /**
     * The feature id for the '<em><b>Ncname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_OBJECT__NCNAME = ARTIFACT__NCNAME;

    /**
     * The feature id for the '<em><b>Associations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_OBJECT__ASSOCIATIONS = ARTIFACT__ASSOCIATIONS;

    /**
     * The feature id for the '<em><b>Artifacts Container</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int DATA_OBJECT__ARTIFACTS_CONTAINER = ARTIFACT__ARTIFACTS_CONTAINER;

    /**
     * The number of structural features of the '<em>Data Object</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int DATA_OBJECT_FEATURE_COUNT = ARTIFACT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GRAPH__EANNOTATIONS = ASSOCIATION_TARGET__EANNOTATIONS;

    /**
     * The feature id for the '<em><b>ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GRAPH__ID = ASSOCIATION_TARGET__ID;

    /**
     * The feature id for the '<em><b>Associations</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GRAPH__ASSOCIATIONS = ASSOCIATION_TARGET__ASSOCIATIONS;

    /**
     * The feature id for the '<em><b>Documentation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GRAPH__DOCUMENTATION = ASSOCIATION_TARGET_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GRAPH__NAME = ASSOCIATION_TARGET_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Ncname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GRAPH__NCNAME = ASSOCIATION_TARGET_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Artifacts</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GRAPH__ARTIFACTS = ASSOCIATION_TARGET_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Vertices</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GRAPH__VERTICES = ASSOCIATION_TARGET_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Sequence Edges</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GRAPH__SEQUENCE_EDGES = ASSOCIATION_TARGET_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Graph</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GRAPH_FEATURE_COUNT = ASSOCIATION_TARGET_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int GROUP__EANNOTATIONS = ARTIFACT__EANNOTATIONS;

    /**
     * The feature id for the '<em><b>ID</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int GROUP__ID = ARTIFACT__ID;

    /**
     * The feature id for the '<em><b>Documentation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__DOCUMENTATION = ARTIFACT__DOCUMENTATION;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int GROUP__NAME = ARTIFACT__NAME;

    /**
     * The feature id for the '<em><b>Ncname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__NCNAME = ARTIFACT__NCNAME;

    /**
     * The feature id for the '<em><b>Associations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__ASSOCIATIONS = ARTIFACT__ASSOCIATIONS;

    /**
     * The feature id for the '<em><b>Artifacts Container</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int GROUP__ARTIFACTS_CONTAINER = ARTIFACT__ARTIFACTS_CONTAINER;

    /**
     * The feature id for the '<em><b>Activities</b></em>' reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int GROUP__ACTIVITIES = ARTIFACT_FEATURE_COUNT + 0;

    /**
     * The number of structural features of the '<em>Group</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int GROUP_FEATURE_COUNT = ARTIFACT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__EANNOTATIONS = ASSOCIATION_TARGET__EANNOTATIONS;

    /**
     * The feature id for the '<em><b>ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__ID = ASSOCIATION_TARGET__ID;

    /**
     * The feature id for the '<em><b>Associations</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__ASSOCIATIONS = ASSOCIATION_TARGET__ASSOCIATIONS;

    /**
     * The feature id for the '<em><b>Documentation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__DOCUMENTATION = ASSOCIATION_TARGET_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__NAME = ASSOCIATION_TARGET_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Ncname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__NCNAME = ASSOCIATION_TARGET_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Activities</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__ACTIVITIES = ASSOCIATION_TARGET_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Pool</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE__POOL = ASSOCIATION_TARGET_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Lane</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LANE_FEATURE_COUNT = ASSOCIATION_TARGET_FEATURE_COUNT + 5;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.MessageVertexImpl <em>Message Vertex</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.MessageVertexImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getMessageVertex()
     * @generated
     */
    int MESSAGE_VERTEX = 11;

    /**
     * The feature id for the '<em><b>Documentation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_VERTEX__DOCUMENTATION = NAMED_BPMN_OBJECT__DOCUMENTATION;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_VERTEX__NAME = NAMED_BPMN_OBJECT__NAME;

    /**
     * The feature id for the '<em><b>Ncname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_VERTEX__NCNAME = NAMED_BPMN_OBJECT__NCNAME;

    /**
     * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_VERTEX__EANNOTATIONS = NAMED_BPMN_OBJECT_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_VERTEX__ID = NAMED_BPMN_OBJECT_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Ordered Messages</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_VERTEX__ORDERED_MESSAGES = NAMED_BPMN_OBJECT_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Incoming Messages</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_VERTEX__INCOMING_MESSAGES = NAMED_BPMN_OBJECT_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Outgoing Messages</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_VERTEX__OUTGOING_MESSAGES = NAMED_BPMN_OBJECT_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Message Vertex</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGE_VERTEX_FEATURE_COUNT = NAMED_BPMN_OBJECT_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGING_EDGE__EANNOTATIONS = ASSOCIATION_TARGET__EANNOTATIONS;

    /**
     * The feature id for the '<em><b>ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGING_EDGE__ID = ASSOCIATION_TARGET__ID;

    /**
     * The feature id for the '<em><b>Associations</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGING_EDGE__ASSOCIATIONS = ASSOCIATION_TARGET__ASSOCIATIONS;

    /**
     * The feature id for the '<em><b>Documentation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGING_EDGE__DOCUMENTATION = ASSOCIATION_TARGET_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGING_EDGE__NAME = ASSOCIATION_TARGET_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Ncname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGING_EDGE__NCNAME = ASSOCIATION_TARGET_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Bpmn Diagram</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGING_EDGE__BPMN_DIAGRAM = ASSOCIATION_TARGET_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Source</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGING_EDGE__SOURCE = ASSOCIATION_TARGET_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Target</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGING_EDGE__TARGET = ASSOCIATION_TARGET_FEATURE_COUNT + 5;

    /**
     * The number of structural features of the '<em>Messaging Edge</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int MESSAGING_EDGE_FEATURE_COUNT = ASSOCIATION_TARGET_FEATURE_COUNT + 6;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.PoolImpl <em>Pool</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.PoolImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getPool()
     * @generated
     */
    int POOL = 14;

    /**
     * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__EANNOTATIONS = GRAPH__EANNOTATIONS;

    /**
     * The feature id for the '<em><b>ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__ID = GRAPH__ID;

    /**
     * The feature id for the '<em><b>Associations</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__ASSOCIATIONS = GRAPH__ASSOCIATIONS;

    /**
     * The feature id for the '<em><b>Documentation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__DOCUMENTATION = GRAPH__DOCUMENTATION;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__NAME = GRAPH__NAME;

    /**
     * The feature id for the '<em><b>Ncname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__NCNAME = GRAPH__NCNAME;

    /**
     * The feature id for the '<em><b>Artifacts</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__ARTIFACTS = GRAPH__ARTIFACTS;

    /**
     * The feature id for the '<em><b>Vertices</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__VERTICES = GRAPH__VERTICES;

    /**
     * The feature id for the '<em><b>Sequence Edges</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__SEQUENCE_EDGES = GRAPH__SEQUENCE_EDGES;

    /**
     * The feature id for the '<em><b>Ordered Messages</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__ORDERED_MESSAGES = GRAPH_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Incoming Messages</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__INCOMING_MESSAGES = GRAPH_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Outgoing Messages</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__OUTGOING_MESSAGES = GRAPH_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Lanes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__LANES = GRAPH_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Bpmn Diagram</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL__BPMN_DIAGRAM = GRAPH_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Pool</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int POOL_FEATURE_COUNT = GRAPH_FEATURE_COUNT + 5;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.SequenceEdgeImpl <em>Sequence Edge</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.SequenceEdgeImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getSequenceEdge()
     * @generated
     */
    int SEQUENCE_EDGE = 15;

    /**
     * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SEQUENCE_EDGE__EANNOTATIONS = ASSOCIATION_TARGET__EANNOTATIONS;

    /**
     * The feature id for the '<em><b>ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SEQUENCE_EDGE__ID = ASSOCIATION_TARGET__ID;

    /**
     * The feature id for the '<em><b>Associations</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SEQUENCE_EDGE__ASSOCIATIONS = ASSOCIATION_TARGET__ASSOCIATIONS;

    /**
     * The feature id for the '<em><b>Documentation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SEQUENCE_EDGE__DOCUMENTATION = ASSOCIATION_TARGET_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SEQUENCE_EDGE__NAME = ASSOCIATION_TARGET_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Ncname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SEQUENCE_EDGE__NCNAME = ASSOCIATION_TARGET_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Condition Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SEQUENCE_EDGE__CONDITION_TYPE = ASSOCIATION_TARGET_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Graph</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SEQUENCE_EDGE__GRAPH = ASSOCIATION_TARGET_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Is Default</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SEQUENCE_EDGE__IS_DEFAULT = ASSOCIATION_TARGET_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Source</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SEQUENCE_EDGE__SOURCE = ASSOCIATION_TARGET_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Target</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SEQUENCE_EDGE__TARGET = ASSOCIATION_TARGET_FEATURE_COUNT + 7;

    /**
     * The number of structural features of the '<em>Sequence Edge</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SEQUENCE_EDGE_FEATURE_COUNT = ASSOCIATION_TARGET_FEATURE_COUNT + 8;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.SubProcessImpl <em>Sub Process</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.SubProcessImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getSubProcess()
     * @generated
     */
    int SUB_PROCESS = 16;

    /**
     * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__EANNOTATIONS = ACTIVITY__EANNOTATIONS;

    /**
     * The feature id for the '<em><b>ID</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__ID = ACTIVITY__ID;

    /**
     * The feature id for the '<em><b>Associations</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__ASSOCIATIONS = ACTIVITY__ASSOCIATIONS;

    /**
     * The feature id for the '<em><b>Outgoing Edges</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__OUTGOING_EDGES = ACTIVITY__OUTGOING_EDGES;

    /**
     * The feature id for the '<em><b>Incoming Edges</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__INCOMING_EDGES = ACTIVITY__INCOMING_EDGES;

    /**
     * The feature id for the '<em><b>Graph</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__GRAPH = ACTIVITY__GRAPH;

    /**
     * The feature id for the '<em><b>Documentation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__DOCUMENTATION = ACTIVITY__DOCUMENTATION;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__NAME = ACTIVITY__NAME;

    /**
     * The feature id for the '<em><b>Ncname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__NCNAME = ACTIVITY__NCNAME;

    /**
     * The feature id for the '<em><b>Ordered Messages</b></em>' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__ORDERED_MESSAGES = ACTIVITY__ORDERED_MESSAGES;

    /**
     * The feature id for the '<em><b>Incoming Messages</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__INCOMING_MESSAGES = ACTIVITY__INCOMING_MESSAGES;

    /**
     * The feature id for the '<em><b>Outgoing Messages</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__OUTGOING_MESSAGES = ACTIVITY__OUTGOING_MESSAGES;

    /**
     * The feature id for the '<em><b>Groups</b></em>' reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int SUB_PROCESS__GROUPS = ACTIVITY__GROUPS;

    /**
     * The feature id for the '<em><b>Activity Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__ACTIVITY_TYPE = ACTIVITY__ACTIVITY_TYPE;

    /**
     * The feature id for the '<em><b>Event Handler For</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__EVENT_HANDLER_FOR = ACTIVITY__EVENT_HANDLER_FOR;

    /**
     * The feature id for the '<em><b>Lane</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__LANE = ACTIVITY__LANE;

    /**
     * The feature id for the '<em><b>Looping</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__LOOPING = ACTIVITY__LOOPING;

    /**
     * The feature id for the '<em><b>Artifacts</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__ARTIFACTS = ACTIVITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Vertices</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__VERTICES = ACTIVITY_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Sequence Edges</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__SEQUENCE_EDGES = ACTIVITY_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Event Handlers</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__EVENT_HANDLERS = ACTIVITY_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Is Transaction</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS__IS_TRANSACTION = ACTIVITY_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Sub Process</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int SUB_PROCESS_FEATURE_COUNT = ACTIVITY_FEATURE_COUNT + 5;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.impl.TextAnnotationImpl <em>Text Annotation</em>}' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.impl.TextAnnotationImpl
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getTextAnnotation()
     * @generated
     */
	int TEXT_ANNOTATION = 17;

    /**
     * The feature id for the '<em><b>EAnnotations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int TEXT_ANNOTATION__EANNOTATIONS = ARTIFACT__EANNOTATIONS;

    /**
     * The feature id for the '<em><b>ID</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int TEXT_ANNOTATION__ID = ARTIFACT__ID;

    /**
     * The feature id for the '<em><b>Documentation</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TEXT_ANNOTATION__DOCUMENTATION = ARTIFACT__DOCUMENTATION;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TEXT_ANNOTATION__NAME = ARTIFACT__NAME;

    /**
     * The feature id for the '<em><b>Ncname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TEXT_ANNOTATION__NCNAME = ARTIFACT__NCNAME;

    /**
     * The feature id for the '<em><b>Associations</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TEXT_ANNOTATION__ASSOCIATIONS = ARTIFACT__ASSOCIATIONS;

    /**
     * The feature id for the '<em><b>Artifacts Container</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TEXT_ANNOTATION__ARTIFACTS_CONTAINER = ARTIFACT__ARTIFACTS_CONTAINER;

    /**
     * The number of structural features of the '<em>Text Annotation</em>' class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	int TEXT_ANNOTATION_FEATURE_COUNT = ARTIFACT_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.ActivityType <em>Activity Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.ActivityType
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getActivityType()
     * @generated
     */
    int ACTIVITY_TYPE = 19;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.DirectionType <em>Direction Type</em>}' enum.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.DirectionType
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getDirectionType()
     * @generated
     */
	int DIRECTION_TYPE = 20;

    /**
     * The meta object id for the '{@link org.eclipse.stp.bpmn.SequenceFlowConditionType <em>Sequence Flow Condition Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.SequenceFlowConditionType
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getSequenceFlowConditionType()
     * @generated
     */
    int SEQUENCE_FLOW_CONDITION_TYPE = 21;

    /**
     * The meta object id for the '<em>Activity Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.ActivityType
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getActivityTypeObject()
     * @generated
     */
    int ACTIVITY_TYPE_OBJECT = 22;


    /**
     * The meta object id for the '<em>Direction Type Object</em>' data type.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.DirectionType
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getDirectionTypeObject()
     * @generated
     */
	int DIRECTION_TYPE_OBJECT = 23;


    /**
     * The meta object id for the '<em>Sequence Flow Condition Type Object</em>' data type.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.stp.bpmn.SequenceFlowConditionType
     * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getSequenceFlowConditionTypeObject()
     * @generated
     */
    int SEQUENCE_FLOW_CONDITION_TYPE_OBJECT = 24;


    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.Activity <em>Activity</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Activity</em>'.
     * @see org.eclipse.stp.bpmn.Activity
     * @generated
     */
    EClass getActivity();

    /**
     * Returns the meta object for the reference list '{@link org.eclipse.stp.bpmn.Activity#getGroups <em>Groups</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Groups</em>'.
     * @see org.eclipse.stp.bpmn.Activity#getGroups()
     * @see #getActivity()
     * @generated
     */
	EReference getActivity_Groups();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.stp.bpmn.Activity#getActivityType <em>Activity Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Activity Type</em>'.
     * @see org.eclipse.stp.bpmn.Activity#getActivityType()
     * @see #getActivity()
     * @generated
     */
    EAttribute getActivity_ActivityType();

    /**
     * Returns the meta object for the container reference '{@link org.eclipse.stp.bpmn.Activity#getEventHandlerFor <em>Event Handler For</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Event Handler For</em>'.
     * @see org.eclipse.stp.bpmn.Activity#getEventHandlerFor()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_EventHandlerFor();

    /**
     * Returns the meta object for the reference '{@link org.eclipse.stp.bpmn.Activity#getLane <em>Lane</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Lane</em>'.
     * @see org.eclipse.stp.bpmn.Activity#getLane()
     * @see #getActivity()
     * @generated
     */
    EReference getActivity_Lane();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.stp.bpmn.Activity#isLooping <em>Looping</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Looping</em>'.
     * @see org.eclipse.stp.bpmn.Activity#isLooping()
     * @see #getActivity()
     * @generated
     */
    EAttribute getActivity_Looping();

    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.Artifact <em>Artifact</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Artifact</em>'.
     * @see org.eclipse.stp.bpmn.Artifact
     * @generated
     */
	EClass getArtifact();

    /**
     * Returns the meta object for the containment reference list '{@link org.eclipse.stp.bpmn.Artifact#getAssociations <em>Associations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Associations</em>'.
     * @see org.eclipse.stp.bpmn.Artifact#getAssociations()
     * @see #getArtifact()
     * @generated
     */
    EReference getArtifact_Associations();

    /**
     * Returns the meta object for the container reference '{@link org.eclipse.stp.bpmn.Artifact#getArtifactsContainer <em>Artifacts Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Artifacts Container</em>'.
     * @see org.eclipse.stp.bpmn.Artifact#getArtifactsContainer()
     * @see #getArtifact()
     * @generated
     */
    EReference getArtifact_ArtifactsContainer();

    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.ArtifactsContainer <em>Artifacts Container</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Artifacts Container</em>'.
     * @see org.eclipse.stp.bpmn.ArtifactsContainer
     * @generated
     */
    EClass getArtifactsContainer();

    /**
     * Returns the meta object for the containment reference list '{@link org.eclipse.stp.bpmn.ArtifactsContainer#getArtifacts <em>Artifacts</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Artifacts</em>'.
     * @see org.eclipse.stp.bpmn.ArtifactsContainer#getArtifacts()
     * @see #getArtifactsContainer()
     * @generated
     */
    EReference getArtifactsContainer_Artifacts();

    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.Association <em>Association</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Association</em>'.
     * @see org.eclipse.stp.bpmn.Association
     * @generated
     */
	EClass getAssociation();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.stp.bpmn.Association#getDirection <em>Direction</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Direction</em>'.
     * @see org.eclipse.stp.bpmn.Association#getDirection()
     * @see #getAssociation()
     * @generated
     */
	EAttribute getAssociation_Direction();

    /**
     * Returns the meta object for the container reference '{@link org.eclipse.stp.bpmn.Association#getSource <em>Source</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Source</em>'.
     * @see org.eclipse.stp.bpmn.Association#getSource()
     * @see #getAssociation()
     * @generated
     */
	EReference getAssociation_Source();

    /**
     * Returns the meta object for the reference '{@link org.eclipse.stp.bpmn.Association#getTarget <em>Target</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Target</em>'.
     * @see org.eclipse.stp.bpmn.Association#getTarget()
     * @see #getAssociation()
     * @generated
     */
	EReference getAssociation_Target();

    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.AssociationTarget <em>Association Target</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Association Target</em>'.
     * @see org.eclipse.stp.bpmn.AssociationTarget
     * @generated
     */
    EClass getAssociationTarget();

    /**
     * Returns the meta object for the reference list '{@link org.eclipse.stp.bpmn.AssociationTarget#getAssociations <em>Associations</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Associations</em>'.
     * @see org.eclipse.stp.bpmn.AssociationTarget#getAssociations()
     * @see #getAssociationTarget()
     * @generated
     */
    EReference getAssociationTarget_Associations();

    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.BpmnDiagram <em>Diagram</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Diagram</em>'.
     * @see org.eclipse.stp.bpmn.BpmnDiagram
     * @generated
     */
    EClass getBpmnDiagram();

    /**
     * Returns the meta object for the containment reference list '{@link org.eclipse.stp.bpmn.BpmnDiagram#getPools <em>Pools</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Pools</em>'.
     * @see org.eclipse.stp.bpmn.BpmnDiagram#getPools()
     * @see #getBpmnDiagram()
     * @generated
     */
    EReference getBpmnDiagram_Pools();

    /**
     * Returns the meta object for the containment reference list '{@link org.eclipse.stp.bpmn.BpmnDiagram#getMessages <em>Messages</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Messages</em>'.
     * @see org.eclipse.stp.bpmn.BpmnDiagram#getMessages()
     * @see #getBpmnDiagram()
     * @generated
     */
    EReference getBpmnDiagram_Messages();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.stp.bpmn.BpmnDiagram#getAuthor <em>Author</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Author</em>'.
     * @see org.eclipse.stp.bpmn.BpmnDiagram#getAuthor()
     * @see #getBpmnDiagram()
     * @generated
     */
    EAttribute getBpmnDiagram_Author();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.stp.bpmn.BpmnDiagram#getTitle <em>Title</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Title</em>'.
     * @see org.eclipse.stp.bpmn.BpmnDiagram#getTitle()
     * @see #getBpmnDiagram()
     * @generated
     */
    EAttribute getBpmnDiagram_Title();

    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.DataObject <em>Data Object</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Data Object</em>'.
     * @see org.eclipse.stp.bpmn.DataObject
     * @generated
     */
	EClass getDataObject();

    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.Graph <em>Graph</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Graph</em>'.
     * @see org.eclipse.stp.bpmn.Graph
     * @generated
     */
    EClass getGraph();

    /**
     * Returns the meta object for the containment reference list '{@link org.eclipse.stp.bpmn.Graph#getVertices <em>Vertices</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Vertices</em>'.
     * @see org.eclipse.stp.bpmn.Graph#getVertices()
     * @see #getGraph()
     * @generated
     */
    EReference getGraph_Vertices();

    /**
     * Returns the meta object for the containment reference list '{@link org.eclipse.stp.bpmn.Graph#getSequenceEdges <em>Sequence Edges</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Sequence Edges</em>'.
     * @see org.eclipse.stp.bpmn.Graph#getSequenceEdges()
     * @see #getGraph()
     * @generated
     */
    EReference getGraph_SequenceEdges();

    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.Group <em>Group</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Group</em>'.
     * @see org.eclipse.stp.bpmn.Group
     * @generated
     */
	EClass getGroup();

    /**
     * Returns the meta object for the reference list '{@link org.eclipse.stp.bpmn.Group#getActivities <em>Activities</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Activities</em>'.
     * @see org.eclipse.stp.bpmn.Group#getActivities()
     * @see #getGroup()
     * @generated
     */
	EReference getGroup_Activities();

    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.Identifiable <em>Identifiable</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Identifiable</em>'.
     * @see org.eclipse.stp.bpmn.Identifiable
     * @generated
     */
    EClass getIdentifiable();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.stp.bpmn.Identifiable#getID <em>ID</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>ID</em>'.
     * @see org.eclipse.stp.bpmn.Identifiable#getID()
     * @see #getIdentifiable()
     * @generated
     */
    EAttribute getIdentifiable_ID();

    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.Lane <em>Lane</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Lane</em>'.
     * @see org.eclipse.stp.bpmn.Lane
     * @generated
     */
    EClass getLane();

    /**
     * Returns the meta object for the reference list '{@link org.eclipse.stp.bpmn.Lane#getActivities <em>Activities</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Activities</em>'.
     * @see org.eclipse.stp.bpmn.Lane#getActivities()
     * @see #getLane()
     * @generated
     */
    EReference getLane_Activities();

    /**
     * Returns the meta object for the container reference '{@link org.eclipse.stp.bpmn.Lane#getPool <em>Pool</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Pool</em>'.
     * @see org.eclipse.stp.bpmn.Lane#getPool()
     * @see #getLane()
     * @generated
     */
    EReference getLane_Pool();

    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.MessageVertex <em>Message Vertex</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Message Vertex</em>'.
     * @see org.eclipse.stp.bpmn.MessageVertex
     * @generated
     */
    EClass getMessageVertex();

    /**
     * Returns the meta object for the attribute list '{@link org.eclipse.stp.bpmn.MessageVertex#getOrderedMessages <em>Ordered Messages</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute list '<em>Ordered Messages</em>'.
     * @see org.eclipse.stp.bpmn.MessageVertex#getOrderedMessages()
     * @see #getMessageVertex()
     * @generated
     */
    EAttribute getMessageVertex_OrderedMessages();

    /**
     * Returns the meta object for the reference list '{@link org.eclipse.stp.bpmn.MessageVertex#getIncomingMessages <em>Incoming Messages</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Incoming Messages</em>'.
     * @see org.eclipse.stp.bpmn.MessageVertex#getIncomingMessages()
     * @see #getMessageVertex()
     * @generated
     */
    EReference getMessageVertex_IncomingMessages();

    /**
     * Returns the meta object for the reference list '{@link org.eclipse.stp.bpmn.MessageVertex#getOutgoingMessages <em>Outgoing Messages</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Outgoing Messages</em>'.
     * @see org.eclipse.stp.bpmn.MessageVertex#getOutgoingMessages()
     * @see #getMessageVertex()
     * @generated
     */
    EReference getMessageVertex_OutgoingMessages();

    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.MessagingEdge <em>Messaging Edge</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Messaging Edge</em>'.
     * @see org.eclipse.stp.bpmn.MessagingEdge
     * @generated
     */
    EClass getMessagingEdge();

    /**
     * Returns the meta object for the container reference '{@link org.eclipse.stp.bpmn.MessagingEdge#getBpmnDiagram <em>Bpmn Diagram</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Bpmn Diagram</em>'.
     * @see org.eclipse.stp.bpmn.MessagingEdge#getBpmnDiagram()
     * @see #getMessagingEdge()
     * @generated
     */
    EReference getMessagingEdge_BpmnDiagram();

    /**
     * Returns the meta object for the reference '{@link org.eclipse.stp.bpmn.MessagingEdge#getSource <em>Source</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Source</em>'.
     * @see org.eclipse.stp.bpmn.MessagingEdge#getSource()
     * @see #getMessagingEdge()
     * @generated
     */
    EReference getMessagingEdge_Source();

    /**
     * Returns the meta object for the reference '{@link org.eclipse.stp.bpmn.MessagingEdge#getTarget <em>Target</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Target</em>'.
     * @see org.eclipse.stp.bpmn.MessagingEdge#getTarget()
     * @see #getMessagingEdge()
     * @generated
     */
    EReference getMessagingEdge_Target();

    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.NamedBpmnObject <em>Named Bpmn Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Named Bpmn Object</em>'.
     * @see org.eclipse.stp.bpmn.NamedBpmnObject
     * @generated
     */
    EClass getNamedBpmnObject();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.stp.bpmn.NamedBpmnObject#getDocumentation <em>Documentation</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Documentation</em>'.
     * @see org.eclipse.stp.bpmn.NamedBpmnObject#getDocumentation()
     * @see #getNamedBpmnObject()
     * @generated
     */
    EAttribute getNamedBpmnObject_Documentation();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.stp.bpmn.NamedBpmnObject#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.eclipse.stp.bpmn.NamedBpmnObject#getName()
     * @see #getNamedBpmnObject()
     * @generated
     */
    EAttribute getNamedBpmnObject_Name();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.stp.bpmn.NamedBpmnObject#getNcname <em>Ncname</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Ncname</em>'.
     * @see org.eclipse.stp.bpmn.NamedBpmnObject#getNcname()
     * @see #getNamedBpmnObject()
     * @generated
     */
    EAttribute getNamedBpmnObject_Ncname();

    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.Pool <em>Pool</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Pool</em>'.
     * @see org.eclipse.stp.bpmn.Pool
     * @generated
     */
    EClass getPool();

    /**
     * Returns the meta object for the containment reference list '{@link org.eclipse.stp.bpmn.Pool#getLanes <em>Lanes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Lanes</em>'.
     * @see org.eclipse.stp.bpmn.Pool#getLanes()
     * @see #getPool()
     * @generated
     */
    EReference getPool_Lanes();

    /**
     * Returns the meta object for the container reference '{@link org.eclipse.stp.bpmn.Pool#getBpmnDiagram <em>Bpmn Diagram</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Bpmn Diagram</em>'.
     * @see org.eclipse.stp.bpmn.Pool#getBpmnDiagram()
     * @see #getPool()
     * @generated
     */
    EReference getPool_BpmnDiagram();

    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.SequenceEdge <em>Sequence Edge</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Sequence Edge</em>'.
     * @see org.eclipse.stp.bpmn.SequenceEdge
     * @generated
     */
    EClass getSequenceEdge();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.stp.bpmn.SequenceEdge#getConditionType <em>Condition Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Condition Type</em>'.
     * @see org.eclipse.stp.bpmn.SequenceEdge#getConditionType()
     * @see #getSequenceEdge()
     * @generated
     */
    EAttribute getSequenceEdge_ConditionType();

    /**
     * Returns the meta object for the container reference '{@link org.eclipse.stp.bpmn.SequenceEdge#getGraph <em>Graph</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Graph</em>'.
     * @see org.eclipse.stp.bpmn.SequenceEdge#getGraph()
     * @see #getSequenceEdge()
     * @generated
     */
    EReference getSequenceEdge_Graph();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.stp.bpmn.SequenceEdge#isIsDefault <em>Is Default</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Is Default</em>'.
     * @see org.eclipse.stp.bpmn.SequenceEdge#isIsDefault()
     * @see #getSequenceEdge()
     * @generated
     */
    EAttribute getSequenceEdge_IsDefault();

    /**
     * Returns the meta object for the reference '{@link org.eclipse.stp.bpmn.SequenceEdge#getSource <em>Source</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Source</em>'.
     * @see org.eclipse.stp.bpmn.SequenceEdge#getSource()
     * @see #getSequenceEdge()
     * @generated
     */
    EReference getSequenceEdge_Source();

    /**
     * Returns the meta object for the reference '{@link org.eclipse.stp.bpmn.SequenceEdge#getTarget <em>Target</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Target</em>'.
     * @see org.eclipse.stp.bpmn.SequenceEdge#getTarget()
     * @see #getSequenceEdge()
     * @generated
     */
    EReference getSequenceEdge_Target();

    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.SubProcess <em>Sub Process</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Sub Process</em>'.
     * @see org.eclipse.stp.bpmn.SubProcess
     * @generated
     */
    EClass getSubProcess();

    /**
     * Returns the meta object for the containment reference list '{@link org.eclipse.stp.bpmn.SubProcess#getEventHandlers <em>Event Handlers</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Event Handlers</em>'.
     * @see org.eclipse.stp.bpmn.SubProcess#getEventHandlers()
     * @see #getSubProcess()
     * @generated
     */
    EReference getSubProcess_EventHandlers();

    /**
     * Returns the meta object for the attribute '{@link org.eclipse.stp.bpmn.SubProcess#isIsTransaction <em>Is Transaction</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Is Transaction</em>'.
     * @see org.eclipse.stp.bpmn.SubProcess#isIsTransaction()
     * @see #getSubProcess()
     * @generated
     */
    EAttribute getSubProcess_IsTransaction();

    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.TextAnnotation <em>Text Annotation</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for class '<em>Text Annotation</em>'.
     * @see org.eclipse.stp.bpmn.TextAnnotation
     * @generated
     */
	EClass getTextAnnotation();

    /**
     * Returns the meta object for class '{@link org.eclipse.stp.bpmn.Vertex <em>Vertex</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Vertex</em>'.
     * @see org.eclipse.stp.bpmn.Vertex
     * @generated
     */
    EClass getVertex();

    /**
     * Returns the meta object for the reference list '{@link org.eclipse.stp.bpmn.Vertex#getOutgoingEdges <em>Outgoing Edges</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Outgoing Edges</em>'.
     * @see org.eclipse.stp.bpmn.Vertex#getOutgoingEdges()
     * @see #getVertex()
     * @generated
     */
    EReference getVertex_OutgoingEdges();

    /**
     * Returns the meta object for the reference list '{@link org.eclipse.stp.bpmn.Vertex#getIncomingEdges <em>Incoming Edges</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Incoming Edges</em>'.
     * @see org.eclipse.stp.bpmn.Vertex#getIncomingEdges()
     * @see #getVertex()
     * @generated
     */
    EReference getVertex_IncomingEdges();

    /**
     * Returns the meta object for the container reference '{@link org.eclipse.stp.bpmn.Vertex#getGraph <em>Graph</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Graph</em>'.
     * @see org.eclipse.stp.bpmn.Vertex#getGraph()
     * @see #getVertex()
     * @generated
     */
    EReference getVertex_Graph();

    /**
     * Returns the meta object for enum '{@link org.eclipse.stp.bpmn.ActivityType <em>Activity Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Activity Type</em>'.
     * @see org.eclipse.stp.bpmn.ActivityType
     * @generated
     */
    EEnum getActivityType();

    /**
     * Returns the meta object for enum '{@link org.eclipse.stp.bpmn.DirectionType <em>Direction Type</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Direction Type</em>'.
     * @see org.eclipse.stp.bpmn.DirectionType
     * @generated
     */
	EEnum getDirectionType();

    /**
     * Returns the meta object for enum '{@link org.eclipse.stp.bpmn.SequenceFlowConditionType <em>Sequence Flow Condition Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Sequence Flow Condition Type</em>'.
     * @see org.eclipse.stp.bpmn.SequenceFlowConditionType
     * @generated
     */
    EEnum getSequenceFlowConditionType();

    /**
     * Returns the meta object for data type '{@link org.eclipse.stp.bpmn.ActivityType <em>Activity Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Activity Type Object</em>'.
     * @see org.eclipse.stp.bpmn.ActivityType
     * @model instanceClass="org.eclipse.stp.bpmn.ActivityType"
     *        extendedMetaData="name='ActivityType:Object' baseType='ActivityType'"
     * @generated
     */
    EDataType getActivityTypeObject();

    /**
     * Returns the meta object for data type '{@link org.eclipse.stp.bpmn.DirectionType <em>Direction Type Object</em>}'.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Direction Type Object</em>'.
     * @see org.eclipse.stp.bpmn.DirectionType
     * @model instanceClass="org.eclipse.stp.bpmn.DirectionType"
     *        extendedMetaData="name='direction_._type:Object' baseType='direction_._type'"
     * @generated
     */
	EDataType getDirectionTypeObject();

    /**
     * Returns the meta object for data type '{@link org.eclipse.stp.bpmn.SequenceFlowConditionType <em>Sequence Flow Condition Type Object</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for data type '<em>Sequence Flow Condition Type Object</em>'.
     * @see org.eclipse.stp.bpmn.SequenceFlowConditionType
     * @model instanceClass="org.eclipse.stp.bpmn.SequenceFlowConditionType"
     *        extendedMetaData="name='SequenceFlowConditionType:Object' baseType='SequenceFlowConditionType'"
     * @generated
     */
    EDataType getSequenceFlowConditionTypeObject();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    BpmnFactory getBpmnFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals  {
        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.ActivityImpl <em>Activity</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.ActivityImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getActivity()
         * @generated
         */
        EClass ACTIVITY = eINSTANCE.getActivity();

        /**
         * The meta object literal for the '<em><b>Groups</b></em>' reference list feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference ACTIVITY__GROUPS = eINSTANCE.getActivity_Groups();

        /**
         * The meta object literal for the '<em><b>Activity Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ACTIVITY__ACTIVITY_TYPE = eINSTANCE.getActivity_ActivityType();

        /**
         * The meta object literal for the '<em><b>Event Handler For</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__EVENT_HANDLER_FOR = eINSTANCE.getActivity_EventHandlerFor();

        /**
         * The meta object literal for the '<em><b>Lane</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ACTIVITY__LANE = eINSTANCE.getActivity_Lane();

        /**
         * The meta object literal for the '<em><b>Looping</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ACTIVITY__LOOPING = eINSTANCE.getActivity_Looping();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.ArtifactImpl <em>Artifact</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.ArtifactImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getArtifact()
         * @generated
         */
		EClass ARTIFACT = eINSTANCE.getArtifact();

        /**
         * The meta object literal for the '<em><b>Associations</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ARTIFACT__ASSOCIATIONS = eINSTANCE.getArtifact_Associations();

        /**
         * The meta object literal for the '<em><b>Artifacts Container</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ARTIFACT__ARTIFACTS_CONTAINER = eINSTANCE.getArtifact_ArtifactsContainer();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.ArtifactsContainerImpl <em>Artifacts Container</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.ArtifactsContainerImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getArtifactsContainer()
         * @generated
         */
        EClass ARTIFACTS_CONTAINER = eINSTANCE.getArtifactsContainer();

        /**
         * The meta object literal for the '<em><b>Artifacts</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ARTIFACTS_CONTAINER__ARTIFACTS = eINSTANCE.getArtifactsContainer_Artifacts();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.AssociationImpl <em>Association</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.AssociationImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getAssociation()
         * @generated
         */
		EClass ASSOCIATION = eINSTANCE.getAssociation();

        /**
         * The meta object literal for the '<em><b>Direction</b></em>' attribute feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EAttribute ASSOCIATION__DIRECTION = eINSTANCE.getAssociation_Direction();

        /**
         * The meta object literal for the '<em><b>Source</b></em>' container reference feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference ASSOCIATION__SOURCE = eINSTANCE.getAssociation_Source();

        /**
         * The meta object literal for the '<em><b>Target</b></em>' reference feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference ASSOCIATION__TARGET = eINSTANCE.getAssociation_Target();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.AssociationTargetImpl <em>Association Target</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.AssociationTargetImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getAssociationTarget()
         * @generated
         */
        EClass ASSOCIATION_TARGET = eINSTANCE.getAssociationTarget();

        /**
         * The meta object literal for the '<em><b>Associations</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ASSOCIATION_TARGET__ASSOCIATIONS = eINSTANCE.getAssociationTarget_Associations();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.BpmnDiagramImpl <em>Diagram</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.BpmnDiagramImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getBpmnDiagram()
         * @generated
         */
        EClass BPMN_DIAGRAM = eINSTANCE.getBpmnDiagram();

        /**
         * The meta object literal for the '<em><b>Pools</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference BPMN_DIAGRAM__POOLS = eINSTANCE.getBpmnDiagram_Pools();

        /**
         * The meta object literal for the '<em><b>Messages</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference BPMN_DIAGRAM__MESSAGES = eINSTANCE.getBpmnDiagram_Messages();

        /**
         * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BPMN_DIAGRAM__AUTHOR = eINSTANCE.getBpmnDiagram_Author();

        /**
         * The meta object literal for the '<em><b>Title</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute BPMN_DIAGRAM__TITLE = eINSTANCE.getBpmnDiagram_Title();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.DataObjectImpl <em>Data Object</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.DataObjectImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getDataObject()
         * @generated
         */
		EClass DATA_OBJECT = eINSTANCE.getDataObject();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.GraphImpl <em>Graph</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.GraphImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getGraph()
         * @generated
         */
        EClass GRAPH = eINSTANCE.getGraph();

        /**
         * The meta object literal for the '<em><b>Vertices</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GRAPH__VERTICES = eINSTANCE.getGraph_Vertices();

        /**
         * The meta object literal for the '<em><b>Sequence Edges</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference GRAPH__SEQUENCE_EDGES = eINSTANCE.getGraph_SequenceEdges();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.GroupImpl <em>Group</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.GroupImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getGroup()
         * @generated
         */
		EClass GROUP = eINSTANCE.getGroup();

        /**
         * The meta object literal for the '<em><b>Activities</b></em>' reference list feature.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		EReference GROUP__ACTIVITIES = eINSTANCE.getGroup_Activities();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.IdentifiableImpl <em>Identifiable</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.IdentifiableImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getIdentifiable()
         * @generated
         */
        EClass IDENTIFIABLE = eINSTANCE.getIdentifiable();

        /**
         * The meta object literal for the '<em><b>ID</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute IDENTIFIABLE__ID = eINSTANCE.getIdentifiable_ID();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.LaneImpl <em>Lane</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.LaneImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getLane()
         * @generated
         */
        EClass LANE = eINSTANCE.getLane();

        /**
         * The meta object literal for the '<em><b>Activities</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LANE__ACTIVITIES = eINSTANCE.getLane_Activities();

        /**
         * The meta object literal for the '<em><b>Pool</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference LANE__POOL = eINSTANCE.getLane_Pool();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.MessageVertexImpl <em>Message Vertex</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.MessageVertexImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getMessageVertex()
         * @generated
         */
        EClass MESSAGE_VERTEX = eINSTANCE.getMessageVertex();

        /**
         * The meta object literal for the '<em><b>Ordered Messages</b></em>' attribute list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute MESSAGE_VERTEX__ORDERED_MESSAGES = eINSTANCE.getMessageVertex_OrderedMessages();

        /**
         * The meta object literal for the '<em><b>Incoming Messages</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MESSAGE_VERTEX__INCOMING_MESSAGES = eINSTANCE.getMessageVertex_IncomingMessages();

        /**
         * The meta object literal for the '<em><b>Outgoing Messages</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MESSAGE_VERTEX__OUTGOING_MESSAGES = eINSTANCE.getMessageVertex_OutgoingMessages();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.MessagingEdgeImpl <em>Messaging Edge</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.MessagingEdgeImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getMessagingEdge()
         * @generated
         */
        EClass MESSAGING_EDGE = eINSTANCE.getMessagingEdge();

        /**
         * The meta object literal for the '<em><b>Bpmn Diagram</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MESSAGING_EDGE__BPMN_DIAGRAM = eINSTANCE.getMessagingEdge_BpmnDiagram();

        /**
         * The meta object literal for the '<em><b>Source</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MESSAGING_EDGE__SOURCE = eINSTANCE.getMessagingEdge_Source();

        /**
         * The meta object literal for the '<em><b>Target</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference MESSAGING_EDGE__TARGET = eINSTANCE.getMessagingEdge_Target();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.NamedBpmnObjectImpl <em>Named Bpmn Object</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.NamedBpmnObjectImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getNamedBpmnObject()
         * @generated
         */
        EClass NAMED_BPMN_OBJECT = eINSTANCE.getNamedBpmnObject();

        /**
         * The meta object literal for the '<em><b>Documentation</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_BPMN_OBJECT__DOCUMENTATION = eINSTANCE.getNamedBpmnObject_Documentation();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_BPMN_OBJECT__NAME = eINSTANCE.getNamedBpmnObject_Name();

        /**
         * The meta object literal for the '<em><b>Ncname</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute NAMED_BPMN_OBJECT__NCNAME = eINSTANCE.getNamedBpmnObject_Ncname();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.PoolImpl <em>Pool</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.PoolImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getPool()
         * @generated
         */
        EClass POOL = eINSTANCE.getPool();

        /**
         * The meta object literal for the '<em><b>Lanes</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference POOL__LANES = eINSTANCE.getPool_Lanes();

        /**
         * The meta object literal for the '<em><b>Bpmn Diagram</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference POOL__BPMN_DIAGRAM = eINSTANCE.getPool_BpmnDiagram();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.SequenceEdgeImpl <em>Sequence Edge</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.SequenceEdgeImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getSequenceEdge()
         * @generated
         */
        EClass SEQUENCE_EDGE = eINSTANCE.getSequenceEdge();

        /**
         * The meta object literal for the '<em><b>Condition Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SEQUENCE_EDGE__CONDITION_TYPE = eINSTANCE.getSequenceEdge_ConditionType();

        /**
         * The meta object literal for the '<em><b>Graph</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SEQUENCE_EDGE__GRAPH = eINSTANCE.getSequenceEdge_Graph();

        /**
         * The meta object literal for the '<em><b>Is Default</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SEQUENCE_EDGE__IS_DEFAULT = eINSTANCE.getSequenceEdge_IsDefault();

        /**
         * The meta object literal for the '<em><b>Source</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SEQUENCE_EDGE__SOURCE = eINSTANCE.getSequenceEdge_Source();

        /**
         * The meta object literal for the '<em><b>Target</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SEQUENCE_EDGE__TARGET = eINSTANCE.getSequenceEdge_Target();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.SubProcessImpl <em>Sub Process</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.SubProcessImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getSubProcess()
         * @generated
         */
        EClass SUB_PROCESS = eINSTANCE.getSubProcess();

        /**
         * The meta object literal for the '<em><b>Event Handlers</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference SUB_PROCESS__EVENT_HANDLERS = eINSTANCE.getSubProcess_EventHandlers();

        /**
         * The meta object literal for the '<em><b>Is Transaction</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute SUB_PROCESS__IS_TRANSACTION = eINSTANCE.getSubProcess_IsTransaction();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.TextAnnotationImpl <em>Text Annotation</em>}' class.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.TextAnnotationImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getTextAnnotation()
         * @generated
         */
		EClass TEXT_ANNOTATION = eINSTANCE.getTextAnnotation();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.impl.VertexImpl <em>Vertex</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.impl.VertexImpl
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getVertex()
         * @generated
         */
        EClass VERTEX = eINSTANCE.getVertex();

        /**
         * The meta object literal for the '<em><b>Outgoing Edges</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference VERTEX__OUTGOING_EDGES = eINSTANCE.getVertex_OutgoingEdges();

        /**
         * The meta object literal for the '<em><b>Incoming Edges</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference VERTEX__INCOMING_EDGES = eINSTANCE.getVertex_IncomingEdges();

        /**
         * The meta object literal for the '<em><b>Graph</b></em>' container reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference VERTEX__GRAPH = eINSTANCE.getVertex_Graph();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.ActivityType <em>Activity Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.ActivityType
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getActivityType()
         * @generated
         */
        EEnum ACTIVITY_TYPE = eINSTANCE.getActivityType();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.DirectionType <em>Direction Type</em>}' enum.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.DirectionType
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getDirectionType()
         * @generated
         */
		EEnum DIRECTION_TYPE = eINSTANCE.getDirectionType();

        /**
         * The meta object literal for the '{@link org.eclipse.stp.bpmn.SequenceFlowConditionType <em>Sequence Flow Condition Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.SequenceFlowConditionType
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getSequenceFlowConditionType()
         * @generated
         */
        EEnum SEQUENCE_FLOW_CONDITION_TYPE = eINSTANCE.getSequenceFlowConditionType();

        /**
         * The meta object literal for the '<em>Activity Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.ActivityType
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getActivityTypeObject()
         * @generated
         */
        EDataType ACTIVITY_TYPE_OBJECT = eINSTANCE.getActivityTypeObject();

        /**
         * The meta object literal for the '<em>Direction Type Object</em>' data type.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.DirectionType
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getDirectionTypeObject()
         * @generated
         */
		EDataType DIRECTION_TYPE_OBJECT = eINSTANCE.getDirectionTypeObject();

	        /**
         * The meta object literal for the '<em>Sequence Flow Condition Type Object</em>' data type.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.eclipse.stp.bpmn.SequenceFlowConditionType
         * @see org.eclipse.stp.bpmn.impl.BpmnPackageImpl#getSequenceFlowConditionTypeObject()
         * @generated
         */
        EDataType SEQUENCE_FLOW_CONDITION_TYPE_OBJECT = eINSTANCE.getSequenceFlowConditionTypeObject();

    }

} //BpmnPackage
