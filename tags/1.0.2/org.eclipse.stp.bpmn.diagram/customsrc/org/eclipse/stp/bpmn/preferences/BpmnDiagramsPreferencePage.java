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

package org.eclipse.stp.bpmn.preferences;

import org.eclipse.gmf.runtime.diagram.ui.preferences.DiagramsPreferencePage;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.ScaleFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Preference page. Follow the tutorial here:
 * <link>http://help.eclipse.org/help32/topic/org.eclipse.gmf.doc/tutorials/diagram/diagramPreferencesTutorial.html</link>
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BpmnDiagramsPreferencePage extends DiagramsPreferencePage {

    /** the preference key used to store the default value for the
     * delay of the connection assistant */
    private static String PREF_CONN_DIAG_ASSISTANT_DELAY_MS =
        BpmnDiagramPreferenceInitializer.PREF_CONN_DIAG_ASSISTANT_DELAY_MS;

    /** the preference key used to store the default value to enable snap-to-geometry*/
    private static String PREF_SNAP_TO_GEOMETRY =
        BpmnDiagramPreferenceInitializer.PREF_SNAP_TO_GEOMETRY;

    public BpmnDiagramsPreferencePage() {
        super();
        setPreferenceStore(BpmnDiagramEditorPlugin.getInstance().getPreferenceStore());
    }
    
    
    /* (non-Javadoc)
     * @see org.eclipse.gmf.runtime.common.ui.preferences.AbstractPreferencePage#addFields(org.eclipse.swt.widgets.Composite)
     */
    protected void addFields(Composite parent) {
        
        Group bpmnFirstSettings = new Group(parent, SWT.NONE);
        GridLayout gridLayout = new GridLayout(1, false);
        bpmnFirstSettings.setLayout(gridLayout);
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.grabExcessHorizontalSpace = true;
        bpmnFirstSettings.setLayoutData(gridData);
        bpmnFirstSettings.setText(BpmnDiagramMessages.BpmnDiagramsPreferencePage_diagram_creation_group_title);
        addField(new StringFieldEditor(
                BpmnDiagramPreferenceInitializer.PREF_AUTHOR,
                BpmnDiagramMessages.BpmnDiagramsPreferencePage_author, bpmnFirstSettings));
        
        
        super.addFields(parent);
        
        Group bpmnGlobalGroup = new Group(parent, SWT.NONE);
        gridLayout = new GridLayout(2, false);
        bpmnGlobalGroup.setLayout(gridLayout);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.grabExcessHorizontalSpace = true;
        gridData.horizontalSpan = 2;
        bpmnGlobalGroup.setLayoutData(gridData);
        bpmnGlobalGroup.setText(BpmnDiagramMessages.BpmnDiagramsPreferencePage_other_settings_group_title);

        BooleanFieldEditor snapGeomEd = new BooleanFieldEditor(
                PREF_SNAP_TO_GEOMETRY,
                BpmnDiagramMessages.BpmnDiagramsPreferencePage_snap_to_geometry_label,
                bpmnGlobalGroup);
        super.addField(snapGeomEd);
        BooleanFieldEditor collapseStyle = new BooleanFieldEditor(
                BpmnDiagramPreferenceInitializer.PREF_SP_COLLAPSE_STYLE,
                BpmnDiagramMessages.BpmnDiagramsPreferencePage_collapse_expand_policy_label,
                bpmnGlobalGroup);
        super.addField(collapseStyle);
            
        IntegerFieldEditor delayEd = new IntegerFieldEditor(
                PREF_CONN_DIAG_ASSISTANT_DELAY_MS,
                BpmnDiagramMessages.BpmnDiagramsPreferencePage_connection_assistant_appearance_delay_label,
                bpmnGlobalGroup);
        delayEd.setErrorMessage(BpmnDiagramMessages.BpmnDiagramsPreferencePage_connection_assistant_appareance_delay_error_message);
        delayEd.setValidRange(0, 20000);
        super.addField(delayEd);
        
        addField(new BooleanFieldEditor(BpmnDiagramPreferenceInitializer.PREF_BPMN1_1_STYLE,
                BpmnDiagramMessages.BpmnDiagramsPreferencePage_bpmn_style, bpmnGlobalGroup));
        
        
        ScaleFieldEditor shadowsTransparency = new ScaleFieldEditor(
                BpmnDiagramPreferenceInitializer.PREF_SHOW_SHADOWS_TRANSPARENCY, BpmnDiagramMessages.BpmnDiagramsPreferencePage_shadow_label,
                bpmnGlobalGroup, 0, 255, 1, 5);
        shadowsTransparency.setPreferenceStore(super.getPreferenceStore());
        super.addField(shadowsTransparency);
    }
    
}
