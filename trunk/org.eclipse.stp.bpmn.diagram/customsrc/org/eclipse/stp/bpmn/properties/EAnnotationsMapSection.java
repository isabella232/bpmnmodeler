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
package org.eclipse.stp.bpmn.properties;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractPropertySection;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

/**
 * Table representing the annotations attached to the selected object.
 * This table appears in the properties Tab "Annotations".
 * @author atoulme
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class EAnnotationsMapSection extends AbstractPropertySection {

	private final static String KEY_COLUMN = "Key"; //$NON-NLS-1$
	
	private final static String VALUE_COLUMN = "Value"; //$NON-NLS-1$

	private TableViewer _viewer;

	/**
	 * Creates the controls of the Property page section.
	 * Creates a table containing information on the 
	 * annotations attached to the selection.
	 */
	public void createControls(Composite parent,
			TabbedPropertySheetPage aTabbedPropertySheetPage) {
		super.createControls(parent, aTabbedPropertySheetPage);

		GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        GridData data = new GridData(GridData.FILL_BOTH);
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        
        parent.setLayout(layout);
        parent.setLayoutData(data);
//		Composite composite = getWidgetFactory()
//		.createFlatFormComposite(parent);
		
		_viewer = new TableViewer(getWidgetFactory().createTable(
//				composite, 
				parent,
				SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION));
		
		_viewer.getTable().setLinesVisible(true);
		_viewer.getTable().setHeaderVisible(true);


		TableColumn keyColumn = new TableColumn(_viewer.getTable(), SWT.LEFT);
		keyColumn.setText(KEY_COLUMN);
		TableColumn valueColumn = new TableColumn(_viewer.getTable(), SWT.LEFT);
		valueColumn.setText(VALUE_COLUMN);
		
		GridData ldata = new GridData(GridData.FILL_BOTH);
        ldata.grabExcessHorizontalSpace = true;
        ldata.grabExcessVerticalSpace = true;
        
		TableLayout data2 = new TableLayout();
		_viewer.getTable().setLayout(data2);
		_viewer.getTable().setLayoutData(ldata);
		data2.addColumnData(new ColumnWeightData(1,FigureUtilities.
				getTextWidth(KEY_COLUMN + "              ", //$NON-NLS-1$
						_viewer.getTable().getFont()),true));
		data2.addColumnData(new ColumnWeightData(3,FigureUtilities.
				getTextWidth(VALUE_COLUMN, _viewer.getTable().getFont()),true));
		
		
        
		_viewer.setContentProvider(new EAnnotationsMapContentProvider());
		_viewer.setLabelProvider(new EAnnotationsMapLabelProvider());
		_viewer.setColumnProperties(new String[] {KEY_COLUMN,VALUE_COLUMN});
		_viewer.setCellEditors(new CellEditor[]{null, 
				new TextCellEditor(_viewer.getTable())});
		_viewer.setCellModifier(new ICellModifier() {
			/**
			 * Can only modify the column named value.
			 */
			public boolean canModify(Object element, String property) {
				if (element instanceof Object[]) {
					if (((Object[]) element)[0]!= null) {
						if (property.equals(VALUE_COLUMN)) {
							return true;
						}
					}
				}
				return false;
			}
			/**
			 * @return the value of the property for the given element.
			 */
			public Object getValue(Object element, String property) {
				if (element instanceof Object[]) {
					if (property != null && (property.equals(VALUE_COLUMN))) {
						return ((EAnnotation) ((Object[]) element)[0]).
							getDetails().get(((Object[]) element)[1]);
					}
					if (property != null && (property.equals(KEY_COLUMN))) {
						return ((Object[]) element)[1];
					}
				}
				return null;
			}
			/**
			 * modifies the value of the EAnnotation according to the
			 * value given by the TextCellEditor.
			 */
			public void modify(Object element, String property, final Object value) {
				if (element instanceof TableItem) {
					if ((((TableItem) element).getData()) instanceof Object[]) {
						final Object[] data = (Object[])
						((TableItem) element).getData();
						final EAnnotation ea = (EAnnotation) (data)[0];
						ModifyBpmnEAnnotationCommand command = 
							new ModifyBpmnEAnnotationCommand(
									ea, BpmnDiagramMessages.EAnnotationsMapSection_command_name) {

							@Override
							protected CommandResult doExecuteWithResult(
									IProgressMonitor arg0, 
									IAdaptable arg1) throws ExecutionException {
								ea.getDetails().put((String) data[1],(String) value);
								
								return CommandResult.newOKCommandResult();
							}};
							try {
								command.execute(null,null);
								refresh();
								refreshSelection();
							} catch (ExecutionException e) {
								BpmnDiagramEditorPlugin.
								getInstance().getLog().log(new Status(
										IStatus.ERROR,
										BpmnDiagramEditorPlugin.ID,
										IStatus.ERROR,
										e.getMessage(),
										e));
							}
							
					}
				}

			}});
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#setInput(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		if(selection instanceof IStructuredSelection) {
			EModelElement elt = null;
			Object unknownInput = 
				((IStructuredSelection) selection).getFirstElement();
			if (unknownInput instanceof IGraphicalEditPart && 
				(((IGraphicalEditPart) unknownInput).getPrimaryView() != null)) {
				unknownInput = ((IGraphicalEditPart) unknownInput).
				getPrimaryView().getElement();
			}
			if (unknownInput
					instanceof EModelElement) {
				elt = (EModelElement) unknownInput;
				_viewer.setInput(elt);
				return;
			}
		}
		_viewer.setInput(null);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		_viewer.refresh();
	}
	
	/**
	 * Refreshes the graphical selection after a modify operation.
	 *
	 */
	private void refreshSelection() {
		if (getSelection() instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) getSelection();
			for (Object selElt : sel.toList()) {
				if (selElt instanceof EditPart) {
					((EditPart) selElt).refresh();
				}
			}
		}
	}
}
