/******************************************************************************
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.stp.bpmn.handles;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.swt.graphics.Image;

/**
 * Connection handle for the association
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ConnectionHandleForAssociation extends ConnectionHandleEx {

    public ConnectionHandleForAssociation(IGraphicalEditPart ownerEditPart,
            HandleDirection relationshipDirection, String tooltip) {
        super(ownerEditPart, relationshipDirection, tooltip);
    }

    @Override
    protected Image getImage(int side) {
        if (side == PositionConstants.WEST) {
            return isIncoming() ? DiagramUIPluginImagesEx
                    .get(DiagramUIPluginImagesEx.IMG_HANDLE_INCOMING_ASSOCIATION_WEST)
                    : DiagramUIPluginImagesEx
                        .get(DiagramUIPluginImagesEx.IMG_HANDLE_OUTGOING_ASSOCIATION_WEST);
        } else if (side == PositionConstants.EAST) {
            return isIncoming() ? DiagramUIPluginImagesEx
                    .get(DiagramUIPluginImagesEx.IMG_HANDLE_INCOMING_ASSOCIATION_EAST)
                    : DiagramUIPluginImagesEx
                        .get(DiagramUIPluginImagesEx.IMG_HANDLE_OUTGOING_ASSOCIATION_EAST);
        } else if (side == PositionConstants.SOUTH) {
            return isIncoming() ? DiagramUIPluginImagesEx
                .get(DiagramUIPluginImagesEx.IMG_HANDLE_INCOMING_ASSOCIATION_SOUTH)
                : DiagramUIPluginImagesEx
                    .get(DiagramUIPluginImagesEx.IMG_HANDLE_OUTGOING_ASSOCIATION_SOUTH);
        } else {
            return isIncoming() ? DiagramUIPluginImagesEx
                .get(DiagramUIPluginImagesEx.IMG_HANDLE_INCOMING_ASSOCIATION_NORTH)
                : DiagramUIPluginImagesEx
                    .get(DiagramUIPluginImagesEx.IMG_HANDLE_OUTGOING_ASSOCIATION_NORTH);
        }
    }
    
    
}
