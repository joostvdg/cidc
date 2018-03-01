#!groovy

import jenkins.model.*
import hudson.security.*
import hudson.PluginWrapper
import jenkins.security.s2m.AdminWhitelistRule
import org.jenkinsci.plugins.KeycloakSecurityRealm;

def instance = Jenkins.getInstance()


// def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
// instance.setAuthorizationStrategy(strategy)
// instance.save()

// Jenkins.instance.getInjector().getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false)
println "Configuring keycloak config 1"

org.kohsuke.stapler.StaplerRequest stapler = null
net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject()
jsonObject.put("realm", "ci")
jsonObject.put("auth-server-url", "http://172.17.0.1:8280/auth")
jsonObject.put("ssl-required", "external")
jsonObject.put("resource", "jenkins")
jsonObject.put("public-client", true)
net.sf.json.JSONObject keycloakJson = new net.sf.json.JSONObject()
keycloakJson.put("keycloakJson", jsonObject)
net.sf.json.JSONObject keycloak = new net.sf.json.JSONObject()
keycloak.put("keycloak", keycloakJson)


// SecurityRealm keycloak_realm = new org.jenkinsci.plugins.KeycloakSecurityRealm.DescriptorImpl().newInstance(stapler, jsonObject)
SecurityRealm keycloak_realm = new org.jenkinsci.plugins.KeycloakSecurityRealm()
println "Configuring keycloak config 2"
keycloak_realm.getDescriptor().configure(stapler, keycloak)
println "Configuring keycloak config 3"

// KeycloakSecurityRealm.DescriptorImpl descriptor = keycloak_realm.getDescriptor() as KeycloakSecurityRealm.DescriptorImpl
// descriptor.configure(stapler, jsonObject)
String keycloakJsonString = keycloak_realm.getDescriptor().getKeycloakJson()
println "Configuring keycloak config 4"
println "keycloakJsonString: " + keycloakJsonString

instance.setSecurityRealm(keycloak_realm)
instance.save()

// SecurityRealm keycloak_realm = new org.jenkinsci.plugins.KeycloakSecurityRealm()

// String keycloakJson = keycloak_realm.getKeycloakJson()
// println "keycloakJson: " + keycloakJson

// PluginWrapper wrapper = Jenkins.instance.pluginManager.getPlugin(KeycloakSecurityRealm)
// KeycloakSecurityRealm plugin = wrapper.getPlugin() as KeycloakSecurityRealm
// plugin.configure(stapler, jsonObject); 

// if (!keycloak_realm.equals(jenkins.getSecurityRealm())) {
//     // swap in the new Github Security realm (for an update)
//     jenkins.setSecurityRealm(keycloak_realm)
//     instance.save()
// }

// SecurityRealm keycloak_realm = new GithubSecurityRealm(oauthSettings.GITHUB_WEB_URI,
//                                                      oauthSettings.GITHUB_API_URI,
//                                                      oauthSettings.CLIENT_ID,
//                                                      oauthSettings.CLIENT_SECRET,
//                                                      oauthSettings.SCOPES
//                                                     )
//   "realm": "ci",
//   "auth-server-url": "http://172.17.0.1:8280/auth",
//   "ssl-required": "external",
//   "resource": "jenkins",
//   "public-client": true

// <?xml version='1.0' encoding='UTF-8'?>
// <org.jenkinsci.plugins.KeycloakSecurityRealm_-DescriptorImpl plugin="keycloak@2.0.3">
//  <keycloakJson>{
//        &quot;realm&quot;: &quot;ci&quot;,
//        &quot;auth-server-url&quot;: &quot;http://nllr4000294225.solon.prd:8280/auth&quot;,
//        &quot;ssl-required&quot;: &quot;external&quot;,
//        &quot;resource&quot;: &quot;jenkins&quot;,
//        &quot;public-client&quot;: true
// }</keycloakJson>
// </org.jenkinsci.plugins.KeycloakSecurityRealm_-DescriptorImpl>