# 关于本项目
[![Language](https://img.shields.io/badge/Language-Java_8_121-007396?color=orange&logo=java)](https://github.com/4575252/SpringBootBook)
[![Framework](https://img.shields.io/badge/Framework-Spring_Boot_2.7.4-6DB33F?logo=spring)](https://github.com/4575252/SpringBootBook)
[![Lombok](https://img.shields.io/badge/Lombok-Spring_Boot_1.18.20-pink?logo=lombok)](https://github.com/4575252/SpringBootBook)
[![Swagger2](https://img.shields.io/badge/Swagger2-Knife4j_3.0.2-blue?logo=swagger)](https://github.com/4575252/SpringBootBook)

- [学习资料来源](https://www.bilibili.com/video/BV1Bq4y1f7YD?p=3&vd_source=2cec6804a8b1b3038974e1b8ed2bcbb6),
- [mp官方文档](https://baomidou.com/pages/24112f/)
MybatisPlus（简称mp）只是在mybatis上做扩展升级，并没有做修改，所以mybatis的所有操作都能用，mp主要是简化了代码，提高效率，具体如下：
- 单表crud由mp封装好的接口来直接使用
- 封装好的接口可以方便的调用service（mapper）

>本章核心内容有(依赖Free Mybatis Tools插件快速生成mapper.xml）
> - 环境配置（回顾、与mybatis的差异对比）
> - mp的配置（mapper、表别名、列别名、驼峰和映射、注解策略、日志）
> - Wrapper进行crud，引出lambdaWrapper
> - mp和mybatis的混合使用，这里还混合了wrapper的条件，方便多表查询、分页时的使用
> - mp自带的分页处理使用，使用Page对象
> - 常用插件的使用：乐观锁、自动填充（审计）、逻辑删除；另外还有行级多租户的插件，不过可以考虑用表前缀做多租户
> - 多表写入时主表id如何获取？

# 一、基础配置
mp只是在mybatis上做增强，所以只做了这些修改或新增
- 替换pom中的mybatis为mp依赖，实际上包含了mybatis
- mapper类继承mp的BaseMapper<T>
- service实现类类继承mp的IService<mapper,domain>

# 二、核心配置

**关于mapper配置**

一种是在mapper类上用@Mapper注解，另一种是在工程主类上用@MapperScan指定包


**表的别名指定**

一种是在domain类上用@TableName注解，另一种是用配置文件进行全局设定前缀`mybatis-plus.global-config.db-config.table-prefix: `


**主键生成策略**

主键默认使用雪花算法，可以替换为数据库自增长（需要表有设计！）或uuid
- AUTO，依赖数据库提前设计的自增长策略
- NONE，未设置，用全局（默认全局是雪花算法，可以在配置文件中修改`mybatis-plus.global-config.db-config.id-type`)
- INPUT，手工输入
- ASSIGN_ID, 雪花算法（去除横线）
- ASSIGN_UUID, uuid
```java
@TableId(type = IdType.AUTO)
private Long id;
```


**驼峰映射、字段映射**

配置文件中：`mybatis-plus.configuration.map-underscore-to-camel-case` 默认为true，打开了配置！
如果字段名特殊可以用@TableField注解指定字段名

**日志**

配置文件中：`mybatis-plus.configuration.log-impl` 修改为std，sql非常详细！

//TODO 补充Slf4j中的详细日志配置？


# 三、条件构造器Wrapper

Wrapper的核心是AbstractWrapper，具体使用是它的两个主要子类QueryWrapper和UpdateWrapper，[文档可以参考](https://baomidou.com/pages/10c804/#abstractwrapper)

其中QueryWrapper需要掌握的是：
- 查询条件的调用，如gt、lt等
- 返回字段的设定，包含，不包含（lambda语法）
- 排序、分组、再过滤

```java
QueryWrapper<User> queryWrapper = new QueryWrapper(new User());
queryWrapper.gt("age", 18);
queryWrapper.orderByDesc("name");
//        queryWrapper.select("id","name");
queryWrapper.select(tableFieldInfo->!tableFieldInfo.getColumn().equals("user_name"));
//        System.out.println(queryWrapper.getCustomSqlSegment());   //queryWrapper核心代码片段
List<User> users = userMapper.selectList(queryWrapper);
System.out.println(users);
```


UpdateWrapper与QueryWrapper都是继承了AbstractWrapper，所以在执行update操作时，select条件一致，只是多了set操作而已。

这里着重加一下LambdaQueryWrapper, 与上面相比，用函数式编程的方法引用比上面用字符串，在编译阶段就已经做了校验了，减少bug出现的问题
```java
LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper(new User());
queryWrapper.gt(User::getAge, 18);
queryWrapper.select(tableFieldInfo->!tableFieldInfo.getColumn().equals("user_name"));
queryWrapper.orderByDesc(User::getName);
List<User> users = userMapper.selectList(queryWrapper);
System.out.println(users);
```

# 四、mp下使用mybatis的mapper配置
配置文件: 默认在resources下的mapper文件夹下，如需修改`mybatis-plus.mapper-locations: classpath*:xx/**/*.xml`

与Wrapper的混合使用(这块在分页、多表查询会用到！)
- mapper.xml 整块where条件替换为`${ew.customSqlSegment}`
- Mapper.java 参数比较特殊用`@Param(Constants.WRAPPER) Wrapper<User> wrapper`


# 五、分页操作
mybatis中分页使用的PageHelper，mp有自带分页插件，使用lambda、page对象，操作起来还是很丝滑的

首先是引入分页插件, 插件代码来源于官方文档
```java
@Configuration
@MapperScan("com.iyyxx.mybatisplustest.mapper")
public class MybatisPlusConfig {

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setUseDeprecatedExecutor(false);
    }
}
```


然后是样例代码

```java
LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper(new User());
queryWrapper.gt(false,User::getAge, 1l);
queryWrapper.orderByDesc(User::getName);

IPage<User> page = new Page<>();
page.setSize(1);
page.setCurrent(2);

userMapper.selectPage(page,queryWrapper);
System.out.println(page);
```

# 六、servie接口

同BaseMapper的提升一样，接口继承IService<Domain>, 在实现类中继承ServiceImpl<Mapper,Domain>并应用@Service注解

> 注：ServiceImpl实现类继承时已传入Mapper，自定义方法时可以直接用getBaseMapper进行操作！


# 七、代码自动生成
这里引用的是SpringBoot趣味实战书中的模板，在本人的另一个项目中也有同样代码，具体如下：

**1、pom文件引用**

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-generator</artifactId>
    <version>3.4.1</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-freemarker</artifactId>
</dependency>
<!-- 这里引入swagger是因为模板中用到了，原生非必须 -->
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>3.0.2</version>
</dependency>
```

**2、我的baseEntity和生成器**

具体参考common中两个类的原码即可，日常修改生成器的数据库、路径配置等信息即可

# 八、自动填充
也就是审计代码的insert、update自动处理,之前做过很多，下面在罗列一下步骤
- 新建一个handler继承MetaObjectHandler，重写2个方法，并声明为bean
- 实体类要对字段指定填充策略

```java
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
}

public class User {
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
```

> 注意！使用LambdaUpdateWrapper需要传入实体，不然无法触发。

```java
@Test
void testUpdate() {
    userService.lambdaUpdate()
            .eq(User::getName,"张三")
            .set(User::getAddress, "福建")
            .update(new User());
}
```

# 九、版本控制/乐观锁

版本控制就是针对放一字段，首次新增时为0，每次修改加1，当并发时后改的操作因条件不匹配而自动失效，从而实现乐观锁！设计步骤如下：
- MybatisPlus配置器添加一个拦截器 OptimisticLockerInnerInterceptor，注意这里有顺序，以官方文档为准！
- 实体类添加一个version字段，类型int（insert时默认0），并用上@Version注解
- 进行更新操作时只要字段有version，mp会自动拦截并自增1！

# 十、逻辑删除

操作方法：
- 配置文件中开启逻辑删除
- 设定逻辑删除字段
- 设定正常使用、非正常使用时该字段的数值（后面crud拼接sql会自动用上）
- 注意！这里需要在insert时自动填充数据，来源配置文件的设置！

```java
public class User {
    @TableField(fill = FieldFill.INSERT)
    private String delFlag;
}
```

```xml
mybatis-plus:
  global-config:
    db-config:
      logic-delete-field: delFlag # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)
      logic-delete-value: 1 # 逻辑已删除值(默认为 1)
      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)
```