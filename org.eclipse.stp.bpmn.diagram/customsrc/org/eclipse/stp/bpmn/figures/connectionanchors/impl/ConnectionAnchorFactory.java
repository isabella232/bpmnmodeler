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
package org.eclipse.stp.bpmn.figures.connectionanchors.impl;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.gmf.runtime.draw2d.ui.figures.BaseSlidableAnchor;
import org.eclipse.stp.bpmn.figures.connectionanchors.IConnectionAnchorFactory;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchorSupport;
import org.eclipse.stp.bpmn.figures.connectionanchors.NodeFigureEx;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchor.INodeFigureAnchorTerminalUpdatable;

/**
 * Abstracts the implementation of the ConnectionAnchor
 * created by a NodeFigureEx
 * 
 * @see NodeFigureEx
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ConnectionAnchorFactory implements IConnectionAnchorFactory {

    public static ConnectionAnchorFactory INSTANCE = new ConnectionAnchorFactory();
    
    /**
     * @return The object in charge of computing the coordinates of a
     * model aware anchor
     */
    protected IModelAwareAnchorSupport getModelAwareAnchorSupport() {
        return BpmnAwareAnchorSupport.INSTANCE;
    }
    
    /**
     * @param fig
     * @return The default connection anchor.
     */
    public ConnectionAnchor createConnectionAnchor(INodeFigureAnchorTerminalUpdatable fig) {
        if (fig.getParent() instanceof INodeFigureAnchorTerminalUpdatable) {
            return new ModelAwareAnchor((INodeFigureAnchorTerminalUpdatable)
                    fig.getParent(), getModelAwareAnchorSupport());
        }
        return new ModelAwareAnchor(fig, getModelAwareAnchorSupport());
    }

    /**
     * @param fig
     * @return The default connection anchor.
     */
    public ConnectionAnchor createConnectionAnchor(INodeFigureAnchorTerminalUpdatable fig, PrecisionPoint ref) {
        if (fig.getParent() instanceof INodeFigureAnchorTerminalUpdatable) {
            return new ModelAwareAnchor((INodeFigureAnchorTerminalUpdatable)
                    fig.getParent(), ref, getModelAwareAnchorSupport());
        }
        return new ModelAwareAnchor(fig, ref, getModelAwareAnchorSupport());
    }
    /**
     * @param fig
     * @param terminal The terminal string usually a precision point.
     * @return The default connection anchor.
     */
    public ConnectionAnchor createConnectionAnchor(INodeFigureAnchorTerminalUpdatable fig, String terminal) {
        PrecisionPoint pp = BaseSlidableAnchor.parseTerminalString(terminal);
        if (pp != null) {
            return createConnectionAnchor(fig, pp);
        }
        return createConnectionAnchor(fig);
    }

}
