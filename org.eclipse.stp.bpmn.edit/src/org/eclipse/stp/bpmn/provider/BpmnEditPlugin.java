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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.provider.EcoreEditPlugin;

/**
 * This is the central singleton for the Bpmn edit plugin.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public final class BpmnEditPlugin extends EMFPlugin {
    /**
     * Keep track of the singleton.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public static final BpmnEditPlugin INSTANCE = new BpmnEditPlugin();

    /**
     * Keep track of the singleton.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	private static Implementation plugin;

    /**
     * Create the instance.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public BpmnEditPlugin() {
        super
          (new ResourceLocator [] {
             EcoreEditPlugin.INSTANCE,
           });
    }

	  /*
	   * Hugues: add the png hack.
	   */
	  public Object getImage(String key)
	  {
		  if (key.indexOf("activities/") != -1) { //$NON-NLS-1$
			  InputStream inputStream = null;
			  try {
                  URL url = new URL(getBaseURL() + "icons/" + key + ".png"); //$NON-NLS-1$ //$NON-NLS-2$
				  inputStream = url.openStream(); 
				  return url;
			  } catch (Exception e) {
			  } finally {
				  if (inputStream != null) {
					  try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				  }
			  }
		  }
		  return super.getImage(key);
	  }

    /**
     * Returns the singleton instance of the Eclipse plugin.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the singleton instance.
     * @generated
     */
	@Override
    public ResourceLocator getPluginResourceLocator() {
        return plugin;
    }

    /**
     * Returns the singleton instance of the Eclipse plugin.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @return the singleton instance.
     * @generated
     */
	public static Implementation getPlugin() {
        return plugin;
    }

    /**
     * The actual implementation of the Eclipse <b>Plugin</b>.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public static class Implementation extends EclipsePlugin {
        /**
         * Creates an instance.
         * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
         * @generated
         */
		public Implementation() {
            super();

            // Remember the static instance.
            //
            plugin = this;
        }
    }

}
