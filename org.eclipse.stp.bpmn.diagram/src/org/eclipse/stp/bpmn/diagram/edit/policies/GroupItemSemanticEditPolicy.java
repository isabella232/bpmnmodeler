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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.diagram.edit.parts.AssociationEditPart;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * @generated
 */
public class GroupItemSemanticEditPolicy extends BpmnBaseItemSemanticEditPolicy {

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
    			command.add(((AssociationEditPart) child).getCommand(new EditCommandRequestWrapper(new DestroyElementRequest(false))));
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
     *  replaced == by getId().equals(
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
        return new Command() {
        };
    }

    /**
     * @generated
     */
    protected Command getCreateStartOutgoingAssociation3003Command(
            CreateRelationshipRequest req) {
        return new Command() {
        };
    }
}
