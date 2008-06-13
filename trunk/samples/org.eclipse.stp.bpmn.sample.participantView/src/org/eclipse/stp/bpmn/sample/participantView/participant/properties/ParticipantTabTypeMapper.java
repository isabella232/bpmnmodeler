/**
 *  Copyright (C) 2008, Intalio Inc.
 *
 *  The program(s) herein may be used and/or copied only with the
 *  written permission of Intalio Inc. or in accordance with the terms
 *  and conditions stipulated in the agreement/contract under which the
 *  program(s) have been supplied.
 *
 * Date         Author             Changes
 * Apr 21, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.sample.participantView.participant.properties;

import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

/**
 * 
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class ParticipantTabTypeMapper implements ITypeMapper {

    /* (non-Javadoc)
     * @see org.eclipse.ui.views.properties.tabbed.ITypeMapper#mapType(java.lang.Object)
     */
    public Class mapType(Object object) {
        System.err.println(object);// REMOVE
        return null;
    }

}
