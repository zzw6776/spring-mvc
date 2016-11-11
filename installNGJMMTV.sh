#!/bin/bash
yum update -y
echo '一键安装shadowsock'

wget --no-check-certificate https://raw.githubusercontent.com/teddysun/shadowsocks_install/master/shadowsocks-libev.sh
chmod +x shadowsocks-libev.sh
./shadowsocks-libev.sh 2>&1 | tee shadowsocks-libev.log


echo '安装net-speeder'

wget https://github.com/snooda/net-speeder/archive/master.zip
unzip master.zip
wget http://dl.fedoraproject.org/pub/epel/6/x86_64/epel-release-6-8.noarch.rpm
rpm -ivh epel-release-6-8.noarch.rpm
yum install -y libnet libpcap libnet-devel libpcap-devel   
cd net-speeder-master
sh build.sh
cd ~




echo '安装git'
yum update -y
yum install -y curl-devel expat-devel gettext-devel  openssl-devel zlib-devel  gcc
wget https://github.com/git/git/archive/v2.10.2.tar.gz
tar -zxf v2.10.2.tar.gz
cd git-2.10.2
make prefix=/usr/local/git all
make prefix=/usr/local/git install

echo 'export PATH=/usr/local/git/bin:$PATH' >>/etc/profile
 

echo '添加开机自启动'


echo 'nohup ~/net-speeder-master/net_speeder venet0 "ip" >/dev/null 2>&1 &
source /etc/profile
~/spring-mvc/restart.sh' >>/etc/rc.local



echo '安装java安装mvn'
wget --no-cookie --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u111-b14/jdk-8u111-linux-x64.tar.gz -O jdk-8u111-linux-x64.tar.gz
tar -zxvf jdk-8u111-linux-x64.tar.gz
mv jdk1.8.0_111 /usr/local/
wget http://mirror.bit.edu.cn/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
tar -xvf  apache-maven-3.3.9-bin.tar.gz
mv  apache-maven-3.3.9 /usr/local/
echo 'export JAVA_HOME=/usr/local/jdk1.8.0_111
export CLASSPATH=.:$JAVA_HOME/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
PATH=$PATH:$JAVA_HOME/bin:$JAVA_HOME/jre/bin
MAVEN_HOME=/usr/local/apache-maven-3.3.9
export MAVEN_HOME
export PATH=${PATH}:${MAVEN_HOME}/bin
' >>/etc/profile

echo '安装mysql'
yum install -y mysql-server mysql mysql-devel
chkconfig mysqld on
service mysqld start
mysqladmin -u root password ''
source /etc/profile

echo '安装tomcat'
http://mirrors.tuna.tsinghua.edu.cn/apache/tomcat/tomcat-7/v7.0.72/bin/apache-tomcat-7.0.72.tar.gz
tar -xvf apache-tomcat-7.0.72.tar.gz

cd ~
git clone https://github.com/zzw6776/spring-mvc.git


echo "begin to install VPN services";
#check wether vps suppot ppp and tun

yum remove -y pptpd ppp
iptables --flush POSTROUTING --table nat
iptables --flush FORWARD
rm -rf /etc/pptpd.conf
rm -rf /etc/ppp

arch=`uname -m`



yum -y install make libpcap iptables gcc-c++ logrotate tar cpio perl pam tcp_wrappers 
rpm -ivh ~/spring-mvc/rpm/dkms-2.0.17.5-1.noarch.rpm
rpm -ivh ~/spring-mvc/rpm/kernel_ppp_mppe-1.0.2-3dkms.noarch.rpm
rpm -Uvh ~/spring-mvc/rpm/ppp-2.4.5-17.0.rhel6.x86_64.rpm
rpm -ivh ~/spring-mvc/rpm/pptpd-1.3.4-2.el6.x86_64.rpm

mknod /dev/ppp c 108 0 
echo 1 > /proc/sys/net/ipv4/ip_forward 
echo "mknod /dev/ppp c 108 0" >> /etc/rc.local
echo "echo 1 > /proc/sys/net/ipv4/ip_forward" >> /etc/rc.local
echo "localip 172.16.36.1" >> /etc/pptpd.conf
echo "remoteip 172.16.36.2-254" >> /etc/pptpd.conf
echo "ms-dns 8.8.8.8" >> /etc/ppp/options.pptpd
echo "ms-dns 8.8.4.4" >> /etc/ppp/options.pptpd


read -p '输入账号' user 


read -p '输入密码' pass




echo "${user} pptpd ${pass} *" >> /etc/ppp/chap-secrets

iptables -t nat -A POSTROUTING -s 172.16.36.0/24 -j SNAT --to-source `ifconfig  | grep 'inet addr:'| grep -v '127.0.0.1' | cut -d: -f2 | awk 'NR==1 { print $1}'`
iptables -A FORWARD -p tcp --syn -s 172.16.36.0/24 -j TCPMSS --set-mss 1356
service iptables save

chkconfig iptables on
chkconfig pptpd on

service iptables start
service pptpd start

echo "VPN service is installed, your VPN username is zzw, VPN password is ${pass}"







echo '删除无用文件'
rm -rf master.zip shadowsocks-libev.sh epel-release-6-8.noarch.rpm git-2.10.2  v2.10.2.tar.gz apache-tomcat-7.0.72.tar.gz





mysql -uroot
use mysql
insert into mysql.user(Host,User,Password) values("%","root",password("密码"));
grant all on *.* to root@% identified by "密码";
flush privileges;
create database zzw
