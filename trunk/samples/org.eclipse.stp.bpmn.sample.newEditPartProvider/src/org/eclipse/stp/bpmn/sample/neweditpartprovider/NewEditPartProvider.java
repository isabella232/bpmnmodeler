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
 * Date         Author                  Changes
 * Feb 29, 2008  Antoine Toulme      Created
 */
package org.eclipse.stp.bpmn.sample.neweditpartprovider;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnEditPartFactory;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotation2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotationEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.diagram.providers.BpmnEditPartProvider;

/**
 * We override the default edit part provider by gicing our own factory, extending the default factory.
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class NewEditPartProvider extends BpmnEditPartProvider {

    public NewEditPartProvider() {
        setFactory(new NewEditPartFactory());
        setAllowCaching(true);
    }
    
    /**
     * We extend the BPMNEditPartFactory to give our own edit parts for text annotations.
     * 
     * @author <a href="http://www.intalio.com">Intalio Inc.</a>
     * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
     */
    private class NewEditPartFactory extends BpmnEditPartFactory {
        
        /**
         * Creates a special text annotation edit part
         */
        public EditPart createEditPart(EditPart context, Object model) {
             if (model instanceof View) {
                 final View view = (View) model;
                 int viewVisualID = BpmnVisualIDRegistry.getVisualID(view);
                 switch (viewVisualID) {
                 case TextAnnotationEditPart.VISUAL_ID :
                     return new TextAnnotationEditPartWithBackground(view);
                 case TextAnnotation2EditPart.VISUAL_ID :
                     return new TextAnnotation2EditPartWithBackground(view);
                 }
             }
             return super.createEditPart(context, model);
        }
    }
    
    /**
     * Our text annotation edit part that will show text annotations with a background.
     * 
     * @author <a href="http://www.intalio.com">Intalio Inc.</a>
     * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
     */
    private class TextAnnotationEditPartWithBackground extends TextAnnotationEditPart {

        public TextAnnotationEditPartWithBackground(View view) {
            super(view);
        }
        
        
        
        protected IFigure createNodeShape() {
            TextAnnotationFigure figure = new TextAnnotationFigure() {
                @Override
                protected void paintFigure(Graphics graphics) {
                    super.paintFigure(graphics);
                    IMapMode mm = MapModeUtil.getMapMode(this);
                    int lineWidth = mm.LPtoDP(1);
                    graphics.setForegroundColor(getForegroundColor());
                    graphics.setBackgroundColor(getBackgroundColor());
                    Rectangle toFill = getBounds().getCopy();
                    toFill.height -= lineWidth * 2;
                    toFill.width -= lineWidth;
                    toFill.x += lineWidth;
                    toFill.y += lineWidth;

                    graphics.fillGradient(toFill, false);
                }
            };
            return primaryShape = figure;
        }
    }
    
    /**
     * Our text annotation for diagram text annotations, with a background.
     * 
     * @author <a href="http://www.intalio.com">Intalio Inc.</a>
     * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
     */
    private class TextAnnotation2EditPartWithBackground extends TextAnnotation2EditPart {

        public TextAnnotation2EditPartWithBackground(View view) {
            super(view);
        }
        
        protected IFigure createNodeShape() {
            TextAnnotationFigure figure = new TextAnnotationFigure() {
                
                @Override
                protected void paintFigure(Graphics graphics) {
                    super.paintFigure(graphics);
                    IMapMode mm = MapModeUtil.getMapMode(this);
                    int lineWidth = mm.LPtoDP(1);
                    graphics.setForegroundColor(getForegroundColor());
                    graphics.setBackgroundColor(getBackgroundColor());
                    Rectangle toFill = getBounds().getCopy();
                    toFill.height -= lineWidth * 2;
                    toFill.width -= lineWidth;
                    toFill.x += lineWidth;
                    toFill.y += lineWidth;

                    graphics.fillGradient(toFill, false);
                }
            };
            return primaryShape = figure;
        }
    }
}
