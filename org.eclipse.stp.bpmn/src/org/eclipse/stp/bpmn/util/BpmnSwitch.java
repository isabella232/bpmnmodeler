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

package org.eclipse.stp.bpmn.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.stp.bpmn.*;

import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.Identifiable;
import org.eclipse.stp.bpmn.Lane;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.Vertex;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.stp.bpmn.BpmnPackage
 * @generated
 */
public class BpmnSwitch<T> {
    /**
	 * The cached model package
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected static BpmnPackage modelPackage;

    /**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public BpmnSwitch() {
		if (modelPackage == null) {
			modelPackage = BpmnPackage.eINSTANCE;
		}
	}

    /**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
    public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

    /**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
    protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

    /**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
    protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case BpmnPackage.ACTIVITY: {
				Activity activity = (Activity)theEObject;
				T result = caseActivity(activity);
				if (result == null) result = caseVertex(activity);
				if (result == null) result = caseNamedBpmnObject(activity);
				if (result == null) result = caseIdentifiableNode(activity);
				if (result == null) result = caseIdentifiable(activity);
				if (result == null) result = caseEModelElement(activity);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BpmnPackage.ARTIFACT: {
				Artifact artifact = (Artifact)theEObject;
				T result = caseArtifact(artifact);
				if (result == null) result = caseIdentifiable(artifact);
				if (result == null) result = caseNamedBpmnObject(artifact);
				if (result == null) result = caseEModelElement(artifact);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BpmnPackage.ARTIFACTS_CONTAINER: {
				ArtifactsContainer artifactsContainer = (ArtifactsContainer)theEObject;
				T result = caseArtifactsContainer(artifactsContainer);
				if (result == null) result = caseNamedBpmnObject(artifactsContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BpmnPackage.ASSOCIATION: {
				Association association = (Association)theEObject;
				T result = caseAssociation(association);
				if (result == null) result = caseEModelElement(association);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BpmnPackage.BPMN_DIAGRAM: {
				BpmnDiagram bpmnDiagram = (BpmnDiagram)theEObject;
				T result = caseBpmnDiagram(bpmnDiagram);
				if (result == null) result = caseIdentifiable(bpmnDiagram);
				if (result == null) result = caseArtifactsContainer(bpmnDiagram);
				if (result == null) result = caseEModelElement(bpmnDiagram);
				if (result == null) result = caseNamedBpmnObject(bpmnDiagram);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BpmnPackage.DATA_OBJECT: {
				DataObject dataObject = (DataObject)theEObject;
				T result = caseDataObject(dataObject);
				if (result == null) result = caseArtifact(dataObject);
				if (result == null) result = caseIdentifiable(dataObject);
				if (result == null) result = caseNamedBpmnObject(dataObject);
				if (result == null) result = caseEModelElement(dataObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BpmnPackage.GRAPH: {
				Graph graph = (Graph)theEObject;
				T result = caseGraph(graph);
				if (result == null) result = caseIdentifiableNode(graph);
				if (result == null) result = caseArtifactsContainer(graph);
				if (result == null) result = caseIdentifiable(graph);
				if (result == null) result = caseNamedBpmnObject(graph);
				if (result == null) result = caseEModelElement(graph);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BpmnPackage.GROUP: {
				Group group = (Group)theEObject;
				T result = caseGroup(group);
				if (result == null) result = caseArtifact(group);
				if (result == null) result = caseIdentifiable(group);
				if (result == null) result = caseNamedBpmnObject(group);
				if (result == null) result = caseEModelElement(group);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BpmnPackage.IDENTIFIABLE: {
				Identifiable identifiable = (Identifiable)theEObject;
				T result = caseIdentifiable(identifiable);
				if (result == null) result = caseEModelElement(identifiable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BpmnPackage.IDENTIFIABLE_NODE: {
				IdentifiableNode identifiableNode = (IdentifiableNode)theEObject;
				T result = caseIdentifiableNode(identifiableNode);
				if (result == null) result = caseIdentifiable(identifiableNode);
				if (result == null) result = caseEModelElement(identifiableNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BpmnPackage.LANE: {
				Lane lane = (Lane)theEObject;
				T result = caseLane(lane);
				if (result == null) result = caseIdentifiable(lane);
				if (result == null) result = caseNamedBpmnObject(lane);
				if (result == null) result = caseEModelElement(lane);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BpmnPackage.MESSAGING_EDGE: {
				MessagingEdge messagingEdge = (MessagingEdge)theEObject;
				T result = caseMessagingEdge(messagingEdge);
				if (result == null) result = caseIdentifiable(messagingEdge);
				if (result == null) result = caseNamedBpmnObject(messagingEdge);
				if (result == null) result = caseEModelElement(messagingEdge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BpmnPackage.NAMED_BPMN_OBJECT: {
				NamedBpmnObject namedBpmnObject = (NamedBpmnObject)theEObject;
				T result = caseNamedBpmnObject(namedBpmnObject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BpmnPackage.POOL: {
				Pool pool = (Pool)theEObject;
				T result = casePool(pool);
				if (result == null) result = caseGraph(pool);
				if (result == null) result = caseNamedBpmnObject(pool);
				if (result == null) result = caseIdentifiableNode(pool);
				if (result == null) result = caseArtifactsContainer(pool);
				if (result == null) result = caseIdentifiable(pool);
				if (result == null) result = caseEModelElement(pool);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BpmnPackage.SEQUENCE_EDGE: {
				SequenceEdge sequenceEdge = (SequenceEdge)theEObject;
				T result = caseSequenceEdge(sequenceEdge);
				if (result == null) result = caseIdentifiable(sequenceEdge);
				if (result == null) result = caseNamedBpmnObject(sequenceEdge);
				if (result == null) result = caseEModelElement(sequenceEdge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BpmnPackage.SUB_PROCESS: {
				SubProcess subProcess = (SubProcess)theEObject;
				T result = caseSubProcess(subProcess);
				if (result == null) result = caseActivity(subProcess);
				if (result == null) result = caseGraph(subProcess);
				if (result == null) result = caseVertex(subProcess);
				if (result == null) result = caseNamedBpmnObject(subProcess);
				if (result == null) result = caseIdentifiableNode(subProcess);
				if (result == null) result = caseArtifactsContainer(subProcess);
				if (result == null) result = caseIdentifiable(subProcess);
				if (result == null) result = caseEModelElement(subProcess);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BpmnPackage.TEXT_ANNOTATION: {
				TextAnnotation textAnnotation = (TextAnnotation)theEObject;
				T result = caseTextAnnotation(textAnnotation);
				if (result == null) result = caseArtifact(textAnnotation);
				if (result == null) result = caseIdentifiable(textAnnotation);
				if (result == null) result = caseNamedBpmnObject(textAnnotation);
				if (result == null) result = caseEModelElement(textAnnotation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BpmnPackage.VERTEX: {
				Vertex vertex = (Vertex)theEObject;
				T result = caseVertex(vertex);
				if (result == null) result = caseIdentifiableNode(vertex);
				if (result == null) result = caseIdentifiable(vertex);
				if (result == null) result = caseEModelElement(vertex);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Activity</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Activity</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseActivity(Activity object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Artifact</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Artifact</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseArtifact(Artifact object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Artifacts Container</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Artifacts Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseArtifactsContainer(ArtifactsContainer object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Association</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Association</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssociation(Association object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Diagram</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Diagram</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseBpmnDiagram(BpmnDiagram object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Data Object</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Data Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDataObject(DataObject object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Graph</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Graph</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseGraph(Graph object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Group</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Group</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGroup(Group object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Identifiable</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Identifiable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseIdentifiable(Identifiable object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Identifiable Node</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Identifiable Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseIdentifiableNode(IdentifiableNode object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Lane</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Lane</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseLane(Lane object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Messaging Edge</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Messaging Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseMessagingEdge(MessagingEdge object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Named Bpmn Object</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Named Bpmn Object</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseNamedBpmnObject(NamedBpmnObject object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Pool</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pool</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T casePool(Pool object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Sequence Edge</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sequence Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseSequenceEdge(SequenceEdge object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Sub Process</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Sub Process</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseSubProcess(SubProcess object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Text Annotation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Text Annotation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTextAnnotation(TextAnnotation object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>Vertex</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Vertex</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseVertex(Vertex object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>EModel Element</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EModel Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public T caseEModelElement(EModelElement object) {
		return null;
	}

    /**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
    public T defaultCase(EObject object) {
		return null;
	}

} //BpmnSwitch
