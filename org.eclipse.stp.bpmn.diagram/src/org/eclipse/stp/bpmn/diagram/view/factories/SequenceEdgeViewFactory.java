/*
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 */
package org.eclipse.stp.bpmn.diagram.view.factories;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.ConnectionViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.notation.LineStyle;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.RoutingStyle;
import org.eclipse.gmf.runtime.notation.Smoothness;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnDiagramEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SequenceEdgeNameEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;

/**
 * @generated
 */
public class SequenceEdgeViewFactory extends ConnectionViewFactory {

    /**
     * @generated NOT adding the line style
     */
    protected List createStyles(View view) {
        List styles = new ArrayList();
        styles.add(NotationFactory.eINSTANCE.createRoutingStyle());
        styles.add(NotationFactory.eINSTANCE.createFontStyle());
        styles.add(NotationFactory.eINSTANCE.createLineStyle());
        return styles;
    }

    /**
     * @generated
     */
    protected void decorateViewGen(View containerView, View view,
            IAdaptable semanticAdapter, String semanticHint, int index,
            boolean persisted) {
        if (semanticHint == null) {
            semanticHint = BpmnVisualIDRegistry
                    .getType(org.eclipse.stp.bpmn.diagram.edit.parts.SequenceEdgeEditPart.VISUAL_ID);
            view.setType(semanticHint);
        }
        super.decorateView(containerView, view, semanticAdapter, semanticHint,
                index, persisted);
        if (!BpmnDiagramEditPart.MODEL_ID.equals(BpmnVisualIDRegistry
                .getModelID(containerView))) {
            EAnnotation shortcutAnnotation = EcoreFactory.eINSTANCE
                    .createEAnnotation();
            shortcutAnnotation.setSource("Shortcut"); //$NON-NLS-1$
            shortcutAnnotation.getDetails().put(
                    "modelID", BpmnDiagramEditPart.MODEL_ID); //$NON-NLS-1$
            view.getEAnnotations().add(shortcutAnnotation);
        }
        getViewService().createNode(
                semanticAdapter,
                view,
                BpmnVisualIDRegistry
                        .getType(SequenceEdgeNameEditPart.VISUAL_ID),
                ViewUtil.APPEND, true, getPreferencesHint());
    }
    /**
     * hmalphettes: for now force the routing style to rectilinear.
     * 
     * @generated NOT
     */
    protected void decorateView(View containerView, View view,
            IAdaptable semanticAdapter, String semanticHint, int index,
            boolean persisted) {

        this.decorateViewGen(containerView, view, semanticAdapter,
                semanticHint, index, persisted);

        RoutingStyle style = (RoutingStyle) view
                .getStyle(NotationPackage.eINSTANCE.getRoutingStyle());
        IPreferenceStore preferenceStore = (IPreferenceStore)
            super.getPreferencesHint().getPreferenceStore();
        
        //routing
        String routingV = preferenceStore.getString(
                BpmnDiagramPreferenceInitializer.PREF_SEQ_LINE_STYLE);
        Routing routing = Routing.getByName(routingV);
        if (routing == null) {
            routing = Routing.RECTILINEAR_LITERAL;
        }
        style.setRouting(routing);
        
        //avoid obstacles
        boolean avoidObstacle =
            preferenceStore.getBoolean(
                BpmnDiagramPreferenceInitializer.PREF_SEQ_ROUTE_AVOID_OBSTACLES);
        style.setAvoidObstructions(avoidObstacle);
        
        //shortestPath
        boolean closestDist =
            preferenceStore.getBoolean(
                    BpmnDiagramPreferenceInitializer.PREF_SEQ_ROUTE_SHORTEST);
        style.setClosestDistance(closestDist);

        //smooth factor
        String smoothStr = preferenceStore.getString(
                BpmnDiagramPreferenceInitializer.PREF_SEQ_ROUTE_SMOOTH_FACTOR);
        Smoothness smoothness = Smoothness.get(smoothStr);
        if (smoothness == null) {
            smoothness = Smoothness.NORMAL_LITERAL;
        }
        style.setSmoothness(smoothness);
        
        //color:
        LineStyle lineStyle = (LineStyle) view
            .getStyle(NotationPackage.eINSTANCE.getLineStyle());
        lineStyle.setLineColor(FigureUtilities.RGBToInteger(
                PreferenceConverter.getColor(preferenceStore,
                BpmnDiagramPreferenceInitializer.PREF_SEQ_LINE_COLOR)));
    }
}
