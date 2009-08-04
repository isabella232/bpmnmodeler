/*
 * Copyright (c) 2007-2009, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *     Denis Dallaire, Techsolcom: Bug 273345: Support sub-process message flows
 */
package org.eclipse.stp.bpmn.diagram.edit.policies;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
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
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
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
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.Identifiable;
import org.eclipse.stp.bpmn.MessageVertex;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.commands.CreateRelationshipCommandEx;
import org.eclipse.stp.bpmn.diagram.edit.parts.MessagingEdgeEditPart;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * @generated
 */
public class PoolItemSemanticEditPolicy extends BpmnBaseItemSemanticEditPolicy {


	/**
     * @generated NOT
     * Fix for 169865 messaging edges are not deleted.
     */
    protected Command getDestroyElementCommand(DestroyElementRequest req) {
        CompoundCommand command = new CompoundCommand();
        View view = (View) getHost().getModel();
        Pool pool = (Pool) view.getElement();
        
        IDiagramGraphicalViewer viewer = (IDiagramGraphicalViewer) getHost().getViewer();
        if (pool != null) {
            destroyMessagingEdges(pool, viewer, command);
        }
        command.add(getMSLWrapper(new DestroyElementCommand(req) {

            protected EObject getElementToDestroy() {
                View view = (View) getHost().getModel();
                EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
                if (annotation != null) {
                    return view;
                }
                return super.getElementToDestroy();
            }

            @Override
            protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info)
                    throws ExecutionException {
                //just debugging.
                IStatus s = super.doUndo(monitor, info);
                
                return s;
            }
            
            

        }));
        
        return command;
    }

    /**
     * @generated NOT  replaced == by getId().equals(
     */
    protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
        if (BpmnElementTypes.Association_3003.getId().equals(req.getElementType().getId())) {
            return req.getTarget() == null ? null
                    : getCreateCompleteIncomingAssociation3003Command(req);
        }
        if (BpmnElementTypes.MessagingEdge_3002.getId().equals(req.getElementType().getId())) {
            return req.getTarget() == null ? getCreateStartOutgoingMessagingEdge3002Command(req)
                    : getCreateCompleteIncomingMessagingEdge3002Command(req);
        }
        return super.getCreateRelationshipCommand(req);
    }

    /**
     * @generated
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
         * @generated NOT: added the SecondarySemanticHintProcessor
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
     
    /**
     * @notgenerated
     * Recursive method used to track the activities connected to messaging edges
     * and remove them even though the messaging edge is not contained by
     * the activity itself.
     * Also removes the messaging edges in an order such that when the delete command
     * is undone, the messages are added back in the same order than their actual
     * order on the activities that they connect:
     * if an activity has 2 messages, the first message to be removed
     * is the last message in the orderedMessage list of the activity.
     * At least a best effort is made for this.
     * @param graph the crrent graph being inspected
     * @param viewer the viewer used to retrieve the editpart for the semantic element,
     * so that it can generate destroy command for it.
     * @param command the compoundCommand being populated.
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
	public static void destroyMessagingEdges(Graph graph, IDiagramGraphicalViewer viewer, 
    		CompoundCommand command) {
    	
        //we want the last messages to be removed first.
        TreeMap<Integer, Set<MessagingEdge>> sorted =
            new TreeMap<Integer, Set<MessagingEdge>>(Collections.reverseOrder());
        sortMessagesByPosition(sorted, graph);
        Iterator<Set<MessagingEdge>> itt = sorted.values().iterator();
        Set<MessagingEdge> alreadyRemoved = new HashSet<MessagingEdge>();
        while (itt.hasNext()) {
            for (final MessagingEdge me : itt.next()) {
                alreadyRemoved.add(me);
              //  final MessagingEdge me = (MessagingEdge) fentry.getValue();
                DestroyElementRequest req = new DestroyElementRequest(me,false);
                List editParts = viewer.findEditPartsForElement(
                        EMFCoreUtil.getProxyID(me),
                        MessagingEdgeEditPart.class);
                if (!editParts.isEmpty()) {
                    IGraphicalEditPart mePart =
                        (IGraphicalEditPart) editParts.get(0);

                    if (mePart != null) {
                        command.add(mePart.getCommand(
                            new EditCommandRequestWrapper(req)));
                    }
                }
                
            }
        }
    }
    /**
     * Collect the messages connected by activities coantined in the graph.
     * Index those messages according to their position in the connected activities.
     * 
     * @param sorted
     * @param graph
     */
    private static void sortMessagesByPosition(TreeMap<Integer,Set<MessagingEdge>> sorted, Graph graph) {
        for (Vertex v  : graph.getVertices()) {
            if (v instanceof Graph) {
                sortMessagesByPosition(sorted, (Graph) v);
            }
            if (v instanceof Activity) {
                Activity a = (Activity)v;
                for (int i = a.getOrderedMessages().size()-1; i >= 0; i--) {
                    Set<MessagingEdge> mes = sorted.get(i);
                    if (mes == null) {
                        mes = new HashSet<MessagingEdge>();
                        sorted.put(i, mes);
                    }
                    mes.add((MessagingEdge)a.getOrderedMessages().getValue(i));
                }
            }
        }
    }
    
    
    /**
     * @generated
     */
    protected Command getCreateCompleteIncomingMessagingEdge3002Command(
            CreateRelationshipRequest req) {
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
     * Almost generated: the private is now protected
     * @generated
     */
    protected static class CreateIncomingMessagingEdge3002Command extends
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
                newElement.setTarget((MessageVertex) getTarget());
                newElement.setSource((MessageVertex) getSource());
            }
            return newElement;
        }
        
        /**
         * Contains Message Flow Rules
         * @generated not
         */
        @Override
        public boolean canExecute() {
            if (getSource() instanceof Pool || 
                    getTarget() instanceof Pool) {
                if (getSource() != getTarget()) {
                    return true;
                }
            }
            if ((!(getSource() instanceof Activity))|| 
                    !(getTarget() instanceof Activity)) {
                return false;
                // the message is created at the same time as the activity is created.
            }
            Activity source = getSource() instanceof Activity ?
                    (Activity) getSource() : null;
            Activity target = getTarget() instanceof Activity ?
                    (Activity) getTarget() : null;
                    
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
                    return false;
                    
                case ActivityType.EVENT_START_EMPTY:
                case ActivityType.EVENT_START_LINK:
                case ActivityType.EVENT_START_MULTIPLE:
                case ActivityType.EVENT_START_RULE:
                case ActivityType.EVENT_START_TIMER:
                case ActivityType.EVENT_START_MESSAGE:
                case ActivityType.EVENT_START_SIGNAL:
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
                case ActivityType.EVENT_INTERMEDIATE_SIGNAL:
//                  all the intermediates can receive a message.

                case ActivityType.SUB_PROCESS:
                case ActivityType.TASK:
                    break;
                    
                //we are being a little lax with the bpmn spec:
                //if the message event shape receive a request, we
                //allow the message response to come from the same
                //shape.
                case ActivityType.EVENT_END_MESSAGE:
                case ActivityType.EVENT_END_MULTIPLE:
                    if (!target.getOrderedMessages().isEmpty()
                         && (target.getOutgoingMessages().contains(
                                 target.getOrderedMessages().get(0).getValue()))) {
                        break;
                    }
                case ActivityType.EVENT_END_COMPENSATION:
                case ActivityType.EVENT_END_EMPTY:
                case ActivityType.EVENT_END_ERROR:
                case ActivityType.EVENT_END_TERMINATE:
                case ActivityType.EVENT_END_CANCEL:
                case ActivityType.EVENT_END_LINK:
                case ActivityType.EVENT_END_SIGNAL:
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
                    return false;
                    
                case ActivityType.EVENT_END_EMPTY:
                case ActivityType.EVENT_END_COMPENSATION:
                case ActivityType.EVENT_END_ERROR:
                case ActivityType.EVENT_END_TERMINATE:
                case ActivityType.EVENT_END_CANCEL:
                case ActivityType.EVENT_END_LINK:
                case ActivityType.EVENT_END_MULTIPLE:
                case ActivityType.EVENT_END_MESSAGE:
                case ActivityType.EVENT_END_SIGNAL:
                    //all the end can send a message
                	
                case ActivityType.SUB_PROCESS:                    
                case ActivityType.TASK:
                    break;
                    
                //we are being a little lax with the bpmn spec:
                //if the message event shape receive a request, we
                //allow the message response to answer from the same
                //shape.
                case ActivityType.EVENT_INTERMEDIATE_MESSAGE:
                case ActivityType.EVENT_START_MESSAGE:
                case ActivityType.EVENT_INTERMEDIATE_MULTIPLE:
                case ActivityType.EVENT_START_MULTIPLE:
                    if (!source.getOrderedMessages().isEmpty()) {
                        boolean incomingFirst = source.getIncomingMessages().contains(
                                source.getOrderedMessages().get(0).getValue());
                        FeatureMap.Entry fentry = (FeatureMap.Entry) source.
                                            getOrderedMessages().get(0);
                        MessagingEdge firstMsgOfSource = (MessagingEdge) fentry.getValue();
                        if (incomingFirst &&
                                firstMsgOfSource.getSource() == target) {
                        // a little bent to the spec
                        // let's let the start events be able to reply
                            break;
                        }
                    }
                case ActivityType.EVENT_START_EMPTY:
                case ActivityType.EVENT_START_LINK:
                case ActivityType.EVENT_START_RULE:
                case ActivityType.EVENT_START_TIMER:
                case ActivityType.EVENT_START_SIGNAL:
                case ActivityType.EVENT_INTERMEDIATE_CANCEL:
                case ActivityType.EVENT_INTERMEDIATE_COMPENSATION:
                case ActivityType.EVENT_INTERMEDIATE_EMPTY:
                case ActivityType.EVENT_INTERMEDIATE_ERROR:
                case ActivityType.EVENT_INTERMEDIATE_LINK:
                case ActivityType.EVENT_INTERMEDIATE_RULE:
                case ActivityType.EVENT_INTERMEDIATE_TIMER:
                case ActivityType.EVENT_INTERMEDIATE_SIGNAL:
                    //the start and intermediate cannot send a message.
                default:
                    return false;
                }

            }
            return super.canExecute();
        }

        
    }
    /**
     * @generated
     */
    protected Command getCreateStartOutgoingMessagingEdge3002Command(
            CreateRelationshipRequest req) {
        return new Command() {
        };
    }
}
