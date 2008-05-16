/*
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *
 * Date         Author             Changes
 * Jun 11, 2007      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.layouts;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.gef.ui.figures.WrapperNodeFigure;
import org.eclipse.stp.gmf.runtime.draw2d.ui.figures.WrappingLabel;


/**
 * A special layout to apply on activities when
 * they are events or gateways, so that the label
 * shows up under the shape.
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class ActivityLayout implements LayoutManager {

    private static final int EVENT_GATEWAYS_LABEL_MIN_WIDTH = 90;
    
    public Dimension getMinimumSize(IFigure container, int wHint, int hHint) {
        return getPreferredSize(container, wHint, hHint);
    }
    
    /**
     * Returns the preferred size of the given figure, using width and height hints.  If the
     * preferred size is cached, that size  is returned.  Otherwise, {@link
     * #calculatePreferredSize(IFigure, int, int)} is called.
     * @param container The figure
     * @param wHint The width hint
     * @param hHint The height hint
     * @return The preferred size
     */
    public Dimension getPreferredSize(IFigure container, int wHint, int hHint) {
         return calculatePreferredSize(container, wHint, hHint);
    }
    /**
     * Constant to be used as a constraint for child figures
     */
    public static final Integer CENTER = new Integer(PositionConstants.CENTER);
    /**
     * Constant to be used as a constraint for child figures
     */
    public static final Integer BOTTOM = new Integer(PositionConstants.BOTTOM);

    private IFigure center;
    private WrappingLabel bottom;
    private int vGap = 0, hGap = 0;

    /**
     * calculate the preferred size of the task and returns it
     */
    protected Dimension calculatePreferredSize(IFigure container, int wHint, int hHint) {
        
        Dimension prefSize = new Dimension();

        if (center != null && center.isVisible()) {
            prefSize = center.getPreferredSize(wHint, wHint);
        }
        
        if (prefSize.width < EVENT_GATEWAYS_LABEL_MIN_WIDTH &&
                bottom != null && bottom.isVisible() && bottom instanceof WrappingLabel) {
            //check that the width of the label is inferior to the
            //width of the preferred size otherwise
            //use the minimum width.
            Dimension prefLabel = ((WrappingLabel)bottom).getPreferredSize(wHint, hHint);
            if (prefLabel.width > EVENT_GATEWAYS_LABEL_MIN_WIDTH) {
                prefSize.width = EVENT_GATEWAYS_LABEL_MIN_WIDTH;
            }
        }
        
        return prefSize;
    }

    /**
     * layout the node, and resizes the container
     * by adding the label.
     */
    public void layout(IFigure container) {
        Rectangle area = container.getClientArea().getCopy();
        if (bottom.getSize().height == 0) {
            area.height = Math.min(area.height, area.width);
            area.width = area.height;
        }
        int squaresize =  0; // the size of the square formed by the event
        // shape or the gateway shape
//start unexpected computation: for an event shape: the container is the size of the center figure.
        int bottomHeight = bottom.getPreferredSize().height;
        if (bottomHeight > 0) {
            squaresize = area.height - bottomHeight;
        } else {
//end of unexpected computation.
            squaresize = area.width;
        }
        // now check that the center's minimum size is respected.
        int centerMinWidth = center.getMinimumSize().width;
        if (centerMinWidth > squaresize) {
            squaresize = centerMinWidth;
        }
        
        Rectangle rect = new Rectangle();
        Dimension childSize;
        if (center != null && center.isVisible()) {
            if (area.width < 0)
                area.width = 0;
            if (area.height < 0)
                area.height = 0;
            
            childSize = new Dimension(squaresize, squaresize);
            
            rect.setLocation(area.x, area.y);
            rect.setSize(childSize);
            center.setBounds(rect);
            area.height -= rect.height + vGap;
            area.y += rect.height + vGap;
        }
        if (bottom != null && bottom.isVisible()) {
            if (bottom instanceof WrappingLabel) {
                if (area.height <= 0) {
                    area.height = FigureUtilities.getFontMetrics(
                            ((WrappingLabel) bottom).getFont()).getHeight() * 2;
                }
            }
            // calculate the preferred size
            if (bottom.getText() != null 
                    && !"".equals(bottom.getText())) { //$NON-NLS-1$
                childSize = bottom.getPreferredSize();
                // because the height of the font is strangely clipped
                // with the 'g' letter for example, we add 4 pixels.
                childSize.height += 4; // maybe should multiply by zoom, hard to catch it.
            } else {
                childSize = Dimension.SINGLETON.getCopy();
            }
            /*
            childSize = FigureUtilities.getTextExtents(((WrappingLabel) bottom).getText(), 
                    ((WrappingLabel) bottom).getFont());
                        
            // change the height to have the whole text on screen
            Font f = bottom.getFont();
            String s = ((WrappingLabel) bottom).getText();
            Dimension d = FigureUtilities.getTextExtents(s, f);
            IMapMode mapMode = MapModeUtil.getMapMode(bottom);
            int fontHeight = mapMode.DPtoLP(FigureUtilities.getFontMetrics(f).getHeight());
            d.width = mapMode.DPtoLP(d.width);
            childSize.height = childSize.width != 0 ? 
                    fontHeight * (d.width / childSize.width) : 0;
            // for now we add all the \n lines, it would be good to 
            // be more intelligent here.
            // the thing is, since people type \n's because they cannot
            // resize themselves, they should not have the need to do it
            // anymore.
            childSize.height += fontHeight * new StringTokenizer(s, "\n").countTokens();//$NON-NLS-1$
            // because the height of the font is strangely clipped
            // with the 'g' letter for example, we add 5 pixels.
            childSize.height += mapMode.DPtoLP(5);
            */
            rect.setSize(childSize);
            rect.setLocation(area.x, area.y);
            bottom.setBounds(rect);
            area.height -= rect.height + vGap;
        }

        // now we have the definitive size of the bottom
        // so we can offset the center or the bottom to be centered,
        // depending on which one is bigger.
        if (bottom.getSize().height != 0) {
            int x = (bottom.getSize().width - squaresize)/2;// centered.
            if (x < 0) {
                bottom.setLocation(bottom.getBounds().getLocation().translate(-x, 0));
            } else {
                center.setLocation(center.getBounds().getLocation().translate(x, 0));
            }
        }
        // experimental code to deal with the label pushing to the right
        // not the right way to deal with it anymore:
        // now we just set the location of the bottom according to the center.location
        
//        if (squaresize < bottom.getSize().width) {
//            container.getBounds().x = center.getBounds().x - squaresize/2;
//            bottom.getBounds().x = container.getClientArea().x;
//            center.getBounds().x = bottom.getBounds().x - (squaresize - bottom.getSize().width)/2;
//        } else {
//            container.getBounds().x = bottom.getLocation().x - bottom.getBounds().width/2;
//            center.getBounds().x = area.x;
//            bottom.getBounds().x = center.getBounds().x + (squaresize - bottom.getSize().width)/2;
//        }
////        center.getBounds().y = area.y;
//        System.err.println(bottom.getLocation().x + " " + center.getBounds().x + " " + squaresize + " " +bottom.getSize().width); //REMOVE
        
        
        Dimension b = new Dimension();
        b.height = center.getSize().height + bottom.getSize().height;
        // the width of the total bounds is the max of the width
        b.width = Math.max(bottom.getSize().width, center.getSize().width);
       
        container.setSize(b.getCopy());
        if (container.getParent() instanceof WrapperNodeFigure) {
          container.getParent().setSize(b.getCopy());
        }
        
    }

    /**
     * @see org.eclipse.draw2d.AbstractLayout#remove(IFigure)
     */
    public void remove(IFigure child) {
        if (center == child) {
            center = null;
        } else if (bottom == child) {
            bottom = null;
        }
    }

    /**
     * Sets the location of the given child in this layout.  Valid constraints:
     * <UL>
     *      <LI>{@link #CENTER}</LI>
     *      <LI>{@link #TOP}</LI>
     *      <LI>{@link #BOTTOM}</LI>
     *      <LI>{@link #LEFT}</LI>
     *      <LI>{@link #RIGHT}</LI>
     *      <LI><code>null</code> (to remove a child's constraint)</LI>
     * </UL>
     * 
     * <p>
     * Ensure that the given Figure is indeed a child of the Figure on which this layout has
     * been set.  Proper behaviour cannot be guaranteed if that is not the case.  Also ensure
     * that every child has a valid constraint.  
     * </p>
     * <p> 
     * Passing a <code>null</code> constraint will invoke {@link #remove(IFigure)}.
     * </p>
     * <p> 
     * If the given child was assigned another constraint earlier, it will be re-assigned to
     * the new constraint.  If there is another child with the given constraint, it will be
     * over-ridden so that the given child now has that constraint.
     * </p>
     * 
     * @see org.eclipse.draw2d.AbstractLayout#setConstraint(IFigure, Object)
     */
    public void setConstraint(IFigure child, Object constraint) {
        remove(child);
        if (constraint == null) {
            return;
        }
        switch (((Integer) constraint).intValue()) {
            case PositionConstants.CENTER :
                center = child;
                break;
            case PositionConstants.BOTTOM :
                bottom = (WrappingLabel)child;
                break;
            default :
                break;
        }
    }

    /**
     * Sets the horizontal spacing to be used between the children.  Default is 0.
     * 
     * @param gap The horizonal spacing
     */
    public void setHorizontalSpacing(int gap) {
        hGap = gap;
    }

    /**
     * Sets the vertical spacing to be used between the children.  Default is 0.
     * 
     * @param gap The vertical spacing
     */
    public void setVerticalSpacing(int gap) {
        vGap = gap;
    }

    public Object getConstraint(IFigure child) {
        return null;
    }

    public void invalidate() {
        
    }
    
    public IFigure getOvalOrDiamondFigure() {
        return center;
    }
    public WrappingLabel getLabelFigure() {
        return bottom;
    }

}
