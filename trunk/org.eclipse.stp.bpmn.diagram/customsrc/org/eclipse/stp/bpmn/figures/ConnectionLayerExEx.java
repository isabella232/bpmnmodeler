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
 * Jul 12, 2006     hmalphettes         Created
 **/

package org.eclipse.stp.bpmn.figures;

import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.gmf.runtime.draw2d.ui.internal.figures.ConnectionLayerEx;
import org.eclipse.stp.bpmn.figures.router.EdgeRectilinearRouter;
import org.eclipse.stp.bpmn.figures.router.EventHandlersRectilinearRouter;
import org.eclipse.stp.bpmn.figures.router.MessageRectilinearRouter;

/**
 * Extended to be able to customize the routers.
 * Not sure what are the side-effects of not extending the GMF layer.
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ConnectionLayerExEx extends ConnectionLayerEx {

    protected ConnectionRouter bpmnMessagingEdgesRectilinearRouter;
    protected ConnectionRouter bpmnSequenceEdgesRectilinearRouter;
    protected ConnectionRouter bpmnSequenceEdgesForBorderedShapesRectilinearRouter;
    
    public ConnectionLayerExEx() {
    }

    /**
     * Provides an access point to the rectilinear router for bpmn messaging 
     * edges for the entire layer. 
     * Each connection will contain a reference to this router so that
     * the router can keep track of overlapping connections and reroute accordingly.
     * 
     * @return the <code>ConnectionRouter</code> 
     * that handles bpmn messaging edges rectilinear style routing.
     */
    public ConnectionRouter getBpmnMessagingEdgeRectilinearRouter() {
        if (bpmnMessagingEdgesRectilinearRouter == null)
            bpmnMessagingEdgesRectilinearRouter = new MessageRectilinearRouter();

        return bpmnMessagingEdgesRectilinearRouter;
    }
    
    /**
     * Provides an access point to the rectilinear router for bpmn sequence 
     * edges for the entire layer. 
     * Each connection will contain a reference to this router so that
     * the router can keep track of overlapping connections and reroute accordingly.
     * 
     * @return the <code>ConnectionRouter</code> 
     * that handles bpmn messaging edges rectilinear style routing.
     */
    public ConnectionRouter getBpmnSequenceEdgeRectilinearRouter() {
        if (bpmnSequenceEdgesRectilinearRouter == null)
            bpmnSequenceEdgesRectilinearRouter = new EdgeRectilinearRouter();

        return bpmnSequenceEdgesRectilinearRouter;
    }
    
    /**
     * Provides an access point to the rectilinear router for bpmn sequence 
     * edges that source is on a shapes bordered on another shape at the bottom
     *  for the entire layer. 
     * Each connection will contain a reference to this router so that
     * the router can keep track of overlapping connections and reroute accordingly.
     * 
     * @return the <code>ConnectionRouter</code> 
     * that handles bpmn messaging edges rectilinear style routing.
     */
    public ConnectionRouter getBpmnSequenceEdgeForBorderedShapesRectilinearRouter() {
        if (bpmnSequenceEdgesForBorderedShapesRectilinearRouter == null)
            bpmnSequenceEdgesForBorderedShapesRectilinearRouter = new EventHandlersRectilinearRouter();

        return bpmnSequenceEdgesForBorderedShapesRectilinearRouter;
    }
    
}
