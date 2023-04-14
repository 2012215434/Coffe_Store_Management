  因为电脑不支持安装npm，系统为 OSX 10.xxx，无法使用Element-UI， 所以完成部分不包括前端部分。其次目前主流的设计，都是以
  前后端分离的模式进行设计的而非MVC方式，好处在于可以将前端部分单独部署，也可以将
  这些静态页面部署到CDN，为用户提高更好的体验。

使用的技术：Spring boot + Spring Data JPA + MySQL + Spring Security

1. 通过使用 Spring Data JPA 来完成持久化操作，（比MyBatis 更快，更便捷）
2. 通过 MySQL 实现数据存储，因为MySQL开源免费，易安装维护。
3. 通过 Spring Security 实现 Authentication，无需自己开发登陆模块，只需实现对应的接口，
通过配置即可应用至对应的后端接口，可以很好的控制哪些接口需要Authentication，哪些接口是公开的


需求列表，我都以后端接口形式完成，所有功能均可以通过postman请求实现
#  **开发门户网站**
这块功能我的设计是全部放到前端，因为这块功能无需权限校验，也跟后端没有关联，新开门店
直接通过修改前端代码加入进来

#  **用户管理模块**
对于员工：【所有操作需要是员工登陆之后才可以】
1.	所有分店会员数据的导入
POST ／employee/users

2.  员工能够看到客户列表和个人详情
必须使用员工账号登陆才能看到
GET /employee/users
```$xslt
GET /employee/users HTTP/1.1
Host: localhost:8080
Authorization: Basic emhhbmcuc2hlbmcuMUBxcS5jb206ODg4ODg4
Cache-Control: no-cache
```
GET /employee/users/{userId}
员工登陆，可以查看任意用户的详细信息
```$xslt
GET /employee/users/6 HTTP/1.1
Host: localhost:8080
Authorization: Basic emhhbmcuc2hlbmcuMUBxcS5jb206ODg4ODg4
Cache-Control: no-cache
```

3.  能够实现发布店铺公开信息或者发布仅会员可见的通知信息
POST  /employee/news 只有employee才有权限操作
```$xslt
POST /employee/news HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Authorization: Basic emhhbmcuc2hlbmcxQHFxLmNvbTo4ODg4ODg=
Cache-Control: no-cache

{ "type":"members", "body" : "add2ed2" }
```
return
```$xslt
{
    "success": true,
    "message": "post news success"
}
```


GET   /member/news   employee和会员都有权限操作
```$xslt
GET /member/news HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Authorization: Basic emhhbmcuc2hlbmdAcXEuY29tOjg4ODg4OA==
Cache-Control: no-cache
```

GET   /member/news/{newsId}   employee和会员都有权限操作

```$xslt
GET /member/news/1 HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Authorization: Basic emhhbmcuc2hlbmdAcXEuY29tOjg4ODg4OA==
Cache-Control: no-cache
```


GET   /public/news   所有人都可以操作，无需登录

```$xslt
GET /public/news HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Cache-Control: no-cache
```

对于用户：
1. 【无需验证 但是后续需要验证用户提供的邮箱，防止恶意注册，每个邮箱仅限注册一次】新用户注册功能，提供姓名，手机，邮箱，地址 
POST /public/users
```POST /public/users HTTP/1.1
 Host: localhost:8080
 Content-Type: application/json
 Cache-Control: no-cache
 
 { "firstName": "Sheng1", "surName":"Zhang2", "email":"zhang.sheng@qq.com", "password":"888888", "phone" : "123456", "userType": 0 }
 ```
return 
```
{
    "success": false,
    "message": "email already registered"
}
```
2. 【需要用户登陆之后才能展示】会员信息展示功能：提供姓名，手机，邮箱，地址，注册日期，消费总额，积分总额
GET /members/users/{userId}
```
GET /member/users/6 HTTP/1.1
Host: localhost:8080
Authorization: Basic emhhbmcuc2hlbmdAcXEuY29tOjg4ODg4OA==
Cache-Control: no-cache

```
return 
```$xslt
{
    "success": true,
    "message": {
        "id": 6,
        "firstName": "Sheng",
        "surName": "Zhang",
        "phone": null,
        "email": "zhang.sheng@qq.com",
        "dateJoined": null,
        "spendToDate": null,
        "accountBalance": null,
        "address": null,
        "userType": 0,
        "latitude": null,
        "longitude": null,
        "easting": null,
        "northing": null,
        "password": "$2a$10$ks16OKb2n1NKENhc51A0bOSUg8SvTTZo1kCmOXsQsBcHK8z2NzT2G"
    }
}
```

3. 【需要用户登陆之后才能修改】信息修改功能，会员能够修改姓名，手机，邮箱，地址
PUT /members/users/{userId}

```
PUT /member/users/6 HTTP/1.1
Host: localhost:8080
Authorization: Basic emhhbmcuc2hlbmdAcXEuY29tOjg4ODg4OA==
Content-Type: application/json
Cache-Control: no-cache

{ "firstName": "Sheng1", "surName":"Zhang2", "email":"zhang.sheng@qq.com", "password":"888888", "phone" : "123456", "userType": 0, "accountBalance": 10 }
```
return
```$xslt

    {
        "success": true,
        "message": {
            "id": 6,
            "firstName": "Sheng1",
            "surName": "Zhang2",
            "phone": null,
            "email": "zhang.sheng@qq.com",
            "dateJoined": null,
            "spendToDate": null,
            "accountBalance": null,
            "address": null,
            "userType": 0,
            "latitude": null,
            "longitude": null,
            "easting": null,
            "northing": null,
            "password": "$2a$10$ks16OKb2n1NKENhc51A0bOSUg8SvTTZo1kCmOXsQsBcHK8z2NzT2G"
        }
    }

```

4. 【需要用户登陆之后才能删除】用户删除功能，会员注销账户后所有相关的信息得到删除
DELETE /members/users/{userId}

```$xslt
DELETE /member/users/8 HTTP/1.1
Host: localhost:8080
Authorization: Basic emhhbmcuc2hlbmcuMUBxcS5jb206ODg4ODg4
Cache-Control: no-cache
```

非功能性设计：
1.  性能设计：
 如果会员数量超过百万，意味着单表查询速度变慢，需要考虑使用分库分表，例如使用 TDDL，ShadingSphere等中间件来实现
 如果系统访问流量很高，可以做横向扩容。如：Load balancer + instances，Load balancer可以使用 HA proxy 或者 Nginx 等，
 配合注册中心 Consul 来避免 Load Balancer单点故障
 
 2. 防止恶意攻击，可以在应用层加上一层网关，做限流 + 防止DOS攻击等
 
 3. 上线前需要将 spring security csrf() enable来保证系统安全。
 
 4. 其他：前端数据校验，后端数据校验，数据默认值等
 
 5. 导入功能直接用DMBS工具倒入就行，使用MySQL提供的LOAD DATA INFILE命令导入CSV文件


