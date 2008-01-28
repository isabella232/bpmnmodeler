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
package org.eclipse.stp.bpmn.dnd;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * Interface used in the extension point associated with a key (String)
 * designed to give an image and a tooltip given an annotation.
 * 
 * Note that the images that are given by this biais will not be disposed.
 * It is the reponsability of the extension points coders to dispose the images.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public interface IEAnnotationDecorator {

	/**
	 * @return the source of the annotation this decorator is associated with.
	 */
	public String getAssociatedAnnotationSource();
	/**
	 * @param part the edit part associated with the model element. May be null.
	 * @param element the element, may be null
	 * @param annotation the annotation causing the decoration.
	 *
	 * @return an image descriptor according to the element 
	 * and the annotation associated with it.
	 */
	public ImageDescriptor getImageDescriptor(EditPart part, EModelElement element,
			EAnnotation annotation);
	
	/**
	 * @param part the edit part associated with the model element.
	 * @param element the element
	 * @param annotation the annotation causing the decoration.
	 * @return a figure representing a tooltip, that will be shown when hovering
	 * the image.
	 */
	public IFigure getToolTip(EditPart part, EModelElement element,
			EAnnotation annotation);
	/**
	 * 
	 * @param part the edit part associated with the model element.
	 * @param element the element
	 * @param annotation the annotation causing the decoration.
	 * @return the direction, ie the emplacement of the annotation decoration.
	 * must be one of the PositionConstants values.
	 */
	public IDecoratorTarget.Direction getDirection(EditPart part, EModelElement elt,
			EAnnotation ann);
}
