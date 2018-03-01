# Assumes context root is root of git repo
KEYCLOAK_HOST=${HOSTNAME,,}
sed "s/KEYCLOAK_HOST/${KEYCLOAK_HOST}/" jenkins/config/keycloak.xml > jenkins/config/keycloak-local.xml