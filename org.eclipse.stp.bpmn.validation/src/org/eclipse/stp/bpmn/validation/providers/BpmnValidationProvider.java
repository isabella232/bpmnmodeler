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
package org.eclipse.stp.bpmn.validation.providers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.ui.services.action.contributionitem.AbstractContributionItemProvider;
import org.eclipse.gmf.runtime.common.ui.util.IWorkbenchPartDescriptor;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramWorkbenchPart;
import org.eclipse.gmf.runtime.emf.core.resources.GMFResource;
import org.eclipse.gmf.runtime.emf.core.resources.GMFResourceFactory;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.validation.BpmnValidationMessages;
import org.eclipse.stp.bpmn.validation.BpmnValidationPlugin;
import org.eclipse.stp.bpmn.validation.BpmnValidationPlugin.IValidationMarkerCreationHook;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation;

/**
 * @generated
 */
public class BpmnValidationProvider extends AbstractContributionItemProvider {
    /**
     * @generated
     */
    public static final String MARKER_TYPE =
        BpmnValidationPlugin.PLUGIN_ID + ".diagnostic"; //$NON-NLS-1$
	/**
	 * @generated
	 */
	private static boolean constraintsActive = false;

	/**
	 * @generated
	 */
	public static boolean shouldConstraintsBePrivate() {
		return false;
	}

	/**
	 * @generated
	 */
	protected IAction createAction(String actionId,
			IWorkbenchPartDescriptor partDescriptor) {
		if (ValidateAction.VALIDATE_ACTION_KEY.equals(actionId)) {
			return new ValidateAction(partDescriptor);
		}
		return super.createAction(actionId, partDescriptor);
	}

	/**
	 * @generated
	 */
	public static class ValidateAction extends Action {

		/**
		 * @generated
		 */
		public static final String VALIDATE_ACTION_KEY = "validateAction"; //$NON-NLS-1$

		/**
		 * @generated
		 */
		private IWorkbenchPartDescriptor workbenchPartDescriptor;

		/**
		 * @generated
		 */
		public ValidateAction(IWorkbenchPartDescriptor workbenchPartDescriptor) {
			setId(VALIDATE_ACTION_KEY);
			setText(BpmnValidationMessages.BpmnValidationProvider_validate);
			this.workbenchPartDescriptor = workbenchPartDescriptor;
		}

		/**
		 * @generated
		 */
		public void run() {
			IWorkbenchPart workbenchPart = workbenchPartDescriptor
					.getPartPage().getActivePart();
			if (workbenchPart instanceof IDiagramWorkbenchPart) {
				final IDiagramWorkbenchPart part = (IDiagramWorkbenchPart) workbenchPart;
				try {
					new WorkspaceModifyDelegatingOperation(
							new IRunnableWithProgress() {
								public void run(IProgressMonitor monitor)
										throws InterruptedException,
										InvocationTargetException {
									runValidation(part.getDiagram());
								}
							}).run(new NullProgressMonitor());
				} catch (Exception e) {
                    BpmnValidationPlugin.getDefault().getLog().log(
                            new Status(IStatus.ERROR,
                                    BpmnValidationPlugin.PLUGIN_ID,
                                    IStatus.ERROR,
                                    BpmnValidationMessages.BpmnValidationProvider_validateActionFailed, e));
				}
			}
		}

		/**
		 * @generated
		 */
		public static void runValidation(View view) {
			final View target = view;
			Runnable task = new Runnable() {
				public void run() {
					try {
						constraintsActive = true;
						validate(target);
                    } catch (Throwable t) {
                        BpmnValidationPlugin.getDefault().getLog().log(
                                new Status(IStatus.ERROR, 
                                        BpmnValidationPlugin.PLUGIN_ID,
                                        IStatus.ERROR, 
                                        t.getMessage(), 
                                        t));
					} finally {
						constraintsActive = false;
					}
				}
			};
			TransactionalEditingDomain txDomain = TransactionUtil
					.getEditingDomain(target);
			if (txDomain != null) {
				try {
					txDomain.runExclusive(task);
				} catch (Exception e) {
                    BpmnValidationPlugin.getDefault().getLog().log(
                            new Status(IStatus.ERROR,
                                    BpmnValidationPlugin.PLUGIN_ID,
                                    IStatus.ERROR,
                                    BpmnValidationMessages.BpmnValidationProvider_validateActionFailed, e));
				}
			} else {
				task.run();
			}
		}

		/**
		 * @generated
		 */
		private static Diagnostic runEMFValidator(View target) {
			if (target.isSetElement() && target.getElement() != null) {
				return new Diagnostician() {
					public String getObjectLabel(EObject eObject) {
                        try {
                            return EMFCoreUtil.getQualifiedName(eObject, true);
                        } catch (Exception e) {
                            return "<" + eObject.eClass().getName() + ">"; //$NON-NLS-1$ //$NON-NLS-2$
                        }
					}
				}.validate(target.getElement());
			}
			return Diagnostic.OK_INSTANCE;
		}

		/**
		 * @notgenerated
		 * now taking care of the code
		 */
		private static void validate(View target) {
			IFile diagramFile = (target.eResource() != null) ? WorkspaceSynchronizer
					.getFile(target.eResource())
					: null;
			try {
				if (diagramFile != null)
					diagramFile.deleteMarkers(MARKER_TYPE, false,
							IResource.DEPTH_ZERO);
			} catch (CoreException e) {
                BpmnValidationPlugin.getDefault().getLog().log(
                        new Status(IStatus.ERROR,
                                BpmnValidationPlugin.PLUGIN_ID,
                                IStatus.ERROR,
                                BpmnValidationMessages.BpmnValidationProvider_validateFailed, e));
			}
			Diagnostic diagnostic = runEMFValidator(target);

			IBatchValidator validator = (IBatchValidator) ModelValidationService
					.getInstance().newValidator(EvaluationMode.BATCH);
			validator.setIncludeLiveConstraints(true);
			IStatus status = Status.OK_STATUS;
			if (target.isSetElement() && target.getElement() != null) {
				status = validator.validate(target.getElement());
			}
			List allStatuses = new ArrayList();
			allStatuses.addAll(Arrays.asList(status.isMultiStatus() ? status
					.getChildren() : new IStatus[] { status }));

			HashSet targets = new HashSet();
			for (Iterator it = diagnostic.getChildren().iterator(); it
					.hasNext();) {
				targets.add(getDiagnosticTarget((Diagnostic) it.next()));
			}

			for (Iterator it = allStatuses.iterator(); it.hasNext();) {
				Object nextStatus = it.next();
				if (nextStatus instanceof IConstraintStatus) {
					targets.add(((IConstraintStatus) nextStatus).getTarget());
				}
			}

			Map viewMap = buildElement2ViewMap(target, targets);
			for (Iterator it = diagnostic.getChildren().iterator(); it
					.hasNext();) {
				Diagnostic nextDiagnostic = (Diagnostic) it.next();
				List data = nextDiagnostic.getData();
				if (!data.isEmpty() && data.get(0) instanceof EObject) {
					EObject element = (EObject) data.get(0);
					View view = findTargetView(element, viewMap);
					if (diagramFile != null)
						addMarker(diagramFile, view != null ? view : target,
								element, nextDiagnostic.getMessage(),
								diagnosticToStatusSeverity(
										nextDiagnostic.getSeverity()),
										nextDiagnostic.getCode());
				}
			}

			for (Iterator it = allStatuses.iterator(); it.hasNext();) {
				Object nextStatusObj = it.next();
				if (nextStatusObj instanceof IConstraintStatus) {
					IConstraintStatus nextStatus = (IConstraintStatus) nextStatusObj;
					View view = findTargetView(nextStatus.getTarget(), viewMap);
					if (diagramFile != null)
						addMarker(diagramFile, view != null ? view : target,
								nextStatus.getTarget(),
								nextStatus.getMessage(), 
								nextStatus.getSeverity(),
								nextStatus.getCode());
				}
			}
		}

		/**
		 * @generated
		 */
		private static View findTargetView(EObject targetElement, Map viewMap) {
			if (targetElement instanceof View) {
				return (View) targetElement;
			}
			for (EObject container = targetElement; container != null; container = container
					.eContainer()) {
				if (viewMap.containsKey(container))
					return (View) viewMap.get(container);
			}
			return null;
		}

		/**
		 * @generated
		 */
		private static Map buildElement2ViewMap(View view, Set targets) {
			HashMap map = new HashMap();
			getElement2ViewMap(view, map, targets);
			if (!targets.isEmpty()) {
				Set path = new HashSet();
				for (Iterator it = targets.iterator(); it.hasNext();) {
					EObject nextNotMapped = (EObject) it.next();
					for (EObject container = nextNotMapped.eContainer(); container != null; container = container
							.eContainer()) {
						if (!map.containsKey(container)) {
							path.add(container);
						} else
							break;
					}
				}
				getElement2ViewMap(view, map, path);
			}
			return map;
		}

		/**
		 * @generated
		 */
		private static void getElement2ViewMap(View view, Map map, Set targets) {
			if (!map.containsKey(view.getElement())
					&& targets.remove(view.getElement())) {
				map.put(view.getElement(), view);
			}
			for (Iterator it = view.getChildren().iterator(); it.hasNext();) {
				getElement2ViewMap((View) it.next(), map, targets);
			}
			if (view instanceof Diagram) {
				for (Iterator it = ((Diagram) view).getEdges().iterator(); it
						.hasNext();) {
					getElement2ViewMap((View) it.next(), map, targets);
				}
			}
		}

		/**
		 * @notgenerated
		 * adding a code to keep track of the error.
		 */
		private static void addMarker(IFile file, View view, EObject element,
				String message, int statusSeverity, int code) {
			try {
                IMarker marker = file.createMarker(MARKER_TYPE);
				marker.setAttribute(IMarker.MESSAGE, message);
				
				String location = EMFCoreUtil.getQualifiedName(element, true);
				if (location.startsWith("<Diagram>::")) {
				    location = location.substring("<Diagram>::".length());
				}
				marker.setAttribute(IMarker.LOCATION, location);
				
				marker.setAttribute(
								org.eclipse.gmf.runtime.common.ui.resources.IMarker.ELEMENT_ID,
								ViewUtil.getIdStr(view));
				int markerSeverity = IMarker.SEVERITY_INFO;
				if (statusSeverity == IStatus.WARNING) {
					markerSeverity = IMarker.SEVERITY_WARNING;
				} else if (statusSeverity == IStatus.ERROR
						|| statusSeverity == IStatus.CANCEL) {
					markerSeverity = IMarker.SEVERITY_ERROR;
				}
				marker.setAttribute(IMarker.SEVERITY, markerSeverity);
				marker.setAttribute("code", code); //$NON-NLS-1$
				
				List<IValidationMarkerCreationHook> hooks =
				    BpmnValidationPlugin.getDefault().getCreationMarkerCallBacks();
				if (hooks != null) {
				    for (IValidationMarkerCreationHook hook : hooks) {
				        hook.validationMarkerCreated(marker, element);
				    }
				}
			} catch (CoreException e) {
                BpmnValidationPlugin.getDefault().getLog().log(
                        new Status(IStatus.ERROR,
                                BpmnValidationPlugin.PLUGIN_ID,
                                IStatus.ERROR,
                                BpmnValidationMessages.BpmnValidationProvider_markerCreationFailed, e));
			}
		}

		/**
		 * @generated
		 */
		private static EObject getDiagnosticTarget(Diagnostic diagnostic) {
			if (!diagnostic.getData().isEmpty()) {
				Object target = diagnostic.getData().get(0);
				return target instanceof EObject ? (EObject) target : null;
			}
			return null;
		}

		/**
		 * @generated
		 */
		private static int diagnosticToStatusSeverity(int diagnosticSeverity) {
			if (diagnosticSeverity == Diagnostic.OK) {
				return IStatus.OK;
			} else if (diagnosticSeverity == Diagnostic.INFO) {
				return IStatus.INFO;
			} else if (diagnosticSeverity == Diagnostic.WARNING) {
				return IStatus.WARNING;
			} else if (diagnosticSeverity == Diagnostic.ERROR
					|| diagnosticSeverity == Diagnostic.CANCEL) {
				return IStatus.ERROR;
			}
			return IStatus.INFO;
		}
	}

	/**
	 * @generated
	 */
	static boolean isInDefaultEditorContext(Object object) {
		if (shouldConstraintsBePrivate() && !constraintsActive) {
			return false;
		}
		if (object instanceof View) {
			return constraintsActive
					&& object instanceof Diagram && ((Diagram)object).getElement()
                    instanceof BpmnDiagram;
		}
		return true;
	}
    
    private static GMFResourceFactory RESOURCE_FACTORY = new GMFResourceFactory();
    
    /**
     * Calls the batch validation on a diagram file.
     * Used by builders
     * @param diagramFile
     * @param monitor
     * @throws IOException
     */
    public static final void validateDiagramFile(IFile diagramFile,
            IProgressMonitor monitor) throws IOException {
        GMFResource bpmnResource = (GMFResource)
        RESOURCE_FACTORY.createResource(
                URI.createURI(diagramFile.getFullPath().toPortableString()));
        bpmnResource.load(bpmnResource.getDefaultLoadOptions());
        
        Collection<EObject> selectedEObjects = bpmnResource.getContents();
        
        for (EObject eobj : selectedEObjects) {
            if (eobj instanceof Diagram) {
                final Diagram diagram = (Diagram)eobj;
                try {
                    new WorkspaceModifyDelegatingOperation(
                            new IRunnableWithProgress() {
                                public void run(IProgressMonitor monitor)
                                        throws InterruptedException,
                                        InvocationTargetException {
                                    ValidateAction.runValidation(diagram);
                                }
                            }).run(monitor);
                } catch (Exception e) {
                    BpmnValidationPlugin.getDefault().getLog().log(
                            new Status(IStatus.ERROR,
                                    BpmnValidationPlugin.PLUGIN_ID,
                                    IStatus.ERROR,
                                    BpmnValidationMessages.BpmnValidationProvider_ValidationActionFailed, e));
                }

            }
            
        }
        
    }
    

} //BpmnValidationProvider
