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
 * Date             Author              Changes
 * Jan 08, 2007     hmalphettes         Created
 **/
package org.eclipse.stp.bpmn.tools;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;
import org.eclipse.gmf.runtime.gef.ui.internal.tools.SelectConnectionEditPartTracker;

/**
 * Makes sure the connection bendpoints are only moved, not created.
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class SelectConnectionEditPartTrackerEx extends SelectConnectionEditPartTracker {

    /** when true the bendpoints can only be moved on the vertical axis
     * when false they can only move on the vertical axis. */
    private boolean _isHorizontal;
    
    /**
     * Method SelectConnectionEditPartTracker.
     * @param owner ConnectionNodeEditPart that creates and owns the tracker object
     * @param isHorizontal only moves of bendpoints on the horizontal axis are allowed
     * when false only on the vertical axis.
     */
    public SelectConnectionEditPartTrackerEx(ConnectionEditPart owner, boolean isHorizontal) {
        super(owner);
        _isHorizontal = isHorizontal;
    }
    
    /* 
     * (non-Javadoc)
     * @see org.eclipse.gef.tools.AbstractTool#handleButtonDown(int)
     */
    protected boolean handleButtonDown(int button) {
        if (!super.handleButtonDown(button))
            return false;

        Point p = getLocation();
        getConnection().translateToRelative(p);
        
//        PointList points = getConnection().getPoints();
//        Dimension size = new Dimension(7, 7);
//        for (int i=1; i<points.size()-1; i++) {
//            Point ptCenter = points.getPoint(i);
//            Rectangle rect = new Rectangle( ptCenter.x - size.width / 2, ptCenter.y - size.height / 2, size.width, size.height);
//            
//            if (rect.contains(p)) {
//                setType(RequestConstants.REQ_MOVE_BENDPOINT);
//                setIndex(i);
//            }
//        }
        
        if (getIndex() == -1) {
            setIndex(PointListUtilities.findNearestLineSegIndexOfPoint(getConnection().getPoints(), new Point(p.x, p.y)));
    
//            setIndex(getIndex() - 1);
            setType(RequestConstants.REQ_MOVE_BENDPOINT);
        }
        
        return true;
    }


    /**
     * @return the <code>Connection</code> that is referenced by the connection edit part.
     */
    private Connection getConnection() {
        return (Connection) getConnectionEditPart().getFigure();
    }

    /**
     * Method getConnectionEditPart.
     * @return ConnectionEditPart
     */
    private ConnectionEditPart getConnectionEditPart() {
        return (ConnectionEditPart)getSourceEditPart();
    }

}
