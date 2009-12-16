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
 * Date             Author              Changes
 * Jul 12, 2006     hmalphettes         Created
 **/
package org.eclipse.stp.bpmn.figures;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.Activity2EditPart.ActivityFigure;
import org.eclipse.stp.bpmn.diagram.ui.OvalSchemeBorder;

/**
 * Have a fixed height, the 
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class SubProcessBorderFigure extends RectangleFigure {

    public int getBorderHeight() {
        IMapMode mm = MapModeUtil.getMapMode(this);
        int height = mm.LPtoDP(SubProcessEditPart.BORDER_HEIGHT);
        if (!hasChildren()) {
            return height/2;
        } else {
            int nameHeight = findNameHeight(this, 5); 
            int h = nameHeight + mm.LPtoDP(ActivityEditPart.EVENT_FIGURE_SIZE) + OvalSchemeBorder.INSETS.bottom;
            return h;
        }
    }

    
    @Override
    public Dimension getMaximumSize() {
        return new Dimension(super.getMaximumSize().width,
                getBorderHeight());
    }

    @Override
    public Dimension getMinimumSize(int wHint, int hHint) {
        int width = super.getMinimumSize(wHint, hHint).width;
        if (!hasChildren()) {
            width = 0;
        }
        return new Dimension(width,
                getBorderHeight());
    }

    @Override
    public void paintFigure(Graphics graphics) {
        //nothing: transparent
//graphics.drawRectangle(getBounds());
//graphics.setAlpha(120);
//graphics.setBackgroundColor(ColorConstants.red);
//graphics.fillRectangle(getBounds());
    }
    
    public boolean hasChildren() {
        return internalHasChildren(this);
    }
    
    private boolean internalHasChildren(IFigure fig) {
        for (Object f : fig.getChildren()) {
            if (f instanceof ActivityFigure) {
                return true;
            }
            if (internalHasChildren((IFigure) f)) {
                return true;
            }
        }
        return false;
    }
    
    private int findNameHeight(IFigure fig, int nameHeight) {
        // only take size into account if the string represents something else
        // from white spaces.
        if (fig instanceof Label) {
            if (!(((Label) fig).getText() == null || 
                    ("".equals(((Label) fig).getText().trim())))) { //$NON-NLS-1$
                nameHeight = Math.max(nameHeight, fig.getBounds().height);
            }
        }
        if (fig instanceof WrappingLabel) {
            if (!(((WrappingLabel) fig).getText() == null || 
                    ("".equals(((WrappingLabel) fig).getText().trim())))) { //$NON-NLS-1$
                nameHeight = Math.max(nameHeight, fig.getBounds().height);
            }
        }
        for (Object child : fig.getChildren()) {
            nameHeight = findNameHeight((IFigure) child, nameHeight);
        }
        return nameHeight;
    }
    
}
