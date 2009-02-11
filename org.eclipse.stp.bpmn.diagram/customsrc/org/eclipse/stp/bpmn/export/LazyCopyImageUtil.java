/**
 * Copyright (C) 2000-2006, Intalio Inc.
 *
 * The program(s) herein may be used and/or copied only with the
 * written permission of Intalio Inc. or in accordance with the terms
 * and conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *
 * Dates       		 Author              Changes
 * Dec 21, 2006      Antoine Toulm&eacute;   Creation
 */
package org.eclipse.stp.bpmn.export;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gmf.runtime.common.core.util.Trace;
import org.eclipse.gmf.runtime.common.ui.services.editor.EditorService;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.image.ImageFileFormat;
import org.eclipse.gmf.runtime.diagram.ui.image.PartPositionInfo;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.gmf.runtime.diagram.ui.render.clipboard.DiagramGenerator;
import org.eclipse.gmf.runtime.diagram.ui.render.clipboard.DiagramSVGGenerator;
import org.eclipse.gmf.runtime.diagram.ui.render.internal.DiagramUIRenderPlugin;
import org.eclipse.gmf.runtime.diagram.ui.render.util.CopyToImageUtil;
import org.eclipse.gmf.runtime.diagram.ui.util.DiagramEditorUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Shell;

/**
 * This extends the CopyImageUtil class
 * so that if the path given as a parameter is null,
 * it keeps the input stream in memory and may be retrieved.
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm&eacute;</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class LazyCopyImageUtil extends CopyToImageUtil {

	private ByteArrayOutputStream _stream = new ByteArrayOutputStream();


	@Override
	protected void saveSVGToFile(IPath destination,
			DiagramSVGGenerator generator, IProgressMonitor monitor)
	throws CoreException {
		if (destination != null) {
			super.saveSVGToFile(destination, generator, monitor);
		} else {
			// populate the ByteArrayStream if there is one.
			if (_stream != null) {
				generator.stream(_stream);
			}
		}
	}


	/**
	 * @return the _stream
	 */
	public ByteArrayOutputStream getStream() {
		return _stream;
	}


	/**
	 * @param stream the stream to set
	 */
	public void setStream(ByteArrayOutputStream stream) {
		_stream = stream;
	}

    /**
     * We are trying t put the contents of the image into the stream
     * if the destination path happens to be null
     */
    @Override
    protected void saveToFile(IPath destination, 
            Image image, 
            ImageFileFormat imageFormat, 
            IProgressMonitor monitor) throws CoreException {
        if (destination != null) {
            super.saveToFile(destination, image, imageFormat, monitor);
        } else {
            
            ImageData imageData = image.getImageData();
            
            if (imageFormat.equals(ImageFileFormat.GIF))
                imageData = createImageData(image); 
            
            ImageLoader imageLoader = new ImageLoader();
            imageLoader.data = new ImageData[] {imageData};
            imageLoader.logicalScreenHeight = image.getBounds().width;
            imageLoader.logicalScreenHeight = image.getBounds().height;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageLoader.save(stream, imageFormat.getOrdinal());
            setStream(stream);
        }
    }
    
    /**
     * Don't save, just stream if you can.
     */ 
    protected void saveToFile(IPath destination, DiagramSVGGenerator generator,
            ImageFileFormat format, IProgressMonitor monitor)
            throws CoreException {
        if (destination == null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            generator.stream(stream);
            setStream(stream);
            return;
        }
        super.saveToFile(destination, generator, format, monitor);
    }
    
    
    // Copied methods from the superclass.
    
    /**
     * 
     * Retrieve the image data for the image, using a palette of at most 256
     * colours.
     * 
     * @param image
     *            the SWT image.
     * @return new image data.
     */
    private ImageData createImageData(Image image) {

        ImageData imageData = image.getImageData();

        /**
         * If the image depth is 8 bits or less, then we can use the existing
         * image data.
         */
        if (imageData.depth <= 8) {
            return imageData;
        }

        /**
         * get an 8 bit imageData for the image
         */
        ImageData newImageData = get8BitPaletteImageData(imageData);

        /**
         * if newImageData is null, it has more than 256 colours. Use the web
         * safe pallette to get an 8 bit image data for the image.
         */
        if (newImageData == null) {
            newImageData = getWebSafePalletteImageData(imageData);
        }

        return newImageData;
    }
    
    /**
     * Retrieve an image data with an 8 bit palette for an image. We assume that
     * the image has less than 256 colours.
     * 
     * @param imageData
     *            the imageData for the image.
     * @return the new 8 bit imageData or null if the image has more than 256
     *         colours.
     */
    private ImageData get8BitPaletteImageData(ImageData imageData) {
        PaletteData palette = imageData.palette;
        RGB colours[] = new RGB[256];
        PaletteData newPaletteData = new PaletteData(colours);
        ImageData newImageData = new ImageData(imageData.width,
            imageData.height, 8, newPaletteData);

        int lastPixel = -1;
        int newPixel = -1;
        for (int i = 0; i < imageData.width; ++i) {
            for (int j = 0; j < imageData.height; ++j) {
                int pixel = imageData.getPixel(i, j);

                if (pixel != lastPixel) {
                    lastPixel = pixel;

                    RGB colour = palette.getRGB(pixel);
                    for (newPixel = 0; newPixel < 256; ++newPixel) {
                        if (colours[newPixel] == null) {
                            colours[newPixel] = colour;
                            break;
                        }
                        if (colours[newPixel].equals(colour))
                            break;
                    }

                    if (newPixel >= 256) {
                        /**
                         * Diagram has more than 256 colors, return null
                         */
                        return null;
                    }
                }

                newImageData.setPixel(i, j, newPixel);
            }
        }

        RGB colour = new RGB(0, 0, 0);
        for (int k = 0; k < 256; ++k) {
            if (colours[k] == null)
                colours[k] = colour;
        }

        return newImageData;
    }

    /**
     * If the image has less than 256 colours, simply create a new 8 bit palette
     * and map the colours to the new palatte.
     */
    private ImageData getWebSafePalletteImageData(ImageData imageData) {
        PaletteData palette = imageData.palette;
        RGB[] webSafePallette = getWebSafePallette();
        PaletteData newPaletteData = new PaletteData(webSafePallette);
        ImageData newImageData = new ImageData(imageData.width,
            imageData.height, 8, newPaletteData);

        int lastPixel = -1;
        int newPixel = -1;
        for (int i = 0; i < imageData.width; ++i) {
            for (int j = 0; j < imageData.height; ++j) {
                int pixel = imageData.getPixel(i, j);

                if (pixel != lastPixel) {
                    lastPixel = pixel;

                    RGB colour = palette.getRGB(pixel);
                    RGB webSafeColour = getWebSafeColour(colour);
                    for (newPixel = 0; newPixel < 216; ++newPixel) {
                        if (webSafePallette[newPixel].equals(webSafeColour))
                            break;
                    }

                    Assert.isTrue(newPixel < 216);
                }
                newImageData.setPixel(i, j, newPixel);
            }
        }

        return newImageData;
    }
    
    /**
     * Retrieves a web safe pallette. Our palette will be 216 web safe colours
     * and the remaining filled with white.
     * 
     * @return array of 256 colours.
     */
    private RGB[] getWebSafePallette() {
        RGB[] colours = new RGB[256];
        int i = 0;
        for (int red = 0; red <= 255; red = red + 51) {
            for (int green = 0; green <= 255; green = green + 51) {
                for (int blue = 0; blue <= 255; blue = blue + 51) {
                    RGB colour = new RGB(red, green, blue);
                    colours[i++] = colour;
                }
            }
        }

        RGB colour = new RGB(0, 0, 0);
        for (int k = 0; k < 256; ++k) {
            if (colours[k] == null)
                colours[k] = colour;
        }

        return colours;
    }
    
    /**
     * Retrieves a web safe colour that closely matches the provided colour.
     * 
     * @param colour
     *            a colour.
     * @return the web safe colour.
     */
    private RGB getWebSafeColour(RGB colour) {
        int red = Math.round((colour.red + 25) / 51) * 51;
        int green = Math.round((colour.green + 25) / 51) * 51;
        int blue = Math.round((colour.blue + 25) / 51) * 51;
        return new RGB(red, green, blue);
    }
    
    /**
     * Copied here to work around 264483.
     * Copies the diagram to an image file in the specified format.
     * 
     * @param diagram
     *            the diagram to be copied
     * @param destination
     *            the destination file, including path and file name
     * @param format
     *            the image file format
     * @param monitor
     *            progress monitor.
     * @param preferencesHint
     *            The preference hint that is to be used to find the appropriate
     *            preference store from which to retrieve diagram preference
     *            values. The preference hint is mapped to a preference store in
     *            the preference registry <@link DiagramPreferencesRegistry>.
     * @return A list of {@link PartPositionInfo} objects with details regarding
     *         each top-level editpart on the diagram represented in the image.
     * @exception CoreException
     *                if this method fails
     */
    public List copyToImage(Diagram diagram, IPath destination,
            ImageFileFormat format, IProgressMonitor monitor,
            PreferencesHint preferencesHint)
        throws CoreException {

        Trace.trace(DiagramUIRenderPlugin.getInstance(),
            "Copy diagram to Image " + destination + " as " + format); //$NON-NLS-1$ //$NON-NLS-2$
        
        List partInfo = Collections.EMPTY_LIST;
        
        DiagramEditor openedDiagramEditor = findOpenedDiagramEditorForID(ViewUtil.getIdStr(diagram));
        if (openedDiagramEditor != null) {
            DiagramGenerator generator = copyToImage(openedDiagramEditor.getDiagramEditPart(),
                    destination, format, monitor);
                partInfo = generator.getDiagramPartInfo(openedDiagramEditor.getDiagramEditPart());
        } else {
    
            Shell shell = new Shell();
            try {
                DiagramEditPart diagramEditPart = createDiagramEditPart(diagram,
                    shell, preferencesHint);
                Assert.isNotNull(diagramEditPart);
                DiagramGenerator generator = copyToImage(diagramEditPart,
                    destination, format, monitor);
                partInfo = generator.getDiagramPartInfo(diagramEditPart);
            } finally {
                shell.dispose();
            }
        }

        return partInfo;
    }
    
    /**
     * Copied here to work around 264483
     * Finds the <code>DiagramEditor</code> that is opened for the diagram
     * with the given diagram view id.
     * 
     * @param id
     *            diagram view's id
     * @return an opened editor that displays the diagram with the given diagram
     *         view id
     */
    public DiagramEditor findOpenedDiagramEditorForID(String id) {
        if (id != null) {
            List diagramEditors = EditorService.getInstance()
                    .getRegisteredEditorParts();
            Iterator it = diagramEditors.iterator();
            while (it.hasNext()) {
                Object obj = it.next();
                if (obj instanceof DiagramEditor) {
                    DiagramEditor diagramEditor = (DiagramEditor) obj;
                    if (diagramEditor.getDiagramEditPart() == null) {
                        continue;
                    }
                    if (id.equals(ViewUtil.getIdStr(diagramEditor
                            .getDiagramEditPart().getDiagramView()))) {
                        return diagramEditor;
                    }
                }
            }
        }
        // no matching guid found
        return null;
    }
}
