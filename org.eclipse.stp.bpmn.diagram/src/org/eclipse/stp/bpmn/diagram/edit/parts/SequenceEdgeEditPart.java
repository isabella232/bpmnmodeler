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
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.LineSeg;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.RoutingStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SequenceFlowConditionType;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.diagram.edit.policies.SequenceEdgeItemSemanticEditPolicy;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.stp.bpmn.figures.BpmnShapesDefaultSizes;
import org.eclipse.stp.bpmn.figures.ConnectionLayerExEx;
import org.eclipse.stp.bpmn.figures.ConnectionUtils;
import org.eclipse.stp.bpmn.figures.activities.ActivityPainter;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchor;
import org.eclipse.stp.bpmn.figures.router.EdgeRectilinearRouter;
import org.eclipse.stp.bpmn.policies.BpmnDragDropEditPolicy;
import org.eclipse.stp.bpmn.policies.ConnectionEndPointEditPolicyEx;
import org.eclipse.stp.bpmn.policies.OpenFileEditPolicy;
import org.eclipse.swt.SWT;

/**
 * @generated
 */
public class SequenceEdgeEditPart extends ConnectionNodeEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 3001;

//    /**
//     * @notgenerated
//     */
//    private static final int DELTA_ANCHOR_SIZE = 10;

    /**
     * @notgenerated
     */
    private ConnectionRouter rectilinearRouter = null;
    
    /**
     * @generated
     */
    public SequenceEdgeEditPart(View view) {
        super(view);
    }
    
    /**
     * @generated NOT
     */
    @Override
    protected void refreshVisuals() {
        refreshSourceAnchor();
        refreshTargetAnchor();
        super.refreshVisuals();
    }

    /**
     * @generated
     */
    protected void createDefaultEditPoliciesGen() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new SequenceEdgeItemSemanticEditPolicy());
    }

    /**
     * @generated not
    */
    protected void createDefaultEditPolicies() {
        createDefaultEditPoliciesGen();
     // adding default drag and drop edit policy
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, 
                new BpmnDragDropEditPolicy(this));
        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
                new ConnectionEndPointEditPolicyEx());
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
     * Creates figure for this edit part.
     * 
     * Body of this method does not depend on settings in generation model
     * so you may safely remove <i>generated</i> tag and modify it.
     * 
     * @generated
     */
    protected Connection createConnectionFigure() {
        return new EdgeFigure();
    }

    /**
     * @generated
     */
    public class EdgeFigure extends
            org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx {


        /**
         * @notgenerated
         */
        private boolean isDefault;

        /**
         * @notgenerated
         */
        private PointList smoothPoints;

        /**
         * @notgenerated
         */
        private boolean routerIsRectilinear = false;

        public EdgeFigure(boolean isRectilinear) {
            this();
            routerIsRectilinear = isRectilinear;
        }
        
        /**
         * @notgenerated added the setting of isDefault.
         */
        public EdgeFigure() {
            this.setForegroundColor(org.eclipse.draw2d.ColorConstants.black

            );
            setTargetDecoration(createTargetDecoration());
            
            RotatableDecoration srcDecoration = createSourceDecoration();
            if (srcDecoration != null) {
                setSourceDecoration(srcDecoration);
            }
            
            if (SequenceEdgeEditPart.this.getEdge().getElement() instanceof 
            		SequenceEdge) {
            	isDefault = ((SequenceEdge)SequenceEdgeEditPart.this
            			.getEdge().getElement()).isIsDefault();
            } else {
            	isDefault = false;
            }
        }
        
        /**
         * @generated
         */
        private org.eclipse.stp.bpmn.figures.SequenceEdgePolylineTargetDecoration createTargetDecoration() {
            org.eclipse.stp.bpmn.figures.SequenceEdgePolylineTargetDecoration df = new org.eclipse.stp.bpmn.figures.SequenceEdgePolylineTargetDecoration();
            return df;
        }

        /**
         * @generated NOT
         * 
         * @see org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx#getSmoothPoints()
         */
        @Override
        public PointList getSmoothPoints() {
            if (!routerIsRectilinear) {
                smoothPoints = super.getSmoothPoints();
            } else {
                smoothPoints =
                  ConnectionUtils.getRoundedRectilinearSmoothPoints(
                          true,
                          getPoints(), super.getSmoothness());
            }
            return smoothPoints;
        }
        
        @Override
        public void paintFigure(Graphics graphics) {
            graphics.setAlpha(ActivityPainter.getSequenceEdgeTransparency());
            super.paintFigure(graphics);
        }
        
        @Override
        /**
         * @notgenerated Adds default connection decorator painting.
         */
        protected void outlineShape(Graphics g) {
            
            if (getSource() instanceof Activity2EditPart && 
                    ((Activity)((Activity2EditPart) getSource()).
                            resolveSemanticElement()).getActivityType().getValue() == 
                                ActivityType.EVENT_INTERMEDIATE_COMPENSATION) {
                g.setLineStyle(SWT.LINE_CUSTOM);
                g.setLineDash(new int[] {1, 2});
            } 
            super.outlineShape(g);
            int alpha = BpmnDiagramEditorPlugin.getInstance().getPreferenceStore().
            getInt(BpmnDiagramPreferenceInitializer.PREF_SHOW_SHADOWS_TRANSPARENCY);
            alpha -= 255 - ActivityPainter.getSequenceEdgeTransparency();
            if (alpha > 0) {
                g.pushState();
                g.setAlpha(alpha);
                PointList pl = smoothPoints.getCopy();
                pl.translate(4, 6);
                pl.setPoint(pl.getFirstPoint().translate(-2, 0), 0);
                pl.setPoint(pl.getLastPoint().translate(-8, 0), pl.size() -1);
                g.drawPolyline(pl);
                PointList head = new PointList();
                head.addPoint(pl.getLastPoint().getCopy().translate(4, -2));
                head.addPoint(pl.getLastPoint().getCopy().translate(4, 2));
                head.addPoint(pl.getLastPoint().getCopy().translate(0, 3));
                head.addPoint(pl.getLastPoint().getCopy().translate(0, -3));
                g.setBackgroundColor(ColorConstants.black);
                g.fillPolygon(head);
                g.popState();
            }
            if (isDefault) {
                Point res = new Point();
                PointList points = smoothPoints;
                final int DISTANCE = MapModeUtil.getMapMode(this)
                    .LPtoDP(40);
                final double TICK_SIZE = 7;
                final double ANGLE = 110.0;

                long length = PointListUtilities.getPointsLength(points);
                long distance;
                if (length < DISTANCE * 2) {
                    distance = length / 2;
                } else {
                    distance = DISTANCE;
                }
                res = PointListUtilities.pointOn(points, distance,
                        LineSeg.KeyPoint.ORIGIN, res);
                LineSeg segment = PointListUtilities.getNearestSegment(
                        PointListUtilities.getLineSegments(points), res.x,
                        res.y);

                double[] equation = segment.getEquation();
                double a = equation[0];
                double b = equation[1];
                double k = -a / b;
                double angleRad = Math.atan(k);
                double angleDeg = Math.toDegrees(angleRad);

                double angle1 = Math.toRadians(ANGLE - angleDeg);
                double angle2 = angle1 + Math.PI;

                double x1 = res.x + TICK_SIZE * Math.cos(angle1);
                double y1 = res.y - TICK_SIZE * Math.sin(angle1);

                double x2 = res.x + TICK_SIZE * Math.cos(angle2);
                double y2 = res.y - TICK_SIZE * Math.sin(angle2);

                Point p1 = new PrecisionPoint(x1, y1);
                Point p2 = new PrecisionPoint(x2, y2);
                
                g.drawLine(p1, p2);
            }
            // if the edge is conditional, a losange is drawn at its source
            if (shouldShowConditionalDecoration()) {
                Point p1 = new Point();
                Point p3 = new Point();
                PointList points = smoothPoints;
                
                p1 = PointListUtilities.pointOn(points, 0,
                        LineSeg.KeyPoint.ORIGIN, p1);
                p3 = PointListUtilities.pointOn(points, getMapMode().LPtoDP(16),
                        LineSeg.KeyPoint.ORIGIN, p3);

                PointList list = new PointList();
                list.addPoint(p1);
                
                Point kitePoint = new Point();
                kitePoint = PointListUtilities.pointOn(points, getMapMode().LPtoDP(8),
                        LineSeg.KeyPoint.ORIGIN, kitePoint);
                boolean onY = Math.abs(p1.y - p3.y) < getMapMode().LPtoDP(3);
                int translate = getMapMode().LPtoDP(4);
                Point p2 = kitePoint.getCopy().translate(onY ? 0 : translate, onY ? translate : 0);
                Point p4 = kitePoint.getCopy().translate(onY ? 0 : -translate, onY ? -translate : 0);
                list.addPoint(p2);
                list.addPoint(p3);
                list.addPoint(p4);
                g.setForegroundColor(ColorConstants.white);
                g.fillPolygon(list);
                g.setForegroundColor(ColorConstants.black);
                g.drawPolygon(list);
            }
        }
        
        public int getTargetGatewayConstraint() {
            return SequenceEdgeEditPart.this.getTargetGatewayConstraint();
        }
        
        public int getSourceGatewayConstraint() {
            return SequenceEdgeEditPart.this.getSourceGatewayConstraint();
        }
        
    }

    /**
     * @notgenerated if the style is rectilinear, use our own bpmn message router
     * ortherwise call super.
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
    protected void installRouter() {
        ConnectionLayer cLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
        RoutingStyle style = (RoutingStyle) ((View) getModel())
                .getStyle(NotationPackage.Literals.ROUTING_STYLE);

        if (style != null && cLayer instanceof ConnectionLayerExEx) {
            ConnectionLayerExEx cLayerEx = (ConnectionLayerExEx) cLayer;
            if (Routing.RECTILINEAR_LITERAL == style.getRouting()) {
                if (rectilinearRouter == null) {
                    Vertex source = null;
                    if (resolveSemanticElement() instanceof SequenceEdge) {
                        SequenceEdge edge = (SequenceEdge) resolveSemanticElement();
                        source = edge.getSource();
                    }
                    if (source instanceof Activity && 
                            ((Activity) source).getEventHandlerFor() != null) {
                        rectilinearRouter = cLayerEx.getBpmnSequenceEdgeForBorderedShapesRectilinearRouter();
                    } else {
                        rectilinearRouter = new EdgeRectilinearRouter(this);
                    }
                }
                getConnectionFigure().setConnectionRouter(rectilinearRouter);
                ((EdgeFigure) getFigure()).routerIsRectilinear = true;
                refreshRouterChange();
                return;
            }
        }
        ((EdgeFigure) getFigure()).routerIsRectilinear = false;
        super.installRouter();
    }

    private boolean shouldShowConditionalDecoration() {
        EObject object = resolveSemanticElement();
        if (object instanceof SequenceEdge) {
            if (((SequenceEdge) object).isIsDefault()) {
                return false;
            }
            Vertex src = ((SequenceEdge) object).getSource();
            if (src != null && (src instanceof Activity)) {
                ActivityType type = ((Activity) src).getActivityType();
                if (ActivityType.VALUES_GATEWAYS.contains(type)) {
                    return false;
                }
            }
            if (((SequenceEdge) object).getConditionType() != 
                SequenceFlowConditionType.NONE_LITERAL) {
                return true;
            }
            
        }
        
        return false;
    }

    /**
     * @generated NOT ability to set a source decoration for the sequence
     * edge. Returns null by default.
     * @return
     */
    protected RotatableDecoration createSourceDecoration() {
        return null;
    }
    
    @Override
    /**
     * @notgenerated Overriden to set <code>isDefault</code> property to
     *               <code>EdgeFigure</code>.
     */
    protected void handleNotificationEvent(Notification notification) {
        if (notification.getEventType() == Notification.SET) {
            if (BpmnPackage.eINSTANCE.getSequenceEdge_IsDefault().
                    equals(notification.getFeature())) {
                ((EdgeFigure) getFigure()).isDefault = notification
                .getNewBooleanValue();
                getFigure().repaint();
            } else if (BpmnPackage.eINSTANCE.getSequenceEdge_ConditionType().
                        equals(notification.getFeature())) {
                ((EdgeFigure) getFigure()).isDefault = notification
                .getNewValue() == SequenceFlowConditionType.DEFAULT_LITERAL;
                getFigure().repaint();
            }
        }
        super.handleNotificationEvent(notification);
    }
    /**
     * Makes sure the connection anchor has its type and other info set if 
     * necessary.
     * 
     * @notgenerated
     */
    @Override
    protected ConnectionAnchor getSourceConnectionAnchor() {
        ConnectionAnchor ca = super.getSourceConnectionAnchor();
        updateConnectionAnchor(ca, true);
        return ca;
    }
    /**
     * Makes sure the connection anchor has its type and other info set if 
     * necessary.
     * 
     * @notgenerated
     */
    @Override
    protected ConnectionAnchor getTargetConnectionAnchor() {
        ConnectionAnchor ca = super.getTargetConnectionAnchor();
        updateConnectionAnchor(ca, false);
        return ca;
    }

    /**
     * Sets the connection anchor with extra parameters if it is an
     * IModelAwareConnectionAnchor
     * @param ca The connection anchor to eventually update
     */
    private void updateConnectionAnchor(ConnectionAnchor ca, boolean isSource) {
        if (ca instanceof IModelAwareAnchor) {
            IModelAwareAnchor modelAware = (IModelAwareAnchor)ca;
            if (super.getSource() == null) {
            	return;
            	
            }
            EList<SequenceEdge> seqs = isSource ?
                    ((Activity)((GraphicalEditPart) super.getSource())
                            .getPrimaryView().getElement()).getOutgoingEdges() :
                    ((Activity)((GraphicalEditPart) super.getTarget())
                            .getPrimaryView().getElement()).getIncomingEdges();
            if (!(getPrimaryView().getElement() instanceof SequenceEdge)) {
                String connectionType =
                    isSource && super.getSource() instanceof Activity2EditPart ?
                            String.valueOf(VISUAL_ID) + "-subprocessBorder" : //$NON-NLS-1$
                                String.valueOf(VISUAL_ID);
                modelAware.setConnectionType(isSource,
                        connectionType, 0, 1);
            } else {
                SequenceEdge seqEdge = (SequenceEdge)getPrimaryView().getElement();
                int ind = seqs.indexOf(seqEdge);
                if (!isOrderImportant(isSource)) {
                    ind = isSource
                        ? ActivityEditPart.getStartAnchorVisualIndex((Edge)getModel())
                        : ActivityEditPart.getTargetAnchorVisualIndex((Edge)getModel());
                }
                int count = seqs.size();
                String connectionType =
                    isSource && super.getSource() instanceof Activity2EditPart ?
                            String.valueOf(VISUAL_ID) + "-subprocessBorder" : //$NON-NLS-1$
                                String.valueOf(VISUAL_ID);
                modelAware.setConnectionType(isSource,
                        connectionType, ind, count);
            }
        }
    }


    private int calculateBestPosition(SequenceEdge seqEdge, 
            boolean isSource) {
        List connections = 
            isSource ?
                    ((IGraphicalEditPart) super.getSource()).getSourceConnections() :
                        ((IGraphicalEditPart) super.getTarget()).getTargetConnections();
        List<IGraphicalEditPart> seqs = new ArrayList<IGraphicalEditPart>();
        IGraphicalEditPart index = null;
        for (Object con : connections) {
            if (con instanceof SequenceEdgeEditPart) {
                IGraphicalEditPart part = (IGraphicalEditPart)  (isSource ? 
                        ((SequenceEdgeEditPart) con).getTarget() : ((SequenceEdgeEditPart) con).getSource());
                if (part != null) {
                    seqs.add(part);
                    if (con == this) {
                        index = part;
                    }
                }
            }
        }
        Collections.sort(seqs, new Comparator<IGraphicalEditPart>() {

            public int compare(IGraphicalEditPart o1, IGraphicalEditPart o2) {
                int y1 = ((Location) ((Node) o1.getModel()).getLayoutConstraint()).getY();
                int y2 = ((Location) ((Node) o2.getModel()).getLayoutConstraint()).getY();
                 if (y1 < y2) {
                     return -1;
                 } else if (y1 == y2){
                     return 0;
                 } else {
                     return 1;
                 }
            }});
        
        return seqs.indexOf(index);
    }

    /**
     * @notgenerated used by the contributionItemProvider extension point
     * to determine if it should show the "Sets as default choice" popup menu 
     * button
     */
    public boolean isConditional() {
    	EObject object = getNotationView().getElement();
		if (object instanceof SequenceEdge) {
			Vertex src = ((SequenceEdge) object).getSource();
			if (src != null && (src instanceof Activity)) {
			    return src.getOutgoingEdges().size() > 1;
			}
		}
		return false;
    }
    
    /**
     * 
     * @return one of {@link EdgeRectilinearRouter#NO_CONSTRAINT},
     * {@link EdgeRectilinearRouter#CONSTRAINT_BOTTOM},
     * {@link EdgeRectilinearRouter#CONSTRAINT_ON_TOP},
     * {@link EdgeRectilinearRouter#CONSTRAINT_MIDDLE}
     * by looking at the target.
     */
    public int getTargetGatewayConstraint() {
        IGraphicalEditPart target = (IGraphicalEditPart) getTarget();
        if (target == null  || target.resolveSemanticElement() == null || getSource() == null) {
            return EdgeRectilinearRouter.NO_CONSTRAINT;
        }
        List<SequenceEdgeEditPart> targets = new ArrayList<SequenceEdgeEditPart>();
        int index = ((Activity) target.resolveSemanticElement()).
            getIncomingEdges().indexOf(resolveSemanticElement());
        
        if (!isOrderImportant(false)) {
            index = ActivityEditPart.getTargetAnchorVisualIndex((Edge)getModel());
        }
        int tgtsize = 0;
        for (Object o : target.getTargetConnections()) {
            if (o instanceof SequenceEdgeEditPart) {
                tgtsize++;
                targets.add((SequenceEdgeEditPart) o);
            }
        }
        int srcsize = 0;
        for (Object o : target.getSourceConnections()) {
            if (o instanceof SequenceEdgeEditPart) {
                srcsize++;
            }
        }
        boolean constraint = shouldShowTopDownEdges((Activity) target.resolveSemanticElement());
        // if it has no more than one edge on the other side.
        constraint = constraint && tgtsize > 1 && srcsize <= 1;
        if (!constraint) {
            return EdgeRectilinearRouter.NO_CONSTRAINT;
        }
     // we have two edges. We can choose, depending on the position of the target shapes, 
        // to place the edges at top and bottom, middle and bottom, or top and middle.
        // this requires to inspect the location of the target edit parts.
        if (tgtsize == 2 ) {
            Bounds targetLoc = BpmnShapesDefaultSizes.getBounds((Node) ((Edge) getModel()).getTarget());
            Bounds sourceLoc = BpmnShapesDefaultSizes.getBounds((Node) ((Edge) getModel()).getSource());
            Bounds otherSourceLoc = null;
            for (SequenceEdgeEditPart p : targets) {
                if (p != this) {
                    otherSourceLoc = BpmnShapesDefaultSizes.getBounds((Node) ((Edge) p.getModel()).getSource());break;
                }
            }
            //don't apply DP2LP: we are in the gmf view coords where there is no scale to apply.
            int topLimit = targetLoc.getY() + targetLoc.getHeight() + 20;//MapModeUtil.getMapMode(getFigure()).DPtoLP(20);
            int bottomLimit = targetLoc.getY() - 20;//MapModeUtil.getMapMode(getFigure()).DPtoLP(20);

            //assuming the anchor gets plugged in the middle.
            int sourceMidY = sourceLoc.getY() + sourceLoc.getHeight()/2;
            int otherSourceMidY = otherSourceLoc.getY() + otherSourceLoc.getHeight()/2;
            
            int midY = targetLoc.getY() + targetLoc.getHeight()/2;
            
//            String n = ((NamedBpmnObject) this.resolveSemanticElement()).getName();
//            if ("one".equals(n) || "two".equals(n) || "three".equals(n)) {
//                System.err.println("hello target " + n + " index=" + index);
//                if ("two".equals(n)) {
//                    n="twotwo.";
//                }
//            }
            return getGatewayConstraintFor2Connections(bottomLimit,
                    topLimit, midY, sourceMidY, otherSourceMidY, index);
        }
        
        if (index == 0) {
            return EdgeRectilinearRouter.CONSTRAINT_ON_TOP;
        } else if (index == tgtsize -1) {
            return EdgeRectilinearRouter.CONSTRAINT_BOTTOM;
        } else {
            return EdgeRectilinearRouter.NO_CONSTRAINT;
        }
    }

    /**
     * 
     * @return one of {@link EdgeRectilinearRouter#NO_CONSTRAINT},
     * {@link EdgeRectilinearRouter#CONSTRAINT_BOTTOM},
     * {@link EdgeRectilinearRouter#CONSTRAINT_ON_TOP}
     * by looking at the source.
     */
    public int getSourceGatewayConstraint() {
        IGraphicalEditPart source = (IGraphicalEditPart) getSource();
        if (source == null || source.resolveSemanticElement() == null) {
            return EdgeRectilinearRouter.NO_CONSTRAINT;
        }
        int srcsize = 0;
        int index = ((Activity) source.resolveSemanticElement()).
            getOutgoingEdges().indexOf(resolveSemanticElement());
        if (!isOrderImportant(true)) {
            index = ActivityEditPart.getStartAnchorVisualIndex((Edge)getModel());
        }
        List<SequenceEdgeEditPart> sources = new ArrayList<SequenceEdgeEditPart>();
        for (Object o : source.getSourceConnections()) {
            if (o instanceof SequenceEdgeEditPart) {
                srcsize++;
                sources.add((SequenceEdgeEditPart) o);
            }
        }
        int tgtsize = 0;
        for (Object o : source.getTargetConnections()) {
            if (o instanceof SequenceEdgeEditPart) {
                tgtsize++;
            }
        }
        boolean constraint = shouldShowTopDownEdges((Activity) source.resolveSemanticElement());
        // if it has no more than one edge on the other side.
        constraint = constraint && srcsize > 1 && tgtsize <= 1;
        if (!constraint) {
            return EdgeRectilinearRouter.NO_CONSTRAINT;
        }
        // we have two edges. We can choose, depending on the position of the target shapes, 
        // to place the edges at top and bottom, middle and bottom, or top and middle.
        // this requires to inspect the location of the target edit parts.
        if (srcsize == 2 ) {
            Bounds targetLoc = BpmnShapesDefaultSizes.getBounds((Node) ((Edge) getModel()).getTarget());
            Bounds sourceLoc = BpmnShapesDefaultSizes.getBounds((Node) ((Edge) getModel()).getSource());
            Bounds otherTargetLoc = null;
            for (SequenceEdgeEditPart p : sources) {
                if (p != this) {
                    otherTargetLoc = BpmnShapesDefaultSizes.getBounds((Node) ((Edge) p.getModel()).getTarget());
                }
            }
          //don't apply LD2DP: we are in the gmf view coords
          //where there is no scale to apply.
            int topLimit = sourceLoc.getY() + sourceLoc.getHeight() + 20;//MapModeUtil.getMapMode(getFigure()).DPtoLP(20);
            int bottomLimit = sourceLoc.getY() - 20;//MapModeUtil.getMapMode(getFigure()).DPtoLP(20);
            int midY = sourceLoc.getY() + sourceLoc.getHeight()/2;
            
            //assuming the anchor gets plugged in the middle.
            int targetMidY = targetLoc.getY() + targetLoc.getHeight()/2;
            int otherTargetMidY = otherTargetLoc.getY() + otherTargetLoc.getHeight()/2;
            
//            String n = ((NamedBpmnObject) this.resolveSemanticElement()).getName();
//            if ("one".equals(n) || "two".equals(n) || "three".equals(n)) {
//                System.err.println("hello source " + n + " index=" + index);
//                if (!isOrderImportant(true)) {
//                    String modName = ((NamedBpmnObject) this.resolveSemanticElement()).getName();
//                    if (modName.equals("one")) {
//                        System.err.println("hello one");
//                    }
//                    index = ActivityEditPart.getStartAnchorVisualIndex((Edge)getModel());
//                }
//            }
            
            return getGatewayConstraintFor2Connections(bottomLimit,
                    topLimit, midY, targetMidY, otherTargetMidY, index);
        }
        if (index == 0) {
            return EdgeRectilinearRouter.CONSTRAINT_ON_TOP;
        } else if (index == srcsize -1) {
            return EdgeRectilinearRouter.CONSTRAINT_BOTTOM;
        } else {
            return EdgeRectilinearRouter.NO_CONSTRAINT;
        }
    }
    
    /**
     * 
     * @param isSource, true if this method should examine the source,
     * false if it should look at the target
     * @return true if the order of the edges over the part to examine is important.
     */
    private boolean isOrderImportant(boolean isSource) {
        EditPart part = isSource ? getSource() : getTarget();
        return ActivityEditPart.isOrderImportant((IGraphicalEditPart) part, isSource);
    }
    
    /**
     * 
     * @param topScreenLimit 20 pixels above the top of the gateway for which we compute the constraint
     * @param bottomScreenLimit 20 pixels below the bottom of the gateway for which we compute the constraint
     * @param gatewayMidY The center Y coord of the gateway
     * @param thisMidY The center of the shape processed
     * @param otherMidY The center of the other shape
     * @param currentBestIndex The currently suggestd index: 0 for the top, 1 for the bottom.
     * @return {@link EdgeRectilinearRouter.CONSTRAINT_ON_TOP},
     * {@link EdgeRectilinearRouter.CONSTRAINT_BOTTOM},
     * {@link EdgeRectilinearRouter.CONSTRAINT_MIDDLE}
     */
    private int getGatewayConstraintFor2Connections(int topScreenLimit, int bottomScreenLimit,
            int gatewayMidY, int thisMidY, int otherMidY, int currentBestIndex) {
        if (currentBestIndex == 0) {//first edge, at the top of the screen.
            //so returned value will be either at-screen-top or at-screen-middle.
            if (thisMidY < topScreenLimit) {//zone-1 above-screen of gateway:
                //put it in the middle to leave some space for the one above:
                return EdgeRectilinearRouter.CONSTRAINT_ON_TOP;
            } else if (thisMidY < bottomScreenLimit) {//zone-2 at the level of the gateway:
                if (Math.abs(thisMidY-gatewayMidY) < Math.abs(otherMidY-gatewayMidY)) {
                    //we are closer: we deserve to be in the middle.
                    //the other one will be at the bottom
                    return EdgeRectilinearRouter.CONSTRAINT_MIDDLE;
                } else {
                    //the other one is closer, let it be in the middle.
                    //put ourselves at the bottom then:
                    return EdgeRectilinearRouter.CONSTRAINT_ON_TOP;//is bottom the bottom of the screen?
                }
            } else {//zone-2 below-screen of the gateway:
                return EdgeRectilinearRouter.CONSTRAINT_MIDDLE;//is bottom the bottom of the screen?
            }
        } else {//second edge, place it below the other one.
            //so returned value will be either at-screen-bottom or at-screen-middle.
            //let's call ourselves:
            if (thisMidY >= bottomScreenLimit) {//zone-2 below-screen of the gateway:
                return EdgeRectilinearRouter.CONSTRAINT_BOTTOM;
            }
            int otherOneresult = getGatewayConstraintFor2Connections(
                    topScreenLimit, bottomScreenLimit,
                    gatewayMidY, otherMidY, thisMidY, 0);
            if (otherOneresult == EdgeRectilinearRouter.CONSTRAINT_MIDDLE) {
                return EdgeRectilinearRouter.CONSTRAINT_BOTTOM;//is top top of the screen?
            } else {
                return EdgeRectilinearRouter.CONSTRAINT_MIDDLE;//is top top of the screen?
            }
        }
    }
        
    /**
     * Returns whether the activity is eligible to show top-down edges
     * @param activity
     * @return true if the activity is a gateway or an event with no messaging edges.
     */
    private static boolean shouldShowTopDownEdges(Activity activity) {
        boolean constraint = 
            ActivityType.VALUES_GATEWAYS.contains(activity.getActivityType());
        if (constraint) {
            return true;
        }
        constraint = ActivityType.VALUES_EVENTS.contains(activity.getActivityType()) && 
            activity.getOrderedMessages().isEmpty();
        return constraint;
    }
}
