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

package org.eclipse.stp.bpmn.palette;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.internal.ui.palette.editparts.SliderPaletteEditPart;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.PaletteEditPartFactory;
import org.eclipse.swt.graphics.Color;

/**
 * Code derived from PaletteEditPartFactory
 * 
 * @see PaletteEditPartFactory
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BpmnPaletteEditPartFactory extends PaletteEditPartFactory {

    private Color _entryBackgroundColor;
    private Color _entryForegroundColor;
    
    public BpmnPaletteEditPartFactory(Color entryForegroundColor,
                                      Color entryBackgroundColor) {
        _entryBackgroundColor = entryBackgroundColor;
        _entryForegroundColor = entryForegroundColor;
    }
    public BpmnPaletteEditPartFactory() {
        _entryBackgroundColor = ColorConstants.button;
        _entryForegroundColor = ColorConstants.buttonLightest;
    }
    
    
    @Override
    protected EditPart createEntryEditPart(EditPart parentEditPart, Object model) {
//        return new BpmnToolEntryEditPart2((PaletteEntry)model);
        return new BpmnToolEntryEditPart((PaletteEntry)model,
                _entryForegroundColor,
                _entryBackgroundColor);
    }
    
    /**
     * Overrides the background color of the main color.
     */
    @Override
    protected EditPart createMainPaletteEditPart(EditPart parentEditPart, Object model) {
        return new SliderPaletteEditPart((PaletteRoot)model) {
            @Override
            public IFigure createFigure() {
                IFigure fig = super.createFigure();
                fig.setBackgroundColor(_entryBackgroundColor);
                return fig;
            }
        };
    }

}
