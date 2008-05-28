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
 * Date           	Author              Changes 
 * 19 Sep 2006   	MPeleshchyshyn  	Created 
 **/

package org.eclipse.stp.bpmn.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.gmf.runtime.diagram.ui.internal.figures.CollapseFigure;

/**
 * This figure does nothing. Collapse handle is painted instantly inside
 * SuBprocessEditPart.SubProcessFigure. This one is only used as stub inside
 * CompartmentCollapseHandlerEx.
 * 
 * @author mpeleshchyshyn
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class CollapseFigureEx extends CollapseFigure {
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.diagram.ui.internal.figures.CollapseFigure#fillShape(org.eclipse.draw2d.Graphics)
     */
    @Override
    protected void fillShape(Graphics graphics) {
        // just an empty figure - used only to handle mouse interactions handle
        // is painted instantly inside SubProcessEditPart.SubProcessFigure
    }
}
