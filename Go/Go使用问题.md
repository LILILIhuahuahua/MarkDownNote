# Go使用问题



## 1.代理设置

终端上输入

```go
go env -w GO111MODULE=on

go env -w GOPROXY=https://goproxy.cn,direct

```





## 2.使用go modlue一键下载依赖

```go
go get -d -v ./...

```



## 3.goland闪退

```
https://blog.csdn.net/qq_42004448/article/details/107727959?utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-6.baidujs&dist_request_id=1330144.22619.16181249093308117&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-6.baidujs
```

重新安装goland的时候，最后一步点击 robot now

