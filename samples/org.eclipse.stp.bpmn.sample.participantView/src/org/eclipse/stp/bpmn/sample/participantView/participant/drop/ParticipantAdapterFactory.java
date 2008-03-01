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

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.stp.bpmn.dnd.IDnDHandler;
import org.eclipse.stp.bpmn.sample.participantView.participant.view.IParticipant;

/**
 * This adapter factory adapts IParticipant objects into IDnDHandlers.
 * Those handlers create the menu item representing the participant
 * and the command to drop the annotation corresponding to it.
 * @author <a href="mailto:hmalphettes@intalio.com">Hugues Malphettes</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ParticipantAdapterFactory implements IAdapterFactory {

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adaptableObject instanceof IParticipant) {
				return new ParticipantDnDHandler((IParticipant) adaptableObject);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	public Class[] getAdapterList() {
		return new Class[] {IDnDHandler.class};
	}

}
