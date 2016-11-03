#!/bin/bash
echo -e "*********************** start git pull ***********************"
git pull
echo -e "*********************** start mvn clean ***********************"
mvn clean
echo -e "*********************** start mvn package ***********************"
mvn package -DskipTests
echo -e "*********************** start MV ***********************"
mv -bf target/springMVC-demo.war ~/apache-tomcat-7.0.69/webapps/ROOT.war
echo -e "*********************** start RM ***********************"
rm -rf ~/apache-tomcat-7.0.69/webapps/ROOT
echo -e "*********************** stop tomcat ***********************"
#~/apache-tomcat-7.0.69/bin/shutdown.sh
id=$(ps -ef|grep java |grep -v grep|awk '{print $2}')
kill -9 $id
echo -e "*********************** start tomcat ***********************"
~/apache-tomcat-7.0.69/bin/startup.sh

service mysqld restart
