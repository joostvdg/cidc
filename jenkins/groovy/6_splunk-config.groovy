import jenkins.model.*
def instance = Jenkins.getInstance()

println "#############################################"
println "### Configuring Splunk for Jenkins - START ##"

def token = System.getProperty("SPLUNK_TOKEN")

def splunk = Jenkins.instance.getExtensionList(com.splunk.splunkjenkins.SplunkJenkinsInstallation.class)[0]
splunk.setHost("splunk")
splunk.setEnabled(true)
splunk.setToken(token)
splunk.setUseSSL(true)
splunk.setPort(8088)
splunk.setMaxEventsBatchSize(262144)
splunk.setRawEventEnabled(false)
splunk.setRetriesOnError(3)
splunk.setScriptContent("""
//send job metadata and junit reports with page size set to 50 (each event contains max 50 test cases)
//     splunkins.sendTestReport(50)
//     //send coverage, each event contains max 50 class metrics
//     splunkins.sendCoverageReport(50)
//     //send all logs from workspace to splunk, with each file size limits to 10MB
//     splunkins.archive(&quot;**/*.log&quot;, null, false, &quot;10MB&quot;)
//     //end
""")
splunk.save()

println "### Configuring Splunk for Jenkins - END ###"
println "############################################"

