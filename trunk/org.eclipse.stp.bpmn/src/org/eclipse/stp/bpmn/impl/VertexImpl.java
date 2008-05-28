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
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.BpmnMessages;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Vertex</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.impl.VertexImpl#getOutgoingEdges <em>Outgoing Edges</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.VertexImpl#getIncomingEdges <em>Incoming Edges</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.VertexImpl#getGraph <em>Graph</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VertexImpl extends AssociationTargetImpl implements Vertex {
    /**
     * The cached value of the '{@link #getOutgoingEdges() <em>Outgoing Edges</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOutgoingEdges()
     * @generated
     * @ordered
     */
    protected EList<SequenceEdge> outgoingEdges;

    /**
     * The cached value of the '{@link #getIncomingEdges() <em>Incoming Edges</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getIncomingEdges()
     * @generated
     * @ordered
     */
    protected EList<SequenceEdge> incomingEdges;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected VertexImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
				protected EClass eStaticClass() {
        return BpmnPackage.Literals.VERTEX;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<SequenceEdge> getOutgoingEdges() {
        if (outgoingEdges == null) {
            outgoingEdges = new EObjectWithInverseResolvingEList<SequenceEdge>(SequenceEdge.class, this, BpmnPackage.VERTEX__OUTGOING_EDGES, BpmnPackage.SEQUENCE_EDGE__SOURCE);
        }
        return outgoingEdges;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<SequenceEdge> getIncomingEdges() {
        if (incomingEdges == null) {
            incomingEdges = new EObjectWithInverseResolvingEList<SequenceEdge>(SequenceEdge.class, this, BpmnPackage.VERTEX__INCOMING_EDGES, BpmnPackage.SEQUENCE_EDGE__TARGET);
        }
        return incomingEdges;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Graph getGraph() {
        if (eContainerFeatureID != BpmnPackage.VERTEX__GRAPH) return null;
        return (Graph)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGraph(Graph newGraph, NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject)newGraph, BpmnPackage.VERTEX__GRAPH, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGraph(Graph newGraph) {
        if (newGraph != eInternalContainer() || (eContainerFeatureID != BpmnPackage.VERTEX__GRAPH && newGraph != null)) {
            if (EcoreUtil.isAncestor(this, newGraph))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newGraph != null)
                msgs = ((InternalEObject)newGraph).eInverseAdd(this, BpmnPackage.GRAPH__VERTICES, Graph.class, msgs);
            msgs = basicSetGraph(newGraph, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.VERTEX__GRAPH, newGraph, newGraph));
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
            case BpmnPackage.VERTEX__OUTGOING_EDGES:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoingEdges()).basicAdd(otherEnd, msgs);
            case BpmnPackage.VERTEX__INCOMING_EDGES:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncomingEdges()).basicAdd(otherEnd, msgs);
            case BpmnPackage.VERTEX__GRAPH:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetGraph((Graph)otherEnd, msgs);
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
            case BpmnPackage.VERTEX__OUTGOING_EDGES:
                return ((InternalEList<?>)getOutgoingEdges()).basicRemove(otherEnd, msgs);
            case BpmnPackage.VERTEX__INCOMING_EDGES:
                return ((InternalEList<?>)getIncomingEdges()).basicRemove(otherEnd, msgs);
            case BpmnPackage.VERTEX__GRAPH:
                return basicSetGraph(null, msgs);
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
            case BpmnPackage.VERTEX__GRAPH:
                return eInternalContainer().eInverseRemove(this, BpmnPackage.GRAPH__VERTICES, Graph.class, msgs);
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
            case BpmnPackage.VERTEX__OUTGOING_EDGES:
                return getOutgoingEdges();
            case BpmnPackage.VERTEX__INCOMING_EDGES:
                return getIncomingEdges();
            case BpmnPackage.VERTEX__GRAPH:
                return getGraph();
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
            case BpmnPackage.VERTEX__OUTGOING_EDGES:
                getOutgoingEdges().clear();
                getOutgoingEdges().addAll((Collection<? extends SequenceEdge>)newValue);
                return;
            case BpmnPackage.VERTEX__INCOMING_EDGES:
                getIncomingEdges().clear();
                getIncomingEdges().addAll((Collection<? extends SequenceEdge>)newValue);
                return;
            case BpmnPackage.VERTEX__GRAPH:
                setGraph((Graph)newValue);
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
            case BpmnPackage.VERTEX__OUTGOING_EDGES:
                getOutgoingEdges().clear();
                return;
            case BpmnPackage.VERTEX__INCOMING_EDGES:
                getIncomingEdges().clear();
                return;
            case BpmnPackage.VERTEX__GRAPH:
                setGraph((Graph)null);
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
            case BpmnPackage.VERTEX__OUTGOING_EDGES:
                return outgoingEdges != null && !outgoingEdges.isEmpty();
            case BpmnPackage.VERTEX__INCOMING_EDGES:
                return incomingEdges != null && !incomingEdges.isEmpty();
            case BpmnPackage.VERTEX__GRAPH:
                return getGraph() != null;
        }
        return super.eIsSet(featureID);
    }

} //VertexImpl