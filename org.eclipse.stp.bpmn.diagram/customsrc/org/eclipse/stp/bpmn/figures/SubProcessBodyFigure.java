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
import org.eclipse.draw2d.RectangleFigure;

/**
 * More to come.
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class SubProcessBodyFigure extends RectangleFigure {

    public SubProcessBodyFigure() {
        // super.setBorder(null);
        // new FixedOneLineBorder(ColorConstants.black, 1,
        // PositionConstants.BOTTOM));
    }

    public void paintFigure(Graphics graphics) {
        // transparent
//graphics.drawRectangle(getClientArea());
//graphics.setAlpha(120);
//graphics.setBackgroundColor(ColorConstants.blue);
//graphics.fillRectangle(getBounds().getCopy().crop(SubProcessEditPart.INSETS));
    }

}
