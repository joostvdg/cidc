import hudson.security.csrf.DefaultCrumbIssuer
import jenkins.model.Jenkins
import jenkins.model.JenkinsLocationConfiguration
import jenkins.CLI
import jenkins.security.s2m.AdminWhitelistRule
import org.kohsuke.stapler.StaplerProxy
import hudson.security.*

def instance = Jenkins.getInstance()
println "=== Configuring Security for Jenkins == Start"

println " = Disable Remoting CLI"
CLI.get().enabled = false

println " = Disable Remember me"
jenkins.setDisableRememberMe(false)

println " = Set Agents Protocols to only JNLP4"
instance.getExtensionList(StaplerProxy.class)
    .get(AdminWhitelistRule.class)
    .masterKillSwitch = false
instance.agentProtocols = new HashSet<String>(["JNLP4-connect"])

println " = Set CSRF"
instance.crumbIssuer = new DefaultCrumbIssuer(true)

println " = Set Authorization Strategy (Matrix)"
def strategy = new GlobalMatrixAuthorizationStrategy()

//  Slave Permissions for Anonymous --> for the Swarm Agent
strategy.add(hudson.model.Computer.BUILD,'anonymous')
strategy.add(hudson.model.Computer.CONFIGURE,'anonymous')
strategy.add(hudson.model.Computer.CONNECT,'anonymous')
strategy.add(hudson.model.Computer.CREATE,'anonymous')
strategy.add(hudson.model.Computer.DELETE,'anonymous')
strategy.add(hudson.model.Computer.DISCONNECT,'anonymous')

// The rest is for Authenticated
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.CREATE,'authenticated')
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.DELETE,'authenticated')
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.MANAGE_DOMAINS,'authenticated')
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.UPDATE,'authenticated')
strategy.add(com.cloudbees.plugins.credentials.CredentialsProvider.VIEW,'authenticated')

strategy.add(hudson.model.Computer.BUILD,'authenticated')
strategy.add(hudson.model.Computer.CONFIGURE,'authenticated')
strategy.add(hudson.model.Computer.CONNECT,'authenticated')
strategy.add(hudson.model.Computer.CREATE,'authenticated')
strategy.add(hudson.model.Computer.DELETE,'authenticated')
strategy.add(hudson.model.Computer.DISCONNECT,'authenticated')

strategy.add(hudson.model.Hudson.ADMINISTER,'authenticated')
strategy.add(hudson.model.Hudson.READ,'authenticated')

strategy.add(hudson.model.Item.BUILD,'authenticated')
strategy.add(hudson.model.Item.CONFIGURE,'authenticated')
strategy.add(hudson.model.Item.CREATE,'authenticated')
strategy.add(hudson.model.Item.DELETE,'authenticated')
strategy.add(hudson.model.Item.EXTENDED_READ,'authenticated')
strategy.add(hudson.model.Item.READ,'authenticated')
strategy.add(hudson.model.Item.WIPEOUT,'authenticated')
strategy.add(hudson.model.Item.WORKSPACE,'authenticated')

strategy.add(hudson.model.Run.ARTIFACTS,'authenticated')
strategy.add(hudson.model.Run.DELETE,'authenticated')
strategy.add(hudson.model.Run.UPDATE,'authenticated')

strategy.add(hudson.model.View.CONFIGURE,'authenticated')
strategy.add(hudson.model.View.CREATE,'authenticated')
strategy.add(hudson.model.View.DELETE,'authenticated')
strategy.add(hudson.model.View.READ,'authenticated')
strategy.add(hudson.scm.SCM.TAG,'authenticated')

instance.setAuthorizationStrategy(strategy)
instance.save()

println "=== Configuring Security for Jenkins == Finish"