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

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchor.INodeFigureAnchorTerminalUpdatable;

/**
 * Abstracts the implementation of the ConnectionAnchor
 * created by a NodeFigureEx
 * @see NodeFigureEx
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public interface IConnectionAnchorFactory {

    /**
     * @param fig
     * @return The default connection anchor.
     */
    public ConnectionAnchor createConnectionAnchor(INodeFigureAnchorTerminalUpdatable fig);

    /**
     * @param fig
     * @return The default connection anchor.
     */
    public ConnectionAnchor createConnectionAnchor(INodeFigureAnchorTerminalUpdatable fig, PrecisionPoint ref);

    /**
     * @param fig
     * @param terminal The terminal string usually a precision point.
     * @return The default connection anchor.
     */
    public ConnectionAnchor createConnectionAnchor(INodeFigureAnchorTerminalUpdatable fig, String terminal);

}
