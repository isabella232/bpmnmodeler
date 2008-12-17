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

import java.lang.ref.WeakReference;

import org.eclipse.draw2d.DefaultRangeModel;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.AutoexposeHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.ViewportAutoexposeHelper;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.render.editparts.RenderedDiagramRootEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.AbstractEditPartProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.CreateGraphicEditPartOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.CreateRootEditPartOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.editpart.IEditPartOperation;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnDiagramEditPart;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnEditPartFactory;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.figures.splitviewsupport.FreeFormViewPortWithSplitters;
import org.eclipse.ui.PlatformUI;

/**
 * @generated
 */
public class BpmnEditPartProvider extends AbstractEditPartProvider {

    /**
     * @generated
     */
    private EditPartFactory factory;

    /**
     * @generated
     */
    private boolean allowCaching;

    /**
     * @generated
     */
    private WeakReference cachedPart;

    /**
     * @generated
     */
    private WeakReference cachedView;

    /**
     * @generated
     */
    public BpmnEditPartProvider() {
        setFactory(new BpmnEditPartFactory());
        setAllowCaching(true);
    }

    /**
     * @generated
     */
    public final EditPartFactory getFactory() {
        return factory;
    }

    /**
     * @generated
     */
    protected void setFactory(EditPartFactory factory) {
        this.factory = factory;
    }

    /**
     * @generated
     */
    public final boolean isAllowCaching() {
        return allowCaching;
    }

    /**
     * @generated
     */
    protected synchronized void setAllowCaching(boolean allowCaching) {
        this.allowCaching = allowCaching;
        if (!allowCaching) {
            cachedPart = null;
            cachedView = null;
        }
    }

    /**
     * @generated
     */
    protected IGraphicalEditPart createEditPart(View view) {
        EditPart part = factory.createEditPart(null, view);
        if (part instanceof IGraphicalEditPart) {
            return (IGraphicalEditPart) part;
        }
        return null;
    }

    /**
     * @generated
     */
    protected IGraphicalEditPart getCachedPart(View view) {
        if (cachedView != null && cachedView.get() == view) {
            return (IGraphicalEditPart) cachedPart.get();
        }
        return null;
    }

    /**
     * @generated
     */
    public synchronized IGraphicalEditPart createGraphicEditPart(View view) {
        if (isAllowCaching()) {
            IGraphicalEditPart part = getCachedPart(view);
            cachedPart = null;
            cachedView = null;
            if (part != null) {
                return part;
            }
        }
        return createEditPart(view);
    }

    /**
     * @generated
     */
    public synchronized boolean providesGen(IOperation operation) {
        
        if (operation instanceof CreateGraphicEditPartOperation) {
            View view = ((IEditPartOperation) operation).getView();
            if (!BpmnDiagramEditPart.MODEL_ID.equals(BpmnVisualIDRegistry
                    .getModelID(view))) {
                return false;
            }
            if (isAllowCaching() && getCachedPart(view) != null) {
                return true;
            }
            IGraphicalEditPart part = createEditPart(view);
            if (part != null) {
                if (isAllowCaching()) {
                    cachedPart = new WeakReference(part);
                    cachedView = new WeakReference(view);
                }
                return true;
            }
        }
        return false;
    }
    
    
    /**
     * @generated NOT Added the creation of the root edit part to be able to customize the zone
     * where auto-scroll is executed
     * @see org.eclipse.gmf.runtime.common.core.service.IProvider#provides(org.eclipse.gmf.runtime.common.core.service.IOperation)
     */
    public boolean provides(IOperation operation) {
        if (operation instanceof CreateRootEditPartOperation) {
            return true;
        }
        return providesGen(operation);
    }

    /** defines the range where autoscroll is active inside a viewer */
    private static final Insets EXPOSE_THRESHOLD = new Insets(24);
    
    /*
     * @generated NOT Added the creation of the root edit part to be able to customize the zone
     * where auto-scroll is executed
     * @see org.eclipse.gmf.runtime.diagram.ui.internal.services.editpart.IEditPartProvider#createDiagramRootEditPart()
     */
    public RootEditPart createRootEditPart(Diagram diagram) {
        
//        MapModeTypes.DEFAULT_MM = EXTENDED_HI_METRICS_MM;
        
        //return new RenderedDiagramRootEditPart(diagram.getMeasurementUnit());
        RenderedDiagramRootEditPart rp =
            new RenderedDiagramRootEditPart(diagram.getMeasurementUnit()) {
            /**
             * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
             */
            public Object getAdapter(Class adapter) {
                if (adapter == AutoexposeHelper.class) {
                    return new MyViewportAutoexposeHelper(this, EXPOSE_THRESHOLD);
                }
                return super.getAdapter(adapter);
            }

            /**
             * Overridden to use a view port aware of splitters in the view.
             */
            @Override
            protected IFigure createFigure() {
                FreeformViewport superRes = (FreeformViewport) super.createFigure();
                FreeformViewport viewport = new FreeFormViewPortWithSplitters();
                viewport.setContents(superRes.getContents());
                return viewport;
            }

            /**
             * Overrides so that it does not crash when executed outside of the
             * PlatformUI's workbench.
             */
			@Override
			protected ScalableFreeformLayeredPane createScalableFreeformLayeredPane() {
				if (PlatformUI.isWorkbenchRunning()) {
					return super.createScalableFreeformLayeredPane();
				} else {
					return new ScalableFreeformLayeredPane();
				}
			}
            
            
            
        };
        //Something probably goes wrong with our usage of GEF-draw2d.
        //it seems that when drag is in progress,
        //the DefaultRangeModel has an extend very close or equal to the max
        //value which litterally caps the auto-scroll to 0 or almost nothing.
        //this is an ugly but very safe way of ensuring that it does not happen.
        //Please let us know if you know better.
        final Viewport vp = findViewport(rp);
        vp.setHorizontalRangeModel(new DefaultRangeModel() {
            public void setAll(int min, int ext, int max) {
                //make sure the max is always bigger than the client area:
                int x = vp.getClientArea().width + vp.getClientArea().x;
                
                if (Math.abs(max-x) < 2*EXPOSE_THRESHOLD.right) {
                    super.setAll(min, ext, x + 2*EXPOSE_THRESHOLD.right);
                } else {
                    super.setAll(min, ext, max);
                }
            }
        });
        
        return rp;
        
    }
    protected Viewport findViewport(GraphicalEditPart part) {
        IFigure figure = null;
        Viewport port = null;
        do {
            if (figure == null)
                figure = part.getContentPane();
            else
                figure = figure.getParent();
            if (figure instanceof Viewport) {
                port = (Viewport) figure;
                break;
            }
        } while (figure != part.getFigure() && figure != null);
        return port;
    }    
    private static class MyViewportAutoexposeHelper extends ViewportAutoexposeHelper {
        private Insets _threshold;
        private long _lastStepTime;
        
        
        /**
         * Constructs a new helper on the given GraphicalEditPart. The editpart must have a
         * <code>Viewport</code> somewhere between its <i>contentsPane</i> and its <i>figure</i>
         * inclusively.
         * @param owner the GraphicalEditPart that owns the Viewport
         * @param threshold the Expose Threshold to use when determing whether or not a scroll
         * should occur.
         */
        public MyViewportAutoexposeHelper(GraphicalEditPart owner, Insets threshold) {
            super(owner, threshold);
            _threshold = threshold;
        }
        /**
         * Returns <code>true</code> if the given point is inside the viewport, but near its edge.
         * @see org.eclipse.gef.AutoexposeHelper#detect(org.eclipse.draw2d.geometry.Point)
         */
        public boolean detect(Point where) {
           // boolean res = super.detect(where);
            _lastStepTime = 0;
            Viewport port = findViewport(owner);
            Rectangle rect = Rectangle.SINGLETON;
            port.getClientArea(rect);
            port.translateToParent(rect);
            port.translateToAbsolute(rect);

            Rectangle biggerRect = rect.getCopy();
            biggerRect.expand(_threshold);
            if (!biggerRect.contains(where)) {
                return false;
            }
            rect.crop(_threshold);
            if (rect.contains(where)) {
                return false;
            }
            return true;
        }
        /**
         * Slightly different than the orginal class:
         * the offset is computed according to how far the mouse is from the cropped client area.
         * It is possible to go out of the client area too.
         * 
         * @see org.eclipse.gef.AutoexposeHelper#step(org.eclipse.draw2d.geometry.Point)
         */
        public boolean step(Point where) {
            long wait = System.currentTimeMillis() - _lastStepTime;
            if (wait < 40 && wait > 0) {
                //brr is there no better solution?
                //this is as bad as the original MyViewportAutoexposeHelper
                return true;
            }
            _lastStepTime = System.currentTimeMillis();
            Viewport port = findViewport(owner);
            Rectangle rect = Rectangle.SINGLETON;
            port.getClientArea(rect);
            port.translateToParent(rect);
            port.translateToAbsolute(rect);
            
            
            Rectangle biggerRect = rect.getCopy();
            biggerRect.expand(_threshold);
            if (!biggerRect.contains(where)) {
                return false;
            }
            rect.crop(_threshold);
            if (rect.contains(where)) {
                return false;
            }
            // set scroll offset (speed factor)
            Point loc = port.getViewLocation();
            
            if (where.x < rect.x) {
                loc.x -= rect.x - where.x;
            } else if (where.x > rect.x + rect.width ) {
                loc.x += + 2 * (where.x - (rect.x + rect.width));
            }
            if (where.y < rect.y) {
                loc.y -= rect.y - where.y;
            } else if (where.y > rect.y + rect.height ) {
                loc.y +=  2 * (where.y - (rect.y + rect.height));
            }
            port.setViewLocation(loc);
            return true;
        }

    }
    
    

        
}