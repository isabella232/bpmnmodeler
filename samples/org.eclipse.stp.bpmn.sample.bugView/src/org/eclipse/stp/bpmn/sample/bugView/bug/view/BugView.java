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

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.stp.bpmn.sample.bugView.BugViewPlugin;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;


/**
 * This is a sample view representing some bugs.
 * @author <a href="mailto:hmalphettes@intalio.com">Hugues Malphettes</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BugView extends ViewPart {

	private TreeViewer tree;

	/**
	 * 
	 */
	public BugView() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		tree = new TreeViewer(parent);
		
		tree.setContentProvider(new BugContentProvider());
		tree.setLabelProvider(new BugLabelProvider());
		int ops = DND.DROP_COPY | DND.DROP_MOVE;
		Transfer[] transfers = new Transfer[] {
				LocalSelectionTransfer.getTransfer()};
		BugDragAdapter drag = new BugDragAdapter();
		tree.addDragSupport(ops, transfers, drag);
		tree.setInput("");
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
	}

	private class BugImpl implements IBug {

		private int number;
		private String summary;
		private int state;
		
		public BugImpl(int number, String summary, int state) {
			assert summary != null;
			this.number = number;
			this.summary = summary;
			this.state = state;
		}

		public String getSummary() {
			return summary;
		}

		public int getNumber() {
			return number;
		}
		
		public int getState() {
		    return state;
		}
		
		@Override
		public String toString() {
		    String stateStr = "";
		    switch(state) {
		    case IBug.NEW:
		        stateStr = "NEW";
		        break;
		    case IBug.ASSIGNED:
		        stateStr = "ASSIGNED";
		        break;
		    case IBug.FIXED:
		        stateStr = "FIXED";
		        break;
		    }
			return "#" + number + ": " + summary + " " + stateStr;
		}
		
	}
	
	
	/**
	 * Simple drag adapter that fills the LocalSelectionTransfer with the selection,
	 * @author <a href="mailto:hmalphettes@intalio.com">Hugues Malphettes</a>
	 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
	 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
	 */
	private class BugDragAdapter extends DragSourceAdapter {
		@Override
		public void dragStart(DragSourceEvent event) {
			LocalSelectionTransfer.getTransfer().setSelection(tree.getSelection());
		}
	}
	
	/**
	 * a static content provider representing some BPMN bugs.
	 * @author <a href="mailto:hmalphettes@intalio.com">Hugues Malphettes</a>
	 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
	 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
	 */
	private class BugContentProvider implements ITreeContentProvider {

		/**
		 * The String representing the root of the tree.
		 */
		private String ROOT = "BPMN Bugs";
		
		/**
		 * Adds some static children under the root,
		 */
		public Object[] getChildren(Object parentElement) {
			if (ROOT.equals(parentElement)) {
				return new Object[] {
						new BugImpl(214891, "i18n for bpmn", IBug.ASSIGNED),
						new BugImpl(221518, "Create the page 2 diagram for the due diligence process", IBug.NEW),
						new BugImpl(219398, "Duplicate tag on example noGatewayInToolbar", IBug.FIXED),
						new BugImpl(156018, "Initial code drop of the BPMN modeller", IBug.FIXED)
				};
			}
			return null;
		}

		public Object getParent(Object element) {
			return ROOT;
		}

		public boolean hasChildren(Object element) {
			if (ROOT.equals(element)) {
				return true;
			}
			return false;
		}

		public Object[] getElements(Object inputElement) {
			return new Object[] {ROOT};
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			
		}
	}
	
	 private static Image BUG;
     
     private static Image BUG_FIXED;
     
     static {
         ImageDescriptor desc = BugViewPlugin.imageDescriptorFromPlugin(
                 BugViewPlugin.PLUGIN_ID, "icons/bug.jpg");
         BUG = desc.createImage();
         ImageDescriptor desc2 = BugViewPlugin.imageDescriptorFromPlugin(
                 BugViewPlugin.PLUGIN_ID, "icons/bug_fixed.jpg");
         BUG_FIXED = desc2.createImage();
     }
     
	private class BugLabelProvider extends LabelProvider {
	    
	    @Override
	    public Image getImage(Object element) {
	        if (element instanceof IBug) {
	            if (((IBug) element).getState() == IBug.FIXED) {
	                return BUG_FIXED;
	            } else {
	                return BUG;
	            }
	        }
	        return super.getImage(element);
	    }
	    
	    @Override
	    public void dispose() {
	        super.dispose();
	    }
	}
}
