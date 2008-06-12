/******************************************************************************
 * Copyright (c) 2006, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Dates       		 Author              Changes
 * Dec 20, 2006      Antoine Toulm&eacute;   Creation
 */
package org.eclipse.stp.bpmn.policies;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.OpenEditPolicy;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.dnd.file.FileDnDConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

/**
 * This edit policy opens a file when there is one attached
 * to the edit part through an annotation.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class OpenFileEditPolicy extends OpenEditPolicy {

	@Override
	protected Command getOpenCommand(Request request) {
		if (request instanceof SelectionRequest) {
			return null;
		}
		EditPart part = getTargetEditPart(request);
		if (part == null) {
			return null;
		}
		IWorkbenchWindow  window = PlatformUI.getWorkbench().
			getActiveWorkbenchWindow();
		if (window == null) {
			return null;
		}
		final IWorkbenchPage page = window.getActivePage();
		if (page == null) {
			return null;
		}
		if (part instanceof IGraphicalEditPart) {
			EObject object =((IGraphicalEditPart) part).getNotationView().
				getElement();
			if (object instanceof EModelElement) {
				EAnnotation ann = ((EModelElement) object).
					getEAnnotation(FileDnDConstants.ANNOTATION_SOURCE);
				if (ann == null) {
					return null;
				}
				String filePath = (String) ann.getDetails().get(
							FileDnDConstants.PROJECT_RELATIVE_PATH);
				String line = (String) ann.getDetails().get(
						FileDnDConstants.LINE_NUMBER);
				
				final IFile ourFile = WorkspaceSynchronizer.getFile(
						object.eResource()).getProject().getFile(filePath);
				IMarker marker = null;
				if (line != null) {
					try {
						marker = ourFile.createMarker(IMarker.MARKER);
						marker.setAttribute(IMarker.TRANSIENT, true);
						marker.setAttribute(IMarker.LINE_NUMBER, 
								Integer.valueOf(line).intValue());
					} catch (CoreException e1) {
						// kill the exception and just open the file
					} catch (NumberFormatException e2) {
						// kill the exception and just open the file too.
					}
				}
				if (marker == null) {
					Command co = new Command(BpmnDiagramMessages.OpenFileEditPolicy_command_name) {
						
						@Override
						public void execute() {

						try {
							IDE.openEditor(page,ourFile);
						} catch (PartInitException e) {
							BpmnDiagramEditorPlugin.getInstance().getLog().log(
									new Status(
										IStatus.ERROR,
										BpmnDiagramEditorPlugin.ID,
										IStatus.ERROR, 
										e.getMessage(), 
										e));
						}
					}};
					return co;
				} else {
					final IMarker finalMarker = marker;
					Command co = 
						new Command(BpmnDiagramMessages.bind(BpmnDiagramMessages.OpenFileEditPolicy_command_name_with_line, line)) { 
						
						@Override
						public void execute() {

						try {
							IDE.openEditor(page, finalMarker);
						} catch (PartInitException e) {
							BpmnDiagramEditorPlugin.getInstance().getLog().log(
									new Status(
											IStatus.ERROR, 
											BpmnDiagramEditorPlugin.ID,
											IStatus.ERROR, 
											e.getMessage(), 
											e));
						}
					}};
					return co;
				}
			}
		}
		return null;
	}

}
