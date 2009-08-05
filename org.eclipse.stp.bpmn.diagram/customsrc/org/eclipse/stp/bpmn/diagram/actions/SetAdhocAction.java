/******************************************************************************
 * Copyright (c) 2009, TecSolCom Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Date				Author					Changes
 * April 22, 2009	Haidar Dahnoun			Created
 * April 22, 2009   Denis Dallaire          Created
 */
package org.eclipse.stp.bpmn.diagram.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.ui.IWorkbenchPage;

/**
 * This action sets a subprocess as adhoc, or not.
 * @author <a href="http://www.techsolcom.ca">TechSolCom</a>
 * @author <a href="mailto:haidar.dahnoun@techsolcom.ca">Haidar Dahnoun</a>
 */
public class SetAdhocAction extends DiagramAction {

	public SetAdhocAction(IWorkbenchPage workbenchPage) {
		super(workbenchPage);
	}

    public final static String ID = "SetAdhocAction"; //$NON-NLS-1$

    public static final String IS_ADHOC_ANNOTATION_SOURCE_AND_KEY_ID = "isAdhoc"; //$NON-NLS-1$
    
    private boolean isAdhoc;
    
    @Override
    public void init() {
        super.init();
        setId(ID);
        refresh();
    }
    
    @Override
	protected Request createTargetRequest() {
		return null;
	}

	@Override
	protected boolean isSelectionListener() {
		return true;
	}
	
    public void refresh() {
        if (getStructuredSelection().isEmpty() || getStructuredSelection().size() > 1) {
            setEnabled(false);
            return;
        }
        if (!(getStructuredSelection().getFirstElement() instanceof SubProcessEditPart)) {
            setEnabled(false);
            return;
        }
        setEnabled(true);
        SubProcess subProcess = (SubProcess) ((SubProcessEditPart) getStructuredSelection().getFirstElement()).resolveSemanticElement();
        if (!subProcess.isAdhoc()) { //$NON-NLS-1$
            isAdhoc = true;
            setText(BpmnDiagramMessages.SetAdhocAction_label_adhoc);
            setToolTipText(BpmnDiagramMessages.SetAdhocAction_adhoc_tooltip); 
        } else {
        	isAdhoc = false;
            setText(BpmnDiagramMessages.SetAdhocAction_label_non_adhoc);
            setToolTipText(BpmnDiagramMessages.SetAdhocAction_non_adhoc_tooltip);
        }
    }

    
    @Override
    protected Command getCommand() {
        ICommand command = new SetDefaultAction.MyCommand(
                ((ShapeNodeEditPart) getStructuredSelection().
                        getFirstElement()).resolveSemanticElement()) {

            @Override
            protected CommandResult doExecuteWithResult(
                    IProgressMonitor monitor, IAdaptable adaptable)
            throws ExecutionException {
            	SubProcess subProcess = (SubProcess) ((SubProcessEditPart) getStructuredSelection().
                        getFirstElement()).resolveSemanticElement();
                if (isAdhoc) {
                    subProcess.setAdhoc(true);
                } else {
                    subProcess.setAdhoc(false);
                }
                ((SubProcessEditPart) getStructuredSelection().getFirstElement()).getFigure().repaint();
                return CommandResult.newOKCommandResult();
            }};
            return new ICommandProxy(command);
    }
    
    
}
