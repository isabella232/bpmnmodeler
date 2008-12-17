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
 * Date          	Author             Changes 
 * 03 Oct 2006   	MPeleshchyshyn         Created 
 **/
package org.eclipse.stp.bpmn.commands;

import java.beans.PropertyChangeEvent;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.tools.TaskDragHelper;

/**
 * Extends functionality of <code>SetBoundsCommand</code>. Calls
 * <code>propertyChange</code> method on parent edit part prior to command
 * execution and in case if parent edit part is an instance of
 * <code>CompartmentEditPart</code>.
 * 
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 * @author MPeleshchyshyn
 * @see SetBoundsCommand
 * @see CompartmentEditPart
 * @deprecated this was used in a previous attempt
 * to manage the resize
 */
public class SetBoundsCommandEx extends SetBoundsCommand {
    /**
     * Stores reeference to edit part
     */
    private GraphicalEditPart editPart;

    /**
     * Bounds to be setby this command
     */
    private Rectangle bounds;

    /**
     * Creates new instance of the command
     * 
     * 
     * @param editingDomain
     *            the editing domain through which model changes are made
     * @param label
     *            The command label
     * @param editPart
     *            edit part, wchich figure bounds should be changes
     * @param bounds
     *            The new bounds
     */
    public SetBoundsCommandEx(TransactionalEditingDomain editingDomain,
            String label, GraphicalEditPart editPart, Rectangle bounds) {
        super(editingDomain, label, editPart, bounds);
        this.editPart = editPart;
        this.bounds = bounds;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor,
     *      org.eclipse.core.runtime.IAdaptable)
     */
    @Override
    protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
            IAdaptable info) throws ExecutionException {
        EditPart parent = editPart.getParent();

        if (parent instanceof ShapeCompartmentEditPart) {
            ((ShapeCompartmentEditPart) parent)
                    .propertyChange(new PropertyChangeEvent(editPart,
                            TaskDragHelper.PROPERTY_CHILD_RESIZED, null, bounds
                                    .getCopy()));
        }
        if (bounds.x < 0) {
            bounds.x = 0;
        }
        if (bounds.y < 0) {
            bounds.y = 0;
        }

        if (editPart == null)
            return CommandResult
                    .newErrorCommandResult("SetBoundsCommand: viewAdapter does not adapt to IView.class"); //$NON-NLS-1$

        View view = (View) editPart.getAdapter(View.class);

        Point location = bounds.getLocation();
        if (location != null) {
            ViewUtil.setStructuralFeatureValue(view, NotationPackage.eINSTANCE
                    .getLocation_X(), new Integer(location.x));
            ViewUtil.setStructuralFeatureValue(view, NotationPackage.eINSTANCE
                    .getLocation_Y(), new Integer(location.y));
        }
        Dimension size = bounds.getSize();
        if (size != null) {
            ViewUtil.setStructuralFeatureValue(view, NotationPackage.eINSTANCE
                    .getSize_Width(), new Integer(size.width));
            ViewUtil.setStructuralFeatureValue(view, NotationPackage.eINSTANCE
                    .getSize_Height(), new Integer(size.height));
        }
        return CommandResult.newOKCommandResult();
    }
}
