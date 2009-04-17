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

import org.eclipse.gef.EditDomain;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.gef.ui.palette.DefaultPaletteViewerPreferences;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerPreferences;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.swt.widgets.Composite;

/**
 * This palettes impleemnts a few enhancements over the standard GEF palette.
 * <ul>
 * <li>Sticky tools: double-click on a tool or selecting an already selected tool
 * will set the tool it creates to not unload after use.
 * Clicking a third time will make it back to standard unload after use 
 * behavior.</li>
 * <li>Colors: settable for the background.</li>
 * </ul>
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BpmnPaletteViewer extends PaletteViewer {
    
    /**
     * Quick implementation of a paletteviewerprovider
     * 
     * @param editDomain
     * @return
     */
    public static PaletteViewerProvider getBpmnPaletteViewerProvider(EditDomain editDomain) {
        return new PaletteViewerProvider(editDomain) {
            @Override
            public PaletteViewer createPaletteViewer(Composite parent) {
                BpmnPaletteViewer pViewer = new BpmnPaletteViewer();
                
                //must set the preference early on.
                IPreferenceStore preferenceStore = 
                        BpmnDiagramEditorPlugin.getInstance()
                            .getPreferenceStore();
                
                //[hugues] save the layout default as it will be overriden by 
                //the constructor DefaultPaletteViewerPreferences
                //should we file a bug against GEF to use a preference initializer
                //instead?
                int trueDefaultPrefLayout = preferenceStore.getInt(
                        PaletteViewerPreferences.PREFERENCE_LAYOUT);
                
                DefaultPaletteViewerPreferences prefs =
                     new DefaultPaletteViewerPreferences(preferenceStore);
                
                //restore:
                preferenceStore.setDefault(PaletteViewerPreferences.PREFERENCE_LAYOUT,
                        trueDefaultPrefLayout);
                
                pViewer.setPaletteViewerPreferences(prefs);
                
//                pViewer.setEditDomain(super.getEditDomain());
                pViewer.createControl(parent);
                configurePaletteViewer(pViewer);
                hookPaletteViewer(pViewer);
                return pViewer;
            }
        };
    }
    /**
     * Constructs a new PaletteViewer
     */
    public BpmnPaletteViewer() {
        super();
        super.setEditPartFactory(new BpmnPaletteEditPartFactory());
    }
    
    /**
     * Takes care of the sticky mode and updating the current tool
     * if necessary
     */
    @Override
    public void setActiveTool(ToolEntry newMode) {
        boolean updateUnloadWhenFinished = false;
        if (newMode != null && newMode instanceof IStickableToolEntry) {
            if (newMode == getActiveTool()) {
                if (((IStickableToolEntry)newMode).isSticky()) {
                    //it was selected and in sticky mode.
                    //let's un-select it.
                    ((IStickableToolEntry)newMode).setIsSticky(false);
                    super.setActiveTool(null);
                    return;
                } else {
                    //selected but not sticky-> in sticky mode.
                    ((IStickableToolEntry)newMode).setIsSticky(
                        true);
                }
            } else {
                ((IStickableToolEntry)newMode).setIsSticky(false);
                if (getActiveTool() instanceof IStickableToolEntry) {
                    ((IStickableToolEntry)getActiveTool()).setIsSticky(false);
                }
            }
            updateUnloadWhenFinished = true;
        }
        super.setActiveTool(newMode);
        if (updateUnloadWhenFinished) {
            Tool tool = getEditDomain().getActiveTool();
            if (tool != null && tool instanceof AbstractTool) {
                ((AbstractTool)tool).setUnloadWhenFinished(
                        !((IStickableToolEntry)newMode).isSticky());
            }
        }
    }
    

}
