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

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.AssociationTarget;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SequenceFlowConditionType;
import org.eclipse.stp.bpmn.commands.CreateRelationshipCommandEx;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * We add a new type of request to understand and deal with.
 * This request will set the condition flow type on the semantic element.
 * @generated not
 */
public class SequenceEdgeItemSemanticEditPolicy extends
        BpmnBaseItemSemanticEditPolicy {

    /**
     * The type of the requests that sets the flow condition type
     */
    public static final String SEQUENCE_EDGE_FLOW_CONDITION_TYPE = "SEQUENCE_EDGE_FLOW_CONDITION_TYPE"; //$NON-NLS-1$
    
    /**
     * The type of the extended data parameter that sets the type of flow condition type.
     */
    public static final String FLOW_CONDITION_TYPE = "flow_condition_type_key"; //$NON-NLS-1$
    /**
     * @generated
     */
    protected Command getDestroyElementCommand(DestroyElementRequest req) {
        return getMSLWrapper(new DestroyElementCommand(req));
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
     * @generated NOT  replaced == by getId().equals(
     */
    protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
        if (BpmnElementTypes.Association_3003.getId().equals(req.getElementType().getId())) {
            return req.getTarget() == null ? null
                    : getCreateCompleteIncomingAssociation3003Command(req);
        }
        return super.getCreateRelationshipCommand(req);
    }
    
    /**
     * @generated NOT this is all added to be able to set the condition flow type
     */
    @Override
    public boolean understandsRequest(Request request) {
        if (SEQUENCE_EDGE_FLOW_CONDITION_TYPE.equals(request.getType())) {
            return true;
        }
        return super.understandsRequest(request);
    }
    
    /**
     * @generated NOT this is all added to be able to set the condition flow type
     */
    @Override
    public Command getCommand(Request request) {
        if (SEQUENCE_EDGE_FLOW_CONDITION_TYPE.equals(request.getType())) {
            int fct = (Integer) request.getExtendedData().get(FLOW_CONDITION_TYPE);
            SequenceEdge seqEdge = (SequenceEdge) ((IGraphicalEditPart) getHost()).
                resolveSemanticElement();
            return getMSLWrapper(new SetFlowConditionType(seqEdge, fct));
        }
        return super.getCommand(request);
    }
    
    /**
     * This command sets the flow condition type.
     *
     * @author <a href="http://www.intalio.com">Intalio Inc.</a>
     * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
     */
    private class SetFlowConditionType extends AbstractTransactionalCommand {

        private int _flowConditionType;
        private SequenceEdge _sequenceEdge;

        public SetFlowConditionType(SequenceEdge sequenceEdge, int fct) {
            super((TransactionalEditingDomain) AdapterFactoryEditingDomain.
                    getEditingDomainFor(sequenceEdge),
                    BpmnDiagramMessages.SequenceEdgeItemSemanticEditPolicy_flow_condition_type_command,
                    getWorkspaceFiles(sequenceEdge));
            _flowConditionType = fct;
            _sequenceEdge = sequenceEdge;
        }

        @Override
        protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
                IAdaptable info) throws ExecutionException {
            _sequenceEdge.setConditionType(SequenceFlowConditionType.get(_flowConditionType));
            return CommandResult.newOKCommandResult();
        }
        
        @Override
        public boolean canExecute() {
            if (_flowConditionType == _sequenceEdge.getConditionType().getValue()) {
                return false;
            }
            //check if there might be another default edge
            boolean isDefault = false;
            boolean isConditional = false;
            boolean parallel = false;
            for (SequenceEdge e : _sequenceEdge.getSource().getOutgoingEdges()) {
                if (e == _sequenceEdge) {
                    continue;
                }
                if (e.isIsDefault()) {// shortcut to check the flow condition type
                    isDefault = true;
                } else if (SequenceFlowConditionType.EXPRESSION_LITERAL.equals(e.getConditionType())) {
                    isConditional = true;
                } else if (SequenceFlowConditionType.NONE_LITERAL.equals(e.getConditionType())) {
                    parallel = true;
                }
            }
            int actype = ((Activity) _sequenceEdge.getSource()).getActivityType().getValue();
            if (ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE == actype 
                    || ActivityType.GATEWAY_DATA_BASED_INCLUSIVE == actype
                    || ActivityType.GATEWAY_COMPLEX == actype) {
                isConditional = true;
                parallel = false;
            }
            //there can only be one default edge, but there must be conditional edges then.
            // and there also musn't be any parallel edges
            if (_flowConditionType == SequenceFlowConditionType.DEFAULT) {
                // we are being flexible: we will validate afterwards
                // if a default edge is created and some edges don't have a condition yet.
                if (isDefault || !isConditional /*|| parallel*/) {
                    return false;
                }
            }
            
            if (_flowConditionType == SequenceFlowConditionType.EXPRESSION) {
                if (ActivityType.VALUES_GATEWAYS.contains(
                        ((Activity) _sequenceEdge.getSource()).getActivityType())) {
                    return false;
                }
            }
            return super.canExecute();
        }
    }
}
