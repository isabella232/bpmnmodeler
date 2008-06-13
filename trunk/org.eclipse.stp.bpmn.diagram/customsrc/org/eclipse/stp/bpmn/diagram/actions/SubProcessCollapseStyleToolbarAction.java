/**
 *  Copyright (C) 2007, Intalio Inc.
 *
 *  The program(s) herein may be used and/or copied only with the
 *  written permission of Intalio Inc. or in accordance with the terms
 *  and conditions stipulated in the agreement/contract under which the
 *  program(s) have been supplied.
 *
 * Date         Author             Changes
 * Jul 17, 2007      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.diagram.actions;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

/**
 * This class changes the collapse style over a subprocess.
 * 
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class SubProcessCollapseStyleToolbarAction extends DiagramAction {

    public SubProcessCollapseStyleToolbarAction(IWorkbenchPart workbenchPart) {
        super(workbenchPart);
    }
    
    public SubProcessCollapseStyleToolbarAction(IWorkbenchPage workbenchPage) {
        super(workbenchPage);
    }

    public static final String ID = "SubProcessCollapseStyleToolbarAction"; //$NON-NLS-1$
    
    @Override
    public void init() {
        setId(ID);
        super.init();
    }
    
    @Override
    public void refresh() {
        setToolTipText(BpmnDiagramMessages.SubProcessCollapseStyleToolbarAction_tooltip);
        super.refresh();
        if (isEnabled() && getCurrentCollapseExpandValue()) {
            setImageDescriptor(BpmnDiagramEditorPlugin.
                    getBundledImageDescriptor(
                            "icons/obj16/arrangeOnCollapseFalse.gif")); //$NON-NLS-1$
            setText(BpmnDiagramMessages.SubProcessCollapseStyleToolbarAction_label);
        } else {
            setImageDescriptor(BpmnDiagramEditorPlugin.
                    getBundledImageDescriptor(
                            "icons/obj16/arrangeOnCollapse.gif")); //$NON-NLS-1$
            setText(BpmnDiagramMessages.SubProcessCollapseStyleToolbarAction_label_do); 
        }
    }
    
    @Override
    protected boolean calculateEnabled() {
        if (getSelectedObjects().size() != 1) {
            return false;
        }
        
        if (!(getSelectedObjects().iterator().next() 
                instanceof SubProcessEditPart)) {
            return false;
        }
        return true;
    }
    
    /**
     * not implemented, we override getCommand instead.
     */
    @Override
    protected Request createTargetRequest() {
        return null;
    }

    @Override
    protected boolean isSelectionListener() {
        return true;
    }
    
    @Override
    protected Command getCommand() {


        AbstractTransactionalCommand command = 
            new AbstractTransactionalCommand(
                    getDiagramEditPart().getEditingDomain(),
                    "", //$NON-NLS-1$
                    null) {

            @Override
            protected CommandResult doExecuteWithResult(
                    IProgressMonitor monitor, IAdaptable info)
            throws ExecutionException {
                boolean currentValue = getCurrentCollapseExpandValue();
                
                SubProcessEditPart ep = (SubProcessEditPart) getSelectedObjects().iterator().next();
                View view = ep.getNotationView();
                EAnnotation ann = view.getEAnnotation(
                        SubProcessSubProcessBodyCompartmentEditPart.SUBPROCESS_ANNOTATION_SOURCE);
                if (ann == null) {
                    ann = EcoreFactory.eINSTANCE.createEAnnotation();
                    ann.setSource(SubProcessSubProcessBodyCompartmentEditPart.SUBPROCESS_ANNOTATION_SOURCE);
                    view.getEAnnotations().add(ann);
                }
                
                ann.getDetails().put(
                        SubProcessSubProcessBodyCompartmentEditPart.ARRANGE_SIBLINGS, 
                        currentValue ? "false" : "true"); //$NON-NLS-1$ //$NON-NLS-2$
                // invert the current value
                
                // refresh the action
                refresh();
                return CommandResult.newOKCommandResult();
            }
        };

        return new ICommandProxy(command);
        
    }

    /**
     * if the action is enabled, this method may be called
     * to evaluate if the arrange children style is on
     * or off 
     * @return true if the subprocess arranges the children
     * false otherwise.
     */
    private boolean getCurrentCollapseExpandValue() {
        SubProcessEditPart ep = (SubProcessEditPart) getSelectedObjects().iterator().next();
        View view = ep.getNotationView();
        EAnnotation ann = view.getEAnnotation(
                SubProcessSubProcessBodyCompartmentEditPart.SUBPROCESS_ANNOTATION_SOURCE);
        boolean currentValue = BpmnDiagramEditorPlugin.getInstance().
        getPreferenceStore().getBoolean(
                BpmnDiagramPreferenceInitializer.PREF_SP_COLLAPSE_STYLE);
        String annValue = ann == null ? null : ann.getDetails().get(
                SubProcessSubProcessBodyCompartmentEditPart.ARRANGE_SIBLINGS);
        if (annValue != null) {
            currentValue = "true".equals(annValue); //$NON-NLS-1$
        }
        return currentValue;
    }
}
