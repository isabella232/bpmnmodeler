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
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;

/**
 * Transparent figure
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class SubProcessFigure extends RoundedRectangle {

    private boolean _isLoop;

    private boolean _isTransaction;

    public boolean isLoop() {
        return _isLoop;
    }

    public void setIsLoop(boolean isLoop) {
        _isLoop = isLoop;
    }

    public boolean isTransaction() {
        return _isTransaction;
    }

    public void setIsTransaction(boolean isTransaction) {
        _isTransaction = isTransaction;
    }

    public Rectangle getVisibleBounds() {
        Rectangle r = getBounds().getCopy();
        // if (super.getChildren().size() == 2) {
        // int borderCompartmentHeight =
        // ((IFigure) super.getChildren().get(1)).getBounds().height;
        // r.height = r.height - borderCompartmentHeight;
        // }
        
        if (getParent() != null && (getParent().getParent() != null)) {
            int borderHeight = 0;
            for (Object child : getChildren()) {
                if (child instanceof SubProcessBorderFigure) {
                    borderHeight = ((SubProcessBorderFigure) child).getBorderHeight();
                }
            }
        	r.height = r.height - borderHeight + ActivityEditPart.EVENT_FIGURE_SIZE/2 ;
        }
        return r;
    }

    /**
     * @see Shape#fillShape(Graphics)
     */
    protected void fillShape(Graphics graphics) {
        graphics.fillRoundRectangle(getVisibleBounds(), corner.width,
                corner.height);
    }

    /**
     * @see Shape#outlineShape(Graphics)
     */
    protected void outlineShape(Graphics graphics) {
        Rectangle f = Rectangle.SINGLETON;
        Rectangle r = getVisibleBounds();
        f.x = r.x + lineWidth / 2;
        f.y = r.y + lineWidth / 2;
        f.width = r.width - lineWidth;
        f.height = r.height - lineWidth;
        graphics.drawRoundRectangle(f, corner.width, corner.height);
    }
}
