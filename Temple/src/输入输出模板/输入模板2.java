package 输入输出模板;



import java.util.Scanner;

/**
 * @author HuaHua
 * @create 2021-03-06 16:35
 */
public class 输入模板2 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int len  = sc.nextInt();
        int[] in = new int[len];
        for(int i=0;i<len;i++){
            in[i] = sc.nextInt();
        }
        for(int num:in){
            System.out.println(num);
        }

    }

    @Test
    public void TestInput(){
        Scanner sc = new Scanner(System.in);
        String inStr = sc.nextLine();
        String[] strs = inStr.split(" ");
        int[] nums = new int[strs.length];
        int index= 0;
        for(String str:strs){
            nums[index++] = Integer.parseInt(str);
        }

        System.out.println(nums);
    }
}



