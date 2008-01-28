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
 * 01 Dec 2006      MPeleshchyshyn      Created 
 **/
package org.eclipse.stp.bpmn.diagram.actions;

import org.eclipse.gmf.runtime.common.ui.action.AbstractActionHandler;
import org.eclipse.gmf.runtime.common.ui.action.ActionMenuManager;
import org.eclipse.gmf.runtime.common.ui.util.IPartSelector;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author MPeleshchsyhyn
 * 
 */
public class GroupActionManager extends ActionMenuManager {
    public static final String MENU_ID = "groupMenu"; //$NON-NLS-1$

    private static final String MENU_TEXT = BpmnDiagramMessages.GroupActionManager_label;

    /**
     * The group menu action containing the UI for the group menu manager
     */
    private static class GroupMenuAction extends Action {
        public GroupMenuAction() {
            setText(MENU_TEXT);
            setToolTipText(GroupAction.TOOLTIP_TEXT);

            setImageDescriptor(BpmnDiagramEditorPlugin
                    .getBundledImageDescriptor(GroupAction.ICON_PATH));
        }
    }

    /**
     * Creates a new instance of the group menu manager
     */
    public GroupActionManager() {
        super(MENU_ID, new GroupMenuAction(), true);
    }

    /**
     * Creates a new instance of the group menu manager
     * 
     * @param action
     *            default action associated with this menu manager (should not
     *            be null)
     */
    public GroupActionManager(IAction action) {
        super(MENU_ID, action, true);

        // If the action is null then use the original menu action
        if (action == null) {
            action = new GroupMenuAction();
            setDefaultAction(action);
        }

        ((AbstractActionHandler) getDefaultAction())
                .setPartSelector(new IPartSelector() {
                    public boolean selects(IWorkbenchPart p) {
                        return p instanceof IDiagramWorkbenchPart;
                    }
                });
    }
}
