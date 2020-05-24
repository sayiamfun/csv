package com.sayiamfun.file20200414;

import com.sayiamfun.common.utils.ScanPackage;

import java.io.*;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

/**
 * 改变电压数据显示
 */
public class CsvToCsv {

    public static void main(String[] args) {
        String file = "D:\\车辆数据\\其他车辆数据\\七辆事故车做电压图原始数据\\LB378Y4W7JA178042_analy\\电压.csv";
        String outFile1 = "D:\\车辆数据\\其他车辆数据\\七辆事故车做电压图原始数据\\LB378Y4W7JA178042_analy\\压差减压差status.csv";

        //csv文件读取方法
        InputStreamReader ir = null;
        BufferedReader reader = null;
        //CSV文件输出可追加
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            ir = new InputStreamReader(new FileInputStream(new File(file)), ScanPackage.UTF_8);

            reader = new BufferedReader(ir);//到读取的文件
            String line = reader.readLine();
            ow = new OutputStreamWriter(new FileOutputStream(new File(outFile1), true), ScanPackage.GBK);
            bw = new BufferedWriter(ow);
            Map<Integer, Integer> map = new TreeMap<>();
            int num1 = 0;
            int num0 = 0;
            StringBuilder stringBuilder;
            while ((line = reader.readLine()) != null) {
                stringBuilder = new StringBuilder(500);
                num1 = 0;
                num0 = 0;
                String[] split = line.split(",");
                for (String s : split) {
                    if (new BigDecimal(s).compareTo(new BigDecimal("0")) > 0) {
                        num1++;
                        stringBuilder.append("1").append(",");
                    } else if (new BigDecimal(s).compareTo(new BigDecimal("0")) < 0) {
                        num0++;
                        stringBuilder.append("0").append(",");
                    } else {
                        stringBuilder.append("&").append(",");
                    }
                }
                String s = "";
                String flag = "1";
                if (num1 > num0) {
                    flag = "0";
                    s = stringBuilder.toString().replaceAll("&", "1");
                } else {
                    s = stringBuilder.toString().replaceAll("&", "1");
                }
                String[] split1 = s.split(",");
                for (int i = 0; i < split1.length; i++) {
                    if (!flag.equals(split1[i])) continue;
                    if (map.containsKey(i + 1)) {
                        map.put(i + 1, map.get(i + 1) + 1);
                    } else {
                        map.put(i + 1, 1);
                    }
                }
            }
            for (Map.Entry<Integer, Integer> integerIntegerEntry : map.entrySet()) {
                System.out.println(integerIntegerEntry.getKey() + "," + integerIntegerEntry.getValue());
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
            try {
                if (null != bw) bw.close();
                if (null != ow) ow.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
