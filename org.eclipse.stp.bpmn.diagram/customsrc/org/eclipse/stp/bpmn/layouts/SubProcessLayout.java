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

package org.eclipse.stp.bpmn.layouts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart.SubProcessFigure;
import org.eclipse.stp.bpmn.figures.SubProcessBodyFigure;
import org.eclipse.stp.bpmn.figures.SubProcessBorderFigure;

/**
 * Support defining mimum and maximum size of a figure.
 * 
 * @author hmalphettes
 * @author atoulme
 * Added support for compartment resize and positioning.
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class SubProcessLayout implements LayoutManager {

    public static final int NAME = 0;
    public static final int BODY =1;
    public static final int BORDER = 2;
    
    private IFigure name;
    
    private SubProcessBodyFigure body;
    
    private SubProcessBorderFigure border;
    
    public Object getConstraint(IFigure figure) {
        return null; // not implemented
    }

    public Dimension getMinimumSize(IFigure container, int wHint, int hHint) {
        return getPreferredSize(container, wHint, hHint);
    }

    public Dimension getPreferredSize(IFigure container, int wHint, int hHint) {
       return container.getMinimumSize();
    }

    public void invalidate() {
        
    }

    public void layout(IFigure container) {
        if (((SubProcessFigure) container).isCollapsed()) {
            Dimension minSize = container.getMinimumSize();
            Dimension borderPrefSize = border.getMinimumSize();
            Dimension namePrefSize = name.getPreferredSize();
            Rectangle area = container.getClientArea();
            name.setLocation(area.getLocation().getCopy());
            name.setSize(minSize.width, namePrefSize.height);
            body.setLocation(new Point(area.x, area.y + namePrefSize.height));
            body.setSize(0, 0);
            border.setLocation(new Point(area.x, area.y + minSize.height - borderPrefSize.height));
            border.setSize(minSize.width, borderPrefSize.height);
            container.setSize(minSize);
            container.getParent().setSize(container.getSize());
            return;
        }
        Rectangle area = container.getClientArea();
        // introduce the initial dimension
        Dimension selfSize = area.getSize().getCopy();
        Dimension borderPrefSize = border.getMinimumSize();
        Dimension namePrefSize = name.getPreferredSize();
        Dimension bodyPrefSize = body.getSize().getCopy();

        Dimension bodyMinSize = body.getMinimumSize().getCopy();
        if (bodyPrefSize.height < bodyMinSize.height ) {
            bodyPrefSize.height = bodyMinSize.height;
        }
        
        if (bodyPrefSize.width < bodyMinSize.width ) {
            bodyPrefSize.width = bodyMinSize.width;
        }
        // find the max width, between the width on the global and the one on the border.
        int maxWidth = Math.max(selfSize.width, borderPrefSize.width);
        
        // assign the max width to the components
        borderPrefSize.width = maxWidth;
        namePrefSize.width = maxWidth;
        bodyPrefSize.width = maxWidth;
        selfSize.width = maxWidth;
        
        // now deal with the height
        // first height must be bigger than the minimum height
        if (selfSize.height < container.getMinimumSize().height) {
            selfSize.height = container.getMinimumSize().height;
        }
        // if the height of the border or the name has changed, then  this condition
        // returns true
        if (body.getSize().height + name.getSize().height + border.getSize().height > 
                                          container.getSize().height) {
            // the size of one of the components changed:
            // the body size should not be changed.
            // we need to adjust the global size
            selfSize.height = body.getSize().height + name.getSize().height + border.getSize().height;
        } else {
            // otherwise the body or the global has changed, and needs to be resized
            bodyPrefSize.height = selfSize.height - borderPrefSize.height - namePrefSize.height;
            int bodyPrefSizeMin = container.getMinimumSize().height - borderPrefSize.height - namePrefSize.height;
            if (bodyPrefSize.height < bodyPrefSizeMin) {
                bodyPrefSize.height = bodyPrefSizeMin;
            }
        }

        // assign locations
        name.setLocation(new Point(area.x, area.y));
        name.setSize(namePrefSize);
        body.setLocation(new Point(area.x, area.y + namePrefSize.height));
        body.setSize(bodyPrefSize);
        border.setLocation(new Point(area.x, body.getLocation().y + body.getSize().height));
        border.setSize(borderPrefSize);
        // set the final size on the container
        container.setSize(new Dimension(maxWidth, 
                borderPrefSize.height + namePrefSize.height + bodyPrefSize.height 
                        ));
        container.getParent().setSize(container.getSize());
    }

    public void remove(IFigure figure) {
        if (body == figure) {
            body = null;
        } else if (border == figure) {
            border = null;
        } else if (name == figure) {
            name = null;
        }
    }

    public void setConstraint(IFigure figure, Object constraint) {
        remove(figure);
        switch(((Integer) constraint).intValue()) {
        case BODY:
            body = (SubProcessBodyFigure) figure;
            break;
        case BORDER:
            border = (SubProcessBorderFigure) figure;
            break;
        case NAME:
            name = figure;
            break;
        default:
            throw new IllegalArgumentException("Invalid constraint");
        }
    }

}
