# Java笔试输入输出



### 1.读入的都是整数

> - 空格不用管
> - 使用 sc.hasNext()判断输入的结束
> - sc.nexInt()一个个读取整数



![image-20210317214241910](C:\Users\lenovo\Desktop\MarkDown\Java笔试输入输出.assets\image-20210317214241910.png)



代码：

```java
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner; //Scanner也在util包下

public class Main {

    public static void main(String[] args) {
    	Scanner in = new Scanner(System.in);
        //hasNext()判断输入的结束
    	while(in.hasNext()){
            //nextInt()读入每一个整数
    		int n = in.nextInt();
    		int m = in.nextInt();
    		HashSet<Integer> hashset = new HashSet<Integer>();
    		for(int i = 0; i < n; i++){
    			hashset.add(in.nextInt());
    		}
    		for(int i = 0; i < m; i++){
    			hashset.add(in.nextInt());
    		}
    		Object[] arr = hashset.toArray();
    		int[] array = new int[arr.length];
    		for(int i = 0; i < array.length; i++){
    			array[i] = (int) arr[i];
    		}
    		Arrays.sort(array);
    		for(int i = 0; i < array.length; i++){
    			System.out.print(array[i]+" ");
    		}
    	}
    }
    
}
```





### 2.单个整数与多个字符串的读法

> 有单个整数与字符串的读法 ----》   整数与字符串都用 nextLine读



![image-20210317215210052](C:\Users\lenovo\Desktop\MarkDown\Java笔试输入输出.assets\image-20210317215210052.png)





**正确读法：**

```java
public class scannerDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //对数字与字符串的输入都用nextLine
        int num = Integer.parseInt(sc.nextLine());
        String[] strs = new String[num] ;
        for(int i =0;i<num;i++){
            strs[i] = sc.nextLine();
            System.out.println(strs[i]);
        }
    }
}
```



**错误读法：**

- **错误写法：** 读出int用nextInt，读取String用nextLine
- **原因：**读取int的时候留下的结束符，会被第一个nextLine读到

```java

public class scannerDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //错误读法
        int num = sc.nextInt();
        //int num = Integer.parseInt(sc.nextLine());
        String[] strs = new String[num] ;
        for(int i =0;i<num;i++){
            strs[i] = sc.nextLine();
            System.out.println(strs[i]);
        }
    }
}
```



**题目答案**

```java
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //”ZERO”“ONE”“TWO”“THREE”“FOUR”“FIVE”“SIX”“SEVEN”“EIGHT”“NINE”
        //0 2 4 6 8 1 3 5 7 9
        //Z W U X G O H F S I
        solve();
    }

        private static void solve() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            int n = Integer.parseInt(scanner.nextLine());
            String str;
            for (int i = 0; i < n; i++) {
                str = scanner.nextLine();
                int[] num = new int[10];
                char[] arrays = str.toCharArray();
                for (int j = 0; j < arrays.length; j++) {
                    if (arrays[j] == 'Z') num[0]++;//0对应2
                    if (arrays[j] == 'W') num[2]++;//2对应4
                    if (arrays[j] == 'U') num[4]++;//4对应6
                    if (arrays[j] == 'X') num[6]++;//6对应8
                    if (arrays[j] == 'G') num[8]++;//8对应0
                    if (arrays[j] == 'O') num[1]++;
                    if (arrays[j] == 'H') num[3]++;
                    if (arrays[j] == 'F') num[5]++;
                    if (arrays[j] == 'S') num[7]++;
                    if (arrays[j] == 'I') num[9]++;
                }
                num[1] = num[1] - num[0] - num[2] - num[4];//1对应3
                num[3] = num[3] - num[8];//3对应5
                num[5] = num[5] - num[4];//5对应7
                num[7] = num[7] - num[6];//7对应9
                num[9] = num[9] - num[5] - num[6] - num[8];//9对应1

                String string = "";
                //遍历每一数字
                for (int j = 0; j < 10; j++) {
                    //每个数字可能出现多次
                    for (int k = 0; k < num[j]; k++) {
                        string += ((j + 2) % 10);
                    }
                }
                char[] result = string.toCharArray();
                Arrays.sort(result);
                System.out.println(result);
            }
        }
    }
}
```





### 3.读取单个字符