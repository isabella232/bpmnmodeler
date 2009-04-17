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
 *
 * Date         Author             Changes
 * Apr 25, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.diagram.ui;

import org.eclipse.draw2d.AbstractBorder;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.stp.bpmn.figures.SubProcessFigure;
import org.eclipse.swt.graphics.Color;

/**
 * Scheme border for rounded rectangle shapes.
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class RoundedSchemeBorder extends AbstractBorder {
    
    public static final Insets INSETS = new Insets(0, 0, 4, 4);

        
    public void paint(IFigure fig, Graphics graphics, Insets insets) {
        int alpha = BpmnDiagramEditorPlugin.getInstance().getPreferenceStore().
            getInt(BpmnDiagramPreferenceInitializer.PREF_SHOW_SHADOWS_TRANSPARENCY);
        if (alpha <= 0) {
            return;
        }
        Color c = graphics.getBackgroundColor();
        int oriAlpha = graphics.getAlpha();
        graphics.setAlpha(alpha);
        graphics.setBackgroundColor(ColorConstants.black);

        fillShadow(fig, graphics, insets);
        
        graphics.setAlpha(oriAlpha);
        graphics.setBackgroundColor(c);
        return;

    }
    
    protected void fillShadow(IFigure fig, Graphics graphics, Insets insets) {
        Rectangle rect = null;
        if (fig instanceof SubProcessFigure) {
            rect = ((SubProcessFigure) fig).getHandleBounds().getCopy().translate(3, 3);
        } else {
            rect = fig.getBounds().getCopy().translate(3, 3)
                    .resize(-4, -4);//getPaintRectangle(fig, insets);
        }
        graphics.fillRoundRectangle(rect, 7, 7);
    }
    
    public Insets getInsets(IFigure figure) {
        return INSETS;
    }
    
}
