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
 * May 26, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.dnd;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;

/**
 * 
 * The IDnDHandler2 gives more choices to the implementor.
 * 
 * The implementor may now choose the priority of the handler depending
 * on the part, as well as the number of menu items.
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public interface IDnDHandler2 extends IDnDHandler {

    /**
     * 
     * @return a number representing the priority of the handler.
     * The handlers are sorted by priority prior being shown
     * in the pop-up menu, the elements which have the strongest
     * priority are shown on the top of the menu.
     */
    public int getPriority(IGraphicalEditPart hoverPart);
    
    /**
     * 
     * @return the number of items that should be shown in 
     * the pop-up menu representing this handler.
     * Each item will represent a different choice.
     */
    public int getItemCount(IGraphicalEditPart hoverPart);
}
