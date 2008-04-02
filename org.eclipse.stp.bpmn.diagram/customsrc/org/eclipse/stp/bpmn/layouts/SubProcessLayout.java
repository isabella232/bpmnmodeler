/******************************************************************************
 * Copyright (c) 2006-2008, Intalio Inc.
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
import org.eclipse.draw2d.StackLayout;
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
 * Added support for compartment resize and positioning.
 * <p>
 * The name is placed at the top of the client area.
 * The border is placed at the bottom of the client area.
 * The body uses the visibleBounds of the subprocessfigure:
 * that is it overlaps with the name and the upper half of the border.
 * </p>
 * 
 * @author hmalphettes
 * @author atoulme
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class SubProcessLayout extends StackLayout {

    public static final int NAME = 0;
    public static final int BODY =1;
    public static final int BORDER = 2;
    
    private IFigure name;
    private SubProcessBodyFigure body;
    private SubProcessBorderFigure border;
    
    public SubProcessLayout() {
        super();
        super.setObserveVisibility(true);
    }
    
    public void layout(IFigure container) {
        SubProcessFigure spFigure = (SubProcessFigure)container;
        Rectangle area = container.getClientArea().getCopy();
        Dimension borderPrefSize = border.getMinimumSize(container.getSize().width, container.getSize().height);
        name.invalidate();
        Dimension namePrefSize = name.getPreferredSize();
        
     // introduce the initial dimension
        Dimension selfSize = area.getSize().getCopy();
//        Dimension borderPrefSize = border.getMinimumSize();
//        Dimension namePrefSize = name.getPreferredSize();
        Dimension bodyPrefSize = body.getSize().getCopy();

//        Dimension bodyMinSize = body.getMinimumSize().getCopy();
//        if (bodyPrefSize.height < bodyMinSize.height ) {
//            bodyPrefSize.height = bodyMinSize.height;
//        }
//        
//        if (bodyPrefSize.width < bodyMinSize.width ) {
//            bodyPrefSize.width = bodyMinSize.width;
//        }
        // find the max width, between the width on the global and the one on the border.
        int maxWidth = Math.max(selfSize.width, borderPrefSize.width);
        maxWidth = Math.max(namePrefSize.width, maxWidth);
        // assign the max width to the components
        borderPrefSize.width = maxWidth;
        namePrefSize.width = maxWidth;
        bodyPrefSize.width = maxWidth;
        selfSize.width = maxWidth;
        
//      Dimension minSize = container.getMinimumSize();
        // minimal size: the widest possible between the label and the border,
        // and the height is the height of the name, plus the collapse handle, plus the border
        Dimension minSize = new Dimension(Math.max(maxWidth, SubProcessEditPart.COLLAPSED_SIZE.width), 
                borderPrefSize.height + namePrefSize.height + SubProcessEditPart.COLLAPSE_HANDLE_HEIGHT);
        if (spFigure.isCollapsed()) {
            // make sure the width is at least the default width 
            minSize.width = Math.max(minSize.width, SubProcessEditPart.COLLAPSED_SIZE.width);
            // make sure the height is the default height; if there are border events, their height is added to the default height.
            minSize.height = Math.max(minSize.height, SubProcessEditPart.COLLAPSED_SIZE.height + borderPrefSize.height - SubProcessEditPart.BORDER_HEIGHT/2);
            
            name.setLocation(area.getLocation().getCopy());
            name.setSize(minSize.width, namePrefSize.height);
            body.setLocation(new Point(area.x, area.y));
            body.setSize(bodyPrefSize);
            border.setLocation(new Point(area.x, area.y + minSize.height - borderPrefSize.height));
            border.setSize(minSize.width, borderPrefSize.height);
            container.setSize(minSize);
            container.getParent().setSize(container.getSize());
            return;
        }
        
        /*
         * make sure the minSize is at least as big as the expanded size.
         * 
         * Here there is a kludge. See EDGE-2213: the insets are lost during the expand collapse process
         * as there 
         */
        minSize.width = Math.max(minSize.width, bodyPrefSize.width + SubProcessEditPart.INSETS.getWidth());
        minSize.width = Math.max(minSize.width, SubProcessEditPart.EXPANDED_SIZE.width);
        minSize.height = Math.max(minSize.height, SubProcessEditPart.EXPANDED_SIZE.height);
        
     // assign the max width to the components
        borderPrefSize.width = minSize.width;
        namePrefSize.width = minSize.width;
        bodyPrefSize.width = minSize.width;
        selfSize.width = minSize.width;
        
        // now deal with the height
        
        // the body size is set in the ResizableSubProcessEditPolicy,
        // so we can just set the container size confidently.
        selfSize.height = bodyPrefSize.height + borderPrefSize.height - ActivityEditPart.EVENT_FIGURE_SIZE/2;
        if (selfSize.height < minSize.height) {
            selfSize.height = minSize.height;
        }
        if (selfSize.width < minSize.width) {
            selfSize.width = minSize.width;
        }

        // assign locations
        name.setLocation(new Point(area.x, area.y));
        name.setSize(namePrefSize);
        body.setLocation(new Point(area.x, area.y));
        body.setSize(bodyPrefSize);
        border.setLocation(new Point(area.x, area.y + selfSize.height - borderPrefSize.height));
        border.setSize(borderPrefSize);
        // set the final size on the container
        
        container.setSize(selfSize.width, selfSize.height);
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
