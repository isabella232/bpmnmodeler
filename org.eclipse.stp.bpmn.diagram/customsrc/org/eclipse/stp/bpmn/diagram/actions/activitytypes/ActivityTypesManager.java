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
package org.eclipse.stp.bpmn.diagram.actions.activitytypes;

import org.eclipse.gmf.runtime.common.ui.action.ActionMenuManager;
import org.eclipse.jface.action.Action;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;

/**
 * This menu manager is used for the activity types popup menus.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ActivityTypesManager extends ActionMenuManager {
	/**
	 * The edit menu action containing the UI for the edit menu manager
	 */
	private static class ActivityTypesManagerAction extends Action {
		public ActivityTypesManagerAction() {
			setText(BpmnDiagramMessages.ActivityTypesManager_menuTitle);
			setId(ID);
		}
		
		/**
		 * This constructor is used to build the blank action of the submenus.
		 * @param id the id of the menu
		 * @param text the text of the menu
		 */
		public ActivityTypesManagerAction(String id, String text) {
            setText(text);
            setId(id);
        }
	}

	public static final String ID = "ActivityTypes"; //$NON-NLS-1$
	
	public ActivityTypesManager() {
		super(ID, new ActivityTypesManagerAction(), false);
	}
	
	/**
	 * Creates an ActionMenuManager object from the id
	 * and the text passed in parameters.
	 * @param id the id of the submenu to create
	 * @param text the text of the submenu.
	 * @return the menu
	 */
	public static ActionMenuManager createSubMenu(String id, String text) {
	    return new ActionMenuManager(id, new ActivityTypesManagerAction(id, text), true);
	}
	
	/**
	 * The ID for the submenu for the task, looping task, subprocess
	 * and looping subprocess activity types.
	 */
	public static final String SUBMENU_TASKS_ID = "ActivityTypes_Tasks";  //$NON-NLS-1$
	
	/**
	 * the submenu id for the gateways
	 */
	public static final String SUBMENU_GATEWAYS_ID = "ActivityTypes_Gateways"; //$NON-NLS-1$
	
	/**
	 * the submenu id for the start events
	 */
	public static final String SUBMENU_START_EVENTS_ID = "ActivityTypes_StartEvents"; //$NON-NLS-1$
	
	/**
	 * the submenu id for the intermediate events
	 */
	public static final String SUBMENU_INTERMEDIATE_EVENTS_ID = "ActivityTypes_IntermediateEvents"; //$NON-NLS-1$
	
	/**
	 * the submenu id for the end events.
	 */
	public static final String SUBMENU_END_EVENTS_ID = "ActivityTypes_EndEvents"; //$NON-NLS-1$
	
}
