/**
 * Copyright (C) 2000-2007, Intalio Inc.
 *
 * The program(s) herein may be used and/or copied only with the
 * written permission of Intalio Inc. or in accordance with the terms
 * and conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *
 * Dates       		 Author              Changes
 * Mar 5, 2007      Antoine Toulm&eacute;   Creation
 */
package org.eclipse.stp.bpmn.validation.file;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.validation.BpmnValidationPlugin;
import org.eclipse.stp.bpmn.validation.builder.BatchValidationBuilder;

/**
 * This class checks that files associated with shapes are existing.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class FileExistenceConstraint extends AbstractModelConstraint {

    private static final NullProgressMonitor monitor = new NullProgressMonitor();
    
    public static String GENERIC_FILE_IMPORT_INDEX_CATEGORY_ID = 
        BatchValidationBuilder.GENERIC_FILE_IMPORT_INDEX_CATEGORY_ID;
    
    
	@Override
	public IStatus validate(IValidationContext ctx) {
		if (ctx.getTarget() instanceof EModelElement) {
			EModelElement elt = (EModelElement) ctx.getTarget();
            if (elt instanceof BpmnDiagram) {
                //remove the imports registered on this diagram as they will be put back.
                //this enables removing the imports fi they were removed.
                if (elt.eResource() != null) {
                    IFile file = WorkspaceSynchronizer.getFile(elt.eResource());
                    BpmnValidationPlugin.getResourceImportersRegistry(file.getProject(),
                            GENERIC_FILE_IMPORT_INDEX_CATEGORY_ID,
                            monitor).clearImports(file);
                }
            }
            
			if (elt != null && elt.eResource() != null && elt.getEAnnotation(
//					FileDnDConstants.ANNOTATION_SOURCE
					// using the hard coded value not to import diagram
					"genericFile") != null) { //$NON-NLS-1$
				EAnnotation ea = elt.getEAnnotation("genericFile"); //$NON-NLS-1$
				String str = (String) ea.getDetails().get(/*FileDnDConstants.PROJECT_RELATIVE_PATH*/
						"projectRelativePath"); //$NON-NLS-1$
				IFile file = WorkspaceSynchronizer.getFile(elt.eResource());
                if (file != null) {
                	// try with a folder
					IResource targetFile = file.getProject().getFolder(str);
					if (targetFile == null || !targetFile.exists()) {
						// try again with a file ?
						targetFile = file.getProject().getFile(str);
					}
                    //make sure the file is in the ResourceImportersRegistry:
                    //grab the corresponding index and feed it.
                    BpmnValidationPlugin.getResourceImportersRegistry(file.getProject(),
                            GENERIC_FILE_IMPORT_INDEX_CATEGORY_ID,
                            monitor).addImport(file, targetFile);
                    
					if (targetFile == null || !targetFile.exists()) {
						return ctx.createFailureStatus(new String[] 
						        {targetFile.getProjectRelativePath().toString()});
					}
				}
			}
		}
		return ctx.createSuccessStatus();
	}

}
