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
 * Feb 6, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.policies;

import org.eclipse.gef.DragTracker;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.handles.AbstractHandle;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.stp.bpmn.tools.GroupDragTracker;
import org.eclipse.stp.bpmn.tools.GroupResizeTracker;

/**
 * 
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class ResizableGroupEditPolicy extends ResizableArtifactEditPolicy {

    protected Handle createHandle(GraphicalEditPart owner, int direction) {
        return new GroupResizeHandle(owner, direction);
        
    }

    /**
     * Creates new handle for activity.
     */
    protected static class GroupResizeHandle extends ResizeHandleEx {
        public GroupResizeHandle(GraphicalEditPart owner, int direction) {
            super(owner, direction);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.stp.bpmn.policies.ResizableShapeEditPolicyEx.ResizeHandleEx#createDragTracker()
         */
        @Override
        protected DragTracker createDragTracker() {
            return new GroupResizeTracker(getOwner(), cursorDirection);
        }
    }

    @Override
    protected void replaceHandleDragEditPartsTracker(Handle handle) {
        if (handle instanceof AbstractHandle) {
            AbstractHandle h = (AbstractHandle) handle;
            h.setDragTracker(new GroupDragTracker((IGraphicalEditPart)getHost()));
        }
    }
}
