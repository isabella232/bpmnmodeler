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
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.LineSeg;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.RoutingStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.diagram.edit.policies.SequenceEdgeItemSemanticEditPolicy;
import org.eclipse.stp.bpmn.figures.ConnectionLayerExEx;
import org.eclipse.stp.bpmn.figures.ConnectionUtils;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchor;
import org.eclipse.stp.bpmn.policies.BpmnDragDropEditPolicy;
import org.eclipse.stp.bpmn.policies.ConnectionEndPointEditPolicyEx;
import org.eclipse.stp.bpmn.policies.OpenFileEditPolicy;
import org.eclipse.stp.bpmn.tools.SelectConnectionEditPartTrackerEx;

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
                        rectilinearRouter = cLayerEx.getBpmnSequenceEdgeRectilinearRouter();
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
                //it seems that for example in the case of EDGE-1142
                //the model ends up being the BpmnDiagram!
//                 System.err.println("weird! " + getPrimaryView() + " " + getModel());
                String connectionType =
                    isSource && super.getSource() instanceof Activity2EditPart ?
                            String.valueOf(VISUAL_ID) + "-subprocessBorder" : //$NON-NLS-1$
                                String.valueOf(VISUAL_ID);
                modelAware.setConnectionType(isSource,
                        connectionType, 0, 1);
            } else {
                SequenceEdge seqEdge = (SequenceEdge)getPrimaryView().getElement();
                int ind = seqs.indexOf(seqEdge);
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
    
}
