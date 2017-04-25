#!/bin/bash
id=$(ps -ef|grep tomcat |grep -v grep|awk '{print $2}')
if [ ! $id ]; then
        ~/spring-mvc/restart.sh
fi