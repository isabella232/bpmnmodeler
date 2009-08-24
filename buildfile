require "buildr4osgi"

repositories.remote << "http://repo1.maven.org/maven2"

VERSION_NUMBER = "1.2.0.000-SNAPSHOT"

layout = Layout.new
layout[:source, :main, :java] = 'src'

define "bpmn-modeler", :layout => layout do
  project.version = VERSION_NUMBER
  project.group = "org.eclipse.stp.bpmn"
  compile.options.source = "1.5"
  compile.options.target = "1.5"
  
  define 'org.eclipse.stp.bpmn'  do
    compile.with project.dependencies
    package :jar
  end

  define 'org.eclipse.stp.bpmn.diagram' do
    compile.from(_('customsrc')).with [project('org.eclipse.stp.bpmn'), project('org.eclipse.stp.bpmn.edit')] + project.dependencies
    package :jar
  end

  define 'org.eclipse.stp.bpmn.edit'  do
    compile.with [project('org.eclipse.stp.bpmn')] + project.dependencies
    package :jar
  end

  define 'org.eclipse.stp.bpmn.validation'  do
    compile.with [project('org.eclipse.stp.bpmn'), project('org.eclipse.stp.bpmn.diagram')] + project.dependencies + project('org.eclipse.stp.bpmn.diagram').dependencies
    package :jar
  end
  
  define 'org.eclipse.stp.bpmn.feature' do
    package(:feature).tap {|f|
      f.label = "BPMN modeler feature"
      f.provider = "Eclipse.org"
      f.description = "The BPMN Modeler is a business process diagram editor for business analysts."
      f.changesURL = "http://www.eclipse.org/bpmn/"
      f.license = File.read("epl-v10.html")
      f.licenseURL = "http://www.eclipse.org/legal/epl-v10.html"
      #f.update_sites << {:url => "http://example.com/update", :name => "My update site"}
      #f.discovery_sites = [{:url => "http://example.com/update2", :name => "My update site2"}, 
      #  {:url => "http://example.com/upup", :name => "My update site in case"}]
      f.plugins = [project('org.eclipse.stp.bpmn'), project('org.eclipse.stp.bpmn.diagram'), 
        project('org.eclipse.stp.bpmn.edit'), project('org.eclipse.stp.bpmn.validation')]
    }
  end
end