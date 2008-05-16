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

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
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
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.NamedBpmnObject;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart.ActivityFigure;
import org.eclipse.stp.bpmn.diagram.edit.policies.Activity2CanonicalEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.Activity2GraphicalNodeEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.Activity2ItemSemanticEditPolicy;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.figures.WrapLabelWithToolTip;
import org.eclipse.stp.bpmn.figures.WrapLabelWithToolTip.IToolTipProvider;
import org.eclipse.stp.bpmn.figures.activities.ActivityNodeFigure;
import org.eclipse.stp.bpmn.figures.activities.ActivityOvalFigure;
import org.eclipse.stp.bpmn.figures.activities.ActivityPainter;
import org.eclipse.stp.bpmn.figures.connectionanchors.IConnectionAnchorFactory;
import org.eclipse.stp.bpmn.figures.connectionanchors.impl.ConnectionAnchorFactory;
import org.eclipse.stp.bpmn.layouts.ActivityLayout;
import org.eclipse.stp.bpmn.policies.BpmnDragDropEditPolicy;
import org.eclipse.stp.bpmn.policies.ConnectionHandleEditPolicyEx;
import org.eclipse.stp.bpmn.policies.ResizableActivityEditPolicy;
import org.eclipse.stp.gmf.runtime.draw2d.ui.figures.WrappingLabel;

/**
 * @generated
 */
public class Activity2EditPart extends ShapeNodeEditPart {
    /**
     * @notgenerated
     */
    private static final int EVENT_FIGURE_SIZE = 30;

    /**
     * @generated
     */
    public static final int VISUAL_ID = 2003;

    /**
     * @generated
     */
    protected IFigure contentPane;

    /**
     * @generated
     */
    protected IFigure primaryShape;

    /**
     * @generated
     */
    public Activity2EditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    protected void createDefaultEditPoliciesGen() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new Activity2ItemSemanticEditPolicy());
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new Activity2GraphicalNodeEditPolicy());
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new Activity2CanonicalEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
    }
    /**
     * @generated NOT
     */
    protected void createDefaultEditPolicies() {
        createDefaultEditPoliciesGen();
//      replace ConnectionHandleEditPolicy with our own
        removeEditPolicy(EditPolicyRoles.CONNECTION_HANDLES_ROLE);
        installEditPolicy(EditPolicyRoles.CONNECTION_HANDLES_ROLE,
                createConnectionHandlerEditPolicy());
     // adding default drag and drop edit policy
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, 
        		new BpmnDragDropEditPolicy(this));
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
                EditPolicy result = child
                        .getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
                if (result == null) {
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

    /**
     * @generated
     */
    protected IFigure createNodeShapeGen() {
        ActivityFigure figure = new ActivityFigure();
        return primaryShape = figure;
    }
    
    /**
     * Helper method
     * @return The figure for the oval or diamond or rectangle figure.
     */
    public IFigure getHandleBoundsFigure() {
        if (primaryShape.getLayoutManager() instanceof ActivityLayout) {
            return ((ActivityLayout)primaryShape.getLayoutManager()).getOvalOrDiamondFigure();
        }
        return getPrimaryShape();
    }

    /**
     * @notgenerated
     */
    protected IFigure createNodeShape() {
        ActivityFigure figure = (ActivityFigure) createNodeShapeGen();
        setActivityTypeAndLabel(figure, (Activity) getPrimaryView()
                .getElement());
        return figure;
    }

    /**
     * Sets the acivity type on the figure.
     * Computes the default label if no name is set on the domain model object.
     * The default label is empty for all activities except the Task.
     * @param activityFigure
     * @param activity
     * @return
     */
    private static boolean setActivityTypeAndLabel(
            ActivityFigure activityFigure, Activity activity) {
        activityFigure.setActivityType(activity.getActivityType().getLiteral());
        if (activity.getName() == null) {
            WrappingLabel wl = activityFigure.getFigureActivityNameFigure();
            if (activity.getActivityType().getValue() == ActivityType.TASK) {
                if (!BpmnDiagramMessages.ActivityEditPart_task_default_name.equals(wl.getText())) {
                    wl.setText(BpmnDiagramMessages.ActivityEditPart_task_default_name);
                }
            } else {
                if (!"".equals(wl.getText())) { //$NON-NLS-1$
                    wl.setText(""); //$NON-NLS-1$
                    return true;
                }
            }
        }
        return false;
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
        if (childEditPart instanceof ActivityName2EditPart) {
            ((ActivityName2EditPart) childEditPart).setLabel(getPrimaryShape()
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
     * @generated not
     */
    protected NodeFigure createNodePlate() {
        int size = getMapMode().DPtoLP(EVENT_FIGURE_SIZE);
        return new ActivityNodeFigure(getConnectionAnchorFactory(), size, size, true);
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
        NodeFigure figure = createNodePlate();
        IFigure shape = createNodeShape();
        IFigure ovalOrDiamond = new ActivityOvalFigure(getConnectionAnchorFactory());
        ActivityLayout layout = new ActivityLayout();
        figure.setLayoutManager(layout);
        WrappingLabel wrapLabel = new WrapLabelWithToolTip(getToolTipProvider(),
                null, null, true, PositionConstants.TOP, PositionConstants.CENTER);
        ovalOrDiamond.setLayoutManager(new BorderLayout());
        ovalOrDiamond.add(shape, BorderLayout.CENTER);
        
        figure.add(ovalOrDiamond, BorderLayout.CENTER);
        figure.add(wrapLabel, BorderLayout.BOTTOM);
        
        ActivityFigure activityFigure = (ActivityFigure)shape;
        activityFigure.setFigureActivityNameFigure(wrapLabel);

        contentPane = setupContentPane(figure);
        EObject sem = resolveSemanticElement();
//        if (sem instanceof Activity) {
//            setAlignments(activityFigure, (Activity)sem, wrapLabel, true);
//        }
        
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
    protected IFigure setupContentPane(IFigure nodeShape) {
        if (nodeShape.getLayoutManager() == null) {
            ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
            layout.setSpacing(getMapMode().DPtoLP(5));
            nodeShape.setLayoutManager(layout);
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
                .getType(ActivityName2EditPart.VISUAL_ID));
    }

    /**
     * @generated NOT
     */
    @Override
    public EditPolicy getPrimaryDragEditPolicy() {
        ResizableActivityEditPolicy r = new ResizableActivityEditPolicy();
        r.setDragAllowed(false);
        return r;
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
     * Synchronizes the shape with the activityType
     * 
     * @notgenerated
     * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#handlePropertyChangeEvent(java.beans.PropertyChangeEvent)
     */
    protected void handleNotificationEvent(Notification notification) {
        if (notification.getEventType() == Notification.SET
                && BpmnPackage.eINSTANCE.getActivity_ActivityType().equals(
                        notification.getFeature())) {
            getPrimaryShape().setActivityType(
                    ((ActivityType) notification.getNewValue()).getLiteral());
            // System.err.println("JUST SET ACTIVITY_TYPE_ONSUB_PROCESS'S
            // ACTIVITY");
            // refreshVisuals();
        }
        super.handleNotificationEvent(notification);
    }

    /**
     * @generated
     */
    public class ActivityFigure extends
            org.eclipse.stp.bpmn.figures.activities.ActivityFigure {

        /**
         * @generated not
         * setting the activity type of the figure.
         * setting the size
         */
        public ActivityFigure() {
            super();
            this.setActivityType(ActivityType.TASK_LITERAL.getName());

            this.setForegroundColor(org.eclipse.draw2d.ColorConstants.black

            );

            int size = getMapMode().DPtoLP(EVENT_FIGURE_SIZE);
//            setPreferredSize(size, size);
            setSize(size, size);
            createContents();
        }

        /**
         * @generated WrappingLabel
         */
        private void createContentsGen() {
            WrappingLabel fig_0 =
                new WrapLabelWithToolTip(Activity2EditPart.this.getToolTipProvider(),
                        null, null, true, PositionConstants.TOP, PositionConstants.TOP);
            setFigureActivityNameFigure(fig_0);

            Object layData0 = null;

            this.add(fig_0, layData0);
        }
        
        /**
         * @generated not
         * don't add the label on the shape.
         * set text wrap to true
         */
        private void createContents() {
            //org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel fig_0 = new org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel();
            WrapLabelWithToolTip fig_0 = new WrapLabelWithToolTip(
                    Activity2EditPart.this.getToolTipProvider(),
                    null, null, true, PositionConstants.TOP, PositionConstants.TOP);
            setFigureActivityNameFigure(fig_0);

//            Object layData0 = null;

//            this.add(fig_0, layData0);
        }

        /**
         * @generated NOT 
         */
        private WrappingLabel fActivityNameFigure;

        /**
         * @generated NOT
         */
        public WrappingLabel getFigureActivityNameFigure() {
            return fActivityNameFigure;
        }

        /**
         * @generated NOT wrapping labels
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
         * always catching
         */
        @Override
        public boolean isCatching() {
            return true;
        }

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
