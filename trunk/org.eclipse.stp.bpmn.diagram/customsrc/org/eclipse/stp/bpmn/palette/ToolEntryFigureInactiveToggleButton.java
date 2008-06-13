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

package org.eclipse.stp.bpmn.palette;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ToggleButton;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Derived from te InactiveToggleButton
 * found inside ToolEntryEditPart.
 * <p>
 * Support UI to display the fact that the tool-entry creates tools 
 * that will not unloaded when finished.
 * </p>
 * @author hmalphettes 
 */
class ToolEntryFigureInactiveToggleButton extends ToggleButton {
    /** true when put in sticky mode.
     * that is selected twice */
    public boolean isSticky = false;

    private final BpmnToolEntryEditPart _part;

    ToolEntryFigureInactiveToggleButton(BpmnToolEntryEditPart part, IFigure contents) {
        super(contents);
        _part = part;
        setOpaque(false);
        setEnabled(true);
    }
    public IFigure findMouseEventTargetAt(int x, int y) {
        return null;
    }
    public IFigure getToolTip() {
        return _part.createToolTip();
    }
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        if (isEnabled()) {
            setRolloverEnabled(true);
            setBorder(BpmnToolEntryEditPart.BORDER_TOGGLE);
            setForegroundColor(null);
        } else {
            setBorder(null);
            setRolloverEnabled(false);
            setForegroundColor(ColorConstants.gray);
        }
    }

    /**
     * @see org.eclipse.draw2d.Figure#paintFigure(Graphics)
     */
     protected void paintFigure(Graphics graphics) {
         if (isSelected() && isOpaque()) {
             fillCheckeredRectangle(graphics);
         } else {
//             super.setBackgroundColor(_part._backgroundColor);
//             graphics.fillRectangle(getBounds());
           super.paintFigure(graphics);
         }
     }

     /**
      * Draws a checkered pattern to emulate a toggle button that is in the selected state.
      * @param graphics  The Graphics object used to paint
      */
     protected void fillCheckeredRectangle(Graphics graphics) {
         Rectangle rect = getClientArea(Rectangle.SINGLETON).crop(new Insets(1, 1, 0, 0));
         if (isSticky) {//and if sticky mode draw an extra rectangle:
             graphics.setLineWidth(3);
             graphics.setForegroundColor(ColorConstants.black);
             graphics.drawRectangle(rect);
             graphics.setLineWidth(1);
         }

         graphics.setBackgroundColor(_part._backgroundColor);
         graphics.setForegroundColor(_part._foregroundColor);
         graphics.fillRectangle(rect.x, rect.y, rect.width, rect.height);

         graphics.clipRect(rect);
         graphics.translate(rect.x, rect.y);
         int n = rect.width + rect.height;
         if (isSticky) {
             
         }
         for (int i = 1; i < n; i += 2) {
             graphics.drawLine(0, i, i, 0);
         }
         
         graphics.restoreState();

     }


}