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
 * Date			Author					Changes
 * Mar 19, 2008	Antoine Toulme		Created
 */
package org.eclipse.stp.bpmn.diagram.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.ui.IWorkbenchPage;

/**
 * This action sets a subprocess as transactional, or not.
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class SetTransactionalAction extends DiagramAction {

    public SetTransactionalAction(IWorkbenchPage workbenchPage) {
        super(workbenchPage);
    }

    public final static String ID = "SetTransactionalAction";

    public static final String IS_TRANSACTIONAL_ANNOTATION_SOURCE_AND_KEY_ID = "isThrowing";
    
    private boolean isTransactional;
    
    @Override
    public void init() {
        super.init();
        setId(ID);
        refresh();
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
        SubProcess sp = (SubProcess) ((SubProcessEditPart) getStructuredSelection().getFirstElement()).resolveSemanticElement();
        String ann = EcoreUtil.getAnnotation(sp, IS_TRANSACTIONAL_ANNOTATION_SOURCE_AND_KEY_ID, 
                IS_TRANSACTIONAL_ANNOTATION_SOURCE_AND_KEY_ID);
        if (ann == null || ann.equals("false")) {
            isTransactional = true;
            setText("Set as transactional");
            setToolTipText("If selected this action will show the " +
                    "shape as \"transactional\".");
        } else {
            isTransactional = false;
            setText("Set as non-transactional");
            setToolTipText("If selected this action will show the " +
                    "shape as \"not transactional\".");
        }
    }


    /**
     * we override getCommand to do the job instead
     */
    @Override
    protected Request createTargetRequest() {
        return null;
    }


    /**
     * always listening to selection
     */
    @Override
    protected boolean isSelectionListener() {
        return true;
    }
    
    @Override
    protected Command getCommand() {
        ICommand command = new SetDefaultAction.MyCommand(
                ((SubProcessEditPart) getStructuredSelection().
                getFirstElement()).resolveSemanticElement()) {

            @Override
            protected CommandResult doExecuteWithResult(
                    IProgressMonitor monitor, IAdaptable adaptable)
            throws ExecutionException {
                SubProcess sp = (SubProcess) ((SubProcessEditPart) getStructuredSelection().
                        getFirstElement()).resolveSemanticElement();
                if (isTransactional) {
                    EcoreUtil.setAnnotation(sp, 
                            IS_TRANSACTIONAL_ANNOTATION_SOURCE_AND_KEY_ID, 
                            IS_TRANSACTIONAL_ANNOTATION_SOURCE_AND_KEY_ID, 
                            "true");
                } else {
                    EcoreUtil.setAnnotation(sp, 
                            IS_TRANSACTIONAL_ANNOTATION_SOURCE_AND_KEY_ID, 
                            IS_TRANSACTIONAL_ANNOTATION_SOURCE_AND_KEY_ID, 
                            "false");
                }
                ((SubProcessEditPart) getStructuredSelection().getFirstElement()).getFigure().repaint();
                return CommandResult.newOKCommandResult();
            }};
            return new ICommandProxy(command);
    }

}
