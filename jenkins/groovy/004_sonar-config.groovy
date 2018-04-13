import jenkins.model.*
import hudson.plugins.sonar.*
import hudson.plugins.sonar.model.*

println("=== Configuring SonarQube Server == Start")
def instance = Jenkins.getInstance()

// hudson.plugins.sonar.SonarGlobalConfiguration plugin="sonar@2.6.1">
//   <installations>
//     <hudson.plugins.sonar.SonarInstallation>
//       <name>sonar</name>
//       <serverUrl>http://sonar:9000</serverUrl>
//       <serverVersion>5.3</serverVersion>
//       <serverAuthenticationToken>0999bb04f233f8473eab3d62a2ebac60eb614451</serverAuthenticationToken>
//       <mojoVersion></mojoVersion>
//       <databaseUrl></databaseUrl>
//       <databaseLogin></databaseLogin>
//       <additionalProperties></additionalProperties>
//       <additionalAnalysisProperties></additionalAnalysisProperties>
//       <triggers>
//         <skipScmCause>false</skipScmCause>
//         <skipUpstreamCause>false</skipUpstreamCause>
//         <envVar></envVar>
//       </triggers>
//       <sonarLogin></sonarLogin>
//       <databaseSecret>{AQAAABAAAAAQ/e0FRsp3G78SN8a5vh+1SDEvxe7x/lOzA7WGSZdRFHI=}</databaseSecret>
//       <sonarSecret>{AQAAABAAAAAQ+ithc54diOxpq/O2crylzoJwm3gBTQTOPEJExsT6ALU=}</sonarSecret>
//     </hudson.plugins.sonar.SonarInstallation>
//   </installations>
//   <buildWrapperEnabled>true</buildWrapperEnabled>
// </hudson.plugins.sonar.SonarGlobalConfiguration>% 
  
SonarGlobalConfiguration sonar_conf = Hudson.instance.getDescriptorByType(SonarGlobalConfiguration.class)
def sonar_inst = new SonarInstallation(
    "sonar", // name, 
    "http://sonar:9000", // serverUrl, 
    SQServerVersions.SQ_5_3_OR_HIGHER, 
    "0999bb04f233f8473eab3d62a2ebac60eb614451", // serverAuthenticationToken,
    "", // databaseUrl, 
    "", // databaseLogin, 
    "", // databasePassword,
    "", // mojoVersion, 
    "", // additionalProperties, 
    new TriggersConfig(), //TriggersConfig triggers,
    "", // sonarLogin, 
    "", // sonarPassword, 
    "" // additionalAnalysisProperties
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