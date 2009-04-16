/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.stp.bpmn.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.ArtifactsContainer;
import org.eclipse.stp.bpmn.BpmnPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Artifacts Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.impl.ArtifactsContainerImpl#getArtifacts <em>Artifacts</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ArtifactsContainerImpl extends NamedBpmnObjectImpl implements ArtifactsContainer {
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ArtifactsContainerImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
				protected EClass eStaticClass() {
        return BpmnPackage.Literals.ARTIFACTS_CONTAINER;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Artifact> getArtifacts() {
        if (artifacts == null) {
            artifacts = new EObjectContainmentWithInverseEList<Artifact>(Artifact.class, this, BpmnPackage.ARTIFACTS_CONTAINER__ARTIFACTS, BpmnPackage.ARTIFACT__ARTIFACTS_CONTAINER);
        }
        return artifacts;
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
            case BpmnPackage.ARTIFACTS_CONTAINER__ARTIFACTS:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getArtifacts()).basicAdd(otherEnd, msgs);
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
            case BpmnPackage.ARTIFACTS_CONTAINER__ARTIFACTS:
                return ((InternalEList<?>)getArtifacts()).basicRemove(otherEnd, msgs);
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
            case BpmnPackage.ARTIFACTS_CONTAINER__ARTIFACTS:
                return getArtifacts();
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
            case BpmnPackage.ARTIFACTS_CONTAINER__ARTIFACTS:
                getArtifacts().clear();
                getArtifacts().addAll((Collection<? extends Artifact>)newValue);
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
            case BpmnPackage.ARTIFACTS_CONTAINER__ARTIFACTS:
                getArtifacts().clear();
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
            case BpmnPackage.ARTIFACTS_CONTAINER__ARTIFACTS:
                return artifacts != null && !artifacts.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} //ArtifactsContainerImpl