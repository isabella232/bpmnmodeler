/******************************************************************************
 * Copyright (c) 2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Date         Author                  Changes
 * Feb 29, 2008  Antoine Toulme      Created
 */
package org.eclipse.stp.bpmn.sample.bugeditor;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnEditPartFactory;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotation2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotationEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.diagram.providers.BpmnEditPartProvider;
import org.eclipse.stp.bpmn.sample.bugeditor.editpolicies.OpenBugEditPolicy;

/**
 * We override the default edit part provider by gicing our own factory, extending the default factory.
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class BugEditPartProvider extends BpmnEditPartProvider {

    public BugEditPartProvider() {
        setFactory(new BugEditPartFactory());
        setAllowCaching(true);
    }
    
    /**
     * We extend the BPMNEditPartFactory to give our own edit parts for text annotations.
     * 
     * @author <a href="http://www.intalio.com">Intalio Inc.</a>
     * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
     */
    private class BugEditPartFactory extends BpmnEditPartFactory {
        
        /**
         * Creates a special text annotation edit part
         */
        public EditPart createEditPart(EditPart context, Object model) {
             if (model instanceof View) {
                 final View view = (View) model;
                 int viewVisualID = BpmnVisualIDRegistry.getVisualID(view);
                 switch (viewVisualID) {
                 case TextAnnotationEditPart.VISUAL_ID :
                     return new BugTextAnnotationEditPart(view);
                 case TextAnnotation2EditPart.VISUAL_ID :
                     return new BugTextAnnotation2EditPart(view);
                 }
             }
             return super.createEditPart(context, model);
        }
    }
    
    /**
     * Our text annotation edit part, with a special edit policy to open bugs.
     * 
     * @author <a href="http://www.intalio.com">Intalio Inc.</a>
     * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
     */
    private class BugTextAnnotationEditPart extends TextAnnotationEditPart {

        public BugTextAnnotationEditPart(View view) {
            super(view);
        }
        
        @Override
        protected void createDefaultEditPolicies() {
            super.createDefaultEditPolicies();
            installEditPolicy(EditPolicyRoles.OPEN_ROLE, new OpenBugEditPolicy());
        }
        
    }
    
    /**
     * Our text annotation for diagram text annotations, 
     * with a special edit policy to open bugs
     * 
     * @author <a href="http://www.intalio.com">Intalio Inc.</a>
     * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
     */
    private class BugTextAnnotation2EditPart extends TextAnnotation2EditPart {

        public BugTextAnnotation2EditPart(View view) {
            super(view);
        }
        
        @Override
        protected void createDefaultEditPolicies() {
            super.createDefaultEditPolicies();
            installEditPolicy(EditPolicyRoles.OPEN_ROLE, new OpenBugEditPolicy());
        }
    }
}
