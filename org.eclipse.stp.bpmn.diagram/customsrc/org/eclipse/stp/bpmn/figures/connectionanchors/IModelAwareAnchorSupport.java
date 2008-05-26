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
 * Nov 24, 2006     hmalphettes         Created
 **/
package org.eclipse.stp.bpmn.figures.connectionanchors;

import org.eclipse.draw2d.geometry.Point;

/**
 * Defines a class in charge of computing the location and bounds of an anchor
 * given its usage in the domain and notation model.
 * <p>
 * In other words: the anchor provides the type of the edge the type of the owner,
 * the preferencesHint, the number of connections and the index of that connection
 * and it computes the actual bounds and location of the anchor.
 * </p>
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public interface IModelAwareAnchorSupport {

    /**
     * Method called by the anchor
     * 
     * @param reference The reference point.
     * @return The location to use.
     */
    public Point getLocation(IModelAwareAnchor anchor, Point reference);

    
}
