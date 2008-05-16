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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.internal.l10n.DiagramUIPluginImages;
import org.eclipse.swt.graphics.Image;

/**
 * @author atoulme
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 * Inspired from PopupbarEditPolicy
 */
public class RoundedRectangleWithTail extends RoundedRectangle {

	private static Image IMAGE_POPUPBAR_PLUS = DiagramUIPluginImages
	.get(DiagramUIPluginImages.IMG_POPUPBAR_PLUS);

	private static Image IMAGE_POPUPBAR = DiagramUIPluginImages
	.get(DiagramUIPluginImages.IMG_POPUPBAR);

		private Image myTailImage = null;

		private boolean bIsInit = false;

		private boolean displayAtMouseHoverLocation = false;
		
		private int myCornerDimension = 6;

		/**
		 * constructor
		 */
		public RoundedRectangleWithTail() {
			// we do not make the myActionTailFigue opaque because it
			// doesn't look good when magnification is set.
			this.setFill(true);
			this.setBackgroundColor(ColorConstants.buttonLightest);
			this.setForegroundColor(ColorConstants.lightGray);
			this.setVisible(true);
			this.setEnabled(true);
			this.setOpaque(true);

		}

		/**
		 * @see org.eclipse.draw2d.Figure#paintFigure(org.eclipse.draw2d.Graphics)
		 */
		public void paintFigure(Graphics graphics) {
		    graphics.setAlpha(127);
			int shiftWidth = 3;
			Image theTail = getTail();
			Rectangle theBounds = this.getBounds().getCopy();
			theBounds.height -= theTail.getBounds().height;
			theBounds.height -= shiftWidth;// shift slight above cursor
			theBounds.x += shiftWidth; // shift slight to right of cursor
			theBounds.width -= (shiftWidth + 1); // otherwise rhs is clipped

			// fill the round rectangle first since it is opaque
			graphics.fillRoundRectangle(
				theBounds,
				myCornerDimension,
				myCornerDimension);
			graphics.drawRoundRectangle(
				theBounds,
				myCornerDimension,
				myCornerDimension);

			graphics.drawImage(
				theTail,
				theBounds.x + 6,
				theBounds.y + theBounds.height - 1);

		}
		private Image getTail()
		{
			if(!bIsInit)
			{
				if(isDisplayAtMouseHoverLocation())
				{
					if(myTailImage == null)
					{
						myTailImage = IMAGE_POPUPBAR_PLUS;
						bIsInit = true;
					}
				}
				else
				{
					if(myTailImage == null)
					{
						myTailImage = IMAGE_POPUPBAR;
						bIsInit = true;
					}
				}

			}
			return myTailImage;

		}

		/**
		 * @return the displayAtMouseHoverLocation
		 */
		public boolean isDisplayAtMouseHoverLocation() {
			return displayAtMouseHoverLocation;
		}

		/**
		 * @param displayAtMouseHoverLocation the displayAtMouseHoverLocation to set
		 */
		public void setDisplayAtMouseHoverLocation(boolean displayAtMouseHoverLocation) {
			this.displayAtMouseHoverLocation = displayAtMouseHoverLocation;
		}

}
