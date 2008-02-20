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
 * Jan 17, 2007      Antoine Toulm�   Creation
 */
package org.eclipse.stp.bpmn.diagram.generation.impl;

import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.ArtifactsContainer;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.AssociationTarget;
import org.eclipse.stp.bpmn.DataObject;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.Group;
import org.eclipse.stp.bpmn.Identifiable;
import org.eclipse.stp.bpmn.Lane;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.TextAnnotation;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.diagram.edit.parts.ActivityEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.AssociationEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.DataObject2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.DataObjectEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.Group2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.GroupEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.LaneEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.MessagingEdgeEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.PoolPoolCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SequenceEdgeEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBodyCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotation2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotationEditPart;
import org.eclipse.stp.bpmn.diagram.generation.IProcessGenerator;
import org.eclipse.stp.bpmn.diagram.generation.IVisualProcessGenerator;
import org.eclipse.stp.bpmn.diagram.generation.impl.BPMNProcessGenerator.InternalRecordingCommand;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;

/**
 * This class extends BPMNProcessGenerator and 
 * implements IVisualProcessGenerator. It is used to create semantic elements
 * and give them the right position and size.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BPMNVisualProcessGenerator  
	implements IVisualProcessGenerator {

	/**
	 * Taken from BpmnDiagramXYLayoutEditPolicy#DEFAULT_X_POOL_COORD
	 * Used to define the initial position of the pool.
	 */
	protected static final int POOL_INTERVAL = 16;
	
	/**
	 * When the container is a subprocess, activities 
	 * are placed on the upper side.
	 * Just to skip on dealing with giant figures.
	 * 
	 * This also applies to the y position of subprocesses in pools.
	 */
	protected static final int CONTAINER_INTERVAL = 10;
//	/**
//	 * Defines the interval between two activities.
//	 */
//	protected static final int ACTIVITY_INTERVAL = 
//		ActivityEditPart.ACTIVITY_FIGURE_SIZE.width/2;
	
	private BPMNProcessGenerator _semanticGenerator;

	/**
	 * Creates an empty BPMNProcessGenerator,
	 * which will creates its own diagram.
	 */
	public BPMNVisualProcessGenerator() {
		_semanticGenerator = new BPMNProcessGenerator();
	}
	
	/**
	 * Creates a BPMNProcessGenerator instance with the given diagram.
	 * @param diagram
	 */
	public BPMNVisualProcessGenerator(Diagram diagram) {
		_semanticGenerator = new BPMNProcessGenerator(diagram);
	}
	
	/**
	 * Creates a BPMNPRocessGenerator that tries to create a new file for this path.
	 * @param path
	 */
	public BPMNVisualProcessGenerator(IPath path) {
		_semanticGenerator = new BPMNProcessGenerator(path);
	}
    
    /**
     * @return Underlying object in charge of building the semantics.
     */
    public IProcessGenerator getSemanticGenerator() {
        return _semanticGenerator;
    }
	
	/**
	 * Creates and adds a task to a parent (either a pool, lane or 
	 * subprocess).
	 * 
	 * @param parent the parent (either a pool or subprocess)
	 * @param name the name of the task
	 * @param the int value for the Activity type.
	 * @see ActivityType for constants to use there.
	 * @return a newly added task
	 * 
	 */
	public Node addActivity(Node parent, String name, int activityType, int x,
			int y, int width, int height) {
        Node view = addActivity(parent, name, activityType);
		setCoordinates(view, x, y , width, height);
		return view;
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
	public Node addLane(Node pool, String name, int x, int y, int width,
			int height) {
        Node view = addLane(pool, name);
		setCoordinates(view, x, y , width, height);
		return view;
	}

	/**
	 * Creates and adds a pool to the model.
	 * 
	 * @param name the name of the pool
	 * 
	 * @return a newly added pool
	 * 
	 */
	public Node addPool(String name, int x, int y, int width, int height) {
        Node view = addPool(name);
		setCoordinates(view, x, y , width, height);
		return view;
	}
	
	/**
	 * sets coordinates on the given view. Very handy when you want to 
	 * reorganize a bunch of tasks.
	 * @param view
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	protected void setCoordinates(
			final View view, int x, int y, int width, int height) {
		final Bounds bounds = NotationFactory.eINSTANCE.createBounds();
		bounds.setX(x);
		bounds.setY(y);
		bounds.setWidth(width);
		bounds.setHeight(height);
		if (view instanceof Node) {
			InternalRecordingCommand command = 
				_semanticGenerator.new InternalRecordingCommand() {

				@Override
				protected void doExecute() {
			((Node) view).setLayoutConstraint(bounds);
				}};
			_semanticGenerator._editingDomain.getCommandStack().execute(command);

		} else {
			// TODO : maybe there are more ways to set the coordinates
			throw new IllegalArgumentException("This view isn't a node, how do I set its coordinates ?" + view); //$NON-NLS-1$
		}
	}
	
	/**
	 * Creates and adds a pool to the model.
	 * 
	 * @param name the name of the pool
	 * 
	 * @return a newly added pool
	 * 
	 */
	public Node addPool(String name) {
		final Pool p = _semanticGenerator.addPool(name);
		InternalRecordingCommand command = 
			_semanticGenerator.new InternalRecordingCommand() {

				@Override
				protected void doExecute() {
					String semanticHints = BpmnVisualIDRegistry.getType(
							PoolEditPart.VISUAL_ID);
					Node poolNotationNode = 
						ViewService.getInstance().createNode(new EObjectAdapter(p),
								_semanticGenerator.getGraphicalModel(), 
								semanticHints, ViewUtil.APPEND,
								BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
//					Bounds bounds = NotationFactory.eINSTANCE.createBounds();
//					bounds.setWidth(PoolEditPart.POOL_WIDTH);
//					bounds.setHeight(PoolEditPart.POOL_HEIGHT);
//					bounds.setX(POOL_INTERVAL);
//					bounds.setY(POOL_INTERVAL + 
//			(((BpmnDiagram)_semanticGenerator.getGraphicalModel().getElement())
//					.getPools().size() - 1) * 
//			(PoolEditPart.POOL_HEIGHT + POOL_INTERVAL));
//					poolNotationNode.setLayoutConstraint(bounds);
					setReturnedObject(poolNotationNode);
				}
			
		};
		_semanticGenerator._editingDomain.getCommandStack().execute(command);
		return (Node) command.getReturnedObject();
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
	public Node addLane(Node pool, String name) {
		final Lane l = _semanticGenerator.addLane(
				(Pool) pool.getElement(), name);
		InternalRecordingCommand command = 
			_semanticGenerator.new InternalRecordingCommand() {

			@Override
			protected void doExecute() {

				String semanticHints = BpmnVisualIDRegistry.getType(
						LaneEditPart.VISUAL_ID);
				Node laneNotationNode = 
					ViewService.getInstance().createNode(new EObjectAdapter(l),
							_semanticGenerator.getGraphicalModel(), 
							semanticHints, ViewUtil.APPEND,
							BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				setReturnedObject(laneNotationNode);
			}

		};
		
		_semanticGenerator._editingDomain.getCommandStack().execute(command);
		return (Node) command.getReturnedObject();
	}
	
	/**
     * Creates and adds a text annotation to the container.
     * 
     * @param container the container
     * @param name the name of the text annotation
     * 
     * @return a newly added text annotation
     * 
     */
    public Node addTextAnnotation(final Node container, String name) {
        final TextAnnotation textAnnotation = _semanticGenerator.addTextAnnotation(
                (ArtifactsContainer) container.getElement(), name);
        InternalRecordingCommand command = 
            _semanticGenerator.new InternalRecordingCommand() {

            @Override
            protected void doExecute() {

                String semanticHints = null;
                if (textAnnotation.getArtifactsContainer() instanceof Graph) {
                    semanticHints =  BpmnVisualIDRegistry.getType(
                        TextAnnotationEditPart.VISUAL_ID);
                } else {
                    semanticHints = BpmnVisualIDRegistry.getType(
                            TextAnnotation2EditPart.VISUAL_ID);
                }
                Node node = 
                    ViewService.getInstance().createNode(
                            new EObjectAdapter(textAnnotation),
                            container, 
                            semanticHints, ViewUtil.APPEND,
                            BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
                setReturnedObject(node);
            }

        };
        
        _semanticGenerator._editingDomain.getCommandStack().execute(command);
        return (Node) command.getReturnedObject();
    }
    
    /**
     * Creates and adds a data object to the container.
     * 
     * @param container the container
     * @param name the name of the data object
     * 
     * @return a newly added data object
     * 
     */
    public Node addDataObject(final Node container, String name) {
        final DataObject dataObject = _semanticGenerator.addDataObject(
                (ArtifactsContainer) container.getElement(), name);
        InternalRecordingCommand command = 
            _semanticGenerator.new InternalRecordingCommand() {

            @Override
            protected void doExecute() {

                String semanticHints = null;
                if (dataObject.getArtifactsContainer() instanceof Graph) {
                    semanticHints =  BpmnVisualIDRegistry.getType(
                        DataObjectEditPart.VISUAL_ID);
                } else {
                    semanticHints = BpmnVisualIDRegistry.getType(
                            DataObject2EditPart.VISUAL_ID);
                }
                Node node = 
                    ViewService.getInstance().createNode(
                            new EObjectAdapter(dataObject),
                            container, 
                            semanticHints, ViewUtil.APPEND,
                            BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
                setReturnedObject(node);
            }

        };
        
        _semanticGenerator._editingDomain.getCommandStack().execute(command);
        return (Node) command.getReturnedObject();
    }
	
    /**
     * Creates and adds a data object to the container.
     * 
     * @param container the container
     * @param name the name of the group
     * 
     * @return a newly added group
     * 
     */
    public Node addGroup(final Node container, String name) {
        final Group group = _semanticGenerator.addGroup(
                (ArtifactsContainer) container.getElement(), name);
        InternalRecordingCommand command = 
            _semanticGenerator.new InternalRecordingCommand() {

            @Override
            protected void doExecute() {

                String semanticHints = null;
                if (group.getArtifactsContainer() instanceof Graph) {
                    semanticHints =  BpmnVisualIDRegistry.getType(
                        GroupEditPart.VISUAL_ID);
                } else {
                    semanticHints = BpmnVisualIDRegistry.getType(
                            Group2EditPart.VISUAL_ID);
                }
                Node node = 
                    ViewService.getInstance().createNode(
                            new EObjectAdapter(group),
                            container, 
                            semanticHints, ViewUtil.APPEND,
                            BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
                setReturnedObject(node);
            }

        };
        
        _semanticGenerator._editingDomain.getCommandStack().execute(command);
        return (Node) command.getReturnedObject();
    }
    
	/**
	 * Creates and adds a task to a parent (either a pool, lane or 
	 * subprocess).
	 * 
	 * @param parent the parent (either a pool or subprocess)
	 * @param name the name of the task
	 * @param the int value for the Activity type.
	 * @see ActivityType for constants to use there.
	 * @return a newly added task
	 * 
	 */
	public Node addActivity(Node poolOrSubProcess, String name, 
			int activityType) {
		final Activity activity = _semanticGenerator.
			addActivity((Graph) poolOrSubProcess.getElement(), 
					name, activityType);
        Node tempParent = null;
		for (Object  child : poolOrSubProcess.getChildren()) {
            if (child instanceof Node &&
                   String.valueOf(PoolPoolCompartmentEditPart.VISUAL_ID).equals(
                            String.valueOf(((Node)child).getType()))) {
            	tempParent = (Node) child;
            } else if (child instanceof Node &&
                    String.valueOf(
                  SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID).equals(
                            String.valueOf(((Node)child).getType()))) {
            	tempParent = (Node) child;
            } 
		}
		
		if (tempParent == null) {
			throw new IllegalArgumentException(
			"Unable to find the compartment view for this graph " +  //$NON-NLS-1$
			poolOrSubProcess);
		}
		final Node parent = tempParent;
		
		InternalRecordingCommand command = 
			_semanticGenerator.new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				
				if (activity.getActivityType().getValue()
						!= ActivityType.SUB_PROCESS) {
					
					String semanticHints2 = BpmnVisualIDRegistry.getType(
							ActivityEditPart.VISUAL_ID);
					Node aNode =
						ViewService.getInstance().createNode(
								new EObjectAdapter(activity),
								(Node)parent, semanticHints2, ViewUtil.APPEND,
								BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
//					Bounds bounds = NotationFactory.eINSTANCE.createBounds();
//					bounds.setX((ActivityEditPart.ACTIVITY_FIGURE_SIZE.width + 
//							ACTIVITY_INTERVAL) * 
//						(((Graph) parent.getElement()).getVertices().size() -1));
//					if (parent.getElement() instanceof Pool) {
//						bounds.setY(PoolEditPart.POOL_HEIGHT / 2 
//							- ActivityEditPart.ACTIVITY_FIGURE_SIZE.height / 2);
//					} else {
//						bounds.setY(CONTAINER_INTERVAL);
//					}
//					if (ActivityType.VALUES_GATEWAYS.
//							contains(activity.getActivityType())) {
//	                	bounds.setHeight(ActivityEditPart.GATEWAY_FIGURE_SIZE);
//	                	bounds.setWidth(ActivityEditPart.GATEWAY_FIGURE_SIZE);
//	                } else if (ActivityType.VALUES_EVENTS.
//	                		contains(activity.getActivityType())) {
//	                	bounds.setHeight(ActivityEditPart.EVENT_FIGURE_SIZE);
//	                	bounds.setWidth(ActivityEditPart.EVENT_FIGURE_SIZE);
//	                } else if (activity.getActivityType().
//	                		getValue() == ActivityType.TASK) {
//	                	bounds.setHeight(
//	                			ActivityEditPart.ACTIVITY_FIGURE_SIZE.height);
//	                	bounds.setWidth(
//	                			ActivityEditPart.ACTIVITY_FIGURE_SIZE.width);
//	                }
//					aNode.setLayoutConstraint(bounds);
					setReturnedObject(aNode);
				} else {
					String semanticHintsForSP = BpmnVisualIDRegistry.getType(
							SubProcessEditPart.VISUAL_ID);

					Node aNode =
						ViewService.getInstance().createNode(
								new EObjectAdapter(activity),
								(Node)parent, semanticHintsForSP, 
								ViewUtil.APPEND,
								BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
//					Location loc = NotationFactory.eINSTANCE.createLocation();
//					loc.setX((ActivityEditPart.ACTIVITY_FIGURE_SIZE.width + 
//							ACTIVITY_INTERVAL) * 
//						(((Graph) parent.getElement()).getVertices().size() - 1));
//					loc.setY(CONTAINER_INTERVAL);
//					aNode.setLayoutConstraint(loc);
					setReturnedObject(aNode);
				}
				
				// just to make sure that the container does its job
//				Bounds bounds = (Bounds) ((Node) parent).getLayoutConstraint();
//				if (bounds == null) {
//					bounds = NotationFactory.eINSTANCE.createBounds();
//				}
//				bounds.setHeight(-1);
//				bounds.setWidth(-1);
//				((Node) parent).setLayoutConstraint(bounds);
			}

		};
		
		_semanticGenerator._editingDomain.getCommandStack().execute(command);
		return (Node) command.getReturnedObject();
	}
	
	/**
	 * Adds a documentation text to the semantic element linked to this 
	 * <code>View</code>.
	 * <p/>
	 * 
	 * @param element the <code>View</code> to add the documentation
	 *        text to
	 * @param documentation the documentation text to add
	 * 
	 */
	public void addDocumentation(View element, String documentation) {
		_semanticGenerator.addDocumentation((Identifiable) element.getElement(), 
				documentation);
	}
	
	/**
	 * Adds an EAnnotation to the semantic element 
	 * represented by the given view.
	 * @param view the view which element is going to be annotated.
	 * @param source the key that ties the element and its annotation.
	 * @param details the map containing the data for the annotation.
	 * @return the EAnnotation.
	 */
	public EAnnotation addAnnotation(View view, String source,
			Map<String, String> details) {
		return _semanticGenerator.addAnnotation(
				(EModelElement) view.getElement(), source, details);
	}
	
	/**
	 * Connects the source and target elements with a sequence.
	 * 
	 * @param source the source element
	 * @param target the target element
	 * 
	 */
	public Edge addSequenceEdge(final Node source, final Node target, String name) {
		final SequenceEdge seqEdge = 
			_semanticGenerator.addSequenceEdge(
					(Vertex) source.getElement(),
					(Vertex) target.getElement(), 
					name);
		InternalRecordingCommand command = 
			_semanticGenerator.new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				String semanticHints = BpmnVisualIDRegistry.getType(
						SequenceEdgeEditPart.VISUAL_ID);
				Edge anEdge =
					(Edge) ViewService.getInstance().createEdge(
							new EObjectAdapter(seqEdge),
							_semanticGenerator.getGraphicalModel(), 
							semanticHints, ViewUtil.APPEND,
							BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				anEdge.setSource(source);
				anEdge.setTarget(target);
				setReturnedObject(anEdge);
			}};
		_semanticGenerator._editingDomain.getCommandStack().execute(command);
			
		return (Edge) command.getReturnedObject();
	}
	
	/**
	 * Connects the source and target elements with a message.
	 * 
	 * @param source the source element
	 * @param target the target element
	 * 
	 */
	public Edge addMessagingEdge(final Node source, final Node target, 
			final String name) {
		final MessagingEdge edge = 
			_semanticGenerator.messageConnect(
					(Activity) source.getElement(), 
					(Activity) target.getElement(), 
					name);
		InternalRecordingCommand command = 
			_semanticGenerator.new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				
				String semanticHints = BpmnVisualIDRegistry.getType(
						MessagingEdgeEditPart.VISUAL_ID);
				Edge anEdge =
					(Edge) ViewService.getInstance().createEdge(
							new EObjectAdapter(edge),
							_semanticGenerator.getGraphicalModel(), 
							semanticHints, ViewUtil.APPEND,
						BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				anEdge.setSource(source);
				anEdge.setTarget(target);
				setReturnedObject(anEdge);
			}};
		_semanticGenerator._editingDomain.getCommandStack().execute(command);

		return (Edge) command.getReturnedObject();
	}
	
	/**
     * Connects the source and target elements with an association.
     * 
     * @param source the artifact
     * @param target the target
     * 
     */
	public Edge addAssociation(final Node source, final Node target) {
        final Association edge = 
            _semanticGenerator.addAssociation(
                    (Artifact) source.getElement(), 
                    (AssociationTarget) target.getElement());
        InternalRecordingCommand command = 
            _semanticGenerator.new InternalRecordingCommand() {

            @Override
            protected void doExecute() {
                
                String semanticHints = BpmnVisualIDRegistry.getType(
                        AssociationEditPart.VISUAL_ID);
                Edge anEdge =
                    (Edge) ViewService.getInstance().createEdge(
                            new EObjectAdapter(edge),
                            _semanticGenerator.getGraphicalModel(), 
                            semanticHints, ViewUtil.APPEND,
                        BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
                anEdge.setSource(source);
                anEdge.setTarget(target);
                setReturnedObject(anEdge);
            }};
        _semanticGenerator._editingDomain.getCommandStack().execute(command);

        return (Edge) command.getReturnedObject();
    }
	
	
	/**
	 * Connects the source and target elements with either a sequence (if in the
	 * same pool) or with a message (if in different pools).
	 * 
	 * @param source the source element
	 * @param target the target element
	 * 
	 */
	public Edge addSequenceOrMessagingEdge(Node source, Node target,
			String name) {
		if (((Vertex) source.getElement()).getGraph().equals(
				((Vertex) target.getElement()).getGraph())) {
			return addSequenceEdge(source, target, name);
		} else {
			return addMessagingEdge(source, target, name);
		}
	} 
	
	/**
	 * Launch a save on the semantic generator.
	 * @see BPMNProcessGenerator#save()
	 */
	public void save() {
		_semanticGenerator.save();
	}
}
