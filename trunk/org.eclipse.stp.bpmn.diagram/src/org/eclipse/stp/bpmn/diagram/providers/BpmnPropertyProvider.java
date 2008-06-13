/*
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 */
package org.eclipse.stp.bpmn.diagram.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.ui.services.properties.GetPropertySourceOperation;
import org.eclipse.gmf.runtime.common.ui.services.properties.ICompositePropertySource;
import org.eclipse.gmf.runtime.common.ui.services.properties.IPropertiesProvider;
import org.eclipse.gmf.runtime.emf.ui.properties.providers.GenericEMFPropertiesProvider;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnDiagramEditPart;

import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;

/**
 * @generated
 */
public class BpmnPropertyProvider extends GenericEMFPropertiesProvider
        implements IPropertiesProvider {

    /**
     * @generated
     */
    public boolean provides(IOperation operation) {
        if (operation instanceof GetPropertySourceOperation) {
            Object object = ((GetPropertySourceOperation) operation)
                    .getObject();
            return getSemanticElement(object) != null;
        }
        return false;
    }

    /**
     * @generated
     */
    public ICompositePropertySource getPropertySource(Object object) {
        EObject element = getSemanticElement(object);
        if (element != null) {
            return super.getPropertySource(element);
        }
        return null;
    }

    /**
     * @generated
     */
    protected EObject getSemanticElement(Object object) {
        View view = null;
        if (object instanceof View) {
            view = (View) object;
        } else if (object instanceof EditPart) {
            EditPart editPart = (EditPart) object;
            if (editPart.getModel() instanceof View) {
                view = (View) editPart.getModel();
            }
        }
        if (view != null
                && BpmnDiagramEditPart.MODEL_ID.equals(BpmnVisualIDRegistry
                        .getModelID(view))) {
            return view.getElement();
        }
        return null;
    }

}
