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
 * Date         Author             Changes
 * May 2, 2007      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.samples.bpel2bpmn;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.dnd.AbstractViewDnDHandler;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * 
 * This handler generates BPMN shapes from a BPEL file
 * in a very basic manner.
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class BPELGenerationDnDHandler extends AbstractViewDnDHandler {

	private IFile _bpelFile;
	
	public BPELGenerationDnDHandler(IFile bpelFile) {
		_bpelFile = bpelFile;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IDnDHandler#getDropCommand(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart, int, org.eclipse.draw2d.geometry.Point)
	 */
	public Command getDropCommand(IGraphicalEditPart hoverPart, int index,
			Point dropLocation) {
		BPEL2BPMNGenerator generator = new BPEL2BPMNGenerator(_bpelFile);
        List<View> result = generator.parseAndGenerateFromFile();
        return getDropViewCommand(result, dropLocation, hoverPart);
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
		return PlatformUI.getWorkbench().getSharedImages().
			getImage(ISharedImages.IMG_OBJ_ELEMENT);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IDnDHandler#getMenuItemLabel(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart, int)
	 */
	public String getMenuItemLabel(IGraphicalEditPart hoverPart, int index) {
		return "Generate BPMN shapes from the BPEL file";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IDnDHandler#getPriority()
	 */
	public int getPriority() {
		return 0; // this drop has a very low priority
	}

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IDnDHandler#isEnabled(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart, int)
	 */
	public boolean isEnabled(IGraphicalEditPart hoverPart, int index) {
		EObject eobject = hoverPart.resolveSemanticElement();
		return eobject instanceof Graph;
	}

}
