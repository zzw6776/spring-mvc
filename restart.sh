#!/bin/bash
echo -e "*********************** start git pull ***********************"
git pull
echo -e "*********************** start MV ***********************"
mv -bf target/springMVC-demo.war ~/apache-tomcat-7.0.69/webapps/ROOT.war
echo -e "*********************** start RM ***********************"
rm -rf ~/apache-tomcat-7.0.69/webapps/ROOT
echo -e "*********************** start tomcat ***********************"
~/apache-tomcat-7.0.69/bin/shutdown.sh
echo -e "*********************** start tomcat ***********************"
~/apache-tomcat-7.0.69/bin/startup.sh


