/*
 *******************************************************************************
 ** Copyright (c) 2006, Intalio Inc.
 ** All rights reserved. This program and the accompanying materials
 ** are made available under the terms of the Eclipse Public License v1.0
 ** which accompanies this distribution, and is available at
 ** http://www.eclipse.org/legal/epl-v10.html
 ** 
 ** Contributors:
 **     Intalio Inc. - initial API and implementation
 ********************************************************************************
 */
package org.eclipse.stp.bpmn.diagram.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.gmf.runtime.common.ui.action.actions.global.GlobalPrintAction;
import org.eclipse.gmf.runtime.common.ui.action.global.GlobalAction;
import org.eclipse.gmf.runtime.common.ui.action.global.GlobalActionId;
import org.eclipse.gmf.runtime.common.ui.action.internal.CommonUIActionPlugin;
import org.eclipse.gmf.runtime.common.ui.action.internal.IHelpContextIds;
import org.eclipse.gmf.runtime.common.ui.action.internal.global.GlobalActionHandlerData;
import org.eclipse.gmf.runtime.common.ui.action.internal.l10n.CommonUIActionMessages;
import org.eclipse.gmf.runtime.common.ui.action.internal.l10n.CommonUIActionPluginImages;
import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.AbstractContributionItemProvider;
import org.eclipse.gmf.runtime.common.ui.services.action.global.GlobalActionHandlerContext;
import org.eclipse.gmf.runtime.common.ui.services.action.global.IGlobalActionHandler;
import org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.printing.actions.PrintPreviewAction;
import org.eclipse.gmf.runtime.diagram.ui.printing.internal.printpreview.PrintPreviewHelper;
import org.eclipse.gmf.runtime.diagram.ui.printing.render.actions.EnhancedPrintActionHelper;
import org.eclipse.gmf.runtime.diagram.ui.printing.render.actions.RenderedPrintPreviewAction;
import org.eclipse.gmf.runtime.diagram.ui.printing.render.providers.DiagramWithPrintGlobalActionHandler;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

/**
 * @generated NOT: when generated there was not code at all.
 */
public class BpmnContributionItemProvider extends
		AbstractContributionItemProvider {
    
    
    
	/**
	 * @generated
	 */
	protected IAction createAction(String actionId,
			IWorkbenchPartDescriptor partDescriptor) {
		if (actionId.equals(PrintPreviewAction.ID)) {
		    
		    if (Platform.getOS().equals(Platform.OS_WIN32)) {
		        return new RenderedPrintPreviewAction(new EnhancedPrintActionHelper());
		    }
		    //this is a hack to enable PrintPreview under linux.
		    //instead of windows only.
		    //everything works fine except ... that it prints blank pages
		    //instead of diagrams.
		    final PrintPreviewHelper helper = new PrintPreviewHelper() {
		        /**
		         * Make sure printer is installed. Should not be able to print preview if no
		         * printer is installed, even though technically it will work.
		         * 
		         * Call this immediately with the rest of the initialization.
		         */
		        protected boolean isPrinterInstalled() {
		            Printer printer = null;
		            try {
		                printer = new Printer();
		            } catch (SWTError e) {
		                //I cannot printer.dispose(); because it may not have been
		                //initialized
//		                Trace.catching(DiagramPrintingPlugin.getInstance(),
//		                    DiagramPrintingDebugOptions.EXCEPTIONS_CATCHING,
//		                    PrintPreviewHelper.class, "isPrinterInstalled", //$NON-NLS-1$
//		                    e);

		                if (e.code == SWT.ERROR_NO_HANDLES) {
		                    //[Hugues] this is the hack
		                    PrinterData[] datas = Printer.getPrinterList();
		                    //it might have really been ERROR_NO_HANDLES, but there's
		                    //no way for me to really know
		                    return (datas.length > 0 && datas[0] != null);
		                }

		                //if (e.code != SWT.ERROR_NO_HANDLES)
//		                Log.error(DiagramPrintingPlugin.getInstance(),
//		                    DiagramPrintingStatusCodes.GENERAL_UI_FAILURE,
//		                    "Failed to make instance of Printer object", e); //$NON-NLS-1$
//
//		                //else if another swt error
//		                Trace.throwing(DiagramPrintingPlugin.getInstance(),
//		                    DiagramPrintingDebugOptions.EXCEPTIONS_CATCHING,
//		                    PrintPreviewHelper.class, "isPrinterInstalled", //$NON-NLS-1$
//		                    e);
		                throw e;
		            }

		            printer.dispose();

		            return true;
		        }

		    };
		    final EnhancedPrintActionHelper printActionHelper =
		        new EnhancedPrintActionHelper();
			return new RenderedPrintPreviewAction(printActionHelper) {
			    /**
			     * Enable the menu item if Platform is running on Windows.
			     */
			    public boolean isEnabled() {
			        return true;//isWindows();
			    }
			    /**
			     * Opens a dialog showing how the diagram will look when printed. Uses the
			     * print preview helper and optionally the print action helper.
			     */
			    public void run() {
			        helper.doPrintPreview(printActionHelper);
			    }
			};
            
		}
	    if (actionId.equals(ActionFactory.PRINT.getId())) {
//use our custom print action: it works better for windows too.
//in particular when no shape is selected in the diagram.
//            if (Platform.getOS().equals(Platform.OS_WIN32)) {
//                return GlobalActionManager.getInstance().createActionHandler(
//                        partDescriptor.getPartPage(), ActionFactory.PRINT.getId());
//            }

	        
//	        System.err.println("Hello PRINT");
	        //we use our own forked action for printing:
	        //for some reason GMF disables print if it is not windows.
	        //we will remove this once that gets fixed in GMF.
	        return new GlobalPrintActionForBPMN(partDescriptor.getPartPage());
	    }
		
		return super.createAction(actionId, partDescriptor);
	}

}
/**
 * Fork of {@link GlobalPrintAction} that works for OS that are not windows
 * and has some enhanced getSelection algorithm to select the diagram even if no shape is selected
 * in the opened editor.
 */
class GlobalPrintActionForBPMN extends GlobalAction {

    /**
     * Imagedescriptor for the print action
     */
    private static final ImageDescriptor PRINT_IMAGE = CommonUIActionPlugin.imageDescriptorFromPlugin
        (CommonUIActionPlugin.getPluginId(), CommonUIActionPluginImages.IMG_PRINT_EDIT_ETOOL16);
    /**
     * Imagedescriptor for the print action
     */
    private static final ImageDescriptor DISABLED_PRINT_IMAGE = CommonUIActionPlugin.imageDescriptorFromPlugin
        (CommonUIActionPlugin.getPluginId(), CommonUIActionPluginImages.IMG_PRINT_EDIT_DTOOL16);
        
    /**
     * @param workbenchPage
     */
    public GlobalPrintActionForBPMN(IWorkbenchPage workbenchPage) {
        super(workbenchPage);
    }


    /**
     * @param workbenchPart
     */
    public GlobalPrintActionForBPMN(IWorkbenchPart workbenchPart) {
        super(workbenchPart);
    }

    /* (non-Javadoc)
     * @see org.eclipse.gmf.runtime.common.ui.action.IDisposableAction#init()
     */
    public void init() {
        /* set the id */
        setId(getWorkbenchActionConstant());

        /* set the label */
        setText(CommonUIActionMessages.GlobalPrintAction_label);

        /* change the image in case someone tries this from a context menu,
         * not needed from the file menu */
        setImageDescriptor(PRINT_IMAGE);
        setHoverImageDescriptor(PRINT_IMAGE);
        setDisabledImageDescriptor(DISABLED_PRINT_IMAGE);

        /* set the context sensitive help */
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IHelpContextIds.PX_U_DEFAULT_CS_HELP);
        
        super.init();
    }

    /* (non-Javadoc)
     * @see org.eclipse.gmf.runtime.common.ui.action.internal.global.GlobalAction#getActionId()
     */
    public String getActionId() {
        return GlobalActionId.PRINT;
    }

    /* (non-Javadoc)
     * @see org.eclipse.gmf.runtime.common.ui.action.IRepeatableAction#refresh()
     */
    public void refresh() {
        //also only allows printing for windows, don't test for windows in the
        //global action handler's canHandle, because that's for selection
        //changes
//        setEnabled(true);
        setEnabled(!getGlobalActionHandlerData().isEmpty());
                //&& System.getProperty("os.name").toUpperCase().startsWith("WIN")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /* (non-Javadoc)
     * @see org.eclipse.gmf.runtime.common.ui.action.AbstractActionHandler#isSelectionListener()
     */
    protected boolean isSelectionListener() {
        return true;
    }
    
//Overriding:
    @Override
    protected List getGlobalActionHandlerData() {
        ISelection sel = getSelection();
        if (sel instanceof IStructuredSelection && ((IStructuredSelection)sel).size() == 1
                &&
                ((IStructuredSelection)sel).getFirstElement() instanceof GraphicalEditPart){
            return getObjectContextGlobalActionHandlerData();
        }
        
        return super.getGlobalActionHandlerData();
    }
    
    @Override
    protected ISelection getSelection() {
        ISelection sel = super.getSelection();
        if (sel.isEmpty() && getWorkbenchPart() != null && getWorkbenchPart() instanceof BpmnDiagramEditor) {
            return new StructuredSelection(((BpmnDiagramEditor)getWorkbenchPart()).getDiagramEditPart());
        }
        return sel;
    }
    
    
    @Override
    protected List getObjectContextGlobalActionHandlerData() {
        GlobalActionHandlerContext context = new GlobalActionHandlerContext(
                getWorkbenchPart(), getActionId(), Object.class, false);

        IGlobalActionHandler globalActionHandler =
            //GlobalActionHandlerService
            //.getInstance().getGlobalActionHandler(context);
            new DiagramWithPrintGlobalActionHandler();

        GlobalActionHandlerData data = new GlobalActionHandlerData(
            globalActionHandler, createContext());

        ArrayList list = new ArrayList();
        list.add(data);
        return list;
        //return super.getObjectContextGlobalActionHandlerData();
    }

    
//DEBUGGING
    @Override
    protected void doRun(IProgressMonitor progressMonitor) {
        //System.err.println("stp.bpmn: doRun the print!");  
        super.doRun(progressMonitor);
    }
    
    
}
