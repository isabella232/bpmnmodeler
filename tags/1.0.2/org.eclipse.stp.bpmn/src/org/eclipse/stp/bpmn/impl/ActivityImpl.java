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
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.Group;
import org.eclipse.stp.bpmn.Lane;
import org.eclipse.stp.bpmn.MessageVertex;
import org.eclipse.stp.bpmn.BpmnMessages;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.NamedBpmnObject;
import org.eclipse.stp.bpmn.SubProcess;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Activity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.impl.ActivityImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.ActivityImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.ActivityImpl#getNcname <em>Ncname</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.ActivityImpl#getOrderedMessages <em>Ordered Messages</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.ActivityImpl#getIncomingMessages <em>Incoming Messages</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.ActivityImpl#getOutgoingMessages <em>Outgoing Messages</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.ActivityImpl#getGroups <em>Groups</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.ActivityImpl#getLanes <em>Lanes</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.ActivityImpl#getActivityType <em>Activity Type</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.ActivityImpl#isLooping <em>Looping</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.ActivityImpl#getEventHandlerFor <em>Event Handler For</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActivityImpl extends VertexImpl implements Activity {
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
     * The cached value of the '{@link #getOrderedMessages() <em>Ordered Messages</em>}' attribute list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getOrderedMessages()
     * @generated
     * @ordered
     */
	protected FeatureMap orderedMessages;

    /**
     * The cached value of the '{@link #getGroups() <em>Groups</em>}' reference list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getGroups()
     * @generated
     * @ordered
     */
	protected EList<Group> groups;

    /**
     * The cached value of the '{@link #getLanes() <em>Lanes</em>}' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLanes()
     * @generated
     * @ordered
     */
    protected EList<Lane> lanes;

    /**
     * The default value of the '{@link #getActivityType() <em>Activity Type</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getActivityType()
     * @generated
     * @ordered
     */
	protected static final ActivityType ACTIVITY_TYPE_EDEFAULT = ActivityType.TASK_LITERAL;

    /**
     * The cached value of the '{@link #getActivityType() <em>Activity Type</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getActivityType()
     * @generated
     * @ordered
     */
	protected ActivityType activityType = ACTIVITY_TYPE_EDEFAULT;

    /**
     * This is true if the Activity Type attribute has been set.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	protected boolean activityTypeESet;

    /**
     * The default value of the '{@link #isLooping() <em>Looping</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #isLooping()
     * @generated
     * @ordered
     */
	protected static final boolean LOOPING_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isLooping() <em>Looping</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #isLooping()
     * @generated
     * @ordered
     */
	protected boolean looping = LOOPING_EDEFAULT;

    /**
     * This is true if the Looping attribute has been set.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
	protected boolean loopingESet;

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected ActivityImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected EClass eStaticClass() {
        return BpmnPackage.Literals.ACTIVITY;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public FeatureMap getOrderedMessages() {
        if (orderedMessages == null) {
            orderedMessages = new BasicFeatureMap(this, BpmnPackage.ACTIVITY__ORDERED_MESSAGES);
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
	public EList<Group> getGroups() {
        if (groups == null) {
            groups = new EObjectWithInverseEList.ManyInverse<Group>(Group.class, this, BpmnPackage.ACTIVITY__GROUPS, BpmnPackage.GROUP__ACTIVITIES);
        }
        return groups;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<Lane> getLanes() {
        if (lanes == null) {
            lanes = new EObjectWithInverseEList.ManyInverse<Lane>(Lane.class, this, BpmnPackage.ACTIVITY__LANES, BpmnPackage.LANE__ACTIVITIES);
        }
        return lanes;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public ActivityType getActivityType() {
        return activityType;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setActivityType(ActivityType newActivityType) {
        ActivityType oldActivityType = activityType;
        activityType = newActivityType == null ? ACTIVITY_TYPE_EDEFAULT : newActivityType;
        boolean oldActivityTypeESet = activityTypeESet;
        activityTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.ACTIVITY__ACTIVITY_TYPE, oldActivityType, activityType, !oldActivityTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void unsetActivityType() {
        ActivityType oldActivityType = activityType;
        boolean oldActivityTypeESet = activityTypeESet;
        activityType = ACTIVITY_TYPE_EDEFAULT;
        activityTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, BpmnPackage.ACTIVITY__ACTIVITY_TYPE, oldActivityType, ACTIVITY_TYPE_EDEFAULT, oldActivityTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public boolean isSetActivityType() {
        return activityTypeESet;
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
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.ACTIVITY__DOCUMENTATION, oldDocumentation, documentation));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public SubProcess getEventHandlerFor() {
        if (eContainerFeatureID != BpmnPackage.ACTIVITY__EVENT_HANDLER_FOR) return null;
        return (SubProcess)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public NotificationChain basicSetEventHandlerFor(SubProcess newEventHandlerFor, NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject)newEventHandlerFor, BpmnPackage.ACTIVITY__EVENT_HANDLER_FOR, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setEventHandlerFor(SubProcess newEventHandlerFor) {
        if (newEventHandlerFor != eInternalContainer() || (eContainerFeatureID != BpmnPackage.ACTIVITY__EVENT_HANDLER_FOR && newEventHandlerFor != null)) {
            if (EcoreUtil.isAncestor(this, newEventHandlerFor))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newEventHandlerFor != null)
                msgs = ((InternalEObject)newEventHandlerFor).eInverseAdd(this, BpmnPackage.SUB_PROCESS__EVENT_HANDLERS, SubProcess.class, msgs);
            msgs = basicSetEventHandlerFor(newEventHandlerFor, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.ACTIVITY__EVENT_HANDLER_FOR, newEventHandlerFor, newEventHandlerFor));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public boolean isLooping() {
        return looping;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setLooping(boolean newLooping) {
        boolean oldLooping = looping;
        looping = newLooping;
        boolean oldLoopingESet = loopingESet;
        loopingESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.ACTIVITY__LOOPING, oldLooping, looping, !oldLoopingESet));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void unsetLooping() {
        boolean oldLooping = looping;
        boolean oldLoopingESet = loopingESet;
        looping = LOOPING_EDEFAULT;
        loopingESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, BpmnPackage.ACTIVITY__LOOPING, oldLooping, LOOPING_EDEFAULT, oldLoopingESet));
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public boolean isSetLooping() {
        return loopingESet;
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
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.ACTIVITY__NAME, oldName, name));
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
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.ACTIVITY__NCNAME, oldNcname, ncname));
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
            case BpmnPackage.ACTIVITY__INCOMING_MESSAGES:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncomingMessages()).basicAdd(otherEnd, msgs);
            case BpmnPackage.ACTIVITY__OUTGOING_MESSAGES:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoingMessages()).basicAdd(otherEnd, msgs);
            case BpmnPackage.ACTIVITY__GROUPS:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getGroups()).basicAdd(otherEnd, msgs);
            case BpmnPackage.ACTIVITY__LANES:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getLanes()).basicAdd(otherEnd, msgs);
            case BpmnPackage.ACTIVITY__EVENT_HANDLER_FOR:
                if (eInternalContainer() != null)
                    msgs = eBasicRemoveFromContainer(msgs);
                return basicSetEventHandlerFor((SubProcess)otherEnd, msgs);
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
            case BpmnPackage.ACTIVITY__ORDERED_MESSAGES:
                return ((InternalEList<?>)getOrderedMessages()).basicRemove(otherEnd, msgs);
            case BpmnPackage.ACTIVITY__INCOMING_MESSAGES:
                return ((InternalEList<?>)getIncomingMessages()).basicRemove(otherEnd, msgs);
            case BpmnPackage.ACTIVITY__OUTGOING_MESSAGES:
                return ((InternalEList<?>)getOutgoingMessages()).basicRemove(otherEnd, msgs);
            case BpmnPackage.ACTIVITY__GROUPS:
                return ((InternalEList<?>)getGroups()).basicRemove(otherEnd, msgs);
            case BpmnPackage.ACTIVITY__LANES:
                return ((InternalEList<?>)getLanes()).basicRemove(otherEnd, msgs);
            case BpmnPackage.ACTIVITY__EVENT_HANDLER_FOR:
                return basicSetEventHandlerFor(null, msgs);
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
            case BpmnPackage.ACTIVITY__EVENT_HANDLER_FOR:
                return eInternalContainer().eInverseRemove(this, BpmnPackage.SUB_PROCESS__EVENT_HANDLERS, SubProcess.class, msgs);
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
            case BpmnPackage.ACTIVITY__DOCUMENTATION:
                return getDocumentation();
            case BpmnPackage.ACTIVITY__NAME:
                return getName();
            case BpmnPackage.ACTIVITY__NCNAME:
                return getNcname();
            case BpmnPackage.ACTIVITY__ORDERED_MESSAGES:
                if (coreType) return getOrderedMessages();
                return ((FeatureMap.Internal)getOrderedMessages()).getWrapper();
            case BpmnPackage.ACTIVITY__INCOMING_MESSAGES:
                return getIncomingMessages();
            case BpmnPackage.ACTIVITY__OUTGOING_MESSAGES:
                return getOutgoingMessages();
            case BpmnPackage.ACTIVITY__GROUPS:
                return getGroups();
            case BpmnPackage.ACTIVITY__LANES:
                return getLanes();
            case BpmnPackage.ACTIVITY__ACTIVITY_TYPE:
                return getActivityType();
            case BpmnPackage.ACTIVITY__LOOPING:
                return isLooping() ? Boolean.TRUE : Boolean.FALSE;
            case BpmnPackage.ACTIVITY__EVENT_HANDLER_FOR:
                return getEventHandlerFor();
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
            case BpmnPackage.ACTIVITY__DOCUMENTATION:
                setDocumentation((String)newValue);
                return;
            case BpmnPackage.ACTIVITY__NAME:
                setName((String)newValue);
                return;
            case BpmnPackage.ACTIVITY__NCNAME:
                setNcname((String)newValue);
                return;
            case BpmnPackage.ACTIVITY__ORDERED_MESSAGES:
                ((FeatureMap.Internal)getOrderedMessages()).set(newValue);
                return;
            case BpmnPackage.ACTIVITY__INCOMING_MESSAGES:
                getIncomingMessages().clear();
                getIncomingMessages().addAll((Collection<? extends MessagingEdge>)newValue);
                return;
            case BpmnPackage.ACTIVITY__OUTGOING_MESSAGES:
                getOutgoingMessages().clear();
                getOutgoingMessages().addAll((Collection<? extends MessagingEdge>)newValue);
                return;
            case BpmnPackage.ACTIVITY__GROUPS:
                getGroups().clear();
                getGroups().addAll((Collection<? extends Group>)newValue);
                return;
            case BpmnPackage.ACTIVITY__LANES:
                getLanes().clear();
                getLanes().addAll((Collection<? extends Lane>)newValue);
                return;
            case BpmnPackage.ACTIVITY__ACTIVITY_TYPE:
                setActivityType((ActivityType)newValue);
                return;
            case BpmnPackage.ACTIVITY__LOOPING:
                setLooping(((Boolean)newValue).booleanValue());
                return;
            case BpmnPackage.ACTIVITY__EVENT_HANDLER_FOR:
                setEventHandlerFor((SubProcess)newValue);
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
            case BpmnPackage.ACTIVITY__DOCUMENTATION:
                setDocumentation(DOCUMENTATION_EDEFAULT);
                return;
            case BpmnPackage.ACTIVITY__NAME:
                setName(NAME_EDEFAULT);
                return;
            case BpmnPackage.ACTIVITY__NCNAME:
                setNcname(NCNAME_EDEFAULT);
                return;
            case BpmnPackage.ACTIVITY__ORDERED_MESSAGES:
                getOrderedMessages().clear();
                return;
            case BpmnPackage.ACTIVITY__INCOMING_MESSAGES:
                getIncomingMessages().clear();
                return;
            case BpmnPackage.ACTIVITY__OUTGOING_MESSAGES:
                getOutgoingMessages().clear();
                return;
            case BpmnPackage.ACTIVITY__GROUPS:
                getGroups().clear();
                return;
            case BpmnPackage.ACTIVITY__LANES:
                getLanes().clear();
                return;
            case BpmnPackage.ACTIVITY__ACTIVITY_TYPE:
                unsetActivityType();
                return;
            case BpmnPackage.ACTIVITY__LOOPING:
                unsetLooping();
                return;
            case BpmnPackage.ACTIVITY__EVENT_HANDLER_FOR:
                setEventHandlerFor((SubProcess)null);
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
            case BpmnPackage.ACTIVITY__DOCUMENTATION:
                return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
            case BpmnPackage.ACTIVITY__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case BpmnPackage.ACTIVITY__NCNAME:
                return NCNAME_EDEFAULT == null ? ncname != null : !NCNAME_EDEFAULT.equals(ncname);
            case BpmnPackage.ACTIVITY__ORDERED_MESSAGES:
                return orderedMessages != null && !orderedMessages.isEmpty();
            case BpmnPackage.ACTIVITY__INCOMING_MESSAGES:
                return !getIncomingMessages().isEmpty();
            case BpmnPackage.ACTIVITY__OUTGOING_MESSAGES:
                return !getOutgoingMessages().isEmpty();
            case BpmnPackage.ACTIVITY__GROUPS:
                return groups != null && !groups.isEmpty();
            case BpmnPackage.ACTIVITY__LANES:
                return lanes != null && !lanes.isEmpty();
            case BpmnPackage.ACTIVITY__ACTIVITY_TYPE:
                return isSetActivityType();
            case BpmnPackage.ACTIVITY__LOOPING:
                return isSetLooping();
            case BpmnPackage.ACTIVITY__EVENT_HANDLER_FOR:
                return getEventHandlerFor() != null;
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
                case BpmnPackage.ACTIVITY__DOCUMENTATION: return BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION;
                case BpmnPackage.ACTIVITY__NAME: return BpmnPackage.NAMED_BPMN_OBJECT__NAME;
                case BpmnPackage.ACTIVITY__NCNAME: return BpmnPackage.NAMED_BPMN_OBJECT__NCNAME;
                default: return -1;
            }
        }
        if (baseClass == MessageVertex.class) {
            switch (derivedFeatureID) {
                case BpmnPackage.ACTIVITY__ORDERED_MESSAGES: return BpmnPackage.MESSAGE_VERTEX__ORDERED_MESSAGES;
                case BpmnPackage.ACTIVITY__INCOMING_MESSAGES: return BpmnPackage.MESSAGE_VERTEX__INCOMING_MESSAGES;
                case BpmnPackage.ACTIVITY__OUTGOING_MESSAGES: return BpmnPackage.MESSAGE_VERTEX__OUTGOING_MESSAGES;
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
                case BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION: return BpmnPackage.ACTIVITY__DOCUMENTATION;
                case BpmnPackage.NAMED_BPMN_OBJECT__NAME: return BpmnPackage.ACTIVITY__NAME;
                case BpmnPackage.NAMED_BPMN_OBJECT__NCNAME: return BpmnPackage.ACTIVITY__NCNAME;
                default: return -1;
            }
        }
        if (baseClass == MessageVertex.class) {
            switch (baseFeatureID) {
                case BpmnPackage.MESSAGE_VERTEX__ORDERED_MESSAGES: return BpmnPackage.ACTIVITY__ORDERED_MESSAGES;
                case BpmnPackage.MESSAGE_VERTEX__INCOMING_MESSAGES: return BpmnPackage.ACTIVITY__INCOMING_MESSAGES;
                case BpmnPackage.MESSAGE_VERTEX__OUTGOING_MESSAGES: return BpmnPackage.ACTIVITY__OUTGOING_MESSAGES;
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
        result.append(", orderedMessages: ");
        result.append(orderedMessages);
        result.append(", activityType: ");
        if (activityTypeESet) result.append(activityType); else result.append("<unset>");
        result.append(", looping: ");
        if (loopingESet) result.append(looping); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //ActivityImpl