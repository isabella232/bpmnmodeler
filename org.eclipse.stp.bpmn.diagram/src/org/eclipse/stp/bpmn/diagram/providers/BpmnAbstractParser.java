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

import java.text.MessageFormat;
import java.text.ParsePosition;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.stp.bpmn.diagram.part.BpmnDiagramEditorPlugin;

/**
 * @generated
 */
public abstract class BpmnAbstractParser implements IParser {

    /**
     * @generated
     */
    private String viewPattern;

    /**
     * @generated
     */
    private MessageFormat viewProcessor;

    /**
     * @generated
     */
    private String editPattern;

    /**
     * @generated
     */
    private MessageFormat editProcessor;

    /**
     * @generated
     */
    public String getViewPattern() {
        return viewPattern;
    }

    /**
     * @generated
     */
    protected MessageFormat getViewProcessor() {
        return viewProcessor;
    }

    /**
     * @generated
     */
    public void setViewPattern(String viewPattern) {
        this.viewPattern = viewPattern;
        viewProcessor = createViewProcessor(viewPattern);
    }

    /**
     * @generated
     */
    protected MessageFormat createViewProcessor(String viewPattern) {
        return new MessageFormat(viewPattern);
    }

    /**
     * @generated
     */
    public String getEditPattern() {
        return editPattern;
    }

    /**
     * @generated
     */
    protected MessageFormat getEditProcessor() {
        return editProcessor;
    }

    /**
     * @generated
     */
    public void setEditPattern(String editPattern) {
        this.editPattern = editPattern;
        editProcessor = createEditProcessor(editPattern);
    }

    /**
     * @generated
     */
    protected MessageFormat createEditProcessor(String editPattern) {
        return new MessageFormat(editPattern);
    }

    /**
     * @generated
     */
    public String getPrintString(IAdaptable adapter, int flags) {
        return getStringByPattern(adapter, flags, getViewPattern(),
                getViewProcessor());
    }

    /**
     * @generated
     */
    public String getEditString(IAdaptable adapter, int flags) {
        return getStringByPattern(adapter, flags, getEditPattern(),
                getEditProcessor());
    }

    /**
     * @generated
     */
    protected abstract String getStringByPattern(IAdaptable adapter, int flags,
            String pattern, MessageFormat processor);

    /**
     * @generated not
     */
    public IParserEditStatus isValidEditString(IAdaptable element,
            String editString) {
        ParsePosition pos = new ParsePosition(0);
        Object[] values = getEditProcessor().parse(editString, pos);
        if (values == null) {
            return new ParserEditStatus(BpmnDiagramEditorPlugin.ID,
                    IParserEditStatus.UNEDITABLE, 
                    BpmnDiagramMessages.bind(BpmnDiagramMessages.BpmnAbstractParser_invalid_input, pos.getErrorIndex())); //$NON-NLS-1$
        }
        return validateNewValues(values);
    }

    /**
     * @generated
     */
    protected IParserEditStatus validateNewValues(Object[] values) {
        return ParserEditStatus.EDITABLE_STATUS;
    }

    /**
     * @generated
     */
    public ICommand getParseCommand(IAdaptable adapter, String newString,
            int flags) {
        Object[] values = getEditProcessor().parse(newString,
                new ParsePosition(0));
        if (values == null
                || validateNewValues(values).getCode() != IParserEditStatus.EDITABLE) {
            return UnexecutableCommand.INSTANCE;
        }
        return getParseCommand(adapter, values);
    }

    /**
     * @generated
     */
    protected abstract ICommand getParseCommand(IAdaptable adapter,
            Object[] values);

    /**
     * @generated
     */
    public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
        return null;
    }

    /**
     * @generated
     */
    protected ICommand getModificationCommand(EObject element,
            EStructuralFeature feature, Object value) {
        value = getValidNewValue(feature, value);
        if (value instanceof InvalidValue) {
            return UnexecutableCommand.INSTANCE;
        }
        SetRequest request = new SetRequest(element, feature, value);
        return new SetValueCommand(request);
    }

    /**
     * @generated
     */
    protected Object getValidValue(EStructuralFeature feature, Object value) {
        EClassifier type = feature.getEType();
        if (type instanceof EDataType) {
            Class iClass = type.getInstanceClass();
            if (String.class.equals(iClass)) {
                if (value == null) {
                    value = ""; //$NON-NLS-1$
                }
            }
        }
        return value;
    }

    /**
     * @generated
     */
    protected Object getValidNewValue(EStructuralFeature feature, Object value) {
        EClassifier type = feature.getEType();
        if (type instanceof EDataType) {
            Class iClass = type.getInstanceClass();
            if (Boolean.TYPE.equals(iClass)) {
                if (value instanceof Boolean) {
                    // ok
                } else if (value instanceof String) {
                    value = Boolean.valueOf((String) value);
                } else {
                    value = new InvalidValue(
                            BpmnDiagramMessages.BpmnAbstractParser_boolean_expected);
                }
            } else if (Character.TYPE.equals(iClass)) {
                if (value instanceof Character) {
                    // ok
                } else if (value instanceof String) {
                    String s = (String) value;
                    if (s.length() == 0) {
                        value = null;
                    } else {
                        value = new Character(s.charAt(0));
                    }
                } else {
                    value = new InvalidValue(
                            BpmnDiagramMessages.BpmnAbstractParser_char_expected);
                }
            } else if (Byte.TYPE.equals(iClass)) {
                if (value instanceof Byte) {
                    // ok
                } else if (value instanceof Number) {
                    value = new Byte(((Number) value).byteValue());
                } else if (value instanceof String) {
                    String s = (String) value;
                    if (s.length() == 0) {
                        value = null;
                    } else {
                        try {
                            value = Byte.valueOf(s);
                        } catch (NumberFormatException nfe) {
                            value = new InvalidValue(
                                    BpmnDiagramMessages.BpmnAbstractParser_string_does_not_convert_to_byte);
                        }
                    }
                } else {
                    value = new InvalidValue(BpmnDiagramMessages.BpmnAbstractParser_byte_expected);
                }
            } else if (Short.TYPE.equals(iClass)) {
                if (value instanceof Short) {
                    // ok
                } else if (value instanceof Number) {
                    value = new Short(((Number) value).shortValue());
                } else if (value instanceof String) {
                    String s = (String) value;
                    if (s.length() == 0) {
                        value = null;
                    } else {
                        try {
                            value = Short.valueOf(s);
                        } catch (NumberFormatException nfe) {
                            value = new InvalidValue(
                                    BpmnDiagramMessages.BpmnAbstractParser_string_does_not_convert_to_short);
                        }
                    }
                } else {
                    value = new InvalidValue(BpmnDiagramMessages.BpmnAbstractParser_short_expected);
                }
            } else if (Integer.TYPE.equals(iClass)) {
                if (value instanceof Integer) {
                    // ok
                } else if (value instanceof Number) {
                    value = new Integer(((Number) value).intValue());
                } else if (value instanceof String) {
                    String s = (String) value;
                    if (s.length() == 0) {
                        value = null;
                    } else {
                        try {
                            value = Integer.valueOf(s);
                        } catch (NumberFormatException nfe) {
                            value = new InvalidValue(
                                    BpmnDiagramMessages.BpmnAbstractParser_string_does_not_convert_to_integer);
                        }
                    }
                } else {
                    value = new InvalidValue(
                            BpmnDiagramMessages.BpmnAbstractParser_integer_expected);
                }
            } else if (Long.TYPE.equals(iClass)) {
                if (value instanceof Long) {
                    // ok
                } else if (value instanceof Number) {
                    value = new Long(((Number) value).longValue());
                } else if (value instanceof String) {
                    String s = (String) value;
                    if (s.length() == 0) {
                        value = null;
                    } else {
                        try {
                            value = Long.valueOf(s);
                        } catch (NumberFormatException nfe) {
                            value = new InvalidValue(
                                    BpmnDiagramMessages.BpmnAbstractParser_string_does_not_convert_to_long);
                        }
                    }
                } else {
                    value = new InvalidValue(BpmnDiagramMessages.BpmnAbstractParser_long_expected);
                }
            } else if (Float.TYPE.equals(iClass)) {
                if (value instanceof Float) {
                    // ok
                } else if (value instanceof Number) {
                    value = new Float(((Number) value).floatValue());
                } else if (value instanceof String) {
                    String s = (String) value;
                    if (s.length() == 0) {
                        value = null;
                    } else {
                        try {
                            value = Float.valueOf(s);
                        } catch (NumberFormatException nfe) {
                            value = new InvalidValue(
                                    BpmnDiagramMessages.BpmnAbstractParser_string_does_not_convert_to_float);
                        }
                    }
                } else {
                    value = new InvalidValue(BpmnDiagramMessages.BpmnAbstractParser_float_expected);
                }
            } else if (Double.TYPE.equals(iClass)) {
                if (value instanceof Double) {
                    // ok
                } else if (value instanceof Number) {
                    value = new Double(((Number) value).doubleValue());
                } else if (value instanceof String) {
                    String s = (String) value;
                    if (s.length() == 0) {
                        value = null;
                    } else {
                        try {
                            value = Double.valueOf(s);
                        } catch (NumberFormatException nfe) {
                            value = new InvalidValue(
                                    BpmnDiagramMessages.BpmnAbstractParser_string_does_not_convert_to_double);
                        }
                    }
                } else {
                    value = new InvalidValue(BpmnDiagramMessages.BpmnAbstractParser_double_expected);
                }
            } else if (type instanceof EEnum) {
                if (value instanceof String) {
                    EEnumLiteral literal = ((EEnum) type)
                            .getEEnumLiteralByLiteral((String) value);
                    if (literal == null) {
                        value = new InvalidValue(BpmnDiagramMessages.bind(
                        		BpmnDiagramMessages.BpmnAbstractParser_unknown_literal, value)); 
                    } else {
                        value = literal.getInstance();
                    }
                } else {
                    value = new InvalidValue(BpmnDiagramMessages.BpmnAbstractParser_string_expected);
                }
            }
        }
        return value;
    }

    /**
     * @generated
     */
    protected class InvalidValue {

        private String description;

        public InvalidValue(String description) {
            this.description = description;
        }

        public String toString() {
            return description;
        }
    }
}
