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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.emf.common.notify.Notification;
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
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.NamedBpmnObject;
import org.eclipse.stp.bpmn.diagram.edit.policies.DataObjectCanonicalEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.DataObjectGraphicalNodeEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.DataObjectItemSemanticEditPolicy;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.figures.WrapLabelWithToolTip;
import org.eclipse.stp.bpmn.figures.WrapLabelWithToolTip.IToolTipProvider;
import org.eclipse.stp.bpmn.figures.activities.ActivityPainter;
import org.eclipse.stp.bpmn.policies.BpmnDragDropEditPolicy;
import org.eclipse.stp.bpmn.policies.ConnectionHandleEditPolicyEx;
import org.eclipse.stp.bpmn.policies.OpenFileEditPolicy;
import org.eclipse.stp.bpmn.policies.ResizableArtifactEditPolicy;
import org.eclipse.stp.bpmn.tools.TaskDragEditPartsTrackerEx;
import org.eclipse.stp.gmf.runtime.draw2d.ui.figures.WrappingLabel;

/**
 * This is for data-objects contained in a pool or a sub-process.
 * @generated
 */
public class DataObjectEditPart extends ShapeNodeEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 2005;

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
    public DataObjectEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    protected void createDefaultEditPoliciesGen() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new DataObjectItemSemanticEditPolicy());
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new DataObjectGraphicalNodeEditPolicy());
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new DataObjectCanonicalEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
    }

    /**
     * @generated NOT
     * Extending the edit policies for darg and drop, handles, connections.
     */
    protected void createDefaultEditPolicies() {
    	createDefaultEditPoliciesGen();
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
    protected IFigure createNodeShape() {
        DataObjectFigure figure = new DataObjectFigure();
        return primaryShape = figure;
    }

    /**
     * @generated
     */
    public DataObjectFigure getPrimaryShape() {
        return (DataObjectFigure) primaryShape;
    }

    /**
     * @generated
     */
    protected boolean addFixedChild(EditPart childEditPart) {
        if (childEditPart instanceof DataObjectNameEditPart) {
            ((DataObjectNameEditPart) childEditPart).setLabel(getPrimaryShape()
                    .getFigureDataObjectNameFigure());
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
     * @generated NOT override to add the tooltip.
     */
    protected NodeFigure createNodePlate() {
        return new DefaultSizeNodeFigure(getMapMode().DPtoLP(40), getMapMode()
                .DPtoLP(40));
    }

    /**
     * Creates figure for this edit part.
     * 
     * Body of this method does not depend on settings in generation model
     * so you may safely remove <i>generated</i> tag and modify it.
     * 
     * @generated
     */
    protected NodeFigure createNodeFigure() {
        NodeFigure figure = createNodePlate();
        figure.setLayoutManager(new StackLayout());
        IFigure shape = createNodeShape();
        figure.add(shape);
        contentPane = setupContentPane(shape);
        return figure;
    }

    /**
     * Default implementation treats passed figure as content pane.
     * Respects layout one may have set for generated figure.
     * @param nodeShape instance of generated figure class
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
                .getType(DataObjectNameEditPart.VISUAL_ID));
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
     * @return The size of the triangle at the top left of the figure
     * @generated NOT
     */
    protected int getFoldSize() {
        return 16;
    }
    
    /**
     * Ability to set different constraints to the layout manager
     * By default leaves the standard centered alignment.
     * @generated NOT
     */
    protected void setLayoutManagerConstraints(DataObjectFigure fig,
            WrappingLabel fig_0) {
    }
    

    /**
     * @generated
     */
    public class DataObjectFigure extends
            org.eclipse.stp.bpmn.figures.DataObjectFigure {

        /**
         * @generated NOT
         */
        public DataObjectFigure() {
            super(DataObjectEditPart.this.getFoldSize());
            createContents();
        }

        /**
         * set the figure wrap label to true, and set the label
         * to the left.
         * also added a tooltip
         * @generated NOT
         */
        private void createContents() {
            ToolbarLayout layout = new ToolbarLayout();
            layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
            setLayoutManager(new ToolbarLayout());
            //org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel fig_0 = new org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel();
            WrapLabelWithToolTip fig_0 = new WrapLabelWithToolTip(
                    DataObjectEditPart.this.getToolTipProvider(),
                    null, null, true, PositionConstants.MIDDLE | PositionConstants.LEFT,
                    PositionConstants.LEFT);
            fig_0.setText(""); //$NON-NLS-1$
            fig_0.setTextAlignment(PositionConstants.CENTER);
            setFigureDataObjectNameFigure(fig_0);

            //Object layData0 = BorderLayout.CENTER;
            this.add(fig_0, null);
            
            //more controle for edit parts that override this default edit part implementation.
            DataObjectEditPart.this.setLayoutManagerConstraints(this, fig_0);
        }

        /**
         * @generated NOT WrappingLabel
         */
        private WrappingLabel fDataObjectNameFigure;

        /**
         * @generated NOT WrappingLabel
         */
        public WrappingLabel getFigureDataObjectNameFigure() {
            return fDataObjectNameFigure;
        }

        /**
         * @generated
         */
        private void setFigureDataObjectNameFigure(WrappingLabel fig) {
            fDataObjectNameFigure = fig;
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
    /**
     * @generated NOT
     */
    @Override
    public EditPolicy getPrimaryDragEditPolicy() {
        return new ResizableArtifactEditPolicy();
    }

    /**
     * @generated NOT
     */
    @Override
    public DragTracker getDragTracker(Request request) {
        return new TaskDragEditPartsTrackerEx(this);
    }
    
    /**
     * @generated NOT
     * Make sure changing the visibility of the view refreshes
     * the source and target connections.
     */
    @Override
    protected void handleNotificationEvent(Notification notification) {
        Object feature = notification.getFeature();
        if (NotationPackage.eINSTANCE.getView_Visible().equals(feature)) {
            refreshSourceConnections();
            refreshTargetConnections();
        }
        super.handleNotificationEvent(notification);
    }

    
    /**
     * Override to for a more specialized tooltip
     */
    protected IToolTipProvider getToolTipProvider() {
        return ActivityPainter.createToolTipProvider(
            (NamedBpmnObject)resolveSemanticElement(), true);
    }
    
}
