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
 *
 * Date         Author             Changes
 * Nov 28, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.diagram.actions;

import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.common.ui.action.ActionMenuManager;
import org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.stp.bpmn.SequenceFlowConditionType;
import org.eclipse.stp.bpmn.diagram.edit.policies.SequenceEdgeItemSemanticEditPolicy;
import org.eclipse.ui.IWorkbenchPage;

/**
 * A simple action to set a condition type on a sequence flow.
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class SequenceFlowConditionTypeAction extends DiagramAction {

    public static final String MENU_ID = "conditionType";
    
    public SequenceFlowConditionTypeAction(IWorkbenchPage workbenchpage, String id) {
        super(workbenchpage);
        setId(id);
        setText(SequenceFlowConditionType.get(id).getName());
    }

    @Override
    protected Request createTargetRequest() {
        Request request = new Request(SequenceEdgeItemSemanticEditPolicy.SEQUENCE_EDGE_FLOW_CONDITION_TYPE);
        request.getExtendedData().put(SequenceEdgeItemSemanticEditPolicy.FLOW_CONDITION_TYPE, 
                SequenceFlowConditionType.get(getId()).getValue());
        return request;
    }

    @Override
    protected boolean isSelectionListener() {
        return true;
    }

    /**
     * @return a simple menu to contain the activities
     */
    public static IMenuManager createConditionTypeMenu() {
        IAction handler = new Action() { public String getText() {return "Condition type";}};
        return new ActionMenuManager(MENU_ID, handler, false);
    }
}
