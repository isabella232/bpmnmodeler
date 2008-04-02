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
 * 11 &eacute;&eacute;&eacute; 2006   	MPeleshchyshyn  	Created 
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
import org.eclipse.gef.SharedCursors;
import org.eclipse.gef.Tool;
import org.eclipse.gef.EditPartViewer.Conditional;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.gef.tools.SelectionTool;
import org.eclipse.gef.tools.TargetingTool;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.jface.viewers.StructuredSelection;
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
import org.eclipse.swt.graphics.Cursor;

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


    private boolean spacePressed;
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
    		EditPart selectedEditPart = getTargetEditPart();
    		if (getCurrentInput().isShiftKeyDown()) {
    			if (selectedEditPart instanceof IGraphicalEditPart) {
    				EObject object = ((IGraphicalEditPart) selectedEditPart).resolveSemanticElement();
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
    				if (nodePart instanceof SubProcessSubProcessBodyCompartmentEditPart) {
    				    nodePart = (IGraphicalEditPart) nodePart.getParent();
    				}
    				ITextAwareEditPart textAwareEditPart = null;
    				for (Object child : nodePart.getChildren()) {
    				    if (child instanceof ITextAwareEditPart) {
    				        textAwareEditPart = (ITextAwareEditPart) child;
    				        break;
    				    }
    				}
    				
    				if (textAwareEditPart != null) {
    				    // doesn't work well: the compartment takes back the selection
//    					if (nodePart instanceof SubProcessEditPart) {
//    					    // if the user double-clicks on a subprocess,
//    					    // the label edit part shows as selected for the user
//    					    // to move it around.
//    					    viewer.setSelection(new StructuredSelection(textAwareEditPart));
//    					    return true;
//    					} else {
    					    selectedEditPart = textAwareEditPart;
//    					}
    					

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
     * Shows up the diagram assistant if Ctrl+Space is pressed.
     * If space is pressed, delegate calls to the insert-space tool.
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
    	    spacePressed = true;
    	    setCursor(SharedCursors.SIZEWE);
    	}
    	return super.handleKeyDown(e);
    }
    
    @Override
    protected boolean handleButtonDown(int button) {
        if (spacePressed && button == 1) {
            MultipleShapesMoveTool t = new MultipleShapesMoveTool() {
                
                private boolean dont = false;
                @Override
                public boolean handleButtonDown(int button) {
                    if (dont) {
                        spacePressed = false;
                        setCursor(SelectionToolEx.this.getDefaultCursor());
                        SelectionToolEx t = SelectionToolEx.this;
                        t.setViewer(getCurrentViewer());
                        t.handleButtonDown(1);
                        getDomain().setActiveTool(t);
                        t.setTargetEditPart(getTargetEditPart());
                        deactivate();
                        return false;
                    }
                    dont = true;
                    return super.handleButtonDown(button);
                }
                
            };
            t.setViewer(getCurrentViewer());
            getDomain().setActiveTool(t);
            t.handleButtonDown(1);
            return true; 
        }
        return super.handleButtonDown(button);
    }
    
    
    
    @Override
    protected boolean handleKeyUp(KeyEvent e) {
        spacePressed = false;
        if (getDomain().getActiveTool() == this) {
            setCursor(SelectionToolEx.this.getDefaultCursor());
        }
        return super.handleKeyUp(e);
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
    protected Conditional getTargetingConditional() {
        return new EditPartViewer.Conditional() {
            public boolean evaluate(EditPart editpart) {
                if (editpart instanceof GroupEditPart || 
                        editpart instanceof Group2EditPart) {
                    IGraphicalEditPart part = (IGraphicalEditPart) editpart;
                    Rectangle rect = part.getFigure().getBounds().getCopy();
                    Point loc = getLocation().getCopy();
                    part.getFigure().translateToRelative(loc);
                    boolean onX = Math.abs(loc.x - rect.x) < 5 ||
                        Math.abs(loc.x - (rect.x + rect.width)) < 5;
                    boolean onY = Math.abs(loc.y - rect.y) < 5 ||
                        Math.abs(loc.y - (rect.y + rect.height)) < 5;
                    if (!(onX || onY)) {
                        return false;
                    }
                }
                if (editpart instanceof SubProcessSubProcessBodyCompartmentEditPart) {
                    // if the MOD1 modifier is pressed (meaning: ctrl on all 
                    // platforms except Mac OS, which uses Command)
                    // then the subprocess may be moved around.
                    // apparently the modifiers are inverted at some point by something,
                    // so we are looking for the reversed flag.
                    return !getCurrentInput().isModKeyDown(SWT.MOD1);
                }
                return editpart.isSelectable();
            }
        };
    }
}