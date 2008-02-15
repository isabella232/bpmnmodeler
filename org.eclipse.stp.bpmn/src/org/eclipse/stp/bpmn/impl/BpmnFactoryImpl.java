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

package org.eclipse.stp.bpmn.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.stp.bpmn.*;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.ArtifactsContainer;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.BpmnFactory;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.DataObject;
import org.eclipse.stp.bpmn.DirectionType;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.Group;
import org.eclipse.stp.bpmn.Identifiable;
import org.eclipse.stp.bpmn.IdentifiableNode;
import org.eclipse.stp.bpmn.Lane;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.NamedBpmnObject;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.TextAnnotation;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.BpmnMessages;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BpmnFactoryImpl extends EFactoryImpl implements BpmnFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static BpmnFactory init() {
        try {
            BpmnFactory theBpmnFactory = (BpmnFactory)EPackage.Registry.INSTANCE.getEFactory("http://stp.eclipse.org/bpmn"); 
            if (theBpmnFactory != null) {
                return theBpmnFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new BpmnFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BpmnFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
				public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case BpmnPackage.ACTIVITY: return createActivity();
            case BpmnPackage.ARTIFACT: return createArtifact();
            case BpmnPackage.ARTIFACTS_CONTAINER: return createArtifactsContainer();
            case BpmnPackage.ASSOCIATION: return createAssociation();
            case BpmnPackage.ASSOCIATION_TARGET: return createAssociationTarget();
            case BpmnPackage.BPMN_DIAGRAM: return createBpmnDiagram();
            case BpmnPackage.DATA_OBJECT: return createDataObject();
            case BpmnPackage.GRAPH: return createGraph();
            case BpmnPackage.GROUP: return createGroup();
            case BpmnPackage.IDENTIFIABLE: return createIdentifiable();
            case BpmnPackage.LANE: return createLane();
            case BpmnPackage.MESSAGE_VERTEX: return createMessageVertex();
            case BpmnPackage.MESSAGING_EDGE: return createMessagingEdge();
            case BpmnPackage.NAMED_BPMN_OBJECT: return createNamedBpmnObject();
            case BpmnPackage.POOL: return createPool();
            case BpmnPackage.SEQUENCE_EDGE: return createSequenceEdge();
            case BpmnPackage.SUB_PROCESS: return createSubProcess();
            case BpmnPackage.TEXT_ANNOTATION: return createTextAnnotation();
            case BpmnPackage.VERTEX: return createVertex();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
				public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
            case BpmnPackage.ACTIVITY_TYPE:
                return createActivityTypeFromString(eDataType, initialValue);
            case BpmnPackage.DIRECTION_TYPE:
                return createDirectionTypeFromString(eDataType, initialValue);
            case BpmnPackage.SEQUENCE_FLOW_CONDITION_TYPE:
                return createSequenceFlowConditionTypeFromString(eDataType, initialValue);
            case BpmnPackage.ACTIVITY_TYPE_OBJECT:
                return createActivityTypeObjectFromString(eDataType, initialValue);
            case BpmnPackage.DIRECTION_TYPE_OBJECT:
                return createDirectionTypeObjectFromString(eDataType, initialValue);
            case BpmnPackage.SEQUENCE_FLOW_CONDITION_TYPE_OBJECT:
                return createSequenceFlowConditionTypeObjectFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
				public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
            case BpmnPackage.ACTIVITY_TYPE:
                return convertActivityTypeToString(eDataType, instanceValue);
            case BpmnPackage.DIRECTION_TYPE:
                return convertDirectionTypeToString(eDataType, instanceValue);
            case BpmnPackage.SEQUENCE_FLOW_CONDITION_TYPE:
                return convertSequenceFlowConditionTypeToString(eDataType, instanceValue);
            case BpmnPackage.ACTIVITY_TYPE_OBJECT:
                return convertActivityTypeObjectToString(eDataType, instanceValue);
            case BpmnPackage.DIRECTION_TYPE_OBJECT:
                return convertDirectionTypeObjectToString(eDataType, instanceValue);
            case BpmnPackage.SEQUENCE_FLOW_CONDITION_TYPE_OBJECT:
                return convertSequenceFlowConditionTypeObjectToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @notgenerated added the UUID
     */
    public Activity createActivity() {
        ActivityImpl activity = new ActivityImpl();
        activity.iD = EcoreUtil.generateUUID();
        return activity;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public Artifact createArtifact() {
        ArtifactImpl artifact = new ArtifactImpl();
        return artifact;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ArtifactsContainer createArtifactsContainer() {
        ArtifactsContainerImpl artifactsContainer = new ArtifactsContainerImpl();
        return artifactsContainer;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public Association createAssociation() {
        AssociationImpl association = new AssociationImpl();
        return association;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AssociationTarget createAssociationTarget() {
        AssociationTargetImpl associationTarget = new AssociationTargetImpl();
        return associationTarget;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @notgenerated added the UUID
     */
    public BpmnDiagram createBpmnDiagram() {
        BpmnDiagramImpl bpmnDiagram = new BpmnDiagramImpl();
        bpmnDiagram.iD = EcoreUtil.generateUUID();
        return bpmnDiagram;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @notgenerated added the UUID
     */
	public DataObject createDataObject() {
        DataObjectImpl dataObject = new DataObjectImpl();
        dataObject.iD = EcoreUtil.generateUUID();
        return dataObject;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Graph createGraph() {
        GraphImpl graph = new GraphImpl();
        return graph;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @notgenerated added the UUID
     */
	public Group createGroup() {
        GroupImpl group = new GroupImpl();
        group.iD = EcoreUtil.generateUUID();
        return group;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Identifiable createIdentifiable() {
        IdentifiableImpl identifiable = new IdentifiableImpl();
        return identifiable;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @notgenerated added the UUID
     */
	public Lane createLane() {
        LaneImpl lane = new LaneImpl();
        lane.iD = EcoreUtil.generateUUID();
        return lane;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MessageVertex createMessageVertex() {
        MessageVertexImpl messageVertex = new MessageVertexImpl();
        return messageVertex;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @notgenerated added the UUID
     */
    public MessagingEdge createMessagingEdge() {
        MessagingEdgeImpl messagingEdge = new MessagingEdgeImpl();
        messagingEdge.iD = EcoreUtil.generateUUID();
        return messagingEdge;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NamedBpmnObject createNamedBpmnObject() {
        NamedBpmnObjectImpl namedBpmnObject = new NamedBpmnObjectImpl();
        return namedBpmnObject;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @notgenerated added the UUID
     */
	public Pool createPool() {
        PoolImpl pool = new PoolImpl();
        pool.setID(EcoreUtil.generateUUID());
        return pool;
    }

    /**
     * <!-- begin-user-doc -->
     * Added setId with a unique ID according to the post here:
     * http://dev.eclipse.org/newslists/news.eclipse.tools.emf/msg18192.html
     * <!-- end-user-doc -->
     * @notgenerated
     */
    public SequenceEdge createSequenceEdge() {
        SequenceEdgeImpl edge = new SequenceEdgeImpl();
        edge.iD = EcoreUtil.generateUUID();
        return edge;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @notgenerated added the UUID
     */
	public SubProcess createSubProcess() {
        SubProcessImpl subProcess = new SubProcessImpl();
        subProcess.iD = EcoreUtil.generateUUID();
        return subProcess;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @notgenerated added the UUID
     */
	public TextAnnotation createTextAnnotation() {
        TextAnnotationImpl textAnnotation = new TextAnnotationImpl();
        textAnnotation.iD = EcoreUtil.generateUUID();
        return textAnnotation;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @notgenerated added the uiid
     */
	public Vertex createVertex() {
        VertexImpl vertex = new VertexImpl();
        vertex.iD = EcoreUtil.generateUUID();
        return vertex;
    }

    /**
     * <!-- begin-user-doc -->
     * for FT there seem to be process with activityType 
     * value="Sub-Process" instead of SubProcess
     * <!-- end-user-doc -->
     * @generated NOT for FT there seem to be process with activityType 
     * value="Sub-Process" instead of SubProcess
     */
    public ActivityType createActivityTypeFromString(EDataType eDataType, String initialValue) {
        ActivityType result = ActivityType.get(initialValue);
        if (result == null) {
            result = ActivityType.get(initialValue);
            if (result == null) {
            	throw new IllegalArgumentException(
            			BpmnMessages.bind(BpmnMessages.BpmnFactoryImpl_value_invalidEnumerator,
            					initialValue, 
            					eDataType.getName()));
            }
        }
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertActivityTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public DirectionType createDirectionTypeFromString(EDataType eDataType, String initialValue) {
        DirectionType result = DirectionType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String convertDirectionTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SequenceFlowConditionType createSequenceFlowConditionTypeFromString(EDataType eDataType, String initialValue) {
        SequenceFlowConditionType result = SequenceFlowConditionType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertSequenceFlowConditionTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ActivityType createActivityTypeObjectFromString(EDataType eDataType, String initialValue) {
        return createActivityTypeFromString(BpmnPackage.Literals.ACTIVITY_TYPE, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertActivityTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertActivityTypeToString(BpmnPackage.Literals.ACTIVITY_TYPE, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public DirectionType createDirectionTypeObjectFromString(EDataType eDataType, String initialValue) {
        return createDirectionTypeFromString(BpmnPackage.Literals.DIRECTION_TYPE, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public String convertDirectionTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertDirectionTypeToString(BpmnPackage.Literals.DIRECTION_TYPE, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SequenceFlowConditionType createSequenceFlowConditionTypeObjectFromString(EDataType eDataType, String initialValue) {
        return createSequenceFlowConditionTypeFromString(BpmnPackage.Literals.SEQUENCE_FLOW_CONDITION_TYPE, initialValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertSequenceFlowConditionTypeObjectToString(EDataType eDataType, Object instanceValue) {
        return convertSequenceFlowConditionTypeToString(BpmnPackage.Literals.SEQUENCE_FLOW_CONDITION_TYPE, instanceValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BpmnPackage getBpmnPackage() {
        return (BpmnPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
				public static BpmnPackage getPackage() {
        return BpmnPackage.eINSTANCE;
    }

} //BpmnFactoryImpl
