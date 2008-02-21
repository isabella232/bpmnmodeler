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
 * Dates       		 Author           Changes
 * Feb 20, 2008     Antoine Toulme     Creation
 */
package org.eclipse.stp.bpmn.sample.editor;

import java.util.Map;

import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.stp.bpmn.diagram.providers.BpmnPaletteProvider;
import org.eclipse.ui.IEditorPart;

/**
 * This provider removes the text annotation from the palette.
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class NoTextAnnotationPaletteContributor extends BpmnPaletteProvider {

    public void contributeToPalette(IEditorPart editor, Object content,
            PaletteRoot root, Map predefinedEntries) {
        //this is the short way
        super.contributeToPalette(editor, content, root, predefinedEntries);
        ((PaletteContainer) root.getChildren().get(1)).getChildren().remove(0);
        
        //the long way consists in populating the palette with your own factory.
    }

}
