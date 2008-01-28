/**
 *  Copyright (C) 2007, Intalio Inc.
 *
 *  The program(s) herein may be used and/or copied only with the
 *  written permission of Intalio Inc. or in accordance with the terms
 *  and conditions stipulated in the agreement/contract under which the
 *  program(s) have been supplied.
 *
 * Date         Author             Changes
 * Oct 2, 2007      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.tools;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.ResizeTracker;

/**
 * This tool is in charge of dragging and resizing
 * the group edit parts.
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class GroupDragTracker extends ResizeTracker {

    public GroupDragTracker(GraphicalEditPart owner, int direction) {
        super(owner, direction);
    }

    @Override
    protected Command getCommand() {
        return super.getCommand();
    }
}
