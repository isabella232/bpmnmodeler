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
import org.eclipse.draw2d.LayoutAnimator;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.TreeSearch;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
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
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderItemsAwareFreeFormLayer;
import org.eclipse.gmf.runtime.diagram.ui.figures.CanonicalShapeCompartmentLayout;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.diagram.edit.policies.BpmnDiagramCanonicalEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.BpmnDiagramItemSemanticEditPolicy;
import org.eclipse.stp.bpmn.figures.GroupFigure;
import org.eclipse.stp.bpmn.policies.BpmnDiagramXYLayoutEditPolicy;
import org.eclipse.stp.bpmn.policies.BpmnDragDropEditPolicy;
import org.eclipse.stp.bpmn.policies.PopupBarEditPolicyEx;

/**
 * @generated
 */
public class BpmnDiagramEditPart extends DiagramEditPart {

    /**
     * @generated NOT copied from superclass, needed for the createFigure method.
     * @author mmostafa
     * PageBreaksLayoutListener Listens to post layout so it can update the page breaks  
     */
    private class PageBreaksLayoutListener extends LayoutListener.Stub {

        public void postLayout(IFigure container) {
            super.postLayout(container);
            updatePageBreaksLocation();
        }
        
        
    }
    
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
     * We also create the figure ourselves to override the way mouse events
     * are propagated, as to avoid that they touch the groups.
     */
    @Override
    protected IFigure createFigure() {
     // Override the containsPoint and findFigureAt methods
        // to treat this layer (Primary Layer) as if it were opaque.

        // This is for the grid layer so that it can be seen beneath the
        // figures.
        IFigure f = new BorderItemsAwareFreeFormLayer() {   
            /* (non-Javadoc)
             * @see org.eclipse.draw2d.Layer#containsPoint(int, int)
             */
            public boolean containsPoint(int x, int y) {
                return getBounds().contains(x, y);
            }

            /* (non-Javadoc)
             * @see org.eclipse.draw2d.Layer#findFigureAt(int, int, org.eclipse.draw2d.TreeSearch)
             */
            public IFigure findFigureAt(int x, int y, TreeSearch search) {
                if (!isEnabled())
                    return null;
                if (!containsPoint(x, y))
                    return null;
                if (search.prune(this))
                    return null;
                IFigure child = findDescendantAtExcluding(x, y, search);
                if (child != null)
                    return child;
                if (search.accept(this))
                    return this;
                return null;
            }

            /* (non-Javadoc)
             * @see org.eclipse.draw2d.Figure#validate()
             * Antoine: not sure that was needed, since the shouldUpdatePageBreakLocation
             * field is never set to true in the super class.
             */
            public void validate() {                
                super.validate();
//                if (shouldUpdatePageBreakLocation){
//                    shouldUpdatePageBreakLocation = false;
//                    updatePageBreaksLocation();
//                }
            }
            
            private Point PRIVATE_POINT = new Point();
            
            /**
             * Skips the groups to keep their mouse events for the compartment, so that
             * the popup toolbar will show up.
             */
            protected IFigure findMouseEventTargetInDescendantsAt(int x, int y) {
                PRIVATE_POINT.setLocation(x, y);
                translateFromParent(PRIVATE_POINT);

                if (!getClientArea(Rectangle.SINGLETON).contains(PRIVATE_POINT))
                    return null;

                IFigure fig;
                for (int i = getChildren().size(); i > 0;) {
                    i--;
                    fig = (IFigure)getChildren().get(i);
                    if (fig.isVisible() && fig.isEnabled()) {
                        if (fig.containsPoint(PRIVATE_POINT.x, PRIVATE_POINT.y)) {
                            fig = fig.findMouseEventTargetAt(PRIVATE_POINT.x, PRIVATE_POINT.y);
                            if (fig instanceof GroupFigure || (fig != null && fig.getParent() instanceof GroupFigure)) {
                                continue; // the mouse events are redirected to us if the target would be a group.
                            }
                            return fig;
                        }
                    }
                }
                return null;
            }
        };
        // the super class uses a FreeFormLayoutEx as its layout, but we don't have access to it.
        // so we settle for its subclass, that we feed with an empty map.
        f.setLayoutManager(new CanonicalShapeCompartmentLayout(Collections.emptyMap()));
        f.addLayoutListener(LayoutAnimator.getDefault());
        f.addLayoutListener(new PageBreaksLayoutListener());
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
