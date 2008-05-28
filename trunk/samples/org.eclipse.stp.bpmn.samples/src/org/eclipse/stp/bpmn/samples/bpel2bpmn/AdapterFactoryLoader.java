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
 * Dates       		 Author              Changes
 * Jan 23, 2007      Antoine Toulmé   Creation
 */
package org.eclipse.stp.bpmn.samples.bpel2bpmn;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.stp.bpmn.samples.SamplesPlugin;
import org.eclipse.ui.IStartup;

/**
 * Loads the adapter factory on startup, forcing the start of the bundle.
 * 
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class AdapterFactoryLoader implements IStartup {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IStartup#earlyStartup()
	 */
	public void earlyStartup() {
		// actually not necessary to load the class explicitely.
		// but that won't hurt either
		try {
			Thread.currentThread().getContextClassLoader().loadClass(
			        "org.eclipse.stp.bpmn.samples.bpel2bpmn.BPEL2BPMNAdapterFactory");
		} catch (ClassNotFoundException e) {
			SamplesPlugin.getDefault().getLog().log(
			        new Status(IStatus.ERROR, SamplesPlugin.PLUGIN_ID, 
			                IStatus.ERROR, e.getMessage(),e));
		}
	}

}
