/******************************************************************************
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************/

/**
 * Date		    	Author		    	Changes
 * Jul 19, 2006		hmalphettes			Created
 **/

package org.eclipse.stp.bpmn.figures.connectionanchors.impl;

import org.eclipse.draw2d.AnchorListener;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.gmf.runtime.common.core.util.StringStatics;
import org.eclipse.gmf.runtime.gef.ui.figures.SlidableAnchor;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchor;
import org.eclipse.stp.bpmn.figures.connectionanchors.IModelAwareAnchorSupport;

/**
 * This connection anchor behaves just like a Slideable anchor except that
 * it computes its location by calling an object and giving it various
 * information regarding the nature of the edge and its position as well
 * as the nature of the owner of the anchor.
 * <p>
 * That object is in charge of deciding the behavior of the anchor.
 * It also updates its key in the index of connection anchors of its owner figure.
 * For that to work the owner figure must implement INodeFigureAnchorTerminalUpdatable.
 * </p>
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ModelAwareAnchor extends SlidableAnchor implements IModelAwareAnchor {

    private String _connectionType;
    private int _orderNumber = -1;
    private int _count = -1;
    
    private Connection _connOwner;
    private int _srcOrTarget = UNKNOWN_ANCHOR;

    private IModelAwareAnchorSupport _support;
    
    /**
     * 
     * @param fig The figure on which this anchor is set
     * @param ref The coordinates of the anchor
     */
    public ModelAwareAnchor(INodeFigureAnchorTerminalUpdatable fig,
            PrecisionPoint ref,
            IModelAwareAnchorSupport support) {
        super(fig, ref);
        _support = support;
    }

    /**
     * 
     * @param fig The figure on which this anchor is set
     * @param ref The coordinates of the anchor
     */
    public ModelAwareAnchor(INodeFigureAnchorTerminalUpdatable fig,
            IModelAwareAnchorSupport support) {
        super(fig);
        _support = support;
    }
    
    /**
     * Returns true if the <Code>SlidableAnchor</Code> is default one with a reference at the center
     * 
     * @return <code>boolean</code> <code>true</code> is the <code>SlidableAnchor</code> is default one, <code>false</code> otherwise
     */
    public boolean isDefaultAnchor() {
        return _connectionType == null;
    }


    /**
     * TODO: don't hardcode the support object.
     * We could use a service based on the type of the connection.
     */
    @Override
    public Point getLocation(Point reference) {
        return getModelAwayAnchorSupport().getLocation(this, reference);
    }
    
    /**
     * @return The object used to compute the coordinates of the anchors
     */
    protected IModelAwareAnchorSupport getModelAwayAnchorSupport() {
        return _support;
    }
    
    /**
     * Returns the anchor's reference point.
     * 
     * @return The reference point
     */
    public Point getReferencePoint() {
        return getLocation(null);
    }
    
    /**
     * @return The order at which this anchor is amongst its other anchors.
     * -1 if it should not be taken into account. 0 is the first one.
     */
    public int getOrderNumber() {
        return _orderNumber;
    }

    /**
     * @return The total number of anchors when they this anchor should be ordered.
     * -1 if it should not be taken into account. 0 is the first one.
     */
    public int getCount() {
        return _count;
    }

    /**
     * @return The type of the connection supported by this anchor or null if
     * unknown. Returns null if it does not know.
     */
    public String getConnectionType() {
        return _connectionType;
    }

    /**
     * @param connectionType The connection type this anchor is used for
     */
    public void setConnectionType(boolean isSource,
            String connectionType, int order, int count) {
        if (_connectionType == null || !_connectionType.equals(connectionType)
                || order != _orderNumber || _count != count) {
            _connectionType = connectionType;
            _orderNumber = order;
            _count = count;
            if (getOwner() instanceof INodeFigureAnchorTerminalUpdatable) {
                ((INodeFigureAnchorTerminalUpdatable)getOwner()).updateTerminal(this);
            } else {
                throw new IllegalArgumentException(BpmnDiagramMessages.bind(
                		BpmnDiagramMessages.ModelAwareAnchor_warning_invalidIndex, getOwner()));
            }
            super.fireAnchorMoved();
        }
    }

    /**
     * Method that returns a location without using the support object.
     * It is called by the support object when it should not be used to 
     * compute the location. It should delegate to the super-class of the anchor
     * implementation the computation of the location.
     * 
     * @param reference The reference point.
     * @return The location to use.
     */
    public Point getDefaultLocation(Point reference) {
        return super.getLocation(reference);
    }
    
    /**
     * @return SOURCE_ANCHOR when source anchor TARGET_ANCHOR if target
     * and UNKNOWN_ANCHOR if it is not know yet.
     */
    public int isSourceAnchor() {
        return _srcOrTarget;
    }
    
    /**
     * @return The index of the anchor amongst other significant anchors
     * if it is relevant. -1 otherwise.
     */
    public int getPositionIndex() {
        return _orderNumber;
    }
    
    /** 
     * Returns the owner Figure on which this anchor's location is dependent.
     *
     * @since 2.0
     * @return  Owner of this anchor
     * @see #setOwner(IFigure)
     */
    public INodeFigureAnchorTerminalUpdatable getCastedOwner() {
        return (INodeFigureAnchorTerminalUpdatable) super.getOwner();
    }
    
    /**
     * @return POSITIONNING_SLIDEABLE or POSITIONNING_DOMAIN_AWARE
     */
    public int getAnchorPositionningStyle() {
        //TODO: use the preference or something to eventually return something else
        return POSITIONNING_DOMAIN_AWARE;
    }

    /**
     * @return The figure that is the connection or null if unknown.
     */
    public Connection getConnectionOwner() {
        return _connOwner;
    }

    /**
     * Used to set the connection owner.
     * @see org.eclipse.draw2d.ConnectionAnchor#addAnchorListener(AnchorListener)
     */
    @Override
    public void addAnchorListener(AnchorListener listener) {
        boolean doFireMove = false;
        if (listener instanceof Connection) {
            Connection conn = (Connection)listener;
            if (conn.getSourceAnchor() == this && 
                    (_connOwner != listener || _srcOrTarget != SOURCE_ANCHOR)) {
                _srcOrTarget = SOURCE_ANCHOR;
                _connOwner = conn;
                doFireMove = true;
            } else if (conn.getTargetAnchor() == this &&
                    (_connOwner != listener || _srcOrTarget != TARGET_ANCHOR)) {
                _srcOrTarget = TARGET_ANCHOR;
                _connOwner = conn;
                doFireMove = true;
            }
        }
        super.addAnchorListener(listener);
        if (doFireMove) {
            if (getOwner().getParent() instanceof
                    INodeFigureAnchorTerminalUpdatable) {
                ((INodeFigureAnchorTerminalUpdatable) getOwner().getParent())
                                .getAnchors().add(this);
            } else {
                ((INodeFigureAnchorTerminalUpdatable) getOwner()).getAnchors().add(this);
            }
            super.fireAnchorMoved();//try to trigger a UI update. it is necessary
        }
    }

    /**
     * Used to unset the connection owner.
     * @see org.eclipse.draw2d.ConnectionAnchor#removeAnchorListener(AnchorListener)
     */
    @Override
    public void removeAnchorListener(AnchorListener listener) {
        boolean doFireMove = false;
        if (_connOwner == listener) {
            if (getOwner().getParent() instanceof
                    INodeFigureAnchorTerminalUpdatable) {
                ((INodeFigureAnchorTerminalUpdatable) getOwner().getParent())
                                .getAnchors().add(this);
            } else {
                ((INodeFigureAnchorTerminalUpdatable) getOwner()).getAnchors().add(this);
            }
            _srcOrTarget = UNKNOWN_ANCHOR;
            _connOwner = null;
            doFireMove = true;
        }
        super.removeAnchorListener(listener);
        if (doFireMove) {
            super.fireAnchorMoved();//try to trigger a UI update. it is necessary
        }
    }
    
    
    

    /**
     * Creates terminal string for the anchor
     * 
     * @return <code>String</code> terminal for slidable anchor
     */
    public String getTerminal() {
        if (isDefaultAnchor()) {
            if (_connectionType != null) {
                return _connectionType;
            }
            return StringStatics.BLANK;
        }
        return composeTerminalString(
                new PrecisionPoint(getReferencePoint())) + _connectionType;
    }


    final private static char TERMINAL_START_CHAR = '(';
    final private static char TERMINAL_DELIMITER_CHAR = ',';
    final private static char TERMINAL_END_CHAR = ')'; 

    /**
     * hmalphettes: taken from BaseSlideableAnchor where it is private.
     * 
     * @param p - a <Code>PrecisionPoint</Code> that must be represented as a unique
     * <Code>String</Code>, namely as "(preciseX,preciseY)"
     * @return <code>String</code> terminal composed from specified <code>PrecisionPoint</code>  
     */
    private String composeTerminalString(PrecisionPoint p) {
        StringBuffer s = new StringBuffer(24);
        s.append(TERMINAL_START_CHAR);      // 1 char
        s.append((float)p.preciseX);        // 10 chars
        s.append(TERMINAL_DELIMITER_CHAR);  // 1 char
        s.append((float)p.preciseY);        // 10 chars
        s.append(TERMINAL_END_CHAR);        // 1 char
        return s.toString();                // 24 chars max (+1 for safety, i.e. for string termination)
    }

}
