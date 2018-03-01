# Continuous Integration with Docker Compose (CIDC)

## Components

* User management/LDAP: [OpenDJ](https://forgerock.github.io/opendj-community-edition/) |  [Docker Image](https://github.com/ghchinoy/forgerock-docker)
* Single Sign On: [Keycloak](http://www.keycloak.org/) | [Docker Image](https://hub.docker.com/r/jboss/keycloak/)
	* [Postgresql DB](https://www.postgresql.org/) |  [Docker Image](https://hub.docker.com/_/postgres/)
	* [PGAdmin4](https://www.pgadmin.org/download/pgadmin-4-windows/) |  [Docker Image](https://hub.docker.com/r/chorss/docker-pgadmin4/)
* Documentation site: [MkDocs](http://www.mkdocs.org/), [MKDocs Material](https://squidfunk.github.io/mkdocs-material/getting-started/) 
* Repository manager: [Nexus 3](http://www.sonatype.org/nexus/category/nexus-3/) |  [Docker Image](https://github.com/cavemandaveman/nexus)
* CI/Build Server: [Jenkins](https://jenkins.io/) |  [Docker Image](https://hub.docker.com/r/jenkins/jenkins/)
* Code Quality: [SonarQube](https://www.sonarqube.org/) |  [Docker Image](https://hub.docker.com/_/sonarqube/)
* Docker UI: [Portainer](https://portainer.readthedocs.io/en/stable/) |  [Docker Image](https://hub.docker.com/r/portainer/portainer/)

## How to use

### Pull & Build 

```bash
./build.sh
```

#### warning

* for some images you will require either a proxy or access to the internet 
* enable wifi worksquare-guest
* rerun ```./build.sh```
* then you will fail on the q-nexus-3 prefix, so return to worksquare network
* repeate until you have all the images downloaded and build
* yes this is cumbersome, but is a **one-time** process
### Start

Make sure you have this repository checked out.

```bash
./start.sh
```

That should build and start all the containers.

Once this finishes, you can now login into SonarQube via keycloak:

* Go to [Jenkins](http://localhost:8282/jenkins)
* Click login (top right) and select OpenID as login provider
* Username: barbossa
* Password: jack
* This should redirect you as a logged in user!


### Stop

```bash
./stop.sh
```

### Clean up all resources

If you want to clean up all the resources related to this docker compose stack, you can do so with ```docker-compose down```.

Though experience shows this doesn't always remove _all_ resources, so there's a convenience script.

```bash
./clean-all.sh
```

## How to access the services

For figuring out where the services can be accessed, take a look at the portainer UI at [localhost:9000](http://localhost:9000).

Go to "Containers", filter on "running" only and you should see several containers with the "cidc" prefix.
On the right you can see their ports and click on them to be opened.

**Jenkins runs on /jenkins**
**SonarQube runs on /sonar**

Or, you can run the utility application and open its webpage in a browser at [localhost:8087/](http://localhost:8087/).

```bash
cicd-sandbox-util.exe -action serve
```

## Single Sign On (with Keycloak)

We use Keycloak for Single Sign On.

The default credentials for Keycloak is ```admin``` ```admin```.

For more information: [localhost:8282](http://localhost:8282) or go to the raw [docs](docs/docs/index.md)
