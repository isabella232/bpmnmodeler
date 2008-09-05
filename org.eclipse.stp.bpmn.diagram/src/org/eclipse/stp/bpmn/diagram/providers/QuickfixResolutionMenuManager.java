/******************************************************************************
 * Copyright (c) 2006-2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Dates       		 Author              Changes
 * July 21, 2008     Hugues Malphettes   Creation
 */
package org.eclipse.stp.bpmn.diagram.providers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gmf.runtime.common.ui.action.ActionMenuManager;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.stp.bpmn.Identifiable;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.MarkerResolutionSelectionDialog;
import org.eclipse.ui.ide.IDE;

/**
 * This menu manager is used for building the quickfixes menu.
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class QuickfixResolutionMenuManager implements IMenuListener {

    /** Menu ID */
    public static final String ID = "Quickfixes"; //$NON-NLS-1$
	
    /** this key points to the quickfix image (the bulb) defined in org.eclipse.ui.ide */
    private static final String IMG_ELCL_QUICK_FIX_ENABLED = "IMG_ELCL_QUICK_FIX_ENABLED"; //$NON-NLS-1$;
    
	private final GraphicalViewer _graphicalViewer;
	private IFile _diagramFile;//resource on which the markers are stored.
	
	/**
	 * Creates the object in charge of generating the quickfix submenu
	 * according to the selected shapes in the diagram.
	 * @param graphicalViewer
	 */
	public QuickfixResolutionMenuManager(GraphicalViewer graphicalViewer) {
	    _graphicalViewer = graphicalViewer;
	}
	
	/**
	 * Helper method.
	 * @return The bpmn_diagram file on which the markers are indexed.
	 */
	private IFile getFile() {
	    if (_diagramFile != null) {
	        return _diagramFile;
	    }
	    _diagramFile = WorkspaceSynchronizer.getFile(
	               ((EObject)_graphicalViewer.getRootEditPart()
	                           .getContents().getModel()).eResource());
	    return _diagramFile;
	}
	
	/**
	 * Creates the Quickfixes menu items.
	 * According to the markers on the diagram file
	 * if they are associated to the selected edit parts.
	 * <p>
	 * There are 2 ways to associate a marker to a shape in the diagram.
	 * The marker must extend the type BpmnValidationDecoratorProvider.MARKER_TYPE
	 * which is the type of the problem markers displayed on bpmn diagrams.
	 * </p>
	 * <p>
	 * Then the marker must have an "elementID" attribute which the id
	 * of the GMF'View element
	 * or the marker must have a "bpmnID" attribute which the id of the BPMN's
	 * Identifiable semantic object.
	 * </p>
	 * <p>
	 * To build the menut-items, the IMarkerHelpRegistry is queried.
	 * This one locates the appropriate IMarkerResolution via the standard
	 * eclipse extension point.
	 * </p>
	 */
    public void menuAboutToShow(IMenuManager manager) {
        
        Map<String,Identifiable> selectedBPmnIDs = null; 
        Map<String,View> selectedViewIDs = null; 
        
        for (Object ep : _graphicalViewer.getSelectedEditParts()) {
            
            if (ep instanceof GraphicalEditPart) {
                GraphicalEditPart gep = (GraphicalEditPart)ep;
                Identifiable identifiable = (Identifiable)gep.getAdapter(Identifiable.class);
                if (identifiable != null) {
                    if (selectedBPmnIDs == null) {
                        selectedBPmnIDs = new HashMap<String,Identifiable>();
                    }
                    selectedBPmnIDs.put(identifiable.getID(), identifiable);
                }
                View view = (View)gep.getAdapter(View.class);
                if (view != null) {
                    if (selectedViewIDs == null) {
                        selectedViewIDs = new HashMap<String,View>();
                    }
                    selectedViewIDs.put(ViewUtil.getIdStr(view), view);
                }
            }
        }
        if (selectedBPmnIDs == null && selectedViewIDs == null) {
            return;
        }
        
        //see if we can adapt this editpart into a marker.
        Map<String,List<IMarker>> markerIndex = null;
        LinkedHashSet<IMarker> allMarkers = null;
        try {
            IMarker[] markers = getFile().findMarkers(
                    BpmnValidationDecoratorProvider.MARKER_TYPE, true,
                    IResource.DEPTH_INFINITE);
            if (markers == null || markers.length == 0) {
                return;
            }
            for (IMarker m : markers) {
                //has resolution?
                if (!IDE.getMarkerHelpRegistry().hasResolutions(m)) {
                   continue;
                }
                String elemId = (String) m.getAttribute(
                  BpmnValidationDecoratorProvider.ELEMENT_ID);
                String bpmnId = (String) m.getAttribute(
                    BpmnValidationDecoratorProvider.BPMN_ID);
                if (bpmnId != null && selectedBPmnIDs != null && selectedBPmnIDs.containsKey(bpmnId)) {
                    if (markerIndex == null) {
                        markerIndex = new HashMap<String,List<IMarker>>();
                    }
                    if (allMarkers == null) {
                        allMarkers = new LinkedHashSet<IMarker>();
                    }
                    List<IMarker> marks = markerIndex.get(bpmnId);
                    if (marks == null) {
                        marks = new ArrayList<IMarker>();
                        markerIndex.put(bpmnId, marks);
                    }
                    marks.add(m);
                    allMarkers.add(m);
                }
                if (elemId != null && selectedViewIDs != null && selectedViewIDs.containsKey(elemId)) {
                    if (markerIndex == null) {
                        markerIndex = new HashMap<String,List<IMarker>>();
                    }
                    if (allMarkers == null) {
                        allMarkers = new LinkedHashSet<IMarker>();
                    }
                    List<IMarker> marks = markerIndex.get(elemId);
                    if (marks == null) {
                        marks = new ArrayList<IMarker>();
                        markerIndex.put(elemId, marks);
                    }
                    marks.add(m);
                    allMarkers.add(m);
                }
            }
            if (markerIndex == null) {
                return;
            }
        } catch (CoreException e) {
            BpmnDiagramEditorPlugin.getInstance().logError(
                    "Matching markers to shapes failure", e); //$NON-NLS-1$
            return;
        }
        
        final LinkedHashSet<IMarker> allTheMarkers = allMarkers; 
        
        //the action that is used for the quickfixes submenu itself.
        //unfortunately, it seems clicking on the sub-menu does not call the run() method
        //not inside a context menu.
        Action quikcfixMenuAction = new Action(
                BpmnDiagramMessages.QuickfixResolutionMenuManager_menuTitle,
                PlatformUI.getWorkbench().getSharedImages()
                            .getImageDescriptor(IMG_ELCL_QUICK_FIX_ENABLED)) {
                @Override
                public void run() {
                    //this seems to never be called;
                    System.err.println("doRunAllQUickfixes");
                    
                    ArrayList<IMarkerResolution> resolutions = new ArrayList<IMarkerResolution>(); 
                    for (final IMarker marker : allTheMarkers) {
                        for (IMarkerResolution r : IDE.getMarkerHelpRegistry().getResolutions(marker)) {
                            resolutions.add(r);
                        }
                    }
                    MarkerResolutionSelectionDialog resolutionDialog =
                        new MarkerResolutionSelectionDialog(
                                _graphicalViewer.getControl().getShell(),
                                resolutions.toArray(new IMarkerResolution[resolutions.size()]));
                    resolutionDialog.open();
                }
            };
        
        final ActionMenuManager subMenu = new ActionMenuManager(ID, quikcfixMenuAction, true);
        subMenu.setParent(manager);
        manager.insertBefore("formatMenu", subMenu); //$NON-NLS-1$
        
        
        for (final IMarker marker : allMarkers) {
            IMarkerResolution[] resolutions = IDE.getMarkerHelpRegistry().getResolutions(marker);
            for (final IMarkerResolution resol : resolutions) {
                ImageDescriptor desc = null;
                if (resol instanceof IMarkerResolution2) {
                    IMarkerResolution2 resol2 = (IMarkerResolution2)resol;
                    if (resol2.getImage() != null) {
                        desc = new ImageDescriptorForImage(resol2.getImage());
                    }
                }
                if (desc == null) {
                    desc = new ImageDescriptorForImage(BpmnValidationDecoratorProvider.getProblemImage(
                            marker.getAttribute(IMarker.SEVERITY, IMarker.SEVERITY_INFO), true));
                }
                Action resolve = new Action(resol.getLabel(), desc) {
                    //"Resolution for: " + marker.getAttribute(IMarker.MESSAGE, ""),
                    @Override
                    public void run() {
                        resol.run(marker);
                    }
                };
                subMenu.add(resolve);
            }
        }
    }

}
/**
 * Wrap an Image in an ImageDescriptor 
 */
class ImageDescriptorForImage extends ImageDescriptor {
      private Image _img;
      public ImageDescriptorForImage(Image image) {
          _img = image;
      }
      public boolean equals(Object obj) {
        return obj instanceof ImageDescriptorForImage && 
            ((ImageDescriptorForImage)obj).hashCode() == this.hashCode();
      }
      public int hashCode() {
        return _img.hashCode();
      }
      public ImageData getImageData() {
        return _img.getImageData();
      }
}