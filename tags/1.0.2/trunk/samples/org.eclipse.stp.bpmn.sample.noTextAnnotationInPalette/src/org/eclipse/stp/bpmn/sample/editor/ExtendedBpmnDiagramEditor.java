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
 * Feb 15, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.sample.editor;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.commands.ElementTypeEx;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditDomain;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditor;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * 
 * This editor extends the bpmn editor by removing all the gateways and
 * the group shape from the popup toolbar.
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class ExtendedBpmnDiagramEditor extends BpmnDiagramEditor {

    /**
     * the editor ID needed for the contributor
     */
    public static final String ID = "org.eclipse.stp.bpmn.sample.noTextAnnotationInPalette.editor1";
    
}
