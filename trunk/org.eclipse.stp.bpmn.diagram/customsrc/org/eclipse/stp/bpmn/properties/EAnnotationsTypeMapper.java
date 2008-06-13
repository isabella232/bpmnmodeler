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
package org.eclipse.stp.bpmn.properties;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.ui.views.properties.tabbed.AbstractTypeMapper;

/**
 * Maps IGraphicalEditParts to EModelElements, so that they can be detected and 
 * the BPMN tab shown.
 * 
 * Hugues : this mapper is currently not used.
 * The notion of tabbed properties contributor is deceptive as only a single one
 * of the contributor extension point will have its attributes
 * (typeMapper, labelProvider) taken into account. And it is not under controle
 * (no priorities). A coupe of related bugs have been filed:
 * https://www.eclipse.org/bugs/show_bug.cgi?id=158607
 * 
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class EAnnotationsTypeMapper extends AbstractTypeMapper {

	/**
     * @inheritDoc
     */
    public Class mapType(Object input) {
        Class type = null;
        if (input instanceof IGraphicalEditPart) {
        	View v = ((IGraphicalEditPart) input).getNotationView();
        	EObject tmpObj = v.getElement();
        	if (tmpObj != null) {
        		type = tmpObj.getClass();
        	}
        } else if (input instanceof EObject) {
        	type = input.getClass();
        }
        if (type == null) {
        	type = super.mapType(input);
        }
        return type;
    }
}
