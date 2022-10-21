# 关于本项目
[![Language](https://img.shields.io/badge/Language-Java_8_121-007396?color=orange&logo=java)](https://github.com/4575252/SpringBootBook)
[![Framework](https://img.shields.io/badge/Framework-Spring_Boot_2.7.4-6DB33F?logo=spring)](https://github.com/4575252/SpringBootBook)
[![Lombok](https://img.shields.io/badge/Lombok-Spring_Boot_1.18.20-pink?logo=lombok)](https://github.com/4575252/SpringBootBook)
[![Swagger2](https://img.shields.io/badge/Swagger2-Knife4j_3.0.2-blue?logo=swagger)](https://github.com/4575252/SpringBootBook)

> Spring框架学习

# Spring框架学习
Spring的优点：
- 简化JavaEE开发
- 易于整合其他框架
- 无侵入的功能增强

Spring核心：
- 控制反转（IoC）：对象控制器从类反转到spring上，易于做装配和改装
  - 耦合过强，比如需要替换子类，需要修改很多代码，spring的做法是：修改id或注解等方式
  - DI依赖注入，是IoC的一种应用场景，由spring来完成依赖关系的维护，有set、构造函数等注入方式
- 面向切面（AOP）:
- 
# 一、IOC学习
## 1.1、spring 快速入门
这里做了一个最精简的演示，通过演示可以看到，如果有多类适配器（子类），只需要修改配置文件，就完成了装配改装，而没有修改java代码！
这就是spring解耦的魅力！

**1、pom导入依赖**
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.1.9.RELEASE</version>
</dependency>
```

**2、添加spring配置文件**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!-- 创建连接池注入容器 -->
    <bean class="com.iyyxx.dao.impl.StudentDaoImpl" id="studentDao">
    </bean>
</beans>
```

**3、编写测试代码**
```java
ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

StudentDao dao = (StudentDao) applicationContext.getBean("studentDao");
Student student = dao.getStudengById(30);
System.out.println(student);
```

## 1.2、Spring IoC DI注入方式
有两种方式，具体如下：
1、xml配置(这里演示的是set方法的三种注入方式，还可以通过构造函数，还有复杂类型list、set、map等)
```xml
<!-- 基于xml的填充和引用及配置文件读取！ -->
<bean class="com.iyyxx.dao.impl.StudentDaoImpl" id="studentDao">
  <properties name="userName" value="张三" />
  <properties name="password" ref="xxx" />
  <properties name="password" ref="${xx.yy.zz}" />
  <constructor-arg name="" value=""/>
  <properties name="list">
    <list>
      <value>xxx</value>
      <value>yyy</value>
    </list>
  </properties>
  <properties name="set">
    <set>
      <value>xxx</value>
      <value>yyy</value>
    </set>
  </properties>
  <properties name="aryyay">
    <array>
      <value>xxx</value>
      <value>yyy</value>
    </array>
  </properties>
  <properties name="properties">
    <props>
      <prop key="k1">v1</prop>
      <prop key="k2">v2</prop>
    </props>
  </properties>
  <properties name="map">
    <map>
      <entry key="k1" value-ref="phone"/>
      <entry key="k2" value-ref="phone"/>
    </map>
  </properties>
</bean>
<!--读取properties文件-->
<context:property-placeholder location="classpath:jdbc.properties"></context:property-placeholder>
```

2、基于注解的方式
```java
@Autowired
private StudentDao studentDao;
```


## 1.3、补充xml配置
**SPEL表达式** 

xml中的value可以用`#{xx+yy}` 进行计算.

**读取配置文件**
```xml
<bean class="com.iyyxx.dao.impl.StudentDaoImpl" id="studentDao">
  <properties name="password" ref="${xx.yy.zz}" />
</bean>
<context:property-placeholder location="classpath:jdbc.properties"></context:property-placeholder>
```

**拆分、导入多个配置文件**
```xml
<import resource="classpath:applicationContext-book.xml" />
```

## 1.4、注解开发

**准备工作**
```xml
<context:component-scan base-package="com.iyyxx"/>
```
**Spring相关注解**
- IOC注解
  - @Component, @Controller, @Service, @Repository， 都用在类上，实现bean标签的效果，后面三个见名知意！
- DI注解
  - @Value，@Autowired，常用在私有元素上，做赋值或引用的注入。注：@Value中也可以使用SPEL表达式
  - @Qualifier，使用@Autowired时碰到多个bean匹配时指定名称做区分，不能单独使用！
- 配置文件相关
  - @Configuration，替代xml文件的功能。用AnnotationConfigApplicationContext容器来使用。
  - @ComponentScan，替代context：component-scan标签扫描功能，作用在@Configuration标签下！
  - @Bean，替代bean标签，用于第三方类注入，常用于@Configuration配置类下的get方法上。
  - @Properties，替代context:property-placeholder注解，也是作用在配置类上，取数时搭配成员变量@Value取值！

>不管是ssm还是SpringBoot尽量都使用注解开发，而ssm中的第三方jar才使用xml进行扫描配置！

# 二、AOP
AOP是Aspect Oriented Programming的缩写，面向切面编程，是一种不修改原来核心代码给程序动态做增强的技术。

## 2.1、快速入门
需求：让service包的所有方法在调用前后都输出，被调用了！

步骤：
- 定义切面类
- 切面类添加切入点、增强方式
- 开启切面代理，等待被调用触发！

**准备工作**
pom依赖
```xml
<!-- spring context依赖是不能少，这里仅罗列aop依赖 -->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.8.13</version>
</dependency>
```

准备好service, 过程略

**开发过程**
开发切面类，设置切点，增强方式，注意类也要声明为bean及切面类！
```java
@Component
@Aspect
public class ServiceAspect {
    @Pointcut("execution(* com.iyyxx.service..*.*(..))")
    public void pointCut(){
    }

    @Before("pointCut()")
    public void methodBefore(){
        System.out.println("方法被调用了");
    }
}
```

**aop 启动配置**

注解方式，配置类开启`@EnableAspectJAutoProxy`

xml方式如下：`<aop:aspectj-autoproxy />`

## 2.2、AOP核心概念
核心概念如下：
- JoinPoint，连接点，spring中就是方法
- PointCut，切入点，就是需要被增强的方法，一般搭配切点表达式来匹配
- Advice，通知/增强，具体增强的代码，如上面实例中方法执行前打印一句话。
- Target，目标对象，被增强的对象
- Aspect，切面，切入点和通知的结合
- Proxy，代理，一个类被增强后就产生了代理类

## 2.3、切点表达式
execution([权限修饰符] * com.iyyxx.service..*.*(..))
- 权限修饰符，可以忽略
- *，返回值类型
- 包名、类名、方法名也可以用*替代
- ..表示当前包及多级子包下的所有类
- (..) 表示方法的参数任意，可以是0到正无穷个
- () 表示方法没有参数才匹配！

## 2.4、切点函数@annotation，对切点表达式的自定义开发
> 这种自定义注解的方式比较灵活，但是代码植入的痕迹比较大！

开发步骤：
- 先开发元注解com.iyyxx.aspect.InvokeLog, @InvokeLog
- 在需要增强的方法函数上，显式加上@InvokeLog注解
- 切面类的切点表达式使用这个注解 @Pointcut("@annotation(com.iyyxx.aspect.InvokeLog)")
- 切面类的增强方法不变即可

## 2.5、通知
通知/增强有这么几种：
- Before，前置通知，在目标方法执行前执行。
- AfterRetuing，正常返回后通知，出异常不执行！
- After，强制返回后通知，有异常也执行！
- AfterThrowing，仅异常通知，仅在异常出现时执行！
- Around，环绕通知，常用于替换上面多种通知同时出现，这个是非常常用的！
```java
@Around("pointCut2()")
public void methodAround(ProceedingJoinPoint proceedingJoinPoint){
    System.out.println("around Before 目标方法执行前执行");
    try {
        proceedingJoinPoint.proceed(); //目标方法执行
        System.out.println("around AfterRetuing 正常返回后通知");
    }catch (Throwable throwable){
        throwable.printStackTrace();
        System.out.println("around AfterThrowing 仅异常通知");
    }finally {
        System.out.println("around After 强制返回后通知，有异常也执行");
    }
}
```

## 2.6 通知中获取方法相关信息
在通知中使用JoinPoint参数，获取signature签名，可以比较方便的获取类、方法信息！
>这里用断点调试+idea计算器很方便！

```java
@Before("pointCut()")
public void methodBefore(JoinPoint joinPoint){
    System.out.println("class="+joinPoint.getSignature().getDeclaringType());
    System.out.println("method="+joinPoint.getSignature().getName());
    System.out.println("方法被调用了");
}
```

## 2.7 通知中获取异常对象
参考如下:
```java
    @AfterThrowing(value = "pointCut()", throwing = "e")
    public void methodAfterThrowing(JoinPoint joinPoint, Throwable e){
        System.out.println("捕获到异常信息"+e.getMessage());
    }
```

## 2.8 aop案例
需求：某个方法的入参和出参都需要做加解密，同类方法有很多，未来可能会拿掉。

解决方案：用aop切面类，对入参进行解密，再对出参进行加密，切入点批量抓取，未来可以随时拿掉切面类！

## 2.9 aop排序
切面类上用@Order注解来定义， 数字小先执行！

## 2.10 动态代理
动态代理有JDK和Cglib两种，JDK需要被代理类有实现接口，而cglib不用，所有spring是两种都用，优先使用JDK动态代理， 切换方法如下：
```xml
<aop:aspectj-autoproxy proxy-target-class="true"/> <!-- 开启proxy-target-class，切换为cglib -->
```
> 注意，切换代理方式后，get bean可能需要指定接口才能从容器中取得！ 


# 三、第三方整合

## 3.1、整合junit
避免四处初始化容器的麻烦！引入2个依赖，在测试类用上@RunWith和@ContextConfiguration注解，代码如下
**pom.xml**
```xml
<dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
  <version>4.12</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>5.1.9.RELEASE</version>
</dependency>
```
Test.java
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class Test1 {
    @Autowired
    private TestService testService;

    @Test
    public void testMethod2() {
        testService.hello();
        testService.test();
    }
}
```

### 3.2、植入mybatis
先把mybatis植入进来，测试下，后面再进行集成！
- pom引入mybatis和mysql驱动包这两个依赖
- 先把user、userdao拷贝过来，并生成com/iyyxx/dao的mapper目录
- 配置mybatis-config.xml和jdbc.properties
- 配置TestMybatis类，进行测试
- 查询成功！

>正式整合mybatis(实际代码可以看SSMTest项目即可)

# 四、补充事务管理

**事务的概念**： 保持数据库的一致性，要么都成功，或都失败。

**事务的特性**
- 隔离性，多个事务之间互相隔离
- 原子性，事务是一个不可分割的整体
- 一致性，保障事务前后的状态，要么都成功，或都失败
- 持久性，提交成功数据就真的改变了

**事务的传播行为**
默认是吃大锅饭，内外层一个connection做commit或rollback，保持一致性，可修改为以下行为
![](http://image.iyyxx.com/i/2022/10/21/6351f6190cde1.png)