/*
 *******************************************************************************
 ** Copyright (c) 2006, Intalio Inc.
 ** All rights reserved. This program and the accompanying materials
 ** are made available under the terms of the Eclipse Public License v1.0
 ** which accompanies this distribution, and is available at
 ** http://www.eclipse.org/legal/epl-v10.html
 ** 
 ** Contributors:
 **     Intalio Inc. - initial API and implementation
 ********************************************************************************
 */
package org.eclipse.stp.bpmn.diagram.providers;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.AbstractContributionItemProvider;
import org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.actions.ArrangeSelectionAction;
import org.eclipse.stp.bpmn.diagram.actions.ArrangeSelectionRecursivelyAction;
import org.eclipse.stp.bpmn.diagram.actions.ChangeEdgeOrderMenuManager;
import org.eclipse.stp.bpmn.diagram.actions.DeleteFileLinkAction;
import org.eclipse.stp.bpmn.diagram.actions.FilterDecorationsAction;
import org.eclipse.stp.bpmn.diagram.actions.FilterDecorationsMenuManager;
import org.eclipse.stp.bpmn.diagram.actions.GroupAction;
import org.eclipse.stp.bpmn.diagram.actions.GroupActionManager;
import org.eclipse.stp.bpmn.diagram.actions.ResetBendpointsAction;
import org.eclipse.stp.bpmn.diagram.actions.SetAsThrowingOrCatchingAction;
import org.eclipse.stp.bpmn.diagram.actions.SetDefaultAction;
import org.eclipse.stp.bpmn.diagram.actions.SetLoopAction;
import org.eclipse.stp.bpmn.diagram.actions.SubProcessCollapseStyleToolbarAction;
import org.eclipse.stp.bpmn.diagram.actions.UngroupAction;
import org.eclipse.stp.bpmn.diagram.actions.activitytypes.ActivityTypesManager;
import org.eclipse.stp.bpmn.diagram.actions.activitytypes.ChangeActivityTypeAction;
import org.eclipse.stp.bpmn.diagram.actions.associationdirectiontypes.AssociationDirectionTypesManager;
import org.eclipse.stp.bpmn.diagram.actions.associationdirectiontypes.ChangeAssociationDirectionTypeAction;
import org.eclipse.stp.bpmn.dnd.file.FileDnDConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.actions.OpenWithMenu;

/**
 * @notgenerated add the reset bendpoint action. overrides the Arrange actions.
 * added the set default action, added the set loop action.
 * added an action to remove the file link.
 * @author hmalphettes
 * @author atoulme
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BpmnDiagramActionProvider extends AbstractContributionItemProvider {

    /** Ability to change the menu for the association direction type
     * by setting it statically here. */
    public static AssociationDirectionTypesManager ASSOCIATION_DIR_MENU_MANAGER =
        new AssociationDirectionTypesManager();
    
    /**
     * @return The menu manager
     */
    protected IMenuManager createMenuManager(String menuId,
            IWorkbenchPartDescriptor partDescriptor) {
        if (GroupActionManager.MENU_ID.equals(menuId)) {
            return new GroupActionManager(getAction(GroupAction.ACTION_ID,
                    partDescriptor));
        } else if (FilterDecorationsMenuManager.ID.equals(menuId)) {
        	return new FilterDecorationsMenuManager(
        			partDescriptor.getPartPage());
        } else if (ActivityTypesManager.ID.equals(menuId)) {
            return new ActivityTypesManager();
        } else if (ActivityTypesManager.SUBMENU_TASKS_ID.equals(menuId)) {
            return ActivityTypesManager.createSubMenu(menuId, BpmnDiagramMessages.BpmnDiagramActionProvider_tasks);
        } else if (ActivityTypesManager.SUBMENU_START_EVENTS_ID.equals(menuId)) {
            return ActivityTypesManager.createSubMenu(menuId, BpmnDiagramMessages.BpmnDiagramActionProvider_start_events);
        } else if (ActivityTypesManager.SUBMENU_INTERMEDIATE_EVENTS_ID.equals(menuId)) {
            return ActivityTypesManager.createSubMenu(menuId, BpmnDiagramMessages.BpmnDiagramActionProvider_inter_events);
        } else if (ActivityTypesManager.SUBMENU_END_EVENTS_ID.equals(menuId)) {
            return ActivityTypesManager.createSubMenu(menuId, BpmnDiagramMessages.BpmnDiagramActionProvider_end_events);
        } else if (ActivityTypesManager.SUBMENU_GATEWAYS_ID.equals(menuId)) {
            return ActivityTypesManager.createSubMenu(menuId, BpmnDiagramMessages.BpmnDiagramActionProvider_gateways);
        } else if (AssociationDirectionTypesManager.ID.equals(menuId)) {
            return new AssociationDirectionTypesManager();
        } else if (OpenWithMenu.ID.equals(menuId)) {
        	Object selected = getSelectedObject(partDescriptor);
        	if (selected instanceof IGraphicalEditPart) {
        		EObject eobject = ((IGraphicalEditPart) selected).
        			getNotationView().getElement();
        		if (eobject instanceof EModelElement) {
        			EAnnotation fileLink = ((EModelElement) eobject).
        			getEAnnotation(FileDnDConstants.ANNOTATION_SOURCE);
        			if (fileLink != null) {
        				String filePath = (String) fileLink.getDetails()
        				    .get(FileDnDConstants.PROJECT_RELATIVE_PATH);
        				IFile linked =
                            WorkspaceSynchronizer.getFile(eobject.eResource())
        							.getProject().getFile(filePath);
        				if (linked.exists()) {
        					IMenuManager manager = new MenuManager(
        					BpmnDiagramMessages.BpmnDiagramActionProvider_open_file_menu_label);
        					    manager.add(new OpenWithMenu(
        							partDescriptor.getPartPage(),
        							linked));
        					return manager;
        					
        				}
        			}
        		}
        	}
        	
        } else if (ChangeEdgeOrderMenuManager.ID.equals(menuId)) {
            return new ChangeEdgeOrderMenuManager();
        } else if (ChangeEdgeOrderMenuManager.SUB_MENU_SOURCE.equals(menuId)) {
            return ChangeEdgeOrderMenuManager.createSubmenu(BpmnDiagramMessages.BpmnDiagramActionProvider_change_edge_order_source_sub_menu, menuId);
        } else if (ChangeEdgeOrderMenuManager.SUB_MENU_TARGET.equals(menuId)) {
            return ChangeEdgeOrderMenuManager.createSubmenu(BpmnDiagramMessages.BpmnDiagramActionProvider_change_edge_order_target_sub_menu, menuId);
        }
        return super.createMenuManager(menuId, partDescriptor);
    }

    /**
     * 
     */
    protected IAction createAction(String actionId,
            IWorkbenchPartDescriptor partDescriptor) {

        IWorkbenchPage workbenchPage = partDescriptor.getPartPage();
        if (GroupAction.ACTION_ID.equals(actionId)) {
            return GroupAction.createGroupAction(workbenchPage);
        } else if (GroupAction.TOOLBAR_ACTION_ID.equals(actionId)) {
            return GroupAction.createToolbarGroupAction(workbenchPage);
        } else if (UngroupAction.ACTION_ID.equals(actionId)) {
            return UngroupAction.createUngroupAction(workbenchPage);
        } else if (UngroupAction.TOOLBAR_ACTION_ID.equals(actionId)) {
            return UngroupAction.createToolbarUngroupAction(workbenchPage);
        } else if (actionId.equals(ArrangeSelectionRecursivelyAction.ID)) {
            return new ArrangeSelectionRecursivelyAction(workbenchPage);
        } else if (actionId.equals(ArrangeSelectionAction.ACTION_TOOLBAR_ARRANGE_SELECTION)) {
            return new ArrangeSelectionRecursivelyAction(workbenchPage);
        } else if (actionId.equals(ResetBendpointsAction.ACTION_ID)) {
            return ResetBendpointsAction
                    .createResetBendpointsAction(workbenchPage);
        } else if (actionId.equals(ResetBendpointsAction.TOOLBAR_ACTION_ID)) {
            return ResetBendpointsAction
                    .createToolbarResetBendpointsAction(workbenchPage);
        } else if (actionId.equals(SetDefaultAction.ID)) {
        	return new SetDefaultAction(workbenchPage);
        } else if (actionId.equals(SetLoopAction.ID)) {
        	return new SetLoopAction(workbenchPage);
        } else if (actionId.startsWith(
                ChangeActivityTypeAction.ABSTRACT_ID)) {
            return new ChangeActivityTypeAction(workbenchPage,actionId);
        } else if (actionId.startsWith(
                ChangeAssociationDirectionTypeAction.ABSTRACT_ID)) {
            return ASSOCIATION_DIR_MENU_MANAGER
                .createChangeAssociationDirectionTypeAction(workbenchPage,actionId);
        }  else if (actionId.equals(FilterDecorationsAction.ID)) {
        	return new FilterDecorationsAction(workbenchPage);
        } else if (DeleteFileLinkAction.ID.equals(actionId)) {
        	return new DeleteFileLinkAction(workbenchPage);
        } else if (SubProcessCollapseStyleToolbarAction.ID.equals(actionId)) {
            return new SubProcessCollapseStyleToolbarAction(workbenchPage);
        } else if (actionId.startsWith(ChangeEdgeOrderMenuManager.ID)) {
            return ChangeEdgeOrderMenuManager.createAction(workbenchPage, actionId);
        } else if (SetAsThrowingOrCatchingAction.ID.equals(actionId)) {
            return new SetAsThrowingOrCatchingAction(workbenchPage);
        }
        return super.createAction(actionId, partDescriptor);
    }
    
}
