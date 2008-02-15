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
 * Nov 24, 2006     hmalphettes         Created
 **/
package org.eclipse.stp.bpmn.figures.connectionanchors.impl;

import java.util.Set;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.diagram.edit.parts.MessagingEdgeEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SequenceEdgeEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart.ActivityFigure;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart.SubProcessFigure;
import org.eclipse.stp.bpmn.figures.activities.ActivityDiamondFigure;
import org.eclipse.stp.bpmn.figures.activities.ActivityOvalFigure;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchor;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchorSupport;
import org.eclipse.stp.bpmn.figures.connectionanchors.WrapperNodeFigureEx;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchor.INodeFigureAnchorTerminalUpdatable;
import org.eclipse.stp.bpmn.figures.router.EdgeRectilinearRouter;

/**
 * <p>
 * Computes the anchor's position according to its usage 
 * and the preferences of the diagram (default slideable, position constrained
 * according to the type of connection in the domain model).
 * </p>
 * <p>
 * If the anchor appears to not have that information set, it will behave like 
 * a default slideable anchor.
 * </p>
 * <p>
 * There was another alternative:
 * have the edge edit part update the anchor itself.
 * </p>
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BpmnAwareAnchorSupport implements IModelAwareAnchorSupport {
    
    /**
     * Method called by the anchor to compute its location.
     * 
     * @param reference The reference point.
     * @return The location to use.
     */
    public Point getLocation(IModelAwareAnchor anchor, Point reference) {
        if (anchor.getConnectionType() == null ||
                anchor.getAnchorPositionningStyle() ==
                    IModelAwareAnchor.POSITIONNING_SLIDEABLE) {
            return returnDefaultLocation(anchor, reference);
        }
        if (anchor.getConnectionType().startsWith(
                String.valueOf(SequenceEdgeEditPart.VISUAL_ID))) {
            //it is a sequence edge
            if (anchor.isSourceAnchor() == IModelAwareAnchor.UNKNOWN_ANCHOR) {
                //it must be on the left-side of the owner's figure.
                //we don't know.
                return returnDefaultLocation(anchor, reference);
            }
            Rectangle thisBox = getOwnerBounds(anchor);
            anchor.getOwner().translateToAbsolute(thisBox);
            Point thisCenter = thisBox.getCenter();
            Point res = new Point();
            if (!anchor.getConnectionType().equals(
                    String.valueOf(SequenceEdgeEditPart.VISUAL_ID))) {
                //this is for an event handler
                //shape on the border of a sub-process.
                res.x = thisCenter.x;
                res.y = thisCenter.y + thisBox.height/2;
            } else {
                if (anchor.isSourceAnchor() == IModelAwareAnchor.SOURCE_ANCHOR) {
                    //it must be on the right-side of the owner's figure.
                    res.x = thisCenter.x + thisBox.width/2;
                } else {//tis target anchor
                    //it must be on the left-side of the owner's figure.
                    res.x = thisCenter.x - thisBox.width/2;
                }

                if (anchor.getCount() > 0 && anchor.getOrderNumber() != -1) {
                    int constraint = EdgeRectilinearRouter.NO_CONSTRAINT;
                    //we also want to distribute them on the y axis if
                    //it is edges of a gateway.
                    if (anchor.getConnectionOwner() instanceof SequenceEdgeEditPart.EdgeFigure) {
                        constraint = anchor.isSourceAnchor() == IModelAwareAnchor.SOURCE_ANCHOR ? 
                                ((SequenceEdgeEditPart.EdgeFigure) anchor.getConnectionOwner()).getSourceGatewayConstraint() : 
                                    ((SequenceEdgeEditPart.EdgeFigure) anchor.getConnectionOwner()).getTargetGatewayConstraint();
                    }
                    if (constraint == EdgeRectilinearRouter.CONSTRAINT_ON_TOP) {
                        res.y = thisCenter.y - thisBox.height/2;
                        res.x = thisCenter.x;
                    } else if (constraint == EdgeRectilinearRouter.CONSTRAINT_BOTTOM) {
                        res.x = thisCenter.x;
                        res.y = thisCenter.y + thisBox.height/2;
                    }  else if (constraint == EdgeRectilinearRouter.CONSTRAINT_MIDDLE) {
                        res.y = thisCenter.y;
                    } else {
                        //well let's try distributing on the y axis for the rest of them.
                        int height = thisBox.height;
                        res.y = thisCenter.y - height / 2 + 
                        (height / (anchor.getCount() + 1))
                        * (anchor.getOrderNumber() + 1);
                    }
                } else {
                    res.y = thisCenter.y;
                }
            }
            return res;
        }
        if (anchor.getConnectionType().startsWith(
                String.valueOf(MessagingEdgeEditPart.VISUAL_ID))) {
            //it is a messaging edge
            //it is either on the top, either on the bottom depending on
            //where is the other-side of the connection
            if (anchor.getOwner() == null ||
                    anchor.getConnectionOwner() == null ||
                    anchor.isSourceAnchor() == IModelAwareAnchor.UNKNOWN_ANCHOR) {
                return returnDefaultLocation(anchor, reference);
            }
            Connection conn = anchor.getConnectionOwner();
            
            Rectangle thisBox = getOwnerBounds(anchor);
            anchor.getOwner().translateToAbsolute(thisBox);
            Point thisCenter = thisBox.getCenter();
            Point otherCenter = null;
            if (anchor.isSourceAnchor() == IModelAwareAnchor.SOURCE_ANCHOR) {
                if (conn.getTargetAnchor().getOwner() == null) {
                    //this is usually the case for an XYAnchor
                    otherCenter = conn.getTargetAnchor().getReferencePoint();
                } else {
                    otherCenter = conn.getTargetAnchor().getOwner().getBounds().getCenter();
                    conn.getTargetAnchor().getOwner().translateToAbsolute(otherCenter);
                }
            } else {
                if (conn.getSourceAnchor().getOwner() == null) {
                    otherCenter = conn.getSourceAnchor().getReferencePoint();
                } else {
                    otherCenter = conn.getSourceAnchor().getOwner().getBounds().getCenter();
                    conn.getSourceAnchor().getOwner().translateToAbsolute(otherCenter);
                }
            }
//            System.err.println("isSource=" + (anchor.isSourceAnchor() == IModelAwareAnchor.SOURCE_ANCHOR) +
//                    " thisCenter=" + thisCenter + " otherCenter=" + otherCenter);
            Point res = new Point();
            if (thisCenter.y > otherCenter.y) {
                //at the top.
                res.y = thisCenter.y - thisBox.height/2;
            } else {
                //at the bottom.
                res.y = thisCenter.y + thisBox.height/2;
            }
            if (anchor.getCount() > 0 && anchor.getOrderNumber() != -1) {
                int width = thisBox.width;
                res.x = thisCenter.x - width / 2 + 
                (width / (anchor.getCount() + 1))
                * (anchor.getOrderNumber() + 1);

            } else {
                res.x = thisCenter.x;
            }
            
            return res;
        }
        if (reference == null) {
            Point thisCenter = anchor.getOwner().getBounds().getCenter();
            anchor.getOwner().translateToAbsolute(thisCenter);
            return thisCenter;
        }
        return anchor.getDefaultLocation(reference);
    }
    
    /**
     * Helper method to return the default location.
     * @param anchor The anchor
     * @param reference The reference point, might be null.
     * @return
     */
    protected Point returnDefaultLocation(IModelAwareAnchor anchor, Point reference) {
        if (reference == null && anchor.getOwner() != null) {
            Point thisCenter = getOwnerBounds(anchor).getCenter();
            anchor.getOwner().translateToAbsolute(thisCenter);
            return thisCenter;
        }
        return anchor.getDefaultLocation(reference);
    }

    /**
     * Helper method to return the bounds of the owner,
     * or only the ones from its interesting feature.
     * @param anchor
     * @return the bounds to create the anchor on.
     */
    protected Rectangle getOwnerBounds(IModelAwareAnchor anchor) {
        IFigure interestingOwner = anchor.getOwner() instanceof WrapperNodeFigureEx ?
                ((WrapperNodeFigureEx) anchor.getOwner()).getSubfigure() :
                    anchor.getOwner();
    	for (Object child : interestingOwner.getChildren()) {
    		if (child instanceof ActivityDiamondFigure || 
    				child instanceof ActivityOvalFigure) {
    			return ((IFigure) child).getBounds().getCopy();
    		}
    		if (child instanceof SubProcessFigure) {
    		    if (((SubProcessFigure) child).getFigureSubProcessBodyFigure() != null &&
    		            !((SubProcessFigure) child).isCollapsed()) {
    		        Rectangle bounds = ((SubProcessFigure) child).getFigureSubProcessBodyFigure().
    		            getBounds().getCopy();
    		        bounds.height += SubProcessEditPart.INSETS.getHeight() + 1;
    		        if (((SubProcessFigure) child).getFigureSubProcessNameFigure() != null) {
    		            bounds.union(((SubProcessFigure) child).getFigureSubProcessNameFigure().getBounds().getCopy());
    		        }
    		        return bounds;
    		    }
    		}
    	}
    	
    	return anchor.getOwner().getBounds().getCopy();
    }
    
    /**
     * 
     * @param figure
     * @return true if the figure represents a gateway
     */
    private boolean isGateway(IFigure figure) {
        IFigure interestingOwner = figure instanceof WrapperNodeFigureEx ? ((WrapperNodeFigureEx) figure)
                .getSubfigure()
                : figure;
        for (Object child : interestingOwner.getChildren()) {
            if (child instanceof ActivityDiamondFigure) {
                return true;
            }
            if (child instanceof ActivityFigure) {
                return ActivityType.VALUES_GATEWAYS.contains(((ActivityFigure) child).getActivityType());
            }
        }
        return false;
    }
}
