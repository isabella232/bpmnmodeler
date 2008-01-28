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
 * Dec 5, 2006     MPeleshchsyhyn         Created
 **/
package org.eclipse.stp.bpmn.commands;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateViewAndOptionallyElementCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnDiagramEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;
import org.eclipse.stp.bpmn.figures.BpmnShapesDefaultSizes;
/**
 * added the possibility to offset the creation of the shape
 * by the half of their default size either on x, y or both.
 * 
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class CreateViewAndOptionallyElementCommandEx extends
        CreateViewAndOptionallyElementCommand {

    private boolean xoffset;
	private boolean yoffset;

	public CreateViewAndOptionallyElementCommandEx(IAdaptable elementAdapter,
            IGraphicalEditPart containerEP, Point location,
            PreferencesHint preferencesHint, boolean offsetOnX, 
            boolean offsetOnY) {
        super(elementAdapter, containerEP, location, preferencesHint);
        xoffset = offsetOnX;
        yoffset = offsetOnY;
    }

    /**
     * Finds the target edit part, even if belonging to a different container.
     */
    @Override
    protected View getExistingView(EObject element) {
        IGraphicalEditPart container = getContainerEP();
        while (!(container instanceof BpmnDiagramEditPart)) {
            container = (IGraphicalEditPart) container.getParent();
        }

        List pools = container.getChildren();

        for (Iterator iter = pools.iterator(); iter.hasNext();) {
            IGraphicalEditPart gep = (IGraphicalEditPart) iter.next();
            if (!(gep instanceof PoolEditPart)) {
                continue;
            }
            IGraphicalEditPart theTarget = (IGraphicalEditPart) gep
                    .findEditPart(null, element);

            if (theTarget != null) {
                return (View) theTarget.getModel();
            }
        }

        return null;
    }

    @Override
    protected Point getLocation() {
    	IElementType type = (IElementType) getElementAdapter()
			.getAdapter(IElementType.class);
    	if (type != null) {
    		Dimension defaultDim = 
    			BpmnShapesDefaultSizes.getDefaultSize(type);
    		if (!BpmnShapesDefaultSizes.DEFAULT_SIZE.equals(defaultDim)) {
    			Point pt = super.getLocation().getCopy();
    			if (yoffset) {
    				pt.y -= defaultDim.height/2;
    			}
    			if (xoffset) {
    				pt.x -= defaultDim.width/2;
    			}
    			return pt;
    		}
    	}
    	return super.getLocation();
    }
}
