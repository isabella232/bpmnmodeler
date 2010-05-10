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
package org.eclipse.stp.bpmn.diagram.edit.parts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramAssistantEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.OneLineBorder;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.NamedBpmnObject;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SequenceFlowConditionType;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.actions.SetAsThrowingOrCatchingAction;
import org.eclipse.stp.bpmn.diagram.edit.policies.ActivityCanonicalEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.ActivityGraphicalNodeEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.ActivityItemSemanticEditPolicy;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.figures.BpmnShapesDefaultSizes;
import org.eclipse.stp.bpmn.figures.WrapLabelWithToolTip;
import org.eclipse.stp.bpmn.figures.WrapLabelWithToolTip.IToolTipProvider;
import org.eclipse.stp.bpmn.figures.activities.ActivityDiamondFigure;
import org.eclipse.stp.bpmn.figures.activities.ActivityNodeFigure;
import org.eclipse.stp.bpmn.figures.activities.ActivityOvalFigure;
import org.eclipse.stp.bpmn.figures.activities.ActivityPainter;
import org.eclipse.stp.bpmn.figures.connectionanchors.IConnectionAnchorFactory;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchor;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchor.INodeFigureAnchorTerminalUpdatable;
import org.eclipse.stp.bpmn.figures.connectionanchors.impl.ConnectionAnchorFactory;
import org.eclipse.stp.bpmn.layouts.ActivityLayout;
import org.eclipse.stp.bpmn.policies.BpmnDragDropEditPolicy;
import org.eclipse.stp.bpmn.policies.ConnectionHandleEditPolicyEx;
import org.eclipse.stp.bpmn.policies.OpenFileEditPolicy;
import org.eclipse.stp.bpmn.policies.ResizableActivityEditPolicy;
import org.eclipse.stp.bpmn.tools.TaskDragEditPartsTrackerEx;

/**
 * @generated
 */
public class ActivityEditPart extends ShapeNodeEditPart {
    // private Point targetFigureLocation = new Point(-300, -300);

    private static final int SHAPE_RECTANGLE = 0;

    private static final int SHAPE_DIAMOND = 1;

    private static final int SHAPE_CIRCLE = 2;

    /**
     * @notgenerated
     */
    public static final int EVENT_FIGURE_SIZE = 30;

    /**
     * @notgenerated
     */
    public static final int GATEWAY_FIGURE_SIZE = 50;

    /**
     * @notgenerated
     */
    public static final Dimension ACTIVITY_FIGURE_SIZE = new Dimension(111, 61);

    /**
     * @generated
     */
    public static final int VISUAL_ID = 2001;

    /**
     * @notgenerated
     */
    private int handlePosition;

    /**
     * @notgenerated
     */
    protected boolean isChildAdded = false;

    /**
     * @generated
     */
    protected IFigure contentPane;

    /**
     * @generated
     */
    protected IFigure primaryShape;

    /**
     * Structure of figure: WrapperNodeFigure is a node plate figure that wraps
     * Oval, Diamond or rectanle, that holds primary shape. Wrapped shape can be
     * changed when activity type is changed.
     * 
     * @notgenerated
     */
    private INodeFigureAnchorTerminalUpdatable wrappedFigure;

    /**
     * @generated
     */
    public ActivityEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    protected void createDefaultEditPoliciesGen() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new ActivityItemSemanticEditPolicy());
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new ActivityGraphicalNodeEditPolicy());
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new ActivityCanonicalEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
    }

    /**
     * @generated NOT
     */
    protected void createDefaultEditPolicies() {
        createDefaultEditPoliciesGen();
        //super.getViewer().getEditDomain();
        // replace ConnectionHandleEditPolicy with our own
        removeEditPolicy(EditPolicyRoles.CONNECTION_HANDLES_ROLE);
        installEditPolicy(EditPolicyRoles.CONNECTION_HANDLES_ROLE,
                createConnectionHandlerEditPolicy());
        // adding default drag and drop edit policy
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
                new BpmnDragDropEditPolicy(this));
        // adding an open edit policy
        installEditPolicy(EditPolicyRoles.OPEN_ROLE, createOpenFileEditPolicy());
    }
    
    /**
     * Ability to override the ConnectionHandleEditPolicy.
     * @generated NOT
     */
    protected DiagramAssistantEditPolicy createConnectionHandlerEditPolicy() {
        return new ConnectionHandleEditPolicyEx();
    }
    
    /**
     * Ability to override the OpenFileEditPolicy.
     * @generated NOT
     */
    protected OpenFileEditPolicy createOpenFileEditPolicy() {
        return new OpenFileEditPolicy();
    }

    /**
     * @generated
     */
    protected LayoutEditPolicy createLayoutEditPolicy() {
        LayoutEditPolicy lep = new LayoutEditPolicy() {

            protected EditPolicy createChildEditPolicy(EditPart child) {
                EditPolicy result = 
                    child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
                if (result == null) {
                	result = getPrimaryDragEditPolicy();
                	if (result != null) {
                		return result;
                	}
                    result = new NonResizableEditPolicy();
                }
                return result;
            }

            protected Command getMoveChildrenCommand(Request request) {
                return null;
            }

            protected Command getCreateCommand(CreateRequest request) {
                return null;
            }
        };
        return lep;
    }

    @Override
    public void installEditPolicy(Object key, EditPolicy editPolicy) {
        super.installEditPolicy(key, editPolicy);
    }

    /**
     * @generated
     */
    protected IFigure createNodeShapeGen() {
        ActivityFigure figure = new ActivityFigure();
        return primaryShape = figure;
    }

    /**
     * @notgenerated
     */
    protected IFigure createNodeShape() {
        ActivityFigure figure = (ActivityFigure) createNodeShapeGen();
        EObject activity = resolveSemanticElement();
        if (activity instanceof Activity) {
            setActivityTypeAndLabelAndLayout(figure, (Activity) activity);
            figure.setLooping(((Activity) activity).isLooping());
        }
        return figure;
    }

    /**
     * 
     * @param activityFigure
     * @param activity
     * @return true if refreshVisual is recommended (there was a change)
     */
    private boolean setActivityTypeAndLabelAndLayout(
            ActivityFigure activityFigure, Activity activity) {
        activityFigure.setActivityType(activity.getActivityType().getLiteral());
        boolean res = false;
        WrappingLabel wl = activityFigure.getFigureActivityNameFigure();
        if (activity.getName() == null) {
            if (activity.getActivityType().equals(ActivityType.TASK_LITERAL)) {
                if (!BpmnDiagramMessages.ActivityEditPart_task_default_name.equals(wl.getText())) {
                    wl.setText(BpmnDiagramMessages.ActivityEditPart_task_default_name);
                }
            }
            res = true;
        }
        return setAlignments(activityFigure, activity, wl, res);
    }
    
    private static final Border BORDER_TOP = new MarginBorder(4, 0, 0, 0);
    private static final Border BORDER_NONE = new MarginBorder(0, 0, 0, 0);
    
    //same than the margin border but the border line is visible.
    private static final Border BORDER_TEST = new OneLineBorder(ColorConstants.black, 4, PositionConstants.TOP);
    
    
    private boolean setAlignments(ActivityFigure activityFigure, Activity activity,
            WrappingLabel wl, boolean res) {
        if (activity.getActivityType().equals(ActivityType.TASK_LITERAL)) {
            if (!(activityFigure.getLayoutManager() instanceof StackLayout)) {
                StackLayout layout = new StackLayout();
                activityFigure.setLayoutManager(layout);
                res = true;
            }
            wl.setAlignment(PositionConstants.CENTER);
            wl.setTextJustification(PositionConstants.CENTER);
            if (wrappedFigure.getLayoutManager() instanceof ActivityLayout) {
                ActivityLayout layout = (ActivityLayout) wrappedFigure.getLayoutManager();
                layout.setVerticalSpacing(0);
            }
//            wl.setBorder(BORDER_NONE);
        } else {
            if (wl != null) {
                wl.setTextJustification(PositionConstants.CENTER);
                wl.setAlignment(PositionConstants.TOP);
//                int prefWidth = MapModeUtil.getMapMode(activityFigure).DPtoLP(ACTIVITY_FIGURE_SIZE.width);
//                wl.setPreferredSize(prefWidth, 0);
                if (wrappedFigure.getLayoutManager() instanceof ActivityLayout) {
                    ActivityLayout layout = (ActivityLayout) wrappedFigure.getLayoutManager();
                    layout.setVerticalSpacing(4);
                }
//                wl.setBorder(BORDER_TOP);
            }
            if (!(activityFigure.getLayoutManager() instanceof ConstrainedToolbarLayout)) {
                ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
                layout.setSpacing(getMapMode().DPtoLP(5));
                activityFigure.setLayoutManager(layout);
                res = true;
            }
        }

        activityFigure.invalidate();
        return res;
    }

    /**
     * @generated
     */
    public ActivityFigure getPrimaryShape() {
        return (ActivityFigure) primaryShape;
    }

    /**
     * @generated
     */
    protected boolean addFixedChild(EditPart childEditPart) {
        if (childEditPart instanceof ActivityNameEditPart) {
            ((ActivityNameEditPart) childEditPart).setLabel(getPrimaryShape()
                    .getFigureActivityNameFigure());
            return true;
        }
        return false;
    }

    /**
     * @generated
     */
    protected boolean removeFixedChild(EditPart childEditPart) {
        return false;
    }
    
    /**
     * @return An appropriate connection anchor factory
     * @generated NOT
     */
    protected IConnectionAnchorFactory getConnectionAnchorFactory() {
        return ConnectionAnchorFactory.INSTANCE;
    }
    
    /**
     * Helper method
     * @return The figure for the oval or diamond or rectangle figure.
     */
    public IFigure getHandleBoundsFigure() {
        if (wrappedFigure.getLayoutManager() instanceof ActivityLayout) {
            return ((ActivityLayout)wrappedFigure.getLayoutManager()).getOvalOrDiamondFigure();
        }
        return getPrimaryShape();
    }
    
    /**
     * @generated not
     */
    protected NodeFigure createNodePlate() {
        //this used to be the createWrappedFigure() method:
        int activityType = ActivityType.TASK;
        if (resolveSemanticElement() instanceof Activity) {
            Activity activity = (Activity) resolveSemanticElement();
            activityType = activity.getActivityType().getValue();
        }

        int shapeType = getShapeType(activityType);
        if (shapeType == SHAPE_RECTANGLE) {
            int width = getMapMode().DPtoLP(ACTIVITY_FIGURE_SIZE.width);
            int height = getMapMode().DPtoLP(ACTIVITY_FIGURE_SIZE.height);
            wrappedFigure = new ActivityNodeFigure(getConnectionAnchorFactory(),
                                                    width, height, false);
        } else if (shapeType == SHAPE_DIAMOND) {
            int width = getMapMode().DPtoLP(GATEWAY_FIGURE_SIZE);
            int height = getMapMode().DPtoLP(GATEWAY_FIGURE_SIZE);
            wrappedFigure = new ActivityNodeFigure(getConnectionAnchorFactory(),
                                                    width, height, false);
        } else {
            int width = getMapMode().DPtoLP(EVENT_FIGURE_SIZE);
            int height = getMapMode().DPtoLP(EVENT_FIGURE_SIZE);
            wrappedFigure = new ActivityNodeFigure(getConnectionAnchorFactory(),
                                                    width, height, false);
        }
        wrappedFigure.setLayoutManager(new StackLayout());
        return (NodeFigure)wrappedFigure;
        
        //FIXME: this is the old way of doing things.
        //it looks unecessary right now.
        //please review and delete eventually
//        return new WrapperNodeFigureEx(getConnectionAnchorFactory(), wrappedFigure) {
//            
//            // Exclude activity name label from handle bounds
//            @Override
//            public Rectangle getHandleBounds() {
//                Rectangle rectangle = super.getHandleBounds();
//                Activity activity = (Activity) resolveSemanticElement();
//                if (activity == null || activity.getActivityType() == null || 
//                		activity.getActivityType().equals(ActivityType.TASK_LITERAL)) {
//                    return rectangle;
//                }
//
//                //return the first child which is the oval or diamond
//                return ((IFigure) wrappedFigure.getChildren().get(0)).getBounds();
//            };
//
//        };
    }

    /**
     * @param ovalOrDiamond The figure that draw the oval of the diamond
     * @param shape The figure that contains the oval or diamond and the wrapLabel.
     * @param wrapLabel The label figure.
     * @param size The default size of ovalOrDiamondFig (is this useful?)
     * @generated not. Used for gateway and event bpmns shapes.
     */
    private void buildFigure(IFigure ovalOrDiamond, IFigure shape,
            WrappingLabel wrapLabel) {
        ActivityLayout layout = new ActivityLayout();
        wrappedFigure.setLayoutManager(layout);
        ovalOrDiamond.setLayoutManager(new BorderLayout());
        ovalOrDiamond.add(shape, BorderLayout.CENTER);

        wrappedFigure.add(ovalOrDiamond, BorderLayout.CENTER);
        wrappedFigure.add(wrapLabel, BorderLayout.BOTTOM);
        
        ActivityFigure activityFigure = (ActivityFigure)shape;
        activityFigure.setFigureActivityNameFigure(wrapLabel);

        contentPane = setupContentPane(wrappedFigure);
        EObject sem = resolveSemanticElement();
        if (sem instanceof Activity) {
            setAlignments(activityFigure, (Activity)sem, wrapLabel, true);
        }
    }




    /**
     * Creates figure for this edit part.
     * 
     * Body of this method does not depend on settings in generation model so
     * you may safely remove <i>generated</i> tag and modify it.
     * 
     * @generated not
     */
    protected NodeFigure createNodeFigure() {
        NodeFigure figure = createNodePlate();//the wrapper figure just there to hold the connections and the handlebound
        IFigure shape = createNodeShape();//the activityFigure

        ActivityType activityType = ActivityType.TASK_LITERAL;
        if (resolveSemanticElement() instanceof Activity) {
            activityType = ((Activity) getPrimaryView().getElement())
                .getActivityType();
        }
        boolean gateway = ActivityType.VALUES_GATEWAYS.contains(activityType);
        boolean event = ActivityType.VALUES_EVENTS.contains(activityType)
                || ActivityType.VALUES_EVENTS_INTERMEDIATE
                        .contains(activityType);

        if (gateway) {
            IFigure diamondFigure = new ActivityDiamondFigure(getConnectionAnchorFactory());
            WrappingLabel label = new WrapLabelWithToolTip(getToolTipProvider(),
                    null, null, true, PositionConstants.TOP, PositionConstants.CENTER);
            buildFigure(diamondFigure, shape, label);
        } else if (event) {
            ActivityOvalFigure ovalFigure = new ActivityOvalFigure(
                    getConnectionAnchorFactory());
            WrappingLabel label = new WrapLabelWithToolTip(getToolTipProvider(),
                    null, null, true, PositionConstants.TOP, PositionConstants.CENTER);
            buildFigure(ovalFigure, shape, label);
        } else {
            wrappedFigure.add(shape);
            contentPane = setupContentPane(shape);
        }
        return figure;
    }

    /**
     * Default implementation treats passed figure as content pane. Respects
     * layout one may have set for generated figure.
     * 
     * @param nodeShape
     *            instance of generated figure class
     * @generated
     */
    protected IFigure setupContentPaneGen(IFigure nodeShape) {
        if (nodeShape.getLayoutManager() == null) {
            ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
            layout.setSpacing(getMapMode().DPtoLP(5));
            nodeShape.setLayoutManager(layout);
        }
        return nodeShape; // use nodeShape itself as contentPane
    }

    /**
     * @generated not
     */
    protected IFigure setupContentPane(IFigure nodeShape) {
        if (nodeShape.getLayoutManager() == null) {
            EObject element = resolveSemanticElement();
            
            int activityType = ActivityType.TASK;
            if (element instanceof Activity) {
                activityType = ((Activity) element).getActivityType().getValue();
            }
                    
            if (activityType == ActivityType.TASK) {
                StackLayout layout = new StackLayout();
                nodeShape.setLayoutManager(layout);
            } else {
                return setupContentPaneGen(nodeShape);
            }
        }
        return nodeShape; // use nodeShape itself as contentPane
    }

    /**
     * @generated
     */
    public IFigure getContentPane() {
        if (contentPane != null) {
            return contentPane;
        }
        return super.getContentPane();
    }

    /**
     * @generated
     */
    public EditPart getPrimaryChildEditPart() {
        return getChildBySemanticHint(BpmnVisualIDRegistry
                .getType(ActivityNameEditPart.VISUAL_ID));
    }

    /**
     * @generated
     */
    protected void addChildVisual(EditPart childEditPart, int index) {
        if (addFixedChild(childEditPart)) {
            return;
        }
        super.addChildVisual(childEditPart, -1);
    }

    /**
     * @generated
     */
    protected void removeChildVisual(EditPart childEditPart) {
        if (removeFixedChild(childEditPart)) {
            return;
        }
        super.removeChildVisual(childEditPart);
    }

    /**
     * 
     * @return true if activity type is event or gateway, false otherwise
     * @notgenerated
     */
    private boolean isEventOrGateway() {
        ActivityType activityType = ((Activity) getPrimaryView().getElement())
                .getActivityType();
        boolean gateway = ActivityType.VALUES_GATEWAYS.contains(activityType);
        boolean event = ActivityType.VALUES_EVENTS.contains(activityType)
                || ActivityType.VALUES_EVENTS_INTERMEDIATE
                        .contains(activityType);

        return gateway || event;
    }

    /**
     * Synchronizes the shape with the activityType
     * 
     * @notgenerated
     * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart
     *      #handlePropertyChangeEvent(java.beans.PropertyChangeEvent)
     */
    protected void handleNotificationEvent(Notification notification) {
        if (notification.getEventType() == Notification.SET ||
                notification.getEventType() == Notification.UNSET) {
            if (BpmnPackage.eINSTANCE.getActivity_ActivityType().equals(
                    notification.getFeature())) {
                handleMajorSemanticChange();
                return;
            } else if (BpmnPackage.eINSTANCE.getActivity_Looping().equals(
                    notification.getFeature())) {
                // looping changed
                getPrimaryShape().setLooping(notification.getNewBooleanValue());
            }/* else if (NotationPackage.eINSTANCE.getSize_Width().equals(
                            notification.getFeature())
                    || NotationPackage.eINSTANCE.getSize_Height().equals(
                            notification.getFeature())) {
            } else if (BpmnPackage.eINSTANCE.getNamedBpmnObject_Name().equals(
                    notification.getFeature())
                    && isEventOrGateway()) {
            }*/
            Activity activity = (Activity) getPrimaryView().getElement();
            if (getPrimaryShape() != null && 
                    setActivityTypeAndLabelAndLayout(getPrimaryShape(), activity)) {
                refreshVisuals();
            }
            if (NotationPackage.eINSTANCE.getSize_Width().equals(notification.getFeature()) ||
                    NotationPackage.eINSTANCE.getSize_Height().equals(notification.getFeature()) ||
                    NotationPackage.eINSTANCE.getLocation_X().equals(notification.getFeature()) ||
                    NotationPackage.eINSTANCE.getLocation_Y().equals(notification.getFeature()) ||
                    NotationPackage.eINSTANCE.getLocation().equals(notification.getFeature()) ||
                    NotationPackage.eINSTANCE.getLayoutConstraint().equals(notification.getFeature())) {
                for (Object e : getSourceConnections()) {
                    if (e instanceof ConnectionEditPart) {
                        ((ConnectionEditPart) e).getTarget().refresh();
                    }
                }
                for (Object e : getTargetConnections()) {
                    if (e instanceof ConnectionEditPart) {
                        ((ConnectionEditPart) e).getSource().refresh();
                    }
                }
                refresh();
            }
        }
        super.handleNotificationEvent(notification);
    }

    @Override
	public void refreshSourceConnections() {
        try {
            super.refreshSourceConnections();
            updateAnchors(true);
        } catch (Exception e) {
            EObject eo = resolveSemanticElement();
            IllegalArgumentException e2 = 
                new IllegalArgumentException("Corrupted model " + eo); //$NON-NLS-1$
            e2.initCause(e);
            throw e2;
        }
    }

    @Override
	public void refreshTargetConnections() {
        try {
            super.refreshTargetConnections();
            updateAnchors(false);
        } catch (Exception e) {
            EObject eo = resolveSemanticElement();
            IllegalArgumentException e2 = 
                new IllegalArgumentException("Corrupted model " + eo); //$NON-NLS-1$
            e2.initCause(e);
            throw e2;
        }
    }

    
    /**
     * Computes the indexes on the anchors so that they can compute their position.
     * 
     * @generated NOT
     */
    private void updateAnchors(boolean sourceOnly) {
        Map<EObject, ConnectionEditPart> connIndex =
            new HashMap<EObject, ConnectionEditPart>();
        
        for (Object object : getSourceConnections()) {
            ConnectionEditPart connEditPart = (ConnectionEditPart) object;
            View model = (View) connEditPart.getModel();
            connIndex.put(model.getElement(), connEditPart);
        }
        for (Object object : getTargetConnections()) {
            ConnectionEditPart connEditPart = (ConnectionEditPart) object;
            View model = (View) connEditPart.getModel();
            connIndex.put(model.getElement(), connEditPart);
        }
        EObject element = resolveSemanticElement();
        if (!(element instanceof Activity)) {
            return;
        }
        Activity act = (Activity) element;
        FeatureMap messages = act.getOrderedMessages();
        int totalLength = messages.size();
        int i = 0;
        for (Iterator<FeatureMap.Entry> it = messages.iterator(); it.hasNext(); ) {
            FeatureMap.Entry msg = it.next();
            switch (msg.getEStructuralFeature().getFeatureID()) {
            case BpmnPackage.MESSAGE_VERTEX__OUTGOING_MESSAGES:
                //sourceMsgs.put(msg.getValue(), i);
                setAnchorIndex(connIndex, (EModelElement)msg.getValue(),
                               i, totalLength, true);
                i++;
                break;
            case BpmnPackage.MESSAGE_VERTEX__INCOMING_MESSAGES:
                //targetMsgs.put(msg.getValue(), i);
                setAnchorIndex(connIndex, (EModelElement)msg.getValue(),
                               i, totalLength, false);
                i++;
                break;
            default: throw new IllegalStateException("Corrupted model?"); //$NON-NLS-1$
            }
        }
        //same for outgoing sequences:
        if (sourceOnly) {
            if (!isOrderImportant(this, sourceOnly)) {
                //set the anchor order according to the visuals.
                Node thisModel = (Node)getModel();
                List<Edge> seqs = getSourceSequenceEdges(thisModel, true);
                totalLength = seqs.size();
                int ind = 0;
                for (Edge srcEdge : seqs) {
                    setAnchorIndex(connIndex, (EModelElement)srcEdge.getElement(), i, totalLength, true);
                    ind++;
                }
            } else {
                //set the anchor order according to the index in the semantic model.
                EList<SequenceEdge> outEdges = act.getOutgoingEdges();
                int ind = 0;
                totalLength = outEdges.size();
                for (SequenceEdge edge : outEdges) {
                    setAnchorIndex(connIndex, edge, i, totalLength, true);
                    ind++;
                }
            }
        } else {
            //same for incoming sequences:
            if (!isOrderImportant(this, false)) {
                //set the anchor order according to the visuals.
                Node thisModel = (Node)getModel();
                List<Edge> seqs = getTargetSequenceEdges(thisModel, true);
                totalLength = seqs.size();
                int ind = 0;
                for (Edge targetEdge : seqs) {
                    setAnchorIndex(connIndex, 
                            (EModelElement)targetEdge.getElement(),
                            i, totalLength, false);
                    ind++;
                }
            } else {
                //set the anchor order according to the index in the semantic model.
                EList<SequenceEdge> inEdges = act.getIncomingEdges();
                int ind = 0;
                totalLength = inEdges.size();
                for (SequenceEdge edge : inEdges) {
                    setAnchorIndex(connIndex, edge, ind, totalLength, false);
                    ind++;
                }
            }
        }
        
        //and now for the associations where the activity is always the target:
        if (!sourceOnly) {
            int ii = 0;
            int l = act.getAssociations().size();
            for (Association assoc : act.getAssociations()) {
                setAnchorIndex(connIndex, assoc, ii, l, false);
                i++;
            }
        }
    }
    
    /**
     * @param obj The semantic model for the connection that anchor to the activity
     * is updated.
     * @return The string that is interpreted to place the anchor on the activity correctly.
     * by default the string is the visualid of the corresponding type of connection:
     * SequenceEdgeEditPart.VISUAL_ID for a sequence edge etc.
     */
    protected String getEdgeType(EModelElement obj) {
        if (obj instanceof SequenceEdge) {
            return String.valueOf(SequenceEdgeEditPart.VISUAL_ID);
        } else if (obj instanceof MessagingEdge) {
            return String.valueOf(MessagingEdgeEditPart.VISUAL_ID);
        } else if (obj instanceof Association) {
            return String.valueOf(AssociationEditPart.VISUAL_ID);
        } else {
            return null;
        }
    }
    
    protected void setAnchorIndex(Map<EObject, ConnectionEditPart> connIndex, 
            EModelElement semanticModelOfConn, int index, int totalLength, boolean isSource) {
        ConnectionEditPart conn = connIndex.get(semanticModelOfConn);
        if (conn == null) {
            //this is usually the sign of a corrupted model :(
//            System.err.println("Could not find a ConnectionEditPart for " +
//                    msgOrSequence);
        } else {
            String edgeType = getEdgeType(semanticModelOfConn);
            PolylineConnectionEx connFigure = 
                (PolylineConnectionEx) conn.getFigure();
            ConnectionAnchor anchor = isSource ?
                    connFigure.getSourceAnchor() : connFigure.getTargetAnchor();
            if (anchor instanceof IModelAwareAnchor) {
                ((IModelAwareAnchor) anchor).setConnectionType(isSource,
                        edgeType, index, totalLength);
                conn.refresh();
            }
        }

    }

    /**
     * @generated
     */
    public class ActivityFigure extends
            org.eclipse.stp.bpmn.figures.activities.ActivityFigure {

        /**
         * @generated
         */
        public ActivityFigure() {
            
            EObject a = resolveSemanticElement();
            if (a instanceof Activity) {//not sure why but I have seen stack traces
                //that might be fixed by this.
                this.setActivityType(((Activity) a).getActivityType().getLiteral());
            } else {
                this.setActivityType(ActivityType.TASK_LITERAL.getLiteral());
            }
            this.setForegroundColor(org.eclipse.draw2d.ColorConstants.black);

            createContents();
            
        }

        /**
         * @notgenerated
         */
        private void createContents() {
            if (!(resolveSemanticElement() instanceof Activity) || 
                    ((Activity) resolveSemanticElement()).getActivityType().
                            equals(ActivityType.TASK_LITERAL)) {
                createContentsGen();
            }
        }

        /**
         * @generated not
         * for sinister reasons, we want to avoid
         * the resize of the activity by its label
         * when its size is not initialized.
         */
        private void createContentsGen() {
            //org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel fig_0 = new org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel();
            WrapLabelWithToolTip fig_0 = new WrapLabelWithToolTip(
                    ActivityEditPart.this.getToolTipProvider(), null, null, true,
                    PositionConstants.TOP, PositionConstants.TOP);
                    //PositionConstants.CENTER, PositionConstants.CENTER);
            fig_0.setText(BpmnDiagramMessages.ActivityEditPart_task_default_name);
                        
            setFigureActivityNameFigure(fig_0);

            Object layData0 = null;

            this.add(fig_0, layData0);
        }

        /**
         * @generated NOT WrappingLabel
         */
        private WrappingLabel fActivityNameFigure;

        /**
         * @generated NOT WrappingLabel
         */
        public WrappingLabel getFigureActivityNameFigure() {
            return fActivityNameFigure;
        }

        /**
         * @generated NOT WrappingLabel
         */
        private void setFigureActivityNameFigure(WrappingLabel fig) {
            fActivityNameFigure = fig;
        }

        /**
         * @generated
         */
        private boolean myUseLocalCoordinates = false;

        /**
         * @generated
         */
        protected boolean useLocalCoordinates() {
            return myUseLocalCoordinates;
        }

        /**
         * @generated
         */
        protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
            myUseLocalCoordinates = useLocalCoordinates;
        }

        /**
         * @generated NOT
         * never a event handler
         */
        @Override
        public boolean isCatching() {
            Activity activity = (Activity) resolveSemanticElement();
            if (activity == null) {
                return false;
            }
            if (ActivityType.VALUES_EVENTS_INTERMEDIATE.contains(activity.getActivityType())) {
                if (!activity.getOrderedMessages().isEmpty()) {
                    return activity.getIncomingMessages().contains(
                            activity.getOrderedMessages().get(0).getValue());
                } else {
                    String str = EcoreUtil.getAnnotation(activity, 
                            SetAsThrowingOrCatchingAction.IS_THROWING_ANNOTATION_SOURCE_AND_KEY_ID, 
                            SetAsThrowingOrCatchingAction.IS_THROWING_ANNOTATION_SOURCE_AND_KEY_ID);
                    if ("true".equals(str)) { //$NON-NLS-1$
                        return false;
                    } else {
                        return true;
                    }
                }
            } else if (ActivityType.VALUES_EVENTS_START.contains(activity.getActivityType())) {
                return true;
            } else {
                return false;
            }
           
        }

    }

    /**
     * @generated NOT
     */
    @Override
    public EditPolicy getPrimaryDragEditPolicy() {
        return new ResizableActivityEditPolicy();
    }

    /**
     * @generated not
     * @param activityType
     * @return the shape type, one of 
     * SHAPE_CIRCLE, SHAPE_RECTANGLE, or SHAPE_DIAMOND depending
     * on the activity type passed as a parameter
     */
    private static int getShapeType(int activityType) {
        int shapeType;
        switch (activityType) {
        case ActivityType.TASK:
            shapeType = SHAPE_RECTANGLE;
            break;
        case ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE:
        case ActivityType.GATEWAY_DATA_BASED_INCLUSIVE:
        case ActivityType.GATEWAY_EVENT_BASED_EXCLUSIVE:
        case ActivityType.GATEWAY_PARALLEL:
        case ActivityType.GATEWAY_COMPLEX:
            shapeType = SHAPE_DIAMOND;
            break;
        default:
            shapeType = SHAPE_CIRCLE;
        }
        return shapeType;
    }

    /**
     * @notgenerated
     */
    @Override
    public DragTracker getDragTracker(Request request) {
        return new TaskDragEditPartsTrackerEx(this);
    }
    
    /**
     * Override to for a more specialized tooltip
     * @generated NOT
     */
    protected IToolTipProvider getToolTipProvider() {
        return ActivityPainter.createToolTipProvider(
            (NamedBpmnObject)resolveSemanticElement(), true);
    }

    /**
     * 
     * @param isSource, true if this method should examine the source,
     * false if it should look at the target
     * @param forOutgoing true if the question is related to outgoing messages, false for incoming.
     * forOutgoing == isSource.
     * !forOugoing == !isSource
     * @return true if the semantice order of the edges over the part to examine is important.
     */
    public static boolean isOrderImportant(IGraphicalEditPart part, boolean forOutgoing) {
        if (!forOutgoing || part.resolveSemanticElement() == null) {
            return false;
        }
        Activity act = (Activity)part.resolveSemanticElement();
        ActivityType type = act.getActivityType();
        if (type.equals(ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE_LITERAL) ||
                type.equals(ActivityType.GATEWAY_DATA_BASED_INCLUSIVE_LITERAL)) {
            if (act.getOutgoingEdges().size() == 2) {
                for (SequenceEdge se : act.getOutgoingEdges()) {
                    if (se.isIsDefault()
                            || (se.getConditionType() != null
                             && se.getConditionType().getValue() == SequenceFlowConditionType.DEFAULT)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    
    /**
     * @generated NOT
     * this method is used to know whether the action 
     * "Set as a throwing shape" and "Set as a catching shape" should show
     */
    public boolean shouldShowSetAsThrowingOrCatching() {
        boolean b = BpmnDiagramEditorPlugin.getInstance().getPreferenceStore().
            getBoolean(BpmnDiagramPreferenceInitializer.PREF_BPMN1_1_STYLE);
        if (!b) {
            return false;
        }
        return resolveSemanticElement() instanceof Activity &&
            ((Activity) resolveSemanticElement()).getOrderedMessages().isEmpty() &&
                ActivityType.VALUES_EVENTS.contains(
                    ((Activity) resolveSemanticElement()).getActivityType());
        
    }
    
    /**
     * the comparator to compare edges depending on the y coordinates of their sources
     */
    private static final EdgeComparator EDGE_AT_START_COMPARATOR = new EdgeComparator(true);
    
    /**
     * the comparator to compare edges depending on the y coordinates of their targets
     */
    private static final EdgeComparator EDGE_AT_END_COMPARATOR = new EdgeComparator(false);
    
    /**
     * Sorts a list of views according to the the y coordinates of the shape either at
     * the end or at the beginning of the connection.
     * @param coll
     * @param compareWithSources true to compare according to the y coord at the shape at the start
     * of the connection. false to compare at the shape of the end of the connection
     */
    private static class EdgeComparator implements Comparator<Edge> {
        /**
         * whether the comparison should be on source or targets of the edges.
         */
        private boolean compareWithSources;
        
        EdgeComparator(boolean accordingToStartY) {
            this.compareWithSources = accordingToStartY;
        }
        
        public int compare(Edge o1, Edge o2) {
            Node n1 = null, n2 = null;
            if (compareWithSources) {
                n1 = (Node)o1.getSource();
                n2 = (Node)o2.getSource();
            } else {
                n1 = (Node)o1.getTarget();
                n2 = (Node)o2.getTarget();
            }
            Bounds y1 = BpmnShapesDefaultSizes.getBounds(n1);
            Bounds y2 = BpmnShapesDefaultSizes.getBounds(n2);
            
            return (2*y1.getY() + y1.getHeight())
                    - (2*y2.getY() + y2.getHeight());
        }
    }
    
    /**
     * @param thisModel
     * @param dosort true to sort the outgoing edges according to the coordinates
     * of the shape at the end of the connection.
     * @return The list of source edges that are for a outgoing SequenceEdge.
     */
    public static List<Edge> getSourceSequenceEdges(Node thisModel, boolean dosort) {
        List<Edge> res = new ArrayList<Edge>();
        for (Object e : thisModel.getSourceEdges()) {
            Edge ee = (Edge)e;
            if (ee.getElement() instanceof SequenceEdge) {
                res.add(ee);
            }
        }
        if (dosort) {//when we sort outgoing edges we must do it looking at the end of them
            Collections.sort(res, EDGE_AT_END_COMPARATOR);
        }
        return res;
    }
    /**
     * @param thisModel
     * @param dosort true to sort the outgoing edges according to the coordinates
     * of the shape at the end of the connection.
     * @return The list of target edges that are for a incoming SequenceEdge.
     */
    public static List<Edge> getTargetSequenceEdges(Node thisModel, boolean dosort) {
        List<Edge> res = new ArrayList<Edge>();
        for (Object e : thisModel.getTargetEdges()) {
            Edge ee = (Edge)e;
            if (ee.getElement() instanceof SequenceEdge) {
                if (ee.getSource() != null && ee.getTarget() != null) {
                    res.add(ee);
                }
            }
        }
        if (dosort) {//when we sort outgoing edges we must do it looking at the start of them
            Collections.sort(res, EDGE_AT_START_COMPARATOR);
        }
        return res;
    }
    
    /**
     * @param The edge for which we need to compute the visual index of its start
     * anchor.
     * @return The visual index of its start anchor.
     */
    public static int getStartAnchorVisualIndex(Edge thisEdge) {
        Node target = (Node) thisEdge.getSource();
        List<Edge> res = getSourceSequenceEdges(target, true);
        return res.indexOf(thisEdge);
    }
    /**
     * @param The edge for which we need to compute the visual index of its start
     * anchor.
     * @return The visual index of its start anchor.
     */
    public static int getTargetAnchorVisualIndex(Edge thisEdge) {
        Node source = (Node) thisEdge.getTarget();
        List<Edge> res = getTargetSequenceEdges(source, true);
        return res.indexOf(thisEdge);
    }
////TESTING
//    public Command getCommand(Request request) {
//        Command command = null;
//        EditPolicyIterator i = getEditPolicyIterator();
//        while (i.hasNext()) {
//            EditPolicy ep = i.next();
//            Command nextCommand = ep.getCommand(request);
//            if (nextCommand == null) {
//                continue;
//            }
//            if (!nextCommand.canExecute()) {
//                System.err.println("Edit Policy " + ep + " returned a non exec command on " + this.resolveSemanticElement());
//            }
//            if (command != null)
//                command = command.chain(nextCommand);
//            else
//                command = nextCommand;
//        }
//        return command;
//    }
    
    
}
