docker-compose down

docker rm -f sb_docs_1
docker rm -f sb_jenkins-slave_1
docker rm -f sb_jenkins_1
docker rm -f sb_keycloak_1
docker rm -f sb_pgadmin4_1
docker rm -f sb_nexus_1
docker rm -f sb_ldap_1
docker rm -f sb_sonar_1
docker rm -f sb_portainer_1
docker rm -f sb_postgres_1

docker volume rm sb_artifactory_data
docker volume rm sb_jenkins_data
docker volume rm sb_jenkins_slave_workspace
docker volume rm sb_nexus_data
docker volume rm sb_pg_data
docker volume rm sb_pgadmin_data
docker volume rm sb_portainer_data
docker volume rm sb_sonar_data