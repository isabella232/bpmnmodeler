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
 * Dec 04, 2006     hmalphettes         Created
 **/

package org.eclipse.stp.bpmn.figures.activities;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;

/**
 * Just makes sure we can recognize easyly a lane figure.
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class LaneFigure extends DefaultSizeNodeFigure {

    Dimension MIN = new Dimension(16,48);
    
    public LaneFigure(int width, int height) {
        super(width, height);
    }

    @Override
    public Dimension getMinimumSize(int wHint, int hHint) {
        if (hHint < 48) {
            return MIN;
        }
        return super.getMinimumSize(wHint, hHint);
    }
    
    

}
