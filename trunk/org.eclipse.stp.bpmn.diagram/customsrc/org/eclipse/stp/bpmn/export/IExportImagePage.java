/**
 ******************************************************************************
 * Copyright (c) 2006, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 *
 * Date         Author             Changes
 * Jun 18, 2007      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.export;

import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.gmf.runtime.diagram.ui.image.ImageFileFormat;
import org.eclipse.jface.wizard.IWizardPage;

/**
 * The interface for the wizard page used for the export
 * of diagrams as images.
 * 
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public interface IExportImagePage extends IWizardPage {

    /**
     * Computes the destination for the image file
     * @return
     */
    public abstract IPath getDestinationPath();

    /**
     * @return the imageFormat
     */
    public abstract ImageFileFormat getImageFormat();

    /**
     * @return the list of the resources selected in the page
     */
    public abstract List getSelectedResources();
}