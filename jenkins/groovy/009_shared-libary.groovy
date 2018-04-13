#!groovy
// https://github.com/abnamrocoesd/jenkins-pipeline-library-core.git
// ROOT: org.jenkinsci.plugins.workflow.libs.GlobalLibraries: getLibraries().add(libraryConfiguration)
// Main element: org.jenkinsci.plugins.workflow.libs.LibraryConfiguration: LibraryConfiguration(String name, LibraryRetriever retriever)
// org.jenkinsci.plugins.workflow.libs.SCMSourceRetriever: SCMSourceRetriever(SCMSource scm)
// jenkins.plugins.git.GitSCMSource: GitSCMSource(remote)

import org.jenkinsci.plugins.workflow.libs.GlobalLibraries
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration
import org.jenkinsci.plugins.workflow.libs.SCMSourceRetriever
import jenkins.plugins.git.GitSCMSource
import jenkins.model.*

println("=== Configuring the Global Shared Libraries == Start")

def libConfigName = 'jenkins-pipeline-library'
def libConfigRemote = 'https://github.com/joostvdg/jenkins-pipeline-lib.git'
def globalLibraries = GlobalLibraries.get()
boolean exists = false
for (LibraryConfiguration lib : globalLibraries.getLibraries()) {
    if (lib.getName().equals(libConfigName)) {
        exists = true
    }
}

if (exists) {
    println("= Global Library with name ${libConfigName} already exists")
} else {
    globalLibraries = GlobalLibraries.get()
    if (globalLibraries.getLibraries().isEmpty()) {
        List<LibraryConfiguration> libraries = new ArrayList<>();
        globalLibraries.setLibraries(libraries)
    }
    def gitScmSource = new GitSCMSource(libConfigRemote)
    def scmSourceRetriever = new SCMSourceRetriever(gitScmSource)
    def libConfig = new LibraryConfiguration(libConfigName, scmSourceRetriever)
    println("= adding Global Library: ${libConfigName} - ${libConfigRemote}")
    globalLibraries.getLibraries().add(libConfig)
    globalLibraries.save()
}


println("=== Configuring the Global Shared Libraries == End")
