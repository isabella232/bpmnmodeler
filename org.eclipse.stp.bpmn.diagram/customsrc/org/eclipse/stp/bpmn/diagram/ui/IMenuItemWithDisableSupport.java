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
 * Date         Author             Changes
 * Apr 23, 2007      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.diagram.ui;

/**
 * @author atoulme
 * an interface used by the PopupMenuWithDisableSupport.
 * If an item of the popup menu implements this interface,
 * it enables it to be shown as disabled when the popup menu 
 * is created.
 */
public interface IMenuItemWithDisableSupport {

	/**
	 * 
	 * @return truye if the menu item should be shown as enabled
	 * on the popup menu.
	 */
	public boolean isEnabled();
}
