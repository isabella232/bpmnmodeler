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
 * Mar 7, 2008	Antoine Toulme		Created
 */
package org.eclipse.stp.bpmn.sample.bugDnD.decorator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget.Direction;
import org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator;
import org.eclipse.stp.bpmn.sample.bugDnD.BugDnDPlugin;
import org.eclipse.stp.bpmn.sample.bugDnD.dnd.BugDnDHandler;
import org.eclipse.swt.graphics.Image;

/**
 * The bug annotation decorator.
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class BugAnnotationDecorator implements IEAnnotationDecorator {

    public String getAssociatedAnnotationSource() {
        return BugDnDHandler.BUG_ANNOTATION_SOURCE;
    }

    public Direction getDirection(EditPart part, EModelElement elt,
            EAnnotation ann) {
        return Direction.SOUTH_WEST;
    }

    public Image getImage(EditPart part,
            EModelElement element, EAnnotation annotation) {
        
        //leaky! don't do this at home!
        return BugDnDPlugin.imageDescriptorFromPlugin(BugDnDPlugin.PLUGIN_ID, 
                "icons/bug_decorator.gif").createImage();
    }

    public IFigure getToolTip(EditPart part, EModelElement element,
            EAnnotation annotation) {
        Label label = new Label();
        label.setText("#" + annotation.getDetails().get("number"));
        return label;
    }

}
