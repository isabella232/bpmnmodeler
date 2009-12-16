/*
 * Copyright (c) 2007-2009, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *     Denis Dallaire, Techsolcom: Bug 273345: Support sub-process message flows
 */
package org.eclipse.stp.bpmn.diagram.edit.parts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramAssistantEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeConnectionRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.MetamodelType;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.diagram.actions.SetTransactionalAction;
import org.eclipse.stp.bpmn.diagram.edit.policies.SubProcessCanonicalEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.SubProcessGraphicalNodeEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.SubProcessItemSemanticEditPolicy;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;
import org.eclipse.stp.bpmn.figures.activities.ActivityPainter;
import org.eclipse.stp.bpmn.figures.connectionanchors.DefaultSizeNodeFigureEx;
import org.eclipse.stp.bpmn.figures.connectionanchors.IConnectionAnchorFactory;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchor;
import org.eclipse.stp.bpmn.figures.connectionanchors.impl.ConnectionAnchorFactory;
import org.eclipse.stp.bpmn.layouts.SubProcessLayout;
import org.eclipse.stp.bpmn.policies.ConnectionHandleEditPolicyEx;
import org.eclipse.stp.bpmn.policies.DelegateToCompartmentEditPolicy;
import org.eclipse.stp.bpmn.policies.OpenFileEditPolicy;
import org.eclipse.stp.bpmn.policies.ResizableSubProcessEditPolicy;
import org.eclipse.stp.bpmn.tools.EdgeConnectionValidator;
import org.eclipse.stp.bpmn.tools.TaskDragEditPartsTrackerEx;

/**
 * @generated
 */
public class SubProcessEditPart extends ShapeNodeEditPart {
    
    /**
     * the height of the collapse handle, also used for the loop, 
     * the compensation and error markers.
     */
    public static final int COLLAPSE_HANDLE_HEIGHT = 20;
    /**
     * the border compartment height
     */
    public static final int BORDER_HEIGHT = 50;
    /**
     * @notgenerated
     * this is obtained by taking the default size of an activity, and adding
     */
    public static final Dimension COLLAPSED_SIZE = new Dimension(111, 71);

    /**
     * @generated NOT the default expanded size, used during the subprocess creation.
     * this size includes the collapse handle size, which makes it bigger than the activity.
     */
    public static final Dimension EXPANDED_SIZE = new Dimension(111, 96);

    /**
     * @generated
     */
    public static final int VISUAL_ID = 2002;

    /**
     * @notgenerated
     */
    protected boolean needToUpdateContainer;

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
    public SubProcessEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    protected void createDefaultEditPoliciesGen() {
        installEditPolicy(EditPolicyRoles.CREATION_ROLE,
                new CreationEditPolicy() {

                    public Command getCommand(Request request) {
                        if (understandsRequest(request)) {
                            if (request instanceof CreateViewAndElementRequest) {
                                CreateElementRequestAdapter adapter = ((CreateViewAndElementRequest) request)
                                        .getViewAndElementDescriptor()
                                        .getCreateElementRequestAdapter();
                                IElementType type = (IElementType) adapter
                                        .getAdapter(IElementType.class);
                                if (type == BpmnElementTypes.Activity_2003) {
                                    EditPart compartmentEditPart = getChildBySemanticHint(BpmnVisualIDRegistry
                                            .getType(SubProcessSubProcessBorderCompartmentEditPart.VISUAL_ID));
                                    return compartmentEditPart == null ? null
                                            : compartmentEditPart
                                                    .getCommand(request);
                                }
                            }
                            return super.getCommand(request);
                        }
                        return null;
                    }
                });
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new SubProcessItemSemanticEditPolicy());
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
                new SubProcessGraphicalNodeEditPolicy());
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new SubProcessCanonicalEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
    }

    /**
     * @notgenerated
     */
    protected void createDefaultEditPolicies() {
        createDefaultEditPoliciesGen();
        // no we don't want to generate an activity for a given subprocess
        // we want it to be in either the body or the border
        removeEditPolicy(EditPolicyRoles.CREATION_ROLE);

        // replace ConnectionHandleEditPolicy with our own
        installEditPolicy(EditPolicyRoles.CONNECTION_HANDLES_ROLE,
                createConnectionHandlerEditPolicy());

        // the compartment is not selectable anymore.
        // it looks like none of the containereidtpolicy gets called
        // because of that.
        // we use a mousedelegating edit policy to still call the proper
        // edit policy on the compartment.
        installEditPolicy(
                EditPolicy.CONTAINER_ROLE,
                new DelegateToCompartmentEditPolicy(
                        BpmnVisualIDRegistry
                                .getType(SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID),
                        EditPolicy.CONTAINER_ROLE));
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
        SubProcessFigure figure = new SubProcessFigure();
        return primaryShape = figure;
    }

    /**
     * @generated
     */
    public SubProcessFigure getPrimaryShape() {
        return (SubProcessFigure) primaryShape;
    }

    /**
     * @generated
     */
    protected boolean addFixedChild(EditPart childEditPart) {
        if (childEditPart instanceof SubProcessNameEditPart) {
            ((SubProcessNameEditPart) childEditPart).setLabel(getPrimaryShape()
                    .getFigureSubProcessNameFigure());
            return true;
        }
        if (childEditPart instanceof SubProcessSubProcessBodyCompartmentEditPart) {
            IFigure pane = getPrimaryShape().getFigureSubProcessBodyFigure();
            setupContentPane(pane); // FIXME each comparment should handle his
            // content pane in his own way
            pane
                    .add(((SubProcessSubProcessBodyCompartmentEditPart) childEditPart)
                            .getFigure());
            return true;
        }
        if (childEditPart instanceof SubProcessSubProcessBorderCompartmentEditPart) {
            IFigure pane = getPrimaryShape().getFigureSubProcessBorderFigure();
            setupContentPane(pane); // FIXME each comparment should handle his
            // content pane in his own way
            pane
                    .add(((SubProcessSubProcessBorderCompartmentEditPart) childEditPart)
                            .getFigure());
            return true;
        }
        return false;
    }

    /**
     * @generated
     */
    protected boolean removeFixedChild(EditPart childEditPart) {
        if (childEditPart instanceof SubProcessSubProcessBodyCompartmentEditPart) {
            IFigure pane = getPrimaryShape().getFigureSubProcessBodyFigure();
            pane
                    .remove(((SubProcessSubProcessBodyCompartmentEditPart) childEditPart)
                            .getFigure());
            return true;
        }
        if (childEditPart instanceof SubProcessSubProcessBorderCompartmentEditPart) {
            IFigure pane = getPrimaryShape().getFigureSubProcessBorderFigure();
            pane
                    .remove(((SubProcessSubProcessBorderCompartmentEditPart) childEditPart)
                            .getFigure());
            return true;
        }
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
     * @generated NOT
     */
    protected NodeFigure createNodePlate() {
        return new DefaultSizeNodeFigureEx(
                getMapMode().DPtoLP(EXPANDED_SIZE.width),
                getMapMode().DPtoLP(EXPANDED_SIZE.height),
                getConnectionAnchorFactory()) {

            @Override
            public Rectangle getHandleBounds() {
                SubProcessFigure figure = getPrimaryShape();
                return figure.getHandleBounds();
            }
            
            @Override
            public void setBounds(Rectangle rect) {
                Rectangle currentBounds = getBounds();
                int widthDiff = rect.width - currentBounds.width ;
                int heightDiff = rect.height - currentBounds.height;
                
//                getPrimaryShape().fSubProcessBodyFigure.getBounds().width += widthDiff;
                getPrimaryShape().fSubProcessBodyFigure.getBounds().height += heightDiff;
                
//                getPrimaryShape().fSubProcessNameFigure.getBounds().width  += widthDiff;
//                getPrimaryShape().fSubProcessBorderFigure.getBounds().width += widthDiff;
//                getPrimaryShape().getClientArea().width += widthDiff;
                super.setBounds(rect);
            }
            
            @Override
            public void computeAbsoluteHandleBounds(Rectangle result) {
                result.setBounds(getHandleBounds());
                super.translateToAbsolute(result);
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
     * Set the border to null
     * 
     * @param nodeShape
     *            instance of generated figure class
     * @generated NOT
     */
    protected IFigure setupContentPane(IFigure nodeShape) {
        if (nodeShape.getLayoutManager() == null) {
            ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
            layout.setSpacing(0);
            nodeShape.setLayoutManager(layout);
            nodeShape.setBorder(null);
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
                .getType(SubProcessNameEditPart.VISUAL_ID));
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
    public class SubProcessFigure extends
            org.eclipse.stp.bpmn.figures.SubProcessFigure {

//    	/**
//    	 * @generated not
//    	 * Calculates the minimum size from the different subfigures,
//    	 * and the contained activities.
//    	 */
//        @Override
//        public Dimension getMinimumSize(int wHint, int hHint) {
//            return BpmnShapesDefaultSizes.getSubProcessMinSize(SubProcessEditPart.this); //calcMinSize();
//        }

        /**
         * @generated
         */
        public SubProcessFigure() {
            this.setForegroundColor(org.eclipse.draw2d.ColorConstants.black

            );

            createContents();
        }

        /**
         * @generated NOT we use our own layout
         */
        private void createContents() {
            
            setLayoutManager(new SubProcessLayout());
            // this is necessary as the g gets cut out currently.
            // FIXME remove when bug 194944 is fixed.
            WrappingLabel spLabel = new WrappingLabel() {
                @Override
                public void setBounds(Rectangle rect) {
                    rect.height += MapModeUtil.getMapMode(this).DPtoLP(1);
                    super.setBounds(rect);
                }
                
            };
            spLabel.setAlignment(PositionConstants.CENTER);
            spLabel.setTextWrap(true);
            spLabel.setText(""); //$NON-NLS-1$

            setFigureSubProcessNameFigure(spLabel);

            Object layData0 = SubProcessLayout.NAME;

            org.eclipse.stp.bpmn.figures.SubProcessBodyFigure spBody = new org.eclipse.stp.bpmn.figures.SubProcessBodyFigure() {
                
                @Override
                public Dimension getMinimumSize(int hint, int hint2) {
                    GraphicalEditPart bodyEditPart = (GraphicalEditPart) getChildBySemanticHint(
                            BpmnVisualIDRegistry.getType(SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID));
                    boolean isCollapsed = ((Boolean) bodyEditPart
                            .getStructuralFeatureValue(NotationPackage.eINSTANCE
                                    .getDrawerStyle_Collapsed())).booleanValue();
                    if (!isCollapsed) {
                        return getChildrenBounds(bodyEditPart).getSize();
                    } else {
                        return COLLAPSED_SIZE.getCopy();
                    }
                }
            };
            setFigureSubProcessBodyFigure(spBody);

            Object layData1 = SubProcessLayout.BODY;

            org.eclipse.stp.bpmn.figures.SubProcessBorderFigure spBorder = 
                new org.eclipse.stp.bpmn.figures.SubProcessBorderFigure();

            setFigureSubProcessBorderFigure(spBorder);

            Object layData2 = SubProcessLayout.BORDER;

            this.add(spBody, layData1);
            this.add(spBorder, layData2);
            //place the label at the end so it is on top of the other figure.
            this.add(spLabel, layData0);
        }

        /**
         * @generated
         */
        private WrappingLabel fSubProcessNameFigure;

        /**
         * @generated
         */
        public WrappingLabel getFigureSubProcessNameFigure() {
            return fSubProcessNameFigure;
        }

        /**
         * @generated
         */
        private void setFigureSubProcessNameFigure(
                WrappingLabel fig) {
            fSubProcessNameFigure = fig;
        }

        /**
         * @generated
         */
        private org.eclipse.stp.bpmn.figures.SubProcessBodyFigure fSubProcessBodyFigure;

        /**
         * @generated
         */
        public org.eclipse.stp.bpmn.figures.SubProcessBodyFigure getFigureSubProcessBodyFigure() {
            return fSubProcessBodyFigure;
        }

        /**
         * @generated
         */
        private void setFigureSubProcessBodyFigure(
                org.eclipse.stp.bpmn.figures.SubProcessBodyFigure fig) {
            fSubProcessBodyFigure = fig;
        }

        /**
         * @generated
         */
        private org.eclipse.stp.bpmn.figures.SubProcessBorderFigure fSubProcessBorderFigure;

        /**
         * @generated
         */
        public org.eclipse.stp.bpmn.figures.SubProcessBorderFigure getFigureSubProcessBorderFigure() {
            return fSubProcessBorderFigure;
        }

        /**
         * @generated
         */
        private void setFigureSubProcessBorderFigure(
                org.eclipse.stp.bpmn.figures.SubProcessBorderFigure fig) {
            fSubProcessBorderFigure = fig;
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
         * @notgenerated Draws loop marker and collapse handle.
         */
        public void paint(Graphics graphics) {
            super.paint(graphics);
            SubProcess model = (SubProcess) ((View) getModel()).getElement();

            paintCollapseHandle(graphics);
            if (shouldPaintMultipleInstances()) {
                SubProcessEditPart.this.paintMultipleInstances(graphics);
            } else {
                SubProcessEditPart.this.paintLoopMarker(graphics);
                SubProcessEditPart.this.paintCompensationMarker(graphics);
                SubProcessEditPart.this.paintTildeMarker(graphics);
            }
        }

        /**
         * @generated NOT
         * the subprocess may show a transactional decoration, ie showing
         * a second border line.
         */
        @Override
        protected void outlineShape(Graphics graphics) {
            super.outlineShape(graphics);
            if (isTransactional()) {
                Rectangle f = Rectangle.SINGLETON;
                Rectangle r = getHandleBounds();
                f.x = r.x + lineWidth / 2;
                f.y = r.y + lineWidth / 2;
                f.width = r.width - lineWidth;
                f.height = r.height - lineWidth;
                f.crop(new Insets(5, 5, 5, 5));
                graphics.drawRoundRectangle(f, corner.width, corner.height);
            }
        }
        /**
         * Draws &quot;+&quot; for collapsed and &quot;-&quot; for expanded
         * subprocess.
         * 
         * @generated not
         */
        private void paintCollapseHandle(Graphics graphics) {
        	
        	GraphicalEditPart bodyEditPart = null;
            for (Object object : children) {
                if (object instanceof SubProcessSubProcessBodyCompartmentEditPart) {
                    bodyEditPart = (GraphicalEditPart) object;
                    break;
                }
            }
            
        	if (bodyEditPart == null) { // been filtered
        		return; // we don't paint anything but the name of the subprocess.
        	}
            Rectangle bounds = getAbsCollapseHandleBounds(true);
            translateToRelative(bounds);
            int lineWidth = 1;
            bounds.shrink(lineWidth, lineWidth);
            graphics.drawRectangle(bounds);
            lineWidth = lineWidth * 2;
            graphics.setLineWidth(lineWidth);
            double delta = lineWidth;
            double y = bounds.y + bounds.height / 2.0;
            PrecisionPoint p1 = new PrecisionPoint(bounds.x + delta, y);
            PrecisionPoint p2 = new PrecisionPoint(bounds.x + bounds.width - delta, y);
            graphics.drawLine(p1, p2);
            
            boolean isCollapsed = false;
            if (bodyEditPart == null) { // been filtered, we should not be there
            	isCollapsed = true;
            } else {
            	isCollapsed = ((Boolean) bodyEditPart
                    .getStructuralFeatureValue(NotationPackage.eINSTANCE
                            .getDrawerStyle_Collapsed())).booleanValue();
            }
            if (isCollapsed) {
                double x = bounds.x + bounds.width / 2.0;
                p1 = new PrecisionPoint(x, bounds.y + delta);
                p2 = new PrecisionPoint(x, bounds.y + bounds.height - delta);
                graphics.drawLine(p1, p2);
            }
            
/*            //add a little hint regarding the expanding style:
            boolean xpandOverlapse = true;//todo: read the value on the notation annotation
            if (xpandOverlapse) {
                //draw a small rectangle at the top of the marker
                //that reminds people of
                //the maximize rectangle of a standard window.
                double extraRectX = bounds.x + bounds.width * 3.0 / 4.0;
                double extraRectY = bounds.y + bounds.height / 12.0;
                PrecisionRectangle pr = new PrecisionRectangle();
                pr.setX(extraRectX);
                pr.setY(extraRectY);
                pr.setWidth(bounds.width / 4.0);
                pr.setHeight(bounds.height / 4.0);
                graphics.drawRectangle(pr);
                
                pr.setHeight(bounds.height / 8.0);
                graphics.setBackgroundColor(ColorConstants.black);
                graphics.fillRectangle(pr);
            }
            */
        }
        
        /**
         * 
         * @return true if the subprocess is collapsed.
         */
        public boolean isCollapsed() {
            GraphicalEditPart bodyEditPart = (GraphicalEditPart) getChildBySemanticHint(
                    BpmnVisualIDRegistry.getType(SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID));
            return ((Boolean) bodyEditPart
                    .getStructuralFeatureValue(NotationPackage.eINSTANCE
                            .getDrawerStyle_Collapsed())).booleanValue();
        }
    }
    
    /**
     * Called during the painting of the sub-process figure.
     * Checks if the sub-process has an incoming seqeunce flow that source is
     * an intermediate event handler for compensation.
     * In that case, paints a compensation marker on the left of the
     * collapse handle. The right of the collapse handle is already used by
     * the eventual loop marker.
     * <p>
     * Editors that extend the stp bpmn modeler can override this method
     * to handle other types of markers.
     * </p>
     * @notgenerated
     */
    protected void paintCompensationMarker(Graphics graphics) {
        SubProcess model = (SubProcess) ((View) getModel()).getElement();
        for (Object o : model.getIncomingEdges()) {
            SequenceEdge e = (SequenceEdge)o;
            if (e.getSource() != null && e.getSource() instanceof Activity) {
                Activity src = (Activity)e.getSource();
                if (src.getActivityType().getValue() ==
                            ActivityType.EVENT_INTERMEDIATE_COMPENSATION) {
                    int size = 18;
                    Rectangle bounds = getAbsBoundsWithoutBorder();
                    getPrimaryShape().translateToRelative(bounds);
                    Rectangle loopRect = new Rectangle();
                    loopRect.x = bounds.x + bounds.width/2 + size;
                    loopRect.y = bounds.y + bounds.height - size - 2;
                    loopRect.height = size;
                    loopRect.width = size;
                    
                    ActivityPainter.paintCompensation(graphics, loopRect,
                            MapModeUtil.getMapMode(getFigure()).LPtoDP(1));
                    
                    break;
                }
            }
        }
    }
    
    /**
     * Paint the multiple instance 
     * @param graphics
     */
    protected void paintMultipleInstances(Graphics graphics) {
        if (shouldPaintMultipleInstances()) {
            int size = COLLAPSE_HANDLE_HEIGHT;
            Rectangle bounds = getAbsBoundsWithoutBorder();
            getPrimaryShape().translateToRelative(bounds);
            Rectangle loopRect = new Rectangle();
            loopRect.x = bounds.x + bounds.width/2 - size + 4;
            loopRect.y = bounds.y + bounds.height -size + 2;
            if (getPrimaryShape().getFigureSubProcessBorderFigure().hasChildren()) {
                loopRect.y = (int) (loopRect.y - (ActivityEditPart.EVENT_FIGURE_SIZE*getZoom())/2);
            }
            loopRect.height = size -4;
            loopRect.width = size - 6;
            if (BpmnDiagramEditorPlugin.getInstance().getPreferenceStore().
                    getBoolean(BpmnDiagramPreferenceInitializer.PREF_BPMN1_1_STYLE)) {
                int width = 2;
                graphics.pushState();
                graphics.setBackgroundColor(ColorConstants.black);
                graphics.fillRectangle(loopRect.x, loopRect.y, width, loopRect.height);
                graphics.fillRectangle(loopRect.x + (-width + loopRect.width)/2, loopRect.y, width, loopRect.height);
                graphics.fillRectangle(loopRect.x + loopRect.width - width, 
                        loopRect.y, width, loopRect.height);
                graphics.popState();
            } else {
                int width = size/2 -4;
                graphics.pushState();
                graphics.setBackgroundColor(ColorConstants.black);
                graphics.fillRectangle(loopRect.x, loopRect.y, width, loopRect.height);
                graphics.fillRectangle(loopRect.x + loopRect.width - width, 
                        loopRect.y, width, loopRect.height);
                graphics.popState();
            }
        }
    }

    /**
     * TODO
     * @return true if a multiple instance marker should be painted.
     */
    protected boolean shouldPaintMultipleInstances() {
        return false; //TODO
    }

    /**
     * @return true if the subprocess is transactional, in which case
     * the border is painted twice.
     */
    private boolean isTransactional() {
        if (!(resolveSemanticElement() instanceof SubProcess)) {
            return false;
        }
        // TODO change insets if true
        SubProcess sp = (SubProcess) resolveSemanticElement();
        String ann = EcoreUtil.getAnnotation(sp, SetTransactionalAction.IS_TRANSACTIONAL_ANNOTATION_SOURCE_AND_KEY_ID, 
                SetTransactionalAction.IS_TRANSACTIONAL_ANNOTATION_SOURCE_AND_KEY_ID);
        if (ann == null || ann.equals("false")) { //$NON-NLS-1$
            return false;
        }
        return true;
    }

    /**
     * Called during the painting of the sub-process figure.
     * Checks if the sub-process is looping and paint the standard loop
     * marker or the multiple instance loop marker in that case.
     * <p>
     * Editors that extend the stp bpmn modeler can override this method
     * to handle other types of markers.
     * </p>
     * @notgenerated
     */
    protected void paintLoopMarker(Graphics graphics) {
        SubProcess zmodel = (SubProcess) ((View) getModel()).getElement();
        if (zmodel.isLooping()) {
            int size = COLLAPSE_HANDLE_HEIGHT;
            Rectangle bounds = getAbsBoundsWithoutBorder();
            getPrimaryShape().translateToRelative(bounds);
            Rectangle loopRect = new Rectangle();
            loopRect.x = bounds.x + bounds.width/2 - size;
            loopRect.y = bounds.y + bounds.height -size;
            if (getPrimaryShape().getFigureSubProcessBorderFigure().hasChildren()) {
                loopRect.y = (int) (loopRect.y - (ActivityEditPart.EVENT_FIGURE_SIZE*getZoom())/2);
            }
            loopRect.height = size;
            loopRect.width = size;
            
            ActivityPainter.paintLoop(graphics,
                    new PrecisionRectangle(loopRect), getFigure());
        }
    }
    

    /**
     * @generated not
     */
    public static final Insets INSETS = new Insets(2, 2, 2, 2);

    /**
     * @subprocessminsize see BpmnShapesDefaultSize
     * @generated not
     *
    public Dimension calcMinSize() {
        GraphicalEditPart bodyEditPart = (GraphicalEditPart) getChildBySemanticHint(
                BpmnVisualIDRegistry.getType(SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID));
        GraphicalEditPart borderEditPart = (GraphicalEditPart) getChildBySemanticHint(
                BpmnVisualIDRegistry.getType(SubProcessSubProcessBorderCompartmentEditPart.VISUAL_ID));;
        GraphicalEditPart nameEditPart = (GraphicalEditPart) getChildBySemanticHint(
                BpmnVisualIDRegistry.getType(SubProcessNameEditPart.VISUAL_ID));;
        Dimension labelSize = nameEditPart.getFigure().getMinimumSize();

        Rectangle r1;
        boolean isCollapsed = ((Boolean) bodyEditPart
                .getStructuralFeatureValue(NotationPackage.eINSTANCE
                        .getDrawerStyle_Collapsed())).booleanValue();
        
     // calculate the dimension of the border edit part.
        int borderHeight = BORDER_HEIGHT/2;
        if (getPrimaryShape().getFigureSubProcessBorderFigure().hasChildren()) {
            borderHeight = getPrimaryShape().
                getFigureSubProcessBorderFigure().getBorderHeight()
                - ActivityEditPart.EVENT_FIGURE_SIZE/2;
        }
        
        if (!isCollapsed) {
            r1 = getChildrenBounds(bodyEditPart);
            if (r1.height == 0) {
                r1.width = EXPANDED_SIZE.width - INSETS.getWidth();
                r1.height = EXPANDED_SIZE.height - INSETS.getHeight() + getAbsCollapseHandleBounds().height;
            }
        } else {
         // calculate the dimension of the border edit part.
            Dimension collapsed = new Dimension(COLLAPSED_SIZE.width, COLLAPSED_SIZE.height);
            if (getPrimaryShape().getFigureSubProcessBorderFigure().hasChildren()) {
                Dimension borderPref = getPrimaryShape().getFigureSubProcessBorderFigure().getMinimumSize();
                collapsed.width = Math.max(borderPref.width, COLLAPSED_SIZE.width);
                collapsed.width = Math.max(collapsed.width, labelSize.width);
                collapsed.height = borderHeight + COLLAPSED_SIZE.height;
            }
            collapsed.expand(new Dimension(INSETS.getWidth(), INSETS.getHeight()));
            return collapsed;
        }


        return new Dimension(r1.width + INSETS.getWidth(),
                labelSize.height + r1.height + INSETS.getHeight() + borderHeight);
    }
    */

    /**
     * Claculates rectangle that contains all children of the specified edit
     * part
     * 
     * @notgenerated
     * @param editPart
     * @return the cumulated size of the children
     */
    private Rectangle getChildrenBounds(EditPart editPart) {
        Rectangle r = new Rectangle();

        boolean isFirst = true;
        for (Object object : editPart.getChildren()) {
            GraphicalEditPart child = (GraphicalEditPart) object;
            if (!isFirst) {
                Rectangle dim = child.getFigure().getBounds().getCopy();
                r.union(dim);
            } else {
                r = child.getFigure().getBounds().getCopy();
                isFirst = false;
            }
        }
        return r;
    }

    @Override
    /**
     * @notgenerated
     */
    public EditPolicy getPrimaryDragEditPolicy() {
        return new ResizableSubProcessEditPolicy();
    }

    @Override
    /**
     * @notgenerated
     */
    protected void refreshBounds() {
        super.refreshBounds();
//        refreshChildrenLocation();
    }

//    /**
//     * Moves body comparment's children in case they exceed the bounds of the
//     * compartment
//     * 
//     * @notgenerated
//     */
//    private void refreshChildrenLocation() {
//
//        List children = getChildren();
//        GraphicalEditPart bodyEditPart = null;
//        GraphicalEditPart borderEditPart = null;
//        GraphicalEditPart nameEditPart = null;
//        for (Object object : children) {
//            GraphicalEditPart editPart = (GraphicalEditPart) object;
//            if (editPart instanceof SubProcessSubProcessBodyCompartmentEditPart) {
//                bodyEditPart = editPart;
//            } else if (editPart instanceof SubProcessSubProcessBorderCompartmentEditPart) {
//                borderEditPart = editPart;
//            } else if (editPart instanceof SubProcessNameEditPart) {
//                nameEditPart = editPart;
//            }
//        }
//
//        if (bodyEditPart != null) {
//            boolean isCollapsed = ((Boolean) bodyEditPart
//                    .getStructuralFeatureValue(NotationPackage.eINSTANCE
//                            .getDrawerStyle_Collapsed())).booleanValue();
//            if (!isCollapsed) {
//                // move children only in expanded state
//                int width = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE
//                        .getSize_Width())).intValue();
//                int totalHeight = ((Integer) getStructuralFeatureValue(NotationPackage.eINSTANCE
//                        .getSize_Height())).intValue();
//
//                int borderCompartmentHeight = borderEditPart.getFigure()
//                        .getSize().height;
//                int labelHeight = nameEditPart.getFigure().getSize().height;
//                int height = totalHeight - borderCompartmentHeight
//                        - labelHeight - MapModeUtil.getMapMode(getFigure()).DPtoLP(5);
//
//                Point moveDelta = new Point(0, 0);
//
//                bodyEditPart
//                        .getStructuralFeatureValue(NotationPackage.eINSTANCE
//                                .getSize_Width());
//                Rectangle rect = getChildrenBounds(bodyEditPart);
//
//                if (rect.x + rect.width > width - INSETS.getWidth()) {
//                    moveDelta.x = rect.x + rect.width
//                            - (width - INSETS.getWidth());
//                }
//                if (rect.y + rect.height > height - INSETS.getHeight()) {
//                    moveDelta.y = rect.y + rect.height
//                            - (height - INSETS.getHeight());
//                }
//
//                children = bodyEditPart.getChildren();
//                if ((moveDelta.x + moveDelta.y) > 0 && children.size() > 0) {
//                    CompositeCommand command = new CompositeCommand(
//                            BpmnDiagramMessages.SubProcessEditPart_move_command_name);
//                    for (int i = 0; i < children.size(); i++) {
//                        GraphicalEditPart part = (GraphicalEditPart) children
//                                .get(i);
//                        Rectangle bound = part.getFigure().getBounds();
//                        Point loc = new Point(bound.x - moveDelta.x, bound.y
//                                - moveDelta.y);
//                        Rectangle newBounds = new Rectangle(loc, bound
//                                .getSize());
//                        SetBoundsCommand cmd = new SetBoundsCommand(
//                                getEditingDomain(), BpmnDiagramMessages.SubProcessEditPart_set_bounds_command, part,
//                                newBounds);
//                        command.add(cmd);
//                    }
//                    // check if the current thread is not read-only.
//                    if (!((TransactionalEditingDomainImpl)getEditingDomain()).
//                    		getActiveTransaction().isReadOnly()) {
//                    	getDiagramEditDomain().getDiagramCommandStack().
//                    		execute(new ICommandProxy(command));
//                    }
//
//                }
//            }
//        }
//    }

    /**
     * Returns bounds of figure without border figure included in absolute
     * coordinates .(zoom not computed == DP DevicePixel).
     * 
     * @notgenerated
     * @return
     */
    protected Rectangle getAbsBoundsWithoutBorder() {
        SubProcessFigure figure = getPrimaryShape();
        Rectangle theBounds = figure.getHandleBounds();
        figure.translateToAbsolute(theBounds);
        return theBounds;
    }
    
    public double getZoom() {
        double zoom = 1.0;
        RootEditPart rootEditPart = getRoot();
        if (rootEditPart instanceof ScalableFreeformRootEditPart) {
            zoom = ((ScalableFreeformRootEditPart) rootEditPart)
                    .getZoomManager().getZoom();
        }
        return zoom;
    }

    /**
     * Calculates bounds of collapse handle in absolute coordinates according to
     * the figures bounds (zoom not computed == DP DevicePixel).
     * 
     * @generated not
     * @return
     */
    public Rectangle getAbsCollapseHandleBounds(boolean zoomCompute) {
        //mystery: why do I need a zoom here?
        double zoom = zoomCompute ? getZoom() : 1.0;
        double size = COLLAPSE_HANDLE_HEIGHT * zoom;;//mm.LPtoDP(20);

        Rectangle theBounds = getAbsBoundsWithoutBorder();
        PrecisionRectangle handleBounds = new PrecisionRectangle(theBounds);

        handleBounds.setX(handleBounds.x + (handleBounds.width - size) / 2);
        handleBounds.setY(handleBounds.y + handleBounds.height - size);
        if (getPrimaryShape().getFigureSubProcessBorderFigure().hasChildren()) {
            handleBounds.setY(handleBounds.y - (ActivityEditPart.EVENT_FIGURE_SIZE*zoom)/2);
        }
        handleBounds.setWidth(size);
        handleBounds.setHeight(size);

        SubProcess model = (SubProcess) ((View) getModel()).getElement();
        if (model.isLooping()) {
            handleBounds.setX(handleBounds.x + size / 2 + 1);
        }
        return handleBounds;
    }

    /**
     * @notgenerated
     */
    @Override
    protected void handleNotificationEvent(Notification notification) {
        if (notification.getEventType() == Notification.SET) {
            if (BpmnPackage.eINSTANCE.getActivity_Looping().equals(
                    notification.getFeature())) {
                figure.repaint();
            }
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
        super.handleNotificationEvent(notification);
    }

    /**
     * @notgenerated
     */
    @Override
    public DragTracker getDragTracker(Request request) {
        return new TaskDragEditPartsTrackerEx(this);
    }

    /**
     * @notgenerated
     */
    @Override
    public Command getCommand(Request _request) {
        if (_request instanceof CreateUnspecifiedTypeConnectionRequest) {
            if (((CreateUnspecifiedTypeConnectionRequest) _request)
                    .getTargetEditPart() == null) {
                if (((IGraphicalEditPart) ((CreateUnspecifiedTypeConnectionRequest) _request).
                        getSourceEditPart()).resolveSemanticElement().eContainer().equals(this.resolveSemanticElement())) {
                    return getEditPolicy(EditPolicy.CONTAINER_ROLE).getCommand(_request);
                }
                Command co = super.getCommand(_request);
                return co;
            }
            Object model = ((CreateUnspecifiedTypeConnectionRequest) _request)
                    .getTargetEditPart().getModel();

            if ((model instanceof Node)) {
                List elTypes = ((CreateUnspecifiedTypeConnectionRequest) _request)
                        .getElementTypes();
                MetamodelType connType = (MetamodelType) BpmnElementTypes.MessagingEdge_3002;

                for (int i = 0; i < elTypes.size(); i++) {
                    if (elTypes.get(i) instanceof MetamodelType) {
                        connType = (MetamodelType) elTypes.get(i);
                        break;
                    }
                }

                if (connType == BpmnElementTypes.SequenceEdge_3001) {
                    
                    if (_request instanceof CreateUnspecifiedTypeConnectionRequest) {
                        CreateUnspecifiedTypeConnectionRequest request = 
                            (CreateUnspecifiedTypeConnectionRequest) _request;

                        if (request.getSourceEditPart() != null) {
                            EditPart ancestor = request.getSourceEditPart().getParent();
                            while (ancestor != null) {
                                if (ancestor == this) {
                                    //the source edit part is contained.
                                    //delegate the call to the body compartment
                                    //that knows what to do to create a connection and an element
                                    //contained inside the sub-process:
                                    EditPart bodyCompartment =
                                        getChildBySemanticHint("" +  //$NON-NLS-1$
                                           SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID);
                                    if (bodyCompartment != null) {
                                        return bodyCompartment.getCommand(request);
                                    }
                                    break;
                                }
                                ancestor = ancestor.getParent();
                            }
                        }
                        
                        // validate edge connection.
                        EdgeConnectionValidator edgeValidator = EdgeConnectionValidator.INSTANCE;
                        if (request.isDirectionReversed()) {
                            // do nothing
                        } else {
                            if ((request.getSourceEditPart() != null)
                                    && (request.getTargetEditPart() != null)) {
                                for (Iterator iter = request.getAllRequests()
                                        .iterator(); iter.hasNext();) {
                                    CreateConnectionRequest connectionRequest = (CreateConnectionRequest) iter
                                            .next();
                                    if (connectionRequest.getSourceEditPart() != null
                                            && connectionRequest
                                                    .getTargetEditPart() != null
                                            && connectionRequest
                                                    .getSourceEditPart() == request
                                                    .getSourceEditPart()
                                            && connectionRequest
                                                    .getTargetEditPart() == request
                                                    .getTargetEditPart()) {
                                        if (!edgeValidator.canConnect(request
                                                .getSourceEditPart(), request
                                                .getTargetEditPart())) {
                                            return null;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return super.getCommand(_request);
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
        SubProcess sp = (SubProcess) resolveSemanticElement();
        if (sp == null) {
            return; // in an inconsistent state, probably ungrouping.
        }
        int totalLength = sp.getIncomingEdges().size() + sp.getOutgoingEdges().size();
        int i = 0;
        //same for outgoing sequences:
        if (sourceOnly) {
            if (!ActivityEditPart.isOrderImportant(this, true)) {
                //set the anchor order according to the visuals.
                Node thisModel = (Node)getModel();
                List<Edge> seqs = ActivityEditPart.getSourceSequenceEdges(thisModel, true);
                totalLength = seqs.size();
                int ind = 0;
                for (Edge srcEdge : seqs) {
                    setAnchorIndex(connIndex, (EModelElement)srcEdge.getElement(), i, totalLength, true);
                    ind++;
                }
            } else {
                //set the anchor order according to the index in the semantic model.
                EList<SequenceEdge> outEdges = sp.getOutgoingEdges();
                int ind = 0;
                totalLength = outEdges.size();
                for (SequenceEdge edge : outEdges) {
                    setAnchorIndex(connIndex, edge, i, totalLength, true);
                    ind++;
                }
            }
        } else {
            //same for incoming sequences:
            if (!ActivityEditPart.isOrderImportant(this, false)) {
                //set the anchor order according to the visuals.
                Node thisModel = (Node)getModel();
                List<Edge> seqs = ActivityEditPart.getTargetSequenceEdges(thisModel, true);
                totalLength = seqs.size();
                int ind = 0;
                for (Edge targetEdge : seqs) {
                    setAnchorIndex(connIndex, (EModelElement)targetEdge.getElement(), i, totalLength, false);
                    ind++;
                }
            } else {
                //set the anchor order according to the index in the semantic model.
                EList<SequenceEdge> inEdges = sp.getIncomingEdges();
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
            int l = sp.getAssociations().size();
            for (Association assoc : sp.getAssociations()) {
                setAnchorIndex(connIndex, assoc, ii, l, false);
                i++;
            }
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

    /**
     * Called during the painting of the sub-process figure.
     * Checks if the sub-process is adhoc and paint the standard tilde
     * marker.
     * <p>
     * Editors that extend the stp bpmn modeler can override this method
     * to handle other types of markers.
     * </p>
     * @notgenerated
     */
    protected void paintTildeMarker(Graphics graphics) {
        SubProcess zmodel = (SubProcess) ((View) getModel()).getElement();
        if (zmodel.isAdhoc()) {
            int size = COLLAPSE_HANDLE_HEIGHT;
            Rectangle bounds = getAbsBoundsWithoutBorder();
            getPrimaryShape().translateToRelative(bounds);
            Rectangle tildeRect = new Rectangle();
            tildeRect.x = bounds.x + bounds.width/2 + size/2 + 12;
            tildeRect.y = bounds.y + bounds.height - size;
            if (getPrimaryShape().getFigureSubProcessBorderFigure().hasChildren()) {
            	tildeRect.y = (int) (tildeRect.y - (ActivityEditPart.EVENT_FIGURE_SIZE*getZoom())/2);
            }
            tildeRect.height = size;
            tildeRect.width = size;

            ActivityPainter.paintTilde(graphics, tildeRect, getFigure());
        }						
    }
}
