package com.sayiamfun.MonomerResistanceCompare;

import com.sayiamfun.common.utils.ScanPackage;
import lombok.Data;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/***
 * 计算压差变化率方法
 * 苏朝磊 提供的计算方法
 *
 *
 * 所有数据区分充放电  只抽停车充电（数据时间长度必须大于60帧）分段
 *
 * 取每段  不同SOC的数据片段（计算样本）
 *
 * 对数据样本每一帧 Vmax - Vmin  得到 V差  获取此片段最大和最小的  V差
 *
 * 一个充电段一条数据结果   vin，充电起始时间，Tmx，Tmin，行驶里程，起始SOC，V差max-10%  I和SOC和V差 ,V差min-10%  I和SOC和V差 ，V差max-50%  I和SOC和V差 ，V差min-50%  I和SOC和V差   ，V差max-90%  I和SOC和V差 ，V差min-90%  I和SOC和V差  ,V差max->98%  I和SOC和V差    ，V差min->98%  I和SOC和V差 ,
 *
 *
 * 对得到的数据进行第一轮筛选，相邻两帧数据必须大于15天
 *
 * 第二轮筛选 ，只保留有50%SOC的数据
 *
 * 上一帧的V差 减去 下一帧 V差  除以  时间间隔天数
 *
 *
 * vin，上一帧充电开始时间，下一帧充电开始时间，时间间隔差（天），V差max-10% V差的差值，
 */
public class PressureDifferenceRateAnalysis {

    /**
     * 压差最大有效值
     */
    public static double maxSub = 0.15;
    /**
     * 时间差最大有效值
     */
    public static int maxSubT = 10;
    /**
     * 一段帧数
     */
    public static int oneSum = 4000;
    /**
     * 读取文件总条数 最后多少条
     */
    public static int totalSum = 40000;
    /**
     * 不参与计算的单体
     */
    public static List<String> lostMon = new ArrayList<>();

    public static double defaultDouble = -9000.0;

    public static int defaultInt = -9000;


    /**
     * 初始化不参与计算的单体列表
     */
    static {
        lostMon = Arrays.asList("6,17,29,41,53,65,76,86,91".split(","));
//        lostMon = Arrays.asList("".split(","));
    }

    /**
     * 最后结果只写一次表头
     */
    public static boolean first = true;


    private static String maxSubV = "maxSubV";  //最大 △V   本帧最大减去本帧最小
    private static String maxSubVSOC = "maxSubVSOC";//最大 △V SOC
    private static String maxSubVI = "maxSubVI";//最大 △V  I
    private static String minSubV = "minSubV";//最小 △V
    private static String minSubVSOC = "minSubVSoc";//最小 △V SOC
    private static String minSubVI = "minSubI";//最小 △V I
    private static String monList = "monList";//单体编号列表（阻值较大）
    private static String avargeSubV;// △V 均值


    public static void main(String[] args) throws IOException {

        String inputPath = "/Volumes/UsbDisk/veh_49_originData/Volumes/UsbDisk/";
        String inputPath1 = "/Volumes/UsbDisk/data_analysis2/";
        String outPath = "/Users/liwenjie/Downloads/vehData/vehOut/result/";


        Set<String> strings = ScanPackage.scanDirectory(inputPath);
        Map<String, List<String>> map = new HashMap<>();
        for (String string : strings) {
            if (string.contains("/base64") && string.contains("/year=2020")) {
                String[] split = string.split("/");
                String vin = split[split.length - 4];
                if (map.containsKey(vin)) {
                    map.get(vin).add(string);
                } else {
                    List<String> tmpList = new ArrayList<>();
                    tmpList.add(string);
                    map.put(vin, tmpList);
                }
            }
        }
        int i = 0;
        for (Map.Entry<String, List<String>> stringListEntry : map.entrySet()) {
            i++;
            System.out.println("第" + i + "辆车开始分析，vin:" + stringListEntry.getKey());
            outResultDataByInputPath(stringListEntry.getValue(), outPath);
            if (i > 0) break;
        }


    }


    /**
     * 根据输入路径输出结果
     *
     * @param strings
     * @param outPath
     * @return void
     * @author liwenjie
     * @creed: Talk is cheap,show me the code
     * @date 2020/7/9 5:30 下午
     */
    private static void outResultDataByInputPath(List<String> strings, String outPath) {
        Map<Long, List<String>> vehData = new TreeMap<>();
        for (String s : strings) {
            if (s.contains("/vehicle")) {
                Set<String> strings1 = ScanPackage.scanThisDirectoryFile(s);
                for (String s1 : strings1) {
                    Map<Long, List<String>> items1 = ScanPackage.getMapItems1(s1, 2, totalSum);
                    vehData.putAll(items1);
                }
            }
        }
        if (vehData.size() == 0) return;

        outResultData(outPath, vehData);

    }

    private static void outResultData(String outPath, Map<Long, List<String>> vehData) {
        List<FirstResultData> resultList = new LinkedList<>();
        List<Map.Entry<Long, List<String>>> tmpList = new LinkedList<>();
        for (Map.Entry<Long, List<String>> longListEntry : vehData.entrySet()) {
            if ("停车充电".equals(longListEntry.getValue().get(4))) {
                tmpList.add(longListEntry);
            } else {
                if (tmpList.size() < 60) {
                    tmpList = new LinkedList<>();
                    continue;
                }
                FirstResultData firstResultData = new FirstResultData();
                firstResultData.setVin(tmpList.get(0).getValue().get(0));
                firstResultData.setStartTime(Long.valueOf(tmpList.get(0).getKey()));
                firstResultData.setEndTime(Long.valueOf(tmpList.get(tmpList.size() - 1).getKey()));
                firstResultData.setStartSOC(tmpList.get(0).getValue().get(10));
                firstResultData.setEndSOC(tmpList.get(tmpList.size() - 1).getValue().get(10));
                firstResultData.setMils(tmpList.get(0).getValue().get(7));
                firstResultData.setTmax(tmpList.get(0).getValue().get(25));
                firstResultData.setTmin(tmpList.get(0).getValue().get(28));
                for (Map.Entry<Long, List<String>> listEntry : tmpList) {
                    List<String> value = listEntry.getValue();
                    Double SOC = Double.valueOf(value.get(10));
                    String I = value.get(9);
                    BigDecimal vMax = new BigDecimal(value.get(19));
                    BigDecimal vMin = new BigDecimal(value.get(22));
                    BigDecimal subtract = vMax.subtract(vMin);
                    if (SOC == 10) {
                        BigDecimal soc_10_vsubMax = firstResultData.getSOC_10_VsubMax();
                        if (soc_10_vsubMax == null || subtract.compareTo(soc_10_vsubMax) > 0) {
                            firstResultData.setSOC_10_VsubMax(subtract);
                            firstResultData.setSOC_10_VsubMaxI(I);
                        }
                        BigDecimal soc_10_vsubMin = firstResultData.getSOC_10_VsubMin();
                        if (soc_10_vsubMin == null || subtract.compareTo(soc_10_vsubMin) < 0) {
                            firstResultData.setSOC_10_VsubMin(soc_10_vsubMin);
                            firstResultData.setSOC_10_VsubMinI(I);
                        }
                    } else if (SOC == 50) {
                        BigDecimal soc_50_vsubMax = firstResultData.getSOC_50_VsubMax();
                        if (soc_50_vsubMax == null || subtract.compareTo(soc_50_vsubMax) > 0) {
                            firstResultData.setSOC_50_VsubMax(subtract);
                            firstResultData.setSOC_50_VsubMaxI(I);
                        }
                        BigDecimal soc_50_vsubMin = firstResultData.getSOC_50_VsubMin();
                        if (soc_50_vsubMin == null || subtract.compareTo(soc_50_vsubMin) < 0) {
                            firstResultData.setSOC_50_VsubMin(subtract);
                            firstResultData.setSOC_50_VsubMinI(I);
                        }
                    } else if (SOC == 90) {
                        BigDecimal soc_90_vsubMax = firstResultData.getSOC_90_VsubMax();
                        if (soc_90_vsubMax == null || subtract.compareTo(soc_90_vsubMax) > 0) {
                            firstResultData.setSOC_90_VsubMax(subtract);
                            firstResultData.setSOC_90_VsubMaxI(I);
                        }
                        BigDecimal soc_90_vsubMin = firstResultData.getSOC_90_VsubMin();
                        if (soc_90_vsubMin == null || subtract.compareTo(soc_90_vsubMin) < 0) {
                            firstResultData.setSOC_90_VsubMin(subtract);
                            firstResultData.setSOC_90_VsubMinI(I);
                        }
                    } else if (SOC >= 98) {
                        BigDecimal soc_98_vsubMax = firstResultData.getSOC_98_VsubMax();
                        if (soc_98_vsubMax == null || subtract.compareTo(soc_98_vsubMax) > 0) {
                            firstResultData.setSOC_98_VsubMax(subtract);
                            firstResultData.setSOC_98_VsubMaxI(I);
                        }
                        BigDecimal soc_98_vsubMin = firstResultData.getSOC_98_VsubMin();
                        if (soc_98_vsubMin == null || subtract.compareTo(soc_98_vsubMin) < 0) {
                            firstResultData.setSOC_98_VsubMin(subtract);
                            firstResultData.setSOC_98_VsubMinI(I);
                        }
                    }
                }
                resultList.add(firstResultData);
                tmpList = new LinkedList<>();
            }
        }
        resultList.stream().forEach(System.out::println);
        System.out.println(resultList.size());
        Iterator<Map.Entry<Long, List<String>>> iterator = tmpList.iterator();

        while (iterator.hasNext()){
            Map.Entry<Long, List<String>> next = iterator.next();
            Calendar calendar = Calendar.getInstance();
        }
    }


}

@Data
class FirstResultData {

    private String vin;
    private Long startTime;
    private Long endTime;
    private String Tmax;
    private String Tmin;
    private String mils;
    private String startSOC;
    private String endSOC;
    private BigDecimal SOC_10_VsubMax;
    private String SOC_10_VsubMaxI;
    private BigDecimal SOC_10_VsubMin;
    private String SOC_10_VsubMinI;
    private BigDecimal SOC_50_VsubMax;
    private String SOC_50_VsubMaxI;
    private BigDecimal SOC_50_VsubMin;
    private String SOC_50_VsubMinI;
    private BigDecimal SOC_90_VsubMax;
    private String SOC_90_VsubMaxI;
    private BigDecimal SOC_90_VsubMin;
    private String SOC_90_VsubMinI;
    private BigDecimal SOC_98_VsubMax;
    private String SOC_98_VsubMaxI;
    private BigDecimal SOC_98_VsubMin;
    private String SOC_98_VsubMinI;


}


