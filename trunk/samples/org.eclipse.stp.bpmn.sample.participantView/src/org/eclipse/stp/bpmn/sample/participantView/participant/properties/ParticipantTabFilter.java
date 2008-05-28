/**
 * Copyright (c) 2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *
 * Date         Author             Changes
 * Apr 21, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.sample.participantView.participant.properties;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.stp.bpmn.Activity;

/**
 * This filter makes it possible to show the participants tab
 * on activities only.
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class ParticipantTabFilter implements IFilter {

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     */
    public boolean select(Object toTest) {
        System.err.println(toTest);// REMOVE
        return toTest instanceof IGraphicalEditPart 
            && ((IGraphicalEditPart) toTest).resolveSemanticElement() instanceof Activity;
    }

}
