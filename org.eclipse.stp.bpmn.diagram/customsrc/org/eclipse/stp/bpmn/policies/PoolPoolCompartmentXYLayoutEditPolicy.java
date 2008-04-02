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
 * Dec 06, 2006     hmalphettes         Created
 **/
package org.eclipse.stp.bpmn.policies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.LabelEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.Group2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.GroupEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.LaneEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.MessagingEdgeNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SequenceEdgeNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.figures.BpmnShapesDefaultSizes;


/**
 * The edit policy makes sure the lanes are correctly laid out.
 * <p>
 * Derived from the BpmnDiagramCompartmentXYLayoutEditPolicy by MPeleshchyshyn.
 * </p>
 * TODO: also change the location of the shapes inside tha lanes when the lanes
 * are resized.
 *  
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class PoolPoolCompartmentXYLayoutEditPolicy extends XYLayoutEditPolicy {

    /**
     * the negative resize constant: if turned to true, we accept resizing 
     * the pool by the top and the left.
     */
    private static final boolean DO_NEGATIVE_RESIZING = true;
    
    protected ZoomManager _zoom;

    /**
     * the minimal distance inserted between two shapes
     * that were collided. 
     * Must be < 0.
     */
    private static final int AVOID_INTERVAL = 1;
    /**
     * @generated not
     */
    public static final Insets INSETS = new Insets(PoolPoolCompartmentEditPart.INSETS);

    
    
    @Override
    public void activate() {
        super.activate();
        _zoom = ((DiagramRootEditPart) getHost().getRoot()).getZoomManager();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#getResizeChildrenCommand(org.eclipse.gef.requests.ChangeBoundsRequest)
     */
    protected Command getResizeChildrenCommand(ChangeBoundsRequest request) {
        CompoundCommand resize = new CompoundCommand();
        // let's skip groups, they do not resize the pool.
        boolean onlyContainsGroups = !request.getEditParts().isEmpty();
        boolean onlyContainsLanes = true; // true by default as no part is included in the request when the lane is deleted

        // true if the pool has been resized, and is making a callback to get the lanes to resize.
        boolean poolResize = false;
        for (Object o : request.getEditParts()) {
            onlyContainsGroups = onlyContainsGroups && (
                    o instanceof GroupEditPart || o instanceof Group2EditPart);
            onlyContainsLanes = onlyContainsLanes && o instanceof LaneEditPart;

            if (o == getHost().getParent()) {
                onlyContainsGroups = false;
                onlyContainsLanes = true;
                poolResize = true;
                break;
            }
        }
        
        if (onlyContainsGroups) {
            return super.getResizeChildrenCommand(request);
        }
        if (onlyContainsLanes) {
            Map<IGraphicalEditPart, Rectangle> map = new HashMap<IGraphicalEditPart, Rectangle>();
            SortedSet<Rectangle> set = new TreeSet<Rectangle>(new RectanglesComparator());
            fillMapAndSet(map, set, request, resize);
            if (!map.isEmpty()) {
                doLayout(request, map, set, resize, null);
            } else {
                return null;
            }
        } else {
            // eliminate the lanes from the request
            List parts = new ArrayList();
            for (Object o : request.getEditParts()) {
                if (!(o instanceof LaneEditPart)) {
                    parts.add(o);
                }
            }
            request.setEditParts(parts);

            CompoundCommand negativeCommand = chunkNegativeMove(request);
            if (negativeCommand != null) {
                if (DO_NEGATIVE_RESIZING) {
                    return negativeCommand;
                } else {
                    return UnexecutableCommand.INSTANCE;
                }
            }
            
            
            resize.add(super.getResizeChildrenCommand(request));
            boolean unexec = avoidOverlap(request, resize);
            if (unexec) {
                return UnexecutableCommand.INSTANCE;
            }
        }
        if (poolResize) {
            return resize.unwrap();
        }
        
        // if the subprocess is resized in the northern direction, shapes should be moved south as an opposite
        // so that the user thinks he is resizing the subprocess by adding space at the top.
        Point moveChildren = new Point(0, 0);
        if ((request.getResizeDirection() & PositionConstants.NORTH) != 0) {
            moveChildren.y = request.getSizeDelta().height;
        }
        // same thing here: if the subprocess is being resized by the left
        // the user must have the impression he is adding space.
        if ((request.getResizeDirection() & PositionConstants.WEST) != 0) {
            moveChildren.x = request.getSizeDelta().width;
        }
        if ((moveChildren.x != 0 || moveChildren.y != 0) 
                && request.getEditParts() != null 
                && !request.getEditParts().isEmpty()) {
            for (Object part : request.getEditParts()) {
                if (part instanceof SubProcessEditPart) {
                    final IGraphicalEditPart comp = ((SubProcessEditPart) part).
                        getChildBySemanticHint(BpmnVisualIDRegistry.getType(
                            SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID));
                    if (comp != null) { // if comp is null this wasn't a subprocess in the first place
                        ChangeBoundsRequest move = new ChangeBoundsRequest(org.eclipse.gef.RequestConstants.REQ_MOVE_CHILDREN);
                        move.setMoveDelta(moveChildren);
                        move.setEditParts(comp.getChildren());
                        Command c = comp.getCommand(move);
                        if (c != null && c.canExecute()) {
                            resize.add(c);
                        }
                    }
                }
            }
        }
//      make sure that the children won't be bigger than the pool.
        // to do that, build a request for the pool to be resized, 
        // and address it to the diagram.
        ChangeBoundsRequest req = new ChangeBoundsRequest(
                RequestConstants.REQ_RESIZE_CHILDREN);
        req.setEditParts(getHost().getParent());
        req.setCenteredResize(true);
        req.setSizeDelta(getMinSizeForPool(request));
        resize.add(getHost().getParent().getParent().getCommand(req));
        // we want to have labels snap back to their original places
        // when a sequence edge or a messaging edge is moved.
        if (BpmnDiagramEditorPlugin.getInstance().getPreferenceStore().
                getBoolean(BpmnDiagramPreferenceInitializer.PREF_SNAP_BACK_ON_MOVE)) {
            makeSnapBackCommands(resize, request);
        }

        return resize.unwrap();
    }

    /**
     * This methods determines if the request can execute or not depending
     * on the overlap with other shapes. By default it returns false if
     * an overlap is detected.
     * <p>
     * Currently we rely on the ResizableShapeEditPolicyEx to have added data
     * to the request if it is overlapping.
     * </p>
     * @param request the current request
     * @param compound the compound command.
     * @return true if an overlap is detected
     */
    private boolean avoidOverlap(ChangeBoundsRequest request, 
            CompoundCommand compound) {
        return ResizableShapeEditPolicyEx.isOverlappingRequest(request);
    }
    
    /**
     * Gets the minimum size when creating a new element in the pool.
     * If the element is created on a border, it will
     * add some width and/or length to have it in the compartment.
     * @param request
     * @return
     */
    private Dimension getMinSizeForPool(CreateRequest request) {
    	ChangeBoundsRequest req = new ChangeBoundsRequest(
    			RequestConstants.REQ_RESIZE_CHILDREN);
    	req.setEditParts(Collections.EMPTY_LIST);
    	Dimension dim = getMinSizeForPool(req);
    	Dimension requestSize = request.getSize() == null ? 
    			null : 
    		request.getSize().getCopy();
    	if (requestSize == null) {
    		// default minimum size for adding a new shape.
    		requestSize = new Dimension(300, 100);
    		
    		if (request instanceof CreateViewRequest) {
    			List descriptors = ((CreateViewRequest) request).getViewDescriptors();
    			if (!descriptors.isEmpty()) {
    				ViewDescriptor desc = (ViewDescriptor) descriptors.get(0);
    				if (desc.getElementAdapter() != null) {
    				    IElementType type = (IElementType) desc.getElementAdapter().
    				    getAdapter(IElementType.class);
    				    requestSize = BpmnShapesDefaultSizes.getDefaultSize(type).getCopy();
    				}
    			}
    		}
    		
    	}
    	Dimension initialdim = ((GraphicalEditPart) ((GraphicalEditPart) getHost()).getParent()).getFigure().getSize().getCopy();
    	
    	Point loc = request.getLocation().getCopy();
        getHostFigure().translateToRelative(loc);
    	dim.width = (int) Math.max(dim.width, loc.x + requestSize.width - initialdim.width
    			+ INSETS.getWidth());
    	dim.height = (int) Math.max(dim.height, loc.y + requestSize.height - initialdim.height
    			 + INSETS.getHeight());
    	
//    	 now calculate the delta.
    	return dim;
    }
    
    /**
     * Finds the minimum size for the pool by examining the children,
     * if they are modified by the request, then they are resized and or moved, 
     * so that the min size is changed accordingly.
     * @param request
     * @return
     */
    private Dimension getMinSizeForPool(ChangeBoundsRequest request) {
    	Dimension initialdim = ((GraphicalEditPart) getHost()).getFigure().getSize().getCopy();
    	Dimension dim = new Dimension(0,0);
    	for (Object child : getHost().getChildren()) {
    		IGraphicalEditPart part = (IGraphicalEditPart) child;
    		Rectangle r = part.getFigure().getBounds().getCopy();
    		int w = (int) (r.x + r.width);
    		if (request.getEditParts().contains(part)) {
    			w += (request.getSizeDelta().width + request.getMoveDelta().x) / _zoom.getZoom();
    			w += 300;
    		}
    		if (dim.width < w) {
    			dim.width = w;
    		}
    		int h = r.y + r.height;
    		if (request.getEditParts().contains(part)) {
    			h += (request.getSizeDelta().height + request.getMoveDelta().y) / _zoom.getZoom();
    		}
    		if (dim.height < h) {
    			dim.height = h;
    		}
    	}
    	
    	// now calculate the delta.
    	dim.width = (int) (dim.width - initialdim.width + INSETS.getWidth());
    	dim.height = (int) (dim.height - initialdim.height + INSETS.getHeight());
    	if (dim.width < 0) {
    		dim.width = 0;
    	}
    	if (dim.height < 0) {
    		dim.height = 0;
    	}
    	return dim;
    }
    /**
     * Indexes the various lane edit part and their bounds.
     * 
     * @param map
     *            edit part - bounds map
     * @param orderedLaneBounds
     *            sorted bounds set (sorts rectangles by their coordinate)
     * @param request
     *            change bouds request (can be <code>null</code> for create
     *            command)
     * @param cc
     *            compound command to append set bounds command for annotations.
     *            Can be <code>null</code> for create request.
     * @return maximal poool width inside diagram.
     */
    private void fillMapAndSet(
            Map<IGraphicalEditPart, Rectangle> map,
            SortedSet<Rectangle> orderedLaneBounds,
            ChangeBoundsRequest request, CompoundCommand cc) {
        List children = request != null ? request.getEditParts()
                : Collections.EMPTY_LIST;
        List allChildren = getHost().getChildren();

        Iterator iter = allChildren.iterator();
        while (iter.hasNext()) {
            IGraphicalEditPart child = (IGraphicalEditPart) iter.next();
            int x = 0;
            if (child instanceof LaneEditPart && 
                    child.resolveSemanticElement() != null) {
                Rectangle bounds;
                if (children.contains(child)) {
                    bounds = (Rectangle) translateToModelConstraint(getConstraintFor(
                            request, child));
                    bounds.x = x;
                } else {
                    int y = (Integer) child
                            .getStructuralFeatureValue(NotationPackage.eINSTANCE
                                    .getLocation_Y());
                    int width = (Integer) child
                            .getStructuralFeatureValue(NotationPackage.eINSTANCE
                                    .getSize_Width());
                    int height = (Integer) child
                            .getStructuralFeatureValue(NotationPackage.eINSTANCE
                                    .getSize_Height());
                    bounds = new Rectangle(x, y, width, height);
                }

                map.put(child, bounds);
                orderedLaneBounds.add(bounds);
            } else if (children.contains(child)) {
                Command c = createChangeConstraintCommand(request, child,
                        translateToModelConstraint(getConstraintFor(request,
                                child)));
                cc.add(c);
            } else {
                
            }
        }
    }
    
    /**
     * Simple comparator class for Rectangles comparison
     * 
     * @author MPeleshchyshyn
     * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
     */
    private class RectanglesComparator implements Comparator<Rectangle> {

        /**
         * Rectangular A is bigger then rectangular B if Y coordinate of A is
         * bigger then Y coordinate of b
         */
        public int compare(Rectangle r1, Rectangle r2) {
            if (r1.y > r2.y) {
                return 1;
            } else if (r1.y < r2.y) {
                return -1;
            }
            return 0;
        }
    }

    
    /**
     * Lays out lanes, sets pools' width, creates
     * <code>SetBoundsCommand</code>s and appends them to the specified
     * compound command
     * 
     * @param map
     *            edit part - bounds map
     * @param set
     *            sorted bounds set - holds all lanes' bounds ordered by y
     *            coordinate
     * @param cc
     *            compound command to append create set bounds commands
     * @param insertedRectangles
     *            The set of rectangles that don't have an edit part yet
     *            as they correspond to the bounds of created objects
     */
    private void doLayout(ChangeBoundsRequest request,
            Map<IGraphicalEditPart, Rectangle> map,
            SortedSet<Rectangle> set, CompoundCommand cc,
            Set<Rectangle> insertedRectangles) {

        //see if the BPmnDiagramLayout edit policy called us.
        //we check if the container is the edit part being modifid.
        //if it is the case we either shorten the top lane or the bottom lane.
        //by the size difference.
        if (request != null && request.getEditParts() != null &&
                request.getEditParts().size() == 1 && 
                request.getEditParts().get(0) instanceof PoolEditPart) {
            //the pool edit part that contains this compartment is being resized.
            //we need to resize either the top-most lane or the bottom most lane.
            PoolEditPart container = (PoolEditPart)request.getEditParts().get(0);
            if (container == getHost().getParent() &&
                    request.getSizeDelta().height != 0) {
                Rectangle r = null;
                if (request.getMoveDelta().y != 0) {
                    //then it is the top line of the pool that is moved
                    //and causes the resize
                    //resize the first lane:
                    Iterator<Rectangle> it = set.iterator();
                    r = it.next();
                    r.setSize(r.width, r.height + request.getSizeDelta().height);
                    if (it.hasNext()) {
                        Rectangle second = it.next();
                        second.y = r.y + r.height;
                    }
                } else {
                    //it is the last
                    r = set.last();
                    r.setSize(r.width, r.height + request.getSizeDelta().height);
                }
            } else {
                return;
            }
        } else if (request != null) {
            
            //decide if the resize is happening at the bottom of a lane or at the top
            if (request.getMoveDelta().y != 0 &&
                    request.getSizeDelta().height == -request.getMoveDelta().y) {
                //it is the resize of the top of a lane
                LinkedList<Rectangle> rects = new LinkedList<Rectangle>(set);
                Rectangle prevLast = null;//rects.removeLast();
                boolean foundTheFirst = false;
                int beforeChangeTopY = -1;
                int beforeChangeBottomY = -1;
                while (!rects.isEmpty()) {
                    Rectangle last = rects.removeLast();
                    if (prevLast != null) {
                        if (prevLast.y != last.y + last.height) {
                            beforeChangeTopY = last.y;
                            beforeChangeBottomY = last.y + last.height;
                            last.height = prevLast.y - last.y;
                            if (last.height <= 48) {
                                last.height = 48;
                                int prevBottomY = prevLast.y + prevLast.height;
                                prevLast.y = last.y + last.height;
                                prevLast.height = prevBottomY - prevLast.y;
                            }
                            
                            //the following is dumb: 2 editparts are actually updated.
                            //not one.
                            /*
                            if (true) {
                                //found the first lane.
                                //it is the lane being resized.
                                //let's compute the the coeff of the resize.
                                //then let's move all the shapes inside the lane
                                //to keep them inside the lane
                                foundTheFirst = true;
                                for (EditPart child : (List<EditPart>)getHost().getChildren()) {
                                    if (child instanceof ShapeEditPart && !(child instanceof LaneEditPart)) {
                                        ShapeEditPart se = (ShapeEditPart)child;
                                        Point topLeft = se.getFigure().getBounds().getTopLeft();
                                        System.err.println(beforeChangeBottomY + " > " + topLeft.y + " >= " + beforeChangeTopY);
                                        if (topLeft.y >= beforeChangeTopY &&
                                                topLeft.y < beforeChangeBottomY) {
                                            System.err.println("got one");
                                            Rectangle newB =
                                                se.getFigure().getBounds().getCopy();
                                            
                                            int beforeLaneHeight = beforeChangeBottomY - beforeChangeTopY;
                                            int afterLaneHeight = last.height;
                                            double coeff = 1.0*afterLaneHeight/beforeLaneHeight;
                                            
                                            int diffChildToBottom = -beforeChangeBottomY + topLeft.y;
                                            double newDiff = diffChildToBottom * coeff;
                                            
                                            newB.y = topLeft.y + (int)Math.round(newDiff);
                                            Command c = createChangeConstraintCommand(se, newB);
                                            cc.add(c);
                                        }
                                    }
                                }
                                
                            }*/
                            
                        }
                    }
                    prevLast = last;
                }
            } else {
                //resize happening at the bottom.

                // prevent lanes overlapping
                Rectangle firstBounds = null;
                for (Rectangle secondBounds : set) {
                    if ((firstBounds != null &&
                            firstBounds.y + firstBounds.height != secondBounds.y) ||
                            secondBounds.y != INSETS.top){
        //                int yDelta = firstBounds != null ?
        //                    firstBounds.y + firstBounds.height - secondBounds.y :
        //                        INSETS.top - secondBounds.y;
                        int heightDelta = -secondBounds.y;
                        if (firstBounds == null) {
                            secondBounds.setLocation(0, INSETS.top);
                        } else {
                            secondBounds.setLocation(0, firstBounds.y + firstBounds.height);
                        }
                        heightDelta += secondBounds.y;
                        //reduce the height of the lane to accomodate the change:
                        secondBounds.setSize(secondBounds.width, secondBounds.height - heightDelta);
                    }
                    firstBounds = secondBounds;
                }
                //make sure the last entry actually fills the rest of the room:
                //make sure the last one is updated to be at the border of the pool:
                Point bottomLane = firstBounds.getBottom();
                Point bottomPool = getHostFigure().getBounds().getBottom();
                
                if (bottomLane.y != bottomPool.y - INSETS.bottom) {
                    firstBounds.setSize(firstBounds.width, firstBounds.height
                            + bottomPool.y - bottomLane.y - INSETS.bottom);
                }
        
            }
        }

        Set<Map.Entry<IGraphicalEditPart, Rectangle>> entries = map.entrySet();
        Iterator<Map.Entry<IGraphicalEditPart, Rectangle>> outerIterator = entries
                .iterator();
        while (outerIterator.hasNext()) {
            Map.Entry<IGraphicalEditPart, Rectangle> outerEntry = outerIterator
                    .next();
            IGraphicalEditPart pep = outerEntry.getKey();
            Rectangle rect = outerEntry.getValue();

            Rectangle prevBounds = pep.getFigure().getBounds();
            if (!prevBounds.equals(rect)) {
                Command c = createChangeConstraintCommand(pep, rect);
                cc.add(c);
            }
        }
    }

    @Override
    protected Command getCreateCommand(CreateRequest request) {
    	// change the location of the request if in the insets. or negative
    	Point loc = request.getLocation().getCopy();
    	Rectangle here = getHostFigure().getParent().getBounds().getCopy();
        getHostFigure().getParent().translateToAbsolute(here);
        getHostFigure().translateToAbsolute(loc);
        getHostFigure().translateToRelative(loc);
    	loc.x -= here.x;
		loc.y -= here.y;
    	
		
		//find out the size of the object to be created
        Dimension requestSize = request.getSize() == null ? 
                null : 
            request.getSize().getCopy().scale(1/_zoom.getZoom());
        
        if (requestSize == null) {
            // default minimum size for adding a new shape.
            requestSize = new Dimension(300, 100);
            
            if (request instanceof CreateViewRequest) {
                List descriptors = ((CreateViewRequest) request).getViewDescriptors();
                if (!descriptors.isEmpty()) {
                    ViewDescriptor desc = (ViewDescriptor) descriptors.get(0);
                    if (desc.getElementAdapter() != null) {
                        IElementType type = (IElementType) desc.getElementAdapter().
                        getAdapter(IElementType.class);
                        requestSize = BpmnShapesDefaultSizes.getDefaultSize(type).getCopy();
                    }
                }
            }
        } 
        //if the object is too much on the right
        if (loc.x + requestSize.width*_zoom.getZoom() > here.width) {
            request.getLocation().x -= loc.x + Math.round(requestSize.width*_zoom.getZoom()) - here.width +1 +1*_zoom.getZoom();
        }
        // if the object is created too low
        if (loc.y + requestSize.height*_zoom.getZoom() > here.height) {
            request.getLocation().y -= loc.y + Math.round(requestSize.height*_zoom.getZoom()) - here.height +1 +1*_zoom.getZoom();
        }
        // if the object is created too much on the left 
        if (loc.x < 0) {
            request.getLocation().x += - loc.x + 1;
        }
        // if the object is created too much at the top
        if (loc.y < 0) {
            request.getLocation().y += - loc.y + 1;
        }
    	
    	
        CreateViewRequest req = (CreateViewRequest) request;
        TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
                .getEditingDomain();

        CompositeTransactionalCommand cc = new CompositeTransactionalCommand(
                editingDomain, BpmnDiagramMessages.AddCommand_Label);
        Iterator iter = req.getViewDescriptors().iterator();

        final Rectangle BOUNDS = (Rectangle) getConstraintFor(request);

        Map<IGraphicalEditPart, Rectangle> map = new HashMap<IGraphicalEditPart, Rectangle>();
        SortedSet<Rectangle> set = new TreeSet<Rectangle>(
                new RectanglesComparator());
        fillMapAndSet(map, set, null, null);
        Map<ViewDescriptor, Rectangle> descriptorsMap = new HashMap<ViewDescriptor, Rectangle>();
        Set<Rectangle> insertedRectangles = null;
        while (iter.hasNext()) {
            CreateViewRequest.ViewDescriptor viewDescriptor = (CreateViewRequest.ViewDescriptor) iter
                    .next();
            Rectangle rect = getBoundsOffest(req, BOUNDS, viewDescriptor);
            if (viewDescriptor.getSemanticHint() != null &&
            		viewDescriptor.getSemanticHint().equals(
                    String.valueOf(LaneEditPart.VISUAL_ID))) {
                rect.x = 0;
                //divide by 2 the height of the lane where
                //the new lane is created.
                //use the new height as the height of the lane.
                Point location = req.getLocation().getCopy();
                getHostFigure().translateToRelative(location);
                int ind = 0;
                for (Rectangle otherLane : set) {
//                    System.err.println(otherLane.y + " compared " + location.y
//                            + " upper " + otherLane.y + otherLane.height);
                    if (otherLane.y < location.y && otherLane.y + otherLane.height >= location.y) {
                        //ok we got the lane
                        otherLane.height = otherLane.height / 2;
                        rect.height = otherLane.height;
                        //see if we insert it before or after the lane
                        //where the creation request is made.
                        //if in the lower half we insert it after.
                        //if in the top half we insert it before.
                        if (otherLane.y + otherLane.height >= location.y) {
                            rect.y = otherLane.y;
//                          add +2 to make sure a command will be issued.
                            otherLane.y += rect.height + 2;
                        } else {
                            rect.y = otherLane.y + rect.height + 2;
                        }
                        break;
                    }
                    ind++;
                }
                
                if (insertedRectangles == null) {
                    insertedRectangles = new HashSet<Rectangle>();
                }
                if (ind == set.size()) {
                    //it is the first lane in a pool.
                    //make sure it will take the entire height:
                    rect.y = INSETS.top;
//                    PoolPoolCompartmentEditPart comp =
//                        (PoolPoolCompartmentEditPart)getHost();
                    rect.height = getHostFigure().getBounds().height
//                        ((PoolEditPart)comp.getParent()).getSize().height
                        - PoolPoolCompartmentEditPart.INSETS.top
                        - PoolPoolCompartmentEditPart.INSETS.bottom;
                }
                insertedRectangles.add(rect);                
            }
            descriptorsMap.put(viewDescriptor, rect);
        }
        if (insertedRectangles != null) {
            //now add the rectangles:
            set.addAll(insertedRectangles);
        }

        CompoundCommand compoundCommand = new CompoundCommand();
        doLayout(null, map, set, compoundCommand, insertedRectangles);
        if (compoundCommand.canExecute()) {
            cc.compose(new CommandProxy(compoundCommand));
        }
        Iterator<Map.Entry<ViewDescriptor, Rectangle>> iterator = descriptorsMap
                .entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ViewDescriptor, Rectangle> entry = iterator.next();
            cc.compose(new SetBoundsCommand(editingDomain,
                    BpmnDiagramMessages.SetLocationCommand_Label_Resize, entry
                            .getKey(), entry.getValue()));
        }

        ChangeBoundsRequest thisSizeReq = new ChangeBoundsRequest(
    			RequestConstants.REQ_RESIZE_CHILDREN);
        thisSizeReq.setEditParts(getHost().getParent());
        thisSizeReq.setCenteredResize(true);
        thisSizeReq.setSizeDelta(getMinSizeForPool(request));
    	
        Command diagCo = getHost().getParent().getParent().
            getCommand(thisSizeReq);
            if (diagCo != null) {
                cc.compose(new CommandProxy(diagCo));
            }
    	
        if (cc.reduce() == null)
            return null;

        return chainGuideAttachmentCommands(request, new ICommandProxy(cc
                .reduce()));
    }    
    
    /**
     * This mysterious method checks that the move of the element in the
     * subprocess wouldn't place at negative coordinates, which would
     * mess up pretty much everything.
     * @param request
     */
    private CompoundCommand chunkNegativeMove(
    		ChangeBoundsRequest request) {
    	if (request == null) {
    		return null;
    	}
    	boolean isNegative = false;
		Point maxMove = new Point(request.getMoveDelta().x, 
				request.getMoveDelta().y);
		for (Object ep : request.getEditParts()) {
			Point loc = ((GraphicalEditPart) ep).getFigure().getBounds().
				getLocation().getCopy();
			int xmove = loc.x + request.getMoveDelta().x;
			if (xmove <= 0) {
				maxMove.x =  -loc.x;
				isNegative = true;
			}
			int ymove = loc.y + request.getMoveDelta().y;
			if (ymove <= 0) {
				maxMove.y = -loc.y;
				isNegative = true;
			}
		}
		
		Point p = new Point(request.getMoveDelta().x - maxMove.x, 
				request.getMoveDelta().y - maxMove.y);
		request.setMoveDelta(maxMove);
		
//		 one last test. Maybe the move is negative and going too far.
		// we have modified the request already
		if (isNegative) {
			// diagonal negative move.
			// |
			// |    /
			// |   /
			// | |/_
			// |___________________
			
			int toobigy = request.getMoveDelta().y + request.getSizeDelta().height;
			int maxHeight = 0;
			for (Object ep : request.getEditParts()) {
				maxHeight = Math.max(maxHeight, ((GraphicalEditPart) ep).
						getFigure().getBounds().height +((GraphicalEditPart) ep).
						getFigure().getBounds().y);
			}
			if (getHostFigure().getBounds().height < (toobigy + maxHeight)) {
				isNegative = false;
			}
		}
		
		if (isNegative) {
//			now test this case :
			/*
			 * _______________
			 *            /\ |
			 *           /   |
			 *          /    |
			 * 
			 */
			
			int toobigx = request.getMoveDelta().x + request.getSizeDelta().width;
			int maxWidth = 0;
			for (Object ep : request.getEditParts()) {
				maxWidth = Math.max(maxWidth, ((GraphicalEditPart) ep).
						getFigure().getBounds().width +((GraphicalEditPart) ep).
						getFigure().getBounds().x);
			}
			if (getHostFigure().getBounds().width < (toobigx + maxWidth)) {
				isNegative = false;
			}
		}
		if (!isNegative) {
			return null;
		}
		
		ChangeBoundsRequest compartmentRequest = 
			new ChangeBoundsRequest(RequestConstants.REQ_RESIZE_CHILDREN);
		
		Dimension d = new Dimension(Math.abs(p.x), Math.abs(p.y));
		compartmentRequest.setMoveDelta(new Point(0, 0));
		compartmentRequest.setSizeDelta(d);
		compartmentRequest.setEditParts(getHost().getParent());
		ChangeBoundsRequest childrenRequest = 
			new ChangeBoundsRequest(RequestConstants.REQ_MOVE_CHILDREN);
		Point childrenMove = new Point(d.width, d.height);
		childrenRequest.setMoveDelta(childrenMove);
		List childrenToMove = new ArrayList();
		for (Object child : getHost().getChildren()) {
		    if (request.getEditParts().contains(child)) {
		        continue;
		    }
		    if (child instanceof IGraphicalEditPart && 
		            ((IGraphicalEditPart) child).getNotationView() instanceof Node) {
		        Location b = (Location) ((Node) ((IGraphicalEditPart) child).
		                getNotationView()).getLayoutConstraint();
		        if (b.getX() == -1 && b.getY() == -1) {
		            continue;
		        }
		    }
		    childrenToMove.add(child);
		}
		childrenRequest.setEditParts(childrenToMove);
		// we use a copy of the array so that the new edit part will not be moved
		
		CompoundCommand co = new CompoundCommand();
		co.add(super.getResizeChildrenCommand(request));
		Command comm = getHost().getParent().getParent().getCommand(compartmentRequest);
		co.add(comm);
		if (!childrenRequest.getEditParts().isEmpty() && (childrenRequest.getMoveDelta().x != 0 || 
		        childrenRequest.getMoveDelta().y != 0)) {
			co.add(getHost().getCommand(childrenRequest));
		}
		return co;
    }
    
    /**
     * Creates snap back commands by iterating over the source and target
     * connections of the interesting parts
     * @param compound the compound command to populate
     * @param request the current request
     */
    private void makeSnapBackCommands(CompoundCommand compound, ChangeBoundsRequest request) {
        for (Object epObj : request.getEditParts()) {
            if (epObj instanceof AbstractGraphicalEditPart) {
                iterateOverConnections(
                        ((AbstractGraphicalEditPart) epObj).getSourceConnections(), 
                        compound);
                iterateOverConnections(
                        ((AbstractGraphicalEditPart) epObj).getTargetConnections(), 
                        compound);
            }
        }
    }
    
    /**
     * Iterates over connections, and gets their labels, then asks them for a snap back command.
     * @param connections the list of connections.
     * @param compound the compound command populated
     */
    private void iterateOverConnections(List connections, CompoundCommand compound) {
        Request snapBack = new Request(RequestConstants.REQ_SNAP_BACK);
        for (Object con : connections) {
            if (con instanceof ConnectionEditPart) {
                LabelEditPart label = (LabelEditPart) ((ConnectionEditPart) con).getChildBySemanticHint(
                        BpmnVisualIDRegistry.getType(SequenceEdgeNameEditPart.VISUAL_ID));
                if (label == null) {
                    label = (LabelEditPart) ((ConnectionEditPart) con).getChildBySemanticHint(
                            BpmnVisualIDRegistry.getType(MessagingEdgeNameEditPart.VISUAL_ID));
                }
                if (label != null) {
                    compound.add(label.getCommand(snapBack));
                }
            }
        }
    }
}
