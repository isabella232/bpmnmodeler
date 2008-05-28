/**
 *  Copyright (C) 2007, Intalio Inc.
 *
 *  The program(s) herein may be used and/or copied only with the
 *  written permission of Intalio Inc. or in accordance with the terms
 *  and conditions stipulated in the agreement/contract under which the
 *  program(s) have been supplied.
 *
 * Date         Author             Changes
 * Apr 23, 2007      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.dnd.file;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.DataObject;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.NamedBpmnObject;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.TextAnnotation;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.dnd.AbstractEAnnotationDnDHandler;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.IWorkbenchAdapter;


/**
 * @author atoulme
 * a simple implementation of IDnDHandler that creates an
 * annotation for a IFile object.
 */
public class FileDnDHandler extends AbstractEAnnotationDnDHandler {

	/**
	 * the file this DnDHandler represents.
	 */
	private IResource file;
	
	public FileDnDHandler(IResource f) {
		file = f;
	}
	
	/**
	 * this method returns a command that will create an annotation
	 * on the semantic element of the edit part passed in parameter.
	 */
	public Command getDropCommand(IGraphicalEditPart hoverPart, int index, 
			Point dropLocation) {
		Map<String, String> details = new HashMap<String, String>();
		details.put(FileDnDConstants.PROJECT_RELATIVE_PATH, 
				file.getProjectRelativePath().toString());
 		return createEAnnotationDropCommand(
				createAnnotation(FileDnDConstants.ANNOTATION_SOURCE, details),
				(EModelElement) hoverPart.resolveSemanticElement());
	}

	/**
	 * the file DnD only enables the drop of one element.
	 */
	public int getItemCount() {
		return 1;
	}

	public Image getMenuItemImage(IGraphicalEditPart hoverPart, int index) {
		IWorkbenchAdapter adapter = (IWorkbenchAdapter) 
	    Platform.getAdapterManager()
	    	.getAdapter(file, IWorkbenchAdapter.class);
	    if (adapter == null) {
	        return PlatformUI.getWorkbench().
	        	getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
	    } else {
	    	synchronized(file) {
	    		if (cachedImages.get(file) == null) {
	    			cachedImages.put(file, adapter.
	    					getImageDescriptor(file).createImage());
	    		}
	    	}
	    	return cachedImages.get(file);
	    }
	}

	public String getMenuItemLabel(IGraphicalEditPart hoverPart, int index) {
		EObject element = hoverPart.resolveSemanticElement();
		String suffix = BpmnDiagramMessages.bind(BpmnDiagramMessages.FileDnDHandler_On, getShapeLabel((NamedBpmnObject) element)); 
		String path = file.getProjectRelativePath().lastSegment().toString();
        return BpmnDiagramMessages.bind(BpmnDiagramMessages.FileDnDHandler_AttachFile, path, suffix); 
	}

	/**
	 * the file drop should have the lowest priority.
	 */
	public int getPriority() {
		return 0;
	}

	
	/**
     * @param namedElement
     * @return a label for a bpmn shape
     */
    protected String getShapeLabel(NamedBpmnObject namedElement) {
        String name = namedElement.getName() != null && 
            namedElement.getName().length() > 0 ? namedElement.getName() : ""; //$NON-NLS-1$
        if (name.length() > 18) {
            name = name.substring(0, 12) + "..."; //$NON-NLS-1$
        }
        if (namedElement instanceof Pool) {
            if (name.indexOf("pool") == -1) { //$NON-NLS-1$
                return "pool " + name; //$NON-NLS-1$
            } else {
                return name;
            }
        } else if (namedElement instanceof Activity) {
            String shape = ((Activity)namedElement).getActivityType().getName();
            if (name.indexOf(shape) == -1) {
                return shape + " " + name; //$NON-NLS-1$
            } else {
                return name;
            }
        } else if (namedElement instanceof MessagingEdge) {
            String shape = "message"; //$NON-NLS-1$
            if (name.indexOf(shape) == -1) {
                return shape + " " + name; //$NON-NLS-1$
            } else {
                return name;
            }
        } else if (namedElement instanceof SequenceEdge) {
            String shape = "sequence"; //$NON-NLS-1$
            if (name.indexOf(shape) == -1) {
                return shape + " " + name; //$NON-NLS-1$
            } else {
                return name;
            }
        } else if (namedElement instanceof Diagram) {
            String shape = "diagram"; //$NON-NLS-1$
            if (name.indexOf(shape) == -1) {
                return shape + " " + name; //$NON-NLS-1$
            } else {
                return name;
            }
        } else if (namedElement instanceof TextAnnotation) {
            String shape = "annotation"; //$NON-NLS-1$
            if (name.indexOf(shape) == -1) {
                return BpmnDiagramMessages.bind(BpmnDiagramMessages.FileDnDHandler_text, shape, name); 
            } else {
                return name;
            }
        } else if (namedElement instanceof DataObject) {
            String shape = "object"; //$NON-NLS-1$
            if (name.indexOf(shape) == -1) {
                return BpmnDiagramMessages.bind(BpmnDiagramMessages.FileDnDHandler_data, shape, name); 
            } else {
                return name;
            }
        }
        return name;
    }

    @Override
    public boolean isEnabled(IGraphicalEditPart hoverPart, int index) {
    	if (super.isEnabled(hoverPart, index)) {
    		EModelElement modelElement = (EModelElement) 
    		hoverPart.resolveSemanticElement();
    		IFile f = WorkspaceSynchronizer.getFile(modelElement.eResource());
    		if (!f.getProject().equals(
    				file.getProject())) {
    			return false;
    		}
    		return true;
    	}
    	return false;
    }
}
