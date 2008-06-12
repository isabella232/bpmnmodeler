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
 * Date             Author              Changes
 * Jul 21, 2006     hmalphettes         Created
 **/
package org.eclipse.stp.bpmn.diagram.edit.helpers;

import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.GetEditContextRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;

/**
 * This class is not generated because there is a specialization of the activity
 * type: the activity either is inside a Graph (pool or sub-process) or is an
 * event handler.
 * 
 * The code has been changed to read the secondary semantic hint set on the
 * IElementTypeEx
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class EdgeEditHelperAdvice extends AbstractEditHelperAdvice {

    public static final EdgeEditHelperAdvice THE_INSTANCE = new EdgeEditHelperAdvice();

    /**
     * @param request
     * @return
     */
    protected CreateRelationshipRequest getCreateRelationshipRequest(
            IEditCommandRequest request) {
        if (request instanceof CreateRelationshipRequest) {
            return (CreateRelationshipRequest) request;
        } else if (request instanceof GetEditContextRequest) {
            GetEditContextRequest gec = (GetEditContextRequest) request;
            if (gec.getEditCommandRequest() instanceof CreateRelationshipRequest) {
                return (CreateRelationshipRequest) gec.getEditCommandRequest();
            } else {
//                 System.err.println("GOT unhandled type " +
//                 gec.getEditCommandRequest());
                return null;
            }
        } else if (request instanceof ConfigureRequest) {
            ConfigureRequest conf = (ConfigureRequest) request;
//             System.err.println("GOT A CONFIG REQ " + conf);
            return null;
        } else {
            return null;
        }
    }
}