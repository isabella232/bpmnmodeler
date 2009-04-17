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
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.GetParserOperation;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserProvider;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.BpmnPackage;

import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityName2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.DataObjectName2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.DataObjectNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.GroupName2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.GroupNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.LaneNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.MessagingEdgeNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SequenceEdgeNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotationName2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotationNameEditPart;

import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;

/**
 * @generated
 */
public class BpmnParserProvider extends AbstractProvider implements
        IParserProvider {

    /**
     * @generated
     */
    private IParser activityActivityName_4001Parser;

    /**
     * @generated
     */
    private IParser getActivityActivityName_4001Parser() {
        if (activityActivityName_4001Parser == null) {
            activityActivityName_4001Parser = createActivityActivityName_4001Parser();
        }
        return activityActivityName_4001Parser;
    }

    /**
     * @generated
     */
    protected IParser createActivityActivityName_4001Parser() {
        BpmnStructuralFeatureParser parser = new BpmnStructuralFeatureParser(
                BpmnPackage.eINSTANCE.getNamedBpmnObject()
                        .getEStructuralFeature("name")); //$NON-NLS-1$
        return parser;
    }

    /**
     * @generated
     */
    private IParser subProcessSubProcessName_4006Parser;

    /**
     * @generated
     */
    private IParser getSubProcessSubProcessName_4006Parser() {
        if (subProcessSubProcessName_4006Parser == null) {
            subProcessSubProcessName_4006Parser = createSubProcessSubProcessName_4006Parser();
        }
        return subProcessSubProcessName_4006Parser;
    }

    /**
     * @generated
     */
    protected IParser createSubProcessSubProcessName_4006Parser() {
        BpmnStructuralFeatureParser parser = new BpmnStructuralFeatureParser(
                BpmnPackage.eINSTANCE.getNamedBpmnObject()
                        .getEStructuralFeature("name")); //$NON-NLS-1$
        return parser;
    }

    /**
     * @generated
     */
    private IParser activityActivityName_4002Parser;

    /**
     * @generated
     */
    private IParser getActivityActivityName_4002Parser() {
        if (activityActivityName_4002Parser == null) {
            activityActivityName_4002Parser = createActivityActivityName_4002Parser();
        }
        return activityActivityName_4002Parser;
    }

    /**
     * @generated
     */
    protected IParser createActivityActivityName_4002Parser() {
        BpmnStructuralFeatureParser parser = new BpmnStructuralFeatureParser(
                BpmnPackage.eINSTANCE.getNamedBpmnObject()
                        .getEStructuralFeature("name")); //$NON-NLS-1$
        return parser;
    }

    /**
     * @generated
     */
    private IParser textAnnotationTextAnnotationName_4003Parser;

    /**
     * @generated
     */
    private IParser getTextAnnotationTextAnnotationName_4003Parser() {
        if (textAnnotationTextAnnotationName_4003Parser == null) {
            textAnnotationTextAnnotationName_4003Parser = createTextAnnotationTextAnnotationName_4003Parser();
        }
        return textAnnotationTextAnnotationName_4003Parser;
    }

    /**
     * @generated
     */
    protected IParser createTextAnnotationTextAnnotationName_4003Parser() {
        BpmnStructuralFeatureParser parser = new BpmnStructuralFeatureParser(
                BpmnPackage.eINSTANCE.getNamedBpmnObject()
                        .getEStructuralFeature("name")); //$NON-NLS-1$
        return parser;
    }

    /**
     * @generated
     */
    private IParser dataObjectDataObjectName_4004Parser;

    /**
     * @generated
     */
    private IParser getDataObjectDataObjectName_4004Parser() {
        if (dataObjectDataObjectName_4004Parser == null) {
            dataObjectDataObjectName_4004Parser = createDataObjectDataObjectName_4004Parser();
        }
        return dataObjectDataObjectName_4004Parser;
    }

    /**
     * @generated
     */
    protected IParser createDataObjectDataObjectName_4004Parser() {
        BpmnStructuralFeatureParser parser = new BpmnStructuralFeatureParser(
                BpmnPackage.eINSTANCE.getNamedBpmnObject()
                        .getEStructuralFeature("name")); //$NON-NLS-1$
        return parser;
    }

    /**
     * @generated
     */
    private IParser groupGroupName_4005Parser;

    /**
     * @generated
     */
    private IParser getGroupGroupName_4005Parser() {
        if (groupGroupName_4005Parser == null) {
            groupGroupName_4005Parser = createGroupGroupName_4005Parser();
        }
        return groupGroupName_4005Parser;
    }

    /**
     * @generated
     */
    protected IParser createGroupGroupName_4005Parser() {
        BpmnStructuralFeatureParser parser = new BpmnStructuralFeatureParser(
                BpmnPackage.eINSTANCE.getNamedBpmnObject()
                        .getEStructuralFeature("name")); //$NON-NLS-1$
        return parser;
    }

    /**
     * @generated
     */
    private IParser laneLaneName_4007Parser;

    /**
     * @generated
     */
    private IParser getLaneLaneName_4007Parser() {
        if (laneLaneName_4007Parser == null) {
            laneLaneName_4007Parser = createLaneLaneName_4007Parser();
        }
        return laneLaneName_4007Parser;
    }

    /**
     * @generated
     */
    protected IParser createLaneLaneName_4007Parser() {
        BpmnStructuralFeatureParser parser = new BpmnStructuralFeatureParser(
                BpmnPackage.eINSTANCE.getNamedBpmnObject()
                        .getEStructuralFeature("name")); //$NON-NLS-1$
        return parser;
    }

    /**
     * @generated
     */
    private IParser poolPoolName_4008Parser;

    /**
     * @generated
     */
    private IParser getPoolPoolName_4008Parser() {
        if (poolPoolName_4008Parser == null) {
            poolPoolName_4008Parser = createPoolPoolName_4008Parser();
        }
        return poolPoolName_4008Parser;
    }

    /**
     * @generated
     */
    protected IParser createPoolPoolName_4008Parser() {
        BpmnStructuralFeatureParser parser = new BpmnStructuralFeatureParser(
                BpmnPackage.eINSTANCE.getNamedBpmnObject()
                        .getEStructuralFeature("name")); //$NON-NLS-1$
        return parser;
    }

    /**
     * @generated
     */
    private IParser textAnnotationTextAnnotationName_4009Parser;

    /**
     * @generated
     */
    private IParser getTextAnnotationTextAnnotationName_4009Parser() {
        if (textAnnotationTextAnnotationName_4009Parser == null) {
            textAnnotationTextAnnotationName_4009Parser = createTextAnnotationTextAnnotationName_4009Parser();
        }
        return textAnnotationTextAnnotationName_4009Parser;
    }

    /**
     * @generated
     */
    protected IParser createTextAnnotationTextAnnotationName_4009Parser() {
        BpmnStructuralFeatureParser parser = new BpmnStructuralFeatureParser(
                BpmnPackage.eINSTANCE.getNamedBpmnObject()
                        .getEStructuralFeature("name")); //$NON-NLS-1$
        return parser;
    }

    /**
     * @generated
     */
    private IParser dataObjectDataObjectName_4010Parser;

    /**
     * @generated
     */
    private IParser getDataObjectDataObjectName_4010Parser() {
        if (dataObjectDataObjectName_4010Parser == null) {
            dataObjectDataObjectName_4010Parser = createDataObjectDataObjectName_4010Parser();
        }
        return dataObjectDataObjectName_4010Parser;
    }

    /**
     * @generated
     */
    protected IParser createDataObjectDataObjectName_4010Parser() {
        BpmnStructuralFeatureParser parser = new BpmnStructuralFeatureParser(
                BpmnPackage.eINSTANCE.getNamedBpmnObject()
                        .getEStructuralFeature("name")); //$NON-NLS-1$
        return parser;
    }

    /**
     * @generated
     */
    private IParser groupGroupName_4011Parser;

    /**
     * @generated
     */
    private IParser getGroupGroupName_4011Parser() {
        if (groupGroupName_4011Parser == null) {
            groupGroupName_4011Parser = createGroupGroupName_4011Parser();
        }
        return groupGroupName_4011Parser;
    }

    /**
     * @generated
     */
    protected IParser createGroupGroupName_4011Parser() {
        BpmnStructuralFeatureParser parser = new BpmnStructuralFeatureParser(
                BpmnPackage.eINSTANCE.getNamedBpmnObject()
                        .getEStructuralFeature("name")); //$NON-NLS-1$
        return parser;
    }

    /**
     * @generated
     */
    private IParser sequenceEdgeSequenceEdgeName_4012Parser;

    /**
     * @generated
     */
    private IParser getSequenceEdgeSequenceEdgeName_4012Parser() {
        if (sequenceEdgeSequenceEdgeName_4012Parser == null) {
            sequenceEdgeSequenceEdgeName_4012Parser = createSequenceEdgeSequenceEdgeName_4012Parser();
        }
        return sequenceEdgeSequenceEdgeName_4012Parser;
    }

    /**
     * @generated
     */
    protected IParser createSequenceEdgeSequenceEdgeName_4012Parser() {
        BpmnStructuralFeatureParser parser = new BpmnStructuralFeatureParser(
                BpmnPackage.eINSTANCE.getNamedBpmnObject()
                        .getEStructuralFeature("name")); //$NON-NLS-1$
        return parser;
    }

    /**
     * @generated
     */
    private IParser messagingEdgeMessagingEdgeName_4013Parser;

    /**
     * @generated
     */
    private IParser getMessagingEdgeMessagingEdgeName_4013Parser() {
        if (messagingEdgeMessagingEdgeName_4013Parser == null) {
            messagingEdgeMessagingEdgeName_4013Parser = createMessagingEdgeMessagingEdgeName_4013Parser();
        }
        return messagingEdgeMessagingEdgeName_4013Parser;
    }

    /**
     * @generated
     */
    protected IParser createMessagingEdgeMessagingEdgeName_4013Parser() {
        BpmnStructuralFeatureParser parser = new BpmnStructuralFeatureParser(
                BpmnPackage.eINSTANCE.getNamedBpmnObject()
                        .getEStructuralFeature("name")); //$NON-NLS-1$
        return parser;
    }

    /**
     * @generated
     */
    protected IParser getParser(int visualID) {
        switch (visualID) {
        case ActivityNameEditPart.VISUAL_ID:
            return getActivityActivityName_4001Parser();
        case SubProcessNameEditPart.VISUAL_ID:
            return getSubProcessSubProcessName_4006Parser();
        case ActivityName2EditPart.VISUAL_ID:
            return getActivityActivityName_4002Parser();
        case TextAnnotationNameEditPart.VISUAL_ID:
            return getTextAnnotationTextAnnotationName_4003Parser();
        case DataObjectNameEditPart.VISUAL_ID:
            return getDataObjectDataObjectName_4004Parser();
        case GroupNameEditPart.VISUAL_ID:
            return getGroupGroupName_4005Parser();
        case LaneNameEditPart.VISUAL_ID:
            return getLaneLaneName_4007Parser();
        case PoolNameEditPart.VISUAL_ID:
            return getPoolPoolName_4008Parser();
        case TextAnnotationName2EditPart.VISUAL_ID:
            return getTextAnnotationTextAnnotationName_4009Parser();
        case DataObjectName2EditPart.VISUAL_ID:
            return getDataObjectDataObjectName_4010Parser();
        case GroupName2EditPart.VISUAL_ID:
            return getGroupGroupName_4011Parser();
        case SequenceEdgeNameEditPart.VISUAL_ID:
            return getSequenceEdgeSequenceEdgeName_4012Parser();
        case MessagingEdgeNameEditPart.VISUAL_ID:
            return getMessagingEdgeMessagingEdgeName_4013Parser();
        }
        return null;
    }

    /**
     * @generated
     */
    public IParser getParser(IAdaptable hint) {
        String vid = (String) hint.getAdapter(String.class);
        if (vid != null) {
            return getParser(BpmnVisualIDRegistry.getVisualID(vid));
        }
        View view = (View) hint.getAdapter(View.class);
        if (view != null) {
            return getParser(BpmnVisualIDRegistry.getVisualID(view));
        }
        return null;
    }

    /**
     * @generated
     */
    public boolean provides(IOperation operation) {
        if (operation instanceof GetParserOperation) {
            IAdaptable hint = ((GetParserOperation) operation).getHint();
            if (BpmnElementTypes.getElement(hint) == null) {
                return false;
            }
            return getParser(hint) != null;
        }
        return false;
    }
}
