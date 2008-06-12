/**
 ******************************************************************************
 * Copyright (c) 2007, Intalio Inc.
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

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.gmf.runtime.common.ui.util.WindowUtil;
import org.eclipse.gmf.runtime.diagram.ui.image.ImageFileFormat;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.stp.bpmn.diagram.BpmnDiagramMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.WizardExportResourcesPage;

/**
 * The page used to hold the destination fields. Does the essential of the work.
 * 
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class ExportImagePage extends WizardExportResourcesPage implements IExportImagePage {

    private Text folderText;
    private boolean folderValid;
    private String folder = ""; //$NON-NLS-1$
    private boolean fileNameValid = false;
    private Text fileNameText;
    private String fileName = "image"; //$NON-NLS-1$
    private Combo imageFormatCombo;
    private ImageFileFormat imageFormat = ImageFileFormat.JPG;

    public ExportImagePage(IStructuredSelection sel) {
        super(BpmnDiagramMessages.ExportImagePage_selectDiagramToExport, sel);
        setTitle(BpmnDiagramMessages.ExportImagePage_ExportAsImage);
        setDescription(BpmnDiagramMessages.ExportImagePage_text);
        
    }

    /**
     * utility method to create a composite widget
     * @param parent the parent widget
     * @param columns the number of columns in the grid layout for the new
     * composite.
     * @return the new composite widget
     */
    private Composite createComposite(Composite parent, int columns) {
        Composite composite = new Composite(parent, SWT.NONE);

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = columns;
        gridLayout.marginHeight = 0;
        gridLayout.marginWidth = 0;
        gridLayout.makeColumnsEqualWidth = false;

        GridData data =
            new GridData(
                GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_HORIZONTAL);

        composite.setLayoutData(data);
        composite.setLayout(gridLayout);

        return composite;
    }

    /**
     * Creates the Destination Group, the text representing the
     * folder to export to and the file name for the image.
     */
    @Override
    protected void createDestinationGroup(Composite parent) {
        Composite composite = createComposite(parent, 3);
        Label folderLabel = new Label(composite,SWT.NONE);
        folderLabel.setText(BpmnDiagramMessages.ExportImagePage_ExportFolder);
        folderText = new Text(composite, SWT.BORDER);
        folderText.setText(folder == null ? ""  : folder); //$NON-NLS-1$
        folderText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                validateFolderText();
            }
        });
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.widthHint = 250;
        folderText.setLayoutData(gridData);

        Button button = new Button(composite, SWT.PUSH);
        button.setText(BpmnDiagramMessages.ExportImagePage_BrowseButton);
        button.setLayoutData(WindowUtil.makeButtonData(button));
        button.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                handleBrowseButtonPressed();
            }
        });
        Label fileNameLabel = new Label(composite,SWT.NONE);
        fileNameLabel.setText(BpmnDiagramMessages.ExportImagePage_ImageFileName);
        fileNameText = new Text(composite, SWT.BORDER);
        fileNameText.setText(fileName + "." + imageFormat.getName().toLowerCase()); //$NON-NLS-1$
        fileNameText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                validateFileNameText();
            }
        });
        gridData =
            new GridData(
                GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
        gridData.widthHint = 250;
        fileNameText.setLayoutData(gridData);
    }

    /* (non-Javadoc)
     * @see org.eclipse.stp.bpmn.export.IExportImagePage#handleEvent(org.eclipse.swt.widgets.Event)
     */
    public void handleEvent(Event event) {
    }
    
    
    @SuppressWarnings("unchecked") //$NON-NLS-1$
    @Override
    protected List getTypesToExport() {
        List types = new LinkedList();
        types.add("bpmn_diagram"); //$NON-NLS-1$
        return types;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.stp.bpmn.export.IExportImagePage#getSelectedResources()
     */
    @SuppressWarnings("unchecked") //$NON-NLS-1$
    @Override
    public List getSelectedResources() {
        return super.getSelectedResources();
    }
    /**
     * The options group contains the image fomat combo box.
     */
    @Override
    protected void createOptionsGroupButtons(Group optionsGroup) {
        Composite composite = createComposite(optionsGroup, 2);
        Label label = new Label(composite,SWT.NONE);
        label.setText(BpmnDiagramMessages.ExportImagePage_ImageFormat);
        imageFormatCombo = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
        imageFormatCombo.setItems(getImageFormatItems());
        imageFormatCombo.setText(imageFormat.getName());
        imageFormatCombo.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                imageFormat =
                    ImageFileFormat.resolveImageFormat(
                        imageFormatCombo.getSelectionIndex());
                
                // update filename to reflect new format
                fileNameText.setText(fileName + "." + imageFormat.getName().toLowerCase()); //$NON-NLS-1$
                validateFileNameText();
            }
        });
        GridData gridData =
            new GridData(
                GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_HORIZONTAL);
        gridData.widthHint = 250;
        imageFormatCombo.setLayoutData(gridData);
    }
    
    /**
     * get the supported image formats from the enumerated type.
     * @return array of supported image formats.
     */
    private String[] getImageFormatItems() {
        String[] items = new String[ImageFileFormat.VALUES.length];
        for (int i = 0; i < ImageFileFormat.VALUES.length; i++) {
            items[i] = ImageFileFormat.VALUES[i].getName();
        }
        return items;
    }
    /**
     * handle a browse button pressed selection.
     */
    private void handleBrowseButtonPressed() {
        DirectoryDialog dialog =
            new DirectoryDialog(Display.getCurrent().getActiveShell());
        dialog.setMessage(BpmnDiagramMessages.ExportImagePage_ExportImage);
        dialog.setText(BpmnDiagramMessages.ExportImagePage_FolderToExportedImage);

        String dirName = folderText.getText();
        if (!dirName.equals("")) { //$NON-NLS-1$
            File path = new File(dirName);
            if (path.exists())
                dialog.setFilterPath(new Path(dirName).toOSString());
        }

        String selectedDirectory = dialog.open();
        if (selectedDirectory != null) {
            folderText.setText(selectedDirectory);
        }
    }
    
    /**
     * validate the folder text field.
     */
    private void validateFolderText() {

        if (folderText.getText().equals("")) { //$NON-NLS-1$
            setMessage(null);
            folderValid = false;
            return;
        }

        IPath path = new Path(""); //$NON-NLS-1$
        if (!path.isValidPath(folderText.getText())) {
            setErrorMessage(BpmnDiagramMessages.ExportImagePage_InvalidFolder);
            folderValid = false;
            return;
        }

        File file = new File(folderText.getText());
        if (!file.exists()) {
            setErrorMessage(BpmnDiagramMessages.ExportImagePage_FolderDoesNotExist);
            folderValid = false;
            return;
        }

        folderValid = true;
        folder = folderText.getText();
        if (fileNameValid) {
            setMessage(null);
        } else {
            validateFileNameText();
        }
    }
    
    /**
     * validate the file name text field.
     */
    private void validateFileNameText() {
        IStatus nameStatus =
            ResourcesPlugin.getWorkspace().validateName(
                fileNameText.getText(),
                IResource.FILE);

        if (!nameStatus.isOK()) {
            setErrorMessage(nameStatus.getMessage());
            fileNameValid = false;
            return;
        }

        fileNameValid = true;
        
        IPath filePath = (new Path(fileNameText.getText())).removeFileExtension();
        fileName = filePath.toString();
        if (folderValid) {
            setMessage(null);
        } else {
            validateFolderText();
        }
    }
    
    /**
     * Make sure that there is only one resource selected,
     * with a bpmn_diagram extension.
     */
    @Override
    protected boolean validateSourceGroup() {
        if (getSelectedResources().isEmpty()) {
            setErrorMessage(BpmnDiagramMessages.ExportImagePage_NeedToSelectOneDiagram);
            return false;
        }
        for (Object res : getSelectedResources()) {
            String ext = ((IResource) res).getFileExtension();
            if (!"bpmn_diagram".equals(ext)) { //$NON-NLS-1$
                setErrorMessage(BpmnDiagramMessages.ExportImagePage_SelectOneDiagramFile);
                return false;
            }
        }
        
        if (getSelectedResources().size() > 1) {
            setErrorMessage(BpmnDiagramMessages.ExportImagePage_cannot_select_more_than_one_diagram);
            return false;
        }
        setMessage(null);
        return true;
    }
    /* (non-Javadoc)
     * @see org.eclipse.stp.bpmn.export.IExportImagePage#getDestinationPath()
     */
    public IPath getDestinationPath() {
        IPath path = new Path(folder);
        String fnWithExtension = fileName +  "." +  //$NON-NLS-1$
            imageFormat.getName().toLowerCase();
        
        return path.append(fnWithExtension);
    }

    /* (non-Javadoc)
     * @see org.eclipse.stp.bpmn.export.IExportImagePage#getImageFormat()
     */
    public ImageFileFormat getImageFormat() {
        return imageFormat;
    }
    
}