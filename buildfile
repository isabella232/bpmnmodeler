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
    package(:feature).feature_xml = _("feature.xml")
    package(:feature).feature_xml = _("feature.properties")
    package(:feature).plugins = [project('org.eclipse.stp.bpmn'), 
                                 project('org.eclipse.stp.bpmn.diagram'), 
                                 project('org.eclipse.stp.bpmn.edit'), 
                                 project('org.eclipse.stp.bpmn.validation')]
    package(:feature).include(_("epl-v10.html"))
    package(:feature).include(_("license.html"))
    package(:feature).include(_("eclipse_update_120.jpg"))
    # Also do a SDK feature
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
