# Tomcat使用

**PPT路径：**

C:\Users\黎先桦\Desktop\CodeStudyingResource\JAVA\JAVAWeb\资料\05-XML & Tomcat\笔记05_尚硅谷_Tomcat_王振国 - 课堂笔记



## 安装与启动

### 1.安装

找到你需要用的 Tomcat 版本对应的 zip 压缩包，解压到需要安装的目录即可。



### 2.启动Tomcat服务器

找到 Tomcat 目录下的 bin 目录下的 startup.bat 文件，双击，就可以启动 Tomcat 服务器。![image-20201219194605455](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219194605455.png)



 打开浏览器，在浏览器地址栏中输入以下地址测试：

```
1、http://localhost:8080 

2、http://127.0.0.1:8080

3、http://真实 ip:8080 
```

当出现如下界面，说明 Tomcat 服务器启动成功！！！

![image-20201219194634815](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219194634815.png)

![image-20201219201159756](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219201159756.png)



**(这个界面是Tomcat的root工程默认显示的)**



## 部暑 web 工程到 Tomcat 中

### 方法一：

#### 1、在Tomcat的webapps 目录下创建一个 book 工程

![image-20201219194815519](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219194815519.png)



#### 2.何访问 Tomcat 下的 web 工程

只需要在浏览器中输入访问地址格式如下： http://ip:port/工程名/目录下/文件名

![image-20201219194852511](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219194852511.png)

![image-20201219194859183](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219194859183.png)





### 方法二：

### 1.找到 Tomcat 下的 conf 目录\Catalina\localhost\ 下,创建如下的配置文件：

![image-20201219195331545](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219195331545.png)





**配置文件的内容**

![image-20201219200555187](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219200555187.png)



```java
//path表示工程的访问路径：/bookProject
//docBase表示工程目录位置 ：D:\Code\Java\JavaWeb\Tomcat\apache-tomcat-8.0.50-windows-x64\apache-tomcat-8.0.50\book

//配置文件的内容
<Context path="/book" docBase="D:\Code\Java\JavaWeb\Tomcat\apache-tomcat-8.0.50-windows-x64\apache-tomcat-8.0.50\book" />
```





## IDEA 整合 Tomcat 服务器

操作的菜单如下：File | Settings | Build, Execution, Deployment | Application Servers

![image-20201219201639924](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219201639924.png)





就可以通过创建一个 Model 查看是不是配置成功！！！

![image-20201219201850581](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219201850581.png)



## IDEA 中如何创建动态 web 工程

### 创建一个新模块

![image-20201219202317671](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219202317671.png)



![image-20201219202325303](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219202325303.png)

![image-20201219202345800](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219202345800.png)





### 动态web工程目录



![image-20201219202601555](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219202601555.png)

### 给动态 web 工程添加额外 jar 包

1、可以打开项目结构菜单操作界面，添加一个自己的类库：

![image-20201219203009296](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219203009296.png)



2、添加你你类库需要的 jar 包文件。

![image-20201219203029819](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219203029819.png)



3、选择你添加的类库，给哪个模块使用：

![image-20201219203042190](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219203042190.png)



### IDEA 中部署工程到 Tomcat 上运行

1.修改Tomcat实例的名字 （便于自己找到）

![image-20201219204223585](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219204223585.png)

![image-20201219204255362](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219204255362.png)

2、确认你的 Tomcat 实例中有你要部署运行的 web 工程模块

![image-20201219204341176](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219204341176.png)



3、你还可以修改你的 Tomcat 实例启动后默认的访问地址：

![image-20201219204359688](C:\Users\黎先桦\Desktop\MarkDown\Java\JavaWeb\Tomcat使用.assets\image-20201219204359688.png)