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
package org.eclipse.stp.bpmn.validation.internal;

import org.eclipse.gmf.runtime.common.ui.util.UIModificationValidator;

/**
 * A way to encapsulate the reference to the UIModificationValidator
 * so that we don't load the gmf.runtime.common.ui plugin if it is
 * designer is running in headless mode.
 * 
 * @author hmalphettes
 */
public class InternalHelper {

	/**
	 * Some hack to prevent a race condition that happens sometimes.
	 */
	public static void loadAndDisposeUIModificationValidator() {
		new UIModificationValidator().dispose();
	}
	
}
