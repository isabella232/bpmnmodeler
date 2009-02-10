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
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.Lane;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.commands.CreateElementCommandEx;
import org.eclipse.stp.bpmn.commands.CreateSubProcessCommand;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * @generated
 */
public class PoolPoolCompartmentItemSemanticEditPolicy extends
        BpmnBaseItemSemanticEditPolicy {

    /**
     * @generated NOT
     */
    protected Command getCreateCommand(CreateElementRequest req) {
        // Hugues: using getId() it is not == with the IElementTypeEx
        if (BpmnElementTypes.Activity_2001.getId().equals(
                req.getElementType().getId())) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(BpmnPackage.eINSTANCE
                        .getGraph_Vertices());
            }
            return getMSLWrapper(new CreateActivity_2001Command(req));
        } else if (BpmnElementTypes.SubProcess_2002.getId().equals(
                req.getElementType().getId())) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(BpmnPackage.eINSTANCE
                        .getGraph_Vertices());
            }
            return getMSLWrapper(new CreateSubProcess_2002Command(req));
        }
        if (BpmnElementTypes.Lane_2007 == req.getElementType()) {
            if (req.getContainmentFeature() == null) {
                req
                        .setContainmentFeature(BpmnPackage.eINSTANCE
                                .getPool_Lanes());
            }
            return getMSLWrapper(new CreateLane_2007Command(req));
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
            return BpmnPackage.eINSTANCE.getPool();
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
            return BpmnPackage.eINSTANCE.getPool();
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
    private static class CreateLane_2007Command extends CreateElementCommandEx {

        /**
         * @generated
         */
        public CreateLane_2007Command(CreateElementRequest req) {
            super(req);
        }

        /**
         * @generated
         */
        protected EClass getEClassToEdit() {
            return BpmnPackage.eINSTANCE.getPool();
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
        
        /**
         * Overriden to make sure the lane name at least at creation time is unique
         * @return the new model element that has been created
         * @generated NOT
         */
        @Override
        protected EObject doDefaultElementCreation() {
            EReference containment = getContainmentFeature();
            EClass eClass = getElementType().getEClass();

            if (containment != null) {
                EObject element = getElementToEdit();

                if (element != null) {
                    EObject res = EMFCoreUtil.create(element, containment, eClass);
                    if (res instanceof Lane) {
                        //lets make sure it has a unique enough label
                        Lane lane = (Lane)res;
                        Pool pool = (Pool)element;
                        String laneName = lane.getName();
                        if (laneName == null) {
                            laneName = BpmnDiagramMessages.PoolPoolCompartmentItemSemanticEditPolicy_lane_base_name;
                        }
                        String baseName = laneName;
                        int i = 0;
                        boolean ok = false;
                        while (!ok) {
                            ok = true;
                            for (Lane p : pool.getLanes()) {
                                if (p != lane) {
                                    if (laneName.equals(p.getName())) {
                                        ok = false;
                                        laneName = baseName + i;
                                        i++;
                                        break;
                                    }
                                }
                            }
                        }
                        lane.setName(laneName);
                    }
                    return res;
                }
            }
            
            return null;
        }
    }

    /**
     * @generated
     */
    private static class CreateTextAnnotation_2004Command extends CreateElementCommandEx {

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
            return BpmnPackage.eINSTANCE.getPool();
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
            return BpmnPackage.eINSTANCE.getPool();
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
            return BpmnPackage.eINSTANCE.getPool();
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
     * @generated NOT copied from BpmnDiagramItemSemanticEditPolicy
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
    }}
