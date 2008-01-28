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

import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ListCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.notation.DrawerStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.policies.SubProcessSubProcessBorderCompartmentCanonicalEditPolicy;
import org.eclipse.stp.bpmn.diagram.edit.policies.SubProcessSubProcessBorderCompartmentItemSemanticEditPolicy;
import org.eclipse.stp.bpmn.policies.BpmnDragDropEditPolicy;
import org.eclipse.stp.bpmn.policies.PopupBarEditPolicyEx;

/**
 * @generated
 */
public class SubProcessSubProcessBorderCompartmentEditPart extends
        ListCompartmentEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 5003;

    /**
     * @generated
     */
    public SubProcessSubProcessBorderCompartmentEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    protected boolean hasModelChildrenChanged(Notification evt) {
        return false;
    }

    /**
     * @generated
     */
    public String getCompartmentNameGen() {
        return "SubProcessBorderCompartment"; //$NON-NLS-1$
    }
    
    /**
     * @notgenerated
     */
    public String getCompartmentName() {
        EObject bpmnObj = getPrimaryView().getElement();
        if (bpmnObj instanceof Activity) {
            Activity a = (Activity)bpmnObj;
            if (a.getName() != null) {
                return BpmnDiagramMessages.bind(
                		BpmnDiagramMessages.SubProcessSubProcessBorderCompartmentEditPart_sp_compartment_name_with_label, 
                		a.getName());
            }
        }
        return BpmnDiagramMessages.SubProcessSubProcessBorderCompartmentEditPart_sp_compartment_name;
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
     * Using a flow layout otherwise nothing shows up and weird stuff happens!
     * 
     * @notgenerated
     */
    public IFigure createFigure() {
        ResizableCompartmentFigure result = (ResizableCompartmentFigure) this
                .createFigureGen();
        FlowLayout layout = new FlowLayout();
        layout.setMinorAlignment(FlowLayout.ALIGN_CENTER);
        layout.setMajorAlignment(FlowLayout.ALIGN_CENTER);
        layout.setMajorSpacing(getMapMode().DPtoLP(5));
        layout.setMinorSpacing(getMapMode().DPtoLP(5));
        result.getContentPane().setLayoutManager(layout);
        result.setBorder(null);
        return result;
    }

    /**
     * @generated
     */
    protected void createDefaultEditPoliciesGen() {
        super.createDefaultEditPolicies();
        installEditPolicy(
                EditPolicyRoles.SEMANTIC_ROLE,
                new SubProcessSubProcessBorderCompartmentItemSemanticEditPolicy());
        installEditPolicy(EditPolicyRoles.CREATION_ROLE,
                new CreationEditPolicy());
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
                new DragDropEditPolicy());
        installEditPolicy(EditPolicyRoles.CANONICAL_ROLE,
                new SubProcessSubProcessBorderCompartmentCanonicalEditPolicy());
    }

    /**
     * @notgenerated
     */
    protected void createDefaultEditPolicies() {
        createDefaultEditPoliciesGen();
        installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE,
                new BpmnDragDropEditPolicy(this));
        // the following is added:
        installEditPolicy(EditPolicyRoles.POPUPBAR_ROLE,
                new PopupBarEditPolicyEx(this));
        // install policy to allow activities to be selected on the border
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new FlowLayoutEditPolicy() {

            @Override
            protected Command createAddCommand(EditPart child, EditPart after) {
                return null;
            }

            @Override
            protected Command createMoveChildCommand(EditPart child,
                    EditPart after) {
                return null;
            }

            @Override
            protected Command getCreateCommand(CreateRequest request) {
                return null;
            }

            @Override
            protected EditPolicy createChildEditPolicy(EditPart child) {
                NonResizableEditPolicy policy = (NonResizableEditPolicy) super
                        .createChildEditPolicy(child);
                // disable drag functionality
                policy.setDragAllowed(false);
                return policy;
            }
        });
        
     
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
     * 
     * @notgenerated this makes sure that visually the primary shape is the one 
     * that has the focus. It is the same code than for all the container 
     * compartments (mpeleshchyshyn)
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
     * @generated not
     * your children are not selectable if you are in collapsed mode,
     * but they are otherwise.
     * 
     * Selecting the subprocess is possible by gripping the border of the shape.
     */
    public boolean isSelectable() {
//         DrawerStyle style = (DrawerStyle)((View)getModel())
//             .getStyle(NotationPackage.eINSTANCE.getDrawerStyle());
         //if (style.isCollapsed()) {
             return false;
         //}
         //return true;
    }

}
