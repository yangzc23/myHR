# myHR
### 项目介绍
所有功能使用Ajax技术，浏览器发送Ajax请求给后台，后台返回json格式的数据，JS回调函数对返回的JSON数据进行处理。针对浏览器禁用cookies的情况，通过URL地址重写技术将sessionId放到URL地址里面。用户的登录状态以及权限使用session缓存。

后台开发使用的框架：**Spring + Spring MVC + MyBatis**
前端开发使用的框架：**BootStrap + EasyUI**
服务器软件：**tomcat**
数据库软件：**mysql**
开发工具：**eclipse + maven**

主要功能：
**权限管理（已完成）**，**部门管理（已完成）**，员工管理（未完成），考勤管理（未完成），薪资管理（未完成），我的考勤（未完成），我的薪资（未完成）

主要角色：
管理员，人事专员，考勤员，薪资福利专员，普通员工
![image.png](https://upload-images.jianshu.io/upload_images/10027900-54f70edacf150ec2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image.png](https://upload-images.jianshu.io/upload_images/10027900-f1f2443a8896a6a1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
### 部门管理功能实现的思路
1、从github上下载某开源项目的代码
2、执行数据初始化的脚本（建库和建表）
3、创建部门表
4、界面配置部门管理功能的URL地址
5、给管理员角色分配部门管理功能的权限
6、设计部门模板类Dept（可以通过工具自动生成）
7、设计部门分页查询工具类DeptExample（可以通过工具自动生成）
8、设计部门表单类DeptForm
9、设计表单实体转换类DeptConvert
10、开发DeptService接口
11、开发DeptService接口的实现类
12、开发数据库访问接口DeptMapper（能够对部门表进行增删改查）
13、设计部门表映射文件DeptMapper.xml（可以通过工具自动生成）
14、开发控制器DeptController（处理浏览器发送过来的请求）
15、控制器DeptController里面会调用DeptService
16、DeptService里面会调用数据库接口DeptMapper
17、设计部门列表页面的JSP代码
18、设计部门表单页面的JSP代码
19、设计部门管理功能的JS代码（发送Ajax请求和回调函数）
### 配置部门管理功能的URL地址
![image.png](https://upload-images.jianshu.io/upload_images/10027900-e23e7539329eae34.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
### 给系统管理员角色分配部门管理的权限
![image.png](https://upload-images.jianshu.io/upload_images/10027900-75b5fcc8977cd6f5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
