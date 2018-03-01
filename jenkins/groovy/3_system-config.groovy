import jenkins.*
import jenkins.model.*
import hudson.model.*
import hudson.tasks.Shell
import hudson.slaves.EnvironmentVariablesNodeProperty
import hudson.slaves.EnvironmentVariablesNodeProperty.Entry
import jenkins.model.Jenkins
import jenkins.model.JenkinsLocationConfiguration
import jenkins.security.s2m.AdminWhitelistRule
import hudson.markup.RawHtmlMarkupFormatter
import hudson.markup.EscapedMarkupFormatter
import hudson.model.Node.Mode

def instance = Jenkins.getInstance()
println "=== Configuring System for Jenkins == Start"

println " = Set System Message"
instance.setSystemMessage("Welcome to the CICD Sandbox")
    
println " = Configure Master's slave settings"
instance.setLabelString('master')
instance.setNumExecutors(2)    
instance.setMode(Mode.EXCLUSIVE)

println(" = Configuring Quiet Period")
// We do not wait for anything
instance.quietPeriod = 0

println " = Set SCM Checkout Retry Count"
instance.setScmCheckoutRetryCount(3)

println " = Set Server URL"
JenkinsLocationConfiguration location = instance.getExtensionList(jenkins.model.JenkinsLocationConfiguration).get(0)
location.setUrl("http://localhost:8282/jenkins")

println " = Set Default Formatter"
RawHtmlMarkupFormatter markupFormatter = new RawHtmlMarkupFormatter(false) // disable syntax highlighting = false
instance.setMarkupFormatter(markupFormatter)

println "=== Configuring System for Jenkins == Finish"