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
package org.eclipse.stp.bpmn.sample.participantView.participant.drop;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget.Direction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator;
import org.eclipse.stp.bpmn.sample.participantView.ParticipantViewPlugin;
import org.eclipse.stp.bpmn.sample.participantView.participant.ParticipantConstants;

/**
 * Decorator for participants.
 * @author <a href="mailto:hmalphettes@intalio.com">Hugues Malphettes</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ParticipantDecorator implements IEAnnotationDecorator {

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator#getAssociatedAnnotationSource()
	 */
	public String getAssociatedAnnotationSource() {
		return ParticipantConstants.PARTICIPANT_ANNOTATION;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator#getDirection(org.eclipse.gef.EditPart, org.eclipse.emf.ecore.EModelElement, org.eclipse.emf.ecore.EAnnotation)
	 */
	public Direction getDirection(EditPart part, EModelElement elt,
			EAnnotation ann) {
		return Direction.NORTH_EAST;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator#getToolTip(org.eclipse.gef.EditPart, org.eclipse.emf.ecore.EModelElement, org.eclipse.emf.ecore.EAnnotation)
	 */
	public IFigure getToolTip(EditPart part, EModelElement element,
			EAnnotation annotation) {
		String name = (String) annotation.getDetails().
			get(ParticipantConstants.PARTICIPANT_NAME);
		String role = (String) annotation.getDetails().
			get(ParticipantConstants.PARTICIPANT_ROLE);
		return new Label("Performed by " + name + " (" + role + ")");
	}

    public ImageDescriptor getImageDescriptor(EditPart part,
            EModelElement element, EAnnotation annotation) {
        ImageDescriptor desc = ParticipantViewPlugin.imageDescriptorFromPlugin(
                ParticipantViewPlugin.PLUGIN_ID, "icons/participant.gif");
        return desc;
    }

}
