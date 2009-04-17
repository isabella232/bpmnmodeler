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
 * Apr 26, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.figures;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gmf.runtime.draw2d.ui.figures.IPolygonAnchorableFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;

/**
 * Implements the IPolygonAnchorableFigure interface
 * to be able to place the anchors right.
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class DataObjectNodeFigure extends DefaultSizeNodeFigure implements IPolygonAnchorableFigure {

    public DataObjectNodeFigure(int width, int height) {
        super(width, height);
    }

    public PointList getPolygonPoints() {
        for (Object child : getChildren()) {
            if (child instanceof DataObjectFigure) {
                int fold = ((DataObjectFigure) child).getFoldSize();
                PointList pl = new PointList();
                pl.addPoint(getHandleBounds().getTopLeft());
                pl.addPoint(getHandleBounds().getTopRight().getCopy().translate(-fold, 0));
                pl.addPoint(getHandleBounds().getTopRight().getCopy().translate(0, fold));
                pl.addPoint(getHandleBounds().getBottomRight());
                pl.addPoint(getHandleBounds().getBottomLeft());
                pl.addPoint(getHandleBounds().getTopLeft());
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
