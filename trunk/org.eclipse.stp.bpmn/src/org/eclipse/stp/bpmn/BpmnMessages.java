/******************************************************************************
* Copyright (c) 2007, Intalio Inc.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
* 
* Contributors:
*     Intalio Inc. - initial API and implementation
*******************************************************************************/

package org.eclipse.stp.bpmn;

import org.eclipse.osgi.util.NLS;

/**
 * Messages for the bpmn plugin.
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class BpmnMessages extends NLS {
    private static final String BUNDLE_NAME = "org.eclipse.stp.bpmn.bpmnmessages"; //$NON-NLS-1$

    public static String ActivityType_complex;

    public static String ActivityType_end_cancel;

    public static String ActivityType_end_compensation;

    public static String ActivityType_end_empty;

    public static String ActivityType_end_error;

    public static String ActivityType_end_link;

    public static String ActivityType_end_message;

    public static String ActivityType_end_multiple;

    public static String ActivityType_end_terminate;

    public static String ActivityType_intermediate_cancel;

    public static String ActivityType_intermediate_compensation;

    public static String ActivityType_intermediate_empty;

    public static String ActivityType_intermediate_error;

    public static String ActivityType_intermediate_link;

    public static String ActivityType_intermediate_message;

    public static String ActivityType_intermediate_multiple;

    public static String ActivityType_intermediate_rule;

    public static String ActivityType_intermediate_timer;

    public static String ActivityType_or;

    public static String ActivityType_parallel;

    public static String ActivityType_start_empty;

    public static String ActivityType_start_link;

    public static String ActivityType_start_message;

    public static String ActivityType_start_multiple;

    public static String ActivityType_start_rule;

    public static String ActivityType_start_timer;

    public static String ActivityType_sub_process;

    public static String ActivityType_task;

    public static String ActivityType_xor;

    public static String ActivityType_xor_events;

    public static String DirectionType_Both;

    public static String DirectionType_From;

    public static String DirectionType_None;

    public static String DirectionType_To;

    public static String ActivityImpl_activityType;

    public static String ActivityImpl_doc;

    public static String ActivityImpl_looping;

    public static String ActivityImpl_name;

    public static String ActivityImpl_ncname;

    public static String ActivityImpl_orderedMessages;

    public static String ActivityImpl_recursiveContainment;

    public static String ActivityImpl_unset;

    public static String ArtifactImpl_doc;

    public static String ArtifactImpl_name;

    public static String ArtifactImpl_ncname;

    public static String ArtifactImpl_recursiveContainment;

    public static String AssociationImpl_direction;

    public static String AssociationImpl_recursiveContainment;

    public static String AssociationImpl_unset;

    public static String BpmnDiagramImpl_author;

    public static String BpmnDiagramImpl_doc;

    public static String BpmnDiagramImpl_name;

    public static String BpmnDiagramImpl_ncname;

    public static String BpmnDiagramImpl_title;

    public static String BpmnFactoryImpl_datatype_invalid_classifier;

    public static String BpmnFactoryImpl_theClass;

    public static String BpmnFactoryImpl_value_invalidEnumerator;

    public static String GraphImpl_doc;

    public static String GraphImpl_name;

    public static String GraphImpl_ncname;

    public static String LaneImpl_doc;

    public static String LaneImpl_name;

    public static String LaneImpl_ncname;

    public static String LaneImpl_recursiveContainment;

    public static String MessagingEdgeImpl_documentation;

    public static String MessagingEdgeImpl_errorRecursiveContainment;

    public static String MessagingEdgeImpl_name;

    public static String MessagingEdgeImpl_ncname;

    public static String NamedBpmnObjectImpl_doc;

    public static String NamedBpmnObjectImpl_name;

    public static String NamedBpmnObjectImpl_ncname;

    public static String PoolImpl_recursiveContainment;

    public static String SequenceEdgeImpl_conditionType;

    public static String SequenceEdgeImpl_default;

    public static String SequenceEdgeImpl_doc;

    public static String SequenceEdgeImpl_name;

    public static String SequenceEdgeImpl_ncname;

    public static String SequenceEdgeImpl_recursiveContainment;

    public static String SequenceEdgeImpl_unset;

    public static String SequenceFlowConditionType_default;

    public static String SequenceFlowConditionType_expr;

    public static String SequenceFlowConditionType_none;

    public static String SubProcessImpl_isTransaction;

    public static String SubProcessImpl_unset;

    public static String VertexImpl_recursiveContainment;
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, BpmnMessages.class);
    }

    private BpmnMessages() {
    }
}
