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
package org.eclipse.stp.bpmn.diagram.providers;

import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.emf.ui.providers.marker.AbstractModelMarkerNavigationProvider;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Identifiable;

/**
 * @generated
 */
public class BpmnMarkerNavigationProvider extends
        AbstractModelMarkerNavigationProvider {
    /**
     * @generated
     *
    protected void doGotoMarker(IMarker marker) {
        String elementId = marker.getAttribute(
                org.eclipse.gmf.runtime.common.ui.resources.IMarker.ELEMENT_ID,
                null);
        if (elementId == null || !(getEditor() instanceof DiagramEditor)) {
            return;
        }
        EditPart targetEditPart = null;
        DiagramEditor editor = (DiagramEditor) getEditor();
        Map epartRegistry = editor.getDiagramGraphicalViewer()
                .getEditPartRegistry();
        for (Iterator it = epartRegistry.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getKey() instanceof View) {
                View view = (View) entry.getKey();
                String viewId = ViewUtil.getIdStr(view);
                if (viewId.equals(elementId)) {
                    targetEditPart = (EditPart) entry.getValue();
                    break;
                }
            }
        }
        if (targetEditPart != null) {
            editor.getDiagramGraphicalViewer().select(targetEditPart);
            editor.getDiagramGraphicalViewer().reveal(targetEditPart);
        }
    }
    */
    
    /**
     * @generated NOT: added the bpmnId attribute support.
     */
    protected void doGotoMarker(IMarker marker) {
        String elementId = marker.getAttribute(
                org.eclipse.gmf.runtime.common.ui.resources.IMarker.ELEMENT_ID,
                null);
        String bpmnId = null;
        if (elementId == null) {
            if (!(getEditor() instanceof DiagramEditor)) {
                return;
            }
            bpmnId = marker.getAttribute("bpmnId", (String)null); //$NON-NLS-1$ //$NON-NLS-2$
            if (bpmnId == null || bpmnId.length() == 0) {
                return;
            }
        }
        navigateTo((DiagramEditor)getEditor(), elementId, bpmnId);
        
    }
    
    /**
     * Navigates to the given bpmn shape defined either by its view id either by
     * it bpmn id.
     * @param gmfElementId
     * @param bpmnId
     * @return true if the shape was located false otherwise.
     */
    public static boolean navigateTo(DiagramEditor editor, String elementId, String bpmnId) {
        EditPart targetEditPart = null;
        Map epartRegistry = editor.getDiagramGraphicalViewer()
                .getEditPartRegistry();
        if (elementId != null) {
            for (Iterator it = epartRegistry.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                if (entry.getKey() instanceof View) {
                    View view = (View) entry.getKey();
                    String viewId = ViewUtil.getIdStr(view);
                    if (viewId.equals(elementId)) {
                        targetEditPart = (EditPart) entry.getValue();
                        if (targetEditPart != null) {
                            editor.getDiagramGraphicalViewer().select(targetEditPart);
                            editor.getDiagramGraphicalViewer().reveal(targetEditPart);
                            return true;
                        }
                    }
                }
            }
        }
        if (bpmnId != null) {
            for (Iterator it = epartRegistry.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                if (entry.getKey() instanceof View) {
                    View view = (View) entry.getKey();
                    EObject obj = view.getElement();
                    if (obj instanceof Identifiable) {
                        if (bpmnId.equals(((Identifiable)obj).getID())) {
                            targetEditPart = (EditPart) entry.getValue();
                            if (targetEditPart != null) {
                                editor.getDiagramGraphicalViewer().select(targetEditPart);
                                editor.getDiagramGraphicalViewer().reveal(targetEditPart);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    
    
}
