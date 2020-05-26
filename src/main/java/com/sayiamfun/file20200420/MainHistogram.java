package com.sayiamfun.file20200420;

import com.sayiamfun.StatisticalAnalysisOfResults.FrequencyStatistics;
import com.sayiamfun.common.utils.ScanPackage;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class MainHistogram {

    public static void main(String[] args) {

        Map<String, FrequencyStatistics> map = new HashMap<>();
        //读取所有的文件路径
        ArrayList<String> strings = ScanPackage.scanFilesWithRecursion("D:\\车辆数据\\其他车辆数据\\中\\LS5A2AJX8JA002135\\20200525172845");
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
        //csv文件读取方法
        InputStreamReader ir = null;
        BufferedReader reader = null;
        Map<Integer, Integer> resultMap = new TreeMap<>();
        for (String path : map.get("LS5A2AJX8JA002135").getVolatilityDetectionList()) {
            try {
                ir = new InputStreamReader(new FileInputStream(new File(path)), ScanPackage.encode);
                reader = new BufferedReader(ir);//到读取的文件
                String line = reader.readLine();
                while ((line = reader.readLine()) != null) {
                    String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                    if (new BigDecimal(item[3]).compareTo(new BigDecimal("20200501000000")) >= 0 && new BigDecimal(item[3]).compareTo(new BigDecimal("20200601000000")) < 0) {
                        String[] s = item[6].split("_");
                        for (String s1 : s) {
                            int i = Integer.parseInt(s1);
                            if (resultMap.containsKey(i)) {
                                resultMap.put(i, resultMap.get(i) + 1);
                            } else {
                                resultMap.put(i, 1);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != reader) reader.close();
                    if (null != ir) ir.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(resultMap.keySet());
        System.out.println(resultMap.values());

    }
}