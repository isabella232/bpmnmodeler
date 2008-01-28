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
 * Mar 8, 2007      Antoine Toulmé   Creation
 */
package org.eclipse.stp.bpmn.figures;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.commands.ElementTypeEx;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotationEditPart;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * This class holds the default sizes for the shapes.
 * It gives the size for an element type as well. It will
 * return (-1, -1) if it doesn't find a correct size.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BpmnShapesDefaultSizes {

	/**
	 * The default size, (-1, -1).
	 */
	public static final Dimension DEFAULT_SIZE = new Dimension(-1, -1);
	
	public static final Dimension getDefaultSize(IElementType type) {
		if (type == null) {
			return DEFAULT_SIZE;
		}
		if (BpmnElementTypes.Activity_2001.getId().equals(type.getId())) {
			if (type instanceof ElementTypeEx) {
				String sHint = ((ElementTypeEx) type).getSecondarySemanticHint();
				ActivityType actype = ActivityType.get(sHint);
				if (ActivityType.VALUES_EVENTS.contains(actype)) {
					return new Dimension(ActivityEditPart.EVENT_FIGURE_SIZE, 
							ActivityEditPart.EVENT_FIGURE_SIZE);
				} else if (ActivityType.VALUES_GATEWAYS.contains(actype)) {
					return new Dimension(ActivityEditPart.GATEWAY_FIGURE_SIZE, 
							ActivityEditPart.GATEWAY_FIGURE_SIZE);
				} else if (ActivityType.SUB_PROCESS_LITERAL.equals(actype)) {
					return SubProcessEditPart.EXPANDED_SIZE;
				} else {
					return ActivityEditPart.ACTIVITY_FIGURE_SIZE;
				}
			} else {
				return ActivityEditPart.ACTIVITY_FIGURE_SIZE;
			}
		}
		if (BpmnElementTypes.Activity_2003.getId().equals(type.getId())) {
			return new Dimension(ActivityEditPart.EVENT_FIGURE_SIZE, 
					ActivityEditPart.EVENT_FIGURE_SIZE);
		}
		if (BpmnElementTypes.Pool_1001.equals(type)) {
			return new Dimension(PoolEditPart.POOL_WIDTH, 
					PoolEditPart.POOL_HEIGHT);
		}
		// added the comparison by id in case it is a looping subprocess.
		if (BpmnElementTypes.SubProcess_2002.getId().equals(type.getId())) {
			return SubProcessEditPart.EXPANDED_SIZE;
		}
		if (BpmnElementTypes.TextAnnotation_1002.equals(type)||
                BpmnElementTypes.TextAnnotation_2004.equals(type)) {
            Dimension dim = TextAnnotationEditPart.TEXT_FIGURE_SIZE.getCopy();
            dim.height += TextAnnotationEditPart.TEXT_FIGURE_INSETS.getHeight();
            dim.width += TextAnnotationEditPart.TEXT_FIGURE_INSETS.getWidth();
			return dim;
		}
		if (BpmnElementTypes.DataObject_1003.equals(type)) {
			// TODO
		}
		if (BpmnElementTypes.DataObject_2005.equals(type)) {
			// TODO
		}
		if (BpmnElementTypes.Group_1004.equals(type)) {
			// TODO
		}
		if (BpmnElementTypes.Group_2006.equals(type)) {
			// TODO
		}
		if (BpmnElementTypes.Lane_2007.equals(type)) {
			// not implemented.
		}
		return DEFAULT_SIZE;
	}
	
	
}
