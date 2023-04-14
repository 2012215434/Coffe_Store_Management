
**技术栈**

* 后端： SpringBoot2.x + Mybatis
* 前端： Vue.JS2.x + ElementUI

**启动说明**

* 启动前，请配置好`application.yml`中的数据库链接。

* 启动前，请创建数据库，建表SQL语句放在`db`文件夹下，其他相关表请自行创建。

* 配置完成后，运行位于 `src/main/com/coffee/`下的`SpringbootApplication`中的`main`方法，访问 `http://localhost:8080/` 进行测试。

**项目设计**

```
.
├── README
├── README.md
├── db
├── mvnw
├── mvnw.cmd
├── pom.xml
├── spring-boot.iml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── coffee
│   │   │           ├── SpringbootApplication.java  -- Spring Boot启动器类
│   │   │           ├── controller  -- MVC-WEB层
│   │   │           ├── entity  -- 实体类
│   │   │           ├── interceptor  -- 自定义拦截器
│   │   │           ├── mapper  -- mybatis-Mapper层映射接口，或称为DAO层
│   │   │           └── service  -- service业务层
│   │   └── resources  -- Spring Boot资源文件目录
│   │       ├── application.yml  -- Spring Boot核心配置文件
│   │       ├── mapper  -- Mybatis Mapper层XML配置文件
│   │       ├── static  -- 前端静态文件（主要是JS、CSS、Image文件，一般不放HTML页面）
│   │       │   ├── css
│   │       │   ├── image
│   │       │   ├── js
│   │       │   ├── lib
│   │       └── templates  -- Thymeleaf模板引擎识别的HTML页面目录，存放HTML页面（相当于之前的WEB—INF目录，即不能通过浏览器直接访问）
│   └── test
```

### 实现查询

> 1.在`src/main/java/com/coffee/entity/`下新建`User.java`实体类

```java
public class User implements Serializable {
    private Long id; //编号
    private String username; //用户名
    private String password; //密码
    //getter/setter
}
```

> 2.在`src/main/java/com/coffee/service/`下创建`BaseService.java`通用接口，目的是简化service层接口基本CRUD方法的编写。

```java
public interface BaseService<T> {

    // 查询所有
    List<T> findAll();

    //根据ID查询
    List<T> findById(Long id);

    //添加
    void create(T t);

    //删除（批量）
    void delete(Long... ids);

    //修改
    void update(T t);
}
```

Service层基本CRUD接口的简易封装，使用了泛型类，其继承接口指定了什么泛型，T就代表什么类。

> 3.在`src/main/java/com/coffee/service/`下创建`UserService.java`接口：

```java
public interface UserService extends BaseService<User> {}
```

> 4.在`src/main/java/com/coffee/service/impl/`下创建`UserServiceImpl.java`实现类：

```java
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
  
    //其他方法省略
}
```

> 5.在`src/main/java/com/coffee/mapper/`下创建`UserMapper.java`Mapper接口类：

```java
@Mapper
public interface UserMapper {
    List<User> findAll();
}
```

使用`@Mapper`接口标识这个接口，不然Mybatis找不到其对应的XML映射文件。

> 6.在`src/main/resources/mapper/`下创建`UserMapper.xml`映射文件：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.coffee.mapper.UserMapper">

    <!-- 查询所有 -->
    <select id="findAll" resultType="com.coffee.entity.User">
        SELECT * FROM user
    </select>
</mapper>
```

> 7.在`src/main/java/com/coffee/controller/admin/`下创建`UserController.java`

```java
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    
    @RequestMapping("/findAll")
    public List<User> findAll() {
        return userService.findAll();
    }
}
```

> 8.运行`src/main/java/com/coffee/SpringbootApplication.java`的main方法，启动springboot

在浏览器上访问`localhost:8080/findAll`即可得到一串JSON数据。
