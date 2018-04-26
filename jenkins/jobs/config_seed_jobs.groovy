def systemScript = ''' 
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
        import hudson.plugins.sonar.*
        import hudson.plugins.sonar.model.*

        println("=== Configuring SonarQube Server == Start")
        def instance = Jenkins.getInstance()


        def sonar_token = build.buildVariableResolver.resolve("sonar_token")
        println "sonar_token=$sonar_token"
        def sonar_url = build.buildVariableResolver.resolve("sonar_url")
        println "sonar_url=$sonar_url"

	SonarGlobalConfiguration sonar_conf = instance.getDescriptorByType(SonarGlobalConfiguration.class)
	// String name, String serverUrl,  String serverAuthenticationToken, String mojoVersion,  String additionalProperties,  TriggersConfig triggers, String additionalAnalysisProperties
	def sonar_inst = new SonarInstallation(
	   "sonar",
	   sonar_url,
	   sonar_token,
	   "6.7",
	   "",
	   null,
	   ""
	)        

        // Only add Sonar if it does not exist - do not overwrite existing config
        def sonar_installations = sonar_conf.getInstallations()
        def sonar_inst_exists = false
        sonar_installations.each {
            installation = (SonarInstallation) it
            if (sonar_inst.getName() == installation.getName()) {
                sonar_inst_exists = true
                println("Found existing installation: " + installation.getName())
            }
        }

        if (!sonar_inst_exists) {
            sonar_installations += sonar_inst
            sonar_conf.setInstallations((SonarInstallation[]) sonar_installations)
            sonar_conf.save()
        }


        println("=== Configuring SonarQube Server == Finish")
    '''

job('configs/sonar') {
    description('Job for configuring SonarQube configuration for Jenkins master.')
    logRotator {
        numToKeep(5)
        daysToKeep(5)
        artifactNumToKeep(5)
        artifactDaysToKeep(5)
    }
    authenticationToken('JENKINS_ROCKS')
    wrappers {
        timestamps()
        timeout {
            absolute(60)
        }
    }
    parameters {
        stringParam('sonar_token', 'INVALID', 'Token for SonarQube.')
    }
    steps {
        systemGroovyCommand(systemScript) {
            binding('sonar_token', 'TESTSETETSSET')
        }
    }
}
