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
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;

import org.eclipse.gef.commands.Command;

import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;

import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.SelectionRequest;

import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;

import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramAssistantEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;

import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;

import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;

import org.eclipse.gmf.runtime.notation.View;

import org.eclipse.stp.bpmn.diagram.edit.policies.GroupCanonicalEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.GroupGraphicalNodeEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.GroupItemSemanticEditPolicy;

import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.policies.BpmnDragDropEditPolicy;
import org.eclipse.stp.bpmn.policies.ConnectionHandleEditPolicyEx;
import org.eclipse.stp.bpmn.policies.OpenFileEditPolicy;
import org.eclipse.stp.bpmn.policies.ResizableArtifactEditPolicy;
import org.eclipse.stp.bpmn.policies.ResizableGroupEditPolicy;
import org.eclipse.stp.bpmn.tools.GroupDragTracker;

/**
 * @generated
 */
public class GroupEditPart extends ShapeNodeEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 2006;

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
    public GroupEditPart(View view) {
        super(view);
    }

    /**
     * @generated not
     */
    protected void createDefaultEditPoliciesGen() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new GroupItemSemanticEditPolicy());
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new GroupGraphicalNodeEditPolicy());
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new GroupCanonicalEditPolicy());
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
        GroupFigure figure = new GroupFigure();
        return primaryShape = figure;
    }

    /**
     * @generated
     */
    public GroupFigure getPrimaryShape() {
        return (GroupFigure) primaryShape;
    }

    /**
     * @generated
     */
    protected boolean addFixedChild(EditPart childEditPart) {
        if (childEditPart instanceof GroupNameEditPart) {
            ((GroupNameEditPart) childEditPart).setLabel(getPrimaryShape()
                    .getFigureGroupNameFigure());
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
                .getType(GroupNameEditPart.VISUAL_ID));
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
    public class GroupFigure extends org.eclipse.stp.bpmn.figures.GroupFigure {

        /**
         * @generated
         */
        public GroupFigure() {

            createContents();
        }

        /**
         * @generated NOT wrappingLabel, set as wrapped and centered
         */
        private void createContents() {
            org.eclipse.stp.gmf.runtime.draw2d.ui.figures.WrappingLabel fig_0 = new org.eclipse.stp.gmf.runtime.draw2d.ui.figures.WrappingLabel();
            fig_0.setText(""); //$NON-NLS-1$
            fig_0.setTextWrap(true);
            fig_0.setAlignment(PositionConstants.TOP);
            setFigureGroupNameFigure(fig_0);

            Object layData0 = null;

            this.add(fig_0, layData0);
        }

        /**
         * @generated
         */
        private org.eclipse.stp.gmf.runtime.draw2d.ui.figures.WrappingLabel fGroupNameFigure;

        /**
         * @generated
         */
        public org.eclipse.stp.gmf.runtime.draw2d.ui.figures.WrappingLabel getFigureGroupNameFigure() {
            return fGroupNameFigure;
        }

        /**
         * @generated
         */
        private void setFigureGroupNameFigure(
                org.eclipse.stp.gmf.runtime.draw2d.ui.figures.WrappingLabel fig) {
            fGroupNameFigure = fig;
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
        return new ResizableGroupEditPolicy();
    }
    
    /**
     * @generated NOT
     */
    @Override
    public DragTracker getDragTracker(Request request) {
        return new GroupDragTracker(this);
    }
}
