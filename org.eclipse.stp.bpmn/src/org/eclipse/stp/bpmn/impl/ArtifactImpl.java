/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.stp.bpmn.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.ArtifactsContainer;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.BpmnMessages;

import org.eclipse.stp.bpmn.NamedBpmnObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Artifact</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.impl.ArtifactImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.ArtifactImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.ArtifactImpl#getNcname <em>Ncname</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.ArtifactImpl#getAssociations <em>Associations</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.ArtifactImpl#getArtifactsContainer <em>Artifacts Container</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ArtifactImpl extends IdentifiableImpl implements Artifact {
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
	 * The cached value of the '{@link #getAssociations() <em>Associations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getAssociations()
	 * @generated
	 * @ordered
	 */
    protected EList<Association> associations;

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArtifactImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BpmnPackage.Literals.ARTIFACT;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<Association> getAssociations() {
		if (associations == null) {
			associations = new EObjectContainmentWithInverseEList<Association>(Association.class, this, BpmnPackage.ARTIFACT__ASSOCIATIONS, BpmnPackage.ASSOCIATION__SOURCE);
		}
		return associations;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public ArtifactsContainer getArtifactsContainer() {
		if (eContainerFeatureID != BpmnPackage.ARTIFACT__ARTIFACTS_CONTAINER) return null;
		return (ArtifactsContainer)eContainer();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetArtifactsContainer(ArtifactsContainer newArtifactsContainer, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newArtifactsContainer, BpmnPackage.ARTIFACT__ARTIFACTS_CONTAINER, msgs);
		return msgs;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setArtifactsContainer(ArtifactsContainer newArtifactsContainer) {
		if (newArtifactsContainer != eInternalContainer() || (eContainerFeatureID != BpmnPackage.ARTIFACT__ARTIFACTS_CONTAINER && newArtifactsContainer != null)) {
			if (EcoreUtil.isAncestor(this, newArtifactsContainer))
				throw new IllegalArgumentException(BpmnMessages.bind(
						BpmnMessages.ArtifactImpl_recursiveContainment,
						toString()));
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newArtifactsContainer != null)
				msgs = ((InternalEObject)newArtifactsContainer).eInverseAdd(this, BpmnPackage.ARTIFACTS_CONTAINER__ARTIFACTS, ArtifactsContainer.class, msgs);
			msgs = basicSetArtifactsContainer(newArtifactsContainer, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.ARTIFACT__ARTIFACTS_CONTAINER, newArtifactsContainer, newArtifactsContainer));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.ARTIFACT__DOCUMENTATION, oldDocumentation, documentation));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.ARTIFACT__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.ARTIFACT__NCNAME, oldNcname, ncname));
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
			case BpmnPackage.ARTIFACT__ASSOCIATIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAssociations()).basicAdd(otherEnd, msgs);
			case BpmnPackage.ARTIFACT__ARTIFACTS_CONTAINER:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetArtifactsContainer((ArtifactsContainer)otherEnd, msgs);
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
			case BpmnPackage.ARTIFACT__ASSOCIATIONS:
				return ((InternalEList<?>)getAssociations()).basicRemove(otherEnd, msgs);
			case BpmnPackage.ARTIFACT__ARTIFACTS_CONTAINER:
				return basicSetArtifactsContainer(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
				public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID) {
			case BpmnPackage.ARTIFACT__ARTIFACTS_CONTAINER:
				return eInternalContainer().eInverseRemove(this, BpmnPackage.ARTIFACTS_CONTAINER__ARTIFACTS, ArtifactsContainer.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BpmnPackage.ARTIFACT__DOCUMENTATION:
				return getDocumentation();
			case BpmnPackage.ARTIFACT__NAME:
				return getName();
			case BpmnPackage.ARTIFACT__NCNAME:
				return getNcname();
			case BpmnPackage.ARTIFACT__ASSOCIATIONS:
				return getAssociations();
			case BpmnPackage.ARTIFACT__ARTIFACTS_CONTAINER:
				return getArtifactsContainer();
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
			case BpmnPackage.ARTIFACT__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case BpmnPackage.ARTIFACT__NAME:
				setName((String)newValue);
				return;
			case BpmnPackage.ARTIFACT__NCNAME:
				setNcname((String)newValue);
				return;
			case BpmnPackage.ARTIFACT__ASSOCIATIONS:
				getAssociations().clear();
				getAssociations().addAll((Collection<? extends Association>)newValue);
				return;
			case BpmnPackage.ARTIFACT__ARTIFACTS_CONTAINER:
				setArtifactsContainer((ArtifactsContainer)newValue);
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
			case BpmnPackage.ARTIFACT__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case BpmnPackage.ARTIFACT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BpmnPackage.ARTIFACT__NCNAME:
				setNcname(NCNAME_EDEFAULT);
				return;
			case BpmnPackage.ARTIFACT__ASSOCIATIONS:
				getAssociations().clear();
				return;
			case BpmnPackage.ARTIFACT__ARTIFACTS_CONTAINER:
				setArtifactsContainer((ArtifactsContainer)null);
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
			case BpmnPackage.ARTIFACT__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case BpmnPackage.ARTIFACT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BpmnPackage.ARTIFACT__NCNAME:
				return NCNAME_EDEFAULT == null ? ncname != null : !NCNAME_EDEFAULT.equals(ncname);
			case BpmnPackage.ARTIFACT__ASSOCIATIONS:
				return associations != null && !associations.isEmpty();
			case BpmnPackage.ARTIFACT__ARTIFACTS_CONTAINER:
				return getArtifactsContainer() != null;
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
				case BpmnPackage.ARTIFACT__DOCUMENTATION: return BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION;
				case BpmnPackage.ARTIFACT__NAME: return BpmnPackage.NAMED_BPMN_OBJECT__NAME;
				case BpmnPackage.ARTIFACT__NCNAME: return BpmnPackage.NAMED_BPMN_OBJECT__NCNAME;
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
				case BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION: return BpmnPackage.ARTIFACT__DOCUMENTATION;
				case BpmnPackage.NAMED_BPMN_OBJECT__NAME: return BpmnPackage.ARTIFACT__NAME;
				case BpmnPackage.NAMED_BPMN_OBJECT__NCNAME: return BpmnPackage.ARTIFACT__NCNAME;
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
		result.append(BpmnMessages.ArtifactImpl_doc);
		result.append(documentation);
		result.append(BpmnMessages.ArtifactImpl_name);
		result.append(name);
		result.append(BpmnMessages.ArtifactImpl_ncname);
		result.append(ncname);
		result.append(')');
		return result.toString();
	}

} //ArtifactImpl