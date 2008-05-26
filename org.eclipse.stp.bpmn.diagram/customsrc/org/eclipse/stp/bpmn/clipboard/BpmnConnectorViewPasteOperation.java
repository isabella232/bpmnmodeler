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
 * Jul 17, 2006     hmalphettes         Created
 **/

package org.eclipse.stp.bpmn.clipboard;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.clipboard.core.ClipboardSupportUtil;
import org.eclipse.gmf.runtime.emf.clipboard.core.ObjectInfo;
import org.eclipse.gmf.runtime.emf.clipboard.core.OverridePasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PostPasteChildOperation;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

/**
 * [hmalphettes] as advised in GMF Q&amp;answers regarding copy/paste support.
 * we had to fork the original ConnectorViewPasteOperation 
 * just so tht we can call our very own methods...
 * Why did calling static methods on NotationHelperCliboard?
 * 
 * @author Yasser Lulu
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BpmnConnectorViewPasteOperation 
    extends OverridePasteChildOperation {

    private boolean pasteSemanticElement;

    private Edge connectorView;

    private View sourceView;

    private View targetView;

    /**
     * @param overriddenChildPasteOperation
     */
    public BpmnConnectorViewPasteOperation(
        PasteChildOperation overriddenChildPasteOperation) {
        super(overriddenChildPasteOperation);
    }

    /* (non-Javadoc)
     * @see org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation#paste()
     */
    public void paste() throws Exception {
        //basically delay...
        connectorView = (Edge) getEObject();
        sourceView = connectorView.getSource();
        targetView = connectorView.getTarget();
        EObject element = connectorView.getElement();
        if (element != null) {
            if (element.eIsProxy()) {
                element = ClipboardSupportUtil.resolve(element,
                    getParentPasteProcess().getLoadedIDToEObjectMapCopy());
            }
            if (element.eIsProxy() == false) {
                pasteSemanticElement = true;
            }
        }
    }

    protected boolean shouldPasteAlwaysCopyObject(
        ObjectInfo alwaysCopyObjectInfo) {
        return false;
    }

    protected PasteChildOperation makeAuxiliaryChildPasteProcess(
        ObjectInfo auxiliaryChildEObjectInfo) {
        return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation#getPostPasteOperation()
     */
    public PasteChildOperation getPostPasteOperation() {
        return new PostPasteChildOperation(this, EMPTY_ARRAY) {

            public void paste() throws Exception {
                //delay
            }

            public PasteChildOperation getPostPasteOperation() {
                return new PostPasteChildOperation(this, EMPTY_ARRAY) {

                    public void paste() throws Exception {
                        //delay
                    }

                    public PasteChildOperation getPostPasteOperation() {
                        return new BpmnConnectorViewPostPasteChildOperation(
                                BpmnConnectorViewPasteOperation.this,
                                BpmnConnectorViewPasteOperation.this.pasteSemanticElement);
                    }
                };
            }
        };
    }

    protected ObjectInfo getChildObjectInfo() {
        return super.getChildObjectInfo();
    }

    protected List getAlwaysCopyObjectPasteOperations() {
        return Collections.EMPTY_LIST;
    }

    /**
     * @return Returns the sourceView.
     */
    protected View getSourceView() {
        return sourceView;
    }

    /**
     * @return Returns the targetView.
     */
    protected View getTargetView() {
        return targetView;
    }

    /**
     * @return Returns the connectorView.
     */
    protected Edge getConnectorView() {
        return connectorView;
    }
}