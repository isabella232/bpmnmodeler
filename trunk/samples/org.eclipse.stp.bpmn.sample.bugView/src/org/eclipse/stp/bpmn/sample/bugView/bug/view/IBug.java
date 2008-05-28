/*
 * Copyright (c) 2008, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 */
package org.eclipse.stp.bpmn.sample.bugView.bug.view;

/**
 * A basic representation of a bug
 * @author <a href="mailto:hmalphettes@intalio.com">Hugues Malphettes</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public interface IBug {

    // bug states
    public static final int NEW = 0;
    
    public static final int ASSIGNED = 1;
    
    public static final int FIXED = 2;
    
	/**
	 * @return the number of the bug
	 */
	public int getNumber();
	
	/**
	 * @return the description of the bug
	 */
	public String getSummary();
	
	/**
	 * @return the state of the bug
	 */
	public int getState();
}
