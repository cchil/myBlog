# myBlog

#### 项目介绍
轻量级基于Spring Boot的博客系统

分为前端展示和后端管理

设计风格整体简约-采用Amaze UI 开源博客模板

文章发布设计两大编辑器，支持Markdown与普通富文本编辑

支持整体页面定制化操作，提供定制化操作模板配置管理页

个人资源支持本地上传与外链引入

文件服务器可选本地服务与第三方存储（已接入七牛云）

使用自动配置脚本，只需简单的数据库与文件服务器配置即可一键配置并启动服务器



![后台管理页面](/img/QQ截图20180601160513.png)

![前端展示](/img/QQ截图20180601160957.png)

![](/img/QQ截图20180601161122.png)

#### 软件架构

基于SpringBoot框架搭建的博客系统

项目采用Mysql作为数据存储

使用 Ehcache 结合Mybatis 缓存数据 提高读取性能

页面渲染使用Thymeleaf

Shiro作为后端管理的安全框架

引入七牛云SDK 配置文件服务器



前端主要框架使用：

Amaze UI

Bootstrap 4

Angular JS

Jquery

UEditor

Editor.md

依赖库：

```xml
<dependencies>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
		 <!--Mysql-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.9</version>
        </dependency>

        <!--Shiro的依赖-->
        <!-- https://mvnrepository.com/artifact/org.apache.shiro/shiro-core -->
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-core</artifactId>
            <version>1.4.0</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.apache.shiro/shiro-spring -->
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-spring</artifactId>
            <version>1.4.0</version>
        </dependency>

        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
        </dependency>

        <!--<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>-->

        <!-- mybatis pager分页工具 -->

        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
            <version>4.1.0</version>
            <exclusions>
                <exclusion>
                    <groupId>org.mybatis</groupId>
                    <artifactId>mybatis</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <dependency>
            <groupId>com.github.miemiedev</groupId>
            <artifactId>mybatis-paginator</artifactId>
            <version>1.2.17</version>
        </dependency>

        <dependency>
            <groupId>com.github.jsqlparser</groupId>
            <artifactId>jsqlparser</artifactId>
            <version>0.9.4</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/net.sf.ehcache/ehcache -->
        <dependency>
            <groupId>net.sf.ehcache</groupId>
            <artifactId>ehcache</artifactId>
            <version>2.10.5</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.mybatis.caches/mybatis-ehcache -->
        <dependency>
            <groupId>org.mybatis.caches</groupId>
            <artifactId>mybatis-ehcache</artifactId>
            <version>1.1.0</version>
        </dependency>


        <!-- https://mvnrepository.com/artifact/org.jsoup/jsoup -->
        <dependency>
            <groupId>org.jsoup</groupId>
            <artifactId>jsoup</artifactId>
            <version>1.11.3</version>
        </dependency>

        <dependency>
            <groupId>org.json</groupId>
            <artifactId>json</artifactId>
            <version>20180130</version>
        </dependency>

        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
        </dependency>

        <!--七牛云存储SDK-->
        <dependency>
            <groupId>com.qiniu</groupId>
            <artifactId>qiniu-java-sdk</artifactId>
            <version>[7.2.0, 7.2.99]</version>
            <exclusions>
                <exclusion>
                    <groupId>com.google.code.gson</groupId>
                    <artifactId>gson</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.6</version>

            </plugin>

            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>

    </build>
```


#### 安装教程

1. 使用Git 克隆到你的项目中

2. 直接下载 使用Maven构建你的项目

3. 配置 数据源 以及 文件服务器地址

4. 创建数据库：名为`blog` 执行项目中的`blog.sql`文件

5. 本地文件服务器简单测试配置  修改Tomcat conf/server.xml参考如下代码 增加一行

   ```xml
    <Host name="localhost"  appBase="webapps"
               unpackWARs="true" autoDeploy="true">
   	 <Context path="/root" docBase="D://test/fileUpload/"></Context>
           <Valve className="org.apache.catalina.valves.AccessLogValve" directory="logs"
                  prefix="localhost_access_log." suffix=".txt"
                  pattern="%h %l %u %t &quot;%r&quot; %s %b" />

         </Host>
   ```

   关键代码`<Context path="/root" docBase="D://test/fileUpload/"></Context>`

6. 启动项目

#### 使用说明

1. 默认后台管理员密码和账号 为小写admin
2. druid 默认登录密码和账号 为小写admin
3. 系统日志存放位置默认为根目录



#### 问题交流

​	欢迎加QQ群交流：**676436122**

​	欢迎大家访问我的主页：**http://www.uychen.com**