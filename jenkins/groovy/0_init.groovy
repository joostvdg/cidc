#!groovy
import jenkins.model.*

def instance = Jenkins.getInstance()
def jenkinsHostName = System.getenv("HOSTNAME")
def jenkinsExternalPort = System.getenv("EXTERNAL_PORT")
def jenkinsContextRoot = System.getenv("CONTEXT_ROOT")
def keycloakHostName = System.getenv("KEYCLOAK_HOSTNAME")
def keycloakPort = System.getenv("KEYCLOAK_PORT")
def keycloakHostNameSuffix = System.getenv("KEYCLOAK_HOSTNAME_SUFFIX")
def keycloakLowercase = System.getenv("KEYCLOAK_HOSTNAME_LOWERCASE")
def sonarHostName = System.getenv("SONAR_HOSTNAME")
def nexusHostName = System.getenv("NEXUS_HOSTNAME")

if (keycloakLowercase.equals('true')) {
    keycloakHostName = keycloakHostName.toLowerCase()
}
def keycloakHostNameFinal = "http://" + keycloakHostName + keycloakHostNameSuffix + ":" + keycloakPort 
def jenkinsHostNameFinal = "http://" + jenkinsHostName + ":" + jenkinsExternalPort + jenkinsContextRoot

System.setProperty("JENKINS_BASE_URL", jenkinsHostNameFinal)
System.setProperty("KEYCLOAK_BASE_URL", keycloakHostNameFinal)

println "###############################################"
println "### INIT GROOVY CONFIG FOR JENKINS ############"
println "###############################################"
println "############# ENV VARIABLES ###################"
println "### jenkinsHostNameFinal   :: ${jenkinsHostNameFinal}"
println "### keycloakHostNameFinal  :: ${keycloakHostNameFinal}"
println "### sonarHostName          :: ${sonarHostName}"
println "### nexusHostName          :: ${nexusHostName}"
println "###############################################"
println "###############################################"