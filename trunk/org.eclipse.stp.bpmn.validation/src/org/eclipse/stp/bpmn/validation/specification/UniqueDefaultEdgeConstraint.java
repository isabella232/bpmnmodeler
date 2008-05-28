/******************************************************************************
 * Copyright (c) 2006, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Dates       		 Author              Changes
 * Dec 1, 2006      Antoine Toulm&eacute;   Creation
 */
package org.eclipse.stp.bpmn.validation.specification;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.validation.BpmnValidationMessages;

/**
 * This constraint checks that there is only one default edge
 * after or before the XOR gateway.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class UniqueDefaultEdgeConstraint extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		if (ctx.getTarget() instanceof SequenceEdge) {
			SequenceEdge edge = (SequenceEdge) ctx.getTarget();
			if (edge.isIsDefault()) {
				if (!checkOk(edge.getSource(),false,edge)) {
					return ctx.createFailureStatus(new Object[] 
					        {getGatewayName(edge.getSource())});
				}
				if (!checkOk(edge.getTarget(),true,edge)) {
					return ctx.createFailureStatus(new Object[] 
					        {getGatewayName(edge.getTarget())});
				}
			}
		}
		return ctx.createSuccessStatus();
	}

	
	/**
	 * checks wether the vertex is a XOR gateway and
	 * that it is not related to two default edges.
	 * @param vertex
	 * @param incoming true if we have to screen the incoming messages,
	 * false if we have to screen the outgoing messages.
	 * @return
	 */
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	private boolean checkOk(Vertex vertex,boolean incoming,SequenceEdge target){
		if (vertex instanceof Activity && (
				(((Activity) vertex).getActivityType().getValue() ==
					ActivityType.GATEWAY_DATA_BASED_INCLUSIVE)||
					((Activity) vertex).getActivityType().getValue() ==
						ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE)) {
			List edges = incoming ? vertex.getIncomingEdges() : 
				vertex.getOutgoingEdges();
			for (Object edge:edges) {
				if (edge instanceof SequenceEdge) {
					if (((SequenceEdge) edge).isIsDefault() &&
							(!edge.equals(target))) {
						return false;
					}
				}
			}
		}
		return true;
	}
	/**
	 * Computes the gateway name, as it may be null, gives a generic name:
	 * XOR Gateway
	 */
	private String getGatewayName(Vertex v) {
		if (v instanceof Activity) {
			if (((Activity) v).getName() != null) {
				return ((Activity) v).getName();
			}
		}
		return BpmnValidationMessages.UniqueDefaultEdgeConstraint_defaultGatewayName;
	}
}
