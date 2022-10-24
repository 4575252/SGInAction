# 关于本项目
[![Language](https://img.shields.io/badge/Language-Java_8_121-007396?color=orange&logo=java)](https://github.com/4575252/SpringBootBook)
[![Framework](https://img.shields.io/badge/Framework-Spring_Boot_2.7.4-6DB33F?logo=spring)](https://github.com/4575252/SpringBootBook)
[![Lombok](https://img.shields.io/badge/Lombok-Spring_Boot_1.18.20-pink?logo=lombok)](https://github.com/4575252/SpringBootBook)
[![Swagger2](https://img.shields.io/badge/Swagger2-Knife4j_3.0.2-blue?logo=swagger)](https://github.com/4575252/SpringBootBook)

- [官方学习文档](https://mybatis.org/mybatis-3/zh/getting-started.html),参照学习，完成quickStart
- 使用idea的liveTemplate，存入常用配置文件的模板，设置为xml等操作
- 引入Free Mybatis Tools 插件

>本章核心内容有
> - 动态标签，特别是忽略查询、更新的空属性！
> - 多表查询
> - 分页查询
> - 二级缓存（全局开启，局部设置，属于mapper级别，但因脏读一般不推荐使用，面试会用到）

# CRUD
略

# 配置文件
官方文档里都有，这边比较常用的是开启驼峰命名法、log4j，方便临时属性的读写
```xml
<settings>
    <setting name="mapUnderscoreToCamelCase" value="true"/>
    <setting name="logImpl" value="log4j"/>
</settings>
```

# #{}和${}区别
#{}可以防止sql注入,有预编译！ ${}是sql拼接，没有预编译，连单引号都没有。

# 注解开发
准备工作，这个也是一开始就配置好了：
```xml
<mappers>
    <package name="com.iyyxx.dao"/>
</mappers>
```
用@Select注解来实现dao.xml相关语句，不过复杂sql不移维护，还是建议以xml为主。

除了@Select，还有insert，update，delete注解可以提供使用

# 动态标签
>这里有三个场景
> - 查询时有参数为空则忽略
> - 更新时有参数为空则忽略
> - 查询时仅匹配一个参数，还可能碰到全空需要执行补一个参数的情况！

sql参数可能碰到null的情况，之前的做法是固定`where 1=1`， mybatis有动态标签来处理，具体如下：

**trim、where标签：** 查询数据时部分参数为NULL，需跳过：
```xml
<!-- 以下标签实现两个参数都没有、都有、或只有任意一个时，sql都能正常拼接后执行！ -->
<select id="" resultType="">
    select * from user
    <trim prefix="where" prefixOverrides="and|or">
        <if test="id!=null">
            id=#{id}
        </if>
        <if test="username!=null">
            and username=#{username}
        </if>
    </trim>
</select>

<!-- where标签登记与上面的trim加一堆参数 -->
<select id="" resultType="">
    select * from user
    <where>
        <if test="id!=null">
            id=#{id}
        </if>
        <if test="username!=null">
            and username=#{username}
        </if>
    </where>
</select>
```

**set标签：** 更新数据时部分参数为NULL，需跳过
```xml
<!-- set标签也可以解决update时属性为null的问题！ -->
<trim prefix="set" suffixOverrides=","></trim> <!--等价-->
<update id="" >
update user
<set>
    <if test="username!=null">
        username=#{username},
    </if>
    <if test="age!=null">
        age=#{age},
    </if>
</set>
where id=#{id}
</update>
```


**foreach标签：** 查询数据时碰到不限定集合参数时的写法
```xml
<!-- select * from user where id in (?,?,?,?)   -->
<select id="" resultType="">
    select * from user
    <where>
        <foreach collection="ids" open="id in(" close=")" item="id" separator=",">
            #{id}
        </foreach>
    </where>
</select>
```

**choose标签：** 用于任选、仅选一个参数，全空则默认的场景
```xml
<!--  类似switch、default的写法  -->
<select id="" resultType="">
    select * from user
    <where>
        <choose>
            <when test="username!=null">
                username=#{username}
            </when>
            <when test="age!=null">
                age=#{age}
            </when>
            <otherwise>
                id=3
            </otherwise>
        </choose>
    </where>
</select>
```

# sql片段抽取
用于去除冗余，类似定义变量，然后四处引用的场景

```xml
<sql id="baseSelect">id,username,age,address</sql>

<select id="" resultType="">
    select <include refid="baseSelect"/> from user
</select>
```

# ResultMap 映射
mybatis默认是开启自动映射，如果用好pojo设计并开启驼峰命名法可以省去很多麻烦，这边演示自定义映射的几种操作
```xml
<!-- 这里演示没有开启驼峰命名法对多字段的手工映射 -->
<resultMap id="orderMap" type="com.xx.x.xx">
    <!-- 默认开启自动映射所以其他匹配的字段可以不写！ -->
    <result column="user_id" property="userId"/>
</resultMap>
<select id="findAll" resultMap="orderMap">
    select id,user_id from orders
</select>

<!-- 关闭自动映射 -->
<resultMap id="orderMap" type="com.xx.x.xx" autoMapping="false">
    <!-- 其他字段将不会被自动赋值 -->    
    <result column="user_id" property="userId"/>
</resultMap>


<!-- 这里演示继承，效果与java oop类似，不赘述 -->
<resultMap id="orderMap" type="com.xx.x.xx" extends="baseOrderMap">
</resultMap>
```

# 多表查询

## 一对一的映射配置
java pojo部分
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer id;
    private Date createtime;
    private Integer age;
    private String remark;
    private Integer userId;
    private User user;  //增加了一个类成员属性
}
```

resultMap两种方式，任选一个id替换即可
```xml
<mapper namespace="com.iyyxx.dao.OrderDao">

    <resultMap id="baseOrderMap" type="com.iyyxx.domain.Order">
        <result property="id" column="id" />
        <result property="createtime" column="createtime" />
        <result property="age" column="age" />
        <result property="remark" column="remark" />
        <result property="userId" column="user_id" />
    </resultMap>

    <resultMap id="orderMap" type="com.iyyxx.domain.Order" autoMapping="false" extends="baseOrderMap">
        <result property="user.id" column="uid" />
        <result property="user.username" column="username" />
        <result property="user.age" column="age" />
        <result property="user.address" column="address" />
    </resultMap>

    <resultMap id="orderMapUseAssociation" type="com.iyyxx.domain.Order" autoMapping="false" extends="baseOrderMap">
        <association property="user" javaType="com.iyyxx.domain.User">
            <result property="id" column="uid" />
            <result property="username" column="username" />
            <result property="age" column="age" />
            <result property="address" column="address" />
        </association>
    </resultMap>

    <select id="findOrderAndUser" resultMap="orderMapUseAssociation">
        SELECT
            o.id, o.createtime,o.price,o.remark,o.user_id,u.id uid, u.username, u.age, u.address
        FROM
            orders o, `user` u
        WHERE
            o.user_id = u.id
          and o.id = #{id}
    </select>
</mapper>
```



## 一对多的映射配置
java pojo部分
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private Integer age;
    private String address;
    private List<Role> roles;   //key
}
```

resultMap两种方式，任选一个id替换即可
```xml

<mapper namespace="com.iyyxx.dao.UserDao">
    <resultMap id="baseUserMap" type="com.iyyxx.domain.User">
        <id property="id" column="id" />
        <result property="username" column="username" />
        <result property="age" column="age" />
        <result property="address" column="address" />
    </resultMap>

    <resultMap id="userRoleMap" type="com.iyyxx.domain.User" autoMapping="false" extends="baseUserMap">
        <collection property="roles" ofType="com.iyyxx.domain.Role">
            <id property="id" column="rid"/>
            <result property="name" column="name" />
            <result property="desc" column="desc" />
        </collection>
    </resultMap>

    <select id="findUserAndRole" resultMap="userRoleMap">
        SELECT
        u.*,r.id rid, r.`name`,r.`desc`
        FROM
        `user` u,
        user_role ur,
        role r
        WHERE
        u.id = ur.user_id
        AND ur.role_id = r.id
        AND u.id = #{id}
    </select>
</mapper>
```

## 分步查询
两个表分别有独立查询，修改其中一个为特定的ResultMap，在RM中设置select属性进行调取，最终由Mybatis进行自动映射
```xml

<resultMap id="baseUserMap" type="com.iyyxx.domain.User">
    <id property="id" column="id" />
    <result property="username" column="username" />
    <result property="age" column="age" />
    <result property="address" column="address" />
</resultMap>

<resultMap id="userRoleMapBySelect" type="com.iyyxx.domain.User" extends="baseUserMap">
    <collection property="roles"
                ofType="com.iyyxx.domain.Role"
                select="com.iyyxx.dao.RoleDao.findRoleByUserId" <!-- 关键 -->
                column="id"/>
</resultMap>
    
<select id="findByUserName" resultMap="userRoleMapBySelect">
    select * from user where username=#{username}
</select>
```
> 分布查询的优点在于可以进行懒加载，如果没被获取成员类的属性就不加载


# 分页查询

**集成工作**
pom
```xml
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>4.0.0</version>
</dependency>
```
mybatis-config
```xml
<plugins>
    <!-- 分页助手的插件要配置再mapper之前 -->
    <plugin interceptor="com.github.pagehelper.PageHelper">
        <property name="dialect" value="mysql"/>
    </plugin>
</plugins>
```

**使用**：
在执行dao的前输入一行分页代码即可，如果需要分页信息，new一个PageInfo，出入list即可！
```java
UserDao userDao = sqlSession.getMapper(UserDao.class);
//dao执行前，输入行记录数、页码即可
PageHelper.startPage(1,1);
List<User> list = userDao.findAll();
System.out.println(list);

//如果需要分页信息，只需要new一个PageInfo
PageInfo<User> pageInfo = new PageInfo<User>(list);
System.out.println(pageInfo);
```
> 一对多多表查询出现分页问题！改成分步查询即可！只对主表做分页，子表信息用分步查询即可满足！

# 二级缓存
一级缓存是sqlsession级别，默认开启，在同一个sqlsession下，相同参数重复执行是会复用缓存数据的

**开启方式**
需要进行全局开启，然后在对应的dao开启cache
```xml
<!-- mybatis-config.xml -->
<settings>
    <setting name="cacheEnabled" value="true"/>
</settings>

<!-- xxxDao.xml -->
<mapper>
    <cache />
</mapper>
```

