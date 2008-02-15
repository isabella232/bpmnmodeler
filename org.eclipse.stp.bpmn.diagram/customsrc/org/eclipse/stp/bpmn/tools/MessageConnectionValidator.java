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
 * Date          	Author             Changes 
 * 27 Oct 2006   	BIlchyshyn         Created
 **/

package org.eclipse.stp.bpmn.tools;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap.ValueListIterator;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.MessageVertex;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;

/**
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 */
public class MessageConnectionValidator implements ConnectionValidator {
    
    public static MessageConnectionValidator INSTANCE = new MessageConnectionValidator();
    
    protected MessageConnectionValidator() {
        
    }
    
    public boolean canStart(EObject element) {
        if (!(element instanceof Activity)) {
            return false;
        }
        int activityType = ((Activity)element).getActivityType().getValue();
        //message connection can't start from gateways & start events
        switch (activityType) {
        case ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE:
        case ActivityType.GATEWAY_DATA_BASED_INCLUSIVE:
        case ActivityType.GATEWAY_EVENT_BASED_EXCLUSIVE:
        case ActivityType.GATEWAY_PARALLEL:
        case ActivityType.GATEWAY_COMPLEX:
        case ActivityType.EVENT_START_EMPTY:
        case ActivityType.EVENT_START_MESSAGE:
        case ActivityType.EVENT_START_RULE:
        case ActivityType.EVENT_START_LINK:
        case ActivityType.EVENT_START_MULTIPLE:
        case ActivityType.EVENT_START_TIMER:
            return false;
        default:
            return true;
        }
    }
    
	public boolean canStart(EditPart sourceEditPart) {
		if (sourceEditPart instanceof ActivityEditPart) {
			EObject element = ((Node) sourceEditPart.getModel()).getElement();
			return canStart(element);
		} else {
			return false;
		}
	}
	
    public boolean canEnd(EObject element) {
        if (!(element instanceof Activity)) {
            return false;
        }
        int activityType = ((Activity)element).getActivityType().getValue();
        //message connection can't end on gateways & end events
        switch (activityType) {
        case ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE:
        case ActivityType.GATEWAY_DATA_BASED_INCLUSIVE:
        case ActivityType.GATEWAY_EVENT_BASED_EXCLUSIVE:
        case ActivityType.GATEWAY_PARALLEL:
        case ActivityType.EVENT_END_COMPENSATION:
        case ActivityType.EVENT_END_EMPTY:
        case ActivityType.EVENT_END_ERROR:
        case ActivityType.EVENT_END_MESSAGE:
        case ActivityType.EVENT_END_TERMINATE:
            return false;
        default:
            return true;
        }
        
    }
	public boolean canEnd(EditPart targetEditPart) {
		if (targetEditPart instanceof ActivityEditPart) {
			EObject element = ((Node) targetEditPart.getModel()).getElement();
			return canEnd(element);
		} else {
		    return false;
        }
	}
	public boolean canConnect(EObject source, EObject target) {
        if (source == null) {
            return false;
        }
        if (target == null) {
            return false;
        }
        if (source.equals(target)) {
            return false;
        }
        if (!canEnd(target)) {
            return false;
        }
        if (!canStart(source)) {
            return false;
        }
        
        Pool srcPool = getPool(source);
        Pool targetPool = getPool(target);
        
        if (srcPool == null || targetPool == null || srcPool.equals(targetPool)) {
            return false;
        }
        
        //now check that if the source or the end is already connected to another
        //pool via another message then we are not connecting to a third pool.
        Activity srcA = (Activity)source;
        Activity targetA = (Activity)target;
        ValueListIterator<Object> it = srcA.getOrderedMessages().valueListIterator();
        while (it.hasNext()) {
            MessagingEdge me = (MessagingEdge)it.next();
            MessageVertex otherAc = me.getSource() == srcA ? me.getTarget() : me.getSource();
            Pool otherPool = getPool(otherAc);
            if (otherPool != targetPool && otherPool != srcPool) {
                return false;
            }
        }
        it = targetA.getOrderedMessages().valueListIterator();
        while (it.hasNext()) {
            MessagingEdge me = (MessagingEdge)it.next();
            MessageVertex otherAc = me.getSource() == targetA ? me.getTarget() : me.getSource();
            Pool otherPool = getPool(otherAc);
            if (otherPool != targetPool && otherPool != srcPool) {
                return false;
            }
        }
        return true;
	    
	}

	public boolean canConnect(EditPart source, EditPart target) {
		if (source == null) {
			return false;
		}
		if (target == null) {
			return false;
		}
		if (source.equals(target)) {
			return false;
		}
		return canConnect(((GraphicalEditPart)source).resolveSemanticElement(),
		        ((GraphicalEditPart)target).resolveSemanticElement());
		//why duplicate the work?
		//it is kind of "optimized to duplicate it for the very simple situation.
		//but not any further.
//		if (!canEnd(target)) {
//			return false;
//		}
//		if (!canStart(source)) {
//			return false;
//		}
//		EditPart srcPool = getPool(source);
//        EditPart targetPool = getPool(target);
//		
//		if (srcPool == null || targetPool == null || srcPool.equals(targetPool)) {
//			return false;
//		}
//		
//		//now check that if the source or the end is already connected to another
//		//pool via another message then we are not connecting to a third pool.
//		
//		
//		return true;
	}
	
    protected EditPart getPool(EditPart activity) {
        if (activity instanceof PoolEditPart) {
            return activity;
        }
        EditPart parent = activity.getParent();
        
        while (!(parent instanceof PoolEditPart) && parent != null) {
            parent = parent.getParent();
        }
        
        return parent;
    }
    protected Pool getPool(EObject activity) {
        if (activity instanceof Pool) {
            return (Pool)activity;
        }
        EObject parent = activity.eContainer();
        
        if (parent != null) {
            return getPool(parent);
        }
        
        return null;
    }
}


