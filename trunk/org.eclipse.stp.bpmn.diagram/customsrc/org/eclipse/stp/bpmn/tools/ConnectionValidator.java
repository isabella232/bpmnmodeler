/******************************************************************************
 * Copyright (c) 2006, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Date				Author				Changes 
 * 27 Oct 2006		BIlchyshyn			Created
 * 2 May 2007		Antoine Toulme		Javadoc
 **/

package org.eclipse.stp.bpmn.tools;

import org.eclipse.gef.EditPart;

/**
 * This interface is designated to help creating utility classes
 * that will show if a connection can be created for a given edit part.
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public interface ConnectionValidator {
	
	/**
	 * @param ep the edit part to examine
	 * @return true if the edit part can be the start of the connection
	 */
	public boolean canStart(EditPart ep);
	
	/**
	 * 
	 * @param ep the edit part to examine
	 * @return true if the edit part can be the end of the connection
	 */
	public boolean canEnd(EditPart ep);
	
	/**
	 * @param source the source of the connection
	 * @param target the target of the connection
	 * @return true if the two edit parts can be connected together
	 */
	public boolean canConnect(EditPart source, EditPart target);
}
