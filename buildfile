require "buildr4osgi"

repositories.remote = ["http://repo1.maven.org/maven2", "file:///shared/common/m2repo"]

repositories.release_to[:url] ||= "file:///shared/common/m2repo"

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
    package(:plugin)
  end

  define 'org.eclipse.stp.bpmn.diagram' do
    compile.from(_('customsrc')).with dependencies
    package(:plugin)
  end

  define 'org.eclipse.stp.bpmn.edit'  do
    compile.with dependencies
    package(:plugin)
  end

  define 'org.eclipse.stp.bpmn.validation'  do
    compile.with dependencies
    package(:plugin)
  end
  
  define 'org.eclipse.stp.bpmn.feature' do
    package(:feature).tap do |f|
      f.feature_id = "org.eclipse.stp.bpmn.feature"
      f.label = "BPMN Project Feature"
      f.provider = "eclipse.org"
      f.copyright = <<-COPYRIGHT
Copyright (c) 2006-2010, Intalio Inc.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html

Contributors:
    Intalio Inc. - initial API and implementation
COPYRIGHT
      f.description = "Development environment for BPMN business processes"
      f.changesURL = ""
      f.license = File.read(_("license"))
      f.licenseURL = "license.html"
      f.update_sites << {:url => "http://download.eclipse.org/stp/updates/", :name => "The Eclipse SOA Tools Platform (STP) Project update site"}
      f.plugins = [project('org.eclipse.stp.bpmn'), 
                                 project('org.eclipse.stp.bpmn.diagram'), 
                                 project('org.eclipse.stp.bpmn.edit'), 
                                 project('org.eclipse.stp.bpmn.validation')]
                                 
      f.include(_("epl-v10.html"))
      f.include(_("license.html"))
      f.include(_("eclipse_update_120.jpg"))
    end
    
    # Also do a SDK feature
    package(:sources).feature_id = "org.eclipse.stp.bpmn.sdk.feature"
    package(:sources).label = "BPMN Project SDK Feature"
    package(:sources).description = "Development environment for BPMN business processes"
    package(:sources).feature_properties = _("feature.properties")
    package(:sources).include(_("epl-v10.html"))
    package(:sources).include(_("license.html"))
    package(:sources).include(_("eclipse_update_120.jpg"))
  end
  
  define "org.eclipse.stp.bpmn.site" do
    package(:site).tap do |site|
      category = Buildr4OSGi::Category.new
      category.name = "org.eclipse.stp" #probably incorrect ?
      category.label = "STP" #TODO
      category.description = "" #TODO
      category.features<< project("bpmn-modeler:org.eclipse.stp.bpmn.feature")
      site.categories << category
    end
    package(:p2_from_site)
  end
end
