import groovy.io.FileType

import jenkins.model.Jenkins
import com.cloudbees.hudson.plugins.folder.Folder

println "##################################################"
println "### Configuring default folders Jenkins - START ##"

if (Jenkins.instance.getItem("development") == null) {
    println "# folder 'development' not found, creating"
    Jenkins.instance.createProject(Folder.class, "development")
}

if (Jenkins.instance.getItem("configuration") == null) {
    println "# folder 'configuration' not found, creating"
    Jenkins.instance.createProject(Folder.class, "configuration")
}


println "### Configuring default folders Jenkins -  END  ##"
println "##################################################"
