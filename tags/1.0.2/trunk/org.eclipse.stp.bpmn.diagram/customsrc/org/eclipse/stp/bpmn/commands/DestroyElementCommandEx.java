package org.eclipse.stp.bpmn.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.Vertex;

public class DestroyElementCommandEx extends DestroyElementCommand {

	public DestroyElementCommandEx(DestroyElementRequest request) {
		super(request);
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		EObject destructee = getElementToDestroy();
		
		//first we need to destroy all children
		if ((destructee instanceof Pool) ) {
			Vertex[] children = (Vertex[]) ((Pool) destructee).getVertices().toArray();
			for (int i = 0; i < children.length; i++) {
				DestroyElementCommandEx dec = new DestroyElementCommandEx(new DestroyElementRequest(children[i], false));
				dec.doExecute(monitor, info);
			}
		}	
		if ((destructee instanceof SubProcess)) {
			Vertex[] children = (Vertex[]) ((SubProcess) destructee).getVertices().toArray();
			for (int i = 0; i < children.length; i++) {
				DestroyElementCommandEx dec = new DestroyElementCommandEx(new DestroyElementRequest(children[i], false));
				dec.doExecute(monitor, info);
			}
		}
		
		//only destroy attached elements
		if ((destructee != null) && (destructee.eResource() != null)) {
			// tear down incoming references
			tearDownIncomingReferences(destructee);
			
			// also tear down outgoing references, because we don't want
			//    reverse-reference lookups to find destroyed objects
			tearDownOutgoingReferences(destructee);
			
			// remove the object from its container
			EcoreUtil.remove(destructee);
			
			// in case it was cross-resource-contained
			Resource res = destructee.eResource();
			if (res != null) {
				res.getContents().remove(destructee);
			}
		}
		
		return CommandResult.newOKCommandResult();
	}
}
