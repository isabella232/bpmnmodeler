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

/**
 * Date			    Author			    Changes
 * Jul 19, 2006		hmalphettes			Created
 **/

package org.eclipse.stp.bpmn.figures.activities;

import org.eclipse.stp.bpmn.figures.connectionanchors.DefaultSizeNodeFigureEx;
import org.eclipse.stp.bpmn.figures.connectionanchors.IConnectionAnchorFactory;

/**
 * Manages connection anchors.
 * 
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ActivityNodeFigure extends DefaultSizeNodeFigureEx {

    public ActivityNodeFigure(IConnectionAnchorFactory connectionAnchorFactory,
            int width, int height) {
        super(width, height, connectionAnchorFactory);
    }
 
}
