package 输入输出模板.单个单个字符输入;

import java.io.IOException;
import java.io.InputStream;

//计算最后一个单词的长度
public class templete {
    //注意：in.read()函数需要有异常
    public static void main(String[] args) throws Exception {
        int res = 0;
        //输入流
        InputStream in = System.in;
        char c;
        //字符串接受的标识符 ‘\n’
        while((c= (char)in.read())!='\n'){
            if(c==' '){
                res = 0;
            }else{
                res++;
            }
        }
        System.out.print(res);
    }
}
