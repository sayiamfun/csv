package com.sayiamfun.StatisticalAnalysisOfResults;

import com.sayiamfun.common.utils.ScanPackage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 测试 统计图表
 */
public class TestFrequencyStatistics {


    static String inpath = "D:\\车辆数据\\其他车辆数据\\20200418\\北京公交\\result\\out\\20200615121824\\";
    static String outPath = inpath + "out/";

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        if (!new File(outPath).exists()) {
            new File(outPath).mkdir();
        }
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
                frequencyStatistics.setVIN(vin);
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

        Map<String, FrequencyStatistics> map2 = mapCopy(map);

        for (Map.Entry<String, FrequencyStatistics> stringFrequencyStatisticsEntry : map.entrySet()) {
            stringFrequencyStatisticsEntry.getValue().setType("2");
            //开始处理压降一致性数据   因为需要获取单体数量
            System.out.println("开始处理压降一致性文件");
            stringFrequencyStatisticsEntry.getValue().doPressureDropConsistencyDayAndNums();
            stringFrequencyStatisticsEntry.getValue().doPressureDropConsistencyWeek();
            System.out.println("开始处理波动一致性文件");
            stringFrequencyStatisticsEntry.getValue().doVolatilityDetectionDayAndNums();
            stringFrequencyStatisticsEntry.getValue().doVolatilityDetectionWeek();
            System.out.println("开始处理熵值一致性文件");
            stringFrequencyStatisticsEntry.getValue().doEntropyDayAndNums();
            stringFrequencyStatisticsEntry.getValue().doEntropyWeek();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            stringFrequencyStatisticsEntry.getValue().setNowTime(formatter.format(new Date()));   //设置统一输出时间

            System.out.println("开始输出压降一致性结果");
            stringFrequencyStatisticsEntry.getValue().outPressureDropConsistency(outPath);
            System.out.println("开始输出波动一致性结果");
            stringFrequencyStatisticsEntry.getValue().outVolatilityDetection(outPath);
            System.out.println("开始输出熵值结果");
            stringFrequencyStatisticsEntry.getValue().outEntropy(outPath);
//            stringFrequencyStatisticsEntry.getValue().outIcon(outPath);
        }

        for (Map.Entry<String, FrequencyStatistics> stringFrequencyStatisticsEntry : map2.entrySet()) {
            stringFrequencyStatisticsEntry.getValue().setType("1");
            //开始处理压降一致性数据   因为需要获取单体数量
            System.out.println("开始处理压降一致性文件");
            stringFrequencyStatisticsEntry.getValue().doPressureDropConsistencyDayAndNums();
            stringFrequencyStatisticsEntry.getValue().doPressureDropConsistencyWeek();
            System.out.println("开始处理波动一致性文件");
            stringFrequencyStatisticsEntry.getValue().doVolatilityDetectionDayAndNums();
            stringFrequencyStatisticsEntry.getValue().doVolatilityDetectionWeek();
            System.out.println("开始处理熵值一致性文件");
            stringFrequencyStatisticsEntry.getValue().doEntropyDayAndNums();
            stringFrequencyStatisticsEntry.getValue().doEntropyWeek();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            stringFrequencyStatisticsEntry.getValue().setNowTime(formatter.format(new Date()));   //设置统一输出时间

            System.out.println("开始输出压降一致性结果");
            stringFrequencyStatisticsEntry.getValue().outPressureDropConsistency(outPath);
            System.out.println("开始输出波动一致性结果");
            stringFrequencyStatisticsEntry.getValue().outVolatilityDetection(outPath);
            System.out.println("开始输出熵值结果");
            stringFrequencyStatisticsEntry.getValue().outEntropy(outPath);
//            stringFrequencyStatisticsEntry.getValue().outIcon(outPath);
        }
        System.out.println("-----------------------结束-------------------------------");
    }

    public static Map<String,FrequencyStatistics> mapCopy(Map<String,FrequencyStatistics> map){
        Map<String,FrequencyStatistics> resultMap = new HashMap<>();
        for (Map.Entry<String, FrequencyStatistics> stringFrequencyStatisticsEntry : map.entrySet()) {
            resultMap.put(stringFrequencyStatisticsEntry.getKey(),new FrequencyStatistics(stringFrequencyStatisticsEntry.getValue()));
        }
        return resultMap;
    }

}
