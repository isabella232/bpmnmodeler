/******************************************************************************
 * Copyright (c) 2006 Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.stp.bpmn.figures.activities;

import java.lang.reflect.Field;
import java.util.WeakHashMap;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PrinterGraphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gmf.runtime.draw2d.ui.internal.graphics.ScaledGraphics;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.NamedBpmnObject;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.stp.bpmn.figures.WrapLabelWithToolTip.IToolTipProvider;
import org.eclipse.swt.SWT;

/**
 * Utility class for activity figures painting.
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ActivityPainter {
    
    public static boolean isBPMN11On() {
        return BpmnDiagramEditorPlugin.getInstance().getPreferenceStore().getBoolean(
                BpmnDiagramPreferenceInitializer.PREF_BPMN1_1_STYLE);
    }
    
    private static int sequenceEdgeTransparency = BpmnDiagramEditorPlugin.getInstance().getPreferenceStore()
        .getInt(BpmnDiagramPreferenceInitializer.PREF_MSG_LINE_ALPHA);
    private static int messagingEdgeTransparency = BpmnDiagramEditorPlugin.getInstance().getPreferenceStore()
        .getInt(BpmnDiagramPreferenceInitializer.PREF_SEQ_LINE_ALPHA);
    
    
    
    static {
        //listens the preferences and update the cached value of the transparency
        //for the 2 types of edge and message connections.
        BpmnDiagramEditorPlugin.getInstance().getPreferenceStore()
            .addPropertyChangeListener(new IPropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent event) {
                    if (event.getProperty().equals(
                            BpmnDiagramPreferenceInitializer.PREF_SEQ_LINE_ALPHA)) {
                        ActivityPainter.sequenceEdgeTransparency = BpmnDiagramEditorPlugin
                            .getInstance().getPreferenceStore()
                            .getInt(BpmnDiagramPreferenceInitializer.PREF_SEQ_LINE_ALPHA);
                    } else if (event.getProperty().equals(
                            BpmnDiagramPreferenceInitializer.PREF_MSG_LINE_ALPHA)) {
                        ActivityPainter.messagingEdgeTransparency = BpmnDiagramEditorPlugin
                            .getInstance().getPreferenceStore()
                            .getInt(BpmnDiagramPreferenceInitializer.PREF_MSG_LINE_ALPHA);
                    }
                }
            });
    }
    
    /**
     * @return The transparency to apply for the sequence edge connection
     */
    public static int getSequenceEdgeTransparency() {
        return sequenceEdgeTransparency;
    }
    /**
     * @return The transparency to apply for the messaging edge connection
     */
    public static int getMessagingEdgeTransparency() {
        return messagingEdgeTransparency;
    }
    
    /**
     * Paints the specified activity figure.
     * 
     * @param graphics
     *            The Graphics object used for painting
     * @param fig
     *            activity figure to be painted.
     */
    public static void paint(Graphics graphics, ActivityFigure fig) {
        graphics.setBackgroundColor(fig.getBackgroundColor());
        graphics.setForegroundColor(fig.getForegroundColor());
        PrecisionRectangle innerRect = null;
        switch (fig.getActivityType()) {
        case ActivityType.EVENT_START_EMPTY:
        case ActivityType.EVENT_START_MESSAGE:
        case ActivityType.EVENT_START_RULE:
        case ActivityType.EVENT_START_LINK:
        case ActivityType.EVENT_START_MULTIPLE:
        case ActivityType.EVENT_START_TIMER:
        case ActivityType.EVENT_START_SIGNAL:
            innerRect = paintEventStart(graphics, fig);
            break;
        case ActivityType.EVENT_INTERMEDIATE_COMPENSATION:
        case ActivityType.EVENT_INTERMEDIATE_EMPTY:
        case ActivityType.EVENT_INTERMEDIATE_ERROR:
        case ActivityType.EVENT_INTERMEDIATE_MESSAGE:
        case ActivityType.EVENT_INTERMEDIATE_RULE:
        case ActivityType.EVENT_INTERMEDIATE_TIMER:
        case ActivityType.EVENT_INTERMEDIATE_CANCEL:
        case ActivityType.EVENT_INTERMEDIATE_LINK:
        case ActivityType.EVENT_INTERMEDIATE_MULTIPLE:
        case ActivityType.EVENT_INTERMEDIATE_SIGNAL:
            innerRect = paintEventIntermediate(graphics, fig);
            break;
        case ActivityType.EVENT_END_COMPENSATION:
        case ActivityType.EVENT_END_EMPTY:
        case ActivityType.EVENT_END_ERROR:
        case ActivityType.EVENT_END_MESSAGE:
        case ActivityType.EVENT_END_TERMINATE:
        case ActivityType.EVENT_END_CANCEL:
        case ActivityType.EVENT_END_MULTIPLE:
        case ActivityType.EVENT_END_LINK:
        case ActivityType.EVENT_END_SIGNAL:
            innerRect = paintEventEnd(graphics, fig);
            break;
        case ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE:
        case ActivityType.GATEWAY_DATA_BASED_INCLUSIVE:
        case ActivityType.GATEWAY_EVENT_BASED_EXCLUSIVE:
        case ActivityType.GATEWAY_PARALLEL:
        case ActivityType.GATEWAY_COMPLEX:
            innerRect = paintGateway(graphics, fig);
            break;
        }
        switch (fig.getActivityType()) {
        case ActivityType.EVENT_START_EMPTY:
            break;
        case ActivityType.EVENT_START_MESSAGE:
            paintMessage(graphics, fig, innerRect);
            break;
        case ActivityType.EVENT_START_RULE:
            paintRule(graphics, fig);
            break;
        case ActivityType.EVENT_START_LINK:
            paintLink(graphics, fig, innerRect);
            break;
        case ActivityType.EVENT_START_MULTIPLE:
            if (isBPMN11On()) {
                paintPentagon(graphics, fig, innerRect);
            } else {
                paintStar(graphics, fig, innerRect);
            }
            break;
        case ActivityType.EVENT_START_TIMER:
            paintTimer(graphics, fig, innerRect);
            break;
        case ActivityType.EVENT_INTERMEDIATE_COMPENSATION:
            paintCompensation(graphics, fig, innerRect);
            break;
        case ActivityType.EVENT_INTERMEDIATE_EMPTY:
            break;
        case ActivityType.EVENT_INTERMEDIATE_ERROR:
            paintError(graphics, fig);
            break;
        case ActivityType.EVENT_INTERMEDIATE_MESSAGE:
            paintMessage(graphics, fig, innerRect);
            break;
        case ActivityType.EVENT_INTERMEDIATE_RULE:
            paintRule(graphics, fig);
            break;
        case ActivityType.EVENT_INTERMEDIATE_TIMER:
            paintTimer(graphics, fig, innerRect);
            break;
        case ActivityType.EVENT_INTERMEDIATE_CANCEL:
            paintCancelX(graphics, fig, innerRect);
            break;
        case ActivityType.EVENT_INTERMEDIATE_LINK:
            paintLink(graphics, fig, innerRect);
            break;
        case ActivityType.EVENT_INTERMEDIATE_MULTIPLE:
            if (isBPMN11On()) {
                paintPentagon(graphics, fig, innerRect);
            } else {
                paintStar(graphics, fig, innerRect);
            }
            break;
        case ActivityType.EVENT_END_COMPENSATION:
            paintCompensation(graphics, fig, innerRect);
            break;
        case ActivityType.EVENT_END_EMPTY:
            break;
        case ActivityType.EVENT_END_ERROR:
            paintError(graphics, fig);
            break;
        case ActivityType.EVENT_END_MESSAGE:
            paintMessage(graphics, fig, innerRect);
            break;
        case ActivityType.EVENT_END_TERMINATE:
            paintTerminate(graphics, fig, innerRect);
            break;
        case ActivityType.EVENT_END_CANCEL:
            paintCancelX(graphics, fig, innerRect);
            break;
        case ActivityType.EVENT_END_MULTIPLE:
            if (isBPMN11On()) {
                paintPentagon(graphics, fig, innerRect);
            } else {
                paintStar(graphics, fig, innerRect);
            }
            break;
        case ActivityType.EVENT_END_LINK:
            paintLink(graphics, fig, innerRect);
            break;
        case ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE:
            paintGatewayX(graphics, fig, innerRect);
            break;
        case ActivityType.GATEWAY_DATA_BASED_INCLUSIVE:
            paintBoldOval(graphics, fig, innerRect);
            break;
        case ActivityType.GATEWAY_EVENT_BASED_EXCLUSIVE:
            if (isBPMN11On()) {
                paintBpmn11EvendBasedGateway(graphics, fig, innerRect);
            } else {
                paintStar(graphics, fig, innerRect);
            }
            break;
        case ActivityType.GATEWAY_PARALLEL:
            paintPlus(graphics, fig, innerRect);
            break;
        case ActivityType.GATEWAY_COMPLEX:
            paintComplex(graphics, fig, innerRect);
            break;
        case ActivityType.EVENT_START_SIGNAL:
        case ActivityType.EVENT_INTERMEDIATE_SIGNAL:
        case ActivityType.EVENT_END_SIGNAL:
            paintSignal(graphics, fig, innerRect);
            break;
        }
        if (fig.isLooping()) {
            paintLoopInsideFigure(graphics, fig.getBounds(), fig);
        }
    }

    public static void paintPentagon(Graphics graphics, ActivityFigure fig,
            PrecisionRectangle innerRect) {
        paintPentagon(graphics, !fig.isCatching(), innerRect);
    }
    
    public static void paintPentagon(Graphics graphics, boolean fill,
            PrecisionRectangle innerRect) {
        double innerWidth = innerRect.width/Math.sqrt(2);
        double innerx = (innerRect.preciseWidth - innerWidth)/2;
        PrecisionRectangle rect = new PrecisionRectangle();
        rect.setWidth(innerWidth);
        rect.setHeight(innerWidth - innerWidth/5);
        rect.setX(innerRect.preciseX + innerx);
        rect.setY(innerRect.preciseY + innerx + innerWidth/10);
        
        PointList pl = new PointList();
        pl.addPoint(new Point(rect.getTopLeft().x, rect.getTopLeft().y + rect.height/3));
        pl.addPoint(rect.getTop());
        pl.addPoint(new Point(rect.getTopRight().x, rect.getTopRight().y + rect.height/3));
        pl.addPoint(new Point(rect.getBottomRight().x - rect.width/6, rect.getBottomRight().y));
        pl.addPoint(new Point(rect.getBottomLeft().x + rect.width/6, rect.getBottomLeft().y));
        
        graphics.pushState();
        if (fill) {
            graphics.setBackgroundColor(ColorConstants.black);
           graphics.fillPolygon(pl); 
        } else {
            graphics.drawPolygon(pl);
        }
        graphics.popState();
    }
    /**
     * Paints gateway
     * 
     * @param graphics
     *            The Graphics object used for painting.
     * @param fig
     *            figure to be painted.
     */
    public static PrecisionRectangle paintGateway(Graphics graphics, ActivityFigure fig) {
        graphics.pushState();
        graphics.setForegroundColor(ColorConstants.darkGray);
        int lineWidth = MapModeUtil.getMapMode(fig).LPtoDP(2);
        graphics.setLineWidth(lineWidth);
        PrecisionRectangle r = calcInnerRectangle(
              fig.getBounds().getCopy(), lineWidth);
        PointList pointList = new PointList();

        pointList.addPoint(r.x + r.width / 2, r.y);
        pointList.addPoint(r.x + r.width, r.y + r.height / 2);
        pointList.addPoint(r.x + r.width / 2, r.y + r.height);
        pointList.addPoint(r.x, r.y + r.height / 2);
        graphics.fillPolygon(pointList);
        graphics.drawPolygon(pointList);

        graphics.popState();
        return r;
    }

    /**
     * Paints event start figure.
     * 
     * @param graphics
     *            The Graphics object used for painting
     * @param fig
     *            event figure to be painted.
     * @return The last rectangle in which something was painted.
     */
    public static PrecisionRectangle paintEventStart(Graphics graphics, ActivityFigure fig) {
        graphics.pushState();
        graphics.setForegroundColor(ColorConstants.black);

        // first fill the biggest oval with white:
        Rectangle rect = fig.getBounds().getCopy();
        graphics.fillOval(rect);

        graphics.setLineWidth(MapModeUtil.getMapMode(fig).LPtoDP(1));
        PrecisionRectangle outerCircle = calcInnerRectangle(rect, graphics.getLineWidth());
        
        //we compute the inner circle as if this was an intermediary event shape
        //so that we return the same size of inner shape to paint the markers
        //inside.
        PrecisionRectangle precRect = outerCircle.getPreciseCopy();
        shrink(precRect,
                outerCircle.preciseWidth/12, outerCircle.preciseHeight/12);
        outerCircle = precRect.getPreciseCopy();
        shrink(outerCircle,
                -outerCircle.preciseWidth/12, -outerCircle.preciseHeight/12);
        
        graphics.drawOval(outerCircle);

        graphics.popState();
        return precRect;
    }

    /**
     * Calculates rectangle inside the specified rectangle for the specified
     * line width.
     * 
     * @param rect
     *            outer rectangle
     * @param lineWidth
     *            the line width
     * @return inner rectangle which width and height is less by the specified
     *         line width from the outer rectangle width and height and location
     *         is shifted by half line width both in horizontal and vertical
     *         direction.
     */
    private static PrecisionRectangle calcInnerRectangle(Rectangle rect, int lineWidth) {
        PrecisionRectangle newRect = new PrecisionRectangle(rect);
        double halfLineWidth = lineWidth / 2.0;
        newRect.setX(newRect.preciseX + halfLineWidth);
        newRect.setY(newRect.preciseY + halfLineWidth);
        newRect.setWidth(newRect.preciseWidth - lineWidth);
        newRect.setHeight(newRect.preciseHeight - lineWidth);
        return newRect;
    }

    
    /**
     * Paints intermediate event figure.
     * 
     * @param graphics
     *            The Graphics object used for painting
     * @param fig
     *            event figure to be painted.
     * @return The inner most rectangle in which something was painted.
     */
    
    public static PrecisionRectangle paintEventIntermediate(Graphics graphics,
            ActivityFigure fig) {
        graphics.pushState();
        graphics.setForegroundColor(ColorConstants.black);

        //do fill te biggest oval with white.
        Rectangle rect = fig.getBounds().getCopy();
        graphics.fillOval(rect);

        graphics.setLineWidth(1);//MapModeUtil.getMapMode(fig).LPtoDP(1));
        
        PrecisionRectangle outerCircle = calcInnerRectangle(rect, graphics.getLineWidth());
        
        PrecisionRectangle innerCircle = outerCircle.getPreciseCopy();
        
        double ddW = outerCircle.preciseWidth/10;
        double ddH = outerCircle.preciseHeight/10;
//        if (ddW<3/graphics.getAbsoluteScale()) {
//            ddW = Math.min(3/graphics.getAbsoluteScale(),outerCircle.preciseWidth/4);
//        }
//        if (ddH<3/graphics.getAbsoluteScale()) {
//            ddH = Math.min(3/graphics.getAbsoluteScale(),outerCircle.preciseHeight/4);
//        }
        
        shrink(innerCircle, ddW, ddH);
        
        graphics.drawOval(innerCircle);
        graphics.drawOval(outerCircle);

        graphics.popState();
        return innerCircle;
    }


    /**
     * Paints event end figure.
     * 
     * @param graphics The Graphics object used for painting
     * @param fig event figure to be painted.
     * @return The last rectangle in wich something was actually painted
     */
    public static PrecisionRectangle paintEventEnd(Graphics graphics, ActivityFigure fig) {
        graphics.pushState();
        graphics.setForegroundColor(ColorConstants.darkGray);
        //don't set the background color! EDGE-1119
//        graphics.setBackgroundColor(ColorConstants.white);

        double lineWidth = fig.getBounds().width * getAbsoluteScale(graphics)/10;
        graphics.setLineWidth((int)Math.floor(lineWidth));
        
        PrecisionRectangle outerCircle = new PrecisionRectangle(fig.getBounds());
        shrink(outerCircle, lineWidth /2, lineWidth /2);
        graphics.fillOval(outerCircle);
        graphics.drawOval(outerCircle);
        
        graphics.popState();
        shrink(outerCircle, outerCircle.preciseWidth/10,
                outerCircle.preciseHeight/10);
        return outerCircle;
    }

    /**
     * Paints terminate figure (filled black circle) inside end activity figure.
     * 
     * @param graphics
     *            The Graphics object used for painting
     * @param fig
     *            event figure
     */
    public static void paintTerminate(Graphics graphics, ActivityFigure fig,
            PrecisionRectangle innerRect) {
        graphics.pushState();
        // linewidth is 1/22 of the figure. rectangle width is 12 for 22:
        // and height is 10 for 22
//        graphics.setForegroundColor(ColorConstants.black);
        graphics.setBackgroundColor(ColorConstants.darkGray);

        shrink(innerRect, innerRect.preciseWidth / 4.5, innerRect.preciseHeight / 4.5, false);
        if (innerRect.width - Math.floor(innerRect.width)>= 0.5) {
            innerRect.x++;
        }
        if (innerRect.height - Math.floor(innerRect.height)>= 0.5) {
            innerRect.y++;
        }
        innerRect.updateInts();
//        innerRect.x ++;
//        innerRect.y ++;
        graphics.fillOval(innerRect);

        graphics.popState();
    }

    /**
     * Pains message figure (envelope) inside event figure.
     * 
     * @param graphics
     *            The Graphics object used for painting
     * @param fig
     *            event figure.
     */
    public static void paintMessage(Graphics graphics, ActivityFigure fig,
            PrecisionRectangle innerRect) {
        int lineWidth = MapModeUtil.getMapMode(fig).LPtoDP(1);
        paintMessage(graphics, innerRect, lineWidth, fig.isCatching());
    }
    
    /**
     * Pains message figure (envelope) inside event figure.
     * 
     * @param graphics
     *            The Graphics object used for painting
     * @param fig
     *            event figure.
     */
    public static void paintMessage(Graphics graphics,
            PrecisionRectangle innerRect, int lineWidth) {
        paintMessage(graphics, innerRect, lineWidth, false);
    }
    /**
     * Pains message figure (envelope) inside event figure.
     * 
     * @param graphics
     *            The Graphics object used for painting
     * @param fig
     *            event figure.
     *  @param isCatching
     *            whether the figure should show as a catch
     */
    public static void paintMessage(Graphics graphics,
            PrecisionRectangle innerRect, int lineWidth, boolean isCatching) {
        graphics.pushState();

        graphics.setForegroundColor(ColorConstants.black);
        graphics.setBackgroundColor(ColorConstants.black);
        graphics.setLineWidth(lineWidth);

        //make sure we are painting _inside_
        shrink(innerRect, innerRect.preciseWidth/6.0,
                innerRect.preciseHeight/4.5);
        
        if (!isCatching && isBPMN11On()) {
            graphics.fillRectangle(innerRect);
            graphics.setForegroundColor(ColorConstants.white);
            graphics.setLineWidth((int) (graphics.getLineWidth()));
        } else {
            graphics.drawRectangle(innerRect);
        }
        // System.err.println("After: " + rect.width);
        // ok. now just need to compute 2 point around the center:
        // basically it is the center -1 on the y and + 1 on the x
        // then -1 and +1
        
        graphics.drawPolyline(new int[] { innerRect.getTopLeft().x,
                innerRect.getTopLeft().y, innerRect.getCenter().x, innerRect.getCenter().y,
                innerRect.getTopRight().x, innerRect.getTopRight().y });

        graphics.popState();
    }

    /**
     * Paints conpensation figure (backwards arrows) inside event figure.
     * 
     * @param graphics
     *            The Graphics object used for painting
     * @param fig
     *            event figure.
     */
    public static void paintCompensation(Graphics graphics, ActivityFigure fig, PrecisionRectangle innerRect) {
        
        double innerWidth = innerRect.width/Math.sqrt(2);
        double innerx = (innerRect.preciseWidth - innerWidth)/2;
        innerRect.setWidth(innerWidth);
        innerRect.setHeight(innerWidth);
        innerRect.setX(innerRect.preciseX + innerx/2);
        innerRect.setY(innerRect.preciseY + innerx);
        int lineWidth = MapModeUtil.getMapMode(fig).LPtoDP(1);
        paintCompensation(graphics, innerRect, lineWidth, fig.isCatching());
    }
        
    public static void paintCompensation(Graphics graphics, Rectangle rect, int lineWidth) {
        paintCompensation(graphics, rect, lineWidth, false);
    }
    
    public static void paintCompensation(Graphics graphics, Rectangle rect, int lineWidth, boolean isCatching) {   
        graphics.pushState();
        // linewidth is 1/22 of the figure. rectangle width is 12 for 22:
        // and height is 10 for 22
        graphics.setBackgroundColor(ColorConstants.darkGray);
        graphics.setForegroundColor(ColorConstants.darkGray);
//        graphics.fillRectangle(rect);
        PointList pl = new PointList(3);
        pl.addPoint(rect.getLeft().getCopy());
        pl.addPoint(rect.getTop().getCopy());
        pl.addPoint(rect.getBottom().getCopy());
        if (isCatching && isBPMN11On()) {
            graphics.setLineWidth(2*lineWidth);
            graphics.drawPolygon(pl);
        } else {
            graphics.fillPolygon(pl);
        }
        pl = new PointList(3);
        pl.addPoint(rect.getCenter());
        pl.addPoint(rect.getTopRight());
        pl.addPoint(rect.getBottomRight());
        if (isCatching && isBPMN11On()) {
            graphics.drawPolygon(pl);
        } else {
            graphics.fillPolygon(pl);
        }
        graphics.popState();
    }

    /**
     * Paints timer inside activity figure.
     * 
     * @param graphics
     *            The Graphics object used for painting
     * @param fig
     *            activity figure.
     * @param innerRect The precision rectangle inside the figure in which 
     * another paint already took place. Might be null
     */
    public static void paintTimer(Graphics graphics, ActivityFigure fig,
            PrecisionRectangle innerRect) {
        graphics.pushState();

        int lineWidth = MapModeUtil.getMapMode(fig).LPtoDP(1);

        PrecisionRectangle rect = innerRect;
        //make sure we are painting _inside_
        shrink(rect, lineWidth / 2.0, lineWidth / 2.0);

        graphics.setForegroundColor(ColorConstants.black);
        graphics.setBackgroundColor(ColorConstants.black);

        graphics.setLineWidth(lineWidth);
        
        //let's use something more precise
        shrink(rect, rect.preciseWidth / 6.0, rect.preciseHeight / 6.0);
        
        graphics.drawOval(rect);
        
        shrink(rect, lineWidth / 2.0, lineWidth / 2.0);

        // now draw ticks
        double a = rect.preciseWidth / 2;
        double b = rect.preciseHeight / 2;
        double x0 = rect.preciseX + a;
        double y0 = rect.preciseY + b;

        final double RATIO = 0.8;
        for (int i = 0; i < 12; i++) {
            int angleGrad = i * 30;

            double x = a * Math.cos(Math.toRadians(angleGrad));
            double y = b * Math.sin(Math.toRadians(angleGrad));

            PrecisionPoint pp1 = new PrecisionPoint(x0 + x * RATIO, y0 + y * RATIO);
            PrecisionPoint pp2 = new PrecisionPoint(x0 + x, y0 + y);

            graphics.drawLine(pp1, pp2);
        }

        // draw hands
        PrecisionPoint center = new PrecisionPoint(x0, y0);
        PrecisionPoint bigHand = new PrecisionPoint(rect.preciseX, rect.preciseY);
        bigHand.preciseX += 11.0 * rect.preciseWidth / 18.0;
        bigHand.preciseY += 3.0 * rect.preciseHeight / 24.0;
        bigHand.updateInts();
        graphics.drawLine(center, bigHand);
        PrecisionPoint littleHand = new PrecisionPoint(x0, y0);
        littleHand.preciseX += 3.0 * rect.preciseWidth / 10.0;
        littleHand.preciseY -= 1.0 * rect.preciseHeight / 24.0;
        littleHand.updateInts();
        graphics.drawLine(center, littleHand);

        graphics.popState();
    }
    
    private static final void shrink(PrecisionRectangle rect, 
            double w, double h) {
        shrink(rect, 
                w, h, true);
    }
    
    private static final void shrink(PrecisionRectangle rect, 
            double w, double h, boolean shouldUpdateInts) {
        rect.setX(rect.preciseX + w);
        rect.setY(rect.preciseY + h);
        rect.setWidth(rect.preciseWidth - 2.0 * w);
        rect.setHeight(rect.preciseHeight - 2.0 * h);
        if (shouldUpdateInts) {
            rect.updateInts();
        }
    }

    /**
     * Paint error figure (flash arrow) inside specified activity figure.
     * 
     * @param graphics The Graphics object used for painting
     * @param fig activity figure.
     */
    public static void paintError(Graphics graphics, ActivityFigure fig) {
        Rectangle rect = fig.getBounds().getCopy();
        rect.shrink(rect.width / 4, rect.height / 4);
        // graphics.drawRoundRectangle(rect, 6, 6);
        paintError(graphics, rect, MapModeUtil.getMapMode(fig).LPtoDP(1), fig.isCatching());
    }
    
    public static void paintError(Graphics graphics, Rectangle rect, int lineWidth) {
        paintError(graphics, rect, lineWidth, false);
    }
    
    /**
     * graphics.setLineWidth();
     * @param graphics
     * @param rect
     * @param lineWidth
     * @param isCatching
     */
    public static void paintError(Graphics graphics, Rectangle rect,
                                  int lineWidth, boolean isCatching) {
        graphics.pushState();
        
        graphics.setLineWidth(lineWidth);
        
        // linewidth is 1/22 of the figure. rectangle width is 12 for 22:
        // and height is 10 for 22
        graphics.setForegroundColor(ColorConstants.black);
        graphics.setBackgroundColor(ColorConstants.black);

        Point one = rect.getTopLeft().translate(1 * rect.width / 4,
                1 * rect.height / 4);
        Point onebis = one.getCopy().translate(1 * rect.width / 7,
                1 * rect.height / 7);
        Point two = rect.getTopLeft().translate(3 * rect.width / 4,
                3 * rect.height / 4);
        Point twobis = two.getCopy().translate(-1 * rect.width / 7,
                -1 * rect.height / 7);
        int lineW = (int)Math.floor(rect.height / 8);
        onebis.y += lineW;
        twobis.y -= lineW;
        
        PointList pl = new PointList(6);
        pl.addPoint(rect.getBottomLeft());
        pl.addPoint(onebis);
        pl.addPoint(two);
        pl.addPoint(rect.getTopRight());
        pl.addPoint(twobis);
        pl.addPoint(one);
        if (isCatching && isBPMN11On()) {
            graphics.drawPolygon(pl);
        } else {
            graphics.fillPolygon(pl);
        }

        graphics.popState();
    }

    /**
     * Paint rule figure inside specified activity figure.
     * 
     * @param graphics
     *            The Graphics object used for painting
     * @param fig
     *            activity figure.
     */
    public static void paintRule(Graphics graphics, ActivityFigure fig) {
        graphics.pushState();
        Rectangle rect = fig.getBounds().getCopy();
        // linewidth is 1/22 of the figure. rectangle width is 12 for 22:
        // and height is 10 for 22
        graphics.setForegroundColor(ColorConstants.black);
        graphics.setBackgroundColor(ColorConstants.black);

        // external rectangle is twice thicker
        graphics.setLineWidth((int) Math.floor(2 * Math.max(rect.width,
                rect.height) / 22));

        // graphics.drawRoundedRectangle(rect.shrink(10, 10), 8, 8);
        // System.err.println("Before: " + rect.width + " line width " +
        // graphics.getLineWidth()/2);
        graphics.drawRectangle(rect.shrink(graphics.getLineWidth() / 2
                + rect.width * (22 - 10) / (22 * 2), graphics.getLineWidth()
                / 2 + rect.height / 4));

        // internal lines are thin
        graphics.setLineWidth((int) Math.floor(Math
                .max(rect.width, rect.height) / 22));

        rect.shrink(graphics.getLineWidth(), graphics.getLineWidth() * 4);

        for (int i = 0; i < 5; i++) {
            graphics.drawLine(rect.getTopLeft()
                    .translate(0, i * rect.width / 5), rect.getTopRight()
                    .translate(0, i * rect.width / 5));
        }

        graphics.popState();
    }

    /**
     * Paints star figure for event-based exclusive gateway figure.
     * 
     * @param graphics
     *            The Graphics object used for painting
     * @param fig
     *            activity figure.
     */
    public static void paintStar(Graphics graphics, ActivityFigure fig,
            PrecisionRectangle innerRect) {
        graphics.pushState();
        Rectangle rect = fig.getBounds().getCopy();
        // linewidth is 1/22 of the figure. rectangle width is 12 for 22:
        // and height is 10 for 22
        graphics.setBackgroundColor(ColorConstants.darkGray);
        graphics.setForegroundColor(ColorConstants.darkGray);

        // if it is a gateway, paint the 2 ovals
        if (fig.getActivityType() == ActivityType.GATEWAY_EVENT_BASED_EXCLUSIVE) {
            //same strategy than the intermediate event:
            //go into PrecisionRectangle, compute the smallest one, expand it
            //draw.
            //this will only work for diamond shapes.
            int lineWidth = 1;//MapModeUtil.getMapMode(fig).LPtoDP(1);
            PrecisionRectangle inner = new PrecisionRectangle(innerRect);//calcInnerRectangle(innerRect, lineWidth);
//            graphics.drawRectangle(inner);
            inner.preciseWidth = 1.414 * rect.width / 2.0;
            inner.preciseHeight = 1.414 * rect.height / 2.0;
            inner.preciseX = rect.x + rect.width * (2-1.41421356) / 4.0;
            inner.preciseY = rect.y + rect.height * (2-1.41421356) / 4.0;
            inner.updateInts();
            inner = calcInnerRectangle(inner, lineWidth);
//            graphics.drawRectangle(inner);
            
            PrecisionRectangle precRect = inner.getPreciseCopy();
            shrink(precRect,
                    2*inner.preciseWidth/12, 2*inner.preciseHeight/12);
            inner = precRect.getPreciseCopy();
            shrink(inner,
                    -3*inner.preciseWidth/24, -3*inner.preciseHeight/24);
            
            graphics.drawOval(precRect);
            graphics.drawOval(inner);
            
            precRect = calcInnerRectangle(precRect, lineWidth);
            shrink(precRect, precRect.preciseWidth / 6, precRect.preciseHeight / 5);
            rect = precRect.getCopy();
        } else {
            // 2 triangles, one north one, one south
            // the only difficulty is to compute the center.

            // first shrink to the same size than the message width (about)
            rect.shrink(rect.width / 5,// *12/22,
                    rect.height / 4);// *12/22;
        }

        // not a true hexagram but good enough for now

        // now translate to
        // graphics.fillPolygon(getStarPolygon(Math.PI/4, Math.PI / 6));
        rect.translate(0, -1 * rect.height / 5);
        
        PointList pl = new PointList(3);
        pl.addPoint(rect.getTop());
        pl.addPoint(rect.getBottomLeft());
        pl.addPoint(rect.getBottomRight());
        graphics.fillPolygon(pl);

        rect.translate(0, 2 * rect.height / 5);
        pl = new PointList(3);
        pl.addPoint(rect.getBottom());
        pl.addPoint(rect.getTopRight());
        pl.addPoint(rect.getTopLeft());
        graphics.fillPolygon(pl);

        graphics.popState();
    }

    /**
     * Calculates star polygon for event-based exclusive gateway.
     * 
     * @param radius
     *            the radius of the circle that should contain the star.
     * @param rotation
     *            rotation angle
     * @return
     */
    static PointList getStarPolygon(double radius, double rotation) {
        PointList pointList = new PointList(10);
        /*
         * radius2: the distance between the inner tip and the center
         */
        double radius2 = radius * Math.sin(Math.PI / 10)
                / Math.cos(Math.PI / 5);
        for (int i = 0; i < 5; i++) {
            // outer tip
            double xAngle = Math.PI * 2 * i / 5 + rotation - Math.PI / 2;
            float x1 = (float) (radius * Math.cos(xAngle));
            float y1 = (float) (radius * Math.sin(xAngle));
            Point point1 = new Point(Math.round(x1), Math.round(y1));
            pointList.addPoint(point1);
            // inner tip
            double xAngle2 = xAngle + Math.PI / 5;
            float x2 = (float) (radius2 * Math.cos(xAngle2));
            float y2 = (float) (radius2 * Math.sin(xAngle2));
            Point point2 = new Point(Math.round(x2), Math.round(y2));
            pointList.addPoint(point2);
        }
        return pointList;
    }

    /**
     * Paints X-cross figure for data-based exclusive gateway figure.
     * 
     * @param graphics
     *            The Graphics object used for painting
     * @param fig
     *            activity figure.
     */
    public static void paintGatewayX(Graphics graphics, ActivityFigure fig, PrecisionRectangle rect) {
        graphics.pushState();

        graphics.setForegroundColor(ColorConstants.darkGray);

        double lineWidth = rect.preciseWidth * getAbsoluteScale(graphics)/6;
        shrink(rect, 3.0* rect.preciseWidth / 8,
                     3.0* rect.preciseHeight / 8);
        try {
            graphics.setLineCap(SWT.CAP_SQUARE);
        } catch (Throwable t) {
            //never mind! the print graphics object does not support this.
        }
        graphics.setLineWidth((int)Math.floor(lineWidth*0.95));
        
        graphics.drawLine(rect.getTopLeft(), rect.getBottomRight());
        graphics.drawLine(rect.getTopRight(), rect.getBottomLeft());
        
        graphics.popState();
    }
    /**
     * Paints + merged with X figure for complex gateway figure.
     * 
     * @param graphics
     *            The Graphics object used for painting
     * @param fig
     *            activity figure.
     */
    public static void paintComplex(Graphics graphics, ActivityFigure fig, PrecisionRectangle rect) {
        graphics.pushState();

        graphics.setForegroundColor(ColorConstants.darkGray);

        double lineWidth = rect.preciseWidth * getAbsoluteScale(graphics)/6;
        shrink(rect, 3.0* rect.preciseWidth / 9.5,
                     3.0* rect.preciseHeight / 9.5);
        try {
            //graphics.setLineCap(SWT.CAP_SQUARE);
        } catch (Throwable t) {
            //never mind! the print graphics object does not support this.
        }
        graphics.setLineWidth((int)Math.floor(lineWidth*0.6));
        
        graphics.drawLine(rect.getTopLeft(), rect.getBottomRight());
        graphics.drawLine(rect.getTopRight(), rect.getBottomLeft());
        shrink(rect, 1.2 * rect.preciseWidth, 1.2 * rect.preciseHeight);
        graphics.drawLine(rect.getLeft(), rect.getRight());
        graphics.drawLine(rect.getTop(), rect.getBottom());
        
        graphics.popState();
    }
    /**
     * Paints X-cross figure for data-based exclusive gateway figure.
     * 
     * @param graphics
     *            The Graphics object used for painting
     * @param fig
     *            activity figure.
     */
    public static void paintCancelX(Graphics graphics, ActivityFigure fig, PrecisionRectangle rect) {
        paintCancelX(graphics, rect, MapModeUtil.getMapMode(fig).LPtoDP(1), fig.isCatching() && isBPMN11On());
    }
    
    public static void paintCancelX(Graphics graphics,
                    PrecisionRectangle rect, int lineWidth, boolean isCatching) {
        
        graphics.setLineWidth(lineWidth);
        graphics.pushState();

        graphics.setForegroundColor(ColorConstants.darkGray);

//        rect = new PrecisionRectangle(fig.getBounds());
        
        double lineW = rect.preciseWidth /4;
        shrink(rect, 3.0* rect.preciseWidth / 15,
                     3.0* rect.preciseHeight / 15);
        try {
           // graphics.setLineCap(SWT.CAP_SQUARE);
        } catch (Throwable t) {
            //never mind! the print graphics object does not support this.
        }
        
        
        if (isCatching) {
            int half = (int) (lineW/2);
            PointList pointList = new PointList();
            pointList.addPoint(rect.getTopLeft().getCopy().translate(-half, half));
            pointList.addPoint(rect.getTopLeft().getCopy().translate(half, -half));
            pointList.addPoint(rect.getTop().getCopy().translate(0, half));
            pointList.addPoint(rect.getTopRight().getCopy().translate(-half, -half));
            pointList.addPoint(rect.getTopRight().getCopy().translate(half, half));
            pointList.addPoint(rect.getRight().getCopy().translate(-half, 0));
            pointList.addPoint(rect.getBottomRight().getCopy().translate(half, -half));
            pointList.addPoint(rect.getBottomRight().getCopy().translate(-half, half));
            pointList.addPoint(rect.getBottom().getCopy().translate(0, -half));
            pointList.addPoint(rect.getBottomLeft().getCopy().translate(half, half));
            pointList.addPoint(rect.getBottomLeft().getCopy().translate(-half, -half));
            pointList.addPoint(rect.getLeft().getCopy().translate(half, 0));
            graphics.drawPolygon(pointList);
        } else {
            lineW *= getAbsoluteScale(graphics);
            graphics.setLineWidth((int)Math.floor(lineW));
            graphics.drawLine(rect.getTopLeft(), rect.getBottomRight());
            graphics.drawLine(rect.getTopRight(), rect.getBottomLeft());
        }
        graphics.popState();
    }

    /**
     * Paints + cross figure for parallel gateway figure.
     * 
     * @param graphics
     *            The Graphics object used for painting
     * @param fig
     *            activity figure where cross should be painted.
     */
    public static void paintPlus(Graphics graphics, ActivityFigure fig,
            PrecisionRectangle rect) {
        graphics.pushState();
        graphics.setForegroundColor(ColorConstants.darkGray);

        double lineWidth = rect.preciseWidth * getAbsoluteScale(graphics)/6;
        shrink(rect, 5*rect.preciseWidth / 16,
                     5*rect.preciseHeight / 16);
        try {
            graphics.setLineCap(SWT.CAP_SQUARE);
        } catch (Throwable t) {
            //never mind the graphics object used during print does
            //not support this for SVG
        }
        graphics.setLineWidth((int)Math.floor(lineWidth));
        
        graphics.drawLine(rect.getTop(), rect.getBottom());
        graphics.drawLine(rect.getLeft(), rect.getRight());
        

        graphics.popState();
    }
    /**
     * Paints + cross figure for parallel gateway figure.
     * 
     * @param graphics
     *            The Graphics object used for painting
     * @param fig
     *            activity figure where cross should be painted.
     */
    public static void paintLink(Graphics graphics, ActivityFigure fig,
            PrecisionRectangle rect) {
        graphics.pushState();
        graphics.setBackgroundColor(ColorConstants.darkGray);
        graphics.setForegroundColor(ColorConstants.darkGray);

       // double lineWidth = rect.preciseWidth * graphics.getAbsoluteScale()/6;
        double shrinkX = rect.preciseWidth / 32;
        shrink(rect, 4*shrinkX,
                     2*rect.preciseHeight / 14);
        
        //draw a grid of 3 column and 5 lines.
        //place the coord of the polygone to draw the arrow
        //Polyline arrow = new Polyline();
        //arrow.addPoint(pt)
        
        double widthOfArrow = shrinkX+rect.preciseX+rect.preciseWidth*6/11;
        
        int[] points = {
                (int)Math.round(shrinkX+rect.preciseX), (int)Math.round(rect.preciseY + rect.preciseHeight/5),
                (int)Math.round(widthOfArrow), (int)Math.round(rect.preciseY + rect.preciseHeight/5),
                (int)Math.round(widthOfArrow), (int)Math.round(rect.preciseY),
                (int)Math.round(shrinkX+rect.preciseX+rect.preciseWidth), (int)Math.round(rect.preciseY + rect.preciseHeight/2),
                (int)Math.round(widthOfArrow), (int)Math.round(rect.preciseY + rect.preciseHeight),
                (int)Math.round(widthOfArrow), (int)Math.round(rect.preciseY + rect.preciseHeight*4/5),
                (int)Math.round(shrinkX+rect.preciseX), (int)Math.round(rect.preciseY + rect.preciseHeight*4/5)
        };
        if (fig.isCatching()) {
            graphics.drawPolygon(points);
        } else {
            graphics.fillPolygon(points);
        }
        graphics.popState();
    }

    /**
     * Paints oval figure for data-based inclusive figure.
     * 
     * @param graphics
     *            The Graphics object used for painting
     * @param fig
     *            data-based inclusive figure.
     */
    public static void paintBoldOval(Graphics graphics, ActivityFigure fig,
            PrecisionRectangle innerRect) {
        graphics.pushState();
        Rectangle rect = innerRect;
        // linewidth is 1/22 of the figure. rectangle width is 12 for 22:
        // and height is 10 for 22
        graphics.setForegroundColor(ColorConstants.darkGray);

        int orih = rect.height;
        int oriw = rect.width;

        Rectangle rr = rect.getCopy().shrink(11 * oriw / 44, 11 * orih / 44);
        graphics.setLineWidth((int)Math.round(getAbsoluteScale(graphics)*rr.width/8));
        graphics.drawOval(rr);

        graphics.popState();

    }

    /**
     * Calculated loop marker bounds for the figure with the specified bounds
     * 
     * @param figureRect
     *            figure bounds
     * @return calculated loop markler bounds
     */
    public static PrecisionRectangle getLoopMarkerBounds(Rectangle figureRect) {
        final double RATIO = 5.0;
        double loopHeight = figureRect.height / RATIO;
        double loopWidth = Math.min(loopHeight, figureRect.width);

        double loopX = figureRect.x + (figureRect.width - loopWidth) / 2.0;
        double loopY = figureRect.y + figureRect.height - loopHeight;

        PrecisionRectangle bounds = new PrecisionRectangle();
        bounds.setX(loopX);
        bounds.setY(loopY);
        bounds.setWidth(loopWidth);
        bounds.setHeight(loopHeight);

        return bounds;
    }

    /**
     * Paints loop marker insice figure wwith the specified bounds.
     * 
     * @param graphics The Graphics object used for painting
     * @param figureRect figure bounds
     */
    public static void paintLoopInsideFigure(Graphics graphics,
            Rectangle figureRect, IFigure fig) {
        PrecisionRectangle loopRect = getLoopMarkerBounds(figureRect);

        paintLoop(graphics, loopRect, fig);
    }
    /**
     * Paint the parallel marker.
     * @param graphics
     * @param loopRect
     */
    public static void paintLoopMultipleInstance(Graphics graphics,
                                                Rectangle loopRect) {
        graphics.pushState();
        
        graphics.setForegroundColor(ColorConstants.black);
        graphics.setBackgroundColor(ColorConstants.black);
        
        int dx = loopRect.width/4;
        int dy = loopRect.width/10;
        
        PointList pl = new PointList(4);
        pl.addPoint(loopRect.x + dx, loopRect.y + dy);
        pl.addPoint(loopRect.x + 2*dx, loopRect.y + dy);
        pl.addPoint(loopRect.x + 2*dx, loopRect.y + 9*dy);
        pl.addPoint(loopRect.x + dx, loopRect.y + 9*dy);
        graphics.fillPolygon(pl);
        
        pl.translate(2*dx, 0);
        graphics.fillPolygon(pl);
        
        graphics.popState();
    }

    /**
     * Paints loop marker with the specified bounds.
     * 
     * @param graphics
     *            The Graphics object used for painting
     * @param loopRect
     *            loop marker bounds.
     */
    public static void paintLoop(Graphics graphics, PrecisionRectangle loopRect,
            IFigure fig) {
        graphics.pushState();
        graphics.setLineWidth(
                MapModeUtil.getMapMode(fig).LPtoDP(2));

        int angleGrad = 30;// between 0 and 90 - angle between vertical axis
        // and start of arc
        graphics.drawArc(loopRect, -(90 - angleGrad), 360 - 2 * angleGrad);

        // now calculate end of arc coordinates
        double dx = loopRect.preciseWidth / 2
                * Math.cos(Math.toRadians(90 - angleGrad));
        double dy = loopRect.preciseHeight / 2
                * Math.sin(Math.toRadians(90 - angleGrad));

        double endX = loopRect.preciseX + loopRect.preciseWidth / 2 - dx;
        double endY = loopRect.preciseY + loopRect.preciseHeight / 2 + dy;
        double length = endX - loopRect.preciseX;

        // and draw arrow
        PrecisionPoint pp1 = new PrecisionPoint(loopRect.preciseX, endY);
        PrecisionPoint pp2 = new PrecisionPoint(endX, endY);
        graphics.drawLine(pp1, pp2);
        PrecisionPoint pp3 = new PrecisionPoint(endX, endY - length);
        graphics.drawLine(pp2, pp3);

        graphics.popState();
    }
    
    private static final Border TOOLTIP_BORDER = new MarginBorder(0, 2, 1, 0) {
        /**
         * This method does nothing, since this border is just for spacing.
         * @see org.eclipse.draw2d.Border#paint(IFigure, Graphics, Insets)
         */
        public void paint(IFigure figure, Graphics graphics, Insets insets) {
            Rectangle r = figure.getBounds();
            graphics.drawRectangle(r.x, r.y, r.width-1, r.height-1);
        }
    };
    public static IFigure createToolTipFigure(String message) {
        if (message == null || message.length() == 0)
            return null;

        FlowPage fp = new FlowPage() {
            public Dimension getPreferredSize(int w, int h) {
                Dimension d = super.getPreferredSize(-1, -1);
                if (d.width > 150)
                    d = super.getPreferredSize(150, -1);
                return d;
            }
        };
        fp.setOpaque(true);
        fp.setBorder(TOOLTIP_BORDER);
        fp.setBackgroundColor(ColorConstants.yellow);
        TextFlow tf = new TextFlow();
        tf.setText(message);
        fp.add(tf);
        return fp;        
    }
    
    /**
     * Creates a tooltip provider from a named bpmn object
     * @param bpmnObj
     * @param toolTipOnlyWhenTruncated
     * @return
     */
    public static IToolTipProvider createToolTipProvider(final NamedBpmnObject bpmnObj,
            final boolean toolTipOnlyWhenTruncated) {
        final boolean isActivity = bpmnObj instanceof Activity;
        return new IToolTipProvider() {

            public String getToolTipText(boolean currentTextIsTruncated) {
                if (toolTipOnlyWhenTruncated) {
                    if (currentTextIsTruncated) {
                        if (isActivity) {
                            String r = bpmnObj.getName();
                            if (r == null) {
                                return ((Activity)bpmnObj).getActivityType().getName();
                            } else {
                                return r;
                            }
                        }
                        return bpmnObj.getName();
                    } else {
                        return null;
                    }
                } else {
                    if (isActivity) {
                        return bpmnObj.getName();
                    } else {
                        return BpmnDiagramMessages.ActivityPainter_unknown_label;
                    }
                }
            }
            
        };
    }
    
    public static void paintBpmn11EvendBasedGateway(Graphics graphics, ActivityFigure fig, 
            PrecisionRectangle innerRect) {
        PrecisionRectangle ovalCopy = new PrecisionRectangle();
        double fixedDelta = innerRect.preciseWidth/12;
        double delta = (innerRect.preciseWidth * (1- 1/Math.sqrt(2)) + fixedDelta)/2;
        ovalCopy.setX(innerRect.preciseX + delta);
        ovalCopy.setY(innerRect.preciseY + delta);
        ovalCopy.setWidth(innerRect.preciseWidth/Math.sqrt(2) - fixedDelta);
        ovalCopy.setHeight(ovalCopy.preciseWidth);
        graphics.drawOval(ovalCopy);
        
        fixedDelta = innerRect.preciseWidth/6;
        delta = (innerRect.preciseWidth * (1- 1/Math.sqrt(2)) + fixedDelta)/2;
        ovalCopy.setX(innerRect.preciseX + delta);
        ovalCopy.setY(innerRect.preciseY + delta);
        ovalCopy.setWidth(innerRect.preciseWidth/Math.sqrt(2) - fixedDelta);
        ovalCopy.setHeight(ovalCopy.preciseWidth);
        
        graphics.drawOval(ovalCopy);
        paintPentagon(graphics, false, ovalCopy);
    }
    
    public static void paintSignal(Graphics graphics, ActivityFigure fig, PrecisionRectangle innerRect) {
        
//        innerRect = calcInnerRectangle(fig.getBounds().getCopy(), graphics.getLineWidth());
        double innerWidth = innerRect.width/Math.sqrt(2);
        innerWidth = Math.round(innerWidth);
        double innerx = (innerRect.preciseWidth - innerWidth)/2;
        innerRect.setWidth(innerWidth);
        innerRect.setHeight(innerWidth);
        innerRect.setX(innerRect.preciseX + innerx);
        innerRect.setY(innerRect.preciseY + innerx);
        
        PointList pl = new PointList();
        pl.addPoint(innerRect.getTop().getCopy());
        pl.addPoint(innerRect.getBottomRight().getCopy());
        pl.addPoint(innerRect.getBottomLeft().getCopy());
        graphics.pushState();
        graphics.setBackgroundColor(ColorConstants.black);
        graphics.setForegroundColor(ColorConstants.black);
        if (fig.isCatching()) {
            graphics.drawPolygon(pl);
        } else {
//            graphics.fillRectangle(innerRect);
            graphics.fillPolygon(pl);
        }
        graphics.popState();
    }
    
    private static Field graphicsField;
    private static Field zoomField;
    private static void init() {
        if (graphicsField != null) {
            return;
        }
        try {
        	graphicsField = ScaledGraphics.class.getDeclaredField("graphics"); //$NON-NLS-1$
        	graphicsField.setAccessible(true);
        	zoomField = org.eclipse.draw2d.ScaledGraphics.class.getDeclaredField("zoom"); //$NON-NLS-1$
        	zoomField.setAccessible(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
    
    private static Graphics getWrappedGraphics(ScaledGraphics scaledGraphics) {
    	try {
			return (Graphics)graphicsField.get(scaledGraphics);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
    }

    private static double getZoom(org.eclipse.draw2d.ScaledGraphics scaledGraphics) {
    	try {
			return zoomField.getDouble(scaledGraphics);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return 1/scaledGraphics.getAbsoluteScale();
    }

    /**
     * Caches the absolute scale for the PrinterGraphics.
     * There is a bug in the method getAbsoluteScale() in GMF
     * when printing.
     * After some introspection we can fix it.
     * We cache the result as it does not change for the whole printer.
     */
    private static WeakHashMap<Graphics, Double> _graphicsPrintingObjectsToZoom =
    	new WeakHashMap<Graphics, Double>();
    
    /**
     * @param graphics
     * @return The absolute suitable even during a print where the method 
     * getAbsoluteScale returns a number where the zoom has been taken into account twice.
     * We take that number and divide it by zoom to get back to the correct result.
     */
    public static double getAbsoluteScale(Graphics graphics) {
    	Double zoom = _graphicsPrintingObjectsToZoom.get(graphics);
    	double scale = graphics.getAbsoluteScale();
    	if (zoom != null) {
    		if (zoom == Double.NEGATIVE_INFINITY) {
    			//normal result: not a printing object
    			return scale;
    		} else {
    			return scale/zoom.doubleValue();
    			//return Math.sqrt(graphics.getAbsoluteScale());
    		}
    	}
    	if (scale != 1.0) {
    		if (graphics instanceof ScaledGraphics) {
    			init();
    			Graphics wrapped = getWrappedGraphics((ScaledGraphics)graphics);
    			if (wrapped instanceof PrinterGraphics) {
    				zoom = getZoom((PrinterGraphics)wrapped);
    				_graphicsPrintingObjectsToZoom.put(graphics, zoom);
    				return scale/zoom.doubleValue();
    				//return Math.sqrt(graphics.getAbsoluteScale());
    			}
    		}
    	}
    	_graphicsPrintingObjectsToZoom.put(graphics, Double.NEGATIVE_INFINITY);
    	return scale;
    }
    
}
