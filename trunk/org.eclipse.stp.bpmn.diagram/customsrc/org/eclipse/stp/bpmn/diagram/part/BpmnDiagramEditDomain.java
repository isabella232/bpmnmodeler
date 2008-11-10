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

package org.eclipse.stp.bpmn.diagram.part;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.gef.Tool;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditDomain;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.stp.bpmn.tools.SelectionToolEx;

/**
 * Specialization of the diagram edit domain for bpmn diagrams.
 * <p>
 * There was a null pointer that started to appear in the default edit domain.
 * Could not find a better way to fix it than this.
 * Also this forces the default tool to be our very own SelectionToolEx
 * </p>
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BpmnDiagramEditDomain extends DiagramEditDomain {

    private Tool _defaultTool;
    private IEditPartSelectionFilter _actionSelectionFilter;
    
    /**
     * List of element types to be removed permanently
     * from the popupbar editpolicy and the prompt edit policy.
     */
    private Set<IElementType> removedElementTypes = 
    	new HashSet<IElementType>();
    
    public BpmnDiagramEditDomain(DiagramEditor editor) {
        super(editor);
    }
        
    /**
     * Not sure why but keep getting a null pointer when using the connection
     * tool if it is not started
     */
    public void loadDefaultTool() {
        setActiveTool(null);
        if (super.getPaletteViewer() != null) {
            super.getPaletteViewer().setActiveTool(
                    super.getPaletteViewer().getPaletteRoot().getDefaultEntry());
        } else {
            setActiveTool(getDefaultTool());
        }
    }

    @Override
    public Tool getDefaultTool() {
        if (_defaultTool == null) {
            _defaultTool = new SelectionToolEx();
        }
        return _defaultTool;
    }

    @Override
    public void setDefaultTool(Tool tool) {
        _defaultTool = tool;
        super.setDefaultTool(tool);
    }

    /**
     * 
     * @return the element types that are disabled in the domain.
     */
	public Set<IElementType> getRemovedElementTypes() {
		return removedElementTypes;
	}

	/**
	 * Sets the element types that should not enabled for the given domain.
	 * @param removedElementTypes
	 */
	public void setRemovedElementTypes(Set<IElementType> removedElementTypes) {
		this.removedElementTypes = removedElementTypes;
	}
	
    /**
     * Object in charge of modifying the selection before an action is applied.
     */
    public interface IEditPartSelectionFilter {
        /**
         * @param actionId The id of the action for which the selection is modified
         * @param selection The structured selection.
         * @return The selection to apply the action on.
         */
        public ISelection filterSelection(String actionId, IStructuredSelection selection);
    }
    
    /**
     * Currently only used to customize the GroupAction.
     * 
     * @return The object in charge of modifying the selection on which an action
     * is applied. or null if no such thing
     */
    public IEditPartSelectionFilter getActionSelectionFilter() {
        return _actionSelectionFilter;
    }
	/**
	 * @param actionSelectionFilter The object in charge of modifying the selection on which an action
     * is applied.
	 */
    public void setActionSelectionFilter(IEditPartSelectionFilter actionSelectionFilter) {
        _actionSelectionFilter = actionSelectionFilter;
    }
    
}