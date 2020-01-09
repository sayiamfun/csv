package com.sayiamfun.StatisticalAnalysisOfResults;

import com.sayiamfun.common.ScanPackage;

import java.text.SimpleDateFormat;
import java.util.*;


public class TestFrequencyStatistics {


    static String inpath = "C:\\Users\\liwenjie\\Downloads\\20200108171936/";
    static String outPath = inpath + "out/";

    public static void main(String[] args) {
//        DecimalFormat df = new DecimalFormat("0.000000000");
//        df.setRoundingMode(RoundingMode.HALF_UP);
//        System.out.println(","+df.format(0.00904985));
        test();
    }

    private static void test() {
        Map<String, FrequencyStatistics> map = new HashMap<>();
        //读取所有的文件路径
        ArrayList<String> strings = ScanPackage.scanFilesWithRecursion(inpath);
        Collections.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] s = o1.split("_");
                String[] s1 = o2.split("_");
                return Integer.parseInt(s[1]) - Integer.parseInt(s1[1]);
            }
        });
        for (String string : strings) {
            if (!string.endsWith(".csv")) continue;
            String[] s = string.split("_");
            String vin = s[0].substring(s[0].length() - 17);
            if (map.containsKey(vin)) {
                if (string.contains("压降一致性故障诊断模型")) {
                    map.get(vin).getPressureDropConsistencyList().add(string);
                } else if (string.contains("波动一致性故障诊断模型")) {
                    map.get(vin).getVolatilityDetectionList().add(string);
                } else if (string.contains("熵值故障诊断模型")) {
                    map.get(vin).getEntropyList().add(string);
                }
            } else {
                FrequencyStatistics frequencyStatistics = new FrequencyStatistics();
                if (string.contains("压降一致性故障诊断模型")) {
                    frequencyStatistics.getPressureDropConsistencyList().add(string);
                } else if (string.contains("波动一致性故障诊断模型")) {
                    frequencyStatistics.getVolatilityDetectionList().add(string);
                } else if (string.contains("熵值故障诊断模型")) {
                    frequencyStatistics.getEntropyList().add(string);
                }
                map.put(vin, frequencyStatistics);
            }

        }
        for (Map.Entry<String, FrequencyStatistics> stringFrequencyStatisticsEntry : map.entrySet()) {
            //开始处理压降一致性数据   因为需要获取单体数量
            System.out.println("开始处理压降一致性文件");
            stringFrequencyStatisticsEntry.getValue().doPressureDropConsistency();
            System.out.println("开始处理波动一致性文件");
            stringFrequencyStatisticsEntry.getValue().doVolatilityDetection();
            System.out.println("开始处理熵值一致性文件");
            stringFrequencyStatisticsEntry.getValue().doEntropy();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            stringFrequencyStatisticsEntry.getValue().setNowTime(formatter.format(new Date()));   //设置统一输出时间
            System.out.println("开始输出压降一致性结果");
            stringFrequencyStatisticsEntry.getValue().outPressureDropConsistency(outPath);
            System.out.println("开始输出波动一致性结果");
            stringFrequencyStatisticsEntry.getValue().outVolatilityDetection(outPath);
            System.out.println("开始输出熵值结果");
            stringFrequencyStatisticsEntry.getValue().outEntropy(outPath);
            stringFrequencyStatisticsEntry.getValue().outIcon(outPath);
        }


    }

}
