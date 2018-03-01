# How to use

```bash
docker-compose up --build
```



## Keycloak

See ci-realm-export.json

## Jenkins

```json
{
  "realm": "ci",
  "auth-server-url": "http://localhost:8280/auth",
  "ssl-required": "external",
  "resource": "jenkins",
  "public-client": true
}
```

## Sonar


## TODO

* configure keycloak for Jenkins
    * manual test
    * automate it
* configure keycloak for Sonar
    * manual test
    * automate it
* configure ldap for nexus 3
    * manual test
    * automate it
* use correct ldap data
* determine host name
* create transform script for translating hostname in keycloak configs
* read data from docker daemon about running containers
    * introduce labels:
        * com.abnamro.cicd.name
        * com.abnamro.cicd.path
        * com.abnamro.cicd.port
* create a UI for container info
