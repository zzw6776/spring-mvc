# spring-mvc-mybatis

##简介
* spring-mvc集成了shiro，mybatis，hibernate，mongodb,所有对xml的引用都在[spring-config.xml](https://github.com/zzw6776/spring-mvc/blob/master/src/main/resources/spring-config.xml)里  
* 有空再集成activiti
* 因为公司里大多只用mybatis开发,所以该项目用hibernate比较多,稍微平衡下hibernate和mybatis的知识储备
* 工作以来有个习惯,大部分看过的教程都会保存下来,如果你有兴趣,可以点击[这里](http://localhost/course/)查看
* 其实判定一个人是不是大神,不是看他会不会开发,而是应该看他开发的时间

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
###[web.xml](https://github.com/zzw6776/spring-mvc/blob/master/src/main/webapp/WEB-INF/web.xml)
* 默认加载[spring-config.xml](https://github.com/zzw6776/spring-mvc/blob/master/src/main/resources/spring-config.xml)和[springservlet-config.xml](https://github.com/zzw6776/spring-mvc/blob/master/src/main/resources/springservlet-config.xml)两个xml,加载顺序参见[web.xml 中的listener、 filter、servlet 加载顺序及其详解 - xusir - 博客园](http://localhost/course/springmvc/web.xml%20中的listener、%20filter、servlet%20加载顺序及其详解%20-%20xusir%20-%20博客园.mhtml)文章
* ```<mime-mapping>```用来支持mhtml格式,每个格式对应的```<mime-mapping>```参见[MIME Types - The Complete List](http://localhost/course/springmvc/MIME%20Types%20-%20The%20Complete%20List.mhtml)文章
* 在```DefaultServlet```添加
```xml
        <init-param>  
            <param-name>listings</param-name>  
            <param-value>true</param-value>  
        </init-param>  
		```
实现静态资源的列表展示

###[springservlet-config.xml](https://github.com/zzw6776/spring-mvc/blob/master/src/main/resources/springservlet-config.xml)

* 多视图映射,详情见[SpringMVC同时支持多视图][manyView]文章
[manyView]:http://localhost/course/springmvc/SpringMVC同时支持多视图(JSP,Velocity,Freemarker等)的一种思路实现%20-%20一片相思林%20-%20博客园.mhtml
* 上传文件支持,详情见[SpringMVC 文件上传配置，多文件上传](http://localhost/course/springmvc/SpringMVC 文件上传配置，多文件上传，使用的MultipartFile - SwingLife的专栏 - 博客频道 - CSDN.NET.mhtml)文章

###[shiro-config.xml](https://github.com/zzw6776/spring-mvc/blob/master/src/main/resources/shiro-config.xml)

* 不多做介绍,[开涛的博客](http://localhost/course/shiro/跟我学Shiro目录贴 - 开涛的博客 - ITeye技术网站.mhtml)已经讲的很清楚.


###[mybatis-config.xml](https://github.com/zzw6776/spring-mvc/blob/master/src/main/resources/mybatis-config.xml)
* 添加了abel533的[Mapper](https://github.com/abel533/Mapper),真是神器
* 其他说明参见注释及该[路径](http://localhost/course/mybatis/)下的文章或自行google

###[mongodb-context.xml](https://github.com/zzw6776/spring-mvc/blob/master/src/main/resources/mongodb-context.xml)
* 其他说明参见注释及该[路径](http://localhost/course/mongo/)下的文章或自行google



###[hibernate-config.xml](https://github.com/zzw6776/spring-mvc/blob/master/src/main/resources/hibernate-config.xml)
* 其他说明参见注释及该[路径](http://localhost/course/hibernate/)下的文章或自行google


###[dataSource-config.xml](https://github.com/zzw6776/spring-mvc/blob/master/src/main/resources/dataSource-config.xml)
* 因为整合了mybatis和hibernate,所以事务是个问题,分开测试的情况下事务为正常情况,当一个service同时调用mybatis的dao和hibernate的dao时没做测试,贴上前人的研究成果[Spring,Hibernate,Mybatis,JDBC事务之间的的关系](http://localhost/course/mybatis/Spring,Hibernate,Mybatis,JDBC事务之间的的关系 - whaon - 开源中国社区.mhtml)
* 其他说明参见注释及该[路径](http://localhost/course/dataSource/)下的文章或自行google






每日记事本：
http://zzw6776.tk/html/editor.html

http://zzw6776.tk/html/editor-mobile.html

http://zzw6776.tk/html/editorList.html


参数统一为
user   用户名
key    加密密钥


登陆：

http://zzw6776.tk/html/login.html
http://zzw6776.tk/html/register.html
