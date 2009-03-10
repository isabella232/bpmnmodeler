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
 * Date         Author             Changes
 * Aug 21, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.dnd;

import java.util.Collection;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.swt.graphics.Image;

/**
* Interface used in the extension point associated with a key (String)
* designed to give an image or more and a tooltip given an annotation.
* 
* Note that the images that are given by this biais will not be disposed.
* It is the reponsability of the extension points coders to dispose the images.
* 
* This 
* @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
* @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
*/
public interface IEAnnotationDecorator2 {

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
    public Collection<IEAnnotationDecoratorData> getDecorators(EditPart part, 
            EModelElement element,
            EAnnotation annotation);
    
    /**
     * An object containing an image, a tooltip figure 
     * and a direction for positioning the image.
     *
     * @author <a href="http://www.intalio.com">Intalio Inc.</a>
     * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
     */
    public interface IEAnnotationDecoratorData {
        
        /**
         * @return the image
         */
        public Image getImage();
        
        /**
         * @return the tooltip.
         */
        public IFigure getToolTip();
        
        /**
         * @return the direction, ie the emplacement of the annotation decoration.
         * must be one of the PositionConstants values.
         */
        public IDecoratorTarget.Direction getDirection();
    }
}
