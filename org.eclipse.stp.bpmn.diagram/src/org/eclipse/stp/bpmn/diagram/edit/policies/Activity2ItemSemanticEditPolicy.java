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
package org.eclipse.stp.bpmn.diagram.edit.policies;

import java.util.Iterator;

import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.AssociationTarget;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.Identifiable;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.commands.CreateRelationshipCommandEx;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * @generated
 */
public class Activity2ItemSemanticEditPolicy extends
        BpmnBaseItemSemanticEditPolicy {

    /**
     * @generated not
     * deleting explicitly the sequence edges
     */
    protected Command getDestroyElementCommand(DestroyElementRequest req) {
        CompoundCommand co = new CompoundCommand();
        if (req.getElementToDestroy() instanceof Activity) {
            for (Object edge : ((Activity) req.getElementToDestroy()).getIncomingEdges()) {
                DestroyElementRequest request = 
                    new DestroyElementRequest((SequenceEdge) edge, false);
                co.add(getMSLWrapper(new DestroyElementCommand(request)));
            }
            for (Object edge : ((Activity) req.getElementToDestroy()).getOutgoingEdges()) {
                DestroyElementRequest request = 
                    new DestroyElementRequest((SequenceEdge) edge, false);
                co.add(getMSLWrapper(new DestroyElementCommand(request)));
            }
        }
        co.add(getMSLWrapper(new DestroyElementCommand(req) {

            protected EObject getElementToDestroy() {
                View view = (View) getHost().getModel();
                EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
                if (annotation != null) {
                    return view;
                }
                return super.getElementToDestroy();
            }

        }));
        return co;
    }

    /**
     * @generated not
     * removed the start for the association. replaced == by getId().equals(
     */
    protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
        if (BpmnElementTypes.SequenceEdge_3001.getId().equals(req.getElementType().getId())) {
            return req.getTarget() == null ? getCreateStartOutgoingSequenceEdge3001Command(req)
                    : getCreateCompleteIncomingSequenceEdge3001Command(req);
        }
        if (BpmnElementTypes.MessagingEdge_3002.getId().equals(req.getElementType().getId())) {
            return req.getTarget() == null ? getCreateStartOutgoingMessagingEdge3002Command(req)
                    : getCreateCompleteIncomingMessagingEdge3002Command(req);
        }
        if (BpmnElementTypes.Association_3003.getId().equals(req.getElementType().getId())) {
            return req.getTarget() == null ? null
                    : getCreateCompleteIncomingAssociation3003Command(req);
        }
        return super.getCreateRelationshipCommand(req);
    }

    /**
     * @generated
     */
    protected Command getCreateStartOutgoingSequenceEdge3001Command(
            CreateRelationshipRequest req) {
        return new Command() {
        };
    }

    /**
     * @generated not
     * activities placed on the border of a subprocess
     * do _not_ accept incoming sequence edges.
     */
    protected Command getCreateCompleteIncomingSequenceEdge3001Command(
            CreateRelationshipRequest req) {
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @generated not an activity placed
     * on the border of a subprocess is not authorized to 
     * start a messaging edge.
     */
    protected Command getCreateStartOutgoingMessagingEdge3002Command(
            CreateRelationshipRequest req) {
        return UnexecutableCommand.INSTANCE;
    }

    
    /**
     * @generated not
     * apparently it was forgotten/not generated, so had to add it by hand.
     */
    protected Command getCreateStartOutgoingAssociation3003Command(
            CreateRelationshipRequest req) {
        return new Command() {
        };
    }
    /**
     * @generated
     */
    protected Command getCreateCompleteIncomingMessagingEdge3002Command(
            CreateRelationshipRequest req) {
        if (!(req.getSource() instanceof Activity)) {
            return UnexecutableCommand.INSTANCE;
        }
        final BpmnDiagram element = (BpmnDiagram) getRelationshipContainer(req
                .getSource(), BpmnPackage.eINSTANCE.getBpmnDiagram(), req
                .getElementType());
        if (element == null) {
            return UnexecutableCommand.INSTANCE;
        }
        if (req.getContainmentFeature() == null) {
            req.setContainmentFeature(BpmnPackage.eINSTANCE
                    .getBpmnDiagram_Messages());
        }
        
        // check that the event may not be connected
        // if being a fault handler, a compensation handler or
        // a timer
        Activity target = (Activity) 
            ((IGraphicalEditPart) getHost()).resolveSemanticElement();
        
        switch (target.getActivityType().getValue()) {
        case ActivityType.EVENT_INTERMEDIATE_TIMER:
        case ActivityType.EVENT_INTERMEDIATE_COMPENSATION:
        case ActivityType.EVENT_INTERMEDIATE_ERROR:
            return UnexecutableCommand.INSTANCE;
        }
        
        Activity srcActivity = (Activity)req.getSource();
        if (srcActivity.getEventHandlerFor() != null) {
            //on the sub-process border it cannot be sending the message.
            return UnexecutableCommand.INSTANCE;
        }
        
        return getMSLWrapper(new CreateIncomingMessagingEdge3002Command(req) {

            /**
             * @generated
             */
            protected EObject getElementToEdit() {
                return element;
            }
        });
    }

    /**
     * @generated
     */
    private static class CreateIncomingMessagingEdge3002Command extends
            CreateRelationshipCommandEx {

        /**
         * @generated
         */
        public CreateIncomingMessagingEdge3002Command(
                CreateRelationshipRequest req) {
            super(req);
        }

        /**
         * @generated
         */
        protected EClass getEClassToEdit() {
            return BpmnPackage.eINSTANCE.getBpmnDiagram();
        };

        /**
         * @generated
         */
        protected void setElementToEdit(EObject element) {
            throw new UnsupportedOperationException();
        }

        /**
         * @generated
         */
        protected EObject doDefaultElementCreation() {
            MessagingEdge newElement = (MessagingEdge) super
                    .doDefaultElementCreation();
            if (newElement != null) {
                newElement.setTarget((Activity) getTarget());
                newElement.setSource((Activity) getSource());
            }
            return newElement;
        }
        
        /**
         * Contains Message Flow Rules
         * @notgenerated
         * @author BIlchyshyn
         */
        @Override
        public boolean canExecute() {
            if (!(getSource() instanceof Activity && getTarget() instanceof Activity)) {
                return false;
                // the message is created at the same time as the activity is created.
            }
            Activity source = (Activity) getSource();
            Activity target = (Activity) getTarget();
                    
            /*
             * Message Flow Rules
             */
            if (source.equals(target)) {
                return false;
            }
            EList tInMessages = target == null ? ECollections.EMPTY_ELIST :
                    target.getIncomingMessages();
            EList tOutMessages = target == null ? ECollections.EMPTY_ELIST :
                    target.getOutgoingMessages();
            EList sInMessages = source == null ? ECollections.EMPTY_ELIST :
                    source.getIncomingMessages();
            EList sOutMessages = source == null ? ECollections.EMPTY_ELIST :
                    source.getOutgoingMessages();

            /*
             * only one incoming and one outgoing message connection between two
             * activities are allowed.
             */
            for (Iterator iter = tInMessages.iterator(); iter.hasNext();) {
                MessagingEdge element = (MessagingEdge) iter.next();
                // better be safe here
                if (element.getSource() != null && 
                        element.getSource().equals(source)) {
                    return false;
                }
            }

            // source pool
            Identifiable sContainer = getSource() instanceof Pool ?
                    (Identifiable) getSource() :
                (Identifiable) source.eContainer();
            while (!(sContainer instanceof Pool)) {
                sContainer = (Identifiable) sContainer.eContainer();
            }

            // target pool
            Identifiable tContainer = getTarget() instanceof Pool ?
                    (Identifiable) getTarget() :
                (Identifiable) target.eContainer();
            while (!(tContainer instanceof Pool)) {
                tContainer = (Identifiable) tContainer.eContainer();
            }

            if (sContainer.equals(tContainer)) {
                return false;
            }
            /*
             * if a shape is already carrying one or more messaging edges, the
             * new messaging edge must not link to an activity that belongs to a
             * third pool.
             */
            for (Iterator iter = tOutMessages.iterator(); iter.hasNext();) {
                MessagingEdge element = (MessagingEdge) iter.next();
                Activity task = (Activity) element.getTarget();
                Identifiable container = (Identifiable) task.eContainer();
                while (!(container instanceof Pool)) {
                    container = (Identifiable) container.eContainer();
                }
                if (!container.equals(sContainer)) {
                    return false;
                }
            }

            for (Iterator iter = tInMessages.iterator(); iter.hasNext();) {
                MessagingEdge element = (MessagingEdge) iter.next();
                Activity task = (Activity) element.getSource();
                Identifiable container = (Identifiable) task.eContainer();
                while (!(container instanceof Pool)) {
                    container = (Identifiable) container.eContainer();
                }
                if (!container.equals(sContainer)) {
                    return false;
                }
            }

            for (Iterator iter = sOutMessages.iterator(); iter.hasNext();) {
                MessagingEdge element = (MessagingEdge) iter.next();
                Activity task = (Activity) element.getTarget();
                Identifiable container = (Identifiable) task.eContainer();
                while (!(container instanceof Pool)) {
                    container = (Identifiable) container.eContainer();
                }
                if (!container.equals(tContainer)) {
                    return false;
                }
            }

            for (Iterator iter = sInMessages.iterator(); iter.hasNext();) {
                MessagingEdge element = (MessagingEdge) iter.next();
                Activity task = (Activity) element.getSource();
                Identifiable container = (Identifiable) task.eContainer();
                while (!(container instanceof Pool)) {
                    container = (Identifiable) container.eContainer();
                }
                if (!container.equals(tContainer)) {
                    return false;
                }
            }
            
            if (target != null) {
                //connection rules for events and gateways
                switch (target.getActivityType().getValue()) {
                case ActivityType.GATEWAY_DATA_BASED_INCLUSIVE:
                case ActivityType.GATEWAY_EVENT_BASED_EXCLUSIVE:
                case ActivityType.GATEWAY_PARALLEL:
                case ActivityType.GATEWAY_COMPLEX:
                case ActivityType.SUB_PROCESS:
                    return false;
                    
                case ActivityType.EVENT_START_EMPTY:
                case ActivityType.EVENT_START_LINK:
                case ActivityType.EVENT_START_MULTIPLE:
                case ActivityType.EVENT_START_RULE:
                case ActivityType.EVENT_START_TIMER:
                case ActivityType.EVENT_START_MESSAGE:
                    //all the starts can receive a message.
                    
                case ActivityType.EVENT_INTERMEDIATE_CANCEL:
                case ActivityType.EVENT_INTERMEDIATE_COMPENSATION:
                case ActivityType.EVENT_INTERMEDIATE_EMPTY:
                case ActivityType.EVENT_INTERMEDIATE_ERROR:
                case ActivityType.EVENT_INTERMEDIATE_LINK:
                case ActivityType.EVENT_INTERMEDIATE_MULTIPLE:
                case ActivityType.EVENT_INTERMEDIATE_RULE:
                case ActivityType.EVENT_INTERMEDIATE_TIMER:
                case ActivityType.EVENT_INTERMEDIATE_MESSAGE:
//                  all the intermediates can receive a message.
                    
                case ActivityType.TASK:
                    break;
                    
                //we are being a little lax with the bpmn spec:
                //if the message event shape receive a request, we
                //allow the message response to come from the same
                //shape.
                case ActivityType.EVENT_END_MESSAGE:
                    if (!source.getOrderedMessages().isEmpty()
                         && ((FeatureMap.Entry) source.
                            getOrderedMessages().get(0))
                               .getEStructuralFeature().getFeatureID() ==
                                BpmnPackage.MESSAGE_VERTEX__OUTGOING_MESSAGES) {
                        break;
                    }
                case ActivityType.EVENT_END_COMPENSATION:
                case ActivityType.EVENT_END_EMPTY:
                case ActivityType.EVENT_END_ERROR:
                case ActivityType.EVENT_END_TERMINATE:
                case ActivityType.EVENT_END_CANCEL:
                case ActivityType.EVENT_END_LINK:
                case ActivityType.EVENT_END_MULTIPLE:
                default:
                    return false;
                }
            }
            if (source != null) {
                //connection rules for events and gateways
                switch (source.getActivityType().getValue()) {
                case ActivityType.GATEWAY_DATA_BASED_INCLUSIVE:
                case ActivityType.GATEWAY_EVENT_BASED_EXCLUSIVE:
                case ActivityType.GATEWAY_PARALLEL:
                case ActivityType.GATEWAY_COMPLEX:
                case ActivityType.SUB_PROCESS:
                    return false;
                case ActivityType.EVENT_END_EMPTY:
                case ActivityType.EVENT_END_COMPENSATION:
                case ActivityType.EVENT_END_ERROR:
                case ActivityType.EVENT_END_TERMINATE:
                case ActivityType.EVENT_END_CANCEL:
                case ActivityType.EVENT_END_LINK:
                case ActivityType.EVENT_END_MULTIPLE:
                case ActivityType.EVENT_END_MESSAGE:
                    //all the end can send a message
                    
                case ActivityType.TASK:
                    break;
                    
                case ActivityType.EVENT_INTERMEDIATE_MESSAGE:
                    //an intermediate message event as an event handler
                    //(on the sub-process border) cannot send a message:
                    //this does not make a difference.
//                    if (source.getEventHandlerFor() != null) {
//                        return false;
//                    }
                    
                    //we are being a little lax with the bpmn spec:
                    //if the message event shape receive a request, we
                    //allow the message response to answer from the same
                    //shape.
                case ActivityType.EVENT_START_MESSAGE:
                    if (!source.getOrderedMessages().isEmpty()) {
                        FeatureMap.Entry fentry = (FeatureMap.Entry) source.
                                            getOrderedMessages().get(0);
                        MessagingEdge firstMsgOfSource =
                        (MessagingEdge) fentry.getValue();
                        if (fentry.getEStructuralFeature().getFeatureID() ==
                            BpmnPackage.MESSAGE_VERTEX__INCOMING_MESSAGES &&
                                firstMsgOfSource.getSource() == target) {
                        // a little bent to the spec
                        // let's let the events be able to reply
                            break;
                        }
                    }
                case ActivityType.EVENT_START_EMPTY:
                case ActivityType.EVENT_START_LINK:
                case ActivityType.EVENT_START_MULTIPLE:
                case ActivityType.EVENT_START_RULE:
                case ActivityType.EVENT_START_TIMER:
                case ActivityType.EVENT_INTERMEDIATE_CANCEL:
                case ActivityType.EVENT_INTERMEDIATE_COMPENSATION:
                case ActivityType.EVENT_INTERMEDIATE_EMPTY:
                case ActivityType.EVENT_INTERMEDIATE_ERROR:
                case ActivityType.EVENT_INTERMEDIATE_LINK:
                case ActivityType.EVENT_INTERMEDIATE_MULTIPLE:
                case ActivityType.EVENT_INTERMEDIATE_RULE:
                case ActivityType.EVENT_INTERMEDIATE_TIMER:
                    //the start and intermediate cannot send a message.
                default:
                    return false;
                }

            }
            return super.canExecute();
        }
    }

    /**
     * @generated not
     */
    protected Command getCreateCompleteIncomingAssociation3003Command(
            CreateRelationshipRequest req) {
        if (!(req.getSource() instanceof Artifact)) {
            return UnexecutableCommand.INSTANCE;
        }
        final Artifact element = (Artifact) getRelationshipContainer(req
                .getSource(), BpmnPackage.eINSTANCE.getArtifact(), req
                .getElementType());
        if (element == null) {
            return UnexecutableCommand.INSTANCE;
        }
        if (req.getContainmentFeature() == null) {
            req.setContainmentFeature(BpmnPackage.eINSTANCE
                    .getArtifact_Associations());
        }
        for (Object tempA : element.getAssociations()) {
        	if (tempA instanceof Association) {
        		Association association = (Association) tempA;
        		if (((IGraphicalEditPart) getHost()).resolveSemanticElement().
        				equals(association.getTarget())) {
        			return UnexecutableCommand.INSTANCE;
        		}
        	}
        }
        return getMSLWrapper(new CreateIncomingAssociation3003Command(req) {

            /**
             * @generated
             */
            protected EObject getElementToEdit() {
                return element;
            }
        });
    }

    /**
     * @generated NOT (CreateRelationshipCommandEx)
     */
    private static class CreateIncomingAssociation3003Command extends
            CreateRelationshipCommandEx {

        /**
         * @generated
         */
        public CreateIncomingAssociation3003Command(
                CreateRelationshipRequest req) {
            super(req);
        }

        /**
         * @generated
         */
        protected EClass getEClassToEdit() {
            return BpmnPackage.eINSTANCE.getArtifact();
        };

        /**
         * @generated
         */
        protected void setElementToEdit(EObject element) {
            throw new UnsupportedOperationException();
        }

        /**
         * @generated
         */
        protected EObject doDefaultElementCreation() {
            Association newElement = (Association) super
                    .doDefaultElementCreation();
            if (newElement != null) {
                newElement.setTarget((AssociationTarget) getTarget());
                newElement.setSource((Artifact) getSource());
            }
            return newElement;
        }
    }
}