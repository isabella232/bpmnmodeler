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
package org.eclipse.stp.bpmn.validation;

import org.eclipse.osgi.util.NLS;

/**
 * Messages for the bpmn.validation plugin.
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class BpmnValidationMessages extends NLS {
    private static final String BUNDLE_NAME = "org.eclipse.stp.bpmn.validation.bpmnvalidationmessages"; //$NON-NLS-1$

    public static String BatchValidationBuilder_diagram_has_no_model;
    
    public static String BatchValidationBuilder_diagram_is_empty;

    public static String BatchValidationBuilder_taskValidateActionFailed;

    public static String BatchValidationBuilder_taskValidateDiagram;
    
    public static String BpmnValidationProvider_markerCreationFailed;

    public static String BpmnValidationProvider_validate;

    public static String BpmnValidationProvider_validateActionFailed;

    public static String BpmnValidationProvider_validateFailed;

    public static String BpmnValidationProvider_ValidationActionFailed;
    
    public static String GatewayEdgesConstraint_named;

    public static String MessageEventConstraint_named;

    public static String MessageEventConstraint_source;

    public static String MessageEventConstraint_target;

    public static String MessagingEdgeConstraint_EndEventMustNotReceiveAMessage;

    public static String MessagingEdgeConstraint_EndEventMustNotReceiveMessage;

    public static String MessagingEdgeConstraint_IntermediateEventMustNotSendMessages;

    public static String MessagingEdgeConstraint_intermediateEventShouldNotHaveIncomingMessages;

    public static String MessagingEdgeConstraint_MessageBetweenSamePoolElements;

    public static String MessagingEdgeConstraint_StartEventMustNotSendMessage;

    public static String SequenceEdgeConstraint_endEventStartOfEdge;

    public static String SequenceEdgeConstraint_StartEventEndOfEdge;

    public static String UniqueDefaultEdgeConstraint_defaultGatewayName;
    
    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, BpmnValidationMessages.class);
    }

    private BpmnValidationMessages() {
    }
}
