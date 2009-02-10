/******************************************************************************
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************/

/** 
 * Date         	Author          Changes 
 * 30 Jan 2007   	hmalphettes  	Created 
 **/

package org.eclipse.stp.bpmn.tools;

import java.lang.reflect.Field;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionDimension;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.SharedCursors;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.handles.HandleBounds;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.gef.tools.DragEditPartsTracker;
import org.eclipse.gef.tools.ResizeTracker;
import org.eclipse.gef.tools.TargetingTool;
import org.eclipse.gef.tools.ToolUtilities;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;

/**
 * A Tracker for dragging a resize handle. The ResizeTracker will resize all of
 * the selected editparts in the viewer which understand a RESIZE request. A
 * {@link ChangeBoundsRequest} is sent to each member of the operation set. The
 * tracker allows for the resize direction to be specified in the constructor.
 * <p>
 * This can also be used for other shapes. It will use the custom handle figures
 * and allow resizing in any direction.
 * It is used by the bpmn text annotation.
 * </p>
 * This basically extends the DragEditPartTracker to benefit from the auto-scroll
 * that is supported there.
 * Then the resize-code is taken from the ResizeTracker.
 * @see ResizeTracker
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ActivityResizeTracker extends DragEditPartsTracker {

    private static Field snapToHelperField;
    private static Field sourceRelativeStartPointField;
    private static Field sourceRectangleField;

    private static void init() {
        if (sourceRectangleField != null) {
            return;
        }
        try {
            sourceRectangleField = DragEditPartsTracker.class
                    .getDeclaredField("sourceRectangle"); //$NON-NLS-1$
            sourceRectangleField.setAccessible(true);
            snapToHelperField = DragEditPartsTracker.class
                .getDeclaredField("snapToHelper"); //$NON-NLS-1$
            snapToHelperField.setAccessible(true);
            sourceRelativeStartPointField = DragEditPartsTracker.class
                .getDeclaredField("sourceRelativeStartPoint"); //$NON-NLS-1$
            sourceRelativeStartPointField.setAccessible(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    
    private static int FLAG_TARGET_FEEDBACK = AbstractTool.MAX_FLAG<<1<<1;
    private static final int FLAG_SOURCE_FEEDBACK = AbstractTool.MAX_FLAG << 1;

    /**
     * The maximum bit-mask used as a flag constant.  Subclasses should start using the next
     * highest bitmask.
     */
    protected static final int MAX_FLAG = FLAG_SOURCE_FEEDBACK;

    private int direction;
    private GraphicalEditPart owner;
    private Request sourceRequest;
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
    public ActivityResizeTracker(GraphicalEditPart owner, int direction) {
        super(owner);
        init();
        this.owner = owner;
        this.direction = direction;
        setDisabledCursor(SharedCursors.NO);
    }

    protected GraphicalEditPart getOwner() {
        return owner;
    }

    protected SnapToHelper getSnapToHelper() {
        try {
            return (SnapToHelper) snapToHelperField.get(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    protected PrecisionRectangle getSourceRectangle() {
        try {
            return (PrecisionRectangle) sourceRectangleField.get(this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    protected void setSourceRectangle(PrecisionRectangle sourceRect) {
        try {
            sourceRectangleField.set(this, sourceRect);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void setSnapToHelper(SnapToHelper snapToHelper) {
        try {
            snapToHelperField.set(this, snapToHelper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void setSourceRelativeStartPointField(PrecisionPoint sourceRelativeStartPoint) {
        try {
            sourceRelativeStartPointField.set(this, sourceRelativeStartPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @see org.eclipse.gef.Tool#activate()
     */
    public void activate() {
        super.activate();
        if (owner != null) {
            if (getTargetEditPart() != null)
                setSnapToHelper((SnapToHelper)getTargetEditPart().getAdapter(SnapToHelper.class));
        
            IFigure figure = owner.getFigure();
            if (figure instanceof HandleBounds)
                setSourceRectangle(
                        new PrecisionRectangle(((HandleBounds)figure).getHandleBounds()));
            else
                setSourceRectangle(new PrecisionRectangle(figure.getBounds()));
            figure.translateToAbsolute(getSourceRectangle());
        }
    }    
    
    /**
     * @see org.eclipse.gef.tools.AbstractTool#commitDrag()
     */
    public void commitDrag() {
        eraseTargetFeedback();
        super.commitDrag();
    }

    /**
     * Returns all selected parts which understand resizing.
     * @see org.eclipse.gef.tools.AbstractTool#createOperationSet()
     */
    protected List createOperationSet() {
        List list = super.createOperationSet();
        ToolUtilities.filterEditPartsUnderstanding(list, getSourceRequest());
        return list;
    }

    /**
     * @see org.eclipse.gef.tools.SimpleDragTracker#createSourceRequest()
     */
    protected Request createSourceRequest() {
        ChangeBoundsRequest request;
        request = new ChangeBoundsRequest(REQ_RESIZE);
        request.setResizeDirection(getResizeDirection());
        return request;
    }

    /**
     * @see org.eclipse.gef.tools.AbstractTool#deactivate()
     */
    public void deactivate() {
        // For the case where ESC key was hit while resizing
        eraseTargetFeedback();
        super.deactivate();
    }

    /**
     * Show the source drag feedback for the drag occurring within the viewer.
     */
    @Override
    protected void eraseSourceFeedback() {
        if (!isShowingFeedback()) {
            return;
        }
        setFlag(FLAG_SOURCE_FEEDBACK, false);
        final List editParts = getOperationSet();
        for (int i = 0; i < editParts.size(); i++) {
            final EditPart editPart = (EditPart) editParts.get(i);
            editPart.eraseSourceFeedback(getSourceRequest());
        }
    }
    /**
     * Returns <code>true</code> if feedback is being shown.
     * @return <code>true</code> if feedback is showing
     */
    protected boolean isShowingFeedback() {
        return getFlag(FLAG_SOURCE_FEEDBACK);
    }
    /**
     * This method is invoked when the resize operation is complete.  It notifies the
     * {@link #getTargetEditPart() target} to erase target feedback.
     */
    protected void eraseTargetFeedback() {
        if (!getFlag(FLAG_TARGET_FEEDBACK))
            return;
        if (getTargetEditPart() != null)
            getTargetEditPart().eraseTargetFeedback(getSourceRequest());
        setFlag(FLAG_TARGET_FEEDBACK, false);
    }

    /**
     * @see org.eclipse.gef.tools.AbstractTool#getCommand()
     */
    protected Command getCommand() {
        List editparts = getOperationSet();
        EditPart part;
        CompoundCommand command = new CompoundCommand();
        command.setDebugLabel("Resize Handle Tracker");//$NON-NLS-1$
        for (int i = 0; i < editparts.size(); i++) {
            part = (EditPart)editparts.get(i);
            command.add(part.getCommand(getSourceRequest()));
        }
        return command.unwrap();
    }

    /**
     * @see org.eclipse.gef.tools.AbstractTool#getCommandName()
     */
    protected String getCommandName() {
        return REQ_RESIZE;
    }

    /**
     * @see org.eclipse.gef.tools.AbstractTool#getDefaultCursor()
     */
    protected Cursor getDefaultCursor() {
        return SharedCursors.getDirectionalCursor(
                direction, getTargetEditPart().getFigure().isMirrored());
    }

    /**
     * @see org.eclipse.gef.tools.AbstractTool#getDebugName()
     */
    protected String getDebugName() {
        return "Resize Handle Tracker";//$NON-NLS-1$
    }

    /**
     * Returns the direction of the resize (NORTH, EAST, NORTH_EAST, etc.).  These constants
     * are from {@link PositionConstants}.
     * @return the resize direction.
     */
    protected int getResizeDirection() {
        return direction;
    }

    /**
     * The TargetEditPart is the parent of the EditPart being resized.
     * 
     * @return  The target EditPart; may be <code>null</code> in 2.1 applications that use
     * the now deprecated {@link ResizeTracker#ResizeTracker(int) constructor}.
     */
    protected GraphicalEditPart getTargetEditPart() {
        if (owner != null)
            return (GraphicalEditPart)owner.getParent();
        return null;
    }

    /**
     * If dragging is in progress, cleans up feedback and calls performDrag().
     * 
     * @see org.eclipse.gef.tools.SimpleDragTracker#handleButtonUp(int)
     */
    protected boolean handleButtonUp(int button) {
        if (stateTransition(STATE_DRAG_IN_PROGRESS, STATE_TERMINAL)) {
            eraseSourceFeedback();
            eraseTargetFeedback();
            performDrag();
        }
        return true;
    }

    protected boolean _isInDragInProgress() {
        return isInState(STATE_DRAG_IN_PROGRESS | STATE_ACCESSIBLE_DRAG_IN_PROGRESS);
    }
    /**
     * Updates the command and the source request, and shows feedback.
     * 
     * @see org.eclipse.gef.tools.SimpleDragTracker#handleDragInProgress()
     */
    protected boolean handleDragInProgress() {
        if (_isInDragInProgress()) {
            updateSourceRequest();
            updateTargetRequest();
            showSourceFeedback();
            showTargetFeedback();
            setCurrentCommand(getCommand());
        }
        return true;
    }

    /**
     * This method is invoked as the drag is happening.  It notifies the 
     * {@link #getTargetEditPart() target} to show target feedback.
     */
    protected void showTargetFeedback() {
        setFlag(FLAG_TARGET_FEEDBACK, true);
        if (getTargetEditPart() != null) {
            getTargetEditPart().showTargetFeedback(getSourceRequest());
        }
    }

    /**
     * Show the source drag feedback for the drag occurring within the viewer.
     */
    protected void showSourceFeedback() {
        List editParts = getOperationSet();
        for (int i = 0; i < editParts.size(); i++) {
            EditPart editPart = (EditPart) editParts.get(i);
            editPart.showSourceFeedback(getSourceRequest());
        }
        setFlag(FLAG_SOURCE_FEEDBACK, true);
    }


    /**
     * @see org.eclipse.gef.tools.SimpleDragTracker#updateSourceRequest()
     */
    protected void updateSourceRequest() {
        //repairStartLocation();
        GraphicalEditPart owner = getOwner();
        if (owner != null && owner.getModel() instanceof View) {
                EObject semanticElement = ((View)owner.getModel()).getElement();
                if (!(semanticElement instanceof Activity) ||
                        ((Activity)semanticElement).getActivityType().getValue()
                                == ActivityType.TASK) {
                            //no resize constraint for a task.
                            //all the other ones must resize equally in height and width
                            superUpdateSourceRequest();
                            return;                    
                }
        }
        
        
        ChangeBoundsRequest request = (ChangeBoundsRequest) getSourceRequest();
        Dimension d = getDragMoveDelta();

        Point location = new Point(getLocation());
        Point moveDelta = new Point(0, 0);
        Dimension resizeDelta = new Dimension(0, 0);
        int direction = getResizeDirection();

        PrecisionRectangle sourceRect = getSourceRectangle();
        SnapToHelper snapToHelper = getSnapToHelper();

        if (owner != null) {
            request.setConstrainedResize(true);

            final float ratio = 1;

            if (direction == PositionConstants.SOUTH_EAST) {
                if (d.height > (d.width * ratio))
                    d.width = (int) (d.height / ratio);
                else
                    d.height = (int) (d.width * ratio);
            } else if (direction == PositionConstants.NORTH_WEST) {
                if (d.height < (d.width * ratio))
                    d.width = (int) (d.height / ratio);
                else
                    d.height = (int) (d.width * ratio);
            } else if (direction == PositionConstants.NORTH_EAST) {
                if (-(d.height) > (d.width * ratio))
                    d.width = -(int) (d.height / ratio);
                else
                    d.height = -(int) (d.width * ratio);
            } else if (direction == PositionConstants.SOUTH_WEST) {
                if (-(d.height) < (d.width * ratio))
                    d.width = -(int) (d.height / ratio);
                else
                    d.height = -(int) (d.width * ratio);
            } else if (direction == PositionConstants.NORTH) {
                direction = direction | PositionConstants.EAST;
                d.width = -(int) (d.height / ratio);
            } else if (direction == PositionConstants.SOUTH) {
                direction = direction | PositionConstants.EAST;
                d.width = (int) (d.height / ratio);
            } else if (direction == PositionConstants.EAST) {
                direction = direction | PositionConstants.SOUTH;
                d.height = (int) (d.width * ratio);
            } else if (direction == PositionConstants.WEST) {
                direction = direction | PositionConstants.SOUTH;
                d.height = -(int) (d.width * ratio);
            }
        } else
            request.setConstrainedResize(false);

        request.setCenteredResize(getCurrentInput().isModKeyDown(SWT.MOD1));

        if ((direction & PositionConstants.NORTH) != 0) {
            if (getCurrentInput().isControlKeyDown()) {
                resizeDelta.height -= d.height;
            }
            moveDelta.y += d.height;
            resizeDelta.height -= d.height;
        }
        if ((direction & PositionConstants.SOUTH) != 0) {
            if (getCurrentInput().isControlKeyDown()) {
                moveDelta.y -= d.height;
                resizeDelta.height += d.height;
            }
            resizeDelta.height += d.height;
        }
        if ((direction & PositionConstants.WEST) != 0) {
            if (getCurrentInput().isControlKeyDown()) {
                resizeDelta.width -= d.width;
            }
            moveDelta.x += d.width;
            resizeDelta.width -= d.width;
        }
        if ((direction & PositionConstants.EAST) != 0) {
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
                    result.preciseWidth *= 2.0;
                }

                if (result.preciseY != 0.0)
                    result.preciseHeight += -result.preciseY;
                else if (result.preciseHeight != 0.0) {
                    result.preciseY = -result.preciseHeight;
                    result.preciseHeight *= 2.0;
                }
                result.updateInts();
            }

            PrecisionPoint preciseMove = new PrecisionPoint(result.x
                    + moveDelta.x, result.y + moveDelta.y);

            PrecisionDimension preciseResize = new PrecisionDimension(
                    result.width + resizeDelta.width, result.height
                            + resizeDelta.height);

            if (preciseResize.width != preciseResize.height) {
                // make resize figure square
                preciseResize.preciseHeight = preciseResize.preciseWidth;
                preciseResize.updateInts();
            }
            request.setMoveDelta(preciseMove);
            request.setSizeDelta(preciseResize);
        }
    }
    

    /**
     * @see org.eclipse.gef.tools.SimpleDragTracker#updateSourceRequest()
     */
    protected void superUpdateSourceRequest() {
        ChangeBoundsRequest request = (ChangeBoundsRequest) getSourceRequest();
        Dimension d = getDragMoveDelta();

        Point location = new Point(getLocation());
        Point moveDelta = new Point(0, 0);
        Dimension resizeDelta = new Dimension(0, 0);    

        if (getCurrentInput().isShiftKeyDown() && getOwner() != null) {
            request.setConstrainedResize(true);
            
            int origHeight = getOwner().getFigure().getBounds().height;
            int origWidth = getOwner().getFigure().getBounds().width;
            float ratio = 1;
            
            if (origWidth != 0 && origHeight != 0)
                ratio = ((float)origHeight / (float)origWidth);
            
            if (getResizeDirection() == PositionConstants.SOUTH_EAST) {
                if (d.height > (d.width * ratio))
                    d.width = (int)(d.height / ratio);
                else
                    d.height = (int)(d.width * ratio);
            } else if (getResizeDirection() == PositionConstants.NORTH_WEST) {
                if (d.height < (d.width * ratio))
                    d.width = (int)(d.height / ratio);
                else
                    d.height = (int)(d.width * ratio);
            } else if (getResizeDirection() == PositionConstants.NORTH_EAST) {
                if (-(d.height) > (d.width * ratio))
                    d.width = -(int)(d.height / ratio);
                else
                    d.height = -(int)(d.width * ratio);
            } else if (getResizeDirection() == PositionConstants.SOUTH_WEST) {
                if (-(d.height) < (d.width * ratio))
                    d.width = -(int)(d.height / ratio);
                else
                    d.height = -(int)(d.width * ratio);
            }
        } else
            request.setConstrainedResize(false);
        
        request.setCenteredResize(getCurrentInput().isModKeyDown(SWT.MOD1));
        
        
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
        
        if (!getCurrentInput().isAltKeyDown() && getSnapToHelper() != null) {
            PrecisionRectangle rect = getSourceRectangle().getPreciseCopy();
            rect.translate(moveDelta);
            rect.resize(resizeDelta);
            PrecisionRectangle result = new PrecisionRectangle();
            
            getSnapToHelper().snapRectangle(request, request.getResizeDirection(), rect, result);
            if (request.isCenteredResize()) {
                if (result.preciseX != 0.0)
                    result.preciseWidth += -result.preciseX;
                else if (result.preciseWidth != 0.0) {
                    result.preciseX = -result.preciseWidth;
                    result.preciseWidth *= 2.0;
                }
                
                if (result.preciseY != 0.0)
                    result.preciseHeight += -result.preciseY;
                else if (result.preciseHeight != 0.0) {
                    result.preciseY = -result.preciseHeight;
                    result.preciseHeight *= 2.0;
                }
                result.updateInts();
            }

            PrecisionPoint preciseMove = new PrecisionPoint(
                    result.x + moveDelta.x,
                    result.y + moveDelta.y);
            
            PrecisionDimension preciseResize = new PrecisionDimension(
                    result.width + resizeDelta.width,
                    result.height + resizeDelta.height);
            
            request.setMoveDelta(preciseMove);
            request.setSizeDelta(preciseResize);
        }
    }
    /**
     * Returns the request for the source of the drag, creating it if necessary.
     * @return the source request
     */
    protected Request getSourceRequest() {
        if (sourceRequest == null)
            sourceRequest = createSourceRequest();
        return sourceRequest;
    }
    /**
     * Calls {@link TargetingTool#updateAutoexposeHelper()} if a drag is in progress.
     * @see org.eclipse.gef.tools.TargetingTool#handleHover()
     */
    @Override
    protected boolean handleHover() {
        if (_isInDragInProgress()) {
            updateAutoexposeHelper();
        }
        return true;
    }

    /**
     * This method is called whenever an autoexpose occurs. When an autoexpose occurs, it is
     * possible that everything in the viewer has moved a little. Therefore, by default,
     * {@link AbstractTool#handleMove() handleMove()} is called to simulate the mouse moving
     * even though it didn't.
     */
    protected void handleAutoexpose() {
        handleDragInProgress();
    }
    
}
