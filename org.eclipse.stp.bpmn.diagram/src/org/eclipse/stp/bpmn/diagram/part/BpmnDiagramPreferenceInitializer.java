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
package org.eclipse.stp.bpmn.diagram.part;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.gef.ui.palette.PaletteViewerPreferences;
import org.eclipse.gmf.runtime.diagram.ui.figures.DiagramColorConstants;
import org.eclipse.gmf.runtime.diagram.ui.preferences.DiagramPreferenceInitializer;
import org.eclipse.gmf.runtime.diagram.ui.preferences.IPreferenceConstants;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.Smoothness;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;

/**
 * @generated
 */
public class BpmnDiagramPreferenceInitializer extends
        DiagramPreferenceInitializer {

	/**
	 * @generated not string representing the state of the filters
	 * contributed to the plugin. The string is a sequence of 
	 * filter information separated by ; The filter information
	 * is represented by the id of the filter, followed by its
	 * current value, enabled or disabled. The id and the value
	 * are separated by a comma ','
	 */
	public static String FILTERS_STATES = "filterStates"; //$NON-NLS-1$
	
	/**
	 * @generated not the default value for the state of the filters.
	 * An empty String.
	 */
	
	public static String FILTERS_STATES_DEFAULT = ""; //$NON-NLS-1$
	/**
	 * @generated not hide the decorations
	 */
    public static String FILTER_DECORATIONS = "filterDecorations"; //$NON-NLS-1$
    
    /**
	 * @generated not
	 */
    public static boolean FILTER_DECORATIONS_DEFAULT = false;
	
    /** @notgenerated the preference key used to store the default color for a pool */
    public static String PREF_POOL_DEFAULT_FILL_COLOR = "bpmn.pool.fill.color.default"; //$NON-NLS-1$

    /** @notgenerated */
    public static String PREF_POOL_DEFAULT_BORDER_COLOR = "bpmn.pool.border.color.default"; //$NON-NLS-1$

    /** @notgenerated the preference key used to store the default color for a subprocess*/
    public static String PREF_SUBPROCESS_DEFAULT_FILL_COLOR = "bpmn.subprocess.fill.color.default"; //$NON-NLS-1$
    
    /** @notgenerated the preference key used to store the default color for a task*/
    public static String PREF_TASK_DEFAULT_FILL_COLOR = "bpmn.task.fill.color.default"; //$NON-NLS-1$
    
    /** @notgenerated the preference key used to store the default color for a event*/
    public static String PREF_EVENT_DEFAULT_FILL_COLOR = "bpmn.event.fill.color.default"; //$NON-NLS-1$
    
    /** @notgenerated the preference key used to store the default color for a gateway*/
    public static String PREF_GATEWAY_DEFAULT_FILL_COLOR = "bpmn.gateway.fill.color.default"; //$NON-NLS-1$
    
    /** @notgenerated the preference key used to store the default value of snap to geometry*/
    public static String PREF_SNAP_TO_GEOMETRY = "bpmn.snap.to.geometry"; //$NON-NLS-1$
    
    /** The key in the preferences for the base prefix to generated default 
     * diagram namespaces */
    public static final String PREF_AUTHOR = "diagramAuthor"; //$NON-NLS-1$
    
    /** The default value for the author of the diagrams **/
    public static final String PREF_AUTHOR_DEFAULT_VALUE = ""; //$NON-NLS-1$

    /** the preference key used to store the default value for the
     * delay of the connection assistant
     * (milliseconds < 0 will always hide it default is 50.) */
    public static final String PREF_CONN_DIAG_ASSISTANT_DELAY_MS =
        "bpmn.connection.assistant.appearance.delay"; //$NON-NLS-1$
    
    /**
     * The preference key to register the collapse style.
     * 
     * Currently this preference key is mapped to a boolean value
     * to set the siblings arranging on.
     */
    public static final String PREF_SP_COLLAPSE_STYLE = 
        "bpmn.subprocess.collapse.style"; //$NON-NLS-1$
    /**
     * the connection line style for the messaging edge;
     * shows up in ConnectionsPreferencePage 
     */
    public static final String PREF_MSG_LINE_STYLE = "bpmn.message.Connectors.lineStyle"; //$NON-NLS-1$
    /**
     * the connection line style for the sequence edge;
     * shows up in ConnectionsPreferencePage 
     */
    public static final String PREF_SEQ_LINE_STYLE = "bpmn.sequence.Connectors.lineStyle"; //$NON-NLS-1$

    /**
     * whether new message connections should be routed with the shortest path
     */
    public static String PREF_MSG_ROUTE_SHORTEST = "bpmn.sequence.Connectors.route.shortest"; //$NON-NLS-1$
    /**
     * whether new sequence connections should be routed with the shortest path
     */
    public static String PREF_SEQ_ROUTE_SHORTEST = "bpmn.message.Connectors.route.shortest"; //$NON-NLS-1$
    /**
     * whether new message connections should use avoid obstacle algo
     */
    public static String PREF_MSG_ROUTE_AVOID_OBSTACLES = "bpmn.message.Connectors.route.avoid.obstacles"; //$NON-NLS-1$
    /**
     * whether new sequence connections should use avoid obstacle algo
     */
    public static String PREF_SEQ_ROUTE_AVOID_OBSTACLES =  "bpmn.sequence.Connectors.route.avoid.obstacles"; //$NON-NLS-1$
    /**
     * smotthness factor for msg edge: 0=NONE, 16=Less, 32=NORMAL, 64=MORE
     */
    public static String PREF_MSG_ROUTE_SMOOTH_FACTOR =  "bpmn.message.Connectors.route.smoothness"; //$NON-NLS-1$
    /**
     * smotthness factor for seq edge: 0=NONE, 16=Less, 32=NORMAL, 64=MORE
     */
    public static String PREF_SEQ_ROUTE_SMOOTH_FACTOR =  "bpmn.sequence.Connectors.route.smoothness"; //$NON-NLS-1$
    
    /**
     * Initializes all the generic diagram preferences with their default
     * values. Override to initialize new preferences added.
     * 
     * Makes sure that after the static initializaters of GMF have been called
     * we override the values with our own.
     * 
     */
    public void initializeDefaultPreferences() {
        super.initializeDefaultPreferences();
        internalSetDefaultFontPref(getPreferenceStore());
        internalSetFillColorPref(getPreferenceStore());
        
        getPreferenceStore().setDefault(PREF_SNAP_TO_GEOMETRY, true);
        getPreferenceStore().setDefault(PREF_CONN_DIAG_ASSISTANT_DELAY_MS, 0);
        getPreferenceStore().setDefault(PREF_SP_COLLAPSE_STYLE, true);
        
        getPreferenceStore().setDefault(PREF_SEQ_LINE_STYLE, Routing.RECTILINEAR);
        getPreferenceStore().setDefault(PREF_MSG_LINE_STYLE, Routing.RECTILINEAR);
        getPreferenceStore().setDefault(PREF_SEQ_ROUTE_AVOID_OBSTACLES, false);
        getPreferenceStore().setDefault(PREF_MSG_ROUTE_AVOID_OBSTACLES, false);
        getPreferenceStore().setDefault(PREF_SEQ_ROUTE_SMOOTH_FACTOR, Smoothness.NORMAL);
        getPreferenceStore().setDefault(PREF_MSG_ROUTE_SMOOTH_FACTOR, Smoothness.NORMAL);
        getPreferenceStore().setDefault(PREF_SEQ_ROUTE_SHORTEST, true);//true for now until the algo gets better.
        getPreferenceStore().setDefault(PREF_MSG_ROUTE_SHORTEST, true);
        
        
        //be careful special treatment is required for all GEF preferences to
        //not be overriden by GEF itself
        getPreferenceStore().setDefault(
                PaletteViewerPreferences.PREFERENCE_LAYOUT,
                PaletteViewerPreferences.LAYOUT_COLUMNS);
        
        getPreferenceStore().setDefault(PREF_AUTHOR, PREF_AUTHOR_DEFAULT_VALUE);
        
        // hiding decorations on demand
        getPreferenceStore().setDefault(FILTER_DECORATIONS, FILTER_DECORATIONS_DEFAULT);
        
        // memorizing the state of the filters.
        getPreferenceStore().setDefault(FILTERS_STATES, FILTERS_STATES_DEFAULT);
        
        // override the default line color
        Color lineColor = DiagramColorConstants.black;
        PreferenceConverter.setDefault(
            getPreferenceStore(),
            IPreferenceConstants.PREF_LINE_COLOR,
            lineColor.getRGB());
    }

    /**
     * @generated
     */
    protected IPreferenceStore getPreferenceStore() {
        return org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin
                .getInstance().getPreferenceStore();
    }

    /**
     * Hardcode the default font to Arial.
     * TODO: use the correct way (symbolic name, font regsitry ?) if there is one.
     * @param store
     */
    private void internalSetDefaultFontPref(IPreferenceStore store) {
        Font f = new Font(JFaceResources.getDefaultFont().getDevice(), "Arial", //$NON-NLS-1$
                9, SWT.NONE);
        FontData[] fontDataArray = f != null ? f.getFontData() : JFaceResources
                .getDefaultFont().getFontData();
        // use all the fontData for the Mac OS platform
        for ( FontData fontData : fontDataArray) {
            fontData.setHeight(9);
        }
        PreferenceConverter.setDefault(store,
                IPreferenceConstants.PREF_DEFAULT_FONT, fontDataArray);
        // end up disposing the font
        f.dispose();
    }

    /**
     * Hardcode the default font to Arial.
     * TODO: use the correct way (symbolic name, font regsitry ?) if there is one.
     * @param store
     */
    private void internalSetFillColorPref(IPreferenceStore store) {
        PreferenceConverter.setDefault(store,
                IPreferenceConstants.PREF_FILL_COLOR, ColorConstants.white
                        .getRGB());
        PreferenceConverter.setDefault(store, PREF_POOL_DEFAULT_FILL_COLOR,
                new RGB(232, 232, 255));
        PreferenceConverter.setDefault(store, PREF_POOL_DEFAULT_BORDER_COLOR,
                new RGB(169, 169, 169));
        
        PreferenceConverter.setDefault(store, PREF_TASK_DEFAULT_FILL_COLOR,
                ColorConstants.white.getRGB());
        PreferenceConverter.setDefault(store, PREF_EVENT_DEFAULT_FILL_COLOR,
                ColorConstants.white.getRGB());
        PreferenceConverter.setDefault(store, PREF_GATEWAY_DEFAULT_FILL_COLOR,
                ColorConstants.white.getRGB());
        PreferenceConverter.setDefault(store, PREF_SUBPROCESS_DEFAULT_FILL_COLOR,
                ColorConstants.white.getRGB());
        
    }

}
