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
package org.eclipse.stp.bpmn.diagram.edit.policies;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.AssociationEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;

/**
 * @generated
 */
public class SubProcessGraphicalNodeEditPolicy extends
        BpmnGraphicalNodeEditPolicy {
	
	/**
	 * @generated not
	 * Change the anchor of the edge.
	 */
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	@Override
	protected Command getReconnectTargetCommand(final ReconnectRequest request) {
		// first collecting the edges that match the moving edge
		
		final ConnectionEditPart moving = request.getConnectionEditPart();
		// ignoring associations
		if (moving instanceof AssociationEditPart) {
			return super.getReconnectTargetCommand(request);
		}
		final boolean isMessagingEdge = ((IGraphicalEditPart) moving).resolveSemanticElement() 
			instanceof MessagingEdge ? true : false;
		int interesting = ((GraphicalEditPart) moving.getTarget()).getFigure().getBounds().y;
		boolean below = ((PolylineConnection) moving.getFigure()).
			getTargetAnchor().getLocation(null).y > interesting;
		final Map<Point, ConnectionEditPart> srcEps = new HashMap<Point, ConnectionEditPart>();
		for (Object src : ((IGraphicalEditPart) getHost()).getSourceConnections()) {
			Point loc = ((PolylineConnection) ((ConnectionEditPart) src).getFigure()).
				getSourceAnchor().getLocation(null);
			if (isInteresting(interesting, below, src, true) && 
					((IGraphicalEditPart) src).resolveSemanticElement() 
					instanceof MessagingEdge) {
				srcEps.put(loc, (ConnectionEditPart) src);
			}
		}
			
		final Map<Point, ConnectionEditPart> tgtEps = new HashMap<Point, ConnectionEditPart>();
		for (Object target : ((IGraphicalEditPart) getHost()).getTargetConnections()) {
			Point loc = ((PolylineConnection) ((ConnectionEditPart) target).getFigure()).
				getTargetAnchor().getLocation(null);
			if ((!moving.equals(target))) {
				if (isInteresting(interesting, below, target, false) && 
						((IGraphicalEditPart) target).resolveSemanticElement() 
							instanceof MessagingEdge) {
					tgtEps.put(loc, (ConnectionEditPart) target);
				} else if ((!isMessagingEdge) && ((IGraphicalEditPart) target).
						resolveSemanticElement() instanceof SequenceEdge) {
					// cannot rely on coordinates when it comes to sequence edges.
					// we are saved by the fact that sequence edges are only
					// placed on one side of the figure (incoming or outgoing)
					tgtEps.put(loc, (ConnectionEditPart) target);
				}
			}
		}
		
		if  (tgtEps.isEmpty() && srcEps.isEmpty()) {
			return super.getReconnectTargetCommand(request);
		}
		
		tgtEps.put(request.getLocation(), moving);
		
		// then sorting the edges so that they are sorted by their x position.
		final List<Point> points = new LinkedList<Point>(tgtEps.keySet());
		points.addAll(srcEps.keySet());
		Comparator comparator = new Comparator<Point>() {

			public int compare(Point o1, Point o2) {
				if (isMessagingEdge) {
					if (o1.x < o2.x) {
						return -1;
					}
					return 1;
				} else {
					if (o1.y < o2.y) {
						return -1;
					}
					return 1;
				}
			}};
		Collections.sort(points, comparator);
		// now applies the command that will do the job.
		final SubProcess target = ((SubProcess) ((IGraphicalEditPart) moving.getTarget()).resolveSemanticElement());
		CompoundCommand compound = new CompoundCommand();
		
		AbstractTransactionalCommand command = 
			new AbstractTransactionalCommand(
					(TransactionalEditingDomain) AdapterFactoryEditingDomain.
				getEditingDomainFor(target), BpmnDiagramMessages.SubProcessGraphicalNodeEditPolicy_edge_order_change_command_name, null) {

					@Override
					protected CommandResult doExecuteWithResult(
							IProgressMonitor monitor, IAdaptable info) 
								throws ExecutionException {
						// we need to move the edge after the edges before it.
						
						for (Point p : points) {
							if (!p.equals(request.getLocation())) {
								continue;
							}
							int index = points.indexOf(p);
							EObject edge = (EObject) 
								((IGraphicalEditPart) moving).resolveSemanticElement();
							
							// moving the edge to the right spot.
							
							int newentry = index;
							int oldentry = -1;
							if (isMessagingEdge) {
								for (int i = 0; i < target.getOrderedMessages().size(); i++) {
									EObject msg = (EObject) target.getOrderedMessages().getValue(i);
									if (msg.equals(edge)) {
										oldentry = i;
										break;
									}
								}

								target.getOrderedMessages().move(newentry, oldentry);
							} else {
								for (int i = 0; i < target.getIncomingEdges().size(); i++) {
									EObject msg = (EObject) target.getIncomingEdges().get(i);
									if (msg.equals(edge)) {
										oldentry = i;
										break;
									}
								}

								target.getIncomingEdges().move(newentry, oldentry);
							}
							break;
						}
					    ((SubProcessEditPart) getHost()).refreshSourceConnections();
					    ((SubProcessEditPart) getHost()).refreshTargetConnections();
						return CommandResult.newOKCommandResult();
					}};
		compound.add(new ICommandProxy(command));
		compound.add(super.getReconnectTargetCommand(request));
		return compound;
	}

	/**
	 * @generated not
	 * Change the anchor of the edge.
	 */
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	@Override
	protected Command getReconnectSourceCommand(final ReconnectRequest request) {
// first collecting the edges that match the moving edge
		
		final ConnectionEditPart moving = request.getConnectionEditPart();
		// ignoring associations
		if (moving instanceof AssociationEditPart) {
			return super.getReconnectSourceCommand(request);
		}
		final boolean isMessagingEdge = ((IGraphicalEditPart) moving).resolveSemanticElement() 
			instanceof MessagingEdge ? true : false;
		int interesting = ((GraphicalEditPart) moving.getSource()).getFigure().getBounds().y;
		boolean below = ((PolylineConnection) moving.getFigure()).
		getSourceAnchor().getLocation(null).y > interesting;
		final Map<Point, ConnectionEditPart> srcEps = new HashMap<Point, ConnectionEditPart>();
		for (Object src : ((IGraphicalEditPart) getHost()).getSourceConnections()) {
			Point loc = ((PolylineConnection) ((ConnectionEditPart) src).getFigure()).
			getSourceAnchor().getLocation(null);
			if ((!moving.equals(src))) {
				if (isMessagingEdge && isInteresting(interesting, below, src, true) && 
						((IGraphicalEditPart) src).resolveSemanticElement() 
						instanceof MessagingEdge) {
					srcEps.put(loc, (ConnectionEditPart) src);
				} else if ((!isMessagingEdge) && ((IGraphicalEditPart) src).
						resolveSemanticElement() instanceof SequenceEdge) {
					// cannot rely on coordinates when it comes to sequence edges.
					// we are saved by the fact that sequence edges are only
					// placed on one side of the figure (incoming or outgoing)
					srcEps.put(loc, (ConnectionEditPart) src);
				}
			}
		}

		final Map<Point, ConnectionEditPart> tgtEps = new HashMap<Point, ConnectionEditPart>();
		for (Object target : ((IGraphicalEditPart) getHost()).getTargetConnections()) {
			Point loc = ((PolylineConnection) ((ConnectionEditPart) target).getFigure()).
			getTargetAnchor().getLocation(null);
			if (isMessagingEdge && isInteresting(interesting, below, target, false) && 
					((IGraphicalEditPart) target).resolveSemanticElement() 
					instanceof MessagingEdge) {
				tgtEps.put(loc, (ConnectionEditPart) target);
			} 
		}

		if  (tgtEps.isEmpty() && srcEps.isEmpty()) {
			return super.getReconnectSourceCommand(request);
		}
		
		srcEps.put(request.getLocation(), moving);
		
		// then sorting the edges so that they are sorted by their x position.
		final List<Point> points = new LinkedList<Point>(tgtEps.keySet());
		points.addAll(srcEps.keySet());
		Comparator comparator = new Comparator<Point>() {

			public int compare(Point o1, Point o2) {
				if (isMessagingEdge) {
					if (o1.x < o2.x) {
						return -1;
					}
					return 1;
				} else {
					if (o1.y < o2.y) {
						return -1;
					}
					return 1;
				}
			}};
		Collections.sort(points, comparator);
		
		// now applies the command that will do the job.
		final SubProcess target = ((SubProcess) ((IGraphicalEditPart) moving.getSource()).resolveSemanticElement());
		CompoundCommand compound = new CompoundCommand();
		
		AbstractTransactionalCommand command = 
			new AbstractTransactionalCommand(
					(TransactionalEditingDomain) AdapterFactoryEditingDomain.
				getEditingDomainFor(target), BpmnDiagramMessages.SubProcessGraphicalNodeEditPolicy_edge_order_change_command_name, null) {

					@Override
					protected CommandResult doExecuteWithResult(
							IProgressMonitor monitor, IAdaptable info) 
								throws ExecutionException {
						// we need to move the edge after the edges before it.
						
						for (Point p : points) {
							if (!p.equals(request.getLocation())) {
								continue;
							}
							int index = points.indexOf(p);
							EObject edge = (EObject) 
								((IGraphicalEditPart) moving).resolveSemanticElement();
							
							// moving the edge to the right spot.
							
							int newentry = index;
							int oldentry = -1;
							if (isMessagingEdge) {
								for (int i = 0; i < target.getOrderedMessages().size(); i++) {
									EObject msg = (EObject) target.getOrderedMessages().getValue(i);
									if (msg.equals(edge)) {
										oldentry = i;
										break;
									}
								}
								target.getOrderedMessages().move(newentry, oldentry);
							} else {
								for (int i = 0; i < target.getOutgoingEdges().size(); i++) {
									EObject msg = (EObject) target.getOutgoingEdges().get(i);
									if (msg.equals(edge)) {
										oldentry = i;
										break;
									}
								}
								target.getOutgoingEdges().move(newentry, oldentry);
							}
							break;
						}
						((SubProcessEditPart) getHost()).refreshSourceConnections();
                        ((SubProcessEditPart) getHost()).refreshTargetConnections();
						return CommandResult.newOKCommandResult();
					}};
		compound.add(new ICommandProxy(command));
		compound.add(super.getReconnectSourceCommand(request));
		return compound;
	}
	
	private boolean isInteresting(int interesting, boolean below, Object ep, boolean lookSource) {
		ConnectionAnchor anchor = lookSource ? ((PolylineConnection) ((GraphicalEditPart) ep).
				getFigure()).getSourceAnchor() : 
					((PolylineConnection) ((GraphicalEditPart) ep).getFigure()).getTargetAnchor(); 
		int y = anchor.getLocation(null).y;
		if (below) {
			return y > interesting;
		} else {
			return y <= interesting;
		}
	}
}
