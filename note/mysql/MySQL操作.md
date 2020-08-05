# MySQL操作

*初始账号：root  密码：root*

## 一、登录

### (1)已配置环境变量

直接打开cmd【WIN+R】，输入：`mysql -u root -p`

![1567657054565](https://gitee.com/wolverineL/Study-note/blob/master/images/mysql/1567657054565.png)

### (2)未配置环境变量

使用mysql安装目录中的command打开cmd

![1567657126067](https://gitee.com/wolverineL/Study-note/blob/master/images/mysql/1567657126067.png)

输入密码：root，即可登录

## 二、使用

1、`show databases;`：展示数据库

![1567657277219](https://gitee.com/wolverineL/Study-note/blob/master/images/mysql/1567657277219.png)

2、use 数据库名：操作数据库【`use test`】

![1567657310576](https://gitee.com/wolverineL/Study-note/blob/master/images/mysql/1567657310576.png)

3、`show tables;`:展示所有表

![1567657367234](https://gitee.com/wolverineL/Study-note/blob/master/images/mysql/1567657367234.png)

4、查看配置：`show variables like "%_buffer%";`

![1567657416715](https://gitee.com/wolverineL/Study-note/blob/master/images/mysql/1567657416715.png)

5、设置属性值：`set global innodb_buffer_pool_size=1073741824;`【数值以1M×1024×1024为单位显示】

**6、通过命令行修改参数，但是重启mysql后会失效，可以直接通过修改my.ini【安装盘下隐藏目录C:\ProgramData\MySQL\MySQL Server 5.7】，找到相应属性项，修改其值。并重启mysql服务使其生效。**


[https://gitee.com/wolverineL/Study-note/blob/master/images/mysql/1567657054565.png]: /images/mysql/1567657054565.png