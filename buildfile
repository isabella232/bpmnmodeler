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
    p project.dependencies
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
end