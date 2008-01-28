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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.emf.clipboard.core.ObjectInfo;
import org.eclipse.gmf.runtime.emf.clipboard.core.OverridePasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation;
import org.eclipse.gmf.runtime.emf.clipboard.core.PostPasteChildOperation;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Vertex;

/**
 * [hmalphettes] as advised in GMF Q&amp;answers regarding copy/paste support.
 * we had to fork the original ConnectorViewPasteOperation 
 * just so tht we can call our very own methods...
 * Why did calling static methods on NotationHelperCliboard?
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BpmnPositionalGeneralViewPasteOperation 
    extends OverridePasteChildOperation {

    private boolean shouldPasteAlwaysCopyObject;

    /**
     * @param overriddenChildPasteOperation
     */
    public BpmnPositionalGeneralViewPasteOperation(
            PasteChildOperation overriddenChildPasteOperation,
            boolean shouldPasteAlwaysCopyObject) {
        super(overriddenChildPasteOperation);
        this.shouldPasteAlwaysCopyObject = shouldPasteAlwaysCopyObject;
    }

    /* (non-Javadoc)
     * @see org.eclipse.gmf.runtime.emf.clipboard.core.PasteChildOperation#paste()
     */
    public void paste()
        throws Exception {
        //      delay unsetting of connector refs
    }

    protected boolean shouldPasteAlwaysCopyObject(
            ObjectInfo alwaysCopyObjectInfo) {
        return shouldPasteAlwaysCopyObject;
    }

    public PasteChildOperation getPostPasteOperation() {

        return new PostPasteChildOperation(this, EMPTY_ARRAY) {

            public void paste()
                throws Exception {
                //unset connectors before pasting so that it won't affect
                //real connectors especially if they happen to belong to the
                // same
                //target diagram
                Node view = (Node) getEObject();
//                view.eUnset(NotationPackage.eINSTANCE.getView_SourceEdges());
//                view.eUnset(NotationPackage.eINSTANCE.getView_TargetEdges());

                //now paste view
                EObject pastedElement = doPasteInto(getParentEObject());
                //did we succeed?
                if (pastedElement != null) {
                    setPastedElement(pastedElement);
                    addPastedElement(pastedElement);
                } else {
                    addPasteFailuresObject(getEObject());
                }
            }

            protected boolean shouldPasteAlwaysCopyObject(
                    ObjectInfo alwaysCopyObjectInfo) {
                return BpmnPositionalGeneralViewPasteOperation.this
                    .shouldPasteAlwaysCopyObject(alwaysCopyObjectInfo);
            }

            /*
             * (non-Javadoc)
             * 
             * @see org.eclipse.gmf.runtime.emf.core.internal.copypaste.PasteChildOperation#makeAuxiliaryChildPasteProcess(org.eclipse.gmf.runtime.emf.core.internal.copypaste.ObjectInfo)
             */
            protected PasteChildOperation makeAuxiliaryChildPasteProcess(
                    ObjectInfo auxiliaryChildEObjectInfo) {             
                EObject semanticPasteTarget = BpmnClipboardSupport
                    .getSemanticPasteTarget((View) getPastedElement());
                if (semanticPasteTarget == null) {
                    return null;
                }
                return new PasteChildOperation(getParentPasteProcess().clone(
                    semanticPasteTarget), auxiliaryChildEObjectInfo);
            }

            public PasteChildOperation getPostPasteOperation() {
                List operations = getAlwaysCopyObjectPasteOperations();
                return new PostPasteChildOperation(this, operations);
            }
        };
    }

}