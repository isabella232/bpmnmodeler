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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.MessageVertex;
import org.eclipse.stp.bpmn.MessagingEdge;

import org.eclipse.stp.bpmn.NamedBpmnObject;
import org.eclipse.stp.bpmn.BpmnMessages;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Messaging Edge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.impl.MessagingEdgeImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.MessagingEdgeImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.MessagingEdgeImpl#getNcname <em>Ncname</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.MessagingEdgeImpl#getBpmnDiagram <em>Bpmn Diagram</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.MessagingEdgeImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.MessagingEdgeImpl#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MessagingEdgeImpl extends AssociationTargetImpl implements MessagingEdge {
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
     * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSource()
     * @generated
     * @ordered
     */
    protected MessageVertex source;

    /**
     * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTarget()
     * @generated
     * @ordered
     */
    protected MessageVertex target;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected MessagingEdgeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
	protected EClass eStaticClass() {
        return BpmnPackage.Literals.MESSAGING_EDGE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BpmnDiagram getBpmnDiagram() {
        if (eContainerFeatureID != BpmnPackage.MESSAGING_EDGE__BPMN_DIAGRAM) return null;
        return (BpmnDiagram)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetBpmnDiagram(BpmnDiagram newBpmnDiagram, NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject)newBpmnDiagram, BpmnPackage.MESSAGING_EDGE__BPMN_DIAGRAM, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setBpmnDiagram(BpmnDiagram newBpmnDiagram) {
        if (newBpmnDiagram != eInternalContainer() || (eContainerFeatureID != BpmnPackage.MESSAGING_EDGE__BPMN_DIAGRAM && newBpmnDiagram != null)) {
            if (EcoreUtil.isAncestor(this, newBpmnDiagram))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newBpmnDiagram != null)
                msgs = ((InternalEObject)newBpmnDiagram).eInverseAdd(this, BpmnPackage.BPMN_DIAGRAM__MESSAGES, BpmnDiagram.class, msgs);
            msgs = basicSetBpmnDiagram(newBpmnDiagram, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.MESSAGING_EDGE__BPMN_DIAGRAM, newBpmnDiagram, newBpmnDiagram));
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
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.MESSAGING_EDGE__DOCUMENTATION, oldDocumentation, documentation));
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
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.MESSAGING_EDGE__NAME, oldName, name));
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
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.MESSAGING_EDGE__NCNAME, oldNcname, ncname));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MessageVertex getSource() {
        if (source != null && source.eIsProxy()) {
            InternalEObject oldSource = (InternalEObject)source;
            source = (MessageVertex)eResolveProxy(oldSource);
            if (source != oldSource) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, BpmnPackage.MESSAGING_EDGE__SOURCE, oldSource, source));
            }
        }
        return source;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MessageVertex basicGetSource() {
        return source;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSource(MessageVertex newSource, NotificationChain msgs) {
        MessageVertex oldSource = source;
        source = newSource;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BpmnPackage.MESSAGING_EDGE__SOURCE, oldSource, newSource);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSource(MessageVertex newSource) {
        if (newSource != source) {
            NotificationChain msgs = null;
            if (source != null)
                msgs = ((InternalEObject)source).eInverseRemove(this, BpmnPackage.MESSAGE_VERTEX__OUTGOING_MESSAGES, MessageVertex.class, msgs);
            if (newSource != null)
                msgs = ((InternalEObject)newSource).eInverseAdd(this, BpmnPackage.MESSAGE_VERTEX__OUTGOING_MESSAGES, MessageVertex.class, msgs);
            msgs = basicSetSource(newSource, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.MESSAGING_EDGE__SOURCE, newSource, newSource));
    }

    /**
     * <!-- begin-user-doc -->
     * This method is not generated.
     * We found out that when the creation of a message is undone and then 
     * re-done, the change description was setting an empty list on the
     * ordered messages of the source activity of the message.
     * <p>
     * This was because the list of ordered messages was recorded during the
     * undo when there were no more messages (ie after the setSource(null))
     * </p>
     * <p>
     * The fix conjsist of making sure that the change descritption for the
     * ordered message list is created when they are still messages in the list.
     * <br/>
     * The change of behavior is limited to when the newSource is null
     * just to minimize the fix to the moment where it was broken.
     * <br/>
     * Still one thing goes unexplained: we did not need to do the same fix for
     * setTarget. We suspect it is because the order in which things are 
     * executed with regard to source and target when the message is deleted.
     * </p>
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public void setSource(Activity newSource) {
        if (newSource != source) {
            NotificationChain msgs = null;
            if (newSource == null) {
                msgs = basicSetSource(newSource, msgs);
            }
            if (source != null) {
                msgs = ((InternalEObject)source).eInverseRemove(this, BpmnPackage.ACTIVITY__OUTGOING_MESSAGES, Activity.class, msgs);
            }
            if (newSource != null) {
                msgs = ((InternalEObject)newSource).eInverseAdd(this, BpmnPackage.ACTIVITY__OUTGOING_MESSAGES, Activity.class, msgs);
                msgs = basicSetSource(newSource, msgs);
            }
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.MESSAGING_EDGE__SOURCE, newSource, newSource));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MessageVertex getTarget() {
        if (target != null && target.eIsProxy()) {
            InternalEObject oldTarget = (InternalEObject)target;
            target = (MessageVertex)eResolveProxy(oldTarget);
            if (target != oldTarget) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, BpmnPackage.MESSAGING_EDGE__TARGET, oldTarget, target));
            }
        }
        return target;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MessageVertex basicGetTarget() {
        return target;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTarget(MessageVertex newTarget, NotificationChain msgs) {
        MessageVertex oldTarget = target;
        target = newTarget;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BpmnPackage.MESSAGING_EDGE__TARGET, oldTarget, newTarget);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTarget(MessageVertex newTarget) {
        if (newTarget != target) {
            NotificationChain msgs = null;
            if (target != null)
                msgs = ((InternalEObject)target).eInverseRemove(this, BpmnPackage.MESSAGE_VERTEX__INCOMING_MESSAGES, MessageVertex.class, msgs);
            if (newTarget != null)
                msgs = ((InternalEObject)newTarget).eInverseAdd(this, BpmnPackage.MESSAGE_VERTEX__INCOMING_MESSAGES, MessageVertex.class, msgs);
            msgs = basicSetTarget(newTarget, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.MESSAGING_EDGE__TARGET, newTarget, newTarget));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
				public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case BpmnPackage.MESSAGING_EDGE__BPMN_DIAGRAM:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetBpmnDiagram((BpmnDiagram)otherEnd, msgs);
            case BpmnPackage.MESSAGING_EDGE__SOURCE:
                if (source != null)
                    msgs = ((InternalEObject)source).eInverseRemove(this, BpmnPackage.MESSAGE_VERTEX__OUTGOING_MESSAGES, MessageVertex.class, msgs);
                return basicSetSource((MessageVertex)otherEnd, msgs);
            case BpmnPackage.MESSAGING_EDGE__TARGET:
                if (target != null)
                    msgs = ((InternalEObject)target).eInverseRemove(this, BpmnPackage.MESSAGE_VERTEX__INCOMING_MESSAGES, MessageVertex.class, msgs);
                return basicSetTarget((MessageVertex)otherEnd, msgs);
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
            case BpmnPackage.MESSAGING_EDGE__BPMN_DIAGRAM:
                return basicSetBpmnDiagram(null, msgs);
            case BpmnPackage.MESSAGING_EDGE__SOURCE:
                return basicSetSource(null, msgs);
            case BpmnPackage.MESSAGING_EDGE__TARGET:
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
            case BpmnPackage.MESSAGING_EDGE__BPMN_DIAGRAM:
                return eInternalContainer().eInverseRemove(this, BpmnPackage.BPMN_DIAGRAM__MESSAGES, BpmnDiagram.class, msgs);
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
            case BpmnPackage.MESSAGING_EDGE__DOCUMENTATION:
                return getDocumentation();
            case BpmnPackage.MESSAGING_EDGE__NAME:
                return getName();
            case BpmnPackage.MESSAGING_EDGE__NCNAME:
                return getNcname();
            case BpmnPackage.MESSAGING_EDGE__BPMN_DIAGRAM:
                return getBpmnDiagram();
            case BpmnPackage.MESSAGING_EDGE__SOURCE:
                if (resolve) return getSource();
                return basicGetSource();
            case BpmnPackage.MESSAGING_EDGE__TARGET:
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
            case BpmnPackage.MESSAGING_EDGE__DOCUMENTATION:
                setDocumentation((String)newValue);
                return;
            case BpmnPackage.MESSAGING_EDGE__NAME:
                setName((String)newValue);
                return;
            case BpmnPackage.MESSAGING_EDGE__NCNAME:
                setNcname((String)newValue);
                return;
            case BpmnPackage.MESSAGING_EDGE__BPMN_DIAGRAM:
                setBpmnDiagram((BpmnDiagram)newValue);
                return;
            case BpmnPackage.MESSAGING_EDGE__SOURCE:
                setSource((MessageVertex)newValue);
                return;
            case BpmnPackage.MESSAGING_EDGE__TARGET:
                setTarget((MessageVertex)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void eUnsetGen(int featureID) {
        switch (featureID) {
            case BpmnPackage.MESSAGING_EDGE__DOCUMENTATION:
                setDocumentation(DOCUMENTATION_EDEFAULT);
                return;
            case BpmnPackage.MESSAGING_EDGE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case BpmnPackage.MESSAGING_EDGE__NCNAME:
                setNcname(NCNAME_EDEFAULT);
                return;
            case BpmnPackage.MESSAGING_EDGE__BPMN_DIAGRAM:
                setBpmnDiagram((BpmnDiagram)null);
                return;
            case BpmnPackage.MESSAGING_EDGE__SOURCE:
                setSource((MessageVertex)null);
                return;
            case BpmnPackage.MESSAGING_EDGE__TARGET:
                setTarget((MessageVertex)null);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * Fix by Bohdan to make sure messages are correctly removed.
     * <!-- end-user-doc -->
     * @notgenerated
     */
    public void eUnset(int featureID) {
        switch (featureID) {
            case BpmnPackage.MESSAGING_EDGE__SOURCE:
            case BpmnPackage.MESSAGING_EDGE__TARGET:
                setTarget((Activity)null);
                setSource((Activity)null);
                setBpmnDiagram((BpmnDiagram)null);
                return;
            default:
                eUnsetGen(featureID);
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
				public boolean eIsSet(int featureID) {
        switch (featureID) {
            case BpmnPackage.MESSAGING_EDGE__DOCUMENTATION:
                return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
            case BpmnPackage.MESSAGING_EDGE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case BpmnPackage.MESSAGING_EDGE__NCNAME:
                return NCNAME_EDEFAULT == null ? ncname != null : !NCNAME_EDEFAULT.equals(ncname);
            case BpmnPackage.MESSAGING_EDGE__BPMN_DIAGRAM:
                return getBpmnDiagram() != null;
            case BpmnPackage.MESSAGING_EDGE__SOURCE:
                return source != null;
            case BpmnPackage.MESSAGING_EDGE__TARGET:
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
				public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
        if (baseClass == NamedBpmnObject.class) {
            switch (derivedFeatureID) {
                case BpmnPackage.MESSAGING_EDGE__DOCUMENTATION: return BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION;
                case BpmnPackage.MESSAGING_EDGE__NAME: return BpmnPackage.NAMED_BPMN_OBJECT__NAME;
                case BpmnPackage.MESSAGING_EDGE__NCNAME: return BpmnPackage.NAMED_BPMN_OBJECT__NCNAME;
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
                case BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION: return BpmnPackage.MESSAGING_EDGE__DOCUMENTATION;
                case BpmnPackage.NAMED_BPMN_OBJECT__NAME: return BpmnPackage.MESSAGING_EDGE__NAME;
                case BpmnPackage.NAMED_BPMN_OBJECT__NCNAME: return BpmnPackage.MESSAGING_EDGE__NCNAME;
                default: return -1;
            }
        }
        return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
    }

    /**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated not added i18n
	 */
    @Override
				public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (");
		result.append(BpmnMessages.MessagingEdgeImpl_documentation);
		result.append(documentation);
		result.append(BpmnMessages.MessagingEdgeImpl_name);
		result.append(name);
		result.append(BpmnMessages.MessagingEdgeImpl_ncname);
		result.append(ncname);
		result.append(')');
		return result.toString();
	}

} //MessagingEdgeImpl