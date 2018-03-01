#!/usr/bin/env bash
echo '############# S T A R T ################'
echo '########################################'
echo '########################################'
echo '##### ABN AMRO - CICD Sandbox Start'
echo '########################################'
echo '####### GENERATE CONFIGURATION #########'
echo '########################################'
# Use Go util to setup machine specific config for Jenkins/Sonar/...?
./cicd-sandbox-util.exe
HOSTNAME=$(hostname)
./scripts/prepare-jenkins-config.sh
echo '########################################'
echo '########################################'
echo '####### DOCKER COMPOSE BUILD  ##########'
echo '########################################'
docker-compose build
echo '########################################'
echo '########################################'
echo '########################################'
echo '####### DOCKER COMPOSE UP  #############'
echo '########################################'
docker-compose -p sb up -d
echo '########################################'
echo '########################################'
echo '####### WAITING FOR SONAR TO START #####'
echo '####### 180 seconds (patience!)'
# Wait and then do sonar init for the security config
# "State": {
#             "Status": "running",
# STATUS=$(docker inspect --format '{{.State.Status}}' sb_sonar_1)
sleep 60
echo '####### 120 seconds left'
sleep 60
echo '####### 60 seconds left'
sleep 30
echo '####### 30 seconds left'
sleep 30
echo '########################################'
echo '####### I N I T - S O N A R ############'
RUNNING=`docker ps | grep -c sb_sonar_1`
if [ $RUNNING -gt 0 ]
then
   echo "Sonar is up"
   ./cicd-sandbox-util.exe --action init-sonar
else
    echo "Sonar is down, lets try again after 30 seconds"
    sleep 30
    RUNNING=`docker ps | grep -c sb_sonar_1`
    if [ $RUNNING -gt 0 ]
    then
        echo "Sonar is up"
        ./cicd-sandbox-util.exe --action init-sonar
    else
        echo "Sonar is down, we give up"
    fi
fi
echo '########################################'
echo '########################################'