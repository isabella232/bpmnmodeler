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
package org.eclipse.stp.bpmn.diagram.providers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Locator;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.handles.HandleBounds;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.DecorationEditPolicy.DecoratorTarget;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditDomain;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.CreateDecoratorsOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoration;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecorator;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget.Direction;
import org.eclipse.gmf.runtime.draw2d.ui.internal.figures.ImageFigureEx;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.stp.bpmn.diagram.edit.parts.BpmnDiagramEditPart;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramPreferenceInitializer;
import org.eclipse.stp.bpmn.diagram.part.BpmnVisualIDRegistry;
import org.eclipse.stp.bpmn.dnd.IEAnnotationDecorator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

/**
 * Decoration in charge of showing icons and tooltips on views 
 * that are associated with modele elements that contain annotations.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BpmnEAnnotationDecoratorProvider extends AbstractProvider 
	implements IDecoratorProvider {

	static class DecoratorListener extends EContentAdapter {

		private EAnnotationDecorator decorator;
		
		public DecoratorListener(EAnnotationDecorator dec) {
			decorator = dec;
		}
        
		public void notifyChanged(Notification notification) {
		    if (notification.getNotifier() instanceof EAnnotation) {
		        super.notifyChanged(notification);
            }
            if (notification.getNotifier() instanceof EModelElement) {
                if (notification.getFeatureID(EModelElement.class) == 
                    EcorePackage.EMODEL_ELEMENT__EANNOTATIONS) {
                    super.notifyChanged(notification);
                    refresh();
                } else if (notification.getEventType() == Notification.NO_FEATURE_ID) {
                    refresh();
                }
            } else if (notification.getNotifier() instanceof EAnnotation) {
                refresh();
            } else if (notification.getNotifier() instanceof BasicEMap.Entry) {
                refresh();
            }
        }
            

		private void refresh() {
		    if (decorator.getView().eResource() == null) {
		        return;
		    }
            final TransactionalEditingDomain domain = 
                TransactionUtil.getEditingDomain(decorator.getView().getDiagram());
		    PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
		        public void run() {
		                try {
                            if (domain != null) {
                                domain.runExclusive(new Runnable() {
                                    public void run() {
                                        
                                        decorator.refresh();
                                    }
                                } );
                            }
                        } catch (InterruptedException e) {
                            // only thrown when cancelled by user.
                        }
		        }
		    });
		}
	}
	/**
	 * Constant for the size of a decoration.
	 * Should be changed, recording the size of the decorations.
	 */
	private static final int DECORATION_SIZE = 17;
	
	/**
	 * the key for the decorator.
	 */
	private static final String KEY = "eannotationdecoration"; //$NON-NLS-1$
	
	/**
	 * Creates the decorator for a decorator target, 
	 * which is actually an edit part.
	 */
	public void createDecorators(IDecoratorTarget decoratorTarget) {
		EditPart editPart = (EditPart) decoratorTarget.getAdapter(EditPart.class);
		if (editPart instanceof GraphicalEditPart
				|| editPart instanceof AbstractConnectionEditPart) {
			Object model = editPart.getModel();
			if ((model instanceof View)) {
				View view = (View) model;
				if (!(view instanceof Edge) && !view.isSetElement()) {
					return;
				}
			}
			EditDomain ed = editPart.getViewer().getEditDomain();
			if (!(ed instanceof DiagramEditDomain)) {
				return;
			}
//			if (((DiagramEditDomain) ed).getEditorPart() 
//					instanceof BpmnDiagramEditor) {
				decoratorTarget.installDecorator(
                        KEY, new EAnnotationDecorator(decoratorTarget));
//			}
		}

	}

	/**
	 * Provides for a CreatesDecoratorsOperation, if the view is part of the
//	 * Bpmn model.
	 */
	public boolean provides(IOperation operation) {
		if (!(operation instanceof CreateDecoratorsOperation)) {
			return false;
		}

		IDecoratorTarget decoratorTarget = ((CreateDecoratorsOperation) 
				operation).getDecoratorTarget();
		View view = (View) decoratorTarget.getAdapter(View.class);
		return view != null
				&& BpmnDiagramEditPart.MODEL_ID.equals(BpmnVisualIDRegistry
						.getModelID(view));
	}

	/**
	 * Handles the decorator, attached to an edit part.
	 * Called by the decoration edit policy.
	 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
	 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
	 */
	public class EAnnotationDecorator implements IDecorator {

		/**
		 * a map of images to avoid recreating them all the time
		 * and be able to dispose them when deactivating.
		 */
		private HashMap<ImageDescriptor, Image> images = 
			new HashMap<ImageDescriptor, Image>();
		
		private DecoratorListener decoratorListener;
		
		/** the object to be decorated */
		private DecoratorTarget decoratorTarget;
		
		private IFigure currentDecoration;
		
		private Map<IDecoration,Direction> decorations = 
			new HashMap<IDecoration,Direction>();

		/**
		 * Default constructor.
		 * @param decoratorTarget
		 */
		public EAnnotationDecorator(IDecoratorTarget decoratorTarget) {
			this.decoratorTarget = (DecoratorTarget) decoratorTarget;
		}

		/**
		 * Activates the decorator by adding a listener to the model element
		 * behind the view.
		 */
		@SuppressWarnings("unchecked") //$NON-NLS-1$
		public void activate() {
			View view = (View) decoratorTarget.getAdapter(View.class);
			if (view == null || view.eResource() == null) {
				return;
			}
			if (view.getElement() instanceof EModelElement) {
				decoratorListener = new DecoratorListener(this);
                ((EModelElement) view.getElement()).eAdapters().add(decoratorListener);
				for (Object ea : ((EModelElement) view.getElement()).
						getEAnnotations()) {
					((EAnnotation) ea).eAdapters().add(decoratorListener);
				}
			}
		}

		/**
		 * Refreshes the decoration, 
		 * called by the listener over the model element.
		 * It adds decorations and tooltips to the shape.
		 * 
		 * For now it has an issue when several icons are on one shape.
		 * 
		 * @author atoulme
		 */
		public void refresh() {
			removeDecorations();

			if (BpmnDiagramEditorPlugin.getInstance().getPreferenceStore().
					getBoolean(BpmnDiagramPreferenceInitializer.FILTER_DECORATIONS)) {
				return;
			}
			
			//when the objects are not in the diagram anymore, in fact
			//the entire decorator should have been removed. (probably)
			//this is just a workaround.
			View view = (View) decoratorTarget.getAdapter(View.class);
			EditPart editPart =
                (EditPart) decoratorTarget.getAdapter(EditPart.class);
			if (view == null || view.eResource() == null) {
				return;
			} else if (view.getElement() == null ||
                    view.getElement().eResource() == null) {
			    return;         
            } else if (editPart == null || editPart.getRoot() == null) {
                return;
            }
			
			if (view.getElement() instanceof EModelElement) {
				EModelElement elt = (EModelElement) view.getElement();
				 decorations = new HashMap<IDecoration,Direction>();
                 
				for (Object objAnn : elt.getEAnnotations()) {
					EAnnotation ann = (EAnnotation) objAnn;
					if (BpmnDecorationFilterService.getInstance().
							isSourceFiltered(ann.getSource())) {
						continue;
					}
					IEAnnotationDecorator decorator = BpmnDiagramEditorPlugin.
						getInstance().getEAnnotationDecorator(ann.getSource());
					// there might be no decorator for the annotation
					if (decorator != null) {
						if (editPart instanceof GraphicalEditPart) {
							ImageDescriptor descriptor = decorator
								.getImageDescriptor(editPart, elt, ann);
							// there might be no descriptor for the decoration as well.
							if (descriptor != null) {
								Image image = images.get(descriptor);
								if (image == null) {
									image = descriptor.createImage();
									images.put(descriptor, image);
								}
								IFigure parentFigure = ((GraphicalEditPart)
										decoratorTarget.
										getAdapter(GraphicalEditPart.class))
										.getFigure();
								if (parentFigure == null) {
									parentFigure = ((GraphicalEditPart)
                                       ((GraphicalEditPart)decoratorTarget
											.getAdapter(GraphicalEditPart.class))
											.getRoot()).getFigure();
								}
								
								if (view instanceof Edge) {
									ImageFigureEx figure = new ImageFigureEx();
									figure.setImage(image);
									parentFigure.add(figure);
									figure.setSize(image.getBounds().width,
											image.getBounds().height);
									currentDecoration = (IFigure) decoratorTarget.
									addConnectionDecoration(figure, 
											55 + 7 * elt.getEAnnotations().indexOf(ann), 
											false);
									decorations.put((IDecoration) currentDecoration, 
											Direction.CENTER);
								} else {
								    
									ImageFigureEx figure = new ImageFigureEx(image);
									parentFigure.add(figure);
									figure.setSize(image.getBounds().width,
											image.getBounds().height);
									Direction direction = 
										decorator.getDirection(editPart,elt,ann);
									IDecoration deco = decoratorTarget
									    .addDecoration(figure,
											new StickyToBorderLocator(
													parentFigure,
													getPositionConstant(direction),
															getOffset(direction)),
															false);
//									IDecoration deco = decoratorTarget.
//									addShapeDecoration(image, Direction.NORTH_EAST, 
//											-1, true);
									// TODO add better support for multiple icons
									// anything better here is welcome
									// since for now the icons 
									// are displayed oriented to the center.

									currentDecoration = (IFigure) 
										(deco instanceof IFigure ? deco : null);
									
									decorations.put((IDecoration) currentDecoration, 
											Direction.NORTH_EAST);
								}

								if (currentDecoration != null) {
									// callback to add a tooltip on the figure
									currentDecoration.
									setToolTip(decorator.
											getToolTip(editPart, elt, ann));
								}
							}
						}
					}
				}
			}
		}

		private int getOffset(Direction dir) {
			int i = 0;
			for (IDecoration deco : decorations.keySet()) {
				if (decorations.get(deco).equals(dir)) {
					i++;
				}
			}
			return i * DECORATION_SIZE;
		}
		/**
		 * Borrowed from DecorationEditPolicy
		 * Converts the direction to an int as defined in PositionConstant.
		 * 
		 * @param direction
		 * @return the int as defined in PositionConstant
		 */
		private int getPositionConstant(Direction direction) {
		    if (direction == Direction.CENTER) {
				return PositionConstants.CENTER;
			} else if (direction == Direction.NORTH) {
				return PositionConstants.NORTH;
			} else if (direction == Direction.SOUTH) {
				return PositionConstants.SOUTH;
			} else if (direction == Direction.WEST) {
				return PositionConstants.WEST;
			} else if (direction == Direction.EAST) {
				return PositionConstants.EAST;
			} else if (direction == Direction.NORTH_EAST) {
				return PositionConstants.NORTH_EAST;
			} else if (direction == Direction.NORTH_WEST) {
				return PositionConstants.NORTH_WEST;
			} else if (direction == Direction.SOUTH_EAST) {
				return PositionConstants.SOUTH_EAST;
			} else if (direction == Direction.SOUTH_WEST) {
				return PositionConstants.SOUTH_WEST;
			}
			return PositionConstants.CENTER;
		}

		private void removeDecorations() {
			for (IDecoration deco : decorations.keySet()) {
				if (deco != null) {
					decoratorTarget.removeDecoration(deco);
//					deco = null;
				}
			}
			decorations.clear();
		}

		/* (non-Javadoc)
		 * @see org.eclipse.gmf.runtime.diagram.ui.services.decorator.AbstractDecorator#deactivate()
		 */
		public void deactivate() {
			removeDecorations();
			// dispose the images and clear the map.
			for (Image image : images.values()) {
				image.dispose();
			}
			images.clear();
			
			View view = (View) decoratorTarget.getAdapter(View.class);
			if (view == null || view.eResource() == null) {
				return;
			}
			if (view.getElement() instanceof EModelElement) {
				view.getElement().eAdapters().remove(decoratorListener);
			}
		}

		public View getView() {
			View view = (View) decoratorTarget.getAdapter(View.class);
			return view;
		}
	}
	
	/**
	 * Locator that sticks to the border it is attached to.
	 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
	 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
	 */
	private class StickyToBorderLocator implements Locator {
		

		/** the offset to add to the location of the object */
		private int offset;

		/** the direction */
		protected int direction;

		/** the parent figure */
		protected IFigure reference;

		/**
		 * Constructor for <code>StickyToBorderLocator</code>.
		 * 
		 * @param reference the parent figure
		 * @param direction A direction to place the figure relative to the reference
		 * figure as defined in {@link PositionConstants}
		 * @param margin The margin is the space between the reference figure's edge 
		 * and the figure.  A positive margin will place the figure outside the
		 * reference figure, a negative margin will place the figure inside the 
		 * reference figure.
		 */
		public StickyToBorderLocator(
			IFigure reference, int direction, int offset) {
				
			this.reference = reference;
			this.direction = direction;
			this.offset = offset;
		}

		/**
		 * Puts the figure either inside or outside the parent edge (where the edge 
		 * is the same as where the resize/move handles would be placed),
		 * identified by the direction, with a margin.
		 * 
		 * @see org.eclipse.draw2d.Locator#relocate(org.eclipse.draw2d.IFigure)
		 */
		public void relocate(IFigure target) {
			Rectangle bounds =
				reference instanceof HandleBounds
					? ((HandleBounds) reference).getHandleBounds().getCopy()
					: reference.getBounds().getCopy();	

			reference.translateToAbsolute(bounds);
			target.translateToRelative(bounds);

			int width = target.getBounds().width;
			int halfWidth = width / 2;

			int height = target.getBounds().height;
			int halfHeight = height / 2;

			if (direction == PositionConstants.CENTER) {

				Dimension shift = new Dimension(-halfWidth, -halfHeight);
				target.setLocation(bounds.getCenter().getTranslated(shift));

			} else if (offset < 0) {
			    
				if (direction == PositionConstants.NORTH_WEST) {

					Dimension shift = new Dimension(2 - offset, 2);
					target.setLocation(bounds.getTopLeft().getTranslated(shift));

				} else if (direction == PositionConstants.NORTH) {

					Dimension shift = new Dimension(-offset, -halfWidth);
					target.setLocation(bounds.getTop().getTranslated(shift));

				} else if (direction == PositionConstants.NORTH_EAST) {

					Dimension shift = new Dimension(offset, 0);
					target.setLocation(bounds.getTopRight().getTranslated(shift));

				} else if (direction == PositionConstants.SOUTH_WEST) {

					Dimension shift = new Dimension(0, offset);
					target.setLocation(bounds.getBottomLeft().getTranslated(shift));

				} else if (direction == PositionConstants.SOUTH) {

					Dimension shift = new Dimension(-halfWidth, -(height + -offset));
					target.setLocation(bounds.getBottom().getTranslated(shift));

				} else if (direction == PositionConstants.SOUTH_EAST) {

					Dimension shift = new Dimension(-(width + -offset),
						-(height));
					target
						.setLocation(bounds.getBottomRight().getTranslated(shift));

				} else if (direction == PositionConstants.WEST) {

					Dimension shift = new Dimension(-offset, -halfHeight);
					target.setLocation(bounds.getLeft().getTranslated(shift));

				} else if (direction == PositionConstants.EAST) {

					Dimension shift = new Dimension(-(width + -offset), -halfHeight);
					target.setLocation(bounds.getRight().getTranslated(shift));

				}
			} else {

				if (direction == PositionConstants.NORTH_WEST) {

					Dimension shift = new Dimension(2 + offset, 2);
					target.setLocation(bounds.getTopLeft().getTranslated(shift));

				} else if (direction == PositionConstants.NORTH) {

					Dimension shift =
						new Dimension(-halfWidth, - (height + offset));
					target.setLocation(bounds.getTop().getTranslated(shift));

				} else if (direction == PositionConstants.NORTH_EAST) {

					Dimension shift = new Dimension(2, offset + 2);
					target.setLocation(bounds.getTopRight().getTranslated(shift));

				} else if (direction == PositionConstants.SOUTH_WEST) {

					Dimension shift = new Dimension(offset + 2, - DECORATION_SIZE - 2);
					target.setLocation(bounds.getBottomLeft().getTranslated(shift));

				} else if (direction == PositionConstants.SOUTH) {

					Dimension shift = new Dimension(-halfWidth, 0);
					target.setLocation(bounds.getBottom().getTranslated(shift));

				} else if (direction == PositionConstants.SOUTH_EAST) {

					Dimension shift = new Dimension(offset + 2, 2);
					target.setLocation(
						bounds.getBottomRight().getTranslated(shift));

				} else if (direction == PositionConstants.WEST) {

					Dimension shift = new Dimension(offset + 2, -halfHeight);
					target.setLocation(bounds.getLeft().getTranslated(shift));

				} else if (direction == PositionConstants.EAST) {

					Dimension shift = new Dimension(offset, -halfHeight);
					target.setLocation(bounds.getRight().getTranslated(shift));

				}
			}
		}
	}
	
}
