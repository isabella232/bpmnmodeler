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
 * Date         	    Author              Changes 
 * 11 ��� 2006   	MPeleshchyshyn  	Created 
 **/

package org.eclipse.stp.bpmn.tools;

import java.lang.reflect.Field;
import java.util.Collections;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.gef.tools.SelectionTool;
import org.eclipse.gef.tools.TargetingTool;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.stp.bpmn.diagram.edit.parts.Group2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.GroupEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBorderCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.dnd.file.FileDnDConstants;
import org.eclipse.stp.bpmn.policies.PopupBarEditPolicyEx;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;

/**
 * Extends the default selection tool and overrides it to behave in this manner:
 * <ul>
 * <li>on drag always always make a selection request unless the curosr is
 * over the bpmn pool's label or the sub-process label or the sub-process
 * is collapsed.
 * In order to be a little less bpmn only related, we do this based
 * on the nature of the editpart.<li>
 * <li>double-click will issue a direct-edit request
 * on either the ITextAwareEditPart or the PrimaryChild of the focused
 * editpart if that primary child is an ITextAwareEditPart.</li>
 * </ul>
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class SelectionToolEx extends SelectionTool {

    //helper
    private static Field toolClassField;
    
    private static void init() {
        if (toolClassField != null) {
            return;
        }
        try {
            toolClassField = ToolEntry.class.getDeclaredField("toolClass"); //$NON-NLS-1$
            toolClassField.setAccessible(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void setToolClass(ToolEntry toolEntry, Class toolClass) {
        init();
        try {
            toolClassField.set(toolEntry, toolClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //--helper
//direct edit on double-click
    /**
     * Issues direct-edit request if the edit-part looks like it should receive
     * such request
     * @see AbstractTool#handleButtonDown(int)
     */
    @Override
    protected boolean handleDoubleClick(int button) {
    	if (isHoverActive()) {
    		handleHoverStop();
    		setHoverActive(false);
    	}
    	EditPartViewer viewer = getCurrentViewer();
    	if (viewer instanceof GraphicalViewer &&
    			(isInState(STATE_DRAG) || isInState(STATE_INITIAL))) {
    		EditPart selectedEditPart =
    			((GraphicalViewer)viewer).getFocusEditPart();
    		if (getCurrentInput().isShiftKeyDown()) {
    			if (selectedEditPart instanceof IGraphicalEditPart) {
    				EObject object = ((IGraphicalEditPart) selectedEditPart).
    					getNotationView().getElement();
    				if (object instanceof EModelElement) {
    					EAnnotation src = ((EModelElement) object).
    					getEAnnotation(FileDnDConstants.ANNOTATION_SOURCE);
    					if (src != null) {
    						Request request = new Request(
    								RequestConstants.REQ_OPEN);
    						selectedEditPart.performRequest(request);
    						return true;
    					}
    				}
    			}
    		} else {
    			if (!(selectedEditPart instanceof ITextAwareEditPart)
    					&& selectedEditPart instanceof IGraphicalEditPart) {
    				IGraphicalEditPart nodePart =
    					(IGraphicalEditPart) selectedEditPart;
    				EditPart primEditPart = nodePart.getPrimaryChildEditPart();
    				if (primEditPart != null &&
    						primEditPart instanceof ITextAwareEditPart) {
    					ITextAwareEditPart textPart =
    						(ITextAwareEditPart) primEditPart;
    					selectedEditPart = textPart;

    					//at this point we could decide to edit only if the label
    					//is in fact null or empty.

    					//if (textPart.getEditLabel() != null
    							//|| && textPart.getEditLabel().getLength() > 0) {
    						//return false;


    					//if above is not commented, double-clicking anywhere
    					//on the task even outside of its label
    					//will put the label in edit mode

    				}
    			}

    			if (selectedEditPart instanceof ITextAwareEditPart) {
    				Request request = new Request(RequestConstants.REQ_DIRECT_EDIT);
    				selectedEditPart.performRequest(request);
    				return true;
    			}
    		}
    	}
    	return false;
    }
//-- end of the direct-edit requests on double-click.

    /**
     * Shows up the diagram assistant if Ctrl+Space is pressed
     */
    @Override
    protected boolean handleKeyDown(KeyEvent e) {
    	if (e.stateMask == SWT.CTRL && e.keyCode == ' ') {
            EditPart part = getTargetEditPart();
            if (!(part instanceof IGraphicalEditPart)) {
                return super.handleKeyDown(e);
            }
    		IGraphicalEditPart selectedEditPart = (IGraphicalEditPart) part;
    		EditPolicy ep = selectedEditPart.getEditPolicy(EditPolicyRoles.POPUPBAR_ROLE);
    		PopupBarEditPolicyEx editPolicy = (PopupBarEditPolicyEx) 
    			(ep instanceof PopupBarEditPolicyEx ? ep : null);
    		if (editPolicy == null) {
    			IGraphicalEditPart child = selectedEditPart.
    			getChildBySemanticHint(BpmnVisualIDRegistry.getType(
    					SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID));
    			
    			if (child != null) {
    			    //see EDGE-2082: see how close we are from the border:
                    Rectangle rect = selectedEditPart.getFigure().getBounds().getCopy();
                    Point loc = getLocation().getCopy();
//                  part.getFigure().translateToAbsolute(rect);
                    selectedEditPart.getFigure().translateToRelative(loc);
                    if (loc.y - (rect.y + rect.height) < 14) {//do we need LD2DP? I don't think so.
                        IGraphicalEditPart border = selectedEditPart.
                            getChildBySemanticHint(BpmnVisualIDRegistry.getType(
                                    SubProcessSubProcessBorderCompartmentEditPart.VISUAL_ID));
                        if (border != null) {
                            child = border;
                        }
                    }
    			}
    			
    			
    			if (child == null) {
    				 child = selectedEditPart.
    	    			getChildBySemanticHint(BpmnVisualIDRegistry.getType(
    	    					SubProcessSubProcessBorderCompartmentEditPart.VISUAL_ID));
    				 if (child == null) {
    					 child = selectedEditPart.
     	    			getChildBySemanticHint(BpmnVisualIDRegistry.getType(
     	    					PoolPoolCompartmentEditPart.VISUAL_ID));
    				 }
    			}
    			if (child != null) {
    				ep = child.getEditPolicy(EditPolicyRoles.POPUPBAR_ROLE);
    				editPolicy = (PopupBarEditPolicyEx) 
        				(ep instanceof PopupBarEditPolicyEx ? ep : null);
    			}
    		}
    		if (editPolicy != null) {
    			editPolicy.showDiagramAssistant();
    			return false;
    		}
    		
    	} else if (e.keyCode == ' ') {
    	    
    	    MultipleShapesMoveTool t = new MultipleShapesMoveTool() {
                @Override
                protected boolean handleKeyUp(KeyEvent e) {
                    if (e.keyCode == ' ') {
                        getDomain().setActiveTool(SelectionToolEx.this);
                    }
                    return super.handleKeyUp(e);
                }
            };
            t.setViewer(getCurrentViewer());
            if (getCurrentInput().isMouseButtonDown(1)) {
                t.handleButtonDown(1);
            }
    	    getDomain().setActiveTool(t);
    	}
    	return super.handleKeyDown(e);
    }
    
    
    /**
     * Do we want to improve the behavior of the cursor so that it changes when above an area that
     * would make the shape below move?
     * Or change the areas of the sub-process where the sub-process can be dragged?
     * 
     * Forwards the mouse move event to the drag tracker, if one exists.
     * @see org.eclipse.gef.Tool#mouseMove(MouseEvent,  
     *                                              org.eclipse.gef.EditPartViewer)
     *
    public void mouseMove(MouseEvent me, EditPartViewer viewer) {
        if (isInState(STATE_INITIAL) && getTargetEditPart() != null && getTargetEditPart()
                instanceof GraphicalEditPart) {
            Point p = getLocation();
//          ((GraphicalEditPart)getTargetEditPart()).getFigure().translateToAbsolute(p);
          System.err.println("currently " + p.x + " , " + p.y);
          //((GraphicalEditPart)getTargetEditPart()).getFigure().translateToAbsolute(p);
          ((GraphicalEditPart)getTargetEditPart()).getFigure().translateToRelative(p);
          System.err.println("translate to relative " + p.x + " , " + p.y);
          
        }
    
        
        if (isInState(STATE_INITIAL) && getTargetEditPart() != null
                && getTargetEditPart() instanceof SubProcessSubProcessBodyCompartmentEditPart) {
            Point p = getLocation();
//            ((GraphicalEditPart)getTargetEditPart()).getFigure().translateToAbsolute(p);
            System.err.println("currently " + p.x + " , " + p.y);
            if (p.x < 5) {
                setCursor(Cursors.SIZEALL);
            } else {
                setCursor(null);
            }
        }
        super.mouseMove(me, viewer);
    }*/
    
    /**
     * Overridden for groups. We only select them when the cursor
     * is placed at less than 5 pixels from the border of the groups,
     * otherwise the parent of the group is selected.
     */
    @Override
    protected boolean updateTargetUnderMouse() {
        boolean updated = super.updateTargetUnderMouse();
        
        if (getTargetEditPart() instanceof GroupEditPart || 
                getTargetEditPart() instanceof Group2EditPart) {
            IGraphicalEditPart part = (IGraphicalEditPart) getTargetEditPart();
            Rectangle rect = part.getFigure().getBounds().getCopy();
            Point loc = getLocation().getCopy();
//          part.getFigure().translateToAbsolute(rect);
            part.getFigure().translateToRelative(loc);
            boolean onX = Math.abs(loc.x - rect.x) < 5 ||
                Math.abs(loc.x - (rect.x + rect.width)) < 5;
            boolean onY = Math.abs(loc.y - rect.y) < 5 ||
                Math.abs(loc.y - (rect.y + rect.height)) < 5;
            if (!(onX || onY)) {
                EditPart res = part.getViewer().findObjectAtExcluding(
                        getLocation(), Collections.singletonList(part.getFigure()), 
                        new EditPartViewer.Conditional() {
                            public boolean evaluate(EditPart editpart) {
                                return editpart.isSelectable();
                            }});
                setTargetEditPart(res);
                updated = true;
            }
        }   
        return updated;
    }
}