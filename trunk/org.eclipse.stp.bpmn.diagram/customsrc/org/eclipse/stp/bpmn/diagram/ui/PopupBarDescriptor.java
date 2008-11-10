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
package org.eclipse.stp.bpmn.diagram.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.gef.DragTracker;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.NullElementType;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * 
 * Class to hold pertinent information about the tool placed on the popup bar
 * Overridden to provide an icon and a text only.
 * Added the ability to choose if the image should be disposed or not.
 * @author atoulme
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class PopupBarDescriptor {

	/** The action button tooltip */
	private String tooltip = new String();

	/** The image for the button */
	private Image icon = null;

	/** The typeinfo used to create the Request for the command */
	private IElementType elementType;

	/** The DracgTracker / Tool associatd with the popup bar button */
	private DragTracker dragTracker = null;

	
	private int severity = IStatus.OK;
	/**
	 * wether the image should be disposed.
	 */
	private boolean _shouldBeDisposed = true;
	
	public PopupBarDescriptor(String message, int severity) {
		this(message, getImage(severity));
		this.severity = severity;
	}
	/** 
	 * 
	 * @param s the text of the label and the text of the tooltip.
	 * @param i the image that is associated with the label.
	 * @param shouldbeDisposed, true if the image is to be disposed.
	 */
	public PopupBarDescriptor(
			String s,
			Image i) {
		this(s, i, null);
	}
	
	public PopupBarDescriptor(
			String s,
			Image i,
			DragTracker theTracker) {
		this(s, i, NullElementType.getInstance(), theTracker);
	}
	/**
	 * constructor
	 * @param s
	 * @param i
	 * @param elementType
	 * @param theTracker
	 */
	public PopupBarDescriptor(
			String s,
			Image i,
			IElementType elementType,
			DragTracker theTracker) {
		tooltip = s;
		icon = i;
		dragTracker = theTracker;
		this.elementType = elementType;

	}

	/**
	 * gets the element type associated with this Descriptor
	 * @return element type
	 */
	public final IElementType getElementtype() {
		return elementType;
	}

	/**
	 * gets the icon associated with this Descriptor
	 * @return Image
	 */
	public final Image getIcon() {
		return icon;
	}

	/**
	 * gets the drag tracker associated with this Descriptor
	 * @return drag tracker
	 */
	public final DragTracker getDragTracker() {
		return dragTracker;
	}

	/**
	 * gets the tool tip associated with this Descriptor
	 * @return string
	 */
	public final String getToolTip() {
		return tooltip;
	}

	/**
	 * @return true if the image is to be disposed,
	 * true by default.
	 * May be false when using images from the platform.
	 */
	boolean shouldBeDisposed() {
		return _shouldBeDisposed;
	}

	/**
	 * @param beDisposed the _shouldBeDisposed to set
	 */
	void setShouldBeDisposed(boolean beDisposed) {
		_shouldBeDisposed = beDisposed;
	}
	
	/**
	 * @return an image according to the severity parameter,
	 * which should be one of the constants of IMarker.
	 */
	private static Image getImage(int severity) {
		String imageName = ISharedImages.IMG_OBJS_ERROR_TSK;
		switch (severity) {
		case IStatus.ERROR:
			imageName = ISharedImages.IMG_OBJS_ERROR_TSK;
			break;
		case IStatus.WARNING:
			imageName = ISharedImages.IMG_OBJS_WARN_TSK;
			break;
		default:
			imageName = ISharedImages.IMG_OBJS_INFO_TSK;
		}
		return PlatformUI.getWorkbench().getSharedImages().getImage(
				imageName);
	}
	/**
	 * @return the severity
	 */
	public int getSeverity() {
		return severity;
	}
	/**
	 * @param severity the severity to set
	 */
	public void setSeverity(int severity) {
		this.severity = severity;
	}

} // end PopupBarDescriptor
