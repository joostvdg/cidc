import hudson.model.FreeStyleProject
import hudson.model.Item
import jenkins.model.Jenkins
import jenkins.model.ModifiableTopLevelItemGroup
import org.jenkinsci.plugins.scriptsecurity.scripts.ScriptApproval

// initial-seed-job.xml
String xmlData = '''<flow-definition plugin="workflow-job@2.18">
<description/>
<keepDependencies>false</keepDependencies>
<properties>
<org.jenkinsci.plugins.gogs.GogsProjectProperty plugin="gogs-webhook@1.0.14">
<gogsSecret/>
<gogsUsePayload>false</gogsUsePayload>
</org.jenkinsci.plugins.gogs.GogsProjectProperty>
<com.synopsys.arc.jenkins.plugins.ownership.jobs.JobOwnerJobProperty plugin="ownership@0.12.0"/>
<com.synopsys.arc.jenkinsci.plugins.jobrestrictions.jobs.JobRestrictionProperty plugin="job-restrictions@0.6"/>
</properties>
<definition class="org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition" plugin="workflow-cps@2.47">
<scm class="hudson.plugins.git.GitSCM" plugin="git@3.8.0">
<configVersion>2</configVersion>
<userRemoteConfigs>
<hudson.plugins.git.UserRemoteConfig>
<url>https://github.com/joostvdg/cidc.git</url>
</hudson.plugins.git.UserRemoteConfig>
</userRemoteConfigs>
<branches>
<hudson.plugins.git.BranchSpec>
<name>*/master</name>
</hudson.plugins.git.BranchSpec>
</branches>
<doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>
<submoduleCfg class="list"/>
<extensions/>
</scm>
<scriptPath>jenkins/jobs/seed_job.groovy</scriptPath>
<lightweight>true</lightweight>
</definition>
<triggers/>
<disabled>false</disabled>
</flow-definition>
'''

println "###########################################"
println "### Configuring Initial Seed Job - START ##"

Jenkins instance = Jenkins.getInstance()
ModifiableTopLevelItemGroup itemgroup = instance

def groupName = 'configuration'
def itemName = 'initial-seed-job'

Item item = instance.getItemByFullName(groupName)
if (item == null) {
    println "Could not find group ${groupName}"
} else {
    println "Found group: ${item}"
}

if (item instanceof com.cloudbees.hudson.plugins.folder.Folder) {
    println "It's a folder as it should be!"
} else {
    println 'Configuration folder isn\'t there, we cannot continue'
    return
}

item.createProjectFromXML(itemName, new ByteArrayInputStream(xmlData.getBytes()))
item.save()

// AND NOW WE HAVE TO ADD THAT SCRIPT TO THE SCRIPT SECURITY AS APPROVED
def scriptApproval = ScriptApproval.get()
scriptApproval.approveScript('6ea2fb406b2e2f15a64262e21d463acc9df27d18')
scriptApproval.approveScript('3b03eefc72fad28b97ba99553c9e7ba41e69be39')
scriptApproval.save()

println "### Configuring Initial Seed Job - END   ##"
println "###########################################"