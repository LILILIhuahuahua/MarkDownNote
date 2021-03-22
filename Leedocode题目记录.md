

# Leedocode题目记录

## 简单题

#### [349. 两个数组的交集](https://leetcode-cn.com/problems/intersection-of-two-arrays/)

给定两个数组，编写一个函数来计算它们的交集。

##### 解法1：使用HashSet

```java
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        //特殊情况
        if(nums1==null||nums2==null) return null;
        HashSet<Integer> hashSet = new HashSet();
        HashSet<Integer> result = new HashSet();

        for(int i =0;i<nums1.length;i++){
            hashSet.add(nums1[i]);
        }
        //交集查询
        for(int i =0;i<nums2.length;i++){
            if(hashSet.contains(nums2[i])){
                result.add(nums2[i]);
            }
        }

        int[] res = new int[result.size()];
        int count =0;
        //遍历器
        Iterator iterator = result.iterator();
        while(iterator.hasNext()){
            res[count++] = (int)iterator.next();
        }

        return res;

    }

}
```

##### 解法2：排序+双指针

- 双数组双指针，选择元素，很经典
- 保证加入元素的唯一性   **（《三数之和》中也有用到，用来去重）**
-  **当res数组定义时，长度比较大，返回结果是一定要返回res中非空的部分，否则会出错**

```java
	class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        //Arrays工具类
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int length1 = nums1.length, length2 = nums2.length;
        //结果不能直接返回interscction，因为interscction有空元素
        int[] intersection = new int[length1 + length2];
        
        //双数组双指针题*     
        int index = 0, index1 = 0, index2 = 0;
        //两个数组都不能越界
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
}
```



## 链表操作类型题目

#### [206. 反转链表](https://leetcode-cn.com/problems/reverse-linked-list/)

难度简单1543

反转一个单链表。

**示例:**

```
输入: 1->2->3->4->5->NULL
输出: 5->4->3->2->1->NULL
```

**进阶:**
你可以迭代或递归地反转链表。你能否用两种方法解决这道题？



##### 解答1：递归法

```java
class Solution {
    //递归法
    public ListNode reverseList(ListNode head) {
        //1.边界
        if(head==null||head.next==null){
            return head;
        }
        //2.将head后面的链表逆转，node为逆转后的头节点
        ListNode node = reverseList(head.next);
        //3.逆转head自己
        head.next.next = head; // 1--》2 变成  1 《-- 2
        head.next = null;  //head为逆转后的最后一个节点，head.next需要置为null
        //node为逆转后的头节点
        return node;
    }
}
```



##### 解法2：迭代法

- **返回的是pre节点**

```java
 //迭代法
public ListNode reverseList(ListNode head){
    ListNode pre = null;
    ListNode cur = head;
    while(cur!=null){
        ListNode temp = cur.next;
        cur.next = pre;
        pre = cur;
        cur = temp;
    }
    //返回的是pre节点
    return pre;
}
```



#### [19. 删除链表的倒数第N个节点](https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/)

![](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201025155923051.png)



##### 解法1：

**（解法缺点：需要对删除的节点就是头节点，即p为null的情况单独考虑）**

- m指针从head节点开始，先走n-1个位置

- q节点与m节点一起移动   （当m在最后一个节点时，q在倒数第n个节点处）

- p节点一直记录着q的前驱 （方便删除q节点）

  

```java
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        //双指针
        if(head==null) return head;
        ListNode p=null,q=head,m=head;

        //m指针先走n-1步
        for(int i =0;i<n-1;i++){
            m=m.next;
        }
        //双指针一起移动
        while(m.next!=null){
            m=m.next;
            p=q; //记录前驱节点
            q = q.next;
        }
        //删除节点
        if(p!=null){
            p.next = q.next;
        }else{
            return q.next; //p = null ,那q在头节点，需要删除的节点也在头节点
        }
        return head;  
       
    }
}
```



##### **解法2：**

**（不用单独考虑删除头节点的情况，但是需要额外创建新节点）**

- 在head头节点前，创建一个dummy节点，front节点从dummy开始移动
- last节点先走n个位置
- front，last一起移动，当last为null，front在倒数第n的节点的前驱节点处
- 返回dummy.next

```java
public ListNode removeNthFromEnd(ListNode head, int n) {
        //双指针
       ListNode dummy = new ListNode (0,head); //head节点前加一个节点
       ListNode front=dummy,last = head; //前后双指针,frone为倒数第n节点的前驱
       for(int i=0;i<n;i++){
           last=last.next; //n题目说一定有效，否则需要判断last为null
       }
       while(last!=null){
           last= last.next;
           front=front.next;
       }
       front.next = front.next.next;
       return dummy.next;
        
       
    }
```





#### [剑指 Offer 06. 从尾到头打印链表](https://leetcode-cn.com/problems/cong-wei-dao-tou-da-yin-lian-biao-lcof/)

输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。

**示例 1：**

```
输入：head = [1,3,2]
输出：[2,3,1]
```

##### 解法1：使用栈

```java
class Solution {
    public int[] reversePrint(ListNode head) {
        //解放1:使用栈
                if(head==null) return new int[]{};
        ListNode p = head;
        Stack<Integer> stack = new Stack();

        while(p!=null){
            stack.push(p.val);
            p = p.next;
            len++;
        }

        int[] res = new int[stack.size()];
        int i=0;
        while(!stack.isEmpty()){
            res[i++] = (int)stack.pop();
        }

        return res;
    }
}
```

##### 解法2：使用递归

- 学习res先声明，最后创建的方式

```java
class Solution {
    int index = 0;
    int[] res; //先声明数组，长度还没确定
    public int[] reversePrint(ListNode head) {
        //解放2:使用递归
        recurrsion(head,0);

        return res;

    }

    public void recurrsion(ListNode head,int len){
        if(head==null){
            //知道长度len后，创建res
            res = new int[len]; 
        }else{
            recurrsion(head.next,len+1);
            res[index++] = head.val;
        }
    }
}
```



#### [142. 环形链表 II](https://leetcode-cn.com/problems/linked-list-cycle-ii/)

难度中等840

给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 `null`。

为了表示给定链表中的环，我们使用整数 `pos` 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 `pos` 是 `-1`，则在该链表中没有环。**注意，`pos` 仅仅是用于标识环的情况，并不会作为参数传递到函数中。**

**说明：**不允许修改给定的链表。

**进阶：**

- 你是否可以使用 `O(1)` 空间解决此题？

 

```java
public class Solution {
    public ListNode detectCycle(ListNode head) {
        if(head==null)return null;

        ListNode fast=head,slow=head,temp=head;
        //让快慢指针相遇
        while(fast!=null){
            slow = slow.next;
            fast = fast.next;
            if(fast!=null){
                fast = fast.next;
            }else{
                return null; //fast走到null了一定无环
            }
            //判断相遇
            if(slow==fast){
                //相遇了，temp从head开始一起走，相等的位置就是环的入口点
                while(slow!=temp){
                    slow=slow.next;
                    temp=temp.next;
                }
                return temp;
            }
        }

        return null;
    }
}
```

**示例 1：**

![img](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/12/07/circularlinkedlist.png)

```
输入：head = [3,2,0,-4], pos = 1
输出：返回索引为 1 的链表节点
解释：链表中有一个环，其尾部连接到第二个节点。
```

**解法1：**数学+算法

- 相遇时，快指针走的距离是慢指针的**2**倍，且快指针比慢指针多走**n个b**（环的长度）
- 走到入环点一定走了 **a+nb**步

![image-20210203181730938](C:%5CUsers%5C%E9%BB%8E%E5%85%88%E6%A1%A6%5CDesktop%5CMarkDown%5CLeedocode%E9%A2%98%E7%9B%AE%E8%AE%B0%E5%BD%95.assets%5Cimage-20210203181730938.png)









#### [148. 排序链表](https://leetcode-cn.com/problems/sort-list/)

难度中等980

给你链表的头结点 `head` ，请将其按 **升序** 排列并返回 **排序后的链表** 。

**进阶：**

- 你可以在 `O(n log n)` 时间复杂度和常数级空间复杂度下，对链表进行排序吗？



**解法1：链表的归并排序**

- 通过head找到mid,从mid断开 

- 对head与mid.next排序后，在merge成有序链表

- 注意：通过head找到mid时，mid需要靠头节点的mid。否则两个节点时，找mid会死循环

  **<u>（findMidNode函数中，看判断head为非单节点，让fast先走两步）</u>**

```java
lass Solution {
    //归并排序
    //通过head找到mid,从mid断开   对head与mid.next排序后，在merge成有序链表
    public ListNode sortList(ListNode head) {
        // 1、递归结束条件 空节点或单节点
        if (head == null || head.next == null) {
            return head;
        }

        // 2、找到链表中间节点并断开链表 & 递归下探
        ListNode midNode = middleNode(head);
        ListNode rightHead = midNode.next;
        midNode.next = null;

        //递归分割
        ListNode left = sortList(head);
        ListNode right = sortList(rightHead);

        // 3、当前层业务操作（合并有序链表）
        return mergeTwoLists(left, right);
    }

    public ListNode middleNode(ListNode head){
        //通过快慢指针找到head为头的链表的中间节点
        //空节点或点节点
        if (head == null || head.next == null) {
            return head;
        }
        //ListNode fast=head,slow=head;
        // //让fast先走两步，避免slow节点夺走
        ListNode slow = head;
        ListNode fast = head.next.next;

        while (fast != null && fast.next != null) {
           slow = slow.next;
            fast = fast.next.next;
        }
        return slow;

    }

    // 合并两个有序链表（21. 合并两个有序链表）
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode sentry = new ListNode(-1);
        ListNode curr = sentry;

        while(l1 != null && l2 != null) {
            if(l1.val < l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }

            curr = curr.next;
        }

        curr.next = l1 != null ? l1 : l2;
        return sentry.next;
    }

    
}
```



#### [25. K 个一组翻转链表](https://leetcode-cn.com/problems/reverse-nodes-in-k-group/)

难度困难931

给你一个链表，每 *k* 个节点一组进行翻转，请你返回翻转后的链表。

*k* 是一个正整数，它的值小于或等于链表的长度。

如果节点总数不是 *k* 的整数倍，那么请将最后剩余的节点保持原有顺序。

**进阶：**

- 你可以设计一个只使用常数额外空间的算法来解决此问题吗？
- **你不能只是单纯的改变节点内部的值**，而是需要实际进行节点交换。

 

**示例 1：**

![img](https://assets.leetcode.com/uploads/2020/10/03/reverse_ex1.jpg)



**解法1：链表题画图更加直观**

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        //构建虚头
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode end = dummy;

        while(end.next!=null){
            for(int i=0;i<k&&end!=null;i++) end=end.next;
            if(end==null) break;  //剩余K个节点不要逆转
            ListNode start = pre.next;
            //每k个结点断开，逆转后在连接上
            ListNode next = end.next;
            end.next =null;
            pre.next =  reverse(start); //会把pre节点也给反转了，需要重新指定pre.next

            //连接上，并开始逆转下k个节点  (此时start是链表的尾节点)
            start.next = next;
            pre = start;
            end = start;
        }

        return dummy.next;
    }

    //反转链表
    public ListNode reverse(ListNode head){
        if(head==null||head.next==null){
            return head;
        }
        ListNode newHead = reverse(head.next);
        head.next.next = head;
        head.next = null;  //此时head是反转后的投
        return newHead;
    }
}
```



#### [23. 合并K个升序链表](https://leetcode-cn.com/problems/merge-k-sorted-lists/)

给你一个链表数组，每个链表都已经按升序排列。

请你将所有链表合并到一个升序链表中，返回合并后的链表。

**示例 1：**

```
输入：lists = [[1,4,5],[1,3,4],[2,6]]
输出：[1,1,2,3,4,4,5,6]
解释：链表数组如下：
[
  1->4->5,
  1->3->4,
  2->6
]
将它们合并到一个有序链表中得到。
1->1->2->3->4->4->5->6
```



##### 解法1：归并排序

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists==null||lists.length==0) return null;
        return patition(0,lists.length-1,lists);
    }

    //归并排序
    public ListNode patition(int left,int right,ListNode[] lists){
        if (left == right) return lists[left];
        int mid = left + (right - left) / 2;
        ListNode l1 = patition(left, mid,lists);
        ListNode l2 = patition( mid + 1, right,lists);
        return merge(l1, l2);

    }

    public ListNode merge(ListNode l1,ListNode l2){
        if(l1==null) return l2;
        if(l2==null) return l1;

        if(l1.val<l2.val){
            l1.next = merge(l1.next,l2);
            return l1;
        }else{
            l2.next = merge(l1,l2.next);
            return l2;
        }
    }

}
```



## 栈、队的使用类

#### [20. 有效的括号](https://leetcode-cn.com/problems/valid-parentheses/)

给定一个只包括 `'('`，`')'`，`'{'`，`'}'`，`'['`，`']'` 的字符串，判断字符串是否有效。

有效字符串需满足：

1. 左括号必须用相同类型的右括号闭合。
2. 左括号必须以正确的顺序闭合。

注意空字符串可被认为是有效字符串。



##### 解法1：

- Java中有stack类直接使用
- **出栈一定需要判空在出栈!!!!!!!**
- 用到一个小技巧,遇到左括号,将右括号进站,那遇到右括号直接判断 c等于stack.pop()即可   **(类似两数之和中,将互补的数压入hash表)**
- **最后一定要去栈判空**

```java
public boolean isValid(String s) {
        //特殊判断
        if(s==null) return true;
        if(s.length()%2==1) return false;

        //stack类
        Stack<Character> stack = new Stack();
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(c=='(')
                stack.push(')');
            else if(c=='{')
                stack.push('}');
            else if(c=='[')
                stack.push(']');
            else if(stack.empty()||c!=stack.pop())
                return false;
        }
		
    	//最后一定要对栈继续判空
        if(stack.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
```







#### [21. 合并两个有序链表](https://leetcode-cn.com/problems/merge-two-sorted-lists/)

将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 

 示例：

输入：1->2->4, 1->3->4
输出：1->1->2->3->4->4

##### 解法1：非递归

- 三个指针，res一直在找result的next节点，p1指向l1上当前正在比较节点，q2指向l2上当前正在比较节点

```java
 public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        //特殊情况
        if(l1==null) return l2;
        if(l2==null) return l1;

        ListNode dummy = new ListNode(0,null); //虚假节点
        ListNode res = dummy,p1 = l1,q2=l2;
        while(p1!=null && q2!=null){
            if(p1.val <= q2.val){
                res.next = p1;
                p1=p1.next;
            }else{
                res.next = q2;
                q2 = q2.next;
            }
            res = res.next;
        }
        if(p1==null) res.next =q2;
        if(q2==null) res.next =p1;

        return dummy.next;
    }
```

##### 解法2：递归法

- 递归题目对于null值一般不用做异常处理，大多将null值作为边界情况，返回需要的值即可

![image-20201026205816452](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201026205816452.png)

![image-20201026205824690](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201026205824690.png)

![image-20201026205839145](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201026205839145.png)

![image-20201026205848242](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201026205848242.png)

![image-20201026205857722](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201026205857722.png)

![image-20201026205904535](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201026205904535.png)

![image-20201026205909849](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201026205909849.png)

![image-20201026205914153](C:\Users\黎先桦\AppData\Roaming\Typora\typora-user-images\image-20201026205914153.png)

```java
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        //递归边界
        if(l1==null){
            return l2;
        }else if(l2==null){
            return l1;
        }else{
            if(l1.val<l2.val){
                //l1是目标节点，为l1找到next节点，在返回l1给上层
                l1.next = mergeTwoLists(l1.next,l2);
                return l1;
            }else{
                l2.next = mergeTwoLists(l1,l2.next);
                return l2;
            }
        }
```





#### [155. 最小栈](https://leetcode-cn.com/problems/min-stack/)

难度简单783

设计一个支持 `push` ，`pop` ，`top` 操作，并能在常数时间内检索到最小元素的栈。

- `push(x)` —— 将元素 x 推入栈中。
- `pop()` —— 删除栈顶的元素。
- `top()` —— 获取栈顶元素。
- `getMin()` —— 检索栈中的最小元素。



##### 解法1：使用额外一个栈，逆序记录入栈的值

![image-20210202172358117](C:\Users\黎先桦\Desktop\MarkDown\Leedocode题目记录.assets\image-20210202172358117.png)

```java
class MinStack {
    Stack<Integer> stack ;
    Stack<Integer> min_stack; //严格逆序
    /** initialize your data structure here. */
    public MinStack() {
        stack = new Stack<Integer>();
        min_stack  = new Stack<Integer>();
        
    }
    
    public void push(int x) {
        stack.add(x);
        if(min_stack.isEmpty()||x<=min_stack.peek()){
            min_stack.add(x);
        }
        
    }
    
    public void pop() {
        int x= stack.pop();
        if(x==(int)min_stack.peek()){
            min_stack.pop();
        }
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int getMin() {
        return min_stack.peek();
    }
}

```



##### 解法2：不使用额外空间

- 用一个变量min记录栈的最小值
- **进栈**：当min发生改变时，将min进栈，再更新min，再进栈x
- **出栈**：当出栈值等于min时，再出栈一个数，更新min

```java
  class MinStack {
    int min = Integer.MAX_VALUE;
    Stack<Integer> stack = new Stack<Integer>();
    public void push(int x) {
        //当前值更小
        if(x <= min){   
            //将之前的最小值保存
            stack.push(min);
            //更新最小值
            min=x;
        }
        stack.push(x);
    }

    public void pop() {
        //如果弹出的值是最小值，那么将下一个元素更新为最小值
        if(stack.pop() == min) {
            min=stack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return min;
    }
}
```



### **单调栈**

**<u>核心：遍历到后面的元素时，才能确定前面元素的结果</u>**

单调栈是否验证单调，看出栈的条件（可以判断一个元素结果的条件） ******

![image-20210204191136622](C:\Users\lenovo\Desktop\MarkDown\Leedocode题目记录.assets\image-20210204191136622.png)

![image-20210204191152041](C:\Users\lenovo\Desktop\MarkDown\Leedocode题目记录.assets\image-20210204191152041.png)



#### [739. 每日温度](https://leetcode-cn.com/problems/daily-temperatures/)

难度中等629

请根据每日 `气温` 列表，重新生成一个列表。对应位置的输出为：要想观测到更高的气温，至少需要等待的天数。如果气温在这之后都不会升高，请在该位置用 `0` 来代替。

例如，给定一个列表 `temperatures = [73, 74, 75, 71, 69, 72, 76, 73]`，你的输出应该是 `[1, 1, 4, 2, 1, 1, 0, 0]`。

**提示：**`气温` 列表长度的范围是 `[1, 30000]`。每个气温的值的均为华氏度，都是在 `[30, 100]` 范围内的整数。



**解法1：** **单调栈**

```java
class Solution {
    //核心：遍历到后面的元素时，才能确定前面元素的结果
    public int[] dailyTemperatures(int[] T) {
        int length = T.length;
        int[] ans = new int[length];
        //栈存着T的下标，按温度逆序存下标，栈顶下标对于温度最低
        Stack<Integer> stack = new Stack<Integer>();
        for(int i=0;i<length;i++){
            int temporary = T[i];
            //碰到温度更高的，可以更新栈顶下标的ans
            while(!stack.isEmpty()&&temporary>T[stack.peek()]){
                ans[stack.peek()] = i-stack.peek();
                stack.pop();
            }
            //上面判断之后，push（i）进去
            stack.push(i);
        }
        return ans;
    }
}
```



#### [84. 柱状图中最大的矩形](https://leetcode-cn.com/problems/largest-rectangle-in-histogram/)

难度困难1177

给定 *n* 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。

求在该柱状图中，能够勾勒出来的矩形的最大面积。

 

![img](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/10/12/histogram.png)

以上是柱状图的示例，其中每个柱子的宽度为 1，给定的高度为 `[2,1,5,6,2,3]`



##### 解法1：单调栈

我们遍历每个柱体

- 若当前的柱体高度大于等于栈顶柱体的高度，就直接将当前柱体入栈
- 否则若当前的柱体高度小于栈顶柱体的高度，说明当前栈顶柱体找到了右边的第一个小于自身的柱体，那么就可以将栈顶柱体出栈来计算以其为高的矩形的面积了。

- 头和尾加了两个高度为 0 的柱体妙的不得了

![image-20210206170552826](C:\Users\lenovo\Desktop\MarkDown\Leedocode题目记录.assets\image-20210206170552826.png)

**例子：**上图中，高度为5的位置可以记录矩形面积了  面积为 hight*wideth = 5**(4-2)

![image-20210206170633790](C:\Users\lenovo\Desktop\MarkDown\Leedocode题目记录.assets\image-20210206170633790.png)



```java
class Solution {
    public int largestRectangleArea(int[] heights) {
        //用两个哨兵  (不用对遍历结束后，栈内的元素进行特殊处理)
        int[] tmp = new int[heights.length + 2];
        for(int i=0;i<heights.length;i++){
            tmp[i+1] = heights[i];
        }

        //单调递增栈
        //当遇到小数时，栈顶左右都比栈顶小，栈顶为高的矩形为一次结果
        int res=  0;
        Stack<Integer> stack = new Stack();
        // 对栈中柱体来说，栈中的下一个柱体就是其「左边第一个小于自身的柱体」；
        // 若当前柱体 i 的高度小于栈顶柱体的高度，说明 i 是栈顶柱体的「右边第一个小于栈顶柱体的柱体」。
        // 因此以栈顶柱体为高的矩形的左右宽度边界就确定了，可以计算面积🌶️ ～
        for(int i=0;i<tmp.length;i++){
            while(!stack.isEmpty()&&tmp[i]<tmp[stack.peek()]){
                //栈内墙  高的柱子  栈外墙
                //记录高的柱子
                int height = tmp[stack.pop()];
                //计算左右墙的长度
                int curRes = height * (i-stack.peek()-1);
                res = Math.max(res,curRes);
            }
            stack.push(i);
        }

        return res;
    }
}

```



#### [42. 接雨水](https://leetcode-cn.com/problems/trapping-rain-water/)

难度困难2139收藏分享切换为英文接收动态反馈

给定 *n* 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。

 

**示例 1：**

![img](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/10/22/rainwatertrap.png)

```
输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
输出：6
解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 
```

**示例 2：**

```
输入：height = [4,2,0,3,2,5]
输出：9
```

##### 解法1：暴力解

- **每次计算一个位置能装多少水 （找左右最高的墙）**



##### 解法2：单调栈

- 构建存的下标的单调减栈
- 小数进栈：小数有积水的可能
- 大数出栈：计算积水的值
- 计算积水：  看成 **墙、积水、墙**

```java
class Solution {
    public int trap(int[] height) {
        //暴力解
        //return baoli(height);

        //单调栈
        int res= 0;
        if(height==null||height.length==0) return 0;
        int len = height.length;
         //存的下标的单调减栈
         //小数进栈：小数有积水的可能
         //大数出栈：计算积水的值
        Stack<Integer> stack = new Stack();
        for(int i =0;i<len;i++){
            //栈内墙  积水  栈外墙
            while(!stack.isEmpty()&&height[i]>height[stack.peek()]){
                //取出积水的高度,将积水出栈出栈
                int h = height[stack.peek()];
                stack.pop();
                //积水左边没墙，不可能有积水
                if(stack.isEmpty()){
                    break;
                }
                //计算左边的墙的高度与两墙之间的距离
                int realHeight = Math.min(height[i],height[stack.peek()])-h;
                int distance = i-stack.peek()-1;
                 //计算积水
                 res+=(distance*realHeight);
            }
            stack.push(i);
        }
        return res;

    }

    public int baoli(int[] height){
        //暴力解：每次计算一个位置能装多少水
        int res= 0;
        if(height==null||height.length==0) return 0;

        int len = height.length;
        for(int i=0;i<len;i++){
            int h= height[i];
            int leftMaxHeight = 0,rightMaxHeight=0;
            //查找左边最高
            for(int j =i-1;j>=0;j--){
                leftMaxHeight = Math.max(leftMaxHeight,height[j]);
            }
            //查找右边最高
            for(int j =i+1;j<len;j++){
                rightMaxHeight = Math.max(rightMaxHeight,height[j]);
            }

            if(rightMaxHeight>=h&&leftMaxHeight>=h){
                res+=(Math.min(leftMaxHeight,rightMaxHeight)-h);
            }
        }
        return res;
    }
}
```



#### [456. 132模式](https://leetcode-cn.com/problems/132-pattern/)

难度中等270

给定一个整数序列：a1, a2, ..., an，一个132模式的子序列 a**i**, a**j**, a**k** 被定义为：当 **i** < **j** < **k** 时，a**i** < a**k** < a**j**。设计一个算法，当给定有 n 个数字的序列时，验证这个序列中是否含有132模式的子序列。

**注意：**n 的值小于15000。

**示例1:**

```
输入: [1, 2, 3, 4]

输出: False

解释: 序列中不存在132模式的子序
```



##### 解法1：单调栈

- 跳着找数字的大小关系，首先想到单调栈
- **目标ai<ak<aj**
- 构建**最小值数组,**便于获取ai与ak的关系
- **单调栈,逆序，从后向前**，便于获取ak与aj的关系

```java
class Solution {
    public boolean find132pattern(int[] nums) {
        if(nums==null||nums.length<3) return false;

        int len = nums.length;
        //目标ai<ak<aj
        //1.构建做小值数组,便于获取ai与ak的关系
        int[] mins  = new int[len];
        mins[0] = nums[0];
        for(int i =1;i<len;i++){
            mins[i] = Math.min(nums[i],mins[i-1]);
        }
        //2.单调栈,逆序，从后向前，便于获取ak与aj的关系
        //栈中存的ak的值，遍历的值为aj
        Stack<Integer> stack = new Stack();
           for(int i =len-1;i>=0;i--){
           //先需要满足ai<ak
            if(nums[i]>mins[i]){
                //nums[i]为aj
                while(!stack.isEmpty()&&stack.peek()<nums[i]){
                    //aj>ai && ak>ai  (aj>ak是进入while 的条件)
                    if(nums[i]>mins[i]&&stack.peek()>mins[i]){
                        return true;
                    }else{
                        stack.pop();
                    }
                }
                stack.push(nums[i]);
            }
        }

        return false;

    }
}
```







## 树--类型题目

### [144. 二叉树的前序遍历](https://leetcode-cn.com/problems/binary-tree-preorder-traversal/)

##### ★解法1：树的非递归前序遍历

- 非递归需要用到栈！
- 注意非递归while循环退出条件：root==null  &&  stack.isEmpty()

```java
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        pre(root, res);
        return res;
    }

    private void pre(TreeNode root, List<Integer> res) {
        Stack<TreeNode> stack = new Stack<>();
        //非递归结束的条件！！
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                res.add(root.val);
                stack.add(root);
                root = root.left;
            }
            root = stack.pop().right;
        }
    }
}
```

##### 解法2：树的递归前序遍历

```java
class Solution {
    List<Integer> res = new ArrayList();
    public List<Integer> preorderTraversal(TreeNode root) {
        preorder(root);
        return res;

    }

    public void preorder(TreeNode root){
        if(root!=null){
            //前序遍历，在访问左右子节点前，访问根节点
            res.add(root.val);
            preorder(root.left);
            preorder(root.right);
        }
    }
}
```





### [剑指 Offer 07. 重建二叉树](https://leetcode-cn.com/problems/zhong-jian-er-cha-shu-lcof/)

输入某二叉树的前序遍历和中序遍历的结果，请重建该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。

例如，给出

```
前序遍历 preorder = [3,9,20,15,7]
中序遍历 inorder = [9,3,15,20,7]
```

返回如下的二叉树：

```
    3
   / \
  9  20
    /  \
   15   7
```

#### 本题突破口：1. 前序遍历 =  根节点+根的左子树+根的右子树

####                         2.中序遍历 = 左子树 + 根节点+右子树

![image-20201120190140007](C:\Users\黎先桦\Desktop\MarkDown\Leedocode题目记录.assets\image-20201120190140007.png)

解析：https://leetcode-cn.com/problems/zhong-jian-er-cha-shu-lcof/solution/mian-shi-ti-07-zhong-jian-er-cha-shu-by-leetcode-s/



#### 解法1：递归法 （分而治法）

![](C:\Users\黎先桦\Desktop\MarkDown\Leedocode题目记录.assets\image-20201120190321764.png)



```java
class Solution {
    int[] preorder,inorder;
    HashMap<Integer,Integer> map;//Map存下inorder每个值的下标
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        //异常判断
        if(preorder==null||preorder.length==0) return null;

        this.preorder = preorder;
        this.inorder = inorder;
        //Map存下inorder每个值的下标
         map = new HashMap();  // <节点值，在inorder数组中下标>
        int preLen = preorder.length;
        int inLen = inorder.length;
        for(int i=0;i<preLen;i++){
            map.put(inorder[i],i);
        }

        return Recurrsion(0,0,inLen-1);
    }

    //root(当前递归根节点在preorder中序号，start，end,root在inorder中节点范围的开始位置与结束位置)
    public TreeNode  Recurrsion(int root,int start,int end){
        //递归边界ino
        if(start<=end){  //等号时，表示当前根节点为叶子节点，在inorder中start==end
            //初始化根节点
            TreeNode rootNode = new TreeNode();
            rootNode.val = preorder[root];
            //当根节点在inorder的位置newRootIndex，inorder中，前面为左树，右为右树
            int newRootIndex = map.get(rootNode.val); 
            //新的根节点和新的根节点在inorder中范围
            //新的左根在preorder中位置root+1
            rootNode.left = Recurrsion(root+1,start,newRootIndex-1);
             //新的右根在preorder中位置root+1+左数节点个数（newRootIndex-start）
            rootNode.right = Recurrsion(root+1+newRootIndex-start,newRootIndex+1,end);
            return rootNode;

        }
        
        return null;
    }

}
```





#### [剑指 Offer 33. 二叉搜索树的后序遍历序列](https://leetcode-cn.com/problems/er-cha-sou-suo-shu-de-hou-xu-bian-li-xu-lie-lcof/)

输入一个整数数组，判断该数组是不是**某二叉搜索树**的后序遍历结果。如果是则返回 `true`，否则返回 `false`。假设输入的数组的任意两个数字都互不相同。

##### 解法1：递归法

- 后序遍历定义： [ 左子树 | 右子树 | 根节点 ] ，即遍历顺序为 “左、右、根” 。
- 二叉搜索树定义： 左子树中所有节点的值 << 根节点的值；右子树中所有节点的值 >> 根节点的值；其左、右子树也分别为二叉搜索树。
- ![image-20201219152808509](C:\Users\黎先桦\Desktop\MarkDown\Leedocode题目记录.assets\image-20201219152808509.png)



```java
class Solution {
    public boolean verifyPostorder(int[] postorder) {
        //后序遍历定义： [ 左子树 | 右子树 | 根节点 ] ，即遍历顺序为 “左、右、根” 。
        //二叉搜索树定义： 左子树中所有节点的值 << 根节点的值；右子树中所有节点的值 >> 根节点的值；其左、右子树也分别为二叉搜索树。
        if(postorder==null) return true;

        return recurssion(postorder,0,postorder.length-1);
    }

    public boolean recurssion(int[] postorder,int start,int end){
        if(start>=end) return true;

        int i = start;
        int rootVal = postorder[end];
        while(postorder[i]<rootVal) i++; //左子树的值都小于根
		
        //此时，i是后续中右子树的第一个节点的下标
        int m = i;   //m用来记录右子树的第一个节点的下标，下来划分左右子树的范围
        while(postorder[i]>rootVal) i++; //右子树的值都大于根

        return j==end &&recurssion(postorder,start,m-1)&&recurssion(postorder,m,end-1);
    }
}
```





### 二叉树的下一节点

https://www.nowcoder.com/practice/9023a0c988684a53960365b889ceaf5e?rp=3&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking

给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。

**解法1：**

分三种情况   （2，3情况可以合并一种情况，不断将3情况转化为2情况）

**注意对`pNode.next`有判空操作，避免对空指针的引用**

- 该节点有右子树，下一节点是右子树的最左节点
- 该节点无右子树，但是是其父节点的左子树，下一节点就是其父节点
- 该节点无右子树，又是父节点的右子树，继续往上找，直到父节点为空或者自己是父节点的左子树



```java
/*
public class TreeLinkNode {
    int val;
    TreeLinkNode left = null;
    TreeLinkNode right = null;
    TreeLinkNode next = null;

    TreeLinkNode(int val) {
        this.val = val;
    }
}
*/
public class Solution {
    public TreeLinkNode GetNext(TreeLinkNode pNode)
    {
        //异常判断
        if(pNode==null) return null;
        
        //有右子树
        if(pNode.right!=null){
            //下一节点是右子树的最左节点
            TreeLinkNode prightNode = pNode.right;
            while(prightNode.left!=null){
                prightNode=prightNode.left;
            }
            return prightNode;
        }
        //无右子树,是父节点的左子树，则下一节点就是父节点，否则往上找
        //注意对`pNode.next`有判空操作，避免对空指针的引用
        else if(pNode.next!=null){
            TreeLinkNode pParent = pNode.next;
            //节点是父节点的右节点，就还要向上找
            //如果找到pParent是空，则自己是最右节点，无下一节点，返回null （刚好此时pParent也是null）
            while(pParent!=null&&pParent.right==pNode){
                pNode =pParent;
                pParent = pNode.next;
            }
            //直到节点是父节点的左节点，返回父节点
            return pParent;
        }
        //无右子树、无父节点，就没有下一节点（自己就是中序遍历的最后一个节点）
        return null;
    }
}
```



## **图--类型题目**

### 图的广度优先，求最短路径长度

#### [127. 单词接龙](https://leetcode-cn.com/problems/word-ladder/)

给定两个单词（*beginWord* 和 *endWord*）和一个字典，找到从 *beginWord*到 *endWord* 的最短转换序列的长度。转换需遵循如下规则：

1. 每次转换只能改变一个字母。
2. 转换过程中的中间单词必须是字典中的单词。

**说明:**

- 如果不存在这样的转换序列，返回 0。
- 所有单词具有相同的长度。
- 所有单词只由小写字母组成。
- 字典中不存在重复的单词。
- 你可以假设 *beginWord* 和 *endWord* 是非空的，且二者不相同。

**示例 1:**

```
输入:
beginWord = "hit",
endWord = "cog",
wordList = ["hot","dot","dog","lot","log","cog"]

输出: 5

解释: 一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog",
     返回它的长度 5。
```

##### 解法1：BFS，求图的最短路径长度，使用visited记录是否访问，queue队列帮助广度优先

1. Java中队使用**LinkedList**，用**add**（）在队尾添加元素，用**poll**()拿出队首元素  （peek（）,返回但不移除首元素）**
2. BFS的模板，while（判断）+for（当前队列的长度）+其他操作

```java
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet();
        for(String s:wordList){
            wordSet.add(s);
        }
        if(wordSet.size()==0||!wordSet.contains(endWord)) return 0;

        //使用visited和queue对图进行广度优先遍历
        Set<String> visited = new HashSet();
        LinkedList<String> queue = new LinkedList();
        int deep =0;
        //队中压入startWord
        visited.add(beginWord);
        queue.add(beginWord);
        //广度优先遍历
        while(!queue.isEmpty()){
            deep++; //每次经过while表示广度的遍历的深度加1
            int queSize = queue.size();
            for(int i =0;i<queSize;i++){
                String curString = queue.poll(); //广度遍历每层的多个节点，用for遍历
                //每层的节点去访问wordList中其他节点
                for(String s:wordList){
                    //访问过的单词不做处理
                    if(visited.contains(s)) continue; 
                    //不能s不能由curString转化的跳过
                    if(!judge(curString,s)) continue;

                    //s能由curString转化，判断s是不是结果
                    if(s.equals(endWord)){
                        return deep+1;
                    }

                    //队中压入s，visited中标记访问过
                    visited.add(s);
                    queue.add(s);
                }

            }
        }
        return 0;
        

    }
    
    //是否两个String可以转换 (两个String差一个字母)
    public boolean judge(String s1,String s2){
        int diff = 0;
        if(s1.length()!= s2.length()) return false;
        for(int i=0;i<s1.length();i++){
            if(s1.charAt(i)!=s2.charAt(i)){
                diff++;
                if(diff>=2){
                    return false;
                }
            }
        }
        return true;
    }
}
```

##### 解法2：优先visited标记，由set改为boolean数组   用时：1189ms---》595

```java
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) {
            return 0;
        }
        // visited 修改为 boolean 数组
        boolean[] visited = new boolean[wordList.size()];
        //如果beginWord在wordList，也要标记已访问
        int idx = wordList.indexOf(beginWord);
        if (idx != -1) {
            visited[idx] = true;
        }
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        int count = 0;
        while (queue.size() > 0) {
            int size = queue.size();
            ++count;
            while (size-- > 0) {
                String start = queue.poll();
                for (int i = 0; i < wordList.size(); ++i) {
                    // 通过 index 判断是否已经访问
                    if (visited[i]) {
                        continue;
                    }
                    //通过i获取s
                    String s = wordList.get(i);
                    if (!canConvert(start, s)) {
                        continue;
                    }
                    if (s.equals(endWord)) {
                        return count + 1;
                    }
                    //i绑定了s
                    visited[i] = true;
                    queue.offer(s);
                }
            }
        }
        return 0;
    }

    public boolean canConvert(String s1, String s2) {
        // 因为题目说了单词长度相同，可以不考虑长度问题
        // if (s1.length() != s2.length()) return false;
        int count = 0;
        for (int i = 0; i < s1.length(); ++i) {
            if (s1.charAt(i) != s2.charAt(i)) {
                ++count;
                if (count > 1) {
                    return false;
                }
            }
        }
        return count == 1;
    }
}

作者：jzj1993
链接：https://leetcode-cn.com/problems/word-ladder/solution/suan-fa-shi-xian-he-you-hua-javashuang-xiang-bfs23/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
```



## DFS、BFS 类型题目

### 回溯法

#### [22. 括号生成](https://leetcode-cn.com/problems/generate-parentheses/)

数字 *n* 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 **有效的** 括号组合。

解法1：DFS深度优先+减枝

- 回溯法的关键是不能上层递归下层时，不能改变上层的状态  （如下图中，递归参数用left-1，而不是left--，不能改变left的值）

```java
public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        // 特判
        if (n == 0) {
            return res;
        }

        // 执行深度优先遍历，搜索可能的结果
        dfs("", n, n, res);
        return res;
    }

    private void dfs(String curStr, int left, int right, List<String> res) {
        // 因为每一次尝试，都使用新的字符串变量，所以无需回溯
        // 在递归终止的时候，直接把它添加到结果集即可，注意与「力扣」第 46 题、第 39 题区分
        if (left == 0 && right == 0) {
            res.add(curStr);
            return;
        }
        // 剪枝（如图，左括号可以使用的个数严格大于右括号可以使用的个数，才剪枝，注意这个细节）
        if (left > right) {
            return;
        }
        if (left > 0) {
            //回溯法的关键是不能上层递归下层时，不能改变上层的状态
            dfs(curStr + "(", left - 1, right, res);
        }
        if (right > 0) {
            dfs(curStr + ")", left, right - 1, res);
        }
    }
```





#### [剑指 Offer 12. 矩阵中的路径](https://leetcode-cn.com/problems/ju-zhen-zhong-de-lu-jing-lcof/)

难度中等205

请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一格开始，每一步可以在矩阵中向左、右、上、下移动一格。如果一条路径经过了矩阵的某一格，那么该路径不能再次进入该格子。例如，在下面的3×4的矩阵中包含一条字符串“bfce”的路径（路径中的字母用加粗标出）。

[["a","**b**","c","e"],
["s","**f**","**c**","s"],
["a","d","**e**","e"]]

但矩阵中不包含字符串“abfb”的路径，因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入这个格子。

**示例 1：**

```
输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
输出：true
```

**示例 2：**

```
输入：board = [["a","b"],["c","d"]], word = "abcd"
输出：false
```

##### 解法1：回溯法   （visited数组的恢复）

- 回溯法最重要的是不能改变遍历的状态，在这里重点是**visited数组的恢复**
- 匹配成功的判断设为`strindex==strlen-1且这一躺匹配成功`,而不是`strindex==strlen`，后者需要多考虑边界问题

```java
class Solution {
    int cow,row,strlen;
    boolean flag = false;
    int[][] visited;
    public boolean exist(char[][] board, String word) {
        //合法判断
        if(word==null||word.length()==0) return false;
        cow = board.length;
        row = board[0].length;
        strlen = word.length();

        //visited数组
        visited = new int[cow][row];

        //找到第一个匹配点
        for(int i=0;i<cow;i++){
            for(int j=0;j<row;j++){
                if(board[i][j] == word.charAt(0)){
                    //深度遍历
                    deepFirst(board,word,0,i,j,visited);
                    if(flag==true){
                        return flag;
                    }
                }
            }
        }

        return flag;

    }

    public void deepFirst(char[][] board, String str,int strindex,int cowNow,int rowNow,int[][] visited){
        //边界合法性判断
        if(cowNow>=0&&cowNow<cow&&rowNow>=0&&rowNow<row&&flag!=true){
            //匹配
            if(visited[cowNow][rowNow]==0){
                //匹配到一个
                if(board[cowNow][rowNow]==str.charAt(strindex)){
                    //匹配成功
                    if(strindex==strlen-1){
                        flag=true;
                        return; 
                    }
                    visited[cowNow][rowNow]=1;
                    
                    deepFirst(board,str,strindex+1,cowNow+1,rowNow,visited);
                    deepFirst(board,str,strindex+1,cowNow-1,rowNow,visited);
                    deepFirst(board,str,strindex+1,cowNow,rowNow+1,visited);
                    deepFirst(board,str,strindex+1,cowNow,rowNow-1,visited);
                    
                    //回溯
                    visited[cowNow][rowNow]=0; //回溯  关键不能改变状态
                }else{
                    //没有匹配成功
                    return;
                }
            }
        }


    }
}
```



##### 解法二：巧妙节省visited数组空间

- visited数组的作用就是避免重复匹配，我们将board【i】【j】匹配成功，将board【i】【j】设为一个不可能匹配成功的值
- 将避免重复匹配条件 --》 可匹配，但不可能匹配成功条件

```java
class Solution {
    public boolean exist(char[][] board, String word) {
        char[] words = word.toCharArray();
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(dfs(board, words, i, j, 0)) return true;
            }
        }
        return false;
    }
    boolean dfs(char[][] board, char[] word, int i, int j, int k) {
        if(i >= board.length || i < 0 || j >= board[0].length || j < 0 || board[i][j] != word[k]) return false;
        if(k == word.length - 1) return true;
        //然后修改当前坐标的值，为一个不可能匹配成功的值
        board[i][j] = '\0';
        boolean res = dfs(board, word, i + 1, j, k + 1) || dfs(board, word, i - 1, j, k + 1) || 
                      dfs(board, word, i, j + 1, k + 1) || dfs(board, word, i , j - 1, k + 1);
        //递归之后再把当前的坐标复原
        board[i][j] = word[k];
        return res;
    }
}


```



#### [39. 组合总和](https://leetcode-cn.com/problems/combination-sum/)

难度中等1128

给定一个**无重复元素**的数组 `candidates` 和一个目标数 `target` ，找出 `candidates` 中所有可以使数字和为 `target` 的组合。

`candidates` 中的数字可以无限制重复被选取。

**说明：**

- 所有数字（包括 `target`）都是正整数。
- 解集不能包含重复的组合。 

**示例 1：**

```c
输入：candidates = [2,3,6,7], target = 7,
所求解集为：
[
  [7],
  [2,2,3]
]
```

##### 解法：经典回溯法

- 重点1：**去除**（DFS中加入start参数， for循环不从0开始，从start开始）
- 重点2：**减枝**  (对combinationSum，for循环中当candidates[i]>target时，后面的数都不用递归，直接break)

```java
class Solution {
    List<List<Integer>> res;
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        res = new LinkedList<List<Integer>>();
        List<Integer> curList = new LinkedList<Integer>();
        Arrays.sort(candidates);  //排序，方便减枝
        DFS(0,candidates,target,curList);
        return res;
    }
    
    
    //去重：每次循环不重0开始，从递归的start开始
    //剪枝：
    public void DFS(int start,int[] candidates, int target,List curList){
        if(target==0){
            res.add(new LinkedList(curList));
            return;
        }else if(target<0){
            return ;
        }else{
            for(int i=start;i<candidates.length;i++){
                if(candidates[i]>target){
                    //当排序过的candidates[i]>target，i之后的数都会大于target
                    break;
                }
                curList.add(candidates[i]);
                DFS(i,candidates,target-candidates[i],curList);
                //回溯
                curList.remove((Object)candidates[i]);
            }
        }
    }
}
```













```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
     List<List<Integer>> res ;
     LinkedList<Integer> curList;
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        res = new LinkedList();
        curList = new LinkedList();
        if(root==null) return res;

        //DFS
        DFS(root,sum,0);
        return res;
    }


    public void DFS(TreeNode root, int sum,int curSum){
        
        //空节点
        if(root==null){
            return;
        }
         curList.add(root.val);
         curSum+=root.val;

        //到达叶子节点
        if(curSum==sum&&root.left==null&&root.right==null){
            res.add(new LinkedList(curList));
        }
        
        DFS(root.left,sum,curSum);
        DFS(root.right,sum,curSum);
        curList.removeLast();
       
    }
}

//list 不能做参数
```







### BFS广度优先、DFS深度优先

- java中 栈直接用Stack数据类型，队用LinkedList数据类型
- 模板：1.所有节点都可入队    2.先排除不合理节点   3.合理节点进行结果计算

#### [剑指 Offer 13. 机器人的运动范围](https://leetcode-cn.com/problems/ji-qi-ren-de-yun-dong-fan-wei-lcof/)

地上有一个m行n列的方格，从坐标 `[0,0]` 到坐标 `[m-1,n-1]` 。一个机器人从坐标 `[0, 0] `的格子开始移动，它每次可以向左、右、上、下移动一格（不能移动到方格外），也不能进入行坐标和列坐标的数位之和大于k的格子。例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。但它不能进入方格 [35, 38]，因为3+5+3+8=19。请问该机器人能够到达多少个格子？

 

**示例 1：**

```
输入：m = 2, n = 3, k = 1
输出：3
```

**示例 2：**

```
输入：m = 3, n = 1, k = 0
输出：1
```

##### 解法1：DFS深度优先模板

```java
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
        if(cowNow>=cow||cowNow<0||rowNow<0||rowNow>=row||
           sumPerNum(cowNow)+sumPerNum(rowNow)>k||arr[cowNow][rowNow]==true){
            return 0;
        }
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

##### 解法2：BFS广度优先模板

```java
class Solution {
    boolean arr[][];
    public int movingCount(int m, int n, int k) {
        //arr用于最后计算总格子数
        arr = new boolean[m][n];

        //DFS
        //return DFS(0,0,m,n,k);
        

        //BFS
        //根节点入队
        int res =0;
        LinkedList queue = new LinkedList();
        queue.add(new int[]{0,0});
        

        //开始遍历队
        while(!queue.isEmpty()){
            int queueSize = queue.size();
            for(int i=0;i<queueSize;i++){
                //poll返回的函数类型是object
                int[] curPosition = (int[])queue.poll();
                int cowNow = curPosition[0];
                int rowNow = curPosition[1];

                //不符合要求的点跳过
                if(cowNow>=m||rowNow>=n||sumPerNum(cowNow)+sumPerNum(rowNow)>k||arr[cowNow][rowNow]==true){
                    continue;
                }
                //符合要求
                res++;
                arr[cowNow][rowNow]=true;
                queue.add(new int[]{cowNow+1,rowNow});
                queue.add(new int[]{cowNow,rowNow+1});
                
            }
        }

        return res;
    }

    //广度优先算法
    public int DFS(int cowNow,int rowNow,int cow,int row,int k){
        if(cowNow>=cow||cowNow<0||rowNow<0||rowNow>=row||sumPerNum(cowNow)+sumPerNum(rowNow)>k||arr[cowNow][rowNow]){
            return 0;
        }
        arr[cowNow][rowNow] = true;
        return 1+DFS(cowNow,rowNow+1,cow,row,k)+DFS(cowNow+1,rowNow,cow,row,k);
    

        // return 0;
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

#### [78. 子集](https://leetcode-cn.com/problems/subsets/)

难度中等1064收藏分享切换为英文接收动态反馈

给你一个整数数组 `nums` ，数组中的元素 **互不相同** 。返回该数组所有可能的子集（幂集）。

解集 **不能** 包含重复的子集。你可以按 **任意顺序** 返回解集。

 

**示例 1：**

```
输入：nums = [1,2,3]
输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
```



```java
class Solution {
    List<List<Integer>> res;
    public List<List<Integer>> subsets(int[] nums) {
        res = new LinkedList();
        if(nums==null||nums.length==0) return res;

        //DFS
        LinkedList curList = new LinkedList();
        DFS(0,nums,nums.length,curList);
        return res;
    }

    //回溯
    public void DFS(int start,int[] nums,int numsLen,LinkedList<Integer> curList){
        if(start>numsLen){
            return;
        }
        //添加结果
        res.add(new LinkedList(curList));
        for(int i =start;i<numsLen;i++){
            curList.add(nums[i]);
            DFS(i+1,nums,numsLen,curList);
            //回溯
            curList.removeLast();
        }
    }
}
```

#### [77. 组合](https://leetcode-cn.com/problems/combinations/)

难度中等527收藏分享切换为英文接收动态反馈

给定两个整数 *n* 和 *k*，返回 1 ... *n* 中所有可能的 *k* 个数的组合。

**示例:**

```
输入: n = 4, k = 2
输出:
[
  [2,4],
  [3,4],
  [2,3],
  [1,2],
  [1,3],
  [1,4],
]
```

**解法1：DFS**

```java
class Solution {
     List<List<Integer>> res ;
    public List<List<Integer>> combine(int n, int k) {
        res = new LinkedList();
        LinkedList<Integer> curList = new LinkedList();
        dfs(n,0,k,curList);

        return res;

    }

    public void dfs(int n,int index,int k,LinkedList<Integer> curList){
        if(k==0){
            res.add(new LinkedList(curList));
            return ;
        }
        for(int i=index;i<n;i++){
            curList.add(i+1);
            //从i开始继续dfs
            dfs(n,i+1,k-1,curList);
            curList.removeLast();
        }
    
    }

}
```



#### [129. 求根到叶子节点数字之和](https://leetcode-cn.com/problems/sum-root-to-leaf-numbers/)

给定一个二叉树，它的每个结点都存放一个 `0-9` 的数字，每条从根到叶子节点的路径都代表一个数字。

例如，从根到叶子节点路径 `1->2->3` 代表数字 `123`。

计算从根到叶子节点生成的所有数字之和。

**说明:** 叶子节点是指没有子节点的节点。

**示例 1:**

```
输入: [1,2,3]
    1
   / \
  2   3
输出: 25
解释:
从根到叶子节点路径 1->2 代表数字 12.
从根到叶子节点路径 1->3 代表数字 13.
因此，数字总和 = 12 + 13 = 25.
```

##### 解放1：DFS深度优先

- 深度优先的递归，一次路径的结束有两个方式判断。  1.判断此时节点为叶子节点   2.判断此时节点为空节点
- 这道题不能用2方式，不然的左空、右空时，res+=curNum会执行多次

```java
 public int sumNumbers(TreeNode root) {
        if(root==null) return 0;

        dfs(root,0);
        return res; //res全局变量
    }
    public void dfs(TreeNode root,int curNum){
        if(root!=null){
            curNum = curNum*10 +root.val;
             if(root.left==null&root.right==null){
                res+=curNum;
            }
            
            dfs(root.left,curNum);
            dfs(root.right,curNum);
        }
    }
```

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
     public int sumNumbers(TreeNode root) {
        return dfs(root, 0);
    }
    //DFS
    public int dfs(TreeNode root,int PreSum){
        if(root==null){
            return 0;
        }

        int sum  = PreSum*10+root.val;
        //到达叶子节点时，sum为一个结果
        if (root.left == null && root.right == null) {
            return sum;
        } else {
            //返回该节点所包含所有叶子的结果
            return dfs(root.left, sum) + dfs(root.right, sum);
        }
    }
}
```





#### [剑指 Offer 47. 礼物的最大价值](https://leetcode-cn.com/problems/li-wu-de-zui-da-jie-zhi-lcof/)

在一个 m*n 的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于 0）。你可以从棋盘的左上角开始拿格子里的礼物，并每次向右或者向下移动一格、直到到达棋盘的右下角。给定一个棋盘及其上面的礼物的价值，请计算你最多能拿到多少价值的礼物？

 

**示例 1:**

```
输入: 
[
  [1,3,1],
  [1,5,1],
  [4,2,1]
]
输出: 12
解释: 路径 1→3→5→2→1 可以拿到最多价值的礼物
```

##### 解法1：无额外使用空间的DFS

- 容易超时

```java
class Solution {
    public int maxValue(int[][] grid) {
        return DFS(grid,0,0,grid.length,grid[0].length,0);
    }

    //回溯法
    public int DFS(int[][] grid,int col,int row,int maxCol,int maxRow,int sumVal ){
        //越界 return 0； 
        if(col>=maxCol||row>=maxRow){
            return 0;
        }
        
        //到达目标位置
        if(col==maxCol-1&&row ==maxRow-1){
            return sumVal+grid[col][row];
        }
        
        //非目标的合法位置
        sumVal+=grid[col][row];
        
        //返回 右与下中的较大值+当前位置的值
        return Math.max(DFS(grid,col+1,row,maxCol,maxRow,sumVal),DFS(grid,col+1,row,maxCol,maxRow,sumVal));

    }
}
```



##### 解法2：使用额外空间的DFS

- 使用store数组记录遍历当前位置的最大值
- 递归时，先判断store中有无值时，有值，不用遍历，直接从store中取值，相当于剪枝

```java
class Solution {
    int[][] store; //存储位置的最大值
    public int maxValue(int[][] grid) {
        store = new int[grid.length][grid[0].length];
        return DFS(grid,0,0,grid.length,grid[0].length);
    }

    //回溯法
    public int DFS(int[][] grid,int col,int row,int maxCol,int maxRow){
        //1.越界
        if(col>=maxCol||row>=maxRow){
            return 0;
        }
        
        //2.右下角
        if(col==maxCol-1&&row ==maxRow-1){
            store[col][row] = grid[col][row];
            return store[col][row];
        }
        
        //3.普通节点
        int downVal=0,rightVal=0;  //记录右与下位置的值
        //向下走
        if(col+1<maxCol){ //*******注意这里的越界判断
            if(store[col+1][row]==0){
                downVal = DFS(grid,col+1,row,maxCol,maxRow);
                store[col+1][row]=downVal;
            }else{
                downVal = store[col+1][row];
            }
        }

        //向右走
        if(row+1<maxRow){
            if(store[col][row+1]==0){
                rightVal = DFS(grid,col,row+1,maxCol,maxRow);
                store[col][row+1]=rightVal;
            }else{
                rightVal = store[col][row+1];
            }
        }
		
        //4.返回
        return Math.max(downVal,rightVal)+grid[col][row];
    }
}
```





#### [200. 岛屿数量](https://leetcode-cn.com/problems/number-of-islands/)

难度中等962

给你一个由 `'1'`（陆地）和 `'0'`（水）组成的的二维网格，请你计算网格中岛屿的数量。

岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。

此外，你可以假设该网格的四条边均被水包围。

 

**示例 1：**

```
输入：grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]
输出：1
```

```java
class Solution {
     //将与值为1相连的所有1变成0，最后计算1的个数
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int cow = grid.length;
        int rol = grid[0].length;
        int res = 0;
        for(int i=0;i<cow;i++){
            for(int j=0;j<rol;j++){
                if(grid[i][j]=='1'){
                    res++;
                    DFS(i,j,cow,rol,grid);
                }
            }
        }

        return res;

    }

    //将与值为1相连的所有1变成0
    public void DFS(int i,int j,int cow,int rol,char[][] grid){
        //越界处理   ,碰到0也可以return 
        if(i>=cow||j>=rol||i<0||j<0||grid[i][j] == '0'){
            return ;
        }
        if(grid[i][j]=='1'){
            grid[i][j] = '0';
        }

        //递归上下左右
        DFS(i+1,j,cow,rol,grid);
        DFS(i-1,j,cow,rol,grid);
        DFS(i,j+1,cow,rol,grid);
        DFS(i,j-1,cow,rol,grid);

    }
}
```





### 递归题

#### [21. 合并两个有序链表](https://leetcode-cn.com/problems/merge-two-sorted-lists/)

**示例：**

```
输入：1->2->4, 1->3->4
输出：1->1->2->3->4->4
```

##### 解法1：递归

```java
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        //递归边界
        //l1为null,表示l1链表的末结点的next要是l2链表的当前遍历的节点
        if(l1==null){
            return l2;
        }else if(l2==null){
            return l1;
        }else{
            if(l1.val<l2.val){
                //先确定next，再将自己返回
                l1.next = mergeTwoLists(l1.next,l2);
                return l1;
            }else{
                l2.next = mergeTwoLists(l1,l2.next);
                return l2;
            }
        }
    }
}
```



#### [剑指 Offer 18. 删除链表的节点](https://leetcode-cn.com/problems/shan-chu-lian-biao-de-jie-dian-lcof/)

难度简单75

给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。

返回删除后的链表的头节点。

**注意：**此题对比原题有改动

**示例 1:**

```java
输入: head = [4,5,1,9], val = 5
输出: [4,1,9]
解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
```

**示例 2:**

```java
输入: head = [4,5,1,9], val = 1
输出: [4,5,9]
解释: 给定你链表中值为 1 的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9.
```

##### 解法1：单链表的递归

```java
 //删除节点的递归法
    public ListNode deleteNode(ListNode head, int val) {
        if(head==null){
            return null;
        }else if(head.val  == val){ //return head.next是关键，相当于删除操作   
            return head.next;
        }
        head.next = deleteNode(head.next,val);  //单链表的递归
        return head;
    }
```



#### [剑指 Offer 36. 二叉搜索树与双向链表](https://leetcode-cn.com/problems/er-cha-sou-suo-shu-yu-shuang-xiang-lian-biao-lcof/)

输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的循环双向链表。要求不能创建任何新的节点，只能调整树中节点指针的指向。

##### **解法1：**中序遍历+设置pre节点

- **在对树的遍历的时候，可以用一个pre节点记录遍历的前后继关系！！**     （查找二叉搜索树经常用到）

```java
class Solution {
    Node pre,head;
    public Node treeToDoublyList(Node root) {
        if(root==null) return null;
        inOrder(root);
        //递归出来时，head是链表第一个，pre是最后一个
        head.left = pre;
        pre.right =head;
        return head;
    }

    public void inOrder(Node cur){
        if(cur!=null){
            inOrder(cur.left);
            //中序遍历
            if(pre==null){
                head = cur; //树一直向左走的叶子节点，是整个链表的头节点
            }else{
                pre.right = cur;
            }
            cur.left = pre; //建立cur与pre的链接关系
            pre = cur; //关键
            inOrder(cur.right);
        }
    }
}
```





#### [剑指 Offer 68 - I. 二叉搜索树的最近公共祖先](https://leetcode-cn.com/problems/er-cha-sou-suo-shu-de-zui-jin-gong-gong-zu-xian-lcof/)

给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。

[百度百科](https://baike.baidu.com/item/最近公共祖先/8918834?fr=aladdin)中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（**一个节点也可以是它自己的祖先**）。”

例如，给定如下二叉搜索树: root = [6,2,8,0,4,7,9,null,null,3,5]

![img](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/12/14/binarysearchtree_improved.png)

##### 解法1：非递归

```java
class Solution {
    //题目中已说明，p，q都在二叉搜索树中。否则需要先查找
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //抓住二叉搜索树这个条件
        while(root!=null){
            if(p.val<root.val&&q.val<root.val){
                //两个节点都在左子树
                root = root.left;
            }else if(p.val>root.val&&q.val>root.val){
                //两个节点都在右子树
               root = root.right;
            }else{
                //一左一右，公共祖先只能是根节点
                //或者 两个节点中一个节点就是祖先
                break;
            }
        }
        return root;
       
    }
}
```

##### 解法2：递归

```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root.val < p.val && root.val < q.val){
            return lowestCommonAncestor(root.right, p, q);
        }else if(root.val > p.val && root.val > q.val){
            return lowestCommonAncestor(root.left, p, q);
        }else{
            //1.p，q在root两侧
            //2.找到p或q节点
             return root;
        }
    }
}
```



#### [剑指 Offer 68 - II. 二叉树的最近公共祖先](https://leetcode-cn.com/problems/er-cha-shu-de-zui-jin-gong-gong-zu-xian-lcof/)

给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。

[百度百科](https://baike.baidu.com/item/最近公共祖先/8918834?fr=aladdin)中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（**一个节点也可以是它自己的祖先**）。”

例如，给定如下二叉树: root = [3,5,1,6,2,0,8,null,null,7,4]

![img](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/12/15/binarytree.png)

 

**示例 1:**

```
输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
输出: 3
解释: 节点 5 和节点 1 的最近公共祖先是节点 3。
```

##### 解法1：

![image-20210125212903198](C:\Users\黎先桦\Desktop\MarkDown\Leedocode题目记录.assets\image-20210125212903198.png)

```java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
       return lastOrder(root,p,q);

    }

    public TreeNode lastOrder(TreeNode root, TreeNode p, TreeNode q){
        if(root!=null){
            //找到目标节点返回目标节点
            if(root.val==p.val||root.val==q.val){
                return root;
            }

            TreeNode left = lastOrder(root.left,p,q);
            TreeNode right = lastOrder(root.right,p,q);
            TreeNode res =null;
            //两个都找到了，返回root为祖先节点
            if(left!=null&&right!=null){
                res = root;
            } else if(left!=null){
                res = left;
            }else if(right!=null){
                res = right;
            }
            return res;              
        }
        return null;
    }
}
```





#### [101. 对称二叉树](https://leetcode-cn.com/problems/symmetric-tree/)

难度简单1218

给定一个二叉树，检查它是否是镜像对称的。

 

例如，二叉树 `[1,2,2,3,4,4,3]` 是对称的。

```
    1
   / \
  2   2
 / \ / \
3  4 4  3
```



##### 解法1：后续遍历，

函数签名：**public boolean lastOrder(TreeNode left,TreeNode right)**

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public boolean isSymmetric(TreeNode root) {
        if(root==null){
            return true;
        }

        return lastOrder(root.left,root.right);
    }

    //后序遍历
    public boolean lastOrder(TreeNode left,TreeNode right){
        if(left==null&&right==null){
            return true;
        }
        if(left==null||right==null){
            return false;
        }

        //能到这，左右都不为空
        boolean isSym = left.val ==right.val;
        if(isSym==false){
            return false;
        }
        //左子树的左子树与右子树的右子树在比较
        //左子树的右子树与右子树的左子树在比较
        return lastOrder(left.left,right.right)&&lastOrder(left.right,right.left);

        
    } 
}
```



## 双指针、三指针类型题目

#### [263. 丑数](https://leetcode-cn.com/problems/ugly-number/)

编写一个程序判断给定的数是否为丑数。

丑数就是只包含质因数 `2, 3, 5` 的**正整数**。

##### 解放1：暴力解 （一直除）

```java
public boolean isUgly(int num) {
        if (num<1) return false;
        while (num%5==0){
            num/=5;
        }
        while (num%3==0){
            num/=3;
        }
        while (num%2==0){
            num/=2;
        }
        return num == 1;
    }
```



#### [264. 丑数 II](https://leetcode-cn.com/problems/ugly-number-ii/)

编写一个程序，找出第 `n` 个丑数。n不超过1690

丑数就是质因数只包含 `2, 3, 5` 的**正整数**。



##### 解法1：三指针

- 题目中说n不超过1690，**设定记录丑数的数组ugly的大小多给一些**，直接给1690可能不小心越界
- 先确定ugly的值，在确定三指针是否移动 （注意可能出现同时多指针需要移动）
- 指针所指的数为ugly数组中的丑数，然后顺序向后移动

```java
class Solution {
    public int nthUglyNumber(int n) {
        //if(n<0) return new Throw("非法输入");

        int[] ugly = new int[n+1];
        int curIndex=2,aIndex=1,bIndex=1,cIndex=1;
        ugly[0] = 0 ;
        ugly[1]= 1;

        //三指针
        while(curIndex<=n){
            int a = ugly[aIndex]*2;
            int b = ugly[bIndex]*3;
            int c = ugly[cIndex]*5;

            ugly[curIndex] = Math.min(Math.min(a,b),c);
             //先确定ugly的值，在确定三指针是否移动 （注意可能出现同时多指针需要移动）
            if(ugly[curIndex]==a) aIndex++;
            if(ugly[curIndex]==b) bIndex++;
            if(ugly[curIndex]==c) cIndex++;

            curIndex++;
        }
            
        return ugly[n];
    }
}
```



#### [剑指 Offer 52. 两个链表的第一个公共节点](https://leetcode-cn.com/problems/liang-ge-lian-biao-de-di-yi-ge-gong-gong-jie-dian-lcof/)

输入两个链表，找出它们的第一个公共节点。

##### 解法1：让长的链表先走 dif（lenA，lenB），在两个指针一起走

- 缺点：代码不好写



##### 解法2：

![image-20210119165934974](C:\Users\黎先桦\Desktop\MarkDown\Leedocode题目记录.assets\image-20210119165934974.png)

```java
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        //tempA和tempB我们可以认为是A,B两个指针
        ListNode tempA = headA;
        ListNode tempB = headB;
        while(tempA!=tempB){
            // ******************
            tempA = tempA==null?headB:tempA.next;
            tempB = tempB==null?headA:tempB.next;
        }
        return tempA;
    }
}
```



#### [剑指 Offer 57 - II. 和为s的连续正数序列](https://leetcode-cn.com/problems/he-wei-sde-lian-xu-zheng-shu-xu-lie-lcof/)

难度简单203

输入一个正整数 `target` ，输出所有和为 `target` 的连续正整数序列（至少含有两个数）。

序列内的数字由小到大排列，不同序列按照首个数字从小到大排列。

 

**示例 1：**

```
输入：target = 9
输出：[[2,3,4],[4,5]]
```

**示例 2：**

```
输入：target = 15
输出：[[1,2,3,4,5],[4,5,6],[7,8]]
```

##### 解法1：滑动窗口

```java
class Solution {
    public int[][] findContinuousSequence(int target) {
        //左开右闭的区间，所有初始 leftIndex=rightIndex=1
        int leftIndex =1,rightIndex=1; 
        int sum = 0;
        List<int[]> res = new ArrayList<>();
        //左开右闭的区间
        while(leftIndex<=target/2){
            if(sum>target){
                //缩小滑动窗口
                sum-=leftIndex;
                leftIndex++;
            }else if(sum<target){
                //增大滑动窗口
                sum+=rightIndex;
                rightIndex++;
            }else{
                // 记录结果
                int[] arr = new int[rightIndex-leftIndex];
                for (int k = leftIndex; k < rightIndex; k++) {
                    arr[k-leftIndex] = k;
                }
                res.add(arr);
                //缩小滑动窗口
                sum-=leftIndex;
                leftIndex++;

            }
        }
        
    //list转化成int[][]
    return res.toArray(new int[res.size()][]);
    }
}
```



### 滑动窗口

#### [76. 最小覆盖子串](https://leetcode-cn.com/problems/minimum-window-substring/)

难度困难950

给你一个字符串 `s` 、一个字符串 `t` 。返回 `s` 中涵盖 `t` 所有字符的最小子串。如果 `s` 中不存在涵盖 `t` 所有字符的子串，则返回空字符串 `""` 。

**注意：**如果 `s` 中存在这样的子串，我们保证它是唯一的答案。

 

**示例 1：**

```
输入：s = "ADOBECODEBANC", t = "ABC"
输出："BANC"
```

解法1：滑动窗口

```java
class Solution {
    public String minWindow(String s, String t) {
        HashMap<Character,Integer> need = new HashMap();
        HashMap<Character,Integer> window = new HashMap();

        //t的每个字符进set
        for(char c:t.toCharArray()){
            need.put(c,need.getOrDefault(c,0)+1);
        }

        //双指针
        int left =0,right=0;
        int valid = 0;  //t中字符串的个数（不重复）
        // 记录最小覆盖字串的起始索引及长度
        int start =0,len=Integer.MAX_VALUE;
        while(right<s.length()){
            //扩大有窗口，进一个元素
            char c = s.charAt(right);
            right++;

            // 判断取出的字符是否在字串中
            if(need.containsKey(c)){
                window.put(c,window.getOrDefault(c,0)+1);
                if(window.get(c).equals(need.get(c))){
                    valid++; //非重复元素的个数
                }
            }

            //考虑左窗口是否需要关闭
            while(valid==need.size()){
                //更新窗口的start与len
                if(right-left<len){
                    //左闭右开
                    len = right-left;
                    start = left;
                }

                //左窗口关闭
                char leftC = s.charAt(left);
                left++;

                if(need.containsKey(leftC)){
                    //leftC如果是need中的字符时，第一次碰到就需要讲valid--；
                    if(window.get(leftC).equals(need.get(leftC))){
                        valid--;
                    }
                   window.put(leftC,window.getOrDefault(leftC,0)-1);
                }
            }
        }
        return len==Integer.MAX_VALUE?"":s.substring(start,start+len);

    }
}
```





#### [5630. 删除子数组的最大得分](https://leetcode-cn.com/problems/maximum-erasure-value/)

给你一个正整数数组 `nums` ，请你从中删除一个含有 **若干不同元素** 的子数组**。**删除子数组的 **得分** 就是子数组各元素之 **和** 。

返回 **只删除一个** 子数组可获得的 **最大得分** *。*

如果数组 `b` 是数组 `a` 的一个连续子序列，即如果它等于 `a[l],a[l+1],...,a[r]` ，那么它就是 `a` 的一个子数组。

 

**示例 1：**

```java
输入：nums = [4,2,4,5,6]
输出：17
解释：最优子数组是 [2,4,5,6]
```

##### 解法1：

> 用HashMap存下<value,index>,遇到相同值时,让i回退到index位置
>
> (HashMap处理数据没有Set快,直接回退到i有很多重复步骤)



解法2:

**双指针**:  

> - start记录不重复数字窗口的开始下标
> - i为当前遍历的位置(不重复数字窗口的右窗口)
> - 每次遇到相同数字,调整start,sum
> - 重点理解while中  start++     (直接start++将(nums[start)留在set与sum中,nums[i]通过i++也跳过了)



```java
class Solution {
    public int maximumUniqueSubarray(int[] nums) {
        //set有contains方法
        Set<Integer> set = new HashSet();
        int sum = 0;
        int max = 0;
        int start = 0;  //表示保证不重复数字爽口的开始下标
        int end = nums.length-1;

        for(int i=0;i<nums.length;i++){
            if(!set.contains(nums[i])){
                sum+=nums[i];
                set.add(nums[i]);
                //结算
                max = sum>max?sum:max;
            }else{
                //移动窗口，将set中重复窗口删除
                while(nums[start]!=nums[i]){
                    sum-=nums[start];
                    set.remove(nums[start]);
                    start++;
                }
                //此时 nums[start] == nums[i]
                //把nums[i]留在set中，所有不做处理，
                //直接start++将(nums[start)留在set与sum中,nums[i]通过i++也跳过了
                start++;
            }
        }

        return max;
    }
}
```





#### [1438. 绝对差不超过限制的最长连续子数组](https://leetcode-cn.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/)

难度中等107

给你一个整数数组 `nums` ，和一个表示限制的整数 `limit`，请你返回最长连续子数组的长度，该子数组中的任意两个元素之间的绝对差必须小于或者等于 `limit` *。*

如果不存在满足条件的子数组，则返回 `0` 。



##### 解法1：滑动窗口+双端队列

- **滑动窗口**用于计算

- **双端队列**Q(1)获取窗口中最大值与最小值

```java
class Solution {
      public int longestSubarray(int[] nums, int limit) {
        if(nums==null||nums.length==0) return 0;
        int resLen=0,len=nums.length;
        int left=0,right=0;
        //双端队列
        Deque<Integer> qMax = new ArrayDeque<>(); //降序
        Deque<Integer> qMin = new ArrayDeque<>(); //升序
        //滑动窗口
        while(right<len){
            int rightNum = nums[right];
            right++;
			
            //qMax首元素为窗口中最大元素，逆序
            //进qMax。需要与qMax尾元素比较，而不是与头比较
            while(!qMax.isEmpty()&&qMax.peekLast()<rightNum){
                qMax.removeLast();
            }
             //qMin首元素为窗口中最小元素，升序
            while(!qMin.isEmpty()&&qMin.peekLast()>rightNum){
                qMin.removeLast();
            }

            qMax.add(rightNum);
            qMin.add(rightNum);

            //左窗口
            while(Math.abs(qMax.peek()-qMin.peek())>limit){
                int leftNum = nums[left];
                left++;
                if(qMax.peek()==leftNum){
                    qMax.poll();
                }
                if(qMin.peek()==leftNum){
                    qMin.poll();
                }
            }
            resLen = Math.max(resLen,right-left);
        }

        return resLen;
    }
}
```





#### [406. 根据身高重建队列](https://leetcode-cn.com/problems/queue-reconstruction-by-height/)

难度中等772

假设有打乱顺序的一群人站成一个队列，数组 `people` 表示队列中一些人的属性（不一定按顺序）。每个 `people[i] = [hi, ki]` 表示第 `i` 个人的身高为 `hi` ，前面 **正好** 有 `ki` 个身高大于或等于 `hi` 的人。

请你重新构造并返回输入数组 `people` 所表示的队列。返回的队列应该格式化为数组 `queue` ，其中 `queue[j] = [hj, kj]` 是队列中第 `j` 个人的属性（`queue[0]` 是排在队列前面的人）。

 



**示例 1：**

```
输入：people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]
输出：[[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]
解释：
编号为 0 的人身高为 5 ，没有身高更高或者相同的人排在他前面。
编号为 1 的人身高为 7 ，没有身高更高或者相同的人排在他前面。
编号为 2 的人身高为 5 ，有 2 个身高更高或者相同的人排在他前面，即编号为 0 和 1 的人。
编号为 3 的人身高为 6 ，有 1 个身高更高或者相同的人排在他前面，即编号为 1 的人。
编号为 4 的人身高为 4 ，有 4 个身高更高或者相同的人排在他前面，即编号为 0、1、2、3 的人。
编号为 5 的人身高为 7 ，有 1 个身高更高或者相同的人排在他前面，即编号为 1 的人。
因此 [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]] 是重新构造后的队列。
```

##### 解法1：

- 1.排序规则：按照先H高度降序，K个数升序排序
- 2.遍历排序后的数组，根据K插入到K的位置上
- LinkedList.add(index,value)方法   

```java
class Solution {
    public int[][] reconstructQueue(int[][] people) {
        //1.排序规则：按照先H高度降序，K个数升序排序
        //2.遍历排序后的数组，根据K插入到K的位置上

        //核心思想：高个子先站好位，矮个子插入到K位置上，前面肯定有K个高个子，矮个子再插到前面也满足K的要求
        Arrays.sort(people,(o1,o2)->(o1[0]==o2[0]?o1[1]-o2[1]:o2[0]-o1[0]));

        List<int[]> res = new LinkedList();
        for(int[] p:people){
            res.add(p[1],p); //将p插入到res的第p[1]个位置
        }

        return res.toArray(new int[res.size()][2]);
    }
}
```



## 查找

### 二分查找

#### [33. 搜索旋转排序数组](https://leetcode-cn.com/problems/search-in-rotated-sorted-array/)

给你一个升序排列的整数数组 `nums` ，和一个整数 `target` 。

假设按照升序排序的数组在预先未知的某个点上进行了旋转。（例如，数组 `[0,1,2,4,5,6,7]` 可能变为 `[4,5,6,7,0,1,2]` ）。

请你在数组中搜索 `target` ，如果数组中存在这个目标值，则返回它的索引，否则返回 `-1` 。

**示例 1：**

```
输入：nums = [4,5,6,7,0,1,2], target = 0
输出：4
```

**示例 2：**

```
输入：nums = [4,5,6,7,0,1,2], target = 3
输出：-1
```

**示例 3：**

```
输入：nums = [1], target = 0
输出：-1
```

 



##### 解法1：部分有序的二分查找

- **题目的这种数组，mid的左右区间一定有一个是有序的**

- **先判断那边有序，在判断target在不在有序区间内 **  

  （不知道区间是否有序，无法判断target在不在区间中。有序的话比较首尾即可判断）

- *****如何target不在有序区间内，改变l或r，**让新的更小的区间又变成题目中部分有序数组的状态**  （把大问题，变成同样的小问题）

- target在无序的部分，改变的区间，数组依旧是部分有序，再按原方法处理

- **二分查找  while条件有等号，最后一个else 无if**

```java
class Solution {
    public int search(int[] nums, int target) {    
        if(nums==null||nums.length==0) return -1;

        int left =0, right = nums.length-1;
        while(left<right){
            int mid = (left+right)>>1;
            if(nums[mid]==target){
                return mid;
            }
            else if(nums[mid]<nums[right]){
                //右有序
                if(target<=nums[right]&&target>nums[mid]){
                    left = mid+1;
                }else{
                    right = mid -1;
                }
            }else{
                //左有序
                if(target>=nums[left]&&target<nums[mid]){
                    right = mid-1;
                }else{
                    left =mid +1;
                }
            }
        }
        if(nums[left]==target){
            return left;
        }else{
            return -1;
        }
       
    }
}
```



### 特殊查找 

#### [剑指 Offer 11. 旋转数组的最小数字](https://leetcode-cn.com/problems/xuan-zhuan-shu-zu-de-zui-xiao-shu-zi-lcof/)

把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。例如，数组 `[3,4,5,1,2]` 为 `[1,2,3,4,5]` 的一个旋转，该数组的最小值为1。 

**示例 1：**

```
输入：[3,4,5,1,2]
输出：1
```

**示例 2：**

```
输入：[2,2,2,0,1]
输出：0
```

##### 解法1：基于HashMap与双向链表

- 构造双向链表，HashMap 存<key,双向链表的Node>
- **有虚头节点与尾节点，方便操作**
- 写几个方法：`moveTohead`、`removeNode`、`addToHead`、`removeTail`
- 对于已有元素，每次get、put都要把这个元素节点放到head。超过容量就removeTail

```java
public class LRUCache {
    class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;
        public DLinkedNode() {}
        public DLinkedNode(int _key, int _value) {key = _key; value = _value;}
    }

    private Map<Integer, DLinkedNode> cache = new HashMap<Integer, DLinkedNode>();
    private int size;
    private int capacity;
    private DLinkedNode head, tail;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        // 使用伪头部和伪尾部节点
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        // 如果 key 存在，先通过哈希表定位，再移到头部
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            // 如果 key 不存在，创建一个新的节点
            DLinkedNode newNode = new DLinkedNode(key, value);
            // 添加进哈希表
            cache.put(key, newNode);
            // 添加至双向链表的头部
            addToHead(newNode);
            ++size;
            if (size > capacity) {
                // 如果超出容量，删除双向链表的尾部节点
                DLinkedNode tail = removeTail();
                // 删除哈希表中对应的项
                cache.remove(tail.key);
                --size;
            }
        }
        else {
            // 如果 key 存在，先通过哈希表定位，再修改 value，并移到头部
            node.value = value;
            moveToHead(node);
        }
    }

    private void addToHead(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }

    private DLinkedNode removeTail() {
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }
}

```









##### 解法1：部分有序的二分查找   （二分查找+暴力遍历）（时间复杂度最差为O(N)，当数组中全为一个数）

- 这道题的数组中可存在重复值
- 特殊情况：没旋转、数组中所有值相同、数组中有多个最小值
- （**时间复杂度最差**为O(N)，当数组中全为一个数）



#####   这道题与一般二分不同的是：

![image-20201128124639657](C:\Users\黎先桦\Desktop\MarkDown\Leedocode题目记录.assets\image-20201128124639657.png)





```java
class Solution {
    int[] numbers;
    public int minArray(int[] numbers) {
        this.numbers = numbers;
        //异常判断
        if(numbers==null||numbers.length==0) return -1;

        //未旋转，直接返回数组第一个数
        if(numbers[0]<numbers[numbers.length-1]) return numbers[0];

        //二分
        return binarySearch(0,numbers.length-1);
    }
    
    
    //一直找无序的段
    public int binarySearch(int left,int right){
        while(left<right){
            int mid = (left+right)>>1;
            //右无序，目标区间  mid+1 -- right  （mid不可能是最小值）    
            if(numbers[mid]>numbers[right]){
                left = mid+1;
            }
             //左无序，目标区间  left -- mid  （mid也可能是最小值）
            else if (numbers[mid] < numbers[right]){
                right = mid;
            }
            //不知道那边无序无序，目标区间  left -- right--  
            //（numbers[mid] == numbers[right]，right--不影响）
            else if (numbers[mid] == numbers[right]){
                right--;
            }
        }

		//退出循环时，left==right
        //这里结果一定存在在数组中，即使while没有使用==，也不要判断left的是否是正确结果
        return numbers[left];
    }
}
```

![image-20201128124258040](C:\Users\黎先桦\Desktop\MarkDown\Leedocode题目记录.assets\image-20201128124258040.png)

![image-20201128124428967](C:\Users\黎先桦\Desktop\MarkDown\Leedocode题目记录.assets\image-20201128124428967.png)

![image-20201128124639657](C:\Users\黎先桦\Desktop\MarkDown\Leedocode题目记录.assets\image-20201128124639657.png)

![image-20201128124745872](C:\Users\黎先桦\Desktop\MarkDown\Leedocode题目记录.assets\image-20201128124745872.png)



#### [35. 搜索插入位置](https://leetcode-cn.com/problems/search-insert-position/)

难度简单831

给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。

你可以假设数组中无重复元素。

**示例 1:**

```
输入: [1,3,5,6], 5
输出: 2
```

**解法1：**二分法  （ 左开右闭型）

- right初始值为nums.length
- 当nums[mid]>target时， right = mid

```java
class Solution {
    public int searchInsert(int[] nums, int target) {
        //二分法  左开右闭  [left,right)  (相当于 mid = (left+right+1)/2  )
        int left =0,right = nums.length;
        while(left<right){
            int mid = left+(right-left)/2;   //左开右闭  [left,right)  (相当于 mid = (left+right+1)/2  )
            if(nums[mid]==target){
                return mid;
            }else if(nums[mid]>target){
                right = mid; //[left,right)
            }else{
                left = mid+1;
            }
        }

        return left;
    }
}
```





#### [34. 在排序数组中查找元素的第一个和最后一个位置](https://leetcode-cn.com/problems/find-first-and-last-position-of-element-in-sorted-array/)

难度中等798

给定一个按照升序排列的整数数组 `nums`，和一个目标值 `target`。找出给定目标值在数组中的开始位置和结束位置。

如果数组中不存在目标值 `target`，返回 `[-1, -1]`。

**进阶：**

- 你可以设计并实现时间复杂度为 `O(log n)` 的算法解决此问题吗？

 

**示例 1：**

```
输入：nums = [5,7,7,8,8,10], target = 8
输出：[3,4]
```

##### 解法1：二分法

- findFirstPosition找到目标出现的第一个位置 **（有可能没有）**
- findLastPosition找到目标的最后一个位置，需要一直向右找，**int mid = (left+ right+1)>>1**;   **mid需要向上取整**
- 当二分法的**while循环没有等号时，退出循环时，left==right  需要对出循环的结果进行二次判断**



```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int[] res = {-1,-1};
        if(nums==null||nums.length==0)return res;
        int first = findFirstPosition(nums,target);
        if(first==-1){
            return res;
        }
        int last = findLastPosition(nums,target);
        return new int[]{first,last};
    }
	
    //找到目标出现的第一个位置 （有可能没有）
    private int findFirstPosition(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = (right + left) / 2;
            // 小于一定不是解
            if (nums[mid] < target) {
                // 下一轮搜索区间是 [mid + 1, right]
                left = mid + 1; 
            } else if (nums[mid] == target) {
                // 下一轮搜索区间是 [left, mid]
                right = mid;  
            } else {
                // nums[mid] > target，下一轮搜索区间是 [left, mid - 1]
                right = mid - 1;
            }
        }

        //判断left因为 while语句中不是等于号，所以left才是结果值（ 退出循环时left == right）
        //因为nums中可能没有target，要判断一下二分的结果是不是正确的
        if (nums[left] == target) {
            return left;
        }
        return -1;
    }
    
    //找到目标的最后一个位置
    public int findLastPosition(int[] nums, int target){
            int left =  0;
            int right = nums.length-1;
            while(left<right){
                //向上取整
                int mid = (left+ right+1)>>1;
                if(nums[mid]==target){
                    left = mid;
                }else if(nums[mid]>target){
                    right  = mid-1;
                }else{
                    left = mid+1;
                }
            }

            if (nums[left] == target) {
                return left;
            }
            return -1;
        }

}
```



## 排序

### 堆排

#### [973. 最接近原点的 K 个点](https://leetcode-cn.com/problems/k-closest-points-to-origin/)

我们有一个由平面上的点组成的列表 `points`。需要从中找出 `K` 个距离原点 `(0, 0)` 最近的点。

（这里，平面上两点之间的距离是欧几里德距离。）

你可以按任何顺序返回答案。除了点坐标的顺序之外，答案确保是唯一的。

**示例 1：**

```
输入：points = [[1,3],[-2,2]], K = 1
输出：[[-2,2]]
解释： 
(1, 3) 和原点之间的距离为 sqrt(10)，
(-2, 2) 和原点之间的距离为 sqrt(8)，
由于 sqrt(8) < sqrt(10)，(-2, 2) 离原点更近。
我们只需要距离原点最近的 K = 1 个点，所以答案就是 [[-2,2]]。
```

**示例 2：**

```
输入：points = [[3,3],[5,-1],[-2,4]], K = 2
输出：[[3,3],[-2,4]]
（答案 [[-2,4],[3,3]] 也会被接受。）
```



##### 解法1：PriorityQueue做堆  （堆长不设n，设为k）

- PriorityQueue+比较器能实现堆的效果

- 比较器一定要用泛型规定好数据类型

- **这道题，只让堆管理K个元素，再让points剩下元素来替换堆中K个元素，节省了时间与空间**

  

```java
public int[][] kClosest(int[][] points, int K) {
        //dis index
        //使用带比较器参数的构造器
        PriorityQueue<int[]> pq = new PriorityQueue(new Comparator<int[]>() {
            @Override
            public int compare(int[] array1, int[] array2) {
                //从大到小
                return array2[0]-array1[0];
            }
        });
        //points前k个元素入堆
        for(int i=0;i<K;i++){
            pq.add(new int[]{points[i][0] * points[i][0] + points[i][1] * points[i][1], i});
        }
        //用points剩下元素来替换堆中K个元素
        for(int i=K;i< points.length;i++){
            int curDis = points[i][0] * points[i][0] + points[i][1] * points[i][1];
            if(curDis <=pq.peek()[0]){
                pq.poll(); //堆底（堆头）删除
                pq.add(new int[]{curDis, i});
            }
        }
        int[][] res = new int[K][2];
        for(int i=0;i<K;i++){
            res[i] = points[pq.poll()[1]];
        }
        return res;
    }
```



#### [215. 数组中的第K个最大元素](https://leetcode-cn.com/problems/kth-largest-element-in-an-array/)

难度中等914

在未排序的数组中找到第 **k** 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。

**示例 1:**

```
输入: [3,2,1,5,6,4] 和 k = 2
输出: 5
```



##### 解法1：小顶堆

-  PriorityQueue<Integer> pq = new PriorityQueue<Integer>();   **默认小顶堆**
-  PriorityQueue<Integer> pq = new PriorityQueue<Integer>(（a,b）->(b-a));   **大顶堆**

```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        //小顶堆,容量为K  （默认小顶堆）
        // PriorityQueue<Integer> pq = new PriorityQueue<Integer>(（a,b）->(b-a));   大顶堆
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();

        //先进堆K个元素
        for(int i =0;i<k;i++){
            pq.add(nums[i]);
        } 

        for(int i=k;i<nums.length;i++){
            pq.add(nums[i]);
            pq.poll();
        }
        return pq.peek();
    }
}
```



##### 解法2：快排法，每一趟快排能将一个元素放在正确的位置

- **快排指针移动有等号 **  `while(left<right&&patition[right]>=temp) right--;`

```java
class Solution {
    public int findKthLargest(int[] nums, int k) {
        int len = nums.length;
        int targetIdex = len-k;  //查找第target小的元素
        int left =0,right=len-1; //左闭右闭的查找区间

        //开始快排
        while(left<right){
            int resultIndex = quickSort(nums,left,right);
            if(resultIndex==targetIdex){
                return nums[targetIdex];
            }else if(resultIndex>targetIdex){
                right = resultIndex-1;
            }else{
                left = resultIndex+1;
            }
        }
        return nums[left];
    }

    public int  quickSort(int[] patition,int left,int right){
        int temp = patition[left];
        while(left<right){
            //右指针移动
            while(left<right&&patition[right]>=temp) right--;
            patition[left] = patition[right];

             //左指针移动
            while(left<right&&patition[left]<=temp) left++;
            patition[right] = patition[left];
        }
        patition[left] = temp;
        return left;
    }

}
```



#### [剑指 Offer 45. 把数组排成最小的数](https://leetcode-cn.com/problems/ba-shu-zu-pai-cheng-zui-xiao-de-shu-lcof/)

难度中等139

输入一个非负整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。

 

**示例 1:**

```
输入: [10,2]
输出: "102"
```

**示例 2:**

```
输入: [3,30,34,5,9]
输出: "3033459"
```

 

##### 解法1：

- 使用贪心算法   当 x+y  < y+x 时，在最终的拼接字符串中 x应该出现在y的前面
- 根据上诉，对nums中每个数字转化成str，依据x+y与y+x比较，对str[]进行排序
- 两个str的大小比较直接使用compareTo方法

```java
class Solution {
    public String minNumber(int[] nums) {
        int len = nums.length;
        String[] str = new String[len];
        //将nums中每个数字转化成str类型
        for(int i=0;i<len;i++){
            str[i] = String.valueOf(nums[i]);
        }
        QuickSortString(str,0,len-1);
        
        //str数组已经排好序，一次append拼接的字符串就是最小字符串
        StringBuilder res = new StringBuilder();
        for(String s : str)
            res.append(s);
        return res.toString();


    }

    //一趟快排
    public int partition(String[] str,int start,int end){
        String temp =str[start];
        while(start<end){
            while(start<end&&(str[end]+temp).compareTo(temp+str[end])>=0) end--;
            str[start] = str[end];

            while(start<end&&(str[start]+temp).compareTo(temp+str[start])<=0) start++;
            str[end] = str[start];
        }
        str[start] = temp;
        return start;
    }
	
    //递归快排
    public void QuickSortString(String[] str,int start,int end){
        if(start<end){
            int pos = partition(str,start,end);
            QuickSortString(str,start,pos-1);
            QuickSortString(str,pos+1,end);
        }
    }
}
```



### 归并排序

#### [剑指 Offer 51. 数组中的逆序对](https://leetcode-cn.com/problems/shu-zu-zhong-de-ni-xu-dui-lcof/)

在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组，求出这个数组中的逆序对的总数。

 

**示例 1:**

```
输入: [7,5,6,4]
输出: 5
```

#####  解法1：归并排序

![image-20210117172520059](C:\Users\lenovo\Desktop\MarkDown\Leedocode题目记录.assets\image-20210117172520059.png)

![image-20210117172549327](C:\Users\lenovo\Desktop\MarkDown\Leedocode题目记录.assets\image-20210117172549327.png)

- 归并时候，如果nums[i]>nums[j]。对于nums[j]而言，逆序对就有**mid-i+1**个
- **mid = left+(right-left)/2 避免溺出**

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

			//合并两端  （当成一个后续遍历，左右都有序，在合并）
            int mergeCount = merge(nums,left,right,temp);
			
            //返回左右段的逆序对，与合并的逆序对
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



### 桶排序

#### [347. 前 K 个高频元素](https://leetcode-cn.com/problems/top-k-frequent-elements/)

难度中等626

给定一个非空的整数数组，返回其中出现频率前 ***\*** 高的元素。

 

**示例 1:**

```
输入: nums = [1,1,1,2,2,3], k = 2
输出: [1,2]
```

**示例 2:**

```
输入: nums = [1], k = 1
输出: [1]
```

**解法1：桶排序**     **时间复杂度**：O(n)

![image-20210203211701077](C:%5CUsers%5C%E9%BB%8E%E5%85%88%E6%A1%A6%5CDesktop%5CMarkDown%5CLeedocode%E9%A2%98%E7%9B%AE%E8%AE%B0%E5%BD%95.assets%5Cimage-20210203211701077.png)

```java
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        //有多个相同频率的数怎么办  
        //HashMap<value,count>
        HashMap<Integer,Integer> hashMap = new HashMap();
        for(int i=0;i<nums.length;i++){
            if(hashMap.containsKey(nums[i])){
                hashMap.put(nums[i],hashMap.get(nums[i])+1);
            }else{
                hashMap.put(nums[i],1);
            }
        }



        //2.桶排序  元素出现的个数为桶的序号  
        ArrayList<Integer>[] buckts = new ArrayList[nums.length+1];
        Set<Map.Entry<Integer, Integer>> entries = hashMap.entrySet();
        for(Map.Entry<Integer,Integer> entry:entries ){
            int num = entry.getKey(), count = entry.getValue();
            if(buckts[count]==null){
                buckts[count] = new ArrayList<Integer>();
            }
            buckts[count].add(num);
        }

        //桶中取数
        int[] res = new int[k];
        int index= 0;
        //从后往前取，（桶中越后面的数，表示出现频率越高）
        for(int i=nums.length;i>0;i--){
            if(buckts[i]!=null){
                for(int j =0;j<buckts[i].size();j++){
                    if(index<k){
                        res[index++] = buckts[i].get(j);
                    }else{
                        return res;
                    }
                }
            }
        }
        return res;
    }
}
```





## 移位或位运算

#### [剑指 Offer 16. 数值的整数次方](https://leetcode-cn.com/problems/shu-zhi-de-zheng-shu-ci-fang-lcof/)

**示例 1:**

```
输入: 2.00000, 10
输出: 1024.00000
```

**示例 2:**

```
输入: 2.10000, 3
输出: 9.26100
```

**示例 3:**

```
输入: 2.00000, -2
输出: 0.25000
解释: 2-2 = 1/22 = 1/4 = 0.25
```

**本题有个细节：当n=- Integer.MIN时， 对n = -n 操作会出现异常** 

**（对输入的数为int类型时，要考虑int的上界与下界的特殊情况）**



##### 解法1：**二进制快速降幂 **    时间复杂度 O（lon(n)）  （lon(n)为n的二进制的长度）

- 用long类型的 exp代替int 类型的n，避免去负号时的异常
- 对exp进行`&`操作     **(exp&1)查看exp的二进制下最右边一位是不是1**
- 时间复杂度 O（lon(n)）  （lon(n)为n的二进制的长度）

```java
class Solution {
    public double myPow(double x, int n) {
        if(n==0)return 1.0;
        if(x==1.0) return 1.0;

        double res = 1.0;
        long exp = n;  // 当n取Integer.min时，-n会出现数组溢出
        //正负数
        if(exp<0){
            exp = -exp;
            x = 1/x;
        }
		
        //二进制快速降幂
        while(exp>0){
            //(exp&1)查看exp的二进制下最右边一位是不是1
            if((exp&1)==1) res*=x;
            x*=x; 
            exp >>=1;
        }
        return res;
    
    }
}
```



##### 解法2：非递归除2快速降幂法

- 当exponent是0的时候，直接返回1即可，


- 当exponent小于0的时候，需要把它转化为正数才能更方便计算，同时base要变为1/base。


- 当exponent大于0的时候要分为两种情况，一种是偶数，一种是奇数。


- **1，** 如果exponent是偶数我们只需要计算Power(base*base, exponent/2)。举个例子，比如我们要计算Power（3，8），我们可以改为Power（3*3，8/2），也就是Power（9，4）。

- **2，** 如果exponent是奇数，我们只需要计算base*Power(base*base, exponent/2)，比如Power（3，9），我们只需要计算3*Power（3*3，9/2），也就是3*Power（9，4）。



```java
class Solution {
    public double myPow(double x, int n) {
        if(n==0)return 1.0;
        if(x==1.0) return 1.0;

        double res = 1.0;
        long exp = n;  // 当n取Integer.min时，-n会出现数组溢出
        //正负数的处理
        if(exp<0){
            exp = -exp;
            x = 1/x;
        }


        //降幂法   （exp每次/2，最终会为0）
        while(exp>0){
            if(exp%2==1){  //（当exp为奇数或者exp==1时）
                exp--;
                res*=x;
            }
            x*=x; // （x指数级别的提高）
            exp/=2; //降幂
        }
        return res;
    
    }
}
```

##### 解法3：

```java
class Solution {
    public double myPow(double x, int n) {
        if(n==0)return 1.0;
        if(x==1.0) return 1.0;

        double res = 1.0;
        long exp = n;  // 当n取Integer.min时，-n会出现数组溢出
        //正负数的处理
        if(exp<0){
            exp = -exp;
            x = 1/x;
        }


		//递归除2快速降幂法
        return  DIGUImyPow(x,exp);
    
    }

    public double DIGUImyPow(double x,long exp){
        if(exp==1){
            return x;
        }
        //根据x是奇数还是偶数来做不同的递归处理
        return exp%2==0?DIGUImyPow(x*x,exp/2):x*DIGUImyPow(x*x,exp/2);
    }
}
```



#### [剑指 Offer 56 - I. 数组中数字出现的次数](https://leetcode-cn.com/problems/shu-zu-zhong-shu-zi-chu-xian-de-ci-shu-lcof/)

难度中等289

一个整型数组 `nums` 里除两个数字之外，其他数字都出现了两次。请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。

 

**示例 1：**

```
输入：nums = [4,1,4,6]
输出：[1,6] 或 [6,1]
```

**示例 2：**

```
输入：nums = [1,2,10,4,1,4,3,3]
输出：[2,10] 或 [10,2]
```



#### [剑指 Offer 65. 不用加减乘除做加法](https://leetcode-cn.com/problems/bu-yong-jia-jian-cheng-chu-zuo-jia-fa-lcof/)

写一个函数，求两个整数之和，要求在函数体内不得使用 “+”、“-”、“*”、“/” 四则运算符号。 

**示例:**

```
输入: a = 1, b = 1
输出: 2
```

```java
class Solution {
    public int add(int a, int b) {
        while(b != 0){
            //保存进位值，下次循环用
           int c = (a & b) << 1;  // c = 进位
           //保存不进位值，下次循环用，
           a ^= b; // a = 非进位和
           //如果还有进位，再循环，如果没有，则直接输出没有进位部分即可
           b = c; // b = 进位

        }
        return a;
    }
}
```



#### [338. 比特位计数](https://leetcode-cn.com/problems/counting-bits/)

难度中等500

给定一个非负整数 **num**。对于 **0 ≤ i ≤ num** 范围中的每个数字 **i** ，计算其二进制数中的 1 的数目并将它们作为数组返回。

**示例 1:**

```
输入: 2
输出: [0,1,1]
```



##### 解法1：dp

```java
class Solution {
    public int[] countBits(int nums) {
        int[] dp = new int[nums+1];
        dp[0] = 0;
        for(int i=1;i<=nums;i++){
            if(i%2==1){
                //奇数
                dp[i]=dp[i-1]+1;
            }else{
                //偶数
                dp[i]=dp[i/2];
            }
        }

        return dp;
    }
}
```



#### [剑指 Offer 41. 数据流中的中位数](https://leetcode-cn.com/problems/shu-ju-liu-zhong-de-zhong-wei-shu-lcof/)

##### 解法1：双堆排序法

- 通过两个堆对序列进行排序，方便找到中位数

![image-20210103121858361](C:\Users\lenovo\Desktop\MarkDown\Leedocode题目记录.assets\image-20210103121858361.png)

```java
class MedianFinder {
    //A为大根堆，保存有序序列的前半段，根为前半段最大值
     //B为小根堆，保存有序序列的后半段，根为后端的最小值
    PriorityQueue<Integer> A;
    PriorityQueue<Integer> B;
    /** initialize your data structure here. */
    public MedianFinder() {
        A  = new PriorityQueue<Integer>((a,b)->b-a); 
        B = new PriorityQueue<Integer>();           
    }
    
    public void addNum(int num) {
        if(A.size()==B.size()){
            //插入B，调整后再给A   
            //（这里先插入A还是先插入B无所谓，保持一致就行）
            B.add(num);
            A.add(B.poll());
        }else{
            //A中元素偏多，插入A，调整后再给B
            A.add(num);
            B.add(A.poll());
        }
    }
    
    public double findMedian() {
        if(A.size()==B.size()){
            return (A.peek()+B.peek())/2.0;
        }else{
            return A.peek();
        }
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
```



## 动态规划

#### [62. 不同路径](https://leetcode-cn.com/problems/unique-paths/)

难度中等925收藏分享切换为英文接收动态反馈

一个机器人位于一个 `m x n` 网格的左上角 （起始点在下图中标记为 “Start” ）。

机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。

问总共有多少条不同的路径？

 

**示例 1：**

![img](https://assets.leetcode.com/uploads/2018/10/22/robot_maze.png)

```
输入：m = 3, n = 7
输出：28
```



##### 解法1：DP

```java
class Solution {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        //边界都设为1，算一次有效路径
        for (int i = 0; i < n; i++) dp[0][i] = 1;
        for (int i = 0; i < m; i++) dp[i][0] = 1;

        //dp状态转移方程：
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];  
    }
}
```



#### [剑指 Offer 14- I. 剪绳子](https://leetcode-cn.com/problems/jian-sheng-zi-lcof/)

给你一根长度为 `n` 的绳子，请把绳子剪成整数长度的 `m` 段（m、n都是整数，n>1并且m>1），每段绳子的长度记为 `k[0],k[1]...k[m-1]` 。请问 `k[0]*k[1]*...*k[m-1]` 可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。

**示例 1：**

```
输入: 2
输出: 1
解释: 2 = 1 + 1, 1 × 1 = 1
```

**示例 2:**

```
输入: 10
输出: 36
解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36
```

##### 解法1：动态规划

- 求长度为n绳子剪开最大值------》对长度为n的绳子进行剪短，分成长度j与n-j，如果能知道长度为n-i的绳子的剪开最大值即可
- 核心思想：n的最优解依赖于n-i的最优解，用res数组记录从0-n的最优解

```java
class Solution {
    public int cuttingRope(int n) {
        if(n==2) return 1;
        if(n==3) return 2;

        int[] res = new int[n+1];
        //绳子长度为3以下时，直接出答案
        res[0]=0;res[1]=1;res[2]=2;res[3]=3;
        //绳子长度从4开始，需要依赖之前的结果
        for(int i=4;i<=n;i++){ //构建res数组，记录n之前每个长度的最优解值
            int max = -1;
            //对长度为i的绳子进行剪短，分成长度j与i-j,j可以取多个
            //如 i=5， j可以等于1，等于2   5=1+4  5=2+3   （j>i/2时是重复的  5=2+3  5=3+2重复）
            for(int j=1;j<=i/2;j++){ 
                res[i] = res[j]*res[i-j];
                max = res[i]>max?res[i]:max;
            }
            //j取多个值算的长度i的绳子的最优解值
            res[i] =max;
        }
        //res[n]依赖前面的最优解值，得到自己的最优解值
        return res[n];
    }
}
```





#### [139. 单词拆分](https://leetcode-cn.com/problems/word-break/)

难度中等885收藏分享切换为英文接收动态反馈

给定一个**非空**字符串 *s* 和一个包含**非空**单词的列表 *wordDict*，判定 *s* 是否可以被空格拆分为一个或多个在字典中出现的单词。

**说明：**

- 拆分时可以重复使用字典中的单词。
- 你可以假设字典中没有重复的单词。

**示例 1：**

```
输入: s = "leetcode", wordDict = ["leet", "code"]
输出: true
解释: 返回 true 因为 "leetcode" 可以被拆分成 "leet code"。
```



**解法1：DP法**

- 遍历s中所有区间，看区间的字符串是否在wordDict
- 利用DP[strLen+1]
- **DP[i]==true 表示s中前i-1个字符已经存在wordDict中了**

```java
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        if(s==null||s.length()==0) return true;
        int strLen = s.length();
        //dp[i]表示str中前i-1的字符串能否被wordDict组成
        boolean[] dp = new boolean[strLen+1];
        dp[0] = true;
        for(int i =1;i<=strLen;i++){
            //j从0开始
            //j不能到i 因为dp[i]还不知道，需要dp[j]来求，j到i了不就变成自己求自己
            for(int j=0;j<i;j++){
                if(dp[j]==true&&wordDict.contains(s.substring(j,i))){
                    dp[i]=true;
                    
                }
            }
        }
        return dp[strLen];
    }
}
```



#### [剑指 Offer 46. 把数字翻译成字符串](https://leetcode-cn.com/problems/ba-shu-zi-fan-yi-cheng-zi-fu-chuan-lcof/)

给定一个数字，我们按照如下规则把它翻译为字符串：0 翻译成 “a” ，1 翻译成 “b”，……，11 翻译成 “l”，……，25 翻译成 “z”。一个数字可能有多个翻译。请编程实现一个函数，用来计算一个数字有多少种不同的翻译方法。

**示例 1:**

```
输入: 12258
输出: 5
解释: 12258有5种不同的翻译，分别是"bccfi", "bwfi", "bczi", "mcfi"和"mzi"
```

##### 解法1：动态规划

- 这道题类似`斐波那契数列`的爬楼梯次数问题
- **区别：**有些情况是非法的  如`05`不能翻译，`50`不能翻译

![image-20210109152602836](C:\Users\lenovo\Desktop\MarkDown\Leedocode题目记录.assets\image-20210109152602836.png)



```java
class Solution {
    public int translateNum(int num) {
        String str = String.valueOf(num);
        int len =str.length();
        
        //这道题有res1，res2，resNow记录状态即可
        //让res1等于1有原因的，当i=1时，如果可以结合dp[2]一定等于2，让res1==1，就可以统一操作
        int res1 = 1,res2 = 1,resNow = 1;

        for(int i=1;i<len;i++){
            //当前位i能不能与i-1组合起来翻译
            if(str.charAt(i-1)=='0'||str.substring(i-1,i+1).compareTo("25")>0){
                //当前位i不能与i-1组合起来翻译,当前位i的次数等于位i-1
                resNow = res2;
            }else{
                //当前位i能与i-1组合起来翻译
                resNow = res1+res2;
            }
            //指针移动
            res1 = res2;
            res2 = resNow;
        }

        return resNow;
    }
}
```

##### 解法2：DFS

- DFS方法解决非树的问题，需要反向考虑，从最后反向输出到最前。

```java
/dfs方法
public int translateNum(int num){
    String s = String.valueOf(num);
    return dfs(s, 0);
}

public int dfs(String str,int index){
    if(index == str.length()){
        return 1;
    }
    if(index==str.length()-1||str.charAt(index)=='0'||str.substring(index,index+2).compareTo("25")>0){
        return dfs(str,index+1);
    }

    return dfs(str,index+1)+dfs(str,index+2);
}
```





#### [剑指 Offer 47. 礼物的最大价值](https://leetcode-cn.com/problems/li-wu-de-zui-da-jie-zhi-lcof/)

在一个 m*n 的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于 0）。你可以从棋盘的左上角开始拿格子里的礼物，并每次向右或者向下移动一格、直到到达棋盘的右下角。给定一个棋盘及其上面的礼物的价值，请计算你最多能拿到多少价值的礼物？

 

**示例 1:**

```
输入: 
[
  [1,3,1],
  [1,5,1],
  [4,2,1]
]
输出: 12
解释: 路径 1→3→5→2→1 可以拿到最多价值的礼物
```



##### 解法2：DP法

![image-20210115223239946](C:\Users\lenovo\Desktop\MarkDown\Leedocode题目记录.assets\image-20210115223239946.png)





```java
class Solution {
    public int maxValue(int[][] grid) {
        int[][] DP = new int[grid.length][grid[0].length];
        for(int i=0;i<DP.length;i++){
            for(int j = 0;j<DP[0].length;j++){
                DP[i][j] = dpVal(grid,DP,i,j);
            }
        }

        return DP[grid.length-1][grid[0].length-1];
    }


    //动态规划法
    public int dpVal(int[][] grid, int[][] dp,int i,int j){
        if(i==0&&j==0){
            return grid[0][0];
        }else if(i!=0&&j==0){
            return grid[i][j]+dp[i-1][j];
        }else if(j!=0&&i==0){
            return grid[i][j]+dp[i][j-1];
        }else{
            return grid[i][j]+Math.max(dp[i-1][j],dp[i][j-1]);
        }
    }


}
```





#### [198. 打家劫舍](https://leetcode-cn.com/problems/house-robber/)

难度中等1256

你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，**如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警**。

给定一个代表每个房屋存放金额的非负整数数组，计算你 **不触动警报装置的情况下** ，一夜之内能够偷窃到的最高金额。

 

**示例 1：**

```c
输入：[1,2,3,1]
输出：4
解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
     偷窃到的最高金额 = 1 + 3 = 4 。
```



##### 解法1：DP

- dp[i] =  max(dp[i-1],dp[i-2]+nums[i]) 

```java
class Solution {
    public int rob(int[] nums) {
        //dp[i] =  max(dp[i-1],dp[i-2]+nums[i])  
        //在i处不偷 得dp[i-2] 
        //在i处偷  只能获得dp[i-2]处+nums[i]的值
        int len = nums.length;
        if(len==0) return 0;
        if(len==1) return nums[0];
        if(len==2) return Math.max(nums[0],nums[1]);

        int sum1=nums[0],sum2=Math.max(nums[0],nums[1]);

        for(int i=2;i<len;i++){
            int sum3 = Math.max(sum1+nums[i],sum2);
            //指针移动
            sum1 = sum2;
            sum2 = sum3;
        }

        return sum2;
        
    }
}
```



#### [213. 打家劫舍 II](https://leetcode-cn.com/problems/house-robber-ii/)

难度中等461

你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 **围成一圈** ，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，**如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警** 。

给定一个代表每个房屋存放金额的非负整数数组，计算你 **在不触动警报装置的情况下** ，能够偷窃到的最高金额。

 

**示例 1：**

```
输入：nums = [2,3,2]
输出：3
解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
```

##### 解法1：dp法

- 环形拆成两个单链

- 1.抢第一家，不能抢最后一家

- 2.不能抢第一家，抢最后一家

```java
class Solution {
    public int rob(int[] nums) {
        //环形拆成两个单链
        //1.抢第一家，不能抢最后一家
        //2.不能抢第一家，抢最后一家
        if(nums==null||nums.length==0) return 0;
        if(nums.length==1) return nums[0];
        
        
         int len = nums.length;
        //分开求两次单例
        int res = 0;
        res = Math.max(res,myRob(Arrays.copyOfRange(nums,0,len-1)));
        res = Math.max(res,myRob(Arrays.copyOfRange(nums,1,len)));
        return res;
    }
    public int myRob(int[] nums){
        //sum1,sum2初始化为0
        int sum1=0,sum2=0,sum3=0;
        for(int i =0;i<nums.length;i++){
            sum3 = Math.max(sum1+nums[i],sum2);
            sum1 = sum2;
            sum2 = sum3;
        }
        return sum3;
    }
}
```



#### [152. 乘积最大子数组](https://leetcode-cn.com/problems/maximum-product-subarray/)

难度中等933

给你一个整数数组 `nums` ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。

 

**示例 1:**

```
输入: [2,3,-2,4]
输出: 6
解释: 子数组 [2,3] 有最大乘积 6。
```

##### 解法1：DP

- 这题是求数组中子区间的最大乘积，对于乘法，我们需要注意，负数乘以负数，会变成正数，所以解这题的时候我们需要维护两个变量，当前的最大值，以及最小值，最小值可能为负数，但没准下一步乘以一个负数，当前的最大值就变成最小值，而最小值则变成最大值了。
- curMax = Math.max**(num[i],**.....)) 没有用**curMax**保证了对连续子数组的取值

```java
maxDP[i + 1] = max(maxDP[i] * A[i + 1], A[i + 1],minDP[i] * A[i + 1])
minDP[i + 1] = min(minDP[i] * A[i + 1], A[i + 1],maxDP[i] * A[i + 1])
dp[i + 1] = max(dp[i], maxDP[i + 1])
```

```java
class Solution {
    public int maxProduct(int[] nums) {    
        //异常判断
        if(nums==null||nums.length==0) ;
        
        int res = nums[0];
        int curMax = nums[0];
        int curMin = nums[0];

        //注意有0的情况
        for(int i=1;i<nums.length;i++){
             //curMax与curMin下会被更改，先记录下来
            int tempMax = curMax;
            int tempMin = curMin;
            //最大积的可能情况有：元素i自己本身，上一个最大积与i元素累乘，上一个最小积与i元素累乘；
            //与i元素自己进行比较是为了处理i元素之前全都是0的情况；
            //(Math.max(num[i],.....)) 没有用curMax保证了对连续子数组的取值
            curMax = Math.max(nums[i],Math.max(tempMin*nums[i],tempMax*nums[i]));
            curMin = Math.min(nums[i],Math.min(tempMin*nums[i],tempMax*nums[i]));
            res = Math.max(res,curMax);
        }

        return res;
    }
}
```



#### [72. 编辑距离](https://leetcode-cn.com/problems/edit-distance/)

难度困难1395

给你两个单词 `word1` 和 `word2`，请你计算出将 `word1` 转换成 `word2` 所使用的最少操作数 。

你可以对一个单词进行如下三种操作：

- 插入一个字符
- 删除一个字符
- 替换一个字符

 

**示例 1：**

```
输入：word1 = "horse", word2 = "ros"
输出：3
解释：
horse -> rorse (将 'h' 替换为 'r')
rorse -> rose (删除 'r')
rose -> ros (删除 'e')
```



##### **解法1：**二维动态规划

![image-20210209203303048](C:%5CUsers%5C%E9%BB%8E%E5%85%88%E6%A1%A6%5CDesktop%5CMarkDown%5CLeedocode%E9%A2%98%E7%9B%AE%E8%AE%B0%E5%BD%95.assets%5Cimage-20210209203303048.png)

![image-20210209203433992](C:%5CUsers%5C%E9%BB%8E%E5%85%88%E6%A1%A6%5CDesktop%5CMarkDown%5CLeedocode%E9%A2%98%E7%9B%AE%E8%AE%B0%E5%BD%95.assets%5Cimage-20210209203433992.png)

```java
class Solution {
    public int minDistance(String word1, String word2) {
        if(word1.length()==0) return word2.length();
        if(word2.length()==0) return word1.length();
        //word1与word2对应两个状态，需要二维的dp方程
        //插入操作：在单词 A 中插入一个字符
        //删除操作：在单词 B 中插入一个字符
        //替换操作：修改单词 A 的一个字符

        //D[i][j] 表示 A 的前 i 个字母和 B 的前 j 个字母之间的编辑距离。
        int[][]dp = new int[word1.length()+1][word2.length()+1];
		
        //初始化操作
        for(int i=0;i<=word1.length();i++){
            dp[i][0] = i;  //dp[i][0]表示 i个元素 转换成0个元素需要i步  (删除i个元素)
        }
        for(int i=0;i<=word2.length();i++){
            dp[0][i] = i;
        }

        //dp开始
        //i,j从1开始
        for(int i=1;i<=word1.length();i++){
            for(int j=1;j<=word2.length();j++){
                int left = dp[i][j-1]+1; //+1表示 i到j-1变成i到j至少需要添加元素一个操作
                int down = dp[i-1][j]+1;
                int left_down=0;
                if(word1.charAt(i-1)==word2.charAt(j-1)){
                    left_down = dp[i-1][j-1];
                }else{
                    left_down = dp[i-1][j-1]+1;  //两个单词最后一个字母不同需要多一步操作
                }

                //dp转移方程
                dp[i][j] = Math.min(left,Math.min(down,left_down));
            }
        }


        return dp[word1.length()][word2.length()];

    }
}
```



#### [96. 不同的二叉搜索树](https://leetcode-cn.com/problems/unique-binary-search-trees/)

难度中等985

给定一个整数 *n*，求以 1 ... *n* 为节点组成的二叉搜索树有多少种？

**示例:**

```
输入: 3
输出: 5
解释:
给定 n = 3, 一共有 5 种不同结构的二叉搜索树:

   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3
```

##### 解法1：动态规划

- 找到动态方程
- 整数 *n*，将1..n的每个数拿出来做根节点得.**方程1**
- 每个根节点i的种类为左边i-1个数于右边n-i个数的乘积。**得方程2**

![image-20210205162828444](C:%5CUsers%5C%E9%BB%8E%E5%85%88%E6%A1%A6%5CDesktop%5CMarkDown%5CLeedocode%E9%A2%98%E7%9B%AE%E8%AE%B0%E5%BD%95.assets%5Cimage-20210205162828444.png)



```java
class Solution {
    public int numTrees(int n) {
        int[] dp = new int[n+1];
        if(n==0)return 0;
        dp[0] = 1; //特殊情况，根节点无左子树时，左边就乘1 dp[0]=1

        //dp[i] = sum(dp[j-1]*dp[i-j])
        for(int i=1;i<=n;i++){
            //每计算一个dp[i].j从1遍历到i
            for(int j=1;j<=i;j++){
                dp[i] +=dp[j-1]*dp[i-j];
            }
        }

        return dp[n];
    }
}
```



#### [718. 最长重复子数组](https://leetcode-cn.com/problems/maximum-length-of-repeated-subarray/)

难度中等395收藏分享切换为英文接收动态反馈

给两个整数数组 `A` 和 `B` ，返回两个数组中公共的、长度最长的子数组的长度。

 

**示例：**

```
输入：
A: [1,2,3,2,1]
B: [3,2,1,4,7]
输出：3
解释：
长度最长的公共子数组是 [3, 2, 1] 。
```

![image-20210319213117534](C:\Users\lenovo\Desktop\MarkDown\Leedocode题目记录.assets\image-20210319213117534.png)

##### 解法1：DP法

- DP法：dp[i] [j]= dp[i-1] [j-1] + 1

```java
class Solution {
    public int findLength(int[] A, int[] B) {
        //
        //DP法：dp[i][j] = dp[i-1][j-1] + 1
        int[][] dp = new int[A.length+1][B.length+1];
        int res = 0;
        for(int i=1;i<=A.length;i++){
            for(int j=1;j<=B.length;j++){
                if(A[i-1]==B[j-1]){
                    dp[i][j]=dp[i-1][j-1]+1;
                    res = Math.max(res,dp[i][j]);
                }
            }
        }

        return res;
    }
}
```



### 01背包问题

模板：

1.初始化dp的最简单状态的值

2.**不能重复使用**的必须：外层循环遍历状态，内层循环遍历dp. **[416. 分割等和子集](https://leetcode-cn.com/problems/partition-equal-subset-sum/)**

   **可重复使用**的：   可以采用外层循环dp，内层循环遍历状态  （答案中可重复）  **[518. 零钱兑换 II](https://leetcode-cn.com/problems/coin-change-2/)**

​                                  可以采用外层循环状态，内层循环遍历dp  （答案中不可重复）      **[377. 组合总和 Ⅳ](https://leetcode-cn.com/problems/combination-sum-iv/)**

3.重不重复使用看dp遍历，dp从小到大为可重复使用，dp从大到小为不可重复，不可重复使用





#### 01背包-可重复使用     答案可重复-答案不可重复

#### [322. 零钱兑换](https://leetcode-cn.com/problems/coin-change/)

难度中等1065

给定不同面额的硬币 `coins` 和一个总金额 `amount`。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 `-1`。

你可以认为每种硬币的数量是无限的。

 

**示例 1：**

```
输入：coins = [1, 2, 5], amount = 11
输出：3 
解释：11 = 5 + 5 + 1
```



**解法1：dp**，内层循环为遍历各种硬币，**<u>从小到大</u>**，使得每个dp[i]可重复使用硬币

```java
// class Solution {
//     int res = Integer.MAX_VALUE;
//     public int coinChange(int[] coins, int amount) {
//         if(amount==0) return 0;
//         if(coins==null||coins.length==0) return -1;

        
//         int coinsLen = coins.length;
//         //回溯法
//         Arrays.sort(coins);
//         DFS(0,coins,coinsLen,amount);
//         return res==Integer.MAX_VALUE?-1:res;
       
//     }

//     public void DFS(int index,int[] coins,int coinsLen,int amount){
//         if(amount==0){
//             res = res>index?index:res;
//         }
//         if(amount<0){
//             return ;
//         }
//         for(int i=0;i<coinsLen;i++){
//             if(amount<coins[i]){
//                 return ;
//             }
//             DFS(index+1,coins,coinsLen,amount-coins[i]);
//         }
//         return;
//     }   
// }

//这道题不能用贪心，因为最后用最小的硬币不一定能得到结果
class Solution {
   //DP方法
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        Arrays.fill(dp,Integer.MAX_VALUE);
        dp[0] = 0; //凑足0元需要0个硬币
        //外层遍历状态，内层遍历dp
        for(int j=0;j<coins.length;j++){
            for(int i=1;i<=amount;i++){
                if(i-coins[j]>=0&&dp[i-coins[j]]!=Integer.MAX_VALUE){
                    dp[i] = Math.min(dp[i],dp[i-coins[j]]+1);
                }
            }
        }
        //能否兑换的判断     
        return dp[amount]==Integer.MAX_VALUE?-1:dp[amount];
    }
}
```



#### [377. 组合总和 Ⅳ](https://leetcode-cn.com/problems/combination-sum-iv/)

难度中等280

给定一个由正整数组成且不存在重复数字的数组，找出和为给定目标正整数的组合的个数。

**示例:**

```
nums = [1, 2, 3]
target = 4

所有可能的组合为：
(1, 1, 1, 1)
(1, 1, 2)
(1, 2, 1)
(1, 3)
(2, 1, 1)
(2, 2)
(3, 1)

请注意，顺序不同的序列被视作不同的组合。

因此输出为 7。
```

##### 解法1：可重复使用，并且答案中可重复

```java
class Solution {
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target+1];
        dp[0] =1;

        for(int i=1;i<=target;i++){
            for(int j=0;j<nums.length;j++){
                //动态转移方程
                if(i-nums[j]>=0){
                    dp[i] = dp[i] +dp[i-nums[j]];
                }
                
            }
        }

        //这种写法 1，1，2 与1，2，1算作一次
        // for(int j=0;j<nums.length;j++){
        //     for(int i=1;i<=target;i++){
        //         //动态转移方程
        //         if(i-nums[j]>=0){
        //             dp[i] = dp[i] +dp[i-nums[j]];
        //         }
        //     }
        // }

        return dp[target];
    }
}
```





#### 01背包-不可重复使用

#### [416. 分割等和子集](https://leetcode-cn.com/problems/partition-equal-subset-sum/)

难度中等676

给定一个**只包含正整数**的**非空**数组。是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。

**注意:**

1. 每个数组中的元素不会超过 100
2. 数组的大小不会超过 200

**示例 1:**

```
输入: [1, 5, 11, 5]

输出: true

解释: 数组可以分割成 [1, 5, 5] 和 [11].
```



![image-20210217151359731](C:\Users\lenovo\Desktop\MarkDown\Leedocode题目记录.assets\image-20210217151359731.png)

![image-20210217151430813](C:\Users\lenovo\Desktop\MarkDown\Leedocode题目记录.assets\image-20210217151430813.png)

**解法1：二维DP**

```java
class Solution {
    public boolean canPartition(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return false;
        }
        int sum = 0, maxNum = 0;
        for (int num : nums) {
            sum += num;
            maxNum = Math.max(maxNum, num);
        }
        if (sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        if (maxNum > target) {
            return false;
        }
       
        //二维DP
        boolean[][] dp = new boolean[target+1][nums.length+1];
        for(int i=0;i<=nums.length;i++){
            dp[0][i]=true;  //任何数都能凑成和为0  （不选即可）
        }

        //不可重复的DP
        for(int j=1;j<=nums.length;j++){
            for(int i=target;i>=1;i--){
                //选择加上nums[j-1]
                if(i-nums[j-1]>=0){
                    dp[i][j] = dp[i-nums[j-1]][j-1] | dp[i][j-1];
                }
                //选择不加上nums[j-1]
                else{
                    dp[i][j] = dp[i][j-1];
                }
            }
        }
        //dp[target][nums.length]表示凑成target，使用nums.length个数的结果
        return dp[target][nums.length];
    }
}

```



#####  解法2：01背包dp：不可重复使用

- boolean[] dp = new boolean[target+1];   **dp[i]**的值表示大小为i的背包能否填满
- 根据每个物体元素，每行每行的更新背包dp[i]的值，保证物体不被重复使用
- 动态转移方程：**<u>dp[i] = dp[i]||dp[i-nums[j]];</u>**      下一行依赖上一行 和同一行前面的背包能否被刚好填满
- 内层循环i从**<u>大容量到小容量</u>**，避免重复使用nums[j]  （大容量会用到小容量的状态，大容量需要先判断状态，否则）

```java
class Solution {
    boolean res = false;
    public boolean canPartition(int[] nums) {
        if(nums==null||nums.length==0) return false;

        int sum=0;
        for(int num:nums){
            sum+=num;
        }
        if(sum%2!=0) return false;
        int target = sum/2;
        Arrays.sort(nums);

        //回溯超时
        // boolean[] visited = new boolean[nums.length];
        // DFS(nums,target,visited);
        // return  res;

        //DP法：参考兑换硬币
        boolean[] dp = new boolean[target+1];
        dp[0] = true;

        //这种写法会重复使用nums[j]
        // for(int i=1;i<=target;i++){
        //     for(int j=0;j<nums.length;j++){
        //         if(i>=nums[j]){
        //             dp[i] = dp[i]||dp[i-nums[j]];
        //         }
        //     }
        // }

        //i从大容量到小容量，避免重复使用nums[j]  （大容量会用到小容量的状态，大容量需要先判断状态，否则）
        for(int j=0;j<nums.length;j++){
            //关键：***********************************
            for(int i=target;i>=0;i--){
                if(i>=nums[j]){
                    dp[i] = dp[i]||dp[i-nums[j]];
                }
            }
        }
        return dp[target];



    }


    // public void DFS(int[] nums,int target,boolean[] visited){
    //     if(res ==false){
    //         if(target==0){
    //             res = true;
    //             return ;
    //         }
    //         if(target<0){
    //             return ;
    //         }

    //         for(int i=0;i<nums.length;i++){
    //             if(nums[i]>target){
    //                 return ;
    //             }else{
    //                 if(visited[i]==false){
    //                     visited[i]=true;
    //                     DFS(nums,target-nums[i],visited);
    //                     visited[i]=false;
    //                 }
    //             }
    //         }
    //     }
    // }
}
```





## 脑经急转弯

#### [剑指 Offer 66. 构建乘积数组](https://leetcode-cn.com/problems/gou-jian-cheng-ji-shu-zu-lcof/)

给定一个数组 `A[0,1,…,n-1]`，请构建一个数组 `B[0,1,…,n-1]`，其中 `B[i]` 的值是数组 `A` 中除了下标 `i` 以外的元素的积, 即 `B[i]=A[0]×A[1]×…×A[i-1]×A[i+1]×…×A[n-1]`。不能使用除法。

 

**示例:**

```
输入: [1,2,3,4,5]
输出: [120,60,40,30,24]
```

 

##### 解法1：

![image-20210125195600876](C:\Users\黎先桦\Desktop\MarkDown\Leedocode题目记录.assets\image-20210125195600876.png)



![image-20210125195615502](C:\Users\黎先桦\Desktop\MarkDown\Leedocode题目记录.assets\image-20210125195615502.png)

```java
class Solution {
    public int[] constructArr(int[] a) {
        //第一次循环算出i之前的，第二次循环算出之后的
        if(a==null||a.length==0) return new int[0];

        int[] b = new int[a.length];

        //将b初始化为下三角的乘积
        for(int i=0;i<b.length;i++){
            if(i==0){
                b[i]=1;
            }else{
                b[i] = b[i-1]*a[i-1];
            }
        }

        //将b乘上上三角
        int temp=1;
        for(int i = b.length-1;i>=0;i--){
            if(i==b.length-1){
                b[i] *=1;
            }else{
                b[i]*=temp;
            }
            temp*=a[i];
        }

        return b;
    }
}
```



#### [621. 任务调度器](https://leetcode-cn.com/problems/task-scheduler/)

给你一个用字符数组 `tasks` 表示的 CPU 需要执行的任务列表。其中每个字母表示一种不同种类的任务。任务可以以任意顺序执行，并且每个任务都可以在 1 个单位时间内执行完。在任何一个单位时间，CPU 可以完成一个任务，或者处于待命状态。

然而，两个 **相同种类** 的任务之间必须有长度为整数 `n` 的冷却时间，因此至少有连续 `n` 个单位时间内 CPU 在执行不同的任务，或者在待命状态。

你需要计算完成所有任务所需要的 **最短时间** 。

 

**示例 1：**

```
输入：tasks = ["A","A","A","B","B","B"], n = 2
输出：8
解释：A -> B -> (待命) -> A -> B -> (待命) -> A -> B
     在本示例中，两个相同类型任务之间必须间隔长度为 n = 2 的冷却时间，而执行一个任务只需要一个单位时间，所以中间出现了（待命）状态。 
```



**解1：**

![image-20210226212844839](C:\Users\lenovo\Desktop\MarkDown\Leedocode题目记录.assets\image-20210226212844839.png)

- 只需要考虑出现次数最多的任务，次数少的任务都能被在间隔中完成
- `maxTimes`为出现次数最多的那个任务出现的次数。
- `maxCount`为一共有多少个任务和出现最多的那个任务出现次数一样。
- 注意考虑：`n==0`的特殊情况

```java
class Solution {
    public int leastInterval(char[] tasks, int n) {
        //maxCount为出现次数最多的任务。 maxSimpleCount为出现次数为maxCount的任务的个数
        // res = (maxCount-1)*(n+1) + maxSimpleCount
        int[] buckets = new int[26];
        for(char c:tasks){
            buckets[c-'A']++;
        }
        Arrays.sort(buckets);
        int maxCount=0,maxSimpleCount=0;
        maxCount = buckets[25];  
        for(int i=25;i>=0;i--){
            if(buckets[i]==maxCount){
                maxSimpleCount++;
            }
        }

        //计算结果
        int res = (maxCount-1)*(n+1) + maxSimpleCount;
        return Math.max(res,tasks.length);  //考虑n==0的情况和小数量的任务出现很多次的情况
    }
}
```



#### 统计每月兔子的个数

有一只兔子，从出生后第3个月起每个月都生一只兔子，小兔子长到第三个月后每个月又生一只兔子，假如兔子都不死，问每个月的兔子总数为多少？



##### 解法1：波菲切纳公式

- f(n) = f(n-1)+在第n月成熟的兔子数量**（生新兔子导致数量增加）**

- f(n)-f(n-1)表示新出生的兔子数量，**等于成熟的兔子数量，这些兔子就是在f(n-2)的总数**
- f(n)) =f(n-1）+ f(n-2)  
- 第n个月的兔子数量等于前两个的兔子数量之后

```java
public static int getTotal(int month){
    if(month<=0) return 0;
    if(month==1||month==2){
        System.out.print(1);
        return 0 ;
    }
	
    //a,b,c表示f(n-2) f(n-1) f(n)
    int a=1,b=1,c=1;
    for(int i=3;i<=month;i++){
        //移动的顺序很重要！！！！
        c =a+b;
        a =b;
        b= c;
    }

    return c;
}
```



### 前缀和

#### [560. 和为K的子数组](https://leetcode-cn.com/problems/subarray-sum-equals-k/)

难度中等771

给定一个整数数组和一个整数 **k，**你需要找到该数组中和为 **k** 的连续的子数组的个数。

**示例 1 :**

```
输入:nums = [1,1,1], k = 2
输出: 2 , [1,1] 与 [1,1] 为两种不同的情况。
```



##### 解法1：前缀和+两个数和的解法

- 计算pre[i]为nums前i个数之和，**连续的子数组和即为两个前缀和之差**
- 题目转化为：**per[j]-pre[i] =k**    **（两数之差为k）**

```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        //有负数，用滑动窗口，左窗口关闭的条件不好设定

        //暴力解：求以end结尾的连续子数组和为k的个数
        // int res = 0;
        // for(int end=0;end<nums.length;end++){
        //     int sum = 0;
        //     for(int i =end;i>=0;i--){
        //         sum+=nums[i];
        //         //思考：为什么可以这样
        //         // 以end结尾的连续子数组和为k的个数
        //         if(sum==k) res++;
        //     }
        // }
        // return res;

        //前缀和+两个之和的解
        int[] pre = new int[nums.length+1];
        //1.前缀和
        pre[0] = 0;
        for(int i=1;i<=nums.length;i++){
            pre[i] = pre[i-1]+nums[i-1];
        }
        //2.求pre[j]-pre[i]==k的个数 pre[j] = pre[i]+k
        int res =0;
        // pre[i]+k 出现的次数
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        for(int i=0;i<pre.length;i++){
            if(hashMap.containsKey(pre[i])){
                res+=hashMap.get(pre[i]);
            }
            //put每次都需要执行的
            hashMap.put(pre[i]+k,hashMap.getOrDefault(pre[i]+k,0)+1);
        }
        return res;
    }
}
```



#### [437. 路径总和 III](https://leetcode-cn.com/problems/path-sum-iii/)

难度中等763

给定一个二叉树，它的每个结点都存放着一个整数值。

找出路径和等于给定数值的路径总数。

路径不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。

二叉树不超过1000个节点，且节点数值范围是 [-1000000,1000000] 的整数。

**示例：**

```
root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8

      10
     /  \
    5   -3
   / \    \
  3   2   11
 / \   \
3  -2   1

返回 3。和等于 8 的路径有:

1.  5 -> 3
2.  5 -> 2 -> 1
3.  -3 -> 11
```



##### 解法1：前缀和

-  preSum-sum：后面的数才能知道自己和前面的数差是不是target。所以是 preSum-sum ，前面的数有没有存在过

```java
class Solution {
    int res = 0;
    public int pathSum(TreeNode root, int sum) {
        if(root==null) return 0;
        //前缀和HashMap 前缀和+出现的次数
        HashMap<Integer,Integer> preSumMap = new HashMap();
        preSumMap.put(0,1);  //吾忘***********************
        inOrder(root,0,preSumMap,sum);
        return res;
    }

    //前缀和+回溯： pre[j]-pre[i]==sum  --》  pre[j] = pre[i]+sum
    public void inOrder(TreeNode root,int preSum,HashMap preSumMap,int sum){
        if(root!=null){
            preSum+=root.val;
            if(preSumMap.containsKey(preSum-sum)){
                res+=(int)preSumMap.get(preSum-sum);
            }
            preSumMap.put(preSum,(int)preSumMap.getOrDefault(preSum,0)+1);

            //先序遍历
            inOrder(root.left,preSum,preSumMap,sum);
            inOrder(root.right,preSum,preSumMap,sum);
            //回溯
            preSumMap.put(preSum,(int)preSumMap.getOrDefault(preSum,0)-1);
            
        }
    }
}
```



## 图

### 拓扑结构

#### [207. 课程表](https://leetcode-cn.com/problems/course-schedule/)

难度中等708

你这个学期必须选修 `numCourses` 门课程，记为 `0` 到 `numCourses - 1` 。

在选修某些课程之前需要一些先修课程。 先修课程按数组 `prerequisites`给出，其中 `prerequisites[i] = [ai, bi]` ，表示如果要学习课程 `ai` 则 **必须** 先学习课程 `bi` 。

- 例如，先修课程对 `[0, 1]` 表示：想要学习课程 `0` ，你需要先完成课程 `1` 。

请你判断是否可能完成所有课程的学习？如果可以，返回 `true` ；否则，返回 `false` 。

 

**示例 1：**

```
输入：numCourses = 2, prerequisites = [[1,0]]
输出：true
解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
```





##### 解法1：BFS解决拓扑结构

- 构建**<u>入度表</u>**与**<u>边表</u>**
- 初始化时将**<u>入度0</u>**的全部入队
- 出队时将**<u>边表</u>**中**<u>出队节点</u>**指向的边的入度--；
- **判断条件：**通过判断**<u>出队的元素是否包含全部节点</u>**来判断是否有环   **（能出队的元素表示元素的入度为0）**

```java
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //1.入度表 indegrees
        int[] indegrees = new int[numCourses];
        //计算入度
        for(int[] temp :prerequisites){
            indegrees[temp[1]]++;
        }

        //2.边表: ArrayList的第i个位置的list，表示 第i个节点所指向的节点集合
        List<List<Integer>> adj = new ArrayList();
        //初始化
        for(int i=0;i<numCourses;i++){
            adj.add(new ArrayList<Integer>());
        }
        //构建边表
        for(int[] temp :prerequisites){
            adj.get(temp[0]).add(temp[1]);
        }
        

        //广度遍历:入度为0的节点入队，出队时将边表中出队节点指向的边的入队--；
        Queue<Integer> queue = new LinkedList();
        //1.将所有入度为0的节点入队
         for(int i = 0; i < numCourses; i++){
             if(indegrees[i] == 0) queue.add(i);
         }
        
        //2.广度遍历
         while(!queue.isEmpty()){
            //出队
            int pre = queue.poll();
            //  (学习到一门课程)
            numCourses--;

            //将边表中出队节点指向的边的入队--；
            for(int cur :adj.get(pre)){
                indegrees[cur]--;
                if(indegrees[cur]==0){
                    //入队
                    queue.add(cur);
                }
            }
         }

        //如果拓扑图中有环，numCoures不会为0，因为环中节点不法入队
         return numCourses==0?true:false;
    }
}
```





## 字符串处理



#### [8. 字符串转换整数 (atoi)](https://leetcode-cn.com/problems/string-to-integer-atoi/)

解法：

- 溢出判断：  **防止 ans = ans * 10 + cur 溢出  **

​                     **等价变形为 ans > (Integer.MAX_VALUE - cur) / 10 进行预判断**

- **`String.trim()`** 去除Stirng前后的所有空格

```java
class Solution {
    public int myAtoi(String s) {
        int res= 0;
        boolean flag = true;//默认为正数
        int flagCount = 0;  //符号+，-出现的次数 
        String str = s.trim();//去除s前后的空格

        char[] chars = str.toCharArray();
        for(int i=0;i<chars.length;i++){
            char c= chars[i];
            //字符
            if(!((c-'0')>=0&&(c-'9'<=0)||c=='+'||c=='-')){
                break;
            }else if(c=='+'||c=='-'){
                if(flagCount>=1){
                    break;
                }else{
                    if(c=='-'){
                        flag=false;
                    }
                    flagCount++;

                }
                //数字
            }else{
                flagCount++; //避免 0000-123这种情况，碰到数字则符号以定，再运到符号直接退出
                 //防止 ans = ans * 10 + cur 溢出
                 // 等价变形为 ans > (Integer.MAX_VALUE - cur) / 10 进行预判断
                if(res> (Integer.MAX_VALUE - (c-'0')) / 10){  
                    res = flag ==true?Integer.MAX_VALUE:Integer.MIN_VALUE;
                    return res;
                }else{
                    res = res*10+(c-'0');
                }
            }
        }
        return  flag==true?res:-res;

    }
}
```





## 贪心算法

#### [53. 最大子序和](https://leetcode-cn.com/problems/maximum-subarray/)

给定一个整数数组 `nums` ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。

 

**示例 1：**

```
输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
输出：6
解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
```

##### 解法1：贪心算法

```java
class Solution {
    public int maxSubArray(int[] nums) {
        //res记录遍历过程中的最大值
        //curSum仍是正数就继续＋
        int res=Integer.MIN_VALUE;
        int curSum = Integer.MIN_VALUE;
        for(int num:nums){
            if(curSum>0){
                curSum+=num;
            }else{
                curSum = Math.max(curSum,num);
            }
            res=  Math.max(res,curSum);
        }

        return res;
    }
}
```

