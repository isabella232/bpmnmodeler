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
package org.eclipse.stp.bpmn.figures.splitviewsupport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.Map.Entry;

import org.eclipse.draw2d.FreeformLayeredPane;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.TreeSearch;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Translatable;
import org.eclipse.draw2d.text.CaretInfo;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.GuideLayer;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.LineSeg;

/**
 * A ViewPort that supports "skipped" vertical areas: areas that are not painted.
 * 
 * @author hmalphettes
 * @author Intalio Inc
 */
public class FreeFormViewPortWithSplitters extends FreeformViewport {
    
    /**
     * Visible areas defined in the coordinate of this viewport.
     */
    private List<Rectangle> _visibleAreas;
    
    public FreeFormViewPortWithSplitters() {
    }
    
    /**
     * We eventually remove from the client area the "skipped" zones.
     */
    @Override
    protected void paintClientArea(Graphics graphics) {
        if (_visibleAreas == null) {
            super.paintClientArea(graphics);
            return;
        }
        Rectangle oriBounds = new Rectangle(bounds);
        Point oriViewLocation = getViewLocation();
        //Rectangle r = new Rectangle(oriBounds);
        int prevRight = 0, sumDeltas = 0;
        for (Rectangle visibleArea : _visibleAreas) {
            /*
             * Compute the intersection of the visible areas with the passed bounds.
             * Other areas should not be painted.
             * The graphics object is then translated to paint continously.
             */
            if (oriBounds.intersects(visibleArea)) {
                Rectangle clientVisibleArea = new Rectangle(oriBounds).intersect(visibleArea);
                
                sumDeltas += clientVisibleArea.x-prevRight;
                prevRight = clientVisibleArea.x + clientVisibleArea.width;
                
                //graphics.clipRect(clientVisibleArea);
                
                if (getChildren().isEmpty())
                    return;
    
                graphics.clipRect(clientVisibleArea.translate(-sumDeltas, 0));
                
    //start code from IFigure with the extra translation
                graphics.translate(
                    getBounds().x + getInsets().left -2 - oriViewLocation.x - sumDeltas,
                    getBounds().y + getInsets().top - oriViewLocation.y);
                graphics.pushState();
                paintChildren(graphics);
                graphics.popState();
                graphics.restoreState();
    //end code from IFigure.
           }
        }
    }
    
    /**
     * Computes the visible areas from the guides set on the guides layer.
     */
    public void computeVisibleAreas() {
        FreeformLayeredPane content = (FreeformLayeredPane)getContents();
        GuideLayer guides = (GuideLayer) content.getLayer(LayerConstants.GUIDE_LAYER);
        
        if (guides.getConstraints().size() < 2) {
            _visibleAreas = null;
            return;
        }
        //now get the vertical guides:
        //order them:
        TreeSet<Integer> ordered = new TreeSet<Integer>();
        ordered.add(0);
        for (Object entry : guides.getConstraints().entrySet()) {
            Entry e = (Entry)entry;
            if (!((Boolean)e.getValue()).booleanValue()) {
                //only interested in vertial values for now.
                IFigure guide = (IFigure)e.getKey();
                ordered.add(guide.getBounds().x);
            }
        }
        if (ordered.size() < 2) {
            _visibleAreas = null;
            return;
        }
        List<Rectangle> areas = new ArrayList<Rectangle>();
        Iterator<Integer> it = ordered.iterator();
        int right = 0;
        while (it.hasNext()) {
            int left = it.next();
            right = it.hasNext() ? it.next() : 300000;
            if (!it.hasNext()) {
                right = 300000;
            }
            Rectangle r = new Rectangle(left, 0, right-left, 300000);
            areas.add(r);
            //System.err.println(left + " width=" + (right-left));
        }
        _visibleAreas = areas;
    }

    @Override
    public void setContents(IFigure figure) {
        FreeformLayeredPane pane = (FreeformLayeredPane)figure;
        pane.removeLayer(LayerConstants.GUIDE_LAYER);
        GuideLayerWithSplitters guide = new GuideLayerWithSplitters(this);
        pane.add(guide, LayerConstants.GUIDE_LAYER);
        super.setContents(figure);
    }

    public void translateFromParent(Translatable t) {
        if (_visibleAreas == null) {
            super.translateFromParent(t);
        } else {
            translateFromParent(t, false);
        }
    }

    /**
     * Overriding this method is necessary to add the extra translation
     * introduced by the splitters.
     * <p>
     * In fact at least in the case of the segments we need to change the 
     * source and target destination instead o doing a simple translation.
     * </p>
     */
    private void translateFromParent(Translatable t, boolean forMouse) {
        //add the sum of the clipped x
        if (_visibleAreas != null) {
            if (t instanceof Rectangle) {
                Rectangle r = (Rectangle)t;
                int right = r.x+r.width;
                right += getDxFromParent(right, forMouse);
                //r.x += getDxFromParent(r.x);
                r.performTranslate(getDxFromParent(r.x, forMouse), 0);
//                System.err.println(r.width + "->" + (right-r.x));
                r.setSize(right-r.x, r.height);
            } else if (t instanceof Point) {
                Point p = (Point)t;
                p.performTranslate(getDxFromParent(p.x, forMouse), 0);
            } else if (t instanceof LineSeg) {
                LineSeg ls = (LineSeg)t;
                Point ori = ls.getOrigin();
                ori.performTranslate(getDxFromParent(ori.x, forMouse), 0);
                Point terminus = ls.getTerminus();
                terminus.x += getDxFromParent(terminus.x, forMouse);
                ls.setTerminus(terminus);
            } else if (t instanceof CaretInfo) {
                CaretInfo ci = (CaretInfo)t;
                ci.performTranslate(getDxFromParent(ci.getX(), forMouse), 0);
            }
        }
        super.translateFromParent(t);
    }
    

    @Override
    protected IFigure findDescendantAtExcluding(int x, int y, TreeSearch search) {
        PRIVATE_POINT.setLocation(x, y);
        translateFromParent(PRIVATE_POINT, false);
        if (!getClientArea(Rectangle.SINGLETON).contains(PRIVATE_POINT))
            return null;

        x = PRIVATE_POINT.x;
        y = PRIVATE_POINT.y;
        IFigure fig;
        for (int i = getChildren().size(); i > 0;) {
            i--;
            fig = (IFigure)getChildren().get(i);
            if (fig.isVisible()) {
                fig = fig.findFigureAt(x, y, search);
                if (fig != null)
                    return fig;
            }
        }
        //No descendants were found
        return null;
    }

    private static final Point PRIVATE_POINT = new Point();
    @Override
    protected IFigure findMouseEventTargetInDescendantsAt(int x, int y) {
        PRIVATE_POINT.setLocation(x, y);
        translateFromParent(PRIVATE_POINT, true);

        if (!getClientArea(Rectangle.SINGLETON).contains(PRIVATE_POINT))
            return null;

        IFigure fig;
        for (int i = getChildren().size(); i > 0;) {
            i--;
            fig = (IFigure)getChildren().get(i);
            if (fig.isVisible() && fig.isEnabled()) {
                if (fig.containsPoint(PRIVATE_POINT.x, PRIVATE_POINT.y)) {
                    fig = fig.findMouseEventTargetAt(PRIVATE_POINT.x, PRIVATE_POINT.y);
                    return fig;
                }
            }
        }
        return null;
    }

    /**
     * This must be the exact mirror function of the
     * translateFromParent. Currently it is not.
     */
    @Override
    public void translateToParent(Translatable t) {
        super.translateToParent(t);
        //remove the sum of the clipped x
        if (_visibleAreas != null) {
            if (t instanceof Rectangle) {
                Rectangle r = (Rectangle)t;
                r.x -= getDxFromParent(r.x, true);
                r.width -= getDxFromParent(r.x+r.width, false)-r.x;
            } else if (t instanceof Point) {
                Point p = (Point)t;
                p.x -= getDxFromParent(p.x, true);
            } else if (t instanceof LineSeg) {
                LineSeg ls = (LineSeg)t;
                Point ori = ls.getOrigin();
                ori.x -= getDxFromParent(ori.x, true);
                Point terminus = ls.getTerminus();
                terminus.x -= getDxFromParent(terminus.x, true);
                ls.setTerminus(terminus);
            } else if (t instanceof CaretInfo) {
                CaretInfo ci = (CaretInfo)t;
                ci.performTranslate(-getDxFromParent(ci.getX(), true), 0);
            }
        }
    }
    
    /**
     * Unfortunately the Translatable interface does not specify a method to read x and y.
     * This method is in charge of hiding the ugliness.
     * 
     * @param t
     * @return
     */
    private static int getX(Translatable t) {
        if (t instanceof Rectangle) {
            return ((Rectangle)t).x;
        }
        if (t instanceof Point) {
            return ((Point)t).x;
        }
        if (t instanceof Point) {
            return ((Point)t).x;
        }
        if (t instanceof LineSeg) {
            return ((LineSeg)t).getOrigin().x;
        }
        if (t instanceof CaretInfo) {
            return ((CaretInfo)t).getX();
        }
        return -1;
    }
    
    /**
     * 
     * @param x
     * @param forMouse true when looking for the figure and it is a mouse
     * event. when false, the point if in an invisible zone should be translated to be at
     * the border of a visible zone.
     * @return
     */
    private int getDxFromParent(int x, boolean forMouse) {
        if (_visibleAreas != null) {
            if (x >= 0) {
                int sum = 0;
                int prevRight = 0;
                boolean dosumonnext = false;
                for (Rectangle r : _visibleAreas) {
                    if (x < r.x) {
                        if (dosumonnext) {
                            sum += r.x - prevRight;
                        }
                        break;
                    }
                    sum += r.x - prevRight;
                    prevRight = r.x + r.width;
                    dosumonnext = forMouse && x > prevRight;
                }
//                if (sum > 0) {
//                    System.err.println("adding " + sum + " to " + x);
//                }
                return sum;
            }
        }
        return 0;
    }
        
}
