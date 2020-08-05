## 一、SpringBoot特性：

1、 能够快速创建基于Spring的应用程序

2、 能够直接使用java main 方法来启动内嵌的Tomcat、Jetty服务器运行SpringBoot程序，不需要部署war包文件

3、 根据约定的starer POM来简化Maven配置，让Maven的配置变得简单

4、 提供了程序的健康检查功能

5、 基本可以完全不使用XML配置文件，采用注解配置

## 二、SpringBoot四大核心

1、 自动配置：针对很多Spring应用程序和常见的应用功能，SpringBoot能自动提供相关配置

2、 起步依赖（Maven依赖）：告诉Spring需要什么功能，他就能引入需要的依赖包

3、 Actuator（健康检查）：能够深入运行中的SpringBoot应用程序，一探SpringBoot程序的内部信息

4、 命令行界面：这是SpringBoot的可选特性，主要针对Groovy语言使用【Groovy是一种基于JVM的敏捷开发语言】

## 三、配置文件

### **1** 、**多个配置文件**

对配置文件进行命名（例：application-dev.properties/ application-test.properties/ application-online.properties）

在主配置文件中激活使用哪个配置文件：spring-profile-active=dev/test/online(即配置文件名称-后边的内容)，在配置文件中没有设置的属性在主配置文件可以设置，并且会生效；在配置文件中设置了的属性，再在主配置文件中设置的话不会生效，会启用激活的配置文件中设置的属性值

### **2** 、**读取自定义配置属性：**

#### **1）**使用@Value注解

```properties
#自定义配置
boot.name=北京
boot.location=海淀
```

```java
//使用自定义配置
@Controller
public class ConfigInfoController {
    @Value("${boot.name}")
    private String name;

    @Value("${boot.location}")
    private String location;

    @RequestMapping("/config")
    public @ResponseBody String config(){
        return name +",,,,"+location;
    }
}
```

#### **2)** 使用@ConfigurationProperties(prefix = "xxx")注解

```xml
//使用@ConfigurationProperties注解是需要在pom.xml中引入依赖
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

例：@ConfigurationProperties(prefix = "boot")

写一个配置类ConfigInfo并用@ConfigurationProperties注解，将该类使用@Component注解为组件，将其注入到另一个类中（@Autowired），并使用ConfigInfo.getxxx来获取自定义属性

## 四、SpringBoot下的SpringMVC

1）、@Controller：即为SpringMVC的注解，用来处理HTTP请求

2）、@RestController：Spring4后新增的注解，是@Controller与@ResponseBody的组合注解；用于返回字符串或JSON数据

3）、@GetMapping：@RequestMapping和Get请求方法的组合【只支持get请求】（@GetMapping=@RequestMapping+RequestMethod.GET）

4）、@PostMapping：@RequestMapping和Post请求方法的组合【只支持post请求】（@PostMapping=@RequestMapping+RequestMethod.POST）

```java
//POST请求方法
@PostMapping("/getUser2")
    public Object getUser2(){
        User user =new User();

        user.setId(101);
        user.setName("李四-POST");

        return user;
    }
```

![images/](C:\Users\Lss\AppData\Roaming\Typora\typora-user-images\1547018881257.png)

**【浏览器地址栏请求默认是get请求】** 

5）、@PutMapping【修改操作】：@RequestMapping和put请求方法的组合（用@PostMapping代替）

（@PutMapping=@RequestMapping+put方法）

6）、@DeleteMapping【删除操作】：@RequestMapping和Delete请求方法的组合（用@GetMapping代替）

（@DeleteMapping=@RequestMapping+delete方法）

## 五、SpringBoot使用JSP

### 1、在pom.xml中引入Tomcat对JSP解析的依赖

```xml
<!--JSTL标签依赖的jar包start-->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
</dependency>
<!--JSP依赖的jar包start-->
<dependency>
    <groupId>javax.servlet.jsp</groupId>
    <artifactId>javax.servlet.jsp-api</artifactId>
    <version>2.3.1</version>
</dependency>
<!--servlet依赖的jar包start-->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
</dependency>
<!--引入SpringBoot内嵌的Tomcat对JSP的解析-->
<dependency>
    <groupId>org.apache.tomcat.embed</groupId>
    <artifactId>tomcat-embed-jasper</artifactId>
    <scope>provided</scope>
</dependency>
```

### 2、在application.properties中添加配置

```properties
#添加视图解析前缀后缀
spring.mvc.view.prefix=/
spring.mvc.view.suffix=.jsp
```

**当无法解析时，需在pom.xml文件build中加入子项配置**

```xml
<!--引入配置-->
<resources>
    <resource>
        <directory>src/main/java</directory>
        <includes>**/*.xml</includes>
    </resource>
    <resource>
        <directory>src/main/resources</directory>
        <includes>**/*.*</includes>
    </resource>
    <resource>
        <directory>src/main/webapp</directory>
        <targetPath>META-INF/resources</targetPath>
        <includes>
            <include>**/*.*</include>
        </includes>
    </resource>
</resources>
```

## 六、SpringBoot集成MyBatis

●**集成方式分两种：一种就是常见的 xml 的方式 ，还有一种是全注解的方式**

[参考教程](https://github.com/Snailclimb/springboot-integration-examples/blob/master/md/springboot-mybatis.md)

[基于SpirngBoot2.0+ 的 SpringBoot+Mybatis 多数据源配置](https://github.com/Snailclimb/springboot-integration-examples/blob/master/md/springboot-mybatis-mutipledatasource.md)

### 1、在pom.xml中引入相关jar包

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>1.3.2</version>
    </dependency>

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

**build项引入配置**

```xml
<build>

    <!--引入配置-->
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>**/*.xml</includes>
        </resource>
        <resource>
            <directory>src/main/resources</directory>
            <includes>**/*.*</includes>
        </resource>
        <resource>
            <directory>src/main/webapp</directory>
            <targetPath>META-INF/resources</targetPath>
            <includes>
                <include>**/*.*</include>
            </includes>
        </resource>
    </resources>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>

        <!--mybatis自动生成代码插件-->
        <plugin>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-maven-plugin</artifactId>
            <version>1.3.6</version>
            <!--配置文件位置-->
            <configuration>
                <configurationFile>src/main/java/GeneratorMapper.xml</configurationFile>
                <verbose>true</verbose>
                <overwrite>true</overwrite>
            </configuration>
        </plugin>

    </plugins>
</build>
```

### 2、在核心配置文件application.properties中配置Mybatis的mapper.xml文件所在的位置

```properties
mybatis.mapper-locations=com.example.springboot.mapper/*.xml
```

### 3、在核心配置文件application.properties中配置数据源

```properties
#配置数据库连接信息
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/mybatis?useUnicode=true&characterEncoding=utf8&useSSL=false

```

### 4、在Mybatis的Mapper接口中添加@Mapper注解或者在运行主类上添加@MapperScan（com.xxx.xxx.xxx.mapper）注解包进行扫描

在controller层写请求，调用service层的方法，实现类xxxImpl实现service层中的方法（需先将mapper类注入进来），在mapper.xml中写具体的实现SQL

```xml
<!--查询所有学生信息-->
<select id="selectAllStudent"  resultMap="BaseResultMap">
    select
    <include refid="Base_Column_List" />
    from student
</select>
```

## 七、SpringBoot事务支持

[事务详解](http://blog.didispace.com/springboottransactional/)

### 1、在入口类中使用注解@EnableTransactionManagement开启事务支持

### 2、在访问数据库的service方法上添加注解@Transaction即可

### 3、隔离级别：

隔离级别是指若干个并发的事务之间的隔离程度，与我们开发时候主要相关的场景包括：脏读取、重复读、幻读。

我们可以看`org.springframework.transaction.annotation.Isolation`枚举类中定义了五个表示隔离级别的值：

- `DEFAULT`：这是默认值，表示使用底层数据库的默认隔离级别。对大部分数据库而言，通常这值就是：`READ_COMMITTED`。
- `READ_UNCOMMITTED`：该隔离级别表示一个事务可以读取另一个事务修改但还没有提交的数据。该级别不能防止脏读和不可重复读，因此很少使用该隔离级别。
- `READ_COMMITTED`：该隔离级别表示一个事务只能读取另一个事务已经提交的数据。该级别可以防止脏读，这也是大多数情况下的推荐值。
- `REPEATABLE_READ`：该隔离级别表示一个事务在整个过程中可以多次重复执行某个查询，并且每次返回的记录都相同。即使在多次查询之间有新增的数据满足该查询，这些新增的记录也会被忽略。该级别可以防止脏读和不可重复读。
- `SERIALIZABLE`：所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是这将严重影响程序的性能。通常情况下也不会用到该级别。

### 4、传播行为：

所谓事务的传播行为是指，如果在开始当前事务之前，一个事务上下文已经存在，此时有若干选项可以指定一个事务性方法的执行行为。

我们可以看`org.springframework.transaction.annotation.Propagation`枚举类中定义了6个表示传播行为的枚举值：

- `REQUIRED`：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
- `SUPPORTS`：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
- `MANDATORY`：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
- `REQUIRES_NEW`：创建一个新的事务，如果当前存在事务，则把当前事务挂起。
- `NOT_SUPPORTED`：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
- `NEVER`：以非事务方式运行，如果当前存在事务，则抛出异常。
- `NESTED`：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于`REQUIRED`。

## 八、SpringBoot实现RESTfull API

### 1、什么是RESTfull

一种软件架构风格、设计风格，而不是标准，只是提供了一组设计原则和约束条件。它主要用于客户端和服务器交互类的软件。基于这个风格设计的软件可以更简洁，更有层次，更易于实现缓存等机制（任何技术都可以实现这种理念）

REST（英文：**Representational State Transfer**，简称**REST**）描述了一个架构样式的网络系统，比如 web 应用程序。它首次出现在 2000 年 Roy Fielding 的博士论文中，Roy Fielding是 HTTP 规范的主要编写者之一。在目前主流的三种Web服务交互方案中，REST相比于SOAP（Simple Object Access protocol，简单对象访问协议）以及XML-RPC更加简单明了，无论是对URL的处理还是对Payload的编码，REST都倾向于用更加简单轻量的方法设计和实现。值得注意的是REST并没有一个明确的标准，而更像是一种设计的风格

### 2、SpringBoot开发RESTfull主要是几个注解实现

1）、@PathVariable注解

```java
@RestController
public class RestFullController {
	//浏览器访问路径：http://localhost:8080/spring-boot-start/user/10/张三丰
    @RequestMapping("/user/{id}/{name}")
    public Object user(@PathVariable("id") Integer id,@PathVariable("name") String name){
        User user = new User();
        user.setId(id);

        user.setName(name);
        return user;
    }
}
```

2）、增加Post方法

PostMapping：接收和处理Post方式的请求

3）、删除Delete方法：

DeleteMapping：接收Delete方式的请求，可以使用GetMapping代替

4）、修改Put方法：

PutMapping：接收put方式的请求，可以用PostMapping代替

5）、查询Get方法：

GetMapping：接收get方式请求

## 九、SpringBoot热部署插件

[IDEA热部署配置](https://www.cnblogs.com/lspz/p/6832358.html)

在实际开发中，我们修改某些代码逻辑功能或页面都需要重启应用，无形中降低了开发效率；热部署就是当我们修改代码之后，服务能自动重启加载新修改的内容，这样大大提高了我们的开发效率。

SpringBoot热部署是通过一个插件实现的，插件为spring-boot-devtools,在Maven中的配置如下

```xml
<!--spring开发自动热部署-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```



## 十、SpringBoot集成Redis

[Springboot集成Redis](https://blog.csdn.net/ai88030669/article/details/78686403)

#### **1、pom.xml 引入redis 开启缓存**  

```xml
<!--  cache -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
<!--  redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

#### 2、**application.properties 配置文件**

```properties
# Redis数据库索引（默认为0）
spring.redis.database=0
# Redis服务器地址
spring.redis.host=localhost
# Redis服务器连接端口
spring.redis.port=6379
# Redis服务器连接密码（默认为空）
spring.redis.password=
# 连接池最大连接数（使用负值表示没有限制）
spring.redis.pool.max-active=8
# 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.pool.max-wait=-1
# 连接池中的最大空闲连接
spring.redis.pool.max-idle=8
# 连接池中的最小空闲连接
spring.redis.pool.min-idle=0
# 连接超时时间（毫秒）
spring.redis.timeout=0

```

#### 3、配置了上面的步骤，SpringBoot将自动配置Redis Template，在需要操作Redis的类中注入Redis Template

示例代码：

```java
//此处是基本类型测试，String类型

//注入StringRedisTemplate
@Autowired
private StringRedisTemplate stringRedisTemplate;

//注入RedisTemplate
@Autowired
private RedisTemplate redisTemplate;

//测试，使用stringRedisTemplate.opsForValue().set()设值，再使用Assert.assertEquals进行比较
@Test
public void test() throws Exception{
    stringRedisTemplate.opsForValue().set("aaa","111");
    Assert.assertEquals("111",stringRedisTemplate.opsForValue().get("aaa"));
}

@Test
public void testObj() throws Exception{
    User user=new User(1, "wangwu", 12, 343.3);
    ValueOperations<String, User> operations=redisTemplate.opsForValue();
    operations.set("com.spring.springboot.Bean.User", user);
    operations.set("com.spring.springboot.Bean.User", user,1,TimeUnit.SECONDS);
    Thread.sleep(1000);
    //redisTemplate.delete("com.neo.f");
    boolean exists=redisTemplate.hasKey("com.spring.springboot.Bean.User");
    if(exists){
        System.out.println("exists is true");
    }else{
        System.out.println("exists is false");
    }

}


//此处是对象类型测试，即在操作中会使用Redis存储对象

//注入对象
@Autowired
private RedisTemplate<String,User1> redisTemplate1;

@Test
public void test1()throws Exception{
    //保存对象，设值
    User1 user1 = new User1("超人",20);
    redisTemplate1.opsForValue().set(user1.getUsername(),user1);

    user1 = new User1("蝙蝠侠",30);
    redisTemplate1.opsForValue().set(user1.getUsername(),user1);

    user1 = new User1("蜘蛛侠",40);
    redisTemplate1.opsForValue().set(user1.getUsername(),user1);


    //比较
    Assert.assertEquals(20, redisTemplate1.opsForValue().get("超人").getAge().longValue());
    Assert.assertEquals(30, redisTemplate1.opsForValue().get("蝙蝠侠").getAge().longValue());
    Assert.assertEquals(40, redisTemplate1.opsForValue().get("蜘蛛侠").getAge().longValue());
}
```



#### 4、RedisTemplate用法详解

[RedisTemplate用法详解](https://blog.csdn.net/weixin_40461281/article/details/82011670)

Redis 可以存储键与5种不同数据结构类型之间的映射，这5种数据结构类型分别为String（字符串）、List（列表）、Set（集合）、Hash（散列）和 Zset（有序集合）。

#### 5、Assert.assertEquals作用

[Assert.assertEquals作用](https://blog.csdn.net/tgvincent/article/details/81296349)

junit.framework包下的Assert提供了多个断言方法. 主用于比较测试传递进去的两个参数.

Assert.assertEquals();及其重载方法: 1. 如果两者一致, 程序继续往下运行. 2. 如果两者不一致, 中断测试方法, 抛出异常信息 AssertionFailedError 

#### 6、RedisTemplate和StringRedisTemplate

[关于RedisTemplate和StringRedisTemplate](https://blog.csdn.net/notsaltedfish/article/details/75948281)

其实他们两者之间的区别主要在于他们使用的序列化类。

- RedisTemplate使用的是 JdkSerializationRedisSerializer
- StringRedisTemplate使用的是 StringRedisSerializer

当你的redis数据库里面本来存的是字符串数据或者你要存取的数据就是字符串类型数据的时候，那么你就使用StringRedisTemplate即可，
但是如果你的数据是复杂的对象类型，而取出的时候又不想做任何的数据转换，直接从Redis里面取出一个对象，那么使用RedisTemplate是更好的选择。



## 十一、SpringBoot集成Dubbo

#### 1、开发Dubbo服务接口

#### 2、开发Dubbo服务提供者

#### 3、开发Dubbo服务服务消费者

## 十二、SpringBoot使用拦截器

1、按照SpringMVC的方式编写一个拦截器

2、编写一个配置类继承WebMvcConfigurerAdapter类

3、为该配置类添加@Configuration注解，标明此类为一个配置类，让SpringBoot扫描到

4、覆盖其中的方法，并添加已经写好的拦截器

```java
@Configuration
public class SessionConfiguration extends WebMvcConfigurerAdapter
{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor()).addPathPatterns("/**").excludePathPatterns();
    }
}
```

## 十三、SpringBoot中使用servlet

### 方式一：通过注解方式实现

1、使用servlet3的注解方式编写一个servlet

2、在main方法的主类上添加注解@ServletComponentScan(basePackages="com.xx.xxx.xx")

### 方式二：通过SpringBoot的配置类实现

1、编写一个普通的servlet

2、编写一个SpringBoot的配置类（即加@Configuration注解）

​	在配置类中使用ServletRegistrationBean方法将Servlet注入

## 十四、SpringBoot中使用Filter

### 方式一：通过注解方式实现

1、编写一个servlet3的注解过滤器

2、在main方法的主类上添加注解@ServletComponentScan(basePackages="com.xx.xxx.xx")

### 方式二：通过SpringBoot的配置类实现

1、编写一个普通的Filter

2、编写一个SpringBoot的配置类（即加@Configuration注解）

​	在配置类中使用FilterRegistrationBean方法将Filter注入

## 十五、SpringBoot配置字符编码

方式一：使用传统的spring提供的字符编码过滤器

CharacterEncodingFilter

***注意：只有在spring.http.encoding.enable=false配置生效，过滤器才会启用***

方式二：在application.properties中配置字符编码

```properties
#编码格式
spring.http.encoding.enabled=true
spring.http.encoding.charset=UTF-8
spring.http.encoding.force=true
```

## 十六、SpringBoot开发非Web项目（纯java）

### 方式一：

直接在main方法中，根据SpringApplication.run()方法获取返回的Spring容器对象，再获取业务Bean进行调用

```java
@SpringBootApplication
public class SpringBootJavaApplication {

    public static void main(String[] args) {
        //返回Spring容器
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootJavaApplication.class, args);
        UserService userService = (UserService) context.getBean("userServiceImpl");

        String hello = userService.sayHello("Spring Boot JAVA");
        System.out.println(hello);
    }

}

```

### 方式二：

1、Spring的入口类实现CommandLineRunner接口

2、覆盖CommandLineRunner接口中的run方法，run方法中编写具体的处理逻辑即可

```java
@SpringBootApplication
public class SpringBootJavaApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        //启动SpringBoot，启动spring容器
        SpringApplication.run(SpringBootJavaApplication.class, args);
    }

    //相当于java的main方法
    @Override
    public void run(String... args) throws Exception {
        String hello = userService.sayHello("Spring Boot JAVA");
        System.out.println(hello);
    }
}
```

### Tip:关闭SpringBoot日志输出图标

```java
//关闭图标输出的设置
SpringApplication springApplication 
				= new	SpringApplication(SpringBootJavaApplication.class);
springApplication.setBannerMode(Banner.Mode.OFF);
springApplication.run(args);
```

## 十七、SpringBoot 程序部署

### 1、war包部署

1）程序入口类需扩展继承SpringBootServletInitializer类

2）程序入口类覆盖方法如下：

```java
@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
```

3）更新包为war，在pom.xml中修改<packaging>war</packaging>

4）配置SpringBoot打包插件

```xml
<plugin>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
```

5）在项目中通过maven下的install在本地maven仓库安装一个war包，然后将war包部署到Tomcat中运行（上线部署）

​	a、将打包成的war包拷贝至Tomcat目录下的webapps中

![images/](C:\Users\Lss\AppData\Roaming\Typora\typora-user-images\1547538060051.png)

​	b、在Tomcat目录下的bin目录启动Tomcat服务

![images/](C:\Users\Lss\AppData\Roaming\Typora\typora-user-images\1547538131389.png)

​	c、启动之后即可运行，在浏览器输入访问地址就可以访问了

### 2、jar包部署

1）SpringBoot部署jar包程序，在pom.xml中引入SpringBoot的maven插件

```xml
<plugins>
    <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
    </plugin>
</plugins>
```

2）在项目中使用maven中的install在本地仓库安装成一个jar（或者使用maven中的package安装成一个jar，这个jar是在本地项目路径下的target目录下）

3）在cmd下使用java -jar 运行上一步生成的jar，即可启动SpringBoot程序

4）打开浏览器即可访问

## ●十八、SpringBoot集成Thymeleaf模板

### 1、认识Thymeleaf

Thymeleaf是一种很流行的模板引擎，该模板引擎采用java语言开发；

模板引擎是一个技术名词，是跨平台跨领域的概念，在java语言体系下有模板引擎，在C#。PHP下也有模板引擎，甚至正在JavaScript中也会用到模板引擎技术；

Java生态下的模板引擎技术有Thymeleaf、Freemaker、Velocity、Beet（国产）等；

Thymeleaf模板引擎既能用于web环境下，也能用于非web环境下，在非web环境下，他能直接显示模板上的静态数据，在web环境下，它能像jsp一样从后台接收数据并替换掉模板上的静态数据；

Thymeleaf他是基于HTML的，一HTML标签为载体，Thymeleaf要寄托在HTML标签下实现对数据的展示；

SpringBoot继承了Thymeleaf的模板技术，并且SpringBoot官方也推荐使用Thymeleaf来替代jsp技术；

Thymeleaf是另外的一种模板技术，它本身并不属于SpringBoot，SpringBoot只是很好的集成这种模板技术，作为前端页面的数据显示。

### 2、SpringBoot集成Thymeleaf

1）在maven中引入Thymeleaf的依赖，加入依赖配置项

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

2）在SpringBoot的核心配置文件application.properties中对Thymeleaf进行配置

```properties
#关闭缓存
spring.thymeleaf.cache=false
#使用遗留的HTML5去掉对html标签的校验
spring.thymeleaf.mode=LEGACY HTML5

```

在使用SpringBoot过程中，如果使用Thymeleaf作为模板文件，则要求HTML格式必须为严格的HTML5格式，必须有结束标签，否则会报错；如果不想对标签进行严格验证，使用spring.thymeleaf.mode=LEGACY HTML5去掉验证

3）写一个Controller去映射到模板页面（和SpringMVC基本一致），例：

```java
@Controller
public class ThymeleafController {
    @RequestMapping("/index")
   public String index(Model model){
        model.addAttribute("msg","SpringBoot 集成 Thymeleaf ");
        return "index";
    }
}
```

4）在src/main/resources的templates下新建一个index.html页面用于展示数据：

HTML页面的<html>元素中添加以下属性：<html xmlns:th="https://www.thymeleaf.org/">

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org/">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <p th:text="${msg}">123456</p>
    <div th:text="${msg}">123456</div>
    <span th:text="${msg}">123456</span>
</body>
</html>
```

★**SpringBoot使用Thymeleaf作为视图展示，约定将文件放置在src/main/resources/templates目录下，静态资源放在src/main/resources/static目录下**



### 3、Thymeleaf的标准表达式

[官方文档使用手册](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#standard-expression-syntax)

**Thymeleaf的标准表达式主要有以下几种：**

#### 1）标准变量表达式

**语法：${xxx.xxx}** 

变量表达式用于访问容器（Tomcat）上下文环境中的变量，功能和JSTL中的${}相同

Thymeleaf中的变量表达式使用${变量名}的方式获取其中的数据

**例：**

在SpringMVC中的Controller中使用model.addAttribute向前端传输数据，代码如下：

```java
@Controller
public class ThymeleafController {
    @RequestMapping("/index")
   public String index(Model model){

        User user = new User();
        user.setId(1);
        user.setAddress("北京");
        user.setEmail("1234567@163.com");
        user.setName("张三");
        user.setPhone("12345678");

        model.addAttribute("user",user);

        model.addAttribute("msg","SpringBoot 集成 Thymeleaf ");
        return "index";
    }

}
```

前段接收代码：

```html
<p>
    <span th:text="${user.name}">name</span>
    <span th:text="${user.phone}">123xxxxx</span>
    <span th:text="${user.email}">123@xxx.com</span>
    <span th:text="${user.address}">北京XXX</span>
</p>
```



#### 2）选择变量表达式

**语法：*{xxx.xxx}** 

与标准变量表达式写法类似

a、先使用th:object获取读取对象，然后再使用*{对象的属性值}读取数据

b、在使用了th:object之后，还可以使用${对象.属性值}来读取数据【即：标准变量表达式可以和选择变量表达式混合使用】

c、也可以不使用th:object获取对象，直接使用*{对象名.属性值}来获取数据

```html
<div th:object="${user}">
    <p>name:<span th:text="*{name}"></span></p>
    <!--当指定object对象时，也可以使用${#object.属性值}进行访问数据-->
    <p>phone:<span th:text="${#object.phone}"></span></p>
    <p>email:<span th:text="*{email}"></span></p>
    <p>address:<span th:text="${user.address}"></span></p>
</div>
```



#### 3）URL表达式



### 4、Thymeleaf的常见属性

### 5、Thymeleaf字面量

### 6、Thymeleaf字符串拼接

### 7、Thymeleaf三元运算判断

### 8、Thymeleaf运算和关系判断

### 9、Thymeleaf表达式基本对象

### 10、Thymeleaf表达式功能对象

### 11、Thymeleaf咋SpringBoot中的配置

## 十九、Springboot中使用@Async实现异步调用

[讲解文章](http://blog.didispace.com/springbootasync/)

在Spring Boot中，我们只需要通过使用`@Async`注解就能简单的将原来的同步函数变为异步函数

为了让`@Async`注解能够生效，还需要在Spring Boot的主程序中配置`@EnableAsync`

什么是“异步调用”？

“异步调用”对应的是“同步调用”，**同步调用**指程序按照定义顺序依次执行，每一行程序都必须等待上一行程序执行完成之后才能执行；**异步调用**指程序在顺序执行时，不等待异步调用的语句返回结果就执行后面的程序。

## 二十、Springboot整合RabbitMQ

### 1、配置Windows中的RAbbitMQ环境

[安装教程](https://blog.csdn.net/qq_31634461/article/details/79377256)

**安装过程中的问题：**

安装rabbitmq时
安装过程detail提示ERROR：epmd error for host xxxx(主机名) : address(can't connect to host/port)
安装完后在cmd面板开启web管理页面，提示：xxxxxx  unchanged，无法对主机xxx做出改变

解决方案：若安装了RabbitMQ，先删除RabbitMQ服务，卸载RabbitMQ，然后杀死epmd.exe进程，删除C:\Users\liushs-s\AppData\Roaming\RabbitMQ
转到控制面板->系统->高级->环境变量，添加一个名为：RABBIT_NODENAME 的变量，值设置为：rabbit@localhost
并重新安装RabbitMQ，即可解决。
[解决方案链接](https://stackoverflow.com/questions/38343656/epmd-error-for-host-myhost-address-cannot-connect-to-host-port-on-windows-10)

***个人理解：在设置了环境变量之后，安装时就会直接注册到用户节点，没有环境变量时，应该是直接注册在系统节点，之后再开启web服务时无法连接到用户节点，所以访问http://localhost:15672时，无法访问***

### 2、[Springboot项目中使用RabbitMQ](http://blog.didispace.com/spring-boot-rabbitmq/)

