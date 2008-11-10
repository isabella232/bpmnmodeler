/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.stp.bpmn.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.ArtifactsContainer;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.AssociationTarget;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.BpmnFactory;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.DataObject;
import org.eclipse.stp.bpmn.DirectionType;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.Group;
import org.eclipse.stp.bpmn.Identifiable;
import org.eclipse.stp.bpmn.Lane;
import org.eclipse.stp.bpmn.MessageVertex;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.NamedBpmnObject;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SequenceFlowConditionType;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.TextAnnotation;
import org.eclipse.stp.bpmn.Vertex;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BpmnPackageImpl extends EPackageImpl implements BpmnPackage {
    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass activityEClass = null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass artifactEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass artifactsContainerEClass = null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass associationEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass associationTargetEClass = null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass bpmnDiagramEClass = null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass dataObjectEClass = null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass graphEClass = null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass groupEClass = null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass identifiableEClass = null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass laneEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass messageVertexEClass = null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass messagingEdgeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass namedBpmnObjectEClass = null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass poolEClass = null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass sequenceEdgeEClass = null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass subProcessEClass = null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass textAnnotationEClass = null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EClass vertexEClass = null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EEnum activityTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EEnum directionTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum sequenceFlowConditionTypeEEnum = null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EDataType activityTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private EDataType directionTypeObjectEDataType = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType sequenceFlowConditionTypeObjectEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.eclipse.stp.bpmn.BpmnPackage#eNS_URI
     * @see #init()
     * @generated
     */
	private BpmnPackageImpl() {
        super(eNS_URI, BpmnFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this
     * model, and for any others upon which it depends.  Simple
     * dependencies are satisfied by calling this method on all
     * dependent packages before doing anything else.  This method drives
     * initialization for interdependent packages directly, in parallel
     * with this package, itself.
     * <p>Of this package and its interdependencies, all packages which
     * have not yet been registered by their URI values are first created
     * and registered.  The packages are then initialized in two steps:
     * meta-model objects for all of the packages are created before any
     * are initialized, since one package's meta-model objects may refer to
     * those of another.
     * <p>Invocation of this method will not affect any packages that have
     * already been initialized.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
	public static BpmnPackage init() {
        if (isInited) return (BpmnPackage)EPackage.Registry.INSTANCE.getEPackage(BpmnPackage.eNS_URI);

        // Obtain or create and register package
        BpmnPackageImpl theBpmnPackage = (BpmnPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof BpmnPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new BpmnPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        EcorePackage.eINSTANCE.eClass();
        XMLTypePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theBpmnPackage.createPackageContents();

        // Initialize created meta-data
        theBpmnPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theBpmnPackage.freeze();

        return theBpmnPackage;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getActivity() {
        return activityEClass;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getActivity_Groups() {
        return (EReference)activityEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getActivity_Lanes() {
        return (EReference)activityEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getActivity_ActivityType() {
        return (EAttribute)activityEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getActivity_EventHandlerFor() {
        return (EReference)activityEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getActivity_Looping() {
        return (EAttribute)activityEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getArtifact() {
        return artifactEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getArtifact_Associations() {
        return (EReference)artifactEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getArtifact_ArtifactsContainer() {
        return (EReference)artifactEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getArtifactsContainer() {
        return artifactsContainerEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getArtifactsContainer_Artifacts() {
        return (EReference)artifactsContainerEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getAssociation() {
        return associationEClass;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getAssociation_Direction() {
        return (EAttribute)associationEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getAssociation_Source() {
        return (EReference)associationEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAssociation_Target() {
        return (EReference)associationEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAssociationTarget() {
        return associationTargetEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAssociationTarget_Associations() {
        return (EReference)associationTargetEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getBpmnDiagram() {
        return bpmnDiagramEClass;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getBpmnDiagram_Pools() {
        return (EReference)bpmnDiagramEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getBpmnDiagram_Messages() {
        return (EReference)bpmnDiagramEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getBpmnDiagram_Author() {
        return (EAttribute)bpmnDiagramEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getBpmnDiagram_Title() {
        return (EAttribute)bpmnDiagramEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getDataObject() {
        return dataObjectEClass;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getGraph() {
        return graphEClass;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getGraph_Vertices() {
        return (EReference)graphEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getGraph_SequenceEdges() {
        return (EReference)graphEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getGroup() {
        return groupEClass;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getGroup_Activities() {
        return (EReference)groupEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getIdentifiable() {
        return identifiableEClass;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getIdentifiable_ID() {
        return (EAttribute)identifiableEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getLane() {
        return laneEClass;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getLane_Activities() {
        return (EReference)laneEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getLane_Pool() {
        return (EReference)laneEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getMessageVertex() {
        return messageVertexEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMessageVertex_OrderedMessages() {
        return (EAttribute)messageVertexEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getMessageVertex_IncomingMessages() {
        return (EReference)messageVertexEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getMessageVertex_OutgoingMessages() {
        return (EReference)messageVertexEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getMessagingEdge() {
        return messagingEdgeEClass;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getMessagingEdge_BpmnDiagram() {
        return (EReference)messagingEdgeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getMessagingEdge_Source() {
        return (EReference)messagingEdgeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getMessagingEdge_Target() {
        return (EReference)messagingEdgeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getNamedBpmnObject() {
        return namedBpmnObjectEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNamedBpmnObject_Documentation() {
        return (EAttribute)namedBpmnObjectEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNamedBpmnObject_Name() {
        return (EAttribute)namedBpmnObjectEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getNamedBpmnObject_Ncname() {
        return (EAttribute)namedBpmnObjectEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getPool() {
        return poolEClass;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getPool_Lanes() {
        return (EReference)poolEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getPool_BpmnDiagram() {
        return (EReference)poolEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getSequenceEdge() {
        return sequenceEdgeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSequenceEdge_ConditionType() {
        return (EAttribute)sequenceEdgeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getSequenceEdge_Graph() {
        return (EReference)sequenceEdgeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getSequenceEdge_IsDefault() {
        return (EAttribute)sequenceEdgeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getSequenceEdge_Source() {
        return (EReference)sequenceEdgeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getSequenceEdge_Target() {
        return (EReference)sequenceEdgeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getSubProcess() {
        return subProcessEClass;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getSubProcess_EventHandlers() {
        return (EReference)subProcessEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EAttribute getSubProcess_IsTransaction() {
        return (EAttribute)subProcessEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getTextAnnotation() {
        return textAnnotationEClass;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EClass getVertex() {
        return vertexEClass;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getVertex_OutgoingEdges() {
        return (EReference)vertexEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getVertex_IncomingEdges() {
        return (EReference)vertexEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EReference getVertex_Graph() {
        return (EReference)vertexEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EEnum getActivityType() {
        return activityTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EEnum getDirectionType() {
        return directionTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getSequenceFlowConditionType() {
        return sequenceFlowConditionTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EDataType getActivityTypeObject() {
        return activityTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EDataType getDirectionTypeObject() {
        return directionTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getSequenceFlowConditionTypeObject() {
        return sequenceFlowConditionTypeObjectEDataType;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public BpmnFactory getBpmnFactory() {
        return (BpmnFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        activityEClass = createEClass(ACTIVITY);
        createEReference(activityEClass, ACTIVITY__GROUPS);
        createEReference(activityEClass, ACTIVITY__LANES);
        createEAttribute(activityEClass, ACTIVITY__ACTIVITY_TYPE);
        createEAttribute(activityEClass, ACTIVITY__LOOPING);
        createEReference(activityEClass, ACTIVITY__EVENT_HANDLER_FOR);

        artifactEClass = createEClass(ARTIFACT);
        createEReference(artifactEClass, ARTIFACT__ASSOCIATIONS);
        createEReference(artifactEClass, ARTIFACT__ARTIFACTS_CONTAINER);

        artifactsContainerEClass = createEClass(ARTIFACTS_CONTAINER);
        createEReference(artifactsContainerEClass, ARTIFACTS_CONTAINER__ARTIFACTS);

        associationEClass = createEClass(ASSOCIATION);
        createEAttribute(associationEClass, ASSOCIATION__DIRECTION);
        createEReference(associationEClass, ASSOCIATION__SOURCE);
        createEReference(associationEClass, ASSOCIATION__TARGET);

        associationTargetEClass = createEClass(ASSOCIATION_TARGET);
        createEReference(associationTargetEClass, ASSOCIATION_TARGET__ASSOCIATIONS);

        bpmnDiagramEClass = createEClass(BPMN_DIAGRAM);
        createEReference(bpmnDiagramEClass, BPMN_DIAGRAM__POOLS);
        createEReference(bpmnDiagramEClass, BPMN_DIAGRAM__MESSAGES);
        createEAttribute(bpmnDiagramEClass, BPMN_DIAGRAM__AUTHOR);
        createEAttribute(bpmnDiagramEClass, BPMN_DIAGRAM__TITLE);

        dataObjectEClass = createEClass(DATA_OBJECT);

        graphEClass = createEClass(GRAPH);
        createEReference(graphEClass, GRAPH__VERTICES);
        createEReference(graphEClass, GRAPH__SEQUENCE_EDGES);

        groupEClass = createEClass(GROUP);
        createEReference(groupEClass, GROUP__ACTIVITIES);

        identifiableEClass = createEClass(IDENTIFIABLE);
        createEAttribute(identifiableEClass, IDENTIFIABLE__ID);

        laneEClass = createEClass(LANE);
        createEReference(laneEClass, LANE__ACTIVITIES);
        createEReference(laneEClass, LANE__POOL);

        messageVertexEClass = createEClass(MESSAGE_VERTEX);
        createEAttribute(messageVertexEClass, MESSAGE_VERTEX__ORDERED_MESSAGES);
        createEReference(messageVertexEClass, MESSAGE_VERTEX__INCOMING_MESSAGES);
        createEReference(messageVertexEClass, MESSAGE_VERTEX__OUTGOING_MESSAGES);

        messagingEdgeEClass = createEClass(MESSAGING_EDGE);
        createEReference(messagingEdgeEClass, MESSAGING_EDGE__SOURCE);
        createEReference(messagingEdgeEClass, MESSAGING_EDGE__TARGET);
        createEReference(messagingEdgeEClass, MESSAGING_EDGE__BPMN_DIAGRAM);

        namedBpmnObjectEClass = createEClass(NAMED_BPMN_OBJECT);
        createEAttribute(namedBpmnObjectEClass, NAMED_BPMN_OBJECT__DOCUMENTATION);
        createEAttribute(namedBpmnObjectEClass, NAMED_BPMN_OBJECT__NAME);
        createEAttribute(namedBpmnObjectEClass, NAMED_BPMN_OBJECT__NCNAME);

        poolEClass = createEClass(POOL);
        createEReference(poolEClass, POOL__LANES);
        createEReference(poolEClass, POOL__BPMN_DIAGRAM);

        sequenceEdgeEClass = createEClass(SEQUENCE_EDGE);
        createEAttribute(sequenceEdgeEClass, SEQUENCE_EDGE__CONDITION_TYPE);
        createEAttribute(sequenceEdgeEClass, SEQUENCE_EDGE__IS_DEFAULT);
        createEReference(sequenceEdgeEClass, SEQUENCE_EDGE__SOURCE);
        createEReference(sequenceEdgeEClass, SEQUENCE_EDGE__TARGET);
        createEReference(sequenceEdgeEClass, SEQUENCE_EDGE__GRAPH);

        subProcessEClass = createEClass(SUB_PROCESS);
        createEReference(subProcessEClass, SUB_PROCESS__EVENT_HANDLERS);
        createEAttribute(subProcessEClass, SUB_PROCESS__IS_TRANSACTION);

        textAnnotationEClass = createEClass(TEXT_ANNOTATION);

        vertexEClass = createEClass(VERTEX);
        createEReference(vertexEClass, VERTEX__OUTGOING_EDGES);
        createEReference(vertexEClass, VERTEX__INCOMING_EDGES);
        createEReference(vertexEClass, VERTEX__GRAPH);

        // Create enums
        activityTypeEEnum = createEEnum(ACTIVITY_TYPE);
        directionTypeEEnum = createEEnum(DIRECTION_TYPE);
        sequenceFlowConditionTypeEEnum = createEEnum(SEQUENCE_FLOW_CONDITION_TYPE);

        // Create data types
        activityTypeObjectEDataType = createEDataType(ACTIVITY_TYPE_OBJECT);
        directionTypeObjectEDataType = createEDataType(DIRECTION_TYPE_OBJECT);
        sequenceFlowConditionTypeObjectEDataType = createEDataType(SEQUENCE_FLOW_CONDITION_TYPE_OBJECT);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);
        EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        activityEClass.getESuperTypes().add(this.getVertex());
        activityEClass.getESuperTypes().add(this.getMessageVertex());
        artifactEClass.getESuperTypes().add(this.getIdentifiable());
        artifactEClass.getESuperTypes().add(this.getNamedBpmnObject());
        artifactsContainerEClass.getESuperTypes().add(this.getNamedBpmnObject());
        associationEClass.getESuperTypes().add(theEcorePackage.getEModelElement());
        associationTargetEClass.getESuperTypes().add(this.getIdentifiable());
        bpmnDiagramEClass.getESuperTypes().add(this.getIdentifiable());
        bpmnDiagramEClass.getESuperTypes().add(this.getArtifactsContainer());
        dataObjectEClass.getESuperTypes().add(this.getArtifact());
        graphEClass.getESuperTypes().add(this.getAssociationTarget());
        graphEClass.getESuperTypes().add(this.getArtifactsContainer());
        groupEClass.getESuperTypes().add(this.getArtifact());
        identifiableEClass.getESuperTypes().add(theEcorePackage.getEModelElement());
        laneEClass.getESuperTypes().add(this.getAssociationTarget());
        laneEClass.getESuperTypes().add(this.getNamedBpmnObject());
        messageVertexEClass.getESuperTypes().add(this.getNamedBpmnObject());
        messageVertexEClass.getESuperTypes().add(this.getIdentifiable());
        messagingEdgeEClass.getESuperTypes().add(this.getAssociationTarget());
        messagingEdgeEClass.getESuperTypes().add(this.getNamedBpmnObject());
        poolEClass.getESuperTypes().add(this.getGraph());
        poolEClass.getESuperTypes().add(this.getMessageVertex());
        sequenceEdgeEClass.getESuperTypes().add(this.getAssociationTarget());
        sequenceEdgeEClass.getESuperTypes().add(this.getNamedBpmnObject());
        subProcessEClass.getESuperTypes().add(this.getActivity());
        subProcessEClass.getESuperTypes().add(this.getGraph());
        textAnnotationEClass.getESuperTypes().add(this.getArtifact());
        vertexEClass.getESuperTypes().add(this.getAssociationTarget());

        // Initialize classes and features; add operations and parameters
        initEClass(activityEClass, Activity.class, "Activity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getActivity_Groups(), this.getGroup(), this.getGroup_Activities(), "groups", null, 0, -1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getActivity_Lanes(), this.getLane(), this.getLane_Activities(), "lanes", null, 0, -1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getActivity_ActivityType(), this.getActivityType(), "activityType", "Task", 0, 1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getActivity_Looping(), theXMLTypePackage.getBoolean(), "looping", null, 0, 1, Activity.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getActivity_EventHandlerFor(), this.getSubProcess(), this.getSubProcess_EventHandlers(), "eventHandlerFor", null, 0, 1, Activity.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(artifactEClass, Artifact.class, "Artifact", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getArtifact_Associations(), this.getAssociation(), this.getAssociation_Source(), "associations", null, 0, -1, Artifact.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getArtifact_ArtifactsContainer(), this.getArtifactsContainer(), this.getArtifactsContainer_Artifacts(), "artifactsContainer", null, 0, 1, Artifact.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(artifactsContainerEClass, ArtifactsContainer.class, "ArtifactsContainer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getArtifactsContainer_Artifacts(), this.getArtifact(), this.getArtifact_ArtifactsContainer(), "artifacts", null, 0, -1, ArtifactsContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(associationEClass, Association.class, "Association", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAssociation_Direction(), this.getDirectionType(), "direction", null, 0, 1, Association.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAssociation_Source(), this.getArtifact(), this.getArtifact_Associations(), "source", null, 0, 1, Association.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAssociation_Target(), this.getAssociationTarget(), this.getAssociationTarget_Associations(), "target", null, 0, 1, Association.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(associationTargetEClass, AssociationTarget.class, "AssociationTarget", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAssociationTarget_Associations(), this.getAssociation(), this.getAssociation_Target(), "associations", null, 0, -1, AssociationTarget.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(bpmnDiagramEClass, BpmnDiagram.class, "BpmnDiagram", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getBpmnDiagram_Pools(), this.getPool(), this.getPool_BpmnDiagram(), "pools", null, 0, -1, BpmnDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getBpmnDiagram_Messages(), this.getMessagingEdge(), this.getMessagingEdge_BpmnDiagram(), "messages", null, 0, -1, BpmnDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getBpmnDiagram_Author(), theXMLTypePackage.getString(), "author", null, 0, 1, BpmnDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getBpmnDiagram_Title(), theXMLTypePackage.getString(), "title", null, 0, 1, BpmnDiagram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(dataObjectEClass, DataObject.class, "DataObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(graphEClass, Graph.class, "Graph", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getGraph_Vertices(), this.getVertex(), this.getVertex_Graph(), "vertices", null, 0, -1, Graph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getGraph_SequenceEdges(), this.getSequenceEdge(), this.getSequenceEdge_Graph(), "sequenceEdges", null, 0, -1, Graph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(groupEClass, Group.class, "Group", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getGroup_Activities(), this.getActivity(), this.getActivity_Groups(), "activities", null, 0, -1, Group.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(identifiableEClass, Identifiable.class, "Identifiable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getIdentifiable_ID(), theXMLTypePackage.getID(), "iD", null, 0, 1, Identifiable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(laneEClass, Lane.class, "Lane", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getLane_Activities(), this.getActivity(), this.getActivity_Lanes(), "activities", null, 0, -1, Lane.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getLane_Pool(), this.getPool(), this.getPool_Lanes(), "pool", null, 0, 1, Lane.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(messageVertexEClass, MessageVertex.class, "MessageVertex", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getMessageVertex_OrderedMessages(), ecorePackage.getEFeatureMapEntry(), "orderedMessages", null, 0, -1, MessageVertex.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getMessageVertex_IncomingMessages(), this.getMessagingEdge(), this.getMessagingEdge_Target(), "incomingMessages", null, 0, -1, MessageVertex.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
        initEReference(getMessageVertex_OutgoingMessages(), this.getMessagingEdge(), this.getMessagingEdge_Source(), "outgoingMessages", null, 0, -1, MessageVertex.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

        initEClass(messagingEdgeEClass, MessagingEdge.class, "MessagingEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getMessagingEdge_Source(), this.getMessageVertex(), this.getMessageVertex_OutgoingMessages(), "source", null, 0, 1, MessagingEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getMessagingEdge_Target(), this.getMessageVertex(), this.getMessageVertex_IncomingMessages(), "target", null, 0, 1, MessagingEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getMessagingEdge_BpmnDiagram(), this.getBpmnDiagram(), this.getBpmnDiagram_Messages(), "bpmnDiagram", null, 0, 1, MessagingEdge.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(namedBpmnObjectEClass, NamedBpmnObject.class, "NamedBpmnObject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getNamedBpmnObject_Documentation(), theXMLTypePackage.getString(), "documentation", null, 0, 1, NamedBpmnObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getNamedBpmnObject_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, NamedBpmnObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getNamedBpmnObject_Ncname(), theXMLTypePackage.getString(), "ncname", null, 0, 1, NamedBpmnObject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(poolEClass, Pool.class, "Pool", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getPool_Lanes(), this.getLane(), this.getLane_Pool(), "lanes", null, 0, -1, Pool.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getPool_BpmnDiagram(), this.getBpmnDiagram(), this.getBpmnDiagram_Pools(), "bpmnDiagram", null, 0, 1, Pool.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(sequenceEdgeEClass, SequenceEdge.class, "SequenceEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getSequenceEdge_ConditionType(), this.getSequenceFlowConditionType(), "conditionType", null, 0, 1, SequenceEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSequenceEdge_IsDefault(), theXMLTypePackage.getBoolean(), "isDefault", null, 0, 1, SequenceEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getSequenceEdge_Source(), this.getVertex(), this.getVertex_OutgoingEdges(), "source", null, 0, 1, SequenceEdge.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getSequenceEdge_Target(), this.getVertex(), this.getVertex_IncomingEdges(), "target", null, 0, 1, SequenceEdge.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getSequenceEdge_Graph(), this.getGraph(), this.getGraph_SequenceEdges(), "graph", null, 0, 1, SequenceEdge.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(subProcessEClass, SubProcess.class, "SubProcess", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getSubProcess_EventHandlers(), this.getActivity(), this.getActivity_EventHandlerFor(), "eventHandlers", null, 0, -1, SubProcess.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getSubProcess_IsTransaction(), theXMLTypePackage.getBoolean(), "isTransaction", null, 0, 1, SubProcess.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(textAnnotationEClass, TextAnnotation.class, "TextAnnotation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(vertexEClass, Vertex.class, "Vertex", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getVertex_OutgoingEdges(), this.getSequenceEdge(), this.getSequenceEdge_Source(), "outgoingEdges", null, 0, -1, Vertex.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getVertex_IncomingEdges(), this.getSequenceEdge(), this.getSequenceEdge_Target(), "incomingEdges", null, 0, -1, Vertex.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getVertex_Graph(), this.getGraph(), this.getGraph_Vertices(), "graph", null, 0, 1, Vertex.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        initEEnum(activityTypeEEnum, ActivityType.class, "ActivityType");
        addEEnumLiteral(activityTypeEEnum, ActivityType.TASK_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.SUB_PROCESS_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_START_EMPTY_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_START_MESSAGE_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_START_RULE_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_START_TIMER_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_START_LINK_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_START_MULTIPLE_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_INTERMEDIATE_EMPTY_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_INTERMEDIATE_MESSAGE_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_INTERMEDIATE_TIMER_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_INTERMEDIATE_ERROR_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_INTERMEDIATE_COMPENSATION_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_INTERMEDIATE_RULE_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_INTERMEDIATE_LINK_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_INTERMEDIATE_MULTIPLE_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_INTERMEDIATE_CANCEL_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_END_EMPTY_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_END_MESSAGE_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_END_ERROR_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_END_COMPENSATION_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_END_TERMINATE_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_END_LINK_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_END_MULTIPLE_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_END_CANCEL_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.GATEWAY_EVENT_BASED_EXCLUSIVE_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.GATEWAY_DATA_BASED_INCLUSIVE_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.GATEWAY_PARALLEL_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.GATEWAY_COMPLEX_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_START_SIGNAL_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_INTERMEDIATE_SIGNAL_LITERAL);
        addEEnumLiteral(activityTypeEEnum, ActivityType.EVENT_END_SIGNAL_LITERAL);

        initEEnum(directionTypeEEnum, DirectionType.class, "DirectionType");
        addEEnumLiteral(directionTypeEEnum, DirectionType.NONE_LITERAL);
        addEEnumLiteral(directionTypeEEnum, DirectionType.TO_LITERAL);
        addEEnumLiteral(directionTypeEEnum, DirectionType.FROM_LITERAL);
        addEEnumLiteral(directionTypeEEnum, DirectionType.BOTH_LITERAL);

        initEEnum(sequenceFlowConditionTypeEEnum, SequenceFlowConditionType.class, "SequenceFlowConditionType");
        addEEnumLiteral(sequenceFlowConditionTypeEEnum, SequenceFlowConditionType.NONE_LITERAL);
        addEEnumLiteral(sequenceFlowConditionTypeEEnum, SequenceFlowConditionType.EXPRESSION_LITERAL);
        addEEnumLiteral(sequenceFlowConditionTypeEEnum, SequenceFlowConditionType.DEFAULT_LITERAL);

        // Initialize data types
        initEDataType(activityTypeObjectEDataType, ActivityType.class, "ActivityTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(directionTypeObjectEDataType, DirectionType.class, "DirectionTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
        initEDataType(sequenceFlowConditionTypeObjectEDataType, SequenceFlowConditionType.class, "SequenceFlowConditionTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        createResource(eNS_URI);

        // Create annotations
        // http:///org/eclipse/emf/ecore/util/ExtendedMetaData
        createExtendedMetaDataAnnotations();
    }

    /**
     * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected void createExtendedMetaDataAnnotations() {
        String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
        addAnnotation
          (activityEClass, 
           source, 
           new String[] {
             "name", "Activity",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getActivity_Groups(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "groups"
           });		
        addAnnotation
          (getActivity_Lanes(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "lanes"
           });		
        addAnnotation
          (getActivity_ActivityType(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "activityType"
           });		
        addAnnotation
          (getActivity_Looping(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "looping"
           });		
        addAnnotation
          (activityTypeEEnum, 
           source, 
           new String[] {
             "name", "ActivityType"
           });		
        addAnnotation
          (activityTypeObjectEDataType, 
           source, 
           new String[] {
             "name", "ActivityType:Object",
             "baseType", "ActivityType"
           });		
        addAnnotation
          (artifactEClass, 
           source, 
           new String[] {
             "name", "Artifact",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getArtifact_Associations(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "associations"
           });		
        addAnnotation
          (artifactsContainerEClass, 
           source, 
           new String[] {
             "name", "ArtifactsContainer",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getArtifactsContainer_Artifacts(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "artifacts"
           });		
        addAnnotation
          (associationEClass, 
           source, 
           new String[] {
             "name", "Association",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getAssociation_Direction(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "direction"
           });		
        addAnnotation
          (getAssociation_Source(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "source"
           });		
        addAnnotation
          (getAssociation_Target(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "target"
           });		
        addAnnotation
          (associationTargetEClass, 
           source, 
           new String[] {
             "name", "AssociationTarget",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getAssociationTarget_Associations(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "associations"
           });		
        addAnnotation
          (bpmnDiagramEClass, 
           source, 
           new String[] {
             "name", "BpmnDiagram",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getBpmnDiagram_Pools(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "pools"
           });		
        addAnnotation
          (getBpmnDiagram_Messages(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "messages"
           });		
        addAnnotation
          (getBpmnDiagram_Author(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "author"
           });		
        addAnnotation
          (getBpmnDiagram_Title(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "title"
           });		
        addAnnotation
          (dataObjectEClass, 
           source, 
           new String[] {
             "name", "DataObject",
             "kind", "elementOnly"
           });		
        addAnnotation
          (directionTypeEEnum, 
           source, 
           new String[] {
             "name", "direction_._type"
           });		
        addAnnotation
          (directionTypeObjectEDataType, 
           source, 
           new String[] {
             "name", "direction_._type:Object",
             "baseType", "direction_._type"
           });		
        addAnnotation
          (graphEClass, 
           source, 
           new String[] {
             "name", "Graph",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getGraph_Vertices(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "vertices"
           });		
        addAnnotation
          (getGraph_SequenceEdges(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "sequenceEdges"
           });		
        addAnnotation
          (groupEClass, 
           source, 
           new String[] {
             "name", "Group",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getGroup_Activities(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "activities"
           });		
        addAnnotation
          (identifiableEClass, 
           source, 
           new String[] {
             "name", "Identifiable",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getIdentifiable_ID(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "ID"
           });		
        addAnnotation
          (laneEClass, 
           source, 
           new String[] {
             "name", "Lane",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getLane_Activities(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "activities"
           });		
        addAnnotation
          (messageVertexEClass, 
           source, 
           new String[] {
             "name", "MessageVertex",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getMessageVertex_OrderedMessages(), 
           source, 
           new String[] {
             "kind", "group",
             "name", "orderedMessages:5"
           });		
        addAnnotation
          (getMessageVertex_IncomingMessages(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "incomingMessages",
             "group", "#orderedMessages:5"
           });		
        addAnnotation
          (getMessageVertex_OutgoingMessages(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "outgoingMessages",
             "group", "#orderedMessages:5"
           });		
        addAnnotation
          (messagingEdgeEClass, 
           source, 
           new String[] {
             "name", "MessagingEdge",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getMessagingEdge_Source(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "source"
           });		
        addAnnotation
          (getMessagingEdge_Target(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "target"
           });		
        addAnnotation
          (namedBpmnObjectEClass, 
           source, 
           new String[] {
             "name", "NamedBpmnObject",
             "kind", "empty"
           });		
        addAnnotation
          (getNamedBpmnObject_Documentation(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "documentation"
           });		
        addAnnotation
          (getNamedBpmnObject_Name(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "name"
           });		
        addAnnotation
          (getNamedBpmnObject_Ncname(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "ncname"
           });		
        addAnnotation
          (poolEClass, 
           source, 
           new String[] {
             "name", "Pool",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getPool_Lanes(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "lanes"
           });		
        addAnnotation
          (sequenceEdgeEClass, 
           source, 
           new String[] {
             "name", "SequenceEdge",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getSequenceEdge_ConditionType(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "conditionType"
           });		
        addAnnotation
          (getSequenceEdge_IsDefault(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "isDefault"
           });		
        addAnnotation
          (getSequenceEdge_Source(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "source"
           });		
        addAnnotation
          (getSequenceEdge_Target(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "target"
           });		
        addAnnotation
          (sequenceFlowConditionTypeEEnum, 
           source, 
           new String[] {
             "name", "SequenceFlowConditionType"
           });		
        addAnnotation
          (sequenceFlowConditionTypeObjectEDataType, 
           source, 
           new String[] {
             "name", "SequenceFlowConditionType:Object",
             "baseType", "SequenceFlowConditionType"
           });		
        addAnnotation
          (subProcessEClass, 
           source, 
           new String[] {
             "name", "SubProcess",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getSubProcess_EventHandlers(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "eventHandlers"
           });		
        addAnnotation
          (getSubProcess_IsTransaction(), 
           source, 
           new String[] {
             "kind", "attribute",
             "name", "isTransaction"
           });		
        addAnnotation
          (textAnnotationEClass, 
           source, 
           new String[] {
             "name", "TextAnnotation",
             "kind", "elementOnly"
           });		
        addAnnotation
          (vertexEClass, 
           source, 
           new String[] {
             "name", "Vertex",
             "kind", "elementOnly"
           });		
        addAnnotation
          (getVertex_OutgoingEdges(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "outgoingEdges"
           });		
        addAnnotation
          (getVertex_IncomingEdges(), 
           source, 
           new String[] {
             "kind", "element",
             "name", "incomingEdges"
           });
    }

} //BpmnPackageImpl
