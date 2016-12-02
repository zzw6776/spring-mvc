#!/bin/bash
cd ~/spring-mvc
echo -e "*********************** start git pull ***********************"
git pull
echo -e "*********************** start mvn clean ***********************"
mvn clean
echo -e "*********************** start mvn package ***********************"
mvn package -DskipTests
echo -e "*********************** start MV ***********************"
mv -bf target/springMVC-demo.war /usr/local/tomcat/webapps/ROOT.war
echo -e "*********************** start RM ***********************"
rm -rf /usr/local/tomcat/webapps/ROOT
echo -e "*********************** stop tomcat ***********************"
#~/apache-tomcat-7.0.72/bin/shutdown.sh
id=$(ps -ef|grep java |grep -v grep|awk '{print $2}')
kill -9 $id
echo -e "*********************** start tomcat ***********************"
/usr/local/tomcat/bin/startup.sh

service mysqld restart
