# [***常用IT博客***](https://blog.csdn.net/doctor_who2004/article/details/46381707)



#### 1、pring Security 的基本组件SecurityContextHolder

[讲解](https://blog.csdn.net/andy_zhang2007/article/details/81559975)

`Spring Security` 中最基本的组件应该是`SecurityContextHolder`了。这是一个工具类，只提供一些静态方法。这个工具类的目的是用来保存应用程序中当前使用人的安全上下文。

SecurityContextHolder可以工作在以下三种模式之一:

MODE_THREADLOCAL (缺省工作模式)

MODE_GLOBAL

MODE_INHERITABLETHREADLOCAL

修改SecurityContextHolder的工作模式有两种方法 :

​	设置一个系统属性spring.security.strategy;

​	调用`SecurityContextHolder`静态方法`setStrategyName()



#### 2、Spring Boot中RestTemplate的使用

[详解 RestTemplate 操作](https://blog.csdn.net/itguangit/article/details/78825505)

初始配置：

```java
@Autowired  
private RestTemplateBuilder builder;  

@Bean  
public RestTemplate restTemplate() {  
    return builder.build();  
}  
```

#### 3、springboot集成Redis时使用的RedisTemplate

[springboot之使用redistemplate优雅地操作redis](https://www.cnblogs.com/superfj/p/9232482.html)

#### 4、@Primary 在spring中的使用

#### [spring @Primary-在spring中的使用](https://blog.csdn.net/qq_16055765/article/details/78833260)

@Primary标注的Bean，如果在多个同类Bean候选时，该 Bean 优先被考虑。「***多数据源配置***的时候注意，必须要有一个主数据源，用 @Primary 标志该 Bean」

#### 5、Bean中的@Data的使用【lombok使代码更加简洁】

[参考](https://blog.csdn.net/zhou_p/article/details/78405539)

使用@Data时需要安装lombok插件，启用lombok插件，在Bean中导入lombok的使用：import lombok.Data;

然后在Bean类中加入注解即可【可以在类外加上注解、或者可以在类方法名class之前加入：public @Data class Student】

**lombok提供的几个注解**

●@Data : 注解在类上, 为类提供读写属性, 此外还提供了 equals()、hashCode()、toString() 方法

●@Log4j : 注解在类上, 为类提供一个属性名为 log 的 log4j 的日志对象

●@Slf4j : 注解在类上, 为类提供一个属性名为 log 的 log4j 的日志对象

●@NoArgsConstructor, @RequiredArgsConstructor, @AllArgsConstructor : 注解在类上, 为类提供无参,有指定必须参数, 全参构造函数

#### 6、【SpringBoot】日期格式转换问题

[参考](https://www.twblogs.net/a/5baa80a62b7177781a0e3e2b/zh-cn)

在使用 SpringBoot 的时候，首先遇到的问题就是日期格式转换了。实体类中有时间类型的属性，往往会在前端与后台的数据交换中出现日期格式转换的问题。

具体解决方式：

**1、使用 SpringBoot 默认的 Json 数据转换 Jackson 时有两种方法可以设置**

**方法一：**

时间类型的属性上添加注解，这样需要在每一个实体类中需要转换的属性中进行设置，有点麻烦，当然，不要忘了加上时区的设置，由于北京时间是东八区，所以使用 GMT-8。

`@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")`

**方法二：**

配置文件 application.properties 中全局处理，进行全局设置，也就不需要在每一个类中都设置了。

`#配置Jackson时间格式转换
spring.jackson.date-format=yyyy-MM-dd HH:mm:ss
spring.jackson.time-zone=GMT+8`

**2、使用阿里的 fastJson 进行 json 数据转换**

时间类型的字段上添加，在每一个需要转换的字段中进行设置

`@JSONField(**format**="yyyy-MM-dd HH:mm:ss")`

