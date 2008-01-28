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

package org.eclipse.stp.bpmn;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Activity Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.stp.bpmn.BpmnPackage#getActivityType()
 * @model extendedMetaData="name='ActivityType'"
 * @generated
 * WARNING! we have i18n'ed most of the activity types names.
 */
public enum ActivityType implements Enumerator
{
	/**
	 * The '<em><b>Task</b></em>' literal object.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #TASK
	 * @generated not
	 * @ordered
	 */
	TASK_LITERAL(0, BpmnMessages.ActivityType_task, "Task"), //$NON-NLS-1$
	/**
     * The '<em><b>Sub Process</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #SUB_PROCESS
     * @generated not 
     * @ordered
     */
	SUB_PROCESS_LITERAL(1, BpmnMessages.ActivityType_sub_process, "SubProcess"), //$NON-NLS-1$
	/**
     * The '<em><b>Event Start Empty</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EVENT_START_EMPTY
     * @generated not
     * @ordered
     */
	EVENT_START_EMPTY_LITERAL(2, BpmnMessages.ActivityType_start_empty, "EventStartEmpty"), //$NON-NLS-1$
	/**
     * The '<em><b>Event Start Message</b></em>' literal object.
     * <!-- begin-user-doc -->last
     * <!-- end-user-doc -->
     * @see #EVENT_START_MESSAGE
     * @generated not
     * @ordered
     */
	EVENT_START_MESSAGE_LITERAL(3, BpmnMessages.ActivityType_start_message, "EventStartMessage"), //$NON-NLS-1$
	/**
     * The '<em><b>Event Start Rule</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EVENT_START_RULE
     * @generated not
     * @ordered
     */
	EVENT_START_RULE_LITERAL(4, BpmnMessages.ActivityType_start_rule, "EventStartRule"), //$NON-NLS-1$
	/**
     * The '<em><b>Event Start Timer</b></em>' literal object.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #EVENT_START_TIMER
     * @generated not
     * @ordered
     */
	EVENT_START_TIMER_LITERAL(5, BpmnMessages.ActivityType_start_timer, "EventStartTimer"), //$NON-NLS-1$
	/**
     * The '<em><b>Event Start Link</b></em>' literal object.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #EVENT_START_LINK
     * @generated not
     * @ordered
     */
	EVENT_START_LINK_LITERAL(6, BpmnMessages.ActivityType_start_link, "EventStartLink"), //$NON-NLS-1$
	/**
     * The '<em><b>Event Start Multiple</b></em>' literal object.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #EVENT_START_MULTIPLE
     * @generated not
     * @ordered
     */
	EVENT_START_MULTIPLE_LITERAL(7, BpmnMessages.ActivityType_start_multiple, "EventStartMultiple"), //$NON-NLS-1$
	/**
     * The '<em><b>Event Intermediate Empty</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EVENT_INTERMEDIATE_EMPTY
     * @generated not
     * @ordered
     */
	EVENT_INTERMEDIATE_EMPTY_LITERAL(8, BpmnMessages.ActivityType_intermediate_empty, "EventIntermediateEmpty"), //$NON-NLS-1$
	/**
     * The '<em><b>Event Intermediate Message</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EVENT_INTERMEDIATE_MESSAGE
     * @generated not
     * @ordered
     */
	EVENT_INTERMEDIATE_MESSAGE_LITERAL(9, BpmnMessages.ActivityType_intermediate_message, "EventIntermediateMessage"), //$NON-NLS-1$
	/**
     * The '<em><b>Event Intermediate Timer</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EVENT_INTERMEDIATE_TIMER
     * @generated not
     * @ordered
     */
	EVENT_INTERMEDIATE_TIMER_LITERAL(10, BpmnMessages.ActivityType_intermediate_timer, "EventIntermediateTimer"), //$NON-NLS-1$
	/**
     * The '<em><b>Event Intermediate Error</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EVENT_INTERMEDIATE_ERROR
     * @generated not
     * @ordered
     */
	EVENT_INTERMEDIATE_ERROR_LITERAL(11, BpmnMessages.ActivityType_intermediate_error, "EventIntermediateError"), //$NON-NLS-1$
	/**
     * The '<em><b>Event Intermediate Compensation</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EVENT_INTERMEDIATE_COMPENSATION
     * @generated not
     * @ordered
     */
	EVENT_INTERMEDIATE_COMPENSATION_LITERAL(12, BpmnMessages.ActivityType_intermediate_compensation, "EventIntermediateCompensation"), //$NON-NLS-1$
	/**
     * The '<em><b>Event Intermediate Rule</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EVENT_INTERMEDIATE_RULE
     * @generated not
     * @ordered
     */
	EVENT_INTERMEDIATE_RULE_LITERAL(13, BpmnMessages.ActivityType_intermediate_rule, "EventIntermediateRule"), //$NON-NLS-1$
	/**
     * The '<em><b>Event Intermediate Link</b></em>' literal object.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #EVENT_INTERMEDIATE_LINK
     * @generated not
     * @ordered
     */
	EVENT_INTERMEDIATE_LINK_LITERAL(14, BpmnMessages.ActivityType_intermediate_link, "EventIntermediateLink"), //$NON-NLS-1$
	/**
     * The '<em><b>Event Intermediate Multiple</b></em>' literal object.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #EVENT_INTERMEDIATE_MULTIPLE
     * @generated not
     * @ordered
     */
	EVENT_INTERMEDIATE_MULTIPLE_LITERAL(15, BpmnMessages.ActivityType_intermediate_multiple, "EventIntermediateMultiple"), //$NON-NLS-1$
	/**
     * The '<em><b>Event Intermediate Cancel</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EVENT_INTERMEDIATE_CANCEL
     * @generated not
     * @ordered
     */
	EVENT_INTERMEDIATE_CANCEL_LITERAL(16, BpmnMessages.ActivityType_intermediate_cancel, "EventIntermediateCancel"), //$NON-NLS-1$
	/**
     * The '<em><b>Event End Empty</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EVENT_END_EMPTY
     * @generated not
     * @ordered
     */
	EVENT_END_EMPTY_LITERAL(17, BpmnMessages.ActivityType_end_empty, "EventEndEmpty"), //$NON-NLS-1$
	/**
     * The '<em><b>Event End Message</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EVENT_END_MESSAGE
     * @generated not
     * @ordered
     */
	EVENT_END_MESSAGE_LITERAL(18, BpmnMessages.ActivityType_end_message, "EventEndMessage"), //$NON-NLS-1$
	/**
     * The '<em><b>Event End Error</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EVENT_END_ERROR
     * @generated not
     * @ordered
     */
	EVENT_END_ERROR_LITERAL(19, BpmnMessages.ActivityType_end_error, "EventEndError"), //$NON-NLS-1$
	/**
     * The '<em><b>Event End Compensation</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EVENT_END_COMPENSATION
     * @generated not
     * @ordered
     */
	EVENT_END_COMPENSATION_LITERAL(20, BpmnMessages.ActivityType_end_compensation, "EventEndCompensation"), //$NON-NLS-1$
	/**
     * The '<em><b>Event End Terminate</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EVENT_END_TERMINATE
     * @generated not
     * @ordered
     */
	EVENT_END_TERMINATE_LITERAL(21, BpmnMessages.ActivityType_end_terminate, "EventEndTerminate"), //$NON-NLS-1$
	/**
     * The '<em><b>Event End Link</b></em>' literal object.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #EVENT_END_LINK
     * @generated not
     * @ordered
     */
	EVENT_END_LINK_LITERAL(22, BpmnMessages.ActivityType_end_link, "EventEndLink"), //$NON-NLS-1$
	/**
     * The '<em><b>Event End Multiple</b></em>' literal object.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #EVENT_END_MULTIPLE
     * @generated not
     * @ordered
     */
	EVENT_END_MULTIPLE_LITERAL(23, BpmnMessages.ActivityType_end_multiple, "EventEndMultiple"), //$NON-NLS-1$
	/**
     * The '<em><b>Event End Cancel</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #EVENT_END_CANCEL
     * @generated not
     * @ordered
     */
	EVENT_END_CANCEL_LITERAL(24, BpmnMessages.ActivityType_end_cancel, "EventEndCancel"), //$NON-NLS-1$
	/**
     * The '<em><b>Gateway Data Based Exclusive</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #GATEWAY_DATA_BASED_EXCLUSIVE
     * @generated not
     * @ordered
     */
	GATEWAY_DATA_BASED_EXCLUSIVE_LITERAL(25, BpmnMessages.ActivityType_xor, "GatewayDataBasedExclusive"), //$NON-NLS-1$
	/**
     * The '<em><b>Gateway Event Based Exclusive</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #GATEWAY_EVENT_BASED_EXCLUSIVE
     * @generated not
     * @ordered
     */
	GATEWAY_EVENT_BASED_EXCLUSIVE_LITERAL(26, BpmnMessages.ActivityType_xor_events, "GatewayEventBasedExclusive"), //$NON-NLS-1$
	/**
     * The '<em><b>Gateway Data Based Inclusive</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #GATEWAY_DATA_BASED_INCLUSIVE
     * @generated not
     * @ordered
     */
	GATEWAY_DATA_BASED_INCLUSIVE_LITERAL(27, BpmnMessages.ActivityType_or, "GatewayDataBasedInclusive"), //$NON-NLS-1$
	/**
     * The '<em><b>Gateway Parallel</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #GATEWAY_PARALLEL
     * @generated not
     * @ordered
     */
	GATEWAY_PARALLEL_LITERAL(28, BpmnMessages.ActivityType_parallel, "GatewayParallel"), //$NON-NLS-1$
	/**
     * The '<em><b>Gateway Complex</b></em>' literal object.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #GATEWAY_COMPLEX
     * @generated not
     * @ordered
     */
	GATEWAY_COMPLEX_LITERAL(29, BpmnMessages.ActivityType_complex, "GatewayComplex"); //$NON-NLS-1$
	/**
* The '<em><b>Task</b></em>' literal value.
* <!-- begin-user-doc -->
* <p>
* If the meaning of '<em><b>Task</b></em>' literal object isn't clear,
* there really should be more of a description here...
* </p>
* <!-- end-user-doc -->
* @see #TASK_LITERAL
* @model name="Task"
* @generated
* @ordered
*/
	public static final int TASK = 0;

	/**
* The '<em><b>Sub Process</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Sub Process</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #SUB_PROCESS_LITERAL
* @model name="SubProcess"
* @generated
* @ordered
*/
	public static final int SUB_PROCESS = 1;

	/**
* The '<em><b>Event Start Empty</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Event Start Empty</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #EVENT_START_EMPTY_LITERAL
* @model name="EventStartEmpty"
* @generated
* @ordered
*/
	public static final int EVENT_START_EMPTY = 2;

	/**
* The '<em><b>Event Start Message</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Event Start Message</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #EVENT_START_MESSAGE_LITERAL
* @model name="EventStartMessage"
* @generated
* @ordered
*/
	public static final int EVENT_START_MESSAGE = 3;

	/**
* The '<em><b>Event Start Rule</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Event Start Rule</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #EVENT_START_RULE_LITERAL
* @model name="EventStartRule"
* @generated
* @ordered
*/
	public static final int EVENT_START_RULE = 4;

	/**
* The '<em><b>Event Start Timer</b></em>' literal value.
* <!-- begin-user-doc -->
* <p>
* If the meaning of '<em><b>Event Start Timer</b></em>' literal object isn't clear,
* there really should be more of a description here...
* </p>
* <!-- end-user-doc -->
* @see #EVENT_START_TIMER_LITERAL
* @model name="EventStartTimer"
* @generated
* @ordered
*/
public static final int EVENT_START_TIMER = 5;

	/**
* The '<em><b>Event Start Link</b></em>' literal value.
* <!-- begin-user-doc -->
* <p>
* If the meaning of '<em><b>Event Start Link</b></em>' literal object isn't clear,
* there really should be more of a description here...
* </p>
* <!-- end-user-doc -->
* @see #EVENT_START_LINK_LITERAL
* @model name="EventStartLink"
* @generated
* @ordered
*/
public static final int EVENT_START_LINK = 6;

	/**
* The '<em><b>Event Start Multiple</b></em>' literal value.
* <!-- begin-user-doc -->
* <p>
* If the meaning of '<em><b>Event Start Multiple</b></em>' literal object isn't clear,
* there really should be more of a description here...
* </p>
* <!-- end-user-doc -->
* @see #EVENT_START_MULTIPLE_LITERAL
* @model name="EventStartMultiple"
* @generated
* @ordered
*/
public static final int EVENT_START_MULTIPLE = 7;

	/**
* The '<em><b>Event Intermediate Empty</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Event Intermediate Empty</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #EVENT_INTERMEDIATE_EMPTY_LITERAL
* @model name="EventIntermediateEmpty"
* @generated
* @ordered
*/
	public static final int EVENT_INTERMEDIATE_EMPTY = 8;

	/**
* The '<em><b>Event Intermediate Message</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Event Intermediate Message</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #EVENT_INTERMEDIATE_MESSAGE_LITERAL
* @model name="EventIntermediateMessage"
* @generated
* @ordered
*/
	public static final int EVENT_INTERMEDIATE_MESSAGE = 9;

	/**
* The '<em><b>Event Intermediate Timer</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Event Intermediate Timer</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #EVENT_INTERMEDIATE_TIMER_LITERAL
* @model name="EventIntermediateTimer"
* @generated
* @ordered
*/
	public static final int EVENT_INTERMEDIATE_TIMER = 10;

	/**
* The '<em><b>Event Intermediate Error</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Event Intermediate Error</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #EVENT_INTERMEDIATE_ERROR_LITERAL
* @model name="EventIntermediateError"
* @generated
* @ordered
*/
	public static final int EVENT_INTERMEDIATE_ERROR = 11;

	/**
* The '<em><b>Event Intermediate Compensation</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Event Intermediate Compensation</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #EVENT_INTERMEDIATE_COMPENSATION_LITERAL
* @model name="EventIntermediateCompensation"
* @generated
* @ordered
*/
	public static final int EVENT_INTERMEDIATE_COMPENSATION = 12;

	/**
* The '<em><b>Event Intermediate Rule</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Event Intermediate Rule</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #EVENT_INTERMEDIATE_RULE_LITERAL
* @model name="EventIntermediateRule"
* @generated
* @ordered
*/
	public static final int EVENT_INTERMEDIATE_RULE = 13;

	/**
* The '<em><b>Event Intermediate Link</b></em>' literal value.
* <!-- begin-user-doc -->
* <p>
* If the meaning of '<em><b>Event Intermediate Link</b></em>' literal object isn't clear,
* there really should be more of a description here...
* </p>
* <!-- end-user-doc -->
* @see #EVENT_INTERMEDIATE_LINK_LITERAL
* @model name="EventIntermediateLink"
* @generated
* @ordered
*/
public static final int EVENT_INTERMEDIATE_LINK = 14;

	/**
* The '<em><b>Event Intermediate Multiple</b></em>' literal value.
* <!-- begin-user-doc -->
* <p>
* If the meaning of '<em><b>Event Intermediate Multiple</b></em>' literal object isn't clear,
* there really should be more of a description here...
* </p>
* <!-- end-user-doc -->
* @see #EVENT_INTERMEDIATE_MULTIPLE_LITERAL
* @model name="EventIntermediateMultiple"
* @generated
* @ordered
*/
public static final int EVENT_INTERMEDIATE_MULTIPLE = 15;

	/**
* The '<em><b>Event Intermediate Cancel</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Event Intermediate Cancel</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #EVENT_INTERMEDIATE_CANCEL_LITERAL
* @model name="EventIntermediateCancel"
* @generated
* @ordered
*/
	public static final int EVENT_INTERMEDIATE_CANCEL = 16;

	/**
* The '<em><b>Event End Empty</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Event End Empty</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #EVENT_END_EMPTY_LITERAL
* @model name="EventEndEmpty"
* @generated
* @ordered
*/
	public static final int EVENT_END_EMPTY = 17;

	/**
* The '<em><b>Event End Message</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Event End Message</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #EVENT_END_MESSAGE_LITERAL
* @model name="EventEndMessage"
* @generated
* @ordered
*/
	public static final int EVENT_END_MESSAGE = 18;

	/**
* The '<em><b>Event End Error</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Event End Error</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #EVENT_END_ERROR_LITERAL
* @model name="EventEndError"
* @generated
* @ordered
*/
	public static final int EVENT_END_ERROR = 19;

	/**
* The '<em><b>Event End Compensation</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Event End Compensation</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #EVENT_END_COMPENSATION_LITERAL
* @model name="EventEndCompensation"
* @generated
* @ordered
*/
	public static final int EVENT_END_COMPENSATION = 20;

	/**
* The '<em><b>Event End Terminate</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Event End Terminate</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #EVENT_END_TERMINATE_LITERAL
* @model name="EventEndTerminate"
* @generated
* @ordered
*/
	public static final int EVENT_END_TERMINATE = 21;

	/**
* The '<em><b>Event End Link</b></em>' literal value.
* <!-- begin-user-doc -->
* <p>
* If the meaning of '<em><b>Event End Link</b></em>' literal object isn't clear,
* there really should be more of a description here...
* </p>
* <!-- end-user-doc -->
* @see #EVENT_END_LINK_LITERAL
* @model name="EventEndLink"
* @generated
* @ordered
*/
public static final int EVENT_END_LINK = 22;

	/**
* The '<em><b>Event End Multiple</b></em>' literal value.
* <!-- begin-user-doc -->
* <p>
* If the meaning of '<em><b>Event End Multiple</b></em>' literal object isn't clear,
* there really should be more of a description here...
* </p>
* <!-- end-user-doc -->
* @see #EVENT_END_MULTIPLE_LITERAL
* @model name="EventEndMultiple"
* @generated
* @ordered
*/
public static final int EVENT_END_MULTIPLE = 23;

	/**
* The '<em><b>Event End Cancel</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Event End Cancel</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #EVENT_END_CANCEL_LITERAL
* @model name="EventEndCancel"
* @generated
* @ordered
*/
	public static final int EVENT_END_CANCEL = 24;

	/**
* The '<em><b>Gateway Data Based Exclusive</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Gateway Data Based Exclusive</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #GATEWAY_DATA_BASED_EXCLUSIVE_LITERAL
* @model name="GatewayDataBasedExclusive"
* @generated
* @ordered
*/
	public static final int GATEWAY_DATA_BASED_EXCLUSIVE = 25;

	/**
* The '<em><b>Gateway Event Based Exclusive</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Gateway Event Based Exclusive</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #GATEWAY_EVENT_BASED_EXCLUSIVE_LITERAL
* @model name="GatewayEventBasedExclusive"
* @generated
* @ordered
*/
	public static final int GATEWAY_EVENT_BASED_EXCLUSIVE = 26;

	/**
* The '<em><b>Gateway Data Based Inclusive</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Gateway Data Based Inclusive</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #GATEWAY_DATA_BASED_INCLUSIVE_LITERAL
* @model name="GatewayDataBasedInclusive"
* @generated
* @ordered
*/
	public static final int GATEWAY_DATA_BASED_INCLUSIVE = 27;

	/**
* The '<em><b>Gateway Parallel</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Gateway Parallel</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #GATEWAY_PARALLEL_LITERAL
* @model name="GatewayParallel"
* @generated
* @ordered
*/
	public static final int GATEWAY_PARALLEL = 28;

	/**
* The '<em><b>Gateway Complex</b></em>' literal value.
* <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Gateway Complex</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
* @see #GATEWAY_COMPLEX_LITERAL
* @model name="GatewayComplex"
* @generated
* @ordered
*/
	public static final int GATEWAY_COMPLEX = 29;

	/**
* An array of all the '<em><b>Activity Type</b></em>' enumerators.
* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
* @generated
*/
	private static final ActivityType[] VALUES_ARRAY =
	    new ActivityType[] {
TASK_LITERAL,
SUB_PROCESS_LITERAL,
EVENT_START_EMPTY_LITERAL,
EVENT_START_MESSAGE_LITERAL,
EVENT_START_RULE_LITERAL,
EVENT_START_TIMER_LITERAL,
EVENT_START_LINK_LITERAL,
EVENT_START_MULTIPLE_LITERAL,
EVENT_INTERMEDIATE_EMPTY_LITERAL,
EVENT_INTERMEDIATE_MESSAGE_LITERAL,
EVENT_INTERMEDIATE_TIMER_LITERAL,
EVENT_INTERMEDIATE_ERROR_LITERAL,
EVENT_INTERMEDIATE_COMPENSATION_LITERAL,
EVENT_INTERMEDIATE_RULE_LITERAL,
EVENT_INTERMEDIATE_LINK_LITERAL,
EVENT_INTERMEDIATE_MULTIPLE_LITERAL,
EVENT_INTERMEDIATE_CANCEL_LITERAL,
EVENT_END_EMPTY_LITERAL,
EVENT_END_MESSAGE_LITERAL,
EVENT_END_ERROR_LITERAL,
EVENT_END_COMPENSATION_LITERAL,
EVENT_END_TERMINATE_LITERAL,
EVENT_END_LINK_LITERAL,
EVENT_END_MULTIPLE_LITERAL,
EVENT_END_CANCEL_LITERAL,
GATEWAY_DATA_BASED_EXCLUSIVE_LITERAL,
GATEWAY_EVENT_BASED_EXCLUSIVE_LITERAL,
GATEWAY_DATA_BASED_INCLUSIVE_LITERAL,
GATEWAY_PARALLEL_LITERAL,
GATEWAY_COMPLEX_LITERAL,
};

	/**
	 * An array of all the '<em><b>Activity Type</b></em>'
	 * enumerators that are events.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @notgenerated
	 */
	private static final ActivityType[] VALUES_EVENTS_ARRAY =
		new ActivityType[] {
			EVENT_START_EMPTY_LITERAL,
			EVENT_START_MESSAGE_LITERAL,
            EVENT_START_RULE_LITERAL,
            EVENT_START_LINK_LITERAL,
            EVENT_START_MULTIPLE_LITERAL,
            EVENT_START_TIMER_LITERAL,
			EVENT_INTERMEDIATE_EMPTY_LITERAL,
			EVENT_INTERMEDIATE_MESSAGE_LITERAL,
			EVENT_INTERMEDIATE_TIMER_LITERAL,
			EVENT_INTERMEDIATE_ERROR_LITERAL,
			EVENT_INTERMEDIATE_COMPENSATION_LITERAL,
			EVENT_INTERMEDIATE_RULE_LITERAL,
            EVENT_INTERMEDIATE_CANCEL_LITERAL,
            EVENT_INTERMEDIATE_LINK_LITERAL,
            EVENT_INTERMEDIATE_MULTIPLE_LITERAL,
			EVENT_END_EMPTY_LITERAL,
			EVENT_END_MESSAGE_LITERAL,
			EVENT_END_ERROR_LITERAL,
			EVENT_END_COMPENSATION_LITERAL,
			EVENT_END_TERMINATE_LITERAL,
            EVENT_END_CANCEL_LITERAL,
            EVENT_END_LINK_LITERAL,
            EVENT_END_MULTIPLE_LITERAL
		};

	/**
     * An array of all the '<em><b>Activity Type</b></em>' enumerators
     * that are gateways.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @notgenerated
	 */
	private static final ActivityType[] VALUES_GATEWAYS_ARRAY =
		new ActivityType[] {
		GATEWAY_DATA_BASED_EXCLUSIVE_LITERAL,
		GATEWAY_EVENT_BASED_EXCLUSIVE_LITERAL,
		GATEWAY_DATA_BASED_INCLUSIVE_LITERAL,
		GATEWAY_PARALLEL_LITERAL,
        GATEWAY_COMPLEX_LITERAL
		};

	/**
     * An array of all the '<em><b>Activity Type</b></em>' enumerators
     * that are intermediate events.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	private static final ActivityType[] VALUES_EVENTS_INTERMEDIATE_ARRAY =
		new ActivityType[] {
			EVENT_INTERMEDIATE_EMPTY_LITERAL,
			EVENT_INTERMEDIATE_MESSAGE_LITERAL,
			EVENT_INTERMEDIATE_TIMER_LITERAL,
			EVENT_INTERMEDIATE_ERROR_LITERAL,
			EVENT_INTERMEDIATE_COMPENSATION_LITERAL,
			EVENT_INTERMEDIATE_RULE_LITERAL,
            EVENT_INTERMEDIATE_CANCEL_LITERAL,
            EVENT_INTERMEDIATE_LINK_LITERAL,
            EVENT_INTERMEDIATE_MULTIPLE_LITERAL
		};

	/**
     * An array of all the '<em><b>Activity Type</b></em>' enumerators
     * that are start events.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	private static final ActivityType[] VALUES_EVENTS_START_ARRAY = 
		new ActivityType[] {
			EVENT_START_EMPTY_LITERAL,
			EVENT_START_LINK_LITERAL,
			EVENT_START_MESSAGE_LITERAL,
			EVENT_START_MULTIPLE_LITERAL,
			EVENT_START_RULE_LITERAL,
			EVENT_START_TIMER_LITERAL
	};

	/**
* A public read-only list of all the '<em><b>Activity Type</b></em>' enumerators.
* <!-- begin-user-doc -->
* <!-- end-user-doc -->
* @generated
*/
public static final List<ActivityType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * A public read-only list of all the '<em><b>Activity Type</b></em>' enumerators
	 * that are events.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public static final List VALUES_EVENTS = Collections.unmodifiableList(Arrays.asList(VALUES_EVENTS_ARRAY));

	/**
	 * A public read-only list of all the '<em><b>Activity Type</b></em>' enumerators
	 * that are intermediate events.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public static final List VALUES_EVENTS_INTERMEDIATE =
		Collections.unmodifiableList(Arrays.asList(VALUES_EVENTS_INTERMEDIATE_ARRAY));

	/**
	 * A public read-only list of all the '<em><b>Activity Type</b></em>' enumerators
	 * that are start events.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public static final List VALUES_EVENTS_START =
		Collections.unmodifiableList(Arrays.asList(VALUES_EVENTS_START_ARRAY));

	/**
	 * A public read-only list of all the '<em><b>Activity Type</b></em>' enumerators
	 * that are events.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @notgenerated
	 */
	public static final List VALUES_GATEWAYS =
		Collections.unmodifiableList(Arrays.asList(VALUES_GATEWAYS_ARRAY));

	/**
* Returns the '<em><b>Activity Type</b></em>' literal with the specified literal value.
* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
* @generated not added a migration fix: try if the literal is the name.
*/
	public static ActivityType get(String literal) {
	    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
	        ActivityType result = VALUES_ARRAY[i];
	        if (result.toString().equals(literal)) {
	            return result;
	        }
	    }
	    return getByName(literal);
	}

	/**
* Returns the '<em><b>Activity Type</b></em>' literal with the specified name.
* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
* @generated
*/
	public static ActivityType getByName(String name) {
for (int i = 0; i < VALUES_ARRAY.length; ++i) {
ActivityType result = VALUES_ARRAY[i];
if (result.getName().equals(name)) {
	return result;
}
}
return null;
}

	/**
* Returns the '<em><b>Activity Type</b></em>' literal with the specified integer value.
* <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
* @generated
*/
	public static ActivityType get(int value) {
switch (value) {
case TASK: return TASK_LITERAL;
case SUB_PROCESS: return SUB_PROCESS_LITERAL;
case EVENT_START_EMPTY: return EVENT_START_EMPTY_LITERAL;
case EVENT_START_MESSAGE: return EVENT_START_MESSAGE_LITERAL;
case EVENT_START_RULE: return EVENT_START_RULE_LITERAL;
case EVENT_START_TIMER: return EVENT_START_TIMER_LITERAL;
case EVENT_START_LINK: return EVENT_START_LINK_LITERAL;
case EVENT_START_MULTIPLE: return EVENT_START_MULTIPLE_LITERAL;
case EVENT_INTERMEDIATE_EMPTY: return EVENT_INTERMEDIATE_EMPTY_LITERAL;
case EVENT_INTERMEDIATE_MESSAGE: return EVENT_INTERMEDIATE_MESSAGE_LITERAL;
case EVENT_INTERMEDIATE_TIMER: return EVENT_INTERMEDIATE_TIMER_LITERAL;
case EVENT_INTERMEDIATE_ERROR: return EVENT_INTERMEDIATE_ERROR_LITERAL;
case EVENT_INTERMEDIATE_COMPENSATION: return EVENT_INTERMEDIATE_COMPENSATION_LITERAL;
case EVENT_INTERMEDIATE_RULE: return EVENT_INTERMEDIATE_RULE_LITERAL;
case EVENT_INTERMEDIATE_LINK: return EVENT_INTERMEDIATE_LINK_LITERAL;
case EVENT_INTERMEDIATE_MULTIPLE: return EVENT_INTERMEDIATE_MULTIPLE_LITERAL;
case EVENT_INTERMEDIATE_CANCEL: return EVENT_INTERMEDIATE_CANCEL_LITERAL;
case EVENT_END_EMPTY: return EVENT_END_EMPTY_LITERAL;
case EVENT_END_MESSAGE: return EVENT_END_MESSAGE_LITERAL;
case EVENT_END_ERROR: return EVENT_END_ERROR_LITERAL;
case EVENT_END_COMPENSATION: return EVENT_END_COMPENSATION_LITERAL;
case EVENT_END_TERMINATE: return EVENT_END_TERMINATE_LITERAL;
case EVENT_END_LINK: return EVENT_END_LINK_LITERAL;
case EVENT_END_MULTIPLE: return EVENT_END_MULTIPLE_LITERAL;
case EVENT_END_CANCEL: return EVENT_END_CANCEL_LITERAL;
case GATEWAY_DATA_BASED_EXCLUSIVE: return GATEWAY_DATA_BASED_EXCLUSIVE_LITERAL;
case GATEWAY_EVENT_BASED_EXCLUSIVE: return GATEWAY_EVENT_BASED_EXCLUSIVE_LITERAL;
case GATEWAY_DATA_BASED_INCLUSIVE: return GATEWAY_DATA_BASED_INCLUSIVE_LITERAL;
case GATEWAY_PARALLEL: return GATEWAY_PARALLEL_LITERAL;
case GATEWAY_COMPLEX: return GATEWAY_COMPLEX_LITERAL;
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
	private ActivityType(int value, String name, String literal) {
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
