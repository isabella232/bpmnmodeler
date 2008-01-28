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
 * 14 Nov 2006      MPeleshchyshyn      Created 
 **/
package org.eclipse.stp.bpmn.diagram.actions;

import java.util.ArrayList;
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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.ToolUtilities;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.SequenceEdgeEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

/**
 * This class implements functionality that moves tasks outside sub-process.
 * Subpocess itself is removed.
 * 
 * @author MPeleshchyshyn, atoulme, hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class UngroupAction extends AbstractGroupUngroupAction {
    
    public static final String ACTION_ID = "ungroupAction"; //$NON-NLS-1$
    public static final String TOOLBAR_ACTION_ID = "toolbarUngroupAction"; //$NON-NLS-1$
    protected static final String ICON_PATH = "icons/Ungroup.gif"; //$NON-NLS-1$
    protected static final String ACTION_TEXT = BpmnDiagramMessages.UngroupAction_label;
    protected static final String TOOLTIP_TEXT = BpmnDiagramMessages.UngroupAction_tooltip;

    /**
     * Holds processes to which children should be moved outside.
     */
    private List<SubProcessEditPart> subProcesses = new ArrayList<SubProcessEditPart>();

    public UngroupAction(IWorkbenchPage workbenchPage) {
        super(workbenchPage);
    }

    public UngroupAction(IWorkbenchPart workbenchPart) {
        super(workbenchPart);
    }

    private static UngroupAction createActionWithoutId(
            IWorkbenchPage workbenchPage) {
        UngroupAction action = new UngroupAction(workbenchPage);
        action.setText(ACTION_TEXT);
        action.setToolTipText(TOOLTIP_TEXT);
        action.setImageDescriptor(BpmnDiagramEditorPlugin.
                getBundledImageDescriptor(ICON_PATH));

        return action;
    }

    public static UngroupAction createUngroupAction(IWorkbenchPage workbenchPage) {
        UngroupAction action = createActionWithoutId(workbenchPage);
        action.setId(ACTION_ID);
        return action;
    }

    public static UngroupAction createToolbarUngroupAction(
            IWorkbenchPage workbenchPage) {
        UngroupAction action = createActionWithoutId(workbenchPage);
        action.setId(TOOLBAR_ACTION_ID);
        return action;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    @Override
    protected void doRun(IProgressMonitor progressMonitor) {
        GraphicalEditPart p = subProcesses.get(0);
    	TransactionalEditingDomain domain = p.getEditingDomain();
    	AbstractTransactionalCommand undoable = 
    		new AbstractTransactionalCommand(domain, BpmnDiagramMessages.UngroupAction_command_name, null) {

            private IDiagramGraphicalViewer viewer;
            //collection of the xmi ids of the edit parts selected
            //and their associated edit part classes.
            //used during redo to be able to update the editpart
            //as they changed during the execution and unexecution of things.
            private Map<String,Class> selectedEps = new HashMap<String, Class>();

            private Map<String,Map<String,Class>> selectedContentForUndo =
                new HashMap<String, Map<String,Class>>();
                //new HashMap<String, Class>();
            
            private Map<String,String> indexOfContainersOfSubProcessUngrouped =
                new HashMap<String, String>();
    	    
    		@Override
    		protected CommandResult doExecuteWithResult(
    				IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
    		    
    		    if (viewer == null) {
    		        //keep track of the edit parts to ungroup
    		        //next time this command is re-done.
    		        for (SubProcessEditPart sp : subProcesses) {
    		            if (viewer == null) {
    		                viewer = (IDiagramGraphicalViewer)sp.getViewer();
    		            }
                        String spId = EMFCoreUtil.getProxyID(sp.resolveSemanticElement());
                        String spContainerId = EMFCoreUtil.getProxyID(sp.resolveSemanticElement().eContainer());
                        indexOfContainersOfSubProcessUngrouped.put(spId, spContainerId);
    		            selectedEps.put(spId, sp.getClass());
    		            Map<String,Class> selectedContentForUndoOne = new HashMap<String,Class>();
    		            selectedContentForUndo.put(spId, selectedContentForUndoOne);
    		            
    		            for (Object cpt : sp.getChildren()) {
    		                //save enough things to identify the editparts
    		                //to group during the undo
    		                if (cpt instanceof ShapeCompartmentEditPart) {
    		                    for (Object child : ((ShapeCompartmentEditPart)cpt).getChildren()) {
    		                        selectedContentForUndoOne.put(EMFCoreUtil.getProxyID(
    		                                ((GraphicalEditPart) child).resolveSemanticElement()),
    		                                child.getClass());
    		                    }
    		                }
    		            }
    		        }
    		    } else {
    		        //the command is redone.
    		        //the edit parts are new.
    		        //let's locate them.
    		        subProcesses.clear();
                    for (Entry<String,Class> entry : selectedEps.entrySet()) {
                        List eps = viewer.findEditPartsForElement(entry.getKey(),
                                    entry.getValue());
                        if (!eps.isEmpty()) {
                            subProcesses.addAll(eps);
                        }
                    }
    		        
    		    }
    		    
    			for (SubProcessEditPart subProcess : subProcesses) {
    				executeUngroupCommand(subProcess);
    			}
    			
    			subProcesses.clear();
    			
    			return CommandResult.newOKCommandResult();
			}

    		/**
    		 * Forces redo to call doExecuteWithResult instead of the standard GMF-undo
    		 * which does corrupt the diagrams in complex situations.
    		 */
            @Override
            protected IStatus doRedo(IProgressMonitor monitor, IAdaptable info)
                    throws ExecutionException {
                doExecuteWithResult(monitor, info);
                return Status.OK_STATUS;
            }

            /**
             * Forces undo to call the group action instead of the standard GMF-undo
             * which does corrupt the diagrams in complex situations.
             */
            @Override
            protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info)
                    throws ExecutionException {
//                IStatus s = super.doUndo(monitor, info);
//                fixDamagedECoreMaps();
//                return s;
                selectedEps.clear();
                TransactionalEditingDomain domain =
                    ((GraphicalEditPart)viewer.getRootEditPart().getContents()).getEditingDomain();
                for (Entry<String,Map<String,Class>> entry : selectedContentForUndo.entrySet()) {
                    GroupInSubprocessCommand cmd = new GroupInSubprocessCommand(domain,
                        viewer, entry.getValue(),
                        indexOfContainersOfSubProcessUngrouped.get(entry.getKey()));
                    cmd.doRedo(monitor, info);
                    selectedEps.put(cmd._createdSubprocessProxyId, SubProcessEditPart.class);
                }
                return Status.OK_STATUS;
            }
            
            
    	};
        
        p.getDiagramEditDomain().getDiagramCommandStack().execute(
        		new ICommandProxy(undoable));
        
    }

    /**
     * Method that ungroups shapes in a  subprocess passed
     * as an argument.
     * Should be executed in a transaction.
     * @param spEditPart
     */
    static void executeUngroupCommand(SubProcessEditPart spEditPart) {
    	SubProcessSubProcessBodyCompartmentEditPart tempComp = null;
    	for (Object ep : spEditPart.getChildren()) {
    		if (ep instanceof SubProcessSubProcessBodyCompartmentEditPart) {
    			tempComp = (SubProcessSubProcessBodyCompartmentEditPart) ep;
    			break;
    		}
    	}
    	if(tempComp == null) {
    	    return;
    	}
    	SubProcessSubProcessBodyCompartmentEditPart compartmentEditPart = tempComp;

    	// get the children views.
    	Set<EObject> semantic = new HashSet<EObject>();
    	Set<View> views = new HashSet<View>();
        Set<EObject> edges = new HashSet<EObject>();
        
//   	 we collect the connection edit parts that will be removed
//   	 to refresh them explicitly
   	    Set<EditPart> removed = new HashSet<EditPart>();  	
    	for (Object part : compartmentEditPart.getChildren()) {
    	    IGraphicalEditPart gpart = (IGraphicalEditPart)part;
    	    EObject v = gpart.resolveSemanticElement();
            if (v instanceof Vertex) {
                edges.addAll(((Vertex) v).getIncomingEdges());
                edges.addAll(((Vertex) v).getOutgoingEdges());
            }
    		semantic.add(v);
    		if (gpart.getModel() instanceof Node) {
    			views.add((View) gpart.getModel());
                for (Object conn : gpart.getSourceConnections()) {
                    ConnectionEditPart connEp = (ConnectionEditPart)conn;
                    EObject model = ((View)connEp.getModel());
                    if (model != null && model instanceof SequenceEdge/* && edges.contains(model)*/) {
                        removed.add(connEp);
                    }
                }
                for (Object conn : gpart.getTargetConnections()) {
                    ConnectionEditPart connEp = (ConnectionEditPart)conn;
                    EObject model = ((View)connEp.getModel());
                    if (model != null && model instanceof SequenceEdge/* && edges.contains(model)*/) {
                        removed.add(connEp);
                    }
                }
    		} 
    	}
    	
    	semantic.addAll(edges);
    	// really not interested into empty subprocesses.
    	if (semantic.isEmpty()) {
    		return;
    	}
    	// get the location of the subprocess
    	Location location = (Location) ((Node) spEditPart.
    			getNotationView()).getLayoutConstraint();
    	
    	// resolve the semantic element that will be the new container.
    	View targetContainer = ((IGraphicalEditPart) spEditPart.
    			getParent()).getNotationView();

    	// populate the list of children without incoming or outgoing edges
    	List<IGraphicalEditPart> startTasksInSubProcess = new LinkedList<IGraphicalEditPart>();
    	List<IGraphicalEditPart> endTasksInSubProcess = new LinkedList<IGraphicalEditPart>();
    	
    	getStartAndEndTasks(spEditPart, endTasksInSubProcess, startTasksInSubProcess);

    	// before moving the activities, we remove their edges
    	// so that the diagram is valid all the time.
    	Map<Activity, List<SequenceEdge>> inEdges = new HashMap<Activity, List<SequenceEdge>>();
		Map<Activity, List<SequenceEdge>> outEdges = new HashMap<Activity, List<SequenceEdge>>();
		for (EObject obj : semantic) {
			if (obj instanceof Activity) {
				Activity act = (Activity) obj;
				inEdges.put(act, new ArrayList<SequenceEdge>(act.getIncomingEdges()));
				outEdges.put(act, new ArrayList<SequenceEdge>(act.getOutgoingEdges()));
				act.getIncomingEdges().clear();
				act.getOutgoingEdges().clear();
			}
		}
		Graph newGraphContainer = (Graph) targetContainer.getElement();
		
        for (EObject obj : semantic) {
            //first change the scope of dataobjects.
            //it is important to remove the scope of the dataobject
            //as dataobject are sometimes managed by the activities they are
            //associated with
            //the fact that they don't have a container during this part of the
            //operation will make sure that they don't get removed.
            if (obj instanceof Artifact) {
                ((Artifact)obj).setArtifactsContainer(null);
            }
        }

    	for (EObject obj : semantic) {
    		if (obj instanceof Activity) {
    			((Activity) obj).setGraph(newGraphContainer);
    		} else if (obj instanceof SequenceEdge) {
                ((SequenceEdge)obj).setGraph(newGraphContainer);
            } else if (obj instanceof Artifact) {
                //already done.
            } else if (obj instanceof Association) {
                //nothing to do my friend. that is because they are contained by
                //the dataobject
            } else {
              //this looks very bad to me: ie unlikely to do any good.
    			targetContainer.getElement().eContents().add(obj);
    		}
    	}
    	
        for (EObject obj : semantic) {
            //now set the scope of the dataobjects
            if (obj instanceof Artifact) {
                ((Artifact)obj).setArtifactsContainer(newGraphContainer);
            }
        }

    	
    	for (EObject obj : semantic) {
			if (obj instanceof Activity) {
				Activity act = (Activity) obj;
				act.getIncomingEdges().addAll(inEdges.get(act));
				act.getOutgoingEdges().addAll(outEdges.get(act));
			}
		}
    	
    	
    	for (View v : views) {
    		spEditPart.getNotationView().removeChild(v);
    		targetContainer.insertChild(v);
    		if (v instanceof Node) {
    			Location constraint = (Location) ((Node) v).getLayoutConstraint();
    			if (constraint != null) {
    			    if (constraint.getX() != -1) {
    			        constraint.setX(constraint.getX() + location.getX());
    			    }
    			    if (constraint.getY() != -1) {
    			        constraint.setY(constraint.getY() + location.getY());
    			    }
    			    ((Node) v).setLayoutConstraint(constraint);
    			} else {
    			    //System.err.println("humf: a node without constraints?");
    			    //we do need a constraint otherwise it seems to crash
    			    //sometimes later. we use a default constraint.
    			    ((Node) v).setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
    			}
    		}
    	}

    	Command command = spEditPart.getParent().getCommand(new Request(RequestConstants.REQ_REFRESH)); 
    	command.execute();

    	// retargeting connections.
try {
    	if (startTasksInSubProcess.size() == 1) { 
    		// limiting the reconnection case to 1 shape.
    	    for (Object edge : spEditPart.getTargetConnections()) {
    			if (!(edge instanceof SequenceEdgeEditPart)) {
    				continue;
    			}
    			SequenceEdgeEditPart sEdge = (SequenceEdgeEditPart) edge;
    			for (IGraphicalEditPart part : startTasksInSubProcess) {
    				((SequenceEdge) sEdge.resolveSemanticElement()).
    					setTarget((Vertex) part.resolveSemanticElement());
    				((Edge) sEdge.getNotationView()).
    					setTarget(part.getNotationView());
    			}
    		}
    	}
    	if (endTasksInSubProcess.size() == 1) { 
    		// limiting the reconnection case to 1 shape.
    		for (Object edge : spEditPart.getSourceConnections()) {
    			if (!(edge instanceof SequenceEdgeEditPart)) {
    				continue;
    			}
    			SequenceEdgeEditPart sEdge = (SequenceEdgeEditPart) edge;
    			for (IGraphicalEditPart part : endTasksInSubProcess) {
    				((SequenceEdge) sEdge.resolveSemanticElement()).
    					setSource((Vertex) part.resolveSemanticElement());
    				((Edge) sEdge.getNotationView()).
    					setSource(part.getNotationView());
    			}
    		}
    	}
} catch (Exception e) {
    e.printStackTrace();
}

    	command = spEditPart.getParent().getCommand(new Request(RequestConstants.REQ_REFRESH)); 
    	command.execute();
    	
    	EditPart parent = spEditPart.getParent();
    	// finally destroy the subprocess.
    	DestroyElementRequest request = new DestroyElementRequest(spEditPart.resolveSemanticElement(), false);
    	Command destroy = spEditPart.getCommand(new EditCommandRequestWrapper(request));
    	destroy.execute();
    	
    	// refreshing the result, so that the edit parts are removed.
    	command = parent.getCommand(new Request(RequestConstants.REQ_REFRESH)); 
    	command.execute();
    	
    	for (Object r : removed) {
    		((EditPart) r).refresh();
    		((EditPart) r).activate();
    	}
    	    	
    }

    /**
     * Searches possible end (without target sequence connections) and start
     * (without source sequence connections) elements inside the specified
     * sub-process' children. Found edit parts are added to the the specified
     * startElements and endElements lists (should be passed from the calling
     * method).
     * <p>
     * Also collect the immediatePredecessors and immediateFollowers of the sub-process editpart.
     * </p>
     * @param subProcessEditPart
     *            the subprocess edit part
     * @param startElements
     *            holds elements without source sequence connections
     * @param endElements
     *            holds elements without target sequence connections
     * @param immediatePredecessors collector for the activities that have an outgoing sequence
     * edge targetting the subProcessEditPart
     * @param immediatePredecessors collector for the activities that have an incoming sequence
     * edge that source is the subProcessEditPart
     */
    private static void getStartAndEndTasks(
            SubProcessEditPart subProcessEditPart,
            List<IGraphicalEditPart> startElements,
            List<IGraphicalEditPart> endElements) {
        List children = subProcessEditPart.getChildBySemanticHint(
                        String.valueOf(SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID))
                     .getChildren();
        for (Object object : children) {
            IGraphicalEditPart editPart = (IGraphicalEditPart) object;
            Object sem = editPart.resolveSemanticElement();
            if (sem == null || !(sem instanceof Vertex)) {
                continue;//data-objects and other things are not concerned.
            }
            List sourceConnections = editPart.getSourceConnections();
            boolean noConnections = true;
            if (!sourceConnections.isEmpty()) {
                for (Object connection : sourceConnections) {
                    if (connection instanceof SequenceEdgeEditPart) {
                        // incoming sequence connection exists so this is not
                        // start task
                        noConnections = false;
                        break;
                    }
                }
            }
            if (noConnections) {
                startElements.add(editPart);
            }
            // now test for outgoing connections
            List targetConnections = editPart.getTargetConnections();
            noConnections = true;
            if (!targetConnections.isEmpty()) {
                for (Object connection : targetConnections) {
                    if (connection instanceof SequenceEdgeEditPart) {
                        // outgoing sequence connection exists so this is not
                        // end task
                        noConnections = false;
                        break;
                    }
                }
            }
            if (noConnections) {
                endElements.add(editPart);
            }
        }
    }

    /*
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void refresh() {
        IStructuredSelection structuredSelection = getStructuredSelection();
        refresh(structuredSelection);
    }
    
    private void refresh(IStructuredSelection structuredSelection) {
        subProcesses.clear();
        if (structuredSelection != null) {
            for (Object selElement : structuredSelection.toList()) {
                if (selElement instanceof ShapeNodeEditPart) {
                    EditPart editPart = (EditPart) selElement;
                    // select parent subprocess (or object itselt if it is
                    // subprocess)
                    SubProcessEditPart subProcess = getSubProcess(editPart);
                    if (subProcess != null
                            && !subProcesses.contains(subProcess)) {
                    	SubProcess sp = (SubProcess) subProcess.resolveSemanticElement();
                    	if (sp.getEventHandlers().isEmpty()) {
                    	    if (!sp.getVertices().isEmpty() ||
                    	            !sp.getArtifacts().isEmpty()) {
                    	        subProcesses.add(subProcess);
                    	    }
                    	}
                    }
                }
            }
            // now remove enclosed subprocesses
            subProcesses = ToolUtilities
                    .getSelectionWithoutDependants(subProcesses);
        }
        setEnabled(!subProcesses.isEmpty());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
     *      org.eclipse.ui.IWorkbenchPart)
     */
    public void setActivePart(IAction action, IWorkbenchPart targetPart) {
        // TODO Auto-generated method stub
    }
}
