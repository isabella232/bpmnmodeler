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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.draw2d.ui.figures.BaseSlidableAnchor;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.IdentityAnchor;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Routing;
import org.eclipse.gmf.runtime.notation.RoutingStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.MessageVertex;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.diagram.edit.policies.MessagingEdgeItemSemanticEditPolicy;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.stp.bpmn.figures.ConnectionLayerExEx;
import org.eclipse.stp.bpmn.figures.ConnectionUtils;
import org.eclipse.stp.bpmn.figures.MessagePolylineTargetDecoration;
import org.eclipse.stp.bpmn.figures.activities.ActivityPainter;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchor;
import org.eclipse.stp.bpmn.policies.BpmnDragDropEditPolicy;
import org.eclipse.stp.bpmn.policies.OpenFileEditPolicy;

/**
 * @generated
 */
public class MessagingEdgeEditPart extends ConnectionNodeEditPart {

    /**
     * @generated not
     */
    private static final int[] DASHES = { 7, 5 };
    
    /**
     * @generated
     */
    public static final int VISUAL_ID = 3002;

    /**
     * @notgenerated
     */
    private ConnectionRouter rectilinearRouter = null;

    /**
     * Edit part used to create source anchor
     */
    protected EditPart srcConnEditPart = null;

    /**
     * Edit part used to create target anchor
     */
    protected EditPart trgConnEditPart = null;

    /**
     * @generated
     */
    public MessagingEdgeEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    protected void createDefaultEditPoliciesGen() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new MessagingEdgeItemSemanticEditPolicy());
    }
    
    /**
     * @notgenerated
     */
    protected void createDefaultEditPolicies() {
        createDefaultEditPoliciesGen();
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
     * Creates figure for this edit part.
     * 
     * Body of this method does not depend on settings in generation model
     * so you may safely remove <i>generated</i> tag and modify it.
     * 
     * @generated
     */
    protected Connection createConnectionFigure() {
        return new ConnectionMessageFigure();
    }

    /**
     * @notgenerated
     */
    public class ConnectionMessageFigure extends
            org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx {

        /**
         * @notgenerated
         */
        private static final int DELTA_ANCHOR_SIZE = 10;

        /**
         * @notgenerated
         */
        private boolean routerIsRectilinear = true;
        
                
        @Override
        public void paintFigure(Graphics graphics) {
            graphics.setAlpha(ActivityPainter.getMessagingEdgeTransparency());
            super.paintFigure(graphics);
        }

        /**
         * Added the DASHES and the routing constraint
         * 
         * @notgenerated
         */
        public ConnectionMessageFigure() {
            this.setLineStyle(org.eclipse.draw2d.Graphics.LINE_CUSTOM);
            this.setLineDash(DASHES);
            this.setForegroundColor(
            		DiagramColorRegistry.getInstance().getColor(
                            PreferenceConverter.getColor(BpmnDiagramEditorPlugin.PREF_STORE,
                                    BpmnDiagramPreferenceInitializer.PREF_MSG_LINE_COLOR)));
            setSourceDecoration(createSourceDecoration());
            setTargetDecoration(createTargetDecoration());
        }

        /**
         * @generated
         */
        private org.eclipse.stp.bpmn.figures.MessagePolylineSourceDecoration createSourceDecoration() {
            org.eclipse.stp.bpmn.figures.MessagePolylineSourceDecoration df = new org.eclipse.stp.bpmn.figures.MessagePolylineSourceDecoration();

            return df;
        }

        /**
         * @generated
         */
        private org.eclipse.stp.bpmn.figures.MessagePolylineTargetDecoration createTargetDecoration() {
            org.eclipse.stp.bpmn.figures.MessagePolylineTargetDecoration df =
                new org.eclipse.stp.bpmn.figures.MessagePolylineTargetDecoration(
                        true);
            
            return df;
        }

        /**
         * @notgenerated
         * 
         * @see org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx#getSmoothPoints()
         */
        @Override
        public PointList getSmoothPoints() {
            PointList smoothPoints;
            if (!routerIsRectilinear) {
                smoothPoints = super.getSmoothPoints();
            } else {
                smoothPoints =
                   ConnectionUtils.getRoundedRectilinearSmoothPoints(
                         true,
                         getPoints(), getSmoothness());
            }
            return smoothPoints;
        }

        /**
         * @generated NOT
         */
        @Override
        public void setVisible(boolean visible) {
            if (isReparented()) {
                visible = true;
            }
            super.setVisible(visible);
        }
        
        /**
         * 
         * @param isRoundStyle true if connected to a task, a pool a sub-process
         * false for round shapes (events and co)
         * @generated NOT
         */
        public void setTargetDecorationStyle(boolean isRoundStyle) {
            ((MessagePolylineTargetDecoration)getTargetDecoration()).setStyle(isRoundStyle);
        }

    }
    /**
     * @notgenerated
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
    protected void installRouter() {
        ConnectionLayer cLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
        
        RoutingStyle style = (RoutingStyle) ((View) getModel())
                .getStyle(NotationPackage.eINSTANCE.getRoutingStyle());

        
        if (style != null && cLayer instanceof ConnectionLayerExEx) {
            ConnectionLayerExEx cLayerEx = (ConnectionLayerExEx) cLayer;
            if (Routing.RECTILINEAR_LITERAL == style.getRouting()) {
                if (rectilinearRouter == null) {
                    rectilinearRouter = cLayerEx.getBpmnMessagingEdgeRectilinearRouter();
                }
                getConnectionFigure().setConnectionRouter(rectilinearRouter);
                ((ConnectionMessageFigure) getFigure()).routerIsRectilinear = true;
                refreshRouterChange();
                return;
            }
        } else {
           throw new IllegalArgumentException("Invalid layer: " + cLayer); //$NON-NLS-1$
        }
        ((ConnectionMessageFigure) getFigure()).routerIsRectilinear = false;
        super.installRouter();
    }
    
    /**
     * Returns top-most collapsed parent subprocess or specified editpart in
     * case if specified edit part has no parent subprocesses or all parent
     * subprocesses are expanded.
     * 
     * @notgenerated
     * @param editPart
     *            connection's source or target edit part
     * @return top-most collapsed parent subprocess.
     */
    private static EditPart getRealEditPart(EditPart editPart) {
        EditPart res = editPart;
        EditPart parent = editPart.getParent();
        while (!(parent instanceof PoolPoolCompartmentEditPart)) {
            if (parent instanceof SubProcessSubProcessBodyCompartmentEditPart) {
                boolean isCollapsed = ((Boolean) ((SubProcessSubProcessBodyCompartmentEditPart) parent)
                        .getStructuralFeatureValue(NotationPackage.eINSTANCE
                                .getDrawerStyle_Collapsed())).booleanValue();
                if (isCollapsed) {
                    res = parent.getParent();
                }
            }
            parent = parent.getParent();
        }
        return res;
    }

    /**
     * Overrides default implementation. Returns source anchor on top-most
     * collapsed parent subprocess.
     * 
     * @notgenerated
     */
    @Override
    protected ConnectionAnchor getSourceConnectionAnchor() {
        srcConnEditPart = null;
        EditPart editPart = getSource();
        if (editPart instanceof PoolEditPart) {
            return super.getSourceConnectionAnchor();
        }
        if (editPart != null) {
            EditPart newSrcEditPart = getRealEditPart(editPart);
            if (newSrcEditPart != editPart) {
                // now check target
                EditPart targetEditPart = getTarget();
                if (targetEditPart != null) {
                    targetEditPart = getRealEditPart(targetEditPart);
                }
                if (newSrcEditPart != targetEditPart) {
                    srcConnEditPart = newSrcEditPart;
                    ConnectionAnchor ca = ((NodeEditPart) srcConnEditPart)
                            .getSourceConnectionAnchor(this);
                    updateConnectionAnchor(ca, true);
                    return ca;
                } else {
                    // don't apply changes becase it will cause self-connection
                    srcConnEditPart = editPart;
                }
            }
        }
        ConnectionAnchor ca = super.getSourceConnectionAnchor();
        updateConnectionAnchor(ca, true);
        return ca;
    }
    
    /**
     * Sets the connection anchor with extra parameters if it is an IModelAwareConnectionAnchor
     * @param ca The connection anchor to eventually update
     * @generated NOT
     */
    protected void updateConnectionAnchor(
            final ConnectionAnchor ca, final boolean isSource) {
        if (ca instanceof IModelAwareAnchor) {
            IModelAwareAnchor modelAware = (IModelAwareAnchor)ca;
            MessagingEdge msgEdge = (MessagingEdge)getPrimaryView().getElement();
            
            EditPart part = isSource ? srcConnEditPart : trgConnEditPart;
            Iterator messages = null;
            int count = 0;
            if (part instanceof SubProcessEditPart) {
                List messagesList = new LinkedList();
                    collectMessagingEdges(((Graph) ((IGraphicalEditPart) part).
                resolveSemanticElement()), messagesList);
                count = messagesList.size();
                messages = messagesList.iterator();
                
                if (!isSource) {
                    //update the target decoration to the TaskStyle.
                    ((ConnectionMessageFigure)getFigure()).setTargetDecorationStyle(true);
                }
                
            } else {
                Activity connectedActivity = isSource ?
                        ((Activity)((GraphicalEditPart) super.getSource())
                                .resolveSemanticElement()) :
                        ((Activity)((GraphicalEditPart) super.getTarget())
                                .resolveSemanticElement());
                
                messages = connectedActivity.getOrderedMessages().valueListIterator();
                count = connectedActivity.getOrderedMessages().size();
                
                if (!isSource) {
                    //update the target decoration to the TaskStyle.
                    ((ConnectionMessageFigure)getFigure()).setTargetDecorationStyle(
                            connectedActivity.getActivityType()
                                .getLiteral().startsWith("Event"));//$NON-NLS-1$
                }
                
            }
            //int ind = messages.indexOf(msgEdge);
            int ind = 0;
            for (; messages.hasNext(); ) {
                MessagingEdge entry = (MessagingEdge) messages.next();
                if (msgEdge.equals(entry)) {
                    break;
                }
                ind++;
            }
            if (ind == count) {
                ind= -1;
            }
            modelAware.setConnectionType(isSource,
                    String.valueOf(VISUAL_ID), ind, count);
        }
    }
    
    protected void collectMessagingEdges(Graph container, List msgEdges) {
        for (Object v : container.getVertices()) {
            if (v instanceof Activity) {
                for (Object msgEntry : ((Activity) v).getOrderedMessages()) {
                    msgEdges.add(((Entry) msgEntry).getValue());
                }
            }
            if (v instanceof SubProcess) {
                collectMessagingEdges((Graph) v, msgEdges);
            }
        }
    }

    @Override
    /**
     * Overrides default implementation. Returns target anchor on top-most
     * collapsed parent subprocess.
     * 
     * @notgenerated
     */
    protected ConnectionAnchor getTargetConnectionAnchor() {
        trgConnEditPart = null;
        EditPart editPart = getTarget();
        if (editPart instanceof PoolEditPart) {
            return super.getTargetConnectionAnchor();
        }
        if (editPart != null) {
            EditPart newTargetEditPart = getRealEditPart(editPart);
            if (newTargetEditPart != editPart) {
                // now check source
                EditPart srcEditPart = getSource();
                if (srcEditPart != null) {
                    srcEditPart = getRealEditPart(srcEditPart);
                }
                if (newTargetEditPart != srcEditPart) {
                    trgConnEditPart = newTargetEditPart;
                    ConnectionAnchor ca = ((NodeEditPart) trgConnEditPart)
                            .getTargetConnectionAnchor(this);
                    updateConnectionAnchor(ca, false);
                    return ca;
                } else {
                    // don't apply changes because it will cause self-connection
                    trgConnEditPart = editPart;
                }
            }
        }
        ConnectionAnchor ca = super.getTargetConnectionAnchor();
        updateConnectionAnchor(ca, false);
        return ca;
    }

    /**
     * Checkes whether this edge has reparented source or target.
     * 
     * @notgenerated
     * @return <code>true</code> in case if source or taget of this connecion
     *         were reparented, <code>false</code> otherwise.
     */
    private boolean isReparented() {
        return (srcConnEditPart != null && srcConnEditPart != this.getSource())
                || (trgConnEditPart != null && trgConnEditPart != getTarget());
    }

    /**
     * mpeleshchyshyn: This handles the changes in the order of the messages
     * to be reflected in the UI.
     * @notgenerated
     */
    @Override
    protected void handleNotificationEvent(Notification notification) {
        Object feature = notification.getFeature();
        if (feature instanceof EAttribute && "anchor" //$NON-NLS-1$
                .equals(((EAttribute) feature).getDefaultValueLiteral())) {
            String newValue = notification.getNewStringValue();
            if (newValue != null) {
                PrecisionPoint p = BaseSlidableAnchor
                        .parseTerminalString(newValue);
                IdentityAnchor notifier = (IdentityAnchor) notification
                        .getNotifier();
                Edge connection = (Edge) notifier.eContainer();
                MessageVertex activity;
                boolean isSource;
                if (connection.getSourceAnchor() == notifier) {
                    isSource = true;
                    activity = (MessageVertex) connection.getSource()
                            .getElement();
                } else {
                    isSource = false;
                    activity = (MessageVertex) connection.getTarget()
                            .getElement();
                }
                FeatureMap messages = activity.getOrderedMessages();
                int connCount = messages.size();
                int newIdx = -1;
                for (int i = 0; i < connCount; i++) {
                    if (p.preciseX < 1.0 / connCount * (i + 1)) {
                        newIdx = i;
                        break;
                    }
                }
                if (newIdx != -1) {
                    //get the old index:
                    int oldInd = 0;
                    for (Iterator<FeatureMap.Entry> it = messages.iterator(); it.hasNext();) {
                        FeatureMap.Entry entry = it.next();
                        if (entry.getValue() == connection.getElement()) {
                            break;
                        }
                        oldInd++;
                    }
                    if (oldInd < messages.size()) {
                        messages.move(newIdx, oldInd);
                    }
                    EditPart activityEditPart;
                    if (isSource) {
                        activityEditPart = getSource();
                    } else {
                        activityEditPart = getTarget();
                    }
                    activityEditPart.refresh();
                }
            }
        }
        super.handleNotificationEvent(notification);
    }
    
    
}
