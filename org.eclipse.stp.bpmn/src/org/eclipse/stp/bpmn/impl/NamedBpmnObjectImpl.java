/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.stp.bpmn.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.NamedBpmnObject;
import org.eclipse.stp.bpmn.BpmnMessages;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Named Bpmn Object</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.stp.bpmn.impl.NamedBpmnObjectImpl#getDocumentation <em>Documentation</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.NamedBpmnObjectImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.stp.bpmn.impl.NamedBpmnObjectImpl#getNcname <em>Ncname</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NamedBpmnObjectImpl extends EObjectImpl implements NamedBpmnObject {
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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected NamedBpmnObjectImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
				protected EClass eStaticClass() {
        return BpmnPackage.Literals.NAMED_BPMN_OBJECT;
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
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION, oldDocumentation, documentation));
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
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.NAMED_BPMN_OBJECT__NAME, oldName, name));
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
            eNotify(new ENotificationImpl(this, Notification.SET, BpmnPackage.NAMED_BPMN_OBJECT__NCNAME, oldNcname, ncname));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
				public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION:
                return getDocumentation();
            case BpmnPackage.NAMED_BPMN_OBJECT__NAME:
                return getName();
            case BpmnPackage.NAMED_BPMN_OBJECT__NCNAME:
                return getNcname();
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
            case BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION:
                setDocumentation((String)newValue);
                return;
            case BpmnPackage.NAMED_BPMN_OBJECT__NAME:
                setName((String)newValue);
                return;
            case BpmnPackage.NAMED_BPMN_OBJECT__NCNAME:
                setNcname((String)newValue);
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
            case BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION:
                setDocumentation(DOCUMENTATION_EDEFAULT);
                return;
            case BpmnPackage.NAMED_BPMN_OBJECT__NAME:
                setName(NAME_EDEFAULT);
                return;
            case BpmnPackage.NAMED_BPMN_OBJECT__NCNAME:
                setNcname(NCNAME_EDEFAULT);
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
            case BpmnPackage.NAMED_BPMN_OBJECT__DOCUMENTATION:
                return DOCUMENTATION_EDEFAULT == null ? documentation != null : !DOCUMENTATION_EDEFAULT.equals(documentation);
            case BpmnPackage.NAMED_BPMN_OBJECT__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case BpmnPackage.NAMED_BPMN_OBJECT__NCNAME:
                return NCNAME_EDEFAULT == null ? ncname != null : !NCNAME_EDEFAULT.equals(ncname);
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
        result.append(" (documentation: ");
        result.append(documentation);
        result.append(", name: ");
        result.append(name);
        result.append(", ncname: ");
        result.append(ncname);
        result.append(')');
        return result.toString();
    }

} //NamedBpmnObjectImpl