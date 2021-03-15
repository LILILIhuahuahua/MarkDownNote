## Leedcode刷题小技巧

## 值得多刷的题

#### 递归

##### 剑指offter17  打印直到n位最大值

##### 剑指offter12  矩阵中的路径

##### 剑指offter13  机器人的运动范围

##### [剑指 Offer 34. 二叉树中和为某一值的路径](https://leetcode-cn.com/problems/er-cha-shu-zhong-he-wei-mou-yi-zhi-de-lu-jing-lcof/)      （有个小陷阱，关于List是引用类型的问题）

[剑指 Offer 36. 二叉搜索树与双向链表](https://leetcode-cn.com/problems/er-cha-sou-suo-shu-yu-shuang-xiang-lian-biao-lcof/)



#### 动态规划

##### [5630. 删除子数组的最大得分](https://leetcode-cn.com/problems/maximum-erasure-value/)        



#### 二分法

##### [215. 数组中的第K个最大元素](https://leetcode-cn.com/problems/kth-largest-element-in-an-array/)





#### 二分法                                       

##### 剑指offter11  逆转数组中最小的数        

##### [剑指 Offer 40. 最小的k个数](https://leetcode-cn.com/problems/zui-xiao-de-kge-shu-lcof/)            



#### 归并排序                 

##### [剑指 Offer 51. 数组中的逆序对](https://leetcode-cn.com/problems/shu-zu-zhong-de-ni-xu-dui-lcof/)

1. 链表合并题，在head节点造一个**bummy节点**，最后return bummy.next

2. 题目中涉及“找出所有”，“所有中满足什么条件”，可以使用**回溯法**，**回溯法一定要在每次递归返回前把修改过的状态修改回来**

3. 两个数组合并类型题目，注意是否**保证加入元素的唯一性**     （如349题目）

   ```java
   class Solution {
       public int[] intersection(int[] nums1, int[] nums2) {
           Arrays.sort(nums1);
           Arrays.sort(nums2);
           int length1 = nums1.length, length2 = nums2.length;
           int[] intersection = new int[length1 + length2];
           int index = 0, index1 = 0, index2 = 0;
           while (index1 < length1 && index2 < length2) {
               int num1 = nums1[index1], num2 = nums2[index2];
               if (num1 == num2) {
                   // 保证加入元素的唯一性
                   if (index == 0 || num1 != intersection[index - 1]) {
                       intersection[index++] = num1;
                   }
                   index1++;
                   index2++;
               } else if (num1 < num2) {
                   index1++;
               } else {
                   index2++;
               }
           }
           return Arrays.copyOfRange(intersection, 0, index);
       }
   
   ```






- **BFS的模板**，while（判断）+for（当前队列的长度）+其他操作（需要有进队操作）
- int size =queue.size();    不能把求size放在for循环里
- for(int i=0;i<**queue.size();**i++)      **错误！！！！！！！！**

```java

//广度遍历
public int maxDepth(TreeNode root) {
    if(root==null){
        return 0;
    }

    //BFS
    Queue<TreeNode> queue = new LinkedList<TreeNode>();
    queue.add(root);
    int deep = 0;

    while(!queue.isEmpty()){
        //深度加1
        deep++;
        int size =queue.size();  
        for(int i=0;i<size;i++){
            TreeNode temp = queue.poll();
            //左右子节点入队
            if(temp.left!=null) queue.add(temp.left);
            if(temp.right!=null) queue.add(temp.right);
        }

}
```

​	**DFS的模板**

- 让DFS带返回值
- 对DFS递归不做限制，进入DFS  先截枝（return0），再处理
- 截枝时，注意加上visited条件，避免死循环

```java
//剑指OFFTER 13
class Solution {
    boolean arr[][];
    public int movingCount(int m, int n, int k) {
        //arr用于最后计算总格子数
        arr = new boolean[m][n];

        //DFS
        return DFS(0,0,m,n,k);
        

        
    }

    //深度优先算法
    public int DFS(int cowNow,int rowNow,int cow,int row,int k){
        //arr[cowNow][rowNow]==true这个条件避免重复访问，不可缺少
        // 先截枝（return0）
        if(cowNow>=cow||cowNow<0||rowNow<0||rowNow>=row||
           sumPerNum(cowNow)+sumPerNum(rowNow)>k||arr[cowNow][rowNow]==true){
            return 0;
        }
        //再处理
        arr[cowNow][rowNow] = true;
        return 1+DFS(cowNow,rowNow+1,cow,row,k)+DFS(cowNow+1,rowNow,cow,row,k);
    
    }
    public int sumPerNum(int num){
        int sum = 0;
        while(num!=0){
            sum+=num%10;
            num/=10;
        }
        return sum;
    }
}
```

- **java中栈直接用Stack数据类型，队用LinkedList数据类型**
- **模板：1.所有节点都可入队    2.先排除不合理节点   3.合理节点进行结果计算**
- **DFS与BFS尽量使用有返回值的**
- **DFS算法`return 1+所有下一节点DFS`    结果是DFS遍历的所有节点数量**    （剑指OFFTER 13题）



6.，无向图求最短路径用BFS，**广搜只要搜到了终点，那么一定是最短的路径 ** **（127.单词接龙）**



8.Java中**String**的两个方法：

  **replace**与**replaceAll**      返回的string才是replace的结果  **（不会对源对象进行改变）**



![image-20201114131309577](C:\Users\黎先桦\Desktop\MarkDown\Leedcode刷题小技巧.assets\image-20201114131309577.png)

```
 //多个空格变成一个   空格+ 表示匹配多个空格
 String str = s.replaceAll(" +"," ");
 
 //去除str前后多个空格
 String str = s.trim();
```



9.

二分查找总结

https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/solution/yi-wen-dai-ni-gao-ding-er-fen-cha-zhao-j-ymwl/

![å¾®ä¿¡å¾ç_20201226200821](https://pic.leetcode-cn.com/1608987585-wTlCwv-file_1608987585742)





- **普通二分查找  while条件有等号，最后一个else 无if** ， left，right取值需要看mid能不能被排除在目标范围中

- **特殊二分查找 while条件无等号**，退出循环时，**left==right，需要对left再次验证**（重要！！！！！！！）

- **while条件无等号的情况下，left与right一定不会越界**，退出循环时，left一定==right

- ​	如果二分查找时，需要**一直向右走**，需要 **取mid时向上取整**  **mid =（left+right+1）/2**

   **(剑指offter11题与33题目)   **  **（剑指offter34）**





10.**对输入的数为int类型时，要考虑int的上界与下界的特殊情况 **  （Integer.MIN与Integer.MAX）

​	 处理： 一般将int类型赋值给long类型，在对long类型的新变量进行操作    **剑指offter16**



11.题目中的 **x/=2** 操作 ，该改为  **x>>=1**, **位运算的效率比乘除法效率高的多**





12.double类型判断相等  **不能用==**

- **方法1**

![image-20201213125823419](C:\Users\黎先桦\Desktop\MarkDown\Leedcode刷题小技巧.assets\image-20201213125823419.png)

- **方法2**

![image-20201213125908872](C:\Users\黎先桦\Desktop\MarkDown\Leedcode刷题小技巧.assets\image-20201213125908872.png)



14.

**ArrayList**、**LinkedList**有序的，可以通过git（i）获取第i个插入的数

**（当不知道输出的长度时，先用ArrayList或LinkedList存下，最后在根据ArrayList.size（）获得输出的长度） **

[剑指 Offer 32 - I. 从上到下打印二叉树](https://leetcode-cn.com/problems/cong-shang-dao-xia-da-yin-er-cha-shu-lcof/)

**ArrayList的使用**

```java
ArrayList<Integer> ans = new ArrayList<>();  //初始化
ans.add();  //在尾部添加元素 == ans.addLast()//在尾部添加元素
ans.addLast()//在尾部添加元素
    
ans.addFirst() //在头部添加元素 （头插法)

ans.removeLast();
ans.remove();
```



**java中栈直接用Stack数据类型，队用LinkedList数据类型**           **（127.单词接龙）**

**队的使用**

```java
//队
Queue<TreeNode> queue = new LinkedList();
queue.add();  //进队
queue.poll(); //出队
```



**JAVA中PriorityQueue**+**比较器**能实现堆的效果  

```java
//PriotityQueue默认小根堆
PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>();
//PriotityQueue实现大根堆
PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>((a, b) -> b - a);

//Integer不可以少
//PriorityQueue<Integer> pq = new PriorityQueue((a, b) -> b - a); 错误，

//add()自动调整，poll()自动取出根 （最大或最小值）
```

 **poll是移除head，peek获得但不移除head**

add与offer基本相同，add会有异常，offer只返回boolean



15.

string要删除某些元素时，用不了replace（replace第二个参数不能是“”）

可以通过将string依次复制到新string，排除这些元素  

> （string有append方法）

```java
//删除number中的‘ ’与‘—’
Stirng number;
StringBuilder str = new StringBuilder();
for(int i=0;i<number.length();i++){
    if(number.charAt(i)!=' '&&number.charAt(i)!='-'){
        str.append(number.charAt(i));  //append方法
    }
}
```





16.

树的路径和问题，尽量将**一次路径的结果**的判断放在叶子节点。而尽量不要在空节点上判断一次路径的结果

[剑指 Offer 34. 二叉树中和为某一值的路径](https://leetcode-cn.com/problems/er-cha-shu-zhong-he-wei-mou-yi-zhi-de-lu-jing-lcof/)

```java
public void DFS(TreeNode root, int sum, int curSum){
        if(root==null){
            return ;
        }
        curSum +=root.val;
        curList.add(root.val);
    
    	//尽量将一次路径的结果的判断放在叶子节点。而尽量不要在空节点上判断一次路径的结果
        if(sum==curSum&&root.left==null&&root.right==null){
            res.add(new LinkedList(curList));
        }

        DFS(root.left,sum,curSum);
        DFS(root.right,sum,curSum);
        curSum-=root.val;
        curList.removeLast();

    }

```







17.

List类型的拷贝是**浅拷贝**

[剑指 Offer 34. 二叉树中和为某一值的路径](https://leetcode-cn.com/problems/er-cha-shu-zhong-he-wei-mou-yi-zhi-de-lu-jing-lcof/)   

```java
List<List<Integer>> res = new LinkedList();

LinkedList<Integer> curList = new LinkedList(); 

//如果多次执行
res.add(curList);  

//只要有一次curList.clear
//res中全为空，List类型的拷贝是浅拷贝


res.add(curList) //有风险
res.add(new LinkedList(curList));  //无风险




```





18.

**在对树的遍历的时候，可以用一个pre节点记录遍历的前后继关系！！**     （查找二叉搜索树经常用到）

[剑指 Offer 36. 二叉搜索树与双向链表](https://leetcode-cn.com/problems/er-cha-sou-suo-shu-yu-shuang-xiang-lian-biao-lcof/)



19.

- String类的substring都是小写
- str转int，需要用Integer的方法 

> Integer.parseInt(strs[0])

- Sring的比较一定要用equals方法

```java
//错误
if(str=="abc"){
    
}

//正确
if(str.equals("abc")){
    
}
```



19.

工具类的常用函数

```java
//Arrays
//拷贝int数组arr到其他数组
Arrays.copyOfRange(arr,0,k);

//排序
Arrays.sort();

//int[]的排序
int[][2] intervals;
//按int[i][0]升序，按int[i][1]降序
Arrays.sort(intervals, (v1, v2) -> v1[0] == v2[0] ? v2[1] - v1[1] : v1[0] - v2[0]);
//按int[i][0]升序，int[i][1]原序
Arrays.sort(intervals, (v1, v2) -> v1[0] - v2[0] );


//初始化数组
int[] dp = new int[size];
Arrays.fill(dp, 1);

//List初始化时添加多个元素
List<Integer> list = new ArrayList<>(Arrays.asList(nums[i],nums[left],nums[right]));
```



20.

快排模板

- 外层while不带等号
- 内层while带等号  **(否则对 [1,1]排序就会死循环)**

```java
//快排模板
public int Partition(int[] arr,int start,int end){  
    int temp = arr[start];
    while(start<end){
        //大于等于
        while(start<end&&arr[end]>=temp) end--;
        arr[start] = arr[end];
        //小于等于
        while(start<end&&arr[start]<=temp) start++;
        arr[end]=arr[start];
    }
    arr[start] = temp;
    return start;
}

public void QuickSqrt(int[] arr,int start,int end){
    if(start<end){
        int pos = Partition(arr,0,arr.length-1);
        //递归
        Partition(arr,0,pos-1);
        Partition(arr,pos+1,arr.length-1);
    }
}
```





21.

**归并排序模板**

- **mid = left+(right-left)/2  避免溺出**

```java
class Solution {
    public int reversePairs(int[] nums) {
        int len = nums.length;

        if (len < 2) {
            return 0;
        }

        //为了不改变原数组，用copy充当nums数组
        int[] copy = new int[len];
        for (int i = 0; i < len; i++) {
            copy[i] = nums[i];
        }
        int[] temp = new int[len];
        return MergeSort(copy, 0, len - 1, temp);



    }
    //归并排序
    //拆分出有序段
    public int MergeSort(int[] nums,int left,int right,int[] temp){
        if(left<right){
            int mid = left + (right-left)/2;
            //继续拆分段
            int leftCount = MergeSort(nums,left,mid,temp);
            int rightCount = MergeSort(nums,mid+1,right,temp);

//            //合并两端  （当成一个后续遍历，左右都有序，在合并）
           if(nums[mid]<=nums[mid+1]){
               return leftCount+rightCount;
           }
            int mergeCount = merge(nums,left,right,temp);

            return leftCount+rightCount+mergeCount;
        }
        return 0;
    }

    //合并两个有序段
    public int merge(int[] nums,int left,int right,int[] temp){
        int i=left,mid = left+(right-left)/2,j=mid+1;
        int k;
        int count = 0;


        //拷贝一下nums
        for( k = left;k<=right;k++){
            temp[k] = nums[k];
        }
        //开始归并
        for(k =left;k<=right&&i<=mid&&j<=right;k++){
            //左右段中相同的值，优先归并左段
            if(temp[i]<=
            temp[j]){
                nums[k]=temp[i++];
            }else{
                //左大，右小，形成逆序对
                //temp[j]小于temp[i]到temp[mid]所有值
                count +=(mid-i+1);
                nums[k]=temp[j++];
            }
        }
        while(i<=mid) nums[k++] = temp[i++];
        while(j<=right) nums[k++] = temp[j++];

        return count;
    }
}
```





22.

**本题有个细节：当n=- Integer.MIN时， 对n = -n 操作会出现异常** 

剑指offter 16



23.

**list转化成int[][]**

```java
List<int[]> res = new ArrayList<>();
res.toArray(new int[res.size()][]);
```

剑指offter 57-2





24.

java 遍历HashMap

**[49. 字母异位词分组](https://leetcode-cn.com/problems/group-anagrams/)**

-  Set keySet()：返回所有key构成的Set集合


-  Collection values()：返回所有value构成的Collection集合


-  Set entrySet()：返回所有key-value对构成的Set集合

  ```java
  for(List<String> list : hashMap.values()){
      res.add(list);
  }
  ```

  

![image-20210122132923610](C:\Users\黎先桦\Desktop\MarkDown\Leedcode刷题小技巧.assets\image-20210122132923610.png)