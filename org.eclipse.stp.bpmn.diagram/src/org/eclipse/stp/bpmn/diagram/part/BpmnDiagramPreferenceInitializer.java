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

import java.util.Locale;

import org.eclipse.core.runtime.Platform;
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

    public static final RGB TRANSPARENCY_COLOR = new RGB(255, 254, 255);
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
     * the connection line color for the sequence edge;
     */
    public static String PREF_MSG_LINE_COLOR = "bpmn.sequence.Connectors.lineColor"; //$NON-NLS-1$
    /**
     * the connection line alpha (transparency) for the sequence edge;
     * 0 is completly transparent, 255 is fully opaque.
     */
    public static String PREF_MSG_LINE_ALPHA = "bpmn.sequence.Connectors.lineAlpha"; //$NON-NLS-1$
    /**
     * the connection line color for the messaging edge;
     */
    public static String PREF_SEQ_LINE_COLOR = "bpmn.message.Connectors.lineColor"; //$NON-NLS-1$
    /**
     * the connection line alpha (transparency) for the messaging edge;
     * 0 is completly transparent, 255 is fully opaque.
     */
    public static String PREF_SEQ_LINE_ALPHA = "bpmn.message.Connectors.lineAlpha"; //$NON-NLS-1$

    
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
     * whether shapes should be shown in a BPMN 1.0 or BPMN 1.1 style.
     */
    public static final String PREF_BPMN1_1_STYLE = "bpmn.1.1.style"; //$NON-NLS-1$

    /**
     * Whether the connection labels should be reset on move.
     */
    public static final String PREF_SNAP_BACK_ON_MOVE = "bpmn.connections.snap.back.on.move"; //$NON-NLS-1$

    public static final String PREF_CONNECTION_LABEL_BORDER_COLOR = "bpmn.connections.label.border.color"; //$NON-NLS-1$

    public static final String PREF_CONNECTION_LABEL_BACKGROUND_COLOR = "bpmn.connections.label.background.color"; //$NON-NLS-1$

    /**
     * if more than 0, shadows are displayed around the shapes, and sets the transparency of the shadows.
     */
    public static final String PREF_SHOW_SHADOWS_TRANSPARENCY = "bpmn.global.shadows.transparency"; //$NON-NLS-1$
    
    
    /**
     * The system property name to override the name of the default font for diagrams.
     */
    public static String VM_ARG_DEFAULT_FONT_NAME = "bpmn.defaultfont.name"; //$NON-NLS-1$
    /**
     * The system property name to override the name of the default font for diagrams.
     */
    public static String VM_ARG_DEFAULT_FONT_SIZE = "bpmn.defaultfont.size"; //$NON-NLS-1$

    private static FontData[] _defaultFontDataArray;
    
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
        
        getPreferenceStore().setDefault(IPreferenceConstants.PREF_SNAP_TO_GEOMETRY, true);
        getPreferenceStore().setDefault(PREF_SNAP_TO_GEOMETRY, true);
        getPreferenceStore().setDefault(PREF_CONN_DIAG_ASSISTANT_DELAY_MS, 0);
        getPreferenceStore().setDefault(PREF_SP_COLLAPSE_STYLE, false);
        
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
        
        getPreferenceStore().setDefault(PREF_BPMN1_1_STYLE, true);
        
        getPreferenceStore().setDefault(PREF_SNAP_BACK_ON_MOVE, true);
        
        // new RGB(1, 0, 0) is a default color that we use for transparency.
        PreferenceConverter.setDefault(getPreferenceStore(), 
                PREF_CONNECTION_LABEL_BORDER_COLOR, 
                TRANSPARENCY_COLOR);
        
        PreferenceConverter.setDefault(getPreferenceStore(), 
                PREF_CONNECTION_LABEL_BACKGROUND_COLOR, 
                TRANSPARENCY_COLOR);
        
        
        getPreferenceStore().setDefault(PREF_SHOW_SHADOWS_TRANSPARENCY, 70);
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
     * unless the locale is Japanese in that case, use MS Gothic
     * TODO: use the correct way (symbolic name, font registry ?) if there is one.
     * @param store
     */
    private void internalSetDefaultFontPref(IPreferenceStore store) {
        FontData[] fontDataArray = BpmnDiagramPreferenceInitializer.getDefaultFont();
        PreferenceConverter.setDefault(store,
                IPreferenceConstants.PREF_DEFAULT_FONT, fontDataArray);
    }

    /**
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
        
        PreferenceConverter.setDefault(store, PREF_MSG_LINE_COLOR,
                ColorConstants.black.getRGB());
        PreferenceConverter.setDefault(store, PREF_SEQ_LINE_COLOR,
                ColorConstants.black.getRGB());
        
        store.setDefault(PREF_MSG_LINE_ALPHA, 170);
        store.setDefault(PREF_SEQ_LINE_ALPHA, 150);
    }
    
    /**
     * @return The font to be used by default for bpmn diagrams until the user
     * changes it in the preferences or on a per shape basis.
     * The default font depends on the locale and the OS.
     * <p>
     * It can also be set via a system property:
     * Starting the modeler with the options:
     * -Dbpmn.defaultfont.name="MS Gothic" -Dbpmn.defaultfont.size=10
     * will force the default font to "MS Gothic" size "10".
     * </p>
     */
    public static FontData[] getDefaultFont() {
        if (_defaultFontDataArray != null) {
            return _defaultFontDataArray;
        }
        String fontName = System.getProperty(VM_ARG_DEFAULT_FONT_NAME,
                getDefaultFontNameAccordingToNL());
        int fontSize = 9;
        try {
            fontSize = Integer.parseInt(System.getProperty(
                    VM_ARG_DEFAULT_FONT_SIZE,
                getDefaultFontSizeAccordingToNL(fontName) + "")); //$NON-NLS-1$
        } catch (NumberFormatException e) {
            System.err.println("Unable to parse the size of the font defined as " + //$NON-NLS-1$
                    System.getProperty(
                            VM_ARG_DEFAULT_FONT_SIZE));
        }
        Font f = new Font(JFaceResources.getDefaultFont().getDevice(),
                fontName,
                fontSize, SWT.NONE);
        FontData[] fontDataArray = f != null ? f.getFontData() : JFaceResources
                .getDefaultFont().getFontData();
        // use all the fontData for the Mac OS platform
        for ( FontData fontData : fontDataArray) {
            fontData.setHeight(fontSize);
        }
        //dispose the font.
        f.dispose();
        _defaultFontDataArray = fontDataArray;
        return fontDataArray;
    }
    
   
    private static String getDefaultFontNameAccordingToNL() {
        String res = "Arial"; //$NON-NLS-1$
        try {
            String nl = Platform.getNL();
            if (nl == null) {
                return res;
            }
            //now we could use a resource bundle to define the default font
            //according to the locale and to the OS
            //at this point we are going to do the straight forward
            //if/else as we handle only japanese:
            String OS = Platform.getOS();
            Locale loc = new Locale(nl);
            if (Locale.JAPANESE.equals(loc.getLanguage())) {
                if (OS.equals(Platform.OS_LINUX) || OS.equals(Platform.OS_SOLARIS)) {
                    return "Konchi Gothic"; //$NON-NLS-1$
                } else if (OS.equals(Platform.OS_MACOSX)) {
                    return "Hiragino Kaku Gothic"; //$NON-NLS-1$
                } else {
                    return "MS Gothic"; //$NON-NLS-1$
                }
            }/* else if (OS.equals(Platform.OS_MACOSX)) {
            	return "Lucida Sans"; //$NON-NLS-1$
        	}*/
        } catch (Exception e) {
            System.err.println("Unable to get the default font name according" + //$NON-NLS-1$
                    " to the locale: " + e); //$NON-NLS-1$
        }
        return res;
    }

    private static int getDefaultFontSizeAccordingToNL(String defaultFontName) {
        int res = 9;
        return res;
    }

}
