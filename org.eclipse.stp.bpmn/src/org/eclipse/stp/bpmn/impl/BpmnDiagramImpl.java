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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.ArtifactsContainer;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.NamedBpmnObject;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.BpmnMessages;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Diagram</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.impl.BpmnDiagramImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.BpmnDiagramImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.BpmnDiagramImpl#getNcname <em>Ncname</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.BpmnDiagramImpl#getArtifacts <em>Artifacts</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.BpmnDiagramImpl#getPools <em>Pools</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.BpmnDiagramImpl#getMessages <em>Messages</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.BpmnDiagramImpl#getAuthor <em>Author</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.BpmnDiagramImpl#getTitle <em>Title</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BpmnDiagramImpl extends IdentifiableImpl implements BpmnDiagram {
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
	 * The cached value of the '{@link #getPools() <em>Pools</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPools()
	 * @generated
	 * @ordered
	 */
    protected EList<Pool> pools;

    /**
	 * The cached value of the '{@link #getMessages() <em>Messages</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getMessages()
	 * @generated
	 * @ordered
	 */
    protected EList<MessagingEdge> messages;

    /**
	 * The default value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
    protected static final String AUTHOR_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
    protected String author = AUTHOR_EDEFAULT;

    /**
	 * The default value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
    protected static final String TITLE_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getTitle() <em>Title</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getTitle()
	 * @generated
	 * @ordered
	 */
    protected String title = TITLE_EDEFAULT;

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected BpmnDiagramImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
				protected EClass eStaticClass() {
		return BpmnPackage.Literals.BPMN_DIAGRAM;
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
			eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.BPMN_DIAGRAM__DOCUMENTATION, oldDocumentation, documentation));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.BPMN_DIAGRAM__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.BPMN_DIAGRAM__NCNAME, oldNcname, ncname));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<Pool> getPools() {
		if (pools == null) {
			pools = new EObjectContainmentWithInverseEList<Pool>(Pool.class, this, BpmnPackage.BPMN_DIAGRAM__POOLS, BpmnPackage.POOL__BPMN_DIAGRAM);
		}
		return pools;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<MessagingEdge> getMessages() {
		if (messages == null) {
			messages = new EObjectContainmentWithInverseEList<MessagingEdge>(MessagingEdge.class, this, BpmnPackage.BPMN_DIAGRAM__MESSAGES, BpmnPackage.MESSAGING_EDGE__BPMN_DIAGRAM);
		}
		return messages;
	}

    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Artifact> getArtifacts() {
		if (artifacts == null) {
			artifacts = new EObjectContainmentWithInverseEList<Artifact>(Artifact.class, this, BpmnPackage.BPMN_DIAGRAM__ARTIFACTS, BpmnPackage.ARTIFACT__ARTIFACTS_CONTAINER);
		}
		return artifacts;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getAuthor() {
		return author;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setAuthor(String newAuthor) {
		String oldAuthor = author;
		author = newAuthor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.BPMN_DIAGRAM__AUTHOR, oldAuthor, author));
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getTitle() {
		return title;
	}

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setTitle(String newTitle) {
		String oldTitle = title;
		title = newTitle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.BPMN_DIAGRAM__TITLE, oldTitle, title));
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
			case BpmnPackage.BPMN_DIAGRAM__ARTIFACTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getArtifacts()).basicAdd(otherEnd, msgs);
			case BpmnPackage.BPMN_DIAGRAM__POOLS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getPools()).basicAdd(otherEnd, msgs);
			case BpmnPackage.BPMN_DIAGRAM__MESSAGES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getMessages()).basicAdd(otherEnd, msgs);
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
			case BpmnPackage.BPMN_DIAGRAM__ARTIFACTS:
				return ((InternalEList<?>)getArtifacts()).basicRemove(otherEnd, msgs);
			case BpmnPackage.BPMN_DIAGRAM__POOLS:
				return ((InternalEList<?>)getPools()).basicRemove(otherEnd, msgs);
			case BpmnPackage.BPMN_DIAGRAM__MESSAGES:
				return ((InternalEList<?>)getMessages()).basicRemove(otherEnd, msgs);
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
			case BpmnPackage.BPMN_DIAGRAM__DOCUMENTATION:
				return getDocumentation();
			case BpmnPackage.BPMN_DIAGRAM__NAME:
				return getName();
			case BpmnPackage.BPMN_DIAGRAM__NCNAME:
				return getNcname();
			case BpmnPackage.BPMN_DIAGRAM__ARTIFACTS:
				return getArtifacts();
			case BpmnPackage.BPMN_DIAGRAM__POOLS:
				return getPools();
			case BpmnPackage.BPMN_DIAGRAM__MESSAGES:
				return getMessages();
			case BpmnPackage.BPMN_DIAGRAM__AUTHOR:
				return getAuthor();
			case BpmnPackage.BPMN_DIAGRAM__TITLE:
				return getTitle();
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
			case BpmnPackage.BPMN_DIAGRAM__DOCUMENTATION:
				setDocumentation((String)newValue);
				return;
			case BpmnPackage.BPMN_DIAGRAM__NAME:
				setName((String)newValue);
				return;
			case BpmnPackage.BPMN_DIAGRAM__NCNAME:
				setNcname((String)newValue);
				return;
			case BpmnPackage.BPMN_DIAGRAM__ARTIFACTS:
				getArtifacts().clear();
				getArtifacts().addAll((Collection<? extends Artifact>)newValue);
				return;
			case BpmnPackage.BPMN_DIAGRAM__POOLS:
				getPools().clear();
				getPools().addAll((Collection<? extends Pool>)newValue);
				return;
			case BpmnPackage.BPMN_DIAGRAM__MESSAGES:
				getMessages().clear();
				getMessages().addAll((Collection<? extends MessagingEdge>)newValue);
				return;
			case BpmnPackage.BPMN_DIAGRAM__AUTHOR:
				setAuthor((String)newValue);
				return;
			case BpmnPackage.BPMN_DIAGRAM__TITLE:
				setTitle((String)newValue);
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
			case BpmnPackage.BPMN_DIAGRAM__DOCUMENTATION:
				setDocumentation(DOCUMENTATION_EDEFAULT);
				return;
			case BpmnPackage.BPMN_DIAGRAM__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BpmnPackage.BPMN_DIAGRAM__NCNAME:
				setNcname(NCNAME_EDEFAULT);
				return;
			case BpmnPackage.BPMN_DIAGRAM__ARTIFACTS:
				getArtifacts().clear();
				return;
			case BpmnPackage.BPMN_DIAGRAM__POOLS:
				getPools().clear();
				return;
			case BpmnPackage.BPMN_DIAGRAM__MESSAGES:
				getMessages().clear();
				return;
			case BpmnPackage.BPMN_DIAGRAM__AUTHOR:
				setAuthor(AUTHOR_EDEFAULT);
				return;
			case BpmnPackage.BPMN_DIAGRAM__TITLE:
				setTitle(TITLE_EDEFAULT);
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
			case BpmnPackage.BPMN_DIAGRAM__DOCUMENTATION:
				return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
			case BpmnPackage.BPMN_DIAGRAM__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BpmnPackage.BPMN_DIAGRAM__NCNAME:
				return NCNAME_EDEFAULT == null ? ncname != null : !NCNAME_EDEFAULT.equals(ncname);
			case BpmnPackage.BPMN_DIAGRAM__ARTIFACTS:
				return artifacts != null && !artifacts.isEmpty();
			case BpmnPackage.BPMN_DIAGRAM__POOLS:
				return pools != null && !pools.isEmpty();
			case BpmnPackage.BPMN_DIAGRAM__MESSAGES:
				return messages != null && !messages.isEmpty();
			case BpmnPackage.BPMN_DIAGRAM__AUTHOR:
				return AUTHOR_EDEFAULT == null ? author != null : !AUTHOR_EDEFAULT.equals(author);
			case BpmnPackage.BPMN_DIAGRAM__TITLE:
				return TITLE_EDEFAULT == null ? title != null : !TITLE_EDEFAULT.equals(title);
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
				case BpmnPackage.BPMN_DIAGRAM__DOCUMENTATION: return BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION;
				case BpmnPackage.BPMN_DIAGRAM__NAME: return BpmnPackage.NAMED_BPMN_OBJECT__NAME;
				case BpmnPackage.BPMN_DIAGRAM__NCNAME: return BpmnPackage.NAMED_BPMN_OBJECT__NCNAME;
				default: return -1;
			}
		}
		if (baseClass == ArtifactsContainer.class) {
			switch (derivedFeatureID) {
				case BpmnPackage.BPMN_DIAGRAM__ARTIFACTS: return BpmnPackage.ARTIFACTS_CONTAINER__ARTIFACTS;
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
				case BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION: return BpmnPackage.BPMN_DIAGRAM__DOCUMENTATION;
				case BpmnPackage.NAMED_BPMN_OBJECT__NAME: return BpmnPackage.BPMN_DIAGRAM__NAME;
				case BpmnPackage.NAMED_BPMN_OBJECT__NCNAME: return BpmnPackage.BPMN_DIAGRAM__NCNAME;
				default: return -1;
			}
		}
		if (baseClass == ArtifactsContainer.class) {
			switch (baseFeatureID) {
				case BpmnPackage.ARTIFACTS_CONTAINER__ARTIFACTS: return BpmnPackage.BPMN_DIAGRAM__ARTIFACTS;
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
		result.append(BpmnMessages.BpmnDiagramImpl_doc);
		result.append(documentation);
		result.append(BpmnMessages.BpmnDiagramImpl_name);
		result.append(name);
		result.append(BpmnMessages.BpmnDiagramImpl_ncname);
		result.append(ncname);
		result.append(BpmnMessages.BpmnDiagramImpl_author);
		result.append(author);
		result.append(BpmnMessages.BpmnDiagramImpl_title);
		result.append(title);
		result.append(')');
		return result.toString();
	}

} //BpmnDiagramImpl