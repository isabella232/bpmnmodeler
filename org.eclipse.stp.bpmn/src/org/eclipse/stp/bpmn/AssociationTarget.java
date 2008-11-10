/******************************************************************************
 * Copyright (c) 2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Date         Author                  Changes
 * 14 Feb 2008  Antoine Toulme      Created
 */
package org.eclipse.stp.bpmn;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Association Target</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.AssociationTarget#getAssociations <em>Associations</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.stp.bpmn.BpmnPackage#getAssociationTarget()
 * @model extendedMetaData="name='AssociationTarget' kind='elementOnly'"
 * @generated
 */
public interface AssociationTarget extends Identifiable {
    /**
     * Returns the value of the '<em><b>Associations</b></em>' reference list.
     * The list contents are of type {@link org.eclipse.stp.bpmn.Association}.
     * It is bidirectional and its opposite is '{@link org.eclipse.stp.bpmn.Association#getTarget <em>Target</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Associations</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Associations</em>' reference list.
     * @see org.eclipse.stp.bpmn.BpmnPackage#getAssociationTarget_Associations()
     * @see org.eclipse.stp.bpmn.Association#getTarget
     * @model opposite="target" resolveProxies="false"
     *        extendedMetaData="kind='element' name='associations'"
     * @generated
     */
    EList<Association> getAssociations();

} // AssociationTarget
