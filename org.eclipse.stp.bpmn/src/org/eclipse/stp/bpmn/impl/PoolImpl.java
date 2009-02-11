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
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.Lane;
import org.eclipse.stp.bpmn.MessageVertex;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.BpmnMessages;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pool</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.impl.PoolImpl#getOrderedMessages <em>Ordered Messages</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.PoolImpl#getIncomingMessages <em>Incoming Messages</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.PoolImpl#getOutgoingMessages <em>Outgoing Messages</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.PoolImpl#getLanes <em>Lanes</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.PoolImpl#getBpmnDiagram <em>Bpmn Diagram</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PoolImpl extends GraphImpl implements Pool {
    /**
     * The cached value of the '{@link #getOrderedMessages() <em>Ordered Messages</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOrderedMessages()
     * @generated
     * @ordered
     */
    protected FeatureMap orderedMessages;
    /**
     * The cached value of the '{@link #getLanes() <em>Lanes</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLanes()
     * @generated
     * @ordered
     */
    protected EList<Lane> lanes;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PoolImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
				protected EClass eStaticClass() {
        return BpmnPackage.Literals.POOL;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOrderedMessages() {
        if (orderedMessages == null) {
            orderedMessages = new BasicFeatureMap(this, BpmnPackage.POOL__ORDERED_MESSAGES);
        }
        return orderedMessages;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<MessagingEdge> getIncomingMessages() {
        return getOrderedMessages().list(BpmnPackage.Literals.MESSAGE_VERTEX__INCOMING_MESSAGES);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<MessagingEdge> getOutgoingMessages() {
        return getOrderedMessages().list(BpmnPackage.Literals.MESSAGE_VERTEX__OUTGOING_MESSAGES);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Lane> getLanes() {
        if (lanes == null) {
            lanes = new EObjectContainmentWithInverseEList<Lane>(Lane.class, this, BpmnPackage.POOL__LANES, BpmnPackage.LANE__POOL);
        }
        return lanes;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BpmnDiagram getBpmnDiagram() {
        if (eContainerFeatureID() != BpmnPackage.POOL__BPMN_DIAGRAM) return null;
        return (BpmnDiagram)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetBpmnDiagram(BpmnDiagram newBpmnDiagram, NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject)newBpmnDiagram, BpmnPackage.POOL__BPMN_DIAGRAM, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setBpmnDiagram(BpmnDiagram newBpmnDiagram) {
        if (newBpmnDiagram != eInternalContainer() || (eContainerFeatureID() != BpmnPackage.POOL__BPMN_DIAGRAM && newBpmnDiagram != null)) {
            if (EcoreUtil.isAncestor(this, newBpmnDiagram))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newBpmnDiagram != null)
                msgs = ((InternalEObject)newBpmnDiagram).eInverseAdd(this, BpmnPackage.BPMN_DIAGRAM__POOLS, BpmnDiagram.class, msgs);
            msgs = basicSetBpmnDiagram(newBpmnDiagram, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.POOL__BPMN_DIAGRAM, newBpmnDiagram, newBpmnDiagram));
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
            case BpmnPackage.POOL__INCOMING_MESSAGES:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncomingMessages()).basicAdd(otherEnd, msgs);
            case BpmnPackage.POOL__OUTGOING_MESSAGES:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoingMessages()).basicAdd(otherEnd, msgs);
            case BpmnPackage.POOL__LANES:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getLanes()).basicAdd(otherEnd, msgs);
            case BpmnPackage.POOL__BPMN_DIAGRAM:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetBpmnDiagram((BpmnDiagram)otherEnd, msgs);
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
            case BpmnPackage.POOL__ORDERED_MESSAGES:
                return ((InternalEList<?>)getOrderedMessages()).basicRemove(otherEnd, msgs);
            case BpmnPackage.POOL__INCOMING_MESSAGES:
                return ((InternalEList<?>)getIncomingMessages()).basicRemove(otherEnd, msgs);
            case BpmnPackage.POOL__OUTGOING_MESSAGES:
                return ((InternalEList<?>)getOutgoingMessages()).basicRemove(otherEnd, msgs);
            case BpmnPackage.POOL__LANES:
                return ((InternalEList<?>)getLanes()).basicRemove(otherEnd, msgs);
            case BpmnPackage.POOL__BPMN_DIAGRAM:
                return basicSetBpmnDiagram(null, msgs);
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
        switch (eContainerFeatureID()) {
            case BpmnPackage.POOL__BPMN_DIAGRAM:
                return eInternalContainer().eInverseRemove(this, BpmnPackage.BPMN_DIAGRAM__POOLS, BpmnDiagram.class, msgs);
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
            case BpmnPackage.POOL__ORDERED_MESSAGES:
                if (coreType) return getOrderedMessages();
                return ((FeatureMap.Internal)getOrderedMessages()).getWrapper();
            case BpmnPackage.POOL__INCOMING_MESSAGES:
                return getIncomingMessages();
            case BpmnPackage.POOL__OUTGOING_MESSAGES:
                return getOutgoingMessages();
            case BpmnPackage.POOL__LANES:
                return getLanes();
            case BpmnPackage.POOL__BPMN_DIAGRAM:
                return getBpmnDiagram();
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
            case BpmnPackage.POOL__ORDERED_MESSAGES:
                ((FeatureMap.Internal)getOrderedMessages()).set(newValue);
                return;
            case BpmnPackage.POOL__INCOMING_MESSAGES:
                getIncomingMessages().clear();
                getIncomingMessages().addAll((Collection<? extends MessagingEdge>)newValue);
                return;
            case BpmnPackage.POOL__OUTGOING_MESSAGES:
                getOutgoingMessages().clear();
                getOutgoingMessages().addAll((Collection<? extends MessagingEdge>)newValue);
                return;
            case BpmnPackage.POOL__LANES:
                getLanes().clear();
                getLanes().addAll((Collection<? extends Lane>)newValue);
                return;
            case BpmnPackage.POOL__BPMN_DIAGRAM:
                setBpmnDiagram((BpmnDiagram)newValue);
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
            case BpmnPackage.POOL__ORDERED_MESSAGES:
                getOrderedMessages().clear();
                return;
            case BpmnPackage.POOL__INCOMING_MESSAGES:
                getIncomingMessages().clear();
                return;
            case BpmnPackage.POOL__OUTGOING_MESSAGES:
                getOutgoingMessages().clear();
                return;
            case BpmnPackage.POOL__LANES:
                getLanes().clear();
                return;
            case BpmnPackage.POOL__BPMN_DIAGRAM:
                setBpmnDiagram((BpmnDiagram)null);
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
            case BpmnPackage.POOL__ORDERED_MESSAGES:
                return orderedMessages != null && !orderedMessages.isEmpty();
            case BpmnPackage.POOL__INCOMING_MESSAGES:
                return !getIncomingMessages().isEmpty();
            case BpmnPackage.POOL__OUTGOING_MESSAGES:
                return !getOutgoingMessages().isEmpty();
            case BpmnPackage.POOL__LANES:
                return lanes != null && !lanes.isEmpty();
            case BpmnPackage.POOL__BPMN_DIAGRAM:
                return getBpmnDiagram() != null;
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
        if (baseClass == MessageVertex.class) {
            switch (derivedFeatureID) {
                case BpmnPackage.POOL__ORDERED_MESSAGES: return BpmnPackage.MESSAGE_VERTEX__ORDERED_MESSAGES;
                case BpmnPackage.POOL__INCOMING_MESSAGES: return BpmnPackage.MESSAGE_VERTEX__INCOMING_MESSAGES;
                case BpmnPackage.POOL__OUTGOING_MESSAGES: return BpmnPackage.MESSAGE_VERTEX__OUTGOING_MESSAGES;
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
        if (baseClass == MessageVertex.class) {
            switch (baseFeatureID) {
                case BpmnPackage.MESSAGE_VERTEX__ORDERED_MESSAGES: return BpmnPackage.POOL__ORDERED_MESSAGES;
                case BpmnPackage.MESSAGE_VERTEX__INCOMING_MESSAGES: return BpmnPackage.POOL__INCOMING_MESSAGES;
                case BpmnPackage.MESSAGE_VERTEX__OUTGOING_MESSAGES: return BpmnPackage.POOL__OUTGOING_MESSAGES;
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
        result.append(" (orderedMessages: ");
        result.append(orderedMessages);
        result.append(')');
        return result.toString();
    }

} //PoolImpl