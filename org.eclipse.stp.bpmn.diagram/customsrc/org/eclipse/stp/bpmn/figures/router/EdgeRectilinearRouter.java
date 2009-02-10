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
 * Date             Author             Changes 
 * 16 Oct 2006      bilchyshyn         Created 
 **/
package org.eclipse.stp.bpmn.figures.router;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.stp.bpmn.diagram.edit.parts.SequenceEdgeEditPart;

/**
 * Connection router for sequence edges
 * 
 * hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class EdgeRectilinearRouter extends RectilinearRouterEx {

    // those constants are used to route correctly the edge

    /**
     * no constraint at all, by default.
     */
    public static final int NO_CONSTRAINT = 0;

    /**
     * a constraint applies, the edge will need a bendpoint on top of the shape
     */
    public static final int CONSTRAINT_ON_TOP = 1;

    /**
     * a constraint applies, the edge will need a bendpoint at the bottom of the shape.
     */
    public static final int CONSTRAINT_BOTTOM = 2;
    
    /**
     * a constraint applies, the edge will be forced in the middle of the shape.
     */
    public static final int CONSTRAINT_MIDDLE = 3;

    private SequenceEdgeEditPart edge;

    /**
     * Constructs a rectilinear router tht normalizes on the x-axis first.
     */
    public EdgeRectilinearRouter() {
        super.normalizeBehavior = NORMALIZE_ON_HORIZONTAL_CENTER;
    }

    public EdgeRectilinearRouter(SequenceEdgeEditPart edge) {
        this();
        this.edge = edge;
    }


    /**
     * @param conn the <code>Connection</code> that is to be routed.
     * @return the <code>PointList</code> that is the list of points that represent
     * the closest distance possible to route the line.
     */
    public PointList routeClosestDistance(Connection conn) {
        int gatewaySourceConstraint = edge.getSourceGatewayConstraint();
        int gatewayTargetConstraint = edge.getTargetGatewayConstraint();
        if (gatewaySourceConstraint == NO_CONSTRAINT && 
                gatewayTargetConstraint == NO_CONSTRAINT) {
            return super.routeClosestDistance(conn);
        }
        PointList newLine = routeFromConstraint(conn);
        
        Point ptOrig = new Point(newLine.getFirstPoint());
        Point ptTerm = new Point(newLine.getLastPoint());
        
        newLine.removeAllPoints();
        newLine.addPoint(ptOrig);

        IMapMode mm = MapModeUtil.getMapMode(conn);

        //special algo when the sequence edge goes from right to left:
        int extra = mm.DPtoLP(16);
        Rectangle src = conn.getSourceAnchor().getOwner() == null ?
                null : getOwnerBounds(conn.getSourceAnchor()).getCopy();
        if (src == null) {
            src = new Rectangle(ptOrig.getCopy().translate(new Dimension(-extra, -extra/2)), ptOrig);
        } else {
            conn.getSourceAnchor().getOwner().translateToAbsolute(src);
            conn.translateToRelative(src);
        }
        
        
        if (normalizeBehavior == NORMALIZE_ON_HORIZONTAL_EXCEPT_FIRST_SEG) {
            if (!areAlmostEqual(ptOrig.x, ptTerm.x)) {
                newLine.addPoint(new Point(ptOrig.x, ptTerm.y));
            }
        } else {
            boolean needMiddleBendPoint = !areAlmostEqual(ptOrig.y, ptTerm.y) && ptOrig.x + extra < ptTerm.x;
            needMiddleBendPoint = needMiddleBendPoint && 
            (gatewaySourceConstraint == gatewayTargetConstraint && (
                    gatewaySourceConstraint == NO_CONSTRAINT || 
                    gatewaySourceConstraint == CONSTRAINT_MIDDLE)); 
            if (needMiddleBendPoint) {

                // there is going to be a middle bend point.
                // let's find in the list of points the two points that are part of that middle bend point
                // if it has been computed already
                Point ptOnOrigY = null;
                Point ptOnTermY = null;
                PointList oldLine = routeFromConstraint(conn);
                for (int i = 1 ; i < oldLine.size() -1 ; i++) {
                    Point bp = oldLine.getPoint(i);
                    if (bp.y == ptOrig.y) {
                        ptOnOrigY = bp;
                        break;
                    } else if (bp.y == ptTerm.y) {
                        ptOnTermY = bp;
                        break;
                    }
                }
                if (ptOnOrigY != null) {
                    if (ptOnOrigY.x >= ptTerm.x || ptOnOrigY.x <= ptOrig.x) {
                        ptOnOrigY.x = (ptOrig.x + ptTerm.x)/2;
                    }
                    newLine.addPoint(ptOnOrigY);
                    newLine.addPoint(new Point(ptOnOrigY.x, ptTerm.y));
                } else if (ptOnTermY != null) {
                    if (ptOnTermY.x >= ptTerm.x || ptTerm.x <= ptOrig.x) {
                        ptOnTermY.x = (ptOrig.x + ptTerm.x)/2;
                    }
                    newLine.addPoint(new Point(ptOnTermY.x, ptOrig.y));

                    newLine.addPoint(ptOnTermY);
                } else {
                    // normally nothing
                }
            }
        }

        
       
        // in case both constraints are applied, we will need to add bend points to help
        Point midTargetYPoint = new Point(ptTerm.x, (ptTerm.y + ptOrig.y)/2);
        // we compute the best bend point we can find around:
        PointList oldLine = routeFromConstraint(conn);
        int bestBendPointX = oldLine.getMidpoint().x;
        int bestBendPointY = oldLine.getMidpoint().y;
        if (ptOrig.x + extra > bestBendPointX 
                || ptTerm.x < bestBendPointX) {
            bestBendPointX = ptOrig.x + src.width + extra > ptTerm.x ? 
                    (ptOrig.x + ptTerm.x)/2 : ptOrig.x + src.width + extra;
        }
        if (ptOrig.y > ptTerm.y) {
            if (ptOrig.y < bestBendPointY || ptTerm.y > bestBendPointY) {
                bestBendPointY = (ptOrig.y + ptTerm.y)/2;
            }
        } else {
            if (ptOrig.y > bestBendPointY || ptTerm.y < bestBendPointY) {
                bestBendPointY = (ptOrig.y + ptTerm.y)/2;
            }
        }
        if (gatewaySourceConstraint != NO_CONSTRAINT && gatewaySourceConstraint != CONSTRAINT_MIDDLE 
                && ptOrig.x + extra < ptTerm.x) {
         // we need to route around the gateway if the activity is placed after it, but too high.
            if (gatewayTargetConstraint == gatewaySourceConstraint &&
                    gatewayTargetConstraint != NO_CONSTRAINT &&
                    gatewayTargetConstraint != CONSTRAINT_MIDDLE) {
                if (gatewaySourceConstraint == CONSTRAINT_BOTTOM) {
                    newLine.addPoint(new Point(ptOrig.x, Math.max(ptOrig.y, ptTerm.y) + extra));
                } else if (gatewaySourceConstraint == CONSTRAINT_ON_TOP) {
                    newLine.addPoint(new Point(ptOrig.x, Math.min(ptOrig.y, ptTerm.y) - extra));
                }
            } else if (ptOrig.y > ptTerm.y && gatewaySourceConstraint == CONSTRAINT_BOTTOM) {
                newLine.addPoint(new Point(ptOrig.x, ptOrig.y + extra));
                newLine.addPoint(new Point(bestBendPointX, ptOrig.y + extra));
                newLine.addPoint(new Point(bestBendPointX, ptTerm.y));
            } else if (ptOrig.y < ptTerm.y &&
                    gatewaySourceConstraint == CONSTRAINT_ON_TOP) {
                newLine.addPoint(new Point(ptOrig.x, ptOrig.y - extra));
                
                newLine.addPoint(new Point(bestBendPointX, ptOrig.y - extra));
                newLine.addPoint(new Point(bestBendPointX, ptTerm.y));
            } else {
                if (!areAlmostEqual(ptOrig.y, ptTerm.y)) {
                    newLine.addPoint(ptOrig.x, 
                            gatewayTargetConstraint != NO_CONSTRAINT ? bestBendPointY : ptTerm.y);
                }
            }
            
        }
        
        
        if (gatewayTargetConstraint != NO_CONSTRAINT   && gatewayTargetConstraint != CONSTRAINT_MIDDLE 
                && ptOrig.x + extra < ptTerm.x) {
            
           if (gatewayTargetConstraint == gatewaySourceConstraint &&
                    gatewaySourceConstraint != NO_CONSTRAINT &&
                    gatewaySourceConstraint != CONSTRAINT_MIDDLE) {
                if (gatewayTargetConstraint == CONSTRAINT_BOTTOM) {
                    newLine.addPoint(new Point(ptTerm.x, Math.max(ptOrig.y, ptTerm.y) + extra));
                } else if (gatewayTargetConstraint == CONSTRAINT_ON_TOP) {
                    newLine.addPoint(new Point(ptTerm.x, Math.min(ptOrig.y, ptTerm.y) - extra));
                }
            } else if ((ptOrig.y < ptTerm.y) && gatewayTargetConstraint == CONSTRAINT_BOTTOM) {
                if (ptOrig.y + extra > ptTerm.y) {
                    newLine.addPoint(new Point(ptOrig.x + extra, ptOrig.y));
                    newLine.addPoint(new Point(ptTerm.x, ptTerm.y + extra));
                } else {
                    newLine.addPoint(new Point(ptTerm.x, ptOrig.y));
                }
            } else if ((ptOrig.y > ptTerm.y) && gatewayTargetConstraint == CONSTRAINT_ON_TOP) {
                if (ptOrig.y - extra < ptTerm.y) {
                    newLine.addPoint(new Point(ptOrig.x + extra, ptOrig.y));
                    newLine.addPoint(new Point(ptTerm.x, ptTerm.y - extra));
                } else {
                    newLine.addPoint(new Point(ptTerm.x, ptOrig.y));
                }
            } else {
                if (!areAlmostEqual(ptOrig.y, ptTerm.y)) {
                    newLine.addPoint(ptTerm.x, 
                            gatewaySourceConstraint != NO_CONSTRAINT ? bestBendPointY : ptOrig.y);
                }
            }
        }

        newLine.addPoint(ptTerm);
        return newLine;
    }

    /**
     * Override the super method, we don't route around when the source or
     * the target have a constraint.
     */
    protected void routeAroundSelfForClosestDistance(Connection conn, PointList newLine) {
        int sourceGatewayConstraint = edge.getSourceGatewayConstraint();
        int targetGatewayConstraint = edge.getTargetGatewayConstraint();
        if ((targetGatewayConstraint != NO_CONSTRAINT && 
                targetGatewayConstraint != CONSTRAINT_MIDDLE) || 
                (sourceGatewayConstraint != NO_CONSTRAINT  && 
                sourceGatewayConstraint != CONSTRAINT_MIDDLE)) {
            Point ptOrig = newLine.getPoint(0);
            Point ptTerm = newLine.getLastPoint();
            IMapMode mm = MapModeUtil.getMapMode(conn);

            //special algo when the sequence edge goes from right to left:
            int tolerance = mm.DPtoLP(3);
            int extra = mm.DPtoLP(16);
            Rectangle src = conn.getSourceAnchor().getOwner() == null ?
                    null : getOwnerBounds(conn.getSourceAnchor()).getCopy();
            if (src == null) {
                src = new Rectangle(ptOrig.getCopy().translate(new Dimension(-extra, -extra/2)), ptOrig);
            } else {
                conn.getSourceAnchor().getOwner().translateToAbsolute(src);
                conn.translateToRelative(src);
            }
            Rectangle target = conn.getTargetAnchor().getOwner() == null ?
                    null : getOwnerBounds(conn.getTargetAnchor()).getCopy();
            if (target == null) {
                target = new Rectangle(ptTerm.getCopy().translate(new Dimension(extra, -extra/2)), ptTerm);
            } else {
                conn.getTargetAnchor().getOwner().translateToAbsolute(target);
                conn.translateToRelative(target);
            }
            if (ptOrig.x + extra >= ptTerm.x) {
                newLine.setSize(newLine.size()-1);
                
                //compute how much away we should be: 1/2 of the biggest height
                int heightOwnerSrc = src.height;
                int heightOwnerTarget = target.height;

                int topRightSrcY = src.y;
                int bottomRightSrcY = src.y + src.height;
                int topLeftTargetY = target.y;
                int bottomLeftTargetY = target.y + target.height;

                //now compute where we draw the line: above or below the 2 shapes.
                PointList oldLine = routeFromConstraint(conn);
                int middleY = oldLine.getMidpoint().y;
                int aboveY = middleY;
                int farRightX = -1;
                //see if we should go in between the 2 shapes:
                if (topRightSrcY > bottomLeftTargetY + extra && sourceGatewayConstraint != CONSTRAINT_BOTTOM) {
                    //src is below the target go in between:
                    if (aboveY > topRightSrcY || aboveY < (bottomLeftTargetY +extra)) {
                        aboveY = (topRightSrcY + bottomLeftTargetY) /2;
                    }
                    farRightX = ptOrig.x + extra;
                } else if (topLeftTargetY > bottomRightSrcY + extra) {
                    //target is below the src go in between:
                    if (aboveY > topLeftTargetY || aboveY < (bottomRightSrcY + extra)) { 
                        aboveY = (topLeftTargetY + bottomRightSrcY) /2;
                    }
                    farRightX = ptOrig.x + extra;
                } else {
                    int extraY = //extra;
                        Math.max(extra,
                                Math.min(heightOwnerSrc/2, heightOwnerTarget/2));

                    //min y if we go above
                    int aboveYone = Math.min(topRightSrcY, topLeftTargetY);
                    //max y if we go below
                    int belowYone = Math.max(bottomLeftTargetY, bottomRightSrcY);

                    //in that case, always go below as we are already at
                    //the bottom-side of a sub-process
                    aboveY = belowYone + extraY;
                    farRightX = Math.max(ptOrig.x + extra,
                            ptTerm.x + extra + target.width);
                }
                int farLeft = ptTerm.x - extra;

                Point origPlus = new Point(farRightX, ptOrig.y);
                Point origAbove = new Point(origPlus.x, aboveY);
                Point termMinus = new Point(farLeft, ptTerm.y);
                Point termAbove = new Point(termMinus.x, aboveY);
                if (sourceGatewayConstraint == NO_CONSTRAINT || 
                        sourceGatewayConstraint == CONSTRAINT_MIDDLE) {
                    newLine.addPoint(origPlus);
                    newLine.addPoint(origAbove);
                } else {
                    if (ptOrig.x + extra >= ptTerm.x) {
                        if (sourceGatewayConstraint == CONSTRAINT_ON_TOP) {
                            if (targetGatewayConstraint == CONSTRAINT_BOTTOM) {
                                newLine.addPoint(new Point(ptOrig.x, aboveY));
                                newLine.addPoint(new Point(ptTerm.x, aboveY));
                                newLine.addPoint(ptTerm);
                                return;
                            }
                            
                            newLine.addPoint(new Point(ptOrig.x, ptOrig.y - extra));
                            int minx = Math.min(ptOrig.x, ptTerm.x);
                            newLine.addPoint(new Point(minx - extra, ptOrig.y - extra));
                            newLine.addPoint(new Point(minx - extra, ptTerm.y));
                            if (targetGatewayConstraint == NO_CONSTRAINT ||
                                    targetGatewayConstraint == CONSTRAINT_MIDDLE) {
                                newLine.addPoint(ptTerm);
                                return;
                            }
                        } else {
                            newLine.addPoint(new Point(ptOrig.x, aboveY));
                        }
                    }
                }
                if (targetGatewayConstraint == NO_CONSTRAINT || 
                        targetGatewayConstraint == CONSTRAINT_MIDDLE) {
                    if (ptOrig.x + extra <= ptTerm.x) {
                    } else {
                    newLine.addPoint(termAbove);
                    newLine.addPoint(termMinus);
                    }
                } else {
                    newLine.addPoint(new Point(ptTerm.x, aboveY));
                }
                newLine.addPoint(ptTerm);
            }
            return;
        }
        super.routeAroundSelfForClosestDistance(conn, newLine);
    }
}
