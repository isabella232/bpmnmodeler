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

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.gmf.runtime.draw2d.ui.figures.OneLineBorder;
import org.eclipse.swt.graphics.Color;

/**
 * The OneLineBorder has a bug: it does not take the color passed into account.
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class FixedOneLineBorder extends OneLineBorder {
    public FixedOneLineBorder(Color color, int width, int position) {
        super(color, width, position);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.draw2d.ui.figures.OneLineBorder#paint(org.eclipse.draw2d.IFigure,
     *      org.eclipse.draw2d.Graphics, org.eclipse.draw2d.geometry.Insets)
     */
    @Override
    public void paint(IFigure figure, Graphics graphics, Insets insets) {
        graphics.pushState();
        try {
            graphics.setForegroundColor(getColor());
            super.paint(figure, graphics, insets);
        } finally {
            graphics.popState();
        }
    }
}
