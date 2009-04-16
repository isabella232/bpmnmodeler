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
 * Date			    Author			    Changes
 * Jul 19, 2006		hmalphettes			Created
 **/

package org.eclipse.stp.bpmn.figures.activities;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.figures.IPolygonAnchorableFigure;
import org.eclipse.stp.bpmn.figures.connectionanchors.DefaultSizeNodeFigureEx;
import org.eclipse.stp.bpmn.figures.connectionanchors.IConnectionAnchorFactory;
import org.eclipse.stp.bpmn.layouts.ActivityLayout;

/**
 * Manages connection anchors.
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ActivityNodeFigure extends DefaultSizeNodeFigureEx implements IPolygonAnchorableFigure{

    private final boolean _isSubProcessEventHandler;
    
    public ActivityNodeFigure(IConnectionAnchorFactory connectionAnchorFactory,
            int width, int height, boolean isSubProcessEventHandler) {
        super(width, height, connectionAnchorFactory);
        _isSubProcessEventHandler = isSubProcessEventHandler;
    }
    
    /**
     * @param result The rectanlge on which the bounds will be set.
     */
    public void computeAbsoluteHandleBounds(Rectangle result) {
        if (getLayoutManager() instanceof ActivityLayout) {
            ActivityLayout activityLayout = (ActivityLayout)getLayoutManager();
            if (_isSubProcessEventHandler) {
                //check the label inside:
                
            }
            IFigure fig = activityLayout.getOvalOrDiamondFigure();
            result.setBounds(fig.getBounds().getCopy().crop(fig.getBorder().getInsets(fig)));
            fig.translateToAbsolute(result);
        } else {
            super.computeAbsoluteHandleBounds(result);
        }

    }
    /**
     * Exclude activity name label from handle bounds, and take into account the border.
     */
    @Override
    public Rectangle getHandleBounds() {
        if (getLayoutManager() instanceof ActivityLayout) {
            ActivityLayout activityLayout = (ActivityLayout)getLayoutManager();
            if (_isSubProcessEventHandler) {
                //check the label inside:

            }
            IFigure fig = activityLayout.getOvalOrDiamondFigure();
            Rectangle res = fig.getBounds().getCopy().crop(fig.getBorder().getInsets(fig));
            fig.translateToParent(res);
            return res;
        } 
        for (Object child : getChildren()) {
            if (child instanceof ActivityFigure) {
                IFigure childF = (IFigure) child;
                return super.getHandleBounds().getCopy().crop(childF.getBorder().getInsets(childF));
            }
        }
        return super.getHandleBounds();
    }
    
    
    public PointList getPolygonPoints() {
        if (getLayoutManager() instanceof ActivityLayout) {
            ActivityLayout activityLayout = (ActivityLayout)getLayoutManager();
            IFigure fig = activityLayout.getOvalOrDiamondFigure();
            if (fig instanceof ActivityDiamondFigure) {
                PointList pl = new PointList();
                pl.addPoint(getHandleBounds().getTop());
                pl.addPoint(getHandleBounds().getLeft());
                pl.addPoint(getHandleBounds().getBottom());
                pl.addPoint(getHandleBounds().getRight());
                pl.addPoint(getHandleBounds().getTop());
                return pl;
            }
        } 
        PointList ptList = new PointList();
        ptList.addPoint(getHandleBounds().getTopLeft());
        ptList.addPoint(getHandleBounds().getTopRight());
        ptList.addPoint(getHandleBounds().getBottomRight());
        ptList.addPoint(getHandleBounds().getBottomLeft());
        ptList.addPoint(getHandleBounds().getTopLeft());
        return ptList;
    }
}
