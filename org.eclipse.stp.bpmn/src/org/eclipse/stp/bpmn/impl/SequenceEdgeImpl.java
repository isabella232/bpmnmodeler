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
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.NamedBpmnObject;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SequenceFlowConditionType;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.BpmnMessages;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sequence Edge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.impl.SequenceEdgeImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.SequenceEdgeImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.SequenceEdgeImpl#getNcname <em>Ncname</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.SequenceEdgeImpl#getConditionType <em>Condition Type</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.SequenceEdgeImpl#isIsDefault <em>Is Default</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.SequenceEdgeImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.SequenceEdgeImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.SequenceEdgeImpl#getGraph <em>Graph</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SequenceEdgeImpl extends AssociationTargetImpl implements SequenceEdge {
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
     * The default value of the '{@link #getConditionType() <em>Condition Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getConditionType()
     * @generated
     * @ordered
     */
    protected static final SequenceFlowConditionType CONDITION_TYPE_EDEFAULT = SequenceFlowConditionType.NONE_LITERAL;

    /**
     * The cached value of the '{@link #getConditionType() <em>Condition Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getConditionType()
     * @generated
     * @ordered
     */
    protected SequenceFlowConditionType conditionType = CONDITION_TYPE_EDEFAULT;

    /**
     * This is true if the Condition Type attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean conditionTypeESet;

    /**
     * The default value of the '{@link #isIsDefault() <em>Is Default</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIsDefault()
     * @generated
     * @ordered
     */
    protected static final boolean IS_DEFAULT_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isIsDefault() <em>Is Default</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isIsDefault()
     * @generated
     * @ordered
     */
    protected boolean isDefault = IS_DEFAULT_EDEFAULT;

    /**
     * This is true if the Is Default attribute has been set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    protected boolean isDefaultESet;

    /**
     * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getSource()
     * @generated
     * @ordered
     */
    protected Vertex source;

    /**
     * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getTarget()
     * @generated
     * @ordered
     */
    protected Vertex target;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected SequenceEdgeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
				protected EClass eStaticClass() {
        return BpmnPackage.Literals.SEQUENCE_EDGE;
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
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.SEQUENCE_EDGE__DOCUMENTATION, oldDocumentation, documentation));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Graph getGraph() {
        if (eContainerFeatureID != BpmnPackage.SEQUENCE_EDGE__GRAPH) return null;
        return (Graph)eContainer();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetGraph(Graph newGraph, NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject)newGraph, BpmnPackage.SEQUENCE_EDGE__GRAPH, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGraph(Graph newGraph) {
        if (newGraph != eInternalContainer() || (eContainerFeatureID != BpmnPackage.SEQUENCE_EDGE__GRAPH && newGraph != null)) {
            if (EcoreUtil.isAncestor(this, newGraph))
                throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newGraph != null)
                msgs = ((InternalEObject)newGraph).eInverseAdd(this, BpmnPackage.GRAPH__SEQUENCE_EDGES, Graph.class, msgs);
            msgs = basicSetGraph(newGraph, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.SEQUENCE_EDGE__GRAPH, newGraph, newGraph));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated not
     */
    public boolean isIsDefault() {
        return isDefault || SequenceFlowConditionType.DEFAULT_LITERAL.equals(getConditionType());
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated not
     * @deprecated kept for backward compatibility. Equivalent to setConditionType(SequenceFlowConditionType.DEFAULT_LITERAL).
     */
    public void setIsDefault(boolean newIsDefault) {
        if (newIsDefault) {
        setConditionType(SequenceFlowConditionType.DEFAULT_LITERAL);
        } else if (getConditionType() == SequenceFlowConditionType.DEFAULT_LITERAL) {
            setConditionType(SequenceFlowConditionType.NONE_LITERAL);
        }
//        boolean oldIsDefault = isDefault;
//        isDefault = newIsDefault;
//        boolean oldIsDefaultESet = isDefaultESet;
//        isDefaultESet = true;
//        if (eNotificationRequired())
//            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.SEQUENCE_EDGE__IS_DEFAULT, oldIsDefault, isDefault, !oldIsDefaultESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated not
     * @deprecated kept for backward compatibility.
     */
    public void unsetIsDefault() {
//        boolean oldIsDefault = isDefault;
//        boolean oldIsDefaultESet = isDefaultESet;
//        isDefault = IS_DEFAULT_EDEFAULT;
//        isDefaultESet = false;
//        if (eNotificationRequired())
//            eNotify(new ENotificationImpl(this, Notification.UNSET, BpmnPackage.SEQUENCE_EDGE__IS_DEFAULT, oldIsDefault, IS_DEFAULT_EDEFAULT, oldIsDefaultESet));
        unsetConditionType();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated not
     * @deprecated kept for backward compatibility.
     */
    public boolean isSetIsDefault() {
//        return isDefaultESet;
        return isSetConditionType();
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
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.SEQUENCE_EDGE__NAME, oldName, name));
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
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.SEQUENCE_EDGE__NCNAME, oldNcname, ncname));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public SequenceFlowConditionType getConditionType() {
        return conditionType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setConditionType(SequenceFlowConditionType newConditionType) {
        SequenceFlowConditionType oldConditionType = conditionType;
        conditionType = newConditionType == null ? CONDITION_TYPE_EDEFAULT : newConditionType;
        boolean oldConditionTypeESet = conditionTypeESet;
        conditionTypeESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.SEQUENCE_EDGE__CONDITION_TYPE, oldConditionType, conditionType, !oldConditionTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void unsetConditionType() {
        SequenceFlowConditionType oldConditionType = conditionType;
        boolean oldConditionTypeESet = conditionTypeESet;
        conditionType = CONDITION_TYPE_EDEFAULT;
        conditionTypeESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, BpmnPackage.SEQUENCE_EDGE__CONDITION_TYPE, oldConditionType, CONDITION_TYPE_EDEFAULT, oldConditionTypeESet));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isSetConditionType() {
        return conditionTypeESet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Vertex getSource() {
        return source;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetSource(Vertex newSource, NotificationChain msgs) {
        Vertex oldSource = source;
        source = newSource;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BpmnPackage.SEQUENCE_EDGE__SOURCE, oldSource, newSource);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setSource(Vertex newSource) {
        if (newSource != source) {
            NotificationChain msgs = null;
            if (source != null)
                msgs = ((InternalEObject)source).eInverseRemove(this, BpmnPackage.VERTEX__OUTGOING_EDGES, Vertex.class, msgs);
            if (newSource != null)
                msgs = ((InternalEObject)newSource).eInverseAdd(this, BpmnPackage.VERTEX__OUTGOING_EDGES, Vertex.class, msgs);
            msgs = basicSetSource(newSource, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.SEQUENCE_EDGE__SOURCE, newSource, newSource));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Vertex getTarget() {
        return target;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetTarget(Vertex newTarget, NotificationChain msgs) {
        Vertex oldTarget = target;
        target = newTarget;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BpmnPackage.SEQUENCE_EDGE__TARGET, oldTarget, newTarget);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setTarget(Vertex newTarget) {
        if (newTarget != target) {
            NotificationChain msgs = null;
            if (target != null)
                msgs = ((InternalEObject)target).eInverseRemove(this, BpmnPackage.VERTEX__INCOMING_EDGES, Vertex.class, msgs);
            if (newTarget != null)
                msgs = ((InternalEObject)newTarget).eInverseAdd(this, BpmnPackage.VERTEX__INCOMING_EDGES, Vertex.class, msgs);
            msgs = basicSetTarget(newTarget, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.SEQUENCE_EDGE__TARGET, newTarget, newTarget));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
				public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case BpmnPackage.SEQUENCE_EDGE__SOURCE:
                if (source != null)
                    msgs = ((InternalEObject)source).eInverseRemove(this, BpmnPackage.VERTEX__OUTGOING_EDGES, Vertex.class, msgs);
                return basicSetSource((Vertex)otherEnd, msgs);
            case BpmnPackage.SEQUENCE_EDGE__TARGET:
                if (target != null)
                    msgs = ((InternalEObject)target).eInverseRemove(this, BpmnPackage.VERTEX__INCOMING_EDGES, Vertex.class, msgs);
                return basicSetTarget((Vertex)otherEnd, msgs);
            case BpmnPackage.SEQUENCE_EDGE__GRAPH:
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
            case BpmnPackage.SEQUENCE_EDGE__SOURCE:
                return basicSetSource(null, msgs);
            case BpmnPackage.SEQUENCE_EDGE__TARGET:
                return basicSetTarget(null, msgs);
            case BpmnPackage.SEQUENCE_EDGE__GRAPH:
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
            case BpmnPackage.SEQUENCE_EDGE__GRAPH:
                return eInternalContainer().eInverseRemove(this, BpmnPackage.GRAPH__SEQUENCE_EDGES, Graph.class, msgs);
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
            case BpmnPackage.SEQUENCE_EDGE__DOCUMENTATION:
                return getDocumentation();
            case BpmnPackage.SEQUENCE_EDGE__NAME:
                return getName();
            case BpmnPackage.SEQUENCE_EDGE__NCNAME:
                return getNcname();
            case BpmnPackage.SEQUENCE_EDGE__CONDITION_TYPE:
                return getConditionType();
            case BpmnPackage.SEQUENCE_EDGE__IS_DEFAULT:
                return isIsDefault() ? Boolean.TRUE : Boolean.FALSE;
            case BpmnPackage.SEQUENCE_EDGE__SOURCE:
                return getSource();
            case BpmnPackage.SEQUENCE_EDGE__TARGET:
                return getTarget();
            case BpmnPackage.SEQUENCE_EDGE__GRAPH:
                return getGraph();
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
            case BpmnPackage.SEQUENCE_EDGE__DOCUMENTATION:
                setDocumentation((String)newValue);
                return;
            case BpmnPackage.SEQUENCE_EDGE__NAME:
                setName((String)newValue);
                return;
            case BpmnPackage.SEQUENCE_EDGE__NCNAME:
                setNcname((String)newValue);
                return;
            case BpmnPackage.SEQUENCE_EDGE__CONDITION_TYPE:
                setConditionType((SequenceFlowConditionType)newValue);
                return;
            case BpmnPackage.SEQUENCE_EDGE__IS_DEFAULT:
                setIsDefault(((Boolean)newValue).booleanValue());
                return;
            case BpmnPackage.SEQUENCE_EDGE__SOURCE:
                setSource((Vertex)newValue);
                return;
            case BpmnPackage.SEQUENCE_EDGE__TARGET:
                setTarget((Vertex)newValue);
                return;
            case BpmnPackage.SEQUENCE_EDGE__GRAPH:
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
    public void eUnsetGen(int featureID) {
        switch (featureID) {
            case BpmnPackage.SEQUENCE_EDGE__DOCUMENTATION:
                setDocumentation(DOCUMENTATION_EDEFAULT);
                return;
            case BpmnPackage.SEQUENCE_EDGE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case BpmnPackage.SEQUENCE_EDGE__NCNAME:
                setNcname(NCNAME_EDEFAULT);
                return;
            case BpmnPackage.SEQUENCE_EDGE__CONDITION_TYPE:
                unsetConditionType();
                return;
            case BpmnPackage.SEQUENCE_EDGE__IS_DEFAULT:
                unsetIsDefault();
                return;
            case BpmnPackage.SEQUENCE_EDGE__SOURCE:
                setSource((Vertex)null);
                return;
            case BpmnPackage.SEQUENCE_EDGE__TARGET:
                setTarget((Vertex)null);
                return;
            case BpmnPackage.SEQUENCE_EDGE__GRAPH:
                setGraph((Graph)null);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @notgenerated
     */
    public void eUnset(int featureID) {
        switch (featureID) {
            case BpmnPackage.SEQUENCE_EDGE__SOURCE:
            case BpmnPackage.SEQUENCE_EDGE__TARGET:
                setTarget((Vertex)null);
                setSource((Vertex)null);
                setGraph((Graph)null);
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
            case BpmnPackage.SEQUENCE_EDGE__DOCUMENTATION:
                return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
            case BpmnPackage.SEQUENCE_EDGE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case BpmnPackage.SEQUENCE_EDGE__NCNAME:
                return NCNAME_EDEFAULT == null ? ncname != null : !NCNAME_EDEFAULT.equals(ncname);
            case BpmnPackage.SEQUENCE_EDGE__CONDITION_TYPE:
                return isSetConditionType();
            case BpmnPackage.SEQUENCE_EDGE__IS_DEFAULT:
                return isSetIsDefault();
            case BpmnPackage.SEQUENCE_EDGE__SOURCE:
                return source != null;
            case BpmnPackage.SEQUENCE_EDGE__TARGET:
                return target != null;
            case BpmnPackage.SEQUENCE_EDGE__GRAPH:
                return getGraph() != null;
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
                case BpmnPackage.SEQUENCE_EDGE__DOCUMENTATION: return BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION;
                case BpmnPackage.SEQUENCE_EDGE__NAME: return BpmnPackage.NAMED_BPMN_OBJECT__NAME;
                case BpmnPackage.SEQUENCE_EDGE__NCNAME: return BpmnPackage.NAMED_BPMN_OBJECT__NCNAME;
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
                case BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION: return BpmnPackage.SEQUENCE_EDGE__DOCUMENTATION;
                case BpmnPackage.NAMED_BPMN_OBJECT__NAME: return BpmnPackage.SEQUENCE_EDGE__NAME;
                case BpmnPackage.NAMED_BPMN_OBJECT__NCNAME: return BpmnPackage.SEQUENCE_EDGE__NCNAME;
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
        result.append(", conditionType: ");
        if (conditionTypeESet) result.append(conditionType); else result.append("<unset>");
        result.append(", isDefault: ");
        if (isDefaultESet) result.append(isDefault); else result.append("<unset>");
        result.append(')');
        return result.toString();
    }

} //SequenceEdgeImpl