# [SpringCloud](https://github.com/forezp/SpringCloudLearning)

## 一、[微服务架构](https://baike.baidu.com/item/%E5%BE%AE%E6%9C%8D%E5%8A%A1%E6%9E%B6%E6%9E%84/18705784?fr=aladdin)

微服务架构是一种在云中部署应用和服务的新技术。微服务可以在“自己的程序”中运行，并通过“轻量级设备与HTTP型API进行沟通”。关键在于该服务可以在自己的程序中运行，通过这一点，我么可以将服务公开与微服务架构（在现有系统中分布一个API）区分开来。在服务公开中。许多服务被内部独立进程所限制，如果其中任何一个服务需要增加某种功能，那么必须缩小进程范围。在为服务架构中，只需要在特定的某种服务中增加所需功能，而不影响整体进程。

## 二、[Eureka](https://www.cnblogs.com/demodashi/p/8509931.html)

Eureka是Spring Cloud Netflix微服务套件中的一部分，可以与Springboot构建的微服务很容易的整合起来。

Eureka包含了**服务器端**和**客户端组件**。

**服务器端**，也被称作是服务注册中心，用于提供服务的注册与发现。Eureka支持高可用的配置，当集群中有分片出现故障时，Eureka就会转入自动保护模式，它允许分片故障期间继续提供服务的发现和注册，当故障分片恢复正常时，集群中其他分片会把他们的状态再次同步回来。

**客户端组件**包含*服务消费者*与*服务生产者*。在应用程序运行时，Eureka客户端向注册中心注册自身提供的服务并周期性的发送心跳来更新它的服务租约。同时也可以从服务端查询当前注册的服务信息并把他们缓存到本地并周期性的刷新服务状态。

## 三、[服务注册与发现Eureka（Finchley版本）](https://blog.csdn.net/forezp/article/details/81040925)

[官方指导文档](http://cloud.spring.io/spring-cloud-static/Finchley.RELEASE/single/spring-cloud.html)

创建两个Module（一个作为服务注册中心，一个作为服务提供者）

### 1、创建Eureka-service

![1551425583905](/1551425583905.png)

![1551425635404](/1551425635404.png)



创建完成后，配置文件会自动加引入spring-cloud-starter-netflix-eureka-server的依赖，代码如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.3.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.springcloud</groupId>
    <artifactId>eureka-service</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>eureka-service</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.RELEASE</spring-cloud.version>
    </properties>

    <dependencies>
        <!--添加Eureka服务器端依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>https://repo.spring.io/milestone</url>
        </repository>
    </repositories>

</project>

```

### 2、启动一个服务注册中心

#### （1）只需要一个注解@EnableEurekaServer，这个注解需要在springboot工程的启动application类上加：

```java
package com.springcloud.eurekaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

//启用Eureka服务
@EnableEurekaServer
@SpringBootApplication
public class EurekaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceApplication.class, args);
    }

}
```

#### （2）编写eureka server的配置文件appication.yml：

在默认情况下erureka server也是一个eureka client ,必须要指定一个 server。通过eureka.client.registerWithEureka：false和fetchRegistry：false来表明自己是一个eureka server

```yaml
server:
  port: 8761

eureka:
  instance:
    hostname: localhost
  client:
    registerWithEureka: false
    fetchRegistry: false
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/

spring:
  application:
    name: eurka-server
```

#### （3）启动应用程序，打开浏览器输入http://localhost:8761，可以访问到Eureka-service页面：

显示No application available 没有服务被发现 ……
因为没有注册服务当然不可能有服务被发现了。

![1551426769044](/1551426769044.png)

### 3、创建一个服务提供者 (eureka client)

当client向server注册时，它会提供一些元数据，例如主机和端口，URL，主页等。Eureka server 从每个client实例接收心跳消息。 如果心跳超时，则通常将该实例从注册server中删除。

#### （1）创建过程与service类似，创建完成后pom文件如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.3.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.springcloud</groupId>
    <artifactId>eureka-hi</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>eureka-hi</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.RELEASE</spring-cloud.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>https://repo.spring.io/milestone</url>
        </repository>
    </repositories>

</project>

```

#### （2）通过注解@EnableEurekaClient 表明自己是一个eurekaclient，代码如下：

```java
package com.springcloud.eurekahi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@SpringBootApplication
@RestController
public class EurekaHiApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaHiApplication.class, args);
    }

    @Value("${server.port}")
    String port;

    @RequestMapping("/hi")
    public String home(@RequestParam(value = "name", defaultValue = "Lss") String name) {
        return "hi " + name + " ,i am from port:" + port;
    }
}
```

#### （3）仅仅@EnableEurekaClient是不够的，还需要在配置文件中注明自己的服务注册中心的地址，application.yml配置文件如下：

**需要指明spring.application.name，这个很重要，这在以后的服务与服务之间相互调用一般都是根据这个name** 

```yaml
server:
  port: 8762

spring:
  application:
    name: service-hi

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```

#### （4）启动工程，打开http://localhost:8761 ，即eureka server 的网址：

你会发现一个服务已经注册在服务中了，服务名为SERVICE-HI ,端口为7862

![1551428033640](/1551428033640.png)

这时打开 http://localhost:8762/hi，你会在浏览器上看到 :

![1551428097659](/1551428097659.png)

## [四、服务消费者](https://blog.csdn.net/forezp/article/details/81040946)

**在微服务架构中，业务都会被拆分成一个独立的服务，服务与服务的通讯是基于HTTP Restful的。SpringCloud有两种服务调用方式，一种是ribbon+restTemplate，另一种是feign。**

### **（一）、ribbon+restTemplate**

#### 1、Ribbon简介

 Spring Cloud Ribbon是一个基于HTTP和TCP的客户端负载均衡工具，它基于Netflix Ribbon实现。通过Spring Cloud的封装，可以让我们轻松地将面向服务的REST模版请求自动转换成客户端负载均衡的服务调用。Spring Cloud Ribbon虽然只是一个工具类框架，它不像服务注册中心、配置中心、API网关那样需要独立部署，但是它几乎存在于每一个Spring Cloud构建的微服务和基础设施中。因为微服务间的调用，API网关的请求转发等内容，实际上都是通过Ribbon来实现的，包括后续我们将要介绍的Feign，它也是基于Ribbon实现的工具。所以，对Spring Cloud Ribbon的理解和使用，对于我们使用Spring Cloud来构建微服务非常重要

#### 2、基于上一章【三、服务注册与发现】中，启动service工程；启动service-hi工程，端口为8762；将service-hi的配置文件中端口改为8763并启动，这时，service-hi在Eureka-service注册了两个实例，这就相当于一个小的集群。

访问localhost:8761可以看到

![1551665570288](/1551665570288.png)

#### 3、创建一个服务消费者

（1）新建模块命名为eureka-ribbon，项目pom文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.3.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.springcloud</groupId>
    <artifactId>eureka-ribbon</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>eureka-ribbon</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.RELEASE</spring-cloud.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>https://repo.spring.io/milestone</url>
        </repository>
    </repositories>

</project>

```

（2）在工程的配置文件指定服务的中心注册地址为：http://localhost:8761/eureka/，程序名称为service-ribbon，程序端口为8764，配置文件application.yml如下：

```yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
server:
  port: 8764
spring:
  application:
    name: service-ribbon
```

（3）在工程的启动类中，通过@EnableDiscoveryClient向服务中心注册，并向程序的IOC注入一个Bean：restTemplate；并通过@LoadBalance注解表明这个restTemplate开启负载均衡

```java
package com.springcloud.eurekaribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaRibbonApplication.class, args);
    }
//    注入RestTemplate
    @Bean
//    开启负载均衡
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
```

（4）写一个测试类HelloService，通过之前注入ioc容器的restTemplate来消费service-hi服务的“/hi”接口，在这里我们直接用的程序名替代了具体的url地址，在ribbon中它会根据服务名来选择具体的服务实例，根据服务实例在请求的时候会用具体的url替换掉服务名，代码如下：

```java
package com.springcloud.eurekaribbon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author : Lss
 * @Time : 2019/3/1 17:00
 **/
@Service
public class HelloService {
    @Autowired
    RestTemplate restTemplate;

    public String hiService(String name){
        return  restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
    }
}
```

（5）写一个controller，在controller中用调用HelloService 的方法，代码如下：

```Java
package com.springcloud.eurekaribbon.controller;

import com.springcloud.eurekaribbon.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : Lss
 * @Time : 2019/3/1 17:03
 **/
@RestController
public class HelloController {

    @Autowired
    HelloService helloService;

    @GetMapping(value = "/hi")
    public String hi(@RequestParam String name){
        return helloService.hiService(name);
    }
}
```

（6）在浏览器上多次访问http://localhost:8764/hi?name=Lss，浏览器交替显示：

![1551667126056](/1551667176354.png)

![1551667159724](/1551667192110.png)

这说明当我们通过调用restTemplate.getForObject(“[http://SERVICE-HI/hi?name=](http://service-hi/hi?name=)”+name,String.class)方法时，已经做了负载均衡，访问了不同的端口的服务实例。

#### ★4、此时的架构

![æ­¤æ¶æ¶æå¾.png](http://upload-images.jianshu.io/upload_images/2279594-9f10b702188a129d.png)

- 一个服务注册中心，eureka server,端口为8761

- service-hi工程跑了两个实例，端口分别为8762,8763，分别向服务注册中心注册

- sercvice-ribbon端口为8764,向服务注册中心注册

- 当sercvice-ribbon通过restTemplate调用service-hi的hi接口时，因为用ribbon进行了负载均衡，会轮流的调用service-hi：8762和8763 两个端口的hi接口；

  

#### ★★★5★★★理解【承上启下，结合上下全篇理解】

**Eureka Server（8761）是服务注册中心（相当于是一个容器，用来承载服务），即所有的服务（后续的Service-hi服务：8762/8763、service-ribbon服务：8764、service-feign服务：8765）都需要向它发起注册，成功之后才可以启动服务【例：Service-hi向Eureka Server发起注册，通过修改配置文件的端口不同从而实现多个注册（注册8762、8763两个端口服务）】；**

**||之后service-ribbon（8764）向服务中心发起请求，请求访问Service-hi的服务（通过之前注入ioc容器的restTemplate来消费service-hi服务的“/hi”接口；通过ribbon的负载均衡技术，即可以访问8762，也可以访问8763），通过浏览器访问http://localhost:8764/hi?name=Lss，经ribbon负载均衡实现两个端口交替访问使用**

**||之后的feign也是如此，因为feign整合了ribbon会默认实现负载均衡，项目中引入feign依赖，@EnableFeignClients开启feign功能，定义接口调用服务，也会交替使用两个服务端口，**

**||再之后使用断路器Hystrix【向服务中加入熔断功能，防止服务线程阻塞而造成的服务瘫痪甚至“雪崩”效应】时，**

**//在ribbon中使用Hystrix，向pom文件中引入Hystrix，并在主类中加@EnableHystrix注解开启Hystrix；在Service方法上加上@HystrixCommand注解，表明该注解对该方法创建了熔断器的功能，并指定了fallbackMethod熔断方法，熔断方法直接返回了一个字符串，字符串为"hi,"+name+",sorry,error!"**

```java
@Service
public class HelloService {
	//注入RestTemplate
    @Autowired
    RestTemplate restTemplate;
	//开启熔断功能，并指明熔断方法
    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name) {
        //若服务在启用中即活动状态，可以成功调用服务访问成功
        return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
    }
	//若服务未启用，即休眠状态，则会启用熔断功能，调用熔断方法
    public String hiError(String name) {
        return "hi,"+name+",sorry,error!";
    }
}
```

**//在feign中使用Hystrix，在配置文件中打开使用熔断功能，在接口的注解中加上fallback的指定类，实现类实现接口并重写方法【即熔断方法】，若服务调用成功，则使用接口中的方法值，成功访问服务端口，若服务未调用，则调用实现类中重写的熔断方法，返回熔断方法中的值。**



### （二）、[feign](https://blog.csdn.net/forezp/article/details/81040965)

#### 1、Feign简介

Feign是一个声明式的伪HTTP客户端，它使得HTTP客户端变得简单。使用Feign，只需要创建一个接口并注解。它具有可插拔的注解性，可使用Feign注解和JAX-RS注解。Feign支持可插拔的编码器和解码器。Feign默认集成了Ribbon，并和Eureka结合，默认实现了负载均衡的效果。

简而言之：

●  Feign采用的是基于接口的注解

●  Feign整合了ribbon，具有负载均衡能力

●  整合了Hystrix，具有熔断的能力

#### 2、准备工作

基于上一节【（一）、ribbon+restTemplate】的工程，启动service-server，端口为8761；启动service-hi两次，端口分别为8762/8763

#### 3、创建一个feign的服务

新建一个spring-boot工程，取名servie-feign，在它的pom文件中引入**Feign**的起步依赖spring-cloud-starter-feign、**Eureka**的起步依赖spring-cloud-starter-netflix-eureka-client、**Web**的起步依赖spring-boot-starter-web，代码如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.3.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.springcloud</groupId>
    <artifactId>service-feign</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>service-feign</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.SR1</spring-cloud.version>
    </properties>

    <dependencies>
        <!--web起步依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--Eureka的起步依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!--Feign的起步依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

在工程配置文件application.yml中，指定程序名为service-feign，端口号8765，注册服务地址：http://loacalhost:8761/eureka/，代码如下：

```yml
#服务注册地址
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
#Feign服务端口号、名称
server:
  port: 8765
spring:
  application:
    name: service-feign
```

在程序启动类上加@EnableFeignClients，来开启Feign功能：

```java
package com.springcloud.servicefeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//开启Feign功能
@EnableFeignClients
public class ServiceFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceFeignApplication.class, args);
    }

}
```

定义一个feign接口，通过@FeignClient（“服务名”），来指定调用哪个服务、比如在代码中调用了service-hi服务的“/hi”接口，代码如下：

```java
package com.springcloud.servicefeign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author : Lss
 * @Time : 2019/3/18 16:53
 **/
//通过注解指定调用哪个服务
@FeignClient(value = "service-hi")
public interface SchedualServiceHi {
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name") String name);
}
```

在web的controller层，对外暴露一个“/hi”的API接口，通过上面定义的Feign客户端SchedualServiceHi 来消费服务。代码如下：

```java
package com.springcloud.servicefeign.web;

import com.springcloud.servicefeign.service.SchedualServiceHi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : Lss
 * @Time : 2019/3/18 16:51
 **/
@RestController
public class HiController {
//    注入接口
    @Autowired
    SchedualServiceHi schedualServiceHi;
//通过方法调用接口。即注入的接口
    @GetMapping(value = "/hi")
    public String sayHi(@RequestParam String name){
        return schedualServiceHi.sayHiFromClientOne(name);
    }
}
```

启动程序，多次访问

http://localhost:8765/hi?name=Lss，浏览器交替显示

hi Lss ,i am from port:8762

hi Lss ,i am from port:8763

### （三）、[Hystrix（断路器）【Finchley版本】](https://blog.csdn.net/forezp/article/details/81040990)

#### 1、断路器的由来

在微服务架构中，根据业务来拆分成一个个的服务，服务与服务之间可以相互调用（RPC），在SpringCloud可以使用RestTemplate+Ribbon和Feign来调用。为了保证其高可用性，单个服务通常会集群部署。由于网络原因或者自身原因，服务并并不能保证100%可用，如果单个服务器出现问题，调用这个服务就会出现线程阻塞，若此时有大量的请求涌入，Servlet容器的线程资源会被消耗完毕，导致整个服务瘫痪。服务与服务之间的依赖性，故障会传播，会对整个微服务系统造成灾难性的严重后果，这就是服务器故障的“雪崩”效应。

为了解决这个问题，业界就提出了断路器模型。

#### 2、断路器简介

Netflix开源了Hystrix组件，实现了断路器模式，SpringCloud对这一组件进行了整合。在为服务架构中，一个请求调用多个服务是非常常见的，如下图：

![img](http://upload-images.jianshu.io/upload_images/2279594-08d8d524c312c27d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/600)



较底层的服务如果出现故障，会导致连锁故障，当对特定的服务调用的不可用打到一个阈值（Hystrix是每5秒20次）断路器就会被打开。

![img](http://upload-images.jianshu.io/upload_images/2279594-8dcb1f208d62046f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/600)

断路器打开后，可避免连锁故障，fallback方法可以直接返回一个固定值。

#### 3、准备工作

基于之前的ribbon和feign工程，启动Eureka-server工程；启动Service-hi工程，端口为8762。

#### 4、在ribbon中使用断路器

改造service-ribbon的代码，首先在pom.xml中加入spring-cloud-starter-netflix-hystrix的起步依赖

```xml
 <!--hystrix依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
```

在程序的启动类中加@EnableHystrix注解开启Hystrix

```java
package com.springcloud.eurekaribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//通过@EnableDiscoveryClient向服务中心注册
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
//开启断路器Hystrix，熔断
@EnableHystrix
public class EurekaRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaRibbonApplication.class, args);
    }
//    注入RestTemplate
    @Bean
//    开启负载均衡
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
```

改造HelloService方法，在hiService方法上加注解。该注解对该方法创建了熔断器的功能，并指定了fallbackMethod熔断方法，熔断方法直接返回了一个字符串，代码如下：

```java
@Service
public class HelloService {
	//注入RestTemplate
    @Autowired
    RestTemplate restTemplate;
	//开启熔断功能，并指明熔断方法
    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name) {
        //若服务在启用中即活动状态，可以成功调用服务访问成功
        return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
    }
	//若服务未启用，即休眠状态，则会启用熔断功能，调用熔断方法
    public String hiError(String name) {
        return "hi,"+name+",sorry,error!";
    }
}
```

启动service-ribbon（8764）工程，当我们访问http://localhost:8764/hi?name=Lss，浏览器显示：

`hi Lss,i am from port:8762`

此时关闭 service-hi 工程，当我们再访问http://localhost:8764/hi?name=Lss，浏览器会显示：

`hi ,forezp,orry,error!`

这就说明，当Service-hi工程不可用的时候，service-ribbon调用Service-hi的API接口时，会执行快速失败，直接返回一组字符串，而不是等待响应，这就很好的控制了容器的线程阻塞。

#### 5、在Feign中使用断路器

Feign是自带断路器的，在D版本之后的SpringCloud之后，他没有默认开启。需要在配置文件中配置打开它，配置文件如下：

```yml
#    feign默认关闭熔断功能，需在配置文件中开启
feign:
  hystrix:
    enabled: true
```

基于service-feign工程的改造，只需要在FeignClient的SchedualServiceHi接口的注解中加上fallback的指定类就可以了：

```java
//通过注解指定调用哪个服务
//在使用断路器时，加入fallback来指明类
@FeignClient(value = "service-hi",fallback = SchedualServiceHiHystric.class)
public interface SchedualServiceHi {
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "name") String name);
}
```

SchedualServiceHiHystric需要实现SchedualServiceHi接口，并注入到ioc容器，代码如下：

```java
package com.springcloud.servicefeign.service;

import org.springframework.stereotype.Component;

/**
 * @Author : Lss
 * @Time : 2019/3/19 11:23
 **/
@Component
//断路器返回指定类，实现接口并重写方法，断路器方法
public class SchedualServiceHiHystric implements SchedualServiceHi {
    @Override
    public String sayHiFromClientOne(String name){
        return "sorry"+name;
    }
}
```

启动四servcie-feign工程，浏览器打开http://localhost:8765/hi?name=Lss，注意此时service-hi工程没有启动，网页显示：

`sorry Lss`

打开service-hi工程，再次访问，浏览器显示：

`hi Lss,i am from port:8762`

证明断路器起到了作用。

#### 6、负载均衡

[参考1](https://blog.csdn.net/forezp/article/details/74820899)、[参考二](https://blog.csdn.net/qq_20597727/article/details/82860521)、[参考三](https://blog.csdn.net/wudiyong22/article/details/80829808)

## 五、路由网关（Zuul）

### 1、简介

在微服务架构中，需要几个基础的服务治理组件，包括服务注册与发现、服务消费、负载均衡、断路器、智能路由、管理配置等，由这几个基础组件相互协调，共同组建了一个简单的微服务系统。

在SpringCloud微服务系统中，一种常见的负载均衡方式是，客户端的请求先经过负载均衡（zuul、Ngnix），再到达服务网关（zuul集群），再到具体的服。

### 2、Zuul简介

Zuul的主要功能是路由转发和过滤器。路由功能是微服务的一部分，比如/api/user转发到user服务，/api/shop转发到shop服务。zuul默认和ribbon结合实现了负载均衡的功能。

### 3、创建service-zuul工程

（1）启动之前项目：eurek-service、eureka-hi、service-ribbon、service-feign，创建一个service-zuul项目，pom配置文件如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.3.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.springcloud</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>demo</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.SR1</spring-cloud.version>
    </properties>

    <dependencies>
        <!--引入Euraka服务依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--引入zuul依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

（2）在入口主类是上加注解@EnableZuulProxy，开启zuul功能：

```java
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableEurekaClient
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

（3）配置文件

首先指定服务注册中心的地址为http://localhost:8761/eureka/，服务的端口为8769，服务名为service-zuul；以/api-a/ 开头的请求都转发给service-ribbon服务；以/api-b/开头的请求都转发给service-feign服务；

```yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
server:
  port: 8769
spring:
  application:
    name: service-demo
#    将api-a的请求都转发给ribbon、将api-b的请求都转发给feign
zuul:
  routes:
    api-a:
      path: /api-a/**
      serviceId: service-ribbon
    api-b:
      path: /api-b/**
      serviceId: service-feign

#      容易造成超时，所以需要配置以下参数
  host:
    connect-timeout-millis: 10000
    socket-timeout-millis: 60000
    
hystrix:
  command:
    default:
      execution:
        isolation:
          thread:
            timeoutInMilliseconds: 60000
```

***注：** 

zuul部署上物理机之后，如果使用默认配置，请求很容易超时，错误信息
com.netflix.zuul.exception.zuulexception timeout
所以需要在配置文件中添加配置信息：

properties配置文件:
	zuul.host.socket-timeout-millis=60000
	zuul.host.connect-timeout-millis=10000
	hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=60000
	
yml配置文件：

```yml
zuul:
		host:
			connect-timeout-millis: 10000
			socket-timeout-millis: 60000
hystrix:
	command:
		default:
			execution:
				isolation:
					thread:
                    timeoutInMilliseconds: 60000
```


（4）依次打开5个工程，浏览器访问：http://localhost:8769/api-a/hi?name=Lss，浏览器显示：

hi Lss,i am from port:8762

访问http://localhost:8769/api-b/hi?name=Lss，浏览器显示：

hi Lss,i am from port:8762

这说明浏览器起到了路由的作用。

### 4、服务过滤

zuul不仅是路由，而且还能过滤，做一些安全验证，改造工程：

```java
@Component
public class MyFilter extends ZuulFilter{

    private static Logger log = LoggerFactory.getLogger(MyFilter.class);
//    filterType返回的字符串类型代表过滤类型，分为四种
//    pre:路由前  routing:路由时   post:路由之后    error:发送错误调用
    @Override
    public String filterType() {
        return "pre";
    }

//    filterOrder过滤的顺序
    @Override
    public int filterOrder() {
        return 0;
    }

//    shouldFilter写逻辑判断，是否要过滤，true永远过滤
    @Override
    public boolean shouldFilter() {
        return true;
    }

//    run方法，过滤器的具体逻辑
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getParameter("token");
        if(accessToken == null) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            }catch (Exception e){}

            return null;
        }
        log.info("ok");
        return null;
    }
}
```

这时访问：http://localhost:8769/api-a/hi?name=Lss；网页显示：

token is empty

访问：http://localhost:8769/api-a/hi?name=Lss&token；网页显示：

hi Lss,i am from port:8762

## 六、分布式配置中心(Spring Cloud Config)(Finchley版本)

### 1、简介

在分布式系统中，由于服务数量巨多，为了方便服务配置文件统一管理，实时更新，所以需要分布式配置中心组件。在Spring Cloud中，有分布式配置中心组件spring cloud config ，它支持配置服务放在配置服务的内存中（即本地），也支持放在远程Git仓库中。在spring cloud config 组件中，分两个角色，一是config server，二是config client。

config-server是配置服务端的工程；config-client是配置客户端的工程，启动客户端工程访问，config-client从config-server获取了指定的属性，而config-server将属性值从git仓库读取。

### 2、构建Config Server

创建config-server工程，

pom配置文件如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.3.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.springcloud</groupId>
    <artifactId>springcloud</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springcloud</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.SR1</spring-cloud.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

主类上添加注解@EnableConfigServer开启配置服务器的功能

```java
@SpringBootApplication
@EnableConfigServer
public class SpringcloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudApplication.class, args);
    }

}
```

配置文件：

```properties
spring.application.name=config-server
server.port=8888

#配置Git仓库地址
spring.cloud.config.server.git.uri=https://github.com/forezp/SpringcloudConfig/
#配置仓库路径
spring.cloud.config.server.git.searchPaths=respo
#配置仓库的分支
spring.cloud.config.label=master

#如果Git仓库为公开仓库，可以不填写用户名和密码，如果是私有仓库需要填写
#访问Git仓库的用户名
spring.cloud.config.server.git.username=
#访问Git仓库的用户密码
spring.cloud.config.server.git.password=
```

启动浏览器访问http://localhost:8888/foo/dev，浏览器显示：

```json
{
"name": "foo",
"profiles": [
"dev"
],
"label": null,
"version": "0fc8081c507d694b27967e9074127b373d196431",
"state": null,
"propertySources": []
}
```

证明配置服务中心可以从远程程序获取配置信息。

### 3、构建Config Client

pom配置文件：（此工程是基于org.springframework.boot 2.0以下版本，2.0以上会报错）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <!--基于Springboot-->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.4.0.RELEASE</version>
        <relativePath/>
    </parent>
    <groupId>com.springcloud</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>demo</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Dalston.RC1</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>https://repo.spring.io/milestone</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>

</project>
```

其配置文件**bootstrap.properties**：

```properties
spring.application.name=config-client
spring.cloud.config.label=master
spring.cloud.config.profile=dev
spring.cloud.config.uri=http://localhost:8888/
server.port=8881
```

程序的入口类，写一个API接口“／hi”，返回从配置中心读取的foo变量的值，代码如下：

```java
@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Value("${foo}")
    String foo;
    @RequestMapping(value = "/hi")
    public String hi(){
        return foo;
    }

}
```

打开网址访问：http://localhost:8881/hi，网页显示:

foo version 21

即从Git仓读取到的数据

![Azure (2).png](https://upload-images.jianshu.io/upload_images/2279594-40ecbed6d38573d9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/600)

![1553241802743](/1553241802743.png)

## 七、高可用分布式配置

[参考](https://blog.csdn.net/yanluandai1985/article/details/86667116)、衍生自[SpringCloud教程第7篇：高可用的分布式配置中心（F版本）](https://www.fangzhipeng.com/springcloud/2018/08/07/sc-f7-config.html)

![Azure (3).png](http://upload-images.jianshu.io/upload_images/2279594-babe706075d72c58.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/600)

### 1、构建一个注册中心eureka-service项目

#### 1.1依赖 pom文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.3.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.springcloud</groupId>
    <artifactId>eureka-service</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>eureka-service</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.RELEASE</spring-cloud.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>https://repo.spring.io/milestone</url>
        </repository>
    </repositories>

</project>

```

#### 1.2配置文件

```yml
#使用8761端口
server:
  port: 8761

eureka:
  instance:
    hostname: localhost
  client:
#    这两个false表明自己是个Eureka server
    registerWithEureka: false
    fetchRegistry: false
#    服务中心的地址
    serviceUrl:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/
#服务器名称
spring:
  application:
    name: eurka-server

```

#### 1.3主程序

```java
package com.springcloud.eurekaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

//@EnableEurekaServer表明是一个注册中心
@EnableEurekaServer
@SpringBootApplication
public class EurekaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceApplication.class, args);
    }

}
```



### 2、修改config-server

通过分析可知，为了搭建高可用的分布式配置中心，需要把配置中心加入注册中心统一管理；其实思路很简单就是把配置中心也当成一个Eureka-Client。

#### 2.1依赖

需引入eureka-client的相关依赖

pom文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>springcloudconfig</artifactId>
        <groupId>com.springcloudconfig</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>config-server</artifactId>

    <dependencies>
        <!--web起步依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--分布式系统中的外部化配置提供服务器和客户端支持-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
            <version>2.1.0.RC3</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
            <version>2.1.1.RELEASE</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

    </dependencies>

</project>
```

#### 2.2配置文件

将自己注册到注册中心

```yml
#项目部署端口
server:
  port: 8881

#配置注册中心的位置，并把自己注册进去
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/

spring:
  #项目名称
  application:
    name: config-server
  #配置中心的相关配置
  cloud:
    config:
      server:
        git:
          # 配置git仓库的URL地址
          uri: https://github.com/hairdryre/Study_SpringCloud
          # git仓库地址下的文件夹，可以配置多个，用“,”分割。
          search-paths: config/chapter6
          #公共仓库可不用配置帐号密码
          username:
          password:
```

#### 2.3主类

在启动类上加@EnableEurekaClient注解

```java
package com.springcloudconfig.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author : Lss
 * @Time : 2019/3/25 16:31
 **/
@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class,args);
    }
}
```



### 3、修改config-client

#### 3.1依赖

配置中心被加到了注册中心，同理client端也要加入到注册中心，引入eureka-client依赖，完整pom文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>springcloudconfig</artifactId>
        <groupId>com.springcloudconfig</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>config-client</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
    </dependencies>
</project>
```

#### 3.2配置文件

修改bootstrap.properties配置文件【bootstrap.properties的优先级大于application.properties】

两个配置文件：

（1）application.properties

```properties
spring.application.name=config-client
server.port=8882
```

（2）bootstrap.properties

删除URI的配置项，加入后三项：服务注册地址、开启从配置中心读取文件、配置中心的serviceID；

●这时，在配置文件中不再写service的IP地址，而是服务名；如果有多份，可以通过负载均衡，达到高可用

```properties
#对应{application}部分
spring.cloud.config.name=jay-config
#对应{profile}部分，如果想使用其他的配置，将值修改test、dev即可
spring.cloud.config.profile=dev

#URI的配置只是在分布式配置时指定的获取数据的服务端口；配置中心的具体地址，即Server的端口
#在高可用配置时删掉URI的配置【高可用配置时不再写URI 而是直接指明serviceID】
#spring.cloud.config.uri=http://localhost:8881/
#git分支
spring.cloud.config.label=master

#服务注册地址
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
#从配置中心读取文件
spring.cloud.config.discovery.enabled=true
#配置中心的serviceId
spring.cloud.config.discovery.serviceId=config-server
```

#### 3.3主类

 在启动类上增加@EnableEurekaClient即可

```java
package com.springcloudconfig.configclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author : Lss
 * @Time : 2019/3/25 17:12
 **/
@SpringBootApplication
@EnableEurekaClient
public class ConfigClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }
}
```

Controller

```java
package com.springcloudconfig.configclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : Lss
 * @Time : 2019/3/25 17:09
 **/
@RestController
public class TestController {
    @Value("${jay.label}")
    private String label;

    @RequestMapping("/hello")
    public String hello(){
        return label;
    }
}
```

### 4、测试

启动Eureka-servie【8761】、config-server（可以启动多个不同端口的实例【例：8881、8883】）、config-client【8882】；

访问eureka-service的8761端口可以看到注册中心的服务：

8761：注册中心

![1553585457811](/1553585457811.png)



8881：config-server

![1553585516189](/1553585516189.png)

8883：config-server

![1553585561928](/1553585561928.png)

8882：config-client

![1553585781306](/1553585781306.png)

开启eureka-server、config-server【两个实例】、config-client

![1553585831697](/1553585831697.png)

通过config-server的两个实例端口8881、8883访问Git上的配置，均可访问到；

![1553585895892](/1553585895892.png)

通过config-client的API映射访问读取Git上配置文件属性值，也可以读取到；

![1553585931646](/1553585931646.png)

随机挂掉一个server的实力端口服务【例：8881】，再通过client的端口访问配置文件属性值，依然可以访问，说明配置中心也可以集群部署。

![1553586007548](/1553586007548.png)

![1553586043792](/1553586043792.png)

![1553586061604](/1553586061604.png)

![1553586259591](/1553586259591.png)





















