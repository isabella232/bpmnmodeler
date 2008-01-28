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

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gmf.runtime.common.ui.services.util.CommonLabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider in charge of rendering the keys and values of 
 * the annotations attached to the object.
 * Currently based on CommonLabelProvider.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class EAnnotationsMapLabelProvider 
	extends CommonLabelProvider 
	implements ITableLabelProvider {

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		if (element instanceof Object[]) {
			Object obj = ((Object[]) element)[columnIndex];
			if (obj instanceof Image) {
				return (Image) obj;
			} else {
				return super.getImage(obj);
			}
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof Object[]) {
			if (columnIndex < ((Object[]) element).length) {
				Object obj = ((Object[]) element)[columnIndex];
				if (obj == null) {
					return null;
				}
				switch (columnIndex) {
				case 0:
					EAnnotation ea = (EAnnotation) obj;
					return ea.getSource() + ":" +  //$NON-NLS-1$
						((Object[]) element)[columnIndex+1];
					case 1 :
					return ((EAnnotation) ((Object[]) element)[columnIndex-1]).
					getDetails().get(((Object[]) element)[columnIndex]).toString();
					default :
					return super.getText(obj);
				}
			}
		}
		return null;
	}
}
