#1 user, #2 password, #3 server

USER_DEFAULT="admin"
USER="${1:-$USER_DEFAULT}"

PASS_DEFAULT="admin"
PASS="${2:-$PASS_DEFAULT}"

SERVER_DEFAULT="http://localhost:8282/jenkins"
SERVER="${3:-$SERVER_DEFAULT}"

curl -k -u $USER:$PASS -p -sSL "${SERVER}/pluginManager/api/xml?depth=1&xpath=/*/*/shortName|/*/*/version&wrapper=plugins" | perl -pe 's/.*?<shortName>([\w-]+).*?<version>([^<]+)()(<\/\w+>)+/\1 \2\n/g'|sed 's/ /:/' > plugins-new.txt
