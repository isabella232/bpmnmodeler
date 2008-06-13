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
 * Jan 10, 2007      Antoine Toulm&eacute;   Creation
 */
package org.eclipse.stp.bpmn.export;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.util.Log;
import org.eclipse.gmf.runtime.diagram.ui.image.ImageFileFormat;
import org.eclipse.gmf.runtime.diagram.ui.render.internal.DiagramUIRenderPlugin;
import org.eclipse.gmf.runtime.diagram.ui.render.internal.DiagramUIRenderStatusCodes;
import org.eclipse.gmf.runtime.diagram.ui.render.internal.l10n.DiagramUIRenderMessages;
import org.eclipse.gmf.runtime.diagram.ui.render.util.CopyToImageUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

/**
 * This wizard is based on the work of Anthony Hunter and cmahoney in CopyToImageAction.
 * 
 * Instead of using a dialog, we use a wizard to do the job.
 * 
 * Note that the wizard can only export BPMN diagrams.
 * 
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ImageExportWizard extends Wizard implements IExportWizard {

	protected IExportImagePage imagePage;

    private IStructuredSelection selection;
    
	public ImageExportWizard() {
	}

	/**
	 * Does the same thing as the CopyToImageAction#run() method.
	 * 
	 */
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	@Override
	public boolean performFinish() {
		List exporting = ((IExportImagePage) imagePage).getSelectedResources();
		if (exporting.isEmpty()) {
			((DialogPage) imagePage).setErrorMessage(BpmnDiagramMessages.ImageExportWizard_NoResourcesSelectedError);
			return false;
		}
		for (Object toExport : exporting) {
			if (toExport == null || !(toExport instanceof IFile)) {
				return false;
			}
			if (!((IFile) toExport).getFileExtension().equals("bpmn_diagram")) { //$NON-NLS-1$
				((DialogPage) imagePage).setErrorMessage(BpmnDiagramMessages.ImageExportWizard_InvalidExtensionError);
				return false;
			}
		}

		// if more than one diagram is selected.
		if (exporting.size() > 1) {
			((DialogPage) imagePage).setErrorMessage(BpmnDiagramMessages.ImageExportWizard_SelectOneDiagramError);
			return false;
		}
		// now getting serious and exporting for real

		Object selected = ((IExportImagePage) imagePage).getSelectedResources().get(0);
		
		IRunnableWithProgress runnable = createRunnable((IFile) selected,
				imagePage.getDestinationPath(),
				imagePage.getImageFormat());

		ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(
				Display.getCurrent().getActiveShell());
		try {
			progressMonitorDialog.run(false, true, runnable);
		} catch (InvocationTargetException e) {
			Log.warning(DiagramUIRenderPlugin.getInstance(),
					DiagramUIRenderStatusCodes.IGNORED_EXCEPTION_WARNING, e
					.getTargetException().getMessage(), e.getTargetException());

			if (e.getTargetException() instanceof OutOfMemoryError) {
				openErrorDialog(DiagramUIRenderMessages.CopyToImageAction_outOfMemoryMessage);
			} else if (e.getTargetException() instanceof SWTError) {
				/**
				 * SWT returns an out of handles error when processing large
				 * diagrams
				 */
				openErrorDialog(DiagramUIRenderMessages.CopyToImageAction_outOfMemoryMessage);
			} else {
				openErrorDialog(e.getTargetException().getMessage());
			}
			return true;
		} catch (InterruptedException e) {
			/* the user pressed cancel */
			Log.warning(DiagramUIRenderPlugin.getInstance(),
					DiagramUIRenderStatusCodes.IGNORED_EXCEPTION_WARNING, e
					.getMessage(), e);
		}


		return true;
	}

	/**
	 * display an error dialog
	 * 
	 * @param message
	 *            cause of the error
	 */
	private void openErrorDialog(String message) {
		MessageDialog
			.openError(
				Display.getCurrent().getActiveShell(),
				DiagramUIRenderMessages.CopyToImageAction_copyToImageErrorDialogTitle,
				NLS
					.bind(
					        DiagramUIRenderMessages.CopyToImageAction_copyToImageErrorDialogMessage,
						message));
	}
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(BpmnDiagramMessages.ImageExportWizard_title);
        this.selection = selection;
	}
    
    /**
     * Add our page, with the current selection.
     */
    @Override
    public void addPages() {
        super.addPages();
        imagePage = new ExportImagePage(selection);
        addPage(imagePage);
    }

	/**
	 * copy the selected shapes in the diagram to an image file.
	 * 
	 * @param diagramEditPart
	 *            the diagram editor
	 * @param list
	 *            list of selected shapes in the diagram
	 * @param destination
	 *            path to the new image file
	 * @param imageFormat
	 *            image format to create
	 * @return the runnable with a progress monitor
	 */
	private IRunnableWithProgress createRunnable(final IFile diagram, 
            final IPath destination, final ImageFileFormat format) {
		return new IRunnableWithProgress() {

			public void run(IProgressMonitor monitor) {
					ResourceSet resourceSet = new ResourceSetImpl();
					TransactionalEditingDomain.Factory.INSTANCE.createEditingDomain(resourceSet);
					Resource res = resourceSet.getResource(
							URI.createPlatformResourceURI(
									diagram.getFullPath().toString(),true), 
									true);
					Diagram tempD = null;

					for (Object resElt : res.getContents()) {
						if (resElt instanceof Diagram) {
							tempD= (Diagram) resElt;

						}
					}
					if (tempD == null) {
						return ;
					}
					final Diagram diagram = tempD;
					
					try {
						new CopyToImageUtil().copyToImage(diagram, destination, 
								format, new NullProgressMonitor(), 
								BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
					} catch (CoreException e) {
						BpmnDiagramEditorPlugin.getInstance().getLog().log(
								new Status(
										IStatus.ERROR,
										BpmnDiagramEditorPlugin.ID,
										IStatus.ERROR,e.getMessage(),e));
					}
			}
		};
	}

    public IStructuredSelection getSelection() {
        return selection;
    }
	
	
	
}
