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
import org.eclipse.gef.handles.HandleBounds;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;

/**
 * Transparent figure
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class SubProcessFigure extends RoundedRectangle
implements HandleBounds {

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

    /**
     * Returns the Rectangle around which handles are to be placed.  The Rectangle should be
     * in the same coordinate system as the figure itself.
     * @return The rectangle used for handles
     */
    public Rectangle getHandleBounds() {
        Rectangle r = getBounds().getCopy();        
        if (getParent() != null && (getParent().getParent() != null)) {
            int borderHeight = 0;
            for (Object child : getChildren()) {
                if (child instanceof SubProcessBorderFigure) {
                    borderHeight = ((SubProcessBorderFigure) child).getBorderHeight();
                    break;
                }
            }
            if (borderHeight != 0) {
                //r.height = (int) Math.floor(r.height - borderHeight/2);
                //the labels of the event shapes on the border might make the border bigger than one might think.
                r.height = r.height - borderHeight + ActivityEditPart.EVENT_FIGURE_SIZE/2;
            }
        }
//        System.err.println("current height=" + getBounds().height);
        return r;
    }

    /**
     * @see Shape#fillShape(Graphics)
     */
    protected void fillShape(Graphics graphics) {
        graphics.fillRoundRectangle(getHandleBounds(), corner.width,
                corner.height);
    }

    /**
     * @see Shape#outlineShape(Graphics)
     */
    protected void outlineShape(Graphics graphics) {
        Rectangle f = Rectangle.SINGLETON;
        Rectangle r = getHandleBounds();
        f.x = r.x + lineWidth / 2;
        f.y = r.y + lineWidth / 2;
        f.width = r.width - lineWidth;
        f.height = r.height - lineWidth;
        graphics.drawRoundRectangle(f, corner.width, corner.height);
    }
}
