package com.sayiamfun.file20200414;

import com.sayiamfun.common.utils.ScanPackage;
import com.sayiamfun.otherfiletomodelinputfile.FileToModelFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * 统计单体离心率
 */
public class MonTotal {


    public static void main(String[] args) {
        String file = "D:\\车辆数据\\其他车辆数据\\七辆事故车做电压图原始数据\\LB378Y4W1HA083647_result\\LB378Y4W1HA083647_result.csv";
        String outfile = "D:\\车辆数据\\其他车辆数据\\七辆事故车做电压图原始数据\\LB378Y4W1HA083647_result\\LB378Y4W1HA083647_V_190627_190705.csv";
        //csv文件读取方法
        InputStreamReader ir = null;
        BufferedReader reader = null;

        //CSV文件输出可追加
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;

        try {
            ir = new InputStreamReader(new FileInputStream(new File(file)), ScanPackage.UTF_8);
            reader = new BufferedReader(ir);//到读取的文件

            ow = new OutputStreamWriter(new FileOutputStream(new File(outfile), true), ScanPackage.encode);
            bw = new BufferedWriter(ow);


            String line = reader.readLine();
            line = reader.readLine();
            String[] latV = null;
            Map<Integer, Integer> map = new TreeMap<>();
            int total = 0;
            List<String> strings = Arrays.asList("6,17,29,41,53,65,76,86,91".split(","));
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                if (new BigDecimal(split[1]).compareTo(new BigDecimal("20190627000000")) < 0) continue;
                String[] s = split[63].split("_");
                BigDecimal bigDecimal = new BigDecimal(split[40]).divide(new BigDecimal("1000"));
                BigDecimal bigDecimal2 = new BigDecimal(split[43]).divide(new BigDecimal("1000"));
                bw.write(String.join(",", s)+","+bigDecimal+","+bigDecimal2+","+bigDecimal.subtract(bigDecimal2)); //中间，隔开不同的单元格，一次写一行
                bw.newLine();
                bw.flush();
//                Map<Integer, BigDecimal> map1 = new HashMap<>();
//                Map<Integer, BigDecimal> map0 = new HashMap<>();
//                if (latV != null) {
//                    for (int i = 0; i < s.length; i++) {
//                        if(strings.contains(""+(i+1))) continue;
//                        BigDecimal subtract = new BigDecimal(s[i]).subtract(new BigDecimal(latV[i]));
//                        if (subtract.compareTo(new BigDecimal("0")) > 0) {
//                            map1.put(i + 1, subtract);
//                        } else if (subtract.compareTo(new BigDecimal("0")) < 0) {
//                            map0.put(i + 1, subtract);
//                        }
//                    }
//                    Map<Integer, BigDecimal> tmpMap = new HashMap<>();
//                    if (map0.size() == 0 || map1.size() == 0) continue;
//                    if (map1.size() > map0.size()) {
//                        tmpMap = getTen(map0);
//                    } else {
//                        tmpMap = getTen(map1);
//                    }
//                    for (Map.Entry<Integer, BigDecimal> integer : tmpMap.entrySet()) {
//                        if (map.containsKey(integer.getKey())) {
//                            map.put(integer.getKey(), map.get(integer.getKey()) + 1);
//                        } else {
//                            map.put(integer.getKey(), 1);
//                        }
//                    }
//                }
//                latV = s;
//                if (total % 10000 == 0) System.out.print(".");
                total++;
            }
            System.out.println(total);
//            for (Map.Entry<Integer, Integer> integerIntegerEntry : map.entrySet()) {
//                System.out.println(integerIntegerEntry.getKey() + "," + integerIntegerEntry.getValue());
//            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileToModelFile.closeFileStream(ir, reader, ow, bw);
        }
    }

    private static Map<Integer, BigDecimal> getTen(Map<Integer, BigDecimal> map1) {
        if (map1.size() <= 10) return map1;
        ArrayList<Map.Entry<Integer, BigDecimal>> entries = new ArrayList<>(map1.entrySet());
        Collections.sort(entries, (o1, o2) -> {
            return o1.getValue().compareTo(o2.getValue());
        });
        int end = 10;
        while (end + 1 < map1.size()) {
            if (entries.get(end).getValue().compareTo(entries.get(end + 1).getValue()) == 0) {
                end++;
            } else {
                break;
            }
        }
        Map<Integer, BigDecimal> result = new HashMap<>();
        List<Map.Entry<Integer, BigDecimal>> entries1 = entries.subList(0, end);
        entries1.stream().forEach(o -> result.put(o.getKey(), o.getValue()));
        return result;
    }

}
