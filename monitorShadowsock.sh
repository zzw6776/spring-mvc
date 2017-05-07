#!/bin/bash
id=$(ps -ef|grep ssserver |grep -v grep|awk '{print $2}')
if [ ! $id ]; then
        #/etc/init.d/shadowsocks restart
        /usr/local/shadowsocks/logrun.sh
fi
