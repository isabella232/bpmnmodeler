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
package org.eclipse.stp.bpmn.diagram.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.DataObject;
import org.eclipse.stp.bpmn.Group;
import org.eclipse.stp.bpmn.Lane;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.TextAnnotation;
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

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented 
 * by a domain model object.
 *
 * @generated
 */
public class BpmnVisualIDRegistry {

    /**
     * @generated
     */
    private static final String DEBUG_KEY = BpmnDiagramEditorPlugin
            .getInstance().getBundle().getSymbolicName()
            + "/debug/visualID"; //$NON-NLS-1$

    /**
     * @generated
     */
    public static int getVisualID(View view) {
        if (view instanceof Diagram) {
            if (BpmnDiagramEditPart.MODEL_ID.equals(view.getType())) {
                return BpmnDiagramEditPart.VISUAL_ID;
            } else {
                return -1;
            }
        }
        return getVisualID(view.getType());
    }

    /**
     * @generated
     */
    public static String getModelID(View view) {
        View diagram = view.getDiagram();
        while (view != diagram) {
            EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
            if (annotation != null) {
                return (String) annotation.getDetails().get("modelID"); //$NON-NLS-1$
            }
            view = (View) view.eContainer();
        }
        return diagram != null ? diagram.getType() : null;
    }

    /**
     * @generated
     */
    public static int getVisualID(String type) {
        try {
            return Integer.parseInt(type);
        } catch (NumberFormatException e) {
            if (Boolean.TRUE.toString().equalsIgnoreCase(
                    Platform.getDebugOption(DEBUG_KEY))) {
                BpmnDiagramEditorPlugin.getInstance().logError(
                        "Unable to parse view type as a visualID number: " //$NON-NLS-1$
                                + type);
            }
        }
        return -1;
    }

    /**
     * @generated
     */
    public static String getType(int visualID) {
        return String.valueOf(visualID);
    }

    /**
     * @generated
     */
    public static int getDiagramVisualID(EObject domainElement) {
        if (domainElement == null) {
            return -1;
        }
        EClass domainElementMetaclass = domainElement.eClass();
        return getDiagramVisualID(domainElement, domainElementMetaclass);
    }

    /**
     * @generated
     */
    private static int getDiagramVisualID(EObject domainElement,
            EClass domainElementMetaclass) {
        if (BpmnPackage.eINSTANCE.getBpmnDiagram().isSuperTypeOf(
                domainElementMetaclass)
                && isDiagramBpmnDiagram_79((BpmnDiagram) domainElement)) {
            return BpmnDiagramEditPart.VISUAL_ID;
        }
        return getUnrecognizedDiagramID(domainElement);
    }

    /**
     * @generated
     */
    public static int getNodeVisualID(View containerView, EObject domainElement) {
        if (domainElement == null) {
            return -1;
        }
        EClass domainElementMetaclass = domainElement.eClass();
        return getNodeVisualID(containerView, domainElement,
                domainElementMetaclass, null);
    }

    /**
     * @generated
     */
    public static int getNodeVisualID(View containerView,
            EObject domainElement, EClass domainElementMetaclass,
            String semanticHint) {
        String containerModelID = getModelID(containerView);
        if (!BpmnDiagramEditPart.MODEL_ID.equals(containerModelID)) {
            return -1;
        }
        int containerVisualID;
        if (BpmnDiagramEditPart.MODEL_ID.equals(containerModelID)) {
            containerVisualID = getVisualID(containerView);
        } else {
            if (containerView instanceof Diagram) {
                containerVisualID = BpmnDiagramEditPart.VISUAL_ID;
            } else {
                return -1;
            }
        }
        int nodeVisualID = semanticHint != null ? getVisualID(semanticHint)
                : -1;
        switch (containerVisualID) {
        case PoolEditPart.VISUAL_ID:
            if (PoolNameEditPart.VISUAL_ID == nodeVisualID) {
                return PoolNameEditPart.VISUAL_ID;
            }
            if (PoolPoolCompartmentEditPart.VISUAL_ID == nodeVisualID) {
                return PoolPoolCompartmentEditPart.VISUAL_ID;
            }
            return getUnrecognizedPool_1001ChildNodeID(domainElement,
                    semanticHint);
        case TextAnnotation2EditPart.VISUAL_ID:
            if (TextAnnotationName2EditPart.VISUAL_ID == nodeVisualID) {
                return TextAnnotationName2EditPart.VISUAL_ID;
            }
            return getUnrecognizedTextAnnotation_1002ChildNodeID(domainElement,
                    semanticHint);
        case DataObject2EditPart.VISUAL_ID:
            if (DataObjectName2EditPart.VISUAL_ID == nodeVisualID) {
                return DataObjectName2EditPart.VISUAL_ID;
            }
            return getUnrecognizedDataObject_1003ChildNodeID(domainElement,
                    semanticHint);
        case Group2EditPart.VISUAL_ID:
            if (GroupName2EditPart.VISUAL_ID == nodeVisualID) {
                return GroupName2EditPart.VISUAL_ID;
            }
            return getUnrecognizedGroup_1004ChildNodeID(domainElement,
                    semanticHint);
        case ActivityEditPart.VISUAL_ID:
            if (ActivityNameEditPart.VISUAL_ID == nodeVisualID) {
                return ActivityNameEditPart.VISUAL_ID;
            }
            return getUnrecognizedActivity_2001ChildNodeID(domainElement,
                    semanticHint);
        case SubProcessEditPart.VISUAL_ID:
            if (SubProcessNameEditPart.VISUAL_ID == nodeVisualID) {
                return SubProcessNameEditPart.VISUAL_ID;
            }
            if (SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID == nodeVisualID) {
                return SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID;
            }
            if (SubProcessSubProcessBorderCompartmentEditPart.VISUAL_ID == nodeVisualID) {
                return SubProcessSubProcessBorderCompartmentEditPart.VISUAL_ID;
            }
            return getUnrecognizedSubProcess_2002ChildNodeID(domainElement,
                    semanticHint);
        case Activity2EditPart.VISUAL_ID:
            if (ActivityName2EditPart.VISUAL_ID == nodeVisualID) {
                return ActivityName2EditPart.VISUAL_ID;
            }
            return getUnrecognizedActivity_2003ChildNodeID(domainElement,
                    semanticHint);
        case TextAnnotationEditPart.VISUAL_ID:
            if (TextAnnotationNameEditPart.VISUAL_ID == nodeVisualID) {
                return TextAnnotationNameEditPart.VISUAL_ID;
            }
            return getUnrecognizedTextAnnotation_2004ChildNodeID(domainElement,
                    semanticHint);
        case DataObjectEditPart.VISUAL_ID:
            if (DataObjectNameEditPart.VISUAL_ID == nodeVisualID) {
                return DataObjectNameEditPart.VISUAL_ID;
            }
            return getUnrecognizedDataObject_2005ChildNodeID(domainElement,
                    semanticHint);
        case GroupEditPart.VISUAL_ID:
            if (GroupNameEditPart.VISUAL_ID == nodeVisualID) {
                return GroupNameEditPart.VISUAL_ID;
            }
            return getUnrecognizedGroup_2006ChildNodeID(domainElement,
                    semanticHint);
        case LaneEditPart.VISUAL_ID:
            if (LaneNameEditPart.VISUAL_ID == nodeVisualID) {
                return LaneNameEditPart.VISUAL_ID;
            }
            return getUnrecognizedLane_2007ChildNodeID(domainElement,
                    semanticHint);
        case PoolPoolCompartmentEditPart.VISUAL_ID:
            if ((semanticHint == null || ActivityEditPart.VISUAL_ID == nodeVisualID)
                    && BpmnPackage.eINSTANCE.getActivity().isSuperTypeOf(
                            domainElementMetaclass)
                    && (domainElement == null || isNodeActivity_2001((Activity) domainElement))) {
                return ActivityEditPart.VISUAL_ID;
            }
            if ((semanticHint == null || SubProcessEditPart.VISUAL_ID == nodeVisualID)
                    && BpmnPackage.eINSTANCE.getSubProcess().isSuperTypeOf(
                            domainElementMetaclass)
                    && (domainElement == null || isNodeSubProcess_2002((SubProcess) domainElement))) {
                return SubProcessEditPart.VISUAL_ID;
            }
            if ((semanticHint == null || LaneEditPart.VISUAL_ID == nodeVisualID)
                    && BpmnPackage.eINSTANCE.getLane().isSuperTypeOf(
                            domainElementMetaclass)
                    && (domainElement == null || isNodeLane_2007((Lane) domainElement))) {
                return LaneEditPart.VISUAL_ID;
            }
            if ((semanticHint == null || TextAnnotationEditPart.VISUAL_ID == nodeVisualID)
                    && BpmnPackage.eINSTANCE.getTextAnnotation().isSuperTypeOf(
                            domainElementMetaclass)
                    && (domainElement == null || isNodeTextAnnotation_2004((TextAnnotation) domainElement))) {
                return TextAnnotationEditPart.VISUAL_ID;
            }
            if ((semanticHint == null || DataObjectEditPart.VISUAL_ID == nodeVisualID)
                    && BpmnPackage.eINSTANCE.getDataObject().isSuperTypeOf(
                            domainElementMetaclass)
                    && (domainElement == null || isNodeDataObject_2005((DataObject) domainElement))) {
                return DataObjectEditPart.VISUAL_ID;
            }
            if ((semanticHint == null || GroupEditPart.VISUAL_ID == nodeVisualID)
                    && BpmnPackage.eINSTANCE.getGroup().isSuperTypeOf(
                            domainElementMetaclass)
                    && (domainElement == null || isNodeGroup_2006((Group) domainElement))) {
                return GroupEditPart.VISUAL_ID;
            }
            return getUnrecognizedPoolPoolCompartment_5001ChildNodeID(
                    domainElement, semanticHint);
        case SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID:
            if ((semanticHint == null || ActivityEditPart.VISUAL_ID == nodeVisualID)
                    && BpmnPackage.eINSTANCE.getActivity().isSuperTypeOf(
                            domainElementMetaclass)
                    && (domainElement == null || isNodeActivity_2001((Activity) domainElement))) {
                return ActivityEditPart.VISUAL_ID;
            }
            if ((semanticHint == null || SubProcessEditPart.VISUAL_ID == nodeVisualID)
                    && BpmnPackage.eINSTANCE.getSubProcess().isSuperTypeOf(
                            domainElementMetaclass)
                    && (domainElement == null || isNodeSubProcess_2002((SubProcess) domainElement))) {
                return SubProcessEditPart.VISUAL_ID;
            }
            if ((semanticHint == null || TextAnnotationEditPart.VISUAL_ID == nodeVisualID)
                    && BpmnPackage.eINSTANCE.getTextAnnotation().isSuperTypeOf(
                            domainElementMetaclass)
                    && (domainElement == null || isNodeTextAnnotation_2004((TextAnnotation) domainElement))) {
                return TextAnnotationEditPart.VISUAL_ID;
            }
            if ((semanticHint == null || DataObjectEditPart.VISUAL_ID == nodeVisualID)
                    && BpmnPackage.eINSTANCE.getDataObject().isSuperTypeOf(
                            domainElementMetaclass)
                    && (domainElement == null || isNodeDataObject_2005((DataObject) domainElement))) {
                return DataObjectEditPart.VISUAL_ID;
            }
            if ((semanticHint == null || GroupEditPart.VISUAL_ID == nodeVisualID)
                    && BpmnPackage.eINSTANCE.getGroup().isSuperTypeOf(
                            domainElementMetaclass)
                    && (domainElement == null || isNodeGroup_2006((Group) domainElement))) {
                return GroupEditPart.VISUAL_ID;
            }
            return getUnrecognizedSubProcessSubProcessBodyCompartment_5002ChildNodeID(
                    domainElement, semanticHint);
        case SubProcessSubProcessBorderCompartmentEditPart.VISUAL_ID:
            if ((semanticHint == null || Activity2EditPart.VISUAL_ID == nodeVisualID)
                    && BpmnPackage.eINSTANCE.getActivity().isSuperTypeOf(
                            domainElementMetaclass)
                    && (domainElement == null || isNodeActivity_2003((Activity) domainElement))) {
                return Activity2EditPart.VISUAL_ID;
            }
            return getUnrecognizedSubProcessSubProcessBorderCompartment_5003ChildNodeID(
                    domainElement, semanticHint);
        case BpmnDiagramEditPart.VISUAL_ID:
            if ((semanticHint == null || PoolEditPart.VISUAL_ID == nodeVisualID)
                    && BpmnPackage.eINSTANCE.getPool().isSuperTypeOf(
                            domainElementMetaclass)
                    && (domainElement == null || isNodePool_1001((Pool) domainElement))) {
                return PoolEditPart.VISUAL_ID;
            }
            if ((semanticHint == null || TextAnnotation2EditPart.VISUAL_ID == nodeVisualID)
                    && BpmnPackage.eINSTANCE.getTextAnnotation().isSuperTypeOf(
                            domainElementMetaclass)
                    && (domainElement == null || isNodeTextAnnotation_1002((TextAnnotation) domainElement))) {
                return TextAnnotation2EditPart.VISUAL_ID;
            }
            if ((semanticHint == null || DataObject2EditPart.VISUAL_ID == nodeVisualID)
                    && BpmnPackage.eINSTANCE.getDataObject().isSuperTypeOf(
                            domainElementMetaclass)
                    && (domainElement == null || isNodeDataObject_1003((DataObject) domainElement))) {
                return DataObject2EditPart.VISUAL_ID;
            }
            if ((semanticHint == null || Group2EditPart.VISUAL_ID == nodeVisualID)
                    && BpmnPackage.eINSTANCE.getGroup().isSuperTypeOf(
                            domainElementMetaclass)
                    && (domainElement == null || isNodeGroup_1004((Group) domainElement))) {
                return Group2EditPart.VISUAL_ID;
            }
            return getUnrecognizedBpmnDiagram_79ChildNodeID(domainElement,
                    semanticHint);
        case SequenceEdgeEditPart.VISUAL_ID:
            if (SequenceEdgeNameEditPart.VISUAL_ID == nodeVisualID) {
                return SequenceEdgeNameEditPart.VISUAL_ID;
            }
            return getUnrecognizedSequenceEdge_3001LinkLabelID(semanticHint);
        case MessagingEdgeEditPart.VISUAL_ID:
            if (MessagingEdgeNameEditPart.VISUAL_ID == nodeVisualID) {
                return MessagingEdgeNameEditPart.VISUAL_ID;
            }
            return getUnrecognizedMessagingEdge_3002LinkLabelID(semanticHint);
        }
        return -1;
    }

    /**
     * @generated
     */
    public static int getLinkWithClassVisualID(EObject domainElement) {
        if (domainElement == null) {
            return -1;
        }
        EClass domainElementMetaclass = domainElement.eClass();
        return getLinkWithClassVisualID(domainElement, domainElementMetaclass);
    }

    /**
     * @generated
     */
    public static int getLinkWithClassVisualID(EObject domainElement,
            EClass domainElementMetaclass) {
        if (BpmnPackage.eINSTANCE.getSequenceEdge().isSuperTypeOf(
                domainElementMetaclass)
                && (domainElement == null || isLinkWithClassSequenceEdge_3001((SequenceEdge) domainElement))) {
            return SequenceEdgeEditPart.VISUAL_ID;
        } else if (BpmnPackage.eINSTANCE.getMessagingEdge().isSuperTypeOf(
                domainElementMetaclass)
                && (domainElement == null || isLinkWithClassMessagingEdge_3002((MessagingEdge) domainElement))) {
            return MessagingEdgeEditPart.VISUAL_ID;
        } else if (BpmnPackage.eINSTANCE.getAssociation().isSuperTypeOf(
                domainElementMetaclass)
                && (domainElement == null || isLinkWithClassAssociation_3003((Association) domainElement))) {
            return AssociationEditPart.VISUAL_ID;
        } else {
            return getUnrecognizedLinkWithClassID(domainElement);
        }
    }

    /**
     * User can change implementation of this method to check some additional 
     * conditions here.
     *
     * @generated
     */
    private static boolean isDiagramBpmnDiagram_79(BpmnDiagram element) {
        return true;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedDiagramID(EObject domainElement) {
        return -1;
    }

    /**
     * User can change implementation of this method to check some additional 
     * conditions here.
     *
     * @generated
     */
    private static boolean isNodePool_1001(Pool element) {
        return true;
    }

    /**
     * User can change implementation of this method to check some additional 
     * conditions here.
     *
     * @generated
     */
    private static boolean isNodeTextAnnotation_1002(TextAnnotation element) {
        return true;
    }

    /**
     * User can change implementation of this method to check some additional 
     * conditions here.
     *
     * @generated
     */
    private static boolean isNodeDataObject_1003(DataObject element) {
        return true;
    }

    /**
     * User can change implementation of this method to check some additional 
     * conditions here.
     *
     * @generated
     */
    private static boolean isNodeGroup_1004(Group element) {
        return true;
    }

    /**
     * User can change implementation of this method to check some additional 
     * conditions here.
     *
     * @generated
     */
    private static boolean isNodeActivity_2001(Activity element) {
        return true;
    }

    /**
     * User can change implementation of this method to check some additional 
     * conditions here.
     *
     * @generated
     */
    private static boolean isNodeSubProcess_2002(SubProcess element) {
        return true;
    }

    /**
     * User can change implementation of this method to check some additional 
     * conditions here.
     *
     * @generated
     */
    private static boolean isNodeActivity_2003(Activity element) {
        return true;
    }

    /**
     * User can change implementation of this method to check some additional 
     * conditions here.
     *
     * @generated
     */
    private static boolean isNodeTextAnnotation_2004(TextAnnotation element) {
        return true;
    }

    /**
     * User can change implementation of this method to check some additional 
     * conditions here.
     *
     * @generated
     */
    private static boolean isNodeDataObject_2005(DataObject element) {
        return true;
    }

    /**
     * User can change implementation of this method to check some additional 
     * conditions here.
     *
     * @generated
     */
    private static boolean isNodeGroup_2006(Group element) {
        return true;
    }

    /**
     * User can change implementation of this method to check some additional 
     * conditions here.
     *
     * @generated
     */
    private static boolean isNodeLane_2007(Lane element) {
        return true;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedPool_1001ChildNodeID(
            EObject domainElement, String semanticHint) {
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedTextAnnotation_1002ChildNodeID(
            EObject domainElement, String semanticHint) {
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedDataObject_1003ChildNodeID(
            EObject domainElement, String semanticHint) {
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedGroup_1004ChildNodeID(
            EObject domainElement, String semanticHint) {
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedActivity_2001ChildNodeID(
            EObject domainElement, String semanticHint) {
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedSubProcess_2002ChildNodeID(
            EObject domainElement, String semanticHint) {
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedActivity_2003ChildNodeID(
            EObject domainElement, String semanticHint) {
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedTextAnnotation_2004ChildNodeID(
            EObject domainElement, String semanticHint) {
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedDataObject_2005ChildNodeID(
            EObject domainElement, String semanticHint) {
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedGroup_2006ChildNodeID(
            EObject domainElement, String semanticHint) {
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedLane_2007ChildNodeID(
            EObject domainElement, String semanticHint) {
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedPoolPoolCompartment_5001ChildNodeID(
            EObject domainElement, String semanticHint) {
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedSubProcessSubProcessBodyCompartment_5002ChildNodeID(
            EObject domainElement, String semanticHint) {
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedSubProcessSubProcessBorderCompartment_5003ChildNodeID(
            EObject domainElement, String semanticHint) {
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedBpmnDiagram_79ChildNodeID(
            EObject domainElement, String semanticHint) {
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedSequenceEdge_3001LinkLabelID(
            String semanticHint) {
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedMessagingEdge_3002LinkLabelID(
            String semanticHint) {
        return -1;
    }

    /**
     * User can change implementation of this method to handle some specific
     * situations not covered by default logic.
     *
     * @generated
     */
    private static int getUnrecognizedLinkWithClassID(EObject domainElement) {
        return -1;
    }

    /**
     * User can change implementation of this method to check some additional 
     * conditions here.
     *
     * @generated
     */
    private static boolean isLinkWithClassSequenceEdge_3001(SequenceEdge element) {
        return true;
    }

    /**
     * User can change implementation of this method to check some additional 
     * conditions here.
     *
     * @generated
     */
    private static boolean isLinkWithClassMessagingEdge_3002(
            MessagingEdge element) {
        return true;
    }

    /**
     * User can change implementation of this method to check some additional 
     * conditions here.
     *
     * @generated
     */
    private static boolean isLinkWithClassAssociation_3003(Association element) {
        return true;
    }
}
