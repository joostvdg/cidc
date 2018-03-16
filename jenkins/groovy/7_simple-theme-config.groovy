#!groovy

import jenkins.model.*
import hudson.security.*
import hudson.PluginWrapper


def instance = Jenkins.getInstance()

println "####################################################"
println "### Configuring Simple Theme for Jenkins - START ##"

def cssThemeUrl = System.getProperty("THEME_CSS")

println "############# ENV VARIABLES ######################"
println "### THEME_CSS   :: ${cssThemeUrl}"
println "### Updated: 2018-03-15 12:15"

def simpleTheme = Jenkins.instance.getExtensionList(org.codefirst.SimpleThemeDecorator.class)[0]
simpleTheme.setCssUrl(cssThemeUrl)
simpleTheme.save()

println "### Configuring Simple Theme for Jenkins - END ###"
println "###################################################"
