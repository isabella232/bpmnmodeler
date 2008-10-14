/******************************************************************************
 * Copyright (c) 2006, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************/

package org.eclipse.stp.bpmn.diagram.providers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.commands.ElementTypeEx;
import org.eclipse.stp.bpmn.commands.IElementTypeEx;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;

/**
 * 
 * Helper collections.
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BPMNElementTypesActivities {

    private static Collection<IElementTypeEx> TypesForSubProcessBorder;
    private static Collection<IElementTypeEx> TypesForPoolAndSubProcessBody;
    
    private static Collection<IElementTypeEx> TypesForSequenceEdgeSource;
    private static Collection<IElementTypeEx> TypesForSequenceEdgeTarget;
    private static Collection<IElementTypeEx> TypesForMessagingEdgeSource;
    private static Collection<IElementTypeEx> TypesForMessagingEdgeTarget;
    private static Collection<IElementType> TypesForAssociationTarget;
    private static Collection<IElementType> TypesForAssociationTargetSource;

    /**
     * @param excludeFCTHandlers true to no include the FCT-handlers (fautl-compensate-termninate)
     * This is the case for sub-processes that are event handlers.
     * @return
     */
    public static Collection<IElementTypeEx> getElementTypesForSubProcessBorder() {//boolean excludeFCTHandlers) {

        if (TypesForSubProcessBorder == null) {
            TypesForSubProcessBorder = Collections
                    .unmodifiableCollection(Arrays
                            .asList(new IElementTypeEx[] {
                                    ElementTypeEx
                                            .wrap(
                                                    BpmnElementTypes.Activity_2003,
                                                    ActivityType.EVENT_INTERMEDIATE_COMPENSATION_LITERAL
                                                            .getLiteral()),
                                    ElementTypeEx
                                            .wrap(
                                                    BpmnElementTypes.Activity_2003,
                                                    ActivityType.EVENT_INTERMEDIATE_ERROR_LITERAL
                                                            .getLiteral()),
                                    ElementTypeEx
                                            .wrap(
                                                    BpmnElementTypes.Activity_2003,
                                                    ActivityType.EVENT_INTERMEDIATE_TIMER_LITERAL
                                                            .getLiteral()) }

                            ));
        }

        return TypesForSubProcessBorder;
    }

    /**
     * TODO: put them in the order you want to see them
     * 
     * @return
     */
    public static Collection<IElementTypeEx> getElementTypesForPoolAndSubProcessBody() {
        if (TypesForPoolAndSubProcessBody == null) {
            TypesForPoolAndSubProcessBody = new ArrayList<IElementTypeEx>();
            for (Object at : ActivityType.VALUES) {
                String name = ((ActivityType) at).getLiteral();
                IElementType elementType;
                if ("SubProcess".equals(name)) { //$NON-NLS-1$
                    elementType = BpmnElementTypes.SubProcess_2002;
                } else {
                    elementType = BpmnElementTypes.Activity_2001;
                }
                TypesForPoolAndSubProcessBody.add(ElementTypeEx.wrap(
                        elementType, name));
            }
            TypesForPoolAndSubProcessBody.add(ElementTypeEx.wrap(
                    BpmnElementTypes.Activity_2001,
                    BpmnPackage.Literals.ACTIVITY__LOOPING.getName(),
                    BpmnDiagramMessages.bind(
                            BpmnDiagramMessages.BpmnPaletteFactory_looping_label, 
                            BpmnDiagramMessages.BPMNElementTypesActivities_activity_label)));
            TypesForPoolAndSubProcessBody.add(ElementTypeEx.wrap(
                    BpmnElementTypes.SubProcess_2002,
                    BpmnPackage.Literals.ACTIVITY__LOOPING.getName(),
                    BpmnDiagramMessages.bind(
                            BpmnDiagramMessages.BpmnPaletteFactory_looping_label, 
                            BpmnDiagramMessages.BPMNElementTypesActivities_sub_process_label)));
        }
        return TypesForPoolAndSubProcessBody;
    }

    /**
     * TODO: put them in the order you want to see them
     * 
     * @return
     */
    public static Collection<IElementTypeEx> getElementTypesForSequenceEdgeSource() {
        if (TypesForSequenceEdgeSource == null) {
            TypesForSequenceEdgeSource = new ArrayList<IElementTypeEx>();
            for (Object at : ActivityType.VALUES) {
                ActivityType attype = (ActivityType)at;
                String name = attype.getLiteral();
                if (name.startsWith("EventEnd")) { //$NON-NLS-1$
                    continue;
                }
                IElementType elementType;
                if ("SubProcess".equals(name)) { //$NON-NLS-1$
                    elementType = BpmnElementTypes.SubProcess_2002;
                } else {
                    elementType = BpmnElementTypes.Activity_2001;
                }
                TypesForSequenceEdgeSource.add(ElementTypeEx.wrap(
                        elementType, name));
            }
            TypesForSequenceEdgeSource.add(ElementTypeEx.wrap(
                    BpmnElementTypes.Activity_2001,
                    BpmnPackage.Literals.ACTIVITY__LOOPING.getName(),
                    BpmnDiagramMessages.bind(
                            BpmnDiagramMessages.BpmnPaletteFactory_looping_label, 
                            BpmnDiagramMessages.BPMNElementTypesActivities_activity_label)));
            TypesForSequenceEdgeSource.add(ElementTypeEx.wrap(
                    BpmnElementTypes.SubProcess_2002,
                    BpmnPackage.Literals.ACTIVITY__LOOPING.getName(),
                    BpmnDiagramMessages.bind(
                            BpmnDiagramMessages.BpmnPaletteFactory_looping_label, 
                            BpmnDiagramMessages.BPMNElementTypesActivities_sub_process_label)));
        }
        return TypesForSequenceEdgeSource;
    }
    /**
     * TODO: put them in the order you want to see them
     * 
     * @return
     */
    public static Collection<IElementTypeEx> getElementTypesForSequenceEdgeTarget() {
        if (TypesForSequenceEdgeTarget == null) {
            TypesForSequenceEdgeTarget = new ArrayList<IElementTypeEx>();
            for (Object at : ActivityType.VALUES) {
                ActivityType attype = (ActivityType)at;
                String name = attype.getLiteral();
                if (name.startsWith("EventStart")) { //$NON-NLS-1$
                    continue;
                }
                IElementType elementType;
                if ("SubProcess".equals(name)) { //$NON-NLS-1$
                    elementType = BpmnElementTypes.SubProcess_2002;
                } else {
                    elementType = BpmnElementTypes.Activity_2001;
                }
                TypesForSequenceEdgeTarget.add(ElementTypeEx.wrap(
                        elementType, name));
            }
            TypesForSequenceEdgeTarget.add(ElementTypeEx.wrap(
                    BpmnElementTypes.Activity_2001,
                    BpmnPackage.Literals.ACTIVITY__LOOPING.getName(),
                    BpmnDiagramMessages.bind(
                            BpmnDiagramMessages.BpmnPaletteFactory_looping_label, 
                            BpmnDiagramMessages.BPMNElementTypesActivities_activity_label)));
            TypesForSequenceEdgeTarget.add(ElementTypeEx.wrap(
                    BpmnElementTypes.SubProcess_2002,
                    BpmnPackage.Literals.ACTIVITY__LOOPING.getName(),
                    BpmnDiagramMessages.bind(
                            BpmnDiagramMessages.BpmnPaletteFactory_looping_label, 
                            BpmnDiagramMessages.BPMNElementTypesActivities_sub_process_label)));
        }
        return TypesForSequenceEdgeTarget;
    }

    /**
     * TODO: put them in the order you want to see them
     * 
     * @return
     */
    public static Collection<IElementTypeEx> getElementTypesForMessagingEdgeTarget() {
        if (TypesForMessagingEdgeTarget == null) {
            TypesForMessagingEdgeTarget = new ArrayList<IElementTypeEx>();
            for (Object at : ActivityType.VALUES) {
                ActivityType attype = (ActivityType)at;
                String name = attype.getLiteral();
                if (name.startsWith("Gateway")) { //$NON-NLS-1$
                    continue;
                }
                switch (attype.getValue()) {
                case ActivityType.EVENT_END_EMPTY:
                case ActivityType.EVENT_START_EMPTY:
                case ActivityType.EVENT_INTERMEDIATE_EMPTY:
                case ActivityType.EVENT_END_TERMINATE:
                case ActivityType.SUB_PROCESS:
                    continue;
                }
                TypesForMessagingEdgeTarget.add(ElementTypeEx.wrap(
                        BpmnElementTypes.Activity_2001, name));
            }
            TypesForMessagingEdgeTarget.add(ElementTypeEx.wrap(
                    BpmnElementTypes.Activity_2001,
                    BpmnPackage.Literals.ACTIVITY__LOOPING.getName(),
                    BpmnDiagramMessages.bind(
                            BpmnDiagramMessages.BpmnPaletteFactory_looping_label, 
                            BpmnDiagramMessages.BPMNElementTypesActivities_activity_label)));
        }
        return TypesForMessagingEdgeTarget;
    }

    /**
     * TODO: put them in the order you want to see them
     * 
     * @return
     */
    public static Collection<IElementTypeEx> getElementTypesForMessagingEdgeSource() {
        if (TypesForMessagingEdgeSource == null) {
            TypesForMessagingEdgeSource = new ArrayList<IElementTypeEx>();
            for (Object at : ActivityType.VALUES) {
                ActivityType attype = (ActivityType)at;
                String name = attype.getLiteral();
                if (name.startsWith("Gateway")) { //$NON-NLS-1$
                    continue;
                }
                switch (attype.getValue()) {
                case ActivityType.EVENT_END_EMPTY:
                case ActivityType.EVENT_START_EMPTY:
                case ActivityType.EVENT_INTERMEDIATE_EMPTY:
                case ActivityType.EVENT_END_TERMINATE:
                case ActivityType.EVENT_END_LINK:
                case ActivityType.SUB_PROCESS:
                    continue;
                }
                TypesForMessagingEdgeSource.add(ElementTypeEx.wrap(
                        BpmnElementTypes.Activity_2001, name));
            }
            TypesForMessagingEdgeSource.add(ElementTypeEx.wrap(
                    BpmnElementTypes.Activity_2001,
                    BpmnPackage.Literals.ACTIVITY__LOOPING.getName(),
                    BpmnDiagramMessages.bind(
                            BpmnDiagramMessages.BpmnPaletteFactory_looping_label, 
                            BpmnDiagramMessages.BPMNElementTypesActivities_activity_label)));
        }
        return TypesForMessagingEdgeSource;
    }
    
    /**
     * @return The collection of types appropriate for the source of an association:
     * any identifiable node: activity, sub-process and pool.
     */
    public static Collection<IElementType> getElementTypesForAssociationTarget() {
        if (TypesForAssociationTarget == null) {
            TypesForAssociationTarget = new ArrayList<IElementType>();
            for (Object at : ActivityType.VALUES) {
                ActivityType attype = (ActivityType)at;
                if (attype.getValue() != ActivityType.SUB_PROCESS) {
                    TypesForAssociationTarget.add(ElementTypeEx.wrap(
                            BpmnElementTypes.Activity_2001, attype.getName()));
                }
            }
            TypesForAssociationTarget.add(
                    BpmnElementTypes.SubProcess_2002);
            TypesForAssociationTarget.add(ElementTypeEx.wrap(
                    BpmnElementTypes.SubProcess_2002,
                    BpmnPackage.Literals.ACTIVITY__LOOPING.getName(),
                    BpmnDiagramMessages.bind(
                            BpmnDiagramMessages.BpmnPaletteFactory_looping_label, 
                            BpmnDiagramMessages.BPMNElementTypesActivities_sub_process_label)));
            TypesForAssociationTarget.add(ElementTypeEx.wrap(
                    BpmnElementTypes.Activity_2001,
                    BpmnPackage.Literals.ACTIVITY__LOOPING.getName(),
                    BpmnDiagramMessages.bind(
                            BpmnDiagramMessages.BpmnPaletteFactory_looping_label, 
                            BpmnDiagramMessages.BPMNElementTypesActivities_activity_label)));
        }
        return TypesForAssociationTarget;
    }
    /**
     * @return The collection of types appropriate for the source of an association
     */
    public static Collection<IElementType> getElementTypesForAssociationSource() {
        if (TypesForAssociationTargetSource == null) {
            TypesForAssociationTargetSource = new ArrayList<IElementType>();
//            for (Object at : ActivityType.VALUES) {
//                ActivityType attype = (ActivityType)at;
//                TypesForAssociationTargetSource.add(ElementTypeEx.wrap(
//                        BpmnElementTypes.Activity_2001, attype.getName()));
//            }
            TypesForAssociationTargetSource.add(BpmnElementTypes.DataObject_1003);
            TypesForAssociationTargetSource.add(BpmnElementTypes.DataObject_2005);
            TypesForAssociationTargetSource.add(BpmnElementTypes.Group_1004);
            TypesForAssociationTargetSource.add(BpmnElementTypes.Group_2006);
            TypesForAssociationTargetSource.add(BpmnElementTypes.TextAnnotation_1002);
            TypesForAssociationTargetSource.add(BpmnElementTypes.TextAnnotation_2004);
        }
        return TypesForAssociationTargetSource;
    }

    
}
