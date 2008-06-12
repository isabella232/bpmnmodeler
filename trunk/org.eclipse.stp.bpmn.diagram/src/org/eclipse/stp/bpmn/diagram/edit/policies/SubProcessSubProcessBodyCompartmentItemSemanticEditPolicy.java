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
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.commands.CreateElementCommandEx;
import org.eclipse.stp.bpmn.commands.CreateSubProcessCommand;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * @generated
 */
public class SubProcessSubProcessBodyCompartmentItemSemanticEditPolicy extends
        BpmnBaseItemSemanticEditPolicy {

    /**
     * @generated not
     */
    protected Command getCreateCommand(CreateElementRequest req) {
//    	 Hugues: using getId() it is not == with the IElementTypeEx
        if (BpmnElementTypes.Activity_2001.getId().equals(
                req.getElementType().getId())) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(BpmnPackage.eINSTANCE
                        .getGraph_Vertices());
            }
            return getMSLWrapper(new CreateActivity_2001Command(req));
        }
        if (BpmnElementTypes.SubProcess_2002.getId().equals(req.getElementType().getId())) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(BpmnPackage.eINSTANCE
                        .getGraph_Vertices());
            }
            return getMSLWrapper(new CreateSubProcess_2002Command(req));
        }
        if (BpmnElementTypes.TextAnnotation_2004.getId().equals(req.getElementType().getId())) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(BpmnPackage.eINSTANCE
                        .getArtifactsContainer_Artifacts());
            }
            return getMSLWrapper(new CreateTextAnnotation_2004Command(req));
        }
        if (BpmnElementTypes.DataObject_2005.getId().equals(req.getElementType().getId())) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(BpmnPackage.eINSTANCE
                        .getArtifactsContainer_Artifacts());
            }
            return getMSLWrapper(new CreateDataObject_2005Command(req));
        }
        if (BpmnElementTypes.Group_2006.getId().equals( req.getElementType().getId())) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(BpmnPackage.eINSTANCE
                        .getArtifactsContainer_Artifacts());
            }
            return getMSLWrapper(new CreateGroup_2006Command(req));
        }
        return super.getCreateCommand(req);
    }

    /**
     * @generated NOT (using CreateElementCommandEx)
     */
    private static class CreateActivity_2001Command extends CreateElementCommandEx  {

        /**
         * @generated
         */
        public CreateActivity_2001Command(CreateElementRequest req) {
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

    /**
     * @generated NOT
     */
    private static class CreateSubProcess_2002Command extends CreateSubProcessCommand {

        /**
         * @generated
         */
        public CreateSubProcess_2002Command(CreateElementRequest req) {
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

    /**
     * @generated
     */
    private static class CreateTextAnnotation_2004Command extends
            CreateElementCommandEx {

        /**
         * @generated
         */
        public CreateTextAnnotation_2004Command(CreateElementRequest req) {
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

    /**
     * @generated
     */
    private static class CreateDataObject_2005Command extends
            CreateElementCommandEx {

        /**
         * @generated
         */
        public CreateDataObject_2005Command(CreateElementRequest req) {
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

    /**
     * @generated
     */
    private static class CreateGroup_2006Command extends CreateElementCommandEx {

        /**
         * @generated
         */
        public CreateGroup_2006Command(CreateElementRequest req) {
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
    /**
     * @generated NOT copied from BpmnDiagramItemSemanticEditPolicy
     */
    protected Command getDuplicateCommand(DuplicateElementsRequest req) {
        TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
                .getEditingDomain();
        return getMSLWrapper(new DuplicateAnythingCommand(editingDomain, req));
    }

    /**
     * @notgenerated copied from BpmnDiagramItemSemanticEditPolicy
     */
    private static class DuplicateAnythingCommand extends
            DuplicateEObjectsCommand {

        /**
         * @notgenerated
         */
        public DuplicateAnythingCommand(
                TransactionalEditingDomain editingDomain,
                DuplicateElementsRequest req) {
            super(editingDomain, req.getLabel(), req
                    .getElementsToBeDuplicated(), req
                    .getAllDuplicatedElementsMap());
        }
    }

}
