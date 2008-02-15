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

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.AssociationTarget;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.commands.CreateRelationshipCommandEx;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * @generated
 */
public class SubProcessItemSemanticEditPolicy extends
        BpmnBaseItemSemanticEditPolicy {

	/**
     * @notgenerated
     * Fix for 169865 messaging edges are not deleted.
     */
    protected Command getDestroyElementCommand(DestroyElementRequest req) {
    	CompoundCommand command = new CompoundCommand();
        View view = (View) getHost().getModel();
        SubProcess sp = (SubProcess) view.getElement();
        
        IDiagramGraphicalViewer viewer = (IDiagramGraphicalViewer) getHost().getViewer();
        PoolItemSemanticEditPolicy.destroyMessagingEdges(sp, viewer, command);
        destroyEventHandlerSequenceEdges(sp, viewer, command);
        command.add(getMSLWrapper(new DestroyElementCommand(req) {

            protected EObject getElementToDestroy() {
                View view = (View) getHost().getModel();
                EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
                if (annotation != null) {
                    return view;
                }
                return super.getElementToDestroy();
            }

        }));
        
        return command;
    }

    
    /**
     * Goes in the subprocess event handlers, looking
     * for the event handlers that might have sequence edges
     * in order to delete them.
     * @param graph
     * @param viewer
     * @param command
     */
    private void destroyEventHandlerSequenceEdges(SubProcess graph, IDiagramGraphicalViewer viewer, 
            CompoundCommand command) {
        for (Object event : graph.getEventHandlers()) {
            Activity activity = (Activity) event;
            for (Object edge : activity.getIncomingEdges()) {
                DestroyElementRequest request = 
                    new DestroyElementRequest((SequenceEdge) edge, false);
                command.add(getMSLWrapper(new DestroyElementCommand(request)));
            }
            for (Object edge : activity.getOutgoingEdges()) {
                DestroyElementRequest request = 
                    new DestroyElementRequest((SequenceEdge) edge, false);
                command.add(getMSLWrapper(new DestroyElementCommand(request)));
            }
        }
    }
        
    /**
     * @generated NOT replaced == by getId().equals(
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
     * @generated NOT
     * Modified to avoid the subprocess to create an incoming 
     * sequence edge to activities not placed in the same pool.
     */
    protected Command getCreateCompleteIncomingSequenceEdge3001Command(
            CreateRelationshipRequest req) {
        if (!(req.getSource() instanceof Vertex)) {
            return UnexecutableCommand.INSTANCE;
        }
        if (!(req.getTarget() instanceof Vertex)) {
            return UnexecutableCommand.INSTANCE;
        }
        return getMSLWrapper(new CreateIncomingSequenceEdge3001Command(req) {

            /**
             * @generated
             */
            protected EObject getElementToEdit() {
                return ((Vertex) ((IGraphicalEditPart) getHost()).
                		resolveSemanticElement()).getGraph();
            }
        });
        /*here is what got generated (to be checked...)
                if (!(req.getSource() instanceof Vertex)) {
            return UnexecutableCommand.INSTANCE;
        }
        final Graph element = (Graph) getRelationshipContainer(req.getSource(),
                BpmnPackage.eINSTANCE.getGraph(), req.getElementType());
        if (element == null) {
            return UnexecutableCommand.INSTANCE;
        }
        if (req.getContainmentFeature() == null) {
            req.setContainmentFeature(BpmnPackage.eINSTANCE
                    .getGraph_SequenceEdges());
        }
        return getMSLWrapper(new CreateIncomingSequenceEdge3001Command(req) {

           
            protected EObject getElementToEdit() {
                return element;
            }
        });

        */
    }

    /**
     * @notgenerated
     * Set the visibility of the class to protected to override it.
     */
    protected static class CreateIncomingSequenceEdge3001Command extends
            CreateRelationshipCommandEx {
        /**
         * @generated
         */
        public CreateIncomingSequenceEdge3001Command(
                CreateRelationshipRequest req) {
            super(req);
        }

        /**
         * @generated
         */
        protected EClass getEClassToEdit() {
            return BpmnPackage.eINSTANCE.getGraph();
        };
        
        /**
         * Contains Sequence Flow Rules
         * @notgenerated
         * @author BIlchyshyn
         */
        @Override
        public boolean canExecute() {
        	if (((getSource() == null))||((getTarget() == null))) {
        		return true;
        		// in this case the sequence flow is created as the activity is created.
        	}
            Activity source = (Activity) getSource();
            Activity target = (Activity) getTarget();

            /*
             * Sequence Flow Rules
             */
            if (source.equals(target)) {
                return false;
            }

            //only one sequence edge between 2 activities is allowed
            for (Object inEdge : target.getIncomingEdges()) {
                SequenceEdge element = (SequenceEdge) inEdge;
                if (element.getSource().equals(source)) {
                    return false;
                }
            }
            
            if (source.getGraph() == null && 
            		source.getEventHandlerFor() != null) {
            	// the source is located on the border of a subprocess.
            	if (source.getEventHandlerFor().equals(getTarget())) {
            		// the target is the subprocess the source is located on.
            		return false;
            	}
            	if (!source.getEventHandlerFor().getGraph().equals(target.getGraph())) {
            		return false;
            	}
            	return true;
            }
            if (!source.getGraph().equals(target.getGraph())) {
            	return false;
            }
            
            if (ActivityType.VALUES_EVENTS_START.contains(target.getActivityType())) {
            	return false;
            }
            return super.canExecute();
        }
        
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
            SequenceEdge newElement = (SequenceEdge) super
                    .doDefaultElementCreation();
            if (newElement != null) {
                newElement.setTarget((Vertex) getTarget());
                newElement.setSource((Vertex) getSource());
            }
            return newElement;
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

    /**
     * @generated
     */
    protected Command getCreateCompleteIncomingMessagingEdge3002Command(
            CreateRelationshipRequest req) {
    	return UnexecutableCommand.INSTANCE;
    	
//        if (!(req.getSource() instanceof Activity)) {
//            return UnexecutableCommand.INSTANCE;
//        }
//        final BpmnDiagram element = (BpmnDiagram) getRelationshipContainer(req
//                .getSource(), BpmnPackage.eINSTANCE.getBpmnDiagram(), req
//                .getElementType());
//        if (element == null) {
//            return UnexecutableCommand.INSTANCE;
//        }
//        if (req.getContainmentFeature() == null) {
//            req.setContainmentFeature(BpmnPackage.eINSTANCE
//                    .getBpmnDiagram_Messages());
//        }
//        return getMSLWrapper(new CreateIncomingMessagingEdge3002Command(req) {
//
//            /**
//             * @generated
//             */
//            protected EObject getElementToEdit() {
//                return element;
//            }
//        });
    }

    /**
     * @notgenerated
     * Set the visibility of the class to protected to override it.
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
                newElement.setTarget((Activity) getTarget());
                newElement.setSource((Activity) getSource());
            }
            return newElement;
        }
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
     * @generated
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
