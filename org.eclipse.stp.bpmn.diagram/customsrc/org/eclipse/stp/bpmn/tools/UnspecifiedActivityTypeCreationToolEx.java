/*
 *******************************************************************************
 ** Copyright (c) 2006, Intalio Inc.
 ** All rights reserved. This program and the accompanying materials
 ** are made available under the terms of the Eclipse Public License v1.0
 ** which accompanies this distribution, and is available at
 ** http://www.eclipse.org/legal/epl-v10.html
 ** 
 ** Contributors:
 **     Intalio Inc. - initial API and implementation
 ********************************************************************************
 */
package org.eclipse.stp.bpmn.tools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.commands.IElementTypeEx;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnDiagramEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * @notgenerated aware if this creates an activity. set to not unload
 * when finished set to not perform direct edit when finished.
 */
public class UnspecifiedActivityTypeCreationToolEx extends
        UnspecifiedTypeCreationTool {
    private boolean isActivity;

    private boolean isArtifact;
    
    public UnspecifiedActivityTypeCreationToolEx(List elementTypes,
            boolean unloadWhenFinished) {
        super(elementTypes);
        super.setUnloadWhenFinished(unloadWhenFinished);
        for (Object object : elementTypes) {
            if (object instanceof IElementTypeEx) {
                isActivity = true;
                break;
            }
            if (object == BpmnElementTypes.Group_1004 ||
                    object == BpmnElementTypes.Group_2006 ||
                    object == BpmnElementTypes.TextAnnotation_1002 ||
                    object == BpmnElementTypes.TextAnnotation_2004 ||
                    object == BpmnElementTypes.DataObject_1003 ||
                    object == BpmnElementTypes.DataObject_2005) {
                isArtifact = true;
                break;
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gef.tools.CreationTool#updateTargetRequest()
     */
    @Override
    protected void updateTargetRequest() {
        if (!isActivity) {
            super.updateTargetRequest();
        } else {
            CreateRequest req = getCreateRequest();
            if (isInState(STATE_DRAG_IN_PROGRESS)) {
                Point loq = getStartLocation();
                Rectangle bounds = new Rectangle(loq, loq);
                Dimension delta = getDragMoveDelta();
                int size = Math.max(Math.abs(delta.width), Math
                        .abs(delta.height));
                bounds.union(loq.getTranslated(sgn(delta.width) * size,
                        sgn(delta.height) * size));
                req.setSize(bounds.getSize());
                req.setLocation(bounds.getLocation());
                req.getExtendedData().clear();
            } else {
                req.setSize(null);
                req.setLocation(getLocation());
            }
        }
    }
    
    /**
     * Recursively finds the parent that could contain shapes
     * @param part the part that we inspect
     * @return the compatible parent
     */
    private EditPart findParent(EditPart part) {
        if (part instanceof PoolPoolCompartmentEditPart || 
                part instanceof SubProcessSubProcessBodyCompartmentEditPart || 
                part instanceof BpmnDiagramEditPart) {
            return part;
        } else {
            if (part == null) {
                throw new IllegalArgumentException("Could not find parent");
            }
            return findParent(part.getParent());
        }
    }
    /**
     * Overriding to get the parent when the element type represents an artifact and it does not fit
     * completely in its current parent.
     */
    @Override
    protected boolean updateTargetUnderMouse() {
        boolean b = super.updateTargetUnderMouse();
        if (isArtifact && getTargetEditPart() instanceof IGraphicalEditPart 
                && getTargetRequest() != null) {
            Point loq = getStartLocation();
            Rectangle bounds = new Rectangle(loq, loq);
            bounds.union(loq.getTranslated(getDragMoveDelta()));
            Rectangle fig = ((IGraphicalEditPart) getTargetEditPart()).getFigure().getBounds().getCopy();
            ((IGraphicalEditPart) getTargetEditPart()).getFigure().translateToAbsolute(fig);
            if (!fig.contains(bounds)) {
                b = true;
                setTargetEditPart(findParent(getTargetEditPart().getParent()));
            }
        }
        return b;
    }

    private static int sgn(int i) {
        return i < 0 ? -1 : 1;
    }
    
    
    /**
     * Copied from {@link org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool}
     * and then commentted out the second part of the method that puts the
     * selected shape into direct edit mode.
     * 
     * Select the newly added shape view by default
     * @param viewer
     * @param objects
     */
    protected void selectAddedObject(EditPartViewer viewer, Collection objects) {
        final List editparts = new ArrayList();
        for (Iterator i = objects.iterator(); i.hasNext();) {
            Object object = i.next();
            if (object instanceof IAdaptable) {
                Object editPart =
                    viewer.getEditPartRegistry().get(
                        ((IAdaptable)object).getAdapter(View.class));
                if (editPart != null)
                    editparts.add(editPart);
            }
        }

    }

    
}