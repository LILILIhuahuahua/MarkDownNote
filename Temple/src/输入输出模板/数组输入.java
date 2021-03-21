package 输入输出模板;

import java.util.*;

/**
 * @author HuaHua
 * @create 2021-02-16 16:38
 */

import java.util.*;
import java.lang.*;
public class 数组输入 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //获取输入的整数序列
        String str = sc.nextLine();
        String[] strings = str.split(" ");

        //转为整数数组
        int[] ints = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            ints[i] = Integer.parseInt(strings[i]);
        }
        System.out.println("题目的结果");
    }

}

class Solution {
    boolean res = false;
    public boolean canPartition(int[] nums) {
        if(nums==null||nums.length==0) {
            return false;
        }

        int sum=0;
        for(int num:nums){
            sum+=num;
        }
        if(sum%2!=0) {
            return false;
        }
        int target = sum/2;
        Arrays.sort(nums);

        //回溯法
        boolean[] visited = new boolean[nums.length];
        DFS(nums,target,visited);
        return  res;
    }


    public void DFS(int[] nums,int target,boolean[] visited){
        if(res ==false){
            if(target==0){
                res = true;
                return ;
            }
            if(target<0){
                return ;
            }

            for(int i=0;i<nums.length;i++){
                if(nums[i]>target){
                    return ;
                }else{
                    if(visited[i]==false){
                        visited[i]=true;
                        DFS(nums,target-nums[i],visited);
                        visited[i]=false;
                    }
                }
            }
        }
    }
}