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

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.providers.AbstractViewProvider;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.stp.bpmn.diagram.edit.parts.Activity2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityName2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.AssociationEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnDiagramEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.DataObject2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.DataObjectEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.DataObjectName2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.DataObjectNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.Group2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.GroupEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.GroupName2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.GroupNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.LaneEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.LaneNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.MessagingEdgeEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.MessagingEdgeNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SequenceEdgeEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SequenceEdgeNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBorderCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotation2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotationEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotationName2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotationNameEditPart;

import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;

import org.eclipse.stp.bpmn.diagram.view.factories.Activity2ViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.ActivityName2ViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.ActivityNameViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.ActivityViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.AssociationViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.BpmnDiagramViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.DataObject2ViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.DataObjectName2ViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.DataObjectNameViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.DataObjectViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.Group2ViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.GroupName2ViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.GroupNameViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.GroupViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.LaneNameViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.LaneViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.MessagingEdgeNameViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.MessagingEdgeViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.PoolNameViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.PoolPoolCompartmentViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.PoolViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.SequenceEdgeNameViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.SequenceEdgeViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.SubProcessNameViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.SubProcessSubProcessBodyCompartmentViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.SubProcessSubProcessBorderCompartmentViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.SubProcessViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.TextAnnotation2ViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.TextAnnotationName2ViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.TextAnnotationNameViewFactory;
import org.eclipse.stp.bpmn.diagram.view.factories.TextAnnotationViewFactory;

/**
 * @generated
 */
public class BpmnViewProvider extends AbstractViewProvider {

    /**
     * @generated
     */
    protected Class getDiagramViewClass(IAdaptable semanticAdapter,
            String diagramKind) {
        EObject semanticElement = getSemanticElement(semanticAdapter);
        if (BpmnDiagramEditPart.MODEL_ID.equals(diagramKind)
                && BpmnVisualIDRegistry.getDiagramVisualID(semanticElement) != -1) {
            return BpmnDiagramViewFactory.class;
        }
        return null;
    }

    /**
     * @generated
     */
    protected Class getNodeViewClass(IAdaptable semanticAdapter,
            View containerView, String semanticHint) {
        if (containerView == null) {
            return null;
        }
        IElementType elementType = getSemanticElementType(semanticAdapter);
        if (elementType != null
                && !BpmnElementTypes.isKnownElementType(elementType)) {
            return null;
        }
        EClass semanticType = getSemanticEClass(semanticAdapter);
        EObject semanticElement = getSemanticElement(semanticAdapter);
        int nodeVID = BpmnVisualIDRegistry.getNodeVisualID(containerView,
                semanticElement, semanticType, semanticHint);
        switch (nodeVID) {
        case PoolEditPart.VISUAL_ID:
            return PoolViewFactory.class;
        case PoolNameEditPart.VISUAL_ID:
            return PoolNameViewFactory.class;
        case TextAnnotation2EditPart.VISUAL_ID:
            return TextAnnotation2ViewFactory.class;
        case TextAnnotationName2EditPart.VISUAL_ID:
            return TextAnnotationName2ViewFactory.class;
        case DataObject2EditPart.VISUAL_ID:
            return DataObject2ViewFactory.class;
        case DataObjectName2EditPart.VISUAL_ID:
            return DataObjectName2ViewFactory.class;
        case Group2EditPart.VISUAL_ID:
            return Group2ViewFactory.class;
        case GroupName2EditPart.VISUAL_ID:
            return GroupName2ViewFactory.class;
        case ActivityEditPart.VISUAL_ID:
            return ActivityViewFactory.class;
        case ActivityNameEditPart.VISUAL_ID:
            return ActivityNameViewFactory.class;
        case SubProcessEditPart.VISUAL_ID:
            return SubProcessViewFactory.class;
        case SubProcessNameEditPart.VISUAL_ID:
            return SubProcessNameViewFactory.class;
        case Activity2EditPart.VISUAL_ID:
            return Activity2ViewFactory.class;
        case ActivityName2EditPart.VISUAL_ID:
            return ActivityName2ViewFactory.class;
        case TextAnnotationEditPart.VISUAL_ID:
            return TextAnnotationViewFactory.class;
        case TextAnnotationNameEditPart.VISUAL_ID:
            return TextAnnotationNameViewFactory.class;
        case DataObjectEditPart.VISUAL_ID:
            return DataObjectViewFactory.class;
        case DataObjectNameEditPart.VISUAL_ID:
            return DataObjectNameViewFactory.class;
        case GroupEditPart.VISUAL_ID:
            return GroupViewFactory.class;
        case GroupNameEditPart.VISUAL_ID:
            return GroupNameViewFactory.class;
        case LaneEditPart.VISUAL_ID:
            return LaneViewFactory.class;
        case LaneNameEditPart.VISUAL_ID:
            return LaneNameViewFactory.class;
        case PoolPoolCompartmentEditPart.VISUAL_ID:
            return PoolPoolCompartmentViewFactory.class;
        case SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID:
            return SubProcessSubProcessBodyCompartmentViewFactory.class;
        case SubProcessSubProcessBorderCompartmentEditPart.VISUAL_ID:
            return SubProcessSubProcessBorderCompartmentViewFactory.class;
        case SequenceEdgeNameEditPart.VISUAL_ID:
            return SequenceEdgeNameViewFactory.class;
        case MessagingEdgeNameEditPart.VISUAL_ID:
            return MessagingEdgeNameViewFactory.class;
        }
        return null;
    }

    /**
     * @generated
     */
    protected Class getEdgeViewClass(IAdaptable semanticAdapter,
            View containerView, String semanticHint) {
        IElementType elementType = getSemanticElementType(semanticAdapter);
        if (elementType != null
                && !BpmnElementTypes.isKnownElementType(elementType)) {
            return null;
        }
        EClass semanticType = getSemanticEClass(semanticAdapter);
        if (semanticType == null) {
            return null;
        }
        EObject semanticElement = getSemanticElement(semanticAdapter);
        int linkVID = BpmnVisualIDRegistry.getLinkWithClassVisualID(
                semanticElement, semanticType);
        switch (linkVID) {
        case SequenceEdgeEditPart.VISUAL_ID:
            return SequenceEdgeViewFactory.class;
        case MessagingEdgeEditPart.VISUAL_ID:
            return MessagingEdgeViewFactory.class;
        case AssociationEditPart.VISUAL_ID:
            return AssociationViewFactory.class;
        }
        return getUnrecognizedConnectorViewClass(semanticAdapter,
                containerView, semanticHint);
    }

    /**
     * @generated
     */
    private IElementType getSemanticElementType(IAdaptable semanticAdapter) {
        if (semanticAdapter == null) {
            return null;
        }
        return (IElementType) semanticAdapter.getAdapter(IElementType.class);
    }

    /**
     * @generated
     */
    private Class getUnrecognizedConnectorViewClass(IAdaptable semanticAdapter,
            View containerView, String semanticHint) {
        // Handle unrecognized child node classes here
        return null;
    }

}
