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
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.NamedBpmnObject;
import org.eclipse.stp.bpmn.diagram.edit.policies.TextAnnotation2CanonicalEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.TextAnnotation2GraphicalNodeEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.TextAnnotation2ItemSemanticEditPolicy;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.figures.WrapLabelWithToolTip;
import org.eclipse.stp.bpmn.figures.WrapLabelWithToolTip.IToolTipProvider;
import org.eclipse.stp.bpmn.figures.activities.ActivityPainter;
import org.eclipse.stp.bpmn.policies.BpmnDragDropEditPolicy;
import org.eclipse.stp.bpmn.policies.ConnectionHandleEditPolicyEx;
import org.eclipse.stp.bpmn.policies.OpenFileEditPolicy;
import org.eclipse.stp.bpmn.policies.ResizableArtifactEditPolicy;
import org.eclipse.stp.bpmn.tools.TaskDragEditPartsTrackerEx;

/**
 * @generated
 */
public class TextAnnotation2EditPart extends ShapeNodeEditPart {
    /**
     * @generated NOT
     */
    public static final Dimension TEXT_FIGURE_SIZE = TextAnnotationEditPart.TEXT_FIGURE_SIZE;

    /**
     * @generated NOT
     */
    public static Insets TEXT_FIGURE_INSETS = TextAnnotationEditPart.TEXT_FIGURE_INSETS;
    /**
     * @generated
     */
    public static final int VISUAL_ID = 1002;

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
    public TextAnnotation2EditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    protected void createDefaultEditPoliciesGen() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new TextAnnotation2ItemSemanticEditPolicy());
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new TextAnnotation2GraphicalNodeEditPolicy());
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new TextAnnotation2CanonicalEditPolicy());
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
        if (childEditPart instanceof TextAnnotationName2EditPart) {
            ((TextAnnotationName2EditPart) childEditPart)
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
                .getType(TextAnnotationName2EditPart.VISUAL_ID));
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
         * @generated NOT setting the text wrap true. 
         */
        private void createContents() {
            //org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel fig_0 = new org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel();
            WrapLabelWithToolTip fig_0 = new WrapLabelWithToolTip(
                    TextAnnotation2EditPart.this.getToolTipProvider());
            fig_0.setText(""); //$NON-NLS-1$
            fig_0.setTextWrap(true);
            setFigureTextAnnotationNameFigure(fig_0);

            Object layData0 = null;

            this.add(fig_0, layData0);
        }

        /**
         * @generated
         */
        private org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel fTextAnnotationNameFigure;

        /**
         * @generated
         */
        public org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel getFigureTextAnnotationNameFigure() {
            return fTextAnnotationNameFigure;
        }

        /**
         * @generated
         */
        private void setFigureTextAnnotationNameFigure(
                org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel fig) {
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
            if (childFig instanceof WrapLabel) {
                return ((WrapLabel) childFig).isTextTruncated();
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

}
