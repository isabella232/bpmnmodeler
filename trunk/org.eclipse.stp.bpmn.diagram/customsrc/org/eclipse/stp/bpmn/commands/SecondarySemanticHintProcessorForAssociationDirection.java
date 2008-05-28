/******************************************************************************
 * Copyright (c) 2000-2007, Intalio Inc.
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
 * Jul 2, 2007     hmalphettes         Created
 **/

package org.eclipse.stp.bpmn.commands;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.DirectionType;
import org.eclipse.stp.bpmn.TextAnnotation;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.dnd.ISecondarySemanticHintProcessor;
import org.eclipse.swt.graphics.Image;

/**
 * In charge of setting the direction type on an association being created.
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class SecondarySemanticHintProcessorForAssociationDirection
implements ISecondarySemanticHintProcessor {

    /**
     * @param createdSemanticElement The semantic object just created
     * @param secondarySemanticHint The value to process to eventually
     * customize the object just created.
     * @return The secondarySemanticHint as it will be further processed by the
     * other registered ISecondarySemanticHintParser
     */
    public String processSecondarySemanticHint(EObject createdSemanticElement,
            String secondarySemanticHint) {
        if (createdSemanticElement instanceof Association) {
            Association assoc = (Association)createdSemanticElement;
            DirectionType dir = DirectionType.get(secondarySemanticHint);
            if (dir != null) {
                if (assoc.getSource() instanceof TextAnnotation) {
                    //for a text annotation during the creation there is no arrow.
                    assoc.setDirection(DirectionType.NONE_LITERAL);
                } else {
                    assoc.setDirection(dir);
                }
            }
        }
        return secondarySemanticHint;
    }
    
    /**
     * @param element
     * @param secondarySemanticHint
     * @return The image for this combination of eclass and secondary semantic hint.
     */
    public Image getImageForTool(ENamedElement element, String secondarySemanticHint) {
        return null;
    }
    /**
     * @param element
     * @param secondarySemanticHint
     * @return The image for this combination of eclass and secondary semantic hint.
     */
    public ImageDescriptor getImageDescriptorForTool(ENamedElement element, 
            String secondarySemanticHint) {
        return null;
    }
    /**
     * @param element
     * @param secondarySemanticHint
     * @return The label for this combination of eclass and secondary semantic hint.
     */
    public String getToolLabel(ENamedElement element, String secondarySemanticHint) {
        if (isProcessedHere(element, secondarySemanticHint)) {
            return BpmnDiagramMessages.bind(BpmnDiagramMessages.SecondarySemanticHintProcessorForAssociationDirection_createAssociation, secondarySemanticHint); 
        }
        return null;
    }
    /**
     * @param element
     * @param secondarySemanticHint
     * @return The tooltip for this combination of eclass and secondary semantic hint.
     */
    public String getToolToolTip(ENamedElement element, String secondarySemanticHint) {
        if (isProcessedHere(element, secondarySemanticHint)) {
            return BpmnDiagramMessages.bind(BpmnDiagramMessages.SecondarySemanticHintProcessorForAssociationDirection_createAssociation, secondarySemanticHint); 
        }
        return null;
    }
    
    private boolean isProcessedHere(ENamedElement element, String secondarySemanticHint) {
        return element.equals(BpmnPackage.eINSTANCE.getAssociation()) &&
            DirectionType.get(secondarySemanticHint) != null;
    }
    
    /**
     * @return an integer that determines in the list of seondarySemanticHintProcessors
     * the order in which they are called. The higher the integer the more they are
     * called first.
     */
    public int getPriority() {
        return 0;
    }
}
