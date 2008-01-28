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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.ArtifactsContainer;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.NamedBpmnObject;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.BpmnMessages;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Graph</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.impl.GraphImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.GraphImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.GraphImpl#getNcname <em>Ncname</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.GraphImpl#getArtifacts <em>Artifacts</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.GraphImpl#getVertices <em>Vertices</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.GraphImpl#getSequenceEdges <em>Sequence Edges</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GraphImpl extends IdentifiableNodeImpl implements Graph {
    /**
	 * The default value of the '{@link #getDocumentation() <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
    protected static final String DOCUMENTATION_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getDocumentation() <em>Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDocumentation()
	 * @generated
	 * @ordered
	 */
    protected String documentation = DOCUMENTATION_EDEFAULT;

    /**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
    protected static final String NAME_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
    protected String name = NAME_EDEFAULT;

    /**
	 * The default value of the '{@link #getNcname() <em>Ncname</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getNcname()
	 * @generated
	 * @ordered
	 */
    protected static final String NCNAME_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getNcname() <em>Ncname</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getNcname()
	 * @generated
	 * @ordered
	 */
    protected String ncname = NCNAME_EDEFAULT;

    /**
	 * The cached value of the '{@link #getArtifacts() <em>Artifacts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getArtifacts()
	 * @generated
	 * @ordered
	 */
    protected EList<Artifact> artifacts;

    /**
	 * The cached value of the '{@link #getVertices() <em>Vertices</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getVertices()
	 * @generated
	 * @ordered
	 */
    protected EList<Vertex> vertices;

    /**
	 * The cached value of the '{@link #getSequenceEdges() <em>Sequence Edges</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getSequenceEdges()
	 * @generated
	 * @ordered
	 */
    protected EList<SequenceEdge> sequenceEdges;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected GraphImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
				protected EClass eStaticClass() {
		return BpmnPackage.Literals.GRAPH;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getDocumentation() {
		return documentation;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDocumentation(String newDocumentation) {
		String oldDocumentation = documentation;
		documentation = newDocumentation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.GRAPH__DOCUMENTATION, oldDocumentation, documentation));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getName() {
		return name;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.GRAPH__NAME, oldName, name));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getNcname() {
		return ncname;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setNcname(String newNcname) {
		String oldNcname = ncname;
		ncname = newNcname;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.GRAPH__NCNAME, oldNcname, ncname));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<Artifact> getArtifacts() {
		if (artifacts == null) {
			artifacts = new EObjectContainmentWithInverseEList<Artifact>(Artifact.class, this, BpmnPackage.GRAPH__ARTIFACTS, BpmnPackage.ARTIFACT__ARTIFACTS_CONTAINER);
		}
		return artifacts;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<Vertex> getVertices() {
		if (vertices == null) {
			vertices = new EObjectContainmentWithInverseEList<Vertex>(Vertex.class, this, BpmnPackage.GRAPH__VERTICES, BpmnPackage.VERTEX__GRAPH);
		}
		return vertices;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<SequenceEdge> getSequenceEdges() {
		if (sequenceEdges == null) {
			sequenceEdges = new EObjectContainmentWithInverseEList<SequenceEdge>(SequenceEdge.class, this, BpmnPackage.GRAPH__SEQUENCE_EDGES, BpmnPackage.SEQUENCE_EDGE__GRAPH);
		}
		return sequenceEdges;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
		@Override
				public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BpmnPackage.GRAPH__ARTIFACTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getArtifacts()).basicAdd(otherEnd, msgs);
			case BpmnPackage.GRAPH__VERTICES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getVertices()).basicAdd(otherEnd, msgs);
			case BpmnPackage.GRAPH__SEQUENCE_EDGES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getSequenceEdges()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
				public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BpmnPackage.GRAPH__ARTIFACTS:
				return ((InternalEList<?>)getArtifacts()).basicRemove(otherEnd, msgs);
			case BpmnPackage.GRAPH__VERTICES:
				return ((InternalEList<?>)getVertices()).basicRemove(otherEnd, msgs);
			case BpmnPackage.GRAPH__SEQUENCE_EDGES:
				return ((InternalEList<?>)getSequenceEdges()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
				public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BpmnPackage.GRAPH__DOCUMENTATION:
				return getDocumentation();
			case BpmnPackage.GRAPH__NAME:
				return getName();
			case BpmnPackage.GRAPH__NCNAME:
				return getNcname();
			case BpmnPackage.GRAPH__ARTIFACTS:
				return getArtifacts();
			case BpmnPackage.GRAPH__VERTICES:
				return getVertices();
			case BpmnPackage.GRAPH__SEQUENCE_EDGES:
				return getSequenceEdges();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
		@Override
				public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BpmnPackage.GRAPH__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case BpmnPackage.GRAPH__NAME:
				setName((String)newValue);
				return;
			case BpmnPackage.GRAPH__NCNAME:
				setNcname((String)newValue);
				return;
			case BpmnPackage.GRAPH__ARTIFACTS:
				getArtifacts().clear();
				getArtifacts().addAll((Collection<? extends Artifact>)newValue);
				return;
			case BpmnPackage.GRAPH__VERTICES:
				getVertices().clear();
				getVertices().addAll((Collection<? extends Vertex>)newValue);
				return;
			case BpmnPackage.GRAPH__SEQUENCE_EDGES:
				getSequenceEdges().clear();
				getSequenceEdges().addAll((Collection<? extends SequenceEdge>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
				public void eUnset(int featureID) {
		switch (featureID) {
			case BpmnPackage.GRAPH__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case BpmnPackage.GRAPH__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BpmnPackage.GRAPH__NCNAME:
				setNcname(NCNAME_EDEFAULT);
				return;
			case BpmnPackage.GRAPH__ARTIFACTS:
				getArtifacts().clear();
				return;
			case BpmnPackage.GRAPH__VERTICES:
				getVertices().clear();
				return;
			case BpmnPackage.GRAPH__SEQUENCE_EDGES:
				getSequenceEdges().clear();
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
				public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BpmnPackage.GRAPH__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case BpmnPackage.GRAPH__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BpmnPackage.GRAPH__NCNAME:
				return NCNAME_EDEFAULT == null ? ncname != null : !NCNAME_EDEFAULT.equals(ncname);
			case BpmnPackage.GRAPH__ARTIFACTS:
				return artifacts != null && !artifacts.isEmpty();
			case BpmnPackage.GRAPH__VERTICES:
				return vertices != null && !vertices.isEmpty();
			case BpmnPackage.GRAPH__SEQUENCE_EDGES:
				return sequenceEdges != null && !sequenceEdges.isEmpty();
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
				public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == NamedBpmnObject.class) {
			switch (derivedFeatureID) {
				case BpmnPackage.GRAPH__DOCUMENTATION: return BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION;
				case BpmnPackage.GRAPH__NAME: return BpmnPackage.NAMED_BPMN_OBJECT__NAME;
				case BpmnPackage.GRAPH__NCNAME: return BpmnPackage.NAMED_BPMN_OBJECT__NCNAME;
				default: return -1;
			}
		}
		if (baseClass == ArtifactsContainer.class) {
			switch (derivedFeatureID) {
				case BpmnPackage.GRAPH__ARTIFACTS: return BpmnPackage.ARTIFACTS_CONTAINER__ARTIFACTS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
				public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == NamedBpmnObject.class) {
			switch (baseFeatureID) {
				case BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION: return BpmnPackage.GRAPH__DOCUMENTATION;
				case BpmnPackage.NAMED_BPMN_OBJECT__NAME: return BpmnPackage.GRAPH__NAME;
				case BpmnPackage.NAMED_BPMN_OBJECT__NCNAME: return BpmnPackage.GRAPH__NCNAME;
				default: return -1;
			}
		}
		if (baseClass == ArtifactsContainer.class) {
			switch (baseFeatureID) {
				case BpmnPackage.ARTIFACTS_CONTAINER__ARTIFACTS: return BpmnPackage.GRAPH__ARTIFACTS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
				public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(BpmnMessages.GraphImpl_doc);
		result.append(documentation);
		result.append(BpmnMessages.GraphImpl_name);
		result.append(name);
		result.append(BpmnMessages.GraphImpl_ncname);
		result.append(ncname);
		result.append(')');
		return result.toString();
	}

} //GraphImpl