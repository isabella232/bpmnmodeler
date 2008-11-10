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

package org.eclipse.stp.bpmn.commands;

import java.net.URL;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementType;
import org.eclipse.gmf.runtime.emf.type.core.IContainerDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.IMetamodelType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelper;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.stp.bpmn.ActivityType;

/**
 * @author hmalphettes
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ElementTypeEx extends ElementType implements IElementTypeEx,
        IAdaptable {

    /**
     * Factory method makes sure all the interfaces implemented are returned
     * 
     * @param elem
     * @param secondarySemanticHint The activityType.getLiteral().getLiteral()
     * @return
     */
    public static IElementTypeEx wrap(IElementType elem,
            String secondarySemanticHint) {
        if (elem instanceof IMetamodelType) {
            return new MetamodelTypeEx((IHintedType) elem,
                    secondarySemanticHint);
        } else if (elem instanceof ISpecializationType) {
            return new SpecializationTypeEx((IHintedType) elem,
                    secondarySemanticHint);
        }
        return new ElementTypeEx((IHintedType) elem, secondarySemanticHint);
    }

    /**
     * Factory method makes sure all the interfaces implemented are returned
     * 
     * @param elem
     * @param secondarySemanticHint
     * @param label an optional label to be shown in context menus
     * @return the element type with the additional data.
     */
    public static IElementTypeEx wrap(IElementType elem,
            String secondarySemanticHint, String label) {
        if (elem instanceof IMetamodelType) {
            return new MetamodelTypeEx((IHintedType) elem,
                    secondarySemanticHint, label);
        } else if (elem instanceof ISpecializationType) {
            return new SpecializationTypeEx((IHintedType) elem,
                    secondarySemanticHint, label);
        }
        return new ElementTypeEx((IHintedType) elem, secondarySemanticHint, label);
    }
    
    static class MetamodelTypeEx extends ElementTypeEx implements
            IMetamodelType {
        MetamodelTypeEx(IHintedType elementType, String secondarySemanticHint) {
            super(elementType, secondarySemanticHint);
        }
        MetamodelTypeEx(IHintedType elementType, String secondarySemanticHint, String label) {
            super(elementType, secondarySemanticHint, label);
        }
    }

    static class SpecializationTypeEx extends ElementTypeEx implements
            ISpecializationType {
        private final ISpecializationType _casted;

        SpecializationTypeEx(IHintedType elementType,
                String secondarySemanticHint) {
            super(elementType, secondarySemanticHint);
            _casted = (ISpecializationType) elementType;
        }

        SpecializationTypeEx(IHintedType elementType,
                String secondarySemanticHint, String label) {
            super(elementType, secondarySemanticHint, label);
            _casted = (ISpecializationType) elementType;
        }

        public IContainerDescriptor getEContainerDescriptor() {
            return _casted.getEContainerDescriptor();
        }

        public IEditHelperAdvice getEditHelperAdvice() {
            return _casted.getEditHelperAdvice();
        }

        public IElementMatcher getMatcher() {
            return _casted.getMatcher();
        }

        public IMetamodelType getMetamodelType() {
            return _casted.getMetamodelType();
        }

        public String[] getSpecializedTypeIds() {
            return _casted.getSpecializedTypeIds();
        }

        public IElementType[] getSpecializedTypes() {
            return _casted.getSpecializedTypes();
        }

        public boolean isSpecializationOf(IElementType type) {
            return _casted.isSpecializationOf(type);
        }
    }

    /**
     * The wrapped element type.
     */
    final IHintedType _wrapped;

    /**
     * The scondary semantic hint is used
     * to specify an additional information regarding
     * the element type that will influence the
     * creation of the shape accordingly.
     */
    private String _secondarySemanticHint;

    /**
     * an optional label to be used as the display name
     * of the secondary semantic hint
     */
    private String _label;
    
    protected ElementTypeEx(IHintedType elementType,
            String secondarySemanticHint) {
        super(elementType.getId(), elementType.getIconURL(), elementType
                .getDisplayName(), elementType.getEClass());
        _wrapped = elementType;
        _secondarySemanticHint = secondarySemanticHint;
    }
    
    protected ElementTypeEx(IHintedType elementType,
            String secondarySemanticHint, String displayName) {
        this(elementType, secondarySemanticHint);
        _label = displayName;
    }

    public String getSemanticHint() {
        return _wrapped.getSemanticHint();
    }

    public String getSecondarySemanticHint() {
        return _secondarySemanticHint;
    }

    @Override
    public String getDisplayName() {
        if (_label != null) {
            return _label;
        }
        if (_secondarySemanticHint != null) {
            ActivityType type = ActivityType.get(_secondarySemanticHint);
            if (type != null) {
                return type.getName();
            }
        }
        return super.getDisplayName() + " " + _secondarySemanticHint; //$NON-NLS-1$
    }

    @Override
    public URL getIconURL() {
        URL url = super.getIconURL();
        return url;
    }

    // these are overriden anyways otherwise it breaks.
    public IEditHelper getEditHelper() {
        return _wrapped.getEditHelper();
    }

    public IElementType[] getAllSuperTypes() {
        return _wrapped.getAllSuperTypes();
    }

    public ICommand getEditCommand(IEditCommandRequest request) {
        return _wrapped.getEditCommand(request);
    }
}
