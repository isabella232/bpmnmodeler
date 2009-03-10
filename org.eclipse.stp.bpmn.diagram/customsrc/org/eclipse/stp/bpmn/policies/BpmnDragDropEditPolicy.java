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
 *
 * Date         Author             Changes
 * Apr 17, 2007      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.policies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.internal.runtime.AdapterManager;
import org.eclipse.core.internal.runtime.IAdapterFactoryExt;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.editpolicies.GraphicalEditPolicy;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.PopupMenuCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.DiagramColorConstants;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.AssociationEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.MessagingEdgeEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SequenceEdgeEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.ui.IMenuItemWithDisableSupport;
import org.eclipse.stp.bpmn.diagram.ui.PopupMenuWithDisableSupport;
import org.eclipse.stp.bpmn.dnd.IDnDHandler;
import org.eclipse.stp.bpmn.dnd.IDnDHandler2;
import org.eclipse.stp.bpmn.figures.activities.ActivityPainter;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * @author atoulme
 * This edit policy is in charge of the DnD of EAnnotations
 * or View objects.
 */
public class BpmnDragDropEditPolicy extends GraphicalEditPolicy {
    
    private static final Color disabledFeedbackColor = new Color(null, 255, 64, 64);
    /**
     * A cache so that we only adapt the request objects once.
     * @author atoulme
     *
     */
    private static class AdaptedCache {
        /**
         * the result of the adapting operation.
         */
        public List<IDnDHandler> result;
        /**
         * the size of the object list attached to the request
         */
        public int size;
    }
    
    private static WeakHashMap<Object, AdaptedCache> CACHE = 
        new WeakHashMap<Object, AdaptedCache>();
    
    public BpmnDragDropEditPolicy(IGraphicalEditPart part) {
        EditPolicy policy = part.getEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE);
        if (policy != null) {
            previousEditPolicy = policy;
        }
    }
    private EditPolicy previousEditPolicy;
    
    /**
     * @return true if the request is of the type 
     * {@link RequestConstants#REQ_DROP_OBJECTS}
     * and that the request's objects' collection isn't empty.
     */
    @Override
    public boolean understandsRequest(Request request) {
        boolean b = RequestConstants.REQ_DROP_OBJECTS.equals(request.getType());
        b = b && (request instanceof DropObjectsRequest && 
                !((DropObjectsRequest) request).getObjects().isEmpty());
        return b;
    }
    
    @Override
    public Command getCommand(Request request) {
        if (!understandsRequest(request)) {
            if (previousEditPolicy != null) {
                return previousEditPolicy.getCommand(request);
            } else {
                return null;
            }
        }
        
        final DropObjectsRequest dropRequest = (DropObjectsRequest) request;
        
        // cache the eobjects produced on this part
        List<IDnDHandler> dndHandlers = null;
//        final List<IStatus> messages = null;
        synchronized(dropRequest) {
            // first get the adapted.
            AdaptedCache cache = CACHE.get(dropRequest.getObjects().iterator().next());
            if (cache != null) {
                    if (cache.size != dropRequest.getObjects().size()) {
                        cache = null;
                }
            }
            List<IDnDHandler> adapted = null;
            // adapt if necessary
            if (cache == null) {
                adapted = retrieveDnDHandlers(dropRequest.getObjects());
                Collections.sort(adapted, new Comparator<IDnDHandler>() {
					public int compare(IDnDHandler o1, IDnDHandler o2) {
					    int p1 = o1 instanceof IDnDHandler2 ? 
					            ((IDnDHandler2) o1).getPriority((IGraphicalEditPart) getHost()) :
					        o1.getPriority();
					    int p2 = o2 instanceof IDnDHandler2 ? 
					            ((IDnDHandler2) o2).getPriority((IGraphicalEditPart) getHost()) :
					        o2.getPriority();
						if (p1 > p2) {
							return -1;
						} else if (p1 < p2){
							return 1;
						} else {
							return 0;
						}
					}});
                cache = new AdaptedCache();
                cache.result = new ArrayList<IDnDHandler>(adapted);
                cache.size = dropRequest.getObjects().size();
                CACHE.put(dropRequest.getObjects().iterator().next(), cache);
            } else {
                adapted = new ArrayList<IDnDHandler>(cache.result);
            }
            dndHandlers = adapted;
        }
        
        // now we can leave if we need to.
        if (dndHandlers == null
                ||dndHandlers.isEmpty()) {
            return UnexecutableCommand.INSTANCE;
        }
        
        // give some feedback on the request
        boolean isEnabled = false;
        for (IDnDHandler handler : dndHandlers) {
        	for (int i = 0 ; i < handler.getItemCount() ; i++) {
        		isEnabled = isEnabled || handler.isEnabled(
        				(IGraphicalEditPart) getHost(), i);
        	}
        }
        if (!isEnabled) {
        	dropRequest.setRequiredDetail(DND.DROP_NONE);
        } else {
        	dropRequest.setRequiredDetail(DND.DROP_COPY);
        }
        final List<IDnDHandler> handlers = dndHandlers;
        // we are now going to build the command.
        // show a popup menu
        // use the right command to do the drop wether we are dropping
        // a view or a EAnnotation.
        
        AbstractTransactionalCommand command = new AbstractTransactionalCommand(
                ((IGraphicalEditPart) getHost()).getEditingDomain(), 
                BpmnDiagramMessages.BpmnDragDropEditPolicy_command_name, 
                null) {
            @Override
            protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) 
            throws ExecutionException {
            	try {
            		// we create a popup command to get one view
            		IGraphicalEditPart gHost = (IGraphicalEditPart)getHost();
            		ILabelProvider provider = new DnDHandlerDelegateLabelProvider(gHost);

            		// build the menu items
            		List<DnDHandlerMenuItemData> items = 
            			new ArrayList<DnDHandlerMenuItemData>();
            		for (IDnDHandler handler : handlers) {
            		    int itemCount = handler instanceof IDnDHandler2 ?
            		            ((IDnDHandler2) handler).getItemCount(gHost)
            		            : handler.getItemCount();
            			for (int i = 0 ; i < itemCount; i++) {
            				DnDHandlerMenuItemData data = new DnDHandlerMenuItemData(handler, i, gHost);
            				data.handler = handler;
            				data.index = i;
            				items.add(data);
            			}
            		}
//            		System.err.println("dropLoc.x = " + dropLocation.x);
            		
            		PopupMenuWithDisableSupport popupMenu = 
            			new PopupMenuWithDisableSupport(items, provider,
            					dropRequest.getLocation(), getHost().getViewer());
            		PopupMenuCommand popupCmd = new PopupMenuCommand("",  //$NON-NLS-1$
            				new Shell(), popupMenu);
            		popupCmd.execute(monitor, info);
            		if (popupCmd.getCommandResult().getStatus().
            				getSeverity() != IStatus.OK || 
            				popupCmd.getCommandResult().getReturnValue() == null) {
            			CACHE.clear();
            			return popupCmd.getCommandResult();
            		}
            		DnDHandlerMenuItemData winner = 
            			((DnDHandlerMenuItemData) popupCmd.
            					getCommandResult().getReturnValue());

            		Command co = 
            			winner.handler.getDropCommand(
            					(IGraphicalEditPart) getHost(),
            					winner.index, 
            					dropRequest.getLocation().getCopy());
            		if (co != null) {
            		    co.execute();
            		}
//          		drop(winner, dropRequest);
//}
            	for (IDnDHandler handler : handlers) {
            		handler.dispose();
            	}
            	} catch (Exception e) {
            	    IStatus st = new Status(IStatus.ERROR, 
            	            BpmnDiagramEditorPlugin.ID, 0, e.getMessage(), e);
            	    BpmnDiagramEditorPlugin.getInstance().getLog().log(st);
            	} finally {
            		CACHE.clear();
            	}
                return CommandResult.newOKCommandResult();
            }
        };
        return new ICommandProxy(command);
    }
    
    
    
    private Command getAnnotationsDropCommand(final List<EAnnotation> annotations) {
        Command co = new ICommandProxy(new AbstractTransactionalCommand(
                ((IGraphicalEditPart) getHost()).getEditingDomain(), 
                BpmnDiagramMessages.BpmnDragDropEditPolicy_annotation_drop_command, 
                null) {
            @Override
            protected CommandResult doExecuteWithResult(
                    IProgressMonitor monitor, IAdaptable info) 
                    throws ExecutionException {
                EObject element = ((IGraphicalEditPart) getHost()).
                    resolveSemanticElement();
                for (EAnnotation ann : annotations) {
                SetCommand.create(getEditingDomain(), ann, 
                        EcorePackage.eINSTANCE.getEAnnotation_EModelElement(),
                        element).execute();
                }
                return CommandResult.newOKCommandResult();
            }
        });
        return co;
    }
    /**
     * 
     * @param objects the list of objects that need to be adapted 
     * into drop descriptors.
     * @return the list of drop descriptors that represent the
     * objects that will be dropped on the diagram.
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
    private List<IDnDHandler> retrieveDnDHandlers(List objects) {
        List<IDnDHandler> dndHandlers = new ArrayList<IDnDHandler>();
        for (Object dropped : objects) {
            if (dropped instanceof IDnDHandler) {
                dndHandlers.add((IDnDHandler) dropped);
            } else {
                // we take all the adapter factories outputs
                Map factories = ((AdapterManager)
                        Platform.getAdapterManager()).getFactories();
                Class[] cl = ((AdapterManager) Platform.getAdapterManager()).
                	computeClassOrder(dropped.getClass());
                for (Class c : cl) {
                    List clfactories = (List) factories.get(c.getName());
                    if (clfactories != null) {
                        for (Object f : clfactories) {
                            IAdapterFactory factory = (IAdapterFactory) f;
                            if (f instanceof IAdapterFactoryExt) {
                                //if we don't call this method
                                //the adapter factory is not returned unless the
                                //plugin is already loaded.
                                try {
                                    ((IAdapterFactoryExt)f).loadFactory(true);
                                } catch(Throwable e) {
                                    System.err.println("Unable to load a BPMNAdapter factory: " + e.getMessage() //$NON-NLS-1$
                                            + " for " + c.getName()); //$NON-NLS-1$
                                    //e.printStackTrace(); // loading the factories must happen.
                                }
                            }
                            Object adapted = factory.getAdapter(dropped, IDnDHandler.class);
                            if (adapted instanceof IDnDHandler) {
                                dndHandlers.add((IDnDHandler) adapted);
                            } else if (adapted instanceof List) {
                                for (Object ad : (List) adapted) {
                                    if (ad instanceof IDnDHandler) {
                                        dndHandlers.add((IDnDHandler) ad);
                                    } else {
                                        // do nothing
                                    }
                                }
                            } else {
                                // do nothing
                            }
                        }
                    }
                }
            }
        }
        return dndHandlers;
    }
    
    
    /**
     * For editparts that consume the entire viewport, statechart, structure,
     * communication, we want to display the popup bar at the mouse location.
     * 
     * @param referencePoint
     *            The reference point which may be used to determine where the
     *            diagram assistant should be located. This is most likely the
     *            current mouse location.
     * @return Point
     */
    private Point getBalloonPosition(Point referencePoint) {
        Point lastRePointForBalloonPosition = referencePoint.getCopy();
        ((GraphicalEditPart)getHost()).getFigure()
            .translateToAbsolute(lastRePointForBalloonPosition);
        
        Point thePoint = new Point();
        thePoint.setLocation(referencePoint);
        getHostFigure().translateToAbsolute(thePoint);
        getHostFigure().translateToRelative(thePoint);

        Control ctrl1 = getHost().getViewer().getControl();
        if (ctrl1 instanceof FigureCanvas) {
            FigureCanvas figureCanvas = (FigureCanvas) ctrl1;
            Viewport vp = figureCanvas.getViewport();
            Rectangle vpRect = vp.getClientArea();

            Rectangle rcBounds = getHostFigure().getBounds().getCopy();
            getHostFigure().translateToAbsolute(rcBounds);
            getHostFigure().translateToRelative(rcBounds);
            thePoint.y += vpRect.y;
            thePoint.x += vpRect.x;
        }

        return thePoint;
    }
    
    
    @Override
    public EditPart getTargetEditPart(Request request) {
        if (understandsRequest(request)) {
            return getHost();
        }
        if (previousEditPolicy != null) {
            return previousEditPolicy.getTargetEditPart(request);
        }
        return null;
    }
        
    private IFigure _feedback = null;
    
    @Override
    public void showTargetFeedback(Request request) {
        if (understandsRequest(request)) {
            if (_feedback == null) {
                Command co = getCommand(request);
                _feedback = createFeedback(co != null && co.canExecute() 
                        && ((DropObjectsRequest) request).getRequiredDetail() != DND.DROP_NONE);
            }

            Rectangle bounds = ((IGraphicalEditPart) getHost()).
                        getFigure().getBounds().getCopy();
            getFeedbackLayer().add(_feedback);
            if (!(getHost() instanceof MessagingEdgeEditPart) 
                    && !(getHost() instanceof SequenceEdgeEditPart)
                    && !(getHost() instanceof AssociationEditPart)) {
                bounds.expand(3, 3);
            } else {
                //good place to expriment for EDGE-2482
            }
            ((IGraphicalEditPart) getHost()).getFigure().translateToAbsolute(bounds);
            _feedback.translateToRelative(bounds);
            _feedback.setBounds(bounds);
        }
    }
    
    /**
     * @see org.eclipse.gef.EditPolicy#eraseTargetFeedback(org.eclipse.gef.Request)
     */
    public void eraseTargetFeedback(Request request) {
            if (_feedback != null && 
                    _feedback.getParent() != null) {
                
                _feedback.getParent().remove(_feedback);
            }
            //once the feedback has been removed we must not use it anymore
            _feedback = null;
    }
    
    protected IFigure createFeedback(boolean executable) {
        if (((IGraphicalEditPart) getHost()) instanceof MessagingEdgeEditPart
                || ((IGraphicalEditPart) getHost()) instanceof SequenceEdgeEditPart
                || ((IGraphicalEditPart) getHost()) instanceof AssociationEditPart) {
            Connection conn = (Connection) ((ConnectionEditPart) getHost()).getFigure();
            PolylineConnectionEx feedback = null;
            if (((IGraphicalEditPart) getHost()) instanceof MessagingEdgeEditPart) {
                feedback = ((MessagingEdgeEditPart) getHost()).new ConnectionMessageFigure() {
                    @Override
                    public void paintFigure(Graphics graphics) {
                        graphics.setAlpha(ActivityPainter.getMessagingEdgeTransparency()/2);
                        super.paintFigure(graphics);
                    }
                };
            } else if (((IGraphicalEditPart) getHost()) instanceof SequenceEdgeEditPart) {
                feedback = ((SequenceEdgeEditPart) getHost()).new EdgeFigure(true) {
                    @Override
                    public void paintFigure(Graphics graphics) {
                        graphics.setAlpha(ActivityPainter.getSequenceEdgeTransparency()/2);
                        super.paintFigure(graphics);
                    }
                };
            } else if (((IGraphicalEditPart) getHost()) instanceof AssociationEditPart) {
                feedback = ((AssociationEditPart) getHost()).new ConnectionAssociationFigure() {
                    @Override
                    public void paintFigure(Graphics graphics) {
                        graphics.setAlpha(127);
                        super.paintFigure(graphics);
                    }
                };
            }
            PointList pl = conn.getPoints().getCopy();
            ZoomManager zoom = ((DiagramRootEditPart) getHost().getRoot()).getZoomManager();
            pl.performScale(zoom.getZoom());
//            pl.translate(0, 4);
//            pl.setPoint(pl.getFirstPoint().translate(0, -4), 0);
//            pl.setPoint(pl.getLastPoint().translate(0, -4), pl.size() -1);
            feedback.setPoints(pl);
            Rectangle rect = conn.getBounds().getCopy();
            feedback.setBounds(rect);
            feedback.setForegroundColor(executable 
                    ? DiagramColorConstants.lightBlue 
                    : disabledFeedbackColor);
            feedback.setLineWidth(3);
            feedback.setSmoothness(PolylineConnectionEx.SMOOTH_NORMAL);
            feedback.setConnectionRouter(conn.getConnectionRouter());
            return feedback;
        } else {
            RoundedRectangle feedback = new RoundedRectangle();
            feedback.setFill(false);
            feedback.setLineWidth(3);
            feedback.setForegroundColor(executable 
                    ? DiagramColorConstants.lightBlue 
                    : disabledFeedbackColor);
            
            return feedback;
        }
    }
    
}
/**
 * Dummy class used to store the handler and associate it
 * an index.
 */
class DnDHandlerMenuItemData implements IMenuItemWithDisableSupport {
	
	public IDnDHandler handler;
	public int index;
	private final IGraphicalEditPart _host;
	
	DnDHandlerMenuItemData(IDnDHandler handler, int index, IGraphicalEditPart host) {
		this.handler = handler;
		this.index = index;
		_host = host;
	}

	/**
	 * simple implementation of the isEnabled() method
	 * that delegates to the handler.
	 */
	public boolean isEnabled() {
		return handler.isEnabled(_host,	index);
	}
	
	/**
	 * @return The tooltip to use for this menu item
	 */
	public ToolTip getToolTip(Control parent) {
		if (handler instanceof IDnDHandler.IToolTipProvider) {
    		return ((IDnDHandler.IToolTipProvider) handler)
    			.getMenuItemToolTip(parent, _host, index);
    	}
		return null;

	}
}

/**
 * Label provider that provides a label and an image to
 * the popup menu items of the popup menu that the user
 * chooses from.
 * It delegates its calls to the IDnDHandler.
 */
class DnDHandlerDelegateLabelProvider extends LabelProvider {
	
	private final IGraphicalEditPart _host;
	
	DnDHandlerDelegateLabelProvider(IGraphicalEditPart host) {
		_host = host;
	}

    public Image getImage(Object element) {
    	if (element instanceof DnDHandlerMenuItemData) {
    		DnDHandlerMenuItemData data = 
    				(DnDHandlerMenuItemData) element;
    		return data.handler.getMenuItemImage(_host, data.index);
    	}
        return null;
    }

    public String getText(Object element) {
    	if (element instanceof DnDHandlerMenuItemData) {
    		DnDHandlerMenuItemData data = 
    				(DnDHandlerMenuItemData) element;
    		return data.handler.getMenuItemLabel(_host, data.index);
    	}
        return null;
    }
    

}

