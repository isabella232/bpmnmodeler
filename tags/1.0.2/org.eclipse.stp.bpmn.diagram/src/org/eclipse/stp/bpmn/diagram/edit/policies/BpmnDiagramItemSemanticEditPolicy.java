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

import java.util.Iterator;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.clipboard.core.ClipboardSupportUtil;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.Identifiable;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * @generated
 */
public class BpmnDiagramItemSemanticEditPolicy extends
        BpmnBaseItemSemanticEditPolicy {

    /**
     * @generated
     */
    protected Command getCreateCommand(CreateElementRequest req) {
        if (BpmnElementTypes.Pool_1001 == req.getElementType()) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(BpmnPackage.eINSTANCE
                        .getBpmnDiagram_Pools());
            }
            return getMSLWrapper(new CreatePool_1001Command(req));
        }
        if (BpmnElementTypes.TextAnnotation_1002 == req.getElementType()) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(BpmnPackage.eINSTANCE
                        .getArtifactsContainer_Artifacts());
            }
            return getMSLWrapper(new CreateTextAnnotation_1002Command(req));
        }
        if (BpmnElementTypes.DataObject_1003 == req.getElementType()) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(BpmnPackage.eINSTANCE
                        .getArtifactsContainer_Artifacts());
            }
            return getMSLWrapper(new CreateDataObject_1003Command(req));
        }
        if (BpmnElementTypes.Group_1004 == req.getElementType()) {
            if (req.getContainmentFeature() == null) {
                req.setContainmentFeature(BpmnPackage.eINSTANCE
                        .getArtifactsContainer_Artifacts());
            }
            return getMSLWrapper(new CreateGroup_1004Command(req));
        }
        return super.getCreateCommand(req);
    }

    /**
     * @generated
     */
    private static class CreatePool_1001Command extends CreateElementCommand {

        /**
         * @generated
         */
        public CreatePool_1001Command(CreateElementRequest req) {
            super(req);
        }

        /**
         * @generated
         */
        protected EClass getEClassToEdit() {
            return BpmnPackage.eINSTANCE.getBpmnDiagram();
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
         * Overriden to make sure the pool name at least at creation time is unique
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
                    if (res instanceof Pool) {
                        //lets make sure it has aunique enough label
                        Pool pool = (Pool)res;
                        BpmnDiagram diag = (BpmnDiagram)element;
                        String poolName = pool.getName();
                        if (poolName == null) {
                            poolName = BpmnDiagramMessages.BpmnDiagramItemSemanticEditPolicy_pool_default_name;
                        }
                        String baseName = poolName;
                        int i = 0;
                        boolean ok = false;
                        while (!ok) {
                            ok = true;
                            for (Pool p : diag.getPools()) {
                                if (p != pool) {
                                    if (poolName.equals(p.getName())) {
                                        ok = false;
                                        poolName = baseName + i;
                                        i++;
                                        break;
                                    }
                                }
                            }
                        }
                        pool.setName(poolName);
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
    private static class CreateTextAnnotation_1002Command extends
            CreateElementCommand {

        /**
         * @generated
         */
        public CreateTextAnnotation_1002Command(CreateElementRequest req) {
            super(req);
        }

        /**
         * @generated
         */
        protected EClass getEClassToEdit() {
            return BpmnPackage.eINSTANCE.getBpmnDiagram();
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
    private static class CreateDataObject_1003Command extends
            CreateElementCommand {

        /**
         * @generated
         */
        public CreateDataObject_1003Command(CreateElementRequest req) {
            super(req);
        }

        /**
         * @generated
         */
        protected EClass getEClassToEdit() {
            return BpmnPackage.eINSTANCE.getBpmnDiagram();
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
    private static class CreateGroup_1004Command extends CreateElementCommand {

        /**
         * @generated
         */
        public CreateGroup_1004Command(CreateElementRequest req) {
            super(req);
        }

        /**
         * @generated
         */
        protected EClass getEClassToEdit() {
            return BpmnPackage.eINSTANCE.getBpmnDiagram();
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
    protected Command getDuplicateCommand(DuplicateElementsRequest req) {
        TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost())
                .getEditingDomain();
        return getMSLWrapper(new DuplicateAnythingCommand(editingDomain, req));
    }

    /**
     * @generated
     */
    private static class DuplicateAnythingCommand extends
            DuplicateEObjectsCommand {

        /**
         * @generated
         */
        public DuplicateAnythingCommand(
                TransactionalEditingDomain editingDomain,
                DuplicateElementsRequest req) {
            super(editingDomain, req.getLabel(), req
                    .getElementsToBeDuplicated(), req
                    .getAllDuplicatedElementsMap());
        }
        
        /**
         * @generated NOT
    	 * Overrides the super method by regenerating the id of the identifiable
    	 * if the object duplicated is an identifiable.
    	 * 
    	 */
    	protected CommandResult doExecuteWithResult(
                IProgressMonitor progressMonitor, IAdaptable info)
            throws ExecutionException {
    		// Remove elements whose container is getting copied.
    		ClipboardSupportUtil.getCopyElements(getObjectsToBeDuplicated());

    		// Perform the copy and update the references.
    		EcoreUtil.Copier copier = new EcoreUtil.Copier();
    		copier.copyAll(getObjectsToBeDuplicated());
    		copier.copyReferences();

    		// Update the map with all elements duplicated.
    		getAllDuplicatedObjectsMap().putAll(copier);

    		// Add the duplicates to the original's container.
    		for (Iterator i = getObjectsToBeDuplicated().iterator(); i.hasNext();) {
    			EObject original = (EObject) i.next();
    			EObject duplicate = (EObject) copier.get(original);
    			// callback to reset the identifiable
    			if (duplicate instanceof Identifiable) {
    				duplicate.eSet(BpmnPackage.eINSTANCE.getIdentifiable_ID(), 
    						EcoreUtil.generateUUID());
    			}
    			EReference reference = original.eContainmentFeature();
    			if (reference != null
    				&& FeatureMapUtil.isMany(original.eContainer(),reference)
    				&& ClipboardSupportUtil.isOkToAppendEObjectAt(
    					original.eContainer(), reference, duplicate)) {
    				
    				ClipboardSupportUtil.appendEObjectAt(original.eContainer(),
    					reference, duplicate);
    			}
    		}
    		return CommandResult.newOKCommandResult(getAllDuplicatedObjectsMap());
    	}
    }
}
