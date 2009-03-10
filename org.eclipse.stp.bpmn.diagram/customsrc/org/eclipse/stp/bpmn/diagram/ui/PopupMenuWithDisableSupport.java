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
 * Apr 18, 2007      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.diagram.ui;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.diagram.ui.menus.PopupMenu;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * @author atoulme
 * adds the support to disabled menu items when the descriptor
 * is disabled, or the sub menu is empty.
 */
public class PopupMenuWithDisableSupport extends PopupMenu {

    public static class CascadingMenuWithDisableSupport extends CascadingMenu {

        private PopupMenuWithDisableSupport subMenu;
        public CascadingMenuWithDisableSupport(Object aParentMenuItem, 
                PopupMenuWithDisableSupport aSubMenu) {
            super(aParentMenuItem, aSubMenu);
            subMenu = aSubMenu;
        }
        
        /**
         * Gets the subMenu.
         * 
         * @return Returns the subMenu.
         */
        public PopupMenuWithDisableSupport getSubMenu() {
            return subMenu;
        }
    }
    
    private TooltipManager _tooltip;
    private Point _locationInTheDisplay;
    private Control _parent;
    Menu _rootMenu;
    
    public PopupMenuWithDisableSupport(List aContent, 
            ILabelProvider aLabelProvider) {
        super(aContent, aLabelProvider);
    }
    
    /**
     * 
     * @param aContent
     * @param aLabelProvider
     * @param location The location according to the drop request
     * @param The edit part viewer used to translate
     * the location to the display's coords.
     */
    public PopupMenuWithDisableSupport(List<?> aContent, 
            ILabelProvider aLabelProvider, org.eclipse.draw2d.geometry.Point location,
            EditPartViewer viewer) {
        super(aContent, aLabelProvider);
        _locationInTheDisplay = viewer.getControl().toDisplay(location.x, location.y);
    }
    
    
	/**
	 * Shows the popup menu and sets the resultList selected by the user.
	 * 
	 * @param parent
	 *            menu will be shown in this parent Control
	 * @return true if this succeeded, false otherwise (e.g. if the user
	 *         cancelled the gesture).
	 */
	public boolean show(Control parent) {
		_parent = parent;
		boolean res = super.show(parent);
		if (_tooltip != null) {
			_tooltip.dispose();
		}
		return res;
	}
	
	/**
	 * The location in the Display coords. Suitable to compute where to place
	 * the tooltip.
	 * @return
	 */
	protected Point getLocationInTheDisplay() {
		return _locationInTheDisplay;
	}

    /**
     * Exactly the same as the parent, except that the menu item
     * is given a chance to be set as disabled.
     * It also support tooltips
     */
    @SuppressWarnings("unchecked")
	@Override
    protected void createMenuItems(Menu parentMenu, PopupMenu root,
            final List resultThusFar) {
    	if (_rootMenu == null) {
    		_rootMenu = parentMenu;
    	}
    	
        final PopupMenuWithDisableSupport rootMenu = 
            (PopupMenuWithDisableSupport) root;
        
        Assert.isNotNull(getContent());
        Assert.isNotNull(getLabelProvider());
        
        if (_locationInTheDisplay != null) {
        	//if we are not doing a D&D, we don't care for tooltips. for now.
        	_tooltip = new TooltipManager(rootMenu, _parent);
        }
        for (Iterator<?> iter = getContent().iterator(); iter.hasNext();) {
            Object contentObject = iter.next();

            final MenuItem menuItem;

            if (contentObject instanceof CascadingMenuWithDisableSupport) {
                PopupMenuWithDisableSupport subMenu = 
                    ((CascadingMenuWithDisableSupport) contentObject)
                    .getSubMenu();
                contentObject = ((CascadingMenu) contentObject).getParentMenuItem();
                if (subMenu.getContent().isEmpty()) {
                    menuItem = new MenuItem(parentMenu, SWT.NONE);
                    menuItem.setEnabled(false);
                } else {
                    List<Object> thisResult = new ArrayList<Object>(resultThusFar);
                    thisResult.add(contentObject);
                    menuItem = new MenuItem(parentMenu, SWT.CASCADE);
                    menuItem.setMenu(new Menu(parentMenu));

                    subMenu.createMenuItems(menuItem.getMenu(), rootMenu,
                            thisResult);
                }
            } else {
                menuItem = new MenuItem(parentMenu, SWT.NONE);
            }

            final Object fContentObject = contentObject;
            String text = getLabelProvider().getText(contentObject);
            menuItem.setText(text == null ? "" : text); //$NON-NLS-1$
            // let's avoid NPEs.
            menuItem.setImage(getLabelProvider().getImage(contentObject));
            if (contentObject instanceof IMenuItemWithDisableSupport) {
                menuItem.setEnabled(((IMenuItemWithDisableSupport) 
                		contentObject).isEnabled());
            }
            menuItem.addSelectionListener(new SelectionListener() {

                public void widgetSelected(SelectionEvent e) {
                    resultThusFar.add(fContentObject);
                    rootMenu.setResult(resultThusFar);
                }

                public void widgetDefaultSelected(SelectionEvent e) {
                    resultThusFar.add(fContentObject);
                    rootMenu.setResult(resultThusFar);
                }
            });
            
            if (_tooltip != null) {
	            menuItem.addListener(SWT.Arm, new Listener() {
					public void handleEvent(Event event) {
						_tooltip.showTooltip(event, menuItem,
								(IMenuItemWithDisableSupport)fContentObject);
					}
	            });
            }
        }
    }
    
}
class TooltipManager {

    private static Method Menu_Get_Bound = null;
    /**
     * Is there a better way to compute the width of the popup menu?
     * currently we do an ugly introspection trick...
     * @param menu
     * @return
     */
    static int getMenuWidth(Menu menu) {
    	try {
    		if (Menu_Get_Bound == null) {
    			Menu_Get_Bound = Menu.class.getDeclaredMethod("getBounds"); //$NON-NLS-1$
    			Menu_Get_Bound.setAccessible(true);
    		}
    		Rectangle rect = (Rectangle)Menu_Get_Bound.invoke(menu, new Object[] {});
    		return rect.width;
    	} catch (Throwable t) {
//    		t.printStackTrace();
    	}
    	return 0;
    }

	private PopupMenuWithDisableSupport _rootMenu;
	private ToolTip _currentToolTip;
	private Control _parent;
	
	TooltipManager(PopupMenuWithDisableSupport rootMenu, Control parent) {
		_rootMenu = rootMenu;
		_parent = parent;
	}
	

	void showTooltip(Event event, MenuItem item, Object content) {
		if (!(content instanceof IMenuItemWithDisableSupport)) {
			return;
		}
		IMenuItemWithDisableSupport tooltip = (IMenuItemWithDisableSupport)content;
		
		_currentToolTip = tooltip.getToolTip(_parent);
		if (_currentToolTip == null) {
			return;
		}
		
		int menuWidth = getMenuWidth(_rootMenu._rootMenu);
		_currentToolTip.show(
				new Point(_rootMenu.getLocationInTheDisplay().x + menuWidth,
				_rootMenu.getLocationInTheDisplay().y));
	}
	
	void dispose() {
		if (_currentToolTip != null) {
			_currentToolTip.deactivate();
		}
	}
		
}
