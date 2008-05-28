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

/** 
 * Date          	Author             Changes 
 * 2 Nov 2006   	BIlchyshyn         Created 
 **/

package org.eclipse.stp.bpmn.handles;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.DragTracker;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.handles.ConnectionHandle;
import org.eclipse.stp.bpmn.tools.ConnectionHandleToolEx;
import org.eclipse.swt.graphics.Image;

/**
 * @author BIlchyshyn override the getImage.
 * @author hmalphettes avoid direct edit.
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ConnectionHandleEx extends ConnectionHandle {

	public ConnectionHandleEx(IGraphicalEditPart ownerEditPart, HandleDirection relationshipDirection, String tooltip) {
		super(ownerEditPart, relationshipDirection, tooltip);
	}

	@Override
	protected Image getImage(int side) {
        if (side == PositionConstants.WEST) {
            return isIncoming() ? DiagramUIPluginImagesEx
                    .get(DiagramUIPluginImagesEx.IMG_HANDLE_INCOMING_FLOW_WEST)
                    : DiagramUIPluginImagesEx
                        .get(DiagramUIPluginImagesEx.IMG_HANDLE_OUTGOING_FLOW_WEST);
        } else if (side == PositionConstants.EAST) {
            return isIncoming() ? DiagramUIPluginImagesEx
                    .get(DiagramUIPluginImagesEx.IMG_HANDLE_INCOMING_FLOW_EAST)
                    : DiagramUIPluginImagesEx
                        .get(DiagramUIPluginImagesEx.IMG_HANDLE_OUTGOING_FLOW_EAST);
        } else if (side == PositionConstants.SOUTH) {
            return isIncoming() ? DiagramUIPluginImagesEx
                .get(DiagramUIPluginImagesEx.IMG_HANDLE_INCOMING_SOUTH)
                : DiagramUIPluginImagesEx
                    .get(DiagramUIPluginImagesEx.IMG_HANDLE_OUTGOING_SOUTH);
        } else {
            return isIncoming() ? DiagramUIPluginImagesEx
                .get(DiagramUIPluginImagesEx.IMG_HANDLE_INCOMING_NORTH)
                : DiagramUIPluginImagesEx
                    .get(DiagramUIPluginImagesEx.IMG_HANDLE_OUTGOING_NORTH);
        }
	}

    /**
     * The extended tool returned here will not go into direct-edit
     * at the end of the creation.
     * @see org.eclipse.gef.handles.AbstractHandle#createDragTracker()
     */
    @Override
    protected DragTracker createDragTracker() {
        return new ConnectionHandleToolEx(this);
    }
    
    

}
