# spring-mvc-mybatis
* 相遇既有缘，免费分享shadowsocks账号~
* [点击去获取账号](http://ss.mrzzw.tk)
* 邀请码在页面右上方链接自行获取，若没有请联系我(zzw6776@gmail.com)。


##简介
* spring-mvc集成了shiro，mybatis，hibernate，mongodb,所有对xml的引用都在[spring-config.xml](https://github.com/mrzzw/spring-mvc/blob/master/src/main/resources/spring-config.xml)里  
* 有空再集成activiti
* 工作以来有个习惯,大部分看过的教程都会保存下来,如果你有兴趣,可以点击[这里](http://mrzzw.tk/course/)查看
* 其实判定一个人是不是大神,不是看他会不会开发,而是应该看他开发的时间

###自用功能
* 每日记事本
    * [电脑端链接](http://mrzzw.tk/html/editor.html)
    * [手机端链接](http://mrzzw.tk/html/editor-mobile.html)
    * [历史记录查看](http://mrzzw.tk/html/editorList.html)
    * 参数统一为
```
user   用户名
key    加密密钥
```
当user不传时,系统默认取当前ip为用户名  
后台不记录密钥,内容采用AES加密

* 登录注册
	* [登录](http://mrzzw.tk/html/login.html)
	* [注册](http://mrzzw.tk/html/register.html)


##脚本介绍
* [installNGJMMTV.sh](https://github.com/mrzzw/spring-mvc/blob/master/installNGJMMTV.sh)
    * 一键安装shadowsock,net-speeder,git,java,maven,mysql,tomcat,PPTP(centos6.X)
	* 当初写这个脚本的时候不知道网上有[lnmp一键安装包](https://lnmp.org/),另外如果ss翻墙的话,建议使用[kcp](https://github.com/xtaci/kcptun),比net-speeder效果好很多

* [monitorShadowsock.sh](https://github.com/mrzzw/spring-mvc/blob/master/monitorShadowsock.sh)
    * 监测ss是否启动脚本

* [restart.sh](https://github.com/mrzzw/spring-mvc/blob/master/restart.sh)
    * 重启脚本




##用前须知
###浏览方式
    在线打开mhtml需使用fireFox或ie浏览器  
* fireFox请安装[unmht](http://www.unmht.org/unmht/en_index.html)以用来解析mhtml  
* ie可直接打开  
* chrome因[自身限制](https://developer.chrome.com/extensions/pageCapture)只能从本地浏览mhtml

###tomcat配置
在server.xml的```<Connector>```标签下添加编码支持
```xml
URIEncoding="UTF-8"
```

因为项目默认上传路径为项目部署的上级目录下的upload文件夹

所以需在```<Host>```标签下添加
```xml
<Context docBase="upload" path="/upload"/>
```

##配置简介
###[web.xml](https://github.com/mrzzw/spring-mvc/blob/master/src/main/webapp/WEB-INF/web.xml)
* 默认加载[spring-config.xml](https://github.com/mrzzw/spring-mvc/blob/master/src/main/resources/spring-config.xml)和[springservlet-config.xml](https://github.com/mrzzw/spring-mvc/blob/master/src/main/resources/springservlet-config.xml)两个xml,加载顺序参见[web.xml 中的listener、 filter、servlet 加载顺序及其详解 - xusir - 博客园](http://mrzzw.tk/course/springmvc/web.xml%20中的listener、%20filter、servlet%20加载顺序及其详解%20-%20xusir%20-%20博客园.mhtml)文章
* ```<mime-mapping>```用来支持mhtml格式,每个格式对应的```<mime-mapping>```参见[MIME Types - The Complete List](http://mrzzw.tk/course/springmvc/MIME%20Types%20-%20The%20Complete%20List.mhtml)文章
* 在```DefaultServlet```添加
```xml
        <init-param>  
            <param-name>listings</param-name>  
            <param-value>true</param-value>  
        </init-param>  
```
实现静态资源的列表展示

###[springservlet-config.xml](https://github.com/mrzzw/spring-mvc/blob/master/src/main/resources/springservlet-config.xml)

* 多视图映射,详情见[SpringMVC同时支持多视图][manyView]文章
[manyView]:http://mrzzw.tk/course/springmvc/SpringMVC同时支持多视图(JSP,Velocity,Freemarker等)的一种思路实现%20-%20一片相思林%20-%20博客园.mhtml
* 上传文件支持,详情见[SpringMVC 文件上传配置，多文件上传](http://mrzzw.tk/course/springmvc/SpringMVC 文件上传配置，多文件上传，使用的MultipartFile - SwingLife的专栏 - 博客频道 - CSDN.NET.mhtml)文章

###[shiro-config.xml](https://github.com/mrzzw/spring-mvc/blob/master/src/main/resources/shiro-config.xml)

* 不多做介绍,[开涛的博客](http://mrzzw.tk/course/shiro/跟我学Shiro目录贴 - 开涛的博客 - ITeye技术网站.mhtml)已经讲的很清楚.


###[mybatis-config.xml](https://github.com/mrzzw/spring-mvc/blob/master/src/main/resources/mybatis-config.xml)
* 添加了abel533的[Mapper](https://github.com/abel533/Mapper),真是神器
* 其他说明参见注释及该[路径](http://mrzzw.tk/course/mybatis/)下的文章或自行google

###[mongodb-context.xml](https://github.com/mrzzw/spring-mvc/blob/master/src/main/resources/mongodb-context.xml)
* 其他说明参见注释及该[路径](http://mrzzw.tk/course/mongo/)下的文章或自行google



###[hibernate-config.xml](https://github.com/mrzzw/spring-mvc/blob/master/src/main/resources/hibernate-config.xml)
* 其他说明参见注释及该[路径](http://mrzzw.tk/course/hibernate/)下的文章或自行google


###[dataSource-config.xml](https://github.com/mrzzw/spring-mvc/blob/master/src/main/resources/dataSource-config.xml)
* 因为整合了mybatis和hibernate,所以事务是个问题,分开测试的情况下事务为正常情况,当一个service同时调用mybatis的dao和hibernate的dao时没做测试,贴上前人的研究成果[Spring,Hibernate,Mybatis,JDBC事务之间的的关系](http://mrzzw.tk/course/mybatis/Spring,Hibernate,Mybatis,JDBC事务之间的的关系 - whaon - 开源中国社区.mhtml)
* 其他说明参见注释及该[路径](http://mrzzw.tk/course/dataSource/)下的文章或自行google

##工具类介绍
* [Dropbox.java](https://github.com/mrzzw/spring-mvc/blob/master/src/main/java/com/demo/util/Dropbox.java)
    * dropbox上传类,详情见[官方文档](https://github.com/dropbox/dropbox-sdk-java)

* [EncryptUtil.java](https://github.com/mrzzw/spring-mvc/blob/master/src/main/java/com/demo/util/EncryptUtil.java)
    * 加密解密工具类,包括AES加解密和MD5加密

* [ExpireJobTask.java](https://github.com/mrzzw/spring-mvc/blob/master/src/main/java/com/demo/util/ExpireJobTask.java)
    * 每日导出sql及备份到dropbox

* [FileUpload.java](https://github.com/mrzzw/spring-mvc/blob/master/src/main/java/com/demo/util/FileUpload.java)
    * 文件上传类,默认目录为项目上级目录的upload文件夹,含base64上传

* [RandomUtil.java](https://github.com/mrzzw/spring-mvc/blob/master/src/main/java/com/demo/util/RandomUtil.java)
     * 随机数生成

* [TypeUtil.java](https://github.com/mrzzw/spring-mvc/blob/master/src/main/java/com/demo/util/TypeUtil.java)
    * 类与类相同属性拷贝

* [LogAop.java](https://github.com/mrzzw/spring-mvc/blob/master/src/main/java/com/demo/util/LogAop.java)
    * aop日志实现
