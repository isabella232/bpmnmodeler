/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.stp.bpmn;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Artifacts Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.ArtifactsContainer#getArtifacts <em>Artifacts</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.stp.bpmn.BpmnPackage#getArtifactsContainer()
 * @model extendedMetaData="name='ArtifactsContainer' kind='elementOnly'"
 * @generated
 */
public interface ArtifactsContainer extends NamedBpmnObject {
    /**
     * Returns the value of the '<em><b>Artifacts</b></em>' containment reference list.
     * The list contents are of type {@link org.eclipse.stp.bpmn.Artifact}.
     * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.Artifact#getArtifactsContainer <em>Artifacts Container</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Artifacts</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Artifacts</em>' containment reference list.
     * @see org.eclipse.stp.bpmn.BpmnPackage#getArtifactsContainer_Artifacts()
     * @see org.eclipse.stp.bpmn.Artifact#getArtifactsContainer
     * @model opposite="artifactsContainer" containment="true"
     *        extendedMetaData="kind='element' name='artifacts'"
     * @generated
     */
    EList<Artifact> getArtifacts();

} // ArtifactsContainer