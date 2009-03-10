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
 * Dates       		 Author              Changes
 * Jan 15, 2007      Antoine Toulm&eacute;   Creation
 */
package org.eclipse.stp.bpmn.diagram.generation.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.ArtifactsContainer;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.AssociationTarget;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.BpmnFactory;
import org.eclipse.stp.bpmn.DataObject;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.Group;
import org.eclipse.stp.bpmn.Identifiable;
import org.eclipse.stp.bpmn.Lane;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.TextAnnotation;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnDiagramEditPart;
import org.eclipse.stp.bpmn.diagram.generation.IProcessGenerator;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramFileCreator;

/**
 * Process Generator for the BPMN API.
 * It can build anything given a diagram or a file as an entry.
 * If given a file, it will create the Resource objects and 
 * their respective Diagram and BpmnDiagram objects.
 * 
 * It will use them extensively to create objects after that, 
 * so creating a pool add it directly to the stored diagrams.
 * 
 * The generator provides a method to save the diagrams' data.
 * 
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BPMNProcessGenerator implements IProcessGenerator {
	
	private BpmnDiagram _semanticModel;
	private Diagram _graphicalModel;
	protected TransactionalEditingDomain _editingDomain;

	/**
	 * Default constructor.
	 * Creates a virtual diagram.
	 */
	public BPMNProcessGenerator() {
		this(new Path("/virtual/modeler.bpmn_diagram"), true); //$NON-NLS-1$
	}
	/**
	 * @see BPMNProcessGenerator#BPMNProcessGenerator(IPath, boolean)
	 * @param path. The full path in the workspace where the diagram is generated.
	 */
	public BPMNProcessGenerator(IPath path) {
		this(path, false);
	}
	/**
	 * Creates a transactional editing domain and creates resources,
	 * placed as to fit the path.
	 * The constructor supposes the creation of the handle 
	 * and its semantic file match,
	 * which should have the same name and the .bpmn file extension.
	 * 
	 * The constructor finally creates a diagram and a BpmnDiagram object.
	 * @param the path to a file supposed to hold the graphical 
	 * elements, ie the bpmn_diagram file.
	 * 
	 * Note that this method is inspired from 
	 * BpmnDiagramEditorUtil#createNewDiagramFile
	 * 
	 * Appends to the diagram generated.
	 */
	public BPMNProcessGenerator(final IPath path, final boolean virtual) {
		
		_editingDomain = GMFEditingDomainFactory.INSTANCE
        	.createEditingDomain();
		final ResourceSet resourceSet = _editingDomain.getResourceSet();
		InternalRecordingCommand command = 
			new InternalRecordingCommand(){

			@SuppressWarnings("unchecked") //$NON-NLS-1$
			@Override
			protected void doExecute() {

				/*
				 * the graphical resource is created first as it will depend
				 * on the semantic resource, the semantic resource is
				 * deducted from the graphical resource path so that it is
				 * relative to it.
				 * In the bpmn_diagram file, the path to semantic elements 
				 * should be modeler.bpmn#ID, not
				 * test.bpm/modeler.bpmn#ID.
				 *  
				 */
				//create the graphical resource.
				IFile diagramFile = null;
				if (!virtual) {
                    diagramFile = BpmnDiagramFileCreator.getInstance().
                        createNewFile(path.removeLastSegments(1), 
                                path.lastSegment(), null, null,
                            new IRunnableContext() {
                                public void run(boolean fork, boolean cancelable,
                                                IRunnableWithProgress runnable)
                                throws InvocationTargetException, InterruptedException {
                                    runnable.run(new NullProgressMonitor());
                                }
                            }
                        );
                }
				Resource graphicalResource = resourceSet.createResource(
						URI.createPlatformResourceURI(virtual ? path.toString() : 
							diagramFile.getFullPath().toString()));

				// create the path to the semantic resource so that it is 
				// relative to the graphical resource.
				IPath modelFileRelativePath = path
				.removeFileExtension().addFileExtension("bpmn"); //$NON-NLS-1$
				IFile modelFile = null;
				if (!virtual) {
					modelFile = diagramFile.getParent().getFile(
						new Path(modelFileRelativePath.lastSegment()));
				}
				// create the semantic resource.
				Resource semanticResource = resourceSet.createResource(
						URI.createPlatformResourceURI(virtual ? 
								modelFileRelativePath.toString() : 
									modelFile.getFullPath().toString()));
				
				// now create the semantic models and 
				// add them to their respective resources.
				_semanticModel = 
					BpmnFactory.eINSTANCE.createBpmnDiagram();
				semanticResource.getContents().add(_semanticModel);

				_graphicalModel = ViewService.getInstance().
				createDiagram(new EObjectAdapter(_semanticModel),
						BpmnDiagramEditPart.MODEL_ID,
						BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);

				graphicalResource.getContents().add(_graphicalModel);
			}};
		_editingDomain.getCommandStack().execute(command);
	}
	/**
	 * Create a BPMNProcessGenerator from an existing diagram.
	 * Guesses the editing domain from the diagram directly.
	 * @param diagram
	 * 
	 * Appends to the given diagram.
	 */
	public BPMNProcessGenerator(Diagram diagram) {
		_editingDomain = (TransactionalEditingDomain) 
			AdapterFactoryEditingDomain.getEditingDomainFor(diagram);
		_graphicalModel = diagram;
		_semanticModel = (BpmnDiagram) diagram.getElement();
	}
	
	/**
	 * Adds a documentation text to a <code>EModelElement</code>.
	 * <p/>
	 * 
	 * @param element the <code>EModelElement</code> to add the documentation
	 *        text to.
	 * @param documentation the documentation text to add.
	 * 
	 */
	public void addDocumentation(final Identifiable identifiable, 
			final String documentation) {
		InternalRecordingCommand command = new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				EObject element = identifiable;
				if (element instanceof Activity) {
					((Activity) element).setDocumentation(documentation);
				} else if (element instanceof MessagingEdge) {
					((MessagingEdge) element).setDocumentation(documentation);
				} else if (element instanceof SequenceEdge) {
					((SequenceEdge) element).setDocumentation(documentation);
				} else if (element instanceof Lane) {
					((Lane) element).setDocumentation(documentation);
				} else if (element instanceof Pool) {
					((Pool) element).setDocumentation(documentation);
				} else if (element instanceof BpmnDiagram) {
					((BpmnDiagram) element).setDocumentation(documentation);
				} else {
					throw new IllegalArgumentException("Don't know how to add " + //$NON-NLS-1$
							"documentation to this element " + element); //$NON-NLS-1$
				}
			}};
		_editingDomain.getCommandStack().execute(command);
	}

	/**
	 * Creates and adds a lane to the pool.
	 * 
	 * @param pool the pool
	 * @param name the name of the lane
	 * 
	 * @return a newly added lane
	 * 
	 */
	public Lane addLane(final Pool pool, final String name) {
		
		InternalRecordingCommand command = new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				Lane l = BpmnFactory.eINSTANCE.createLane();
				l.setPool(pool);
				l.setName(name);
				setReturnedObject(l);
			}};
		_editingDomain.getCommandStack().execute(command);
		
		return (Lane) command.getReturnedObject();
	}

	/**
	 * Creates and adds a pool to the model.
	 * 
	 * @param name the name of the pool
	 * 
	 * @return a newly added pool
	 * 
	 */
	public Pool addPool(final String name) {
		InternalRecordingCommand command = new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				Pool p = BpmnFactory.eINSTANCE.createPool();
				p.setBpmnDiagram(_semanticModel);
				p.setName(name);

				setReturnedObject(p);
			}};
		_editingDomain.getCommandStack().execute(command);

		return (Pool) command.getReturnedObject();
	}

	/**
	 * Connects the source and target elements with a sequence.
	 * 
	 * @param source the source element
	 * @param target the target element
	 * 
	 */
	public SequenceEdge addSequenceEdge(final Vertex source, 
			final Vertex target, final String name) {
		InternalRecordingCommand command = new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				SequenceEdge edge = BpmnFactory.eINSTANCE.createSequenceEdge();
				edge.setGraph(target.getGraph());
				edge.setSource(source);
				edge.setTarget(target);
				edge.setName(name);
				setReturnedObject(edge);
				
			}};
		_editingDomain.getCommandStack().execute(command);

		return (SequenceEdge) command.getReturnedObject();
	}

	
	/**
	 * Connects the source and target elements with an association.
	 * 
	 * @param source the source element, the artifact
	 * @param target the target element, an identifiable (anything except
	 * the diagram and the artifacts)
	 * 
	 */
	public Association addAssociation(final Artifact source, 
			final AssociationTarget target) {
		InternalRecordingCommand command = new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				Association edge = BpmnFactory.eINSTANCE.createAssociation();
				edge.setSource(source);
				edge.setTarget(target);
				setReturnedObject(edge);
				
			}};
		_editingDomain.getCommandStack().execute(command);

		return (Association) command.getReturnedObject();
	}
	
	
	/**
	 * Creates and adds a task to a parent (either a pool, lane or 
	 * subprocess).
	 * 
	 * @param parent the parent (either a pool or subprocess)
	 * the parent of the activity may not be null.
	 * @param name the name of the task
	 * @param the int value for the Activity type.
	 * @see ActivityType for constants to use there.
	 * @return a newly added task
	 * 
	 */
	public Activity addActivity(final Graph poolOrSubProcess, final String name, 
			final int activityType) {
		assert(poolOrSubProcess != null);
		
		InternalRecordingCommand command = new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				
				if (activityType != ActivityType.SUB_PROCESS) {
					Activity activity = BpmnFactory.eINSTANCE.createActivity();
					
					activity.setGraph((Graph) poolOrSubProcess);
					activity.setActivityType(ActivityType.get(activityType));
					
					activity.setName(name);
					
					setReturnedObject(activity);
				} else {
					SubProcess sp = BpmnFactory.eINSTANCE.createSubProcess();
					
					sp.setActivityType(ActivityType.get(activityType));
					sp.setGraph((Graph) poolOrSubProcess);
					sp.setName(name);
					
					
					setReturnedObject(sp);
				}
				

				
			}};
		_editingDomain.getCommandStack().execute(command);

		return (Activity) command.getReturnedObject();
	}
	
	/**
	 * Creates and adds a text annotation to a parent (either a pool, lane,  
	 * subprocess or a diagram).
	 * 
	 * @param parent the parent
	 * @param name the name of the text annotation
	 * 
	 * @return a newly added text annotation.
	 * 
	 */
	public TextAnnotation addTextAnnotation(final ArtifactsContainer artifactContainer, 
			final String name) {
		
		
		InternalRecordingCommand command = new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				
					TextAnnotation text = BpmnFactory.
						eINSTANCE.createTextAnnotation();
					
					text.setArtifactsContainer(artifactContainer);
					text.setName(name);
					
					setReturnedObject(text);
			}};
		_editingDomain.getCommandStack().execute(command);

		return (TextAnnotation) command.getReturnedObject();
	}
	
	/**
	 * Creates and adds a data object to a parent (either a pool, lane,  
	 * subprocess or a diagram).
	 * 
	 * @param parent the parent
	 * @param name the name of the data object
	 * 
	 * @return a newly added data object
	 * 
	 */
	public DataObject addDataObject(final ArtifactsContainer artifactContainer, 
			final String name) {
		
		
		InternalRecordingCommand command = new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				
				DataObject dataObject = BpmnFactory.
						eINSTANCE.createDataObject();
					
				dataObject.setArtifactsContainer(artifactContainer);
				dataObject.setName(name);
					
				setReturnedObject(dataObject);
			}};
		_editingDomain.getCommandStack().execute(command);

		return (DataObject) command.getReturnedObject();
	}
	
	/**
	 * Creates and adds a group to a parent (either a pool, lane,  
	 * subprocess or a diagram).
	 * 
	 * @param parent the parent
	 * @param name the name of the group
	 * 
	 * @return a newly added group
	 * 
	 */
	public Group addGroup(final ArtifactsContainer artifactContainer, 
			final String name) {
		
		
		InternalRecordingCommand command = new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				
				Group group = BpmnFactory.eINSTANCE.createGroup();
					
				group.setArtifactsContainer(artifactContainer);
				group.setName(name);
					
				setReturnedObject(group);
			}};
		_editingDomain.getCommandStack().execute(command);

		return (Group) command.getReturnedObject();
	}
	
	
	/**
	 * Creates an event on the border of the given subprocess.
	 * It does NOT check the validity of the event type.
	 * 
	 * @param parent the subprocess that is going to be linked to this 
	 * event handler
	 * @param name the name for the event handler
	 * @param activityType the activity type for the evetn handler.
	 * Should be one of {@link ActivityType#EVENT_INTERMEDIATE_COMPENSATION},
	 * {@link ActivityType#EVENT_INTERMEDIATE_ERROR}, 
	 * {@link ActivityType#EVENT_INTERMEDIATE_TIMER}
	 * @return
	 */
	public Activity addBorderEvent(final SubProcess parent, final String name, 
			final int activityType) {
		InternalRecordingCommand command = new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				Activity activity = BpmnFactory.eINSTANCE.createActivity();

				activity.setEventHandlerFor(parent);
				activity.setActivityType(ActivityType.get(activityType));

				activity.setName(name);

				setReturnedObject(activity);

			}};
			_editingDomain.getCommandStack().execute(command);

		return (Activity) command.getReturnedObject();
	}

	/**
	 * Connects the source and target elements with a message.
	 * 
	 * @param source the source element
	 * @param target the target element
	 * 
	 */
	public MessagingEdge messageConnect(final Activity source, final Activity target, 
			final String name) {
		InternalRecordingCommand command = new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				MessagingEdge edge = BpmnFactory.eINSTANCE.createMessagingEdge();
				edge.setBpmnDiagram(_semanticModel);
				edge.setSource((Activity) source);
				edge.setTarget((Activity) target);
				edge.setName(name);
				
				setReturnedObject(edge);
			}};
		_editingDomain.getCommandStack().execute(command);

		return (MessagingEdge) command.getReturnedObject();
	}

//	/**
//	 * Connects the source and target elements with either a sequence (if in the
//	 * same pool) or with a message (if in different pools).
//	 * 
//	 * @param source the source element
//	 * @param target the target element
//	 * 
//	 */
//	public Identifiable sequenceOrMessageConnect(Activity source, Activity target,
//			String name) {
//		if ((source).getGraph().equals(
//				(target).getGraph())) {
//			return addSequenceEdge(source,target,name);
//		} else {
//			return messageConnect(source,target,name);
//		}
//	} 
	
	/**
	 * Adds an EAnnotation to the semantic element 
	 * represented by the given view.
	 * @param view the view which element is going to be annotated.
	 * @param source the key that ties the element and its annotation.
	 * @param details the map containing the data for the annotation.
	 * @return the EAnnotation.
	 */
	public EAnnotation addAnnotation(
			final EModelElement modelElement, final String source, 
			final Map<String,String> details) {
		InternalRecordingCommand command = new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
                EAnnotation ann = modelElement.getEAnnotation(source);
                if (ann == null) {
                    ann = EcoreFactory.eINSTANCE.createEAnnotation();
                    ann.setSource(source);
                }
				ann.getDetails().putAll(details);
				ann.setEModelElement(modelElement);
				setReturnedObject(ann);
			}
			
		};
		_editingDomain.getCommandStack().execute(command);
		return (EAnnotation) command.getReturnedObject();
	}

	
	/**
	 * Saves the resources present in the transactional editing domain.
	 * It saves them with an encoding set to UTF-8.
	 * 
	 * This method should be used cautiously as the domain may hold more than 
	 * the resources that the user have been working on in the generator. 
	 * As an alternative, the user can reimplement this method and filter 
	 * the resources.
	 * 
	 * If the process generator is launched on an opened diagram,
	 * it might be as well to modify it and let the user save the changes.
	 */
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	public void save() {
		Map options = new HashMap();
		options.put(XMIResource.OPTION_ENCODING, "UTF-8"); //$NON-NLS-1$
		
			try {
                _semanticModel.eResource().save(options);
                _graphicalModel.eResource().save(Collections.EMPTY_MAP);
			} catch (IOException e) {
				BpmnDiagramEditorPlugin.getInstance().getLog().log(new Status(
						IStatus.ERROR,BpmnDiagramEditorPlugin.ID,
						IStatus.ERROR,BpmnDiagramMessages.BPMNProcessGenerator_cannot_save_resource, e));
			}
		
	}
	
	
	/**
	 * This command extends RecordingCommand by giving the opportunity
	 * to the user to return the created object of the command.
	 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
	 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
	 */
	protected abstract class InternalRecordingCommand extends RecordingCommand {

		private EObject _returnedObject;
		
		public InternalRecordingCommand() {
			super(_editingDomain);
		}

		

		/**
		 * @return the _returnedObject
		 */
		public EObject getReturnedObject() {
			return _returnedObject;
		}

		/**
		 * @param object the _returnedObject to set
		 */
		protected void setReturnedObject(EObject object) {
			_returnedObject = object;
		}
		
	}
	
	/**
	 * 
	 * @return the Diagram object that holds the views.
	 * This method is declared final as we want the BPMNProcessGenerator
	 * to be the class in control of such an object.
	 */
	protected final Diagram getGraphicalModel() {
			return _graphicalModel;
	}
	
	/**
	 * This method is declared final as we want the BPMNProcessGenerator
	 * to be the class in control of such an object.
	 * @return the BpmnDiagram object the hols the semantic elements.
	 */
	protected final BpmnDiagram getSemanticModel() {
		return _semanticModel;
	}
}
