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

import java.lang.reflect.Field;

import org.eclipse.draw2d.ActionEvent;
import org.eclipse.draw2d.ActionListener;
import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.ButtonBorder;
import org.eclipse.draw2d.ButtonModel;
import org.eclipse.draw2d.Clickable;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.internal.ui.palette.editparts.DetailedLabelFigure;
import org.eclipse.gef.internal.ui.palette.editparts.ToolEntryEditPart;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.swt.graphics.Color;

/**
 * This palettes impleemnts a few enhancements over the standard GEF palette.
 * <ul>
 * <li>Sticky tools: click on a tool-entry that was already toggled in.
 * It will set the tool it creates not be unloaded after they have been used.</li>
 * <li>Colors: green background for the tool entries, grey for the drawers.</li>
 * </ul>
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BpmnToolEntryEditPart extends ToolEntryEditPart {

    private static Field customLabelField;
    private static Field detailedLabelNameTextField;
    private static Field paragraphTextLayoutWrapStyleField;
    private static void init() {
        if (customLabelField != null) {
            return;
        }
        try {
            customLabelField = ToolEntryEditPart.class.getDeclaredField("customLabel"); //$NON-NLS-1$
            customLabelField.setAccessible(true);
            detailedLabelNameTextField = DetailedLabelFigure.class
                .getDeclaredField("nameText"); //$NON-NLS-1$
            detailedLabelNameTextField.setAccessible(true);
            paragraphTextLayoutWrapStyleField = ParagraphTextLayout.class
                .getDeclaredField("wrappingStyle"); //$NON-NLS-1$
            paragraphTextLayoutWrapStyleField.setAccessible(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /** background color */
    Color _backgroundColor;
    /** foreground color */
    Color _foregroundColor;
    
    static final Border BORDER_TOGGLE = new ButtonBorder(ButtonBorder.SCHEMES.TOOLBAR);
    static final Border COLUMNS_BORDER = new MarginBorder(2, 0, 1, 0);

    public BpmnToolEntryEditPart(PaletteEntry paletteEntry,
            Color foregroundColor, Color backgroundColor) {
        super(paletteEntry);
        _foregroundColor = foregroundColor;
        _backgroundColor = backgroundColor;
        init();
    }

    /**
     * Overrides to create our own toggle button that is able to paint differently
     * when in sticky mode.
     */
    @Override
    public IFigure createFigure() {
        DetailedLabelFigure customLabel = new DetailedLabelFigure();

        this.setCustomLabel(customLabel);
//        Clickable button = new InactiveToggleButton(customLabel);
        Clickable button = new ToolEntryFigureInactiveToggleButton(this, customLabel);
        try {
            //customize the word-wrap inside the palette
            TextFlow theNameText = (TextFlow)
                detailedLabelNameTextField.get(customLabel);
            ParagraphTextLayout txtLayout = (ParagraphTextLayout)
                theNameText.getLayoutManager();
            paragraphTextLayoutWrapStyleField
                .setInt(txtLayout, ParagraphTextLayout.WORD_WRAP_HARD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                BpmnPaletteViewer bpmnPaletteViewer = (BpmnPaletteViewer)getViewer();
                bpmnPaletteViewer.setActiveTool(getToolEntry());
            }
        });

        return button;
    }
    
    private ToolEntry getToolEntry() {
        return (ToolEntry)getModel();
    }
    
//    public DetailedLabelFigure getCustomLabel() {
//        try {
//            return (DetailedLabelFigure) customLabelField.get(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    protected void setCustomLabel(DetailedLabelFigure customLabel) {
        try {
            customLabelField.set(this, customLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private ButtonModel getButtonModel() {
        Clickable c = (Clickable)getFigure();
        return c.getModel();
    }
    public void setToolSelected(boolean value) {
        ToolEntryFigureInactiveToggleButton tog = (ToolEntryFigureInactiveToggleButton)getFigure();
        if (getModel() instanceof IStickableToolEntry) {
            tog.isSticky = ((IStickableToolEntry)getModel()).isSticky();
        } else{
            tog.isSticky = false;
        }
        getButtonModel().setSelected(value);
        getFigure().setOpaque(value);
    }
    protected IFigure createToolTip() {
        return super.createToolTip();
    }

    
}