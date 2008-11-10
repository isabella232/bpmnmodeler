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
package org.eclipse.stp.bpmn.dnd.file;

import java.util.List;
import java.util.Map;

import org.eclipse.core.internal.runtime.AdapterManager;
import org.eclipse.core.internal.runtime.IAdapterFactoryExt;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget.Direction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.IWorkbenchAdapter;

/**
 * The decorator for the file link decoration.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class FileEAnnotationDecorator implements IEAnnotationDecorator {

	/** we call the loading of the factories that adapt to an ImageRegistry only once. */
	private static boolean ADAPTER_FACTORIES_WERE_LOADED = false;
	
	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator#getAssociatedAnnotationSource()
	 */
	public String getAssociatedAnnotationSource() {
		return FileDnDConstants.ANNOTATION_SOURCE;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator#getDirection(org.eclipse.gef.EditPart, org.eclipse.emf.ecore.EModelElement, org.eclipse.emf.ecore.EAnnotation)
	 */
	public Direction getDirection(EditPart part, EModelElement elt,
			EAnnotation ann) {
		return Direction.SOUTH_WEST;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator#getImage(org.eclipse.gef.EditPart, org.eclipse.emf.ecore.EModelElement, org.eclipse.emf.ecore.EAnnotation)
	 */
	public ImageDescriptor getImageDescriptor(EditPart part, EModelElement element,
			EAnnotation annotation) {
		if (element == null) {
			return PlatformUI.getWorkbench().
				getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
		}
		String filePath = (String) annotation.getDetails().
		    get(FileDnDConstants.PROJECT_RELATIVE_PATH);
		if (filePath == null) {
		    return null;
        }
		IProject currentProject = WorkspaceSynchronizer
								.getFile(element.eResource()).getProject();
		IResource ourResource = currentProject.findMember(filePath);
		if (ourResource == null) {
			ourResource = currentProject.getFile(filePath);
		}
		//trick: sometimes (for example for folders) we want to 
		//override the icon displayed by default.
		//we could have defined our own extension point
		//but reusing an ImageRegistry does the trick well enough
		if (!ADAPTER_FACTORIES_WERE_LOADED) {
			ADAPTER_FACTORIES_WERE_LOADED = true;
			forceLoadAdapterFactoryForResourceToImageRegistry();
		}
		ImageRegistry customizer = (ImageRegistry)
			Platform.getAdapterManager()
                    .getAdapter(ourResource, ImageRegistry.class);
		if (customizer != null) {
			return customizer.getDescriptor(ourResource.getFileExtension());
		}
		IWorkbenchAdapter adapter = (IWorkbenchAdapter) 
		    Platform.getAdapterManager()
                    .getAdapter(ourResource, IWorkbenchAdapter.class);
		if (adapter == null) {
			return PlatformUI.getWorkbench().
				getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJ_FILE);
		} else {
			return adapter.getImageDescriptor(ourResource);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator#getToolTip(org.eclipse.gef.EditPart, org.eclipse.emf.ecore.EModelElement, org.eclipse.emf.ecore.EAnnotation)
	 */
	public IFigure getToolTip(EditPart part, EModelElement element,
			EAnnotation annotation) {
		return new Label(annotation.getDetails().
				get(FileDnDConstants.PROJECT_RELATIVE_PATH).toString());
	}
    
	/**
	 * Access Eclipse Internal APIs to force the loading of the AdapterFactories
	 * necessary to display the images properly: the one that can adapt an
	 * IResource into an ImageRegistry.
	 */
	@SuppressWarnings({ "unchecked", "restriction" })
	private void forceLoadAdapterFactoryForResourceToImageRegistry() {
        // we take all the adapter factories outputs
        Map factories = ((AdapterManager)
                Platform.getAdapterManager()).getFactories();
        Class[] cl = ((AdapterManager) Platform.getAdapterManager()).
        	computeClassOrder(IResource.class);
        for (Class c : cl) {
            List clfactories = (List) factories.get(c.getName());
            if (clfactories != null) {
                for (Object f : clfactories) {
                    if (f instanceof IAdapterFactoryExt) {
                    	String[] adapterNames = ((IAdapterFactoryExt)f).getAdapterNames();
                        if (adapterNames != null) {
                    		for (String adapterName : adapterNames) {
                    			if ("org.eclipse.jface.resource.ImageRegistry".equals(adapterName)
                    					|| "org.eclipse.ui.model.IWorkbenchAdapter".equals(adapterName)) {
                                    try {
                                        ((IAdapterFactoryExt)f).loadFactory(true);
                                    } catch(Exception e) {
                                        System.err.println("Unable to load a Adapterfactory for ImageRegistry factory: " + e.getMessage() //$NON-NLS-1$
                                                + " for " + c.getName()); //$NON-NLS-1$
                                        //e.printStackTrace(); // loading the factories must happen.
                                    }
                                    break;
                    			}
                    		}
                    	}
                    }
                }
            }
        }
	}
    
}
