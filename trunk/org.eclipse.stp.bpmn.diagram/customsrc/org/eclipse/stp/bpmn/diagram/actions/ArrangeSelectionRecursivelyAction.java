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
 * Dates       		 Author              Changes
 * Jan 26, 2007      Antoine Toulm&eacute;   Creation
 */
package org.eclipse.stp.bpmn.diagram.actions;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.ArrangeRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

/**
 * Extension for the Arrange selection action. The big difference here is
 * that it goes down the compartments and arranges them recursively
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ArrangeSelectionRecursivelyAction extends DiagramAction {

	public static final String ID = "ArrangeSelectionRecursivelyAction"; //$NON-NLS-1$
	
	public ArrangeSelectionRecursivelyAction(IWorkbenchPage workbenchPage) {
		super(workbenchPage);
		setId(ID);
		setText(BpmnDiagramMessages.ArrangeSelectionRecursivelyAction_arrangeSelection);
	}

	public ArrangeSelectionRecursivelyAction(IWorkbenchPart workbenchPart) {
		super(workbenchPart);
		setId(ID);
		setText(BpmnDiagramMessages.ArrangeSelectionRecursivelyAction_arrangeSelection);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#createTargetRequest()
	 */
	@Override
	public Request createTargetRequest() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#isSelectionListener()
	 */
	@Override
	protected boolean isSelectionListener() {
		return true;
	}

	/**
	 * Selects the views representing
	 * activities 
	 * contained by the container.
	 * Delegates to the children that neither are corresponding to activities
	 * or sequence edges to look further into their children by 
	 * recursively calling activityRecursiveSearch.
	 * @param activities
	 * @param container
	 * @param onlyAccepted for the first level of recursivity
	 * we only accept the shapes that are selected explicitly
	 */
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	private void nodeRecursiveSearch(Map activities, List containers, 
			View container, List onlyAccepted) {
		if (!containers.contains(container)) {
			containers.add(0,container);
		}
		if (activities.get(container) == null) {
			activities.put(container, new LinkedList());
		}
		for (Object view : container.getChildren()) {
			if (view instanceof Node) {
				if (((View) view).getElement() != null && ((View) view).getElement() instanceof Vertex) {
					if (((Vertex) ((View) view).getElement()).getGraph() == null) {
						// activities on the border of a subprocess.
						continue;
					}
				}
				if (onlyAccepted != null && !onlyAccepted.contains(view)) {
					continue;
				}
				((List) activities.get(container)).add(view);
			}
			if (((View) view).getElement() instanceof Graph) {
				nodeRecursiveSearch(activities, containers, ((View) view), null);
			}
		}
	}

	/**
	 * Returns a compound command that arranges the children of the selection
	 * The action must be provided with a selection of IGraphicalEditParts to
	 * arrange them.
	 */
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	@Override
	public Command getCommand() {
		if (getSelectedObjects().isEmpty()) {
			return null;
		}

		CompoundCommand compoundArrange = new CompoundCommand();



		Map activities = new HashMap();
		List containers = new LinkedList();

		List<View> onlyAccepted = new LinkedList<View>();
		Set<View> firstLevelContainers = new HashSet<View>();
		for (Object editPart : getSelectedObjects()) {
			if (editPart instanceof IGraphicalEditPart) {
				View v = ((IGraphicalEditPart) editPart).getNotationView();
				onlyAccepted.add(v);
                // when undoing parents are instance of ChangeDescription
                if (v.eContainer() instanceof View) {
				firstLevelContainers.add((View) v.eContainer());
                }
			} else {
				return null;
			}
		}
		
		for (View firstLevelContainer : firstLevelContainers) {
            if (firstLevelContainer != null) {
                nodeRecursiveSearch(activities, containers,
						firstLevelContainer, onlyAccepted);
            }
		}
		
		// arrange all the nodes by their container in a HashMap
		// with an inversed ordered list for the containers.
		// this way we will arrange the inner compartments first.
		if (((EditPart) getSelectedObjects().get(0)).getRoot() == null ||
				((EditPart) getSelectedObjects().get(0)).
				getRoot().getViewer() == null) {
			// it seems that during the Arrange action,
			// the viewer was disposed.
			return null;
		}
		Map epRegistry = ((EditPart) getSelectedObjects().get(0)).
		getRoot().getViewer().getEditPartRegistry();

		// we try to get all of the edit parts associated with the views.
		for (Object container : containers) {
			ArrangeRequest arrangeRequest = new ArrangeRequest(
					ActionIds.ACTION_ARRANGE_SELECTION);
			List editParts = new LinkedList();
			for (Object activity : ((List) activities.get(container))) {
				Object ep = epRegistry.get(activity);
				if (ep instanceof Collection) { 
					editParts.addAll((Collection) ep); 
				} else if (ep != null){
					editParts.add(ep);
				}
			}

			if (editParts.isEmpty()) {
				continue;
			}
			arrangeRequest.setPartsToArrange(editParts);
			Command co = ((IGraphicalEditPart) editParts.get(0)).
			getCommand(arrangeRequest);
			compoundArrange.add(co);
		}
		return compoundArrange;
	}

	/**
	 * Static method that does what the action does, 
	 * without setting the selection explicity.
	 * Handy when arranging things programmatically.
	 * 
	 * Beware of using it without a diagram open, 
	 * or if the workbench is disposed.
	 * @param editParts
	 */
	public static void arrange(final List<? extends EditPart> editParts) {

		if (editParts.isEmpty()) {
			return;
		}
		IWorkbenchPage page = null;
		if (PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null) {
			page = PlatformUI.getWorkbench().
			getActiveWorkbenchWindow().getActivePage();
		} else {
			if (PlatformUI.getWorkbench().
					getWorkbenchWindowCount() != 0) {
				page = PlatformUI.getWorkbench().
				getWorkbenchWindows()[0].getActivePage();
			}
		}
		if (page == null) {
			throw new IllegalArgumentException(BpmnDiagramMessages.ArrangeSelectionRecursivelyAction_no_page_to_initialize);
		}
		ArrangeSelectionRecursivelyAction action = 
			new ArrangeSelectionRecursivelyAction(page) {
			@SuppressWarnings("unchecked") //$NON-NLS-1$
			@Override
			protected List getSelectedObjects() {
				return editParts;
			}
		};
		IGraphicalEditPart gmfEditPart = null;
		for (EditPart ep : editParts) {
			if (ep instanceof IGraphicalEditPart) {
				gmfEditPart = (IGraphicalEditPart) ep;
				break;
			}
		}
		if (gmfEditPart == null) {
			for (Object childEp : editParts.get(0).getChildren()) {
				if (childEp instanceof IGraphicalEditPart) {
					gmfEditPart = (IGraphicalEditPart) childEp;
					break;
				}
			}
		} 
		if (gmfEditPart == null) {
			throw new IllegalArgumentException(BpmnDiagramMessages.ArrangeSelectionRecursivelyAction_no_part_available);
		}
		gmfEditPart.getDiagramEditDomain().
		getDiagramCommandStack().execute(action.getCommand());
	}
}
