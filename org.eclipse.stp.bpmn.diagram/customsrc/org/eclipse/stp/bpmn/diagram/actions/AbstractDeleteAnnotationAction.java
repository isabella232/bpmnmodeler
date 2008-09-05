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
package org.eclipse.stp.bpmn.diagram.actions;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.ui.action.AbstractActionHandler;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Abstract class to help creating actions designed to remove annotations
 * from diagram elements.
 * 
 * @author atoulme
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public abstract class AbstractDeleteAnnotationAction extends AbstractActionHandler {

	/**
	 * Creates the action and associates it with the selected page.
	 * @param part
	 */
	 public AbstractDeleteAnnotationAction(IWorkbenchPage page) {
		super(page);
	}
	 
	/**
	 * Creates the action and associates it with the selected editor.
	 * @param part
	 */
	 public AbstractDeleteAnnotationAction(IWorkbenchPart part) {
		super(part);
	}

	private EModelElement selectedElt;
	private IGraphicalEditPart selectedPart;
    
	/**
	 * Runs the deletion of the annotation with the source declared in
	 * the getAnnotationSource() method.
	 */
    public void doRun(IProgressMonitor progressMonitor) {
        if (getSelectedElt() == null) {
            return;//humf
        }
        TransactionalEditingDomain domain = (TransactionalEditingDomain) 
            AdapterFactoryEditingDomain.getEditingDomainFor(getSelectedElt());
        RecordingCommand myCmd = new RecordingCommand(domain) {
            @Override
            protected void doExecute() {
                EAnnotation ea =
                    getSelectedElt().getEAnnotation(getAnnotationSource());
                if (ea != null) {
                    if (getAnnotationDetails() != null) {
	                    for (String detail : getAnnotationDetails()) {
	                        ea.getDetails().remove(detail);
	                    }
                    }
                    if (getAnnotationDetails() == null ||
                            (removeAnnotationWhenEmptyDetails() &&
                                    ea.getDetails().size() == 0)) {
                        getSelectedElt().getEAnnotations().remove(ea);
                    }
                }
                getSelectedPart().refresh();
            }
        };
        domain.getCommandStack().execute(myCmd);	        
    }
	    
    /**
     * {@inheritDoc}
     */
    public void refresh() {
    	ISelection selection = getSelection();
    	if (!(selection instanceof IStructuredSelection)) {
    		setEnabled(false);
    		return;
    	}

    	updateSelectedEModelElement(getSelection());
    	boolean ok = selectedElt != null && selectedPart != null;
    	setEnabled(ok);
    	if (ok) {
    		setText(updateText(selectedElt));
    		setImageDescriptor(updateImage(selectedElt));
    		setDescription(updateDescription(selectedElt));
    		setToolTipText(updateToolTipText(selectedElt));
    	} else {
    		setText(getDefaultLabel());
    		setImageDescriptor(getDefaultImage());
    		setDescription(getDefaultDescription());
    		setToolTipText(getDefaultToolTipText());
    	}

    }
	    
	/**
     * @param elt
     * @return the tooltip text for this action.
     * Clients can override this method.
     */
    protected String updateToolTipText(EModelElement elt) {
		return null;
	}
    /**
     * 
     * @param elt
     * @return the description for the element.
     * Clients can override this method.
     */
	protected String updateDescription(EModelElement elt) {
		return null;
	}

	/**
     * updates the label of the action 
     * @param elt
     * @return the text of the label for the action.
     */
    protected abstract String updateText(EModelElement elt);

    /**
     * Updates the image for the action label.
     * @param elt
     * @return the image for the action label or null.
     */
    protected abstract ImageDescriptor updateImage(EModelElement elt);
    
    /**
     * 
     * Updates The BPMN Model element and the selected edit part if it has an
     * attached annotation with getAnnotationSource() source. null otherwise.
     * May be overridden by clients to force more checks.
     */
    protected void updateSelectedEModelElement(ISelection selection) {
        if (selection != null && selection instanceof IStructuredSelection) {
            if (((IStructuredSelection) selection).getFirstElement() 
            		instanceof IGraphicalEditPart) {
            	IGraphicalEditPart part =
            		(IGraphicalEditPart)
            		((IStructuredSelection) selection).getFirstElement();
            	if (part != null && part.getNotationView() != null) {
            	    if (part.resolveSemanticElement() instanceof EModelElement) {
            	        EModelElement elt = (EModelElement) part.resolveSemanticElement();

            	        if (elt.getEAnnotation(getAnnotationSource()) != null) {
            	            selectedElt = elt;
            	            selectedPart = part;
            	            return;
            	        }
            	    }
            	}
            }
        }
        selectedElt = null;
		selectedPart = null;
    }
	
    /**
     * @return the string representing the source of the annotation.
     */
    protected abstract String getAnnotationSource();
    /**
     * @return the list of the string representing the detail of the annotation
     * to remove or null to remove the entire annotation
     */
    protected abstract List<String> getAnnotationDetails();
    
    /**
     * Default label when no element is selected
     * @return
     */
    protected String getDefaultLabel() {
    	return BpmnDiagramMessages.AbstractDeleteAnnotationAction_default_label;
    }
    
    /**
     * Default tool tip text when no element is selected
     * @return
     */
    protected String getDefaultToolTipText() {
    	return BpmnDiagramMessages.AbstractDeleteAnnotationAction_default_tooltip;
    }
    
    /**
     * Default image when no element is selected.
     * @return
     */
    protected ImageDescriptor getDefaultImage() {
		return null;
	}
    
   /**
    * Default description when no element is selected.
    * @return
    */
    protected String getDefaultDescription() {
		return BpmnDiagramMessages.AbstractDeleteAnnotationAction_default_description;
	}

	/**
	 * @return the selectedElt
	 */
	protected EModelElement getSelectedElt() {
		return selectedElt;
	}

	/**
	 * @param selectedElt the selectedElt to set
	 */
	protected void setSelectedElt(EModelElement selectedElt) {
		this.selectedElt = selectedElt;
	}

	/**
	 * @return the selectedPart
	 */
	public IGraphicalEditPart getSelectedPart() {
		return selectedPart;
	}

	/**
	 * @param selectedPart the selectedPart to set
	 */
	public void setSelectedPart(IGraphicalEditPart selectedPart) {
		this.selectedPart = selectedPart;
	}
	
	/**
	 * Returns true by default. Override this to keep empty annotations.
	 * @return true to remove the annotation when it contains no details
	 */
	protected boolean removeAnnotationWhenEmptyDetails() {
	    return true;
	}
}
