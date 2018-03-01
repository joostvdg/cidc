#!groovy

import jenkins.model.*
import hudson.security.*
import hudson.PluginWrapper
import jenkins.security.s2m.AdminWhitelistRule
import org.jenkinsci.plugins.KeycloakSecurityRealm;

def instance = Jenkins.getInstance()

println "=== Configuring keycloak for Jenkins == Start"

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

SecurityRealm keycloak_realm = new org.jenkinsci.plugins.KeycloakSecurityRealm()
keycloak_realm.getDescriptor().configure(stapler, keycloak)
instance.setSecurityRealm(keycloak_realm)
instance.save()
println "=== Configuring keycloak for Jenkins == Finish"