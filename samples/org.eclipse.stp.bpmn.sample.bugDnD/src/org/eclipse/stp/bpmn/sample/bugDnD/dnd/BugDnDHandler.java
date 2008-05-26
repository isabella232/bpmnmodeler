/**
 *  Copyright (C) 2008, Intalio Inc.
 *
 *  The program(s) herein may be used and/or copied only with the
 *  written permission of Intalio Inc. or in accordance with the terms
 *  and conditions stipulated in the agreement/contract under which the
 *  program(s) have been supplied.
 *
 * Date         Author             Changes
 * Mar 6, 2008      Antoine Toulme     Created
 */
package org.eclipse.stp.bpmn.sample.bugDnD.dnd;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.TextAnnotation;
import org.eclipse.stp.bpmn.diagram.generation.impl.BPMNVisual2ProcessGenerator;
import org.eclipse.stp.bpmn.dnd.AbstractViewDnDHandler;
import org.eclipse.stp.bpmn.sample.bugView.bug.view.BugView;
import org.eclipse.stp.bpmn.sample.bugView.bug.view.IBug;
import org.eclipse.swt.graphics.Image;

/**
 * This class is in charge of creating a text annotation with a special annotation
 *
 * @author <a href="http://www.intalio.com">Intalio Inc.</a>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulme</a>
 */
public class BugDnDHandler extends AbstractViewDnDHandler {

    /**
     * the String used as a source for the bug annotation on the text annotation
     * created by the command
     */
    public static final String BUG_ANNOTATION_SOURCE = "bug_info";
    
    private IBug bug;
    
    public BugDnDHandler(IBug bug) {
        this.bug = bug;
    }

    public Command getDropCommand(IGraphicalEditPart hoverPart, int index,
            Point dropLocation) {
        BPMNVisual2ProcessGenerator generator = new BPMNVisual2ProcessGenerator();
        Pool p = generator.addPool("virtual");
        TextAnnotation textA = generator.addTextAnnotation(p, bug.getSummary());
        Map<String, String> details = new HashMap<String, String>();
        details.put("state", String.valueOf(bug.getState()));
        details.put("number", String.valueOf(bug.getNumber()));
        generator.addAnnotation(textA, BUG_ANNOTATION_SOURCE, details);
        generator.generateViews();
        View textAnnotationView = generator.getSemantic2notationMap().get(textA);
        
        return getDropViewCommand(Collections.singletonList(textAnnotationView), 
                dropLocation, hoverPart);
    }

    public int getItemCount() {
        return 1;
    }

    public Image getMenuItemImage(IGraphicalEditPart hoverPart, int index) {
        if (bug.getState() == IBug.FIXED) {
            return BugView.BUG_FIXED;
        } else {
            return BugView.BUG;
        }
    }

    public String getMenuItemLabel(IGraphicalEditPart hoverPart, int index) {
        return "Drop a bug over the diagram";
    }

    public int getPriority() {
        return 0;
    }

}
