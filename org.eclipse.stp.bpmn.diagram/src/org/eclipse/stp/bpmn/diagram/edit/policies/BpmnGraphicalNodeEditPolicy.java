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

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewAndElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.stp.bpmn.diagram.edit.commands.BpmnReorientConnectionViewCommand;

/**
 * @generated
 */
public class BpmnGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {
    
    /**
     * @generated
     */
    protected IElementType getElementType(CreateConnectionRequest request) {
        if (request instanceof CreateConnectionViewAndElementRequest) {
            CreateElementRequestAdapter requestAdapter = ((CreateConnectionViewAndElementRequest) request)
                    .getConnectionViewAndElementDescriptor()
                    .getCreateElementRequestAdapter();
            return (IElementType) requestAdapter.getAdapter(IElementType.class);
        }
        return null;
    }

    /**
     * @generated
     */
    protected Command getConnectionWithReorientedViewCompleteCommand(
            CreateConnectionRequest request) {
        ICommandProxy c = (ICommandProxy) super
                .getConnectionCompleteCommand(request);
        CompositeCommand cc = (CompositeCommand) c.getICommand();
        TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
                .getEditingDomain();
        BpmnReorientConnectionViewCommand rcvCommand = new BpmnReorientConnectionViewCommand(
                editingDomain, null);
        rcvCommand.setEdgeAdaptor(getViewAdapter());
        cc.compose(rcvCommand);
        return c;
    }
    
    
    //This seems to do more harm than good now.
//    /**
//     * @generated NOT Bug fix in the super class see bug #172821
//     */
//    protected Command getReversedUnspecifiedConnectionCompleteCommand(
//            CreateUnspecifiedTypeConnectionRequest request) {
//        EditPart realSourceEP = request.getTargetEditPart();
//        EditPart realTargetEP = request.getSourceEditPart();
//        for (Iterator iter = request.getAllRequests().iterator();
//                iter.hasNext();) {
//            CreateConnectionRequest connectionRequest = 
//                (CreateConnectionRequest) iter.next();
//
//            // First, setup the request to initialize the connection start
//            // command.
//            connectionRequest.setType(RequestConstants.REQ_CONNECTION_START);
//            connectionRequest.setSourceEditPart(null);
//            connectionRequest.setTargetEditPart(realSourceEP);
////          [hugues] added some more reset of objects
//            if ((connectionRequest instanceof CreateConnectionViewRequest)||
//            (connectionRequest instanceof CreateConnectionViewAndElementRequest)) {
//                CreateConnectionViewRequest connViewAndElem =
//                    (CreateConnectionViewRequest)connectionRequest;
//                CreateRelationshipRequest createRelationshipRequest =
//                    (CreateRelationshipRequest) 
//                    connViewAndElem.getConnectionViewDescriptor()
//                    .getElementAdapter().getAdapter(
//                            CreateRelationshipRequest.class);
//                if (createRelationshipRequest != null) {
//                    //if we don't do this
//                    //TextAnnotationItemSemanticEditPolicy
//                    //#getCreateRelationshipCommand returns null
//                    createRelationshipRequest.setSource(null);
//                    createRelationshipRequest.setTarget(null);
//                }
//            }
////          end of the addition.
//            realSourceEP.getCommand(connectionRequest);
//
//            // Now, setup the request in preparation to get the connection end
//            // command.
//            connectionRequest.setSourceEditPart(realSourceEP);
//            connectionRequest.setTargetEditPart(realTargetEP);
//            connectionRequest.setType(RequestConstants.REQ_CONNECTION_END);
//        }
//
//        // The requests are now ready to be sent to get the connection end
//        // command from real source to real target.
//        request.setDirectionReversed(false);
//        Command command = realTargetEP.getCommand(request);
//        return command;
//    }

    
}
