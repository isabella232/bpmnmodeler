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

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchor.INodeFigureAnchorTerminalUpdatable;

/**
 * Identical to a DefaultSizeNode except that it takes an Anchor factory
 * responsible for the generation of the anchors instead of hardcoding
 * SlideableAnchors.
 * <p>
 * Not extending the NodeFigureEx in case cast are run inside the code
 * that would expect a DefaultSizeNodeFigure
 * </p>
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class DefaultSizeNodeFigureEx extends DefaultSizeNodeFigure 
implements INodeFigureAnchorTerminalUpdatable {
    
    private IConnectionAnchorFactory _connAnchorFactory;
    private Set<IModelAwareAnchor> _anchors = new HashSet<IModelAwareAnchor>();
    
    /**
     * @param connectionAnchorFactory The connection anchor factory
     */
    public DefaultSizeNodeFigureEx(Dimension defSize,
            IConnectionAnchorFactory connectionAnchorFactory) {
        super(defSize);
        _connAnchorFactory = connectionAnchorFactory;
    }
    
    /**
     * @param connectionAnchorFactory The connection anchor factory
     */
    public DefaultSizeNodeFigureEx(int width, int height,
            IConnectionAnchorFactory connectionAnchorFactory) {
        super(width, height);
        _connAnchorFactory = connectionAnchorFactory;
    }
    
    /**
     * @return The set of anchors registered on this figure.
     */
    public Set<IModelAwareAnchor> getAnchors() {
        if (getParent() instanceof INodeFigureAnchorTerminalUpdatable) {
            return ((INodeFigureAnchorTerminalUpdatable)getParent()).getAnchors();
        }
        return _anchors;
    }
    /**
     * @return The factory used.
     */
    public IConnectionAnchorFactory getConnectionAnchorFactory() {
        return _connAnchorFactory;
    }
    
    /**
     * Creates the default Slidable anchor with a reference point at the center
     * of the figure's bounds
     * 
     * @return - default SlidableAnchor, relative reference the center of the figure
     */
    protected ConnectionAnchor createDefaultAnchor() {
        return _connAnchorFactory.createConnectionAnchor(this);
    }
    
    /**
     * Creates a slidable anchor at the specified point (from the ratio of the
     * reference's coordinates and bounds of the figure
     * 
     * @param p - relative reference for the <Code>SlidableAnchor</Code>
     * @return a <code>SlidableAnchor</code> for this figure with relative reference at p
     */
    protected ConnectionAnchor createAnchor(PrecisionPoint p) {
        if (p == null) {
            return createDefaultAnchor();
        }
        return _connAnchorFactory.createConnectionAnchor(this, p);
    }

    /**
     * Updates the index of connection anchors: look for the passed
     * connection anchors and make sure the key for it is up to date.
     * 
     * @param ca The connection anchor that index might have changed.
     */
    public void updateTerminal(IModelAwareAnchor ca) {
        NodeFigureEx.updateTerminal(ca, getConnectionAnchors());
    }
    
    /**
     * Returns the connectionAnchors.
     * @return Hashtable
     */
    public Hashtable getConnectionAnchors() {
        if (getParent() instanceof WrapperNodeFigureEx) {
            return ((WrapperNodeFigureEx)getParent()).getConnectionAnchors();
        }
        return super.getConnectionAnchors();
    }
}
