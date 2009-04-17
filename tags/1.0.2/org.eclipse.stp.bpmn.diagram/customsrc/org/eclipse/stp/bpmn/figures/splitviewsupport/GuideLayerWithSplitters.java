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
package org.eclipse.stp.bpmn.figures.splitviewsupport;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.editparts.GuideLayer;

/**
 * Keeps the ViewPort updated about the changes in the guides
 * defined on the guide layer.
 * Those guides are currently interpreted as the limits of view splitters
 * 
 * This is the fastest way to experiment with the splitters supported on
 * the ViewPort.
 * 
 * @author hmalphettes
 * @author Intalio Inc
 */
public class GuideLayerWithSplitters extends GuideLayer {

    private FreeFormViewPortWithSplitters _freeFormWithSplitters;
    
    /**
     * @param owner
     */
    public GuideLayerWithSplitters(FreeFormViewPortWithSplitters owner) {
        _freeFormWithSplitters = owner;
    }

    @Override
    public void remove(IFigure child) {
        boolean doRecompute = getConstraints().containsKey(child);
        super.remove(child);
        if (doRecompute) {
            _freeFormWithSplitters.invalidateTree();
            _freeFormWithSplitters.computeVisibleAreas();
            _freeFormWithSplitters.repaint();
        }
    }

    @Override
    public void setConstraint(IFigure child, Object constraint) {
        super.setConstraint(child, constraint);
        if (!((Boolean)constraint).booleanValue()) {
            _freeFormWithSplitters.invalidateTree();
            _freeFormWithSplitters.computeVisibleAreas();
            _freeFormWithSplitters.repaint();
        }
    }
    
    
}
