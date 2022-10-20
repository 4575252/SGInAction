# 关于本项目
[![Language](https://img.shields.io/badge/Language-Java_8_121-007396?color=orange&logo=java)](https://github.com/4575252/SpringBootBook)
[![Framework](https://img.shields.io/badge/Framework-Spring_Boot_2.7.4-6DB33F?logo=spring)](https://github.com/4575252/SpringBootBook)
[![Lombok](https://img.shields.io/badge/Lombok-Spring_Boot_1.18.20-pink?logo=lombok)](https://github.com/4575252/SpringBootBook)
[![Swagger2](https://img.shields.io/badge/Swagger2-Knife4j_3.0.2-blue?logo=swagger)](https://github.com/4575252/SpringBootBook)

> 本文是ssm集成教程，也是面向前后端分离的后端实现，是与SpringBoot对比的好材料，但不是老旧系统。

# 整合步骤
- 创建web模块
  - 项目中添加模块SSMTest，选择空的项目，maven骨架也不要
  - 继续在项目中选中本项目，然后添加web
  - 将web目录移动到main下，重命名为webapp
  - pom文件修改打包方式为war
  - 项目配置中检查原先web的地方有没爆红，有的话修改正确的路径为webapp
- 基础依赖，数量很多，具体查看pom
- 数据库建表语句，查看resources
- resources下配置文件
  - applicationContext.xml
  - mybatis-config.xml
  - jdbc.properties
  - log4j.properties
  - spring-mvc.xml
- 修改web.xml,引入常用配置，并将spring整合到web中
- 添加mvc代码，进行测试
  - 添加user、controller、dao
  - 用插件生成mybatis脚本，当然sql还是要自己手敲
- pom中添加build代码，主要是引入tomcat，刷新配置后，在maven的控制面板找到plugin，可以进行tomcat的run，并选择debug运行

> 这里就整合成功啦~

# 改进
- 统一的数据格式
  - 添加domain/ResponseResult，修改controller，让执行成功、失败都有同样数据，不过错误处理还是不够智能
- 完善CRUD
  - 重点在于分页处理，pom引用了pagehelper，mybatis-config中也配置了插件；
  - CU操作注意要用到post、put的映射注解，参数用RequestBody注解！
  - Del操作用Restful风格，采用@pathVariable获取参数
- 统一异常处理方案
  - 这个也算常见代码如下，如果不是用RestControllerAdvice，而是用ControllerAdvice，记得方法体要有ResponseBody
- 声明式事务
  - 在serviceImpl的方法上加@@Transactional注解，即可完成，当然，分布式环境会更复杂
- 拦截器
  - 新建一个类，继承HandlerInterceptor，重写他的方法，然后在spring-mvc的配置文件中开启
- AOP
  - pom引入aspectjweaver组件，applicationContext中开启注解支持
  - 定义切面类，切点，然后增强
  - 注意aop一般常用于service，controller用拦截器更合适，这里做了两个容器，所以也切入不了controller

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseResult allExceptionHandler(Exception ex){
        return new ResponseResult(500, ex.getMessage());
    }
}
```

