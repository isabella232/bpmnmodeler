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
package org.eclipse.stp.bpmn.dnd;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.swt.graphics.Image;

/**
 * The IDnDHandler interface specifies how the modeler handles the 
 * drag and drop operations.
 * The IDnDHandler objects can be retrieved from the selection of
 * the LocalSelectionTransfer instance or be adapted from this selection.
 * 
 * The user drops the IDnDHandlers by releasing the mouse button 
 * over an edit part. This edit part will build a pop-up menu
 * which will contain as many choices as specified by the sum of the
 * getItemCount methods of the IDnDHandlers present.
 * 
 * For each item, the IDnDHandler will show a text, an image for the
 * item, enabled or disabled.
 * 
 * 
 * 
 * @author atoulme
 *
 */
public interface IDnDHandler {

	/**
	 * 
	 * @return a number representing the priority of the handler.
	 * The handlers are sorted by priority prior being shown
	 * in the pop-up menu, the elements which have the strongest
	 * priority are shown on the top of the menu.
	 */
	public int getPriority();
	
	/**
	 * 
	 * @return the number of items that should be shown in 
	 * the pop-up menu representing this handler.
	 * Each item will represent a different choice.
	 */
	public int getItemCount();
	
	/**
	 * 
	 * @param hoverPart
	 * @param index the current index of the handler elements.
	 * @return the label for the descriptor passed in parameter
	 * for the part which is currently hovered.
	 * 
	 */
	public String getMenuItemLabel(IGraphicalEditPart hoverPart, int index);
	
	
	/**
	 * @param hoverPart
	 * @param index the current index of the handler elements.
	 * @return the image for the descriptor passed in parameter
	 * for the part which is currently hovered.
	 */
	public Image getMenuItemImage(IGraphicalEditPart hoverPart, int index);
	
	/**
	 * @param hoverPart
	 * @param index the current index of the handler elements.
	 * @return true if the element should be shown as enabled in the pop-up menu,
	 * false if the element should be shown as disabled, and thus not
	 * available for a drop.
	 */
	public boolean isEnabled(IGraphicalEditPart hoverPart, int index);
	
	/**
	 * @param hoverPart the part on which the element is dropped
	 * @param index the index representing the choice of the user
	 * in the menu.
	 * @param dropLocation the location of the drop
	 * @return a Command object that accomplishes the drop of an EObject
	 * or a View over the given edit part.
	 * 
	 */
	public Command getDropCommand(IGraphicalEditPart hoverPart, int index, 
			Point dropLocation);

	/**
	 * Disposes the IDnDHandler. It is the best method to get rid
	 * of the images created by the IDnDHandler during its lifecycle.
	 *
	 */
	public void dispose();
}
