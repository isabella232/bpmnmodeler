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
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchor.INodeFigureAnchorTerminalUpdatable;

/**
 * Identical to a NodeFigure except that it takes an Anchor factory
 * responsible for the generation of the anchors instead of hardcoding
 * SlideableAnchors.
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class NodeFigureEx extends NodeFigure
implements INodeFigureAnchorTerminalUpdatable {

    private Set<IModelAwareAnchor> _anchors = new HashSet<IModelAwareAnchor>();

    /**
     * Updates the index of connection anchors: look for the passed
     * connection anchors and make sure the key for it is up to date.
     * 
     * @param ca The connection anchor that index might have changed.
     * @param indexToUpdate The index of connection anchors where the key
     * is the terminal.
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
    public static boolean updateTerminal(IModelAwareAnchor ca,
                                    Hashtable indexToUpdate) {
        Object thekey = null;
        for (Object key : indexToUpdate.keySet()) {
            Object value = indexToUpdate.get(key);
            if (value == ca) {
                if (key.equals(ca.getTerminal())) {
                    return false;//no change
                }
                thekey = key;
                break;
            }
        }
        if (thekey != null) {
            indexToUpdate.remove(thekey);
            indexToUpdate.put(ca.getTerminal(), ca);
            return true;
        }
        return false;//no change
    }
    
    
    
    private IConnectionAnchorFactory _connAnchorFactory;
    
    /**
     * @param connectionAnchorFactory The connection anchor factory
     */
    public NodeFigureEx(IConnectionAnchorFactory connectionAnchorFactory) {
        _connAnchorFactory = connectionAnchorFactory;
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
        if (p == null)
            return createDefaultAnchor();
        return _connAnchorFactory.createConnectionAnchor(this, p);
    }

    /**
     * Updates the index of connection anchors: look for the passed
     * connection anchors and make sure the key for it is up to date.
     * 
     * @param ca The connection anchor that index might have changed.
     */
    public void updateTerminal(IModelAwareAnchor ca) {
        updateTerminal(ca, getConnectionAnchors());
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
     * @param result The object on which the bounds will be set.
     * By default it is the bounds of the figure itself.
     */
    public void computeAbsoluteHandleBounds(Rectangle result) {
        result.setBounds(super.getBounds());
        super.translateToAbsolute(result);
    }

    

}
