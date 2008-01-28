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
package org.eclipse.stp.bpmn.diagram.providers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.emf.core.util.IProxyEObject;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.commands.IElementTypeEx;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.dnd.ISecondarySemanticHintProcessor;
import org.eclipse.swt.graphics.Image;

/**
 * @generated
 */
public class BpmnElementTypes {

    /**
     * @generated
     */
    private BpmnElementTypes() {
    }

    /**
     * @generated NOT added the typing
     */
    private static Map<String, EClass> elements;

    /**
     * @generated
     */
    private static ImageRegistry imageRegistry;

    /**
     * @generated
     */
    private static ImageRegistry getImageRegistry() {
        if (imageRegistry == null) {
            imageRegistry = new ImageRegistry();
        }
        return imageRegistry;
    }

    /**
     * @generated
     */
    private static String getImageRegistryKey(ENamedElement element) {
        return element.getName();
    }

    /**
     * @generated
     */
    private static ImageDescriptor getProvidedImageDescriptor(
            ENamedElement element) {
        if (element instanceof EStructuralFeature) {
            element = ((EStructuralFeature) element).getEContainingClass();
        }
        if (element instanceof EClass) {
            EClass eClass = (EClass) element;
            if (!eClass.isAbstract()) {
                return BpmnDiagramEditorPlugin.getInstance()
                        .getItemImageDescriptor(
                                eClass.getEPackage().getEFactoryInstance()
                                        .create(eClass));
            }
        }
        // TODO : support structural features
        return null;
    }

    /**
     * @generated
     */
    public static ImageDescriptor getImageDescriptor(ENamedElement element) {
        String key = getImageRegistryKey(element);
        ImageDescriptor imageDescriptor = getImageRegistry().getDescriptor(key);
        if (imageDescriptor == null) {
            imageDescriptor = getProvidedImageDescriptor(element);
            if (imageDescriptor == null) {
                imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
            }
            getImageRegistry().put(key, imageDescriptor);
        }
        return imageDescriptor;
    }

    /**
     * @generated
     */
    public static Image getImage(ENamedElement element) {
        String key = getImageRegistryKey(element);
        Image image = getImageRegistry().get(key);
        if (image == null) {
            ImageDescriptor imageDescriptor = getProvidedImageDescriptor(element);
            if (imageDescriptor == null) {
                imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
            }
            getImageRegistry().put(key, imageDescriptor);
            image = getImageRegistry().get(key);
        }
        return image;
    }

    /**
     * @generated NOT
     */
    private static String getImageRegistryKey(ENamedElement element,
            String activityType) {
        return element.getName() + "." + activityType; //$NON-NLS-1$
    }

    /**
     * Pass the acticity type to get the right icon
     * 
     * @generated NOT
     */
    private static ImageDescriptor getProvidedImageDescriptor(
            ENamedElement element, String activityString) {
        if (element instanceof EStructuralFeature) {
            element = ((EStructuralFeature) element).getEContainingClass();
        }
        if (element instanceof EClass) {
            
            //check the extension of the editor.
            for (ISecondarySemanticHintProcessor sec : BpmnDiagramEditorPlugin.getInstance()
                        .getSecondarySemanticHintParsers()) {
                ImageDescriptor imgDesc = sec.getImageDescriptorForTool(element, activityString);
                if (imgDesc != null) {
                    return imgDesc;
                }
            }

            
            EClass eClass = (EClass) element;
            if (!eClass.isAbstract()) {

                EObject eo = eClass.getEPackage().getEFactoryInstance().create(
                        eClass);
                if (eo instanceof Activity) {
                    if (activityString != null) {
                        ActivityType at = ActivityType.get(activityString);
                        if (at != null) {
                            ((Activity) eo).setActivityType(at);
                        }
                    }
                }
                return BpmnDiagramEditorPlugin.getInstance()
                        .getItemImageDescriptor(eo);
            }
        }
        // TODO : support structural features
        return null;
    }

    /**
     * @generated NOT
     */
    public static ImageDescriptor getImageDescriptor(ENamedElement element,
            String activityType) {
        //check the extension of the editor.
        for (ISecondarySemanticHintProcessor sec : BpmnDiagramEditorPlugin.getInstance()
                    .getSecondarySemanticHintParsers()) {
            ImageDescriptor imgDesc = sec.getImageDescriptorForTool(element, activityType);
            if (imgDesc != null) {
                return imgDesc;
            }
        }
        String key = getImageRegistryKey(element, activityType);
        ImageDescriptor imageDescriptor = getImageRegistry().getDescriptor(key);
        if (imageDescriptor == null) {
            imageDescriptor = getProvidedImageDescriptor(element, activityType);
            if (imageDescriptor == null) {
                imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
            }
            getImageRegistry().put(key, imageDescriptor);
        }
        return imageDescriptor;
    }

    /**
     * @generated NOT
     */
    public static Image getImage(ENamedElement element, String activityString) {
        //check the extension of the editor.
        for (ISecondarySemanticHintProcessor sec : BpmnDiagramEditorPlugin.getInstance()
                    .getSecondarySemanticHintParsers()) {
            Image img = sec.getImageForTool(element, activityString);
            if (img != null) {
                return img;
            }
        }
        String key = getImageRegistryKey(element, activityString);
        Image image = getImageRegistry().get(key);
        if (image == null) {
            ImageDescriptor imageDescriptor = getProvidedImageDescriptor(
                    element, activityString);
            if (imageDescriptor == null) {
                imageDescriptor = ImageDescriptor.getMissingImageDescriptor();
            }
            getImageRegistry().put(key, imageDescriptor);
            image = getImageRegistry().get(key);
        }
        return image;
    }

    /**
     * @generated NOT
     */
    public static ImageDescriptor getImageDescriptor(IAdaptable hint) {
        ENamedElement element = getElement(hint);
        String activityString = hint instanceof IElementTypeEx ? ((IElementTypeEx) hint)
                .getSecondarySemanticHint()
                : null;
        if (element == null) {
            return null;
        }
        return getImageDescriptor(element, activityString);
    }

    /**
     * make sure we catch the type of the activity
     * 
     * @generated NOT
     */
    public static Image getImage(IAdaptable hint) {
        String activityString = hint instanceof IElementTypeEx ?
                ((IElementTypeEx) hint).getSecondarySemanticHint() : null;
        ENamedElement element = getElement(hint);
        if (element == null) {
            return null;
        }
                
        return getImage(element, activityString);
    }

    /**
     * Returns 'type' of the ecore object associated with the hint.
     * 
     * @generated NOT: put in place the HashMap instead of the IdentifityHashMap for IElementTypeEx to work
     * and we use ids insitead of instances 
     */
    public static ENamedElement getElement(IAdaptable hint) {
        if (hint instanceof IProxyEObject) {
            Object oo = ((IProxyEObject) hint).resolve();
            if (oo instanceof BpmnDiagram) {
                hint = BpmnDiagram_79;
            } else if (oo instanceof IAdaptable) {
                hint = (IAdaptable) oo;
            }
        }
        IElementType type = hint instanceof IElementType ? (IElementType) hint
                : (IElementType) hint.getAdapter(IElementType.class);
        if (elements == null) {
            elements = new HashMap<String,EClass>();
            elements.put(BpmnDiagram_79.getId(), BpmnPackage.eINSTANCE.getBpmnDiagram());
            elements.put(Activity_2001.getId(), BpmnPackage.eINSTANCE.getActivity());
            elements.put(SubProcess_2002.getId(), BpmnPackage.eINSTANCE.getSubProcess());
            elements.put(Activity_2003.getId(), BpmnPackage.eINSTANCE.getActivity());
            elements.put(TextAnnotation_2004.getId(), BpmnPackage.eINSTANCE.getTextAnnotation());
            elements.put(DataObject_2005.getId(), BpmnPackage.eINSTANCE.getDataObject());
            elements.put(Group_2006.getId(), BpmnPackage.eINSTANCE.getGroup());
            elements.put(Lane_2007.getId(), BpmnPackage.eINSTANCE.getLane());
            elements.put(Pool_1001.getId(), BpmnPackage.eINSTANCE.getPool());
            elements.put(TextAnnotation_1002.getId(), BpmnPackage.eINSTANCE.getTextAnnotation());
            elements.put(DataObject_1003.getId(), BpmnPackage.eINSTANCE.getDataObject());
            elements.put(Group_1004.getId(), BpmnPackage.eINSTANCE.getGroup());
            elements.put(SequenceEdge_3001.getId(), BpmnPackage.eINSTANCE.getSequenceEdge());
            elements.put(MessagingEdge_3002.getId(), BpmnPackage.eINSTANCE.getMessagingEdge());
            elements.put(Association_3003.getId(), BpmnPackage.eINSTANCE.getAssociation());
        }
        if (type != null) {
            return (ENamedElement) elements.get(type.getId());
        }
        // System.err.println("Looking for " + hint + " returns null");
        return null;
    }

    /**
     * @generated
     */
    public static final IElementType BpmnDiagram_79 = getElementType("org.eclipse.stp.bpmn.diagram.BpmnDiagram_79"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType Activity_2001 = getElementType("org.eclipse.stp.bpmn.diagram.Activity_2001"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType SubProcess_2002 = getElementType("org.eclipse.stp.bpmn.diagram.SubProcess_2002"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType Activity_2003 = getElementType("org.eclipse.stp.bpmn.diagram.Activity_2003"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType TextAnnotation_2004 = getElementType("org.eclipse.stp.bpmn.diagram.TextAnnotation_2004"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType DataObject_2005 = getElementType("org.eclipse.stp.bpmn.diagram.DataObject_2005"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType Group_2006 = getElementType("org.eclipse.stp.bpmn.diagram.Group_2006"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType Lane_2007 = getElementType("org.eclipse.stp.bpmn.diagram.Lane_2007"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType Pool_1001 = getElementType("org.eclipse.stp.bpmn.diagram.Pool_1001"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType TextAnnotation_1002 = getElementType("org.eclipse.stp.bpmn.diagram.TextAnnotation_1002"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType DataObject_1003 = getElementType("org.eclipse.stp.bpmn.diagram.DataObject_1003"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType Group_1004 = getElementType("org.eclipse.stp.bpmn.diagram.Group_1004"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType SequenceEdge_3001 = getElementType("org.eclipse.stp.bpmn.diagram.SequenceEdge_3001"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType MessagingEdge_3002 = getElementType("org.eclipse.stp.bpmn.diagram.MessagingEdge_3002"); //$NON-NLS-1$

    /**
     * @generated
     */
    public static final IElementType Association_3003 = getElementType("org.eclipse.stp.bpmn.diagram.Association_3003"); //$NON-NLS-1$

    /**
     * @generated
     */
    private static IElementType getElementType(String id) {
        return ElementTypeRegistry.getInstance().getType(id);
    }

    /**
     * @generated
     */
    private static Set KNOWN_ELEMENT_TYPES;

    /**
     * @generated NOT
     */
    public static boolean isKnownElementType(IElementType elementType) {
        if (elements == null) {
            return getElement(elementType) != null;
        }
        return elements.containsKey(elementType.getId());
/*        if (KNOWN_ELEMENT_TYPES == null) {
            KNOWN_ELEMENT_TYPES = new HashSet();
            KNOWN_ELEMENT_TYPES.add(BpmnDiagram_79);
            KNOWN_ELEMENT_TYPES.add(Activity_2001);
            KNOWN_ELEMENT_TYPES.add(SubProcess_2002);
            KNOWN_ELEMENT_TYPES.add(Activity_2003);
            KNOWN_ELEMENT_TYPES.add(TextAnnotation_2004);
            KNOWN_ELEMENT_TYPES.add(DataObject_2005);
            KNOWN_ELEMENT_TYPES.add(Group_2006);
            KNOWN_ELEMENT_TYPES.add(Lane_2007);
            KNOWN_ELEMENT_TYPES.add(Pool_1001);
            KNOWN_ELEMENT_TYPES.add(TextAnnotation_1002);
            KNOWN_ELEMENT_TYPES.add(DataObject_1003);
            KNOWN_ELEMENT_TYPES.add(Group_1004);
            KNOWN_ELEMENT_TYPES.add(SequenceEdge_3001);
            KNOWN_ELEMENT_TYPES.add(MessagingEdge_3002);
            KNOWN_ELEMENT_TYPES.add(Association_3003);
        }
        return KNOWN_ELEMENT_TYPES.contains(elementType);*/
    }
}
