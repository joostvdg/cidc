#!/bin/bash
FILE_DEFAULT="plugins.txt"
FILE="${1:-$FILE_DEFAULT}"
COMMANDS_FILE=plugin-install-commands.txt

if [ -e $COMMANDS_FILE ]; then
   rm $COMMANDS_FILE
fi
touch $COMMANDS_FILE

PLUGINS=""

while read -r line
do
    name="$line"
    PLUGINS="$PLUGINS $name"
done < "$FILE"

echo "$PLUGINS"

echo "RUN /usr/local/bin/install-plugins.sh $PLUGINS" >> $COMMANDS_FILE