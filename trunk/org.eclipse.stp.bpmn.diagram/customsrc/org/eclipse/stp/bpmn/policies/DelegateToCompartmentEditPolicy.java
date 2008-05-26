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
 * Date         	Author             Changes 
 * Nov 22, 2006   	hmalphettes  	Created 
 */
package org.eclipse.stp.bpmn.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;

/**
 * Edit policy that delegates to a child part.
 * <p>
 * Used on PoolEditPart and SubProcessEditPart as they don't have a container role
 * but they receive request for a ContainerEditPolicy: paste, arrange
 * even though their compartment do not receive it.
 * </p>
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class DelegateToCompartmentEditPolicy extends
        AbstractEditPolicy {
    
    private final String _childID;
    private final String _editPolicyID;
    
    private EditPart _childEditPart;
    private EditPolicy _editPolicy;
    
    /**
     * 
     * @param childId The visual id of the child
     * @param editPolicyId The id of the ditpolicy on that child to 
     * delegate the calls to.
     */
    public DelegateToCompartmentEditPolicy(String childId, String editPolicyId) {
        _childID = childId;
        _editPolicyID = editPolicyId;
    }
    

    /**
     * Does nothing by default.
     * @see org.eclipse.gef.EditPolicy#activate()
     */
    public void activate() {
        _childEditPart =
            ((IGraphicalEditPart)getHost()).getChildBySemanticHint(_childID);
        if (_childEditPart == null) {
            return;
        }
        _editPolicy = _childEditPart.getEditPolicy(_editPolicyID);
        if (_editPolicy != null) {
            _editPolicy.setHost(_childEditPart);
            _editPolicy.activate();
        }
    }

    /**
     * Does nothing by default.
     * @see org.eclipse.gef.EditPolicy#deactivate()
     */
    public void deactivate() {
        if (_editPolicy == null) {
            return;
        }
        _editPolicy.deactivate();
        _childEditPart = null;
        _editPolicy = null;
    }


    @Override
    public void eraseSourceFeedback(Request request) {
        if (_editPolicy == null) {
            return;
        }
        _editPolicy.eraseSourceFeedback(request);
    }


    @Override
    public void eraseTargetFeedback(Request request) {
        if (_editPolicy == null) {
            return;
        }
        _editPolicy.eraseTargetFeedback(request);
    }


    @Override
    public Command getCommand(Request request) {
        if (_editPolicy == null) {
            return null;
        }
        return _editPolicy.getCommand(request);
    }


    @Override
    public EditPart getTargetEditPart(Request request) {
        if (_editPolicy == null) {
            return null;
        }
        return _editPolicy.getTargetEditPart(request);
    }


    @Override
    public void showSourceFeedback(Request request) {
        if (_editPolicy == null) {
            return;
        }
        _editPolicy.showSourceFeedback(request);
    }


    @Override
    public void showTargetFeedback(Request request) {
        if (_editPolicy == null) {
            return;
        }
        _editPolicy.showTargetFeedback(request);
    }


    @Override
    public boolean understandsRequest(Request req) {
        if (_childEditPart == null) {
            activate();
        }
        if (_editPolicy == null) {
            return false;
        }
        return _editPolicy.understandsRequest(req);
        /*boolean res = _editPolicy.understandsRequest(req);
        if (res) {
            System.err.println("Delegated understood " + req);
        }
        return res;*/
    }


}
