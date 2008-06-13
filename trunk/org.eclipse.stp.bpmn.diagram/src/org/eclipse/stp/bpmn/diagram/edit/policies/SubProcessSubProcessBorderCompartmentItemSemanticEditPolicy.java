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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.commands.CreateElementCommandEx;
import org.eclipse.stp.bpmn.commands.IElementTypeEx;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * @generated
 */
public class SubProcessSubProcessBorderCompartmentItemSemanticEditPolicy extends
        BpmnBaseItemSemanticEditPolicy {

    /**
     * @generated NOT no more than one compensation event handler
     * per border.
     */
    protected Command getCreateCommand(CreateElementRequest req) {
        
        // was
        // if (BpmnElementTypes.Activity_2003 == req.getElementType())
        IElementType type = req.getElementType();
        
        if (BpmnElementTypes.Activity_2003.getId().equals(
                type.getId())) {
            ActivityType actype = ActivityType.
                get(((IElementTypeEx) type).
                        getSecondarySemanticHint());
            switch (actype.getValue()) {
            case ActivityType.EVENT_INTERMEDIATE_COMPENSATION:
                // if we want to add a new compensation envent
                // we must make sure there is no other one
                // already there.
                SubProcess sp = (SubProcess) ((IGraphicalEditPart) 
                            getHost()).resolveSemanticElement();
                for (Object eh : sp.getEventHandlers()) { // eh for event handler
                    if (((Activity) eh).getActivityType().getValue() 
                            == ActivityType.EVENT_INTERMEDIATE_COMPENSATION) {
                        return UnexecutableCommand.INSTANCE;
                    }
                }
            }
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(BpmnPackage.eINSTANCE
                        .getSubProcess_EventHandlers());
            }
            return getMSLWrapper(new CreateActivity_2003Command(req));
        }
        return super.getCreateCommand(req);
    }

    /**
     * @generated
     */
    private static class CreateActivity_2003Command extends CreateElementCommandEx {

        /**
         * @generated
         */
        public CreateActivity_2003Command(CreateElementRequest req) {
            super(req);
        }

        /**
         * @generated
         */
        protected EClass getEClassToEdit() {
            return BpmnPackage.eINSTANCE.getSubProcess();
        };

        /**
         * @generated
         */
        protected EObject getElementToEdit() {
            EObject container = ((CreateElementRequest) getRequest())
                    .getContainer();
            if (container instanceof View) {
                container = ((View) container).getElement();
            }
            return container;
        }
    }

}
