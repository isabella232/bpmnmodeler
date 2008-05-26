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
package org.eclipse.stp.bpmn.sample.participantView.participant;

/**
 * Defines the constants used to be used in the annotations 
 * representing participants.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public interface ParticipantConstants {

	/**
	 * This key references the annotation from the shape.
	 */
	public static final String PARTICIPANT_ANNOTATION = "participant";
	/**
	 * This key will reference the name of the participant.
	 */
	public static final String PARTICIPANT_NAME = "name";
	/**
	 * This key will reference the role of the participant.
	 */
	public static final String PARTICIPANT_ROLE = "role";
}
