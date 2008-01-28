/******************************************************************************
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Dates       		 Author              Changes
 * Jan 15, 2007      Antoine Toulm�   Creation
 */
package org.eclipse.stp.bpmn.diagram.generation;

import java.util.Map;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.Identifiable;
import org.eclipse.stp.bpmn.Lane;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.Vertex;

/**
 * This factory helps creating BPMN diagrams for non-GMF developers.
 * 
 * It wraps some complex calls, helps create a new model file.
 * 
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public interface IProcessGenerator {

	/**
	 * Creates and adds a pool to the model.
	 * 
	 * @param name the name of the pool
	 * 
	 * @return a newly added pool
	 * 
	 */
	public abstract Pool addPool(String name);

	/**
	 * Creates and adds a lane to the pool.
	 * 
	 * @param pool the pool
	 * @param name the name of the lane
	 * 
	 * @return a newly added lane
	 * 
	 */
	public abstract Lane addLane(Pool pool, String name);

	/**
	 * Creates and adds a task to a parent (either a pool, lane or 
	 * subprocess).
	 * 
	 * @param parent the parent (either a pool or subprocess)
	 * the parent of the activity may be null.
	 * @param name the name of the task
	 * @param the int value for the Activity type.
	 * @see ActivityType for constants to use there.
	 * @return a newly added task
	 * 
	 */
	public abstract Activity addActivity(Graph parent, String name, 
			int activityType);

	/**
	 * Connects the source and target elements with a sequence.
	 * 
	 * @param source the source element
	 * @param target the target element
	 * 
	 */
	public abstract SequenceEdge addSequenceEdge(Vertex source,
			Vertex target,String name);

	/**
	 * Connects the source and target elements with a message.
	 * 
	 * @param source the source element
	 * @param target the target element
	 * 
	 */
	public abstract MessagingEdge messageConnect(Activity source,
			Activity target,String name);

//	/**
//	 * Connects the source and target elements with either a sequence (if in the
//	 * same pool) or with a message (if in different pools).
//	 * 
//	 * @param source the source element
//	 * @param target the target element
//	 * 
//	 */
//	public abstract Identifiable sequenceOrMessageConnect(Activity source,
//			Activity target,String name);

	/**
	 * Adds a documentation text to a <code>EModelElement</code>.
	 * <p/>
	 * 
	 * @param element the <code>EModelElement</code> to add the documentation
	 *        text to.
	 * @param documentation the documentation text to add.
	 * 
	 */
	public abstract void addDocumentation(Identifiable element,
			String documentation);

	/**
	 * Adds an EAnnotation to the semantic element 
	 * represented by the given view.
	 * @param view the view which element is going to be annotated.
	 * @param source the key that ties the element and its annotation.
	 * @param details the map containing the data for the annotation.
	 * @return the EAnnotation.
	 */
	public EAnnotation addAnnotation(EModelElement view, String source, 
			Map<String,String> details);

}