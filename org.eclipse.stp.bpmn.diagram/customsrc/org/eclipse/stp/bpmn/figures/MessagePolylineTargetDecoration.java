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
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.stp.bpmn.figures.activities.ActivityPainter;

/**
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class MessagePolylineTargetDecoration extends FilledPolylineDecoration {

    private static final PointList MSG_TRIANGLE_TIP_ROUND_STYLE = new PointList();
    private static final PointList MSG_TRIANGLE_TIP_TASK_STYLE = new PointList();
    private static double scaleX, scaleY;
    static {
  //this decoration ends exactly on the recangle bounds of the shape it connects to.
        MSG_TRIANGLE_TIP_TASK_STYLE.addPoint(-3, 2);
        MSG_TRIANGLE_TIP_TASK_STYLE.addPoint(0, 0);
        MSG_TRIANGLE_TIP_TASK_STYLE.addPoint(-3, -2);
        MSG_TRIANGLE_TIP_TASK_STYLE.addPoint(-3, 2);
        scaleX = 2.0;
        scaleY = 2.0;
        
  //this decoration is at the same level that the source decoration.
        //this is centered at the end of the connection. scale is 2x
        MSG_TRIANGLE_TIP_ROUND_STYLE.addPoint(-2, 2);
        MSG_TRIANGLE_TIP_ROUND_STYLE.addPoint(1, 0);
        MSG_TRIANGLE_TIP_ROUND_STYLE.addPoint(-2, -2);
        MSG_TRIANGLE_TIP_ROUND_STYLE.addPoint(-2, 2);
        //push even further towards the end:
        MSG_TRIANGLE_TIP_ROUND_STYLE.translate(1, 0);
    }
    
    private boolean _styleIsRound;
    private boolean _currTplIsRound;
    
    /**
     * @param isRoundStyle true for round-style: the center of the arrow head 
     * is on the border of the shape it connects to. Works well for round shapes.
     * when false, the tip of the arrow is on the border of the shape is connects
     * to. Works well for rectangular shape. Matches the screen-shot of the BPMN spec
     */
    public MessagePolylineTargetDecoration(boolean isRoundStyle) {
        super();
        this.setFillXOR(true);
        this.setBackgroundColor(org.eclipse.draw2d.ColorConstants.white);
        this.setForegroundColor(org.eclipse.draw2d.ColorConstants.black);
        this.setScale(scaleX, scaleY);
        _styleIsRound = isRoundStyle;
        _currTplIsRound = !_styleIsRound;//force an update of the template and scale
        updateStyle();
    }
    
    public void setStyle(boolean isRoundStyle) {
        _styleIsRound = isRoundStyle;
        updateStyle();
    }
    /**
     * Update the template and scale if there is change in the style.
     */
    private void updateStyle() {
        if (_styleIsRound != _currTplIsRound) {
            if (_styleIsRound) {
                this.setTemplate(MSG_TRIANGLE_TIP_ROUND_STYLE);
            } else {
                this.setTemplate(MSG_TRIANGLE_TIP_TASK_STYLE);
//                this.setScale(scaleXTaskStyle, scaleYTaskStyle);
            }
        }
    }
    /**
     * Overrides so we keep track of the type of template set.
     */
    @Override
    public void setTemplate(PointList pl) {
        _currTplIsRound = pl == MSG_TRIANGLE_TIP_ROUND_STYLE;
        super.setTemplate(pl);
    }
    
    //uncomment and comment outlineshape to apply the transparency to
    //both the outline and the fill.
    //when commented and outline shape is no commented,
    //the transparency applies only to the outline.
//    @Override
//    public void paintFigure(Graphics graphics) {
//        graphics.setAlpha(ActivityPainter.getMessagingEdgeTransparency());
//        super.paintFigure(graphics);
//    }
    
    /**
     * Overrides to set the alpha. only for the shape outline. Not for the fill.
     * @param graphics the graphics object
     */
    @Override
    protected void outlineShape(Graphics graphics) {
        int alpha = graphics.getAlpha();
        graphics.setAlpha(ActivityPainter.getMessagingEdgeTransparency());
        super.outlineShape(graphics);
        graphics.setAlpha(alpha);
    }
    
}
