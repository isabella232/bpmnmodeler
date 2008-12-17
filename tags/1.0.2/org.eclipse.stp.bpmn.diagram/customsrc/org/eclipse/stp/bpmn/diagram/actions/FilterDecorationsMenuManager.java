/******************************************************************************
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 *
 * Date         Author             Changes
 * Apr 30, 2007      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.diagram.actions;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.ui.action.ActionMenuManager;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.providers.BpmnDecorationFilterService;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

/**
 * This menu is built dynamically on startup by reading the filters
 * provided by the BpmnDecorationFilterService instance.
 * Each action is indirectly contributed by the DecorationFilter
 * extension point which provides a label and optionally an image
 * for the filter.
 * 
 * The filter may be activated or deactivated through the actions
 * built here.
 * 
 * The state of the filters is remembered through the preference store.
 * @author atoulme
 */
public class FilterDecorationsMenuManager extends ActionMenuManager {

	/**
	 * a fake action that helps creating the menu, giving it
	 * an ID and a text.
	 */
	private static class FilterDecorationsMenuManagerAction extends Action {
		public FilterDecorationsMenuManagerAction() {
			setImageDescriptor(BpmnDiagramEditorPlugin.
					getBundledImageDescriptor(
					"icons/obj16/showDecorations.gif")); //$NON-NLS-1$
			setToolTipText(BpmnDiagramMessages.FilterDecorationsMenuManager_tooltip);
			setId(ID);
		}
	}
	
	/**
	 * this action reverts the value of the filter with which it
	 * is associated.
	 * 
	 * @author atoulme
	 */
	private class TriggerFilterAction extends Action {
		
		@Override
		public void run() {
			BpmnDecorationFilterService.getInstance().
				filterChanged(getId());
			
			IWorkbenchPage page = PlatformUI.getWorkbench().
				getActiveWorkbenchWindow().getActivePage();
			if (page != null) {
				IEditorPart part = page.getActiveEditor();
				EditPart ep = (EditPart) part.getAdapter(EditPart.class);
				ep.refresh();
				recursiveRefresh(ep);
			}
		}
		
		/**
		 * recursively refreshes the edit parts, so that
		 * decorations are hidden or shown.
		 * @param part
		 */
		private void recursiveRefresh(EditPart part) {
			for (Object child : part.getChildren()) {
				((EditPart) child).refresh();
				recursiveRefresh((EditPart) child);
			}
		}
	}
	
	/**
	 * the menu Id.
	 */
	public static final String ID = "FilterDecorationsMenuManager"; //$NON-NLS-1$
	
	/**
	 * initializes the menu with a fake action, and 
	 * adds all the filter actions.
	 * @param id
	 * @param actionHandler
	 * @param retargetLastAction
	 */
	public FilterDecorationsMenuManager(IWorkbenchPage page) {
		super(ID, new FilterDecorationsAction(page), false);
		BpmnDecorationFilterService filterService = 
			BpmnDecorationFilterService.getInstance();
		for (String filterId : filterService.getCurrentFilters()) {
			Action action = new TriggerFilterAction();
			action.setId(filterId);
			
			ImageDescriptor imageDesc = filterService.
				getFilterImageDescriptor(filterId);
			action.setImageDescriptor(imageDesc);
			String name = filterService.getFilterName(filterId);
			action.setText(name);
			action.setToolTipText(name);
			action.setChecked(filterService.isFilterActive(filterId));
			add(action);
		}
		
	}

	
}
