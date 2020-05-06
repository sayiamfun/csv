package com.sayiamfun.csv;


import com.sayiamfun.common.utils.ScanPackage;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;

public class Test {

    public static void main(String[] args) {

        String s1 = "LEWTEB140HE103804,\n" +
                "LEWTEB140HE103897,\n" +
                "LEWTEB140HE104015,\n" +
                "LEWTEB140HF166900,\n" +
                "LEWTEB140HF166928,\n" +
                "LEWTEB140HF167223,\n" +
                "LEWTEB140JN100410,\n" +
                "LEWTEB141HE103794,\n" +
                "LEWTEB141HE103830,\n" +
                "LEWTEB141HE103911,\n" +
                "LEWTEB141HE103925,\n" +
                "LEWTEB141HF167179,\n" +
                "LEWTEB141HF167215,\n" +
                "LEWTEB142HE103741,\n" +
                "LEWTEB142HE103819,\n" +
                "LEWTEB142HE103917,\n" +
                "LEWTEB142HE103920,\n" +
                "LEWTEB142HF167160,\n" +
                "LEWTEB142HF167191,\n" +
                "LEWTEB142HF167224,\n" +
                "LEWTEB142JN100389,\n" +
                "LEWTEB142JN101266,\n" +
                "LEWTEB142JN201044,\n" +
                "LEWTEB143HE103800,\n" +
                "LEWTEB143HE104042,\n" +
                "LEWTEB143HF166924,\n" +
                "LEWTEB143JN100868,\n" +
                "LEWTEB143JN101924,\n" +
                "LEWTEB144HE103756,\n" +
                "LEWTEB144HE103899,\n" +
                "LEWTEB144HE104020,\n" +
                "LEWTEB144JN100670,\n" +
                "LEWTEB144JN100698,\n" +
                "LEWTEB145HE103846,\n" +
                "LEWTEB145HE103913,\n" +
                "LEWTEB145HE103944,\n" +
                "LEWTEB145HE103958,\n" +
                "LEWTEB145HE104009,\n" +
                "LEWTEB145HF167198,\n" +
                "LEWTEB145HF167203,\n" +
                "LEWTEB145JN100614,\n" +
                "LEWTEB146HE103774,\n" +
                "LEWTEB146HE103886,\n" +
                "LEWTEB146HE103905,\n" +
                "LEWTEB146HE103922,\n" +
                "LEWTEB146HE103936,\n" +
                "LEWTEB146HE104021,\n" +
                "LEWTEB146HF167176,\n" +
                "LEWTEB147HE104030,\n" +
                "LEWTEB147JN100839,\n" +
                "LEWTEB148HE103730,\n" +
                "LEWTEB148HE103906,\n" +
                "LEWTEB148HE103968,\n" +
                "LEWTEB148HF166885,\n" +
                "LEWTEB148HF167213,\n" +
                "LEWTEB148HF167227,\n" +
                "LEWTEB148HF167230,\n" +
                "LEWTEB148JN100994,\n" +
                "LEWTEB148JN101238,\n" +
                "LEWTEB148KN100382,\n" +
                "LEWTEB148KN100432,\n" +
                "LEWTEB149HE103784,\n" +
                "LEWTEB149HE104059,\n" +
                "LEWTEB149HF166880,\n" +
                "LEWTEB149HF167172,\n" +
                "LEWTEB149JN402214,\n" +
                "LEWTEB14XHE103860,\n" +
                "LEWTEB14XHE103910,\n" +
                "LEWTEB14XHE103924,\n" +
                "LEWTEB14XKN100058,\n" +
                "LEWWCA325JN400002,\n" +
                "LEWWDA336JN428509,\n" +
                "LEWWDA33XJN428559,\n" +
                "LEWWDB331JN443814,\n" +
                "LEWWDB331JN443876,\n" +
                "LEWWDB331JN443926,\n" +
                "LEWWDB332JN443966,\n" +
                "LEWWDB336JN443842,\n" +
                "LEWWDB336JN443873,\n" +
                "LEWWDB338JN443597,\n" +
                "LEWWDB339JN443947,\n" +
                "LEWWDB33XJN443732,\n" +
                "LEWWEB348JN080109,\n" +
                "LEWXWBJM0KF143929,\n" +
                "LEWXWBJM0KF144014,\n" +
                "LEWXWBJM3KF143679,\n" +
                "LEWXWBJM4KF143920,\n" +
                "LEWXWBJM5KF143358,\n" +
                "LEWXWBJM5KF143361,\n" +
                "LEWXWBJM7KF143183,\n" +
                "LEWXWBJM7KF143359,\n" +
                "LEWXWBJM7KF143362,\n" +
                "LEWXWBJM7KF143930,\n" +
                "LEWXWBJM7KF144012,\n" +
                "LEWXWBJM8KF143192,\n" +
                "LEWXWBJM9KF143427,\n" +
                "LEWXWBJM9KF144013,\n" +
                "LEWXWBJM9KF144044,\n" +
                "LEWXWBJMXKF143372,\n";

        String s2 = "LEWTEB140HE103804,\n" +
                "LEWTEB140HE103897,\n" +
                "LEWTEB140HE104015,\n" +
                "LEWTEB140HF166900,\n" +
                "LEWTEB140HF166928,\n" +
                "LEWTEB140HF167223,\n" +
                "LEWTEB140JN100410,\n" +
                "LEWTEB141HE103794,\n" +
                "LEWTEB141HE103830,\n" +
                "LEWTEB141HE103911,\n" +
                "LEWTEB141HE103925,\n" +
                "LEWTEB141HF167179,\n" +
                "LEWTEB141HF167215,\n" +
                "LEWTEB142HE103741,\n" +
                "LEWTEB142HE103819,\n" +
                "LEWTEB142HE103917,\n" +
                "LEWTEB142HE103920,\n" +
                "LEWTEB142HF167160,\n" +
                "LEWTEB142HF167191,\n" +
                "LEWTEB142HF167224,\n" +
                "LEWTEB142JN100389,\n" +
                "LEWTEB142JN101266,\n" +
                "LEWTEB142JN201044,\n" +
                "LEWTEB143HE103800,\n" +
                "LEWTEB143HF166924,\n" +
                "LEWTEB143JN100868,\n" +
                "LEWTEB143JN101924,\n" +
                "LEWTEB144HE103756,\n" +
                "LEWTEB144HE103899,\n" +
                "LEWTEB144HE104020,\n" +
                "LEWTEB144JN100670,\n" +
                "LEWTEB144JN100698,\n" +
                "LEWTEB145HE103846,\n" +
                "LEWTEB145HE103913,\n" +
                "LEWTEB145HE103944,\n" +
                "LEWTEB145HE103958,\n" +
                "LEWTEB145HE104009,\n" +
                "LEWTEB145HF167198,\n" +
                "LEWTEB145HF167203,\n" +
                "LEWTEB145JN100614,\n" +
                "LEWTEB146HE103774,\n" +
                "LEWTEB146HE103886,\n" +
                "LEWTEB146HE103905,\n" +
                "LEWTEB146HE103922,\n" +
                "LEWTEB146HE103936,\n" +
                "LEWTEB146HE104021,\n" +
                "LEWTEB146HF167176,\n" +
                "LEWTEB147HE104030,\n" +
                "LEWTEB147JN100839,\n" +
                "LEWTEB148HE103730,\n" +
                "LEWTEB148HE103906,\n" +
                "LEWTEB148HE103968,\n" +
                "LEWTEB148HF166885,\n" +
                "LEWTEB148HF167213,\n" +
                "LEWTEB148HF167227,\n" +
                "LEWTEB148HF167230,\n" +
                "LEWTEB148JN100994,\n" +
                "LEWTEB148JN101238,\n" +
                "LEWTEB148KN100382,\n" +
                "LEWTEB148KN100432,\n" +
                "LEWTEB149HE103784,\n" +
                "LEWTEB149HE104059,\n" +
                "LEWTEB149HF166880,\n" +
                "LEWTEB149HF167172,\n" +
                "LEWTEB149JN402214,\n" +
                "LEWTEB14XHE103860,\n" +
                "LEWTEB14XHE103910,\n" +
                "LEWTEB14XHE103924,\n" +
                "LEWTEB14XKN100058,\n" +
                "LEWWCA325JN400002,\n" +
                "LEWWDA336JN428509,\n" +
                "LEWWDA33XJN428559,\n" +
                "LEWWDB331JN443814,\n" +
                "LEWWDB331JN443876,\n" +
                "LEWWDB331JN443926,\n" +
                "LEWWDB332JN443966,\n" +
                "LEWWDB336JN443842,\n" +
                "LEWWDB336JN443873,\n" +
                "LEWWDB338JN443597,\n" +
                "LEWWDB339JN443947,\n" +
                "LEWWDB33XJN443732,\n" +
                "LEWWEB348JN080109,\n" +
                "LEWXWBJM0KF143929,\n" +
                "LEWXWBJM0KF144014,\n" +
                "LEWXWBJM3KF143679,\n" +
                "LEWXWBJM4KF143920,\n" +
                "LEWXWBJM5KF143358,\n" +
                "LEWXWBJM5KF143361,\n" +
                "LEWXWBJM7KF143183,\n" +
                "LEWXWBJM7KF143359,\n" +
                "LEWXWBJM7KF143362,\n" +
                "LEWXWBJM7KF143930,\n" +
                "LEWXWBJM7KF144012,\n" +
                "LEWXWBJM8KF143192,\n" +
                "LEWXWBJM9KF143427,\n" +
                "LEWXWBJM9KF144013,\n" +
                "LEWXWBJM9KF144044,\n" +
                "LEWXWBJMXKF143372,\n";

        String[] split = s1.replaceAll("\n","").split(",");
        String[] split1 = s2.replaceAll("\n","").split(",");
        List<String> strings = Arrays.asList(split1);
        for (String s : split) {
            if(!strings.contains(s)){
                System.out.println(s);
            }
        }

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
