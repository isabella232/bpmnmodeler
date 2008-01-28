/**
 *  Copyright (C) 2007, Intalio Inc.
 *
 *  The program(s) herein may be used and/or copied only with the
 *  written permission of Intalio Inc. or in accordance with the terms
 *  and conditions stipulated in the agreement/contract under which the
 *  program(s) have been supplied.
 *
 * Date         Author             Changes
 * Apr 23, 2007      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.dnd;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.swt.graphics.Image;

/**
 * @author atoulme
 * an abstract class representing the IDnDHandler.
 * 
 * Clients can subclass this class.
 */
public abstract class AbstractDnDHandler implements IDnDHandler {

	protected Map<Object, Image> cachedImages = new HashMap<Object, Image>();
	
	/**
	 * Utility class used to simplify the use of AbstracTransactionalCommand.
	 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
	 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
	 */
	protected abstract class EasyCommand extends AbstractTransactionalCommand {
		public EasyCommand(EObject elt) {
			super((TransactionalEditingDomain) AdapterFactoryEditingDomain.
					getEditingDomainFor(elt),
					"", //$NON-NLS-1$
					getWorkspaceFiles(elt));
		}
		
		public EasyCommand(EObject elt, String name) {
			super((TransactionalEditingDomain) AdapterFactoryEditingDomain.
					getEditingDomainFor(elt),
					name,
					getWorkspaceFiles(elt));
		}
	}
	
	public void dispose() {
		for (Image img : cachedImages.values()) {
			if (img != null) {
				img.dispose();
			}
		}
		cachedImages.clear();
	}
}
