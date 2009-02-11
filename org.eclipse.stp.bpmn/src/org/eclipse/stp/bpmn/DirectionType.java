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
 * A representation of the literals of the enumeration '<em><b>Direction Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.stp.bpmn.BpmnPackage#getDirectionType()
 * @model extendedMetaData="name='direction_._type'"
 * @generated
 */
public enum DirectionType implements Enumerator
{
	/**
     * The '<em><b>None</b></em>' literal object.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #NONE
     * @generated NOT i18n
     * @ordered
     */
	NONE_LITERAL(0, BpmnMessages.DirectionType_none, "None"),  //$NON-NLS-1$
	/**
     * The '<em><b>To</b></em>' literal object.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #TO
     * @generated NOT i18n
     * @ordered
     */
	TO_LITERAL(1, BpmnMessages.DirectionType_to, "To"),  //$NON-NLS-1$
	/**
     * The '<em><b>From</b></em>' literal object.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #FROM
     * @generated NOT i18n
     * @ordered
     */
	FROM_LITERAL(2, BpmnMessages.DirectionType_from, "From"),  //$NON-NLS-1$
	/**
     * The '<em><b>Both</b></em>' literal object.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #BOTH
     * @generated NOT i18n
     * @ordered
     */
	BOTH_LITERAL(3, BpmnMessages.DirectionType_both, "Both");  //$NON-NLS-1$
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
     * The '<em><b>To</b></em>' literal value.
     * <!-- begin-user-doc -->
* <p>
* To as in it point to the Artifact at the source of the association.
* </p>
* <!-- end-user-doc -->
     * @see #TO_LITERAL
     * @model name="To"
     * @generated
     * @ordered
     */
public static final int TO = 1;

	/**
     * The '<em><b>From</b></em>' literal value.
     * <!-- begin-user-doc -->
* <p>
* From as in from the artifact at the source of the association to something else.
* </p>
* <!-- end-user-doc -->
     * @see #FROM_LITERAL
     * @model name="From"
     * @generated
     * @ordered
     */
public static final int FROM = 2;

	/**
     * The '<em><b>Both</b></em>' literal value.
     * <!-- begin-user-doc -->
* <p>
* If the meaning of '<em><b>Both</b></em>' literal object isn't clear,
* there really should be more of a description here...
* </p>
* <!-- end-user-doc -->
     * @see #BOTH_LITERAL
     * @model name="Both"
     * @generated
     * @ordered
     */
public static final int BOTH = 3;

	/**
     * An array of all the '<em><b>Direction Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
* <!-- end-user-doc -->
     * @generated
     */
private static final DirectionType[] VALUES_ARRAY =
	    new DirectionType[] {
            NONE_LITERAL,
            TO_LITERAL,
            FROM_LITERAL,
            BOTH_LITERAL,
        };

	/**
     * A public read-only list of all the '<em><b>Direction Type</b></em>' enumerators.
     * <!-- begin-user-doc -->
* <!-- end-user-doc -->
     * @generated
     */
public static final List<DirectionType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
     * Returns the '<em><b>Direction Type</b></em>' literal with the specified literal value.
     * <!-- begin-user-doc -->
* <!-- end-user-doc -->
     * @generated
     */
public static DirectionType get(String literal) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DirectionType result = VALUES_ARRAY[i];
            if (result.toString().equals(literal)) {
                return result;
            }
        }
        return null;
    }

	/**
     * Returns the '<em><b>Direction Type</b></em>' literal with the specified name.
     * <!-- begin-user-doc -->
* <!-- end-user-doc -->
     * @generated
     */
public static DirectionType getByName(String name) {
        for (int i = 0; i < VALUES_ARRAY.length; ++i) {
            DirectionType result = VALUES_ARRAY[i];
            if (result.getName().equals(name)) {
                return result;
            }
        }
        return null;
    }

	/**
     * Returns the '<em><b>Direction Type</b></em>' literal with the specified integer value.
     * <!-- begin-user-doc -->
* <!-- end-user-doc -->
     * @generated
     */
public static DirectionType get(int value) {
        switch (value) {
            case NONE: return NONE_LITERAL;
            case TO: return TO_LITERAL;
            case FROM: return FROM_LITERAL;
            case BOTH: return BOTH_LITERAL;
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
private DirectionType(int value, String name, String literal) {
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
