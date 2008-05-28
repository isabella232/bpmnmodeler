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
 * Date         Author             Changes
 * Apr 30, 2007      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.diagram.providers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * This class handles the filters for the decorations.
 * It loads them from the plugin extension points, then
 * sets them to true according to the plugin preferences, so
 * that the state of the filters is remembered from one launch to
 * another.
 * 
 * The class is built as a singleton, and loads everything on its
 * initialization.
 * 
 * @author atoulme
 */
public class BpmnDecorationFilterService {

	/**
	 * Internal class holding the data necessary 
	 * to filter a marker.
	 * @author atoulme
	 */
	private class MarkerFilter {
		
		/**
		 * the type of the marker to be filtered.
		 */
		private String markerType;
		
		/**
		 * the optional code that can make the filter more specific.
		 * It corresponds to the code attribute attached to the marker
		 */
		private String code;
		
		/**
		 * Overridden equals to be able to be compared
		 * to a string corresponding to the markerType.
		 */
		public boolean equals(Object object) {
			if (object instanceof String) {
				return ((String) object).equals(markerType);
			}
			return super.equals(object);
		}
	}
	/**
	 * The class holding the information regarding the filter.
	 * It contains the name and the image descriptor that will
	 * be shown in the toolbar menu to select it.
	 *
	 * It also contains the EAnnotation sources and marker types
	 * on which it applies.
	 *
	 * It declares if it is enabled by default, and its current status
	 * is shown by its field active.
	 * 
	 * @author atoulme
	 */
	private class Filter {
		
		private String id;
		
		private List<String> sources = new ArrayList<String>();
		
		private List<MarkerFilter> markers = 
			new ArrayList<MarkerFilter>();
		
		private boolean onByDefault;
		
		private boolean active = false;

		private String name;

		public ImageDescriptor image;
	}
	
	private Map<String, Filter> filters = new HashMap<String, Filter>();
	
	private static BpmnDecorationFilterService instance;
	
	private BpmnDecorationFilterService() {
		init();
	}
	
	/**
	 * @return the central instance representing the 
	 * filters over the BPMN modeler.
	 */
	public static BpmnDecorationFilterService getInstance() {
		if (instance == null) {
			instance = new BpmnDecorationFilterService();
		}
		return instance;
	}
	
	/**
	 * Loads the extension point, then checks if they are activated
	 * by looking into the preference store.
	 */
	private void init() {
		
		IConfigurationElement[] configElems = Platform.getExtensionRegistry()
            .getConfigurationElementsFor("org.eclipse.stp.bpmn.diagram.DecorationFilter"); //$NON-NLS-1$
		for (int j = configElems.length - 1; j >= 0; j--) {
			try {
				parseFilter(configElems[j]);
			} catch (Exception e) {
				BpmnDiagramEditorPlugin.getInstance().
					logError(e.getMessage(), e);
				continue;
			}
		}
    	
    	String configuration = BpmnDiagramEditorPlugin.getInstance().
    		getPreferenceStore().
    		getString(BpmnDiagramPreferenceInitializer.FILTERS_STATES);
    	for (String filterInformation : configuration.split(";")) { //$NON-NLS-1$
    		String[] filterSplit = filterInformation.split(","); //$NON-NLS-1$
    		if (filterSplit.length == 2) { // the last element
    			// is an empty string, so we filter it.
    			Filter filter = filters.get(filterSplit[0]);
    			if (filter == null) {
    				continue;
    			}
    			filter.active = Boolean.parseBoolean(filterSplit[1]);
    		}
    	}
	}

	/**
	 * Parses the IConfigurationElement to store the information
	 * relative to the filter into an instance of the Filter class.
	 * @param element
	 */
	private void parseFilter(IConfigurationElement element) {
		Filter filter = new Filter();
		
		filter.id = element.getAttribute("id"); //$NON-NLS-1$
		
		filter.name = element.getAttribute("name"); //$NON-NLS-1$
		
		filter.onByDefault = Boolean.parseBoolean(
				element.getAttribute("onByDefault")); //$NON-NLS-1$
		
		filter.active = filter.onByDefault;
		
		String iconName = element.getAttribute("icon"); //$NON-NLS-1$
		
		if (iconName != null) {
			ImageDescriptor imageDesc = AbstractUIPlugin
				.imageDescriptorFromPlugin(
						element.getNamespaceIdentifier(), 
						iconName);
			filter.image = imageDesc;
		}
		
		for (IConfigurationElement source : element.getChildren("source")) { //$NON-NLS-1$
			filter.sources.add(source.getAttribute("value")); //$NON-NLS-1$
		}
		
		for (IConfigurationElement markerType : element.getChildren("marker")) { //$NON-NLS-1$
			MarkerFilter markerFilter = new MarkerFilter();
			markerFilter.markerType = markerType.getAttribute("type"); //$NON-NLS-1$
			markerFilter.code = markerType.getAttribute("code"); //$NON-NLS-1$
			filter.markers.add(markerFilter);
		}
		
		if (filter.id != null && (!filter.sources.isEmpty() || 
				!filter.markers.isEmpty())) {
			filters.put(filter.id, filter);
		}
	}
	
	/**
	 * This method iterates over the filters, if one is active and 
	 * contains this source, then the method returns true.
	 * 
	 * @param annotationSource
	 * @return true if the annotation source is currently filtered.
	 */ 
	public boolean isSourceFiltered(String annotationSource) {
		for (Filter filter : filters.values()) {
			if (filter.active) {
				if (filter.sources.contains(annotationSource)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * This method iterates over the filters, if one is active and 
	 * contains this marker type, then the method returns true.
	 * 
	 * @param markerType the type of the marker
	 * @param code the value of the code attribute
	 * @return true if the marker type is currently filtered.
	 */ 
	public boolean isMarkerFiltered(String markerType, Object code) {
		String codeAsString = code == null ? null : code.toString();
		for (Filter filter : filters.values()) {
			if (filter.active) {
				for (MarkerFilter markerFilter : filter.markers) {
					if (markerFilter.equals(markerType)) {
						if (codeAsString != null) {
							if (!codeAsString.equals(markerFilter.code)) {
								continue;
							}
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Sets the active value of the filter to the contrary of 
	 * its present value.  
	 * The filter values are saved immediately when this event occurs.
	 * @param filterId the id representing the filter.
	 */
	public void filterChanged(String filterId) {
		Filter filter = filters.get(filterId);
		assert(filter != null);
		filter.active = !filter.active;
		saveFilterValues();
	}
	
	/**
	 * Saves all the filter values in the preference store under this
	 * form:
	 * org.eclipse.stp.filter1,true;org.eclipse.stp.filter2,false;
	 */
	private void saveFilterValues() {
		StringBuffer buffer = new StringBuffer();
		for (Filter filter : filters.values()) {
			buffer.append(filter.id + "," + filter.active + ";"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		BpmnDiagramEditorPlugin.getInstance().
			getPreferenceStore().setValue(
					BpmnDiagramPreferenceInitializer.FILTERS_STATES, 
					buffer.toString());
	}
	
	/**
	 * @return the list of the current filters loaded on this instance.
	 * Used to create the toolbar menu actions.
	 */
	public Set<String> getCurrentFilters() {
		return filters.keySet();
	}
	
	/**
	 * @param id the id of the filter.
	 * @return the name for this filter if such a filter exists
	 * in the filters Map present in memory, or null.
	 */
	public String getFilterName(String id) {
		return filters.get(id) == null ? null :
			filters.get(id).name;
	}
	
	/**
	 * @param id the id of the filter.
	 * @return the image descriptor for this filter 
	 * if such a filter exists
	 * in the filters Map present in memory and if an image descriptor
	 * is defined for this item, or null.
	 */
	public ImageDescriptor getFilterImageDescriptor(String id) {
		return filters.get(id) == null ? null :
			filters.get(id).image;
	}

	/**
	 * 
	 * @param filterId
	 * @return true if the filter associated with this id is 
	 * active, false if the filter was not found or the 
	 * filter is inactive.
	 */
	public boolean isFilterActive(String filterId) {
		return filters.get(filterId) == null ? false :
			filters.get(filterId).active;
	}
}
