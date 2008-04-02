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
 * Date         	Author             Changes 
 * Sep 19, 2006   	mpeleshchyshyn  	Created 
 */
package org.eclipse.stp.bpmn.handles;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Locator;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.handles.CompartmentCollapseHandle;
import org.eclipse.gmf.runtime.notation.DrawerStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.figures.CollapseFigureEx;

/**
 * Implemented compartment collapse handle with +/- button for expand/collapse.
 * 
 * @author mpeleshchyshyn
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class CompartmentCollapseHandleEx extends CompartmentCollapseHandle {

    /**
     * Positions the supplied figure in its owner.
     */
    private class CollapseHandleLocator implements Locator {
        public void relocate(IFigure target) {
            EditPart ownerParent = getOwner().getParent();
            Rectangle theBounds = null;
            if (ownerParent instanceof SubProcessEditPart) {
                theBounds = ((SubProcessEditPart) ownerParent)
                    .getAbsCollapseHandleBounds(true);
            } else {//it is a pool put it in the top left corner
                IFigure ownerParentFig = ((IGraphicalEditPart)ownerParent).getFigure();
                theBounds = ownerParentFig.getClientArea().getCopy();          
                ownerParentFig.translateToAbsolute(theBounds);
            }
            target.translateToRelative(theBounds);
            setBounds(theBounds);
        }
    }

    /**
     * Creates new instance of collapse handler.
     * 
     * @param owner
     */
    public CompartmentCollapseHandleEx(IGraphicalEditPart owner) {
        super(owner);
        setSize(1, 1);
        setLocator(new CollapseHandleLocator());
        remove(collapseFigure);
        add(collapseFigure = new CollapseFigureEx());
        View view = owner.getNotationView();
        if (view != null) {
            DrawerStyle style = (DrawerStyle) view
                    .getStyle(NotationPackage.eINSTANCE.getDrawerStyle());
            if (style != null) {
                collapseFigure.setCollapsed(style.isCollapsed());
                return;
            }
        }
        collapseFigure.setCollapsed(false);
    }
}
