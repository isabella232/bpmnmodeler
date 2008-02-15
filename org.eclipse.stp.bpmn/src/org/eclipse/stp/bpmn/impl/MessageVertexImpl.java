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

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.Identifiable;
import org.eclipse.stp.bpmn.MessageVertex;
import org.eclipse.stp.bpmn.MessagingEdge;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Message Vertex</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.impl.MessageVertexImpl#getEAnnotations <em>EAnnotations</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.MessageVertexImpl#getID <em>ID</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.MessageVertexImpl#getOrderedMessages <em>Ordered Messages</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.MessageVertexImpl#getIncomingMessages <em>Incoming Messages</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.MessageVertexImpl#getOutgoingMessages <em>Outgoing Messages</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MessageVertexImpl extends NamedBpmnObjectImpl implements MessageVertex {
    /**
     * The cached value of the '{@link #getEAnnotations() <em>EAnnotations</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getEAnnotations()
     * @generated
     * @ordered
     */
    protected EList<EAnnotation> eAnnotations;
    /**
     * The default value of the '{@link #getID() <em>ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getID()
     * @generated
     * @ordered
     */
    protected static final String ID_EDEFAULT = null;
    /**
     * The cached value of the '{@link #getID() <em>ID</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getID()
     * @generated
     * @ordered
     */
    protected String iD = ID_EDEFAULT;
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected MessageVertexImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return BpmnPackage.Literals.MESSAGE_VERTEX;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<EAnnotation> getEAnnotations() {
        if (eAnnotations == null) {
            eAnnotations = new EObjectContainmentWithInverseEList<EAnnotation>(EAnnotation.class, this, BpmnPackage.MESSAGE_VERTEX__EANNOTATIONS, EcorePackage.EANNOTATION__EMODEL_ELEMENT);
        }
        return eAnnotations;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getID() {
        return iD;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setID(String newID) {
        String oldID = iD;
        iD = newID;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.MESSAGE_VERTEX__ID, oldID, iD));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FeatureMap getOrderedMessages() {
        if (orderedMessages == null) {
            orderedMessages = new BasicFeatureMap(this, BpmnPackage.MESSAGE_VERTEX__ORDERED_MESSAGES);
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
    public EAnnotation getEAnnotation(String source) {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case BpmnPackage.MESSAGE_VERTEX__EANNOTATIONS:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getEAnnotations()).basicAdd(otherEnd, msgs);
            case BpmnPackage.MESSAGE_VERTEX__INCOMING_MESSAGES:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncomingMessages()).basicAdd(otherEnd, msgs);
            case BpmnPackage.MESSAGE_VERTEX__OUTGOING_MESSAGES:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoingMessages()).basicAdd(otherEnd, msgs);
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
            case BpmnPackage.MESSAGE_VERTEX__EANNOTATIONS:
                return ((InternalEList<?>)getEAnnotations()).basicRemove(otherEnd, msgs);
            case BpmnPackage.MESSAGE_VERTEX__ORDERED_MESSAGES:
                return ((InternalEList<?>)getOrderedMessages()).basicRemove(otherEnd, msgs);
            case BpmnPackage.MESSAGE_VERTEX__INCOMING_MESSAGES:
                return ((InternalEList<?>)getIncomingMessages()).basicRemove(otherEnd, msgs);
            case BpmnPackage.MESSAGE_VERTEX__OUTGOING_MESSAGES:
                return ((InternalEList<?>)getOutgoingMessages()).basicRemove(otherEnd, msgs);
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
            case BpmnPackage.MESSAGE_VERTEX__EANNOTATIONS:
                return getEAnnotations();
            case BpmnPackage.MESSAGE_VERTEX__ID:
                return getID();
            case BpmnPackage.MESSAGE_VERTEX__ORDERED_MESSAGES:
                if (coreType) return getOrderedMessages();
                return ((FeatureMap.Internal)getOrderedMessages()).getWrapper();
            case BpmnPackage.MESSAGE_VERTEX__INCOMING_MESSAGES:
                return getIncomingMessages();
            case BpmnPackage.MESSAGE_VERTEX__OUTGOING_MESSAGES:
                return getOutgoingMessages();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case BpmnPackage.MESSAGE_VERTEX__EANNOTATIONS:
                getEAnnotations().clear();
                getEAnnotations().addAll((Collection<? extends EAnnotation>)newValue);
                return;
            case BpmnPackage.MESSAGE_VERTEX__ID:
                setID((String)newValue);
                return;
            case BpmnPackage.MESSAGE_VERTEX__ORDERED_MESSAGES:
                ((FeatureMap.Internal)getOrderedMessages()).set(newValue);
                return;
            case BpmnPackage.MESSAGE_VERTEX__INCOMING_MESSAGES:
                getIncomingMessages().clear();
                getIncomingMessages().addAll((Collection<? extends MessagingEdge>)newValue);
                return;
            case BpmnPackage.MESSAGE_VERTEX__OUTGOING_MESSAGES:
                getOutgoingMessages().clear();
                getOutgoingMessages().addAll((Collection<? extends MessagingEdge>)newValue);
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
            case BpmnPackage.MESSAGE_VERTEX__EANNOTATIONS:
                getEAnnotations().clear();
                return;
            case BpmnPackage.MESSAGE_VERTEX__ID:
                setID(ID_EDEFAULT);
                return;
            case BpmnPackage.MESSAGE_VERTEX__ORDERED_MESSAGES:
                getOrderedMessages().clear();
                return;
            case BpmnPackage.MESSAGE_VERTEX__INCOMING_MESSAGES:
                getIncomingMessages().clear();
                return;
            case BpmnPackage.MESSAGE_VERTEX__OUTGOING_MESSAGES:
                getOutgoingMessages().clear();
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
            case BpmnPackage.MESSAGE_VERTEX__EANNOTATIONS:
                return eAnnotations != null && !eAnnotations.isEmpty();
            case BpmnPackage.MESSAGE_VERTEX__ID:
                return ID_EDEFAULT == null ? iD != null : !ID_EDEFAULT.equals(iD);
            case BpmnPackage.MESSAGE_VERTEX__ORDERED_MESSAGES:
                return orderedMessages != null && !orderedMessages.isEmpty();
            case BpmnPackage.MESSAGE_VERTEX__INCOMING_MESSAGES:
                return !getIncomingMessages().isEmpty();
            case BpmnPackage.MESSAGE_VERTEX__OUTGOING_MESSAGES:
                return !getOutgoingMessages().isEmpty();
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
        if (baseClass == EObject.class) {
            switch (derivedFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == EModelElement.class) {
            switch (derivedFeatureID) {
                case BpmnPackage.MESSAGE_VERTEX__EANNOTATIONS: return EcorePackage.EMODEL_ELEMENT__EANNOTATIONS;
                default: return -1;
            }
        }
        if (baseClass == Identifiable.class) {
            switch (derivedFeatureID) {
                case BpmnPackage.MESSAGE_VERTEX__ID: return BpmnPackage.IDENTIFIABLE__ID;
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
        if (baseClass == EObject.class) {
            switch (baseFeatureID) {
                default: return -1;
            }
        }
        if (baseClass == EModelElement.class) {
            switch (baseFeatureID) {
                case EcorePackage.EMODEL_ELEMENT__EANNOTATIONS: return BpmnPackage.MESSAGE_VERTEX__EANNOTATIONS;
                default: return -1;
            }
        }
        if (baseClass == Identifiable.class) {
            switch (baseFeatureID) {
                case BpmnPackage.IDENTIFIABLE__ID: return BpmnPackage.MESSAGE_VERTEX__ID;
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
        result.append(" (iD: ");
        result.append(iD);
        result.append(", orderedMessages: ");
        result.append(orderedMessages);
        result.append(')');
        return result.toString();
    }

} //MessageVertexImpl
