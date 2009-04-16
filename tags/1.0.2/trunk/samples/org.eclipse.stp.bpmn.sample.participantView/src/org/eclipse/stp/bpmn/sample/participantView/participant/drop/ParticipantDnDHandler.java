/******************************************************************************
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 */
package org.eclipse.stp.bpmn.sample.participantView.participant.drop;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.stp.bpmn.dnd.AbstractEAnnotationDnDHandler;
import org.eclipse.stp.bpmn.sample.participantView.ParticipantViewPlugin;
import org.eclipse.stp.bpmn.sample.participantView.participant.ParticipantConstants;
import org.eclipse.stp.bpmn.sample.participantView.participant.view.IParticipant;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * The DnD handler is in charge of dropping the annotation
 * representing a participant on a BPMN shape which semantic
 * element is a EModelElement.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ParticipantDnDHandler extends AbstractEAnnotationDnDHandler {

	private IParticipant participant;
	
	public ParticipantDnDHandler(IParticipant participant) {
		cachedImages.put(this, AbstractUIPlugin.imageDescriptorFromPlugin(
				ParticipantViewPlugin.PLUGIN_ID, "icons/participant.gif").createImage());
		this.participant = participant;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IDnDHandler#getDropCommand(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart, int, org.eclipse.draw2d.geometry.Point)
	 */
	public Command getDropCommand(IGraphicalEditPart hoverPart, int index,
			Point dropLocation) {
		Map<String, String> details = new HashMap<String, String>();
		details.put(ParticipantConstants.PARTICIPANT_NAME, 
				participant.getName());
		details.put(ParticipantConstants.PARTICIPANT_ROLE, 
				participant.getRole());
		return createEAnnotationDropCommand(createAnnotation(
				ParticipantConstants.PARTICIPANT_ANNOTATION, details), 
				(EModelElement) hoverPart.resolveSemanticElement());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IDnDHandler#getItemCount()
	 */
	public int getItemCount() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IDnDHandler#getMenuItemImage(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart, int)
	 */
	public Image getMenuItemImage(IGraphicalEditPart hoverPart, int index) {
		return cachedImages.get(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IDnDHandler#getMenuItemLabel(org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart, int)
	 */
	public String getMenuItemLabel(IGraphicalEditPart hoverPart, int index) {
		EObject eobject = hoverPart.resolveSemanticElement();
		if (eobject instanceof EModelElement) {
			if (((EModelElement) eobject).getEAnnotation(
					ParticipantConstants.PARTICIPANT_ANNOTATION) != null) {
				return "Adding a participant on this activity will " +
					"remove the previous one";
			}
		}
		return "Add a participant to this activity";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.stp.bpmn.dnd.IDnDHandler#getPriority()
	 */
	public int getPriority() {
		return 0;
	}

}
