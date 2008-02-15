/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.stp.bpmn.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EModelElementImpl;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.AssociationTarget;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.DirectionType;
import org.eclipse.stp.bpmn.IdentifiableNode;
import org.eclipse.stp.bpmn.BpmnMessages;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Association</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.impl.AssociationImpl#getDirection <em>Direction</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.AssociationImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.AssociationImpl#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AssociationImpl extends EModelElementImpl implements Association {
    /**
     * The default value of the '{@link #getDirection() <em>Direction</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getDirection()
     * @generated
     * @ordered
     */
	protected static final DirectionType DIRECTION_EDEFAULT = DirectionType.NONE_LITERAL;

    /**
     * The cached value of the '{@link #getDirection() <em>Direction</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getDirection()
     * @generated
     * @ordered
     */
	protected DirectionType direction = DIRECTION_EDEFAULT;

    /**
     * This is true if the Direction attribute has been set.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	protected boolean directionESet;

    /**
     * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTarget()
     * @generated
     * @ordered
     */
    protected AssociationTarget target;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected AssociationImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected EClass eStaticClass() {
        return BpmnPackage.Literals.ASSOCIATION;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public DirectionType getDirection() {
        return direction;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setDirection(DirectionType newDirection) {
        DirectionType oldDirection = direction;
        direction = newDirection == null ? DIRECTION_EDEFAULT : newDirection;
        boolean oldDirectionESet = directionESet;
        directionESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.ASSOCIATION__DIRECTION, oldDirection, direction, !oldDirectionESet));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void unsetDirection() {
        DirectionType oldDirection = direction;
        boolean oldDirectionESet = directionESet;
        direction = DIRECTION_EDEFAULT;
        directionESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, BpmnPackage.ASSOCIATION__DIRECTION, oldDirection, DIRECTION_EDEFAULT, oldDirectionESet));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public boolean isSetDirection() {
        return directionESet;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public Artifact getSource() {
        if (eContainerFeatureID != BpmnPackage.ASSOCIATION__SOURCE) return null;
        return (Artifact)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSource(Artifact newSource, NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject)newSource, BpmnPackage.ASSOCIATION__SOURCE, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSource(Artifact newSource) {
        if (newSource != eInternalContainer() || (eContainerFeatureID != BpmnPackage.ASSOCIATION__SOURCE && newSource != null)) {
            if (EcoreUtil.isAncestor(this, newSource))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newSource != null)
                msgs = ((InternalEObject)newSource).eInverseAdd(this, BpmnPackage.ARTIFACT__ASSOCIATIONS, Artifact.class, msgs);
            msgs = basicSetSource(newSource, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.ASSOCIATION__SOURCE, newSource, newSource));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public AssociationTarget getTarget() {
        if (target != null && target.eIsProxy()) {
            InternalEObject oldTarget = (InternalEObject)target;
            target = (AssociationTarget)eResolveProxy(oldTarget);
            if (target != oldTarget) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, BpmnPackage.ASSOCIATION__TARGET, oldTarget, target));
            }
        }
        return target;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public AssociationTarget basicGetTarget() {
        return target;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTarget(AssociationTarget newTarget, NotificationChain msgs) {
        AssociationTarget oldTarget = target;
        target = newTarget;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BpmnPackage.ASSOCIATION__TARGET, oldTarget, newTarget);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTarget(AssociationTarget newTarget) {
        if (newTarget != target) {
            NotificationChain msgs = null;
            if (target != null)
                msgs = ((InternalEObject)target).eInverseRemove(this, BpmnPackage.ASSOCIATION_TARGET__ASSOCIATIONS, AssociationTarget.class, msgs);
            if (newTarget != null)
                msgs = ((InternalEObject)newTarget).eInverseAdd(this, BpmnPackage.ASSOCIATION_TARGET__ASSOCIATIONS, AssociationTarget.class, msgs);
            msgs = basicSetTarget(newTarget, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.ASSOCIATION__TARGET, newTarget, newTarget));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case BpmnPackage.ASSOCIATION__SOURCE:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetSource((Artifact)otherEnd, msgs);
            case BpmnPackage.ASSOCIATION__TARGET:
                if (target != null)
                    msgs = ((InternalEObject)target).eInverseRemove(this, BpmnPackage.ASSOCIATION_TARGET__ASSOCIATIONS, AssociationTarget.class, msgs);
                return basicSetTarget((AssociationTarget)otherEnd, msgs);
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
            case BpmnPackage.ASSOCIATION__SOURCE:
                return basicSetSource(null, msgs);
            case BpmnPackage.ASSOCIATION__TARGET:
                return basicSetTarget(null, msgs);
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
            case BpmnPackage.ASSOCIATION__SOURCE:
                return eInternalContainer().eInverseRemove(this, BpmnPackage.ARTIFACT__ASSOCIATIONS, Artifact.class, msgs);
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
            case BpmnPackage.ASSOCIATION__DIRECTION:
                return getDirection();
            case BpmnPackage.ASSOCIATION__SOURCE:
                return getSource();
            case BpmnPackage.ASSOCIATION__TARGET:
                if (resolve) return getTarget();
                return basicGetTarget();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case BpmnPackage.ASSOCIATION__DIRECTION:
                setDirection((DirectionType)newValue);
                return;
            case BpmnPackage.ASSOCIATION__SOURCE:
                setSource((Artifact)newValue);
                return;
            case BpmnPackage.ASSOCIATION__TARGET:
                setTarget((AssociationTarget)newValue);
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
            case BpmnPackage.ASSOCIATION__DIRECTION:
                unsetDirection();
                return;
            case BpmnPackage.ASSOCIATION__SOURCE:
                setSource((Artifact)null);
                return;
            case BpmnPackage.ASSOCIATION__TARGET:
                setTarget((AssociationTarget)null);
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
            case BpmnPackage.ASSOCIATION__DIRECTION:
                return isSetDirection();
            case BpmnPackage.ASSOCIATION__SOURCE:
                return getSource() != null;
            case BpmnPackage.ASSOCIATION__TARGET:
                return target != null;
        }
        return super.eIsSet(featureID);
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
        result.append(" (direction: ");
        if (directionESet) result.append(direction); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //AssociationImpl