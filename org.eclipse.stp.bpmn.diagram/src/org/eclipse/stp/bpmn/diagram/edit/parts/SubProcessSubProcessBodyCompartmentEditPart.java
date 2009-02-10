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
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToGuides;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.gef.tools.DeselectAllTracker;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableCompartmentEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.diagram.ui.figures.ShapeCompartmentFigure;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateUnspecifiedTypeConnectionRequest;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.DrawerStyle;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.policies.SubProcessSubProcessBodyCompartmentCanonicalEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.SubProcessSubProcessBodyCompartmentItemSemanticEditPolicy;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;
import org.eclipse.stp.bpmn.figures.BpmnShapesDefaultSizes;
import org.eclipse.stp.bpmn.figures.GroupFigure;
import org.eclipse.stp.bpmn.policies.BpmnDragDropEditPolicy;
import org.eclipse.stp.bpmn.policies.ContainerNodeEditPolicyEx;
import org.eclipse.stp.bpmn.policies.OpenFileEditPolicy;
import org.eclipse.stp.bpmn.policies.PopupBarEditPolicyEx;
import org.eclipse.stp.bpmn.policies.ResizableCompartmentEditPolicyEx;
import org.eclipse.stp.bpmn.policies.SubProcessCreationEditPolicy;
import org.eclipse.stp.bpmn.policies.SubProcessSubProcessCompartmentXYLayoutEditPolicy;
import org.eclipse.stp.bpmn.tools.RubberbandDragTrackerForBPMN;
import org.eclipse.stp.bpmn.tools.TaskDragHelper;
 
/** 
 * This class represents the subprocess body compartment 
 * edit part. 
 *  
 * It handles the collapse/expand mechanism. 
 *  
 * @author <a href="http://www.intalio.com">Intalio Inc.</a> 
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a> 
 */ 
public class SubProcessSubProcessBodyCompartmentEditPart extends 
        ShapeCompartmentEditPart { 
 
    /** 
     * Those constants are used to define an annotation 
     * that will remember the previous bounds while 
     * expanded or collapsed. 
     */ 
    public static final String SUBPROCESS_ANNOTATION_SOURCE = "SubProcess_Annotation"; //$NON-NLS-1$ 
    public static final String WIDTH = "width"; //$NON-NLS-1$ 
    public static final String HEIGHT = "height"; //$NON-NLS-1$ 
    public static final String ARRANGE_SIBLINGS = "arrangeSiblings"; //$NON-NLS-1$ 
    /** 
     * @generated 
     */ 
    public static final int VISUAL_ID = 5002; 
 
    /** 
     * @notgenerated 
     */ 
    private static final int BORDER_INSET = 5; 
 
    /** 
     * @generated 
     */ 
    public SubProcessSubProcessBodyCompartmentEditPart(View view) { 
        super(view); 
    } 
 
    /** 
     * @generated 
     */ 
    public String getCompartmentNameGen() { 
        return "SubProcessBodyCompartment"; //$NON-NLS-1$ 
    } 
 
    /** 
     * @notgenerated 
     */ 
    public String getCompartmentName() { 
        EObject bpmnObj = getPrimaryView().getElement(); 
        if (bpmnObj instanceof Activity) { 
            Activity a = (Activity) bpmnObj; 
            if (a.getName() != null) { 
                return BpmnDiagramMessages.bind(BpmnDiagramMessages.SubProcessSubProcessBodyCompartmentEditPart_name_w_suffix, a.getName()); 
            } 
        } 
        return BpmnDiagramMessages.SubProcessSubProcessBodyCompartmentEditPart_name; 
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

    private static final Point PRIVATE_POINT = new Point();
    
    /** 
     * @generated Not 
     */ 
    public IFigure createFigure() { 
        ShapeCompartmentFigure scf = new ShapeCompartmentFigure(getCompartmentName(), getMapMode())  {
            
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
                                return null; // the mouse events are redirected to us if the target would be a group.
                            }
                            return fig;
                        }
                    }
                }
                return null;
            }
        };
        scf.getContentPane().setLayoutManager(getLayoutManager());
        scf.getContentPane().addLayoutListener(LayoutAnimator.getDefault());
        
//        NotScrollableShapeCompartmentFigure result = 
//            new NotScrollableShapeCompartmentFigure(getCompartmentNameGen(), getMapMode(), false); 
        scf.getContentPane().setLayoutManager(getLayoutManager()); 
        scf.getContentPane().addLayoutListener(LayoutAnimator.getDefault());
        scf.getScrollPane().setScrollBarVisibility(ScrollPane.NEVER);
        scf.setTitleVisibility(false); 
        scf.setBorder(null); 
        return scf; 
    } 
 
    /** 
     * @generated 
     */ 
    protected void createDefaultEditPoliciesGen() { 
        super.createDefaultEditPolicies(); 
        installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, 
                new ResizableCompartmentEditPolicy()); 
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, 
                new SubProcessSubProcessBodyCompartmentItemSemanticEditPolicy()); 
        installEditPolicy(EditPolicyRoles.CREATION_ROLE, 
                new CreationEditPolicy()); 
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, 
                new DragDropEditPolicy()); 
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE, 
                new SubProcessSubProcessBodyCompartmentCanonicalEditPolicy()); 
    } 
 
    /** 
     * @notgenerated 
     */ 
    protected void createDefaultEditPolicies() { 
        createDefaultEditPoliciesGen(); 
        // the following is added: 
        installEditPolicy(EditPolicyRoles.POPUPBAR_ROLE, 
                new PopupBarEditPolicyEx()); 
        removeEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE); 
        installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, 
                new ResizableCompartmentEditPolicyEx()); 
         
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, 
                new BpmnDragDropEditPolicy(this)); 
        removeEditPolicy(EditPolicyRoles.CANONICAL_ROLE); 
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE, 
            new SubProcessSubProcessBodyCompartmentCanonicalEditPolicy()); 
        removeEditPolicy(EditPolicyRoles.CREATION_ROLE); 
        installEditPolicy(EditPolicyRoles.CREATION_ROLE, 
                new SubProcessCreationEditPolicy()); 
        removeEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE); 
        installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,  
                new ContainerNodeEditPolicyEx()); 
        installEditPolicy(EditPolicy.LAYOUT_ROLE,  
                new SubProcessSubProcessCompartmentXYLayoutEditPolicy()); 
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
    protected void setCollapsed(boolean collapsed, boolean animate) { 
        processConnections(this); 
        super.setCollapsed(collapsed, animate); 
    } 
 
    /** 
     * Calls refresh() on the specified edit part's connections. This method is 
     * called recursively also for all children of the specified edit part. 
     *  
     * @param editPart 
     *            the edit part who's children connections should be refreshed. 
     */ 
    private static void processConnections(GraphicalEditPart editPart) { 
        List children = editPart.getChildren(); 
        for (Object obj : children) { 
            GraphicalEditPart childEditPart = (GraphicalEditPart) obj; 
            processConnections(childEditPart); 
        } 
        List connections = editPart.getSourceConnections(); 
        for (Object object : connections) { 
            ConnectionEditPart connection = (ConnectionEditPart) object; 
            connection.refresh(); 
        } 
        connections = editPart.getTargetConnections(); 
        for (Object object : connections) { 
            ConnectionEditPart connection = (ConnectionEditPart) object; 
            connection.refresh(); 
        } 
    } 
 
    /** 
     * @notgenerated 
     */ 
    @Override 
    protected void handleNotificationEvent(Notification event) { 
        Object feature = event.getFeature(); 
        if (NotationPackage.eINSTANCE.getDrawerStyle_Collapsed() 
                .equals(feature)) { 
            handleCollapseExpand(); 
        } 
 
        super.handleNotificationEvent(event); 
    } 
 
    /** 
     * Handles collapse/expands events. Changes the size of the subprocess and 
     * layouts sibling figure. 
     *  
     */ 
    private void handleCollapseExpand() { 
        boolean isCollapsed = ((Boolean) getStructuralFeatureValue(NotationPackage.eINSTANCE 
                .getDrawerStyle_Collapsed())).booleanValue(); 
 
        // Retrieve current bounds from the model 
        SubProcessEditPart subProcessEditPart = (SubProcessEditPart) getParent(); 
        int oldX = ((Integer) subProcessEditPart 
                .getStructuralFeatureValue(NotationPackage.eINSTANCE 
                        .getLocation_X())).intValue(); 
        int oldY = ((Integer) subProcessEditPart 
                .getStructuralFeatureValue(NotationPackage.eINSTANCE 
                        .getLocation_Y())).intValue(); 
        int oldWidth = ((Integer) subProcessEditPart 
                .getStructuralFeatureValue(NotationPackage.eINSTANCE 
                        .getSize_Width())).intValue(); 
        int oldHeight = ((Integer) subProcessEditPart 
                .getStructuralFeatureValue(NotationPackage.eINSTANCE 
                        .getSize_Height())).intValue(); 
        Dimension defaultSize = BpmnShapesDefaultSizes.getDefaultSize(BpmnElementTypes.SubProcess_2002).getCopy();
        if (oldWidth == -1) { 
            oldWidth = defaultSize.width; 
        } 
        if (oldHeight == -1) { 
            oldHeight = defaultSize.height; 
        } 
 
        // Retrieve previously saved bounds from the diagram 
        View view = subProcessEditPart.getPrimaryView(); 
        EAnnotation annotation = view 
                .getEAnnotation(SUBPROCESS_ANNOTATION_SOURCE); 
 
        if (annotation == null) { 
            annotation = EcoreFactory.eINSTANCE.createEAnnotation(); 
            annotation.setSource(SUBPROCESS_ANNOTATION_SOURCE); 
            view.getEAnnotations().add(annotation); 
        } 
        EMap details = annotation.getDetails(); 
        // obtain prevoius width and height from annotation 
        String width = (String) details.get(WIDTH); 
        String height = (String) details.get(HEIGHT); 
 
        // Calculate new bounds 
        Dimension minimalDim = BpmnShapesDefaultSizes.getSubProcessMinSize( 
                (SubProcessEditPart) subProcessEditPart);// subProcessEditPart.calcMinSize(); 
 
        int newWidth; 
        int newHeight; 
 
        if (width == null || height == null) { 
            // no previous record in annotation 
            if (isCollapsed) { 
                newWidth = Math.max(SubProcessEditPart.COLLAPSED_SIZE.width, 
                        minimalDim.width); 
                newHeight = Math.max(SubProcessEditPart.COLLAPSED_SIZE.height, 
                        minimalDim.height); 
            } else { 
                newWidth = Math.max(SubProcessEditPart.EXPANDED_SIZE.width, 
                        minimalDim.width); 
                newHeight = Math.max(SubProcessEditPart.EXPANDED_SIZE.height, 
                        minimalDim.height); 
            } 
        } else { 
            newWidth = Math.max(minimalDim.width, Integer.parseInt(width)); 
            newHeight = Math.max(minimalDim.height, Integer.parseInt(height)); 
        } 
 
        // Save new bounds in the diagram 
        details.put(WIDTH, Integer.toString(oldWidth)); 
        details.put(HEIGHT, Integer.toString(oldHeight)); 
 
        // now see if should arrange siblings 
        String isArrangeSiblingsStr =  
            annotation.getDetails().get(ARRANGE_SIBLINGS); 
        boolean isArrangeSiblings = isArrangeSiblingsStr != null ? 
                "true".equals(isArrangeSiblingsStr) :  //$NON-NLS-1$ 
                    BpmnDiagramEditorPlugin.getInstance(). 
                    getPreferenceStore().getBoolean( 
                            BpmnDiagramPreferenceInitializer.PREF_SP_COLLAPSE_STYLE); 
         
        // Apply new bounds if something changed 
        if (newWidth != oldWidth || newHeight != oldHeight) { 
            Rectangle subprocessBounds = new Rectangle(oldX, oldY, newWidth, 
                    newHeight); 
            setNewBounds(subprocessBounds, new Dimension(oldWidth, oldHeight), 
                    isCollapsed, isArrangeSiblings); 
//            if (!isCollapsed) { 
                // subProcessEditPart.setChildAdded(true); 
            ((ShapeCompartmentFigure) this.getFigure())
                .getScrollPane().setScrollBarVisibility(ScrollPane.NEVER);
                subProcessEditPart.refresh(); 
//            } 
        }
    } 
 
    /** 
     * Sets new bounds on parent subprocess and layouts sibling figures. 
     * Sibling figures that lay to the right and bottom from parent subprocess 
     * are shifted to the right and to the bottom on expand and to the left and 
     * to the top on collapse. 
     *  
     * @param newBounds 
     *            the new bounds of subprocess. 
     * @param oldSize 
     *            old size of subprocess. 
     * @param isArrangeSiblingsOn 
     *            wether we should arrange the siblings 
     * around the sp or not. 
     */ 
    private void setNewBounds(Rectangle newBounds, Dimension oldSize, 
            boolean isCollapsed, boolean isArrangeSiblingsOn) { 
        final SubProcessEditPart subProcessEditPart = (SubProcessEditPart) getParent(); 
        CompositeCommand compoudCommand = new CompositeCommand( 
                BpmnDiagramMessages.SubProcessSubProcessBodyCompartmentEditPart_collapse_expand_command_name); 
        TransactionalEditingDomain editingDomain = getEditingDomain(); 
        List siblings = subProcessEditPart.getParent().getChildren(); 
        int dx = newBounds.width - oldSize.width; 
        int dy = newBounds.height - oldSize.height; 
        int prevLeft = newBounds.x; 
        int prevTop = newBounds.y; 
 
        Rectangle newTotalBounds = new Rectangle(newBounds); 
        // add the command for the sp edit part 
        SetBoundsCommand setBoundsCommand = new SetBoundsCommand( 
                editingDomain, BpmnDiagramMessages.SubProcessSubProcessBodyCompartmentEditPart_set_bounds_command_name, subProcessEditPart, 
                newBounds); 
        compoudCommand.add(setBoundsCommand); 
        // now if we are in arrange style, change the siblings bounds 
        // if needed. 
        if (isArrangeSiblingsOn) { 
            for (Object sibling : siblings) { 
                GraphicalEditPart siblingEditPart = (GraphicalEditPart) sibling; 
                if (!(siblingEditPart.getModel() instanceof Node)) { 
                    continue; 
                } 
                if (siblingEditPart == subProcessEditPart) { 
                    continue; 
                } 
                if (siblingEditPart != subProcessEditPart) { 
                    Bounds currSiblingBounds = (Bounds) ((Node) siblingEditPart.getModel()).getLayoutConstraint(); 
//                    Rectangle currSiblingBounds = siblingEditPart.getFigure() 
//                        .getBounds().getCopy(); 
                    Rectangle bounds = new Rectangle(currSiblingBounds.getX(),  
                            currSiblingBounds.getY(), currSiblingBounds.getWidth(),  
                            currSiblingBounds.getHeight()); 
                    boolean changed = false; 
 
                    if (dx != 0 && bounds.x > prevLeft 
                            // //if it is above the current shape don't move anyways 
                            // && currSiblingBounds.y + currSiblingBounds.height > prevTop + 
                            // 4 
                            // && currSiblingBounds.y + currSiblingBounds.height >= 
                            // newBounds.y 
                    ) { 
                        int xchanged = bounds.x + dx; 
                        bounds.x = xchanged > prevLeft ? xchanged : prevLeft + 1; 
                        // avoid going back further than the left of 
                        // the shape that is collapsed. 
                        // otherwise when expanded again, it will not be moved 
                        // anymore. 
                        changed = true; 
                    } 
                    if (dy != 0 && bounds.y > prevTop 
                            // //if it is on the left, don't move anyways. 
                            // && currSiblingBounds.x < prevLeft 
                            // && currSiblingBounds.x + currSiblingBounds.width >= 
                            // newBounds.x 
                    ) { 
                        int ychanged = bounds.y + dy; 
                        // avoid going up further than the top of 
                        // the shape that is collapsed. 
                        // otherwise when expanded again, it will not be moved 
                        // anymore. 
                        bounds.y = ychanged > prevTop ? ychanged : prevTop + 1; 
                        changed = true; 
                    } 
                    if (changed) { 
                        SetBoundsCommand sibilingBoundsCmd = new SetBoundsCommand( 
                                editingDomain, BpmnDiagramMessages.SubProcessSubProcessBodyCompartmentEditPart_set_bounds_command_name, siblingEditPart, 
                                bounds); 
                        compoudCommand.add(sibilingBoundsCmd); 
                        newTotalBounds.union(bounds); 
                    } 
                } 
            } 
        } 
        try { 
            compoudCommand.execute(new NullProgressMonitor(), null); 
            if (!isCollapsed) { 
                GraphicalEditPart parentContainer = (GraphicalEditPart) getParent() 
                        .getParent().getParent(); 
                Rectangle prevBounds = parentContainer.getFigure().getBounds(); 
//                TaskDragHelper.updateContainerBounds((SubProcessEditPart) this 
//                        .getParent(), newTotalBounds, false); 
                // now do this recursively for all parent subprocesses 
                if (parentContainer instanceof SubProcessEditPart) { 
                    int newX = ((Integer) parentContainer 
                            .getStructuralFeatureValue(NotationPackage.eINSTANCE 
                                    .getLocation_X())).intValue(); 
                    int newY = ((Integer) parentContainer 
                            .getStructuralFeatureValue(NotationPackage.eINSTANCE 
                                    .getLocation_Y())).intValue(); 
                    int newWidth = ((Integer) parentContainer 
                            .getStructuralFeatureValue(NotationPackage.eINSTANCE 
                                    .getSize_Width())).intValue(); 
                    int newHeight = ((Integer) parentContainer 
                            .getStructuralFeatureValue(NotationPackage.eINSTANCE 
                                    .getSize_Height())).intValue(); 
 
                    Rectangle changedBounds = new Rectangle(newX, newY, 
                            newWidth, newHeight); 
                    if (!changedBounds.equals(prevBounds)) { 
                        SubProcessSubProcessBodyCompartmentEditPart body = 
                            (SubProcessSubProcessBodyCompartmentEditPart) parentContainer 
                            .getChildBySemanticHint(Integer.toString( 
                                 SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID)); 
                        body.setNewBounds(changedBounds, prevBounds.getSize(), false, isArrangeSiblingsOn); 
                    } 
                } 
            } 
        } catch (ExecutionException e) { 
            e.printStackTrace(); 
        } 
    } 
 
    @Override 
    /** 
     * @notgenerated 
     */ 
    protected void handlePropertyChangeEvent(PropertyChangeEvent event) { 
        super.handlePropertyChangeEvent(event); 
        TaskDragHelper.handlePropertyChangeEvent(event, this); 
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
 
    @Override 
    /** 
     * @notgenerated 
     */ 
    public EditPart getTargetEditPart(Request request) { 
        EditPart targetEditPart; 
        if (request instanceof CreateUnspecifiedTypeConnectionRequest) { 
            targetEditPart = getParent(); 
        } else { 
            targetEditPart = super.getTargetEditPart(request); 
        } 
        return targetEditPart; 
    } 
 
    /** 
     * @generated not 
     * your children are not selectable if you are in collapsed mode, 
     * but they are otherwise. 
     *  
     * Selecting the subprocess is possible by gripping the border of the shape. 
     */ 
    public boolean isSelectable() { 
         DrawerStyle style = (DrawerStyle)((View)getModel()) 
             .getStyle(NotationPackage.eINSTANCE.getDrawerStyle()); 
         if (style.isCollapsed()) { 
             return false; 
         } 
        return true; 
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
     
     
    /** 
     * @generated NOT Override to use the BPMN extension to the RubberBand tracker. 
     * It behaves more nicely with sub-process border compartment not taken 
     * into account for the computation of what editparts are selected. 
     * @see org.eclipse.gef.EditPart#getDragTracker(org.eclipse.gef.Request) 
     */ 
    @Override 
    public DragTracker getDragTracker(Request req) { 
         
        if (!supportsDragSelection()) { 
            return super.getDragTracker(req); 
        } 
 
        if (req instanceof SelectionRequest 
                && ((SelectionRequest) req).getLastButtonPressed() == 3) { 
            return new DeselectAllTracker(this) { 
 
                protected boolean handleButtonDown(int button) { 
                    getCurrentViewer().select(SubProcessSubProcessBodyCompartmentEditPart.this); 
                    return true; 
                } 
            }; 
        } 
        return new RubberbandDragTrackerForBPMN() { 
 
            //no more direct edit of the sub-process label when double-click 
            //on the sub-process body. 
//            /** 
//             * Defer selection to the name edit part for a direct edit. 
//             */ 
//            @Override 
//            protected boolean handleDoubleClick(int button) { 
//                IGraphicalEditPart part = ((IGraphicalEditPart) getParent()). 
//                    getChildBySemanticHint( 
//                            BpmnVisualIDRegistry.getType(SubProcessNameEditPart.VISUAL_ID)); 
//                DirectEditRequest req = new DirectEditRequest(RequestConstants.REQ_DIRECT_EDIT); 
//                req.setLocation(getLocation()); 
//                if (part != null) { 
//                    part.performRequest(req); 
//                } 
//                return true; 
//            } 
             
            protected void handleFinished() { 
                if (getViewer().getSelectedEditParts().isEmpty()) 
                    getViewer().select(SubProcessSubProcessBodyCompartmentEditPart.this); 
            } 
        }; 
    } 
 
    /**
     * @return true if the subprocess is collapsed, false otherwise.
     */
    public boolean isCollapsed() {
        return ((Boolean) getStructuralFeatureValue(NotationPackage.eINSTANCE
                        .getDrawerStyle_Collapsed())).booleanValue(); 
    }
    
    @Override
    /**
     * @generated NOT have the lanes be the first children, followed by the groups
     * then by everyone else. That way groups and lanes always appear below the other shapes.
     */
    protected List getModelChildren() {
        Object model = getModel();
        if (model != null && model instanceof View) {
            List list = ((View) model).getVisibleChildren();
            List res = new ArrayList();
            List groups = new ArrayList();
            for (Object object : list) {
                Node node = (Node) object;
                if (node.getType().equals(Integer.toString(GroupEditPart.VISUAL_ID))) {
                    groups.add(object);
                } else {
                    res.add(object);
                }
            }
            res.addAll(0, groups);
            return res;
        }
        return Collections.EMPTY_LIST;
    }
} 
