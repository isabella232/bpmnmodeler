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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gmf.runtime.diagram.ui.menus.PopupMenu;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * @author atoulme
 * adds the support to disabled menu items when the descriptor
 * is disabled.
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
    public PopupMenuWithDisableSupport(List aContent, 
            ILabelProvider aLabelProvider) {
        super(aContent, aLabelProvider);
    }

    /**
     * exactly the same as the parent, except that the menu item
     * is given a chance to be set as disabled.
     */
    @Override
    protected void createMenuItems(Menu parentMenu, PopupMenu root,
            final List resultThusFar) {
        final PopupMenuWithDisableSupport rootMenu = 
            (PopupMenuWithDisableSupport) root;
        Assert.isNotNull(getContent());
        Assert.isNotNull(getLabelProvider());

        for (Iterator iter = getContent().iterator(); iter.hasNext();) {
            Object contentObject = iter.next();

            MenuItem menuItem;

            if (contentObject instanceof CascadingMenuWithDisableSupport) {
                PopupMenuWithDisableSupport subMenu = 
                    ((CascadingMenuWithDisableSupport) contentObject)
                    .getSubMenu();
                contentObject = ((CascadingMenu) contentObject)
                    .getParentMenuItem();
                List thisResult = new ArrayList(resultThusFar);
                thisResult.add(contentObject);
                menuItem = new MenuItem(parentMenu, SWT.CASCADE);
                menuItem.setMenu(new Menu(parentMenu));

                subMenu.createMenuItems(menuItem.getMenu(), rootMenu,
                    thisResult);
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
        }
    }
}
