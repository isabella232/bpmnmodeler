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
 * Date          	Author             Changes 
 * 11 Aug 2006   	BIlchyshyn         Created 
 **/

package org.eclipse.stp.bpmn.tools;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionDimension;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;
import org.eclipse.swt.SWT;

/**
 * TweakedResizeTracker
 * 
 * @author mpeleshchyshyn
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class PoolResizeTracker extends ActivityResizeTracker {

    /**
     * Constructs a resize tracker that resizes in the specified direction. The
     * direction is specified using {@link PositionConstants#NORTH},
     * {@link PositionConstants#NORTH_EAST}, etc.
     * 
     * @param owner
     *            of the resize handle which returned this tracker
     * @param direction
     *            the direction
     */
    public PoolResizeTracker(GraphicalEditPart owner, int direction) {
        super(owner, direction);
    }

    /**
     * @see org.eclipse.gef.tools.SimpleDragTracker#updateSourceRequest()
     */
    protected void updateSourceRequest() {
        ChangeBoundsRequest request = (ChangeBoundsRequest) getSourceRequest();
        Dimension dragDelta = getDragMoveDelta();
        
        Point location = new Point(getLocation());
        Point moveDelta = new Point(0, 0);
        Dimension resizeDelta = new Dimension(0, 0);
        int direction = getResizeDirection();
        List parts = getOperationSet();
        
        request.setConstrainedResize(false);
        request.setCenteredResize(getCurrentInput().isModKeyDown(SWT.MOD1));

        GraphicalEditPart owner = getOwner();
        PrecisionRectangle sourceRect = getSourceRectangle();
        SnapToHelper snapToHelper = getSnapToHelper();

        //
        // When the length of a pool is modified, all pools, are modified to
        // have the same length.
        //
        
        List<PoolEditPart> siblings = collectPoolEditParts(parts);

        
        if (siblings != null
                && ((direction == PositionConstants.EAST)
                        || (direction == PositionConstants.WEST)
//                        || (direction == PositionConstants.NORTH_EAST)
//                        || (direction == PositionConstants.NORTH_WEST)
//                        || (direction == PositionConstants.SOUTH_EAST)
//                        || (direction == PositionConstants.SOUTH_WEST)
                        )) {
            for (PoolEditPart s : siblings) {
                if (!parts.contains(s)) {
                    parts.add(s);
                }
            }
        }

        boolean northernResize = (request.getResizeDirection() & PositionConstants.NORTH) != 0;
        Dimension minSize = getMinPoolSize(parts, northernResize);
        Dimension siblingsMinSize = getMinPoolSize(siblings, northernResize);
        Dimension figureSize = owner.getFigure().getSize();
        RootEditPart rootEditPart = owner.getRoot();
        if (rootEditPart instanceof ScalableFreeformRootEditPart) {
            //compute the zoom
            ScalableFreeformRootEditPart rootSc =
                (ScalableFreeformRootEditPart) rootEditPart;
            double zoom = rootSc.getZoomManager().getZoom();
            minSize.scale(zoom);
            siblingsMinSize.scale(zoom);
            figureSize.scale(zoom);
            //compute the scroll.
            Point pt = ((Viewport)rootSc.getFigure()).getViewLocation();
            figureSize.performTranslate(-pt.x, -pt.y);
            minSize.performTranslate(-pt.x, -pt.y);
            siblingsMinSize.performTranslate(-pt.x, -pt.y);
        }

        if (((direction & PositionConstants.NORTH) != 0) && (parts.size() < 2)) {
            // prevents children shapes from clipping

            if (getCurrentInput().isControlKeyDown()) {
                if (minSize.height > (figureSize.height - 2 * dragDelta.height)) {
                    dragDelta.height =
                            (figureSize.height - minSize.height) / 2;
                }
                resizeDelta.height -= dragDelta.height;
            } else if (minSize.height > (figureSize.height - dragDelta.height)) {
                dragDelta.height = figureSize.height - minSize.height;
            }
            moveDelta.y += dragDelta.height;
            resizeDelta.height -= dragDelta.height;
        }
        if (((direction & PositionConstants.SOUTH) != 0) && (parts.size() < 2)) {
            if (getCurrentInput().isControlKeyDown()) {
                if (minSize.height > (figureSize.height + 2 * dragDelta.height)) {
                    dragDelta.height =
                            -(figureSize.height - minSize.height) / 2;
                }
                moveDelta.y -= dragDelta.height;
                resizeDelta.height += dragDelta.height;
            } else if (minSize.height > (figureSize.height + dragDelta.height)) {
                dragDelta.height =  -(figureSize.height - minSize.height);
            }
            resizeDelta.height += dragDelta.height;
        }
        if ((direction & PositionConstants.WEST) != 0) {
            if (siblingsMinSize.width > (figureSize.width - dragDelta.width)) {
                dragDelta.width = figureSize.width - siblingsMinSize.width;
            }
            moveDelta.x += dragDelta.width;
            resizeDelta.width -= dragDelta.width;
        }
        if ((direction & PositionConstants.EAST) != 0) {
            if (siblingsMinSize.width > (figureSize.width + dragDelta.width)) {
                dragDelta.width = -(figureSize.width - siblingsMinSize.width);
            }
            resizeDelta.width += dragDelta.width;
        }

        request.setMoveDelta(moveDelta);
        request.setSizeDelta(resizeDelta);
        request.setLocation(location);
        request.setEditParts(parts);
        request.getExtendedData().clear();

        if (!getCurrentInput().isAltKeyDown() && snapToHelper != null) {
            PrecisionRectangle rect = sourceRect.getPreciseCopy();
            rect.translate(moveDelta);
            rect.resize(resizeDelta);
            PrecisionRectangle result = new PrecisionRectangle();

            snapToHelper.snapRectangle(request, request.getResizeDirection(),
                    rect, result);
            if (request.isCenteredResize()) {
                if (result.preciseX != 0.0)
                    result.preciseWidth += -result.preciseX;
                else if (result.preciseWidth != 0.0) {
                    result.preciseX = -result.preciseWidth;
//                    result.preciseWidth *= 2.0;
                }

                if (result.preciseY != 0.0)
                    result.preciseHeight += -result.preciseY;
                else if (result.preciseHeight != 0.0) {
                    result.preciseY = -result.preciseHeight;
//                    result.preciseHeight *= 2.0;
                }
                result.updateInts();
            }
            moveDelta.x = 16 - getSourceRectangle().x;
            	
            PrecisionPoint preciseMove = new PrecisionPoint(moveDelta.x, result.y + moveDelta.y);

            PrecisionDimension preciseResize = new PrecisionDimension(
                    result.width + resizeDelta.width, result.height
                            + resizeDelta.height);

            request.setMoveDelta(preciseMove);
            request.setSizeDelta(preciseResize);
        }
    }

    /**
     * If it finds amongst the selected edit parts a pool. 
     * collects all its sibling pools. Otherwise returns null
     * @param selectedParts
     * @return
     */
    private List<PoolEditPart> collectPoolEditParts(List selectedParts) {
        
        for (Object part : selectedParts) {
            if (part instanceof PoolEditPart) {
                List<PoolEditPart> res = new ArrayList<PoolEditPart>();
                PoolEditPart p = (PoolEditPart)part;
                for (Object child : p.getParent().getChildren()) {
                    if (child instanceof PoolEditPart) {
                        res.add((PoolEditPart)child);
                    }
                }
                return res;
            }
        }
        return null;
    }

    /**
     * 
     * @param parts
     * @return the minimal pool size.
     */
    private Dimension getMinPoolSize(List parts, boolean north) {
        Dimension result = new Dimension(200, 50);

        if (parts == null) {
            return result;
        }

        for (int i = 0; i < parts.size(); i++) {
            if (parts.get(i) instanceof PoolEditPart) {
                PoolEditPart pool = (PoolEditPart) parts.get(i);
                IGraphicalEditPart poolCompartment = pool
                        .getChildBySemanticHint(Integer
                                .toString(PoolPoolCompartmentEditPart.VISUAL_ID));
                int nameWidth = poolCompartment.getFigure().getBounds().x + 1;
                
                // now take in account the shapes in the pool
                Point minChildLocation = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
                Dimension maxRoomOfChildren = new Dimension(0, 0);
                for (Object ep : poolCompartment.getChildren()) {
                	if (ep instanceof IGraphicalEditPart) {
                		// we use the figure as width and lengths may be 
                		// not initialized on the views objects
                		IFigure figure = ((IGraphicalEditPart) ep).getFigure();
                		Rectangle bounds = figure.getBounds();
                		maxRoomOfChildren.height = Math.max(bounds.y + 
                				bounds.height, maxRoomOfChildren.height);
                		maxRoomOfChildren.width = Math.max(bounds.x +
                				bounds.width, maxRoomOfChildren.width);
                		minChildLocation.x = Math.min(bounds.x, minChildLocation.x);
                		minChildLocation.y = Math.min(bounds.y, minChildLocation.y);
                	}
                }
                maxRoomOfChildren.expand(PoolPoolCompartmentEditPart.INSETS.
                		getWidth(), PoolPoolCompartmentEditPart.INSETS.getHeight() + 2);
                minChildLocation.x -= PoolPoolCompartmentEditPart.INSETS.getWidth();
                minChildLocation.y -= PoolPoolCompartmentEditPart.INSETS.getHeight() + 2;
                if (!north) {
                result.height = Math.max(maxRoomOfChildren.height, result.height);
                result.width = Math.max(maxRoomOfChildren.width + nameWidth, 
                        result.width);
                } else {
                    result.height = Math.max(poolCompartment.getFigure().getBounds().height - minChildLocation.y, result.height);
                    result.width = Math.max(poolCompartment.getFigure().getBounds().width - minChildLocation.x, result.width);
                }
                
            }
        }

        return result;
    }
    
}
