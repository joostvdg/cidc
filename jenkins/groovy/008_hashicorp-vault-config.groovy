import com.datapipe.jenkins.vault.credentials.VaultTokenCredential
import com.datapipe.jenkins.vault.configuration.GlobalVaultConfiguration
import com.datapipe.jenkins.vault.configuration.VaultConfiguration
import com.cloudbees.plugins.credentials.domains.Domain
import com.cloudbees.plugins.credentials.CredentialsScope
import com.cloudbees.plugins.credentials.Credentials
import hudson.util.Secret

def secret = Secret.fromString('2Q2tvgz8Puqy1w9D')
def vaultCredentialsId = 'vault-token'
def vaultCredentialsDesc = 'vault token'
Credentials vaultCred = new VaultTokenCredential(CredentialsScope.GLOBAL, vaultCredentialsId, vaultCredentialsDesc, secret) as Credentials
def globalDomain = Domain.global()

def credentials_store = jenkins.model.Jenkins.instance.getExtensionList(
        'com.cloudbees.plugins.credentials.SystemCredentialsProvider'
        )

println "vaultTokenCred: ${vaultCred}"
println "credentials_store: ${credentials_store}"
credentials_store[0].addCredentials(globalDomain, vaultCred.asType(Credentials.class))
credentials_store.each {  println "credentials_store.each: ${it}" }

// String vaultUrl, String vaultCredentialId
def vaultUrl = "http://vault:8200"

VaultConfiguration vaultConfiguration = new VaultConfiguration(vaultUrl, vaultCredentialsId)

// GlobalVaultConfiguration
GlobalVaultConfiguration vault_conf = Hudson.instance.getDescriptorByType(GlobalVaultConfiguration.class)
vault_conf.setConfiguration(vaultConfiguration) // it calls save when given configuration
