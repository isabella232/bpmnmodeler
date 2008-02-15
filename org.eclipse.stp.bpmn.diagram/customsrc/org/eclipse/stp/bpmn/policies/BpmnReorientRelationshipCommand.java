/******************************************************************************
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 *
 * Date              Author             Changes
 * August 17, 2007   Hugues Malphettes     Created
 */
package org.eclipse.stp.bpmn.policies;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.AssociationTarget;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.tools.EdgeConnectionValidator;
import org.eclipse.stp.bpmn.tools.MessageConnectionValidator;

/**
 * Basic command to reconnect.
 * 
 * @author hmalphettes
 * @authr Intalio Inc.
 */
public class BpmnReorientRelationshipCommand extends Command {
    
    protected final ReorientRelationshipRequest _req;
    
    public BpmnReorientRelationshipCommand(ReorientRelationshipRequest req) {
        super(BpmnDiagramMessages.BpmnReorientRelationshipCommand_command_name);
        _req = req;
    }

    @Override
    public boolean canExecute() {
        return canExecute(_req.getNewRelationshipEnd(), _req.getOldRelationshipEnd());
    }
    
    /**
     * Checks that the connection may be created
     * by using all connection rules existing for BPMN.
     * @param newEnd the new end
     * @param oldEnd the old end
     * @return true if the command can execute, false
     * otherwise.
     */
    private boolean canExecute(EObject newEnd, EObject oldEnd) {
        if (newEnd != null && oldEnd != null && newEnd != oldEnd) {
            if (_req.getRelationship() instanceof Association) {
                if (_req.getDirection() == ReorientRelationshipRequest.REORIENT_SOURCE) {
                    if (newEnd instanceof Artifact) {
                        return !alreadyConnected((Artifact) newEnd, 
                                ((Association)_req.getRelationship()).getTarget());
                    } else {
                        return false;
                    }
                } else {
                    if (newEnd instanceof AssociationTarget) {
                        return !alreadyConnected(
                                ((Association)_req.getRelationship()).getSource(),
                                (AssociationTarget) newEnd);
                    } else {
                        return false;
                    }
                }
            } else if (_req.getRelationship() instanceof SequenceEdge) {
                if (newEnd instanceof Activity) {
                    Activity act = (Activity)newEnd;
                    if (_req.getDirection() == ReorientRelationshipRequest.REORIENT_TARGET) {
                        return EdgeConnectionValidator.INSTANCE.canConnect(
                                ((SequenceEdge)_req.getRelationship()).getSource(), act);
                    } else {
                        return EdgeConnectionValidator.INSTANCE.canConnect(act,
                                ((SequenceEdge)_req.getRelationship()).getTarget());
                    }
                }
            } else if (_req.getRelationship() instanceof MessagingEdge) {
                if (newEnd instanceof Activity) {
                    Activity act = (Activity)newEnd;
                    if (_req.getDirection() == ReorientRelationshipRequest.REORIENT_TARGET) {
                        return MessageConnectionValidator.INSTANCE.canConnect(
                                ((MessagingEdge)_req.getRelationship()).getSource(), act);
                    } else {
                        return MessageConnectionValidator.INSTANCE.canConnect(act,
                                ((MessagingEdge)_req.getRelationship()).getTarget());
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Checks that elements are not already connected.
     * @param src the source of the association
     * @param target the target of the association
     * @return true if they  are already connected, false
     * otherwise.
     */
    private boolean alreadyConnected(Artifact src, AssociationTarget target) {
        for (Association assoc : src.getAssociations()) {
         if (target.equals(assoc.getTarget())) {
             return true;
         }
        }
        return false;
    }
    
    @Override
    public boolean canUndo() {
        return canExecute(_req.getOldRelationshipEnd(), _req.getNewRelationshipEnd());
    }

    @Override
    public void execute() {
        connect(_req.getNewRelationshipEnd(), _req.getOldRelationshipEnd());
    }

    @Override
    public void undo() {
        connect(_req.getOldRelationshipEnd(), _req.getNewRelationshipEnd());
    }
    
    /**
     * Actually sets the new end for the various type of BPMN objects.
     * Can be overridden if extra commands are required.
     * 
     * @param newEnd
     * @param oldEnd
     */
    protected void connect(EObject newEnd, EObject oldEnd) {
        if (_req.getRelationship() instanceof Association) {
            if (_req.getDirection() == ReorientRelationshipRequest.REORIENT_SOURCE) {
                ((Association)_req.getRelationship()).setSource((Artifact)newEnd);
            } else {
                ((Association)_req.getRelationship()).setTarget((AssociationTarget)newEnd);
            }
        } else if (_req.getRelationship() instanceof SequenceEdge) {
            if (_req.getDirection() == ReorientRelationshipRequest.REORIENT_SOURCE) {
                ((SequenceEdge)_req.getRelationship()).setSource((Vertex)newEnd);
            } else {
                ((SequenceEdge)_req.getRelationship()).setTarget((Vertex)newEnd);
            }
        } else if (_req.getRelationship() instanceof MessagingEdge) {
            if (_req.getDirection() == ReorientRelationshipRequest.REORIENT_SOURCE) {
                ((MessagingEdge)_req.getRelationship()).setSource((Activity)newEnd);
            } else {
                ((MessagingEdge)_req.getRelationship()).setTarget((Activity)newEnd);
            }
        }
    }

}
