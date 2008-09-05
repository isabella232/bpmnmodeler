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
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.DirectionType;
import org.eclipse.stp.bpmn.diagram.edit.policies.AssociationItemSemanticEditPolicy;
import org.eclipse.stp.bpmn.figures.activities.ActivityPainter;
import org.eclipse.stp.bpmn.policies.BpmnDragDropEditPolicy;
import org.eclipse.stp.bpmn.policies.OpenFileEditPolicy;

/**
 * @generated
 */
public class AssociationEditPart extends ConnectionNodeEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 3003;

    /**
     * @generated
     */
    public AssociationEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    protected void createDefaultEditPoliciesGen() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new AssociationItemSemanticEditPolicy());
    }

    /**
     * @generated NOT
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
     * Creates figure for this edit part.
     * 
     * Body of this method does not depend on settings in generation model
     * so you may safely remove <i>generated</i> tag and modify it.
     * 
     * @generated
     */
    protected Connection createConnectionFigure() {
        return new ConnectionAssociationFigure();
    }
    
    /**
     * Ability to override the OpenFileEditPolicy.
     * @generated NOT
     */
    protected OpenFileEditPolicy createOpenFileEditPolicy() {
        return new OpenFileEditPolicy();
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
     * Synchronizes the shape with the activityType
     * 
     * @generated NOT
     * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart
     *      #handlePropertyChangeEvent(java.beans.PropertyChangeEvent)
     */
    protected void handleNotificationEvent(Notification notification) {
        if (notification.getEventType() == Notification.SET ||
                notification.getEventType() == Notification.UNSET) {
            if (BpmnPackage.eINSTANCE.getAssociation_Direction().equals(
                    notification.getFeature())) {
                DirectionType newActivityType = (DirectionType) notification
                        .getNewValue();
                ConnectionAssociationFigure conn =
                    (ConnectionAssociationFigure)getFigure();
                conn.updateDirectionType(newActivityType.getValue());
                conn.repaint();
            }
        }
        
        super.handleNotificationEvent(notification);
    }
    
    /**
     * @notgenerated smaller dashes than the messages
     */
    private static final int[] DASHES = { 1, 2 };

    /**
     * @generated
     */
    public class ConnectionAssociationFigure extends
            org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx {

        /**
         * @generated
         */
        public ConnectionAssociationFigure() {

           // this.setLineStyle(org.eclipse.draw2d.Graphics.LINE_DOT);
            this.setLineStyle(org.eclipse.draw2d.Graphics.LINE_CUSTOM);
            this.setLineDash(DASHES);
            //TODO: add the event notifyer so that the decoration paints itself when the 
            //direction type of the semantic element is changed.
            setSourceDecoration(createSourceDecoration());
            setTargetDecoration(createTargetDecoration());
        }
        
        /**
         * @param newDirectionType
         * @generated not
         */
        public void updateDirectionType(int newDirectionType) {
            ((org.eclipse.stp.bpmn.figures.AssociationPolylineSourceDecoration)
                    super.getSourceDecoration()).updateDirectionType(newDirectionType);
            ((org.eclipse.stp.bpmn.figures.AssociationPolylineTargetDecoration)
                    super.getTargetDecoration()).updateDirectionType(newDirectionType);
        }
        
        /**
         * @generated not pass the model element
         */
        private org.eclipse.stp.bpmn.figures.AssociationPolylineSourceDecoration createSourceDecoration() {
            EObject eo = AssociationEditPart.this.resolveSemanticElement();
            if (eo == null || !(eo instanceof Association)) {
                return  new org.eclipse.stp.bpmn.figures.AssociationPolylineSourceDecoration(DirectionType.NONE);
            }
            Association assoc = (Association)eo;
            int dir = assoc.getDirection().getValue();
            org.eclipse.stp.bpmn.figures.AssociationPolylineSourceDecoration df =
                new org.eclipse.stp.bpmn.figures.AssociationPolylineSourceDecoration(dir);
            return df;
        }

        /**
         * @generated not pass the model element
         */
        private org.eclipse.stp.bpmn.figures.AssociationPolylineTargetDecoration createTargetDecoration() {
            EObject eo = AssociationEditPart.this.resolveSemanticElement();
            if (eo == null || !(eo instanceof Association)) {
                return  new org.eclipse.stp.bpmn.figures.AssociationPolylineTargetDecoration(DirectionType.NONE);
            }
            Association assoc = (Association)eo;
            int dir = assoc == null ? DirectionType.NONE : assoc.getDirection().getValue();
            org.eclipse.stp.bpmn.figures.AssociationPolylineTargetDecoration df =
                new org.eclipse.stp.bpmn.figures.AssociationPolylineTargetDecoration(dir);
            return df;
        }
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
     * Sets the connection anchor with extra parameters if it is relevant to
     * do so.
     * @param ca The connection anchor to eventually update
     */
    protected void updateConnectionAnchor(ConnectionAnchor ca, boolean isSource) {
        
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
        return null;
    }

    
}
