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
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.AssociationTarget;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.commands.CreateRelationshipCommandEx;
import org.eclipse.stp.bpmn.diagram.edit.parts.AssociationEditPart;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * @generated
 */
public class DataObjectItemSemanticEditPolicy extends
        BpmnBaseItemSemanticEditPolicy {

	/**
     * @generated not explicitly deleting the associations.
     * When deleting a text annotation, it will delete its children automatically.
     * But not references to its children.
     * Children are deleted first, then connections are trying to be deleted.
     * 
     * The target of the association is not loaded and is corrupt if 
     * we don't do that.
     * 
     */
    protected Command getDestroyElementCommand(DestroyElementRequest req) {
    	CompoundCommand command = new CompoundCommand();
    	for (Object child : ((ShapeNodeEditPart) getHost()).getSourceConnections()) {
    		if (child instanceof AssociationEditPart) {
    		    //before we remove this child
    		    //we must make sure that it is semantically still a child
    		    //if we are currently modifying a bunch of things.
    		    //we might not be in that situation;
    		    //in other words: don't trust the editparts or the view.
    		    AssociationEditPart assocEP = (AssociationEditPart) child;
    		    EObject assoc = assocEP.resolveSemanticElement();
    		    if (assoc == null) {
    		        //NOT SURE WHERE THINGS WENT WRONG
    		        throw new IllegalArgumentException("The association is null ");  //$NON-NLS-1$
    		    }
    		    if (assoc == null || assoc.eContainer() == ((GraphicalEditPart)getHost()).resolveSemanticElement()) {
    		        command.add(assocEP.getCommand(new EditCommandRequestWrapper(new DestroyElementRequest(false))));
    		    }
    		    
    		}
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

        }));
        return command;
    }

    /**
     * @generated NOT
     * the relationship is enabled in the two directions.
     * Particularly important when trying to have an activity create it,
     * in the case that the artifact has just been created.
     * replaced == by getId().equals(
     */
    protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
        if (BpmnElementTypes.Association_3003.getId().equals(req.getElementType().getId())) {
            return req.getTarget() == null ? getCreateStartOutgoingAssociation3003Command(req) : 
            	getCreateCompleteIncomingAssociation3003Command(req);
        }
        return super.getCreateRelationshipCommand(req);
    }

    /**
     * @generated NOT
     * @param req
     * @return an empty command
     * added to enable the creation of an association with an activity.
     */
    protected Command getCreateCompleteIncomingAssociation3003Command(
            CreateRelationshipRequest req) {
        if (!(req.getSource() instanceof Artifact)) {
            return UnexecutableCommand.INSTANCE;
        }
        if (!(req.getTarget() instanceof Artifact) || req.getTarget() == req.getSource()) {
            return new Command(){};
        }
        return new Command() {};
        
        //TODO: change the schema so artifact can support being the target of an association.
/*        final Artifact element = (Artifact) getRelationshipContainer(req
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
                if (((IGraphicalEditPart) getHost()).resolveSemanticElement()
                        .equals(association.getTarget())) {
                    return UnexecutableCommand.INSTANCE;
                }
            }
        }
        return getMSLWrapper(new CreateIncomingAssociation3003Command(req));*/
    }
    
    /**
     * @generated
     */
    protected Command getCreateStartOutgoingAssociation3003Command(
            CreateRelationshipRequest req) {
        return new Command() {
        };
    }
    
    /**
     * @generated NOT
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
         * Initializes the container of the new element by asking the create to
         * create the container, if necessary.
         */
        protected EObject getElementToEdit() {
            CreateRelationshipRequest create = (CreateRelationshipRequest)getRequest();
            if (create.getSource() instanceof Artifact) {
                return create.getSource();
            }
            return super.getElementToEdit();
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
