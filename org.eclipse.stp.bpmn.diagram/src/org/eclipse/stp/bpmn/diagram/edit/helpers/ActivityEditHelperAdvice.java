/*
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 */
package org.eclipse.stp.bpmn.diagram.edit.helpers;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.commands.IElementTypeEx;

/**
 * @generated
 */
public class ActivityEditHelperAdvice extends AbstractEditHelperAdvice {
    /**
     * @generated not
     */
    public static final IEditHelperAdvice THE_INSTANCE = new ActivityEditHelperAdvice();

    /**
     * @generated not
     */
    public static final IEditHelperAdvice[] INSTANCE = { THE_INSTANCE };

//    /**
//     * @generated not
//     */
//    @Override
//    public ICommand getAfterEditCommand(IEditCommandRequest request) {
//        return super.getAfterEditCommand(request);
//    }
//
//    /**
//     * @generated not
//     */
//    @Override
//    public ICommand getBeforeEditCommand(IEditCommandRequest request) {
//        return super.getBeforeEditCommand(request);
//    }

    /**
     * Checks that the subprocess event holders have only 
     * one compensation intermediate event.
     * 
     * @generated not
     */
    @Override
    public boolean approveRequest(IEditCommandRequest request) {
    	
        if (request instanceof GetEditContextRequest) {
            GetEditContextRequest gec = (GetEditContextRequest) request;
            if (gec.getEditContext() instanceof SubProcess) {
                if (gec.getEditCommandRequest() instanceof CreateElementRequest) {
                    CreateElementRequest creq = (CreateElementRequest) gec
                            .getEditCommandRequest();
                    if (creq.getContainmentFeature() != null
                            && BpmnPackage.eINSTANCE.getSubProcess_EventHandlers().
                            equals(creq.getContainmentFeature())) {
                        // make sure the type of the request is for an activity
                        // of type intermediate event
                        if (creq.getElementType() instanceof IElementTypeEx) {
                            ActivityType at = ActivityType
                                    .get(((IElementTypeEx) creq
                                            .getElementType())
                                            .getSecondarySemanticHint());
                            if (at != null) {
                                switch (at.getValue()) {
                                case ActivityType.EVENT_INTERMEDIATE_COMPENSATION:
                                case ActivityType.EVENT_INTERMEDIATE_ERROR:
                                case ActivityType.EVENT_INTERMEDIATE_TIMER:
                                    super.configureRequest(request);
                                    return true;
                                }
                            }
                        }

                        return false;
                    }
                }
            }
        }

        
        //a single intermediate compensation event per sub-process can be created
        if ((request instanceof CreateElementRequest)
        		&& (((CreateElementRequest) request).getElementType() instanceof IElementTypeEx)) {
        	if (((CreateElementRequest) request).getContainer() instanceof SubProcess) {
        		if (((CreateElementRequest) request).getContainmentFeature() != null
        				&& BpmnPackage.eINSTANCE.getSubProcess_EventHandlers().
        				equals(((CreateElementRequest) request).getContainmentFeature())) {
        			ActivityType at = ActivityType
        			.get(((IElementTypeEx) ((CreateElementRequest) request)
        					.getElementType()).getSecondarySemanticHint());
        			if (at != null && //at is null when it is looping task or sub-process
        					at.getValue() == ActivityType.EVENT_INTERMEDIATE_COMPENSATION) {
        				List events = ((SubProcess) ((CreateElementRequest) request)
        						.getContainer()).getEventHandlers();

        				for (Object event : events) {
        					if (event instanceof Activity && //sometimes it is an edge.
        							((Activity) event).getActivityType().getValue() == ActivityType.EVENT_INTERMEDIATE_COMPENSATION) {
        						return false;
        					}
        				}
        			}
        		}

        	}
        }

        //a single intermediate compensation event per sub-process can be dropped
        if (request instanceof MoveRequest) {
        	if (((MoveRequest) request).getTargetContainer() instanceof SubProcess) {

        		Collection srcEvents = ((MoveRequest) request)
        		.getElementsToMove().keySet();

        		for (Object srcEvent : srcEvents) {
        			if (BpmnPackage.eINSTANCE.getSubProcess_EventHandlers().
        					equals(((MoveRequest) request).
        							getTargetFeature((EObject) srcEvent))) {
        				if (srcEvent instanceof Activity
        						&& ((Activity) srcEvent).getActivityType()
        						.getValue() == ActivityType.EVENT_INTERMEDIATE_COMPENSATION) {
        					List targetEvents = ((SubProcess) ((MoveRequest) request)
        							.getTargetContainer()).getEventHandlers();

        					for (Object event : targetEvents) {
        						if (event instanceof Activity &&
        								((Activity) event).getActivityType()
        								.getValue() == ActivityType.EVENT_INTERMEDIATE_COMPENSATION) {

        							return false;
        						}
        					}
        				}
        			}
        		}

        	}
        }

        return super.approveRequest(request);
    }

    // commented out as such things seem to duplicate the constraints
    // and they should be in the semantic edit policy anyway
//    /**
//     * @generated not
//     */
//    @Override
//    public void configureRequest(IEditCommandRequest request) {
//        if (request instanceof GetEditContextRequest) {
//            GetEditContextRequest gec = (GetEditContextRequest) request;
//            if (gec.getEditContext() instanceof SubProcess) {
//                if (gec.getEditCommandRequest() instanceof CreateElementRequest) {
//                    CreateElementRequest creq = (CreateElementRequest) gec
//                            .getEditCommandRequest();
//                    if (creq.getContainmentFeature() != null
//                            && BpmnPackage.eINSTANCE.getSubProcess_EventHandlers().
//                            equals(creq.getContainmentFeature())) {
//                        // make sure the type of the request is for an activity
//                        // of type intermediate event for now just an
//                        // experiment.
//                        if (creq.getElementType() instanceof IElementTypeEx) {
//                            ActivityType at = ActivityType
//                                    .getByName(((IElementTypeEx) creq
//                                            .getElementType())
//                                            .getSecondarySemanticHint());
//                            if (at != null) {
//                                switch (at.getValue()) {
//                                case ActivityType.EVENT_INTERMEDIATE_COMPENSATION:
//                                case ActivityType.EVENT_INTERMEDIATE_ERROR:
//                                case ActivityType.EVENT_INTERMEDIATE_TIMER:
//                                    super.configureRequest(request);
//                                    return;
//                                }
//                            }
//                        }
//                        creq.setParameter(
//                                IEditCommandRequest.REPLACE_DEFAULT_COMMAND,
//                                true);
//                        creq.setParameter(
//                                BpmnBaseEditHelper.EDIT_POLICY_COMMAND,
//                                UnexecutableCommand.INSTANCE);
//                    }
//                }
//            }
//        }
//        super.configureRequest(request);
//    }
}