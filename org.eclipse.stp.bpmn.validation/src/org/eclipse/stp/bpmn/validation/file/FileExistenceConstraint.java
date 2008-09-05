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

import java.util.List;

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
import org.eclipse.stp.bpmn.validation.IConstraintStatusEx;
import org.eclipse.stp.bpmn.validation.builder.BatchValidationBuilder;
import org.eclipse.stp.bpmn.validation.quickfixes.IBpmnFileExistenceConstraintCustomizer;
import org.eclipse.stp.bpmn.validation.quickfixes.internal.FileExistenceCustomizerHelper;

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
                //this enables removing the imports if they were removed.
                if (elt.eResource() != null) {
                    IFile file = WorkspaceSynchronizer.getFile(elt.eResource());
                    BpmnValidationPlugin.getResourceImportersRegistry(file.getProject(),
                            GENERIC_FILE_IMPORT_INDEX_CATEGORY_ID,
                            monitor).clearImports(file);
                }
            }
            
			if (elt != null && elt.eResource() != null && elt.getEAnnotation(
//					FileDnDConstants.ANNOTATION_SOURCE
// using the hard coded value to avoid depending on the bpmn.diagram plugin.
					"genericFile") != null) { //$NON-NLS-1$
				EAnnotation ea = elt.getEAnnotation("genericFile"); //$NON-NLS-1$
				String str = (String) ea.getDetails().get(
				        /*FileDnDConstants.PROJECT_RELATIVE_PATH*/
// using the hard coded value to avoid depending on the bpmn.diagram plugin.
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
						IStatus st = ctx.createFailureStatus(new String[] 
						        {targetFile.getProjectRelativePath().toString()});
						if (targetFile != null) {//setup the bpmn quickfixes
    						IConstraintStatusEx consSt = BpmnValidationPlugin.asConstraintStatusEx(st,
    						        FileExistenceContraintQuickfixProvider.ID);
    						if (consSt != null) {
    						    //see the file existence customizers:
    						    boolean setupTheStandardQuikcfix = true;
    						    List<IBpmnFileExistenceConstraintCustomizer> custs =
    						        FileExistenceCustomizerHelper.getFileExistenceCustomizers();
    						    if (custs != null) {
    						        for (IBpmnFileExistenceConstraintCustomizer cu : custs) {
    						            if (cu.validates(elt, targetFile, consSt)) {
    						                setupTheStandardQuikcfix = false;
    						                break;
    						            }
    						        }
    						    }
    						    
    						    if (setupTheStandardQuikcfix) {
        						    //add the attributes for the marker
        						    //so that the marker can be matched to a resolution marker.
        						    consSt.addMarkerAttribute(
        						        FileExistenceContraintQuickfixProvider.MISSING_FILE_PROJ_RELATIVE_PATH_ATTRIBUTE,
        						        targetFile.getProjectRelativePath().toString());
    						    }
    						    return consSt;
    						}
						}
						return st;
					}
				}
			}
		}
		return ctx.createSuccessStatus();
	}
	

	
	

}
