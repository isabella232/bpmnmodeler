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
package org.eclipse.stp.bpmn.diagram.edit.policies;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.SemanticEditPolicy;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IEditHelperContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyReferenceRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientReferenceRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.diagram.edit.helpers.BpmnBaseEditHelper;
import org.eclipse.stp.bpmn.diagram.edit.helpers.EdgeEditHelperAdvice;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;
import org.eclipse.stp.bpmn.policies.BpmnReorientRelationshipCommand;

/**
 * @generated
 */
public class BpmnBaseItemSemanticEditPolicy extends SemanticEditPolicy {

    /**
     * @generated NOT
     */
    protected Command getSemanticCommand(IEditCommandRequest request) {
        IEditCommandRequest completedRequest = completeRequest(request);
        Object editHelperContext = completedRequest.getEditHelperContext();
        if (editHelperContext instanceof View
                || (editHelperContext instanceof IEditHelperContext && ((IEditHelperContext) editHelperContext)
                        .getEObject() instanceof View)) {
            // no semantic commands are provided for pure design elements
            return null;
        }
        if (editHelperContext == null) {
            editHelperContext = ViewUtil
                    .resolveSemanticElement((View) getHost().getModel());
        }
        IElementType elementType = ElementTypeRegistry.getInstance()
                .getElementType(editHelperContext);
        if (elementType == ElementTypeRegistry.getInstance().getType(
                "org.eclipse.gmf.runtime.emf.type.core.default")) { //$NON-NLS-1$ 
            elementType = null;
        }
        Command epCommand = getSemanticCommandSwitch(completedRequest);
        if (epCommand != null) {
            ICommand command = epCommand instanceof ICommandProxy ? ((ICommandProxy) epCommand)
                    .getICommand()
                    : new CommandProxy(epCommand);
            completedRequest.setParameter(
                    BpmnBaseEditHelper.EDIT_POLICY_COMMAND, command);
        }
        Command ehCommand = null;
        if (elementType != null) {
            ICommand command = elementType.getEditCommand(completedRequest);
            if (command != null) {
                // added start
                if (elementType == BpmnElementTypes.Activity_2001
                        && command.toString().equals("Create Edge Connection()")) { //$NON-NLS-1$
                    if (!EdgeEditHelperAdvice.THE_INSTANCE.approveRequest(request)) {
//                        System.err.println("  GOT COMMAND for " + elementType
//                                + " -> " + command + " request " + request);
                        command = elementType.getEditCommand(completedRequest);
                    }
                }
                // added end
                if (!(command instanceof CompositeTransactionalCommand)) {
                    TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
                            .getEditingDomain();
                    command = new CompositeTransactionalCommand(editingDomain,
                            null).compose(command);
                }
                ehCommand = new ICommandProxy(command);
            }
        }
        boolean shouldProceed = true;
        if (completedRequest instanceof DestroyRequest) {
            shouldProceed = shouldProceed((DestroyRequest) completedRequest);
        }
        if (shouldProceed) {
            if (completedRequest instanceof DestroyRequest) {
                TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
                        .getEditingDomain();
                Command deleteViewCommand = 
                    new ICommandProxy(new DeleteCommand(editingDomain, (View) getHost().getModel()));
                if (findMultipleViewsOfSameType(getHost().getRoot(), 
                        ((IGraphicalEditPart) getHost()).resolveSemanticElement())) {
                    return deleteViewCommand;
                } 
                ehCommand = ehCommand == null ? deleteViewCommand : ehCommand
                        .chain(deleteViewCommand);
            }
            return ehCommand;
        }
        return null;
    }

    /**
     * returns true if there are other views with the same type in the diagram.
     */
    private boolean findMultipleViewsOfSameType(EditPart part, EObject semantic) {
        Map<String, View> views = new HashMap<String, View>();
        return internalFoundMultipleViewsOfSameType(part, semantic, views);
    }
    
    /**
     * 
     * @param part the part to examine
     * @param semantic the semantic element
     * @param views list of views
     * @return true if a view of the same type for a semantic element exists.
     */
    private boolean internalFoundMultipleViewsOfSameType(EditPart part, EObject semantic, Map<String, View> views) {
        if (part instanceof IGraphicalEditPart &&
                ((IGraphicalEditPart) part).resolveSemanticElement() != null) {
            if (((IGraphicalEditPart) part).resolveSemanticElement().equals(semantic)) {
                if (views.get(((View) part.getModel()).getType()) != null
                        && views.get(((View) part.getModel()).getType()) != part.getModel()) {
                    return true;
                    
                }
                views.put(((View) part.getModel()).getType(), ((View) part.getModel()));
            }
        }
        
        for (Object p : part.getChildren()) {
            if (internalFoundMultipleViewsOfSameType((EditPart) p, semantic, views)) {
                return true;
            }
        }
        
        return false;
    }
    /**
     * @generated
     */
    protected Command getSemanticCommandSwitch(IEditCommandRequest req) {
        if (req instanceof CreateRelationshipRequest) {
            return getCreateRelationshipCommand((CreateRelationshipRequest) req);
        } else if (req instanceof CreateElementRequest) {
            return getCreateCommand((CreateElementRequest) req);
        } else if (req instanceof ConfigureRequest) {
            return getConfigureCommand((ConfigureRequest) req);
        } else if (req instanceof DestroyElementRequest) {
            return getDestroyElementCommand((DestroyElementRequest) req);
        } else if (req instanceof DestroyReferenceRequest) {
            return getDestroyReferenceCommand((DestroyReferenceRequest) req);
        } else if (req instanceof DuplicateElementsRequest) {
//            return getDuplicateCommand((DuplicateElementsRequest) req);
        	// commented out because e decide not to use duplicate anymore.
        	// the main reason for this is exposed in bug 169861
        } else if (req instanceof GetEditContextRequest) {
            return getEditContextCommand((GetEditContextRequest) req);
        } else if (req instanceof MoveRequest) {
            return getMoveCommand((MoveRequest) req);
        } else if (req instanceof ReorientReferenceRelationshipRequest) {
            return getReorientReferenceRelationshipCommand((ReorientReferenceRelationshipRequest) req);
        } else if (req instanceof ReorientRelationshipRequest) {
            return getReorientRelationshipCommand((ReorientRelationshipRequest) req);
        } else if (req instanceof SetRequest) {
            return getSetCommand((SetRequest) req);
        }
        return null;
    }

    /**
     * @generated
     */
    protected Command getConfigureCommand(ConfigureRequest req) {
        return null;
    }

    /**
     * @generated
     */
    protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
//        System.err.println("default getCreateRelationshipCommand " + req.getTarget());
//        if (req.getTarget() == null) {
//            System.err.println("null?");
//        } else if (req.getTarget() instanceof Activity && req.getTarget() != req.getSource()) {
//            System.err.println("yo");
//        }
        return null;
    }

    /**
     * @generated
     */
    protected Command getCreateCommand(CreateElementRequest req) {
        return null;
    }

    /**
     * @generated
     */
    protected Command getSetCommand(SetRequest req) {
        return null;
    }

    /**
     * @generated
     */
    protected Command getEditContextCommand(GetEditContextRequest req) {
        return null;
    }

    /**
     * @generated
     */
    protected Command getDestroyElementCommand(DestroyElementRequest req) {
        return null;
    }

    /**
     * @generated
     */
    protected Command getDestroyReferenceCommand(DestroyReferenceRequest req) {
        return null;
    }

    /**
     * @generated
     */
    protected Command getDuplicateCommand(DuplicateElementsRequest req) {
        return null;
    }

    /**
     * @generated
     */
    protected Command getMoveCommand(MoveRequest req) {
        return null;
    }

    /**
     * @generated
     */
    protected Command getReorientReferenceRelationshipCommand(
            ReorientReferenceRelationshipRequest req) {
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * @generated NOT
     */
    protected Command getReorientRelationshipCommand(
            ReorientRelationshipRequest req) {
        return new BpmnReorientRelationshipCommand(req);
    }

    /**
     * @generated
     */
    protected Command getMSLWrapper(ICommand cmd) {
        return new ICommandProxy(cmd);
    }

    /**
     * @generated
     */
    protected EObject getSemanticElement() {
        return ViewUtil.resolveSemanticElement((View) getHost().getModel());
    }

    /**
     * Finds container element for the new relationship of the specified type.
     * Default implementation goes up by containment hierarchy starting from
     * the specified element and returns the first element that is instance of
     * the specified container class.
     * 
     * @generated
     */
    protected EObject getRelationshipContainer(EObject element,
            EClass containerClass, IElementType relationshipType) {
        for (; element != null; element = element.eContainer()) {
            if (containerClass.isSuperTypeOf(element.eClass())) {
                return element;
            }
        }
        return null;
    }

}
