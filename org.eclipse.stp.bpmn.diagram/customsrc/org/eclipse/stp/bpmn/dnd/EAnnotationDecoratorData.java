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

import org.eclipse.draw2d.IFigure;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget.Direction;
import org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator2.IEAnnotationDecoratorData;
import org.eclipse.swt.graphics.Image;

/**
 * @see IEAnnotationDecoratorData
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class EAnnotationDecoratorData implements IEAnnotationDecoratorData {

    private Direction _direction;
    private Image _image;
    private IFigure _tooltip;

    public void setDirection(Direction dir) {
        _direction = dir;
    }
    
    /**
     * @return the direction, ie the emplacement of the annotation decoration.
     * must be one of the PositionConstants values.
     */
    public Direction getDirection() {
        return _direction;
    }

    public void setImage(Image desc) {
        _image = desc;
    }
    
    /**
     * @return the image
     */
    public Image getImage() {
        return _image;
    }

    public void setTooltip(IFigure fig) {
        _tooltip = fig;
    }
    
    /**
     * @return the tooltip.
     */
    public IFigure getToolTip() {
        return _tooltip;
    }

}
