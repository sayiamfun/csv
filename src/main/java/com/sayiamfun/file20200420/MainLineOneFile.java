package com.sayiamfun.file20200420;

import com.sayiamfun.common.utils.ScanPackage;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class MainLineOneFile {

    public static void main(String[] args) {

        InputStreamReader ir = null;
        BufferedReader reader = null;
        String path = "D:\\车辆数据\\其他车辆数据\\LJNEDV1T4JN650206\\LJNEDV1T4JN650206_波动一致性故障诊断模型.csv";


        String startTime = "20200100000000";
        String endTime = "20200201000000";
        List<Map<String, Object>> list = new LinkedList<>();
        Map<String, Object> tmpMap;
        try {
            ir = new InputStreamReader(new FileInputStream(new File(path)), ScanPackage.encode);
            reader = new BufferedReader(ir);//到读取的文件
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                if (new BigDecimal(item[2]).compareTo(new BigDecimal(startTime)) >= 0 && new BigDecimal(item[2]).compareTo(new BigDecimal(endTime)) < 0) {
                    tmpMap = new HashMap<>();
                    tmpMap.put("octime", item[2]);
                    tmpMap.put("stringvars", item[8]);
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


        try {
            ir = new InputStreamReader(new FileInputStream(new File(path)), ScanPackage.encode);
            reader = new BufferedReader(ir);//到读取的文件
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                if (new BigDecimal(item[2]).compareTo(new BigDecimal(startTime)) >= 0 && new BigDecimal(item[2]).compareTo(new BigDecimal(endTime)) < 0 && new BigDecimal(item[7]).compareTo(new BigDecimal("4")) > 0) {
                    tmpMap = new HashMap<>();
                    tmpMap.put("octime", item[2]);
                    tmpMap.put("stringvars", item[8]);
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
        for (int i = 0; i < nums; i++) {
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
        for (Map.Entry<String, Object> stringObjectEntry : maps.entrySet()) {
            System.out.println(stringObjectEntry.getKey() + ":" + stringObjectEntry.getValue() + ",");
        }
        System.out.print("}");
        System.out.println("}");
    }
}
