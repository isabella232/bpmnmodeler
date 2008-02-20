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
 * Feb 18, 2008	Antoine Toulme		Created
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
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;
import org.eclipse.ui.IWorkbenchPage;

/**
 * This action changes the way the shape is painted in BPMN 1.1 style by
 * setting an annotation on the semantic element.
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class SetAsThrowingOrCatchingAction extends DiagramAction {

    public SetAsThrowingOrCatchingAction(IWorkbenchPage workbenchPage) {
        super(workbenchPage);
    }

    public final static String ID = "SetAsThrowingOrCatchingAction";

    public static final String IS_THROWING_ANNOTATION_SOURCE_AND_KEY_ID = "isThrowing";
    
    private boolean toThrow;
    
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
        if (!(getStructuredSelection().getFirstElement() instanceof ActivityEditPart)) {
            setEnabled(false);
            return;
        }
        setEnabled(((ActivityEditPart) getStructuredSelection().getFirstElement()).shouldShowSetAsThrowingOrCatching());
        Activity activity = (Activity) ((ActivityEditPart) getStructuredSelection().getFirstElement()).resolveSemanticElement();
        String ann = EcoreUtil.getAnnotation(activity, IS_THROWING_ANNOTATION_SOURCE_AND_KEY_ID, 
                IS_THROWING_ANNOTATION_SOURCE_AND_KEY_ID);
        if (ann == null || ann.equals("false")) {
            toThrow = true;
            setText("Set as a throwing shape");
            setToolTipText("If selected this action will show the " +
            		"shape as \"throwing\" per the BPMN 1.1 standard.");
        } else {
            toThrow = false;
            setText("Set as a catching shape");
            setToolTipText("If selected this action will show the " +
                    "shape as \"catching\" per the BPMN 1.1 standard.");
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
                ((ActivityEditPart) getStructuredSelection().
                getFirstElement()).resolveSemanticElement()) {

            @Override
            protected CommandResult doExecuteWithResult(
                    IProgressMonitor monitor, IAdaptable adaptable)
            throws ExecutionException {
                Activity activity = (Activity) ((ActivityEditPart) getStructuredSelection().
                        getFirstElement()).resolveSemanticElement();
                if (toThrow) {
                    EcoreUtil.setAnnotation(activity, 
                            IS_THROWING_ANNOTATION_SOURCE_AND_KEY_ID, 
                            IS_THROWING_ANNOTATION_SOURCE_AND_KEY_ID, 
                            "true");
                } else {
                    EcoreUtil.setAnnotation(activity, 
                            IS_THROWING_ANNOTATION_SOURCE_AND_KEY_ID, 
                            IS_THROWING_ANNOTATION_SOURCE_AND_KEY_ID, 
                            "false");
                }
                ((ActivityEditPart) getStructuredSelection().getFirstElement()).getFigure().repaint();
                return CommandResult.newOKCommandResult();
            }};
            return new ICommandProxy(command);
    }
}
