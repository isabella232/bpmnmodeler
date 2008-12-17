/**
 * Copyright (C) 2000-2007, Intalio Inc.
 *
 * The program(s) herein may be used and/or copied only with the
 * written permission of Intalio Inc. or in accordance with the terms
 * and conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *
 * Dates       		 Author              Changes
 * Mar 16, 2007      Antoine Toulm&eacute;   Creation
 */
package org.eclipse.stp.bpmn.diagram.actions;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.dnd.file.FileDnDConstants;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * An action that deletes the link to the file associated with the shape
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class DeleteFileLinkAction extends AbstractDeleteAnnotationAction {

	public static final String ID = "deleteFileLinkAction"; //$NON-NLS-1$
	public DeleteFileLinkAction(IWorkbenchPage page) {
		super(page);
		setId(ID);
		setText(BpmnDiagramMessages.DeleteFileLinkAction_label);
	}

	public DeleteFileLinkAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText(BpmnDiagramMessages.DeleteFileLinkAction_label);
	}
	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.diagram.actions.AbstractDeleteAnnotationAction#getAnnotationSource()
	 */
	@Override
	protected String getAnnotationSource() {
		return FileDnDConstants.ANNOTATION_SOURCE;
	}
    /**
     * @return the list of the string representing the detail of the annotation
     * to remove or null to remove the entire annotation
     */
    protected List<String> getAnnotationDetails() {
        return Collections.singletonList(FileDnDConstants.PROJECT_RELATIVE_PATH);
    }

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.diagram.actions.AbstractDeleteAnnotationAction#updateImage(org.eclipse.emf.ecore.EModelElement)
	 */
	@Override
	protected ImageDescriptor updateImage(EModelElement elt) {
		if (elt == null) {
			return PlatformUI.getWorkbench().
				getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
		}
		EAnnotation annotation = elt.getEAnnotation(getAnnotationSource());
		if (annotation == null) {
			return PlatformUI.getWorkbench().
				getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
		}
		String filePath = (String) annotation.getDetails().
		get(FileDnDConstants.PROJECT_RELATIVE_PATH);
		
		IFile ourFile = WorkspaceSynchronizer.getFile(elt.eResource()).
			getProject().getFile(filePath);
		IWorkbenchAdapter adapter = (IWorkbenchAdapter) 
		Platform.getAdapterManager().
			getAdapter(ourFile, IWorkbenchAdapter.class);
		if (adapter == null) {
			return PlatformUI.getWorkbench().
				getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
		} else {
			return adapter.getImageDescriptor(ourFile);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.diagram.actions.AbstractDeleteAnnotationAction#updateText(org.eclipse.emf.ecore.EModelElement)
	 */
	@Override
	protected String updateText(EModelElement elt) {
		if (elt == null || elt.getEAnnotation(getAnnotationSource()) == null) {
			return BpmnDiagramMessages.DeleteFileLinkAction_label;
		} else {
			EAnnotation annotation = elt.getEAnnotation(getAnnotationSource());
			String path = annotation.getDetails().
				get(FileDnDConstants.PROJECT_RELATIVE_PATH).toString();
			return BpmnDiagramMessages.bind(BpmnDiagramMessages.DeleteFileLinkAction_label_path, path);
		}
		
	}

	@Override
	protected String getDefaultLabel() {
		return BpmnDiagramMessages.DeleteFileLinkAction_label;
	}
}
