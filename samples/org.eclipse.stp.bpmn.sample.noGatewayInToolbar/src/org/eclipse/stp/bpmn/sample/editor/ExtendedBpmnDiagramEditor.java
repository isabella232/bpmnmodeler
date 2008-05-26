/******************************************************************************
 * Copyright (c) 2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Date         Author             Changes
 * Feb 15, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.sample.editor;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.commands.ElementTypeEx;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditDomain;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditor;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * 
 * This editor extends the bpmn editor by removing all the gateways and
 * the group shape from the popup toolbar.
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class ExtendedBpmnDiagramEditor extends BpmnDiagramEditor {

    /**
     * the editor ID needed for the contributor
     */
    public static final String ID = "org.eclipse.stp.bpmn.sample.noGatewayInToolbar.editor1";
    
    /**
     * Adds the types to remove from the popup toolbar.
     * 
     * We remove all the gateways, as well as the text annotations.
     */
    protected void createDiagramEditDomain() {
        BpmnDiagramEditDomain domain = new BpmnDiagramEditDomain(this);
        domain.setActionManager(createActionManager());
        
        Set<IElementType> types = new HashSet<IElementType>();
        for (Object gatewayType : ActivityType.VALUES_GATEWAYS) {
            types.add(ElementTypeEx.wrap(BpmnElementTypes.Activity_2001, 
                    ((ActivityType) gatewayType).getLiteral()));
        }
        types.add(BpmnElementTypes.Group_1004);
        types.add(BpmnElementTypes.Group_2006);
        domain.setRemovedElementTypes(types);
        
        setEditDomain(domain);
    }
}
