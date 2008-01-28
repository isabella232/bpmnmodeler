/******************************************************************************
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Date         Author             Changes
 * Apr 23, 2007      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.dnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.LayoutConstraint;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.actions.ArrangeSelectionRecursivelyAction;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBorderCompartmentEditPart;


/**
 * @author atoulme
 *
 */
public abstract class AbstractViewDnDHandler extends AbstractDnDHandler {

	/**
     * This method takes care of dropping a view on the given edit part.
     * It has some semantic rules installed in the canExecute method of
     * the command it creates. It also takes care of the children, 
     * by copying the references. Finally it inspects the children 
     * to see if there are any edges remaining and 
     * copy them to the new children structure.
     * @param views the views to drop
     * @param dropLocation the point where the view should be dropped
     * @param part the part on which the views should be dropped.
     * @return a command that enables the drop of a view 
     * on the edit policy host.
     */
    protected Command getDropViewCommand(final List<View> views,
            final Point dropLocation, final IGraphicalEditPart part) {

        AbstractTransactionalCommand viewCommand = 
            new AbstractTransactionalCommand(
                    part.getEditingDomain(), 
                    BpmnDiagramMessages.AbstractViewDnDHandler_droppingView, 
                    null) {

            @SuppressWarnings("unchecked") //$NON-NLS-1$
			@Override
            protected CommandResult doExecuteWithResult(
                    IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {

                // access the other domain. The view might come from an other
                // domain, so we need to have its go for a write transaction.
                TransactionalEditingDomain domain = 
                    (TransactionalEditingDomain) AdapterFactoryEditingDomain.
                    getEditingDomainFor(views.get(0));
                RecordingCommand command = 
                    new RecordingCommand(part.getEditingDomain()) {
                    @Override
                    protected void doExecute() {
//                      try {
                        Map<View, View> newNodes = new HashMap<View, View>();
                        Map<View, View> edges = new HashMap<View, View>();
                        
                        for (EObject eobject : views) {
                            View view = (View) eobject;
                            // first copy the view and its semantic element.
                            EcoreUtil.Copier copier = new EcoreUtil.Copier();
                            View newView = (View) copier.copy(view);
                            if (view instanceof Node) {
                                newNodes.put(view, newView);
                            } else {
                                edges.put(view, newView);
                            }
                            copier.copyReferences();
                            // create the copy of the semantic element.
                            EObject newSemantic = copier.copy(view.getElement());
                            copier.copyReferences();
                            newView.setElement(newSemantic);
                            // insert the new view into the host diagram.
                            if (newView instanceof Node) {
                                part.getNotationView().
                                    insertChild(newView);
                            } else {
                                part.getNotationView().
                                    getDiagram().insertEdge((Edge) newView);
                            }
                            // now insert the semantic 
                            // element into the new container.
                            final EObject parentElement = 
                                part.resolveSemanticElement();
                            if (newSemantic instanceof EAnnotation) {
                                ((EAnnotation) newSemantic).setEModelElement(
                                        (EModelElement) parentElement);
                            } else if (parentElement instanceof BpmnDiagram) {
                                if (newSemantic instanceof Pool) {
                                    ((Pool) newSemantic).
                                    setBpmnDiagram((BpmnDiagram) parentElement);
                                } else if (newSemantic instanceof MessagingEdge) {
                                    ((MessagingEdge) newSemantic).setBpmnDiagram(
                                            (BpmnDiagram) parentElement);
                                } else if (newSemantic instanceof Artifact) {
                                    ((Artifact) newSemantic).setArtifactsContainer(
                                            (BpmnDiagram) parentElement);
                                }
                            } else if (parentElement instanceof Graph) {
                                if (part instanceof 
                                        SubProcessSubProcessBorderCompartmentEditPart) {
                                    ((Activity) newSemantic).
                                    setEventHandlerFor((SubProcess) parentElement);
                                } else {
                                    if (newSemantic instanceof Activity) {
                                        ((Activity) newSemantic).
                                        setGraph((Graph) parentElement);
                                    } else if (newSemantic instanceof SequenceEdge) {
                                        ((SequenceEdge) newSemantic).
                                            setGraph((Graph) parentElement);
                                    } else if (newSemantic instanceof Artifact) {
                                        ((Artifact) newSemantic).
                                            setArtifactsContainer((Graph) parentElement);
                                    }
                                }
                            }
                            

                            if (view instanceof Node) {
                                // inspect the edges and register 
                                // their new sources and targets into Maps.
                                TreeIterator treeIter = view.eAllContents();
                                TreeIterator newTreeIter = newView.eAllContents();
                                Map<Edge,Node> sources = new HashMap<Edge,Node>();
                                Map<Edge,Node> targets = new HashMap<Edge,Node>();
                                while (treeIter.hasNext()) {
                                    Object oldNode = treeIter.next();
                                    Object newNode = newTreeIter.next();
                                    if (oldNode instanceof Node) {
                                        newNodes.put((View) oldNode,(View) newNode);
                                    }
                                    if (oldNode instanceof Node) {
                                        for (Object edge : ((Node) oldNode).
                                                getSourceEdges()) {
                                            sources.put((Edge) edge, (Node) newNode);
                                        }

                                        for (Object edge : ((Node) oldNode).
                                                getTargetEdges()) {
                                            targets.put((Edge) edge, (Node) newNode);
                                        }
                                    }
                                }
                                // now iterate over the edges, 
                                // copy them and set the copy's properties
                                for (Edge oldEdge : sources.keySet()) {
                                    if (targets.get(oldEdge) == null) {
                                        // invalid edge, should not be copied.
                                        // we may raise an exception here.
                                        continue;
                                    }

                                    Edge newEdge = (Edge) copier.copy(oldEdge);
                                    copier.copyReferences();
                                    ((View) part.getModel()).getDiagram().
                                    	insertEdge(newEdge);

                                }
                            }
                            // sets the location of the view to be the drop point.
                            // this point is just a starting point that 
                            // will be changed during the arrange operations.
                            if (newView instanceof Node) {
                                Object bounds = ((Node) newView).
                                getLayoutConstraint();
                                if (bounds == null) {
                                    bounds = NotationFactory.
                                    eINSTANCE.createBounds();
                                }
                                Point realDropLocation = dropLocation.getCopy();
                            	part.getFigure().getParent().translateToAbsolute(realDropLocation);
                            	part.getFigure().translateToRelative(realDropLocation);
                            	Rectangle rect = part.getFigure().getBounds().getCopy();
                            	part.getFigure().translateToAbsolute(rect);
                            	realDropLocation.x = realDropLocation.x - rect.x;
                            	realDropLocation.y = realDropLocation.y - rect.y;
                            	// do not let a negative position
                            	realDropLocation.x = Math.max(0, realDropLocation.x);
                            	realDropLocation.y = Math.max(0, realDropLocation.y);
                            	if (part.getNotationView() instanceof Node) {
                            	    Object layout = ((Node) part.getNotationView()).getLayoutConstraint();
                            	    if (layout instanceof Location) {
                            	        // maybe need to deflect
                            	        int parentx = ((Location) layout).getX();
                            	        int parenty = ((Location) layout).getY();
                            	        if (parentx - realDropLocation.x < 0) {
                            	            realDropLocation.x += - (parentx - realDropLocation.x);
                            	        }
                            	        if (parenty - realDropLocation.y < 0) {
                                            realDropLocation.y += - (parenty - realDropLocation.y);
                                        }
                            	    }
                            	}
                                if (bounds instanceof Location) {
                                    ((Location) bounds).setX(realDropLocation.x);
                                    ((Location) bounds).setY(realDropLocation.y);
                                }
                                ((Node) newView).setLayoutConstraint(
                                        (LayoutConstraint) bounds);
                            }
                        }
                        // we have collected the edges to retarget them later.
                        for (View oldEdge : edges.keySet()) {
                            View newEdge = edges.get(oldEdge);
                            View oldSource = ((Edge) oldEdge).getSource();
                            View oldTarget = ((Edge) oldEdge).getTarget();
                            View newSource = newNodes.get(oldSource);
                            if (newSource == null) {
                                
                            }
                            View newTarget = newNodes.get(oldTarget);
                            ((SequenceEdge) ((Edge) newEdge).getElement()).
                            	setTarget((Vertex) newTarget.getElement());
                            ((SequenceEdge) ((Edge) newEdge).getElement()).
                            	setSource((Vertex) newSource.getElement());
                            ((Edge) newEdge).setTarget(newTarget);
                            ((Edge) newEdge).setSource(newSource);
                        }
//                      } catch (Exception e) {
//                          e.printStackTrace();
//                      }
                    }
                };
//                try {
                domain.getCommandStack().execute(command);
                
//              finally we arrange the paerts selectively.
//              We try to arrange the parts more concerned, 
//              ie activities connected together.
                final List previousEditParts = new ArrayList(part.getChildren());
                

//              then we add a refresh command.
                Command refreshCo = part.getCommand(new Request(RequestConstants.REQ_REFRESH));
                if (refreshCo != null) {
                    refreshCo.execute();
                }

                List<IGraphicalEditPart> newEditParts = 
                    new ArrayList<IGraphicalEditPart>();
                for (Object editPart : part.getChildren()) {
                    if (!previousEditParts.contains(editPart)) {
                        newEditParts.add((IGraphicalEditPart) editPart);
                    }
                }

                ArrangeSelectionRecursivelyAction.arrange(newEditParts);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                return CommandResult.newOKCommandResult();
            }

        };
        return new ICommandProxy(viewCommand);
    }
    
    /**
     * By default, a view may be dropped on a pool or a subprocess compartment.
     * 
     * Feel free to override as needed.
     */
    public boolean isEnabled(IGraphicalEditPart hoverPart, int index) {
        return hoverPart.resolveSemanticElement() instanceof Graph && 
        (hoverPart instanceof SubProcessSubProcessBodyCompartmentEditPart ||
                hoverPart instanceof PoolPoolCompartmentEditPart);
    }
}
