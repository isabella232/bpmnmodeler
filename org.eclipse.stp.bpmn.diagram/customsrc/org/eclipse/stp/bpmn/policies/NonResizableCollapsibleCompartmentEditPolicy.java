/******************************************************************************
 * Copyright (c) 2006, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.stp.bpmn.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IResizableCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ResizableCompartmentEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.handles.CompartmentCollapseHandle;
import org.eclipse.gmf.runtime.diagram.ui.internal.tools.CompartmentCollapseTracker;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;

/**
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class NonResizableCollapsibleCompartmentEditPolicy extends ResizableCompartmentEditPolicy {

    /**
     * Makes sure the compartment is not resiable.
     * @see org.eclipse.gef.EditPolicy#getCommand(org.eclipse.gef.Request)
     */
    public Command getCommand(Request request) {
        if (REQ_RESIZE.equals(request.getType())) {
            return null;
        }
        return super.getCommand(request);
    }

    /**
     * This method is used to get the collapse handle(s). Subclasses can
     * override to provide different collapse handles
     * 
     * @return a list of collapse handles
     */
    protected List createCollapseHandles() {
        IGraphicalEditPart part = (IGraphicalEditPart) getHost();

        List collapseHandles = new ArrayList();
        collapseHandles.add(new CompartmentCollapseHandleForPool(
                (PoolEditPart)part.getParent(), (PoolPoolCompartmentEditPart)part));
        return collapseHandles;
    }

}

class CompartmentCollapseHandleForPool extends CompartmentCollapseHandle {
    
    private IResizableCompartmentEditPart _compartment;
    
    CompartmentCollapseHandleForPool(PoolEditPart pool,
                PoolPoolCompartmentEditPart poolCompartment) {
        super(pool);
        boolean isCollapsed = ((Boolean) poolCompartment
                .getStructuralFeatureValue(NotationPackage.eINSTANCE
                .getDrawerStyle_Collapsed())).booleanValue();
        collapseFigure.setCollapsed(isCollapsed);
        _compartment = poolCompartment;
    }
    
    /**
     * @see org.eclipse.gef.handles.AbstractHandle#createDragTracker()
     */
    protected DragTracker createDragTracker() {
        return new CompartmentCollapseTracker(_compartment);
    }
    /**
     * @see org.eclipse.draw2d.IFigure#addNotify()
     */
    public void addNotify() {
        super.addNotify();
        View view = _compartment.getNotationView();
        if (view!=null){
            getDiagramEventBroker().addNotificationListener(view, this);
        }
    }

    /**
     * @see org.eclipse.draw2d.IFigure#removeNotify()
     */
    public void removeNotify() {
        getDiagramEventBroker().removeNotificationListener(_compartment.getNotationView(),this);
        super.removeNotify();
    }
    private DiagramEventBroker getDiagramEventBroker() {
        TransactionalEditingDomain theEditingDomain = ((IGraphicalEditPart) getOwner())
            .getEditingDomain();
        if (theEditingDomain != null) {
            return DiagramEventBroker.getInstance(theEditingDomain);
        }
        return null;
    }

}
