package com.sayiamfun.csv;


import com.sayiamfun.common.ScanPackage;

import java.math.BigDecimal;
import java.util.*;

public class Test {

    public static void main(String[] args) {
        int num1 = 0;
        int num2 = 0;
        int num3 = 0;
        ArrayList<String> strings = ScanPackage.scanFilesWithRecursion("C:\\Users\\liwenjie\\Downloads\\20191227183418");
        for (String string : strings) {
            if (string.contains("基于熵值的过压故障诊断模型") && string.contains("日期")) {
                List<String[]> items = ScanPackage.getItems(string);
                num1 += items.size();
            }
        }
        for (String string : strings) {
            if (string.contains("波动性检测模型") && string.contains("日期")) {
                List<String[]> items = ScanPackage.getItems(string);
                num2 += items.size();
            }
        }
        for (String string : strings) {
            if (string.contains("压降一致性模型") && string.contains("日期")) {
                List<List<String>> lists = ScanPackage.getItems1(string);

                //开始统计今天所有数据   将最小压差绝对值小于 0.02 的删除
                Iterator<List<String>> iterator = lists.iterator();
                while (iterator.hasNext()) {
                    List<String> next = iterator.next();
                    //删除不符合条件的数据
                    if (new BigDecimal(next.get(11)).abs().compareTo(new BigDecimal("0.02")) < 0) {
                        iterator.remove();
                    }
                }
                if (lists.size() == 0) continue;
                //排序取最大压差绝对值与最小压差绝对值比最大的五条
                Collections.sort(lists, new Comparator<List<String>>() {
                    @Override
                    public int compare(List<String> o1, List<String> o2) {
                        if (new BigDecimal(o2.get(14)).compareTo(new BigDecimal(o1.get(14))) != 0)
                            return new BigDecimal(o2.get(14)).compareTo(new BigDecimal(o1.get(14)));
                        return new BigDecimal(o2.get(10)).abs().compareTo(new BigDecimal(o1.get(10)).abs());
                    }
                });
                //根据规则获取需要保留的数据
                int size = lists.size();
                if (size > 5) {
                    int index = 5;
                    int index2 = 5;
                    while (index < size && new BigDecimal(lists.get(index).get(14)).compareTo(new BigDecimal(lists.get(index - 1).get(14))) == 0) {
                        index++;
                    }
                    if (index > 5) {
                        while (index2 < index && new BigDecimal(lists.get(index2).get(10)).abs().compareTo(new BigDecimal(lists.get(index2 - 1).get(10)).abs()) == 0) {
                            index2++;
                        }
                    }
                    size = index2;
                }
                num3 += size;
            }
        }
        System.out.println(num1);
        System.out.println(num2);
        System.out.println(num3);
    }

}
