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
 * Date                 Author              Changes 
 * 20 Nov 2006      MPeleshchyshyn      Created 
 **/
package org.eclipse.stp.bpmn.diagram.actions;

import java.util.Collection;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.action.AbstractActionHandler;
import org.eclipse.gmf.runtime.diagram.core.commands.SetConnectionAnchorsCommand;
import org.eclipse.gmf.runtime.diagram.core.commands.SetConnectionEndsCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.commands.MoveElementsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.SequenceEdgeEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

/**
 * This class has common methods used both in group and ungroup actions.
 * 
 * @author MPeleshchyshyn
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public abstract class AbstractGroupUngroupAction extends AbstractActionHandler {

    public AbstractGroupUngroupAction(IWorkbenchPage workbenchPage) {
        super(workbenchPage);
    }

    public AbstractGroupUngroupAction(IWorkbenchPart workbenchPart) {
        super(workbenchPart);
    }

    /**
     * Reparents innner connections (move connections to new parent
     * semantically)
     * 
     * @param innerConnections
     *            collection with inner connections
     * @param domain
     *            the transactional editing domain
     * @param container
     *            new parent
     * @param cc
     *            composite command where append reparent commands to
     */
    protected static void reparentInnnerConnections(
            Collection<SequenceEdgeEditPart> innerConnections,
            TransactionalEditingDomain domain, EObject container,
            CompositeCommand cc) {
        if (!innerConnections.isEmpty()) {
            CompositeCommand reparentCommands = new CompositeCommand(
                    BpmnDiagramMessages.AbstractGroupUngroupAction_command_name);
            cc.compose(reparentCommands);
            for (SequenceEdgeEditPart connection : innerConnections) {
                View view = (View) connection.getModel();
                EObject element = ViewUtil.resolveSemanticElement(view);

                ICommand moveSemanticCmd = new MoveElementsCommand(
                        new MoveRequest(domain, container, element));
                reparentCommands.compose(moveSemanticCmd);
            }
        }
    }

    /**
     * Creates command the will reassing source of the specified connection to
     * the specified edit part.
     * 
     * @param cep
     *            the connection
     * @param newParentEditPart
     *            new connection source
     * @param index
     *            current connection index
     * @param numberOfConnections
     *            total number of connections
     * @return composiote command that will reassing set the specified edit part
     *         as new source of the specified connection.
     */
    protected static ICommand getReconnectSourceCommand(ConnectionEditPart cep,
            final ShapeNodeEditPart newParentEditPart, int index,
            int numberOfConnections) {

        TransactionalEditingDomain editingDomain = newParentEditPart
                .getEditingDomain();
        ReconnectRequest request = new ReconnectRequest();
        request.setConnectionEditPart(cep);
        request.setTargetEditPart(newParentEditPart);
        IFigure newParentFigure = newParentEditPart.getFigure();
        Rectangle newParentBounds = newParentFigure.getBounds();
        Point p = newParentBounds.getRight();
        int height = newParentBounds.height;
        if (cep instanceof SubProcessEditPart) {
            height = height - 32;
        }
        p.y = newParentBounds.y + height / (numberOfConnections + 1)
                * (index + 1);
        // p.translate(-newParentBounds.width / 3, 0);
        newParentFigure.translateToAbsolute(p);
        request.setLocation(p);

        ConnectionAnchor sourceAnchor = newParentEditPart
                .getSourceConnectionAnchor(request);
        SetConnectionEndsCommand sceCommand = new SetConnectionEndsCommand(
                editingDomain, null);
        sceCommand.setEdgeAdaptor(new EObjectAdapter((View) cep.getModel()));
        sceCommand.setNewSourceAdaptor(new EObjectAdapter(
                (View) newParentEditPart.getModel()));
        SetConnectionAnchorsCommand scaCommand = new SetConnectionAnchorsCommand(
                editingDomain, null);
        scaCommand.setEdgeAdaptor(new EObjectAdapter((View) cep.getModel()));
        scaCommand.setNewSourceTerminal(newParentEditPart
                .mapConnectionAnchorToTerminal(sourceAnchor));
        CompositeCommand cc = new CompositeCommand(
                BpmnDiagramMessages.Commands_SetConnectionEndsCommand_Source);
        cc.compose(sceCommand);
        cc.compose(scaCommand);

        EditPart containerEditPart = cep.getSource().getParent();
        View container = (View) containerEditPart.getModel();
        EObject context = ViewUtil.resolveSemanticElement(container);

        View view = (View) cep.getModel();
        final EObject element = ViewUtil.resolveSemanticElement(view);

        View newView = (View) newParentEditPart.getModel();
        final EObject newSource = ViewUtil.resolveSemanticElement(newView);
//        ICommand moveSemanticCmd = new MoveElementsCommand(new MoveRequest(
//                editingDomain, context, element)) {
//            @Override
//            protected CommandResult doExecuteWithResult(
//                    IProgressMonitor monitor, IAdaptable info)
//                    throws ExecutionException {
//                CommandResult result = super.doExecuteWithResult(monitor, info);
//                ((SequenceEdge) element).setSource((Vertex) newSource);
//                return result;
//            }
//        };
//
//        cc.add(moveSemanticCmd);
        
        class SetNewSourceCommand extends AbstractTransactionalCommand {
            public SetNewSourceCommand(TransactionalEditingDomain domain,
                    String label) {
                super(domain, label, null);
            }

            @Override
            protected CommandResult doExecuteWithResult(
                    IProgressMonitor monitor, IAdaptable info)
                    throws ExecutionException {
                CommandResult res;
                try {
                    ((SequenceEdge) element).setSource((Vertex) newSource);
                    res = CommandResult.newOKCommandResult();
                } catch (Exception e) {
                    res = CommandResult.newErrorCommandResult(e);
                }
                return res;
            }
        }
        ;
        ICommand moveSemantCommand = new SetNewSourceCommand(editingDomain,
                BpmnDiagramMessages.AbstractGroupUngroupAction_command_new_source);
        cc.compose(moveSemantCommand);
        return cc;
    }

    /**
     * Creates command the will reassing target of the specified connection to
     * the specified edit part.
     * 
     * @param cep
     *            the connection
     * @param newParentEditPart
     *            new connection target
     * @param index
     *            current connection index
     * @param numberOfConnections
     *            total number of connections
     * @return composiote command that will reassing set the specified edit part
     *         as new target of the specified connection.
     */
    protected static ICommand getReconnectTargetCommand(ConnectionEditPart cep,
            final ShapeNodeEditPart newParentEditPart, int index,
            int numberOfConnections) {

        TransactionalEditingDomain editingDomain = newParentEditPart
                .getEditingDomain();
        ReconnectRequest request = new ReconnectRequest();
        request.setConnectionEditPart(cep);
        request.setTargetEditPart(newParentEditPart);
        IFigure newParentFigure = newParentEditPart.getFigure();
        Rectangle newParentBounds = newParentFigure.getBounds();
        Point p = newParentBounds.getLeft();
        int height = newParentBounds.height;
        if (cep instanceof SubProcessEditPart) {
            height = height - 32;
        }
        p.y = newParentBounds.y + height / (numberOfConnections + 1)
                * (index + 1);
        // p.translate(newParentBounds.width / 3, 0);
        newParentFigure.translateToAbsolute(p);
        request.setLocation(p);

        ConnectionAnchor targetAnchor = newParentEditPart
                .getTargetConnectionAnchor(request);
        SetConnectionEndsCommand sceCommand = new SetConnectionEndsCommand(
                editingDomain, null);
        sceCommand.setEdgeAdaptor(new EObjectAdapter((View) cep.getModel()));
        sceCommand.setNewTargetAdaptor(new EObjectAdapter(
                (View) newParentEditPart.getModel()));
        SetConnectionAnchorsCommand scaCommand = new SetConnectionAnchorsCommand(
                editingDomain, null);
        scaCommand.setEdgeAdaptor(new EObjectAdapter((View) cep.getModel()));
        scaCommand.setNewTargetTerminal(newParentEditPart
                .mapConnectionAnchorToTerminal(targetAnchor));
        CompositeCommand cc = new CompositeCommand(
                BpmnDiagramMessages.Commands_SetConnectionEndsCommand_Target);
        cc.compose(sceCommand);
        cc.compose(scaCommand);

        View view = (View) cep.getModel();
        final EObject element = ViewUtil.resolveSemanticElement(view);

        View newView = (View) newParentEditPart.getModel();
        final EObject newTarget = ViewUtil.resolveSemanticElement(newView);
        class SetNewTargetCommand extends AbstractTransactionalCommand {
            public SetNewTargetCommand(TransactionalEditingDomain domain,
                    String label) {
                super(domain, label, null);
            }

            @Override
            protected CommandResult doExecuteWithResult(
                    IProgressMonitor monitor, IAdaptable info)
                    throws ExecutionException {
                CommandResult res;
                try {
                    ((SequenceEdge) element).setTarget((Vertex) newTarget);
                    res = CommandResult.newOKCommandResult();
                } catch (Exception e) {
                    res = CommandResult.newErrorCommandResult(e);
                }
                return res;
            }
        }
        ;
        ICommand moveSemantCommand = new SetNewTargetCommand(editingDomain,
                BpmnDiagramMessages.AbstractGroupUngroupAction_command_new_target);
        cc.compose(moveSemantCommand);

        return cc;
    }

    /**
     * Returns parent subprocess for the specified edit part, edit part itself
     * if edit part is sub-process of <code>null</code> if specified edit part
     * is not placed inside sub-process.
     * 
     * @param editPart
     *            the edit part
     * @return parent subprocess for the specified edit part, edit part itself
     *         if edit part is sub-process of <code>null</code> if specified
     *         edit part is not placed inside sub-process.
     */
    protected static SubProcessEditPart getSubProcess(EditPart editPart) {
        SubProcessEditPart subProcessEditPart = null;
        EditPart currEditPart = editPart;
        while (currEditPart != null) {
            if (currEditPart instanceof SubProcessEditPart) {
                subProcessEditPart = (SubProcessEditPart) currEditPart;
                break;
            }
            currEditPart = currEditPart.getParent();
        }
        return subProcessEditPart;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.common.ui.action.AbstractActionHandler#isSelectionListener()
     */
    protected boolean isSelectionListener() {
        return true;
    }
}