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
 * Jan 24, 2007      Antoine Toulm&eacute;   Creation
 */
package org.eclipse.stp.bpmn.diagram.generation.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.DrawerStyle;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.DataObject;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.Group;
import org.eclipse.stp.bpmn.Lane;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.TextAnnotation;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.Activity2EditPart;
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
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessSubProcessBorderCompartmentEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotation2EditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.TextAnnotationEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;

/**
 * This class extends BPMNProcessGenerator by 
 * creating semantic elements, associating them optionally with a 
 * location and/or a size.
 * Then it is able to generate all the Views at once, 
 * and returning them to the user for further use.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BPMNVisual2ProcessGenerator extends BPMNProcessGenerator {

	/**
	 * Default constructor, will create a virtual diagram.
	 */
	public BPMNVisual2ProcessGenerator() {
		
	}
	/**
	 * Will create a new diagram on the new file.
	 * @param path the path - it'd better be ending with bpmn_diagram.
	 */
	public BPMNVisual2ProcessGenerator(IPath path) {
		super(path);
	}
	/**
	 * Appends to the given diagram.
	 * @param diagram
	 */
	public BPMNVisual2ProcessGenerator(Diagram diagram) {
		super(diagram);
	}
	
	/**
	 * Keeps track of the positions, so that it creates constraints 
	 * when generating the semantic elements' views.
	 */
	private Map<EObject, Rectangle> positions = 
		new HashMap<EObject, Rectangle>();
	/**
	 * Map populated during the generateViews method. Keeps track of the
	 * link between semantic and notational elements,
	 * so that it is possible to select the shape to drop on the editor next.
	 */
	private Map<EObject, View> semantic2notationMap = 
		new LinkedHashMap<EObject, View>();
	/**
	 * List of sequence edges collected during the view generation,
	 * so that their views can be generated after.
	 */
	private List<SequenceEdge> collectedSequenceEdges = 
		new LinkedList<SequenceEdge>();
	/**
	 * List of messaging edges collected during the view generation,
	 * so that their views can be generated after.
	 */
	private List<MessagingEdge> collectedMessagingEdges = 
		new LinkedList<MessagingEdge>();
	
	/**
	 * List of associations collected during the view generation,
	 * so that their views are generated right after.
	 */
	private List<Association> collectedAssociations = 
		new LinkedList<Association>();
	/**
	 * Creates an activity, keep the bounds in memory to apply 
	 * them when generating the view.
	 * @param parent the parent or null
	 * @param name the name of the activity, ie the label of the shape
	 * @param activityType the type of the activity, 
	 * one of the values of ActivityType
	 * @param x the int corresponding to 
	 * the x position of the shape in the parent
	 * @param y the int corresponding to 
	 * the y position of the shape in the parent
	 * @param width the width of the shape
	 * @param height the height of the shape
	 * @return
	 */
	public Activity addActivity(Graph parent, String name, 
			int activityType, int x, int y, int width, int height) {
		Rectangle rect = new Rectangle(x, y, width, height);
		Activity act = super.addActivity(parent, name, activityType);
		positions.put(act, rect);
		return act;
	}
	
	/**
	 * Creates a semantic object represnting a lane and associate it with a pool
	 * @param pool the parent pool
	 * @param name the name of the lane
	 * @param x the x position in parent
	 * @param y the y position in parent
	 * @param width width of the lane
	 * @param height height of the lane
	 * @return
	 */
	public Lane addLane(Pool pool, String name, 
			int x, int y, int width, int height) {
		Rectangle rect = new Rectangle(x, y, width, height);
		Lane lane = super.addLane(pool, name);
		positions.put(lane, rect);
		return lane;
	}
	/**
	 * Creates the semantic element representing a pool
	 * @param name the name of the pool
	 * @param x its x position
	 * @param y its y position
	 * @param width its width 
	 * @param height its height
	 */
	public Pool addPool(String name, int x, int y, int width, int height) {
		Rectangle rect = new Rectangle(x, y, width, height);
		Pool pool = addPool(name);
		positions.put(pool,rect);
		return pool;
	}
	
	/**
	 * Generate the views corresponding to all the semantic elements contained
	 * in the diagram hold by the generator.
	 */
	public List<IStatus> generateViews() {
		List<IStatus> statuses = new LinkedList<IStatus>();
		semantic2notationMap.clear();
		
		statuses.addAll(recursiveGenerate(getSemanticModel().eContents()));
		
		for (Association association : collectedAssociations) {
			semantic2notationMap.put(association, 
					generateAssociationView(association, statuses));
		}
		for (MessagingEdge mesEdge : collectedMessagingEdges) {
			semantic2notationMap.put(mesEdge, 
					generateMessagingEdgeView(mesEdge, statuses));
		}
		
		for (SequenceEdge seqEdge : collectedSequenceEdges) {
			semantic2notationMap.put(seqEdge, 
			generateSequenceEdgeView(seqEdge, statuses));
		}
		return statuses;
	}
	
	@SuppressWarnings("unchecked") //$NON-NLS-1$
	private List<IStatus> recursiveGenerate(List elements) {
		List<IStatus> statuses = new LinkedList<IStatus>();
		for (Object elt : elements) {
			if (elt instanceof Pool) {
				Node pool = generatePoolView((Pool) elt, statuses);
				semantic2notationMap.put((EObject) elt, pool);
				statuses.addAll(recursiveGenerate(((Pool) elt).eContents()));
			} else if (elt instanceof SubProcess) {
				Node sp = generateActivityView((SubProcess) elt, statuses);
				semantic2notationMap.put((EObject) elt, sp);
				statuses.addAll(recursiveGenerate(((SubProcess) elt).eContents()));
			} else if (elt instanceof Activity) {
				semantic2notationMap.put((EObject) elt, 
						generateActivityView((Activity) elt, statuses));
			} else if (elt instanceof SequenceEdge) {
				collectedSequenceEdges.add((SequenceEdge) elt);
			} else if (elt instanceof MessagingEdge) {
				collectedMessagingEdges.add((MessagingEdge) elt);
			} else if (elt instanceof Association) {
				collectedAssociations.add((Association) elt);
			} else if (elt instanceof Lane) {
				semantic2notationMap.put((EObject) elt, 
						generateLaneView((Lane) elt, statuses));
			} else if (elt instanceof EAnnotation) {
				// i won't generate a view for an EAnnotation.
			} else if (elt instanceof TextAnnotation) {
				semantic2notationMap.put((EObject) elt, 
						generateTextAnnotationView((TextAnnotation) elt, statuses));
			} else if (elt instanceof DataObject) {
				semantic2notationMap.put((EObject) elt, 
						generateDataObjectView((DataObject) elt, statuses));
			} else if (elt instanceof Group) {
				semantic2notationMap.put((EObject) elt, 
						generateGroupView((Group) elt, statuses));
			} else {
				IStatus unknownElt = new Status(IStatus.WARNING, 
						BpmnDiagramEditorPlugin.ID, 
						IStatus.WARNING, 
						BpmnDiagramMessages.bind("BPMNVisual2ProcessGenerator_generateViewException", elt), null); //$NON-NLS-1$
				statuses.add(unknownElt);
			}
		}
		return statuses;
	}
	
	/**
	 * Generates the view for a given activity.
	 * @param activity
	 * @return Node
	 */
	private Node generateActivityView(final Activity activity, 
			final List<IStatus> statuses) {
		InternalRecordingCommand command = 
			new InternalRecordingCommand() {

			@Override
			protected void doExecute() {

				Node parent = null;
				if (activity.getGraph() != null) {
					for (Object  child : semantic2notationMap.
							get(activity.eContainer()).getChildren()) {
						if (child instanceof Node && 
								String.valueOf(PoolPoolCompartmentEditPart.VISUAL_ID).
								equals(String.valueOf(((Node)child).getType()))) {
							parent = (Node) child;
						} else if (child instanceof Node && 
								String.valueOf(SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID).
								equals(String.valueOf(((Node)child).getType()))) {
							parent = (Node) child;
						} 
					}
				} else {
					// then the activity is certainly attached to a subprocess
					for (Object  child : semantic2notationMap.
							get(activity.getEventHandlerFor()).getChildren()) {
						if (child instanceof Node && 
								String.valueOf(SubProcessSubProcessBorderCompartmentEditPart.VISUAL_ID).
								equals(String.valueOf(((Node)child).getType()))) {
							parent = (Node) child;
						}
					}
				}
				if (parent == null) {
					throw new IllegalArgumentException(
						BpmnDiagramMessages.bind(
								BpmnDiagramMessages.BPMNVisual2ProcessGenerator_cannotFindCompartment, 
								activity.eContainer()));
				}

				Node aNode = null;

				if (activity.getActivityType().getValue()
						!= ActivityType.SUB_PROCESS) {
					
					String semanticHints2 = BpmnVisualIDRegistry.getType(
							ActivityEditPart.VISUAL_ID);
					if (activity.getEventHandlerFor() != null) {
						semanticHints2 = BpmnVisualIDRegistry.getType(
								Activity2EditPart.VISUAL_ID);
					}
					aNode =
						ViewService.getInstance().createNode(
								new EObjectAdapter(activity),
								parent,
								semanticHints2, ViewUtil.APPEND,
								BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
					
				} else {
					String semanticHintsForSP = BpmnVisualIDRegistry.getType(
							SubProcessEditPart.VISUAL_ID);

					aNode =
						ViewService.getInstance().createNode(
								new EObjectAdapter(activity),
								parent,
								semanticHintsForSP, 
								ViewUtil.APPEND,
								BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				}
				if (aNode != null) {
					generateLayoutConstraint(aNode);
					setReturnedObject(aNode);
				} else {
					// this is an error
					statuses.add(new Status(IStatus.WARNING, 
							BpmnDiagramEditorPlugin.ID, IStatus.WARNING, 
							BpmnDiagramMessages.bind(
									BpmnDiagramMessages.BPMNVisual2ProcessGenerator_activity_cannot_create_view, 
									activity), 
							null));
				}
			}

		};
		
		_editingDomain.getCommandStack().execute(command);
		return (Node) command.getReturnedObject();
	}
	
	/**
	 * Generates a view for the text annotation.
	 * @param textAnnotation
	 * @param statuses 
	 * @return
	 */
	private Node generateTextAnnotationView(final TextAnnotation textAnnotation, 
			final List<IStatus> statuses) {
		final View parent = findParentView(textAnnotation);
		InternalRecordingCommand command = 
			new InternalRecordingCommand() {

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
							parent, 
							semanticHints, ViewUtil.APPEND,
							BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				if (node != null) {
					generateLayoutConstraint(node);
					setReturnedObject(node);
				} else {
					// this is an error.
					statuses.add(new Status(IStatus.WARNING, 
							BpmnDiagramEditorPlugin.ID, IStatus.WARNING, 
							BpmnDiagramMessages.bind(BpmnDiagramMessages.BPMNVisual2ProcessGenerator_text_ann_cannot_create_view,
									textAnnotation), 
							null));
				}
			}

		};
		_editingDomain.getCommandStack().execute(command);
		return (Node) command.getReturnedObject();
	}
	
	/**
	 * Finds the parent view for the semantic element - an Artifact.
	 * @param artifact
	 * @return
	 */
	private View findParentView(Artifact artifact) {
		View tempParent = null;
		if (artifact.getArtifactsContainer() instanceof BpmnDiagram) {
			tempParent = semantic2notationMap.get(artifact.getArtifactsContainer());
		} else {
			for (Object  child : semantic2notationMap.
					get(artifact.eContainer()).getChildren()) {
				if (child instanceof Node && 
						String.valueOf(PoolPoolCompartmentEditPart.VISUAL_ID).
						equals(String.valueOf(((Node)child).getType()))) {
					tempParent = (Node) child;
				} else if (child instanceof Node && 
						String.valueOf(SubProcessSubProcessBodyCompartmentEditPart.VISUAL_ID).
						equals(String.valueOf(((Node)child).getType()))) {
					tempParent = (Node) child;
				} 
			}
		}
		return tempParent;
	}
	/**
	 * Generates a view for the data object.
	 * @param dataObject
	 * @return
	 */
	private Node generateDataObjectView(final DataObject dataObject, 
			final List<IStatus> statuses) {
		final View parent = findParentView(dataObject);
		InternalRecordingCommand command = 
			new InternalRecordingCommand() {

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
							parent, 
							semanticHints, ViewUtil.APPEND,
							BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				if (node != null) {
					generateLayoutConstraint(node);
					setReturnedObject(node);
				} else {
					// this is an error.
					statuses.add(new Status(IStatus.WARNING, 
							BpmnDiagramEditorPlugin.ID, IStatus.WARNING, 
							BpmnDiagramMessages.bind(
									BpmnDiagramMessages.BPMNVisual2ProcessGenerator_data_object_cannot_create_view, dataObject),
							null));
				}
			}

		};
		_editingDomain.getCommandStack().execute(command);
		return (Node) command.getReturnedObject();
	}
	
	
	/**
	 * Generates a view for the group.
	 * @param group
	 * @return
	 */
	private Node generateGroupView(final Group group,
			final List<IStatus> statuses) {
		final View parent = findParentView(group);
		InternalRecordingCommand command = 
			new InternalRecordingCommand() {

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
							parent, 
							semanticHints, ViewUtil.APPEND,
							BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				if (node != null) {
					generateLayoutConstraint(node);
					setReturnedObject(node);
				} else {
//					 this is an error
					statuses.add(new Status(IStatus.WARNING, 
							BpmnDiagramEditorPlugin.ID, IStatus.WARNING, 
							BpmnDiagramMessages.bind(
									BpmnDiagramMessages.BPMNVisual2ProcessGenerator_group_cannot_create_view, group), 
							null));
				}
			}

		};
		_editingDomain.getCommandStack().execute(command);
		return (Node) command.getReturnedObject();
	}
	
	
	/**
	 * Generates a view for the given pool
	 * @param pool
	 * @return
	 */
	private Node generatePoolView(final Pool pool, final List<IStatus> statuses) {
		InternalRecordingCommand command = 
			new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				String semanticHints = BpmnVisualIDRegistry.getType(
						PoolEditPart.VISUAL_ID);
				Node poolNotationNode = 
					ViewService.getInstance().createNode(
							new EObjectAdapter(pool),
							getGraphicalModel(), 
							semanticHints, ViewUtil.APPEND,
							BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				if (poolNotationNode !=  null) {
				generateLayoutConstraint(poolNotationNode);
				setReturnedObject(poolNotationNode);
				} else {
					// this is an error
					statuses.add(new Status(IStatus.WARNING, 
							BpmnDiagramEditorPlugin.ID, IStatus.WARNING, 
							BpmnDiagramMessages.bind(BpmnDiagramMessages.BPMNVisual2ProcessGenerator_pool_cannot_create_view, pool),  
							null));
				}
			}

		};
		_editingDomain.getCommandStack().execute(command);
		return (Node) command.getReturnedObject();
	}
	
	/**
	 * To be used only in a command.
	 * Generates the layout constraint associated to a semantic element,
	 * then applies to the node.
	 * @param node
	 */
	private void generateLayoutConstraint(Node node) {
		if (positions.get(node.getElement()) != null) {
			Bounds bounds = NotationFactory.eINSTANCE.createBounds();
			Rectangle rect = positions.get(node.getElement());
			bounds.setHeight(rect.height);
			bounds.setWidth(rect.width);
			bounds.setX(rect.x);
			bounds.setY(rect.y);
			node.setLayoutConstraint(bounds);
		}
	}
	
	/**
	 * Generates the view for the lane
	 * @param lane
	 * @return a Node representing the lane
	 */
	private Node generateLaneView(final Lane lane, 
			final List<IStatus> statuses) {
		InternalRecordingCommand command = 
			new InternalRecordingCommand() {

			@Override
			protected void doExecute() {

				Node parent = null;
				for (Object  child : semantic2notationMap.
						get(lane.eContainer()).getChildren()) {
					if (child instanceof Node &&
				String.valueOf(PoolPoolCompartmentEditPart.VISUAL_ID).equals(
									String.valueOf(((Node)child).getType()))) {
						parent = (Node) child;
					} 
				}

				if (parent == null) {
					throw new IllegalArgumentException(
						BpmnDiagramMessages.bind(
								BpmnDiagramMessages.BPMNVisual2ProcessGenerator_cannotFindCompartment,  
								lane.eContainer()));
				}
				String semanticHints = BpmnVisualIDRegistry.getType(
						LaneEditPart.VISUAL_ID);
				Node laneNotationNode = 
					ViewService.getInstance().createNode(new EObjectAdapter(lane),
							parent, 
							semanticHints, ViewUtil.APPEND,
							BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				if (laneNotationNode != null) {
				generateLayoutConstraint(laneNotationNode);
				setReturnedObject(laneNotationNode);
				} else {
//					 this is an error
					statuses.add(new Status(IStatus.WARNING, 
							BpmnDiagramEditorPlugin.ID, IStatus.WARNING, 
							BpmnDiagramMessages.bind(BpmnDiagramMessages.BPMNVisual2ProcessGenerator_lane_cannot_create_view, lane),
							null));
				}
			}

		};
		
		_editingDomain.getCommandStack().execute(command);
		return (Node) command.getReturnedObject();
	}
	
	/**
	 * Generates the view for the sequence edge.
	 * Note that the source and the target of the view are expected to be found
	 * through the semantic2notationsMap, so it needs to be populated first.
	 * @param seqEdge
	 * @return
	 */
	private Edge generateSequenceEdgeView(final SequenceEdge seqEdge, 
			final List<IStatus> statuses) {
		InternalRecordingCommand command = 
			new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				
				String semanticHints = BpmnVisualIDRegistry.getType(
						SequenceEdgeEditPart.VISUAL_ID);
				Edge anEdge =
					(Edge) ViewService.getInstance().createEdge(
							new EObjectAdapter(seqEdge),
							getGraphicalModel(), 
							semanticHints, ViewUtil.APPEND,
							BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				if (anEdge == null) {
					statuses.add(new Status(IStatus.WARNING, 
							BpmnDiagramEditorPlugin.ID, IStatus.WARNING, 
							BpmnDiagramMessages.bind(BpmnDiagramMessages.BPMNVisual2ProcessGenerator_sequence_edge_cannot_create_view, seqEdge), 
							null));
				} else {
					anEdge.setSource(semantic2notationMap.get(seqEdge.getSource()));
					anEdge.setTarget(semantic2notationMap.get(seqEdge.getTarget()));
					setReturnedObject(anEdge);
				}
			}};
		_editingDomain.getCommandStack().execute(command);
			
		return (Edge) command.getReturnedObject();
	}
	
	/**
	 * Generates a view for the messaging edge
	 * Note that the source and the target of the view are expected to be found
	 * through the semantic2notationsMap, so it needs to be populated first.
	 * @param mesEdge
	 * @return
	 */
	private Edge generateMessagingEdgeView(final MessagingEdge mesEdge, 
			final List<IStatus> statuses) {
		InternalRecordingCommand command = 
			new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				
				String semanticHints = BpmnVisualIDRegistry.getType(
						MessagingEdgeEditPart.VISUAL_ID);
				Edge anEdge =
					(Edge) ViewService.getInstance().createEdge(
							new EObjectAdapter(mesEdge),
							getGraphicalModel(), 
							semanticHints, ViewUtil.APPEND,
						BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				if (anEdge == null) {
					statuses.add(new Status(IStatus.WARNING, 
							BpmnDiagramEditorPlugin.ID, IStatus.WARNING, 
							BpmnDiagramMessages.bind(BpmnDiagramMessages.BPMNVisual2ProcessGenerator_messaging_edge_cannot_create_view, mesEdge),
							null));
				} else {
					anEdge.setSource(semantic2notationMap.get(mesEdge.getSource()));
					anEdge.setTarget(semantic2notationMap.get(mesEdge.getTarget()));
					setReturnedObject(anEdge);
				}
			}};
		_editingDomain.getCommandStack().execute(command);

		return (Edge) command.getReturnedObject();
	}

	/**
	 * Generates a view for the association
	 * Note that the source and the target of the view are expected to be found
	 * through the semantic2notationsMap, so it needs to be populated first.
	 * @param assocation
	 * @return the generated view
	 */
	private Edge generateAssociationView(final Association association, 
			final List<IStatus> statuses) {
		InternalRecordingCommand command = 
			new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				
				String semanticHints = BpmnVisualIDRegistry.getType(
						AssociationEditPart.VISUAL_ID);
				Edge anEdge =
					(Edge) ViewService.getInstance().createEdge(
							new EObjectAdapter(association),
							getGraphicalModel(), 
							semanticHints, ViewUtil.APPEND,
							BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				if (anEdge == null) {
					statuses.add(new Status(IStatus.WARNING, 
							BpmnDiagramEditorPlugin.ID, IStatus.WARNING, 
							BpmnDiagramMessages.bind(BpmnDiagramMessages.BPMNVisual2ProcessGenerator_association_cannot_create_view, association),  
							null));
				} else {
					anEdge.setSource(semantic2notationMap.get(association.getSource()));
					anEdge.setTarget(semantic2notationMap.get(association.getTarget()));
					setReturnedObject(anEdge);
				}
			}};
		_editingDomain.getCommandStack().execute(command);

		return (Edge) command.getReturnedObject();
	}
	
	
	/**
	 * @return the semantic2notationMap
	 */
	public Map<EObject, View> getSemantic2notationMap() {
		return semantic2notationMap;
	}
	
	/**
	 * Sets the feature of the given element to the value.
	 * @param elt
	 * @param feature
	 * @param value
	 */
	public void setEObjectFeature(final EObject elt, 
			final EStructuralFeature feature, final Object value) {
		InternalRecordingCommand command = new InternalRecordingCommand() {

			@Override
			protected void doExecute() {
				elt.eSet(feature, value);
			}
			
		};
		_editingDomain.getCommandStack().execute(command);
	}
	
	/**
     * This method must be called after the views are generated, or it won't do a thing.
     * Collapses a subprocess.
     */
    public void setSubProcessCollapsed(final SubProcess sp) {
        InternalRecordingCommand command = new InternalRecordingCommand() {

            @Override
            protected void doExecute() {
                View v = getSemantic2notationMap().get(sp);
                if (v != null) {
                    TreeIterator<EObject> iterator = v.eAllContents();
                    while (iterator.hasNext()) {
                        EObject child = iterator.next();
                        if (child instanceof View) {
                            for (Object style : ((View) child).getStyles()) {
                                if (style instanceof DrawerStyle) {
                                    ((DrawerStyle) style).setCollapsed(true);
                                    //return;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        };
        _editingDomain.getCommandStack().execute(command);
    }
}
