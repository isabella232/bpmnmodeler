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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.DeferredLayoutCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalConnectionEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.ArtifactsContainer;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.diagram.edit.parts.Activity2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.AssociationEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnDiagramEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.DataObject2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.DataObjectEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.Group2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.GroupEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.LaneEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.MessagingEdgeEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SequenceEdgeEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotation2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotationEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;

/**
 * @generated
 */
public class BpmnDiagramCanonicalEditPolicy extends
        CanonicalConnectionEditPolicy {

    /**
     * @generated
     */
    protected List getSemanticChildrenList() {
        List result = new LinkedList();
        EObject modelObject = ((View) getHost().getModel()).getElement();
        View viewObject = (View) getHost().getModel();
        EObject nextValue;
        int nodeVID;
        for (Iterator values = ((BpmnDiagram) modelObject).getPools()
                .iterator(); values.hasNext();) {
            nextValue = (EObject) values.next();
            nodeVID = BpmnVisualIDRegistry.getNodeVisualID(viewObject,
                    nextValue);
            if (PoolEditPart.VISUAL_ID == nodeVID) {
                result.add(nextValue);
            }
        }
        for (Iterator values = ((ArtifactsContainer) modelObject)
                .getArtifacts().iterator(); values.hasNext();) {
            nextValue = (EObject) values.next();
            nodeVID = BpmnVisualIDRegistry.getNodeVisualID(viewObject,
                    nextValue);
            switch (nodeVID) {
            case TextAnnotation2EditPart.VISUAL_ID: {
                result.add(nextValue);
                break;
            }
            case DataObject2EditPart.VISUAL_ID: {
                result.add(nextValue);
                break;
            }
            case Group2EditPart.VISUAL_ID: {
                result.add(nextValue);
                break;
            }
            }
        }
        return result;
    }

    /**
     * @generated
     */
    protected boolean shouldDeleteView(View view) {
        return view.isSetElement() && view.getElement() != null
                && view.getElement().eIsProxy();
    }

    /**
     * @generated
     */
    protected String getDefaultFactoryHint() {
        return null;
    }

    /**
     * @generated
     */
    protected List getSemanticConnectionsList() {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    protected EObject getSourceElement(EObject relationship) {
        return null;
    }

    /**
     * @generated
     */
    protected EObject getTargetElement(EObject relationship) {
        return null;
    }

    /**
     * @generated
     */
    protected boolean shouldIncludeConnection(Edge connector,
            Collection children) {
        return false;
    }

    /**
     * @generated
     */
    protected void refreshSemantic() {
        List createdViews = new LinkedList();
        createdViews.addAll(refreshSemanticChildren());
        List createdConnectionViews = new LinkedList();
        createdConnectionViews.addAll(refreshSemanticConnections());
        createdConnectionViews.addAll(refreshConnections());

        if (createdViews.size() > 1) {
            // perform a layout of the container
            DeferredLayoutCommand layoutCmd = new DeferredLayoutCommand(host()
                    .getEditingDomain(), createdViews, host());
            executeCommand(new ICommandProxy(layoutCmd));
        }

        createdViews.addAll(createdConnectionViews);
        makeViewsImmutable(createdViews);
    }

    /**
     * @generated
     */
    private Collection myLinkDescriptors = new LinkedList();

    /**
     * @generated
     */
    private Map myEObject2ViewMap = new HashMap();

    /**
     * @generated
     */
    private Collection refreshConnections() {
        try {
            collectAllLinks(getDiagram());
            Collection existingLinks = new LinkedList(getDiagram().getEdges());
            for (Iterator diagramLinks = existingLinks.iterator(); diagramLinks
                    .hasNext();) {
                Edge nextDiagramLink = (Edge) diagramLinks.next();
                if (nextDiagramLink == null ||
                        nextDiagramLink.getSource() == null ||
                        nextDiagramLink.getTarget() == null) {
                    continue;//are we in the middle of a removal?
                }
                EObject diagramLinkObject = nextDiagramLink.getElement();
                EObject diagramLinkSrc = nextDiagramLink.getSource()
                        .getElement();
                EObject diagramLinkDst = nextDiagramLink.getTarget()
                        .getElement();
                int diagramLinkVisualID = BpmnVisualIDRegistry
                        .getVisualID(nextDiagramLink);
                boolean diagLinkWasRemoved = false;
                for (Iterator modelLinkDescriptors = myLinkDescriptors
                        .iterator(); modelLinkDescriptors.hasNext();) {
                    LinkDescriptor nextLinkDescriptor = (LinkDescriptor) modelLinkDescriptors
                            .next();
                    if (diagramLinkObject == nextLinkDescriptor
                            .getLinkElement()
                            && diagramLinkSrc == nextLinkDescriptor.getSource()
                            && diagramLinkDst == nextLinkDescriptor
                                    .getDestination()
                            && diagramLinkVisualID == nextLinkDescriptor
                                    .getVisualID()) {
                        try {
                            if (!diagLinkWasRemoved)
                                diagramLinks.remove();
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        modelLinkDescriptors.remove();
                        break;
                    }
                }
            }
            deleteViews(existingLinks.iterator());
            return createConnections(myLinkDescriptors);
        } finally {
            myLinkDescriptors.clear();
            myEObject2ViewMap.clear();
        }
    }

    /**
     * @generated
     */
    private void collectAllLinks(View view) {
        EObject modelElement = view.getElement();
        int diagramElementVisualID = BpmnVisualIDRegistry.getVisualID(view);
        switch (diagramElementVisualID) {
        case PoolEditPart.VISUAL_ID:
        case TextAnnotation2EditPart.VISUAL_ID:
        case DataObject2EditPart.VISUAL_ID:
        case Group2EditPart.VISUAL_ID:
        case ActivityEditPart.VISUAL_ID:
        case SubProcessEditPart.VISUAL_ID:
        case Activity2EditPart.VISUAL_ID:
        case TextAnnotationEditPart.VISUAL_ID:
        case DataObjectEditPart.VISUAL_ID:
        case GroupEditPart.VISUAL_ID:
        case LaneEditPart.VISUAL_ID:
        case BpmnDiagramEditPart.VISUAL_ID: {
            myEObject2ViewMap.put(modelElement, view);
            storeLinks(modelElement, getDiagram());
        }
        default: {
        }
            for (Iterator children = view.getChildren().iterator(); children
                    .hasNext();) {
                View childView = (View) children.next();
                collectAllLinks(childView);
            }
        }
    }

    /**
     * @generated
     */
    private Collection createConnections(Collection linkDescriptors) {
        if (linkDescriptors.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List adapters = new LinkedList();
        for (Iterator linkDescriptorsIterator = linkDescriptors.iterator(); linkDescriptorsIterator
                .hasNext();) {
            final LinkDescriptor nextLinkDescriptor = (LinkDescriptor) linkDescriptorsIterator
                    .next();
            EditPart sourceEditPart = getEditPartFor(nextLinkDescriptor
                    .getSource());
            EditPart targetEditPart = getEditPartFor(nextLinkDescriptor
                    .getDestination());
            if (sourceEditPart == null || targetEditPart == null) {
                continue;
            }
            CreateConnectionViewRequest.ConnectionViewDescriptor descriptor = new CreateConnectionViewRequest.ConnectionViewDescriptor(
                    nextLinkDescriptor.getSemanticAdapter(), null,
                    ViewUtil.APPEND, false, ((IGraphicalEditPart) getHost())
                            .getDiagramPreferencesHint());
            CreateConnectionViewRequest ccr = new CreateConnectionViewRequest(
                    descriptor);
            ccr.setType(RequestConstants.REQ_CONNECTION_START);
            ccr.setSourceEditPart(sourceEditPart);
            sourceEditPart.getCommand(ccr);
            ccr.setTargetEditPart(targetEditPart);
            ccr.setType(RequestConstants.REQ_CONNECTION_END);
            Command cmd = targetEditPart.getCommand(ccr);
            if (cmd != null && cmd.canExecute()) {
                executeCommand(cmd);
                IAdaptable viewAdapter = (IAdaptable) ccr.getNewObject();
                if (viewAdapter != null) {
                    adapters.add(viewAdapter);
                }
            }
        }
        return adapters;
    }

    /**
     * @generated
     */
    private EditPart getEditPartFor(EObject modelElement) {
        View view = (View) myEObject2ViewMap.get(modelElement);
        if (view != null) {
            return (EditPart) getHost().getViewer().getEditPartRegistry().get(
                    view);
        }
        return null;
    }

    /**
     *@generated
     */
    private void storeLinks(EObject container, Diagram diagram) {
        EClass containerMetaclass = container.eClass();
        storeFeatureModelFacetLinks(container, containerMetaclass, diagram);
        storeTypeModelFacetLinks(container, containerMetaclass);
    }

    /**
     * @generated
     */
    private void storeTypeModelFacetLinks(EObject container,
            EClass containerMetaclass) {
        if (BpmnPackage.eINSTANCE.getGraph().isSuperTypeOf(containerMetaclass)) {
            for (Iterator values = ((Graph) container).getSequenceEdges()
                    .iterator(); values.hasNext();) {
                EObject nextValue = ((EObject) values.next());
                int linkVID = BpmnVisualIDRegistry
                        .getLinkWithClassVisualID(nextValue);
                if (SequenceEdgeEditPart.VISUAL_ID == linkVID) {
                    Object structuralFeatureResult = ((SequenceEdge) nextValue)
                            .getTarget();
                    if (structuralFeatureResult instanceof EObject) {
                        EObject dst = (EObject) structuralFeatureResult;
                        structuralFeatureResult = ((SequenceEdge) nextValue)
                                .getSource();
                        if (structuralFeatureResult instanceof EObject) {
                            EObject src = (EObject) structuralFeatureResult;
                            myLinkDescriptors.add(new LinkDescriptor(src, dst,
                                    nextValue, linkVID));
                        }
                    }
                }
            }
        }
        if (BpmnPackage.eINSTANCE.getBpmnDiagram().isSuperTypeOf(
                containerMetaclass)) {
            for (Iterator values = ((BpmnDiagram) container).getMessages()
                    .iterator(); values.hasNext();) {
                EObject nextValue = ((EObject) values.next());
                int linkVID = BpmnVisualIDRegistry
                        .getLinkWithClassVisualID(nextValue);
                if (MessagingEdgeEditPart.VISUAL_ID == linkVID) {
                    Object structuralFeatureResult = ((MessagingEdge) nextValue)
                            .getTarget();
                    if (structuralFeatureResult instanceof EObject) {
                        EObject dst = (EObject) structuralFeatureResult;
                        structuralFeatureResult = ((MessagingEdge) nextValue)
                                .getSource();
                        if (structuralFeatureResult instanceof EObject) {
                            EObject src = (EObject) structuralFeatureResult;
                            myLinkDescriptors.add(new LinkDescriptor(src, dst,
                                    nextValue, linkVID));
                        }
                    }
                }
            }
        }
        if (BpmnPackage.eINSTANCE.getArtifact().isSuperTypeOf(
                containerMetaclass)) {
            for (Iterator values = ((Artifact) container).getAssociations()
                    .iterator(); values.hasNext();) {
                EObject nextValue = ((EObject) values.next());
                int linkVID = BpmnVisualIDRegistry
                        .getLinkWithClassVisualID(nextValue);
                if (AssociationEditPart.VISUAL_ID == linkVID) {
                    Object structuralFeatureResult = ((Association) nextValue)
                            .getTarget();
                    if (structuralFeatureResult instanceof EObject) {
                        EObject dst = (EObject) structuralFeatureResult;
                        structuralFeatureResult = ((Association) nextValue)
                                .getSource();
                        if (structuralFeatureResult instanceof EObject) {
                            EObject src = (EObject) structuralFeatureResult;
                            myLinkDescriptors.add(new LinkDescriptor(src, dst,
                                    nextValue, linkVID));
                        }
                    }
                }
            }
        }
    }

    /**
     *@generated
     */
    private void storeFeatureModelFacetLinks(EObject container,
            EClass containerMetaclass, Diagram diagram) {

    }

    /**
     * @generated
     */
    private Diagram getDiagram() {
        return ((View) getHost().getModel()).getDiagram();
    }

    /**
     * @generated
     */
    private class LinkDescriptor {

        /**
         * @generated
         */
        private EObject mySource;

        /**
         * @generated
         */
        private EObject myDestination;

        /**
         * @generated
         */
        private EObject myLinkElement;

        /**
         * @generated
         */
        private int myVisualID;

        /**
         * @generated
         */
        private IAdaptable mySemanticAdapter;

        /**
         * @generated
         */
        protected LinkDescriptor(EObject source, EObject destination,
                EObject linkElement, int linkVID) {
            this(source, destination, linkVID);
            myLinkElement = linkElement;
            mySemanticAdapter = new EObjectAdapter(linkElement);
        }

        /**
         * @generated
         */
        protected LinkDescriptor(EObject source, EObject destination,
                IElementType elementType, int linkVID) {
            this(source, destination, linkVID);
            myLinkElement = null;
            final IElementType elementTypeCopy = elementType;
            mySemanticAdapter = new IAdaptable() {
                public Object getAdapter(Class adapter) {
                    if (IElementType.class.equals(adapter)) {
                        return elementTypeCopy;
                    }
                    return null;
                }
            };
        }

        /**
         * @generated
         */
        private LinkDescriptor(EObject source, EObject destination, int linkVID) {
            mySource = source;
            myDestination = destination;
            myVisualID = linkVID;
        }

        /**
         * @generated
         */
        protected EObject getSource() {
            return mySource;
        }

        /**
         * @generated
         */
        protected EObject getDestination() {
            return myDestination;
        }

        /**
         * @generated
         */
        protected EObject getLinkElement() {
            return myLinkElement;
        }

        /**
         * @generated
         */
        protected int getVisualID() {
            return myVisualID;
        }

        /**
         * @generated
         */
        protected IAdaptable getSemanticAdapter() {
            return mySemanticAdapter;
        }
    }

}
