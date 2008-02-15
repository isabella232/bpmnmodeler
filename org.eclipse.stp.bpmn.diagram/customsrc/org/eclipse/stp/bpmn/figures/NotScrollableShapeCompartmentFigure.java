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

import java.util.Iterator;

import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemsAwareFreeFormLayer;
import org.eclipse.gmf.runtime.diagram.ui.figures.ShapeCompartmentFigure;
import org.eclipse.gmf.runtime.draw2d.ui.internal.figures.AnimatableScrollPane;
import org.eclipse.gmf.runtime.draw2d.ui.internal.figures.OverlayScrollPaneLayout;
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
        super.scrollPane = new AnimatableScrollPane() {

            @Override
            public void scrollHorizontalTo(int x) {
                super.scrollHorizontalTo(0);
            }

            @Override
            public void scrollVerticalTo(int y) {
                super.scrollVerticalTo(0);
            }
            
        };
        AnimatableScrollPane res = super.createScrollpane(mm);
        res.setHorizontalScrollBarVisibility(ScrollPane.NEVER);
        res.setVerticalScrollBarVisibility(ScrollPane.NEVER);
        return res;
    }    
    private static final Point ZERO = new Point(0,0);
/*    protected void configureFigure(IMapMode mm) {
        super.configureFigure(mm);
        super.getScrollPane().setViewport(new FreeformViewport() {

            @Override
            public Point getViewLocation() {
                return ZERO;
            }

            @Override
            public void setViewLocation(int x, int y) {
                super.setViewLocation(0, 0);
            }
            
        });
    }    */
/*
    protected void configureFigure(IMapMode mm) {
        ScrollPane scrollpane = getScrollPane();
        if(scrollpane==null){
            scrollpane = scrollPane = new AnimatableScrollPane();
        }

        scrollPane.setScrollBarVisibility(ScrollPane.AUTOMATIC);
        scrollpane.setLayoutManager(new OverlayScrollPaneLayout() );

        IFigure contents = new BorderItemsAwareFreeFormLayer();
        contents.setLayoutManager(new FreeFormLayoutExCopied());
        scrollpane.setContents(contents);
        
        int MB = mm.DPtoLP(5);
        scrollpane.setBorder(new MarginBorder(MB, MB, MB, MB));
        int SZ = mm.DPtoLP(10);
        scrollpane.setMinimumSize(new Dimension(SZ, SZ));
    
        this.setFont(FONT_TITLE);
    }    
*/

    
}
class FreeFormLayoutExCopied extends FreeformLayout {

    public void layout(IFigure parent) {
        Iterator children = parent.getChildren().iterator();
        Point offset = getOrigin(parent);
        IFigure f;
        while (children.hasNext()) {
            f = (IFigure)children.next();
            Rectangle bounds = (Rectangle)getConstraint(f);
            if (bounds == null) continue;

            if (bounds.width == -1 || bounds.height == -1) {
                Dimension _preferredSize = f.getPreferredSize(bounds.width, bounds.height);
                bounds = bounds.getCopy();
                if (bounds.width == -1)
                    bounds.width = _preferredSize.width;
                if (bounds.height == -1)
                    bounds.height = _preferredSize.height;
            }
            Dimension min = f.getMinimumSize();
            Dimension max = f.getMaximumSize();

            if (min.width>bounds.width)
                bounds.width = min.width;
            else if (max.width < bounds.width)
                bounds.width = max.width;

            if (min.height>bounds.height)
                bounds.height = min.height;
            else if (max.height < bounds.height)
                bounds.height = max.height;
            bounds = bounds.getTranslated(offset);
            f.setBounds(bounds);
        }
    }
}
