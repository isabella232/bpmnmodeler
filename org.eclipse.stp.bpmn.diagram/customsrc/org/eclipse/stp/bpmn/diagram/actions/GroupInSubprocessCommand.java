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
 * Date             Author           Changes 
 * 28 August 2007   hmalphettes      Created 
 **/
package org.eclipse.stp.bpmn.diagram.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.commands.CreateSubProcessCommand;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.SequenceEdgeEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;
import org.eclipse.stp.bpmn.figures.SubProcessBorderFigure;

/**
 * This command groups selected shapes inside a sub-process that it creates.
 * 
 * @author hmalphettes, atoulme
 * @author Intalio Inc
 */
public class GroupInSubprocessCommand extends AbstractTransactionalCommand {
    //the view of the newly created sub-process editpart or null
    //if the command has not been executed yet.
    String _createdSubprocessProxyId;
    private String _originalContainerProxyId;
    private IDiagramGraphicalViewer viewer;
    //collection of the xmi ids of the edit parts selected
    //and their associated edit part classes.
    //used during redo to be able to update the editpart
    //as they changed during the execution and unexecution of things.
    private Map<String,Class> selectedEps = new HashMap<String, Class>();
    
    /** The edit parts to be grouped */
    private Set<GraphicalEditPart> editParts;
    /**
     * Edit part that are boundary events of a sub-process that belongs to the shapes being grouped.
     * It is necessary to consider those when computing the internal connections.
     * See EDGE-1108
     */
    private List<IGraphicalEditPart> almostSelectedBoundaryEvents = new ArrayList<IGraphicalEditPart>();
    /** External source connections */
    private Collection<SequenceEdgeEditPart> externalSrcConnections = new ArrayList<SequenceEdgeEditPart>();
    /** External target connections */
    private Collection<SequenceEdgeEditPart> externalTgtConnections = new ArrayList<SequenceEdgeEditPart>();
    /** Internal connections - between edit parts to be grouped */
    private Collection<SequenceEdgeEditPart> internalConnections = new HashSet<SequenceEdgeEditPart>();
    
    /**
     * Constructor for the action. when the button group is pressed.
     * @param domain
     * @param editParts
     * @param almostSelectedBoundaryEvents
     * @param externalSrcConnections
     * @param externalTgtConnections
     * @param internalConnections
     */
    public GroupInSubprocessCommand(
            TransactionalEditingDomain domain,
            Set<GraphicalEditPart> editParts,
            List<IGraphicalEditPart> almostSelectedBoundaryEvents,
            Collection<SequenceEdgeEditPart> externalSrcConnections,
            Collection<SequenceEdgeEditPart> externalTgtConnections,
            Collection<SequenceEdgeEditPart> internalConnections) {
        super(domain, BpmnDiagramMessages.GroupInSubprocessCommand_label, null);
        this.editParts = editParts;
        this.almostSelectedBoundaryEvents = almostSelectedBoundaryEvents;
        this.externalSrcConnections = externalSrcConnections;
        this.externalTgtConnections = externalTgtConnections;
        this.internalConnections = internalConnections;
    }

    /**
     * Constructor for the action. when the button group is pressed.
     * @param domain
     * @param editParts
     * @param almostSelectedBoundaryEvents
     * @param externalSrcConnections
     * @param externalTgtConnections
     * @param internalConnections
     */
    public GroupInSubprocessCommand(
            TransactionalEditingDomain domain,
            IDiagramGraphicalViewer viewer,
            Map<String,Class> contentToGroup,
            String originalContainerProxyId) {
        super(domain, BpmnDiagramMessages.GroupInSubprocessCommand_label, null);
        this.editParts = new HashSet<GraphicalEditPart>();
        this.almostSelectedBoundaryEvents = new ArrayList<IGraphicalEditPart>();
        this.externalSrcConnections = new ArrayList<SequenceEdgeEditPart>();
        this.externalTgtConnections = new ArrayList<SequenceEdgeEditPart>();
        this.internalConnections = new ArrayList<SequenceEdgeEditPart>();
        this.viewer = viewer;
        this.selectedEps = contentToGroup;
        _originalContainerProxyId = originalContainerProxyId;
    }

    
    @Override
    protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) {
        
        EditPart tmpContainerEditPart = null;
        if (viewer == null) {
            //it is null the first time this command is excuted.
            tmpContainerEditPart = editParts.iterator().next().getParent();
            _originalContainerProxyId = EMFCoreUtil.getProxyID(
                    ((GraphicalEditPart)tmpContainerEditPart).resolveSemanticElement());
            viewer = (IDiagramGraphicalViewer)tmpContainerEditPart.getViewer();
            for (GraphicalEditPart ep : editParts) {
                selectedEps.put(EMFCoreUtil.getProxyID(ep.resolveSemanticElement()), ep.getClass());
            }   
        } else {
            //it is not null when called from redo()
            //but the editparts have changed:
            //it is required to re-resolve them
            List<ShapeNodeEditPart> eps =
                viewer.findEditPartsForElement(_originalContainerProxyId,
                        ShapeCompartmentEditPart.class);
            if (eps.isEmpty()) {
                return null;//argh!?
            }
            tmpContainerEditPart = eps.get(0);
            List<EditPart> newSel = new ArrayList<EditPart>();
            for (Entry<String,Class> entry : selectedEps.entrySet()) {
                eps = viewer.findEditPartsForElement(entry.getKey(),
                            entry.getValue());
                if (!eps.isEmpty()) {
                    newSel.addAll(eps);
                }
            }
            GroupAction.refresh(null, new StructuredSelection(newSel),
                    editParts, almostSelectedBoundaryEvents,
                    externalSrcConnections, externalTgtConnections,
                    internalConnections);
        }
        
        // common parent of selected edit part now will become a parent
        // for new subprocess
        final EditPart containerEditPart = tmpContainerEditPart;
        

        // first let's create new sub-process
        final CreateViewRequest req = CreateViewRequestFactory.
            getCreateShapeRequest(BpmnElementTypes.SubProcess_2002, 
                BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
        // get to the semantic command to add a parameter to skip
        // the subprocess from being added a child by default
        for (Object desc : req.getViewDescriptors()) {
            CreateElementRequest createSubProcessSemanticRequest =
                (CreateElementRequest) ((ViewDescriptor) desc).
                getElementAdapter().getAdapter(CreateElementRequest.class);
            createSubProcessSemanticRequest.setParameter(
                    CreateSubProcessCommand.CREATE_CHILD_PARAMETER, 
                    Boolean.FALSE);
        }


        Rectangle rect = getSizeAndLocation(req);
        Command create = containerEditPart.getCommand(req);
        create.execute();
        final Node spview = (Node) ((IAdaptable) 
                ((List) req.getNewObject()).get(0)).getAdapter(Node.class);
        _createdSubprocessProxyId = EMFCoreUtil.getProxyID(spview.getElement());
//       retargeting connections.

        for (SequenceEdgeEditPart part : externalSrcConnections) {
            SequenceEdge edge = (SequenceEdge) part.resolveSemanticElement();
//          DestroyElementRequest request = new DestroyElementRequest(edge, false);
//          Command destroy = part.getCommand(new EditCommandRequestWrapper(request));
            edge.setSource((Vertex) spview.getElement());
            ((Edge) part.getNotationView()).setSource(spview);
//          destroy.execute();
        }
        for (SequenceEdgeEditPart part : externalTgtConnections) {
            SequenceEdge edge = (SequenceEdge) part.resolveSemanticElement();
//          DestroyElementRequest request = new DestroyElementRequest(edge, false);
//          Command destroy = part.getCommand(new EditCommandRequestWrapper(request));
            edge.setTarget((Vertex) spview.getElement());
            ((Edge) part.getNotationView()).setTarget(spview);
//          destroy.execute();
        }
        
        Bounds b = NotationFactory.eINSTANCE.createBounds();
        b.setX(rect.x);
        b.setY(rect.y);
        b.setHeight(rect.height);
        b.setWidth(rect.width);
        spview.setLayoutConstraint(b);

        View compartmentView = null;
        String compartmentId = Integer.toString(
                SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID);
        for (Object spchild : spview.getChildren()) {
            View vchild = (View) spchild;
            if (compartmentId.equals(vchild.getType())) {
                compartmentView = vchild;
                break;
            }
        }


        final List<EObject> semantic = new LinkedList<EObject>(); 
        Location sploc = (Location) spview.getLayoutConstraint();
        
        for (GraphicalEditPart part : editParts) {
            View v = (View) part.getModel();
            semantic.add(part.resolveSemanticElement());
            compartmentView.insertChild(v);
            
            if (v instanceof Node) {
                Bounds constraint = (Bounds) ((Node) v).getLayoutConstraint();
                if (constraint != null) {
                    if (constraint.getX() > 0) {
                        constraint.setX(constraint.getX() - sploc.getX());
                        if (constraint.getX() < 0) {
                            constraint.setX(0);
                        }
                    }
                    if (constraint.getY() > 0) {
                        constraint.setY(constraint.getY() - sploc.getY());
                        if (constraint.getY() < 0) {
                            constraint.setY(0);
                        }
                    }
                } else {
                    ((Node) v).setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
                }
            }
        }
        
        // let's have the diagram being valid all the time
        // remove the edges explicitly before moving the nodes
        // to a new container
        Map<Activity, List<SequenceEdge>> incoming = new HashMap<Activity, List<SequenceEdge>>();
        Map<Activity, List<SequenceEdge>> outgoing = new HashMap<Activity, List<SequenceEdge>>();
        for (EObject obj : semantic) {
            if (obj instanceof Activity) {
                Activity act = (Activity) obj;
                incoming.put(act, new ArrayList<SequenceEdge>(act.getIncomingEdges()));
                outgoing.put(act, new ArrayList<SequenceEdge>(act.getOutgoingEdges()));
                act.getIncomingEdges().clear();
                act.getOutgoingEdges().clear();
            }
        }
        
        // moving the edges now
        for (SequenceEdgeEditPart part : internalConnections) {
            SequenceEdge edge = (SequenceEdge) part.resolveSemanticElement();
            ((Graph) spview.getElement()).getSequenceEdges().add(edge);
        }
        
        // moving the nodes now
        
        //first move the artifacts because they are containers of associations
        for (EObject obj : semantic) {
            if (obj instanceof Activity) {
                //later
            } else if (obj instanceof Artifact) {
                ((SubProcess)spview.getElement()).getArtifacts().add((Artifact)obj);
            } else {
              //this looks very bad to me: ie unlikely to do any good.
                spview.getElement().eContents().add(obj);
            }
        }
        //second move the activities:               
        for (EObject obj : semantic) {
            if (obj instanceof Activity) {
                Graph graph = ((Activity) obj).getGraph();
                graph.getVertices().remove(obj);
                ((Activity) obj).setGraph(null);
                ((Activity) obj).setGraph((Graph) spview.getElement());
            }
        }

        // retargeting the edges now
        for (EObject obj : semantic) {
            if (obj instanceof Activity) {
                Activity act = (Activity) obj;
                act.getIncomingEdges().addAll(incoming.get(act));
                act.getOutgoingEdges().addAll(outgoing.get(act));
            }
        }
        
        // refreshing the result to clean the edit parts
        Command command = containerEditPart.getCommand(new Request(RequestConstants.REQ_REFRESH)); 
        command.execute();

        // refreshing the edge edit parts that
        // don't like being moved around
        for (SequenceEdgeEditPart part : externalSrcConnections) {
            part.refresh();
            part.activate();
        }
        
        for (SequenceEdgeEditPart part : externalTgtConnections) {
            part.refresh();
            part.activate();
        }
        
        for (GraphicalEditPart p : editParts) {
            for (Object connection : p.getTargetConnections()) {
                ((EditPart) connection).refresh();
                ((EditPart) connection).activate();
            }
            for (Object connection : p.getSourceConnections()) {
                ((EditPart) connection).refresh();
                ((EditPart) connection).activate();
            }
        }
        return CommandResult.newOKCommandResult();
    }

    @Override
    protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
        if (_createdSubprocessProxyId == null) {
            return Status.CANCEL_STATUS;
        }
       
        if (viewer == null) {
            return Status.CANCEL_STATUS;
        }
        List<ShapeNodeEditPart> eps =
            viewer.findEditPartsForElement(_createdSubprocessProxyId,
                    SubProcessEditPart.class);
        if (!eps.isEmpty()) {
            SubProcessEditPart spEditPart = (SubProcessEditPart)eps.get(0);
            UngroupAction.executeUngroupCommand(spEditPart);
            _createdSubprocessProxyId = null;
            return Status.OK_STATUS;
        } else {
            return Status.CANCEL_STATUS;
        }
    }

    @Override
    protected IStatus doRedo(IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
        doExecuteWithResult(monitor, info);
        return Status.OK_STATUS;
    }
    
    
    
    
    /**
     * @param req
     * @return The size and location of the sub-process being reated to group them all.
     */
    private Rectangle getSizeAndLocation(CreateViewRequest req) {
        int x = 0;
        int y = 0;
        int width = 0;
        int height = 0;
        boolean firstTime = true;
        for (GraphicalEditPart part : editParts) {
            if (part.getNotationView() instanceof Node) {
                Object constraint = ((Node) part.getNotationView()).getLayoutConstraint();
                if (constraint instanceof Location) {
                    Location loc = (Location) constraint;
                    int X = loc.getX();
                    int Y = loc.getY();
                    if (X != -1 && Y != -1) {
                        if (firstTime) {
                            firstTime = false;
                            x = X;
                            y = Y;
                        } else {
                            x = Math.min(X, x);
                            y = Math.min(Y, y);
                        }
                    }
                }
                    
                if (constraint instanceof Bounds) {
                    Bounds bounds = (Bounds)constraint;
                    int w = bounds.getWidth();
                    // autosize is definitively in need to be implemented.
                    // in the mean time, the view is initialized with a -1 -1 size
                    // and we have to rely on the figure and dirty hacks...
                    if (w == -1) {
                        w = part.getFigure().getBounds().width;
                    }
                    int h = bounds.getHeight();
                    if (h == -1) {
                        h = part.getFigure().getBounds().height;
                    }
                    int X = bounds.getX();
                    if (X == -1) {
                        X = part.getFigure().getBounds().x;
                    }
                    int Y = bounds.getY();
                    if (Y == -1) {
                        Y = part.getFigure().getBounds().y;
                    }
                    width = Math.max(width, w + X);
                    height = Math.max(height, h + Y);
                }
            }
        }
        width -= x;
        height -= y;
        // add the height of the border edit part
        // a figure is required to get the mapMode
        height += MapModeUtil.getMapMode(
                editParts.iterator().next().getFigure()).
                LPtoDP(SubProcessEditPart.BORDER_HEIGHT)/2; // border. 
        // insets
        height += SubProcessEditPart.INSETS.getHeight();
        
        width += SubProcessEditPart.INSETS.getWidth();
        Point pt = new Point(x, y);
        return new Rectangle(pt, new Dimension(width, height));
    }

    
    
    
}
