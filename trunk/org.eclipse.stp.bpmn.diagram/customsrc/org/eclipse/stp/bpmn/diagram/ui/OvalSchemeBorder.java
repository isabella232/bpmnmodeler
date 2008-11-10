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
 * Apr 25, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.diagram.ui;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * This scheme border shows a border under the oval shape.
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class OvalSchemeBorder extends RoundedSchemeBorder {

    @Override
    protected void fillShadow(IFigure fig, Graphics graphics, Insets insets) {
        Rectangle rect = fig.getBounds().getCopy().translate(2, 2)
                    .resize(-4, -4);//getPaintRectangle(fig, insets);
//        if (fig instanceof SubProcessFigure) {
//            rect = ((SubProcessFigure) fig).getHandleBounds().getCopy().expand(getInsets(fig));
//        } else {
//            rect = fig.getBounds().getCopy().crop(insets);
//        }

        graphics.fillOval(rect.getCopy().crop(insets));
    }
}
