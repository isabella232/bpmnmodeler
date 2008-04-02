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

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutAnimator;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToGuides;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.gef.tools.DeselectAllTracker;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.policies.PoolPoolCompartmentCanonicalEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.PoolPoolCompartmentItemSemanticEditPolicy;
import org.eclipse.stp.bpmn.figures.FixedOneLineBorder;
import org.eclipse.stp.bpmn.figures.PoolPoolCompartmentFigure;
import org.eclipse.stp.bpmn.policies.BpmnDragDropEditPolicy;
import org.eclipse.stp.bpmn.policies.ContainerNodeEditPolicyEx;
import org.eclipse.stp.bpmn.policies.NonResizableCollapsibleCompartmentEditPolicy;
import org.eclipse.stp.bpmn.policies.OpenFileEditPolicy;
import org.eclipse.stp.bpmn.policies.PoolPoolCompartmentXYLayoutEditPolicy;
import org.eclipse.stp.bpmn.policies.PopupBarEditPolicyEx;
import org.eclipse.stp.bpmn.policies.SubProcessCreationEditPolicy;
import org.eclipse.stp.bpmn.tools.RubberbandDragTrackerForBPMN;
import org.eclipse.stp.bpmn.tools.TaskDragHelper;
import org.eclipse.swt.graphics.Color;

/**
 * @generated
 */
public class PoolPoolCompartmentEditPart extends ShapeCompartmentEditPart {
    /**
     * @notgenerated
     */
    public static final Insets INSETS = new Insets(5, 6, 5, 5);

    /**
     * @notgenerated
     */
    public static final Color POOLCOMPARTMENTFIGURE_BORDER = new Color(null,
            169, 169, 169);

    /**
     * @generated
     */
    public static final int VISUAL_ID = 5001;

    /**
     * @generated
     */
    public PoolPoolCompartmentEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    public String getCompartmentNameGen() {
        return "PoolCompartment"; //$NON-NLS-1$
    }

    /**
     * @generated NOT
     */
    public String getCompartmentName() {
        EObject bpmnObj = getPrimaryView().getElement();
        if (bpmnObj instanceof Pool) {
            Pool a = (Pool) bpmnObj;
            if (a.getName() != null) {
                if (a.getName().toLowerCase().indexOf(BpmnDiagramMessages.PoolPoolCompartmentEditPart_default_pool_name_lowercase) == -1) {
                    return BpmnDiagramMessages.bind(BpmnDiagramMessages.PoolPoolCompartmentEditPart_pool_prefix, a.getName());
                }
                return a.getName();
            }
        }
        return BpmnDiagramMessages.PoolPoolCompartmentEditPart_pool_default_name;
    }

    /**
     * @generated
     */
    public IFigure createFigureGen() {
        ResizableCompartmentFigure result = (ResizableCompartmentFigure) super
                .createFigure();
        result.setTitleVisibility(false);
        return result;
    }

    /**
     * Hugues: a couple of details to take care of the vertical orientation. and
     * the background color that is not the same for the compartment than it is
     * for the pool name label.
     * 
     * @notgenerated
     */
    public IFigure createFigure() {
//        ResizableCompartmentFigure result =
            //(ResizableCompartmentFigure) this
             //   .createFigureGen();
        PoolPoolCompartmentFigure result = new PoolPoolCompartmentFigure(getCompartmentName(), getMapMode());
        result.getContentPane().setLayoutManager(getLayoutManager());
        result.getContentPane().addLayoutListener(LayoutAnimator.getDefault());
        result.setTitleVisibility(false);
        result.setOpaque(false);

        FixedOneLineBorder border = new FixedOneLineBorder(
                POOLCOMPARTMENTFIGURE_BORDER, 1, PositionConstants.LEFT);
        result.setBorder(border);
        return result;
    }
    
    /**
     * @generated
     */
    protected void createDefaultEditPoliciesGen() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new PoolPoolCompartmentItemSemanticEditPolicy());
        installEditPolicy(EditPolicyRoles.CREATION_ROLE,
                new CreationEditPolicy());
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
                new DragDropEditPolicy());
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new PoolPoolCompartmentCanonicalEditPolicy());
    }

    /**
     * @notgenerated
     */
    protected void createDefaultEditPolicies() {
        createDefaultEditPoliciesGen();
        // the following is added:
        installEditPolicy(EditPolicyRoles.POPUPBAR_ROLE,
                new PopupBarEditPolicyEx());

        removeEditPolicy(EditPolicyRoles.CREATION_ROLE);
        installEditPolicy(EditPolicyRoles.CREATION_ROLE,
                new SubProcessCreationEditPolicy());

        NonResizableCollapsibleCompartmentEditPolicy dragPolicy =
            new NonResizableCollapsibleCompartmentEditPolicy();
        dragPolicy.setDragAllowed(false);
        installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, dragPolicy);

        // //the compartment is not selectable anymore.
        // //it looks like none of the containereidtpolicy gets called
        // //because of that.
        // //we use a mousedelegating edit policy to still call the proper
        // //edit policy on the compartment.
        // installEditPolicy(EditPolicy.CONTAINER_ROLE, editPolicy)
        removeEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE);
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new ContainerNodeEditPolicyEx());
        
         //takes care of the lanes
        installEditPolicy(EditPolicy.LAYOUT_ROLE,
                new PoolPoolCompartmentXYLayoutEditPolicy());

        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, 
                new BpmnDragDropEditPolicy(this));
        // adding an open edit policy
        installEditPolicy(EditPolicyRoles.OPEN_ROLE,
        		new OpenFileEditPolicy());
    }

    /**
     * @generated
     */
    protected void setRatio(Double ratio) {
        if (getFigure().getParent().getLayoutManager() instanceof ConstrainedToolbarLayout) {
            super.setRatio(ratio);
        }
    }

    /**
     * @notgenerated
     */
    protected void handlePropertyChangeEvent(PropertyChangeEvent event) {
        super.handlePropertyChangeEvent(event);
        TaskDragHelper.handlePropertyChangeEvent(event, this);
    }

    @Override
    /**
     * @notgenerated
     */
    protected List getModelChildren() {
        Object model = getModel();
        if (model != null && model instanceof View) {
            List list = ((View) model).getVisibleChildren();
            List res = new ArrayList();
            List groups = new ArrayList();
            for (Object object : list) {
                Node node = (Node) object;
                if (node.getType().equals(
                        Integer.toString(LaneEditPart.VISUAL_ID))) {
                    res.add(0, object);
                } else if (node.getType().equals(Integer.toString(GroupEditPart.VISUAL_ID))) {
                    groups.add(object);
                } else {
                    res.add(object);
                }
            }
            res.addAll(groups);
            return res;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Overrides the default: always selectable to prevent being able to move
     * the pools by selecting their compartments. (EDGE-1024)
     */
    public boolean isSelectable() {
        return true;
    }

    /**
     * @notgenerated this makes sure that visually the primary shape is the one
     *               that has the focus. It is the same code than for all the
     *               container compartments (mpeleshchyshyn)
     */
    @Override
    public void setSelected(int value) {
        if (value == SELECTED_PRIMARY) {
            getViewer().select(getParent());
            return;
        }
        super.setSelected(value);
    }

    /**
     * @notgenerated Taken from logic example adn comments on bug 78462
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

    @Override
    protected void setCollapsed(boolean collapsed, boolean animate) {
        super.setCollapsed(collapsed, animate);
        //tell the vertical label:
        PoolEditPart poolEditPart = (PoolEditPart)getParent();
        PoolNameEditPart nameEditPart = (PoolNameEditPart)
            poolEditPart.getPrimaryChildEditPart();
        nameEditPart.setCollapsed(collapsed, animate);
    }
    
    
    /**
     * @generated NOT
     * -handles the collapse expand notification.
     * -makes sure the lanes will fit with all the available space on lane deletion
     */
    @Override
    protected void handleNotificationEvent(Notification event) {
        Object feature = event.getFeature();
        if (NotationPackage.eINSTANCE.getDrawerStyle_Collapsed()
                .equals(feature)) {
            handleCollapseExpand();
        } else if (event.getEventType() == Notification.REMOVE || 
                        event.getEventType() == Notification.REMOVE_MANY) {
            boolean isLane = false;
            if (event.getOldValue() instanceof List) {
                for (Object elt : (List) event.getOldValue()) {
                    if (elt instanceof Node && 
                            Integer.toString(LaneEditPart.VISUAL_ID).equals(((Node) elt).getType())) {
                        isLane = true;
                        break;
                    }
                }
            } else {
                isLane = event.getOldValue() instanceof Node && 
                    Integer.toString(LaneEditPart.VISUAL_ID).equals(((Node) event.getOldValue()).getType());
            }
            if (isLane) {
                ChangeBoundsRequest dummyRequest = new ChangeBoundsRequest(RequestConstants.REQ_RESIZE_CHILDREN);
                dummyRequest.setEditParts(Collections.EMPTY_LIST);
                dummyRequest.setMoveDelta(Point.SINGLETON.getCopy());
                dummyRequest.setSizeDelta(Dimension.SINGLETON.getCopy());
                Command co = getCommand(dummyRequest);
                if (co != null) {
                    co.execute();
                }
            }
        }
        super.handleNotificationEvent(event);
    }

    private final String POOL_ANNOTATION_SOURCE = "Pool_Annotation"; //$NON-NLS-1$
    private final String WIDTH = "width"; //$NON-NLS-1$
    private final String HEIGHT = "height"; //$NON-NLS-1$

    /**
     * Handles collapse/expands events. Changes the size of the subprocess and
     * layouts sibling figure.
     * 
     */
    private void handleCollapseExpand() {
        boolean isCollapsed = ((Boolean) getStructuralFeatureValue(NotationPackage.eINSTANCE
                .getDrawerStyle_Collapsed())).booleanValue();

        // Retrieve current bounds from the model
        PoolEditPart poolEditPart = (PoolEditPart) getParent();
        int oldX = ((Integer) poolEditPart
                .getStructuralFeatureValue(NotationPackage.eINSTANCE
                        .getLocation_X())).intValue();
        int oldY = ((Integer) poolEditPart
                .getStructuralFeatureValue(NotationPackage.eINSTANCE
                        .getLocation_Y())).intValue();
        int oldHeight = ((Integer) poolEditPart
                .getStructuralFeatureValue(NotationPackage.eINSTANCE
                        .getSize_Height())).intValue();
//        oldHeight = poolEditPart.getFigure().getBounds().height;
        int oldWidth = ((Integer) poolEditPart
                .getStructuralFeatureValue(NotationPackage.eINSTANCE
                        .getSize_Width())).intValue();
        if (oldHeight == -1) {
            oldHeight = PoolEditPart.POOL_HEIGHT;
        }

        // Retrieve previously saved bounds from the diagram
        View view = poolEditPart.getPrimaryView();
        EAnnotation annotation = view
                .getEAnnotation(POOL_ANNOTATION_SOURCE);

        if (annotation == null) {
            annotation = EcoreFactory.eINSTANCE.createEAnnotation();
            annotation.setSource(POOL_ANNOTATION_SOURCE);
            view.getEAnnotations().add(annotation);
        }
        EMap details = annotation.getDetails();
        // obtain prevoius width and height from annotation
        String height = (String) details.get(HEIGHT);

        // Calculate new bounds
        Dimension minimalDim = new Dimension(32,32);//poolEditPart.calcMinSize();

        int newWidth;
        int newHeight;

        if (height == null) {
            // no previous record in annotation
            if (isCollapsed) {
                newWidth = Math.max(oldWidth,
                        minimalDim.width);
                newHeight = Math.max(PoolEditPart.POOL_HEIGHT_COLLAPSED,
                        minimalDim.height);
            } else {
                newWidth = Math.max(oldWidth,
                        minimalDim.width);
                newHeight = Math.max(SubProcessEditPart.EXPANDED_SIZE.height,
                        minimalDim.height);
            }
        } else {
            newWidth = Math.max(minimalDim.width, oldWidth);
            newHeight = Math.max(minimalDim.height, Integer.parseInt(height));
        }

        // Save new bounds in the diagram
//        details.put(WIDTH, Integer.toString(oldWidth));
        details.put(HEIGHT, Integer.toString(oldHeight));
//System.err.println("storing oldHeight=" + oldHeight + " setting " + newHeight);
        // Apply new bounds if something changed
        if (newHeight != oldHeight) {
            Rectangle subprocessBounds = new Rectangle(oldX, oldY, newWidth,
                    newHeight);
            setNewBounds(subprocessBounds, new Dimension(newHeight, oldHeight),
                    isCollapsed);
            if (!isCollapsed) {
                // subProcessEditPart.setChildAdded(true);
                poolEditPart.refresh();
            }
        }
    }

    /**
     * Sets new bounds on the parent shape. TODO: pass through the edit policy.
     * 
     * @param newBounds
     *            the new bounds of subprocess.
     * @param oldSize
     *            old size of subprocess.
     */
    private void setNewBounds(Rectangle newBounds, Dimension oldSize,
            boolean isCollapsed) {
        CompositeCommand compoudCommand = new CompositeCommand(
            BpmnDiagramMessages.PoolPoolCompartmentEditPart_collapse_expand_command_name);
        
        Rectangle newTotalBounds = new Rectangle(newBounds);
        
        ICommand changeThisBoundsCommand = new SetBoundsCommand(
                this.getEditingDomain(), BpmnDiagramMessages.PoolPoolCompartmentEditPart_collapse_expand_command_name,
                this.getParent(), newTotalBounds);
        compoudCommand.add(changeThisBoundsCommand);
        
        PoolEditPart poolEditPart = (PoolEditPart) getParent();
        //also move all shapes that are below this shape to not create some white space
        List siblings = poolEditPart.getParent().getChildren();
        int dy = newBounds.height - oldSize.height;
        
        if (isCollapsed) {
            Rectangle labelBounds = newBounds.getCopy();
            labelBounds.crop(INSETS);
            labelBounds.width--; 
            SetBoundsCommand setBoundsCommandLabel = new SetBoundsCommand(
                    this.getEditingDomain(), BpmnDiagramMessages.PoolPoolCompartmentEditPart_set_bounds_pool_label_command_name,
                    poolEditPart.getPrimaryChildEditPart(),
                    labelBounds);
            compoudCommand.add(setBoundsCommandLabel);
            ((org.eclipse.gef.GraphicalEditPart) poolEditPart.getPrimaryChildEditPart()).
            getFigure().setSize(new Dimension(labelBounds.width, labelBounds.height));
        } else {
            Rectangle labelBounds = newBounds.getCopy();
            labelBounds.crop(INSETS);
            labelBounds.width = 32; 
            SetBoundsCommand setBoundsCommandLabel = new SetBoundsCommand(
                    this.getEditingDomain(), BpmnDiagramMessages.PoolPoolCompartmentEditPart_set_bounds_pool_label_command_name,
                    poolEditPart.getPrimaryChildEditPart(),
                    labelBounds);
            compoudCommand.add(setBoundsCommandLabel);
        }
        
        for (Object sibling : siblings) {
            GraphicalEditPart siblingEditPart = (GraphicalEditPart) sibling;
            if (siblingEditPart != poolEditPart) {
                Rectangle currSiblingBounds = siblingEditPart.getFigure()
                        .getBounds().getCopy();
                Rectangle bounds = currSiblingBounds.getCopy();
               
                if (bounds.y > newBounds.y) {
                    bounds.y += dy;
                    SetBoundsCommand setBoundsCommand = new SetBoundsCommand(
                            this.getEditingDomain(), BpmnDiagramMessages.PoolPoolCompartmentEditPart_set_bounds_pool_command_name, siblingEditPart,
                            bounds);
                    compoudCommand.add(setBoundsCommand);
                    newTotalBounds.union(bounds);
                }
            }
        }
        try {
            compoudCommand.execute(new NullProgressMonitor(), null);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    /**
     * @generated NOT Override to use the BPMN extension to the RubberBand tracker.
     * It behaves more nicely with sub-process border compartment not taken
     * into account for the computation of what editparts are selected.
     * @see org.eclipse.gef.EditPart#getDragTracker(org.eclipse.gef.Request)
     */
    @Override
    public DragTracker getDragTracker(Request req) {
        if (!supportsDragSelection())
            return super.getDragTracker(req);

        if (req instanceof SelectionRequest
            && ((SelectionRequest) req).getLastButtonPressed() == 3)
            return new DeselectAllTracker(this) {

                protected boolean handleButtonDown(int button) {
                    getCurrentViewer().select(PoolPoolCompartmentEditPart.this);
                    return true;
                }
            };
        return new RubberbandDragTrackerForBPMN() {

            protected void handleFinished() {
                if (getViewer().getSelectedEditParts().isEmpty())
                    getViewer().select(PoolPoolCompartmentEditPart.this);
            }
        };
    }

}
