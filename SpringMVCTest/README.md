# 关于本项目
[![Language](https://img.shields.io/badge/Language-Java_8_121-007396?color=orange&logo=java)](https://github.com/4575252/SpringBootBook)
[![Framework](https://img.shields.io/badge/Framework-Spring_Boot_2.7.4-6DB33F?logo=spring)](https://github.com/4575252/SpringBootBook)
[![Lombok](https://img.shields.io/badge/Lombok-Spring_Boot_1.18.20-pink?logo=lombok)](https://github.com/4575252/SpringBootBook)
[![Swagger2](https://img.shields.io/badge/Swagger2-Knife4j_3.0.2-blue?logo=swagger)](https://github.com/4575252/SpringBootBook)

- [学习资料来源](https://www.bilibili.com/video/BV1AK4y1o74Y/?spm_id_from=333.999.0.0&vd_source=2cec6804a8b1b3038974e1b8ed2bcbb6)
- [SpringMVC官方文档]()

- 
>本章核心内容有
> - 入门案例，讲解如何搭建环境，引出web.xml两个核心解析器和映射关键配置！
> - 讲解后端映射注解，前端两种处理风格，介绍RestFul风格要求（uri即资源，crud风格，简单id、复杂传json）
> - RestFul、QueryString在CRUD四种场景下的使用套路！ 
> - 补充如何响应前端处理，有json返回，页面跳转，视图解析器的使用
> - 前端控制器如何读写请求域、会话域
> - 如何统一处理异常
> - 拦截器在登录会话域权限使用的案例
> - 文件上传下载
> - 过一遍单体模式、前后端开发的执行过程


# 一、入门案例
1、新建空工程，添加web模块，添加html测试
- 这里实例用的空工程，所以要在项目设置中对module添加web模块，然后移动到main下改名为webapp，再修改路径以免爆红
- pom要修改打包方式为war
- 添加web相关依赖，及tomcat插件（这些操作在ssm集成项目中也操作过了）
- webapp下添加html文件，启动tomcat测试，ok，这里基本的就ok了

2、在上面的基础上，引入springMVC依赖，添加controller和jsp，访问/hello测试跳转和url参数获取
- pom配置,引入jsp、servlet、springMVC等依赖
- web.xml配置，添加dispatchServlet，springmvc配置文件
- springmvc配置

# 二、核心配置

**web.xml要点分析：**

web容器默认两个servlet，上面的配置用DispatchServlet替换了DefaultServlet，静态资源用spring-mvc的配置做了补充！
- DefaultServlet对静态资源进行解析
- JspServlet解析jsp,jspx文件
- DispatchServlet的urlpatten三种方式分别对应了不处理静态（mvc配置有补全）、仅处理.do、全部处理三种方式

**@RequestMapping**

- 请求路径
  - 打开注解源码，可以看到target可以用在类和方法上，并且value和path互相映射，所以不写属性名，可以自动映射到path
  - 作用在类上面，会默认对所有方法加上前缀
- 请求方法
  - 属性method，请求方法设定，枚举，有get、del、post、put、head等枚举，后面相应有了postmapping等四个常用扩展注解！
- 请求参数
  - 属性params，设定必有或必须没有的参数，在value里放感叹号，字符串数组，所以可以放多个
    - 还可以指定这个参数必须等于某个值，如`params="code=gt"`,或`params="code!=gt"`
- 请求头
  - 属性headers，同params，字符串数组，可以限定必须有或没有的值
- 指定请求头
  - 属性consumes，同params，如值为`Content-Type=multipart/from-data`，才可以进行文件上传？ 后面还会聊到文件上传，这里TODO

# 三、接收前端请求的配置
**RestFul风格**

- 每一个URI代表一种资源
- 客户端使用GET、PUT、POST、DELETE表示对服务端资源的操作
- 简单参数将ID放在URL路径上，如 /user/1 Get，获取id=1的user信息
- 复杂参数转换成json或xml放到请求体中

**RestFul取数**

- @PathVariable，作用在controller的方法参数上, @GetMapping中value中对应的参数要用`大括号`！
  - 多个参数一般推荐用json传递，这里也能实现，用value中不同的名称指定也行，不过不推荐，如`value="/user/{id}/{name}"`
- json数据格式，外部用花括号包括，内部是键值对格式，用逗号隔开，数组用方括号
- @RequestBody，获取请求体中的json格式参数，这里用到jackson-databind组件，//TODO SpringBoot中是否还需要这个注解及jackson组件
  - 参数一般用自定义对象，如vo或pojo，这里还可以用`Map`及集合对象`List<User>`！ 
  - jackson-databind配置如下：
```xml
<!-- pom依赖 -->
<dependency>
  <groupId>com.fasterxml.jackson.core</groupId>
  <artifactId>jackson-databind</artifactId>
  <version>2.9.0</version>
</dependency>
<!-- mvc配置中开启注解驱动 -->
<mvc:annotation-driven>
</mvc:annotation-driven>
```

**QueryString风格**
与RestFul风格的对比，也就是我们常用的`?XX=YY&AA=BB`
- @RequestParam, 作用于参数、并且可以指定url中的参数名,同名可省略, 常用于参数名对应不上的情况
  - 如果是数组就多个同名参数`XXX/user?id=xx&aaa=1&aaa=2`
- 按对象传递，
  - RestFul风格
    - GET请求，前端用queryString，后端简单参数直接用变量获取，复杂参数用对象，不需要注解
    - POST请求，前端用JSON，后端用@RequestBody注解(jackson-databind组件)
  - QueryString风格
    - GET请求，前端用queryString，后端简单参数直接用变量获取或加@RequestParam参数，复杂参数用对象，不需要注解
    - POST请求，前端用表单，后端不需要注解，直接用对象接收

**小结**
- mapping映射用GET、POST、PUT、DELETE简约时尚
- GET请求
  - 简单查询，参数用queryString风格，后端用变量接收；
  - 接口调用，采用RestFul风格，后端指定URL中用大括号，变量用@PathVariable
  - （少）复杂条件查询，参数用RestFul+Json格式，后端用@RequestBody+对象接收
- POST请求
  - 不管参数多寡，统一用RestFul+Json格式，后端用@RequestBody+对象接收
- PUT请求
  - 同POST
- DELETE请求
  - 同GET请求的接口调用

**日期格式转换**
- 可以自定义转换器，继承Converter，并对spring相关控制器进行授权，过程繁琐，推荐第二种方法
- 使用@DateTimeFormat(pattern='yyyy-MM-dd')的参数补充形式进行自动转换


# 四、响应回前端的配置

**@ResponseBody**

- 反馈json数据给前端，在方法、类上用这个注解即可，当然，还可以把@Controller换成@RestController
- 将对象转换json这工作，只要用了这注解，spring自动处理了

**页面跳转**
前端控制器中
- 使用`return “xxx.jsp”` 可以完成页面跳转
- 使用`return “forward:xxx.jsp”` 可以完成页面跳转
- 使用`return “redirect:xxx.jsp”` 重定向，结果与forward一致，但url会变化！

**视图解析器**
ssh时代，习惯将jsp放在WEB-INF下，提高安全性，如果所有的请求都在相同位置，则视图解析器可以提高效率
- mvc配置下，创建一个bean，并指定前缀、后缀
- 前端控制器，默认使用掐头去尾的名字，如果要全称，则用上forward或redirect

```xml
<!-- spring-mvc.xml 末端即可 -->
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/page/"/>
    <property name="suffix" value=".jsp"/>
</bean>
```

```java
    @RequestMapping("/testJumpJsp")
    public String testJumpJsp(){
        return "forward:/WEB-INF/page/test.jsp";
    }

    @RequestMapping("/testJumpJsp2")
    public String testJumpJsp2(){
        return "test";
    }
```

# 五、请求处理（请求到响应）
**获取原生对象**

前端控制器中，在方法参数中用以下三个参数
- HttpServletRequest
- HttpServletResponse
- HttpSession

**获取请求头和cookie**

前端控制器中，在方法参数中用以下参数
- 请求头用注解@RequestHead("XXXX", required=false)
- cookie用注解@CookieValue("JSESSIONID", required=false), 当然，用上面的request对象获取cookie数组也是可以的。



**使用model往域中存数据**
jsp当中取得model域对象的属性值，实际上是取得request对象里attribute数组中的值，jsp中用`${requestScope.xxx}`
```java
@RequestMapping("/testRequestScope")
public String testRequestScope(Model model){
    model.addAttribute("xxx","hello");
    model.addAttribute("yyy","model");
    return "requestScope";
}
```

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <p>requestScope</p>
    <p>${requestScope.xxx}</p>
    <p>${requestScope.yyy}</p>
</body>
</html>
```

**使用ModelAndView**
类似上面，只是换了个对象和返回值
```java
@RequestMapping("/testRequestScope2")
public ModelAndView testRequestScope2(ModelAndView modelAndView){
    modelAndView.addObject("xxx","hello");
    modelAndView.addObject("yyy","model and view");
    modelAndView.setViewName("requestScope");
    return modelAndView;
}
```

**获取请求域对象的值**
这个操作是上面域对象存数据的逆操作，在前端控制器的方法参数，用@RequestAttribute("xxx")即可


**往session域存数据并跳转**
操作跟model存数据类似，颗粒度不是很够，SpringMVC的做法是在类上面用@SessionAttributes，并指定属性名，遇到model存数据自动丢到session域中
```java
@Controller
@SessionAttributes({"xxx"}) //xxx数据也要存session域
public class TestController {

    @RequestMapping("/testRequestScope2")
    public ModelAndView testRequestScope2(ModelAndView modelAndView){
        modelAndView.addObject("xxx","hello");
        modelAndView.addObject("yyy","model and view");
        modelAndView.setViewName("requestScope");
        return modelAndView;
    }

    @RequestMapping("/testSessionScope")
    public String testSessionScope(Model model){
        model.addAttribute("xxx","hello");
        model.addAttribute("yyy","model");
        return "requestScope";
    }
}
// 上面2个方法效果是一样的，区别在于jsp中用requestScope或sessionScope对象来取值
```

**获取session域数据**
同获取请求与类似，用@SessionAttribute("xxx")即可

# 六、补充jsp前端技术（历史）
jsp有九大隐式对象，比如requestScope，那个年代做前端展示有使用标准JSTL标签，也有自定义，比如母公司。。。

# 七、拦截器

**拦截器与过滤器的区别**

拦截器底层使用aop实现的，是位于DispatcherServlet内部，在handler处理器前的组件，一般用于权限管控等场景

过滤器是与DispatcherServlet平级，且在它之前，对Request、Response进行过滤处理的组件

**创建并配置拦截器**

- 创建一个类，实现HandlerInterCeptor
- 选择性重写preHandler、postHandler、afterCompletion方法
- 在mvc配置文件用 mvc:interceptors 进行配置
```xml
    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/**"/>
          <!--
            /test/*     只能拦截test目录下的请求
            /test/**    可以拦截多级目录下的请求
          -->
<!--            <mvc:exclude-mapping path="/"/>-->
            <bean class="com.iyyxx.interceptor.MyHandlerInterceptor" />
        </mvc:interceptor>
    </mvc:interceptors>
```

**案例：登录会话权限控制**
有这么几个要点：
- 登录接口、静态资源不拦截
  - 登录接口用`HttpSession.setAttribute("","")`，进行用户名保存
  - 退出接口,可以用session删除属性，或者把session清空
- 其他接口如果没权限就踢到登录页
  - 拦截器中的preHandle对request.getSession，用StringUtils判断属性是否为空
    - 为空则进行重定向，代码302，response.sendRedirect(request.getServletContext.getContextPaht()+"/xxx/login.html");
    - mvc.xml的配置文件要排除过滤路径，比如静态文件、WEB-INF、login


**多拦截器下的执行顺序**
顺序如下：
- preHandler，按拦截器顺序从前往后执行
- postHandler，按拦截器顺序从后往前执行
- afterCompletion,同postHandler,从后往前

> 注意：当尾部的拦截器preHandler结果为false，则自身post、after均不执行，后续只执行前置拦截器放行的afterCompletion！

# 八、异常统一处理

**方式一：HandlerExceptionResolver**

创建一个类，实现HandlerExceptionResolver接口，声明@Component，并且mvc中要被扫描到！重写仅有的那个方法：
- 参数：request
- 参数：response
- 参数：handler，执行的前置方法
- 参数：ex，异常信息

处理思路：根据异常类型，创建对应的ModelAndView，将ex.getMessage返回到error.jsp。


**方式二：@ControllerAdvice**

创建一个类，使用@ControllerAdvice和@Component注解，并且被扫描！
方法用@ExceptionHandler标识处理的异常

处理思路同上，方法参数可以用上面的，数量可以灵活匹配，返回值可以是字符串、实体类Result/json，或ModelAndView

# 九、文件上传下载
文件上传下载有这些通用要求：
- 文件上传
  - 获取原生文件的文件名
  - 获取文件的MIME类型
  - 获取文件的大小
  - 获取文件的输入流，后续可以转oss
- 文件下载
  - 设置响应头的MIME类型，也就是提供Content-Type
  - 设置响应头支持中文，Content-disposition


前端页面：form表单，post提交，enctype="multipart/from-data"
```html
<form action="/testUpload" method="post" enctype="multipart/form-data">
    <input type="file" name="uploadFile"/>
    <input type="submit">
</form>
```
后端程序：使用commons-fileupload组件，使用org.springframework.web.multipart.commons.CommonsMultipartResolver类进行接收

CommonsMultipartResolver 声明为bean，设置字符编码utf-8，单个文件大小，总大小等设置
```xml
<dependency>
    <groupId>commons-fileupload</groupId>
    <artifactId>commons-fileupload</artifactId>
    <version>1.4</version>
</dependency>

<!--
    文件上传解析器
    注意：id必须为 multipartResolver
    如果需要上传文件可以放开相应配置
-->
<bean class="org.springframework.web.multipart.commons.CommonsMultipartResolver" id="multipartResolver">
  <property name="defaultEncoding" value="utf-8" />
  <property name="maxUploadSize" value="#{1024*1024*100}" /> <!-- 总的大小上限，单位字节 -->
  <property name="maxUploadSizePerFile" value="#{1024*1024*50}" /> <!-- 单个文件上限50MB -->
</bean>
```

controller
```java
    @RequestMapping(value = "/testUpload", method = RequestMethod.POST)
    public String testUpload(MultipartFile uploadFile, HttpServletRequest request) throws IOException {
        String filename = UUID.randomUUID().toString().replace("-","") + uploadFile.getOriginalFilename().toString();
        uploadFile.transferTo(new File(filename));
        return "forward:/success.jsp";
    }
```

![](http://image.iyyxx.com/i/2022/10/26/63588e442a63e.png)

![](http://image.iyyxx.com/i/2022/10/26/63588dc72f2b9.png)

![](http://image.iyyxx.com/i/2022/10/26/63588ddae4267.png)