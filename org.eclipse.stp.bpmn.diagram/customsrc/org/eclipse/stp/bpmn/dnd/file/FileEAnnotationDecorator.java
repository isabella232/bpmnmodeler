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
package org.eclipse.stp.bpmn.dnd.file;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget.Direction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * The decorator for the file link decoration.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class FileEAnnotationDecorator implements IEAnnotationDecorator {

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator#getAssociatedAnnotationSource()
	 */
	public String getAssociatedAnnotationSource() {
		return FileDnDConstants.ANNOTATION_SOURCE;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator#getDirection(org.eclipse.gef.EditPart, org.eclipse.emf.ecore.EModelElement, org.eclipse.emf.ecore.EAnnotation)
	 */
	public Direction getDirection(EditPart part, EModelElement elt,
			EAnnotation ann) {
		return Direction.SOUTH_WEST;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator#getImage(org.eclipse.gef.EditPart, org.eclipse.emf.ecore.EModelElement, org.eclipse.emf.ecore.EAnnotation)
	 */
	public ImageDescriptor getImageDescriptor(EditPart part, EModelElement element,
			EAnnotation annotation) {
		if (element == null) {
			return PlatformUI.getWorkbench().
				getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
		}
		String filePath = (String) annotation.getDetails().
		    get(FileDnDConstants.PROJECT_RELATIVE_PATH);
		if (filePath == null) {
		    return null;      
        }
		IFile ourFile = WorkspaceSynchronizer.getFile(element.eResource()).
			getProject().getFile(filePath);
		IWorkbenchAdapter adapter = (IWorkbenchAdapter) 
		    Platform.getAdapterManager()
                    .getAdapter(ourFile, IWorkbenchAdapter.class);
		if (adapter == null) {
			return PlatformUI.getWorkbench().
				getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
		} else {
            return adapter.getImageDescriptor(ourFile);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator#getToolTip(org.eclipse.gef.EditPart, org.eclipse.emf.ecore.EModelElement, org.eclipse.emf.ecore.EAnnotation)
	 */
	public IFigure getToolTip(EditPart part, EModelElement element,
			EAnnotation annotation) {
		return new Label(annotation.getDetails().
				get(FileDnDConstants.PROJECT_RELATIVE_PATH).toString());
	}
    
    
}
