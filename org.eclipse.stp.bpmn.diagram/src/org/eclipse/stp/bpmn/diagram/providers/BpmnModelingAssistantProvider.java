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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.ModelingAssistantProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.commands.ElementTypeEx;
import org.eclipse.stp.bpmn.commands.IElementTypeEx;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.Activity2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnDiagramEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBorderCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditDomain;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.tools.ConnectionValidator;
import org.eclipse.stp.bpmn.tools.EdgeConnectionValidator;
import org.eclipse.stp.bpmn.tools.MessageConnectionValidator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * @generated
 */
@SuppressWarnings("unchecked") //$NON-NLS-1$
public class BpmnModelingAssistantProvider extends ModelingAssistantProvider {

    /**
     * @generated
     */
    public List getTypesForPopupBarGen(IAdaptable host) {
        IGraphicalEditPart editPart = (IGraphicalEditPart) host
                .getAdapter(IGraphicalEditPart.class);
        if (editPart instanceof SubProcessEditPart) {
            List types = new ArrayList();
            types.add(BpmnElementTypes.Activity_2003);
            return types;
        }
        if (editPart instanceof PoolPoolCompartmentEditPart) {
            List types = new ArrayList();
            types.add(BpmnElementTypes.Activity_2001);
            types.add(BpmnElementTypes.SubProcess_2002);
            types.add(BpmnElementTypes.Lane_2007);
            types.add(BpmnElementTypes.TextAnnotation_2004);
            types.add(BpmnElementTypes.DataObject_2005);
            types.add(BpmnElementTypes.Group_2006);
            return types;
        }
        if (editPart instanceof SubProcessSubProcessBodyCompartmentEditPart) {
            List types = new ArrayList();
            types.add(BpmnElementTypes.Activity_2001);
            types.add(BpmnElementTypes.SubProcess_2002);
            types.add(BpmnElementTypes.TextAnnotation_2004);
            types.add(BpmnElementTypes.DataObject_2005);
            types.add(BpmnElementTypes.Group_2006);
            return types;
        }
        if (editPart instanceof BpmnDiagramEditPart) {
            List types = new ArrayList();
            types.add(BpmnElementTypes.Pool_1001);
            types.add(BpmnElementTypes.TextAnnotation_1002);
            types.add(BpmnElementTypes.DataObject_1003);
            types.add(BpmnElementTypes.Group_1004);
            return types;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Make sure we don't do add things to the subprocesseditpart. we only we
     * want to add to its compartment.
     * 
     * @generated NOT
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
    public List getTypesForPopupBar(IAdaptable host) {
        IGraphicalEditPart editPart = (IGraphicalEditPart) host
                .getAdapter(IGraphicalEditPart.class);
        /*
         * Hugues: nope. if (editPart instanceof SubProcessEditPart) { List
         * types = new ArrayList(); types.add(BpmnElementTypes.Activity_2003);
         * return types; }
         * For some reason it is what gets generated. is there a mistake in the mapping file?
         */
        // added by hugues
        if (editPart instanceof SubProcessSubProcessBorderCompartmentEditPart) {
            List types = new ArrayList();
            types.addAll(BPMNElementTypesActivities
                    .getElementTypesForSubProcessBorder());
            types = filterElementTypes(host, types);
            return types;
        }
        // -- added by hugues.
        if (editPart instanceof PoolPoolCompartmentEditPart) {
            List types = new ArrayList();
            types.addAll(BPMNElementTypesActivities
                    .getElementTypesForPoolAndSubProcessBody());
            types.add(BpmnElementTypes.Lane_2007);
            types.add(BpmnElementTypes.TextAnnotation_2004);
            types.add(BpmnElementTypes.DataObject_2005);
            types.add(BpmnElementTypes.Group_2006);
            types = filterElementTypes(host, types);
            return types;
        }
        if (editPart instanceof SubProcessSubProcessBodyCompartmentEditPart) {
            List types = new ArrayList();
            types.addAll(BPMNElementTypesActivities
                    .getElementTypesForPoolAndSubProcessBody());
            types.add(BpmnElementTypes.TextAnnotation_2004);
            types.add(BpmnElementTypes.DataObject_2005);
            types.add(BpmnElementTypes.Group_2006);
            types = filterElementTypes(host, types);
            return types;
        }
        if (editPart instanceof BpmnDiagramEditPart) {
            List types = new ArrayList();
            types.add(BpmnElementTypes.Pool_1001);
            types.add(BpmnElementTypes.TextAnnotation_1002);
            types.add(BpmnElementTypes.DataObject_1003);
            types.add(BpmnElementTypes.Group_1004);
            types = filterElementTypes(host, types);
            return types;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated NOT
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
    public List getRelTypesOnSource(IAdaptable source) {
       // System.err.println("getRelTypesOnSource source=" + source);
        IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source
                .getAdapter(IGraphicalEditPart.class);
        if (sourceEditPart instanceof ActivityEditPart) {
            ActivityType actype = ((Activity) sourceEditPart.
                    resolveSemanticElement()).getActivityType();
            List types = new ArrayList();
            switch (actype.getValue()) {
            case ActivityType.EVENT_END_COMPENSATION:
            case ActivityType.EVENT_END_EMPTY:
            case ActivityType.EVENT_END_ERROR:
            case ActivityType.EVENT_END_MESSAGE:
            case ActivityType.EVENT_END_TERMINATE:
            case ActivityType.EVENT_END_CANCEL:
            case ActivityType.EVENT_END_LINK:
            case ActivityType.EVENT_END_MULTIPLE:
                break;
            default :
                types.add(BpmnElementTypes.SequenceEdge_3001);
            }
            
//          message connection can't start from gateways & start events
            switch (actype.getValue()) {
            case ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE:
            case ActivityType.GATEWAY_DATA_BASED_INCLUSIVE:
            case ActivityType.GATEWAY_EVENT_BASED_EXCLUSIVE:
            case ActivityType.GATEWAY_PARALLEL:
            case ActivityType.GATEWAY_COMPLEX:
            case ActivityType.EVENT_START_EMPTY:
            case ActivityType.EVENT_START_MESSAGE:
            case ActivityType.EVENT_START_RULE:
            case ActivityType.EVENT_START_LINK:
            case ActivityType.EVENT_START_MULTIPLE:
            case ActivityType.EVENT_START_TIMER:
                break;
            default:
                types.add(BpmnElementTypes.MessagingEdge_3002);
            }
            types = filterElementTypes(source, types);
            return types;
        }
        if (sourceEditPart instanceof Activity2EditPart) {
            List types = new ArrayList();
            types.add(BpmnElementTypes.SequenceEdge_3001);
            types = filterElementTypes(source, types);
            return types;
        }
        if (sourceEditPart instanceof SubProcessEditPart) {
            List types = new ArrayList();
            types.add(BpmnElementTypes.SequenceEdge_3001);
            types = filterElementTypes(source, types);
            return types;
        }
        if (isEditPartArtifact(sourceEditPart)) {
        	List types = new ArrayList();
			types.add(BpmnElementTypes.Association_3003);
			types = filterElementTypes(source, types);
			return types;
		}
        return Collections.EMPTY_LIST;
    }
    
    /**
     * @param editPart the edit part to evaluate.
     * @return true if the edit part represents an artifact, 
     * false otherwise
     */
    public static boolean isEditPartArtifact(EditPart editPart) {
    	GraphicalEditPart se = (GraphicalEditPart)editPart;
    	return se.resolveSemanticElement() instanceof Artifact;
    }

    /**
     * @generated NOT
     */
    public List getRelTypesOnTarget(IAdaptable target) {
       // System.err.println("getRelTypesOnTarget target=" + target);
        IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target
                .getAdapter(IGraphicalEditPart.class);
        if (targetEditPart instanceof ActivityEditPart) {

            List types = new ArrayList();
            types.add(BpmnElementTypes.Association_3003);
            types.add(BpmnElementTypes.SequenceEdge_3001);
            // only add this possibility if the 
            // activity is not one of the types below
            ActivityType actype = ((Activity) targetEditPart.resolveSemanticElement()).getActivityType();
            switch (actype.getValue()) {
            case ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE:
            case ActivityType.GATEWAY_DATA_BASED_INCLUSIVE:
            case ActivityType.GATEWAY_EVENT_BASED_EXCLUSIVE:
            case ActivityType.GATEWAY_PARALLEL:
            case ActivityType.EVENT_END_COMPENSATION:
            case ActivityType.EVENT_END_EMPTY:
            case ActivityType.EVENT_END_ERROR:
            case ActivityType.EVENT_END_MESSAGE:
            case ActivityType.EVENT_END_TERMINATE:
                break;
            default:
                types.add(BpmnElementTypes.MessagingEdge_3002);
            }
            types = filterElementTypes(target, types);
            return types;
        }
        // activities placed on the border of a subprocess
        // only accept incoming associations.
        if (targetEditPart instanceof Activity2EditPart) {
            List types = new ArrayList();
			types.add(BpmnElementTypes.Association_3003);
            types = filterElementTypes(target, types);
            return types;
        }
        if (targetEditPart instanceof SubProcessEditPart) {
            List types = new ArrayList();
			types.add(BpmnElementTypes.Association_3003);
            types.add(BpmnElementTypes.SequenceEdge_3001);
            types = filterElementTypes(target, types);
            return types;
        }
        if (isEditPartArtifact(targetEditPart)) {
        	List types = new ArrayList();
			types.add(BpmnElementTypes.Association_3003);
			types = filterElementTypes(target, types);
			return types;
		}
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated NOT
     */
    public List getRelTypesOnSourceAndTarget(IAdaptable source,
            IAdaptable target) {
        if (source.equals(target)) {
            return Collections.EMPTY_LIST;
        }
        IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source
                .getAdapter(IGraphicalEditPart.class);
        IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target
                .getAdapter(IGraphicalEditPart.class);

        if (sourceEditPart instanceof SubProcessEditPart) {
            List types = new ArrayList();
            if (targetEditPart instanceof ActivityEditPart) {
                types.add(BpmnElementTypes.SequenceEdge_3001);
            } else if (targetEditPart instanceof Activity2EditPart) {
                types.add(BpmnElementTypes.SequenceEdge_3001);
            } else if (targetEditPart instanceof SubProcessEditPart) {
                types.add(BpmnElementTypes.SequenceEdge_3001);
            } else if (isEditPartArtifact(targetEditPart)) {
            	types.add(BpmnElementTypes.Association_3003);
            }
            types = filterElementTypes(source, types);
            return types;
        }

        if (sourceEditPart instanceof ActivityEditPart) {
            List types = new ArrayList();
            if (targetEditPart instanceof ActivityEditPart) {
                types.add(BpmnElementTypes.SequenceEdge_3001);
                types.add(BpmnElementTypes.MessagingEdge_3002);
            } else if (targetEditPart instanceof Activity2EditPart) {
                // no incoming connections is allowed
                // types.add(BpmnElementTypes.SequenceEdge_3001);
            } else if (targetEditPart instanceof SubProcessEditPart) {
                types.add(BpmnElementTypes.SequenceEdge_3001);
            } else if (isEditPartArtifact(targetEditPart)) {
            	types.add(BpmnElementTypes.Association_3003);
            }
            types = filterElementTypes(source, types);
            return types;
        }
        if (sourceEditPart instanceof Activity2EditPart) {
            List types = new ArrayList();
            if (targetEditPart instanceof ActivityEditPart) {
                types.add(BpmnElementTypes.SequenceEdge_3001);
            } else if (targetEditPart instanceof Activity2EditPart) {

            } else if (targetEditPart instanceof SubProcessEditPart) {
                types.add(BpmnElementTypes.SequenceEdge_3001);
            } else if (isEditPartArtifact(targetEditPart)) {
            	types.add(BpmnElementTypes.Association_3003);            
            }
            types = filterElementTypes(source, types);
            return types;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated NOT
     */
    public List getTypesForSource(IAdaptable target,
            IElementType relationshipType) {
        IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target
                .getAdapter(IGraphicalEditPart.class);
        if (targetEditPart instanceof ActivityEditPart) {
            List types = new ArrayList();
            if (relationshipType == BpmnElementTypes.MessagingEdge_3002) {
                types.addAll(BPMNElementTypesActivities
                        .getElementTypesForMessagingEdgeSource());
            } else if (relationshipType == BpmnElementTypes.SequenceEdge_3001) {
                types.addAll(BPMNElementTypesActivities
                        .getElementTypesForSequenceEdgeSource());
            }  else if (relationshipType == BpmnElementTypes.Association_3003) {
                types.addAll(BPMNElementTypesActivities
                        .getElementTypesForAssociationSource());
            }
            types = filterElementTypes(target, types);
            return types;
        }
        if (targetEditPart instanceof Activity2EditPart) {
        	List types = new ArrayList();
        	if (relationshipType == BpmnElementTypes.Association_3003) {
                types.addAll(BPMNElementTypesActivities
                        .getElementTypesForAssociationSource());
            }
        	 types = filterElementTypes(target, types);
            return types;
        }
        if (targetEditPart instanceof SubProcessEditPart) {
            if (relationshipType == BpmnElementTypes.SequenceEdge_3001) {
                List types = new ArrayList();
                types.addAll(BPMNElementTypesActivities
                        .getElementTypesForSequenceEdgeSource());
                return types;
            }  else if (relationshipType == BpmnElementTypes.Association_3003) {
                List types = new ArrayList();
                types.addAll(BPMNElementTypesActivities
                        .getElementTypesForAssociationSource());
                types = filterElementTypes(target, types);
                return types;
            }
        }
        if (isEditPartArtifact(targetEditPart) &&
                relationshipType == BpmnElementTypes.Association_3003) {
            List types = new ArrayList();
            types.addAll(BPMNElementTypesActivities
                    .getElementTypesForAssociationSource());
            types = filterElementTypes(target, types);
            return types;
        }
        
        /* what was generated
            if (relationshipType == BpmnElementTypes.MessagingEdge_3002) {
                types.add(BpmnElementTypes.Activity_2001);
            }
            if (relationshipType == BpmnElementTypes.MessagingEdge_3002) {
                types.add(BpmnElementTypes.Activity_2003);
            }
            return types;
        }
        if (targetEditPart instanceof Activity2EditPart) {
            List types = new ArrayList();
            if (relationshipType == BpmnElementTypes.MessagingEdge_3002) {
                types.add(BpmnElementTypes.Activity_2001);
            }
            if (relationshipType == BpmnElementTypes.MessagingEdge_3002) {
                types.add(BpmnElementTypes.Activity_2003);
            }
            return types;
        }*/
        return Collections.EMPTY_LIST;
    }

    public List getTypesForTarget(IAdaptable source,
            IElementType relationshipType) {
        IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source
                .getAdapter(IGraphicalEditPart.class);
        if (sourceEditPart instanceof ActivityEditPart) {
            List types = new ArrayList();
            if (relationshipType == BpmnElementTypes.MessagingEdge_3002) {
                types.addAll(BPMNElementTypesActivities
                        .getElementTypesForMessagingEdgeTarget());
            } else if (relationshipType == BpmnElementTypes.SequenceEdge_3001) {
                types.addAll(BPMNElementTypesActivities
                        .getElementTypesForSequenceEdgeTarget());
            }  else if (relationshipType == BpmnElementTypes.Association_3003) {
                types.addAll(BPMNElementTypesActivities
                        .getElementTypesForAssociationTarget());
            } 
            types = filterElementTypes(source, types);
            return types;
        }
        if (sourceEditPart instanceof Activity2EditPart) {
        	List types = new ArrayList();
            if (relationshipType == BpmnElementTypes.MessagingEdge_3002) {
                return Collections.EMPTY_LIST;
            } else if (relationshipType == BpmnElementTypes.SequenceEdge_3001) {
                types.addAll(BPMNElementTypesActivities
                        .getElementTypesForSequenceEdgeTarget());
            } else if (relationshipType == BpmnElementTypes.Association_3003) {
                types.addAll(BPMNElementTypesActivities
                        .getElementTypesForAssociationTarget());
            }
            types = filterElementTypes(source, types);
            return types;
        }
        if (sourceEditPart instanceof SubProcessEditPart) {
            List types = new ArrayList();
            if (relationshipType == BpmnElementTypes.SequenceEdge_3001) {
                types.addAll(BPMNElementTypesActivities
                        .getElementTypesForSequenceEdgeTarget());
            } else if (relationshipType == BpmnElementTypes.Association_3003) {
                types.addAll(BPMNElementTypesActivities
                        .getElementTypesForAssociationTarget());
            }
            types = filterElementTypes(source, types);
            return types;
        }
        if (isEditPartArtifact(sourceEditPart)) {
        	List types = new ArrayList();
			types.addAll(BPMNElementTypesActivities.getElementTypesForAssociationTarget());
			types = filterElementTypes(source, types);
			return types;
		}
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    public EObject selectExistingElementForSource(IAdaptable target,
            IElementType relationshipType) {
        return selectExistingElement(target, getTypesForSource(target,
                relationshipType), relationshipType, true);
    }

    /**
     * @generated
     */
    public EObject selectExistingElementForTarget(IAdaptable source,
            IElementType relationshipType) {
        return selectExistingElement(source, getTypesForTarget(source,
                relationshipType), relationshipType, false);
    }

    /**
     * @generated NOT
     */
    protected EObject selectExistingElement(IAdaptable host, Collection types,
            IElementType relationshipType, boolean isReversed) {
        if (types.isEmpty()) {
            return null;
        }
        IGraphicalEditPart editPart = (IGraphicalEditPart) host
                .getAdapter(IGraphicalEditPart.class);
        if (editPart == null) {
            return null;
        }
        IGraphicalEditPart diagramEP = (IGraphicalEditPart) editPart.getRoot()
                .getContents();
        ConnectionValidator validator;
        if (relationshipType == BpmnElementTypes.MessagingEdge_3002) {
            validator = MessageConnectionValidator.INSTANCE;
        } else {
            validator = EdgeConnectionValidator.INSTANCE;
        }
        EditPart dep = editPart.getRoot().getContents();
        Diagram diagram = (Diagram) editPart.getRoot().getContents().getModel();
        Collection elements = new HashSet();
        for (Iterator it = diagram.getElement().eAllContents(); it.hasNext();) {
            EObject element = (EObject) it.next();
            boolean okToAdd = isApplicableElement(element, types);
            if (!isReversed) {
            	okToAdd = okToAdd && validator.canConnect(editPart, diagramEP.findEditPart(
            			null, element));
            }	else {
            	okToAdd = okToAdd && validator.canConnect(diagramEP.findEditPart(
            			null, element), editPart);
            }
            if (okToAdd) {
                elements.add(element);
            }
        }
        if (elements.isEmpty()) {
            return null;
        }
        return selectElement((EObject[]) elements.toArray(new EObject[elements
                .size()]));
    }

    /**
     * @generated NOT
     */
    protected boolean isApplicableElement(EObject element, Collection types) {
        Collection<String> hints = new HashSet<String>();
        for (Iterator iter = types.iterator(); iter.hasNext();) {
        	
            IElementType el = (IElementType) iter.next();
            if (el instanceof ElementTypeEx) {
            	hints.add(((ElementTypeEx) el).getSecondarySemanticHint());
            }
        }

        String hint;

        if (element instanceof SubProcess) {
            hint = ActivityType.SUB_PROCESS_LITERAL.getLiteral();
            return hints.contains(hint);
        } else if (element instanceof Activity) {
            hint = ((Activity) element).getActivityType().getLiteral();
            return hints.contains(hint);
        } else if (element instanceof Artifact) {
            System.err.println("TODO complete the BpmnModeleingAssistant for the artifacts."); // TODO //$NON-NLS-1$
        }

        return false;
    }

    /**
     * @generated
     */
    protected EObject selectElement(EObject[] elements) {
        Shell shell = Display.getCurrent().getActiveShell();
        ILabelProvider labelProvider = new AdapterFactoryLabelProvider(
                BpmnDiagramEditorPlugin.getInstance()
                        .getItemProvidersAdapterFactory());
        ElementListSelectionDialog dialog = new ElementListSelectionDialog(
                shell, labelProvider);
        dialog.setMessage(BpmnDiagramMessages.BpmnModelingAssistantProvider_msg);
        dialog.setTitle(BpmnDiagramMessages.BpmnModelingAssistantProvider_title);
        dialog.setMultipleSelection(false);
        dialog.setElements(elements);
        EObject selected = null;
        if (dialog.open() == Window.OK) {
            selected = (EObject) dialog.getFirstResult();
        }
        return selected;
    }
    
    private List filterElementTypes(IAdaptable host, List elementTypes) {
    	EditDomain domain = ((IGraphicalEditPart) host.
    			getAdapter(IGraphicalEditPart.class)).
    				getRoot().getViewer().getEditDomain();
    	if (domain instanceof BpmnDiagramEditDomain) {
    		Set<IElementType> rTypes = ((BpmnDiagramEditDomain) domain).
    			getRemovedElementTypes();
    		List<IElementType> newTypes = new LinkedList<IElementType>();
    		List<String> ids = new LinkedList<String>();
    		for (IElementType rType : rTypes) {
    			ids.add(rType.getId());
    		}
    		for (Object elt : elementTypes) {
    			if (!ids.contains(((IElementType) elt).getId())) {
    				newTypes.add((IElementType) elt);
    			} else {
    				// if their IDs are equal, that is not enough to determine 
    				// if they are really equal, in the case that the 
    				// element type is holding a secondary semantic hint.
    				if (elt instanceof IElementTypeEx) {
    					boolean reallyEquals = false;
    					for (IElementType rType : rTypes) {
    						if (rType.getId().equals(((IElementType) elt).getId())) {
    							if (rType instanceof IElementTypeEx && 
    									(((IElementTypeEx) rType).getSecondarySemanticHint().
    									equals(((IElementTypeEx) elt).getSecondarySemanticHint()))) {
    								reallyEquals = true;
    								break; 
    							}
    						}
    					}
    					if (!reallyEquals) {
    						newTypes.add((IElementType) elt);
    					}
    				}
    			}
    		}
    		return newTypes;
    	}
    	return elementTypes;
    }
}
