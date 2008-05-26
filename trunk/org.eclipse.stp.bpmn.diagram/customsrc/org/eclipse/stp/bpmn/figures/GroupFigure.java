/*
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 */
package org.eclipse.stp.bpmn.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.swt.SWT;

/**
 * The group figure is represented as a box around tasks that are part of it.
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class GroupFigure extends RoundedRectangle {

    public GroupFigure() {
        setOpaque(false);
    }
    @Override
    protected void fillShape(Graphics graphics) {
        
        if (getBackgroundColor() != null && 
                !getBackgroundColor().equals(ColorConstants.white)) { // the default color is white
            graphics.pushState();
            graphics.setBackgroundColor(getBackgroundColor());
            graphics.setAlpha(96);
            graphics.setForegroundColor(getForegroundColor());
            graphics.fillRectangle(getClientArea());
            graphics.popState();
        }
    }

    @Override
    protected void outlineShape(Graphics graphics) {
        graphics.setForegroundColor(getForegroundColor());
        graphics.setLineStyle(SWT.LINE_CUSTOM);
        graphics.setLineDash(new int[] {1, 3, 10, 3});
        super.outlineShape(graphics);
        
    }
    
    
    
}
