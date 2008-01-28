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
package org.eclipse.stp.bpmn.dnd;

import java.util.List;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramDropTargetListener;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DND;
import org.eclipse.ui.views.navigator.LocalSelectionTransfer;

/**
 * Listener taking the localSelectionTransfer selection
 * and giving it to the superclass to form the DropObjectsRequest.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BpmnDropTargetListener extends
		DiagramDropTargetListener {

	public BpmnDropTargetListener(EditPartViewer viewer) {
		super(viewer, LocalSelectionTransfer.getInstance());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.diagram.ui.parts.DiagramDropTargetListener#getObjectsBeingDropped()
	 */
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	@Override
	protected List getObjectsBeingDropped() {
		ISelection selection =
			LocalSelectionTransfer.getInstance().getSelection();
		if (selection instanceof IStructuredSelection) {
			return ((IStructuredSelection) selection).toList();
		}
		return null;
	}

	/**
	 * Overrides the default request creation by adding it a MOVE operation
	 * in case that the selection is valid.
	 * FIXME if only I knew why this is needed !!!!!
	 * 
	 * Also overridden the super method when 
	 * the getCurrentEvent() returns null, just to avoid
	 * trouble.
	 */
	@Override
	protected Request createTargetRequest() {
	    DropObjectsRequest req = null;
	    if (getCurrentEvent() == null) {
	        req =  new DropObjectsRequest();
	        req.setObjects(getObjectsBeingDropped());
	        req.setAllowedDetail(DND.DROP_COPY);
	    } else {
	        req = (DropObjectsRequest) super.createTargetRequest();
	    }
		req.setRequiredDetail(DND.DROP_COPY);
		return req;
	}
}
