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
 * Date                 Author              Changes 
 * 17 Nov 2006      MPeleshchyshyn      Created 
 **/
package org.eclipse.stp.bpmn.diagram.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeCompartmentEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.Activity2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.LaneEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SequenceEdgeEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBorderCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditDomain;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditDomain.IEditPartSelectionFilter;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

/**
 * This class groups actions
 * 
 * @author MPeleshchyshyn
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class GroupAction extends AbstractGroupUngroupAction {
    
    /**
     * the action id declared in the plugin.xml
     */
    public static final String ACTION_ID = "groupAction"; //$NON-NLS-1$

    /**
     * the action id for the toolbar action declared in
     * the plugin.xml
     */
    public static final String TOOLBAR_ACTION_ID = "toolbarGroupAction"; //$NON-NLS-1$

    /**
     * the icon for the group action.
     */
    protected static final String ICON_PATH = "icons/Group.gif"; //$NON-NLS-1$

    /**
     * the text displayed by the action.
     */
    protected static final String ACTION_TEXT = BpmnDiagramMessages.GroupAction_label;

    /**
     * the text displayed by the tooltip of the toolbar action.
     */
    protected static final String TOOLTIP_TEXT = BpmnDiagramMessages.GroupAction_tooltip;

    /**
     * Default constructor with a IWorkbenchPage
     * to be attached to.
     * @param workbenchPage the page
     */
    public GroupAction(IWorkbenchPage workbenchPage) {
        super(workbenchPage);
    }

    /**
     * Default constructor with a IWorkbenchPart
     * object to be attached to
     * @param workbenchPart the part
     */
    public GroupAction(IWorkbenchPart workbenchPart) {
        super(workbenchPart);
    }

    /**
     * Creates the action
     * @param workbenchPage
     * @return the action, without an ID set yet.
     */
    private static GroupAction createActionWithoutId(
            IWorkbenchPage workbenchPage) {
        GroupAction action = new GroupAction(workbenchPage);
        action.setText(ACTION_TEXT);
        action.setToolTipText(TOOLTIP_TEXT);
        action.setImageDescriptor(BpmnDiagramEditorPlugin
                .getBundledImageDescriptor(ICON_PATH));

        return action;
    }

    /**
     * Creates the popup menu group action and attach it
     * to the page passed in parameter.
     * @param workbenchPage the page
     */
    public static GroupAction createGroupAction(IWorkbenchPage workbenchPage) {
        GroupAction action = createActionWithoutId(workbenchPage);
        action.setId(ACTION_ID);

        return action;
    }

    /**
     * Creates the toolbar menu action and attach it to
     * the page passed in parameter
     * @param workbenchPage
     * @return the action
     */
    public static GroupAction createToolbarGroupAction(
            IWorkbenchPage workbenchPage) {
        GroupAction action = createActionWithoutId(workbenchPage);
        action.setId(TOOLBAR_ACTION_ID);

        return action;
    }

    /**
     * Edit parts to be grouped
     */
    private Set<GraphicalEditPart> editParts = new HashSet<GraphicalEditPart>();
    
    /**
     * Edit part that are boundary events of a sub-process that belongs to the shapes being grouped.
     * It is necessary to consider those when computing the internal connections.
     */
    private List<IGraphicalEditPart> almostSelectedBoundaryEvents = new ArrayList<IGraphicalEditPart>();
    /** External source connections */
    private Collection<SequenceEdgeEditPart> externalSrcConnections = new ArrayList<SequenceEdgeEditPart>();
    /** External target connections */
    private Collection<SequenceEdgeEditPart> externalTgtConnections = new ArrayList<SequenceEdgeEditPart>();
    /** Internal connections - between edit parts to be grouped */
    private Collection<SequenceEdgeEditPart> internalConnections = new HashSet<SequenceEdgeEditPart>();

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    @Override
    protected void doRun(final IProgressMonitor progressMonitor) {

    	IGraphicalEditPart firstEditPart = editParts.iterator().next();
    	AbstractTransactionalCommand groupCommand =
    	    new GroupInSubprocessCommand(firstEditPart.getEditingDomain(), editParts, 
    	            almostSelectedBoundaryEvents,
    	            externalSrcConnections, externalTgtConnections,
    	            internalConnections);


    	firstEditPart.getDiagramEditDomain().
    			getDiagramCommandStack().execute(
    				new ICommandProxy(groupCommand));
    }



    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void refresh() {

        IStructuredSelection strSelection = getStructuredSelection();
        refresh(this, strSelection, editParts, almostSelectedBoundaryEvents,
                externalSrcConnections, externalTgtConnections,
                internalConnections);
        
    }
    
    static void refresh(Action action,
            IStructuredSelection strSelection,
            Set<GraphicalEditPart> editParts,
            List<IGraphicalEditPart> almostSelectedBoundaryEvents,
            Collection<SequenceEdgeEditPart> externalSrcConnections,
            Collection<SequenceEdgeEditPart> externalTgtConnections,
            Collection<SequenceEdgeEditPart> internalConnections) {
        editParts.clear();
        almostSelectedBoundaryEvents.clear();
        internalConnections.clear();
        externalSrcConnections.clear();
        externalTgtConnections.clear();
        
        //EDGE-2030: get the sub-processes selected.
        //any element that is selected and that is contained inside one of
        //those selected sub-processes will be removed from the selection to group.
        Set<SubProcess> selectedSubProcesses = null;
        
        List selectionEp = strSelection.toList();
        for (Object editPart : selectionEp) {
            if (editPart instanceof SubProcessEditPart) {
                SubProcess sp = (SubProcess)
                    ((SubProcessEditPart)editPart).resolveSemanticElement();
                if (selectedSubProcesses == null) {
                    selectedSubProcesses = new HashSet<SubProcess>();
                }
                selectedSubProcesses.add(sp);
            }
        }
        
        for (Object editPart : selectionEp) {
            if (editPart instanceof GraphicalEditPart
                  &&  !(editPart instanceof Activity2EditPart)
                  &&  !(editPart instanceof ITextAwareEditPart)
                  &&  !(editPart instanceof ShapeCompartmentEditPart)//this will skip the labels
                    ) {
                if (selectedSubProcesses != null) {
                    //make sure the shape is not contained in a selected sub-process (EDGE-2030)
                    EObject eo = (EObject) ((GraphicalEditPart)editPart).resolveSemanticElement();
                    if (eo != null && selectedSubProcesses.contains(eo.eContainer())) {
                        continue;
                    }
                }
                //this will not take into account the selected connections
                editParts.add((GraphicalEditPart) editPart);
            }
        }

        EditPart parentContainer = null;
        Iterator<GraphicalEditPart> iterator = editParts.iterator();
        boolean onlyArtifacts = true;
        while (iterator.hasNext()) {
            GraphicalEditPart editPart = iterator.next();
            EditPart container = editPart.getParent();
            if (!(editPart.resolveSemanticElement() instanceof Artifact)) {
            	onlyArtifacts = false;
            } else {
                continue;
            }
            if (editPart instanceof LaneEditPart
                    || !(container instanceof PoolPoolCompartmentEditPart)
                    && !(container instanceof SubProcessSubProcessBodyCompartmentEditPart)) {
                editParts.clear();
                almostSelectedBoundaryEvents.clear();
                break;
            }
            if (container instanceof SubProcessSubProcessBodyCompartmentEditPart) {
                if (isInsideAnother(container, editParts)) {
                    iterator.remove();
                    continue;
                }
            }
            if (editPart instanceof SubProcessEditPart) {
                //add the boundary events to the selection
                SubProcessSubProcessBorderCompartmentEditPart borderEditPart =
                    (SubProcessSubProcessBorderCompartmentEditPart)
                ((SubProcessEditPart)editPart)
                    .getChildBySemanticHintOnPrimaryView(
                            SubProcessSubProcessBorderCompartmentEditPart.VISUAL_ID + ""); //$NON-NLS-1$
                if (borderEditPart != null) {
                    for (Object child : borderEditPart.getChildren()) {
                        if (child instanceof Activity2EditPart) {
                            almostSelectedBoundaryEvents.add((IGraphicalEditPart) child);
                        }
                    }
                }
            }
            
           
            if (parentContainer == null) {
                parentContainer = container;
            } else if (parentContainer != container) {
                editParts.clear();
                almostSelectedBoundaryEvents.clear();
                break;
            }
        }
        if (onlyArtifacts) {
        	editParts.clear();
        }
//        //group is not enabled if 1 or less edit parts are selected
//        //always group more than one edit part.
//        if (editParts.size() == 1) {
//            editParts.clear();
//            almostSelectedBoundaryEvents.clear();
//        }

        for (IGraphicalEditPart editPart : editParts) {
            sortConnections(editPart, editParts, almostSelectedBoundaryEvents,
                    internalConnections,
                    externalSrcConnections, externalTgtConnections);
        }

        EditPart element = null;
        for (SequenceEdgeEditPart connection : externalSrcConnections) {
            EditPart currElement = connection.getSource();
            if (element == null) {
                element = currElement;
            } else if (element != currElement) {
                if (action != null)  action.setEnabled(false);
                return;
            }
        }
        element = null;
        for (SequenceEdgeEditPart connection : externalTgtConnections) {
            EditPart currElement = connection.getTarget();
            if (element == null) {
                element = currElement;
            } else if (element != currElement) {
                if (action != null)  action.setEnabled(false);
                return;
            }
        }

        for (GraphicalEditPart editPart : editParts) {
        	if (lookForIgnoredSequenceMembers(editPart, editParts, false, new HashSet<EditPart>())) {
        		editParts.clear();
        		break;
        	}
        }
        
        for (GraphicalEditPart editPart : editParts) {
            if (editPart instanceof IGraphicalEditPart && 
                    ((IGraphicalEditPart) editPart).resolveSemanticElement() instanceof Activity &&
                    ActivityType.VALUES_GATEWAYS.contains(
                            ((Activity) ((IGraphicalEditPart) editPart).
                                    resolveSemanticElement()).getActivityType())) {
                if (lookForIgnoredGatewayBranch(editPart, editParts)) {
                    editParts.clear();
                    break;
                }
            }
            
        }
        if (action != null)  action.setEnabled(!editParts.isEmpty());
    }

    /**
     * Checks if all the merging or forking branches of the gateway
     * are in the selection if at least one of them is
     * in the selection
     * @param editPart the edit part of the gateway
     * @param editParts the set of selected nodes.
     * @return true if a branch is ignored, false
     * otherwise.
     */
    private static boolean lookForIgnoredGatewayBranch(
            GraphicalEditPart editPart, Set<GraphicalEditPart> editParts) {
        boolean isMerge = false;
        Activity gateway = (Activity) editPart.resolveSemanticElement();
        
        if (gateway.getIncomingEdges().size() > 1) {
            isMerge = true;
        } else if (gateway.getOutgoingEdges().size() > 1) {
            isMerge = false;
        } else {
            // neither a merge nor a fork so we don't care.
            return false;
        }
        List eps = new ArrayList();
        if (isMerge) {
            for (Object ep : editPart.getTargetConnections()) {
                if (ep instanceof SequenceEdgeEditPart) {
                    eps.add(((SequenceEdgeEditPart) ep).getSource());
                }
            }
        } else {
            for (Object ep : editPart.getSourceConnections()) {
                if (ep instanceof SequenceEdgeEditPart) {
                    eps.add(((SequenceEdgeEditPart) ep).getTarget());
                }
            }
        }
        
        boolean oneSelected = false;
        boolean oneNotSelected = false;
        
        for (Object ep : eps) {
            if (editParts.contains(ep)) {
                oneSelected = true;
            } else {
                oneNotSelected = true;
            }
        }
        
        return oneSelected && oneNotSelected;
    }

    /**
     * Checks all source and target connections of the specified edit part and
     * sets it into one of the specified collections. If connection connects the
     * specified object with some other selected element it is added to internal
     * connection. In other case it is added to external source or external
     * target connections.
     * 
     * @param editPart
     *            the edit part
     * @param internalConnections
     *            set that holds internal connections
     * @param externalSrcConnection
     *            holds external source connections
     * @param externalTgtConnections
     *            holds external target connections
     */
    static void sortConnections(IGraphicalEditPart editPart,
            Set<GraphicalEditPart> editParts,
            List<IGraphicalEditPart> almostSelectedBoundaryEvents,
            Collection<SequenceEdgeEditPart> internalConnections,
            Collection<SequenceEdgeEditPart> externalSrcConnection,
            Collection<SequenceEdgeEditPart> externalTgtConnections) {
        List srcConnections = editPart.getSourceConnections();
        for (Object connection : srcConnections) {
            if (connection instanceof SequenceEdgeEditPart) {
                SequenceEdgeEditPart sequenceConnection = (SequenceEdgeEditPart) connection;
                if (editParts.contains(sequenceConnection.getTarget()) ||
                        almostSelectedBoundaryEvents.contains(
                                sequenceConnection.getTarget())) {
                    internalConnections.add(sequenceConnection);
                } else {
                    externalSrcConnection.add(sequenceConnection);
                }
            }
        }
        List targetConnections = editPart.getTargetConnections();
        for (Object connection : targetConnections) {
            if (connection instanceof SequenceEdgeEditPart) {
                SequenceEdgeEditPart sequenceConnection = (SequenceEdgeEditPart) connection;
                if (editParts.contains(sequenceConnection.getSource()) ||
                        almostSelectedBoundaryEvents.contains(
                                sequenceConnection.getSource())) {
                    internalConnections.add(sequenceConnection);
                } else {
                    externalTgtConnections.add(sequenceConnection);
                }
            }
        }
    }


    
    /**
     * Detects that a task is in the sequence, ignored in the selection
     * @param editPart the edit part to follow
     * @param metIgnoredShapes if already detected an ignored shape
     * @return true if there was an edit part ignored between 
     * two selected edit parts
     * 
     * 
     */
    static boolean lookForIgnoredSequenceMembers(GraphicalEditPart editPart, 
            Set<GraphicalEditPart> editParts,
    		boolean metIgnoredShapes, Set<EditPart>  visited) {
    	visited.add(editPart);
    	boolean foundIgnored = false;
		for (Object connection : editPart.getTargetConnections()) {
			if (connection instanceof SequenceEdgeEditPart) {
				GraphicalEditPart part = (GraphicalEditPart) ((ConnectionEditPart) 
							connection).getSource();
				if (editParts.contains(part)) {
					if (metIgnoredShapes) {
						return true;
					}
				} else {
					metIgnoredShapes = true;
				}
				if (visited.contains(part)) {
					return false;
				}
				foundIgnored = foundIgnored || lookForIgnoredSequenceMembers(
						part, editParts, metIgnoredShapes, visited);
			}
		}
		return foundIgnored;
	}

	/**
     * Determines whether specified edit part is a child of ony other edit part
     * (in undefined depth)
     * 
     * @param editPart
     *            the edit part to test
     * @param editParts
     *            other edit parts
     * @return <code>true</code> if specified edit part is child of ony other
     *         edit part (in undefined depth), <code>false</code> otherwise.
     */
    private static boolean isInsideAnother(EditPart editPart, Set<GraphicalEditPart> editParts) {
        SubProcessEditPart subProcess = getSubProcess(editPart);
        while (subProcess != null) {
            if (editParts.contains(subProcess)) {
                return true;
            }
            subProcess = getSubProcess(subProcess.getParent());
        }
        return false;
    }
    
    /**
     * Retrieves the current selection.
     * We override the method to enable other editors to modify the selection
     * to be grouped.
     * 
     * @return The current selection.
     */
    @Override
    protected ISelection getSelection() {
        ISelection sel = super.getSelection();
        if (sel != null && sel instanceof IStructuredSelection) {
            IStructuredSelection structSel = (IStructuredSelection)sel;
            if (structSel.getFirstElement() instanceof GraphicalEditPart) {
                EditDomain domain = ((GraphicalEditPart)structSel.getFirstElement()).getViewer().getEditDomain();
                if (domain instanceof BpmnDiagramEditDomain) {
                    IEditPartSelectionFilter filter =
                        ((BpmnDiagramEditDomain) domain).getActionSelectionFilter();
                        if (filter == null) {
                            return sel;
                        }
                        return filter.filterSelection(ACTION_ID, structSel);
                }
            }
        }
        return sel;
    }
    

}