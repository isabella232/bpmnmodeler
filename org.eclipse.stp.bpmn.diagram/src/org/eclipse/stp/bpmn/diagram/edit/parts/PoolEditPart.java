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

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.GravityConstrainedFlowLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.GravityDirectionType;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.policies.PoolCanonicalEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.PoolGraphicalNodeEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.PoolItemSemanticEditPolicy;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.policies.BpmnDragDropEditPolicy;
import org.eclipse.stp.bpmn.policies.DelegateToCompartmentEditPolicy;
import org.eclipse.stp.bpmn.policies.ResizablePoolEditPolicy;
import org.eclipse.swt.graphics.Color;

/**
 * @generated
 */
public class PoolEditPart extends ShapeNodeEditPart {
    /**
     * @notgenerated
     */
    public static final int POOL_HEIGHT = 200;

    /**
     * @notgenerated
     */
    public static final int POOL_HEIGHT_COLLAPSED = 32;

    /**
     * @notgenerated
     */
    public static final int POOL_WIDTH = 1500;

    /**
     * @generated
     */
    public static final int VISUAL_ID = 1001;

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
    public PoolEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    protected void createDefaultEditPoliciesGen() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new PoolItemSemanticEditPolicy());
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new PoolGraphicalNodeEditPolicy());
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new PoolCanonicalEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
//      adding default drag and drop edit policy
//        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, 
//                new BpmnDragDropEditPolicy(this));
    }

    /**
     * @notgenerated
     */
    protected void createDefaultEditPolicies() {
        createDefaultEditPoliciesGen();
        removeEditPolicy(EditPolicyRoles.CONNECTION_HANDLES_ROLE);
        // the compartment is not selectable anymore.
        // it looks like none of the containereidtpolicy gets called
        // because of that.
        // we use a mousedelegating edit policy to still call the proper
        // edit policy on the compartment.
        installEditPolicy(EditPolicy.CONTAINER_ROLE,
                new DelegateToCompartmentEditPolicy(BpmnVisualIDRegistry
                        .getType(PoolPoolCompartmentEditPart.VISUAL_ID),
                        EditPolicy.CONTAINER_ROLE));
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
        		new BpmnDragDropEditPolicy(this));
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
        PoolFigure figure = new PoolFigure();
        return primaryShape = figure;
    }

    /**
     * @generated
     */
    public PoolFigure getPrimaryShape() {
        return (PoolFigure) primaryShape;
    }

    /**
     * @generated
     */
    protected boolean addFixedChild(EditPart childEditPart) {
        if (childEditPart instanceof PoolNameEditPart) {
            ((PoolNameEditPart) childEditPart).setLabel(getPrimaryShape()
                    .getFigurePoolNameFigure());
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
     * @notgenerated
     */
    protected NodeFigure createNodePlate() {
        Rectangle parentBounds = ((GraphicalEditPart) getParent()).getFigure()
                .getBounds();
//        boolean isCollapsed = ((Boolean) getStructuralFeatureValue(NotationPackage.eINSTANCE
//                .getDrawerStyle_Collapsed())).booleanValue();
//        int oldHeight = ((Integer) this
//                .getStructuralFeatureValue(NotationPackage.eINSTANCE
//                        .getSize_Height())).intValue();
//        int defaultHeight = isCollapsed ? oldHeight
//                : getMapMode().DPtoLP(POOL_HEIGHT);
        return new DefaultSizeNodeFigure(getMapMode().DPtoLP(
                parentBounds.width - 25), getMapMode().DPtoLP(POOL_HEIGHT)) {

            @Override
            public void setBackgroundColor(Color bg) {
                List children = getChildren();
                for (Object object : children) {
                    ((IFigure) object).setBackgroundColor(bg);
                }
                super.setBackgroundColor(bg);
            }
        };
    }

    /**
     * Creates figure for this edit part.
     * 
     * Body of this method does not depend on settings in generation model so
     * you may safely remove <i>generated</i> tag and modify it.
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
     * Difference with the generated one: the layout is
     * GravityConstrainedFlowLayout.
     * 
     * @param nodeShape
     *            instance of generated figure class
     * @generated NOT
     */
    protected IFigure setupContentPane(IFigure nodeShape) {
        if (nodeShape.getLayoutManager() == null) {
            GravityConstrainedFlowLayout layout = new GravityConstrainedFlowLayout();
            layout.setGravity(GravityDirectionType.WEST);// WEST is default
            // anyways
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
                .getType(PoolNameEditPart.VISUAL_ID));
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
    
    public boolean isPoolBodyCompartmentCollapsed() {
        IGraphicalEditPart poolCompartment =
            getChildBySemanticHint(Integer
                .toString(PoolPoolCompartmentEditPart.VISUAL_ID));
        return poolCompartment != null &&
            ((Boolean) poolCompartment
                .getStructuralFeatureValue(NotationPackage.eINSTANCE
                        .getDrawerStyle_Collapsed())).booleanValue();

    }
    
    /**
     * @generated
     */
    public class PoolFigure extends org.eclipse.draw2d.RectangleFigure {


		/**
         * @generated
         */
        public PoolFigure() {
            this.setForegroundColor(org.eclipse.draw2d.ColorConstants.black

            );
            this.setBackgroundColor(POOLFIGURE_BACK

            );
            this.setBorder(new org.eclipse.draw2d.LineBorder(BORDER

            ));
            createContents();
        }

        /**
         * @notgenerated
         */
        protected void createContents() {
            
            boolean isCollapsed = isPoolBodyCompartmentCollapsed();

            
            org.eclipse.stp.bpmn.figures.VerticalLabel fig_0 = new org.eclipse.stp.bpmn.figures.VerticalLabel(!isCollapsed) {
                /*
                 * (non-Javadoc) Overriden to implement label centering in the
                 * space between pool's left side and pool compartment
                 * 
                 * @see org.eclipse.draw2d.Figure#getBounds()
                 */
                @Override
                public Rectangle getBounds() {
                    Rectangle bounds = super.getBounds();
                    IFigure parentFigure = getParent();
                    if (parentFigure != null) {
                        List children = getParent().getChildren();
                        IFigure poolCompartmentFigure = null;
                        for (Object child : children) {
                            if (child instanceof ResizableCompartmentFigure) {
                                poolCompartmentFigure = (IFigure) child;
                                break;
                            }
                        }
                        if (poolCompartmentFigure != null) {
                            Rectangle parentBounds = parentFigure.getBounds();
                            bounds.x = (poolCompartmentFigure.getBounds().x - bounds.width)
                                    / 2 + parentBounds.x;
                        }
                    }
                    return bounds;
                }
            };

            fig_0.setTextWrap(true);
            fig_0.setBackgroundColor(POOLNAMEFIGURE_BACK

            );

            setFigurePoolNameFigure(fig_0);

            Object layData0 = null;

            this.add(fig_0, layData0);
        }

        /**
         * @generated
         */
        private org.eclipse.stp.bpmn.figures.VerticalLabel fPoolNameFigure;

        /**
         * @generated
         */
        public org.eclipse.stp.bpmn.figures.VerticalLabel getFigurePoolNameFigure() {
            return fPoolNameFigure;
        }

        /**
         * @generated
         */
        private void setFigurePoolNameFigureGen(
                org.eclipse.stp.bpmn.figures.VerticalLabel fig) {
            fPoolNameFigure = fig;
        }

        /**
         * @notgenerated
         */
        protected void setFigurePoolNameFigure(
                org.eclipse.stp.bpmn.figures.VerticalLabel fig) {
            setFigurePoolNameFigureGen(fig);
            fig.setText(BpmnDiagramMessages.PoolEditPart_default_label);
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
     * @generated
     */
    public static final org.eclipse.swt.graphics.Color POOLNAMEFIGURE_BACK = new org.eclipse.swt.graphics.Color(
            null, 227, 235, 251);

    /**
     * @generated
     */
    public static final org.eclipse.swt.graphics.Color POOLFIGURE_BACK = new org.eclipse.swt.graphics.Color(
            null, 232, 232, 255);

    /**
     * @generated
     */
    public static final org.eclipse.swt.graphics.Color BORDER = new org.eclipse.swt.graphics.Color(
            null, 169, 169, 169);

    /**
     * @generated NOT
     */
    @Override
    public EditPolicy getPrimaryDragEditPolicy() {
        return new ResizablePoolEditPolicy();
    }
}
