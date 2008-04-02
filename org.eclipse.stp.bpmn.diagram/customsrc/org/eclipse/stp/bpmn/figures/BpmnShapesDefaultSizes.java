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
 * Mar 8, 2007      Antoine Toulm&eacute;   Creation
 */
package org.eclipse.stp.bpmn.figures;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.commands.ElementTypeEx;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBorderCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotationEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * This class holds the default sizes for the shapes.
 * It gives the size for an element type as well. It will
 * return (-1, -1) if it doesn't find a correct size.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BpmnShapesDefaultSizes {

	/**
	 * The default size, (-1, -1).
	 */
	public static final Dimension DEFAULT_SIZE = new Dimension(-1, -1);
	
	/**
	 * @param type The bpmn element type.
	 * @return The default dimension
	 */
	public static final Dimension getDefaultSize(IElementType type) {
        if (type == null) {
            return DEFAULT_SIZE;
        }
	    if (type instanceof ElementTypeEx) {
            String sHint = ((ElementTypeEx) type).getSecondarySemanticHint();
            return getDefaultSizeFromElementTypeId(type.getId(), sHint);
	    }
	    return getDefaultSizeFromElementTypeId(type.getId(), null);
	}
	
	/**
     * @param gmfViewNode The view for the bpmn shape
     * @return The default dimension
     */
    public static final Dimension getDefaultSize(Node gmfViewNode) {
        IHintedType type = BpmnElementTypes.getBpmnElementType(gmfViewNode.getType());
        if (type == null) {
            return DEFAULT_SIZE;
        }
        if (String.valueOf(ActivityEditPart.VISUAL_ID).equals(gmfViewNode.getType())) {
            if (gmfViewNode.getElement() instanceof Activity) {
                return getDefaultSizeFromElementTypeSemanticHint(gmfViewNode.getType(),
                        ((Activity)gmfViewNode.getElement()).getActivityType().getLiteral());
            }
        }
        return getDefaultSizeFromElementTypeSemanticHint(gmfViewNode.getType(), null);
    }
    
    /**
     * @param semanticHint The semanticHint as defined by IHintedElementType.
     *          For example "2001" for an activity in a pool
     * @param optionalActivityTypeLiteral The activity type literal if this is an activity. null otherwise.
     * @return The default dimension
     */
    public static final Dimension getDefaultSizeFromElementTypeSemanticHint(String semanticHint, String optionalActivityTypeLiteral) {
        IHintedType type = BpmnElementTypes.getBpmnElementType(semanticHint);
        if (type == null) {
            return DEFAULT_SIZE;
        }
        return getDefaultSizeFromElementTypeId(type.getId(), optionalActivityTypeLiteral);
    }
	
	/**
     * @param elementTypeId The typeId as defined by IElementType.getId.
     *              Beware, this is _not_ the semantic hint.
     * @return The default dimension
     */
    public static final Dimension getDefaultSizeFromElementTypeId(String elementTypeId,
            String optionalActivityTypeLiteral) {
        if (elementTypeId == null) {
            return DEFAULT_SIZE;
        }
        
        if (BpmnElementTypes.Activity_2001.getId().equals(elementTypeId)) {
            if (optionalActivityTypeLiteral != null) {
                ActivityType actype = ActivityType.get(optionalActivityTypeLiteral);
                if (ActivityType.VALUES_EVENTS.contains(actype)) {
                    return new Dimension(ActivityEditPart.EVENT_FIGURE_SIZE, 
                            ActivityEditPart.EVENT_FIGURE_SIZE);
                } else if (ActivityType.VALUES_GATEWAYS.contains(actype)) {
                    return new Dimension(ActivityEditPart.GATEWAY_FIGURE_SIZE, 
                            ActivityEditPart.GATEWAY_FIGURE_SIZE);
                } else if (ActivityType.SUB_PROCESS_LITERAL.equals(actype)) {
                    return SubProcessEditPart.EXPANDED_SIZE;
                }
            }
            return ActivityEditPart.ACTIVITY_FIGURE_SIZE;
        }
        if (BpmnElementTypes.Activity_2003.getId().equals(elementTypeId)) {
            return new Dimension(ActivityEditPart.EVENT_FIGURE_SIZE, 
                    ActivityEditPart.EVENT_FIGURE_SIZE);
        }
        if (BpmnElementTypes.Pool_1001.getId().equals(elementTypeId)) {
            return new Dimension(PoolEditPart.POOL_WIDTH, 
                    PoolEditPart.POOL_HEIGHT);
        }
        // added the comparison by id in case it is a looping subprocess.
        if (BpmnElementTypes.SubProcess_2002.getId().equals(elementTypeId)) {
            return SubProcessEditPart.EXPANDED_SIZE;
        }
        if (BpmnElementTypes.TextAnnotation_1002.getId().equals(elementTypeId)
                || BpmnElementTypes.TextAnnotation_2004.getId().equals(elementTypeId)) {
            Dimension dim = TextAnnotationEditPart.TEXT_FIGURE_SIZE.getCopy();
            dim.height += TextAnnotationEditPart.TEXT_FIGURE_INSETS.getHeight();
            dim.width += TextAnnotationEditPart.TEXT_FIGURE_INSETS.getWidth();
            return dim;
        }
        if (BpmnElementTypes.DataObject_1003.getId().equals(elementTypeId)) {
            // TODO
        }
        if (BpmnElementTypes.DataObject_2005.getId().equals(elementTypeId)) {
            // TODO
        }
        if (BpmnElementTypes.Group_1004.getId().equals(elementTypeId)) {
            // TODO
        }
        if (BpmnElementTypes.Group_2006.getId().equals(elementTypeId)) {
            // TODO
        }
        if (BpmnElementTypes.Lane_2007.getId().equals(elementTypeId)) {
            // not implemented.
        }
        return DEFAULT_SIZE;
    }
    

	/**
	 * @param gmfViewNode
	 * @return
	 */
	public static Bounds getBounds(Node gmfViewNode) {
	    Bounds targetLoc = (Bounds) gmfViewNode.getLayoutConstraint();
	    if (targetLoc != null) {
	        if (targetLoc.getHeight() == -1 && targetLoc.getWidth() == -1) {
	            Dimension defaultDim = getDefaultSize(gmfViewNode);
	            if (defaultDim.height != -1 && defaultDim.width != -1) {
    	            Bounds targetLocClone = NotationFactory.eINSTANCE.createBounds();
    	            targetLocClone.setHeight(defaultDim.height);
    	            targetLocClone.setWidth(defaultDim.width);
    	            targetLocClone.setX(targetLoc.getX());
    	            targetLocClone.setY(targetLoc.getY());
    	            return targetLocClone;
	            }
	        }
	    }
	    return targetLoc;
	}
	
    /**
     * Copied from SubProcessResizeTracker
     * Calculates subprocess' minimal size.
     * Returns the dimension based on the figures's bounds. These are DevicePixels.s
     * 
     * @param subprocessEditPart the subprocess edit part
     * @return calculated minimal size of the s (zoom not computed == DP DevicePixel).
     */
    public static Dimension getSubProcessMinSize(SubProcessEditPart subprocessEditPart) {
        return getSubProcessMinSize(subprocessEditPart, true);
    }
	
    /**
     * Currently unused.
     * 
     * @param subprocessEditPart the subprocess edit part
     * @param countBorder true to count the border. False to not count it. For example the reize tracker
     * does not count it.
     * @return calculated minimal size of the s (zoom not computed == DP DevicePixel).
     */
    public static Dimension getSubProcessMinSizeGMFNotation(SubProcessEditPart subprocessEditPart, boolean countBorder) {
        SubProcessSubProcessBodyCompartmentEditPart body = 
            (SubProcessSubProcessBodyCompartmentEditPart) subprocessEditPart.
            getChildBySemanticHint(BpmnVisualIDRegistry.getType(
                    SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID));
        Dimension maxRoomOfChildren = new Dimension(0, 0);
        if (body == null) {
            return maxRoomOfChildren;
        }
        
        for (Object ep : body.getChildren()) {
            if (ep instanceof GraphicalEditPart) {
                GraphicalEditPart gap = (GraphicalEditPart) ep;
                View view = gap.getNotationView();
                if (view instanceof Node) {
                    Bounds bounds = getBounds((Node)view);
                    maxRoomOfChildren.height = Math.max(
                            bounds.getY() + bounds.getHeight(), maxRoomOfChildren.height);
                    maxRoomOfChildren.width = Math.max(
                            bounds.getX() + bounds.getWidth(), maxRoomOfChildren.width);
                }
            }
        }
//        System.err.println("maxChildrenHeight=" + maxRoomOfChildren.height
//                + " insets: " + Math.floor(SubProcessEditPart.INSETS.getHeight())
//                + " handles: " + subprocessEditPart.getAbsCollapseHandleBounds(false).height);
        //when there are children, the left and top of the insets are already counted.
        int addTwoInset = maxRoomOfChildren.height != 0 ? 1 : 2;
        maxRoomOfChildren.width += SubProcessEditPart.INSETS.getWidth()*addTwoInset;
        maxRoomOfChildren.height += SubProcessEditPart.INSETS.getHeight()*addTwoInset;
        maxRoomOfChildren.height += subprocessEditPart.getAbsCollapseHandleBounds(false).height;
        if (countBorder) {
            maxRoomOfChildren.height += SubProcessEditPart.BORDER_HEIGHT/2;
        }
//        System.err.println("min.height=" + maxRoomOfChildren.height);
        return maxRoomOfChildren;
    }
    
    public static Dimension getSubProcessMinSize(SubProcessEditPart subprocessEditPart, boolean countBorder) {
        SubProcessSubProcessBodyCompartmentEditPart body = 
            (SubProcessSubProcessBodyCompartmentEditPart) subprocessEditPart.
            getChildBySemanticHint(BpmnVisualIDRegistry.getType(
                    SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID));
        if (body == null) {
            return new Dimension(0, 0);
        }
        // now take in account the shapes in the pool
        Dimension maxRoomOfChildren = new Dimension(0, 0);
//        for (Object fig : subprocessEditPart.getPrimaryShape()
//                    .getFigureSubProcessBodyFigure().getChildren()) {
        for (Object ep : body.getChildren()) {
            if (ep instanceof IGraphicalEditPart) {
                // we use the figure as width and lengths may be 
                // not initialized on the views objects
                IFigure figure = ((IGraphicalEditPart) ep).getFigure();
//                IFigure figure = (IFigure)fig;
                Rectangle bounds = figure.getBounds();
                //The bounds are relative to the compartment.
                //The maximum x and Y coord in the compartment gives the
                //the minimum width of the compartment.
                
                //MYSTERY: why do we need to multiply by the zoom?
                maxRoomOfChildren.height = Math.max(
                        bounds.y + (int)Math.floor(bounds.height), maxRoomOfChildren.height);
                maxRoomOfChildren.width = Math.max(
                        bounds.x + (int)Math.floor(bounds.width), maxRoomOfChildren.width);
            }
        }
        
//        //count the shapes on the border for the width:
//        for (Object fig : subprocessEditPart.getPrimaryShape()
//                        .getFigureSubProcessBorderFigure().getChildren()) {
//            IFigure figure = (IFigure)fig;
//            Rectangle bounds = figure.getBounds();
//            //The bounds are relative to the compartment.
//            //The maximum x and Y coord in the compartment gives the
//            //the minimum width of the compartment.
//            maxRoomOfChildren.width = Math.max(
//                    bounds.x + bounds.width, maxRoomOfChildren.width);
//        }
        
//        System.err.println("maxChildrenHeight=" + maxRoomOfChildren.height
//                + " insets: " + Math.floor(SubProcessEditPart.INSETS.getHeight()*zoom)
//                + " handles: " + subprocessEditPart.getAbsCollapseHandleBounds(true).height);
        //when there are children, the left and top of the insets are already counted.
        int addTwoInset = maxRoomOfChildren.height != 0 ? 2 : 1;
        maxRoomOfChildren.width += (int)Math.floor(SubProcessEditPart.INSETS.getWidth()*addTwoInset);
        maxRoomOfChildren.height += (int)Math.floor(SubProcessEditPart.INSETS.getHeight()*addTwoInset);
        maxRoomOfChildren.height += subprocessEditPart.getAbsCollapseHandleBounds(true).height;
        if (countBorder) {
            SubProcessSubProcessBorderCompartmentEditPart border = 
                (SubProcessSubProcessBorderCompartmentEditPart) subprocessEditPart.
                getChildBySemanticHint(BpmnVisualIDRegistry.getType(
                        SubProcessSubProcessBorderCompartmentEditPart.VISUAL_ID));
            maxRoomOfChildren.height += border.getFigure().getBounds().height;
        }
//        System.err.println(subprocessEditPart + " min.height=" + maxRoomOfChildren.height + "  currently: " + subprocessEditPart.getPrimaryShape().getBounds().height);
        return maxRoomOfChildren;
    }
    
	
}
