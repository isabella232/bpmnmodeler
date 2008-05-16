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
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.SharedCursors;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.internal.ui.rulers.GuideEditPart;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.SimpleDragTracker;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.policies.ResizableShapeEditPolicyEx;
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

    private static final Rectangle SINGLETON = new Rectangle();
    
    /**
     * the guide figure to use for feedback
     */
    private IFigure guideline;
    /**
     * the initial position when the mouse was pressed
     * for the first time.
     */
    private Integer _initialPosition;
    
    private Integer _initPosNoZoom;
    
    /**
     * the list of moving shapes that are concerned
     * by the move.
     */
    private List<IGraphicalEditPart> _movingShapes = Collections.emptyList();
    
    /**
     * the list of subprocesses that will be resized by the tool as well
     */
    private List<IGraphicalEditPart> _subProcesses = Collections.emptyList();
    
    /**
     * the container edit part, only a pool, that we execute on.
     */
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
        request.setEditParts(Collections.emptyList());
        request.setSizeDelta(new Dimension(0, 0));
        request.setMoveDelta(new Point(0, 0));
        for (IGraphicalEditPart part : _movingShapes) {
            part.eraseSourceFeedback(request);
        }
        
        ChangeBoundsRequest spRequest = new ChangeBoundsRequest(
                RequestConstants.REQ_RESIZE);
        spRequest.setEditParts(Collections.emptyList());
        spRequest.setSizeDelta(new Dimension(0, 0));
        spRequest.setMoveDelta(new Point(0, 0));
        for (IGraphicalEditPart sp : _subProcesses) {
            sp.eraseSourceFeedback(spRequest);
        }
    }

    /**
     * creates the command to move the shapes left or right.
     */
    protected Command getCommand() {
        if (_container == null) {
            return null;
        }
        CompoundCommand command = new CompoundCommand(BpmnDiagramMessages.MultipleShapesMoveTool_command_name);
        TransactionalEditingDomain editingDomain = _container.getEditingDomain();
        
        Point moveDelta  = ((ChangeBoundsRequest) getSourceRequest()).getMoveDelta().getCopy();
        Dimension spSizeDelta = new Dimension(moveDelta.x, moveDelta.y);
        ZoomManager zoomManager = ((DiagramRootEditPart) 
                getCurrentViewer().getRootEditPart()).getZoomManager();
        spSizeDelta.scale(1/zoomManager.getZoom());
//        for (IGraphicalEditPart part : _movingShapes) {
//            Rectangle rect = part.getFigure().getBounds().getCopy();
//            rect.translate(moveDelta);
//            SetBoundsCommand setBounds = 
//                new SetBoundsCommand(editingDomain, "Moving shape", part, rect);
//            command.add(new ICommandProxy(setBounds));
//        }
        // or maybe call the parent ?
        command.add(_container.getCommand(getSourceRequest()));
        
        
        for (IGraphicalEditPart sp : _subProcesses) {
            Dimension spDim = sp.getFigure().getBounds().getSize().getCopy();
            spDim.expand(spSizeDelta);
            SetBoundsCommand setBounds = 
                new SetBoundsCommand(editingDomain, BpmnDiagramMessages.MultipleShapesMoveTool_resizing, sp, spDim);
            command.add(new ICommandProxy(setBounds));
        }
        return command;
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
            position = (int)Math.round(position * zoomManager.getZoom());
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
        _initPosNoZoom = getCurrentPositionZoomed();
        
        // calculate the initial selection
        // of shapes that should move
        _container = (IGraphicalEditPart) findPool(underMouse);
        
        // the children that will be moved around
        List<IGraphicalEditPart> rightChildren = new ArrayList<IGraphicalEditPart>();
        List<IGraphicalEditPart> subProcesses = new ArrayList<IGraphicalEditPart>();
        
        if (_container != null && _container.resolveSemanticElement() instanceof Pool) {
            List children  = null;
            if (_container instanceof PoolEditPart) {
                PoolPoolCompartmentEditPart compartment = 
                    (PoolPoolCompartmentEditPart) ((PoolEditPart) _container).getChildBySemanticHint(
                            BpmnVisualIDRegistry.getType(PoolPoolCompartmentEditPart.VISUAL_ID));
                children = compartment.getChildren();
            } else if (_container instanceof PoolPoolCompartmentEditPart) {
                children = ((PoolPoolCompartmentEditPart) _container).getChildren();
            }
            if (children == null) {
                throw new IllegalArgumentException("The part " + _container + " did not contain elements"); //$NON-NLS-1$ //$NON-NLS-2$
            }
            // now iterate over the compartment children
            // and take those that are on the right.
            for (Object child : children) {
                if (child instanceof ShapeNodeEditPart) {
                    TaskDragEditPartsTrackerEx.setBoundsForOverlapComputation(
                            (IGraphicalEditPart)child, SINGLETON);
                    ((DiagramEditPart) getCurrentViewer().getContents())
                                .getFigure().translateToRelative(SINGLETON);
                    if (SINGLETON.x > _initPosNoZoom) {
                        rightChildren.add((IGraphicalEditPart) child);
                    } else if (child instanceof SubProcessEditPart && 
                            SINGLETON.x < _initPosNoZoom && 
                            SINGLETON.x + SINGLETON.width > _initPosNoZoom) {
                        addEnclosedChildren((IGraphicalEditPart) child, rightChildren, subProcesses, _initPosNoZoom);
                    }
                }
            }
        }
        _movingShapes = rightChildren;
        _subProcesses = subProcesses;
        
        updateSourceRequest();
        showSourceFeedback();
        
        return true;
    }

    /**
     * 
     * @param sp the subprocess to examine
     * @param rightChildren the right children, the objects that move
     * @param subProcesses the subprocesses that might be crossed by the lines. They will be resized
     * @param curPosNoZoom the current mouse location
     */
    private void addEnclosedChildren(IGraphicalEditPart sp,
            List<IGraphicalEditPart> rightChildren,
            List<IGraphicalEditPart> subProcesses, int curPosNoZoom) {
        subProcesses.add(sp);
        SubProcessSubProcessBodyCompartmentEditPart compartment = 
            (SubProcessSubProcessBodyCompartmentEditPart) sp.getChildBySemanticHint(
                    BpmnVisualIDRegistry.getType(SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID));
        List children = compartment.getChildren();
        for (Object child : children) {
            TaskDragEditPartsTrackerEx.setBoundsForOverlapComputation(
                    (IGraphicalEditPart)child, SINGLETON);
            ((DiagramEditPart) getCurrentViewer().getContents())
            .getFigure().translateToRelative(SINGLETON);
            if (SINGLETON.x > curPosNoZoom) {
                rightChildren.add((IGraphicalEditPart) child);
            } else if (child instanceof SubProcessEditPart && 
                    SINGLETON.x < curPosNoZoom && 
                    SINGLETON.x + SINGLETON.width > curPosNoZoom) {
                addEnclosedChildren((IGraphicalEditPart) child, rightChildren, subProcesses, curPosNoZoom);
            }
        }
    }
        

    /**
     * Finds a part that represents a Graph, acting recursively.
     * @param part
     * @return
     */
    private IGraphicalEditPart findPool(Object part) {
        if (part instanceof IGraphicalEditPart) {
            if (((IGraphicalEditPart) part).resolveSemanticElement() instanceof Pool) {
                return (IGraphicalEditPart) part;
            }
            return findPool(((IGraphicalEditPart) part).getParent());
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
        bounds.x = getCurrentPosition();
        
        Rectangle containerBounds = _container.getFigure().getBounds().getCopy();
        _container.getFigure().translateToAbsolute(containerBounds);
        
        ((DiagramEditPart) getCurrentViewer().getContents())
                .getFigure().translateToRelative(containerBounds);
        bounds.y = containerBounds.y;
        bounds.width = 1;
        bounds.height = containerBounds.height;
        
        guideline.setBounds(bounds);
        guideline.setVisible(getState() == STATE_DRAG_IN_PROGRESS);
        
        ChangeBoundsRequest request = 
            new ChangeBoundsRequest(RequestConstants.REQ_MOVE);
        request.setMoveDelta(((ChangeBoundsRequest) getSourceRequest()).getMoveDelta());
        request.setSizeDelta(new Dimension(0, 0));
        request.setEditParts(_movingShapes);
        ResizableShapeEditPolicyEx.setOtherSelectedEditParts(_subProcesses, request);
        for (IGraphicalEditPart part : _movingShapes) {
            part.showSourceFeedback(request);
        }
        
        ChangeBoundsRequest spRequest = new ChangeBoundsRequest(
                RequestConstants.REQ_RESIZE);
        Point moveDelta  = ((ChangeBoundsRequest) getSourceRequest()).getMoveDelta().getCopy();
        Dimension spSizeDelta = new Dimension(moveDelta.x, moveDelta.y);
        spRequest.setSizeDelta(spSizeDelta);
        spRequest.setMoveDelta(new Point(0, 0));
        spRequest.setEditParts(_subProcesses);
        ResizableShapeEditPolicyEx.setOtherSelectedEditParts(_movingShapes, spRequest);
        for (IGraphicalEditPart sp : _subProcesses) {
            sp.showSourceFeedback(spRequest);
        }
    }

    /**
     * Creates the initial request,
     * overridden to use a ChangeBoundsRequest.
     */
    @Override
    protected Request createSourceRequest() {
        ChangeBoundsRequest request = new ChangeBoundsRequest(getCommandName());
        request.setSizeDelta(new Dimension(0, 0));
        return request;
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
