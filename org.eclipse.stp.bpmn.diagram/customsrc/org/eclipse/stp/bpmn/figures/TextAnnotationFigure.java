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
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.IPolygonAnchorableFigure;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;

public class TextAnnotationFigure extends DefaultSizeNodeFigure implements IPolygonAnchorableFigure {
    
    /**
     * Constructor
     * 
     * @param width <code>int</code> value that is the default width in logical units
     * @param height <code>int</code> value that is the default height in logical units
     * @param insets <code>Insets</code> that is the empty margin inside the note figure in logical units
     */
    public TextAnnotationFigure(int width, int height, Insets insets) {
        super(width, height);
        setBorder(
                new MarginBorder(insets.top, insets.left, insets.bottom, insets.right));
        ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
        layout.setMinorAlignment(ConstrainedToolbarLayout.ALIGN_TOPLEFT);
        layout.setSpacing(insets.top);
        setLayoutManager(layout);
    }

    /**
     * Draw a polyline
     * looking like a C, with square angles,
     * then trying to set a background color for the rest of the shape.
     */
    protected void paintFigure(Graphics graphics) {
        IMapMode mm = MapModeUtil.getMapMode(this);
        int lineWidth = mm.LPtoDP(1);
        graphics.setForegroundColor(ColorConstants.black);
        
        graphics.setLineWidth(lineWidth);
        Rectangle rect = getBounds().getCopy();
        rect.height--;
        rect.width = Math.min(rect.width, mm.LPtoDP(30));
        PointList pl = new PointList();
        pl.addPoint(rect.getTop());
        pl.addPoint(rect.getTopLeft());
        pl.addPoint(rect.getBottomLeft());
        pl.addPoint(rect.getBottom());
        graphics.drawPolyline(pl);
        
        // fill the shape
//        graphics.setForegroundColor(getForegroundColor());
//        graphics.setBackgroundColor(getBackgroundColor());
//        Rectangle toFill = getBounds().getCopy();
//        toFill.height -= lineWidth * 2;
//        toFill.width -= lineWidth;
//        toFill.x += lineWidth;
//        toFill.y += lineWidth;

//        graphics.fillGradient(toFill, false);
    }

    /**
     * @see org.eclipse.draw2d.IFigure#getPreferredSize(int, int)
     */
    public Dimension getPreferredSize(int wHint, int hHint) {
        IMapMode mm = MapModeUtil.getMapMode(this);
        return super.getPreferredSize(wHint, hHint).getUnioned(new Dimension(
                                mm.DPtoLP(50), 
                                mm.DPtoLP(30)));
    }

    public PointList getPolygonPoints() {
        Rectangle rect = getBounds().getCopy();
        rect.height--;
        rect.width = Math.min(rect.width, 30);
        PointList pl = new PointList();
        pl.addPoint(rect.getTop());
        pl.addPoint(rect.getTopLeft());
        pl.addPoint(rect.getBottomLeft());
        pl.addPoint(rect.getBottom());
        return pl;
    }

}