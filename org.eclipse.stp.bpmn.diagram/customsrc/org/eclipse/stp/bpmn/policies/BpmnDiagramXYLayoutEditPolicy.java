/******************************************************************************
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Dates       		 Author              Changes
 * Feb 12, 2007      Antoine Toulmé   Creation
 */
package org.eclipse.stp.bpmn.policies;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * Policy to enforce layout constraints on pools:
 * no overlapping
 * hopefully some space between children:
 * as we cannot touch the pool before it is created, we move the pool below its
 * location.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BpmnDiagramXYLayoutEditPolicy extends XYLayoutEditPolicy {

	/** TODO: replace this by taking into account the page guides */
	// 15 january 2007: set the offset from 10 to 16, seems better.
    public static final int DEFAULT_POOL_X_COORD = 16;
    
    
	@Override
	protected Command getResizeChildrenCommand(ChangeBoundsRequest request) {
	    // don't bother this edit policy if your calls are not
	    // about a part, and if there is neither move nor resize planned.
	    if (request.getEditParts() == null || request.getEditParts().isEmpty()) {
	        return null;
	    }
	    if ((request.getMoveDelta() == null || 
	            request.getMoveDelta().equals(new Point(0, 0))) &&
	            (request.getSizeDelta() == null || 
	                    request.getSizeDelta().equals(new Dimension(0, 0)))) {
	        return null;
	    }
		//calculate maxWidth
		int maxWidth = /*PoolEditPart.POOL_WIDTH*/ 200;
		// put child parts in a map, referenced by their bounds
		// if changes apply to their bounds, record them in the bounds used as key
		Map<Rectangle, IGraphicalEditPart> toSortBounds = 
			new LinkedHashMap<Rectangle, IGraphicalEditPart>();
		
		for (Object child : getHost().getChildren()) {
			IGraphicalEditPart childPart = (IGraphicalEditPart) child;
			Rectangle rect = childPart.getFigure().getBounds().getCopy();
			
			if (request.getEditParts() != null && 
                    request.getEditParts().contains(child)) {
			    
				if (request.getSizeDelta() != null) {
					rect.width += request.getSizeDelta().width;
					rect.height += request.getSizeDelta().height;
				}
				if (request.getMoveDelta() != null) {
					rect.y += request.getMoveDelta().y;
					rect.x += request.getMoveDelta().x;
				}
			}
			maxWidth = maxWidth < rect.width ? rect.width : maxWidth;
            //make sure that there is no edit parts using the same coordinates
            // thus forbidding each other to move.
            while (toSortBounds.keySet().contains(rect)) {
                rect.y += 1;
            }
			toSortBounds.put(rect, childPart);
		}
		
		// sort the new bounds by y
		List<Rectangle> keys = new LinkedList<Rectangle>
			(toSortBounds.keySet());
		Collections.sort(keys, new Comparator<Rectangle>() {

					public int compare(Rectangle o1, Rectangle o2) {
						if (o1.y <= o2.y) {
							return -1;
						} else {
							return 1;
						}
					}});
		
		int y = 0;
		
		
		// now create a chain of command, 
		// placing pools according to their y position, 
		// but not letting them choose it.
		CompoundCommand command = new CompoundCommand(BpmnDiagramMessages.BpmnDiagramXYLayoutEditPolicy_command_name);
		for (Rectangle key : keys) {
			IGraphicalEditPart part = toSortBounds.get(key);
			
			// keep the y coordinate of the pool if more than the mininum space between pools.
			y = key.y < y + DEFAULT_POOL_X_COORD ? 
					y + DEFAULT_POOL_X_COORD : key.y;
			key.y = y;
			if (part instanceof PoolEditPart) {
				key.x = DEFAULT_POOL_X_COORD;
				key.width = maxWidth;
			}
			
			Command co = createChangeConstraintCommand(part, key);
			command.add(co);
			y += key.height;
			
			// if the pool is resized in the northern direction, shapes should be moved south as an opposite
			// so that the user thinks he is resizing the pool by adding space at the top.
			if (request.getSizeDelta().height == -request.getMoveDelta().y && (request.getSizeDelta().height != 0)) {
			    if (request.getEditParts() != null && request.getEditParts().contains(part)) {
			        final IGraphicalEditPart comp = part.getChildBySemanticHint(BpmnVisualIDRegistry.getType(
			                PoolPoolCompartmentEditPart.VISUAL_ID));
			        final int ymove = request.getSizeDelta().height;
			            for (Object child : comp.getChildren()) {
			                IGraphicalEditPart ep = (IGraphicalEditPart) child;
			                Rectangle rect = ep.getFigure().getBounds().getCopy();
			                rect.y += ymove;
			                command.add(createChangeConstraintCommand(ep, rect));
			            }
			    }
			}
		}
		return command;
	}
	
	@Override
	protected Object getConstraintFor(CreateRequest request) {
		Object constraint = super.getConstraintFor(request);
		if (request == null || request.getNewObject() == null || 
                ((List) request.getNewObject()).isEmpty() ||
				((List) request.getNewObject()).get(0) == null ||
				((ViewDescriptor) ((List) request.getNewObject()).get(0)).
					getElementAdapter() == null ||
				((List) request.getNewObject()).get(0) == null) {
			return constraint;
		}
		 IElementType type = (IElementType)
			((ViewDescriptor) ((List) request.getNewObject()).get(0)).getElementAdapter().getAdapter(IElementType.class);
		 
		 
		 //recalculate maxWidth
		 int maxWidth = 200;
		 if (((BpmnDiagram) ((IGraphicalEditPart) getHost()).resolveSemanticElement()).
				 	getPools().isEmpty()) {
			 maxWidth = PoolEditPart.POOL_WIDTH;
		 }
		 

		 for (Object child : getHost().getChildren()) {
			 IGraphicalEditPart childPart = (IGraphicalEditPart) child;
			 Rectangle rect = childPart.getFigure().getBounds().getCopy();

			 maxWidth = maxWidth < rect.width ? rect.width : maxWidth;
		 }
		 
		if (type == BpmnElementTypes.Pool_1001) {
			((Rectangle) constraint).x = DEFAULT_POOL_X_COORD; 
			((Rectangle) constraint).width = maxWidth;
			((Rectangle) constraint).height = PoolEditPart.POOL_HEIGHT;
			return constraint;
		}
		return constraint;
	}
	
	
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		CompoundCommand co = new CompoundCommand();
		co.add(super.getCreateCommand(request));
		ChangeBoundsRequest req = new ChangeBoundsRequest();
		// find if there is a pool below.
		Rectangle addedThing = ((Rectangle) getConstraintFor(request));
		List children = ((IGraphicalEditPart) getHost()).getChildren();
		List<IGraphicalEditPart> parts = new LinkedList<IGraphicalEditPart>();
		
		Point location = request.getLocation().getCopy();
        getHostFigure().translateToRelative(location);
		for (Object child : children) {
			Rectangle rect = ((IGraphicalEditPart) child).getFigure().getBounds();
			if (rect.y > (location.y)) { // added 5
				parts.add((IGraphicalEditPart) child);
			}
		}
		if (!parts.isEmpty()) {
			req.setEditParts(parts);
			req.setConstrainedMove(true);
			
			req.setMoveDelta(new Point(
					addedThing.x, 
					((Rectangle) getConstraintFor(request)).height + 
					DEFAULT_POOL_X_COORD));
			co.add(getResizeChildrenCommand(req));
		}
		return co;
	}
}
