/******************************************************************************
 * Copyright (c) 2008, Intalio Inc.
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
 * Mar 10, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.sample.bugeditor.editpolicies;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.policies.OpenFileEditPolicy;
import org.eclipse.stp.bpmn.sample.bugDnD.dnd.BugDnDHandler;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;


/**
 * We extend the open file edit policy to open bugs using the browser
 *
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class OpenBugEditPolicy extends OpenFileEditPolicy {

    @Override
    protected Command getOpenCommand(Request request) {
        EditPart part = getTargetEditPart(request);
        if (part == null) {
            return null;
        }
        if (part instanceof IGraphicalEditPart) {
            final EObject object =((IGraphicalEditPart) part).resolveSemanticElement();
            if (object instanceof EModelElement) {
                EAnnotation ann = ((EModelElement) object).
                    getEAnnotation(BugDnDHandler.BUG_ANNOTATION_SOURCE);
                if (ann == null) {
                    return super.getCommand(request);
                }
                Command co = new Command(
                        BpmnDiagramMessages.OpenFileEditPolicy_command_name) {

                    @Override
                    public void execute() {
                        String number = EcoreUtil.getAnnotation(
                                ((EModelElement) object),
                                BugDnDHandler.BUG_ANNOTATION_SOURCE, "number");
                        // and open the browser
                        try {
                            PlatformUI
                                    .getWorkbench()
                                    .getBrowserSupport()
                                    .createBrowser(
                                            IWorkbenchBrowserSupport.AS_EXTERNAL
                                                    | IWorkbenchBrowserSupport.NAVIGATION_BAR
                                                    | IWorkbenchBrowserSupport.LOCATION_BAR,
                                            "openbug", // the id, use the same if you want to reuse the same browser 
                                            "Bug #" + number, // the title
                                            ""). // the tooltip
                                    openURL(
                                            new URL("http://bugs.eclipse.org/"
                                                    + number));
                        } catch (PartInitException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                    }
                };
                return co;
            }
        }
        return super.getCommand(request);
    }
}
