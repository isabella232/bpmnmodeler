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
import org.eclipse.emf.ecore.util.EObjectWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.Lane;
import org.eclipse.stp.bpmn.NamedBpmnObject;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.BpmnMessages;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Lane</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.impl.LaneImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.LaneImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.LaneImpl#getNcname <em>Ncname</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.LaneImpl#getActivities <em>Activities</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.LaneImpl#getPool <em>Pool</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LaneImpl extends AssociationTargetImpl implements Lane {
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
     * The cached value of the '{@link #getActivities() <em>Activities</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getActivities()
     * @generated
     * @ordered
     */
    protected EList<Activity> activities;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected LaneImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
				protected EClass eStaticClass() {
        return BpmnPackage.Literals.LANE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Activity> getActivities() {
        if (activities == null) {
            activities = new EObjectWithInverseEList.ManyInverse<Activity>(Activity.class, this, BpmnPackage.LANE__ACTIVITIES, BpmnPackage.ACTIVITY__LANES);
        }
        return activities;
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
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.LANE__DOCUMENTATION, oldDocumentation, documentation));
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
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.LANE__NAME, oldName, name));
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
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.LANE__NCNAME, oldNcname, ncname));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Pool getPool() {
        if (eContainerFeatureID != BpmnPackage.LANE__POOL) return null;
        return (Pool)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetPool(Pool newPool, NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject)newPool, BpmnPackage.LANE__POOL, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPool(Pool newPool) {
        if (newPool != eInternalContainer() || (eContainerFeatureID != BpmnPackage.LANE__POOL && newPool != null)) {
            if (EcoreUtil.isAncestor(this, newPool))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newPool != null)
                msgs = ((InternalEObject)newPool).eInverseAdd(this, BpmnPackage.POOL__LANES, Pool.class, msgs);
            msgs = basicSetPool(newPool, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.LANE__POOL, newPool, newPool));
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
            case BpmnPackage.LANE__ACTIVITIES:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getActivities()).basicAdd(otherEnd, msgs);
            case BpmnPackage.LANE__POOL:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetPool((Pool)otherEnd, msgs);
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
            case BpmnPackage.LANE__ACTIVITIES:
                return ((InternalEList<?>)getActivities()).basicRemove(otherEnd, msgs);
            case BpmnPackage.LANE__POOL:
                return basicSetPool(null, msgs);
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
            case BpmnPackage.LANE__POOL:
                return eInternalContainer().eInverseRemove(this, BpmnPackage.POOL__LANES, Pool.class, msgs);
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
            case BpmnPackage.LANE__DOCUMENTATION:
                return getDocumentation();
            case BpmnPackage.LANE__NAME:
                return getName();
            case BpmnPackage.LANE__NCNAME:
                return getNcname();
            case BpmnPackage.LANE__ACTIVITIES:
                return getActivities();
            case BpmnPackage.LANE__POOL:
                return getPool();
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
            case BpmnPackage.LANE__DOCUMENTATION:
                setDocumentation((String)newValue);
                return;
            case BpmnPackage.LANE__NAME:
                setName((String)newValue);
                return;
            case BpmnPackage.LANE__NCNAME:
                setNcname((String)newValue);
                return;
            case BpmnPackage.LANE__ACTIVITIES:
                getActivities().clear();
                getActivities().addAll((Collection<? extends Activity>)newValue);
                return;
            case BpmnPackage.LANE__POOL:
                setPool((Pool)newValue);
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
            case BpmnPackage.LANE__DOCUMENTATION:
                setDocumentation(DOCUMENTATION_EDEFAULT);
                return;
            case BpmnPackage.LANE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case BpmnPackage.LANE__NCNAME:
                setNcname(NCNAME_EDEFAULT);
                return;
            case BpmnPackage.LANE__ACTIVITIES:
                getActivities().clear();
                return;
            case BpmnPackage.LANE__POOL:
                setPool((Pool)null);
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
            case BpmnPackage.LANE__DOCUMENTATION:
                return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
            case BpmnPackage.LANE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case BpmnPackage.LANE__NCNAME:
                return NCNAME_EDEFAULT == null ? ncname != null : !NCNAME_EDEFAULT.equals(ncname);
            case BpmnPackage.LANE__ACTIVITIES:
                return activities != null && !activities.isEmpty();
            case BpmnPackage.LANE__POOL:
                return getPool() != null;
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
                case BpmnPackage.LANE__DOCUMENTATION: return BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION;
                case BpmnPackage.LANE__NAME: return BpmnPackage.NAMED_BPMN_OBJECT__NAME;
                case BpmnPackage.LANE__NCNAME: return BpmnPackage.NAMED_BPMN_OBJECT__NCNAME;
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
                case BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION: return BpmnPackage.LANE__DOCUMENTATION;
                case BpmnPackage.NAMED_BPMN_OBJECT__NAME: return BpmnPackage.LANE__NAME;
                case BpmnPackage.NAMED_BPMN_OBJECT__NCNAME: return BpmnPackage.LANE__NCNAME;
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
        result.append(" (documentation: ");
        result.append(documentation);
        result.append(", name: ");
        result.append(name);
        result.append(", ncname: ");
        result.append(ncname);
        result.append(')');
        return result.toString();
    }

} //LaneImpl