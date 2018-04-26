#!groovy

import jenkins.model.*
import hudson.security.*
import jenkins.security.s2m.AdminWhitelistRule

def instance = Jenkins.getInstance()

println "#######################################"
println "### Configuring Default User - START ##"

def user = "admin"
def pass = "admin"

println "# Creating user " + user + "..."

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount(user, pass)
instance.setSecurityRealm(hudsonRealm)

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)
instance.save()

Jenkins.instance.getInjector().getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false)

println "# User " + user + " was created"
User adminUser = User.getById(user, false)
List<UserProperty>  properties = adminUser.getAllProperties()

User adminUser = User.getById('admin', false)
List<UserProperty>  properties = adminUser.getAllProperties()

for(UserProperty prop : properties) {
    // println "Prop=${prop}"
    if (prop instanceof jenkins.security.ApiTokenProperty) {
        def token = prop.getApiToken()
        println "# API TOKEN=${token}"
    }
}

println "### Configuring Default User - END   ##"
println "#######################################"