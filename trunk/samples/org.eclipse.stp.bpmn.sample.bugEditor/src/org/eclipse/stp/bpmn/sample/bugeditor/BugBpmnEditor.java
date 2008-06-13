/******************************************************************************
 * Copyright (c) 2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Date         Author             Changes
 * Mar 10, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.sample.bugeditor;

import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditor;
import org.eclipse.ui.PlatformUI;

/**
 * This subclasses the BPMN editor to change its name. 
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class BugBpmnEditor extends BpmnDiagramEditor {

    public static final String ID = "org.eclipse.stp.bpmn.sample.bugeditor.editor";
    
    static {
        
        PlatformUI.getWorkbench().getEditorRegistry().
            setDefaultEditor("*.bpmn_diagram", ID); //$NON-NLS-1$
    }
}
