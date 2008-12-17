/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.stp.bpmn;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Sequence Flow Condition Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.stp.bpmn.BpmnPackage#getSequenceFlowConditionType()
 * @model extendedMetaData="name='SequenceFlowConditionType'"
 * @generated
 */
public enum SequenceFlowConditionType implements Enumerator
{
	/**
     * The '<em><b>None</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #NONE
     * @generated
     * @ordered
     */
	NONE_LITERAL(0, BpmnMessages.SequenceFlowConditionType_none, "None"),  //$NON-NLS-2$
	/**
     * The '<em><b>Expression</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EXPRESSION
     * @generated
     * @ordered
     */
	EXPRESSION_LITERAL(1, BpmnMessages.SequenceFlowConditionType_expression, "Expression"),  //$NON-NLS-2$
	/**
     * The '<em><b>Default</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #DEFAULT
     * @generated
     * @ordered
     */
	DEFAULT_LITERAL(2, BpmnMessages.SequenceFlowConditionType_default, "Default");  //$NON-NLS-2$
	/**
     * The '<em><b>None</b></em>' literal value.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>None</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @see #NONE_LITERAL
     * @model name="None"
     * @generated
     * @ordered
     */
	public static final int NONE = 0;

	/**
     * The '<em><b>Expression</b></em>' literal value.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Expression</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @see #EXPRESSION_LITERAL
     * @model name="Expression"
     * @generated
     * @ordered
     */
	public static final int EXPRESSION = 1;

	/**
     * The '<em><b>Default</b></em>' literal value.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Default</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @see #DEFAULT_LITERAL
     * @model name="Default"
     * @generated
     * @ordered
     */
	public static final int DEFAULT = 2;

	/**
     * An array of all the '<em><b>Sequence Flow Condition Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private static final SequenceFlowConditionType[] VALUES_ARRAY =
	    new SequenceFlowConditionType[] {
            NONE_LITERAL,
            EXPRESSION_LITERAL,
            DEFAULT_LITERAL,
        };

	/**
     * A public read-only list of all the '<em><b>Sequence Flow Condition Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public static final List<SequenceFlowConditionType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
     * Returns the '<em><b>Sequence Flow Condition Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public static SequenceFlowConditionType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SequenceFlowConditionType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

	/**
     * Returns the '<em><b>Sequence Flow Condition Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public static SequenceFlowConditionType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            SequenceFlowConditionType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

	/**
     * Returns the '<em><b>Sequence Flow Condition Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public static SequenceFlowConditionType get(int value) {
        switch (value) {
            case NONE: return NONE_LITERAL;
            case EXPRESSION: return EXPRESSION_LITERAL;
            case DEFAULT: return DEFAULT_LITERAL;
        }
        return null;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private final int value;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private final String name;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private final String literal;

	/**
     * Only this class can construct instances.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private SequenceFlowConditionType(int value, String name, String literal) {
        this.value = value;
        this.name = name;
        this.literal = literal;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public int getValue() {
      return value;
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
	public String getLiteral() {
      return literal;
    }

	/**
     * Returns the literal value of the enumerator, which is its string representation.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public String toString() {
        return literal;
    }
}
