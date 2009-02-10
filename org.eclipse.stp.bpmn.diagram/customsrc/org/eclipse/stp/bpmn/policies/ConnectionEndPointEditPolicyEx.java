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

/**
 * Date             Author              Changes
 * Dec 06, 2006     hmalphettes         Created
 **/
package org.eclipse.stp.bpmn.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;
import org.eclipse.gef.handles.ConnectionHandle;
import org.eclipse.gef.tools.ConnectionEndpointTracker;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.diagram.edit.parts.SequenceEdgeEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;


/**
 * This edit policy is overridden so that the drag tracker attached
 * to the handles is taking the subprocessEditPart, not one of the nested
 * shapes, into account when changing the order of sequence edges on
 * it.
 *  
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class ConnectionEndPointEditPolicyEx extends ConnectionEndpointEditPolicy {

    /**
     * @see org.eclipse.gef.editpolicies.SelectionHandlesEditPolicy#createSelectionHandles()
     */
    protected List createSelectionHandles() {
        List list = new ArrayList();
        list.add(new ConnectionEndHandleEx((ConnectionEditPart)getHost()));
        list.add(new ConnectionStartHandleEx((ConnectionEditPart)getHost()));
        return list;
    }

    /**
     * we override ConnectionEndHandle, but since it is a final class,
     * we have to copy all its contents, see bug #212935 for details
     * @author <a href="http://www.intalio.com">Intalio Inc.</a>
     * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
     */
    private class ConnectionEndHandleEx extends ConnectionHandle {

        /**
         * Creates a new ConnectionEndHandle, sets its owner to <code>owner</code>,
         * and sets its locator to a {@link ConnectionLocator}.
         * @param owner the ConnectionEditPart owner
         */
        public ConnectionEndHandleEx(ConnectionEditPart owner) {
            setOwner(owner);
            setLocator(new ConnectionLocator(getConnection(), ConnectionLocator.TARGET));
        }

        /**
         * Creates a new ConnectionEndHandle with its owner set to <code>owner</code>.
         * If the handle is fixed, it cannot be dragged.
         * @param owner the ConnectionEditPart owner
         * @param fixed if true, handle cannot be dragged
         */
        public ConnectionEndHandleEx(ConnectionEditPart owner, boolean fixed) {
            super(fixed);
            setOwner(owner);
            setLocator(new ConnectionLocator(getConnection(), ConnectionLocator.TARGET));
        }

        /**
         * Creates a new ConnectionEndHandle.
         */
        public ConnectionEndHandleEx() { }

        /**
         * Creates and returns a new {@link ConnectionEndpointTracker}.
         * @return the new ConnectionEndpointTracker
         */
        protected DragTracker createDragTracker() {
            if (isFixed())
                return null;
            ConnectionEndpointTracker tracker;
            tracker = new ConnectionEndpointTrackerEx((ConnectionEditPart)getOwner());
            tracker.setCommandName(RequestConstants.REQ_RECONNECT_TARGET);
            tracker.setDefaultCursor(getCursor());
            return tracker;
        }

    }

    /**
     * Overridding ConnectionStartHandle to change the tracker created.
     * @author <a href="http://www.intalio.com">Intalio Inc.</a>
     * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
     */
    private class ConnectionStartHandleEx extends ConnectionHandle {

        /**
         * Creates a new ConnectionStartHandle, sets its owner to <code>owner</code>,
         * and sets its locator to a {@link ConnectionLocator}.
         * @param owner the ConnectionEditPart owner
         */
        public ConnectionStartHandleEx(ConnectionEditPart owner) {
            setOwner(owner);
            setLocator(new ConnectionLocator(getConnection(), ConnectionLocator.SOURCE));
        }

        /**
         * Creates a new ConnectionStartHandle and sets its owner to <code>owner</code>.
         * If the handle is fixed, it cannot be dragged.
         * @param owner the ConnectionEditPart owner
         * @param fixed if true, handle cannot be dragged.
         */
        public ConnectionStartHandleEx(ConnectionEditPart owner, boolean fixed) {
            super(fixed);
            setOwner(owner);
            setLocator(new ConnectionLocator(getConnection(), ConnectionLocator.SOURCE));
        }

        /**
         * Creates a new ConnectionStartHandle.
         */
        public ConnectionStartHandleEx() { }

        /**
         * Creates and returns a new {@link ConnectionEndpointTracker}.
         * @return the new ConnectionEndpointTracker
         */
        protected DragTracker createDragTracker() {
            if (isFixed()) 
                return null;
            ConnectionEndpointTracker tracker;
            tracker = new ConnectionEndpointTrackerEx((ConnectionEditPart)getOwner());
            tracker.setCommandName(RequestConstants.REQ_RECONNECT_SOURCE);
            tracker.setDefaultCursor(getCursor());
            return tracker;
        }

    }

    /**
     * A subclass implementation of ConnectionEndPointTracker
     * to override its getTargetEditPart method in the case that we want
     * to select the shape that is containing the hovered shape because
     * the compartment alone is in the same graph.
     * @author <a href="http://www.intalio.com">Intalio Inc.</a>
     * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
     */
    private class ConnectionEndpointTrackerEx extends ConnectionEndpointTracker {

        public ConnectionEndpointTrackerEx(ConnectionEditPart cep) {
            super(cep);
        }

    protected boolean updateTargetUnderMouse() {
        if (!isTargetLocked()) {
            EditPart editPart = getCurrentViewer().findObjectAtExcluding(
                    getLocation(),
                    getExclusionSet(),
                    getTargetingConditional());
            if (getConnectionEditPart() instanceof SequenceEdgeEditPart) {
                Graph g = ((SequenceEdge) ((SequenceEdgeEditPart) getConnectionEditPart()).
                        resolveSemanticElement()).getGraph();
                
                if (editPart instanceof IGraphicalEditPart && 
                        !(((IGraphicalEditPart) editPart).resolveSemanticElement() instanceof Vertex)) {
                    // it might be possible that no shape is available just under the cursor.
                    // when changing the order of edges, we want to make possible that
                    // people can drag the edge at the top of the shape, and at the bottom of the shape,
                    // to the point that it isn't on the shape anymore.
                    // this is particularly important for gateways
                    // to do this let's try to find if an edit part is present in the 20 pixels up or down from the current mouse location.
                    int offset = MapModeUtil.getMapMode(getConnection()).LPtoDP(20);
                    editPart = getConnectionEditPart().getViewer().findObjectAt(getCurrentInput().
                            getMouseLocation().getCopy().getTranslated(0, offset));
                    if (editPart instanceof IGraphicalEditPart && 
                            !(((IGraphicalEditPart) editPart).resolveSemanticElement() instanceof Vertex)) {
                        editPart = getConnectionEditPart().getViewer().findObjectAt(getCurrentInput().
                                getMouseLocation().getCopy().getTranslated(0, -offset));
                    }
                }

                if (editPart instanceof IGraphicalEditPart) {
                	EObject eobj = ((IGraphicalEditPart) editPart).resolveSemanticElement();
                    if (eobj instanceof Vertex) {
	                    Vertex target = (Vertex) eobj;
	                    if (g != null && !g.equals(target.getGraph())) {
	                        IGraphicalEditPart newT =
	                        	findParentTarget(super.getTargetEditPart(), g);
	                        if (newT != null) {
	                            editPart = newT;
	                        }
	                    }
                    }
                }
            }
            if (editPart != null)
                editPart = editPart.getTargetEditPart(getTargetRequest());
            boolean changed = getTargetEditPart() != editPart;
            setTargetEditPart(editPart);
            return changed;
        } else
            return false;
    }
    private IGraphicalEditPart findParentTarget(EditPart currentTarget, Graph toComplyWith) {
        if (!(currentTarget instanceof IGraphicalEditPart)) {
            return null;
        }
        if (currentTarget instanceof SubProcessEditPart && 
                toComplyWith.equals(((Vertex) ((IGraphicalEditPart) currentTarget).
                        resolveSemanticElement()).getGraph())) {
            return (IGraphicalEditPart) currentTarget;
        }
        return findParentTarget(currentTarget.getParent(), toComplyWith);
    }

    @Override
    protected Command getCommand() {
        Command co = super.getCommand();
        return co;
    }

}
}
