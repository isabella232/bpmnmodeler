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
 * Date          	Author             Changes 
 * 27 Oct 2006   	BIlchyshyn         Created
 * 2 May 2007		Antoine Toulme		Doc and removing the merge rules
 **/

package org.eclipse.stp.bpmn.tools;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.diagram.edit.parts.Activity2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;

/**
 * The edge connection validator is in charge of respecting
 * the connection between objects through sequence edges.
 *
 * This class and its parent are specific to the BPMN modeler.
 * 
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class EdgeConnectionValidator implements ConnectionValidator {
    
    public static EdgeConnectionValidator INSTANCE = new EdgeConnectionValidator();
    
    protected EdgeConnectionValidator() {
        
    }
    /**
     * @param ep the edit part to examine
     * @return true if the edit part can be the start of the connection
     */
    public boolean canStart(EObject element) {
        if (!(element instanceof Activity)) {
            return false;
        }
        Activity act = (Activity) element;
        int activityType = act.getActivityType().getValue();
        //sequence connection can't start from end events
        switch (activityType) {
        case ActivityType.EVENT_END_COMPENSATION:
        case ActivityType.EVENT_END_EMPTY:
        case ActivityType.EVENT_END_ERROR:
        case ActivityType.EVENT_END_MESSAGE:
        case ActivityType.EVENT_END_TERMINATE:
        case ActivityType.EVENT_END_CANCEL:
        case ActivityType.EVENT_END_LINK:
        case ActivityType.EVENT_END_MULTIPLE:
            return false;
        case ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE:
        case ActivityType.GATEWAY_DATA_BASED_INCLUSIVE:
        case ActivityType.GATEWAY_EVENT_BASED_EXCLUSIVE:
        case ActivityType.GATEWAY_PARALLEL:
        case ActivityType.GATEWAY_COMPLEX:
            if (act.getOutgoingEdges().size() > 0
                    && act.getIncomingEdges().size() > 1) {
                /* 
                 * this is not standard. And this is not revelant.
                 */
                /*
                 * the gateway is merging and it can't have
                 * more than 1 outgoing sequence edge
                 */
//                return false;
            }
        default:
            return true;
        }

    }

	/**
	 * @param ep the edit part to examine
	 * @return true if the edit part can be the start of the connection
	 */
	public boolean canStart(EditPart sourceEditPart) {
		if (sourceEditPart instanceof ActivityEditPart) {
			EObject element = ((Node) sourceEditPart.getModel()).getElement();
			return canStart(element);
		} else if (sourceEditPart instanceof Activity2EditPart ||
                sourceEditPart instanceof SubProcessEditPart) {
            return true;
        }
		
		return false;
	}
    /**
     * 
     * @param ep the edit part to examine
     * @return true if the edit part can be the end of the connection
     */
    public boolean canEnd(EObject element) {
        if (!(element instanceof Activity)) {
            return false;
        }
        Activity act = (Activity) element;
        int activityType = act.getActivityType().getValue();
        
        //sequence connections can't end on start events
        switch (activityType) {
//      case ActivityType.EVENT_START_EMPTY:
//        case ActivityType.EVENT_START_MESSAGE:
//        case ActivityType.EVENT_START_RULE:
//        case ActivityType.EVENT_START_LINK:
//        case ActivityType.EVENT_START_MULTIPLE:
//        case ActivityType.EVENT_START_TIMER:
//          return false;
        // now a validation rule exists to do this
        case ActivityType.GATEWAY_DATA_BASED_EXCLUSIVE:
        case ActivityType.GATEWAY_DATA_BASED_INCLUSIVE:
        case ActivityType.GATEWAY_EVENT_BASED_EXCLUSIVE:
        case ActivityType.GATEWAY_PARALLEL:
        case ActivityType.GATEWAY_COMPLEX:
            if (act.getOutgoingEdges().size() > 1
                    && act.getIncomingEdges().size() > 0) {
                /*
                 * Again, not standard and not revelant.
                 * the gateway is forking and it can't have
                 * more than 1 incoming sequence edge
                 */
//                return false;
            }
        default:
            return true;
        }
    }
	/**
	 * 
	 * @param ep the edit part to examine
	 * @return true if the edit part can be the end of the connection
	 */
	public boolean canEnd(EditPart targetEditPart) {
		if (targetEditPart instanceof ActivityEditPart) {
			EObject element = ((Node) targetEditPart.getModel()).getElement();
			return canEnd(element);
		} else if (targetEditPart instanceof Activity2EditPart) {
            return false;
        } else if (targetEditPart instanceof SubProcessEditPart) {
            return true;
        }
		return false;
	}
	
	 /**
     * @return true if the source and the target of the edit part
     * can be connected.
     * 
     * Note that this method calls canEnd and canStart during
     * its execution.
     */
    public boolean canConnect(EObject source, EObject target) {
        if (source.equals(target)) {
            return false;
        }
        if (!canEnd(target)) {
            return false;
        }   
        if (!canStart(source)) {
            return false;
        }
        if (((Activity) source).getEventHandlerFor() != null) {
            if (!getGraph(source).equals(getGraph(target)) && 
                    !(getGraph(getGraph(source)).equals(getGraph(target)))) {
                return false;
            }
            if (getGraph(source).equals(target)) {
                return false;
            }
        } else if (!((Vertex) source).getGraph().
                equals(((Vertex) target).getGraph())) {
            return false;
        }
        return true;
    }
    
	/**
	 * @return true if the source and the target of the edit part
	 * can be connected.
	 * 
	 * Note that this method calls canEnd and canStart during
	 * its execution.
	 */
	public boolean canConnect(EditPart source, EditPart target) {
		if (source.equals(target)) {
			return false;
		}
		if (!canEnd(target)) {
			return false;
		}	
		if (!canStart(source)) {
			return false;
		}
		if (source instanceof Activity2EditPart) {
			if (!getGraph(source).equals(getGraph(target)) && 
                    !(getGraph(getGraph(source)).equals(getGraph(target)))) {
				return false;
			}
			if (getGraph(source).equals(target)) {
				return false;
			}
		} else if (!getGraph(source).equals(getGraph(target))) {
			return false;
		}
		
		return true;
	}
	
    /**
     * recursive method that return the pool edit part.
     * @param ep the edit part to examine.
     * @return an instance of PoolEditPart
     */
    protected EditPart getGraph(EditPart ep) {
        if (ep instanceof PoolEditPart) {
            return ep;
        }
        
        EditPart parent = ep.getParent();
        
        while (!(parent instanceof PoolEditPart) && parent != null) {
            if (parent instanceof SubProcessEditPart) {
                break;
            }
            parent = parent.getParent();
        }
        
        return parent;
    }
    /**
     * recursive method that return the pool edit part.
     * @param ep the edit part to examine.
     * @return an instance of PoolEditPart
     */
    protected Graph getGraph(EObject obj) {
        if (obj instanceof Graph) {
            return (Graph)obj;
        }
        
        EObject parent = obj.eContainer();
        if (parent != null) {
            return getGraph(parent);
        }
        
        return null;
    }
}
