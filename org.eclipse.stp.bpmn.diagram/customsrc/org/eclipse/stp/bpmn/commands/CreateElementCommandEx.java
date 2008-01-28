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

package org.eclipse.stp.bpmn.commands;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.dnd.ISecondarySemanticHintProcessor;

/**
 * Support for the {@link IElementTypeEx} to set the activityType
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class CreateElementCommandEx extends CreateElementCommand {

    public CreateElementCommandEx(CreateElementRequest request) {
        super(request);
    }

    /**
     * @return the new model element that has been created
     */
    protected EObject doDefaultElementCreation() {
        EReference containment = getContainmentFeature();
        EClass eClass = getElementType().getEClass();

        if (containment != null) {
            EObject element = getElementToEdit();

            if (element != null) {
                EObject res = EcoreUtil.create(eClass);
                if (getElementType() instanceof ElementTypeEx) {
                    String secondarySemanticHint = ((IElementTypeEx) getElementType())
                            .getSecondarySemanticHint();
                    secondarySemanticHint =
                        processSecondarySemanticHint(res, secondarySemanticHint);
                    if (res instanceof Activity || res instanceof SubProcess) {
                        if (BpmnPackage.Literals.ACTIVITY__LOOPING.getName()
                                .equals(secondarySemanticHint)) {
                            if (res instanceof Activity) {
                                ((Activity) res).setLooping(true);
                            } else {
                                ((SubProcess) res).setLooping(true);
                            }
                        } else if (res instanceof Activity) {
                            ActivityType at = ActivityType
                                    .get(secondarySemanticHint);
                            if (at != null) {
                                ((Activity) res).setActivityType(at);
                            }
                        }
                    }
                }
                if (FeatureMapUtil.isMany(element, containment)) {
        			((Collection) element.eGet(containment)).add(res);
        		} else {
        			element.eSet(containment, res);
        		}
                return res;
            }
        }
        return null;
    }
    
    /**
     * Ability to customize the creation of an Artifact object according to the
     * secondarySemanticHint passed in the request.
     * <p>
     * To take advantage of this it is required to extend this class and the policy
     * that creates this command.
     * </p>
     * 
     * @param artifact The artifact that has been created
     * @param secondarySemanticHint The secondarySemanticHint passed in the 
     * creation request.
     * @return The secondarySemanticHint as it will be used by the other
     * registered processors. Should return the same one if it does not know what to do.
     */
    private static final String processSecondarySemanticHint(
            EObject objectBeingCreated, String secondarySemanticHint) {
        for (ISecondarySemanticHintProcessor secParser : 
                BpmnDiagramEditorPlugin.getInstance().getSecondarySemanticHintParsers()) {
            secondarySemanticHint = secParser
                .processSecondarySemanticHint(objectBeingCreated, secondarySemanticHint);
        }
        return secondarySemanticHint;
    }
    
    /**
     * Helper that does the call back to the ISecondarySemanticHintProcessors
     * @param objectBeingCreated
     * @param elementType
     */
    protected static void processSecondarySemanticHint(
            EObject objectBeingCreated, IElementType elementType) {
        if (!(elementType instanceof IElementTypeEx)) {
            return;
        }
        String secondarySemanticHint = ((IElementTypeEx) elementType)
                .getSecondarySemanticHint();
        processSecondarySemanticHint(objectBeingCreated, secondarySemanticHint);
    }
}
