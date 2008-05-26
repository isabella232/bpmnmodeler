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

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IPrimaryEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.palette.PaletteService;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.commands.ElementTypeEx;
import org.eclipse.stp.bpmn.commands.IElementTypeEx;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;
import org.eclipse.stp.bpmn.palette.IStickableToolEntry;
import org.eclipse.stp.bpmn.provider.BpmnEditPlugin;
import org.eclipse.stp.bpmn.tools.MultipleShapesMoveTool;
import org.eclipse.stp.bpmn.tools.SelectionToolEx;
import org.eclipse.stp.bpmn.tools.UnspecifiedActivityTypeCreationToolEx;
import org.osgi.framework.Bundle;

/**
 * @generated
 */
@SuppressWarnings("unchecked") //$NON-NLS-1$
public class BpmnPaletteFactory {


    /**
     * @generated
     */
    public void fillPaletteGen(PaletteRoot paletteRoot) {
        paletteRoot.add(createbpmn1Group());
        //paletteRoot.add(createEventShapes2Group());
        paletteRoot.add(createGatewayShapes3Group());
        paletteRoot.add(createArtifacts4Group());
    }
    /**
     * 
     * 
     * @param paletteRoot
     * @notgenenated customizes the default entry to use the customized
     * select-tool. TODO: find a better place to do this so we don't need to
     * use introspection.
     */
    public void fillPalette(PaletteRoot paletteRoot) {
        PaletteEntry pe = null;
        for (Object paletteEntry : paletteRoot.getChildren()) {
            pe = (PaletteEntry)paletteEntry;
            if (pe.getId().equals(PaletteService.GROUP_STANDARD)) {
                break;
            }
            pe = null;
        }
        if (pe != null) {
            setupStandardPaletteEntries(pe);
        }
        paletteRoot.add(createbpmn1Group());
        paletteRoot.add(createEventShapes21Group());
        paletteRoot.add(createEventShapes22Group());
        paletteRoot.add(createEventShapes23Group());
        paletteRoot.add(createGatewayShapes3Group());
        paletteRoot.add(createArtifacts4Group());

        ToolEntry toolEntry = paletteRoot.getDefaultEntry();
        SelectionToolEx.setToolClass(toolEntry, SelectionToolEx.class);
    }
    /**
     * @see PaletteService
     * @param pe The palette entry that contains the default selection tool,
     * the zoom and the note attachment tool. We subsitute them by BPMN tools.
     */
    protected void setupStandardPaletteEntries(PaletteEntry pe) {
        PaletteContainer standard = (PaletteContainer) pe;
        ImageDescriptor smallImage = findSmallToolImgDescr(MOVE_HALF_EAST);
        ImageDescriptor largeImage = findLargeToolImgDescr(MOVE_HALF_EAST);
        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_insert_space_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_insert_space_description, smallImage, 
                largeImage, MultipleShapesMoveTool.class);
        standard.add(result);
    }

    /**
     * @notgenerated
     */
    private PaletteContainer createbpmn1Group() {
        PaletteContainer paletteContainer = new PaletteDrawer(BpmnDiagramMessages.BpmnPaletteFactory_basic_bpmn_drawer_label);
        ((PaletteDrawer) paletteContainer).setInitialState(PaletteDrawer.INITIAL_STATE_OPEN);
        paletteContainer.setDescription(BpmnDiagramMessages.BpmnPaletteFactory_basic_bpmn_drawer_description);
        paletteContainer.add(createTextAnnotation2CreationTool());
        paletteContainer.add(createTask1CreationTool());
        paletteContainer.add(createLoopingTask1CreationTool());
        paletteContainer.add(createEdgeConnection2CreationTool());
        paletteContainer.add(createMessageConnection5CreationTool());
        paletteContainer.add(createAssociation1CreationTool());
        paletteContainer.add(createPool3CreationTool());
        paletteContainer.add(createSubProcess4CreationTool());
        paletteContainer.add(createSubProcessLooping5CreationTool());
        paletteContainer.add(createLane6CreationTool());
        return paletteContainer;
    }

    /**
     * @generated
     */
    private PaletteContainer createEventShapes2Group() {
        PaletteContainer paletteContainer = new PaletteDrawer(BpmnDiagramMessages.BpmnPaletteFactory_event_shapes_label);
        paletteContainer.setDescription(BpmnDiagramMessages.BpmnPaletteFactory_event_shapes_description);
        paletteContainer.add(createStartEmpty1CreationTool());
        paletteContainer.add(createStartMessage2CreationTool());
        paletteContainer.add(createStartRule3CreationTool());
        paletteContainer.add(createIntermediateEmpty4CreationTool());
        paletteContainer.add(createIntermediateMessage5CreationTool());
        paletteContainer.add(createIntermediateTimer6CreationTool());
        paletteContainer.add(createIntermediateError7CreationTool());
        paletteContainer.add(createIntermediateCompensation8CreationTool());
        paletteContainer.add(createIntermediateRule9CreationTool());
        paletteContainer.add(createEndEmpty10CreationTool());
        paletteContainer.add(createEndMessage11CreationTool());
        paletteContainer.add(createEndError12CreationTool());
        paletteContainer.add(createEndCompensation13CreationTool());
        paletteContainer.add(createEndTerminate14CreationTool());
        return paletteContainer;
    }
    /**
     * @generated NOT todo: re-do this through the gmftool file.
     */
    private PaletteContainer createEventShapes21Group() {
        PaletteContainer paletteContainer1 = new PaletteDrawer(BpmnDiagramMessages.BpmnPaletteFactory_start_events_label);
        ((PaletteDrawer) paletteContainer1).setInitialState(PaletteDrawer.INITIAL_STATE_CLOSED);
        paletteContainer1.setDescription(BpmnDiagramMessages.BpmnPaletteFactory_start_events_description);
        paletteContainer1.add(createStartEmpty1CreationTool());
        paletteContainer1.add(createStartMessage2CreationTool());
        paletteContainer1.add(createStartRule3CreationTool());
        paletteContainer1.add(createStartTimer4CreationTool());
        paletteContainer1.add(createStartSignal5CreationTool());
        paletteContainer1.add(createStartMultiple6CreationTool());
        paletteContainer1.add(createStartLink7CreationTool());
        return paletteContainer1;
    }
    
    /**
     * @generated NOT
     */
    private PaletteEntry createStartLink7CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(START_LINK);
        ImageDescriptor largeImage = findLargeImgDescr(START_LINK);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx
        .wrap(
                BpmnElementTypes.Activity_2001,
                ActivityType.EVENT_START_LINK_LITERAL.getLiteral());
        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_link_start_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_link_start_tooltip, smallImage, largeImage,
                elementTypes) {
        };

        return result;
    }
    
    /**
     * @generated NOT
     */
    private PaletteEntry createStartMultiple6CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(START_MULTIPLE);
        ImageDescriptor largeImage = findLargeImgDescr(START_MULTIPLE);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx
        .wrap(
                BpmnElementTypes.Activity_2001,
                ActivityType.EVENT_START_MULTIPLE_LITERAL.getLiteral());
        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_multiple_start_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_multiple_start_tooltip, smallImage, largeImage,
                elementTypes) {
        };

        return result;
    }
    /**
     * @generated NOT
     */
    private PaletteEntry createStartTimer4CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(START_TIMER);
        ImageDescriptor largeImage = findLargeImgDescr(START_TIMER);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx
        .wrap(
                BpmnElementTypes.Activity_2001,
                ActivityType.EVENT_START_TIMER_LITERAL.getLiteral());
        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_timer_start_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_timer_start_description, smallImage, largeImage,
                elementTypes) {
        };

        return result;
    }
    
    /**
     * @generated NOT
     */
    private PaletteEntry createStartSignal5CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(START_SIGNAL);
        ImageDescriptor largeImage = findLargeImgDescr(START_SIGNAL);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx
        .wrap(
                BpmnElementTypes.Activity_2001,
                ActivityType.EVENT_START_SIGNAL_LITERAL.getLiteral());
        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_signal_start_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_signal_start_tooltip, smallImage, largeImage,
                elementTypes) {
        };

        return result;
    }
    /**
     * @generated NOT todo: re-do this through the gmftool file.
     */
    private PaletteContainer createEventShapes22Group() {
        PaletteContainer paletteContainer2 = new PaletteDrawer(BpmnDiagramMessages.BpmnPaletteFactory_inter_events_label);
        ((PaletteDrawer) paletteContainer2).setInitialState(PaletteDrawer.INITIAL_STATE_CLOSED);
        paletteContainer2.setDescription(BpmnDiagramMessages.BpmnPaletteFactory_inter_events_description);
        paletteContainer2.add(createIntermediateEmpty4CreationTool());
        paletteContainer2.add(createIntermediateMessage5CreationTool());
        paletteContainer2.add(createIntermediateTimer6CreationTool());
        paletteContainer2.add(createIntermediateError7CreationTool());
        paletteContainer2.add(createIntermediateCompensation8CreationTool());
        paletteContainer2.add(createIntermediateRule9CreationTool());
        paletteContainer2.add(createIntermediateCancel10CreationTool());
        paletteContainer2.add(createIntermediateSignal11CreationTool());
        paletteContainer2.add(createIntermediateMultiple12CreationTool());
        paletteContainer2.add(createIntermediateLink13CreationTool());
        return paletteContainer2;
    }
    
    /**
     * @generated NOT
     */
    private PaletteEntry createIntermediateLink13CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(INTERMEDIATE_LINK);
        ImageDescriptor largeImage = findLargeImgDescr(INTERMEDIATE_LINK);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                BpmnElementTypes.Activity_2001,
                ActivityType.EVENT_INTERMEDIATE_LINK_LITERAL.getLiteral());
        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_link_intermediate_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_link_intermediate_tooltip, smallImage, largeImage,
                elementTypes) {
        };
        
        return result;
    }
    
    /**
     * @generated NOT
     */
    private PaletteEntry createIntermediateMultiple12CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(INTERMEDIATE_MULTIPLE);
        ImageDescriptor largeImage = findLargeImgDescr(INTERMEDIATE_MULTIPLE);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                BpmnElementTypes.Activity_2001,
                ActivityType.EVENT_INTERMEDIATE_MULTIPLE_LITERAL.getLiteral());
        elementTypes.add(activity);
        elementTypes.add(ElementTypeEx.wrap(
                BpmnElementTypes.Activity_2003,
                ActivityType.EVENT_INTERMEDIATE_MULTIPLE_LITERAL.getLiteral()));

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_multiple_intermediate_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_multiple_intermediate_tooltip, smallImage, largeImage,
                elementTypes) {
        };
        
        return result;
    }
    /**
     * @generated NOT todo: re-do this through the gmftool file.
     */
    private PaletteContainer createEventShapes23Group() {
        PaletteContainer paletteContainer3 = new PaletteDrawer(BpmnDiagramMessages.BpmnPaletteFactory_end_events_label);
        ((PaletteDrawer) paletteContainer3).setInitialState(PaletteDrawer.INITIAL_STATE_CLOSED);
        paletteContainer3.setDescription(BpmnDiagramMessages.BpmnPaletteFactory_end_events_description);
        paletteContainer3.add(createEndEmpty10CreationTool());
        paletteContainer3.add(createEndMessage11CreationTool());
        paletteContainer3.add(createEndError12CreationTool());
        paletteContainer3.add(createEndCompensation13CreationTool());
        paletteContainer3.add(createEndTerminate14CreationTool());
        paletteContainer3.add(createEndSignal15CreationTool());
        paletteContainer3.add(createEndMultiple16CreationTool());
        paletteContainer3.add(createEndCancel17CreationTool());
        paletteContainer3.add(createEndLink18CreationTool());
        return paletteContainer3;
    }

    private PaletteEntry createEndLink18CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(END_LINK);
        ImageDescriptor largeImage = findLargeImgDescr(END_LINK);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                BpmnElementTypes.Activity_2001,
                ActivityType.EVENT_END_LINK_LITERAL.getLiteral());
        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_link_end_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_link_end_tooltip, smallImage, largeImage,
                elementTypes) {
        };
        
        return result;
    }
    
    private PaletteEntry createEndCancel17CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(END_CANCEL);
        ImageDescriptor largeImage = findLargeImgDescr(END_CANCEL);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                BpmnElementTypes.Activity_2001,
                ActivityType.EVENT_END_CANCEL_LITERAL.getLiteral());
        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_cancel_end_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_cancel_end_tooltip, smallImage, largeImage,
                elementTypes) {
        };
        
        return result;
    }
    /**
     * @generated NOT
     */
    private PaletteEntry createEndMultiple16CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(END_MULTIPLE);
        ImageDescriptor largeImage = findLargeImgDescr(END_MULTIPLE);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                BpmnElementTypes.Activity_2001,
                ActivityType.EVENT_END_MULTIPLE_LITERAL.getLiteral());
        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_multiple_end_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_multiple_end_tooltip, smallImage, largeImage,
                elementTypes) {
        };
        
        return result;
    }
    /**
     * @generated NOT
     */
    private PaletteContainer createGatewayShapes3Group() {
        PaletteContainer paletteContainer = new PaletteDrawer(BpmnDiagramMessages.BpmnPaletteFactory_gateways_label);
        ((PaletteDrawer) paletteContainer).setInitialState(PaletteDrawer.INITIAL_STATE_CLOSED);
        paletteContainer.setDescription(BpmnDiagramMessages.BpmnPaletteFactory_gateways_description);
        paletteContainer.add(createDatabasedexcusive1CreationTool());
        paletteContainer.add(createEventbasedexclusive2CreationTool());
        paletteContainer.add(createDatabasedinclusive3CreationTool());
        paletteContainer.add(createParallel4CreationTool());
        paletteContainer.add(createComplex5CreationTool());
        return paletteContainer;
    }
    
    private PaletteEntry createComplex5CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(GATEWAY_COMPLEX);
        ImageDescriptor largeImage = findLargeImgDescr(GATEWAY_COMPLEX);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                    BpmnElementTypes.Activity_2001,
                    ActivityType.GATEWAY_COMPLEX_LITERAL.getLiteral());

        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_complex_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_complex_tooltip,
                smallImage, largeImage, elementTypes) {
        };

        return result;
    }
    /**
     * @generated NOT adding an id to the palette drawer, so
     * that subclasses can remove it.
     */
    private PaletteContainer createArtifacts4Group() {
        PaletteContainer paletteContainer = new PaletteDrawer(BpmnDiagramMessages.BpmnPaletteFactory_artifacts_label);
        ((PaletteDrawer) paletteContainer).setInitialState(PaletteDrawer.INITIAL_STATE_CLOSED);
        paletteContainer.setDescription(BpmnDiagramMessages.BpmnPaletteFactory_artifacts_description);
        paletteContainer.add(createGroup3CreationTool());
        paletteContainer.add(createDataObject4CreationTool());
        paletteContainer.setId("bpmn/artifactsDrawer"); //$NON-NLS-1$
        return paletteContainer;
    }
   /**
     * @generated NOT
     */
    private ToolEntry createTask1CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(TASK);
        ImageDescriptor largeImage = findLargeImgDescr(TASK);

        final List elementTypes = new ArrayList();
        elementTypes.add(BpmnElementTypes.Activity_2001);
        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_task_label, 
        		BpmnDiagramMessages.BpmnPaletteFactory_create_task_description,
                smallImage, largeImage, elementTypes);

        return result;
    }
    /**
     * @notgenerated NOT
     */
    private ToolEntry createLoopingTask1CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(TASK_LOOPING);
        ImageDescriptor largeImage = findLargeImgDescr(TASK_LOOPING);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity1 = ElementTypeEx.wrap(
                BpmnElementTypes.Activity_2001,
                BpmnPackage.Literals.ACTIVITY__LOOPING.getName());
        elementTypes.add(activity1);
        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_looping_task_label, 
        		BpmnDiagramMessages.BpmnPaletteFactory_create_looping_task_description,
                smallImage, largeImage, elementTypes);

        return result;
    }

    /**
     * @notgenerated
     */
    private ToolEntry createEdgeConnection2CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(FLOW_CONNECTOR);
        ImageDescriptor largeImage = findLargeImgDescr(FLOW_CONNECTOR);

        final List relationshipTypes = new ArrayList();
        relationshipTypes.add(BpmnElementTypes.SequenceEdge_3001);
        ToolEntry result = new LinkToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_flow_connector_label,
                BpmnDiagramMessages.BpmnPaletteFactory_create_flow_connector_description, smallImage, largeImage,
                relationshipTypes);

        return result;
    }

    /**
     * @notgenerated
     */
    private ToolEntry createPool3CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(POOL);
        ImageDescriptor largeImage = findLargeImgDescr(POOL);

        final List elementTypes = new ArrayList();
        elementTypes.add(BpmnElementTypes.Pool_1001);
        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_pool_label, 
        		BpmnDiagramMessages.BpmnPaletteFactory_create_pool_description,
                smallImage, largeImage, elementTypes);

        return result;
    }

    /**
     * @notgenerated
     */
    private ToolEntry createSubProcess4CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(SUB_PROCESS_EXPANDED);
        ImageDescriptor largeImage = findLargeImgDescr(SUB_PROCESS_EXPANDED);

        final List elementTypes = new ArrayList();
        elementTypes.add(BpmnElementTypes.SubProcess_2002);
        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_sp_label,
                BpmnDiagramMessages.BpmnPaletteFactory_create_sp_description, smallImage, largeImage, elementTypes);

        return result;
    }
    
    /**
     * @notgenerated
     */
    private ToolEntry createSubProcessLooping5CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(SUB_PROCESS_EXPANDED_LOOPING);
        ImageDescriptor largeImage = findLargeImgDescr(SUB_PROCESS_EXPANDED_LOOPING);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                BpmnElementTypes.SubProcess_2002,
                BpmnPackage.Literals.ACTIVITY__LOOPING.getName());
        elementTypes.add(activity);
        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_looping_sp_label,
                BpmnDiagramMessages.BpmnPaletteFactory_create_looping_sp_description, smallImage, largeImage, elementTypes);
        return result;
    }
    
    /**
     * @generated
     */
    private ToolEntry createMessageConnection5CreationTool() {
        ImageDescriptor smallImage;
        ImageDescriptor largeImage;

        smallImage = BpmnElementTypes
                .getImageDescriptor(BpmnElementTypes.MessagingEdge_3002);

        largeImage = smallImage;

        final List relationshipTypes = new ArrayList();
        relationshipTypes.add(BpmnElementTypes.MessagingEdge_3002);
        ToolEntry result = new LinkToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_messaging_edge_label,
                BpmnDiagramMessages.BpmnPaletteFactory_create_messaging_edge_description, smallImage, largeImage,
                relationshipTypes);

        return result;
    }

    /**
     * @generated
     */
    private ToolEntry createLane6CreationTool() {
        ImageDescriptor smallImage;
        ImageDescriptor largeImage;

        smallImage = BpmnElementTypes
                .getImageDescriptor(BpmnElementTypes.Lane_2007);

        largeImage = smallImage;

        final List elementTypes = new ArrayList();
        elementTypes.add(BpmnElementTypes.Lane_2007);
        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_lane_label, 
        		BpmnDiagramMessages.BpmnPaletteFactory_create_lane_description,
                smallImage, largeImage, elementTypes);

        return result;
    }

    /**
     * @notgenerated
     */
    private ToolEntry createStartEmpty1CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(START_EMPTY);
        ImageDescriptor largeImage = findLargeImgDescr(START_EMPTY);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx
                .wrap(
                        BpmnElementTypes.Activity_2001,
                        ActivityType.EVENT_START_EMPTY_LITERAL.getLiteral());

        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_empty_start_label,
                BpmnDiagramMessages.BpmnPaletteFactory_empty_start_tooltip, smallImage, largeImage,
                elementTypes) {
        };

        return result;
    }

    /**
     * @notgenerated
     */
    private ToolEntry createStartMessage2CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(START_MESSAGE);
        ImageDescriptor largeImage = findLargeImgDescr(START_MESSAGE);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx
                .wrap(
                        BpmnElementTypes.Activity_2001,
                        ActivityType.EVENT_START_MESSAGE_LITERAL.getLiteral());
        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_message_start_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_message_start_tooltip, smallImage, largeImage, 
                elementTypes) {
        };

        return result;
    }

    /**
     * @notgenerated
     */
    private ToolEntry createStartRule3CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(START_RULE);
        ImageDescriptor largeImage = findLargeImgDescr(START_RULE);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                        BpmnElementTypes.Activity_2001,
                        ActivityType.EVENT_START_RULE_LITERAL.getLiteral());

        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_rule_start_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_rule_start_tooltip, smallImage, largeImage,
                elementTypes) {
        };

        return result;
    }

    /**
     * @notgenerated
     */
    private ToolEntry createIntermediateEmpty4CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(INTERMEDIATE_EMPTY);
        ImageDescriptor largeImage = findLargeImgDescr(INTERMEDIATE_EMPTY);
        
        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                        BpmnElementTypes.Activity_2001,
                        ActivityType.EVENT_INTERMEDIATE_EMPTY_LITERAL.getLiteral());

        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_empty_intermediate_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_empty_intermediate_tooltip, smallImage, largeImage,
                elementTypes) {
        };

        return result;
    }

    /**
     * @generated not, set as protected.
     */
    protected ToolEntry createIntermediateMessage5CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(INTERMEDIATE_MESSAGE);
        ImageDescriptor largeImage = findLargeImgDescr(INTERMEDIATE_MESSAGE);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                        BpmnElementTypes.Activity_2001,
                        ActivityType.EVENT_INTERMEDIATE_MESSAGE_LITERAL.getLiteral());

        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_message_intermediate_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_message_intermediate_tooltip, smallImage, 
                largeImage, elementTypes) {
        };

        return result;
    }

    /**
     * @notgenerated Specifies the secondary hint and support either activity
     *  in compartment either activity on sub-process border
     */
    private ToolEntry createIntermediateTimer6CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(INTERMEDIATE_TIMER);
        ImageDescriptor largeImage = findLargeImgDescr(INTERMEDIATE_TIMER);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                BpmnElementTypes.Activity_2001,
                ActivityType.EVENT_INTERMEDIATE_TIMER_LITERAL.getLiteral());
        IElementTypeEx activity2 = ElementTypeEx.wrap(
                BpmnElementTypes.Activity_2003,
                ActivityType.EVENT_INTERMEDIATE_TIMER_LITERAL.getLiteral());
        elementTypes.add(activity);
        elementTypes.add(activity2);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_timer_intermediate_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_timer_intermediate_tooltip, smallImage, largeImage, 
                elementTypes) {
        };

        return result;
    }

    /**
     * @notgenerated Specifies the secondary hint and support either activity
     *  in compartment either activity on sub-process border
     */
    private ToolEntry createIntermediateError7CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(INTERMEDIATE_ERROR);
        ImageDescriptor largeImage = findLargeImgDescr(INTERMEDIATE_ERROR);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                BpmnElementTypes.Activity_2001,
                ActivityType.EVENT_INTERMEDIATE_ERROR_LITERAL.getLiteral());
        IElementTypeEx activity2 = ElementTypeEx.wrap(
                BpmnElementTypes.Activity_2003,
                ActivityType.EVENT_INTERMEDIATE_ERROR_LITERAL.getLiteral());

        elementTypes.add(activity);
        elementTypes.add(activity2);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_error_intermediate_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_error_intermediate_tooltip, smallImage, largeImage, 
                elementTypes) {
        };

        return result;
    }

    /**
     * This tool creates either activities on the border of the sub-process or on
     * the compartment of a pool or a sub-process
     * @notgenerated
     */
    private ToolEntry createIntermediateCompensation8CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(INTERMEDIATE_COMPENSATION);
        ImageDescriptor largeImage = findLargeImgDescr(INTERMEDIATE_COMPENSATION);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                BpmnElementTypes.Activity_2001,
                ActivityType.EVENT_INTERMEDIATE_COMPENSATION_LITERAL.getLiteral());
        IElementTypeEx activity2 = ElementTypeEx.wrap(
                BpmnElementTypes.Activity_2003,
                ActivityType.EVENT_INTERMEDIATE_COMPENSATION_LITERAL.getLiteral());

        elementTypes.add(activity);
        elementTypes.add(activity2);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_compensation_intermediate_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_compensation_intermediate_tooltip, smallImage,
                largeImage, elementTypes) {
        };
        
        

        return result;
    }

    /**
     * @notgenerated
     */
    private ToolEntry createIntermediateRule9CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(INTERMEDIATE_RULE);
        ImageDescriptor largeImage = findLargeImgDescr(INTERMEDIATE_RULE);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                    BpmnElementTypes.Activity_2001,
                    ActivityType.EVENT_INTERMEDIATE_RULE_LITERAL.getLiteral());
        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_rule_intermediate_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_rule_intermediate_tooltip, smallImage, largeImage, 
                elementTypes) {
        };

        return result;
    }
    
    /**
     * @notgenerated
     */
    private ToolEntry createEndEmpty10CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(END_EMPTY);
        ImageDescriptor largeImage = findLargeImgDescr(END_EMPTY);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                        BpmnElementTypes.Activity_2001,
                        ActivityType.EVENT_END_EMPTY_LITERAL.getLiteral());
        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_empty_end_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_empty_end_tooltip, smallImage, largeImage, 
                elementTypes) {
        };

        return result;
    }

    /**
     * @notgenerated
     */
    private ToolEntry createEndMessage11CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(END_MESSAGE);
        ImageDescriptor largeImage = findLargeImgDescr(END_MESSAGE);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                        BpmnElementTypes.Activity_2001,
                        ActivityType.EVENT_END_MESSAGE_LITERAL.getLiteral());

        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_message_end_label,
                BpmnDiagramMessages.BpmnPaletteFactory_message_end_tooltip, smallImage, largeImage, 
                elementTypes) {
        };

        return result;
    }

    /**
     * @notgenerated
     */
    private ToolEntry createEndError12CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(END_ERROR);
        ImageDescriptor largeImage = findLargeImgDescr(END_ERROR);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                        BpmnElementTypes.Activity_2001,
                        ActivityType.EVENT_END_ERROR_LITERAL.getLiteral());
        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_end_error_label,
                BpmnDiagramMessages.BpmnPaletteFactory_create_end_error_description, smallImage, largeImage, 
                elementTypes) {
        };

        return result;
    }
    /**
     * @notgenerated
     */
    private ToolEntry createEndCompensation13CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(END_COMPENSATION);
        ImageDescriptor largeImage = findLargeImgDescr(END_COMPENSATION);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx
                .wrap(
                        BpmnElementTypes.Activity_2001,
                        ActivityType.EVENT_END_COMPENSATION_LITERAL.getLiteral());
        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_end_compensation_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_create_end_compensation_description, smallImage, largeImage, 
                elementTypes) {
        };

        return result;
    }

    /**
     * @notgenerated
     */
    private ToolEntry createEndTerminate14CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(END_TERMINATE);
        ImageDescriptor largeImage = findLargeImgDescr(END_TERMINATE);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                        BpmnElementTypes.Activity_2001,
                        ActivityType.EVENT_END_TERMINATE_LITERAL.getLiteral());
        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_terminate_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_create_terminate_description, smallImage, largeImage, 
                elementTypes) {
        };

        return result;
    }

    /**
     * @notgenerated
     */
    private ToolEntry createDatabasedexcusive1CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(GATEWAY_DATABASED_EXCLUSIVE);
        ImageDescriptor largeImage = findLargeImgDescr(GATEWAY_DATABASED_EXCLUSIVE);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                        BpmnElementTypes.Activity_2001,
                        ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE_LITERAL.getLiteral());
        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_exclusive_gateway_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_create_exclusive_gateway_description, smallImage, largeImage, 
                elementTypes) {
        };

        return result;
    }

    /**
     * @notgenerated
     */
    private ToolEntry createEventbasedexclusive2CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(GATEWAY_EVENT_BASED);
        ImageDescriptor largeImage = findLargeImgDescr(GATEWAY_EVENT_BASED);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx
                .wrap(
                        BpmnElementTypes.Activity_2001,
                        ActivityType.GATEWAY_EVENT_BASED_EXCLUSIVE_LITERAL.getLiteral());

        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_exclusive_event_gateway_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_create_exclusive_event_gateway_description, smallImage, largeImage, 
                elementTypes) {
        };

        return result;
    }

    /**
     * @notgenerated
     */
    private ToolEntry createDatabasedinclusive3CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(GATEWAY_DATABASED_INCLUSIVE);
        ImageDescriptor largeImage = findLargeImgDescr(GATEWAY_DATABASED_INCLUSIVE);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                BpmnElementTypes.Activity_2001,
                ActivityType.GATEWAY_DATA_BASED_INCLUSIVE_LITERAL.getLiteral());
        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_inclusive_gateway_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_create_inclusive_gateway_description, smallImage, largeImage, 
                elementTypes) {
        };

        return result;
    }

    /**
     * @notgenerated
     */
    private ToolEntry createParallel4CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(GATEWAY_PARALLEL);
        ImageDescriptor largeImage = findLargeImgDescr(GATEWAY_PARALLEL);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                    BpmnElementTypes.Activity_2001,
                    ActivityType.GATEWAY_PARALLEL_LITERAL.getLiteral());

        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_parallel_gateway_label, 
        		BpmnDiagramMessages.BpmnPaletteFactory_create_parallel_gateway_description,
                smallImage, largeImage, elementTypes) {
        };

        return result;
    }

    /**
     * @notgenerated
     */
    private ToolEntry createCreateEmptyStartEvent7CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(START_EMPTY);
        ImageDescriptor largeImage = findLargeImgDescr(START_EMPTY);

        final List elementTypes = new ArrayList();
        elementTypes.add(ElementTypeEx.wrap(
                (IHintedType) BpmnElementTypes.Activity_2001,
                ActivityType.EVENT_START_EMPTY_LITERAL.getLiteral()));
        final List aTypes = new ArrayList();
        //		
        // aTypes.add(ActivityType.EVENT_START_EMPTY);
        // aTypes.add(ActivityType.EVENT_START_EMPTY);

        ToolEntry result = new NodeToolEntry("Empty Start", //$NON-NLS-1$
                "CreateEventStartEmpty", smallImage, largeImage, elementTypes); //$NON-NLS-1$

        return result;
    }
    /**
     * @generated
     */
    private ToolEntry createAssociation1CreationTool() {
        ImageDescriptor smallImage;
        ImageDescriptor largeImage;

        smallImage = BpmnElementTypes
                .getImageDescriptor(BpmnElementTypes.Association_3003);

        largeImage = smallImage;

        final List relationshipTypes = new ArrayList();
        relationshipTypes.add(BpmnElementTypes.Association_3003);
        ToolEntry result = new LinkToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_association_label,
                BpmnDiagramMessages.BpmnPaletteFactory_create_association_description, smallImage, largeImage,
                relationshipTypes);

        return result;
    }

    /**
     * @generated
     */
    private ToolEntry createTextAnnotation2CreationTool() {
        ImageDescriptor smallImage;
        ImageDescriptor largeImage;

        smallImage = BpmnElementTypes
                .getImageDescriptor(BpmnElementTypes.TextAnnotation_2004);

        largeImage = smallImage;

        final List elementTypes = new ArrayList();
        elementTypes.add(BpmnElementTypes.TextAnnotation_2004);
        elementTypes.add(BpmnElementTypes.TextAnnotation_1002);
        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_text_annotation_label,
                BpmnDiagramMessages.BpmnPaletteFactory_create_text_annotation_description, smallImage, largeImage,
                elementTypes);

        return result;
    }

    /**
     * @generated
     */
    private ToolEntry createGroup3CreationTool() {
        ImageDescriptor smallImage;
        ImageDescriptor largeImage;

        smallImage = BpmnElementTypes
                .getImageDescriptor(BpmnElementTypes.Group_2006);

        largeImage = smallImage;

        final List elementTypes = new ArrayList();
        elementTypes.add(BpmnElementTypes.Group_2006);
        elementTypes.add(BpmnElementTypes.Group_1004);
        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_group_label, 
        		BpmnDiagramMessages.BpmnPaletteFactory_create_group_description,
                smallImage, largeImage, elementTypes);

        return result;
    }

    /**
     * @generated
     */
    private ToolEntry createDataObject4CreationTool() {
        ImageDescriptor smallImage;
        ImageDescriptor largeImage;

        smallImage = BpmnElementTypes
                .getImageDescriptor(BpmnElementTypes.DataObject_2005);

        largeImage = smallImage;

        final List elementTypes = new ArrayList();
        elementTypes.add(BpmnElementTypes.DataObject_2005);
        elementTypes.add(BpmnElementTypes.DataObject_1003);
        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_create_data_object_label,
                BpmnDiagramMessages.BpmnPaletteFactory_create_data_object_description, smallImage, largeImage, elementTypes);

        return result;
    }

    /**
     * @generated not implements IStickableToolEntry.
     */
    protected static class NodeToolEntry extends ToolEntry implements IStickableToolEntry {

        /**
         * @generated not
         */
        private List elementTypes;
        
        private boolean _isSticky = false;
        
        /**
         * @generated not
         */
        public NodeToolEntry(String title, String description,
                ImageDescriptor smallIcon, ImageDescriptor largeIcon,
                List elementTypes) {
            super(title, description, smallIcon, largeIcon);
            this.elementTypes = elementTypes;
        }
        
        /**
         * @generated not
         */
        public NodeToolEntry(String title, String description,
                ImageDescriptor smallIcon, ImageDescriptor largeIcon,
                Class className) {
            super(title, description, smallIcon, largeIcon, className);
        }
        /**
         * @return true if in sticky mode
         * where the tool created is not unloaded once it 
         * has been used.
         */
        public boolean isSticky() {
            return _isSticky;
        }
        
        /**
         * @param isSticky True if in sticky mode
         * where the tool created is not unloaded once it 
         * has been used.
         */
        public void setIsSticky(boolean isSticky) {
            _isSticky = isSticky;
        }

        /**
         * @generated not
         * if no element types are provided, try 
         * the super implementation.
         */
        public Tool createTool() {
            if (elementTypes == null) {
                return super.createTool();
            }
            AbstractTool tool = new UnspecifiedActivityTypeCreationToolEx(
                    elementTypes, !_isSticky);
            tool.setProperties(getToolProperties());
            return tool;
        }
    }

    /**
     * @generated
     */
    private static class LinkToolEntry extends ToolEntry implements IStickableToolEntry {

        /**
         * @generated
         */
        private final List relationshipTypes;
        
        private boolean _isSticky = true;

        /**
         * @generated
         */
        private LinkToolEntry(String title, String description,
                ImageDescriptor smallIcon, ImageDescriptor largeIcon,
                List relationshipTypes) {
            super(title, description, smallIcon, largeIcon);
            this.relationshipTypes = relationshipTypes;
        }
        
        /**
         * @return true if in sticky mode
         * where the tool created is not unloaded once it 
         * has been used.
         */
        public boolean isSticky() {
            return _isSticky;
        }
        
        /**
         * @param isSticky True if in sticky mode
         * where the tool created is not unloaded once it 
         * has been used.
         */
        public void setIsSticky(boolean isSticky) {
            _isSticky = isSticky;
        }


        /**
         * @notgenerated
         */
        public Tool createTool() {
            Tool tool = new UnspecifiedTypeConnectionTool(relationshipTypes) {
                
                /**
                 * Creates a connection between the two select shapes. edit parts.
                 */
                protected void createConnection() {
                    if (getCurrentViewer() instanceof GraphicalViewer) {
                        super.createConnection();
                    }
                    //nothing we are probably still above the palette
                }

                
                /**
                 * Method selectAddedObject. Select the newly added connection
                 */
                protected void selectAddedObject(EditPartViewer viewer,
                        Collection objects) {
                    final List editparts = new ArrayList();
                    final EditPart[] primaryEP = new EditPart[1];
                    for (Iterator i = objects.iterator(); i.hasNext();) {
                        Object object = i.next();
                        if (object instanceof IAdaptable) {
                            Object editPart = viewer.getEditPartRegistry().get(
                                    ((IAdaptable) object)
                                            .getAdapter(View.class));

                            if (editPart instanceof IPrimaryEditPart) {
                                editparts.add(editPart);
                            }

//uncomment to put shape in direct edit mode.
//                            // Priority is to put a shape into direct edit mode.
//                            if (editPart instanceof ShapeEditPart) {
//                                primaryEP[0] = (ShapeEditPart) editPart;
//                            }
                        }
                    }

                    if (!editparts.isEmpty()) {
                        viewer.setSelection(new StructuredSelection(editparts));

                        // next section of code was removed as EDGE-1020 fix:
                        // turn off auto edit mode after
                        // connection creation
                        
                        
                        
                    }
                }
            };
            
            tool.setProperties(getToolProperties());
            ((AbstractTool)tool).setUnloadWhenFinished(!isSticky());
            return tool;
        }
    }
    
    
//constants:
    public static String STP_BPMN_EDIT_PLUGIN_ID = "org.eclipse.stp.bpmn.edit"; //$NON-NLS-1$
    public static final String START_IMG_URI = "icons/full/"; //$NON-NLS-1$
    public static final String OBJ_16 = "obj16/activities/"; //$NON-NLS-1$
    public static final String OBJ_24 = "obj24/activities/"; //$NON-NLS-1$
    public static final String OBJ_32 = "obj32/activities/"; //$NON-NLS-1$
    public static final String OBJ_48 = "obj48/activities/"; //$NON-NLS-1$
    
    public static final String TOOL_16 = "obj16/"; //$NON-NLS-1$
    public static final String TOOL_24 = "obj24/"; //$NON-NLS-1$
    public static final String TOOL_32 = "obj24/"; //$NON-NLS-1$
    
    public static final String END_COMPENSATION = "end_compensation"; //$NON-NLS-1$
    public static final String END_EMPTY = "end_empty"; //$NON-NLS-1$
    public static final String END_ERROR = "end_error"; //$NON-NLS-1$
    public static final String END_MESSAGE = "end_message"; //$NON-NLS-1$
    public static final String END_SIGNAL = "end_signal"; //$NON-NLS-1$
    public static final String END_MULTIPLE = "end_multiple"; //$NON-NLS-1$
    public static final String END_CANCEL = "end_cancel"; //$NON-NLS-1$
    public static final String END_LINK = "end_link"; //$NON-NLS-1$
    public static final String END_TERMINATE = "end_terminate"; //$NON-NLS-1$
    public static final String GATEWAY_COMPLEX = "gateway_complex"; //$NON-NLS-1$
    public static final String GATEWAY_DATABASED_EXCLUSIVE = "gateway_databased_exclusive"; //$NON-NLS-1$
    public static final String GATEWAY_DATABASED_INCLUSIVE = "gateway_databased_inclusive"; //$NON-NLS-1$
    public static final String GATEWAY_EVENT_BASED = "gateway_event_based"; //$NON-NLS-1$
    public static final String GATEWAY_PARALLEL = "gateway_parallel"; //$NON-NLS-1$
    public static final String INTERMEDIATE_CANCEL = "intermediate_cancel"; //$NON-NLS-1$
    public static final String INTERMEDIATE_LINK = "intermediate_link"; //$NON-NLS-1$
    public static final String INTERMEDIATE_COMPENSATION = "intermediate_compensation"; //$NON-NLS-1$
    public static final String INTERMEDIATE_EMPTY = "intermediate_empty"; //$NON-NLS-1$
    public static final String INTERMEDIATE_ERROR = "intermediate_error"; //$NON-NLS-1$
    public static final String INTERMEDIATE_MESSAGE = "intermediate_message"; //$NON-NLS-1$
    public static final String INTERMEDIATE_RULE = "intermediate_rule"; //$NON-NLS-1$
    public static final String INTERMEDIATE_TIMER = "intermediate_timer"; //$NON-NLS-1$
    public static final String INTERMEDIATE_MULTIPLE = "intermediate_multiple"; //$NON-NLS-1$
    public static final String INTERMEDIATE_SIGNAL = "intermediate_signal"; //$NON-NLS-1$
    public static final String POOL = "pool"; //$NON-NLS-1$
    public static final String START_EMPTY = "start_empty"; //$NON-NLS-1$
    public static final String START_MESSAGE = "start_message"; //$NON-NLS-1$
    public static final String START_RULE = "start_rule"; //$NON-NLS-1$
    public static final String START_TIMER = "start_timer"; //$NON-NLS-1$
    public static final String START_MULTIPLE = "start_multiple"; //$NON-NLS-1$
    public static final String START_LINK = "start_link"; //$NON-NLS-1$
    public static final String START_SIGNAL = "start_signal"; // $NON-NLS-1$ //$NON-NLS-1$
    public static final String SUB_PROCESS = "sub_process"; //$NON-NLS-1$
    public static final String SUB_PROCESS_EXPANDED = "sub_process_expanded"; //$NON-NLS-1$
    public static final String SUB_PROCESS_EXPANDED_LOOPING = "sub_process_expanded_looping"; //$NON-NLS-1$
    public static final String SUB_PROCESS_LOOPING = "sub_process_looping"; //$NON-NLS-1$
    public static final String TASK = "task";     //$NON-NLS-1$
    public static final String TASK_LOOPING = "task_looping"; //$NON-NLS-1$

    public static final String MOVE_HALF_EAST = "tool_move_half_E"; //$NON-NLS-1$
    public static final String MOVE_HALF_WEST = "tool_move_half_W"; //$NON-NLS-1$
    public static final String MOVE_QUADRANT_NE = "tool_move_quadrant_NE"; //$NON-NLS-1$
    public static final String MOVE_QUADRANT_NW = "tool_move_quadrant_NW"; //$NON-NLS-1$
    public static final String MOVE_QUADRANT_SE = "tool_move_quadrant_SE"; //$NON-NLS-1$
    public static final String MOVE_QUADRANT_SW = "tool_move_quadrant_SW"; //$NON-NLS-1$
    
    public static final String FLOW_CONNECTOR = "flow_connector";     //$NON-NLS-1$
    public static final String MESSAGE_CONNECTOR = "message_connector"; //$NON-NLS-1$
    

    public static final String getImgPath(String objSize, String fileName, String extension) {
        return START_IMG_URI + objSize + fileName + "." + extension; //$NON-NLS-1$
    }
    public static final ImageDescriptor findImgDescr(String objSize, String fileName) {
        return BpmnDiagramEditorPlugin.imageDescriptorFromPlugin(STP_BPMN_EDIT_PLUGIN_ID,
                getImgPath(objSize, fileName, "png")); //$NON-NLS-1$
    }
    public static final ImageDescriptor findSmallImgDescr(String fileName) {
        return BpmnDiagramEditorPlugin.imageDescriptorFromPlugin(STP_BPMN_EDIT_PLUGIN_ID,
                getImgPath(OBJ_16, fileName, "png")); //$NON-NLS-1$
    }
    public static final ImageDescriptor findMediumImgDescr(String fileName) {
        return BpmnDiagramEditorPlugin.imageDescriptorFromPlugin(STP_BPMN_EDIT_PLUGIN_ID,
                getImgPath(OBJ_24, fileName, "png")); //$NON-NLS-1$
    }
    public static final ImageDescriptor findLargeImgDescr(String fileName) {
        return BpmnDiagramEditorPlugin.imageDescriptorFromPlugin(STP_BPMN_EDIT_PLUGIN_ID,
                getImgPath(OBJ_32, fileName, "png")); //$NON-NLS-1$
    }
    
    public static final ImageDescriptor findSmallToolImgDescr(String fileName) {
        return BpmnDiagramEditorPlugin.imageDescriptorFromPlugin(STP_BPMN_EDIT_PLUGIN_ID,
                getImgPath(TOOL_16, fileName, "png")); //$NON-NLS-1$
    }
    public static final ImageDescriptor findMediumToolImgDescr(String fileName) {
        return BpmnDiagramEditorPlugin.imageDescriptorFromPlugin(STP_BPMN_EDIT_PLUGIN_ID,
                getImgPath(TOOL_24, fileName, "png")); //$NON-NLS-1$
    }
    public static final ImageDescriptor findLargeToolImgDescr(String fileName) {
        return BpmnDiagramEditorPlugin.imageDescriptorFromPlugin(STP_BPMN_EDIT_PLUGIN_ID,
                getImgPath(TOOL_32, fileName, "png")); //$NON-NLS-1$
    }

    
    public static final URL getSVGURL(String fileName) {
        Bundle bundle = Platform.getBundle(BpmnEditPlugin.INSTANCE.getSymbolicName());
        return FileLocator.find(bundle, new Path("icons/full/objsvg/" + fileName + ".svg"), null); //$NON-NLS-1$ //$NON-NLS-2$
    }
    
    /**
     * @generated not the tool for the intermediate cancel event
     */
    private ToolEntry createIntermediateCancel10CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(INTERMEDIATE_CANCEL);
        ImageDescriptor largeImage = findLargeImgDescr(INTERMEDIATE_CANCEL);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                        BpmnElementTypes.Activity_2003,
                        ActivityType.EVENT_INTERMEDIATE_CANCEL_LITERAL.getLiteral());
        elementTypes.add(activity);
        elementTypes.add(ElementTypeEx.wrap(
                BpmnElementTypes.Activity_2001,
                ActivityType.EVENT_INTERMEDIATE_CANCEL_LITERAL.getLiteral()));

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_cancel_intermediate_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_cancel_intermediate_description, smallImage, largeImage, 
                elementTypes) {
        };

        return result;
    }
    
    /**
     * @generated not
     */
    private ToolEntry createIntermediateSignal11CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(INTERMEDIATE_SIGNAL);
        ImageDescriptor largeImage = findLargeImgDescr(INTERMEDIATE_SIGNAL);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                        BpmnElementTypes.Activity_2001,
                        ActivityType.EVENT_INTERMEDIATE_SIGNAL_LITERAL.getLiteral());
        elementTypes.add(activity);
        elementTypes.add(ElementTypeEx.wrap(
                BpmnElementTypes.Activity_2003,
                ActivityType.EVENT_INTERMEDIATE_SIGNAL_LITERAL.getLiteral()));
        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_intermediate_signal_event_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_intermediate_signal_event_tooltip, smallImage, largeImage, 
                elementTypes) {
        };

        return result;
    }
    
    /**
     * @generated not
     */
    private ToolEntry createEndSignal15CreationTool() {
        ImageDescriptor smallImage = findSmallImgDescr(END_SIGNAL);
        ImageDescriptor largeImage = findLargeImgDescr(END_SIGNAL);

        final List elementTypes = new ArrayList();
        IElementTypeEx activity = ElementTypeEx.wrap(
                        BpmnElementTypes.Activity_2001,
                        ActivityType.EVENT_END_SIGNAL_LITERAL.getLiteral());
        elementTypes.add(activity);

        ToolEntry result = new NodeToolEntry(BpmnDiagramMessages.BpmnPaletteFactory_end_signal_label, 
                BpmnDiagramMessages.BpmnPaletteFactory_end_signal_tooltip, smallImage, largeImage, 
                elementTypes) {
        };

        return result;
    }
}
