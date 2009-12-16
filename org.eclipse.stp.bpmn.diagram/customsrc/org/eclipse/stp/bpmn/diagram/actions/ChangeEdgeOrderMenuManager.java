/**
 *  Copyright (C) 2007, Intalio Inc.
 *
 *  The program(s) herein may be used and/or copied only with the
 *  written permission of Intalio Inc. or in accordance with the terms
 *  and conditions stipulated in the agreement/contract under which the
 *  program(s) have been supplied.
 *
 * Date         Author             Changes
 * Dec 14, 2007      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.diagram.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SequenceEdgeEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

/**
 * This menu shows to submenus, source and target, which show
 * in turn an action that changes the order of the edge on the shape. 
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class ChangeEdgeOrderMenuManager extends MenuManager {

    /**
     * menu ID.
     */
    public static final String ID = "changeEdgeOrderMenu"; //$NON-NLS-1$
    
    // submenu IDs.
    /**
     * submenu for the source submenu
     */
    public static final String SUB_MENU_SOURCE = "changeEdgeOrderMenu_source"; //$NON-NLS-1$
    
    /**
     * submenu for the target submenu
     */
    public static final String SUB_MENU_TARGET = "changeEdgeOrderMenu_target"; //$NON-NLS-1$
    
    // actions IDs
    public static final String EDGE_ORDER_TARGET_UP = "changeEdgeOrderMenu_target_up"; //$NON-NLS-1$
    
    public static final String EDGE_ORDER_TARGET_DOWN = "changeEdgeOrderMenu_target_down"; //$NON-NLS-1$
    
    public static final String EDGE_ORDER_SOURCE_UP = "changeEdgeOrderMenu_source_up"; //$NON-NLS-1$
    
    public static final String EDGE_ORDER_SOURCE_DOWN = "changeEdgeOrderMenu_source_down"; //$NON-NLS-1$
    
    public ChangeEdgeOrderMenuManager() {
        super(BpmnDiagramMessages.ChangeEdgeOrderMenuManager_menu_label, ID);
    }

    /**
     * Creates a submenu
     * @param text the label of the action
     * @param id the ID of the action
     * @return the submenu
     */
    public static MenuManager createSubmenu(String text, String id) {
        return new MenuManager(text, id);
    }
    
    /**
     * the action to change the order itself.
     * It is initialized knowing it should apply to the source or the target, 
     * and add or remove a degree in the edge position.
     *
     * @author <a href="http://www.intalio.com">Intalio Inc.</a>
     * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
     */
    private static class ChangeEdgeOrderAction extends DiagramAction {

        /**
         * whether this action applies to the target or the source of the edge
         */
        private boolean isTarget = false;
        
        /**
         * whether this action adds or remove one in the position of the edge.
         */
        private boolean isUp = false;
        
        public ChangeEdgeOrderAction(IWorkbenchPage workbenchPage, String id) {
            super(workbenchPage);
            setId(id);
        }

        public ChangeEdgeOrderAction(IWorkbenchPart workbenchPart, String id) {
            super(workbenchPart);
            setId(id);
        }
        
        @Override
        public void init() {
            super.init();
            if (EDGE_ORDER_SOURCE_DOWN.equals(getId())) {
               isTarget = false;
               isUp = false;
               setText(BpmnDiagramMessages.ChangeEdgeOrderMenuManager_down);
            } else if (EDGE_ORDER_SOURCE_UP.equals(getId())) {
                isTarget = false;
                isUp = true;
                setText(BpmnDiagramMessages.ChangeEdgeOrderMenuManager_up);
            } else if (EDGE_ORDER_TARGET_DOWN.equals(getId())) {
                isTarget = true;
                isUp = false;
                setText(BpmnDiagramMessages.ChangeEdgeOrderMenuManager_down);
            } else if (EDGE_ORDER_TARGET_UP.equals(getId())) {
                isTarget = true;
                isUp = true;
                setText(BpmnDiagramMessages.ChangeEdgeOrderMenuManager_up);
            } else {
                throw new IllegalArgumentException("Invalid id: " + getId()); //$NON-NLS-1$
            }
            if (isUp) {
                setImageDescriptor(BpmnDiagramEditorPlugin.
                        getBundledImageDescriptor("icons/obj16/up.png"));//$NON-NLS-1$
            } else {
                setImageDescriptor(BpmnDiagramEditorPlugin.
                        getBundledImageDescriptor("icons/obj16/down.png"));//$NON-NLS-1$
            }
            refresh();
        }
        /**
         * not implemented
         * @see getCommand
         */
        @Override
        protected Request createTargetRequest() {
            return null;
        }
        
        /**
         * overridden to return our very own command, 
         * no need of an edit policy for this
         */
        @Override
        protected Command getCommand() {
            boolean wouldWork = true;
            Object sel = getStructuredSelection().getFirstElement();
            if (!(sel instanceof SequenceEdgeEditPart)) {
                return UnexecutableCommand.INSTANCE;
            }
            final SequenceEdge edge = (SequenceEdge) ((SequenceEdgeEditPart) sel).resolveSemanticElement();
            Vertex vertex = isTarget ? edge.getTarget() : edge.getSource();
            EList<SequenceEdge> edges = isTarget ? vertex.getIncomingEdges() : vertex.getOutgoingEdges();

            if (isUp) {
                if (0 == edges.indexOf(edge)) {
                    wouldWork = false;
                }
            } else {
                if ((edges.size() - 1) == edges.indexOf(edge)) {
                    wouldWork = false;
                }
            }
            if (!wouldWork) {
                return UnexecutableCommand.INSTANCE;
            }
            final SequenceEdgeEditPart seqEp = ((SequenceEdgeEditPart) getStructuredSelection().
                    getFirstElement());
            AbstractTransactionalCommand command = new AbstractTransactionalCommand(
                    (TransactionalEditingDomain) AdapterFactoryEditingDomain.
                    getEditingDomainFor(edge),
                    BpmnDiagramMessages.ChangeEdgeOrderMenuManager_command_name,
                    null) {

                        @Override
                        protected CommandResult doExecuteWithResult(
                                IProgressMonitor monitor, IAdaptable info)
                                throws ExecutionException {
                            Vertex vertex = isTarget ? edge.getTarget() : edge.getSource();
                            EList<SequenceEdge> edges = isTarget ? vertex.getIncomingEdges() : vertex.getOutgoingEdges();
                            int oldIndex = edges.indexOf(edge);
                            int newIndex = 0;
                            if (isUp) {
                                newIndex = oldIndex -1;
                            } else {
                                newIndex = oldIndex + 1;
                            }
                            edges.move(newIndex, oldIndex);
                            EditPart part = isTarget ? seqEp.getTarget() : seqEp.getSource();
                            if (part instanceof ActivityEditPart) {
                                ((ActivityEditPart) part).refreshSourceConnections();
                                ((ActivityEditPart) part).refreshTargetConnections();
                            } else if (part instanceof SubProcessEditPart) {
                                    ((SubProcessEditPart) part).refreshSourceConnections();
                                    ((SubProcessEditPart) part).refreshTargetConnections();
                            } else {
                                // hopefully not possible, can't do much here.
                            }
                            return CommandResult.newOKCommandResult();
                        }};
             CompoundCommand compound = new CompoundCommand();
             compound.add(new ICommandProxy(command));
             compound.add(((SequenceEdgeEditPart) sel).getCommand(
                     new Request(RequestConstants.REQ_REFRESH)));
             return compound;
        }

        @Override
        protected boolean isSelectionListener() {
            return true;
        }
        
    }

    /**
     * Creates an action with the Id supplied as a parameter.
     * @param workbenchPage
     * @param actionId
     * @return
     */
    public static IAction createAction(IWorkbenchPage workbenchPage, String actionId) {
        return new ChangeEdgeOrderAction(workbenchPage, actionId);
    }
}
