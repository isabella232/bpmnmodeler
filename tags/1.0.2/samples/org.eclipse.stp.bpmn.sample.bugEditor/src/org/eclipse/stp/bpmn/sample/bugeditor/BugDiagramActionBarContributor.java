/**
 *  Copyright (C) 2008, Intalio Inc.
 *
 *  The program(s) herein may be used and/or copied only with the
 *  written permission of Intalio Inc. or in accordance with the terms
 *  and conditions stipulated in the agreement/contract under which the
 *  program(s) have been supplied.
 *
 * Date         Author             Changes
 * Mar 10, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.sample.bugeditor;

import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramActionBarContributor;

/**
 * This contributor binds the diagram action bar to our editor
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class BugDiagramActionBarContributor extends
        BpmnDiagramActionBarContributor {

    @SuppressWarnings("unchecked")
    protected Class getEditorClass() {
        return BugBpmnEditor.class;
    }

    protected String getEditorId() {
        return BugBpmnEditor.ID;
    }
}
