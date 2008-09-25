/*
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 */
package org.eclipse.stp.bpmn.diagram.edit.policies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.ValueListIterator;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Artifact;
import org.eclipse.stp.bpmn.Association;
import org.eclipse.stp.bpmn.AssociationTarget;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.BpmnFactory;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.Identifiable;
import org.eclipse.stp.bpmn.MessageVertex;
import org.eclipse.stp.bpmn.MessagingEdge;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.SubProcess;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.commands.CreateRelationshipCommandEx;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.edit.parts.SubProcessEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.diagram.providers.BpmnElementTypes;

/**
 * @generated
 */
public class ActivityItemSemanticEditPolicy extends
        BpmnBaseItemSemanticEditPolicy {

    /**
     * The constant for all requests regarding the change of activity type
     */
    public static final String ACTIVITY_TYPE_CHANGE_REQUEST = "activity_type_change_request"; //$NON-NLS-1$
    /**
     * The constant for the activity type stored in the extended data map of
     * the request.
     */
    public static final Object ACTIVITY_TYPE_EXTENDED_DATA = "activity_type_extended_data"; //$NON-NLS-1$
    /**
     * @generated
     */
    protected Command getDestroyElementCommand(DestroyElementRequest req) {
        return getMSLWrapper(new DestroyElementCommand(req) {

            private List<String> _msgIds;
            
            @Override
            protected CommandResult doExecuteWithResult(
                    IProgressMonitor monitor, IAdaptable info)
                    throws ExecutionException {
                Activity act = (Activity)getElementToDestroy();
                Map<MessagingEdge, MessageVertex> otherMsgVerices =
                    new HashMap<MessagingEdge, MessageVertex>(act.getOrderedMessages().size());
                _msgIds = new ArrayList<String>(otherMsgVerices.size());
                ValueListIterator<Object> msgsIt = act.getOrderedMessages().valueListIterator();
                while (msgsIt.hasNext()) {
                    MessagingEdge m = (MessagingEdge)msgsIt.next();
                    if (m.getTarget() != act && m.getTarget() != null) {
                        otherMsgVerices.put(m, m.getTarget());
                    } else if (m.getSource() != act && m.getSource() != null) {
                        otherMsgVerices.put(m, m.getSource());
                    }
                    _msgIds.add(m.getID());
                }
                CommandResult res = super.doExecuteWithResult(monitor, info);
                //same issues than inside the destruction of the messaging edge:
                //when it destroys the messaging edge it does not remove 
                //the reference to the messaging edge
                //on the activity on the other side that is not removed.
                for (Entry<MessagingEdge, MessageVertex> entry : otherMsgVerices.entrySet()) {
                    msgsIt = entry.getValue().getOrderedMessages().valueListIterator();
                    while (msgsIt.hasNext()) {
                        MessagingEdge m = (MessagingEdge)msgsIt.next();
                        if (entry.getKey() == m) {
                            //remove it!
                            //don't send notifications while fixing a corrupted things
                            msgsIt.remove();
                            break;
                        }
                    }
                }
                return res;
            }

            protected EObject getElementToDestroy() {
                View view = (View) getHost().getModel();
                EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
                if (annotation != null) {
                    return view;
                }
                return super.getElementToDestroy();
            }

            /**
             * It seems that we do get another corruption of the featuremap
             * with duplicate entries. We also take care of it here
             * by manually setting the list of [valid] Featuremap entries
             * for the ordered messages.
             */
            @Override
            protected IStatus doUndo(IProgressMonitor monitor, IAdaptable info)
                    throws ExecutionException {
                IStatus s = super.doUndo(monitor, info);
                Activity act = (Activity)getElementToDestroy();
                FeatureMap msgs = act.getOrderedMessages();
                ListIterator<FeatureMap.Entry> it = msgs.listIterator();
                Map<String,EntryAndIndex> entries = new HashMap<String, EntryAndIndex>();
                int index = 0;
                List<FeatureMap.Entry> newData = new ArrayList<FeatureMap.Entry>(_msgIds.size());
                boolean isCorrupted = false;
                while (it.hasNext()) {
                    FeatureMap.Entry entry = it.next();
                    MessagingEdge m = (MessagingEdge)entry.getValue();
                    if (entries.containsKey(m.getID())) {
                        isCorrupted = true;
                    } else {
                        newData.add(entry);
                        entries.put(m.getID(), new EntryAndIndex(entry, index));
                    }
                }
                if (isCorrupted) {
                    FeatureMap.Entry[] arr = new FeatureMap.Entry[_msgIds.size()];
                    ((BasicEList)msgs).setData(newData.size(), newData.toArray(arr));
                }
                return s;
            }
            
            

        });
    }
    
    private static class EntryAndIndex {
        FeatureMap.Entry entry;
        int index;
        EntryAndIndex(FeatureMap.Entry entry, int index) {
            this.entry = entry;
            this.index = index;
        }
    }

    /**
     * @generated  not
     * removed the start for the association.
     */
    protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
        if (BpmnElementTypes.SequenceEdge_3001.getId().equals(req.getElementType().getId())) {
            return req.getTarget() == null ? getCreateStartOutgoingSequenceEdge3001Command(req)
                    : getCreateCompleteIncomingSequenceEdge3001Command(req);
        }
        if (BpmnElementTypes.MessagingEdge_3002.getId().equals(req.getElementType().getId())) {
            return req.getTarget() == null ? getCreateStartOutgoingMessagingEdge3002Command(req)
                    : getCreateCompleteIncomingMessagingEdge3002Command(req);
        }
        if (BpmnElementTypes.Association_3003.getId().equals(req.getElementType().getId())) {
            return req.getTarget() == null ? null
                    : getCreateCompleteIncomingAssociation3003Command(req);
        }
        return super.getCreateRelationshipCommand(req);
    }

    /**
     * @generated
     */
    protected Command getCreateStartOutgoingSequenceEdge3001Command(
            CreateRelationshipRequest req) {
        return new Command() {
        };
    }

    /**
     * @generated not
     * changed the container if it is the source (little bug)
     */
    protected Command getCreateCompleteIncomingSequenceEdge3001Command(
            CreateRelationshipRequest req) {
        if (!(req.getSource() instanceof Vertex)) {
            return UnexecutableCommand.INSTANCE;
        }
        Graph element = (Graph) getRelationshipContainer(req.getSource(),
                BpmnPackage.eINSTANCE.getGraph(), req.getElementType());
        if (element == null) {
            return UnexecutableCommand.INSTANCE;
        }
        if (element.equals(req.getSource())) {
        	element = ((Vertex) element).getGraph();
        }
        if (req.getContainmentFeature() == null) {
            req.setContainmentFeature(BpmnPackage.eINSTANCE
                    .getGraph_SequenceEdges());
        }
        final Graph finalElement = element;
        return getMSLWrapper(new CreateIncomingSequenceEdge3001Command(req) {

            /**
             * @generated not
             */
            protected EObject getElementToEdit() {
                return finalElement;
            }
        });
    }

    /**
     * @generated
     */
    protected static class CreateIncomingSequenceEdge3001Command extends
            CreateRelationshipCommandEx {

        /**
         * @generated
         */
        public CreateIncomingSequenceEdge3001Command(
                CreateRelationshipRequest req) {
            super(req);
        }

        /**
         * @generated
         */
        protected EClass getEClassToEdit() {
            return BpmnPackage.eINSTANCE.getGraph();
        };

        /**
         * @generated
         */
        protected void setElementToEdit(EObject element) {
            throw new UnsupportedOperationException();
        }

        /**
         * @generated
         */
        protected EObject doDefaultElementCreation() {
            SequenceEdge newElement = (SequenceEdge) super
                    .doDefaultElementCreation();
            if (newElement != null) {
                newElement.setTarget((Vertex) getTarget());
                newElement.setSource((Vertex) getSource());
            }
            return newElement;
        }
    
        /**
         * Contains Sequence Flow Rules
         * @generated NOT
         * @author BIlchyshyn
         */
        @Override
        public boolean canExecute() {
            /*
             * Sequence Flow Rules
             */
        	if (getSource() == null|| getTarget() == null) {
        		return true;
        		// in this case the sequence flow is created as the activity is created.
        	}
            
            if (!(getSource() instanceof Activity && getTarget() instanceof Activity)) {
                //it does not look right!
                return false;
            }
            if (getSource().equals(getTarget())) {
                return false;
            }
            
            Activity source = (Activity) getSource();
            Activity target = (Activity) getTarget();

            //only one sequence edge between 2 activities is allowed
            for (Object inEdge : target.getIncomingEdges()) {
                SequenceEdge element = (SequenceEdge) inEdge;
                if (element.getSource().equals(source)) {
                    return false;
                }
            }
            
            if (source.getGraph() == null && 
            		source.getEventHandlerFor() != null) {
            	// the source is located on the border of a subprocess.
            	if (source.getEventHandlerFor().equals(getTarget())) {
            		// the target is the subprocess the source is located on.
            		return false;
            	}
            	if (!source.getEventHandlerFor().getGraph().equals(target.getGraph())) {
            		return false;
            	}
            	return true;
            }
            if (!source.getGraph().equals(target.getGraph())) {
            	return false;
            }
            
            if (ActivityType.VALUES_EVENTS_START.contains(target.getActivityType())) {
            	return false;
            }
            return super.canExecute();
        }

    }
    /**
     * @generated
     */
    protected Command getCreateStartOutgoingMessagingEdge3002Command(
            CreateRelationshipRequest req) {
        return new Command() {
        };
    }

    /**
     * @generated
     */
    protected Command getCreateCompleteIncomingMessagingEdge3002Command(
            CreateRelationshipRequest req) {
        final BpmnDiagram element = (BpmnDiagram) getRelationshipContainer(req
                .getSource(), BpmnPackage.eINSTANCE.getBpmnDiagram(), req
                .getElementType());
        if (element == null) {
            return UnexecutableCommand.INSTANCE;
        }
        if (req.getContainmentFeature() == null) {
            req.setContainmentFeature(BpmnPackage.eINSTANCE
                    .getBpmnDiagram_Messages());
        }
        return getMSLWrapper(new CreateIncomingMessagingEdge3002Command(req) {

            /**
             * @generated
             */
            protected EObject getElementToEdit() {
                return element;
            }
        });
    }

    /**
     * Almost generated: the private is now protected
     * @generated
     */
    protected static class CreateIncomingMessagingEdge3002Command extends
            CreateRelationshipCommandEx {

        /**
         * @generated
         */
        public CreateIncomingMessagingEdge3002Command(
                CreateRelationshipRequest req) {
            super(req);
        }

        /**
         * @generated
         */
        protected EClass getEClassToEdit() {
            return BpmnPackage.eINSTANCE.getBpmnDiagram();
        };

        /**
         * @generated
         */
        protected void setElementToEdit(EObject element) {
            throw new UnsupportedOperationException();
        }

        /**
         * @generated
         */
        protected EObject doDefaultElementCreation() {
            MessagingEdge newElement = (MessagingEdge) super
                    .doDefaultElementCreation();
            if (newElement != null) {
                newElement.setTarget((MessageVertex) getTarget());
                newElement.setSource((MessageVertex) getSource());
            }
            return newElement;
        }
        
        /**
         * Contains Message Flow Rules
         * @generated not
         */
        @Override
        public boolean canExecute() {
            
            if (getSource() instanceof Pool || getTarget() instanceof Pool) {
                return true;
            }
        	if (!(getSource() instanceof Activity && getTarget() instanceof Activity)) {
        		return false;
        		// the message is created at the same time as the activity is created.
        	}
            Activity source = (Activity) getSource();
            Activity target = (Activity) getTarget();
                    
            /*
             * Message Flow Rules
             */
            if (source.equals(target)) {
                return false;
            }

            EList tInMessages = target == null ? ECollections.EMPTY_ELIST :
                    target.getIncomingMessages();
            EList tOutMessages = target == null ? ECollections.EMPTY_ELIST :
                    target.getOutgoingMessages();
            EList sInMessages = source == null ? ECollections.EMPTY_ELIST :
                    source.getIncomingMessages();
            EList sOutMessages = source == null ? ECollections.EMPTY_ELIST :
                    source.getOutgoingMessages();

            /*
             * only one incoming and one outgoing message connection between two
             * activities are allowed.
             */
            for (Iterator iter = tInMessages.iterator(); iter.hasNext();) {
                MessagingEdge element = (MessagingEdge) iter.next();
                // better be safe here
                if (element.getSource() != null && 
                		element.getSource().equals(source)) {
                    return false;
                }
            }

            // source pool
            Identifiable sContainer = getSource() instanceof Pool ?
                    (Identifiable) getSource() :
                (Identifiable) source.eContainer();
            while (!(sContainer instanceof Pool)) {
                sContainer = (Identifiable) sContainer.eContainer();
            }

            // target pool
            Identifiable tContainer = getTarget() instanceof Pool ?
                    (Identifiable) getTarget() :
                (Identifiable) target.eContainer();
            while (!(tContainer instanceof Pool)) {
                tContainer = (Identifiable) tContainer.eContainer();
            }

            if (sContainer.equals(tContainer)) {
            	return false;
            }
            /*
             * if a shape is already carrying one or more messaging edges, the
             * new messaging edge must not link to an activity that belongs to a
             * third pool.
             */
            for (Iterator iter = tOutMessages.iterator(); iter.hasNext();) {
                MessagingEdge element = (MessagingEdge) iter.next();
                Activity task = (Activity) element.getTarget();
                Identifiable container = (Identifiable) task.eContainer();
                while (!(container instanceof Pool)) {
                    container = (Identifiable) container.eContainer();
                }
                if (!container.equals(sContainer)) {
                    return false;
                }
            }

            for (Iterator iter = tInMessages.iterator(); iter.hasNext();) {
                MessagingEdge element = (MessagingEdge) iter.next();
                Activity task = (Activity) element.getSource();
                Identifiable container = (Identifiable) task.eContainer();
                while (!(container instanceof Pool)) {
                    container = (Identifiable) container.eContainer();
                }
                if (!container.equals(sContainer)) {
                    return false;
                }
            }

            for (Iterator iter = sOutMessages.iterator(); iter.hasNext();) {
                MessagingEdge element = (MessagingEdge) iter.next();
                Activity task = (Activity) element.getTarget();
                Identifiable container = (Identifiable) task.eContainer();
                while (!(container instanceof Pool)) {
                    container = (Identifiable) container.eContainer();
                }
                if (!container.equals(tContainer)) {
                    return false;
                }
            }

            for (Iterator iter = sInMessages.iterator(); iter.hasNext();) {
                MessagingEdge element = (MessagingEdge) iter.next();
                Activity task = (Activity) element.getSource();
                Identifiable container = (Identifiable) task.eContainer();
                while (!(container instanceof Pool)) {
                    container = (Identifiable) container.eContainer();
                }
                if (!container.equals(tContainer)) {
                    return false;
                }
            }
            
            boolean isBpmn11 = BpmnDiagramEditorPlugin.getInstance().getPreferenceStore().getBoolean(
                    BpmnDiagramPreferenceInitializer.PREF_BPMN1_1_STYLE);
            if (target != null) {
                //connection rules for events and gateways
                switch (target.getActivityType().getValue()) {
                case ActivityType.GATEWAY_DATA_BASED_INCLUSIVE:
                case ActivityType.GATEWAY_EVENT_BASED_EXCLUSIVE:
                case ActivityType.GATEWAY_PARALLEL:
                case ActivityType.GATEWAY_COMPLEX:
                case ActivityType.SUB_PROCESS:
                    return false;
                    
                case ActivityType.EVENT_START_EMPTY:
                case ActivityType.EVENT_START_LINK:
                case ActivityType.EVENT_START_MULTIPLE:
                case ActivityType.EVENT_START_RULE:
                case ActivityType.EVENT_START_TIMER:
                case ActivityType.EVENT_START_MESSAGE:
                case ActivityType.EVENT_START_SIGNAL:
                    //all the starts can receive a message.
                    
                case ActivityType.EVENT_INTERMEDIATE_CANCEL:
                case ActivityType.EVENT_INTERMEDIATE_COMPENSATION:
                case ActivityType.EVENT_INTERMEDIATE_EMPTY:
                case ActivityType.EVENT_INTERMEDIATE_ERROR:
                case ActivityType.EVENT_INTERMEDIATE_LINK:
                case ActivityType.EVENT_INTERMEDIATE_MULTIPLE:
                case ActivityType.EVENT_INTERMEDIATE_RULE:
                case ActivityType.EVENT_INTERMEDIATE_TIMER:
                case ActivityType.EVENT_INTERMEDIATE_MESSAGE:
                case ActivityType.EVENT_INTERMEDIATE_SIGNAL:
//                  all the intermediates can receive a message.
                    
                case ActivityType.TASK:
                    break;
                    
                //we are being a little lax with the bpmn spec:
                //if the message event shape receive a request, we
                //allow the message response to come from the same
                //shape.
                case ActivityType.EVENT_END_MESSAGE:
                case ActivityType.EVENT_END_MULTIPLE:
                    if (isBpmn11) {
                        break;
                    }
                    if (!target.getOrderedMessages().isEmpty()
                         && (target.getOutgoingMessages().contains(
                                 target.getOrderedMessages().get(0).getValue()))) {
                        break;
                    }
                case ActivityType.EVENT_END_COMPENSATION:
                case ActivityType.EVENT_END_EMPTY:
                case ActivityType.EVENT_END_ERROR:
                case ActivityType.EVENT_END_TERMINATE:
                case ActivityType.EVENT_END_CANCEL:
                case ActivityType.EVENT_END_LINK:
                case ActivityType.EVENT_END_SIGNAL:
                default:
                    return false;
                }
            }
            if (source != null) {
                //connection rules for events and gateways
                switch (source.getActivityType().getValue()) {
                case ActivityType.GATEWAY_DATA_BASED_INCLUSIVE:
                case ActivityType.GATEWAY_EVENT_BASED_EXCLUSIVE:
                case ActivityType.GATEWAY_PARALLEL:
                case ActivityType.GATEWAY_COMPLEX:
                case ActivityType.SUB_PROCESS:
                    return false;
                case ActivityType.EVENT_END_EMPTY:
                case ActivityType.EVENT_END_COMPENSATION:
                case ActivityType.EVENT_END_ERROR:
                case ActivityType.EVENT_END_TERMINATE:
                case ActivityType.EVENT_END_CANCEL:
                case ActivityType.EVENT_END_LINK:
                case ActivityType.EVENT_END_MULTIPLE:
                case ActivityType.EVENT_END_MESSAGE:
                case ActivityType.EVENT_END_SIGNAL:
                    //all the end can send a message
                    
                case ActivityType.TASK:
                    break;
                    
                //we are being a little lax with the bpmn spec:
                //if the message event shape receive a request, we
                //allow the message response to answer from the same
                //shape.
                case ActivityType.EVENT_INTERMEDIATE_MESSAGE:
                    if (source.getEventHandlerFor() != null) {
                        return false;//no outgoing message on the event handler.
                    }
                    
                case ActivityType.EVENT_START_MESSAGE:
                case ActivityType.EVENT_INTERMEDIATE_MULTIPLE:
                case ActivityType.EVENT_START_MULTIPLE:
                    
                    if (isBpmn11) {
                        break;
                    }
                    if (!source.getOrderedMessages().isEmpty()) {
                        boolean incomingFirst = source.getIncomingMessages().contains(
                                source.getOrderedMessages().get(0).getValue());
                        FeatureMap.Entry fentry = (FeatureMap.Entry) source.
                                            getOrderedMessages().get(0);
                        MessagingEdge firstMsgOfSource = (MessagingEdge) fentry.getValue();
                        if (incomingFirst &&
                                firstMsgOfSource.getSource() == target) {
                        // a little bent to the spec
                        // let's let the start events be able to reply
                            break;
                        }
                    }
                case ActivityType.EVENT_START_EMPTY:
                case ActivityType.EVENT_START_LINK:
                case ActivityType.EVENT_START_RULE:
                case ActivityType.EVENT_START_TIMER:
                case ActivityType.EVENT_START_SIGNAL:
                case ActivityType.EVENT_INTERMEDIATE_CANCEL:
                case ActivityType.EVENT_INTERMEDIATE_COMPENSATION:
                case ActivityType.EVENT_INTERMEDIATE_EMPTY:
                case ActivityType.EVENT_INTERMEDIATE_ERROR:
                case ActivityType.EVENT_INTERMEDIATE_LINK:
                case ActivityType.EVENT_INTERMEDIATE_RULE:
                case ActivityType.EVENT_INTERMEDIATE_TIMER:
                case ActivityType.EVENT_INTERMEDIATE_SIGNAL:
                    //the start and intermediate cannot send a message.
                default:
                    return false;
                }

            }
            return super.canExecute();
        }

        
    }

    /**
     * @generated  not
     */
    protected Command getCreateCompleteIncomingAssociation3003Command(
            CreateRelationshipRequest req) {
        if (!(req.getSource() instanceof Artifact)) {
            return UnexecutableCommand.INSTANCE;
        }
        final Artifact element = (Artifact) getRelationshipContainer(req
                .getSource(), BpmnPackage.eINSTANCE.getArtifact(), req
                .getElementType());
        if (element == null) {
            return UnexecutableCommand.INSTANCE;
        }
        if (req.getContainmentFeature() == null) {
            req.setContainmentFeature(BpmnPackage.eINSTANCE
                    .getArtifact_Associations());
        }
        for (Object tempA : element.getAssociations()) {
        	if (tempA instanceof Association) {
        		Association association = (Association) tempA;
        		if (((IGraphicalEditPart) getHost()).resolveSemanticElement().
        				equals(association.getTarget())) {
        			return UnexecutableCommand.INSTANCE;
        		}
        	}
        }
        return getMSLWrapper(new CreateIncomingAssociation3003Command(req) {

            /**
             * @generated
             */
            protected EObject getElementToEdit() {
                return element;
            }
        });
    }

    /**
     * @generated NOT
     */
    private static class CreateIncomingAssociation3003Command extends
            CreateRelationshipCommandEx {

        /**
         * @generated
         */
        public CreateIncomingAssociation3003Command(
                CreateRelationshipRequest req) {
            super(req);
        }

        /**
         * @generated
         */
        protected EClass getEClassToEdit() {
            return BpmnPackage.eINSTANCE.getArtifact();
        };

        /**
         * @generated
         */
        protected void setElementToEdit(EObject element) {
            throw new UnsupportedOperationException();
        }

        /**
         * @generated
         */
        protected EObject doDefaultElementCreation() {
            Association newElement = (Association) super
                    .doDefaultElementCreation();
            if (newElement != null) {
                newElement.setTarget((AssociationTarget) getTarget());
                newElement.setSource((Artifact) getSource());
            }
            return newElement;
        }
    }
    
    /**
     * @generated not
     * overridden for activity type command
     */
    @Override
    public boolean understandsRequest(Request request) {
        return super.understandsRequest(request) 
            || (ACTIVITY_TYPE_CHANGE_REQUEST.equals(request.getType()));
    }
    
    /**
     * @generated not
     * overridden for activity type command
     */
    @Override
    public Command getCommand(Request request) {
        if (ACTIVITY_TYPE_CHANGE_REQUEST.equals(request.getType())) {
            return getCommandForActivityTypeChange(request);
        }
        return super.getCommand(request);
    }

    /**
     * 
     * @param request
     * @return a command that changes the activity type of an activity.
     */
    protected Command getCommandForActivityTypeChange(Request request) {
        ActivityType type = (ActivityType) request.getExtendedData().get(ACTIVITY_TYPE_EXTENDED_DATA);
        return getMSLWrapper(new ActivityTypeChangeCommand(type));
    }
    
    /**
     * Command to change the activity type of an activity.
     * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
     * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
     */
    protected class ActivityTypeChangeCommand extends AbstractTransactionalCommand {
        
        private ActivityType _type;
        
        public ActivityTypeChangeCommand(ActivityType type) {
            super((TransactionalEditingDomain) AdapterFactoryEditingDomain.
                    getEditingDomainFor(((IGraphicalEditPart) getHost()).resolveSemanticElement()),
                    BpmnDiagramMessages.ChangeActivityTypeAction_settingActivityTypeCommand,
                    getWorkspaceFiles(((IGraphicalEditPart) getHost()).resolveSemanticElement()));
            _type = type;
        }
        
        @Override
        protected CommandResult doExecuteWithResult(
                IProgressMonitor monitor, IAdaptable info)
        throws ExecutionException {
            if (ActivityType.SUB_PROCESS_LITERAL.equals(_type)) {
                Activity act = (Activity) ((IGraphicalEditPart) getHost()).resolveSemanticElement();
                Node actnode = (Node) ((IGraphicalEditPart) getHost()).getNotationView();
                SubProcess sp = BpmnFactory.eINSTANCE.createSubProcess();
                sp.setActivityType(ActivityType.SUB_PROCESS_LITERAL);
                sp.setName(act.getName());
                sp.setNcname(act.getNcname());
                sp.setDocumentation(act.getDocumentation());
                sp.setGraph(act.getGraph());
                sp.setID(act.getID());
//              sp.setLane(act.getLane());
                sp.setLooping(act.isLooping());
                sp.getIncomingEdges().addAll(act.getIncomingEdges());
                sp.getOutgoingEdges().addAll(act.getOutgoingEdges());
                Node node = ViewService.createNode((View) actnode.eContainer(), 
                        sp,BpmnVisualIDRegistry.getType(SubProcessEditPart.VISUAL_ID) , 
                        BpmnDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
                node.setLayoutConstraint(actnode.getLayoutConstraint());
                node.getTargetEdges().addAll(actnode.getTargetEdges());
                node.getSourceEdges().addAll(actnode.getSourceEdges());
                Command co = getHost().getCommand(
                        new EditCommandRequestWrapper(
                                new DestroyElementRequest(act, false)));

                co.execute();
                Command autosize = 
                    getHost().getCommand(new Request(RequestConstants.REQ_AUTOSIZE));
                autosize.execute();
                return CommandResult.newOKCommandResult();
            } else {
                Activity act = (Activity) ((IGraphicalEditPart) getHost()).resolveSemanticElement();
                if (getActivityType().getValue() != 
                    ActivityType.TASK && 
                    (getActivityType().getValue() != 
                        ActivityType.SUB_PROCESS)) {
                    act.setLooping(false);
                }
                act.setActivityType(getActivityType());
                getHost().deactivate();
                getHost().activate();
                Command autosize = 
                    getHost().getCommand(new Request(RequestConstants.REQ_AUTOSIZE));
                autosize.execute();
                return CommandResult.newOKCommandResult();
            }
        }
        
        private ActivityType getActivityType() {
            return _type;
        }
        
        @Override
        public boolean canExecute() {
            if (!super.canExecute()) {
                return false;
            }
            boolean forbidEnablement = false;
            Activity act = (Activity) ((IGraphicalEditPart) getHost()).resolveSemanticElement();
            if (act instanceof SubProcess) {// changing a subprocess into an 
                // activity is too complicated for now.
                forbidEnablement = true;
            }
            // if the activity has messages, then don't allow it
            // to be transformed into a gateway
            // to avoid having something illegal here.
            if (!act.getOrderedMessages().isEmpty() &&
                    ActivityType.VALUES_GATEWAYS.contains(_type)) {
                forbidEnablement = true;
            }
            // empty events are not authorized to be associated
            // with messages
            if (!act.getOrderedMessages().isEmpty() &&
                    ActivityType.EVENT_END_EMPTY_LITERAL.equals(_type)) {
                forbidEnablement = true;
            }
            
            if (!act.getOrderedMessages().isEmpty() &&
                    ActivityType.EVENT_INTERMEDIATE_EMPTY_LITERAL.equals(_type)) {
                forbidEnablement = true;
            }
            
            if (!act.getOrderedMessages().isEmpty() &&
                    ActivityType.EVENT_START_EMPTY_LITERAL.equals(_type)) {
                forbidEnablement = true;
            }
            
            if (forbidEnablement) {
                return false;
            }
            if (act.getActivityType().getValue() 
                    == getActivityType().getValue()) {
                forbidEnablement = true;
            }
            
            // only accepting to change an activity into a subprocess if
            // the activity doesn't have messages and is of type TASK.
            if (_type.getValue() == ActivityType.SUB_PROCESS && 
                    (act.getActivityType().getValue() != ActivityType.TASK || 
                            (!act.getOrderedMessages().isEmpty()))) {
                forbidEnablement = true;
            }
            return !forbidEnablement;
        }
    }
}
