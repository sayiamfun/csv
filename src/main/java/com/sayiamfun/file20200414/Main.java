package com.sayiamfun.file20200414;

import com.sayiamfun.common.utils.ScanPackage;
import com.sayiamfun.otherfiletomodelinputfile.FileToModelFile;

import java.io.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 统计单体离心率
 */
public class Main {


    public static void main(String[] args) {
        String file = "D:\\车辆数据\\其他车辆数据\\七辆事故车做电压图原始数据\\LB378Y4W7JA178042_analy\\压差－压差.csv";
        String outFile1 = "D:\\车辆数据\\其他车辆数据\\七辆事故车做电压图原始数据\\LB378Y4W7JA178042_analy\\电压-中位数.csv";
        String outFile2 = "D:\\车辆数据\\其他车辆数据\\七辆事故车做电压图原始数据\\LB378Y4W7JA178042_analy\\电压-平均值.csv";

        //csv文件读取方法
        InputStreamReader ir = null;
        BufferedReader reader = null;
        //CSV文件输出可追加
        OutputStreamWriter ow = null;
        OutputStreamWriter ow2 = null;
        BufferedWriter bw = null;
        BufferedWriter bw2 = null;
        try {
            ir = new InputStreamReader(new FileInputStream(new File(file)), ScanPackage.UTF_8);

            reader = new BufferedReader(ir);//到读取的文件
            String line = reader.readLine();
            ow = new OutputStreamWriter(new FileOutputStream(new File(outFile1), true), ScanPackage.GBK);
            ow2 = new OutputStreamWriter(new FileOutputStream(new File(outFile2), true), ScanPackage.GBK);
            bw = new BufferedWriter(ow);
            bw2 = new BufferedWriter(ow2);
            Map<Integer, Integer> map = new TreeMap<>();
            Set<Integer> set1 = new HashSet<>();
            Set<Integer> set0 = new HashSet<>();
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                for (int i = 0; i < split.length; i++) {
                    if ("1".equals(split[i])) {
                        set1.add(i + 1);
                    } else {
                        set0.add(i + 1);
                    }
                }
                if (set0.size() == 0 || set1.size() == 0) continue;
                if (set1.size() > set0.size()) {
                    set1 = set0;
                }
                for (Integer integer : set1) {
                    if (map.containsKey(integer)) {
                        map.put(integer, map.get(integer) + 1);
                    } else {
                        map.put(integer, 1);
                    }
                }
            }
            for (Map.Entry<Integer, Integer> integerIntegerEntry : map.entrySet()) {
                System.out.println(integerIntegerEntry.getKey() + "," + integerIntegerEntry.getValue());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileToModelFile.closeFileStream(ir, reader, ow, bw);
            try {
                if (null != bw2) bw2.close();
                if (null != ow2) ow2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
