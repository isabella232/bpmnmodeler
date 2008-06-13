/*
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 */
package org.eclipse.stp.bpmn.sample.participantView.participant.view;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;


/**
 * This is a sample view representing some participants.
 * @author <a href="mailto:hmalphettes@intalio.com">Hugues Malphettes</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ParticipantView extends ViewPart {

	private TreeViewer tree;


	/**
	 * 
	 */
	public ParticipantView() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		tree = new TreeViewer(parent);
		
		tree.setContentProvider(new ParticipantContentProvider());
		int ops = DND.DROP_COPY | DND.DROP_MOVE;
		Transfer[] transfers = new Transfer[] {
				LocalSelectionTransfer.getTransfer()};
		ParticipantDragAdapter drag = new ParticipantDragAdapter();
		tree.addDragSupport(ops, transfers, drag);
		tree.setInput("");
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
	}

	private class ParticipantImpl implements IParticipant {

		private String name;
		private String role;
		
		
		public ParticipantImpl(String name, String role) {
			super();
			this.name = name;
			this.role = role;
		}

		public String getName() {
			return name;
		}

		public String getRole() {
			return role;
		}
		
		@Override
		public String toString() {
			return name + "(" + role + ")";
		}
		
	}
	
	
	/**
	 * Simple drag adapter that fills the LocalSelectionTransfer with the selection,
	 * @author <a href="mailto:hmalphettes@intalio.com">Hugues Malphettes</a>
	 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
	 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
	 */
	private class ParticipantDragAdapter extends DragSourceAdapter {
		@Override
		public void dragStart(DragSourceEvent event) {
			LocalSelectionTransfer.getTransfer().setSelection(tree.getSelection());
		}
	}
	
	/**
	 * a static content provider representing some simple participants.
	 * @author <a href="mailto:hmalphettes@intalio.com">Hugues Malphettes</a>
	 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulmé</a>
	 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
	 */
	private class ParticipantContentProvider implements ITreeContentProvider {

		/**
		 * The String representing the root of the tree.
		 */
		private String ROOT = "Participants";
		
		/**
		 * Adds some static children under the root,
		 */
		public Object[] getChildren(Object parentElement) {
			if (ROOT.equals(parentElement)) {
				return new Object[] {
						new ParticipantImpl("Hugues", "Developer"),
						new ParticipantImpl("Antoine", "Developer"),
						new ParticipantImpl("Janet", "attendee"),
						new ParticipantImpl("John", "attendee"),
						new ParticipantImpl("Denis", "Eclipse Organization"),
						new ParticipantImpl("Vanessa", "Eclipse Organization")
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
}
