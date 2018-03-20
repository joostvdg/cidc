node {
    // define the secrets and the env variables
    def secrets = [
        [$class: 'VaultSecret', path: 'secret/hello', secretValues: [
            [$class: 'VaultSecretValue', envVar: 'testing', vaultKey: 'value']]
        ]
    ]

    // optional configuration, if you do not provide this the next higher configuration
    // (e.g. folder or global) will be used
    // def configuration = [$class: 'VaultConfiguration',
    //                      vaultUrl: 'http://my-very-other-vault-url.com',
    //                      vaultCredentialId: 'my-vault-cred-id']
    // inside this block your credentials will be available as env variables
    //  configuration: configuration
    wrap([$class: 'VaultBuildWrapper', vaultSecrets: secrets]) {
        sh 'echo $testing'
    }
}