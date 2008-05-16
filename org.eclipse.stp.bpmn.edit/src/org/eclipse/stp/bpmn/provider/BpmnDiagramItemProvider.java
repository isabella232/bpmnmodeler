/******************************************************************************
* Copyright (c) 2006, Intalio Inc.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
* 
* Contributors:
*     Intalio Inc. - initial API and implementation
*******************************************************************************/

package org.eclipse.stp.bpmn.provider;



import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.stp.bpmn.BpmnDiagram;
import org.eclipse.stp.bpmn.BpmnFactory;
import org.eclipse.stp.bpmn.BpmnPackage;

/**
 * This is the item provider adapter for a {@link org.eclipse.stp.bpmn.BpmnDiagram} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class BpmnDiagramItemProvider
	extends IdentifiableItemProvider
	implements	
		IEditingDomainItemProvider,	
		IStructuredItemContentProvider,	
		ITreeItemContentProvider,	
		IItemLabelProvider,	
		IItemPropertySource {
    /**
     * This constructs an instance from a factory and a notifier.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public BpmnDiagramItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * This returns the property descriptors for the adapted class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        if (itemPropertyDescriptors == null) {
            super.getPropertyDescriptors(object);

            addDocumentationPropertyDescriptor(object);
            addNamePropertyDescriptor(object);
            addNcnamePropertyDescriptor(object);
            addAuthorPropertyDescriptor(object);
            addTitlePropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Documentation feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addDocumentationPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_NamedBpmnObject_documentation_feature"), //$NON-NLS-1$
                 getString("_UI_PropertyDescriptor_description", "_UI_NamedBpmnObject_documentation_feature", "_UI_NamedBpmnObject_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                 BpmnPackage.Literals.NAMED_BPMN_OBJECT__DOCUMENTATION,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Name feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addNamePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_NamedBpmnObject_name_feature"), //$NON-NLS-1$
                 getString("_UI_PropertyDescriptor_description", "_UI_NamedBpmnObject_name_feature", "_UI_NamedBpmnObject_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                 BpmnPackage.Literals.NAMED_BPMN_OBJECT__NAME,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Ncname feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void addNcnamePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_NamedBpmnObject_ncname_feature"), //$NON-NLS-1$
                 getString("_UI_PropertyDescriptor_description", "_UI_NamedBpmnObject_ncname_feature", "_UI_NamedBpmnObject_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                 BpmnPackage.Literals.NAMED_BPMN_OBJECT__NCNAME,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Author feature.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected void addAuthorPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_BpmnDiagram_author_feature"), //$NON-NLS-1$
                 getString("_UI_PropertyDescriptor_description", "_UI_BpmnDiagram_author_feature", "_UI_BpmnDiagram_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                 BpmnPackage.Literals.BPMN_DIAGRAM__AUTHOR,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This adds a property descriptor for the Title feature.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected void addTitlePropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_BpmnDiagram_title_feature"), //$NON-NLS-1$
                 getString("_UI_PropertyDescriptor_description", "_UI_BpmnDiagram_title_feature", "_UI_BpmnDiagram_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                 BpmnPackage.Literals.BPMN_DIAGRAM__TITLE,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.GENERIC_VALUE_IMAGE,
                 null,
                 null));
    }

    /**
     * This specifies how to implement {@link #getChildren} and is used to deduce an appropriate feature for an
     * {@link org.eclipse.emf.edit.command.AddCommand}, {@link org.eclipse.emf.edit.command.RemoveCommand} or
     * {@link org.eclipse.emf.edit.command.MoveCommand} in {@link #createCommand}.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public Collection<? extends EStructuralFeature> getChildrenFeatures(Object object) {
        if (childrenFeatures == null) {
            super.getChildrenFeatures(object);
            childrenFeatures.add(BpmnPackage.Literals.ARTIFACTS_CONTAINER__ARTIFACTS);
            childrenFeatures.add(BpmnPackage.Literals.BPMN_DIAGRAM__POOLS);
            childrenFeatures.add(BpmnPackage.Literals.BPMN_DIAGRAM__MESSAGES);
        }
        return childrenFeatures;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    protected EStructuralFeature getChildFeature(Object object, Object child) {
        // Check the type of the specified child object and return the proper feature to use for
        // adding (see {@link AddCommand}) it as a child.

        return super.getChildFeature(object, child);
    }

    /**
     * This returns BpmnDiagram.gif.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public Object getImage(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/BpmnDiagram")); //$NON-NLS-1$
    }

    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public String getText(Object object) {
        String label = ((BpmnDiagram)object).getName();
        return label == null || label.length() == 0 ?
            getString("_UI_BpmnDiagram_type") : //$NON-NLS-1$
            getString("_UI_BpmnDiagram_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * This handles model notifications by calling {@link #updateChildren} to update any cached
     * children and by creating a viewer notification, which it passes to {@link #fireNotifyChanged}.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public void notifyChanged(Notification notification) {
        updateChildren(notification);

        switch (notification.getFeatureID(BpmnDiagram.class)) {
            case BpmnPackage.BPMN_DIAGRAM__DOCUMENTATION:
            case BpmnPackage.BPMN_DIAGRAM__NAME:
            case BpmnPackage.BPMN_DIAGRAM__NCNAME:
            case BpmnPackage.BPMN_DIAGRAM__AUTHOR:
            case BpmnPackage.BPMN_DIAGRAM__TITLE:
                fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case BpmnPackage.BPMN_DIAGRAM__ARTIFACTS:
            case BpmnPackage.BPMN_DIAGRAM__POOLS:
            case BpmnPackage.BPMN_DIAGRAM__MESSAGES:
                fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), true, false));
                return;
        }
        super.notifyChanged(notification);
    }

    /**
     * This adds {@link org.eclipse.emf.edit.command.CommandParameter}s describing the children
     * that can be created under this object.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    protected void collectNewChildDescriptors(Collection<Object> newChildDescriptors, Object object) {
        super.collectNewChildDescriptors(newChildDescriptors, object);

        newChildDescriptors.add
            (createChildParameter
                (BpmnPackage.Literals.ARTIFACTS_CONTAINER__ARTIFACTS,
                 BpmnFactory.eINSTANCE.createArtifact()));

        newChildDescriptors.add
            (createChildParameter
                (BpmnPackage.Literals.ARTIFACTS_CONTAINER__ARTIFACTS,
                 BpmnFactory.eINSTANCE.createDataObject()));

        newChildDescriptors.add
            (createChildParameter
                (BpmnPackage.Literals.ARTIFACTS_CONTAINER__ARTIFACTS,
                 BpmnFactory.eINSTANCE.createGroup()));

        newChildDescriptors.add
            (createChildParameter
                (BpmnPackage.Literals.ARTIFACTS_CONTAINER__ARTIFACTS,
                 BpmnFactory.eINSTANCE.createTextAnnotation()));

        newChildDescriptors.add
            (createChildParameter
                (BpmnPackage.Literals.BPMN_DIAGRAM__POOLS,
                 BpmnFactory.eINSTANCE.createPool()));

        newChildDescriptors.add
            (createChildParameter
                (BpmnPackage.Literals.BPMN_DIAGRAM__MESSAGES,
                 BpmnFactory.eINSTANCE.createMessagingEdge()));
    }

    /**
     * Return the resource locator for this item provider's resources.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public ResourceLocator getResourceLocator() {
        return BpmnEditPlugin.INSTANCE;
    }

}
