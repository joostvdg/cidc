# CIDC

## Single Sign On (with Keycloak)

We use Keycloak for Single Sign On.

We need the following configurations in Keycloak:

* Realm (create realm ci)
* Connection to LDAP
* Client for Jenkins
* Client for the Docs
* Client for Sonar
* Create a group
* Create a user
* Add user to the group

The default credetials for Keycloak is ```admin``` ```admin```.

### Docs

* Enabled: on
* Client Protocol: opend-connect
* Access type: confidential
* Standard flow enabled: on
* Direct access grants enabled: on
* Service accounts enabled: on
* Authorization enabled: on
* Valid Redirect URI's: http://localhost:8281/*

Make sure to update the docs environment variables to reflect the clientId and clientSecret.

### Jenkins

* [Jenkins Keycloak Plugin](https://wiki.jenkins.io/display/JENKINS/keycloak-plugin)

#### Keycloak config

* Enabled: on
* Client Protocol: opend-connect
* Standard flow enabled: on
* Direct access grants enabled: on
* Valid Redirect URI's: http://localhost:8282/jenkins/*

#### Jenkins config

```json
{
  "realm": "ci",
  "auth-server-url": "http://172.17.0.1:8280/auth",
  "ssl-required": "external",
  "resource": "jenkins",
  "public-client": true
}
```

#### Warning

```"confidential-port": 0```

Crashes Jenkins!

### Sonar

#### Sonar Configuration

* [Sonar OPIC Plugin](https://github.com/vaulttec/sonar-auth-oidc)
* **Set Sonar Server Base URL** before enabling the OpenID connect!
* **WITHOUTH TRAILING SLASH**
	* Good: localhost:9000/sonar
	* Bad: localhost:9000/sonar/

```json
{
	"issuer": "http://172.17.0.1:8280/auth/realms/ci",
	"authorization_endpoint": "http://172.17.0.1:8280/auth/realms/ci/protocol/openid-connect/auth",
	"token_endpoint": "http://172.17.0.1:8280/auth/realms/ci/protocol/openid-connect/token",
	"token_introspection_endpoint": "http://172.17.0.1:8280/auth/realms/ci/protocol/openid-connect/token/introspect",
	"userinfo_endpoint": "http://172.17.0.1:8280/auth/realms/ci/protocol/openid-connect/userinfo",
	"end_session_endpoint": "http://172.17.0.1:8280/auth/realms/ci/protocol/openid-connect/logout",
	"jwks_uri": "http://172.17.0.1:8280/auth/realms/ci/protocol/openid-connect/certs",
	"check_session_iframe": "http://172.17.0.1:8280/auth/realms/ci/protocol/openid-connect/login-status-iframe.html",
	"grant_types_supported": ["authorization_code", "implicit", "refresh_token", "password", "client_credentials"],
	"response_types_supported": ["code", "none", "id_token", "token", "id_token token", "code id_token", "code token", "code id_token token"],
	"subject_types_supported": ["public", "pairwise"],
	"id_token_signing_alg_values_supported": ["RS256"],
	"userinfo_signing_alg_values_supported": ["RS256"],
	"request_object_signing_alg_values_supported": ["none", "RS256"],
	"response_modes_supported": ["query", "fragment", "form_post"],
	"registration_endpoint": "http://172.17.0.1:8280/auth/realms/ci/clients-registrations/openid-connect",
	"token_endpoint_auth_methods_supported": ["private_key_jwt", "client_secret_basic", "client_secret_post"],
	"token_endpoint_auth_signing_alg_values_supported": ["RS256"],
	"claims_supported": ["sub", "iss", "auth_time", "name", "given_name", "family_name", "preferred_username", "email"],
	"claim_types_supported": ["normal"],
	"claims_parameter_supported": false,
	"scopes_supported": ["openid", "offline_access"],
	"request_parameter_supported": true,
	"request_uri_parameter_supported": true
}
```


## Nexus 3

Nexus 3 does not support openidc in the opensource version.

So we will connect to LDAP directly: https://help.sonatype.com/display/NXRM3/LDAP

### LDAP configuration

* Base DN: ou=people
* [unchecked] User subtree 
* Object class: inetOrgPerson
* UserID: uid
* Real Name: cn
* Email: mail
* Password: leave empty
* [check] Map LDAP Groups as role
* Group type: static
* Group base dn: ou=groups
* [check] use group subtree
* Group object class: groupOfUniqueNames
* Group ID: cn
* Group member attribute: uniqueMember
* Group member format: ${dn}

## LDAP

* url: ldap://ldap:1389 (internal within the compose group)
* base dn: dc=example,dc=com
* users dn: ou=users,dc=example,dc=com
* bind dn: cn=Directory Manager
* password: password

### LDAP vieuwer

* [Apache DS Studio](https://directory.apache.org/studio/download/download-linux.html)