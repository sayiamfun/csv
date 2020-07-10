package com.sayiamfun.csv;

import com.sayiamfun.common.utils.ScanPackage;
import com.sayiamfun.csv.utils.EntropyDo;

import java.text.DecimalFormat;
import java.util.*;

/**
 * @author liwenjie
 * @date 2019/10/12 + 11:19
 * @describe  处理熵值系数文件
 */
public class EntropyMain {

    public static void main(String[] args) {
        getSum();
//        String[] split = "20190708,20190709,20190711,20190712,20190715,20190719,20190720,20190723,20190725,20190726,20190727,20190729,20190730,20190731".split(",");
//        List<String> dates = Arrays.asList(split);
//        String[] split1 = "1,3,35,48,54,66,67,71,77,80,91,135".split(",");
//        List<String> codes = Arrays.asList(split1);
//        System.out.println(codes.get(0));
//        System.out.println(codes.get(3));
//        System.out.println(codes.get(6));
    }

    /**
     * @return void
     * @describe 统计一个月内熵值系数大于4的单体出现次数
     * @author liwenjie
     * @date 2019/10/14   9:32
     * @params []
     */
    private static void getSum() {
        try {
            Map<String, Map<String, Integer>> map = new HashMap<>();
            DecimalFormat df = new DecimalFormat("#########.00");
            ArrayList<String> strings = ScanPackage.scanFilesWithRecursion("D:\\文档资料\\预警\\20191009182230");
            for (String string : strings) {
                EntropyDo.getNum(map, string);
            }
            String[] split = "20190725,20190726,20190727,20190729,20190730,20190731".split(",");
            List<String> dates = Arrays.asList(split);
            String[] split1 = "3,48,67,71,77,80".split(",");
            List<String> codes = Arrays.asList(split1);
//            soutDatesAndCodes(map);
//            Outs.toEnrropyCSV(map, "单体熵值系数大于4的单体统计.xls");
            StringBuilder stringBuilder = new StringBuilder();
            boolean b = false;
            for (Map.Entry<String, Map<String, Integer>> stringMapEntry : map.entrySet()) {
                for (Map.Entry<String, Integer> stringIntegerEntry : stringMapEntry.getValue().entrySet()) {
                    if(b) stringBuilder.append(",");
                    b = true;
                    stringBuilder.append("["+codes.indexOf(stringIntegerEntry.getKey())+","+dates.indexOf(stringMapEntry.getKey())+","+stringIntegerEntry.getValue()+"]");
                }
            }
            System.out.println("var data = ["+stringBuilder.toString()+"]");
            soutDatesAndCodes(map);
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
    /**
    *@describe  打印时间和单体编号   X轴和Y轴数据
    *@author liwenjie
    *@date  2019/10/14   10:38
    *@params [map]
    *@return void
    */
    private static void soutDatesAndCodes(Map<String, Map<String, Integer>> map) {
        Set<Integer> date = new TreeSet<>();
        Set<Integer> code = new TreeSet<>();
        for (Map.Entry<String, Map<String, Integer>> stringMapEntry : map.entrySet()) {
            date.add(Integer.parseInt(stringMapEntry.getKey()));
            for (Map.Entry<String, Integer> stringIntegerEntry : stringMapEntry.getValue().entrySet()) {
                code.add(Integer.parseInt(stringIntegerEntry.getKey()));
            }
        }
        String hour = "var hours = [";
        int i = 0;
        for (Integer integer : date) {
            if (i > 0) hour += ",";
            i++;
            hour += "'" + integer + "'";
        }
        System.out.println(hour + "]");
        String days = "var days = [";
        i = 0;
        for (Integer integer : code) {
            if (i > 0) days += ",";
            i++;
            days += "'" + integer + "'";
        }
        System.out.println(days + "]");
    }
}
