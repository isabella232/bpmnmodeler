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

import org.eclipse.draw2d.geometry.PointList;

/**
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class MessagePolylineTargetDecoration extends FilledPolylineDecoration {

    private static final PointList MSG_TRIANGLE_TIP = new PointList();
    static {
        MSG_TRIANGLE_TIP.addPoint(-1, 1);
        MSG_TRIANGLE_TIP.addPoint(0, 0);
        MSG_TRIANGLE_TIP.addPoint(-1, -1);
        MSG_TRIANGLE_TIP.addPoint(-1, 1);
    }
    
    public MessagePolylineTargetDecoration() {
        super();
        this.setFillXOR(true);
        this.setBackgroundColor(org.eclipse.draw2d.ColorConstants.white);
        this.setForegroundColor(org.eclipse.draw2d.ColorConstants.black);
        this.setTemplate(MSG_TRIANGLE_TIP);
    }
}
