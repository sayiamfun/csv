package com.sayiamfun.csv;


import org.apache.commons.collections.list.TreeList;

import java.util.*;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
//        int i = -2147483648;
//        System.out.println(i);
        int[] num = new int[]{
                1,-1,-1,0
        };
        System.out.println(new Test().threeSum(num));
    }


    /**
     * 1010
     * 001
     */

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> resultList = new ArrayList<>();
        Arrays.sort(nums);
//        System.out.println(Arrays.toString(nums));
        for (int first = 0; first < nums.length - 2; first++) {
            if (first > 0 && nums[first - 1] == nums[first]) continue;
            int third = nums.length - 1;
            for (int second = first + 1; second < nums.length - 1; second++) {
                if ((second > first + 1 && nums[second] == nums[second - 1])) continue;
                while (nums[first] + nums[second] + nums[third] > 0 && third > second + 1) third--;
//                System.out.println(first + "," + second + "," + third);
                if(second==third) break;
                if (nums[first] + nums[second] + nums[third] == 0)
                    resultList.add(Arrays.asList(nums[first], nums[second], nums[third]));
            }

        }
        return resultList;
    }


}
