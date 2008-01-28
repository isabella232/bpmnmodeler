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

import java.lang.reflect.Field;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.stp.bpmn.figures.activities.ActivityPainter;

/**
 * Wrap label that maintains its tooltip.
 * 
 * @author hmalphettes
 * @author Intalio Inc.
 */
public class WrapLabelWithToolTip extends WrapLabel {

    /**
     */
    public interface IToolTipProvider {
        /**
         * @param currentTextIsTruncated true when the label is truncated.
         * @return The tooltip to use.
         */
        public String getToolTipText(boolean currentTextIsTruncated);
        
    }
    
    private static Field textField;
    private static void init() {
        if (textField != null) {
            return;
        }
        try {
            textField = WrapLabel.class.getDeclaredField("text"); //$NON-NLS-1$
            textField.setAccessible(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    
    private final IToolTipProvider _toolTipProvider;
    private String _lastToolTip;
    private boolean trunc = false;
        
    /**
     * @param toolTipProvider
     */
    public WrapLabelWithToolTip(IToolTipProvider toolTipProvider) {
        init();
        _toolTipProvider = toolTipProvider;
    }
    
    /**
     * Makes sure we don't call the methods of the super class to setup the figure.
     * Otherwise the figure gets invalidated before the font is set on the figure
     * and we get NPEs.
     * 
     * @param toolTipProvider
     */
    public WrapLabelWithToolTip(IToolTipProvider toolTipProvider,
            Dimension prefSize, Dimension minSize, boolean isWrap, int textAlignment) {
        this(toolTipProvider);
        super.prefSize = prefSize;
        super.minSize = minSize;
        //don't use the method of the super.
        //otherwise unvalidate is called and the font is expected to be set.
        //NPEs would be next.
        setFlag(/*FLAG_WRAP*/MAX_FLAG << 5, true);
        setFlag(/*FLAG_TEXT_ALIGN*/ MAX_FLAG << 6, true);
    }
    
    
    /**
     * Sometimes this method is called before the font is set.
     * and that makes the GMF code throw NPEs.
     */
    @Override
    public void setText(String s) {
        if (getFont() == null) {
            try {
                textField.set(this, s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            super.setText(s);
        }
    }
    /**
     * Sometimes this method is called before the font is set.
     * and that makes the GMF code throw NPEs.
     */
    @Override
    public void setTextWrap(boolean b) {
        if (getFont() != null) {
            super.setTextWrap(b);
        } else {
            setFlag(/*FLAG_WRAP*/MAX_FLAG << 5, b);
        }
    }
    /**
     * Sometimes this method is called before the font is set.
     * and that makes the GMF code throw NPEs.
     */
    @Override
    public void setTextAlignment(int align) {
        if (getFont() != null) {
            super.setTextAlignment(align);
        } else {
            setAlignmentFlags(align, /*FLAG_TEXT_ALIGN*/MAX_FLAG << 6);
        }
    }
    
    /**
     * Copied from the super where it is private
     */
    private void setAlignmentFlags(int align, int flagOffset) {
        flags &= ~(0x7 * flagOffset);
        switch (align) {
            case CENTER:
                flags |= 0x1 * flagOffset;
                break;
            case TOP:
                flags |= 0x2 * flagOffset;
                break;
            case LEFT:
                flags |= 0x3 * flagOffset;
                break;
            case RIGHT:
                flags |= 0x4 * flagOffset;
                break;
            case BOTTOM:
                flags |= 0x5 * flagOffset;
                break;
        }
    }


    @Override
    public void setTextPlacement(int where) {
        if (getFont() != null) {
            setTextPlacement(where);
        } else {
            setPlacementFlags(where, /*FLAG_TEXT_PLACEMENT*/MAX_FLAG << 18);
        }
        super.setTextPlacement(where);
    }
    /**
     * Copied from the super where it is private
     */
    private void setPlacementFlags(int align, int flagOffset) {
        flags &= ~(0x7 * flagOffset);
        switch (align) {
            case EAST:
                flags |= 0x1 * flagOffset;
                break;
            case WEST:
                flags |= 0x2 * flagOffset;
                break;
            case NORTH:
                flags |= 0x3 * flagOffset;
                break;
            case SOUTH:
                flags |= 0x4 * flagOffset;
                break;
        }
    }

    @Override
    public void setTextStrikeThrough(boolean b) {
        if (getFont() != null) {
            super.setTextStrikeThrough(b);
        } else {
            setFlag(/*FLAG_STRIKEDTHROUGH*/ MAX_FLAG << 4, b);
        }
    }

    @Override
    public void setTextUnderline(boolean b) {
        if (getFont() != null) {
            super.setTextUnderline(b);
        } else {
            setFlag(/*FLAG_UNDERLINED*/ MAX_FLAG << 3, b);
        }
    }

    @Override
    public void setTextWrapAlignment(int i) {
        if (getFont() != null) {
            super.setTextWrapAlignment(i);
        } else {
            setAlignmentFlags(i, MAX_FLAG << 9);
        }
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

    @Override
    public String getSubStringText() {
        String sub = super.getSubStringText();
        if (_toolTipProvider != null) {
            updateToolTip(sub);
        }
        return sub;
    }
    
    /**
     * Invalidating the figure does not mean that its preferred, min and max size
     * should be reset to null. (!).
     * Also if the font is not set we know for sure the figure is not ready
     * and has never been painted yet. so there is nothing to invalidate.
     * @see IFigure#invalidate()
     */
    @Override
    public void invalidate() {
        if (getFont() == null) {
            return;//the figure is not ready.
        }
        Dimension theprefSize = getPreferredSize();
        Dimension theminSize = getMinimumSize();
        super.invalidate();
        //now restore them:
        super.minSize = theminSize;
        super.prefSize = theprefSize;
    }

    /**
     * Returns the width of the label when not truncated
     * @param w
     * @param h
     * @return
     */
    public int getTextSizeWidth(int w, int h) {
        return super.getTextSize(w, h).width;
    }
}
