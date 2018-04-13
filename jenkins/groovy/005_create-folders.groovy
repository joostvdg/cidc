import groovy.io.FileType

import jenkins.model.Jenkins
import com.cloudbees.hudson.plugins.folder.Folder

println "##################################################"
println "### Configuring default folders Jenkins - START ##"

if (Jenkins.instance.getItem("pipelines") == null) {
    println "# folder 'pipelines' not found, creating"
    Jenkins.instance.createProject(Folder.class, "pipelines")
}

if (Jenkins.instance.getItem("configs") == null) {
    println "# folder 'configs' not found, creating"
    Jenkins.instance.createProject(Folder.class, "configs")
}


println "### Configuring default folders Jenkins -  END  ##"
println "##################################################"
