#!/bin/bash
# Runs apt-get apache2 in foreground, much like https://github.com/docker-library/httpd
source /etc/apache2/envvars
rm -f $APACHE_LOG_DIR/*.log
ln -s /proc/self/fd/1 $APACHE_LOG_DIR/access.log
ln -s /proc/self/fd/2 $APACHE_LOG_DIR/error.log

# ALLOW TO BE SET VIA DOCKER SECRETS
# if [ -f "${USER_NAME_SECRET}" ]; then
#     read USR < ${USER_NAME_SECRET}    
#     COMMAND_OPTIONS="${COMMAND_OPTIONS} -username $USR"
# fi

# if [ -f "${PASSWORD_SECRET}" ]; then
#     read PSS < ${PASSWORD_SECRET}
#     export PSS
#     COMMAND_OPTIONS="${COMMAND_OPTIONS} -passwordEnvVariable PSS"
# fi


# OIDC_PASS_PHRASE=""\ 
# OIDC_METADATA_URL=""\
# OIDC_CLIENT_ID=""\
# OIDC_CLIENT_SECRET=""\
# OIDC_REDIRECT_URL=""\
# OIDC_REMOTE_USER_CLAIM="preferred_username"

sed \
-e "s|OIDC_PASS_PHRASE|${OIDC_PASS_PHRASE}|g" \
-e "s|OIDC_METADATA_URL|${OIDC_METADATA_URL}|g" \
-e "s|OIDC_CLIENT_ID|${OIDC_CLIENT_ID}|g" \
-e "s|OIDC_CLIENT_SECRET|${OIDC_CLIENT_SECRET}|g" \
-e "s|OIDC_REDIRECT_URL|${OIDC_REDIRECT_URL}|g" \
-e "s|OIDC_REMOTE_USER_CLAIM|${OIDC_REMOTE_USER_CLAIM}|g" \
-i "${DEFAULT_SITE_LOC}/${DEFAULT_SITE}"


apache2 -DFOREGROUND