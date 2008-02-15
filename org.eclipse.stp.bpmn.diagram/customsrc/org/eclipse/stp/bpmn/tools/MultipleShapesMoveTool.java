/*
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *
 * Date         Author             Changes
 * Sep 11, 2007      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.SharedCursors;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.internal.ui.rulers.GuideEditPart;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.SimpleDragTracker;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.TextCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.stp.bpmn.AssociationTarget;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBorderCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.swt.graphics.Cursor;

/**
 * This class implements a tool to move all the pools' shapes
 * present on the right of the mouse location on the left or the right.
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class MultipleShapesMoveTool extends SimpleDragTracker {

    /**
     * the guide figure to use for feedback
     */
    private IFigure guideline;
    /**
     * the initial position when the mouse was pressed
     * for the first time.
     */
    private Integer _initialPosition;
    
    /**
     * the list of moving shapes that are concerned
     * by the move.
     */
    private List<IGraphicalEditPart> _movingShapes = Collections.EMPTY_LIST;
    private IGraphicalEditPart _container;

    /**
     * Default constructor, with no args as required
     * by the palette.
     */
    public MultipleShapesMoveTool() {
        guideline = new GuideEditPart.GuideLineFigure();
        guideline.setVisible(false);
    }

    /**
     * Erases the guide and the feedback shown by the container
     * of the elements.
     */
    protected void eraseSourceFeedback() {
        if (guideline.getParent() != null) {
            guideline.getParent().remove(guideline);
        }
        
        
        if (_container != null) {
            _container.eraseSourceFeedback(getSourceRequest());
        }
        
        ChangeBoundsRequest request = 
            new ChangeBoundsRequest(RequestConstants.REQ_MOVE);
        for (IGraphicalEditPart part : _movingShapes) {
            part.eraseSourceFeedback(request);
        }
    }

    /**
     * creates the command to move the shapes left or right.
     */
    protected Command getCommand() {
        return _container.getCommand(getSourceRequest());
    }

    /**
     * the command will move things around.
     */
    protected String getCommandName() {
        return REQ_MOVE_CHILDREN;
    }

    /**
     * @return the current location without the zoom.
     */
    protected int getCurrentPositionZoomed() {
        Point pt = getLocation();
        ((IGraphicalEditPart) ((IDiagramGraphicalViewer) 
                getCurrentViewer()).getRootEditPart().
            getChildren().get(0)).getFigure().translateToRelative(pt);
        return  pt.x;
    }

    /**
     * @return the current position with a zoom multiplier
     */
    protected int getCurrentPosition() {
        int position = getCurrentPositionZoomed();
        ZoomManager zoomManager = ((DiagramRootEditPart) 
                getCurrentViewer().getRootEditPart()).getZoomManager();
        if (zoomManager != null) {
            position = (int)Math.round(position / zoomManager.getZoom());
        }
        return position;
    }

    /**
     * the name used to debug the tool.
     */
    protected String getDebugName() {
        return "Multiple Shape Horizontal Move";  //$NON-NLS-1$
    }

    /**
     * if the drag is permitted, show the cursor.
     * 
     * Else if a drag is occuring show it too, or show the 
     * no cursor.
     */
    protected Cursor getDefaultCursor() {
        if (isMoveValid()) {
            return SharedCursors.SIZEWE;
        } else {
            if (getState() == STATE_DRAG_IN_PROGRESS) {
                return SharedCursors.SIZEWE;
            } 
            return SharedCursors.ARROW;
        }
    }

    /**
     * The move is valid if the editor selection
     * contains at least one pool.
     * @return true if the move is valid
     */
    private boolean isMoveValid() {
        if (getCurrentViewer() == null || getCurrentInput() == null) {
            return true;
        }
        Object obj = getCurrentViewer().findObjectAt(getCurrentInput().getMouseLocation());
        return obj instanceof IGraphicalEditPart && 
            ((IGraphicalEditPart) obj).resolveSemanticElement() instanceof Graph;
    }

    /**
     * Moved to public so that it may be called programmatically
     * by an other tool.
     */
    public boolean handleButtonDown(int button) {
        Object underMouse = getCurrentViewer().findObjectAt(getCurrentInput().getMouseLocation());
        if (!(underMouse instanceof IGraphicalEditPart)) {
            return true;
        }
        stateTransition(STATE_INITIAL, STATE_DRAG_IN_PROGRESS);
        _initialPosition = getCurrentPosition();
        // calculate the initial selection
        // of shapes that should move
        _container = (IGraphicalEditPart) underMouse;
        
        // the children that will be moved around
        List<IGraphicalEditPart> rightChildren = new ArrayList<IGraphicalEditPart>();
        
        // special cases.
        // we want those parts to be selected as their parent.
        if (_container instanceof SubProcessSubProcessBorderCompartmentEditPart
                || _container instanceof SubProcessNameEditPart) {
            _container = (IGraphicalEditPart) _container.getParent();
        }
        if (_container instanceof SubProcessEditPart ||
                (_container.resolveSemanticElement() instanceof AssociationTarget && 
                        !(_container.resolveSemanticElement() instanceof Graph))) {
            IGraphicalEditPart parent = findParent(_container.getParent());
            if (parent != null) {
                if (_container instanceof TextCompartmentEditPart) {
                    _container = (IGraphicalEditPart) _container.getParent();
                }
                rightChildren.add(_container);
                _container = parent;
            }
        }
        
        if (_container.resolveSemanticElement() instanceof Graph) {
            List children  = null;
            if (_container instanceof PoolEditPart) {
                PoolPoolCompartmentEditPart compartment = 
                    (PoolPoolCompartmentEditPart) ((PoolEditPart) _container).getChildBySemanticHint(
                            BpmnVisualIDRegistry.getType(PoolPoolCompartmentEditPart.VISUAL_ID));
                children = compartment.getChildren();
//              } else if (_container instanceof SubProcessEditPart) {
//              SubProcessSubProcessBodyCompartmentEditPart compartment = 
//              (SubProcessSubProcessBodyCompartmentEditPart) ((IGraphicalEditPart) _container)
//              .getChildBySemanticHint(BpmnVisualIDRegistry.
//              getType(SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID));
//              children = compartment.getChildren();
            } else if (_container instanceof PoolPoolCompartmentEditPart) {
                children = ((PoolPoolCompartmentEditPart) _container).getChildren();
            } else if (_container instanceof SubProcessSubProcessBodyCompartmentEditPart) {
                children = ((SubProcessSubProcessBodyCompartmentEditPart) _container).getChildren();
            }
            if (children == null) {
                throw new IllegalArgumentException("The part " + _container + " did not contain elements"); //$NON-NLS-1$ //$NON-NLS-2$
            }
            // now iterate over the compartment children
            // and take those that are on the right.
            for (Object child : children) {
                if (child instanceof IGraphicalEditPart) {
                    Point loc = ((IGraphicalEditPart) child).getFigure().
                    getBounds().getLocation().getCopy();
                    ((IGraphicalEditPart) child).getFigure().translateToAbsolute(loc);
                    ((DiagramEditPart) getCurrentViewer().getContents()).getFigure().translateToRelative(loc);
                    if (loc.x > _initialPosition) {
                        rightChildren.add((IGraphicalEditPart) child);
                    }
                }
            }
        }
        _movingShapes = rightChildren;
        
        updateSourceRequest();
        showSourceFeedback();
        
        return true;
    }

    /**
     * Finds a part that represents a Graph, acting recursively.
     * @param part
     * @return
     */
    private IGraphicalEditPart findParent(Object part) {
        if (part instanceof IGraphicalEditPart) {
            if (((IGraphicalEditPart) part).resolveSemanticElement() instanceof Graph) {
                return (IGraphicalEditPart) part;
            }
            return findParent(((IGraphicalEditPart) part).getParent());
        }
        return null;
    }

    protected boolean handleButtonUp(int button) {
        if (stateTransition(STATE_DRAG_IN_PROGRESS, STATE_TERMINAL)) {
            setCurrentCommand(getCommand());
            executeCurrentCommand();
        }
        
        eraseSourceFeedback();
        
        _movingShapes = Collections.EMPTY_LIST;
        _initialPosition = null;
        
        
        setState(STATE_INITIAL);
        
        _container = null;
        return true;
    }

    protected boolean movedPastThreshold() {
        return true;
    }

    /**
     * Shows a nice guideline to show the move to
     * the right or the left.
     */
    protected void showSourceFeedback() {
        if (guideline.getParent() == null) {
            addFeedback(guideline);
        }
        Rectangle bounds = Rectangle.SINGLETON.getCopy();
        bounds.x = getCurrentPositionZoomed();
        
        Rectangle containerBounds = _container.getFigure().getBounds().getCopy();
        _container.getFigure().translateToAbsolute(containerBounds);
        
        ((DiagramEditPart) getCurrentViewer().getContents()).getFigure().translateToRelative(containerBounds);
        bounds.y = containerBounds.y;
        bounds.width = 1;
        bounds.height = containerBounds.height;
        guideline.setBounds(bounds);
        guideline.setVisible(getState() == STATE_DRAG_IN_PROGRESS);
        
        ChangeBoundsRequest request = 
            new ChangeBoundsRequest(RequestConstants.REQ_MOVE);
        request.setMoveDelta(((ChangeBoundsRequest) 
                getSourceRequest()).getMoveDelta().getCopy());
        for (IGraphicalEditPart part : _movingShapes) {
            part.showSourceFeedback(request);
        }
    }

    /**
     * Creates the initial request,
     * overridden to use a ChangeBoundsRequest.
     */
    @Override
    protected Request createSourceRequest() {
        return new ChangeBoundsRequest(getCommandName());
    }
    
    /**
     * Creates the children move request
     */
    @Override
    protected void updateSourceRequest() {
        super.updateSourceRequest();
        int moved = getCurrentPosition() - _initialPosition;
        ((ChangeBoundsRequest) getSourceRequest()).
            setMoveDelta(new Point(moved, 0));
        ((ChangeBoundsRequest) getSourceRequest()).
            setEditParts(_movingShapes);
    }
}
