/******************************************************************************
* Copyright (c) 2008, Intalio Inc.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
* 
* Contributors:
*     Intalio Inc. - initial API and implementation
*******************************************************************************/
package org.eclipse.stp.bpmn.validation.quickfixes;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.stp.bpmn.Identifiable;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditor;
import org.eclipse.stp.bpmn.validation.providers.HeadlessBpmnValidationProvider;
import org.eclipse.stp.bpmn.validation.quickfixes.internal.BpmnQuickfixes;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.views.markers.WorkbenchMarkerResolution;

/**
 * Support the grouping of the resolution with other related markers.
 * Provide some convenience methods.
 * 
 * @author hmalphettes
 * @author Intalio Inc
 */
public abstract class AbstractBpmnMarkerResolution extends WorkbenchMarkerResolution {
    
    /**
     * Returns optional additional information about the resolution.
     * The additional information will be presented to assist the user
     * in deciding if the selected proposal is the desired choice.
     *
     * @return the additional information or <code>null</code>
     */
    public abstract String getDescription();
    
    /**
     * Returns the image to be displayed in the list of resolutions.
     * The image would typically be shown to the left of the display string.
     *
     * @return the image to be shown or <code>null</code> if no image is desired
     */
    public abstract Image getImage();
    
    /** 
     * Returns a short label indicating what the resolution will do. 
     * 
     * @return a short label for this resolution
     */
    public abstract String getLabel();
    
    /**
     * Runs this resolution. Resolve all <code>markers</code>.
     * <code>markers</code> must be a subset of the markers returned
     * by <code>findOtherMarkers(IMarker[])</code>.
     * 
     * @param markers The markers to resolve, not null
     * @param monitor The monitor to report progress
     */
    public void run(IMarker[] markers, IProgressMonitor monitor) {
        for (int i = 0; i < markers.length; i++) {
            monitor.subTask(markers[i].getAttribute(IMarker.MESSAGE, ""));//$NON-NLS-1$
            run(markers[i], monitor);
        }
    }
    
    public void run(IMarker marker) {
        run(marker, new NullProgressMonitor());
    }
    
    /**
     * Iterate through the list of supplied markers. Return any that can also have
     * the receiver applied to them.
     * @param markers
     * @return IMarker[]
     *   
     * */
    public IMarker[] findOtherMarkers(IMarker[] markers) {
        return BpmnQuickfixes.findOtherMarkers(markers, this);
    }
    
    /**
     * @return The id of the affected bpmn object or null.
     */
    protected String getBpmnId(IMarker marker) {
        return (String)marker.getAttribute("bpmnId", (String)null); //$NON-NLS-1$
    }
    /**
     * @return The id of the affected gmf notation view object or null.
     */
    protected String getElementId(IMarker marker) {
        return (String)marker.getAttribute(
        		HeadlessBpmnValidationProvider.ELEMENT_ID,
                (String)null);
    }
    
    public abstract void run(IMarker marker, IProgressMonitor monitor);
    
    public BpmnDiagramEditor openEditor(IMarker marker, IProgressMonitor monitor) throws PartInitException {
        if (marker.getResource() == null || marker.getResource().getType() != IResource.FILE) {
            return null;
        }
        String fileExt = marker.getResource().getFileExtension();
        if (fileExt == null || !fileExt.endsWith("_diagram")) { //$NON-NLS-1$
            return null;
        }
        IEditorPart ep = IDE.openEditor(
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage(),
                (IFile)marker.getResource());
        if (ep == null || !(ep instanceof BpmnDiagramEditor)) {
            return null;
        }
        BpmnDiagramEditor ed = (BpmnDiagramEditor)ep;
        return ed;
    }
    
    /**
     * Open the corresponding bpmn editor and select the shape.
     * @param marker
     * @param monitor
     * @return The bpmn object or null if it could not be located.
     * @throws PartInitException 
     */
    protected Identifiable navigateToBpmnShape(BpmnDiagramEditor ed, IMarker marker, IProgressMonitor monitor) throws PartInitException {
        if (ed.navigateTo(getElementId(marker), getBpmnId(marker))) {
            ISelection sel = ed.getEditorSite().getSelectionProvider().getSelection();
            if (sel != null && sel instanceof IStructuredSelection && !sel.isEmpty()) {
                Object selected = ((IStructuredSelection)sel).getFirstElement();
                if (selected instanceof IAdaptable) {
                    return (Identifiable)((IAdaptable)selected)
                                .getAdapter(Identifiable.class);
                }
            }
        }
       return null;
    }

    
}
