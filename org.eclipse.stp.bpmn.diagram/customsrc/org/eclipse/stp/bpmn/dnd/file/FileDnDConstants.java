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
package org.eclipse.stp.bpmn.dnd.file;

/**
 * Constants used to retrieve the annotation attached to the shape
 * and the path of the afile stored in the annotation.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public interface FileDnDConstants {

	/**
	 * The annotation source used to retrieve the annotation.
	 */
	public static final String ANNOTATION_SOURCE = "genericFile"; //$NON-NLS-1$
	/**
	 * The path to the file linked to the shape.
	 */
	public static final String PROJECT_RELATIVE_PATH = "projectRelativePath"; //$NON-NLS-1$
	/**
	 * An optional parameter to reference the line number in the file.
	 */
	public static final String LINE_NUMBER = "lineNumber"; //$NON-NLS-1$
}
