/******************************************************************************
 * Copyright (c) 2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Date			Author					Changes
 * Feb 22, 2008	Antoine Toulme		Created
 */
package org.eclipse.stp.bpmn.sample.annotationdecoration;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget.Direction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * The decorator for the annotation used in the other sample.
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class AnnotationDecorator implements IEAnnotationDecorator {

    /* (non-Javadoc)
     * @see org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator#getAssociatedAnnotationSource()
     */
    public String getAssociatedAnnotationSource() {
        return "textAnnotationSource";
    }

    /* (non-Javadoc)
     * @see org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator#getDirection(org.eclipse.gef.EditPart, org.eclipse.emf.ecore.EModelElement, org.eclipse.emf.ecore.EAnnotation)
     */
    public Direction getDirection(EditPart part, EModelElement elt,
            EAnnotation ann) {
        return Direction.WEST;
    }

    /* (non-Javadoc)
     * @see org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator#getImageDescriptor(org.eclipse.gef.EditPart, org.eclipse.emf.ecore.EModelElement, org.eclipse.emf.ecore.EAnnotation)
     */
    public ImageDescriptor getImageDescriptor(EditPart part,
            EModelElement element, EAnnotation annotation) {
        return PlatformUI.getWorkbench().getSharedImages().
            getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
    }

    /* (non-Javadoc)
     * @see org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator#getToolTip(org.eclipse.gef.EditPart, org.eclipse.emf.ecore.EModelElement, org.eclipse.emf.ecore.EAnnotation)
     */
    public IFigure getToolTip(EditPart part, EModelElement element,
            EAnnotation annotation) {
        if (annotation != null) {
            Label label = new Label();
            label.setText(annotation.getDetails().get("name"));
            return label;
        }
        return null;
    }

}
