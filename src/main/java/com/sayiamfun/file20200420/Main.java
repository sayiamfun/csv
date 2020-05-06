package com.sayiamfun.file20200420;

import com.sayiamfun.StatisticalAnalysisOfResults.FrequencyStatistics;
import com.sayiamfun.common.utils.ScanPackage;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.map.util.JSONPObject;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Map<String, FrequencyStatistics> map = new HashMap<>();
        //读取所有的文件路径
        ArrayList<String> strings = ScanPackage.scanFilesWithRecursion("D:\\车辆数据\\其他车辆数据\\20200420\\结果");
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

        List<Map<String, Object>> list = new LinkedList<>();
        Map<String, Object> tmpMap;
        //csv文件读取方法
        InputStreamReader ir = null;
        BufferedReader reader = null;

        for (String path : map.get("LVCB4L4D7KM001717").getEntropyList()) {
            try {
                ir = new InputStreamReader(new FileInputStream(new File(path)), ScanPackage.encode);
                reader = new BufferedReader(ir);//到读取的文件
                String line = reader.readLine();
                while ((line = reader.readLine()) != null) {
                    String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                    if (new BigDecimal(item[2]).compareTo(new BigDecimal("20190824000000")) < 0 && new BigDecimal(item[9]).compareTo(new BigDecimal("4"))>0) {
                        tmpMap = new HashMap<>();
                        tmpMap.put("octime", item[2]);
                        tmpMap.put("stringvars", item[10]);
                        list.add(tmpMap);
                    }
                }
            } catch (
                    IOException e) {
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

        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> maps = new HashMap<>();
        String maxentropy = "0";
        Integer maxentropyName = 0;
        int nums = 0;
        for (Map<String, Object> tmpmap : list) {
            String[] split = tmpmap.get("stringvars").toString().split("_");
            if (nums == 0) {
                nums = split.length;
            }
            String s = Arrays.stream(split).max((o1, o2) -> new BigDecimal(o1).abs().compareTo(new BigDecimal(o2).abs())).get();
            if (new BigDecimal(s).abs().compareTo(new BigDecimal(maxentropy).abs()) > 0) {
                maxentropy = s;
                maxentropyName = Arrays.asList(split).indexOf(s);
            }
            maps.put(tmpmap.get("octime").toString(), "[" + String.join(",", split) + "]");
        }

        LinkedList<Integer> nameList = new LinkedList<>();
        for (
                int i = 0;
                i < nums; i++) {
            nameList.add(i + 1);
        }
        System.out.println("{");
        resultMap.put("linenameList", maps.keySet());
        resultMap.put("nameList", nameList);
        resultMap.put("maxentropy", maxentropy);
        resultMap.put("maxentropyName", maxentropyName);
        resultMap.put("numName", maxentropyName + 1);
        resultMap.put("dataList", maps);
        System.out.println("linenameList:" + resultMap.get("linenameList") + ",");
        System.out.println("nameList:" + resultMap.get("nameList") + ",");
        System.out.println("maxentropy:" + resultMap.get("maxentropy") + ",");
        System.out.println("maxentropyName:" + resultMap.get("maxentropyName") + ",");
        System.out.println("numName:" + resultMap.get("numName") + ",");
        System.out.print("dataList:{");
        for (
                Map.Entry<String, Object> stringObjectEntry : maps.entrySet()) {
            System.out.println(stringObjectEntry.getKey() + ":" + stringObjectEntry.getValue() + ",");
        }
        System.out.print("}");
        System.out.println("}");
    }
}
