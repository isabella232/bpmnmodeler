/******************************************************************************
 * Copyright (c) 2006, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Dates       		 Author              Changes
 * Dec 18, 2006      Antoine Toulm�   Creation
 */
package org.eclipse.stp.bpmn.diagram.actions.associationdirectiontypes;

import org.eclipse.gmf.runtime.common.ui.action.ActionMenuManager;
import org.eclipse.jface.action.Action;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.ui.IWorkbenchPage;

/**
 * This menu manager is used for the activity types popup menus.
 * 
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class AssociationDirectionTypesManager extends ActionMenuManager {
	/**
	 * The edit menu action containing the UI for the edit menu manager
	 */
	private static class AssociationDirectionTypesAction extends Action {
		public AssociationDirectionTypesAction(String menuText) {
			setText(menuText);
			setId(ID);
		}
	}

	public static final String ID = "AssociationDirectionTypes"; //$NON-NLS-1$
	
	
	/**
     * Default menuText: "Set association direction".
     */
    public AssociationDirectionTypesManager() {
        this(BpmnDiagramMessages.AssociationDirectionTypesManager_label);
    }
	/**
	 * @menuText The label of the menu.
	 */
	protected AssociationDirectionTypesManager(String menuText) {
		super(ID, new AssociationDirectionTypesAction(menuText), true);
	}
	
    /**
     * Default constructor
     * @param workbenchPage
     */
    public ChangeAssociationDirectionTypeAction
            createChangeAssociationDirectionTypeAction(
                    IWorkbenchPage workbenchPage,String name) {
        return new ChangeAssociationDirectionTypeAction(workbenchPage, name);
    }

}
