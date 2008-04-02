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
 * 
 * Date         	Author             Changes 
 * 1 Sep 2006   	MPeleshchyshyn  	Created 
 */
package org.eclipse.stp.bpmn.figures.activities;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.stp.bpmn.figures.connectionanchors.IConnectionAnchorFactory;
import org.eclipse.stp.bpmn.figures.connectionanchors.NodeFigureEx;

/**
 * Oval node figure for event activities. Sets preferred size equal to size
 * specified in setBounds() method if current preferred size is
 * <code>null</code>. This allows to prevent figure resizing as user types
 * label text.
 * 
 * @author MPeleshchyshyn
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ActivityOvalFigure extends NodeFigureEx {
    
    public ActivityOvalFigure(IConnectionAnchorFactory connectionAnchorFactory) {
        super(connectionAnchorFactory);
    }

    /**
     * Sets the bounds of this Figure to the Rectangle <i>rect</i> and stores
     * these bounds as figure's preferred size when this method is called on
     * object for the forst time. Note that <i>rect</i> is compared to the
     * Figure's current bounds to determine what needs to be repainted and/or
     * exposed and if validation is required. Since {@link #getBounds()} may
     * return the current bounds by reference, it is not safe to modify that
     * Rectangle and then call setBounds() after making modifications. The
     * figure would assume that the bounds are unchanged, and no layout or paint
     * would occur. For proper behavior, always use a copy.
     * 
     * @param rect
     *            The new bounds
     */
    @Override
    public void setBounds(Rectangle rect) {
        Rectangle r = rect.getCopy();
        r.height = r.width;
        super.setBounds(r);
    }

    @Override
    protected void paintFigure(Graphics g) {
        //stay transparent
    }
    
    /**
     * @return Rectangle that bounds the oval that is to be drawn.
     * This may or may not be the same as the bounds of the figure itself.
     */
    public Rectangle getOvalBounds() {
        Rectangle r = getBounds();
        Rectangle ovalRect = new Rectangle(r);
        // avoid edge of the oval being chopped off:
//        ovalRect.setSize(r.width-1, r.height-1);
        return ovalRect;
    }

}
