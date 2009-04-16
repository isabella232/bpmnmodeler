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

/** 
 * Date          	Author             Changes 
 * 2 Nov 2006   	BIlchyshyn         Created 
 **/

package org.eclipse.stp.bpmn.handles;

import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author BIlchyshyn
 * @author Intalio Inc
 */
public class DiagramUIPluginImagesEx {

	/**
	 * The icons root directory.
	 */
	static final String PREFIX_ROOT = "icons/handles/"; //$NON-NLS-1$

    public static final String IMG_HANDLE_INCOMING_SOUTH = PREFIX_ROOT + "handle_incoming_south.gif"; //$NON-NLS-1$
    public static final String IMG_HANDLE_OUTGOING_SOUTH = PREFIX_ROOT + "handle_outgoing_south.gif"; //$NON-NLS-1$
    public static final String IMG_HANDLE_INCOMING_NORTH = PREFIX_ROOT + "handle_incoming_north.gif"; //$NON-NLS-1$
    public static final String IMG_HANDLE_OUTGOING_NORTH = PREFIX_ROOT + "handle_outgoing_north.gif"; //$NON-NLS-1$
    
    public static final String IMG_HANDLE_INCOMING_ASSOCIATION_SOUTH = PREFIX_ROOT + "handle_incoming_association_south.gif"; //$NON-NLS-1$
    public static final String IMG_HANDLE_INCOMING_ASSOCIATION_EAST = PREFIX_ROOT + "handle_incoming_association_east.gif"; //$NON-NLS-1$
    public static final String IMG_HANDLE_INCOMING_ASSOCIATION_WEST = PREFIX_ROOT + "handle_incoming_association_west.gif"; //$NON-NLS-1$
    public static final String IMG_HANDLE_INCOMING_ASSOCIATION_NORTH = PREFIX_ROOT + "handle_incoming_association_north.gif"; //$NON-NLS-1$
    
    public static final String IMG_HANDLE_OUTGOING_ASSOCIATION_SOUTH = PREFIX_ROOT + "handle_outgoing_association_south.gif"; //$NON-NLS-1$
    public static final String IMG_HANDLE_OUTGOING_ASSOCIATION_EAST = PREFIX_ROOT + "handle_outgoing_association_east.gif"; //$NON-NLS-1$
    public static final String IMG_HANDLE_OUTGOING_ASSOCIATION_WEST = PREFIX_ROOT + "handle_outgoing_association_west.gif"; //$NON-NLS-1$
    public static final String IMG_HANDLE_OUTGOING_ASSOCIATION_NORTH = PREFIX_ROOT + "handle_outgoing_association_north.gif"; //$NON-NLS-1$

    public static final String IMG_HANDLE_INCOMING_FLOW_EAST = PREFIX_ROOT + "handle_incoming_flow_east.gif"; //$NON-NLS-1$
    public static final String IMG_HANDLE_INCOMING_FLOW_WEST = PREFIX_ROOT + "handle_incoming_flow_west.gif"; //$NON-NLS-1$
    public static final String IMG_HANDLE_OUTGOING_FLOW_EAST = PREFIX_ROOT + "handle_outgoing_flow_east.gif"; //$NON-NLS-1$
    public static final String IMG_HANDLE_OUTGOING_FLOW_WEST = PREFIX_ROOT + "handle_outgoing_flow_west.gif"; //$NON-NLS-1$
    
	/** 
	 * @param imageName
	 *            the full filename of the image
	 * @return the image or null if it has not been cached in the registry
	 */
	public static Image get(String imageName) {
		return BpmnDiagramEditorPlugin.getInstance().getBundledImage(imageName);
	}

}

