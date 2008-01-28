/*
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 */
package org.eclipse.stp.bpmn.tools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.internal.tools.RubberbandDragTracker;
import org.eclipse.gmf.runtime.diagram.ui.internal.tools.RubberbandSelectionTool;
import org.eclipse.gmf.runtime.diagram.ui.util.SelectInDiagramHelper;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;

/**
 * The goal of this class is to customize the computation of the selected editparts.
 * The limitation with the normal tool is that it won't select a sub-process
 * if the border compartment is not selected. However users are
 * expecting that lassoing around the compartment alone of the sub-process will
 * select the sub-process.
 * <p>
 * The solution used here consists of overriding the method calculateNewSelection().
 * Unfortunately it is private. So we get to override a couple more methods on
 * the way and do some reflection do make some read-only private methods accessible.
 * </p>
 * <p>
 * In the future if the method calculateNewSelection and the methods it uses
 * were protected we could avoid all the ugly refection hacks.
 * </p>
 * @author hmalphettes
 * @author Intalio Inc
 */
public class RubberbandDragTrackerForBPMN extends RubberbandDragTracker {

    private static Field selectedEditPartsField;
    private static Field modeField;
    private static Method eraseTargetFeedbackMethod;
    private static Method showMarqueeFeedbackMethod;
    private static Method showTargetFeedbackMethod;
    private static Method getAllChildrenMethod;
    private static Method getMarqueeBoundsMethod;
    private static Method isFigureVisibleNotPrivateMethod;
    
    private static final Object[] empty = new Object[0];
    
    private static final void init() {
        if (modeField != null) {
            return;
        }
        Class[] empty = new Class[0];
        try {
            modeField = RubberbandSelectionTool.class.getDeclaredField("mode"); //$NON-NLS-1$
            modeField.setAccessible(true);
            selectedEditPartsField = RubberbandSelectionTool.class.getDeclaredField("selectedEditParts"); //$NON-NLS-1$
            selectedEditPartsField.setAccessible(true);
            eraseTargetFeedbackMethod = RubberbandSelectionTool.class.getDeclaredMethod("eraseTargetFeedback", empty); //$NON-NLS-1$
            eraseTargetFeedbackMethod.setAccessible(true);
            getAllChildrenMethod = RubberbandSelectionTool.class.getDeclaredMethod("getAllChildren", empty); //$NON-NLS-1$
            getAllChildrenMethod.setAccessible(true);
            getMarqueeBoundsMethod = RubberbandSelectionTool.class.getDeclaredMethod("getMarqueeBounds", empty); //$NON-NLS-1$
            getMarqueeBoundsMethod.setAccessible(true);
            showMarqueeFeedbackMethod = RubberbandSelectionTool.class.getDeclaredMethod("showMarqueeFeedback", empty); //$NON-NLS-1$
            showMarqueeFeedbackMethod.setAccessible(true);
            showTargetFeedbackMethod = RubberbandSelectionTool.class.getDeclaredMethod("showTargetFeedback", empty); //$NON-NLS-1$
            showTargetFeedbackMethod.setAccessible(true);
            Class[] param = new Class[1];
            param[0] = IFigure.class;
            isFigureVisibleNotPrivateMethod = RubberbandSelectionTool.class.getDeclaredMethod("isFigureVisible", param); //$NON-NLS-1$
            isFigureVisibleNotPrivateMethod.setAccessible(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    
    public RubberbandDragTrackerForBPMN() {
        init();
    }
    
    
    /**
     * Overridden to be able to override the calls to calculateNewSelection().
     * @see org.eclipse.gef.tools.AbstractTool#handleDragInProgress()
     */
    @Override
    protected boolean handleDragInProgress() {
        if (isInState(STATE_DRAG | STATE_DRAG_IN_PROGRESS)) {
            showMarqueeFeedbackNotPrivate();
            eraseTargetFeedbackNotPrivate();      
            setSelectedEditParts(calculateNewSelectionNotPrivate());
            showTargetFeedbackNotPrivate();
            SelectInDiagramHelper.exposeLocation((FigureCanvas)getCurrentViewer().getControl(),getLocation());
        }
        return true;
    }
    
    private static final Request MARQUEE_REQUEST_NOT_PRIVATE =
        new Request(RequestConstants.REQ_SELECTION); 
    private static final int TOGGLE_MODE_NOT_PRIVATE = 1;
    private static final int APPEND_MODE_NOT_PRIVATE = 2;
    
    protected void showMarqueeFeedbackNotPrivate() {
        try {
            showMarqueeFeedbackMethod.invoke(this, empty);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    protected void eraseTargetFeedbackNotPrivate() {
        try {
            eraseTargetFeedbackMethod.invoke(this, empty);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }        
    }
    protected void showTargetFeedbackNotPrivate() {
        try {
            showTargetFeedbackMethod.invoke(this, empty);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    protected void setSelectedEditParts(List selectedEPs) {
        try {
            selectedEditPartsField.set(this, selectedEPs);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    protected List calculateNewSelectionNotPrivate() {
        List newSelections = new ArrayList();
        Iterator children = getAllChildrenNotPrivate().iterator();

        // Calculate new selections based on which children fall
        // inside the marquee selection rectangle.  Do not select
        // children who are not visible
        while (children.hasNext()){ 
            EditPart child = (EditPart) children.next();
            if (!child.isSelectable()) {
                continue;
            }
            IFigure figure = getFigureToUseToCalculateSelection((GraphicalEditPart)child);
            if (figure == null) {
                continue;
            }
            Rectangle r = getRectangleToUseToCalculateSelection((GraphicalEditPart)child, figure);
            figure.translateToAbsolute(r);

            Rectangle marqueeBounds = getMarqueeBoundsNotPrivate();   
            getMarqueeFeedbackFigure().translateToRelative(r);
            if (marqueeBounds.contains(r.getTopLeft())
              && marqueeBounds.contains(r.getBottomRight())       
              && child.getTargetEditPart(MARQUEE_REQUEST_NOT_PRIVATE) == child
              && isFigureVisibleNotPrivate(figure)){
                newSelections.add(child);
            }
        }
        return newSelections;
    }
    
    /**
     * @param graphicalEditPart
     * @return The figure that should be used to calculate if it should be enclosed
     * in the new selection of the tool.
     */
    protected IFigure getFigureToUseToCalculateSelection(GraphicalEditPart graphicalEditPart) {
        return graphicalEditPart.getFigure();
    }
    /**
     * @param graphicalEditPart
     * @return The rectangle that should be used to calculate if it should be enclosed
     * in the new selection of the tool.
     */
    protected Rectangle getRectangleToUseToCalculateSelection(GraphicalEditPart graphicalEditPart, IFigure epFigure) {
        Rectangle res = epFigure.getBounds().getCopy();
        if (graphicalEditPart instanceof SubProcessEditPart) {
            res.height -= 35;//remove the height of the sub-process border
            //actually half the height would 25 but a little experiment shows that 35 works better.
        }
        return res;
    }
    
    protected boolean isFigureVisibleNotPrivate(IFigure figure) {
        Object[] param = new Object[1];
        param[0] = figure;
        try {
            return (Boolean) isFigureVisibleNotPrivateMethod.invoke(this, param);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return true;
    }
    
    protected Rectangle getMarqueeBoundsNotPrivate() {
        try {
            return (Rectangle)getMarqueeBoundsMethod.invoke(this, empty);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    protected int getSelectionModeNotPrivate() {
        try {
            return modeField.getInt(this);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return TOGGLE_MODE_NOT_PRIVATE;
    }
    
    protected HashSet getAllChildrenNotPrivate() {
        try {
            return (HashSet) getAllChildrenMethod.invoke(this, empty);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    protected void performMarqueeSelect() {
        EditPartViewer viewer = getCurrentViewer();
        List newSelections = calculateNewSelectionNotPrivate();

        // If in multi select mode, add the new selections to the already
        // selected group; otherwise, clear the selection and select the new group
        if (getSelectionModeNotPrivate() == APPEND_MODE_NOT_PRIVATE) {
            for (int i = 0; i < newSelections.size(); i++) {
                EditPart editPart = (EditPart)newSelections.get(i); 
                viewer.appendSelection(editPart); 
            } 
        } else if (getSelectionModeNotPrivate() == TOGGLE_MODE_NOT_PRIVATE) {
            List selected = new ArrayList(viewer.getSelectedEditParts());
            for (int i = 0; i < newSelections.size(); i++) {
                EditPart editPart = (EditPart)newSelections.get(i); 
                if (editPart.getSelected() != EditPart.SELECTED_NONE)
                    selected.remove(editPart);
                else
                    selected.add(editPart);
            }
            viewer.setSelection(new StructuredSelection(selected));
        } else {
            viewer.setSelection(new StructuredSelection(newSelections));
        }
    }
    
    
}
