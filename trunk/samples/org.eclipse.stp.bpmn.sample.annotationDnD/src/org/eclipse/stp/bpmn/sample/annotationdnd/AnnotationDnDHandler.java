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
 * Feb 22, 2008	Antoine Toulme		Created
 */
package org.eclipse.stp.bpmn.sample.annotationdnd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.stp.bpmn.dnd.AbstractEAnnotationDnDHandler;
import org.eclipse.stp.bpmn.dnd.IDnDHandler;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * This DnDHandler creates an annotation on the shape, 
 * given the text of the .txt files.
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class AnnotationDnDHandler extends AbstractEAnnotationDnDHandler implements
        IDnDHandler {

    private IFile _file;

    public AnnotationDnDHandler(IFile file) {
        _file = file;
    }
    
    private static final String TEXT_ANNOTATION_SOURCE = "textAnnotationSource";
    /* (non-Javadoc)
     * @see org.eclipse.stp.bpmn.dnd.IDnDHandler#getDropCommand(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart, int, org.eclipse.draw2d.geometry.Point)
     */
    public Command getDropCommand(IGraphicalEditPart hoverPart, int index,
            Point dropLocation) {
        
        Map<String, String> details = new HashMap<String, String>();
        try {
            details.put("lastModified", String.valueOf(_file.getLocalTimeStamp()));
            details.put("name", _file.getName());
            details.put("fileExtension", _file.getFileExtension());
            details.put("content-type", _file.getContentDescription().getContentType().getName());

            Reader in = new BufferedReader(new InputStreamReader(_file.getContents()));
            StringBuffer buffer= new StringBuffer(1024);
            char[] readBuffer= new char[1024];
            int n= in.read(readBuffer);
            while (n > 0) {
                buffer.append(readBuffer, 0, n);
                n= in.read(readBuffer);
            }
            details.put("text", buffer.toString());
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return createEAnnotationDropCommand(
                createAnnotation(TEXT_ANNOTATION_SOURCE, details),
                (EModelElement) hoverPart.resolveSemanticElement());
    }

    /* (non-Javadoc)
     * @see org.eclipse.stp.bpmn.dnd.IDnDHandler#getItemCount()
     */
    public int getItemCount() {
        return 1;
    }

    /* (non-Javadoc)
     * @see org.eclipse.stp.bpmn.dnd.IDnDHandler#getMenuItemImage(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart, int)
     */
    public Image getMenuItemImage(IGraphicalEditPart hoverPart, int index) {
        return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
    }

    /* (non-Javadoc)
     * @see org.eclipse.stp.bpmn.dnd.IDnDHandler#getMenuItemLabel(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart, int)
     */
    public String getMenuItemLabel(IGraphicalEditPart hoverPart, int index) {
        return "Insert the text as an annotation";
    }

    /* (non-Javadoc)
     * @see org.eclipse.stp.bpmn.dnd.IDnDHandler#getPriority()
     */
    public int getPriority() {
        return 0;
    }

}
