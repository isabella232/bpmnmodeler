/******************************************************************************
 * Copyright (c) 2006-2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Dates       		 Author              Changes
 * July 06, 2007      Hugues Malphettes   Creation
 */
package org.eclipse.stp.bpmn.diagram.actions.associationdirectiontypes;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.ui.action.AbstractActionHandler;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.DataObject;
import org.eclipse.stp.bpmn.DirectionType;
import org.eclipse.stp.bpmn.Group;
import org.eclipse.stp.bpmn.TextAnnotation;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.ui.IWorkbenchPage;

/**
 * Abstract action to change the activity type of the selected
 * object into the one specified by the action.
 * @author <a href="mailto:hmalphettes@intalio.com">Hugues Malphettes</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ChangeAssociationDirectionTypeAction extends AbstractActionHandler {

	/**
	 * The abstract constant which is completed by the name of the type
	 */
	public static final String ABSTRACT_ID = "setAssociationDirectionTypeTo" ; //$NON-NLS-1$
	/**
	 * the type that this action represents.
	 */
	private DirectionType _type;
	
	/**
	 * Default constructor
	 * @param workbenchPage
	 * @param name the type of direction that will be presented by this action.
	 */
	public ChangeAssociationDirectionTypeAction(IWorkbenchPage workbenchPage, String name) {
		super(workbenchPage);
		_type = DirectionType.get(name.substring(ABSTRACT_ID.length()));
		if (_type == null) {
		    _type = DirectionType.NONE_LITERAL;
		}
	}
	
	/**
	 * Initializes the action.
	 */
	@Override
	public void init() {
	    super.init();
		if (getDirectionType() == null) {
		    return;
		}
		setId(ABSTRACT_ID + getDirectionType().getLiteral());
		Association assoc = getAssociationSelected();
		if (assoc == null ||
		        getDirectionType().getValue() == DirectionType.NONE) {
		    setText(getDirectionType().getName());
		} else {
		    if (getDirectionType().getValue() == DirectionType.BOTH) {
		        setText(BpmnDiagramMessages.ChangeAssociationDirectionTypeAction_both_directions);
		    } else {
                String sourceL = BpmnDiagramMessages.ChangeAssociationDirectionTypeAction_artifact;
                if (assoc.getSource() instanceof DataObject) {
                    sourceL = BpmnDiagramMessages.ChangeAssociationDirectionTypeAction_data_object;
                } else if (assoc.getSource() instanceof TextAnnotation) {
                    sourceL = BpmnDiagramMessages.ChangeAssociationDirectionTypeAction_text_annotation;
                } else if (assoc.getSource() instanceof Group) {
                    sourceL = BpmnDiagramMessages.ChangeAssociationDirectionTypeAction_group;
                }
                String targetL = BpmnDiagramMessages.ChangeAssociationDirectionTypeAction_shape;
                if (assoc.getTarget() instanceof Activity) {
                    targetL = ((Activity) assoc.getTarget())
                        .getActivityType().getName();
                }
		        if (getDirectionType().getValue() == DirectionType.FROM) {
		            setText(BpmnDiagramMessages.bind(BpmnDiagramMessages.ChangeAssociationDirectionTypeAction_from_to, sourceL, targetL)); 
		        } else {
                    setText(BpmnDiagramMessages.bind(BpmnDiagramMessages.ChangeAssociationDirectionTypeAction_from_to, targetL, sourceL));        
		        }
		    }
		}
//		try {
//		    setImageDescriptor(ImageDescriptor.createFromURL(
//				(URL) new ActivityItemProvider(null).getImage(getDirectionType())));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	/**
	 * Runs the action through a command.
	 */
	@Override
	protected void doRun(IProgressMonitor progressMonitor) {
        final Association assoc = getAssociationSelected();
        if (assoc == null) {
            return;
        }
        TransactionalEditingDomain domain = (TransactionalEditingDomain) 
            AdapterFactoryEditingDomain.getEditingDomainFor(assoc);
        RecordingCommand myCmd = new RecordingCommand(domain) {
            @Override
            protected void doExecute() {
                assoc.setDirection(getDirectionType());
                ConnectionEditPart gp = getGraphicalPartSelected();
                if (gp != null) {
                    gp.refresh();
                }
            }
        };
        domain.getCommandStack().execute(myCmd);
	}
	
	/**
	 * Introspects the selection and extracts the Association
	 * objects if present
	 * @return the association object
	 */
	protected Association getAssociationSelected() {
       IStructuredSelection selection = getStructuredSelection();
        if (selection == null || (selection.isEmpty())) {
            return null;
        }
        if (selection.getFirstElement() instanceof IGraphicalEditPart) {
            final EObject object = ((IGraphicalEditPart) selection.
                    getFirstElement()).resolveSemanticElement();
            if (object instanceof Association) {
                return (Association)object;
            }
        }
	    return null;
	}
	
	/**
	 * @return the ConnectionEditPart first element of the
	 * selection, or null.
	 */
	protected ConnectionEditPart getGraphicalPartSelected() {
	    IStructuredSelection selection = getStructuredSelection();
        if (selection == null || (selection.isEmpty())) {
            return null;
        }
        if (selection.getFirstElement() instanceof ConnectionEditPart) {
            return (ConnectionEditPart)selection.getFirstElement();
        }
        return null;
	}

	/**
	 * Refreshing the action, setting it enabled if the selection is an Association
	 * and the direction type is different of its association type.
	 */
	public void refresh() {
		boolean forbidEnablement = false;
		if (getSelection() == null|| getSelection().isEmpty()) {
			forbidEnablement = true;
		}
		if (!(getSelection() instanceof IStructuredSelection)) {
			forbidEnablement = true;
		}
		if (forbidEnablement) {
			setEnabled(false);
			return;
		}
		Object selected = ((IStructuredSelection) getSelection()).
			getFirstElement();
		if (selected instanceof IGraphicalEditPart) {
			selected = ((IGraphicalEditPart) selected).
				getNotationView().getElement();
		}
		if (!(selected instanceof Association)) {
			forbidEnablement = true;
		}
		if (forbidEnablement) {
            setEnabled(false);
            return;
        }
        Association assoc = (Association)selected;
        if (assoc.getDirection() == getDirectionType()) {
            //no need to select something that won't make a difference.
            forbidEnablement = true;
        }
		setEnabled(!forbidEnablement);
	} 

	/**
	 * @return the direction type associated with this
	 * action.
	 */
	public DirectionType getDirectionType() {
		return _type;
	}
}
