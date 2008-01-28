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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.AbstractFlowBorder;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
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
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramAssistantEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.NamedBpmnObject;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.policies.ActivityCanonicalEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.ActivityGraphicalNodeEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.ActivityItemSemanticEditPolicy;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.figures.WrapLabelWithToolTip;
import org.eclipse.stp.bpmn.figures.WrapLabelWithToolTip.IToolTipProvider;
import org.eclipse.stp.bpmn.figures.activities.ActivityDiamondFigure;
import org.eclipse.stp.bpmn.figures.activities.ActivityNodeFigure;
import org.eclipse.stp.bpmn.figures.activities.ActivityOvalFigure;
import org.eclipse.stp.bpmn.figures.activities.ActivityPainter;
import org.eclipse.stp.bpmn.figures.connectionanchors.IConnectionAnchorFactory;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchor;
import org.eclipse.stp.bpmn.figures.connectionanchors.WrapperNodeFigureEx;
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
     * chaged when activity type is changed.
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
        super.getViewer().getEditDomain();
        // replace ConnectionHandleEditPolicy with our own
        removeEditPolicy(EditPolicyRoles.CONNECTION_HANDLES_ROLE);
        installEditPolicy(EditPolicyRoles.CONNECTION_HANDLES_ROLE,
                createConnectionHandlerEditPolicy());
        // adding default drag and drop edit policy
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
                new BpmnDragDropEditPolicy(this));
        // adding an open edit policy
        installEditPolicy(EditPolicyRoles.OPEN_ROLE,
        		new OpenFileEditPolicy());
    }
    
    /**
     * Ability to override the ConnectionHandleEditPolicy.
     * @generated NOT
     */
    protected DiagramAssistantEditPolicy createConnectionHandlerEditPolicy() {
        return new ConnectionHandleEditPolicyEx();
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
        Activity activity = (Activity) getPrimaryView().getElement();
        setActivityTypeAndLabelAndLayout(figure, activity);
        figure.setLooping(activity.isLooping());
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
        WrapLabel wl = activityFigure.getFigureActivityNameFigure();
        if (activity.getName() == null) {
            if (activity.getActivityType().equals(ActivityType.TASK_LITERAL)) {
                if (!BpmnDiagramMessages.ActivityEditPart_task_default_name.equals(wl.getText())) {
                    wl.setText(BpmnDiagramMessages.ActivityEditPart_task_default_name);
                }
            }
            res = true;
        }

        if (activity.getActivityType().equals(ActivityType.TASK_LITERAL)) {
            if (!(activityFigure.getLayoutManager() instanceof StackLayout)) {
                StackLayout layout = new StackLayout();
                activityFigure.setLayoutManager(layout);
                res = true;
            }
            wl.setTextAlignment(PositionConstants.MIDDLE);
        } else {
            if (wl != null) {
                wl.setTextAlignment(PositionConstants.TOP);
                wl.setPreferredSize(ACTIVITY_FIGURE_SIZE.width, 0);
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
     * @notgenerated
     */
    protected NodeFigure createNodePlate() {

        // final Activity2EditPart zis = this;

        return new WrapperNodeFigureEx(getConnectionAnchorFactory(),
                createWrappedFigure()) {
            @Override
            /**
             * Exclude activity name label from handle bounds
             */
            public Rectangle getHandleBounds() {
                Rectangle rectangle = super.getHandleBounds();
                Activity activity = (Activity) resolveSemanticElement();
                if (activity == null || activity.getActivityType() == null || 
                		activity.getActivityType().equals(ActivityType.TASK_LITERAL)) {
                    return rectangle;
                }

                return ((IFigure) wrappedFigure.getChildren().get(0)).getBounds();
//                IGraphicalEditPart activityNameEditPart = getChildBySemanticHint(BpmnVisualIDRegistry
//                        .getType(org.eclipse.stp.bpmn.diagram.edit.parts.ActivityNameEditPart.VISUAL_ID));
//                int nameFigureHeight = activityNameEditPart == null 
//                		|| activityNameEditPart.getFigure() == null ? 
//                		0 : activityNameEditPart.getFigure().getSize().height;
//
//                return new Rectangle(rectangle.x, rectangle.y, rectangle.width,
//                        rectangle.height - nameFigureHeight);

            };

        };
    }

    /**
     * @notgenerated
     * @return
     */
    private INodeFigureAnchorTerminalUpdatable createWrappedFigure() {
        Activity activity = (Activity) getPrimaryView().getElement();
        final int activityType = activity.getActivityType().getValue();

        int shapeType = getShapeType(activityType);
        if (shapeType == SHAPE_RECTANGLE) {
            int width = getMapMode().DPtoLP(ACTIVITY_FIGURE_SIZE.width);
            int height = getMapMode().DPtoLP(ACTIVITY_FIGURE_SIZE.height);
            wrappedFigure = new ActivityNodeFigure(getConnectionAnchorFactory(),
                                                    width, height);
        } else if (shapeType == SHAPE_DIAMOND) {
        	int width = getMapMode().DPtoLP(GATEWAY_FIGURE_SIZE);
            int height = getMapMode().DPtoLP(GATEWAY_FIGURE_SIZE);
            wrappedFigure = new ActivityNodeFigure(getConnectionAnchorFactory(),
                                                    width, height);
        } else {
        	int width = getMapMode().DPtoLP(EVENT_FIGURE_SIZE);
            int height = getMapMode().DPtoLP(EVENT_FIGURE_SIZE);
            wrappedFigure = new ActivityNodeFigure(getConnectionAnchorFactory(),
                                                    width, height);
        }
        wrappedFigure.setLayoutManager(new StackLayout());
        return wrappedFigure;
    }

    /**
     * @notgenerated Used for gateway and event bpmns shapes.
     */
    private void buildFigure(IFigure container, IFigure shape,
            WrapLabel wrapLabel, int size) {
        int resultSize = getMapMode().DPtoLP(size);
        container.setSize(resultSize, resultSize + 30);

        ActivityLayout layout = new ActivityLayout();
        wrappedFigure.setLayoutManager(layout);
        container.setLayoutManager(new BorderLayout());
        container.add(shape, BorderLayout.CENTER);

        wrappedFigure.add(container, BorderLayout.CENTER);
        wrappedFigure.add(wrapLabel, BorderLayout.BOTTOM);
        
//        wrapLabel.setTextWrap(true);
        wrapLabel.setLabelAlignment(PositionConstants.CENTER);
//        if (isEventOrGateway) {
//            //in that case the label is placed below the event marker shape.
//            //the label should be as close as possible to the figure
//            //above hence the text alignment.
//            wrapLabel.setTextAlignment(PositionConstants.TOP);
//        }
        ((ActivityFigure) shape).setFigureActivityNameFigure(wrapLabel);

        contentPane = setupContentPane(wrappedFigure);
    }




    /**
     * Creates figure for this edit part.
     * 
     * Body of this method does not depend on settings in generation model so
     * you may safely remove <i>generated</i> tag and modify it.
     * 
     * @notgenerated
     */
    protected NodeFigure createNodeFigure() {
        NodeFigure figure = createNodePlate();
        IFigure shape = createNodeShape();

        ActivityType activityType = ((Activity) getPrimaryView().getElement())
                .getActivityType();
        boolean gateway = ActivityType.VALUES_GATEWAYS.contains(activityType);
        boolean event = ActivityType.VALUES_EVENTS.contains(activityType)
                || ActivityType.VALUES_EVENTS_INTERMEDIATE
                        .contains(activityType);

        if (gateway) {
            int size = getMapMode().DPtoLP(GATEWAY_FIGURE_SIZE);
            IFigure diamondFigure = new ActivityDiamondFigure(
                   getConnectionAnchorFactory(), new Dimension(size, size));
            WrapLabel label = new WrapLabelWithToolTip(getToolTipProvider(),
                    null, null, true, PositionConstants.TOP);
            buildFigure(diamondFigure, shape, label, GATEWAY_FIGURE_SIZE);
        } else if (event) {
            int size = getMapMode().DPtoLP(EVENT_FIGURE_SIZE);
            ActivityOvalFigure ovalFigure = new ActivityOvalFigure(
                    getConnectionAnchorFactory(),
                    new Dimension(size, size));
            WrapLabel label = new WrapLabelWithToolTip(getToolTipProvider(),
                    null, null, true, PositionConstants.TOP);
            buildFigure(ovalFigure, shape, label, EVENT_FIGURE_SIZE);
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
     * @notgenerated
     */
    protected IFigure setupContentPane(IFigure nodeShape) {
        if (nodeShape.getLayoutManager() == null) {
            EObject element = ((Node) getModel()).getElement();
            int activityType = ((Activity) element).getActivityType()
                    .getValue();
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
        Activity act = (Activity) (((Node) getModel()).getElement());
        FeatureMap messages = act.getOrderedMessages();
        int totalLength = messages.size();
        int i = 0;
        for (Iterator<FeatureMap.Entry> it = messages.iterator(); it.hasNext(); ) {
            FeatureMap.Entry msg = it.next();
            switch (msg.getEStructuralFeature().getFeatureID()) {
            case BpmnPackage.ACTIVITY__OUTGOING_MESSAGES:
                //sourceMsgs.put(msg.getValue(), i);
                setAnchorIndex(connIndex, (EModelElement)msg.getValue(),
                               i, totalLength, true);
                i++;
                break;
            case BpmnPackage.ACTIVITY__INCOMING_MESSAGES:
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
            EList<SequenceEdge> outEdges = act.getOutgoingEdges();
            int ind = 0;
            totalLength = outEdges.size();
            for (SequenceEdge edge : outEdges) {
                setAnchorIndex(connIndex, edge, i, totalLength, true);
                ind++;
            }
        } else {
            //same for incoming sequences:
            EList<SequenceEdge> inEdges = act.getIncomingEdges();
            int ind = 0;
            totalLength = inEdges.size();
            for (SequenceEdge edge : inEdges) {
                setAnchorIndex(connIndex, edge, i, totalLength, false);
                ind++;
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
            Activity a = (Activity) resolveSemanticElement();
            if (a != null) {//not sure why but I have seen stack traces
                //that might be fixed by this.
                this.setActivityType(a.getActivityType().getLiteral());
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
            ActivityType activityType = ((Activity) getPrimaryView()
                    .getElement()).getActivityType();
            if (activityType.equals(ActivityType.TASK_LITERAL)) {
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
                    PositionConstants.CENTER);
            fig_0.setText(BpmnDiagramMessages.ActivityEditPart_task_default_name);
            
            setBorder(new AbstractFlowBorder() {
            	@Override
            	public int getLeftMargin() {
            		return 2;
            	}

            	@Override
            	public int getRightMargin() {
            		return 2;
            	}
            	
            	@Override
            	public Insets getInsets(IFigure figure) {
            		return new Insets(0, 2, 0, 2);
            	}
            });
            
            setFigureActivityNameFigure(fig_0);

            Object layData0 = null;

            this.add(fig_0, layData0);
        }

        /**
         * @generated
         */
        private org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel fActivityNameFigure;

        /**
         * @generated
         */
        public org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel getFigureActivityNameFigure() {
            return fActivityNameFigure;
        }

        /**
         * @generated
         */
        private void setFigureActivityNameFigure(
                org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel fig) {
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

    }

    @Override
    /**
     * @notgenerated
     */
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

    
}
