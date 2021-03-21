package t1;

import java.sql.SQLOutput;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        solve();
    }
    public  static  void solve(){
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String word = sc.nextLine();

        int wordLen = word.length();
        //word上的指针
        int index = 0;
        int res = 0;

        //开始遍历tempStr数组
        while(index<wordLen){
            String tempStr = new String(str);
            int strLen = tempStr.length();
            //字符数组
            char[] chars = tempStr.toCharArray();
            char[] wordChar = word.toCharArray();
            //遍历
            for(int i = 0;i<strLen;i++){
                //找到对应的字符
                if(index<wordLen){
                    if(chars[i]==wordChar[index]){
                        index++;
                    }else{
                        res++;
                    }

                }else{
                    break;
                }
            }
        }
        System.out.println( res);
    }
}
