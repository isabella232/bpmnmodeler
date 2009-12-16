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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EModelElement;
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
import org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator2;
import org.eclipse.stp.bpmn.dnd.ISecondarySemanticHintProcessor;
import org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator2.IEAnnotationDecoratorData;
import org.eclipse.stp.bpmn.dnd.file.IFileDnDHandlerFactory;
import org.eclipse.stp.bpmn.menu.internal.ProxiedBpmnDiagramContextMenuListener;
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

    private Map<String,Object> _annotationDecoratorCache;
    private List<ISecondarySemanticHintProcessor> _secondarySemanticHintProcessorCache;
    private Map<String,List<ProxiedBpmnDiagramContextMenuListener>> _bpmnContextMenuProviders;

    private List<IFileDnDHandlerFactory> _fileDnDHandlerFactoriesRegistry;
    
    /**
     * @generated
     */
    public BpmnDiagramEditorPlugin() {
    }

    /**
     * @generated not
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        instance = this;
        PreferencesHint.registerPreferenceStore(DIAGRAM_PREFERENCES_HINT, getPreferenceStore());
        adapterFactory = createAdapterFactory();
        initEAnnotationDecoratorRegistry();
        initSecondarySemanticHintProcessorRegistry();
        initBpmnContextMenuProvidersRegistry();
        initFileDnDHandlersRegistry();
    }

    private void initFileDnDHandlersRegistry() {
        _fileDnDHandlerFactoriesRegistry =
            new ArrayList<IFileDnDHandlerFactory>();
        IConfigurationElement[] configElems = Platform.getExtensionRegistry()
            .getConfigurationElementsFor(BPMN_DIAGRAM_FILE_DND_HANDLERS);
        for (int j = configElems.length - 1; j >= 0; j--) {
            try {
                IFileDnDHandlerFactory res =
                    (IFileDnDHandlerFactory) configElems[j].createExecutableExtension("class"); //$NON-NLS-1$
                _fileDnDHandlerFactoriesRegistry.add(res);
            } catch (Exception e) {
                logError(e.getMessage(), e);
                continue;
            }
        }
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
     * @generated not
     */
    public static final String BPMN_DND_HANDLER_EXTENSION_POINT = ID + 
    				".BpmnDnD"; //$NON-NLS-1$
    
    /** 
     * @generated not
     */
    public static final String EANNOTATION_DECORATOR_EXTENSION_POINT =
        ID + ".EAnnotationDecorator"; //$NON-NLS-1$
    /** 
     * @generated not
     */
    public static final String SECONDARY_SEMANTIC_HINT_PROCESSOR_EXTENSION_POINT =
        ID + ".SecondarySemanticHintProcessor"; //$NON-NLS-1$
    /** 
     * @generated not
     */
    public static final String BPMN_DIAGRAM_CONTEXT_MENU_PROVIDERS =
        ID + ".BpmnDiagramEditorContextMenuContributor"; //$NON-NLS-1$
        
    /**
     * @generated not
     */
    public static final String BPMN_DIAGRAM_FILE_DND_HANDLERS = 
        ID + ".FileDnDHandlers"; //$NON-NLS-1$
    /**
     * @notgenerated
     * returns the class implementing the extension point 
     * for the generic decoration of an Eannotation 
     */
    public Object getEAnnotationDecorator(String annotationSource) {
        return _annotationDecoratorCache.get(annotationSource);
    }
    
    /**
     * Finds the image for a particular annotation source.
     */
    public Image findAnnotationImage(EModelElement elt, String annotationSource) {
        Object deco = BpmnDiagramEditorPlugin.getInstance().
            getEAnnotationDecorator(annotationSource);
        if (deco instanceof IEAnnotationDecorator) {
            return ((IEAnnotationDecorator) deco).getImage(null, elt,
                    elt.getEAnnotation(annotationSource));
        } else if (deco instanceof IEAnnotationDecorator2) {
            Collection<IEAnnotationDecoratorData> decorators = 
                ((IEAnnotationDecorator2) deco).getDecorators(null,elt,
                    elt.getEAnnotation(annotationSource));
            if (!decorators.isEmpty()) {
            	return decorators.iterator().next().getImage();
            }
        }
        return null;
    }
    /**
     * @generated not
     * returns the class implementing the extension point 
     * for the generic drag and drop of an Eannotation 
     */
    public List<ISecondarySemanticHintProcessor> getSecondarySemanticHintParsers() {
        return _secondarySemanticHintProcessorCache;
    }
    
    public List<IFileDnDHandlerFactory> getFileDnDHandlerFactories() {
        return Collections.unmodifiableList(_fileDnDHandlerFactoriesRegistry);
    }
    
    private void initEAnnotationDecoratorRegistry() {
        _annotationDecoratorCache =
            new HashMap<String, Object>();
        IConfigurationElement[] configElems = Platform.getExtensionRegistry()
            .getConfigurationElementsFor(EANNOTATION_DECORATOR_EXTENSION_POINT);
        for (int j = configElems.length - 1; j >= 0; j--) {
            try {
                String sourceAtt = configElems[j].getAttribute("source"); //$NON-NLS-1$
                if (sourceAtt != null) {
                    Object res = configElems[j]
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
    private void initBpmnContextMenuProvidersRegistry() {
        _bpmnContextMenuProviders =
            new HashMap<String,List<ProxiedBpmnDiagramContextMenuListener>>();
        IConfigurationElement[] configElems = Platform.getExtensionRegistry()
            .getConfigurationElementsFor(BPMN_DIAGRAM_CONTEXT_MENU_PROVIDERS);
        for (int j = configElems.length - 1; j >= 0; j--) {
            try {
                String ids = configElems[j].getAttribute("targetedBpmnEditorIDs"); //$NON-NLS-1$
                String[] idColl = ids == null
                    ? new String[] {BpmnDiagramEditor.ID}
                    : ids.split(" ,"); //$NON-NLS-1$
                int priority = 0;
                try {
                    priority = Integer.parseInt(configElems[j].getAttribute("priority")); //$NON-NLS-1$
                } catch (Throwable nfe) {
                    //nevermind.
                }
                ProxiedBpmnDiagramContextMenuListener res =
                    new ProxiedBpmnDiagramContextMenuListener(configElems[j], priority);
                for (String id : idColl) {
                    List<ProxiedBpmnDiagramContextMenuListener> theList = _bpmnContextMenuProviders.get(id);
                    if (theList == null) {
                        theList = new ArrayList<ProxiedBpmnDiagramContextMenuListener>();
                        _bpmnContextMenuProviders.put(id, theList);
                    }
                    theList.add(res);
                }
            } catch (Exception e) {
                logError(e.getMessage(), e);
                continue;
            }
        }
        for (List<ProxiedBpmnDiagramContextMenuListener> proxied : _bpmnContextMenuProviders.values()) {
            Collections.sort(proxied, new Comparator<ProxiedBpmnDiagramContextMenuListener>() {
                public int compare(ProxiedBpmnDiagramContextMenuListener arg0,
                        ProxiedBpmnDiagramContextMenuListener arg1) {
                    int res = arg0.getPriority() - arg1.getPriority();
                    if (res == 0) {
                        res = arg0.getClass().getSimpleName().compareTo(arg1.getClass().getSimpleName());
                        if (res == 0) {
                            return arg0.getClass().getName().compareTo(arg1.getClass().getName());
                        }
                    }
                    return res;
                }
            });
        }
    }
    
    public List<ProxiedBpmnDiagramContextMenuListener> getContextMenuListeners(String editorId) {
        return _bpmnContextMenuProviders.get(editorId);
    }
        
}

