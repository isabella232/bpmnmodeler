/******************************************************************************
 * Copyright (c) 2006-2007 Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.stp.bpmn.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.stp.bpmn.figures.activities.ActivityPainter;
import org.eclipse.stp.gmf.runtime.draw2d.ui.figures.WrappingLabel;

/**
 * Wrap label that maintains its tooltip.
 * 
 * @author hmalphettes
 * @author Intalio Inc.
 */
public class WrapLabelWithToolTip extends WrappingLabel {

    /**
     */
    public interface IToolTipProvider {
        /**
         * @param currentTextIsTruncated true when the label is truncated.
         * @return The tooltip to use.
         */
        public String getToolTipText(boolean currentTextIsTruncated);
        
    }
    
    private final IToolTipProvider _toolTipProvider;
    private String _lastToolTip;
    private boolean trunc = false;
        
//    /**
//     * @param toolTipProvider
//     */
//    public WrapLabelWithToolTip(IToolTipProvider toolTipProvider) {
//        _toolTipProvider = toolTipProvider;
//        super.setTextWrap(true);
//    }
    
    /**
     * Makes sure we don't call the methods of the super class to setup the figure.
     * Otherwise the figure gets invalidated before the font is set on the figure
     * and we get NPEs.
     * 
     * @param toolTipProvider
     */
    public WrapLabelWithToolTip(IToolTipProvider toolTipProvider,
            Dimension prefSize, Dimension minSize, boolean isWrap,
            int labelAlignmentWithinFigure,//MIDDLE|LEFT xor TOP|CENTER
            int textJustification) {//LEFT xor CENTER
        super();
        _toolTipProvider = toolTipProvider;
        super.prefSize = prefSize;
        super.minSize = minSize;
        //don't use the method of the super.
        //otherwise unvalidate is called and the font is expected to be set.
        //NPEs would be next.
        setAlignment(labelAlignmentWithinFigure);//labelAlignmentWithinFigure);//alignment of the text within the figure
//        setTextAlignment(PositionConstants.TOP);//textAlignment);//only relevant to align compare to the icon of the label.
//        setTextPlacement(PositionConstants.NORTH);//only useful to place the text compared to the icon.
        setTextJustification(textJustification);
        super.setTextWrap(isWrap);
    }
    
    @Override
    public String getTruncationString() {
        String sub = super.getTruncationString();
        if (_toolTipProvider != null) {
            updateToolTip(sub);
        }
        return sub;
    }
    /**
     * refresh the tooltip if necessary
     */
    private void updateToolTip(String sub) {
        //when we use the method provided by WrapLabel all kind of issue ariase.
        //not sure why they need to compute the size of the font to see
        //if it gets truncated or not
        trunc = sub != null && sub.length() > 0 && !sub.equals(getText());
        String newToolTip = _toolTipProvider.getToolTipText(trunc);
        if (newToolTip == null) {
            if (_lastToolTip != null) {
                _lastToolTip = null;
                setToolTip(ActivityPainter.createToolTipFigure(_lastToolTip));
            }
        } else if (!newToolTip.equals(_lastToolTip)) {
            _lastToolTip = newToolTip;
            setToolTip(ActivityPainter.createToolTipFigure(_lastToolTip));
        }
    }
    
//    public void paintFigure(Graphics graphics) {
//        super.paintFigure(graphics);
//        graphics.drawRectangle(getTextFigure().getBounds());
//        graphics.setForegroundColor(ColorConstants.blue);
//        graphics.drawRectangle(getBounds());
//        System.err.println(getParent() + " " + getParent().getLayoutManager()
//                + " heightWhole=" + getBounds().height
//                + " heightTextFlow=" + getTextFigure().getBounds().height);
//        //graphics.drawRectangle(super.get);
//    }

}
