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
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.util.MeasurementUnitHelper;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.AbstractLabelViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.notation.FillStyle;
import org.eclipse.gmf.runtime.notation.LineStyle;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;

/**
 * @generated
 */
public class MessagingEdgeNameViewFactory extends AbstractLabelViewFactory {

    /**
     * @generated
     */
    public View createView(IAdaptable semanticAdapter, View containerView,
            String semanticHint, int index, boolean persisted,
            PreferencesHint preferencesHint) {
        Node view = (Node) super.createView(semanticAdapter, containerView,
                semanticHint, index, persisted, preferencesHint);
        Location location = (Location) view.getLayoutConstraint();
        IMapMode mapMode = MeasurementUnitHelper.getMapMode(containerView
                .getDiagram().getMeasurementUnit());
        location.setX(mapMode.DPtoLP(0));
        location.setY(mapMode.DPtoLP(40));
        return view;
    }

    /**
     * @generated NOT adding the fill and line styles.
     */
    protected List createStyles(View view) {
        List styles = new ArrayList();
        styles.add(NotationFactory.eINSTANCE.createFillStyle());
        styles.add(NotationFactory.eINSTANCE.createLineStyle());
        return styles;
    }
    
    /**
     * @generated NOT adding the fill and line styles.
     * Setting a default value for the line style
     */
    @Override
    protected void initializeFromPreferences(View view) {
        super.initializeFromPreferences(view);
        LineStyle style = (LineStyle) view.getStyle(NotationPackage.eINSTANCE.getLineStyle());
        // later, a mix between not showing, and showing with a specific color.
        style.setLineColor(FigureUtilities.RGBToInteger(
                PreferenceConverter.getColor(
                        BpmnDiagramEditorPlugin.getInstance().getPreferenceStore(), 
                BpmnDiagramPreferenceInitializer.PREF_CONNECTION_LABEL_BORDER_COLOR)));
        
        FillStyle fill = (FillStyle) view.getStyle(NotationPackage.eINSTANCE.getFillStyle());
        fill.setFillColor(FigureUtilities.RGBToInteger(
                PreferenceConverter.getColor(
                        BpmnDiagramEditorPlugin.getInstance().getPreferenceStore(), 
                BpmnDiagramPreferenceInitializer.PREF_CONNECTION_LABEL_BACKGROUND_COLOR)));
    }
}
