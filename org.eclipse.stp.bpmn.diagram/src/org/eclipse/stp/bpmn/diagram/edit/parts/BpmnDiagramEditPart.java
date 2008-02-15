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
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToGuides;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramDragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.diagram.edit.policies.BpmnDiagramCanonicalEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.BpmnDiagramItemSemanticEditPolicy;
import org.eclipse.stp.bpmn.policies.BpmnDiagramXYLayoutEditPolicy;
import org.eclipse.stp.bpmn.policies.BpmnDragDropEditPolicy;
import org.eclipse.stp.bpmn.policies.PopupBarEditPolicyEx;

/**
 * @generated
 */
public class BpmnDiagramEditPart extends DiagramEditPart {

    /**
     * @generated
     */
    public static String MODEL_ID = "Bpmn"; //$NON-NLS-1$

    /**
     * @generated
     */
    public static final int VISUAL_ID = 79;

    /**
     * @generated
     */
    public BpmnDiagramEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    protected void createDefaultEditPoliciesGen() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new BpmnDiagramItemSemanticEditPolicy());
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new BpmnDiagramCanonicalEditPolicy());
    }

    /**
     * @notgenerated
     */
    protected void createDefaultEditPolicies() {
        createDefaultEditPoliciesGen();
        installEditPolicy(EditPolicyRoles.POPUPBAR_ROLE,
                new PopupBarEditPolicyEx());
        // adding drag and drop edit policy
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
                new DiagramDragDropEditPolicy());
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
                new BpmnDragDropEditPolicy(this));
        installEditPolicy(EditPolicy.LAYOUT_ROLE,
                new BpmnDiagramXYLayoutEditPolicy());
    }

    /**
     * @generated NOT: add insets to make sure there are white spaces around 
     * the pools.
     */
    @Override
    protected IFigure createFigure() {
        IFigure f = super.createFigure();
        f.setBorder(new MarginBorder(new Insets(16, 16, 80, 16)));
        // f.addLayoutListener(new ArrangeLayoutListener());
        return f;

    }

    /**
     * Taken from logic example adn comments on bug 78462
     * 
     * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
     */
    public Object getAdapter(Class adapter) {
        if (adapter == SnapToHelper.class) {
            List<SnapToHelper> snapStrategies = new ArrayList<SnapToHelper>();
            Boolean val = (Boolean) getViewer().getProperty(
                    RulerProvider.PROPERTY_RULER_VISIBILITY);
            if (val != null && val.booleanValue())
                snapStrategies.add(new SnapToGuides(this));
            val = (Boolean) getViewer().getProperty(
                    SnapToGeometry.PROPERTY_SNAP_ENABLED);
            if (val != null && val.booleanValue())
                snapStrategies.add(new SnapToGeometry(this));
            val = (Boolean) getViewer().getProperty(
                    SnapToGrid.PROPERTY_GRID_ENABLED);
            if (val != null && val.booleanValue())
                snapStrategies.add(new SnapToGrid(this));

            if (snapStrategies.size() == 0)
                return null;
            if (snapStrategies.size() == 1)
                return snapStrategies.get(0);

            SnapToHelper ss[] = new SnapToHelper[snapStrategies.size()];
            for (int i = 0; i < snapStrategies.size(); i++)
                ss[i] = snapStrategies.get(i);
            return new CompoundSnapToHelper(ss);
        }
        return super.getAdapter(adapter);
    }

    /**
     * @notgenerated
     */
    @Override
    protected void handleNotificationEvent(Notification event) {
        super.handleNotificationEvent(event);
        if (event.getEventType() == Notification.REMOVE) {
            Object obj = event.getOldValue();
            if (obj instanceof Pool) {
                ChangeBoundsRequest req = new ChangeBoundsRequest(
                        RequestConstants.REQ_MOVE_CHILDREN);
                req.setEditParts(Collections.EMPTY_LIST);
                Command command = getCommand(req);
                if (command != null) {
                    command.execute();
                }
            }
        } else if (event.getEventType() == Notification.ADD) {
            Object obj = event.getOldValue();
            if (obj instanceof Pool) {
                handleMajorSemanticChange();
            }
        } 
    }
    
    /**
     * @notgenerated Makes sure the ConnectionLayerExEx that registers the
     *  special routers is well set.
     *  If we do this only for the configureGraphicalEditViewer, when 
     *  the diagram is printed it does not install it correctly
     */
    @Override
    public void setParent(EditPart parent) {
    	if (parent != null) {
    		EditPart root = parent instanceof DiagramRootEditPart ?
    				((DiagramRootEditPart)parent) : parent.getRoot();
    				if (root != null && root instanceof DiagramRootEditPart) {
    					BpmnEditPartFactory.setupConnectionLayerExEx(
    							(DiagramRootEditPart)root);
    				}
    	}
    	super.setParent(parent);
    }

//    @Override
//    public Command getCommand(Request _request) {
//        System.err.println("hello " + _request);
//        return super.getCommand(_request);
//    }
    
    
    @Override
    protected List getModelChildren() {
        Object model = getModel();
        if (model != null && model instanceof View) {
            List list = ((View) model).getVisibleChildren();
            List res = new ArrayList();
            List artifacts = new ArrayList();
            for (Object object : list) {
                Node node = (Node) object;
               if (node.getType().equals(Integer.toString(Group2EditPart.VISUAL_ID)) ||
                       node.getType().equals(Integer.toString(DataObject2EditPart.VISUAL_ID)) ||
                       node.getType().equals(Integer.toString(TextAnnotation2EditPart.VISUAL_ID))) {
                    artifacts.add(object);
                } else {
                    res.add(object);
                }
            }
            res.addAll(artifacts);
            return res;
        }
        return Collections.EMPTY_LIST;
    }
}
