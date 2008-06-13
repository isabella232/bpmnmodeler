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
 * Date           Author             Changes
 * Sep 01, 2006   Mykola Peleshchyshyn  Created
 **/
package org.eclipse.stp.bpmn.figures.connectionanchors;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.figures.BaseSlidableAnchor;
import org.eclipse.gmf.runtime.gef.ui.figures.WrapperNodeFigure;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchor.INodeFigureAnchorTerminalUpdatable;

/**
 * Extends <code>WrapperNodeFigure</code> to support
 * <code>IModelAwareAnchor</code>.
 * <p>
 * In fact this figure behaves exactly like the super class.
 * Except that it returns ModelAwareAnchor.
 * The edit parts are in charge of setting the extra information
 * on the creted anchors.
 * </p>
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class WrapperNodeFigureEx extends WrapperNodeFigure
implements INodeFigureAnchorTerminalUpdatable {

    private IConnectionAnchorFactory _connectionAnchorFactory;
    private Set<IModelAwareAnchor> _anchors = new HashSet<IModelAwareAnchor>();
    
    private INodeFigureAnchorTerminalUpdatable _subFigure;

    /**
     * Composite pattern for wrapping a template list compartment around any
     * potential figure.
     * 
     * @param subFigure
     *            the <code>IFigure</code> that is being wrapped.
     */
    public WrapperNodeFigureEx(IConnectionAnchorFactory connectionAnchorFactory,
            INodeFigureAnchorTerminalUpdatable subFigure) {
        super(subFigure);
        _subFigure = subFigure;
        _connectionAnchorFactory = connectionAnchorFactory;
    }

    public void setSubfigure(INodeFigureAnchorTerminalUpdatable subFigure) {
        if (_subFigure != null) {
            remove(_subFigure);
        }
        _subFigure = subFigure;
        add(subFigure);
    }

    public INodeFigureAnchorTerminalUpdatable getSubfigure() {
        return _subFigure;
    }

    /**
     * Returns the connectionAnchors.
     * @return Hashtable
     */
    public Hashtable getConnectionAnchors() {
        return super.getConnectionAnchors();
    }

    /**
     * Returns a new anchor for this node figure.
     * 
     * @param p <code>Point</code> on the figure that gives a hint which anchor to return.
     * @return <code>ConnectionAnchor</code> reference to an anchor associated with the 
     * given point on the figure.
     */
    protected ConnectionAnchor createConnectionAnchor(Point p) {
        if (p == null) {
            return _connectionAnchorFactory.createConnectionAnchor(this);
        }
        else {
            Point temp = p.getCopy();
            translateToRelative(temp);
            PrecisionPoint pt = BaseSlidableAnchor.getAnchorRelativeLocation(temp, getBounds());
            if (isDefaultAnchorArea(pt))
                return getConnectionAnchor(szAnchor);
            return createAnchor(pt);
        }
    }
    
    /**
     * Creates the default Slidable anchor with a reference point at the center
     * of the figure's bounds
     * 
     * @return - default SlidableAnchor, relative reference the center of the figure
     */
    protected ConnectionAnchor createDefaultAnchor() {
        return _connectionAnchorFactory.createConnectionAnchor(this);
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
        return _connectionAnchorFactory.createConnectionAnchor(this, p);
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
     * @return The set of anchors registered on this figure.
     */
    public Set<IModelAwareAnchor> getAnchors() {
        return _anchors;
    }
    /**
     * @return The factory used.
     */
    public IConnectionAnchorFactory getConnectionAnchorFactory() {
        return _connectionAnchorFactory;
    }
    
    /**
     * @param result The object on which the bounds will be set.
     * By default it is the bounds of the figure itself.
     */
    public void computeAbsoluteHandleBounds(Rectangle result) {
        getSubfigure().computeAbsoluteHandleBounds(result);
    }


}
