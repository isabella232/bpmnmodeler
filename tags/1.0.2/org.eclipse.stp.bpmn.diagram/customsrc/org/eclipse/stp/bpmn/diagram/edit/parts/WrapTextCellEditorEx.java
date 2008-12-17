/*
 * Copyright (c) 2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 */
package org.eclipse.stp.bpmn.diagram.edit.parts;

import org.eclipse.gmf.runtime.gef.ui.internal.parts.WrapTextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;

/**
 * Support other combination of keys to enter newlines:
 *  <code>enter+shift</code> and <code>enter+alt</code>
 * Those combinations are common on software that our users are familiar with.
 * 
 * @author hmalphettes
 */
public class WrapTextCellEditorEx extends WrapTextCellEditor {

    public WrapTextCellEditorEx() {
        super();
    }
    private static final int defaultStyle = SWT.WRAP | SWT.MULTI;
    public WrapTextCellEditorEx(Composite parent) {
        super(parent, defaultStyle);
    }
    public WrapTextCellEditorEx(Composite parent, int style) {
        super(parent, style);
    }
    
    /**
     * @see org.eclipse.jface.viewers.CellEditor#keyReleaseOccured(org.eclipse.swt.events.KeyEvent)
     */
    protected void keyReleaseOccured(KeyEvent keyEvent) {
        // Make sure that if 'enter+alt or enter+shifht is pressed we
        // insert a newline. It is consistent with many software for business analysts.
        // GMF only inserts a new line for 'enter+ctr'.
        if (keyEvent.character == '\r') {
            if ((keyEvent.stateMask & SWT.CTRL) != 0) {
                //nothing to change.
            } else if ((keyEvent.stateMask & SWT.ALT) != 0) {
                keyEvent.stateMask &= ~SWT.ALT; //remove the alt
                keyEvent.stateMask |= SWT.CTRL; //add the ctr expected by GMF to insert a newline
            } else if ((keyEvent.stateMask & SWT.SHIFT) != 0) {
                keyEvent.stateMask &= ~SWT.SHIFT; //remove the shift
                keyEvent.stateMask |= SWT.CTRL; //add the ctr expected by GMF to insert a newline
            }
        }
        super.keyReleaseOccured(keyEvent);
    }
    
}
