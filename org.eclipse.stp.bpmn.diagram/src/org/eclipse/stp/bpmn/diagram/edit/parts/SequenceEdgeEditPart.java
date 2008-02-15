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
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
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
import org.eclipse.stp.bpmn.figures.ConnectionLayerExEx;
import org.eclipse.stp.bpmn.figures.ConnectionUtils;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchor;
import org.eclipse.stp.bpmn.figures.router.EdgeRectilinearRouter;
import org.eclipse.stp.bpmn.policies.BpmnDragDropEditPolicy;
import org.eclipse.stp.bpmn.policies.ConnectionEndPointEditPolicyEx;
import org.eclipse.stp.bpmn.policies.OpenFileEditPolicy;

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
        // adding an open edit policy
        installEditPolicy(EditPolicyRoles.OPEN_ROLE,
        		new OpenFileEditPolicy());
        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
                new ConnectionEndPointEditPolicyEx());
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
         * @notgenerated
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
        /**
         * @notgenerated Adds default connection decorator painting.
         */
        protected void outlineShape(Graphics g) {
            super.outlineShape(g);
            if (isDefault && isConditional()) {
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
                    if (getSource() != null && getSource() instanceof Activity2EditPart) {
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
            if (BpmnPackage.eINSTANCE.getSequenceEdge_IsDefault().equals(
                    notification.getFeature())) {
                ((EdgeFigure) getFigure()).isDefault = notification
                        .getNewBooleanValue();
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
                    ind = ActivityEditPart.calculateBestPosition(seqEdge, isSource, 
                            (Node) (isSource ? getSource().getModel() : getTarget().getModel()));
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
				ActivityType type = ((Activity) src).getActivityType();
				if (type.getValue() == 
					ActivityType.GATEWAY_DATA_BASED_INCLUSIVE||
					type.getValue() == 
						ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE) {
					return true;
				}
			}
		}
		return false;
    }
    
    /**
     * 
     * @return one of {@link EdgeRectilinearRouter#NO_CONSTRAINT},
     * {@link EdgeRectilinearRouter#CONSTRAINT_BOTTOM},
     * {@link EdgeRectilinearRouter#CONSTRAINT_ON_TOP}
     * by looking at the target.
     */
    public int getTargetGatewayConstraint() {
        IGraphicalEditPart target = (IGraphicalEditPart) getTarget();
        if (target == null  || target.resolveSemanticElement() == null || getSource() == null) {
            return EdgeRectilinearRouter.NO_CONSTRAINT;
        }
        int tgtsize = 0;
        List<SequenceEdgeEditPart> targets = new ArrayList<SequenceEdgeEditPart>();
        int index = ((Activity) target.resolveSemanticElement()).
            getIncomingEdges().indexOf(resolveSemanticElement());
        if (!isOrderImportant(true)) {
            index = ActivityEditPart.calculateBestPosition(
                    (SequenceEdge) resolveSemanticElement(), false, (Node) getTarget().getModel());
        }
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
        // if the activity is a gateway
        boolean constraint = ActivityType.VALUES_GATEWAYS.contains(
                ((Activity) target.resolveSemanticElement()).getActivityType());
        // if it has no more than one edge on the other side.
        constraint = constraint && tgtsize > 1 && 
                srcsize <= 1;
        if (!constraint) {
            return EdgeRectilinearRouter.NO_CONSTRAINT;
        }
     // we have two edges. We can choose, depending on the position of the target shapes, 
        // to place the edges at top and bottom, middle and bottom, or top and middle.
        // this requires to inspect the location of the target edit parts.
     // we have two edges. We can choose, depending on the position of the target shapes, 
        // to place the edges at top and bottom, middle and bottom, or top and middle.
        // this requires to inspect the location of the target edit parts.
        if (tgtsize == 2 ) {
            
            Bounds sourceLoc = ((Bounds) ((Node) ((Edge) getModel()).getSource()).getLayoutConstraint());
            Bounds targetLoc = ((Bounds) ((Node) ((Edge) getModel()).getTarget()).getLayoutConstraint());
            Bounds otherSourceLoc = null;
            for (SequenceEdgeEditPart p : targets) {
                if (p != this) {
                    otherSourceLoc = ((Bounds) ((Node) ((Edge) p.getModel()).getSource()).getLayoutConstraint());
                }
            }
            int topLimit = targetLoc.getY() + targetLoc.getHeight() + MapModeUtil.getMapMode(getFigure()).DPtoLP(20);
            int bottomLimit = targetLoc.getY() - MapModeUtil.getMapMode(getFigure()).DPtoLP(20);
            if (sourceLoc.getY() < topLimit && otherSourceLoc.getY() < topLimit) {
                // the two shapes are higher than the gateway.
                // the highest shape has a top constraint, while the other one will have a middle constraint.
                if (index == tgtsize -1) {
                    return EdgeRectilinearRouter.CONSTRAINT_MIDDLE;
                }
            } else if (sourceLoc.getY() + sourceLoc.getHeight()/2 > bottomLimit && 
                    otherSourceLoc.getY() + otherSourceLoc.getHeight()/2 > bottomLimit) {
             // the two shapes are lower than the gateway.
                // the highest shape has a top constraint, while the other one will have a middle constraint.
                if (index == 0) {
                    return EdgeRectilinearRouter.CONSTRAINT_MIDDLE;
                }
            }
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
            index = ActivityEditPart.calculateBestPosition(
                    (SequenceEdge) resolveSemanticElement(), true, (Node) getSource().getModel());
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
     // if the activity is a gateway
        boolean constraint = ActivityType.VALUES_GATEWAYS.contains(
                ((Activity) source.resolveSemanticElement()).getActivityType());
     // if it has no more than one edge on the other side.
        constraint = constraint && srcsize > 1 && 
                tgtsize <= 1;
        if (!constraint) {
            return EdgeRectilinearRouter.NO_CONSTRAINT;
        }
        // we have two edges. We can choose, depending on the position of the target shapes, 
        // to place the edges at top and bottom, middle and bottom, or top and middle.
        // this requires to inspect the location of the target edit parts.
        if (srcsize == 2 ) {
            
            Bounds targetLoc = (Bounds) ((Node) ((Edge) getModel()).getTarget()).getLayoutConstraint();
            Bounds sourceLoc = (Bounds) ((Node) ((Edge) getModel()).getSource()).getLayoutConstraint();
            Bounds otherTargetLoc = null;
            for (SequenceEdgeEditPart p : sources) {
                if (p != this) {
                    otherTargetLoc = (Bounds) ((Node) ((Edge) p.getModel()).getTarget()).getLayoutConstraint();
                }
            }
            int topLimit = sourceLoc.getY() + sourceLoc.getHeight() + MapModeUtil.getMapMode(getFigure()).DPtoLP(20);
            int bottomLimit = sourceLoc.getY() - MapModeUtil.getMapMode(getFigure()).DPtoLP(20);
            if (targetLoc.getY() < topLimit && otherTargetLoc.getY() < topLimit) {
                // the two shapes are higher than the gateway.
                // the highest shape has a top constraint, while the other one will have a middle constraint.
                if (index == srcsize -1) {
                    return EdgeRectilinearRouter.CONSTRAINT_MIDDLE;
                }
            } else if (targetLoc.getY() + targetLoc.getHeight()/2 > bottomLimit && 
                    otherTargetLoc.getY() + otherTargetLoc.getHeight()/2 > bottomLimit) {
             // the two shapes are lower than the gateway.
                // the highest shape has a top constraint, while the other one will have a middle constraint.
                if (index == 0) {
                    return EdgeRectilinearRouter.CONSTRAINT_MIDDLE;
                }
            }
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
        return ActivityEditPart.isOrderImportant((IGraphicalEditPart) part);
    }
}
