require "buildr4osgi"

repositories.remote = ["http://repo1.maven.org/maven2", "file:///shared/common/m2repo"]

repositories.release_to[:url] ||= "file:///shared/common/m2repo"

VERSION_NUMBER = "1.2.0.000-SNAPSHOT"

tag_time = Time.now.strftime("%Y%m%d%H%M")
# Monkey patching Buildr to change the way we tag releases.
module Buildr
  class Release #:nodoc:
    class << self
      def with_release_candidate_version
        release_candidate_buildfile = Buildr.application.buildfile.to_s + '.next'
        release_candidate_buildfile_contents = change_version { |version| version[-1] = "v#{tag_time}" }
        File.open(release_candidate_buildfile, 'w') { |file| file.write release_candidate_buildfile_contents }
        begin
          yield release_candidate_buildfile
          mv release_candidate_buildfile, Buildr.application.buildfile.to_s
        ensure
          rm release_candidate_buildfile rescue nil
        end
      end
    end
  end
end

layout = Layout.new
layout[:source, :main, :java] = 'src'

# === Build.eclipse.org specific methods ===

COMMITTER_UPDATE_SITE = "/home/data/httpd/download.eclipse.org/stp/committers/updates/"
SIGN_STAGING="/home/data/httpd/download-staging.priv/stp"
LAUNCHER="java -jar /shared/stp/platforms/releng/M7_34/org.eclipse.releng.basebuilder/plugins/org.eclipse.equinox.launcher.jar"

# Signs an update site, using a script that may only be found on the Eclipse Foundation build machine.
# This method may take a long time as signing works as a queue.
def sign(artifact, output = artifact, send_email = true)
  puts "Start signing of #{artifact}"
  signed_folder = File.basename(artifact).gsub(/\./, "_")
  File.makedirs "#{SIGN_STAGING}/#{signed_folder}"
  system("cp #{artifact} #{SIGN_STAGING}")
  mail = send_email ? "mail" : "nomail"
  puts %x[/usr/bin/sign #{SIGN_STAGING}/#{File.basename artifact} #{mail} #{SIGN_STAGING}/#{signed_folder}]
  fail "Signing failed " if $? != 0
  while (!File.exist?"#{SIGN_STAGING}/#{signed_folder}/#{File.basename artifact}") 
    puts "Signing not complete. Waiting for 2 more minutes..."
    sleep 120
  end
  system("cp #{SIGN_STAGING}/#{signed_folder}/#{File.basename artifact} #{output}")
  system "rm -rf #{SIGN_STAGING}/#{File.basename artifact} #{SIGN_STAGING}/#{signed_folder}"
  puts "Done signing, copied final file here: #{output}" 
end

# Packs an update site (artifact). artifact is the path to a zipped update site
def pack(artifact, output = artifact)
 puts("Start packing #{artifact}") 
 system("#{LAUNCHER} -Xmx768m -Dorg.eclipse.update.jarprocessor.pack200=/shared/stp/scripts/pack200 -consolelog -application org.eclipse.update.core.siteOptimizer -jarProcessor -verbose -pack -outputDir /tmp -processAll #{artifact}")
  system("mv /tmp/#{File.basename artifact} #{output}")
  puts("Done packing, copied to #{output}") 
end

#Digest an update site. The artifact is a zip of an update site.
def digest(artifact, output = artifact)
  puts("Start digest #{artifact}")
  temp = Dir.tmpdir + "/" + Time.now.to_i.to_s
  begin
    File.makedirs temp
    system("unzip -oq #{artifact} -d #{temp}")
    system("#{LAUNCHER} -application org.eclipse.update.core.siteOptimizer -digestBuilder -digestOutputDir=#{temp} -siteXML=#{temp}/site.xml")
    system("rm -Rf #{temp}/tmp")
    system("cd #{temp} ; zip -r #{output}.tmp * ; mv #{output}.tmp #{output}")
    puts("Done digesting, copied to #{output}")
  end
  system("rm -Rf #{temp}") 
end

# === End of build.eclipse.org specific methods ===

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
      f.license = File.read(_("license.txt"))
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
    ec = task :eclipse_specific do
      site = package(:site)
      sign(site.to_s, site.to_s, false)
      pack(site.to_s)
      digest(site.to_s)
    end
    ec.enhance [package(:site)]
    package(:p2_from_site).enhance([ec]).with :site => package(:site)
  end
end
