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
 * Nov 28, 2006     MPeleshchsyhyn         Created
 **/
package org.eclipse.stp.bpmn.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.util.ObjectAdapter;
import org.eclipse.gmf.runtime.diagram.ui.commands.PromptForConnectionAndEndCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.internal.commands.ElementTypeLabelProvider;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.menus.PopupMenu;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IMetamodelType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.ModelingAssistantService;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;
import org.eclipse.stp.bpmn.diagram.ui.PopupMenuWithDisableSupport;
import org.eclipse.stp.bpmn.diagram.ui.PopupMenuWithDisableSupport.CascadingMenuWithDisableSupport;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;

/**
 * Makes sure that the message connections are between pools.
 * Make sure that the sequence connections are in the same sub-process or pool.
 * 
 * @author MPeleshchyshyn
 * @author hmalphettes
 * @author atoulme
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class PromptForConnectionAndEndCommandEx extends
    PromptForConnectionAndEndCommand {

    /**
     * constant to select the underlying pool as the target of the message
     * Should not be i18n'ized, see the end label provider for its text representation.
     */
    private static final String UNDERLYING_POOL = "underlying pool"; //$NON-NLS-1$

    private static Image EXISTING = null;
    
    protected class ConnectionAndEndLabelProviderEx 
        extends ElementTypeLabelProvider {

        @Override
        public void dispose() {
            super.dispose();
            if (EXISTING != null) {
                EXISTING.dispose();
                EXISTING = null;
            }
        }
        
        /** the known connection item */
        private Object connectionItem;
        
        protected ConnectionAndEndLabelProviderEx(Object connectionItem) {
            this.connectionItem = connectionItem;
        }
        
        @Override
        public Image getImage(Object object) {
            // either we have images for all root elements, or we have none.
            // For now let's stick to none.
//            if (EXISTING_ELEMENT.equals(object)) {
//                if (EXISTING != null && !EXISTING.isDisposed()) {
//                    return EXISTING;
//                }
//                EXISTING = BpmnDiagramEditorPlugin.
//                    getBundledImageDescriptor("icons/obj24/existingElement.png"). //$NON-NLS-1$
//                    createImage();
//                return EXISTING;
//            } 
            return super.getImage(object);
        }
        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
         */
        public String getText(Object element) {
            if (element instanceof String) {
                if (EXISTING_ELEMENT.equals(element)) {
                    return BpmnDiagramMessages.PromptForConnectionAndEndCommandEx_ConnectToExisting;
                }
                if (UNDERLYING_POOL.equals(element)) {
                    return BpmnDiagramMessages.PromptForConnectionAndEndCommandEx_underlying_pool_label;
                }
                return (String) element;
            }
            return super.getText(element);
        }
        
        /**
         * Gets the connection item.
         * 
         * @return the connection item
         */
        protected Object getConnectionItem() {
            return connectionItem;
        }
    }
	/**
	 * taken from superclass.
	 * This can be added to the content list to add a 'select existing' option.
	 */
	protected static String EXISTING_ELEMENT = DiagramUIMessages.ConnectionHandle_Popup_ExistingElement;
	
    private CreateConnectionRequest request;
    private IGraphicalEditPart _containerEP;

//    /** Adapts to the connection type result. */
//	private ObjectAdapter connectionAdapter = new ObjectAdapter();
//
//	/** Adapts to the other end type result. */
//	private ObjectAdapter endAdapter = new ObjectAdapter();
	
    public PromptForConnectionAndEndCommandEx(CreateConnectionRequest request,
            IGraphicalEditPart containerEP) {
        super(request, containerEP);
        this.request = request;
        _containerEP = containerEP;
    }

    @Override
    protected List getConnectionMenuContent() {
        List l = super.getConnectionMenuContent();
        
        EditPart source = request.getSourceEditPart();
        EditPart target = request.getTargetEditPart();
        EditPart sPool = getPool(source);
        EditPart tPool = getPool(target);
        
        EditPart sContainer = getContainer(source.getParent());
        
        // filter the possible connections
        if (tPool != null && sPool.equals(tPool)) {
            l.remove(BpmnElementTypes.MessagingEdge_3002);
        }
        if (tPool != null && 
                (sContainer != _containerEP || !sPool.equals(tPool))) {
            l.remove(BpmnElementTypes.SequenceEdge_3001);
        }
        return l;
    }
    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.gmf.runtime.diagram.ui.commands.PromptForConnectionAndEndCommand#getEndMenuContent(java.lang.Object)
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
    @Override
    protected List getEndMenuContent(Object connectionItem) {
        List l = super.getEndMenuContent(connectionItem);
        if ((IMetamodelType) connectionItem == BpmnElementTypes.Association_3003) {
        	// remove the possibility to connect to an existing artifact for now.
        	l.remove(EXISTING_ELEMENT);
        }
        EditPart source = request.getSourceEditPart();
        EditPart target = request.getTargetEditPart();
        EditPart sPool = getPool(source);
        EditPart tPool = getPool(target);
        
        EditPart sContainer = getContainer(source.getParent());
        if (!(connectionItem instanceof IMetamodelType)) {
        	return l;
        }
        if ((IMetamodelType) connectionItem == BpmnElementTypes.MessagingEdge_3002) {
            if (tPool != null && sPool.equals(tPool)) {
//                Object lastElement = l.get(l.size() - 1);
                l.clear();
//                l.add(lastElement);
            } else {
                l.add(0, UNDERLYING_POOL);
            }
        } else if ((IMetamodelType) connectionItem == BpmnElementTypes.SequenceEdge_3001) {
            if (tPool != null && 
            		(sContainer != _containerEP || !sPool.equals(tPool))) {
//                Object lastElement = l.get(l.size() - 1);
                l.clear();
//                l.add(lastElement);
            }
        }
        
        if (l.isEmpty()) {
            return l;
        }
        List result = new ArrayList();
        List activities = new ArrayList();
        List tasks = new ArrayList();
        List gateways = new ArrayList();
        List startEvents = new ArrayList();
        List intermediateEvents = new ArrayList();
        List endEvents = new ArrayList();
        IElementType simpleTask = null;
        for (Object elt : l) {
            if (elt instanceof IElementTypeEx) {
                IElementTypeEx eltType = (IElementTypeEx) elt;
                String secHint = eltType.getSecondarySemanticHint();
                ActivityType actype = ActivityType.get(secHint);
                if (ActivityType.VALUES_EVENTS.contains(actype)) {
                    if (ActivityType.VALUES_EVENTS_START.contains(actype)) {
                        startEvents.add(elt);
                    } else if (ActivityType.VALUES_EVENTS_INTERMEDIATE.contains(actype)) {
                        intermediateEvents.add(elt);
                    } else {
                        endEvents.add(elt);
                    }
                } else if (ActivityType.VALUES_GATEWAYS.contains(actype)) {
                    gateways.add(elt);
                } else if (eltType.getId().equals(BpmnElementTypes.Activity_2001.getId()) 
                        && ActivityType.TASK_LITERAL.equals(actype)) {
                    simpleTask = (IElementType) elt;
                } else {
                    tasks.add(elt);
                }
            } else if (elt == BpmnElementTypes.SubProcess_2002) {
                tasks.add(elt);
            } else if (elt != null) {
                result.add(elt);
            }
        }
        
        ILabelProvider provider = getConnectionAndEndLabelProvider(connectionItem);
        if (simpleTask != null) {
            activities.add(simpleTask);
        }
        if (!tasks.isEmpty()) {
            activities.add(new CascadingMenuWithDisableSupport(
                    BpmnDiagramMessages.PromptForConnectionAndEndCommandEx_activities_menu_label, 
                    new PopupMenuWithDisableSupport(tasks, provider)));
        }
        if (!gateways.isEmpty()) {
            activities.add(new CascadingMenuWithDisableSupport(
                    BpmnDiagramMessages.PromptForConnectionAndEndCommandEx_gateways_menu_label, 
                    new PopupMenuWithDisableSupport(gateways, provider)));
        }
        if (!startEvents.isEmpty()) {
            activities.add(new CascadingMenuWithDisableSupport(
                    BpmnDiagramMessages.PromptForConnectionAndEndCommandEx_start_events_menu_label, 
                    new PopupMenuWithDisableSupport(startEvents, provider)));
        }
        if (!intermediateEvents.isEmpty()) {
            activities.add(new CascadingMenuWithDisableSupport(
                    BpmnDiagramMessages.PromptForConnectionAndEndCommandEx_intermediate_label, 
                    new PopupMenuWithDisableSupport(intermediateEvents, provider)));
        }
        if (!endEvents.isEmpty()) {
            activities.add(new CascadingMenuWithDisableSupport(
                    BpmnDiagramMessages.PromptForConnectionAndEndCommandEx_end_events_menu_label, 
                    new PopupMenuWithDisableSupport(endEvents, provider)));
        }
        result.addAll(0, activities);
        return result;
    }

    /**
     * Returns parent pool for the specified edit part or edit part itself in
     * case if specified edit part is a pool edit part
     * 
     * @param editPart
     *            the edit part
     * @return parent pool for the specified edit part or edit part itself in
     *         case if specified edit part is a pool edit part
     */
    protected EditPart getPool(EditPart editPart) {
        if (editPart instanceof PoolEditPart) {
            return editPart;
        }
        if (editPart == null) {
        	return null;
        }
        EditPart parent = editPart.getParent();

        while (!(parent instanceof PoolEditPart) && parent != null) {
            parent = parent.getParent();
        }

        return parent;
    }
    /**
     * Returns the container compartment edit part in which the passed
     * edit part should be considered enclosed as far as the sequence
     * connection is concerned.
     * <p>
     * If the edit part is an activity or a sub-process, it matches the edit 
     * part of the graph that contains it.
     * </p>
     * <p>
     * If the edit part is a boundary event activity, the parent container
     * is not the sub-process that carries the event but the container of that
     * sub-process. This way it is allowed to have a connection between a
     * event boundary and the activity to execute when the event is triggered.
     * </p>
     * @param editPart
     *            the edit part
     * @return parent pool for the specified edit part or edit part itself in
     *         case if specified edit part is a pool edit part
     */
    protected EditPart getContainer(EditPart editPart) {
        if (editPart instanceof PoolPoolCompartmentEditPart || 
                editPart instanceof SubProcessSubProcessBodyCompartmentEditPart) {
            return editPart;
        }
        
        EditPart parent = editPart.getParent();
        if (parent == null) {
            return null;//humf!
        }
        
        return getContainer(parent);
    }
    
    /**
     * overriden to return shorter messages
     */
    protected ILabelProvider getConnectionAndEndLabelProvider(
            Object connectionItem) {
        return new ConnectionAndEndLabelProviderEx(connectionItem);
    }
    
    private CommandResult doExecuteWithResultPopupMenuCommand(IProgressMonitor progressMonitor) {
        if (getPopupMenu() != null) {
            if (getPopupMenu().show(getParentShell()) == false) {
                // user cancelled gesture
                progressMonitor.setCanceled(true);
                return CommandResult.newCancelledCommandResult();
            }
            return CommandResult.newOKCommandResult(getPopupMenu().getResult());
            
        } else if (getPopupDialog() != null) {
            if (getPopupDialog().open() == Dialog.CANCEL
                || getPopupDialog().getResult() == null
                || getPopupDialog().getResult().length <= 0) {
                
                // user cancelled dialog
                progressMonitor.setCanceled(true);
                return CommandResult.newCancelledCommandResult();
            }
            return CommandResult.newOKCommandResult(getPopupDialog().getResult()[0]);
        }

        return CommandResult.newOKCommandResult();
    }
    
    /**
     * Pops up the dialog with the content provided. If the user selects 'select
     * existing', then the select elements dialog also appears.
     */
    protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor,
            IAdaptable info)
        throws ExecutionException {
        
        PopupMenu popup = createPopupMenu();

        if (popup == null) {
            return CommandResult.newErrorCommandResult(getLabel());
        }

        setPopupMenu(popup);

        CommandResult cmdResult = doExecuteWithResultPopupMenuCommand(progressMonitor);
        if (!cmdResult.getStatus().isOK()) {
            return cmdResult;
        }

        Object result = cmdResult.getReturnValue();
        if (result instanceof List) {
            List resultList = (List) result;
            if (resultList.size() == 2) {
                connectionAdapter.setObject(resultList.get(0));

                Object targetResult = resultList.get(1);
                if (targetResult instanceof List) {
                    targetResult = ((List) targetResult).get(((List) targetResult).size() -1);
                }
                if (targetResult.equals(EXISTING_ELEMENT)) {
                    targetResult = isDirectionReversed() ? ModelingAssistantService
                        .getInstance().selectExistingElementForSource(
                            getKnownEnd(), (IElementType) resultList.get(0))
                        : ModelingAssistantService.getInstance()
                            .selectExistingElementForTarget(getKnownEnd(),
                                (IElementType) resultList.get(0));
                    if (targetResult == null) {
                        return CommandResult.newCancelledCommandResult();
                    }
                } else if (targetResult.equals(UNDERLYING_POOL)) {
                    targetResult = ((IGraphicalEditPart) getPool(request.getTargetEditPart())).
                        resolveSemanticElement();
                }
                
                endAdapter.setObject(targetResult);
                return CommandResult.newOKCommandResult();
            }
        }
        return CommandResult.newErrorCommandResult(getLabel());
    }
    
    /**
     * Gets the known end, which even in the case of a reversed
     * <code>CreateUnspecifiedTypeConnectionRequest</code>, is the source
     * editpart.
     * 
     * @return the known end
     */
    private EditPart getKnownEnd() {
        return request.getSourceEditPart();
    }
    
    /** Adapts to the connection type result. */
    private ObjectAdapter connectionAdapter = new ObjectAdapter();

    /** Adapts to the other end type result. */
    private ObjectAdapter endAdapter = new ObjectAdapter();
    
    /**
     * Gets the connectionAdapter.
     * 
     * @return Returns the connectionAdapter.
     */
    public ObjectAdapter getConnectionAdapter() {
        return connectionAdapter;
    }

    /**
     * Gets the endAdapter.
     * 
     * @return Returns the endAdapter.
     */
    public IAdaptable getEndAdapter() {
        return endAdapter;
    }
    
    @Override
    protected PopupMenu createPopupMenu() {
        final List connectionMenuContent = getConnectionMenuContent();

        if (connectionMenuContent == null || connectionMenuContent.isEmpty()) {
            return null;
        } else if (connectionMenuContent.size() == 1) {
            List menuContent = getEndMenuContent(connectionMenuContent.get(0));
            if (menuContent == null || menuContent.isEmpty()) {
                return null;
            }

            ILabelProvider labelProvider = getConnectionAndEndLabelProvider(connectionMenuContent
                .get(0));
            return new PopupMenuWithDisableSupport(menuContent, labelProvider) {

                /**
                 * @see org.eclipse.gmf.runtime.diagram.ui.menus.PopupMenu#getResult()
                 */
                public Object getResult() {
                    Object endResult = super.getResult();
                    if (endResult == null) {
                        return null;
                    } else {
                        List resultList = new ArrayList(2);
                        resultList.add(connectionMenuContent.get(0));
                        resultList.add(endResult);
                        return resultList;
                    }
                }
            };
        } else {
            List menuContent = new ArrayList();
            for (Iterator iter = connectionMenuContent.iterator(); iter
                .hasNext();) {
                Object connectionItem = iter.next();

                List subMenuContent = getEndMenuContent(connectionItem);

                if (subMenuContent.isEmpty()) {
                    continue;
                }

                PopupMenu subMenu = new PopupMenuWithDisableSupport(subMenuContent,
                    getEndLabelProvider());

                menuContent.add(new PopupMenu.CascadingMenu(connectionItem,
                    subMenu));
            }
            if (!menuContent.isEmpty()) {
                return new PopupMenuWithDisableSupport(menuContent, getConnectionLabelProvider());
            }
        }
        return null;
    }
    
//    @Override
//    public boolean canExecute() {
//    	return createPopupBalloon() != null;
//    }
    
//    /**
//     * New method that is going to generate a balloon instead of the popup menu.
//     * @return a PopupBalloon object or null
//     */
//    protected PopupBalloon createPopupBalloon() {
//    	final List connectionMenuContent = getConnectionMenuContent();
//		if (connectionMenuContent == null || connectionMenuContent.isEmpty()) {
//			return null;
//		} else {
//			PopupBalloon balloon = new PopupBalloon(_containerEP);
//			for (Iterator iter = connectionMenuContent.iterator(); iter.hasNext();) {
//				Object connectionItem = iter.next();
//				for (Object subItem : getEndMenuContent(connectionItem)) {
//					IElementType item = (IElementType) subItem;
//					PassivePopupBarTool theTracker =
//						balloon.new PassivePopupBarTool(balloon, _containerEP, item);
//					balloon.addPopupBarDescriptor(item, theTracker);
//				}
//			}
//			if ((balloon.hasPopupBarDescriptors())) {
//				return balloon;
//			}
//		}
//		return null;
//    }
    
//    @Override
//    protected CommandResult doExecuteWithResult(
//    		IProgressMonitor progressMonitor, IAdaptable info)
//    		throws ExecutionException {
//    	PopupBalloon balloon = createPopupBalloon();
//
//		if (balloon == null) {
//			return CommandResult.newErrorCommandResult(getLabel());
//		}
//
//		balloon.showBalloonExclusive(request.getLocation(), getParentShell());
//		
//		ICommand com = (ICommand) balloon.getCommand();
//		
//		com.execute(progressMonitor, info);
//		CommandResult cmdResult = com.getCommandResult();
//		if (cmdResult.getStatus().getSeverity() != IStatus.OK) {
//			return cmdResult;
//		}
//		Object result = cmdResult.getReturnValue();
//		if (result instanceof List) {
//			List resultList = (List) result;
//			if (resultList.size() == 2) {
//				connectionAdapter.setObject(resultList.get(0));
//
//				Object targetResult = resultList.get(1);
//
//				if (targetResult.equals(EXISTING_ELEMENT)) {
//					targetResult = isDirectionReversed() ? ModelingAssistantService
//						.getInstance().selectExistingElementForSource(
//							getKnownEnd(), (IElementType) resultList.get(0))
//						: ModelingAssistantService.getInstance()
//							.selectExistingElementForTarget(getKnownEnd(),
//								(IElementType) resultList.get(0));
//					if (targetResult == null) {
//						return CommandResult.newCancelledCommandResult();
//					}
//				}
//				endAdapter.setObject(targetResult);
//				return CommandResult.newOKCommandResult();
//			}
//		}
//		return CommandResult.newErrorCommandResult(getLabel());
//    }
    
//    /**
//	 * Gets the known end, which even in the case of a reversed
//	 * <code>CreateUnspecifiedTypeConnectionRequest</code>, is the source
//	 * editpart.
//	 * 
//	 * @return the known end
//	 */
//	private EditPart getKnownEnd() {
//		return request.getSourceEditPart();
//	}
//	
//	/**
//	 * This can be added to the content list to add a 'select existing' option.
//	 */
//	private static String EXISTING_ELEMENT = DiagramUIMessages.ConnectionHandle_Popup_ExistingElement;
}
