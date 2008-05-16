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
package org.eclipse.stp.bpmn.diagram.part;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator;
import org.eclipse.stp.bpmn.dnd.ISecondarySemanticHintProcessor;
import org.eclipse.stp.bpmn.provider.BpmnItemProviderAdapterFactory;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * @generated
 */
public class BpmnDiagramEditorPlugin extends AbstractUIPlugin {

    /**
     * @generated
     */
    public static final String ID = "org.eclipse.stp.bpmn.diagram"; //$NON-NLS-1$

    /**
     * @generated
     */
    public static final PreferencesHint DIAGRAM_PREFERENCES_HINT = new PreferencesHint(
            ID);

    /**
     * @generated
     */
    private static BpmnDiagramEditorPlugin instance;

    /**
     * @generated
     */
    private ComposedAdapterFactory adapterFactory;

    /**
     * The unique preference store.
     */
    public static IPreferenceStore PREF_STORE;

    private Map<String,IEAnnotationDecorator> _annotationDecoratorCache;
    private List<ISecondarySemanticHintProcessor> _secondarySemanticHintProcessorCache;
    /**
     * @generated
     */
    public BpmnDiagramEditorPlugin() {
    }

    /**
     * @generated
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        instance = this;
        PreferencesHint.registerPreferenceStore(DIAGRAM_PREFERENCES_HINT, getPreferenceStore());
        adapterFactory = createAdapterFactory();
        initEAnnotationDecoratorRegistry();
        initSecondarySemanticHintProcessorRegistry();
    }

    /**
     * Make sure there is a unique preference store here.
     */
    @Override
    public IPreferenceStore getPreferenceStore() {
        if (PREF_STORE == null) {
            PREF_STORE = super.getPreferenceStore();
        }
        return PREF_STORE;
    }

    /**
     * @generated
     */
    public void stop(BundleContext context) throws Exception {
    	instance = null;
        adapterFactory.dispose();
        adapterFactory = null;
        super.stop(context);
    }

    /**
     * @generated
     */
    public static BpmnDiagramEditorPlugin getInstance() {
        return instance;
    }

    /**
     * @generated
     */
    protected ComposedAdapterFactory createAdapterFactory() {
        List factories = new ArrayList();
        fillItemProviderFactories(factories);
        return new ComposedAdapterFactory(factories);
    }

    /**
     * @generated
     */
    protected void fillItemProviderFactories(List factories) {
        factories.add(new BpmnItemProviderAdapterFactory());
        factories.add(new EcoreItemProviderAdapterFactory());
        factories.add(new ResourceItemProviderAdapterFactory());
        factories.add(new ReflectiveItemProviderAdapterFactory());
    }

    /**
     * @generated
     */
    public AdapterFactory getItemProvidersAdapterFactory() {
        return adapterFactory;
    }

    /**
     * @generated
     */
    public ImageDescriptor getItemImageDescriptor(Object item) {
        IItemLabelProvider labelProvider = (IItemLabelProvider) adapterFactory
                .adapt(item, IItemLabelProvider.class);
        if (labelProvider != null) {
            return ExtendedImageRegistry.getInstance().getImageDescriptor(
                    labelProvider.getImage(item));
        }
        return null;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path.
     * 
     * @generated
     * @param path
     *            the path
     * @return the image descriptor
     */
    public static ImageDescriptor getBundledImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(ID, path);
    }

    /**
     * Returns an image for the image file at the given plug-in relative path.
     * Client do not need to dispose this image. Images will be disposed
     * automatically.
     * 
     * @generated
     * @param path
     *            the path
     * @return image instance
     */
    public Image getBundledImage(String path) {
        ImageDescriptor descriptor = getImageRegistry().getDescriptor(path);
        if (descriptor == null) {
            getImageRegistry().put(path, getBundledImageDescriptor(path));
        }
        return getImageRegistry().get(path);
    }

    /**
     * @generated
     */
    public void logError(String error) {
        logError(error, null);
    }

    /**
     * @param throwable
     *            actual error or null could be passed
     * @generated
     */
    public void logError(String error, Throwable throwable) {
        if (error == null && throwable != null) {
            error = throwable.getMessage();
        }
        getLog().log(
                new Status(IStatus.ERROR, BpmnDiagramEditorPlugin.ID,
                        IStatus.OK, error, throwable));
        debug(error, throwable);
    }

    /**
     * @generated
     */
    public void logInfo(String message) {
        logInfo(message, null);
    }

    /**
     * @param throwable
     *            actual error or null could be passed
     * @generated
     */
    public void logInfo(String message, Throwable throwable) {
        if (message == null && message != null) {
            message = throwable.getMessage();
        }
        getLog().log(
                new Status(IStatus.INFO, BpmnDiagramEditorPlugin.ID,
                        IStatus.OK, message, throwable));
        debug(message, throwable);
    }

    /**
     * @generated
     */
    private void debug(String message, Throwable throwable) {
        if (!isDebugging()) {
            return;
        }
        if (message != null) {
            System.err.println(message);
        }
        if (throwable != null) {
            throwable.printStackTrace();
        }
    }

    /**
     * @notgenerated
     */
    public static final String BPMN_DND_HANDLER_EXTENSION_POINT = ID + 
    				".BpmnDnD"; //$NON-NLS-1$
    
    /**
     * @notgenerated
     */
    public static final String EANNOTATION_DECORATOR_EXTENSION_POINT =
        ID + ".EAnnotationDecorator"; //$NON-NLS-1$
    /**
     * @notgenerated
     */
    public static final String SECONDARY_SEMANTIC_HINT_PROCESSOR_EXTENSION_POINT =
        ID + ".SecondarySemanticHintProcessor"; //$NON-NLS-1$
        
    /**
     * @notgenerated
     * returns the class implementing the extension point 
     * for the generic drag and drop of an Eannotation 
     */
    public IEAnnotationDecorator getEAnnotationDecorator(String annotationSource) {
        return _annotationDecoratorCache.get(annotationSource);
    }
    /**
     * @notgenerated
     * returns the class implementing the extension point 
     * for the generic drag and drop of an Eannotation 
     */
    public List<ISecondarySemanticHintProcessor> getSecondarySemanticHintParsers() {
        return _secondarySemanticHintProcessorCache;
    }
    
    private void initEAnnotationDecoratorRegistry() {
        _annotationDecoratorCache =
            new HashMap<String, IEAnnotationDecorator>();
        IConfigurationElement[] configElems = Platform.getExtensionRegistry()
            .getConfigurationElementsFor(EANNOTATION_DECORATOR_EXTENSION_POINT);
        for (int j = configElems.length - 1; j >= 0; j--) {
            try {
                String sourceAtt = configElems[j].getAttribute("source"); //$NON-NLS-1$
                if (sourceAtt != null) {
                    IEAnnotationDecorator res = (IEAnnotationDecorator) configElems[j]
                               .createExecutableExtension("class"); //$NON-NLS-1$
                    _annotationDecoratorCache.put(sourceAtt, res);
                }
            } catch (Exception e) {
                logError(e.getMessage(), e);
                continue;
            }
        }
    }
    
    private void initSecondarySemanticHintProcessorRegistry() {
        _secondarySemanticHintProcessorCache =
            new ArrayList<ISecondarySemanticHintProcessor>();
        IConfigurationElement[] configElems = Platform.getExtensionRegistry()
            .getConfigurationElementsFor(SECONDARY_SEMANTIC_HINT_PROCESSOR_EXTENSION_POINT);
        for (int j = configElems.length - 1; j >= 0; j--) {
            try {
                ISecondarySemanticHintProcessor res =
                    (ISecondarySemanticHintProcessor) configElems[j].createExecutableExtension("class"); //$NON-NLS-1$
                _secondarySemanticHintProcessorCache.add(res);
            } catch (Exception e) {
                logError(e.getMessage(), e);
                continue;
            }
        }
    }
        
}

