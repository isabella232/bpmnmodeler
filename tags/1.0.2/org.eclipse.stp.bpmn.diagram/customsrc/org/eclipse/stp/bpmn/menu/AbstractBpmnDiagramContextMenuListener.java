/*
 * Copyright (c) 2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *
 * Date           Author             Changes
 * Aug 12, 2008   Hugues Malphettes  Created
 */
package org.eclipse.stp.bpmn.menu;

import org.eclipse.gef.EditPart;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.stp.bpmn.Identifiable;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditor;

/**
 * Called back when a context menu for a bpmn diagram editor
 * is created. Declared as extension points.
 * <p>
 * This abstract implementation contains a couple of helper methods
 * </p>
 * 
 * @author hmalphettes
 * @author Intalio Inc
 */
public abstract class AbstractBpmnDiagramContextMenuListener implements IBpmnDiagramContextMenuListener {

    /**
     * @param menuManager The menu that is currently created.
     * @param currentEditor Gives access to the current selection as well as the entire
     * diagram currently displayed. For example to get access to the current selection:
     * currentEditor.getGraphicalViewer().getSelection()
     */
    public abstract void menuAboutToShow(IMenuManager menuManager,
            BpmnDiagramEditor currentEditor);
    
    /**
     * Returns the current selection.
     * @param editor
     * @return
     */
    protected ISelection getSelection(BpmnDiagramEditor editor) {
        return editor.getGraphicalViewer().getSelection();
    }
    
    /**
     * 
     * @param editor
     * @return
     */
    protected Identifiable getSelectedBpmnObject(BpmnDiagramEditor editor) {
        for (Object sel : editor.getGraphicalViewer().getSelectedEditParts()) {
            Identifiable r = (Identifiable)((EditPart)sel).getAdapter(Identifiable.class);
            if (r != null) {
                return r;
            }
        }
        return null;
    }
    
}
