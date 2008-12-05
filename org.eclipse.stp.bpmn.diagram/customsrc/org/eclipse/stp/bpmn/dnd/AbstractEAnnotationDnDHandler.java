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
 *
 * Date         Author             Changes
 * Apr 23, 2007      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.dnd;

import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.stp.bpmn.BpmnDiagram;

/**
 * @author atoulme
 * An abstract implementation of IDnDHandler which provides some
 * utility methods to easily create and drop an EAnnotation.
 */
public abstract class AbstractEAnnotationDnDHandler extends AbstractDnDHandler {

	/**
	 * Creates an EAnnotation with a source parameter and
	 * a details map.
	 * @param source
	 * @param details
	 * @return
	 */
	protected EAnnotation createAnnotation(String source, Map<String, String> details) {
		EAnnotation ea = EcoreFactory.eINSTANCE.createEAnnotation();
		ea.setSource(source);
		ea.getDetails().putAll(details);
		return ea;
	}
	
	/**
	 * @param ea the annotation that this handler drops.
	 * @param element the element on which the annotation should be dropped.
	 * @return a command representing the drop of an EAnnotation
	 * on a EModelElement.
	 */
	protected Command createEAnnotationDropCommand(final EAnnotation ea, final EModelElement element) {
		AbstractTransactionalCommand command = new EasyCommand(element) {

			@Override
			protected CommandResult doExecuteWithResult(
					IProgressMonitor monitor, IAdaptable info) 
			throws ExecutionException {
				if (element.getEAnnotation(ea.getSource()) != null) {
					RemoveCommand.create(getEditingDomain(), 
							element,
							EcorePackage.eINSTANCE.getEModelElement_EAnnotations(),
							element.getEAnnotation(
									ea.getSource())).execute();
				}
				SetCommand.create(getEditingDomain(), ea, 
						EcorePackage.eINSTANCE.getEAnnotation_EModelElement(),
						element).execute();
				return CommandResult.newOKCommandResult();
			}};
		return new ICommandProxy(command);
	}
	
	/**
	 * checks that the semantic element resolved by the part
	 * hovered by the tool is an EModelElement.
	 */
	public boolean isEnabled(IGraphicalEditPart hoverPart, int index) {
		EObject elt = hoverPart.resolveSemanticElement();
		if (!(elt instanceof EModelElement)) {
			return false;
		}
		if (elt instanceof BpmnDiagram) {
			return false; // better not drop stuff on the diagram.
		}
		return true;
	}
	
	
	
	
}
