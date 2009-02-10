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

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide.util.IDEEditorFileCreator;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.util.DiagramFileCreator;

/**
 * @generated
 */
public class BpmnDiagramFileCreator extends IDEEditorFileCreator {

    /**
     * @generated
     */
    private static BpmnDiagramFileCreator INSTANCE = new BpmnDiagramFileCreator();

    /**
     * @generated
     */
    public static DiagramFileCreator getInstance() {
        return INSTANCE;
    }

    /**
     * @generated
     */
    public String getExtension() {
        return ".bpmn_diagram"; //$NON-NLS-1$
    }

    /**
     * @generated
     */
    public String getUniqueFileName(IPath containerPath, String fileName) {
        int nFileNumber = 1;
        fileName = removeExtensionFromFileName(fileName);
        String newFileName = fileName;

        IPath diagramFilePath = containerPath
                .append(appendExtensionToFileName(newFileName));
        IPath modelFilePath = containerPath
                .append(appendExtensionToModelFileName(newFileName));
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

        while (workspaceRoot.exists(diagramFilePath)
                || workspaceRoot.exists(modelFilePath)) {
            nFileNumber++;
            newFileName = fileName + nFileNumber;
            diagramFilePath = containerPath
                    .append(appendExtensionToFileName(newFileName));
            modelFilePath = containerPath
                    .append(appendExtensionToModelFileName(newFileName));
        }
        return newFileName;
    }

    /**
     * @generated
     */
    private String removeExtensionFromFileName(String fileName) {
        if (fileName.endsWith(getExtension())) {
            return fileName.substring(0, fileName.length()
                    - getExtension().length());
        }
        return fileName;
    }

    /**
     * @generated
     */
    private String appendExtensionToModelFileName(String fileName) {
        return fileName + ".bpmn"; //$NON-NLS-1$
    }

}