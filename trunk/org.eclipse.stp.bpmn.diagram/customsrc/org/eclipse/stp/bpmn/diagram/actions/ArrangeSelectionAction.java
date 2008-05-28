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
 * Date                 Author              Changes 
 * 29 Nov 2006      MPeleshchyshyn      Created 
 **/
package org.eclipse.stp.bpmn.diagram.actions;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.Animation;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.tools.ToolUtilities;
import org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.internal.editparts.IEditableEditPart;
import org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Action handler class that implements "Arrange Selection" functionality.
 * 
 * @author MPeleshchyshyn
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ArrangeSelectionAction extends DiagramAction {
    private static final String ACTION_TEXT = BpmnDiagramMessages.ArrangeSelectionAction_label;

    private static final String TOOLTIP_TEXT = BpmnDiagramMessages.ArrangeSelectionAction_tooltip;

    private static final String ICON_PATH = "icons/arrangeselected.gif"; //$NON-NLS-1$

    public static final String ACTION_ARRANGE_SELECTION = "arrangeSelectionAction2"; //$NON-NLS-1$
    public static final String ACTION_TOOLBAR_ARRANGE_SELECTION = "toolbarArrangeSelectionAction2"; //$NON-NLS-1$
    
    protected ArrangeSelectionAction(IWorkbenchPage workbenchPage) {
        super(workbenchPage);
        setId(ACTION_ARRANGE_SELECTION);
    }

    protected ArrangeSelectionAction(IWorkbenchPart workbenchPart) {
        super(workbenchPart);
        setId(ACTION_ARRANGE_SELECTION);
    }

    private static ArrangeSelectionAction createActionWithoutId(
            IWorkbenchPage workbenchPage) {
        ArrangeSelectionAction action = new ArrangeSelectionAction(
                workbenchPage);
        action.setId(ACTION_TOOLBAR_ARRANGE_SELECTION);
        action.setText(ACTION_TEXT);
        action.setToolTipText(TOOLTIP_TEXT);

        action.setImageDescriptor(BpmnDiagramEditorPlugin
                .getBundledImageDescriptor(ICON_PATH));
        // action
        // .setDisabledImageDescriptor(DiagramUIActionsPluginImages.DESC_ARRANGE_SELECTED_DISABLED);
        // action
        // .setHoverImageDescriptor(DiagramUIActionsPluginImages.DESC_ARRANGE_SELECTED);
        return action;
    }

    /**
     * Creates the Arrange Selection action for the window and popup menus
     * 
     * @param workbenchPage
     */
    public static ArrangeSelectionAction createArrangeSelectionAction(
            IWorkbenchPage workbenchPage) {
        ArrangeSelectionAction action = createActionWithoutId(workbenchPage);
        action.setId(ACTION_ARRANGE_SELECTION);

        return action;
    }

    /**
     * Creates the Arrange Selection action for the toolbar menu
     * 
     * @param workbenchPage
     */
    public static ArrangeSelectionAction createToolbarArrangeSelectionAction(
            IWorkbenchPage workbenchPage) {
        ArrangeSelectionAction action = createActionWithoutId(workbenchPage);
        action.setId(ACTION_TOOLBAR_ARRANGE_SELECTION);

        return action;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#createTargetRequest()
     */
    protected Request createTargetRequest() {
        return new ArrangeRequest(getId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#updateTargetRequest()
     */
    protected void updateTargetRequest() {
        ArrangeRequest request = (ArrangeRequest) getTargetRequest();
        request.setPartsToArrange(getOperationSet());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#getCommand()
     */
    protected Command getCommand() {
        if (getOperationSet().size() >= 2) {
            EditPart parent = getSelectionParent(getOperationSet());
            if (parent != null)
                return parent.getCommand(getTargetRequest());
        }
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * Action is enabled if arrange all. If arrange selection, action is enabled
     * if the operation set's parent has XYLayout and there is atleast 2
     * siblings to arrange
     * 
     * @see org.eclipse.gef.ui.actions.EditorPartAction#calculateEnabled()
     */
    protected boolean calculateEnabled() {

        List operationSet = getOperationSet();

        EditPart parentEP = getSelectionParent(operationSet);

        // bugzilla 156733: disable this action if the parent or selected edit
        // parts are not editable
        if ((parentEP instanceof IEditableEditPart)
                && !((IEditableEditPart) parentEP).isEditModeEnabled()) {
            return false;
        }

        for (Iterator i = operationSet.iterator(); i.hasNext();) {
            Object next = i.next();
            if ((next instanceof IEditableEditPart)
                    && !((IEditableEditPart) next).isEditModeEnabled()) {
                return false;
            }
        }

        // arrange selection
        if (operationSet.size() >= 2) {
            if (parentEP instanceof GraphicalEditPart) {
                GraphicalEditPart parent = (GraphicalEditPart) parentEP;
                if ((parent != null)
                        && (parent.getContentPane().getLayoutManager() instanceof XYLayout))
                    return true;
            }
        }
        return false;
    }

    /*
     * The operation set is the shapes, connections or both on the diagrm edit
     * part (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#createOperationSet()
     */
    protected List createOperationSet() {
        List selection = getSelectedObjects();

        if (selection.isEmpty()
                || !(selection.get(0) instanceof IGraphicalEditPart))
            return Collections.EMPTY_LIST;

        selection = ToolUtilities.getSelectionWithoutDependants(selection);
        return createOperationSet(selection);
    }

    /**
     * getSelectionParent Utility to return the logical parent of the selection
     * list
     * 
     * @param editparts
     *            List to parse for a common parent.
     * @return EditPart that is the parent or null if a common parent doesn't
     *         exist.
     */
    private EditPart getSelectionParent(List editparts) {
        ListIterator li = editparts.listIterator();
        while (li.hasNext()) {
            Object obj = li.next();
            if (!(obj instanceof ConnectionEditPart) && obj instanceof EditPart) {
                return ((EditPart) obj).getParent();
            }
        }

        return null;
    }

    private List createOperationSet(List editparts) {
        if (editparts == null || editparts.isEmpty())
            return Collections.EMPTY_LIST;
        if (editparts.size() == 1
                && editparts.get(0) instanceof SubProcessEditPart) {
            editparts = ((SubProcessEditPart) editparts.get(0))
                    .getChildBySemanticHint(
                            Integer.toString(SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID))
                    .getChildren();
            if (editparts.isEmpty()) {
                return Collections.EMPTY_LIST;
            }
        }
        EditPart parent = getSelectionParent(editparts);
        if (parent == null)
            return Collections.EMPTY_LIST;

        for (int i = 1; i < editparts.size(); i++) {
            EditPart part = (EditPart) editparts.get(i);
            if (part instanceof ConnectionEditPart) {
                continue;
            }
            if (part.getParent() != parent)
                return Collections.EMPTY_LIST;
        }
        return editparts;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.common.ui.action.AbstractActionHandler#isSelectionListener()
     */
    protected boolean isSelectionListener() {
        return true;
    }

    protected void doRun(IProgressMonitor progressMonitor) {
        IPreferenceStore preferenceStore = (IPreferenceStore) getDiagramEditPart()
                .getDiagramPreferencesHint().getPreferenceStore();
        boolean animatedLayout = preferenceStore
                .getBoolean(IPreferenceConstants.PREF_ENABLE_ANIMATED_LAYOUT);

        if (animatedLayout)
            Animation.markBegin();

        super.doRun(progressMonitor);

        if (animatedLayout) {
            int durationInc = 800;
            int factor = 10;
            int size = 0;

            List operationSet = getOperationSet();
            if (operationSet != null && !operationSet.isEmpty()) {
                IGraphicalEditPart container = (IGraphicalEditPart) getSelectionParent(operationSet);
                size += container.getFigure().getChildren().size();
            }

            int totalDuration = Math.min(durationInc * factor / 2, Math.max(
                    durationInc, (size / factor) * durationInc));

            Animation.run(totalDuration);
        }
    }
}
