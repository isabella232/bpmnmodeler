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
 * Date             Author           Changes 
 * 26 Nov 2006      hmalphettes      Created 
 **/
package org.eclipse.stp.bpmn.diagram.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.requests.SetAllBendpointRequest;
import org.eclipse.jface.action.IAction;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.ui.IWorkbenchPage;

/**
 * Removes all bendpoints.
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ResetBendpointsAction extends DiagramAction {
    
    public static final String ACTION_ID = "resetBendpointsAction"; //$NON-NLS-1$
    public static final String TOOLBAR_ACTION_ID = "toolbarResetBendpointsAction"; //$NON-NLS-1$
    
    public static final ResetBendpointsAction createResetBendpointsAction(IWorkbenchPage workbenchPage) {
        ResetBendpointsAction action = new ResetBendpointsAction(workbenchPage);
        action.setId(ACTION_ID);
        action.setText(BpmnDiagramMessages.ResetBendpointsAction_label);
        action.setToolTipText(BpmnDiagramMessages.ResetBendpointsAction_tooltip);
//        action
//            .setImageDescriptor(DiagramUIActionsPluginImages.DESC_ARRANGE_ALL);
//        action
//            .setDisabledImageDescriptor(DiagramUIActionsPluginImages.DESC_ARRANGE_ALL_DISABLED);
//        action
//            .setHoverImageDescriptor(DiagramUIActionsPluginImages.DESC_ARRANGE_ALL);
        return action;
    }

    /**
     * Creates the Arrange All action for the toolbar menu
     * @param workbenchPage
     */
    public static ResetBendpointsAction createToolbarResetBendpointsAction(IWorkbenchPage workbenchPage) {
        ResetBendpointsAction action = new ResetBendpointsAction(workbenchPage);
        action.setId(TOOLBAR_ACTION_ID);
        action.setText(BpmnDiagramMessages.ResetBendpointsAction_label);
        action.setToolTipText(BpmnDiagramMessages.ResetBendpointsAction_tooltip);
        
//        action
//            .setImageDescriptor(DiagramUIActionsPluginImages.DESC_ARRANGE_ALL);
//        action
//            .setDisabledImageDescriptor(DiagramUIActionsPluginImages.DESC_ARRANGE_ALL_DISABLED);
//        action
//            .setHoverImageDescriptor(DiagramUIActionsPluginImages.DESC_ARRANGE_ALL);
        return action;
    }
    
    
    /**
     * @param workbenchPage
     */
    protected ResetBendpointsAction(
        IWorkbenchPage workbenchPage) {
        super(workbenchPage);
    }


    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {
        
    }
    /* 
     * The operation set is the shapes, connections or both on the diagrm edit part
     * (non-Javadoc)
     * @see org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#createOperationSet()
     */
    protected List createOperationSet() {
        List selection = getSelectedObjects();
        List res = new ArrayList();
        for (Object o : selection) {
            if (o instanceof ConnectionEditPart) {
                res.add(o);
            }
        }
        return res;
    }
    /**
     * Action is enabled if arrange all.   
     * If arrange selection, action is enabled if the 
     * operation set's parent has XYLayout 
     * and there is atleast 2 siblings to arrange
     * @see org.eclipse.gef.ui.actions.EditorPartAction#calculateEnabled()
     */
    protected boolean calculateEnabled() {
        return getOperationSet().size() > 0;
    }

    /* (non-Javadoc)
     * @see org.eclipse.gmf.runtime.common.ui.action.AbstractActionHandler#isSelectionListener()
     */
    protected boolean isSelectionListener() {
        return true;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#updateTargetRequest()
     */
    protected void updateTargetRequest() {
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#createTargetRequest()
     */
    protected Request createTargetRequest() {
        return new SetAllBendpointRequest(
                RequestConstants.REQ_SET_ALL_BENDPOINT,
                new PointList());
    }

    /* (non-Javadoc)
     * @see org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#getCommand()
     */
    protected Command getCommand() {
        CompoundCommand setBendpointsCC = new CompoundCommand(getLabel());
        List elements = getOperationSet();
        for (Iterator iter = elements.iterator(); iter.hasNext();) {
            EditPart element = (EditPart) iter.next();
            Command cmd = element.getCommand(getTargetRequest());
            if (cmd !=null)
                setBendpointsCC.add(cmd);
        }
        if (!setBendpointsCC.isEmpty()) {
            return setBendpointsCC;
        } else {
            return UnexecutableCommand.INSTANCE;
        }
    }

}