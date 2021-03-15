#  Git使用

## 1.Git的使用

- github中创建一个项目

- ![image-20201219110429860](C:\Users\lenovo\Desktop\MarkDown\Git的使用\Git使用.assets\image-20201219110429860.png)

- 在本地目标文件夹下，使用gitBash依次输入命令

  ```java
  echo "# GitTest" >> README.md
  git init
  git add README.md
  git commit -m "first commit"
  git branch -M main  //远端比较master分支，叫main分支了
  git remote add origin git@github.com:LILILIhuahuahua/GitTest.git
  git push -u origin main
  ```

  











## 2.Git结构

![image-20210313154133813](C:\Users\lenovo\Desktop\MarkDown\Git的使用\image-20210313154133813.png)

最重要的就是 

git add 文件名

git commit -m “提交注释”  文件名





## 1.初始化本地库

- 在项目文件夹打开GitBash， 使用命令git init
- 

## 2.设置签名

作用：用于区分不同开发人员的身份

命令：

1. **仓库级别**

![image-20201107104628144](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201107104628144.png)

![image-20201107104919916](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201107104919916.png)



2**.系统级别**

![image-20201107104713598](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201107104713598.png)

![image-20201107105013099](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201107105013099.png)

（签名两个级别必须有一个，两者都没有是不被允许的）



## 3.基本操作

#### 状态查看

​	![image-20201107111815925](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201107111815925.png)



#### 添加

**1.git add 文件名**

​	**文件放入缓存区**

![image-20201107111849360](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201107111849360.png)

![image-20201107112538870](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201107112538870.png)

（git会转换文件的结尾，在上传到暂存区）



**2.git  rm --cached 文件名**

​	将文件移除缓冲区 **（本地文件不会被删除）**



#### 提交

![image-20201107112429811](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201107112429811.png)



![image-20201107122524350](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201107122524350.png)

#### 查看历史记录

1. git log    （HEAD指针指向当前版本）

   ![image-20210313154209942](C:\Users\lenovo\Desktop\MarkDown\Git的使用\image-20210313154209942.png)

   

   2.git log  --oneline 一行显示一个历史记录

   ![image-20210313154220020](C:\Users\lenovo\Desktop\MarkDown\Git的使用\image-20210313154220020.png)

   

   3.git reflog 显示HEAD指针移动到该版本需要多少步、

   ![image-20210313154229076](C:\Users\lenovo\Desktop\MarkDown\Git的使用\image-20210313154229076.png)



#### 版本前进后退

##### 基于索引   --hard

1.移动HEAD指针，实现版本的前进后退 

![image-20210313154246960](C:\Users\lenovo\Desktop\MarkDown\Git的使用\image-20210313154246960.png)

2.如不想查找索引值，可以简单回退一步或多步

![image-20210313154341994](C:\Users\lenovo\Desktop\MarkDown\Git的使用\image-20210313154341994.png)



##### reset命令的三个参数对比

![image-20210313154318628](C:\Users\lenovo\Desktop\MarkDown\Git的使用\image-20210313154318628.png)



### 删除文件的找回

前提：删除前，文件提交到本地库

![image-20201107130737747](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201107130737747.png)

​                        git reset --hard 索引值

​						git reset --hard HEAD  （当删除操作没有提交到本地库时）



### 比较文件差异

git diff [文件名]  

**将工作区文件与暂存区比较**

**红色表删除的行，绿色表添加的行**

![image-20201107131423119](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201107131423119.png)



git diff 【本地库历史版本】[文件名]  

**将工作区文件与本地库历史记录比较**

例： **git diff HEAD^ apple.txt**  (与本地库上一个版本的aplle.txt文件比较)



### 分支管理

#### **1.查看分支**

  git branch -v

![image-20201107194737566](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201107194737566.png)



#### **2.创建分支**

 git branch 【分支名】



#### **3.切换分支**

 git checkout【分支名】



#### **4.合并分支**

 **切换到需要合并的分支**

 **在git merge 新分支名**

![image-20201107194713991](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201107194713991.png)

![image-20201107194720095](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201107194720095.png)





### Git文件管理机制

Tree对象保存每个文件的Hash值，commit对象记录Tree对象以及提交的作者等其他信息

![image-20201109160606343](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201109160606343.png)



### Git分析管理机制

![image-20201109160758195](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201109160758195.png)

![image-20201109160811624](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201109160811624.png)



![image-20201109160910634](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201109160910634.png)



## 本地库与远程库的交互

### 1.创建远程库的地址别名

![image-20201109162414361](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201109162414361.png)

当前远程库的别名：origin



### 2.推送

![image-20201109163004276](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201109163004276.png)

![image-20201109165834100](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201109165834100.png)





### 3.拉取

**命令: git fetch 远程库别名 分支名**

![image-20201109170350859](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201109170350859.png)

**pull = fetch +merge** 

是指当我们pull时，先拉取最新的仓库状态，检查后，合并入自己的分支，在进行pull **（减少冲突）**



#### 1.执行fetch拉取操作，不会马上改本地的数据信息

  需要切换到远程库的对应分支查看

**例**：git checkout origin/master  **(切换到拉取到本地的origin库中的master分支)**



#### 2.检查拉取分支没有问题，将本地的分支与拉取的分支合并

**例**：git merge origin/master 



### SHH登陆

设置好之后，push的时候不用每次都去输入账号与密码

![image-20201110124356337](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201110124356337.png)

![image-20201110124408411](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201110124408411.png)