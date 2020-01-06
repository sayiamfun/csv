package com.sayiamfun.StatisticalAnalysisOfResults;

import com.sayiamfun.common.ScanPackage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TestFrequencyStatistics {


    static String inpath = "C:/Users/liwenjie/Downloads/20191227135955/";
    static String outPath = inpath + "out/";

    public static void main(String[] args) {
//        DecimalFormat df = new DecimalFormat("0.000000000");
//        df.setRoundingMode(RoundingMode.HALF_UP);
//        System.out.println(","+df.format(0.00904985));
        test();

    }

    private static void test() {
        FrequencyStatistics frequencyStatistics = new FrequencyStatistics();
        //读取所有的文件路径
        ArrayList<String> strings = ScanPackage.scanFilesWithRecursion(inpath);
        for (String string : strings) {
            if (!string.endsWith(".csv")) continue;
            if (string.contains("压降一致性模型")) {
                frequencyStatistics.getPressureDropConsistencyList().add(string);
            } else if (string.contains("波动性检测模型")) {
                frequencyStatistics.getVolatilityDetectionList().add(string);
            } else if (string.contains("熵值的过压故障诊断模型")) {
                frequencyStatistics.getEntropyList().add(string);
            }
        }

        //开始处理压降一致性数据   因为需要获取单体数量
        System.out.println("开始处理压降一致性文件");
        frequencyStatistics.doPressureDropConsistency(frequencyStatistics);
        System.out.println("开始处理波动一致性文件");
        frequencyStatistics.doVolatilityDetection(frequencyStatistics);
        System.out.println("开始处理熵值一致性文件");
        frequencyStatistics.doEntropy(frequencyStatistics);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        frequencyStatistics.setNowTime(formatter.format(new Date()));   //设置统一输出时间
        System.out.println("开始输出压降一致性结果");
        frequencyStatistics.outPressureDropConsistency(frequencyStatistics,outPath);
        System.out.println("开始输出波动一致性结果");
        frequencyStatistics.outVolatilityDetection(frequencyStatistics,outPath);
        System.out.println("开始输出熵值结果");
        frequencyStatistics.outEntropy(frequencyStatistics,outPath);
        frequencyStatistics.outIcon(frequencyStatistics,outPath);

    }

}
