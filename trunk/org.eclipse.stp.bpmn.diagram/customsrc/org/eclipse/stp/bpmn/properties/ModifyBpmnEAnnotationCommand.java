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
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

/**
 * A utility class to simplify 
 * the instanciation of AbstractTransactionalCommand.
 * 
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public abstract class ModifyBpmnEAnnotationCommand 
	extends AbstractTransactionalCommand {

	public ModifyBpmnEAnnotationCommand(EAnnotation ann, String label) {
		super((TransactionalEditingDomain) AdapterFactoryEditingDomain.
				getEditingDomainFor(ann), label, getWorkspaceFiles(ann));
	}

}
