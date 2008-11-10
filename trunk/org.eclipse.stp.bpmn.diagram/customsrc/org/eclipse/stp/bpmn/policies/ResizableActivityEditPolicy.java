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
 * Date           	Author              Changes 
 * 12 Sep 2006   	MPeleshchyshyn  	Created 
 **/
package org.eclipse.stp.bpmn.policies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.handles.AbstractHandle;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Group;
import org.eclipse.stp.bpmn.Lane;
import org.eclipse.stp.bpmn.commands.ElementTypeEx;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityName2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityNameEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.Group2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.GroupEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.LaneEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.figures.BpmnShapesDefaultSizes;
import org.eclipse.stp.bpmn.tools.ActivityResizeTracker;
import org.eclipse.stp.bpmn.tools.TaskDragEditPartsTrackerEx;

/**
 * This edit policy helps resizing activities and handles the changes of groups.
 * 
 * @author MPeleshchyshyn
 * @author Antoine Toulme
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ResizableActivityEditPolicy extends ResizableShapeEditPolicyEx {
    
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.stp.bpmn.policies.ResizableShapeEditPolicyEx#createHandle(org.eclipse.gef.GraphicalEditPart,
     *      int)
     */
    protected Handle createHandle(GraphicalEditPart owner, int direction) {
        return new ActivityResizeHandle(owner, direction);
    }

    /**
     * Creates new handle for activity.
     */
    protected static class ActivityResizeHandle extends ResizeHandleEx {
        public ActivityResizeHandle(GraphicalEditPart owner, int direction) {
            super(owner, direction);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.stp.bpmn.policies.ResizableShapeEditPolicyEx.ResizeHandleEx#createDragTracker()
         */
        @Override
        protected DragTracker createDragTracker() {
            return new ActivityResizeTracker(getOwner(), cursorDirection);
        }
    }

    @Override
    protected void replaceHandleDragEditPartsTracker(Handle handle) {
        if (handle instanceof AbstractHandle) {
            AbstractHandle h = (AbstractHandle) handle;
            h.setDragTracker(new TaskDragEditPartsTrackerEx((IGraphicalEditPart)getHost()));
        }
    }
    
    /**
     * Overidden the default behaviour to have the name edit part
     * being excluded from the computation of the size, when 
     * the activity is a gateway or an event.
     * 
     */
    @Override
    protected Command getAutoSizeCommand(Request request) {
    	EObject semantic = ((IGraphicalEditPart) getHost()).resolveSemanticElement();
        IElementType type = ElementTypeRegistry.getInstance().
            getElementType(semantic);
        if (semantic instanceof Activity) {
        	type = ElementTypeEx.wrap(type, 
        			((Activity) semantic).getActivityType().getName());
        }
        Dimension size = BpmnShapesDefaultSizes.getDefaultSize(type).getCopy();
        TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
        Rectangle rect = new Rectangle(getHostFigure().getBounds().getLocation().getCopy(), size);
        if (ActivityType.VALUES_EVENTS.contains(((Activity) semantic).getActivityType()) ||
        		ActivityType.VALUES_GATEWAYS.contains(((Activity) semantic).getActivityType())) {
        	IGraphicalEditPart nameEp = ((IGraphicalEditPart) getHost()).
        		getChildBySemanticHint(BpmnVisualIDRegistry.getType(
        				ActivityNameEditPart.VISUAL_ID));
        	if (nameEp == null) {
        		nameEp = ((IGraphicalEditPart) getHost()).
        		getChildBySemanticHint(BpmnVisualIDRegistry.getType(
        				ActivityName2EditPart.VISUAL_ID));
        	}
//        	if (nameEp != null && nameEp.getFigure() != null) {
//        		Rectangle textBounds = nameEp.getFigure().getBounds().getCopy();
//        		if (nameEp.getFigure() instanceof WrapLabel) {
//        			textBounds = ((WrapLabel) nameEp.getFigure()).getTextBounds();
//        		}
//        		rect.width = Math.max(rect.width, textBounds.width);
//        		// even if there is nothing in the label
//        		// the height is not null, which causes problems
//        		if (((Activity) semantic).getName() != null && 
//        				((Activity) semantic).getName().length() != 0) {
//        			rect.height = rect.height + textBounds.height;
//        		}
//        	}
        }
        ICommand resizeCommand = new SetBoundsCommand(editingDomain, 
            "", //$NON-NLS-1$
            (org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart) getHost(), rect);
        return new ICommandProxy(resizeCommand);
    }
    
    /**
     * Try handling the resize of the label.
     */
    @Override
    public Command getCommand(Request request) {
        if (RequestConstants.REQ_RESIZE_CHILDREN.equals(request.getType())) {
            TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) 
                    getHost()).getEditingDomain();
            final ChangeBoundsRequest change = (ChangeBoundsRequest) request;
            
            final IGraphicalEditPart nameEp = (IGraphicalEditPart) change.getEditParts().iterator().next();
            SetBoundsCommand resize  = new SetBoundsCommand(editingDomain,
                    BpmnDiagramMessages.ResizableActivityEditPolicy_command_name, nameEp, 
                    change.getSizeDelta()) {
                @Override
                protected CommandResult doExecuteWithResult(
                        IProgressMonitor monitor, IAdaptable info)
                        throws ExecutionException {
                    
                    // doing this explicitly to circumvene the problem
                    // of accessing the view in the layout
                    // could also be done from the notify in the NameEditPart
                    nameEp.getFigure().setSize(nameEp.getFigure().getSize().
                            getCopy().expand(change.getSizeDelta()));
                    return super.doExecuteWithResult(monitor, info);
                }
            };
            
            ChangeBoundsRequest selfResize = 
                new ChangeBoundsRequest(RequestConstants.REQ_RESIZE);
            selfResize.setEditParts(getHost());
            selfResize.setSizeDelta(change.getSizeDelta().getCopy());
            CompoundCommand  compound = new CompoundCommand();
            compound.add(new ICommandProxy(resize));
            compound.add(super.getCommand(selfResize));
            return compound;
        }
        return super.getCommand(request);
    }
    
    static List<Group> findContainingGroups(ChangeBoundsRequest request, IGraphicalEditPart host) {
        Rectangle rect = host.getFigure().getBounds().getCopy();
        host.getFigure().translateToAbsolute(rect);
        rect.resize(request.getSizeDelta());
        rect.translate(request.getMoveDelta());
        return findContainingGroups(rect, host.getViewer());
    }
    
    static List<Group> findContainingGroups(Rectangle rect, EditPartViewer viewer) {
        List<Group> groups = new ArrayList<Group>();
        for (Object o : viewer.getVisualPartMap().keySet()) {
            EditPart p = (EditPart) viewer.getVisualPartMap().get(o);
            if (p instanceof GroupEditPart || p instanceof Group2EditPart) {
                IGraphicalEditPart part = (IGraphicalEditPart) p;
                Rectangle bounds = part.getFigure().getBounds().getCopy();
                part.getFigure().translateToAbsolute(bounds);
                if (bounds.contains(rect)) {
                    groups.add((Group) part.resolveSemanticElement());
                }
            }
        }
        return groups;
    }
    
    static List<Lane> findContainingLanes(ChangeBoundsRequest request, IGraphicalEditPart host) {
    	Rectangle rect = host.getFigure().getBounds().getCopy();
        host.getFigure().translateToAbsolute(rect);
        rect.resize(request.getSizeDelta());
        rect.translate(request.getMoveDelta());
        return findContainingLanes(rect, host.getViewer());
    }
    
    /**
     * searches through all LaneEditParts and checks if <br>
     * <ul><li>the activity starts in this lane</li>
     * <li>the activity goes straight through the whole lane</li>
     * <li>the activity ends in this lane</li></ul>
     * and adds those activities to the returned list.
     * @param activity Rectanlge representing the activity
     * @param viewer EditPartViewer to receive the LaneEditParts
     * @return List of all lanes contaning the given activity
     * @author <a href="mailto:till.essers@gmail.com">Till Essers</a>
     */
    static List<Lane> findContainingLanes(Rectangle activity, EditPartViewer viewer) {
    	List<Lane> lanes = new ArrayList<Lane>();
    	int actUpper = activity.y;
    	int actLower = activity.y + activity.height;
    	int laneUpper, laneLower;
        for (Object o : viewer.getVisualPartMap().keySet()) {
            EditPart p = (EditPart) viewer.getVisualPartMap().get(o);
            if (p instanceof LaneEditPart ) {
                IGraphicalEditPart part = (IGraphicalEditPart) p;
                Rectangle lane = part.getFigure().getBounds().getCopy();
                part.getFigure().translateToAbsolute(lane);
                laneUpper = lane.y;
                laneLower = lane.y + lane.height;
                if ((laneUpper <= actUpper && laneLower > actUpper) //start of the activity lies in this lane
                		|| (laneLower < actLower && laneUpper > actUpper ) //activity crosses this lane completly
                		|| (laneUpper < actLower && laneLower >= actLower)) {// end of the activity lies in this lane
                    lanes.add((Lane) part.resolveSemanticElement());
                }
            }
        }
        return lanes;
    }
    
    /**
     * This command sets the lanes on the activity.
     * @author <a href="mailto:till.essers@gmail.com">Till Essers</a>
     */
    public static class SetLanesCommand extends AbstractTransactionalCommand {
    	
    	private Activity activity;
    	
    	private List<Lane> lanes;
    	
    	private CreateViewAndElementRequest request;
    	
    	 /**
         * Default constructor
         * 
         * @param lanes the lanes to add
         * @param activity the activity to act on
         */
    	public SetLanesCommand(List<Lane> lanes, Activity activity) {
    		super((TransactionalEditingDomain) AdapterFactoryEditingDomain.
                    getEditingDomainFor(activity),
                    BpmnDiagramMessages.ResizableActivityEditPolicy_groups_command_name, 
                    getWorkspaceFiles(activity));
    		this.activity = activity;
    		this.lanes = lanes;
		}

    	/**
         * Constructor for newly created activities
         * 
         * @param lanes the lanes to add
         * @param request the request which will contain the new activity
         * @param container the container needed to resolve the editing domain
         */
		public SetLanesCommand(List<Lane> lanes, 
                CreateViewAndElementRequest request, 
                EObject container) {
			 super((TransactionalEditingDomain) AdapterFactoryEditingDomain.
	                    getEditingDomainFor(container),
	                    BpmnDiagramMessages.ResizableActivityEditPolicy_groups_command_name, 
	                    getWorkspaceFiles(container));
			 this.request = request;
			 this.lanes = lanes;
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
				IAdaptable info) throws ExecutionException {
			 if (request != null) {
	                activity = (Activity) request.getViewAndElementDescriptor().
	                    getCreateElementRequestAdapter().resolve();
	            }
	            List<Lane> toRemove = new ArrayList<Lane>();
	            List<Lane> lanes = new ArrayList<Lane>(this.lanes);
	            for (Lane a : activity.getLanes()) {
	                if (!lanes.remove(a)) {
	                    toRemove.add(a);
	                }
	            }
	            
	            activity.getLanes().removeAll(toRemove);
	            activity.getLanes().addAll(lanes);
            return CommandResult.newOKCommandResult();
		}
    	
    }
   
    
    /**
     * This command sets the groups on the activity.
     * @author <a href="http://www.intalio.com">Intalio Inc.</a>
     * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
     */
    public static class SetGroupsCommand extends AbstractTransactionalCommand {
        
        private List<Group> _groups;
        
        private Activity _activity;
        
        private CreateViewAndElementRequest _request;
        
        /**
         * Default constructor
         * 
         * @param groups the groups
         * @param activity the activity to act on
         */
        public SetGroupsCommand(List<Group> groups, Activity activity) {
            super((TransactionalEditingDomain) AdapterFactoryEditingDomain.
                    getEditingDomainFor(activity),
                    BpmnDiagramMessages.ResizableActivityEditPolicy_lanes_command_name, 
                    getWorkspaceFiles(activity));
            _groups = groups;
            _activity = activity;
        }
        
        /**
         * Constructor for newly created activities
         * 
         * @param groups the groups
         * @param request the request which will contain the new activity
         * @param container the container needed to resolve the editing domain
         */
        public SetGroupsCommand(List<Group> groups, 
                CreateViewAndElementRequest request, 
                EObject container) {
            super((TransactionalEditingDomain) AdapterFactoryEditingDomain.
                    getEditingDomainFor(container),
                    BpmnDiagramMessages.ResizableActivityEditPolicy_groups_command_name, 
                    getWorkspaceFiles(container));
            _groups = groups;
            _request = request;
        }

        @Override
        protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
                IAdaptable info) throws ExecutionException {
            if (_request != null) {
                _activity = (Activity) _request.getViewAndElementDescriptor().
                    getCreateElementRequestAdapter().resolve();
            }
            List<Group> toRemove = new ArrayList<Group>();
            List<Group> groups = new ArrayList<Group>(_groups);
            for (Group a : _activity.getGroups()) {
                if (!groups.remove(a)) {
                    toRemove.add(a);
                }
            }
            
            _activity.getGroups().removeAll(toRemove);
            _activity.getGroups().addAll(groups);
            return CommandResult.newOKCommandResult();
        }
        
    }
    
    @Override
    protected Command getMoveCommand(ChangeBoundsRequest request) {
        CompoundCommand compound = new CompoundCommand();
        compound.add(super.getMoveCommand(request));
        compound.add(new ICommandProxy(
                new SetGroupsCommand(findContainingGroups(request, (IGraphicalEditPart) getHost()), 
                        (Activity) ((IGraphicalEditPart) getHost()).resolveSemanticElement())));
        compound.add(new ICommandProxy(
        		new SetLanesCommand(findContainingLanes(request, (IGraphicalEditPart) getHost()),
        				(Activity) ((IGraphicalEditPart) getHost()).resolveSemanticElement())));
        return compound;
    }
    
    @Override
    public Command getResizeCommand(ChangeBoundsRequest request) {
        CompoundCommand compound = new CompoundCommand();
        compound.add(super.getResizeCommand(request));
        compound.add(new ICommandProxy(
                new SetGroupsCommand(findContainingGroups(request, (IGraphicalEditPart) getHost()), 
                        (Activity) ((IGraphicalEditPart) getHost()).resolveSemanticElement())));
        compound.add(new ICommandProxy(
        		new SetLanesCommand(findContainingLanes(request, (IGraphicalEditPart) getHost()), 
        				(Activity) ((IGraphicalEditPart) getHost()).resolveSemanticElement())));
        return compound;
    }
    
//    public boolean understandsRequest(Request request) {
//        if (REQ_MOVE.equals(request.getType())) {
//            return isDragAllowed();
//        }
//        return super.understandsRequest(request);
//    }

    
}
