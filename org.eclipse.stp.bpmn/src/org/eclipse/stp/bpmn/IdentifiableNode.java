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
 * A representation of the model object '<em><b>Identifiable Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.IdentifiableNode#getAssociations <em>Associations</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.stp.bpmn.BpmnPackage#getIdentifiableNode()
 * @model extendedMetaData="name='IdentifiableNode' kind='elementOnly'"
 * @generated
 */
public interface IdentifiableNode extends Identifiable {
    /**
	 * Returns the value of the '<em><b>Associations</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.stp.bpmn.Association}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.Association#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Associations</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Associations</em>' reference list.
	 * @see org.eclipse.stp.bpmn.BpmnPackage#getIdentifiableNode_Associations()
	 * @see org.eclipse.stp.bpmn.Association#getTarget
	 * @model opposite="target"
	 *        extendedMetaData="kind='element' name='associations'"
	 * @generated
	 */
    EList<Association> getAssociations();

} // IdentifiableNode