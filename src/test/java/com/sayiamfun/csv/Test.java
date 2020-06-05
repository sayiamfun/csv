package com.sayiamfun.csv;


import com.sayiamfun.common.utils.ScanPackage;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;

public class Test {

    public static void main(String[] args) {




        String tmp = "4.063_4.059_4.06_4.06_4.061_4.059_4.06_4.061_4.062_4.06_4.061_4.041_4.061_4.061_4.061_4.061_4.062_4.062_4.06_4.061_4.059_4.06_4.059_4.061_4.059_4.062_4.06_4.059_4.061_4.061_4.063_4.061_4.061_4.061_4.062_4.06_4.062_4.059_4.062_4.063_4.063_4.063_4.061_4.06_4.062_4.06_4.062_4.061_4.062_4.061_4.061_4.062_4.063_4.061_4.062_4.061_4.059_4.062_4.06_4.062_4.06_4.064_4.061_4.062_4.058_4.06_4.062_4.059_4.06_4.06_4.06_4.059_4.062_4.064_4.062_4.062_4.061_4.064_4.061_4.062_4.06_4.061_4.06_4.06_4.06_4.061\n";
        String[] s = tmp.split("_");
        System.out.println(s.length);
//        for (String s1 : s) {
//            System.out.println(s1);
//        }

//        String s = "D:\\车辆数据\\传为佳话\\2020-03-25.tar\\2020-03-25\\backup_data\\year=2019\\month=09\\day=08\\vin=LEWTEB140HE103804\\part-00000-661197f6-6c8b-4828-ae18-9f53a5b8a3d9.c000.csv";
//        int year = s.indexOf("year=");
//        int month = s.indexOf("month=");
//        int day = s.indexOf("day=");
//        int vin = s.indexOf("vin=");
//        String years = s.substring(year + 5, year + 9);
//        String months = s.substring(month + 6, month + 8);
//        String days = s.substring(day + 4, day + 6);
//        String vins = s.substring(vin + 4, vin + 4 + "LEWTEB140HE103804".length());
//        System.out.println(vins + "_" + years + months + days);


//        String[] split = s.split(":");
//        String[] split1 = split[2].split("]");
//        int mSize = Integer.parseInt(StringUtils.substring(split1[0], 1, split1[0].length()));//温度探针数量
//        String tmpV = StringUtils.substring(split[3], 1); //第一个单体电压
//        System.out.println(mSize + " " + tmpV);


//        Test test = new Test();
//        List<List<Integer>> lists = test.threeSum(new int[]{0, 0, 0, 0});
//        for (List<Integer> list : lists) {
//            for (Integer integer : list) {
//                System.out.print(integer + ",");
//            }
//            System.out.println();
//        }
    }

    private static String getTime(String s) {
        StringBuilder stringBuilder = new StringBuilder(30);
        String[] s1 = s.split(" ");
        String[] split = s1[0].split("/");
        for (String s2 : split) {
            if (s2.length() == 1) {
                stringBuilder.append("0" + s2);
            } else {
                stringBuilder.append(s2);
            }
        }
        String[] split1 = s1[1].split(":");
        for (String s2 : split1) {
            stringBuilder.append(s2);
        }
        return stringBuilder.toString();
    }

    /**
     *
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        list.add(Arrays.asList(new Integer[]{nums[i], nums[j], nums[k]}));
                    }
                }
            }
        }
        return list;
    }
}
