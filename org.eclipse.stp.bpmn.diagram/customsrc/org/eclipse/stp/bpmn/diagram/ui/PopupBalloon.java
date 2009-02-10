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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.LayerManager;
import org.eclipse.gmf.runtime.common.ui.services.icon.IconService;
import org.eclipse.gmf.runtime.diagram.ui.tools.PopupBarTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

/**
 * Pops up a balloon representing PopupBarDescriptors as Labels.
 *
 * @author atoulme
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 * 
 * This class has been created using PopupBarEditPolicy.
 * @author affrantz@us.ibm.com, cmahoney
 */
public class PopupBalloon {


	public PopupBalloon(EditPart part) {
		this.layer = (Layer) LayerManager.Helper.find(part).
			getLayer(LayerConstants.FEEDBACK_LAYER); 
		partHashCode = part.hashCode();
	}
	
	/**
	 * Used to know which edit part triggered the balloon, clients application
	 * may want to redraw the balloon if a new edit part triggers one.
	 */
	private int partHashCode;
	
	static private int ACTION_BUTTON_START_X = 5;

	static private int ACTION_BUTTON_START_Y = 5;
	static private int ACTION_MARGIN_RIGHT = 10;

	/** Y postion offset from shape where the balloon top begin. */
	static private int ACTION_WIDTH_HGT = 30;

	static private double BALLOON_X_OFFSET_RHS = 0.65;

	static private double BALLOON_X_OFFSET_LHS = 0.25;
	
	/** Y postion offset from shape where the balloon top begin. */
	static private int BALLOON_Y_OFFSET = 10;
	
	/**
	 * The amount of time to wait before hiding the diagram assistant after it
	 * has been made visible.
	 */
	private static final int DISAPPEARANCE_DELAY = 1000;

    private int disappearance_delay = DISAPPEARANCE_DELAY;
	/** Images created that must be deleted when popup bar is removed */
	protected List<Image> imagesToBeDisposed = new LinkedList<Image>();

	private double myBallonOffsetPercent = BALLOON_X_OFFSET_RHS;

	/** the figure used to surround the action buttons */
	private IFigure myBalloon = null;


	/** The popup bar descriptors for the popup bar buttons */
	private List<PopupBarDescriptor> myPopupBarDescriptors = new LinkedList<PopupBarDescriptor>();

	
	private Layer layer;

	/**
	 * Creates a new popup bar descriptor with an icon according to the severity.
	 * This icon will not be disposed when the balloon is disposed.
	 * @param text
	 */
	public void addPopupBarDescriptor(String text,int severity) {
		if (text == null) {
			return;
		}
		List<PopupBarDescriptor> toRemove = 
			new ArrayList<PopupBarDescriptor>();
		// test if this message isn't already there
		for (PopupBarDescriptor desc : myPopupBarDescriptors) {
			if (desc.getToolTip().equals(text)) {
				if (severity > desc.getSeverity()) {
					toRemove.add(desc);
				} else {
                    return;
				}
			}
		}
			
        myPopupBarDescriptors.removeAll(toRemove);
		PopupBarDescriptor p = new PopupBarDescriptor(text, severity);
		p.setShouldBeDisposed(false);
		myPopupBarDescriptors.add(p);
	}
	
	/**
	 * Adds a popup bar descriptor that is related to the element type.
	 * @param type
	 */
	public void addPopupBarDescriptor(IElementType type) {
		PopupBarDescriptor p = new PopupBarDescriptor(
				BpmnDiagramMessages.bind(BpmnDiagramMessages.PopupBalloon_add_new, type.getDisplayName()), 
				IconService.getInstance().getIcon(type), 
				type,
				getDragTracker());
		p.setShouldBeDisposed(false);
		myPopupBarDescriptors.add(p);
	}
	
	/**
	 * Adds a popup bar descriptor for the given element type, 
	 * and associate it with the given tracker.
	 * @param type
	 * @param tracker
	 */
	public void addPopupBarDescriptor(IElementType type, DragTracker tracker) {
		
		PopupBarDescriptor p = new PopupBarDescriptor(
				BpmnDiagramMessages.bind(BpmnDiagramMessages.PopupBalloon_add_new, type.getDisplayName()), 
				IconService.getInstance().getIcon(type), 
				type,
				tracker);
		p.setShouldBeDisposed(false);
		myPopupBarDescriptors.add(p);
	}
	
	protected IFigure createPopupBarFigure() {
		return new RoundedRectangleWithTail();
	}

	/**
	 * This is the entry point that subclasses can override to fill the
	 * popup bar descrioptors if they have customized tools that cannot be done
	 * using the type along with the modeling assistant service.
	 */
	protected void fillPopupBarDescriptors() {
		// subclasses can override.
	}

	public IFigure getBalloon() {
		return myBalloon;
	}

	/**
	 * Gets the amount of time to wait before hiding the diagram assistant after
	 * it has been made visible.
	 * 
	 * @return the time to wait in milliseconds
	 */
	protected int getDisappearanceDelay() {
		return disappearance_delay;
	}

	/**
	 * gets the popup bar descriptors
	 * @return list
	 */
	protected List<PopupBarDescriptor> getPopupBarDescriptors() {
		return myPopupBarDescriptors;
	}

	/**
	 * Hides the balloon immediately.
	 * Will disassociate the edit part with the balloon, enabling it to be
	 * repainted.
	 */
	public void hide() {
		internalHide(true);

	}

	/**
	 * Hides the balloon, and optionnaly disassociate it with the selected edit
	 * part.
	 * @param disassociateWithEditPart
	 */
	private void internalHide(boolean disassociateWithEditPart) {
		if (getBalloon() != null) {

			teardownPopupBar();
			
		}
	}
	/**
	 * Hides the balloon after a certain amount of time has passed.
	 * 
	 * @param delay
	 *            the delay in milliseconds
	 */
	protected void hideAfterDelay(int delay) {
		if (isShowing()) {
			Display.getCurrent().timerExec(delay, new Runnable() {

				public void run() {
					internalHide(false);
					
				}});
		}
	}

	/**
	 * initialize the popup bars from the list of action descriptors.
	 */
	private void initPopupBars() {

		List<PopupBarDescriptor> theList = getPopupBarDescriptors();
		if (theList.isEmpty()) {
			return;
		}
		myBalloon = createPopupBarFigure();
		
		int yTotal = 0;
		int xTotal =  0;
		

		int yLoc = ACTION_BUTTON_START_Y;
		int xLoc = ACTION_BUTTON_START_X;

		for (PopupBarDescriptor theDesc : theList) {

			Label b = new Label(theDesc.getToolTip(),theDesc.getIcon());

			Rectangle r1 = new Rectangle();
			r1.setLocation(xLoc, yLoc);
			
			b.setFont(Display.getCurrent().getSystemFont());
			r1.setSize(b.getPreferredSize());
			
			b.setBounds(r1);
			yLoc += r1.height;
			getBalloon().add(b);
			yTotal = yLoc + r1.height;
			xTotal = xTotal < r1.width ? r1.width : xTotal;
		}
		
		xTotal += ACTION_BUTTON_START_X + ACTION_MARGIN_RIGHT;
		getBalloon().setSize(xTotal, yTotal);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.DiagramAssistantEditPolicy#isDiagramAssistantShowing()
	 */
	public boolean isShowing() {
		return getBalloon() != null;
	}

	/**
	 * check thee right display status
	 * @return true or false
	 */
	protected boolean isRightDisplay() {
		return (BALLOON_X_OFFSET_RHS == myBallonOffsetPercent);
	}

	/**
	 * This is the default which places the popup bar to favor the right side
	 * of the shape
	 * 
	 */
	protected void setRightHandDisplay() {
		this.myBallonOffsetPercent = BALLOON_X_OFFSET_RHS;
	}

	/**
	 * Populates the popup bar with popup bar descriptors added by suclassing
	 * this editpolicy (i.e. <code>fillPopupBarDescriptors</code> and by
	 * querying the modeling assistant service for all types supported on the
	 * popup bar of this host. For those types added by the modeling assistant
	 * service the icons are retrieved using the Icon Service.
	 */
	protected void populatePopupBars() {
		fillPopupBarDescriptors();
		// nothing more.
	}
	/**
	 * register the part then
	 * shows immediately the ballon at the given reference point.
	 * @param referencePoint
	 */
	public void showBalloon(Point referencePoint, EditPart part) {
		partHashCode = part.hashCode();
		showBalloon(referencePoint);
	}
	/**
	 * shows immediately the ballon at the given reference point.
	 * @param referencePoint
	 */
	public void showBalloon(Point referencePoint) {

		
		// already have one
		if (getBalloon() != null
//				&& getBalloon().getParent() != null
				) {
			return;
		}


		populatePopupBars();
		initPopupBars();

		if (myPopupBarDescriptors.isEmpty()) {
			return; // nothing to show
		}

		// the feedback layer figures do not recieve mouse events so do not use
		// it for popup bars
		
		layer.add(getBalloon());

		if (referencePoint == null) {
			throw new IllegalArgumentException("Reference point is null"); //$NON-NLS-1$
		}
		setLocation(referencePoint);
		// dismiss the popup bar after a delay
		hideAfterDelay(getDisappearanceDelay());
	}

	/**
	 * Destroys the balloon and disposes the images,
	 * except the error image from SharedImages.IMG_ERROR.
	 */
	private void teardownPopupBar() {
		if (myBalloon.getParent() != null) {
			layer.remove(myBalloon);
		} 
		myBalloon = null;
		for (PopupBarDescriptor desc : myPopupBarDescriptors) {
			if (desc.shouldBeDisposed()) {
				imagesToBeDisposed.add(desc.getIcon()); 
			}
		}
		this.myPopupBarDescriptors.clear();
		setRightHandDisplay(); // set back to default

		for (Image img : imagesToBeDisposed) {
			img.dispose();
		}
		imagesToBeDisposed.clear();

	}
	/**
	 * Sets the location of the popup balloon,
	 * with an offset on y corresponding to the height of the balloon
	 * and an offset on x corresponding to the tail of the balloon.
	 * @param location
	 */
	public void setLocation(Point location) {
		location = location.getTranslated(10, 
				-(getBalloon().getBounds().height + 30));
		getBalloon().setLocation(location);
		
	}

	// not working...
//	public void translateToRelative(Point point) {
//		
//		System.err.println(point);
//		System.err.println(getLayer(LayerConstants.FEEDBACK_LAYER));
//		getLayer(LayerConstants.FEEDBACK_LAYER).translateToRelative(point);
//		System.err.println(point);
//		point.y = point.y - BALLOON_Y_OFFSET 
//		- getBalloon().getBounds().height;
//		getBalloon().setLocation(point);
//	}
	
	public boolean showsOnThisEditPart(EditPart part) {
		if (part != null && part.hashCode() == partHashCode) {
			return true;
		}
		return false;
	}
	
	/**
	 * @return a drag tracker for the popup balloon items.
	 * Used only if no drag tracker is specified.
	 * 
	 * Clients can override this method to specify what they want to do when 
	 * the user clicks on an item.
	 */
	protected DragTracker getDragTracker() {
		return null;
	}

	/**
	 * @return true if no popup bar descriptors are present.
	 * @see java.util.List#isEmpty()
	 */
	public boolean hasPopupBarDescriptors() {
		return myPopupBarDescriptors.isEmpty();
	}
	
	/**
	 * Shows the popup menu and sets the resultList selected by the user.
	 * 
	 * @param location, the balloon will be shown at the given location
	 * @return true if this succeeded, false otherwise (e.g. if the user
	 *         cancelled the gesture).
	 */
	public void showBalloonExclusive(Point location, Control control) {
		
		showBalloon(location);

		

		Display display = control.getDisplay();
		while (getBalloon() != null) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	/**
	 * Command that would have been created by one of the poupBarDescriptors.
	 * @return
	 */
	public Command getCommand() {
		for (PopupBarDescriptor descriptor : myPopupBarDescriptors) {
			if (descriptor.getDragTracker() instanceof PassivePopupBarTool) {
				if (((PassivePopupBarTool) descriptor.
						getDragTracker()).getCommand() != null) {
					return ((PassivePopupBarTool) descriptor.
							getDragTracker()).getCommand();
				}
			}
		}
		return null;
	}
	
	public class PassivePopupBarTool extends PopupBarTool {

    	private PopupBalloon _balloon;
    	
		public PassivePopupBarTool(PopupBalloon balloon, 
				EditPart epHost, IElementType elementType) {
			super(epHost, elementType);
			_balloon = balloon;
		}
    	
		/**
		 * 
		 * @see org.eclipse.gef.tools.AbstractTool#handleButtonUp(int)
		 */
		protected boolean handleButtonUp(int button) {
			setCurrentCommand(getCommand());
			if (_balloon != null) {
				_balloon.hide();
			}
			return true;
		}
		
		/**
		 * 
		 * @see org.eclipse.gef.tools.AbstractTool#handleButtonUp(int)
		 */
		protected boolean handleButtonDown(int button) {
			// do nothing
			return false;
		}
		
		@Override
		public Command getCommand() {
			// TODO Auto-generated method stub
			return super.getCommand();
		}
    }

    public void setDisappearanceDelay(int disappearance_delay) {
        this.disappearance_delay = disappearance_delay;
    }
}
