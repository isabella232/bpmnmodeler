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
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
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
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.NamedBpmnObject;
import org.eclipse.stp.bpmn.diagram.edit.policies.TextAnnotationCanonicalEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.TextAnnotationGraphicalNodeEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.TextAnnotationItemSemanticEditPolicy;
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
import org.eclipse.swt.graphics.Image;

/**
 * @generated
 */
public class TextAnnotationEditPart extends ShapeNodeEditPart {
    
    /**
     * @generated NOT
     */
    public static final Dimension TEXT_FIGURE_SIZE = new Dimension(60, 30);

    /**
     * @generated NOT
     */
    public static Insets TEXT_FIGURE_INSETS = new Insets(5, 10, 5, 14);

    /**
     * @generated NOT
     */
    static final Dimension TASK_MARKER_DIM = new Dimension(10, 10);
    
    /**
     * @generated
     */
    public static final int VISUAL_ID = 2004;

    /**
     * @generated
     */
    protected IFigure contentPane;

    /**
     * @generated
     */
    protected IFigure primaryShape;

    /**
     * @generated NOT the image to show on the label
     */
    private Image image;

    /**
     * the direction of the image to show on the label
     */
    private int imageDirection;

    /**
     * the image tooltip
     */
    private Label imageTooltip;

    /**
     * @generated
     */
    public TextAnnotationEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    protected void createDefaultEditPoliciesGen() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new TextAnnotationItemSemanticEditPolicy());
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new TextAnnotationGraphicalNodeEditPolicy());
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new TextAnnotationCanonicalEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
    }

    /**
     * @generated NOT
     * Extending the edit policies for darg and drop, handles, connections.
     */
    protected void createDefaultEditPolicies() {
    	createDefaultEditPoliciesGen();
    	// replace ConnectionHandleEditPolicy with our own
        installEditPolicy(EditPolicyRoles.CONNECTION_HANDLES_ROLE,
                createConnectionHandlerEditPolicy());
        // adding default drag and drop edit policy
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
                new BpmnDragDropEditPolicy(this));
        // adding an open edit policy
        installEditPolicy(EditPolicyRoles.OPEN_ROLE, createOpenFileEditPolicy());
    }
    /**
     * Ability to override the OpenFileEditPolicy.
     * @generated NOT
     */
    protected OpenFileEditPolicy createOpenFileEditPolicy() {
        return new OpenFileEditPolicy();
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
        TextAnnotationFigure figure = new TextAnnotationFigure();
        return primaryShape = figure;
    }

    /**
     * @generated
     */
    public TextAnnotationFigure getPrimaryShape() {
        return (TextAnnotationFigure) primaryShape;
    }

    /**
     * @generated
     */
    protected boolean addFixedChild(EditPart childEditPart) {
        if (childEditPart instanceof TextAnnotationNameEditPart) {
            ((TextAnnotationNameEditPart) childEditPart)
                    .setLabel(getPrimaryShape()
                            .getFigureTextAnnotationNameFigure());
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
     * @generated
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
                .getType(TextAnnotationNameEditPart.VISUAL_ID));
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
     * @generated
     */
    public class TextAnnotationFigure extends
            org.eclipse.stp.bpmn.figures.TextAnnotationFigure {

        /**
         * @generated NOT
         */
        public TextAnnotationFigure() {
            super(TEXT_FIGURE_SIZE.width, TEXT_FIGURE_SIZE.height, TEXT_FIGURE_INSETS);
            createContents();
        }
        
        /**
         * @generated NOT (Added the wrap), added the image
         */
        private void createContents() {
            //org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel fig_0 = new org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel();
            WrapLabelWithToolTip fig_0 = new WrapLabelWithToolTip(
                    TextAnnotationEditPart.this.getToolTipProvider(),
                    null, null, true, PositionConstants.MIDDLE | PositionConstants.LEFT,
                    PositionConstants.LEFT) {
            };
            fig_0.setText(""); //$NON-NLS-1$
            fig_0.setTextWrap(true);//not generated
            
            if (image != null) {
                fig_0.setIcon(image);
                fig_0.setIconAlignment(imageDirection);
                fig_0.setToolTip(imageTooltip);
            }
            
            setFigureTextAnnotationNameFigure(fig_0);
            
            Object layData0 = null;

            this.add(fig_0, layData0);
        }

        /**
         * @generated NOT WrappingLabel
         */
        private WrappingLabel fTextAnnotationNameFigure;

        /**
         * @generated NOT WrappingLabel
         */
        public WrappingLabel getFigureTextAnnotationNameFigure() {
            return fTextAnnotationNameFigure;
        }

        /**
         * @generated NOT WrappingLabel
         */
        private void setFigureTextAnnotationNameFigure(WrappingLabel fig) {
            fTextAnnotationNameFigure = fig;
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
     * Overrides to set the tooltip
     */
    @Override
    protected void refreshBounds() {
        super.refreshBounds();
        getFigure().setToolTip(createToolTip());
    }
    
    /**
     * @return The tooltip figure.
     */
    protected IFigure createToolTip() {
        return ActivityPainter.createToolTipFigure(getToolTipText());
    }

    /**
     * @return The default tooltip: the name of the dataObject
     * @generated NOT
     */
    protected String getToolTipText() {
        if (isLabelTruncated()) {
            return ((NamedBpmnObject)resolveSemanticElement()).getName();
        }
        return null;
    }
    
    /**
     * @return true when the label is truncated
     * @generated NOT
     */
    protected boolean isLabelTruncated() {
        if (getFigure() != null && getFigure().getChildren().size() > 0) {
            Object childFig =
                ((IFigure)getFigure().getChildren().get(0)).getChildren().get(0);
            if (childFig instanceof WrappingLabel) {
                return ((WrappingLabel) childFig).isTextWrapOn();
            }
        }
        return false;
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
     * @generated NOT method added to set an icon on the text annotation edit part label.
     * @param img the image to set
     * @param direction the direction in which the image will be showing 
     * @param toolTip the tooltip of the image
     */
    public void setLabelImage(Image img, int direction, Label toolTip) {
        image = img;
        this.imageDirection = direction;
        this.imageTooltip = toolTip;
        if (getPrimaryShape() != null && 
                getPrimaryShape().fTextAnnotationNameFigure != null) {
            WrappingLabel label = getPrimaryShape().fTextAnnotationNameFigure;
            label.setIcon(image);
            label.setIconAlignment(imageDirection);
            label.setToolTip(imageTooltip);
        }
    }

}
