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
 * Date           	Author              Changes 
 * 12 Sep 2006   	MPeleshchyshyn  	Created 
 **/

package org.eclipse.stp.bpmn.tools;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionDimension;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBorderCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;
import org.eclipse.swt.SWT;

/**
 * Resize tracker that limits minimal size od the subprocess based on figures
 * that are inside body compartment.
 * 
 * @author MPeleshchyshyn
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class SubProcessResizeTracker extends ActivityResizeTracker {

    public SubProcessResizeTracker(GraphicalEditPart owner, int direction) {
        super(owner, direction);
    }

    /**
     * @see org.eclipse.gef.tools.SimpleDragTracker#updateSourceRequest()
     */
    protected void updateSourceRequest() {
        ChangeBoundsRequest request = (ChangeBoundsRequest) getSourceRequest();
        Dimension d = getDragMoveDelta();

        Point location = new Point(getLocation());
        Point moveDelta = new Point(0, 0);
        Dimension resizeDelta = new Dimension(0, 0);

        IGraphicalEditPart owner = (IGraphicalEditPart) getOwner();
        PrecisionRectangle sourceRect = getSourceRectangle();
        SnapToHelper snapToHelper = getSnapToHelper();

        if (getCurrentInput().isShiftKeyDown() && owner != null) {
            request.setConstrainedResize(true);

            int origHeight = owner.getFigure().getBounds().height;
            int origWidth = owner.getFigure().getBounds().width;
            float ratio = 1;

            if (origWidth != 0 && origHeight != 0)
                ratio = ((float) origHeight / (float) origWidth);

            if (getResizeDirection() == PositionConstants.SOUTH_EAST) {
                if (d.height > (d.width * ratio))
                    d.width = (int) (d.height / ratio);
                else
                    d.height = (int) (d.width * ratio);
            } else if (getResizeDirection() == PositionConstants.NORTH_WEST) {
                if (d.height < (d.width * ratio))
                    d.width = (int) (d.height / ratio);
                else
                    d.height = (int) (d.width * ratio);
            } else if (getResizeDirection() == PositionConstants.NORTH_EAST) {
                if (-(d.height) > (d.width * ratio))
                    d.width = -(int) (d.height / ratio);
                else
                    d.height = -(int) (d.width * ratio);
            } else if (getResizeDirection() == PositionConstants.SOUTH_WEST) {
                if (-(d.height) < (d.width * ratio))
                    d.width = -(int) (d.height / ratio);
                else
                    d.height = -(int) (d.width * ratio);
            }
        } else
            request.setConstrainedResize(false);

        request.setCenteredResize(getCurrentInput().isModKeyDown(SWT.MOD1));

        Dimension minSize = new Dimension(0, 0);
        SubProcessSubProcessBodyCompartmentEditPart bodyEditPart =
        	(SubProcessSubProcessBodyCompartmentEditPart) 
        	owner.getChildBySemanticHint(BpmnVisualIDRegistry.getType(
				SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID));
        boolean isCollapsed = ((Boolean) bodyEditPart
                .getStructuralFeatureValue(NotationPackage.eINSTANCE
                        .getDrawerStyle_Collapsed())).booleanValue();
        if (!isCollapsed) {
        	minSize = getSubProcessMinSize(owner);
        }
        Dimension figureSize = owner.getFigure().getSize();
        RootEditPart rootEditPart = owner.getRoot();
        if (rootEditPart instanceof ScalableFreeformRootEditPart) {
            double zoom = ((ScalableFreeformRootEditPart) rootEditPart)
                    .getZoomManager().getZoom();
            minSize.scale(zoom);
            figureSize.scale(zoom);
        }

        if ((getResizeDirection() & PositionConstants.NORTH) != 0) {
            if (getCurrentInput().isControlKeyDown()) {
                if (minSize.height > figureSize.height - 2 * d.height) {
                    d.setSize(new Dimension(d.width,
                            (figureSize.height - minSize.height) / 2));
                }
                resizeDelta.height -= d.height;
            } else if (minSize.height > figureSize.height - d.height) {
                d.setSize(new Dimension(d.width, figureSize.height
                        - minSize.height));
            }
            moveDelta.y += d.height;
            resizeDelta.height -= d.height;
        }
        if ((getResizeDirection() & PositionConstants.SOUTH) != 0) {
            if (getCurrentInput().isControlKeyDown()) {
                if (minSize.height > figureSize.height + 2 * d.height) {
                    d.setSize(new Dimension(d.width,
                            -(figureSize.height - minSize.height) / 2));
                }
                moveDelta.y -= d.height;
                resizeDelta.height += d.height;
            } else if (minSize.height > figureSize.height + d.height) {
                d.setSize(new Dimension(d.width,
                        -(figureSize.height - minSize.height)));
            }
            resizeDelta.height += d.height;
        }
        if ((getResizeDirection() & PositionConstants.WEST) != 0) {
            if (getCurrentInput().isControlKeyDown()) {
                if (minSize.width > figureSize.width - 2 * d.width) {
                    d.setSize(new Dimension(
                            (figureSize.width - minSize.width) / 2, d.height));
                }
                resizeDelta.width -= d.width;
            } else if (minSize.width > figureSize.width - d.width) {
                d.setSize(new Dimension(figureSize.width - minSize.width,
                        d.height));
            }
            moveDelta.x += d.width;
            resizeDelta.width -= d.width;
        }
        if ((getResizeDirection() & PositionConstants.EAST) != 0) {
            if (getCurrentInput().isControlKeyDown()) {
                if (minSize.width > figureSize.width + 2 * d.width) {
                    d.setSize(new Dimension(
                            -(figureSize.width - minSize.width) / 2, d.height));
                }
                moveDelta.x -= d.width;
                resizeDelta.width += d.width;
            } else if (minSize.width > figureSize.width + d.width) {
                d.setSize(new Dimension(-(figureSize.width - minSize.width),
                        d.height));
            }
            resizeDelta.width += d.width;
        }

        request.setMoveDelta(moveDelta);
        request.setSizeDelta(resizeDelta);
        request.setLocation(location);
        request.setEditParts(getOperationSet());

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

            PrecisionPoint preciseMove = new PrecisionPoint(result.x
                    + moveDelta.x, result.y + moveDelta.y);

            PrecisionDimension preciseResize = new PrecisionDimension(
                    result.width + resizeDelta.width, result.height
                            + resizeDelta.height);
            if (minSize.width > figureSize.width + preciseResize.width) {
                preciseResize.preciseWidth = resizeDelta.width;
                preciseResize.updateInts();
            }
            if (minSize.height > figureSize.height + preciseResize.height) {
                preciseResize.preciseHeight = resizeDelta.height;
                preciseResize.updateInts();
            }
            request.setMoveDelta(preciseMove);
            request.setSizeDelta(preciseResize);
        }
    }

    /**
     * Calculates subprocess' minimal size.
     * 
     * @param subprocessEditPart
     *            the subprocess edit part
     * @return calculated minimal size of the s
     */
    public Dimension getSubProcessMinSize(GraphicalEditPart subprocessEditPart) {
    	SubProcessSubProcessBodyCompartmentEditPart body = null;
    	SubProcessSubProcessBorderCompartmentEditPart border = null;
    	SubProcessNameEditPart name = null;
    	for (Object child : subprocessEditPart.getChildren()) {
    		if (child instanceof SubProcessSubProcessBodyCompartmentEditPart) {
    			body = (SubProcessSubProcessBodyCompartmentEditPart) child;
    		}
    		if (child instanceof SubProcessSubProcessBorderCompartmentEditPart) {
    			border = (SubProcessSubProcessBorderCompartmentEditPart) child;
    		}
    		if (child instanceof SubProcessNameEditPart) {
    			name = (SubProcessNameEditPart) child;
    		}
    	}
    	if (body == null) {
    		return new Dimension(0, 0);
    	}
        // now take in account the shapes in the pool
        Dimension maxRoomOfChildren = new Dimension(0, 0);
        for (Object ep : body.getChildren()) {
        	if (ep instanceof IGraphicalEditPart) {
        		// we use the figure as width and lengths may be 
        		// not initialized on the views objects
        		IFigure figure = ((IGraphicalEditPart) ep).getFigure();
        		Rectangle bounds = figure.getBounds();
        		maxRoomOfChildren.height = Math.max(bounds.y + 
        				bounds.height, maxRoomOfChildren.height);
        		maxRoomOfChildren.width = Math.max(bounds.x + 
        				bounds.width, maxRoomOfChildren.width);
        	}
        }
        maxRoomOfChildren.expand(SubProcessEditPart.INSETS.
        		getWidth(), SubProcessEditPart.INSETS.getHeight() + 2);
        maxRoomOfChildren.height += border.getFigure().getBounds().height;
        if (name != null) {
        	maxRoomOfChildren.height += name.getFigure().getBounds().height;
        }
        return maxRoomOfChildren;
//        return ((SubProcessEditPart) subprocessEditPart).calcMinSize();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.tools.AbstractTool#getDragMoveDelta()
     */
    @Override
    protected Dimension getDragMoveDelta() {
        Dimension d = super.getDragMoveDelta();
        // d = new Dimension(d.width * 2, d.height * 2);
        return d;
    }
}
