/******************************************************************************
 * Copyright (c) 2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Date         Author             Changes
 * Apr 25, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.diagram.ui;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * This scheme border is made for diamond shaped figures, and shows
 * a border under it.
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class DiamondSchemeBorder extends RoundedSchemeBorder {

    public DiamondSchemeBorder() {
        super();
    }
    
    protected void fillShadow(IFigure fig, Graphics graphics, Insets insets) {
        //Rectangle rect = getPaintRectangle(fig, insets);

        Rectangle r = new Rectangle();
        PointList pointList = new PointList();

        r.x = fig.getBounds().x+3;
        r.y = fig.getBounds().y+2;
        r.width = fig.getBounds().width - 4;
        r.height = fig.getBounds().height - 4;
        pointList.removeAllPoints();
        pointList.addPoint(r.x + r.width / 2, r.y);
        pointList.addPoint(r.x + r.width, r.y + r.height / 2);
        pointList.addPoint(r.x + r.width / 2, r.y + r.height);
        pointList.addPoint(r.x, r.y + r.height / 2);
        
        graphics.fillPolygon(pointList);
    }
}
