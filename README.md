# Continuous Integration with Docker Compose (CIDC)

## Components

* Single Sign On: [Keycloak](http://www.keycloak.org/) [Docker Image](https://hub.docker.com/r/jboss/keycloak/)
	* [Postgresql DB](https://www.postgresql.org/) [Docker Image](https://hub.docker.com/_/postgres/)
	* [PGAdmin4](https://www.pgadmin.org/download/pgadmin-4-windows/) [Docker Image](https://hub.docker.com/r/chorss/docker-pgadmin4/)
* Documentation site: [MkDocs](http://www.mkdocs.org/), [MKDocs Material](https://squidfunk.github.io/mkdocs-material/getting-started/) 
* Repository manager: [Nexus 3](http://www.sonatype.org/nexus/category/nexus-3/) [Docker Image](https://github.com/cavemandaveman/nexus)
* CI/Build Server: [Jenkins](https://jenkins.io/) [Docker Image](https://hub.docker.com/r/jenkins/jenkins/)
* Code Quality: [SonarQube](https://www.sonarqube.org/) [Docker Image](https://hub.docker.com/_/sonarqube/)
* Git Source Code Management: [Gogs](https://gogs.io/) [Docker Image](https://hub.docker.com/r/gogs/gogs/)
* User management/LDAP: [OpenDJ](https://forgerock.github.io/opendj-community-edition/) [Docker Image](https://github.com/ghchinoy/forgerock-docker)
* Docker UI: [Portainer](https://portainer.readthedocs.io/en/stable/) [Docker Image](https://hub.docker.com/r/portainer/portainer/)


## How to use

Make sure you have this repository checked out.

```bash
docker-compose up --build
```

That should bring up all the containers.
For figuring out where the services can be accessed, take a look at the portainer UI at [localhost:9000](http://localhost:9000).

Go to "Containers", filter on "running" only and you should see several containers with the "cidc" prefix.
On the right you can see their ports and click on them to be opened.

**Jenkins runs on /jenkins**
**SonarQube runs on /sonar**

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
* Connect nexus 3 to LDAP
* Connect portainer to LDAP

The default credetials for Keycloak is ```admin``` ```admin```.

For more information: [localhost:8282](http://localhost:8282) or go to the raw [docs](docs/docs/index.md)
