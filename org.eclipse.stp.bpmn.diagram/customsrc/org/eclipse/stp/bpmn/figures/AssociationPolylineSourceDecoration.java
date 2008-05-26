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
 * Jul 12, 2006     hmalphettes         Created
 **/

package org.eclipse.stp.bpmn.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.stp.bpmn.DirectionType;

/**
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class AssociationPolylineSourceDecoration extends PolylineDecoration {

    private int _directionType;
    
    public AssociationPolylineSourceDecoration(int directionType) {
        _directionType = directionType;
    }
    
    public void updateDirectionType(int directionType) {
        _directionType = directionType;
        this.invalidate();
    }
    
    @Override
    protected void outlineShape(Graphics g) {
        switch (_directionType) {
        case DirectionType.NONE:
        case DirectionType.FROM:
            return;
        }
        g.setForegroundColor(ColorConstants.black);
        super.outlineShape(g);
    }

}
