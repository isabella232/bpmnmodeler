/**
 * Copyright (C) 2000-2007, Intalio Inc.
 *
 * The program(s) herein may be used and/or copied only with the
 * written permission of Intalio Inc. or in accordance with the terms
 * and conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *
 * Dates       		 Author              Changes
 * Feb 20, 2007      Antoine Toulmé   Creation
 */
package org.eclipse.stp.bpmn.diagram.actions;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.ui.action.AbstractActionHandler;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

/**
 * Hides the decorations by setting a value in the preference store.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class FilterDecorationsAction extends AbstractActionHandler {
	
	public static final String ID = "FilterDecorationsAction"; //$NON-NLS-1$
	
	
	public FilterDecorationsAction(IWorkbenchPart workbenchPart) {
		super(workbenchPart);
	}
	
	public FilterDecorationsAction(IWorkbenchPage workbenchPage) {
		super(workbenchPage);
	}
	/**
	 * Inits the action with its id, description, tooltip and image.
	 */
	@Override
	public void init() {
		
		setId(ID);
		super.init();
		setEnabled(true);
        
        boolean b = BpmnDiagramEditorPlugin.getInstance().getPreferenceStore().
        getBoolean(BpmnDiagramPreferenceInitializer.FILTER_DECORATIONS);
        
        
        if (b) {
            String text = BpmnDiagramMessages.FilterDecorationsAction_label; 
            setDescription(text);
            setToolTipText(text);
            setImageDescriptor(BpmnDiagramEditorPlugin.
                    getBundledImageDescriptor("icons/obj16/showDecorations.gif")); //$NON-NLS-1$
        } else {
            String text = BpmnDiagramMessages.FilterDecorationsAction_hide_label; 
            setDescription(text);
            setToolTipText(text);
            setImageDescriptor(BpmnDiagramEditorPlugin.
                    getBundledImageDescriptor("icons/obj16/hideDecorations.gif")); //$NON-NLS-1$
        }
	}
	/**
	 * Runs the operation, basically invert the value of isDefault 
	 * property on the sequence edge.
	 */
	@Override
	protected void doRun(IProgressMonitor progressMonitor) {
		boolean b = BpmnDiagramEditorPlugin.getInstance().getPreferenceStore().
		getBoolean(BpmnDiagramPreferenceInitializer.FILTER_DECORATIONS);
		BpmnDiagramEditorPlugin.getInstance().getPreferenceStore().setValue(
		        BpmnDiagramPreferenceInitializer.FILTER_DECORATIONS, !b);
		refresh();
        
		IWorkbenchPage page = PlatformUI.getWorkbench().
		    getActiveWorkbenchWindow().getActivePage();
		if (page != null) {
		    IEditorPart part = page.getActiveEditor();
		    EditPart ep = (EditPart) part.getAdapter(EditPart.class);
		    ep.refresh();
		    recursiveRefresh(ep);
		}
	}

	/**
	 * recursively refreshes the edit parts, so that
	 * decorations are hidden or shown.
	 * @param part
	 */
	private void recursiveRefresh(EditPart part) {
		for (Object child : part.getChildren()) {
			((EditPart) child).refresh();
			recursiveRefresh((EditPart) child);
		}
	}
	/**
	 * Refreshes the action according to the value hold
	 * in the preference store
	 */
	public void refresh() {
		boolean b = BpmnDiagramEditorPlugin.getInstance().getPreferenceStore().
		getBoolean(BpmnDiagramPreferenceInitializer.FILTER_DECORATIONS);
		if (b) {
            String text = BpmnDiagramMessages.FilterDecorationsAction_label; 
			setDescription(text);
			setToolTipText(text);
			setImageDescriptor(BpmnDiagramEditorPlugin.
					getBundledImageDescriptor("icons/obj16/showDecorations.gif")); //$NON-NLS-1$
		} else {
            String text = BpmnDiagramMessages.FilterDecorationsAction_hide_label; 
            setDescription(text);
            setToolTipText(text);
			setImageDescriptor(BpmnDiagramEditorPlugin.
					getBundledImageDescriptor("icons/obj16/hideDecorations.gif")); //$NON-NLS-1$
		}
		setEnabled(true);
	} 
}
