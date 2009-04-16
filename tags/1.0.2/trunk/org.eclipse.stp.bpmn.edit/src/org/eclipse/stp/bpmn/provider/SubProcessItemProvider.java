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
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ViewerNotification;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.BpmnFactory;
import org.eclipse.stp.bpmn.BpmnPackage;
import org.eclipse.stp.bpmn.SubProcess;

/**
 * This is the item provider adapter for a {@link org.eclipse.stp.bpmn.SubProcess} object.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class SubProcessItemProvider
	extends ActivityItemProvider
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
	public SubProcessItemProvider(AdapterFactory adapterFactory) {
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

            addIsTransactionPropertyDescriptor(object);
        }
        return itemPropertyDescriptors;
    }

    /**
     * This adds a property descriptor for the Is Transaction feature.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected void addIsTransactionPropertyDescriptor(Object object) {
        itemPropertyDescriptors.add
            (createItemPropertyDescriptor
                (((ComposeableAdapterFactory)adapterFactory).getRootAdapterFactory(),
                 getResourceLocator(),
                 getString("_UI_SubProcess_isTransaction_feature"), //$NON-NLS-1$
                 getString("_UI_PropertyDescriptor_description", "_UI_SubProcess_isTransaction_feature", "_UI_SubProcess_type"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                 BpmnPackage.Literals.SUB_PROCESS__IS_TRANSACTION,
                 true,
                 false,
                 false,
                 ItemPropertyDescriptor.BOOLEAN_VALUE_IMAGE,
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
            childrenFeatures.add(BpmnPackage.Literals.GRAPH__VERTICES);
            childrenFeatures.add(BpmnPackage.Literals.GRAPH__SEQUENCE_EDGES);
            childrenFeatures.add(BpmnPackage.Literals.SUB_PROCESS__EVENT_HANDLERS);
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
     * This returns SubProcess.gif.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public Object getImageGen(Object object) {
        return overlayImage(object, getResourceLocator().getImage("full/obj16/SubProcess")); //$NON-NLS-1$
    }

    /**
     * @notgenerated
     */
    public Object getImage(Object object) {
        String filename;
        if (((Activity)object).isLooping()) {
            filename = SUB_PROCESS_EXPANDED_LOOPING;
        } else {
            filename = SUB_PROCESS_EXPANDED;
        }
        return overlayImage(object, getResourceLocator().getImage(
                "full/obj24/activities/" + filename)); //$NON-NLS-1$
    }
    
    /**
     * This returns the label text for the adapted class.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public String getText(Object object) {
        String label = ((SubProcess)object).getName();
        return label == null || label.length() == 0 ?
            getString("_UI_SubProcess_type") : //$NON-NLS-1$
            getString("_UI_SubProcess_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
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

        switch (notification.getFeatureID(SubProcess.class)) {
            case BpmnPackage.SUB_PROCESS__IS_TRANSACTION:
                fireNotifyChanged(new ViewerNotification(notification, notification.getNotifier(), false, true));
                return;
            case BpmnPackage.SUB_PROCESS__ARTIFACTS:
            case BpmnPackage.SUB_PROCESS__VERTICES:
            case BpmnPackage.SUB_PROCESS__SEQUENCE_EDGES:
            case BpmnPackage.SUB_PROCESS__EVENT_HANDLERS:
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
                (BpmnPackage.Literals.GRAPH__VERTICES,
                 BpmnFactory.eINSTANCE.createVertex()));

        newChildDescriptors.add
            (createChildParameter
                (BpmnPackage.Literals.GRAPH__VERTICES,
                 BpmnFactory.eINSTANCE.createActivity()));

        newChildDescriptors.add
            (createChildParameter
                (BpmnPackage.Literals.GRAPH__VERTICES,
                 BpmnFactory.eINSTANCE.createSubProcess()));

        newChildDescriptors.add
            (createChildParameter
                (BpmnPackage.Literals.GRAPH__SEQUENCE_EDGES,
                 BpmnFactory.eINSTANCE.createSequenceEdge()));

        newChildDescriptors.add
            (createChildParameter
                (BpmnPackage.Literals.SUB_PROCESS__EVENT_HANDLERS,
                 BpmnFactory.eINSTANCE.createActivity()));

        newChildDescriptors.add
            (createChildParameter
                (BpmnPackage.Literals.SUB_PROCESS__EVENT_HANDLERS,
                 BpmnFactory.eINSTANCE.createSubProcess()));
    }

    /**
     * This returns the label text for {@link org.eclipse.emf.edit.command.CreateChildCommand}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getCreateChildText(Object owner, Object feature, Object child, Collection<?> selection) {
        Object childFeature = feature;
        Object childObject = child;

        if (childFeature instanceof EStructuralFeature && FeatureMapUtil.isFeatureMap((EStructuralFeature)childFeature)) {
            FeatureMap.Entry entry = (FeatureMap.Entry)childObject;
            childFeature = entry.getEStructuralFeature();
            childObject = entry.getValue();
        }

        boolean qualify =
            childFeature == BpmnPackage.Literals.MESSAGE_VERTEX__INCOMING_MESSAGES ||
            childFeature == BpmnPackage.Literals.MESSAGE_VERTEX__OUTGOING_MESSAGES ||
            childFeature == BpmnPackage.Literals.GRAPH__VERTICES ||
            childFeature == BpmnPackage.Literals.SUB_PROCESS__EVENT_HANDLERS;

        if (qualify) {
            return getString
                ("_UI_CreateChild_text2", //$NON-NLS-1$
                 new Object[] { getTypeText(childObject), getFeatureText(childFeature), getTypeText(owner) });
        }
        return super.getCreateChildText(owner, feature, child, selection);
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
