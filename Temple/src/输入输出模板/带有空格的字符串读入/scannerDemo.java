package 输入输出模板.带有空格的字符串读入;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.Scanner;


/*
输入
    2
    abc bcf
    efg gfe

期望打印
    abc bcf
    efg gfe
 */
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

//错误写法： 读出int用nextInt，读取String用nextLine
//原因：读取int的时候留下的结束符，会被第一个nextLine读到
//public class scannerDemo {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        //对数字与字符串的输入都用nextLine
//        int num = sc.nextInt();
//        //int num = Integer.parseInt(sc.nextLine());
//        String[] strs = new String[num] ;
//        for(int i =0;i<num;i++){
//            strs[i] = sc.nextLine();
//            System.out.println(strs[i]);
//        }
//    }
//}
