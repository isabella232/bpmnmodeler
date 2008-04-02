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
 * Jan 17, 2007      Antoine Toulm&eacute;   Creation
 */
package org.eclipse.stp.bpmn.diagram.generation;

import java.util.Map;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.ActivityType;

/**
 * This interface expands to the generation
 * of visual objects extending {@link View}.
 *  
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public interface IVisualProcessGenerator {

	/**
	 * Creates and adds a pool to the model.
	 * 
	 * @param name the name of the pool
	 * 
	 * @return a newly added pool
	 * 
	 */
	public abstract Node addPool(
			String name, int x, int y, int width, int height);

	/**
	 * Creates and adds a lane to the pool.
	 * 
	 * @param pool the pool
	 * @param name the name of the lane
	 * 
	 * @return a newly added lane
	 * 
	 */
	public abstract Node addLane(Node pool, String name, 
			int x, int y, int width, int height);

	/**
	 * Creates and adds a task to a parent (either a pool, lane or 
	 * subprocess).
	 * 
	 * @param parent the parent (either a pool or subprocess)
	 * @param name the name of the task
	 * @param the int value for the Activity type.
	 * @see ActivityType for constants to use there.
	 * @return a newly added task
	 * 
	 */
	public abstract Node addActivity(Node parent, String name, 
			int activityType, int x, int y, int width, int height);

	/**
	 * Creates and adds a pool to the model.
	 * 
	 * @param name the name of the pool
	 * 
	 * @return a newly added pool
	 * 
	 */
	public abstract Node addPool(String name);

	/**
	 * Creates and adds a lane to the pool.
	 * 
	 * @param pool the pool
	 * @param name the name of the lane
	 * 
	 * @return a newly added lane
	 * 
	 */
	public abstract Node addLane(Node pool, String name);

	/**
	 * Creates and adds a task to a parent (either a pool, lane or 
	 * subprocess).
	 * 
	 * @param parent the parent (either a pool or subprocess)
	 * @param name the name of the task
	 * @param the int value for the Activity type.
	 * @see ActivityType for constants to use there.
	 * @return a newly added task
	 * 
	 */
	public abstract Node addActivity(Node parent, String name, 
			int activityType);

	/**
	 * Connects the source and target elements with a sequence.
	 * 
	 * @param source the source element
	 * @param target the target element
	 * 
	 */
	public abstract Edge addSequenceEdge(Node source,
            Node target, String name);

	/**
	 * Connects the source and target elements with a message.
	 * 
	 * @param source the source element
	 * @param target the target element
	 * 
	 */
	public abstract Edge addMessagingEdge(Node source,
            Node target, String name);

	/**
	 * Connects the source and target elements with either a sequence (if in the
	 * same pool) or with a message (if in different pools).
	 * 
	 * @param source the source element
	 * @param target the target element
	 * 
	 */
	public abstract Edge addSequenceOrMessagingEdge(Node source,
            Node target, String name);

	/**
	 * Adds a documentation text to the semantic element linked to this 
	 * <code>View</code>.
	 * <p/>
	 * 
	 * @param element the <code>View</code> to add the documentation
	 *        text to
	 * @param documentation the documentation text to add
	 * 
	 */
	public abstract void addDocumentation(View element,
			String documentation);

	/**
	 * Adds an EAnnotation to the semantic element 
	 * represented by the given view.
	 * @param view the view which element is going to be annotated.
	 * @param source the key that ties the element and its annotation.
	 * @param details the map containing the data for the annotation.
	 * @return the EAnnotation.
	 */
	public EAnnotation addAnnotation(View view, String source, 
			Map<String,String> details);
}
