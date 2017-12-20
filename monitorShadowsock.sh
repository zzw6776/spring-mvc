#!/bin/bash
#id=$(ps -ef|grep ssserver |grep -v grep|awk '{print $2}')
id=$(ps -ef|grep server.py |grep -v grep|awk '{print $2}')
if [ ! $id ]; then
        #/etc/init.d/shadowsocks restart
        /root/shadowsocksr/shadowsocks/logrun.sh
fi
