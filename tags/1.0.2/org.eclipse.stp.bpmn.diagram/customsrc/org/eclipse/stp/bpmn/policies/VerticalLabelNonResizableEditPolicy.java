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
 * 20 Oct 2006      MPeleshchyshyn      Created 
 **/
package org.eclipse.stp.bpmn.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.handles.HandleBounds;
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.gef.handles.MoveHandleLocator;
import org.eclipse.stp.bpmn.figures.VerticalLabel;

/**
 * Changes move handle locator to correctly calculate bounds of vertical label.
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class VerticalLabelNonResizableEditPolicy extends NonResizableEditPolicy {
    protected List createSelectionHandles() {
        List handles = new ArrayList();
        GraphicalEditPart editPart = (GraphicalEditPart) getHost();
        MoveHandle moveHandle = new MoveHandle(editPart, new MoveHandleLocator(
                editPart.getFigure()) {
            public void relocate(IFigure target) {
                Insets insets = target.getInsets();
                Rectangle bounds;
                if (getReference() instanceof HandleBounds)
                    bounds = ((HandleBounds) getReference()).getHandleBounds();
                else {
                    bounds = ((VerticalLabel) getReference()).getTextBounds();
                    Rectangle figureBounds = getReference().getBounds();
                    bounds.x = figureBounds.x;
                    bounds.y = figureBounds.y;
                }
                bounds = new PrecisionRectangle(bounds.getResized(-1, -1));
                getReference().translateToAbsolute(bounds);
                target.translateToRelative(bounds);
                bounds.translate(-insets.left, -insets.top);
                bounds.resize(insets.getWidth() + 1, insets.getHeight() + 1);
                target.setBounds(bounds);
            }
        });
        handles.add(moveHandle);
        return handles;
    }

    public Command getCommand(Request request) {
        return null;
    }

    public boolean understandsRequest(Request request) {
        return false;
    }
}
