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
 * Jul 18, 2006     hmalphettes         Created
 **/

package org.eclipse.stp.bpmn.figures.activities;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;
import org.eclipse.stp.bpmn.diagram.ui.RoundedSchemeBorder;

/**
 * Figure that morphes into various shapes according to the type of activity.
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public abstract class ActivityFigure extends Shape {

    private int _activityType = ActivityType.TASK;

    private boolean isLooping;

    public ActivityFigure() {
        setBorder(new RoundedSchemeBorder() {
            @Override
            public void paint(IFigure fig, Graphics graphics, Insets insets) {
                if (_activityType == ActivityType.TASK) {
                    super.paint(fig, graphics, insets);
                }
            }
        });
    }

    /**
     * Sets the activity type over the figure, so that it will be painted properly.
     * @param type one of  the ActivityType literal values.
     */
    public void setActivityType(String type) {
        ActivityType at = ActivityType.get(type);
        if (at == null) {
            _activityType = ActivityType.TASK;
        } else {
            _activityType = at.getValue();
        }
        if (ActivityType.VALUES_EVENTS.contains(at)) {
            setMinimumSize(new Dimension(ActivityEditPart.EVENT_FIGURE_SIZE, 
                    ActivityEditPart.EVENT_FIGURE_SIZE));
        } else if (ActivityType.VALUES_GATEWAYS.contains(at)) {
            setMinimumSize(new Dimension(ActivityEditPart.GATEWAY_FIGURE_SIZE, 
                    ActivityEditPart.GATEWAY_FIGURE_SIZE));
        }
        revalidate();
        repaint();
    }

    public int getActivityType() {
        return _activityType;
    }

    @Override
    protected void fillShape(Graphics graphics) {
        if (_activityType != ActivityType.TASK) {
//            ActivityPainter.paint(graphics, this);
             graphics.fillOval(getBounds());
        } else {
            graphics.fillRoundRectangle(getBounds().getCopy().crop(getBorder().getInsets(this)), 9, 9);
        }
    }

    @Override
    protected void outlineShape(Graphics graphics) {
        if (_activityType == ActivityType.TASK) {
            Rectangle f = Rectangle.SINGLETON;
            Rectangle r = getBounds().getCopy().crop(getBorder().getInsets(this));
            f.x = r.x + lineWidth / 2;
            f.y = r.y + lineWidth / 2;
            f.width = r.width - lineWidth;
            f.height = r.height - lineWidth;
            graphics.drawRoundRectangle(f, 8, 8);
        }
    }

    @Override
    public void paintFigure(Graphics graphics) {
        paintShadow(graphics);
//        System.err.println("activity height = " + getBounds().height + " graphics = " + graphics);
        switch (_activityType) {
        case ActivityType.TASK:
        case ActivityType.SUB_PROCESS:
            super.paintFigure(graphics);
            if (isLooping) {
                ActivityPainter.paintLoopInsideFigure(graphics, bounds, this);
            }
            break;
        default:
            ActivityPainter.paint(graphics, this);
        }
    }
    
    protected void paintShadow(Graphics graphics) {
        super.paintBorder(graphics);
    }

    @Override
    protected void paintBorder(Graphics graphics) {
        //do nothing. we paint the border before the rest of the figure as it is where we paint the shadow.
    }

    /**
     * @return the isLooping
     */
    public boolean isLooping() {
        return isLooping;
    }

    /**
     * @param isLooping
     *            the isLooping to set
     */
    public void setLooping(boolean isLooping) {
        this.isLooping = isLooping;
        revalidate();
        repaint();
    }
    
    /**
     * 
     * @return true if the activity figure represents 
     * a handler
     */
    public abstract boolean isCatching();
}
