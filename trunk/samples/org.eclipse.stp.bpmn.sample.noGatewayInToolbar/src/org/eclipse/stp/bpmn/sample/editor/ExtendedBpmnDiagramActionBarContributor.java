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
 * Date			Author					Changes
 * Feb 15, 2008	Antoine Toulme		Created
 */
package org.eclipse.stp.bpmn.sample.editor;

import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramActionBarContributor;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditor;

/**
 * 
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class ExtendedBpmnDiagramActionBarContributor extends
        BpmnDiagramActionBarContributor {

    /**
     * @generated
     */
    protected Class getEditorClass() {
        return ExtendedBpmnDiagramEditor.class;
    }

    /**
     * @generated
     */
    protected String getEditorId() {
        return ExtendedBpmnDiagramEditor.ID;
    }
}
