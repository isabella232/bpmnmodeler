/******************************************************************************
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.stp.bpmn.dnd;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

/**
 * Interface used in the extension point associated with a key (String)
 * designed to enable processing secondarySemanticHints when a semantic
 * BPMN object has just been created. The object can be customized according
 * to the value of the secondarySemanticHint.
 * 
 * @author <a href="mailto:hmalphettes@intalio.com">Hugues Malphettes</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public interface ISecondarySemanticHintProcessor {

    /**
     * @param createdSemanticElement The semantic object just created
     * @param secondarySemanticHint The value to process to eventually
     * customize the object just created.
     * @return The secondarySemanticHint as it will be further processed by the
     * other registered ISecondarySemanticHintParser
     */
    public String processSecondarySemanticHint(EObject createdSemanticElement,
            String secondarySemanticHint);
    
    /**
     * @param element
     * @param secondarySemanticHint
     * @return The image for this combination of eclass and secondary semantic hint.
     */
    public Image getImageForTool(ENamedElement element, String secondarySemanticHint);
    /**
     * @param element
     * @param secondarySemanticHint
     * @return The image for this combination of eclass and secondary semantic hint.
     */
    public ImageDescriptor getImageDescriptorForTool(ENamedElement element, String secondarySemanticHint);
    /**
     * @param element
     * @param secondarySemanticHint
     * @return The label for this combination of eclass and secondary semantic hint.
     */
    public String getToolLabel(ENamedElement element, String secondarySemanticHint);
    /**
     * @param element
     * @param secondarySemanticHint
     * @return The tooltip for this combination of eclass and secondary semantic hint.
     */
    public String getToolToolTip(ENamedElement element, String secondarySemanticHint);
    
    /**
     * @return an integer that determines in the list of seondarySemanticHintProcessors
     * the order in which they are called. The higher the integer the more they are
     * called first.
     */
    public int getPriority();
}
