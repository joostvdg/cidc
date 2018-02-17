#!/bin/bash
#1 user, #2 server

USER_DEFAULT="root"
USER="${1:-$USER_DEFAULT}"

SERVER_DEFAULT="http://localhost:8080"
SERVER="${2:-$SERVER_DEFAULT}"

curl -k -u $USER -p -sSL "${SERVER}/pluginManager/api/xml?depth=1&xpath=/*/*/shortName|/*/*/version&wrapper=plugins" | perl -pe 's/.*?<shortName>([\w-]+).*?<version>([^<]+)()(<\/\w+>)+/\1 \2\n/g'|sed 's/ /:/' > plugins-new.txt
