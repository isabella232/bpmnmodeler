/******************************************************************************
 * Copyright (c) 2006, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************/

/**
 * Date             Author              Changes
 * Nov 28, 2006     MPeleshchsyhyn         Created
 **/
package org.eclipse.stp.bpmn.policies;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ContainerNodeEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeConnectionRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.stp.bpmn.commands.CreateViewAndOptionallyElementCommandEx;
import org.eclipse.stp.bpmn.commands.PromptForConnectionAndEndCommandEx;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * 
 * Overrides the edit policy by providing a modified command
 * so that the messaging edges and sequence edges menus are
 * only shown when it is possible.
 * @author atoulme
 * @author MPeleshchyshyn
 * 
 */
public class ContainerNodeEditPolicyEx extends ContainerNodeEditPolicy {

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.ContainerNodeEditPolicy#getConnectionAndEndCommands(org.eclipse.gef.requests.CreateConnectionRequest)
     */
    @Override
    protected Command getConnectionAndEndCommands(
            CreateConnectionRequest request) {
        
        CompoundCommand cc = new CompoundCommand(
                DiagramUIMessages.Command_CreateRelationship_Label);

        // Flags the case where the connection is to be created from a known
        // target
        // to unspecified source.
        boolean isDirectionReversed = request instanceof CreateUnspecifiedTypeConnectionRequest
                && ((CreateUnspecifiedTypeConnectionRequest) request)
                        .isDirectionReversed();

        // Adds the command for the popup menu to get the relationship type and
        // end element.
        PromptForConnectionAndEndCommandEx menuCmd = getPromptForConnectionAndEndCommand(request);
        cc.add(new ICommandProxy(menuCmd));

        // Adds the command to create a view (and optionally an element) for
        // the other end.
        CreateViewAndOptionallyElementCommandEx createOtherEndCmd = 
            (CreateViewAndOptionallyElementCommandEx) getCreateOtherEndCommand(
                menuCmd.getEndAdapter(), request.getLocation(), request);
        cc.add(new ICommandProxy(createOtherEndCmd));

        // Adds the command to create the connection view and element.
        ICommand connectionCmd = isDirectionReversed ? getCreateConnectionCommand(
                request, menuCmd.getConnectionAdapter(), createOtherEndCmd
                        .getResult(), request.getSourceEditPart())
                : getCreateConnectionCommand(request, menuCmd
                        .getConnectionAdapter(), request.getSourceEditPart(),
                        createOtherEndCmd.getResult());

        cc.add(new ICommandProxy(connectionCmd));

        return cc;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.ContainerNodeEditPolicy#getPromptForConnectionAndEndCommand(org.eclipse.gef.requests.CreateConnectionRequest)
     */
    @Override
    protected PromptForConnectionAndEndCommandEx getPromptForConnectionAndEndCommand(
            CreateConnectionRequest request) {
        return new PromptForConnectionAndEndCommandEx(request,
                (IGraphicalEditPart) getHost());
    }

    /**
     * Called by {@link #getConnectionAndEndCommands}.
     * 
     * @param endAdapter
     *            the end adapter
     * @param location
     *            the location
     * @return command
     * added the request itself so that it can guess in which direction
     * the task should be offset when created.
     */
    protected CreateViewAndOptionallyElementCommandEx getCreateOtherEndCommand(
            IAdaptable endAdapter, Point location, CreateConnectionRequest request) {
    	boolean xoffset = false;
    	boolean yoffset = false;
    	if (request instanceof CreateUnspecifiedTypeConnectionRequest) {
    		List eltTypes = ((CreateUnspecifiedTypeConnectionRequest) 
    				request).getElementTypes();
    		if (!(eltTypes.isEmpty())) {
    			IElementType type = (IElementType) eltTypes.get(0);
    			if (BpmnElementTypes.SequenceEdge_3001.equals(type)) {
    				yoffset = true;
    			} else if (BpmnElementTypes.MessagingEdge_3002.equals(type)) {
    				xoffset = true;
    			}
    		}
    			
    	}
        return new CreateViewAndOptionallyElementCommandEx(endAdapter,
                (IGraphicalEditPart) getHost(), location,
                ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint(), 
                xoffset, yoffset);
    }
}
