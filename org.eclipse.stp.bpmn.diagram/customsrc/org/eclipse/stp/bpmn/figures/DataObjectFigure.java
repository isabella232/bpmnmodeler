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

/** Date         	Author             Changes 
 * 6 August 2006   	MPeleshchyshyn  	Created 
 */
package org.eclipse.stp.bpmn.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Draws a BPMN-Data Object figure: mostly a rectangle with a triangle 
 * in one of its corners.
 * 
 * @author hmalphettes
 * @author Intalio Inc
 */
public class DataObjectFigure extends RectangleFigure {
    
    private int _foldSize;
    
    public DataObjectFigure(int foldSize) {
        if (_foldSize < 0) {
            _foldSize = 16;//some kind of default.
        }
        _foldSize = foldSize;
    }

	protected void dopaint(Graphics graphics, boolean isOutline) {
		
		graphics.setForegroundColor(ColorConstants.black);
		//graphics.setLineWidth(1);
		Rectangle r = getBounds();
		int x = r.x + lineWidth / 2;
		int y = r.y + lineWidth / 2;
		int w = r.width - Math.max(1, lineWidth);
		int h = r.height - Math.max(1, lineWidth);
		
		int d = Math.min(w / 2, h / 2);
		d = Math.min(_foldSize, d);
		
		/*
		1       6
		--------|\
		|       | \
		|      5|--\4
		|          |
		|          |
		|          |
		------------
		2          3
		*/
		
		Point p1 = new Point(x, y);
		Point p2 = new Point(x, y+h);
		Point p3 = new Point(x+w , p2.y);
		Point p4 = new Point(x+w, y); p4.y = p4.y + d;
		Point p5 = new Point(p4.x-d, p4.y);
		Point p6 = new Point(p5.x, p1.y);
		
		PointList pl = new PointList();
		pl.addPoint(p1);
		pl.addPoint(p2);
		pl.addPoint(p3);
		pl.addPoint(p4);
		if (isOutline) {
			pl.addPoint(p5);
		}
		pl.addPoint(p6);
		if (isOutline) {
			pl.addPoint(p1);
		}
		
		if (isOutline) {
			graphics.drawPolyline(pl);
			graphics.drawLine(p6, p4);
		} else {
			graphics.fillPolygon(pl);
		}
		
	}

	/**
	 * @see Shape#fillShape(Graphics)
	 */
	protected void fillShape(Graphics graphics) {
		dopaint(graphics, false);
	}
	/**
	 * @see Shape#outlineShape(Graphics)
	 */
	protected void outlineShape(Graphics graphics) {
		dopaint(graphics, true);
	}
}
