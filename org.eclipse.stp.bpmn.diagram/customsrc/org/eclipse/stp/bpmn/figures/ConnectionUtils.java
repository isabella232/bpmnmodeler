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

/** Date         	Author             Changes 
 * 6 August 2006   	MPeleshchyshyn  	Created 
 */
package org.eclipse.stp.bpmn.figures;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;

/**
 * Utility class for different connection-related calculations.
 * 
 * @author MPeleshchyshyn
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public final class ConnectionUtils {

    private ConnectionUtils() {
    }

    /**
     * Calculates the approximated smooth polyline for rectilinear connection
     * for display or other purposes. Must be invoked after rectilinear router.
     * This implementation adds additional points instead of bend points. These
     * poins lays on the circle arc with length of 90 degrees that joins two
     * adjacent segments.
     * <p>
     * For the value of smooth, try to keep the smooth factor used in PolylineConnection
     * 0 - None, 15 - some, 30 - lots
     * </p>
     * 
     * @param points
     *            polyline figure points after rectilinear routing invocation.
     * @param isDuringDrag true when during the drag in that case we don't compute
     * the extra points.
     * @return <code>PointList</code> that is a polyline approximation.
     */
    public static PointList getRoundedRectilinearSmoothPoints(
            boolean isDuringDrag, PointList points, int smoothness) {
//        if (isDuringDrag) {
//            return points;
//        }
        
        if (smoothness < 10 || points.size() == 0) {
            return points;
        }
        double PREFERRED_RADIUS = smoothness < 20 ? 8.0 :
            smoothness < 25 ? 12.0 : smoothness < 35 ? 16.0 : 22 ;
        PointList newPoints = new PointList();
        newPoints.addPoint(points.getFirstPoint());
        for (int i = 1; i < points.size() - 1; i++) {
            Point prev = points.getPoint(i - 1);
            Point curr = points.getPoint(i);
            Point next = points.getPoint(i + 1);
            
            //first thing we do is to put ourselves in the situation
            //where the prev point is on the left of the next.
            //it is easier to visualize (we assume x-s of prev and next are
            //not equal.)
            //we will make sure we reverse the order in which the points are added
            //though.
            boolean reverse = false;
            if (prev.x - next.x > 2) {//sometimes it is very close such as one pixel!
//                System.err.println(i + " is reversed");
                prev = next;
                next = points.getPoint(i - 1);
                reverse = true;
            }

            // calculate radius
            double halfSeg1 = curr.getDistance(prev) / 2.0;
            double halfSeg2 = curr.getDistance(next) / 2.0;

            double radius = Math.min(halfSeg1, halfSeg2);
            radius = Math.min(radius, PREFERRED_RADIUS);

            // calculate center of arc and direction
            boolean centerIsLeft = curr.x - prev.x > 2 ;
            boolean centerIsTop;
            double centerX;
            double centerY;
            int direction = 1;
            if (centerIsLeft) {
                centerX = curr.x - radius;
                centerIsTop = next.y < prev.y;
            } else {
                centerX = curr.x + radius;
                centerIsTop = next.y > prev.y;
            }
            if (centerIsTop) {
                centerY = curr.y - radius;
            } else {
                centerY = curr.y + radius;
            }
            
            // calculate arc start angle
            double startAngle;
            if (centerIsTop) {
                direction = -1;
                if (centerIsLeft) {
                    startAngle = -90;
                } else {
                    startAngle = 0;
                }
            } else {
                direction = 1;
                if (centerIsLeft) {
                    // third quarter
                    startAngle = 90;
                } else {
                    // fourth quarter
                    startAngle = 0;
                }
            }
            int pointsCount = 5;//five is the smoothest curve it seems
            //a lot more makes it really ugly (chart like)
            //less is not good. scale does not really matter surprisingly.
            //Tried adding more points when scaling or when smootheness changes
            //and it is worse
                //smoothness < 20 ? 5 : smoothness < 25 ? 5 : smoothness < 35 ? 5 : 5 ;
            double deltaAngle = 90.0 / pointsCount;
            PointList pl = reverse ? new PointList() : null;
            for (int k = 0; k <= pointsCount; k++) {
                double angle = Math.toRadians(startAngle + 
                       direction * deltaAngle * k);
                double x = centerX - radius * Math.cos(angle);
                double y = centerY - radius * Math.sin(angle);
                if (reverse) {
                    pl.addPoint(new PrecisionPoint(x, y));
                } else {
                    newPoints.addPoint(new PrecisionPoint(x, y));
                }
            }
            if (reverse) {
                pl.reverse();
                newPoints.addAll(pl);
            }
        }
        newPoints.addPoint(points.getLastPoint());
        return newPoints;
    }

}
