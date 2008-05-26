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
import org.eclipse.gmf.runtime.diagram.ui.view.factories.AbstractShapeViewFactory;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnDiagramEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.swt.graphics.RGB;

/**
 * @generated
 */
public class PoolViewFactory extends AbstractShapeViewFactory {

    /**
     * @generated 
     */
    protected List createStyles(View view) {
        List styles = new ArrayList();
        styles.add(NotationFactory.eINSTANCE.createFontStyle());
        styles.add(NotationFactory.eINSTANCE.createDescriptionStyle());
        styles.add(NotationFactory.eINSTANCE.createFillStyle());
        styles.add(NotationFactory.eINSTANCE.createLineStyle());
        return styles;
    }

    /**
     * @generated
     */
    protected void decorateView(View containerView, View view,
            IAdaptable semanticAdapter, String semanticHint, int index,
            boolean persisted) {
        if (semanticHint == null) {
            semanticHint = BpmnVisualIDRegistry
                    .getType(org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart.VISUAL_ID);
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
        getViewService().createNode(semanticAdapter, view,
                BpmnVisualIDRegistry.getType(PoolNameEditPart.VISUAL_ID),
                ViewUtil.APPEND, true, getPreferencesHint());
        getViewService().createNode(
                semanticAdapter,
                view,
                BpmnVisualIDRegistry
                        .getType(PoolPoolCompartmentEditPart.VISUAL_ID),
                ViewUtil.APPEND, true, getPreferencesHint());
    }
    /**
     * Initialize the passed view from the preference store Reads the fil color
     * and border color specific to the pool.
     * @generated NOT
     * @param view
     *            the view to initialize
     */
    protected void initializeFromPreferences(View view) {

        IPreferenceStore store = (IPreferenceStore) getPreferencesHint()
                .getPreferenceStore();
        if (store == null) {
            return;
        }
        super.initializeFromPreferences(view);

        // now takes care of the fill style
        // fill color
        RGB fillRGB = PreferenceConverter.getColor(store,
                BpmnDiagramPreferenceInitializer.PREF_POOL_DEFAULT_FILL_COLOR);
        ViewUtil.setStructuralFeatureValue(view, NotationPackage.eINSTANCE
                .getFillStyle_FillColor(), FigureUtilities
                .RGBToInteger(fillRGB));
        // now takes care of the line-color style
        // border color
        RGB borderRGB = PreferenceConverter
                .getColor(
                        store,
                        BpmnDiagramPreferenceInitializer.PREF_POOL_DEFAULT_BORDER_COLOR);
        ViewUtil.setStructuralFeatureValue(view, NotationPackage.eINSTANCE
                .getLineStyle_LineColor(), FigureUtilities
                .RGBToInteger(borderRGB));

    }
}
