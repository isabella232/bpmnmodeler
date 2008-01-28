/******************************************************************************
 * Copyright (c) 2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************/

/**
 * Date             Author              Changes
 * Jan 04, 2008     hmalphettes         Created
 **/
package org.eclipse.stp.bpmn.figures;

import org.eclipse.draw2d.ScrollPane;
import org.eclipse.gmf.runtime.diagram.ui.figures.ShapeCompartmentFigure;
import org.eclipse.gmf.runtime.draw2d.ui.internal.figures.AnimatableScrollPane;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;

/**
 * 
 * Overrides the default figure to paint horizontal lines at the level of
 * the border between 2 lanes figure.
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class NotScrollableShapeCompartmentFigure extends ShapeCompartmentFigure {

    public NotScrollableShapeCompartmentFigure(String title, IMapMode mm) {
        super(title, mm);
    }
    
    /**
     * Creates the animatable scroll pane
     * Override to try prevent the scrollbar from appearing.
     */
    @Override
    protected AnimatableScrollPane createScrollpane(IMapMode mm) {
        AnimatableScrollPane res = super.createScrollpane(mm);
        res.setHorizontalScrollBarVisibility(ScrollPane.NEVER);
        res.setVerticalScrollBarVisibility(ScrollPane.NEVER);
        return res;
    }    


    
}
