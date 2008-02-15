/******************************************************************************
 * Copyright (c) 2007, Intalio Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Intalio Inc. - initial API and implementation
 *******************************************************************************
 * Dates       		 Author           Changes
 * Jan 22 2007     Antoine Toulm�     Creation
 * Jan 22 2007     Hugues Malphettes  The parser
 */
package org.eclipse.stp.bpmn.samples.bpel2bpmn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.stp.bpmn.Activity;
import org.eclipse.stp.bpmn.ActivityType;
import org.eclipse.stp.bpmn.Graph;
import org.eclipse.stp.bpmn.Pool;
import org.eclipse.stp.bpmn.SequenceEdge;
import org.eclipse.stp.bpmn.Vertex;
import org.eclipse.stp.bpmn.diagram.generation.impl.BPMNVisual2ProcessGenerator;
import org.eclipse.stp.bpmn.dnd.file.FileDnDConstants;
import org.eclipse.stp.bpmn.samples.SamplesPlugin;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

/**
 * Very basic sample that generates BPMN out of a BPEL file.
 * The BPEL parsed is a small subset of the bpel spec:
 * scope, assign, receive, reply, invoke, flow, sequence.
 * <p>
 * It is very forgiving in terms of namespace (read: it does not take them into
 * account).
 * </p>
 * <p>
 * It is only meant as an example of usage of The BPMNProcessGenerator.
 * Contributions are welcome!
 * </p>
 * @author <a href="mailto:atoulme@intalio.com">Antoine Toulm�</a>
 * @author <a href="mailto:hmalphettes@intalio.com">Hugues Malphettes</a>
 * @author <a href="http://www.intalio.com">&copy; Intalio, Inc.</a>
 */
public class BPEL2BPMNGenerator extends BPMNVisual2ProcessGenerator {

    private static Set<String> PARSED_BPEL_LOCAL_NAME = new HashSet<String>();
    private static Set<String> CONTAINERS_LOCAL_NAME = new HashSet<String>();
    static {
        
        CONTAINERS_LOCAL_NAME.add("scope");
        CONTAINERS_LOCAL_NAME.add("flow");
        
        //PARSED_BPEL_LOCAL_NAME.add("sequence");
        PARSED_BPEL_LOCAL_NAME.add("assign");
        PARSED_BPEL_LOCAL_NAME.add("receive");
        PARSED_BPEL_LOCAL_NAME.add("reply");
        PARSED_BPEL_LOCAL_NAME.add("invoke");
        PARSED_BPEL_LOCAL_NAME.addAll(CONTAINERS_LOCAL_NAME);
    }
    public static final String NAMESPACES_SAXF = 
        "http://xml.org/sax/features/namespaces";    
    
    private EObject _currentSemanticElement;
	
	private Pool _virtualPool;
	
    private IFile _file;
    /** the parser's locator to annotate the model with the exact location. */
    private Locator _locator;
    
	/**
	 * Default constructor. We only want to create a generator
	 * that will act over a virtual resource
	 */
	public BPEL2BPMNGenerator(IFile file) {
        _file = file;
	}
	
	/**
	 * Parse and generate the views from the BPEL file
	 * @param file the BPEL file
     * @return The children views of the virtual pool.
 	 */
	public List<View> parseAndGenerateFromFile() {
		
		try {
            _virtualPool = addPool("nonCopiedPool");
            
			XMLReader saxReader = SAXParserFactory.newInstance().
				newSAXParser().getXMLReader();
            saxReader.setFeature(NAMESPACES_SAXF, true);
            saxReader.setContentHandler(new BPELSimpleContentHandler());
            saxReader.parse(new InputSource(_file.getContents()));
            
            generateViews();
            
            List<View> res = new ArrayList<View>();
            Map<EObject, View> sem2views = getSemantic2notationMap();
            for (Object overtex : _virtualPool.getVertices()) {
                res.add(sem2views.get(overtex));
            }
            for (Object overtex : _virtualPool.getSequenceEdges()) {
                res.add(sem2views.get(overtex));
            }
            return res;
        } catch (SAXParseException e) {
            SamplesPlugin.getDefault().getLog().log(
                    new Status(IStatus.ERROR, SamplesPlugin.PLUGIN_ID,
                            IStatus.ERROR, 
                            _file + " lineNb=" +e.getLineNumber() + " - " +
                            e.getMessage(), e));
        } catch (SAXException e) {
            SamplesPlugin.getDefault().getLog().log(
                    new Status(IStatus.ERROR, SamplesPlugin.PLUGIN_ID,
                            IStatus.ERROR, "" + e.getMessage(), e));
		} catch (IOException e) {
			SamplesPlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR, SamplesPlugin.PLUGIN_ID,
                            IStatus.ERROR, "" + e.getMessage(), e));
		} catch (CoreException e) {
			SamplesPlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR, SamplesPlugin.PLUGIN_ID,
                            IStatus.ERROR, "" + e.getMessage(), e));
		} catch (ParserConfigurationException e) {
			SamplesPlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR, SamplesPlugin.PLUGIN_ID,
                            IStatus.ERROR, "" + e.getMessage(), e));
		}
		return null;
	}
	
    private Graph getParentNode(String currentLocalName, String name)
    throws SAXParseException {
        if (_currentSemanticElement == null) {
            return _virtualPool;
        } else if (_currentSemanticElement instanceof Graph) {
            return (Graph) _currentSemanticElement;
        } else if (_currentSemanticElement instanceof Activity) {
            Activity  cu = ((Activity)_currentSemanticElement);
            if (cu.getActivityType().getValue() == ActivityType.GATEWAY_PARALLEL) {
                return cu.getGraph();
            }
        }
        throw new SAXParseException("Expecting to be inside a container " +
                "to add a " + currentLocalName + 
                (name != null ? " name='" + name : "'") + " but is inside " +
                _currentSemanticElement, _locator);
    }
    
    /**
     * Looks in the children of the parent node the last node
     * that was added before the newlyCreatedChild and adds a sequence between it
     *  and the newlyCreatedChild 
     * @param parentView
     * @param newlyCreatedChild The node that has just been added as a child of 
     * the parentNode
     */
    private void connectPredecessor(Graph parentNode, Vertex newlyCreatedChild)
    throws SAXParseException {
        Vertex predecessor = null;
        for (Object v : parentNode.getVertices()) {
            Vertex currentChild = (Vertex) v;
            if (newlyCreatedChild == currentChild) {
                if (predecessor != null) {
                    SequenceEdge seqEdge = 
                    	addSequenceEdge(predecessor, newlyCreatedChild, "");
                }
                break;
            }
            if (currentChild instanceof Vertex) {
                predecessor = (Vertex) currentChild;
            }
        }
    }
        
    private Activity addActivity(String bpelLocalName, String name, int type)
    throws SAXParseException {
        Graph parentNode = getParentNode(bpelLocalName, name);
        Activity newActivity = addActivity(parentNode, name, type);
        createLinkAnnotation(newActivity, _locator);
        connectPredecessor(parentNode, newActivity);
        _currentSemanticElement = newActivity;
        return newActivity;
    }
	
	/**
	 * the content handler we use to read the BPEL file.
	 */
	private class BPELSimpleContentHandler implements ContentHandler {

        
        public void setDocumentLocator (Locator locator) {
            _locator = locator;
        }
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			
		}

		public void endDocument() throws SAXException {
			
		}
		/**
		 * Releases the latest sequence.
		 */
		public void endElement(String uri, String localName, String name)
				throws SAXException {
            if (!PARSED_BPEL_LOCAL_NAME.contains(localName)) {
                return;//skip it.
            }
            if (_currentSemanticElement == null) {
                throw new SAXParseException("No more containers in the semantic model." +
                      " We are at </" + localName + ">. Not sure what to do.", _locator);
            }
            //we will see later if we need to do something more subtle.
            
            _currentSemanticElement = _currentSemanticElement.eContainer();
		}

		public void endPrefixMapping(String prefix) throws SAXException {
		}

		public void ignorableWhitespace(char[] ch, int start, int length)
				throws SAXException {
		}

		public void processingInstruction(String target, String data)
				throws SAXException {
		}

		public void skippedEntity(String name) throws SAXException {
		}

		public void startDocument() throws SAXException {
		}

		/**
		 * Filters the start elements, interested in sequences 
		 * and assign elements.
		 */
		public void startElement(String uri, String localName, String name,
				Attributes atts) throws SAXException {
            if (!PARSED_BPEL_LOCAL_NAME.contains(localName)) {
                return;//skip it.
            }
			String bpelName = atts.getValue("name");
            /*if ("sequence".equals(localName)) {
                //nothing to do.
            } else */if ("scope".equals(localName)) {
                addActivity(localName, bpelName, ActivityType.SUB_PROCESS);
            } else if ("flow".equals(localName)) {
                addActivity(localName, bpelName, ActivityType.GATEWAY_PARALLEL);
            } else if ("assign".equals(localName)) {
                addActivity("assign", bpelName, ActivityType.TASK);
            } else if ("receive".equals(localName)) {
                if (name == null) {
                    name = "Receive";
                } else if (name.toLowerCase().indexOf("receive") == -1) {
                    name = "Receive " + bpelName;
                }
                addActivity(localName, name, ActivityType.EVENT_INTERMEDIATE_MESSAGE);
            } else if ("reply".equals(localName)) {
                if (name == null) {
                    name = "Reply";
                } else if (name.toLowerCase().indexOf("receive") == -1) {
                    name = "Reply " + bpelName;
                }
                addActivity(localName, bpelName, ActivityType.EVENT_INTERMEDIATE_MESSAGE);
            } else if ("invoke".equals(localName)) {
                if (name == null) {
                    name = "Invoke";
                } else if (name.toLowerCase().indexOf("receive") == -1) {
                    name = "Invoke " + bpelName;
                }
                addActivity(localName, bpelName, ActivityType.TASK);
            } else {
            
			}
		}


		public void startPrefixMapping(String prefix, String uri)
				throws SAXException {
		}
		
	}
	    
    private void createLinkAnnotation(EModelElement semanticElement, Locator locator) {
        Map<String, String> details = new HashMap<String, String>();
        details.put(FileDnDConstants.PROJECT_RELATIVE_PATH, 
                _file.getProjectRelativePath().toString());
        details.put(FileDnDConstants.LINE_NUMBER, String.valueOf(locator.getLineNumber()));
        addAnnotation(
                semanticElement, FileDnDConstants.ANNOTATION_SOURCE, details);
    }
}
