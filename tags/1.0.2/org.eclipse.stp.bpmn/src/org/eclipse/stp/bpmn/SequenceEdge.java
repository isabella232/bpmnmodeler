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
 * A representation of the model object '<em><b>Sequence Edge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.SequenceEdge#getConditionType <em>Condition Type</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.SequenceEdge#isIsDefault <em>Is Default</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.SequenceEdge#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.SequenceEdge#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.SequenceEdge#getGraph <em>Graph</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.stp.bpmn.BpmnPackage#getSequenceEdge()
 * @model extendedMetaData="name='SequenceEdge' kind='elementOnly'"
 * @generated
 */
public interface SequenceEdge extends AssociationTarget, NamedBpmnObject {
    /**
     * Returns the value of the '<em><b>Condition Type</b></em>' attribute.
     * The literals are from the enumeration {@link org.eclipse.stp.bpmn.SequenceFlowConditionType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Condition Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Condition Type</em>' attribute.
     * @see org.eclipse.stp.bpmn.SequenceFlowConditionType
     * @see #isSetConditionType()
     * @see #unsetConditionType()
     * @see #setConditionType(SequenceFlowConditionType)
     * @see org.eclipse.stp.bpmn.BpmnPackage#getSequenceEdge_ConditionType()
     * @model unsettable="true"
     *        extendedMetaData="kind='attribute' name='conditionType'"
     * @generated
     */
    SequenceFlowConditionType getConditionType();

    /**
     * Sets the value of the '{@link org.eclipse.stp.bpmn.SequenceEdge#getConditionType <em>Condition Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Condition Type</em>' attribute.
     * @see org.eclipse.stp.bpmn.SequenceFlowConditionType
     * @see #isSetConditionType()
     * @see #unsetConditionType()
     * @see #getConditionType()
     * @generated
     */
    void setConditionType(SequenceFlowConditionType value);

    /**
     * Unsets the value of the '{@link org.eclipse.stp.bpmn.SequenceEdge#getConditionType <em>Condition Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetConditionType()
     * @see #getConditionType()
     * @see #setConditionType(SequenceFlowConditionType)
     * @generated
     */
    void unsetConditionType();

    /**
     * Returns whether the value of the '{@link org.eclipse.stp.bpmn.SequenceEdge#getConditionType <em>Condition Type</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Condition Type</em>' attribute is set.
     * @see #unsetConditionType()
     * @see #getConditionType()
     * @see #setConditionType(SequenceFlowConditionType)
     * @generated
     */
    boolean isSetConditionType();

    /**
     * Returns the value of the '<em><b>Graph</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.Graph#getSequenceEdges <em>Sequence Edges</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Graph</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Graph</em>' container reference.
     * @see #setGraph(Graph)
     * @see org.eclipse.stp.bpmn.BpmnPackage#getSequenceEdge_Graph()
     * @see org.eclipse.stp.bpmn.Graph#getSequenceEdges
     * @model opposite="sequenceEdges"
     * @generated
     */
    Graph getGraph();

    /**
     * Sets the value of the '{@link org.eclipse.stp.bpmn.SequenceEdge#getGraph <em>Graph</em>}' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Graph</em>' container reference.
     * @see #getGraph()
     * @generated
     */
    void setGraph(Graph value);

    /**
     * Returns the value of the '<em><b>Is Default</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Is Default</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Is Default</em>' attribute.
     * @see #isSetIsDefault()
     * @see #unsetIsDefault()
     * @see #setIsDefault(boolean)
     * @see org.eclipse.stp.bpmn.BpmnPackage#getSequenceEdge_IsDefault()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     *        extendedMetaData="kind='attribute' name='isDefault'"
     * @generated
     */
    boolean isIsDefault();

    /**
     * Sets the value of the '{@link org.eclipse.stp.bpmn.SequenceEdge#isIsDefault <em>Is Default</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Is Default</em>' attribute.
     * @see #isSetIsDefault()
     * @see #unsetIsDefault()
     * @see #isIsDefault()
     * @generated
     */
    void setIsDefault(boolean value);

    /**
     * Unsets the value of the '{@link org.eclipse.stp.bpmn.SequenceEdge#isIsDefault <em>Is Default</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetIsDefault()
     * @see #isIsDefault()
     * @see #setIsDefault(boolean)
     * @generated
     */
    void unsetIsDefault();

    /**
     * Returns whether the value of the '{@link org.eclipse.stp.bpmn.SequenceEdge#isIsDefault <em>Is Default</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Is Default</em>' attribute is set.
     * @see #unsetIsDefault()
     * @see #isIsDefault()
     * @see #setIsDefault(boolean)
     * @generated
     */
    boolean isSetIsDefault();

    /**
     * Returns the value of the '<em><b>Source</b></em>' reference.
     * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.Vertex#getOutgoingEdges <em>Outgoing Edges</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Source</em>' reference.
     * @see #setSource(Vertex)
     * @see org.eclipse.stp.bpmn.BpmnPackage#getSequenceEdge_Source()
     * @see org.eclipse.stp.bpmn.Vertex#getOutgoingEdges
     * @model opposite="outgoingEdges" resolveProxies="false" transient="true"
     *        extendedMetaData="kind='attribute' name='source'"
     * @generated
     */
    Vertex getSource();

    /**
     * Sets the value of the '{@link org.eclipse.stp.bpmn.SequenceEdge#getSource <em>Source</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Source</em>' reference.
     * @see #getSource()
     * @generated
     */
    void setSource(Vertex value);

    /**
     * Returns the value of the '<em><b>Target</b></em>' reference.
     * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.Vertex#getIncomingEdges <em>Incoming Edges</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Target</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Target</em>' reference.
     * @see #setTarget(Vertex)
     * @see org.eclipse.stp.bpmn.BpmnPackage#getSequenceEdge_Target()
     * @see org.eclipse.stp.bpmn.Vertex#getIncomingEdges
     * @model opposite="incomingEdges" resolveProxies="false" transient="true"
     *        extendedMetaData="kind='attribute' name='target'"
     * @generated
     */
    Vertex getTarget();

    /**
     * Sets the value of the '{@link org.eclipse.stp.bpmn.SequenceEdge#getTarget <em>Target</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Target</em>' reference.
     * @see #getTarget()
     * @generated
     */
    void setTarget(Vertex value);

} // SequenceEdge