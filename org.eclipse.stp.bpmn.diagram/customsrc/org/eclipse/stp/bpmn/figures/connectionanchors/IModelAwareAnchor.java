package org.eclipse.stp.bpmn.figures.connectionanchors;

import java.util.Set;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Defines an anchor aware of the type of the connection it supports, eventually
 * its 
 * <p>
 * In other words: the anchor provides the type of the edge the type of the owner,
 * the preferencesHint, the number of connections and the index of that connection
 * and it computes the actual bounds and location of the anchor.
 * </p>
 * <p>
 * It also updates its key in the index of connection anchors of its owner figure.
 * For that to work the owner figure must implement INodeFigureAnchorTerminalUpdatable.
 * </p>
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public interface IModelAwareAnchor extends ConnectionAnchor {

    /**
     * slideable is default.
     */
    public static final int POSITIONNING_SLIDEABLE = 0;
    
    /**
     * positionning should rely on domain model specifics
     */
    public static final int POSITIONNING_DOMAIN_AWARE = 1;
    
    public static final int SOURCE_ANCHOR = 0;
    public static final int TARGET_ANCHOR = 1;
    public static final int UNKNOWN_ANCHOR = 2;
    
    /**
     * Method that returns a location without using the support object.
     * It is called by the support object when it should not be used to 
     * compute the location. It should delegate to the super-class of the anchor
     * implementation the computation of the location.
     * 
     * @param reference The reference point.
     * @return The location to use.
     */
    public Point getDefaultLocation(Point reference);
    
    /**
     * @return The type of the connection supported by this anchor or null if
     * unknown. Returns null if it does not know.
     */
    public String getConnectionType();
    
    /**
     * @return SOURCE_ANCHOR when source anchor TARGET_ANCHOR if target
     * and UNKNOWN_ANCHOR if it is not know yet.
     */
    public int isSourceAnchor();
    
    /**
     * @return The index of the anchor amongst other significant anchors
     * if it is relevant. -1 otherwise.
     */
    public int getPositionIndex();
    
    /** 
     * Returns the owner Figure on which this anchor's location is dependent.
     * In this case it is always an INodeFigureAnchorTerminalUpdatable
     *
     * @return  Owner of this anchor
     */
    public INodeFigureAnchorTerminalUpdatable getCastedOwner();
    
    /**
     * @return POSITIONNING_SLIDEABLE or POSITIONNING_DOMAIN_AWARE
     */
    public int getAnchorPositionningStyle();

    /**
     * @return The figure that is the connection or null if unknown.
     */
    public Connection getConnectionOwner();
    
    /**
     * @return The order at which this anchor is amongst its other anchors.
     * -1 if it should not be taken into account. 0 is the first one.
     */
    public int getOrderNumber();

    /**
     * @return The total number of anchors when they this anchor should be ordered.
     * -1 if it should not be taken into account. 0 is the first one.
     */
    public int getCount();
    
    /**
     * @param connectionType The connection type this anchor is used for
     * @param order The order in which this anchor is amongst its sibling anchors
     * that are ordered
     * @param count The total number of anchors ordered where this anchor is one
     * of them
     */
    public void setConnectionType(boolean isSource,
            String connectionType, int order, int count);

    
    /**
     * The terminal string reflects the current state of the anchor.
     * It is used as the id of the anchor in the semantic model.
     * So it has to be synchronized when it changes.
     * 
     * @return <code>String</code> terminal
     */
    public String getTerminal();
    
    /**
     * A figure that holds an index of terminals that can be updated
     * when the anchor knows that its key has changed.
     * When using an IModelAwareAnchor, it is likely that the terminal string keeps
     * changing so it is highly recommended that the owner figure support
     * this method if the owner figure indexes the connection anchors that way.
     * <p>
     * Also the figure always extends NodeFigure
     * </p>
     * 
     * @author hmalphettes
     * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
     */
    public interface INodeFigureAnchorTerminalUpdatable extends IFigure {
        /**
         * Updates the index of connection anchors: look for the passed
         * connection anchors and make sure the key for it is up to date.
         * 
         * @param ca The connection anchor that index might have changed.
         */
        public void updateTerminal(IModelAwareAnchor ca);
        
        /**
         * @return The set of anchors registered on this figure.
         */
        public Set<IModelAwareAnchor> getAnchors();
        
        /**
         * @return The factory used.
         */
        public IConnectionAnchorFactory getConnectionAnchorFactory();
        
        /**
         * @param result The object on which the bounds will be set.
         * By default it is the bounds of the figure itself.
         */
        public void computeAbsoluteHandleBounds(Rectangle result);

    }

}
