# 虚拟机使用

## 使用问题

### 1.Xhell无法连接centos7

```
cd /etc/sysconfig/network-scripts
sudo vim ifcfg-ens33 
```

![image-20210317143805698](C:\Users\lenovo\Desktop\MarkDown\Linux\Linux虚拟机使用.assets\image-20210317143805698.png)

```shell
[root@localhost ~]# systemctl restart sshd
[root@localhost ~]# systemctl restart network
[root@localhost ~]# nmcli device connect ens33
```





重新Xshell连接

![image-20210317144021256](C:\Users\lenovo\Desktop\MarkDown\Linux\Linux虚拟机使用.assets\image-20210317144021256.png)