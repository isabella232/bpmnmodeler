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
 * 12 Sep 2006      MPeleshchyshyn      Created 
 **/

package org.eclipse.stp.bpmn.tools;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionDimension;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.swt.SWT;

/**
 * Resize tracker that limits minimal size of the subprocess based on figures
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

        SubProcessEditPart owner = (SubProcessEditPart) getOwner();
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

//        Dimension minSize = new Dimension(0, 0);
        SubProcessSubProcessBodyCompartmentEditPart bodyEditPart =
            (SubProcessSubProcessBodyCompartmentEditPart) 
            owner.getChildBySemanticHint(BpmnVisualIDRegistry.getType(
                SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID));
        boolean isCollapsed = ((Boolean) bodyEditPart
                .getStructuralFeatureValue(NotationPackage.eINSTANCE
                        .getDrawerStyle_Collapsed())).booleanValue();
        
        double zoom = owner.getZoom();
        
//        Dimension figureSize = owner.getFigure().getSize();
        if (!isCollapsed) {
//            minSize = BpmnShapesDefaultSizes.getSubProcessMinSize(owner, false);
//            minSize.scale(zoom);
        }
//        figureSize.scale(zoom);

        if ((getResizeDirection() & PositionConstants.NORTH) != 0) {
            if (getCurrentInput().isControlKeyDown()) {
                resizeDelta.height -= d.height;
            }
            moveDelta.y += d.height;
            resizeDelta.height -= d.height;
        }
        if ((getResizeDirection() & PositionConstants.SOUTH) != 0) {
            if (getCurrentInput().isControlKeyDown()) {
                moveDelta.y -= d.height;
                resizeDelta.height += d.height;
            }
            resizeDelta.height += d.height;
        }
        if ((getResizeDirection() & PositionConstants.WEST) != 0) {
            if (getCurrentInput().isControlKeyDown()) {
                resizeDelta.width -= d.width;
            }
            moveDelta.x += d.width;
            resizeDelta.width -= d.width;
        }
        if ((getResizeDirection() & PositionConstants.EAST) != 0) {
            if (getCurrentInput().isControlKeyDown()) {
                moveDelta.x -= d.width;
                resizeDelta.width += d.width;
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
            request.setMoveDelta(preciseMove);
            request.setSizeDelta(preciseResize);
        }
    }

}
