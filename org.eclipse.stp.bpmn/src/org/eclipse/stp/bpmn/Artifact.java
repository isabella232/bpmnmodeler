/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.stp.bpmn;


import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Artifact</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.Artifact#getAssociations <em>Associations</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.Artifact#getArtifactsContainer <em>Artifacts Container</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.stp.bpmn.BpmnPackage#getArtifact()
 * @model extendedMetaData="name='Artifact' kind='elementOnly'"
 * @generated
 */
public interface Artifact extends Identifiable, NamedBpmnObject {
    /**
	 * Returns the value of the '<em><b>Associations</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.stp.bpmn.Association}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.Association#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Associations</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Associations</em>' containment reference list.
	 * @see org.eclipse.stp.bpmn.BpmnPackage#getArtifact_Associations()
	 * @see org.eclipse.stp.bpmn.Association#getSource
	 * @model opposite="source" containment="true"
	 *        extendedMetaData="kind='element' name='associations'"
	 * @generated
	 */
    EList<Association> getAssociations();

    /**
	 * Returns the value of the '<em><b>Artifacts Container</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.ArtifactsContainer#getArtifacts <em>Artifacts</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Artifacts Container</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifacts Container</em>' container reference.
	 * @see #setArtifactsContainer(ArtifactsContainer)
	 * @see org.eclipse.stp.bpmn.BpmnPackage#getArtifact_ArtifactsContainer()
	 * @see org.eclipse.stp.bpmn.ArtifactsContainer#getArtifacts
	 * @model opposite="artifacts" transient="false"
	 *        extendedMetaData="kind='attribute' name='artifactsContainer'"
	 * @generated
	 */
    ArtifactsContainer getArtifactsContainer();

    /**
	 * Sets the value of the '{@link org.eclipse.stp.bpmn.Artifact#getArtifactsContainer <em>Artifacts Container</em>}' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Artifacts Container</em>' container reference.
	 * @see #getArtifactsContainer()
	 * @generated
	 */
    void setArtifactsContainer(ArtifactsContainer value);

} // Artifact