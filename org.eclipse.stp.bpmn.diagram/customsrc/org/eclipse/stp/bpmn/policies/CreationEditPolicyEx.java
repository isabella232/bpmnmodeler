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

package org.eclipse.stp.bpmn.policies;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.figures.BpmnShapesDefaultSizes;

/**
 * 
 * @author atoulme added the offset on creation.
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class CreationEditPolicyEx extends CreationEditPolicy {

	@Override
	protected Command getCreateElementAndViewCommand(CreateViewAndElementRequest request) {
		Point loc = request.getLocation().getCopy();
    	((GraphicalEditPart) getHost()).getFigure().translateToAbsolute(loc);
    	((GraphicalEditPart) getHost()).getFigure().translateToRelative(loc);
    	Rectangle rect = ((GraphicalEditPart) getHost()).getFigure().getBounds().getCopy();
    	((GraphicalEditPart) getHost()).getFigure().translateToAbsolute(rect);
    	loc.x = loc.x - rect.x;
    	loc.y = loc.y - rect.y;
		IElementType type = (IElementType) request.getViewAndElementDescriptor().
			getElementAdapter().getAdapter(IElementType.class);
		Dimension dim = BpmnShapesDefaultSizes.getDefaultSize(type).getCopy();
		Insets insets = PoolPoolCompartmentEditPart.INSETS;
		if (getHost() instanceof SubProcessSubProcessBodyCompartmentEditPart) {
			insets = SubProcessEditPart.INSETS;
		}
		int y = loc.y + dim.height + insets.bottom - rect.height;
		int x = loc.x + dim.width + insets.right - rect.width;
		x = Math.max(x, 0);
		y = Math.max(y, 0);
		
		request.getLocation().x -= x;
		request.getLocation().y -= y;
		return super.getCreateElementAndViewCommand(request);
	}
}
