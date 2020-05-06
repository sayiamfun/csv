package com.sayiamfun.common.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 合并同类型文件
 */
public class MergedDocumentsUtil {

    private static final String readPath = "D:\\车辆数据\\L6T78Y4W4GN084987\\output";
    private static final String outPath = "D:\\车辆数据\\L6T78Y4W4GN084987\\out\\";
    private static final String bodong = "UUID,VIN,滑窗第一帧时间,滑窗当前时间,滑窗第一帧SOC,滑窗当前SOC,异常单体,行驶2或充电1,异常单体所在包编号";
    private static final String shangzhi = "UUID,VIN,当前时间,当前SOC,迭代开始时间,迭代开始SOC,单体包编号,系数异常类型,最大系数单体号,最大系数值,单体异常系数值,排名前的单体代号";
    private static final String yajiang = "UUID,VIN,报警名,上一帧时间,当前帧时间,上一帧SOC,当前帧SOC,上一帧单体,当前帧单体,单体包号,绝对值最大压差值,绝对值最小压差值,压差绝对值最大值编号,压差绝对值最小值编号,最大压差绝对值与最小压差绝对值比,充电1或行驶2";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");


    public static void main(String[] args) {
   /*     Set<String> set1 = new HashSet<>();
        set1.add("1");
        set1.add("2");
        set1.add("3");
        set1.add("4");
        Set<String> set2 = new HashSet<>();
        set2.add("2");
        set2.add("3");
        set2.add("4");
        set2.add("5");
        set1.addAll(set2);
        for (String s : set1) {
            System.out.println(s);
        }*/
        System.out.println("开始读取文件路径");
        ArrayList<String> strings = ScanPackage.scanFilesWithRecursion(readPath);
        Map<String,List<String>> map = new HashMap<>();
        System.out.println("开始对相同日期文件归类");
        for (String string : strings) {
            if(!string.contains(".csv")) continue;
            String basePath = string.substring(string.lastIndexOf("\\")+1, string.lastIndexOf("_"));
            if(map.containsKey(basePath)){
                List<String> strings1 = map.get(basePath);
                strings1.add(string);
                map.put(basePath,strings1);
            }else {
                List<String> strings1 = new ArrayList<>();
                strings1.add(string);
                map.put(basePath,strings1);
            }
        }
        System.out.println(map);
        System.out.println("开始读取并写入文件");
        for (Map.Entry<String, List<String>> stringListEntry : map.entrySet()) {
            List<String> list = new ArrayList<>();
            for (String s : stringListEntry.getValue()) {
                readFileData(s, list);
            }
            writeFileData(list, outPath + stringListEntry.getKey() + "_" + formatter.format(new Date()) + ".csv");
        }
        System.out.println("写入完成");


    }

    /**
     * 写data
     * @param list
     * @param outPath
     */
    private static void writeFileData(List<String> list, String outPath) {
        //CSV文件输出可追加
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            ow = new OutputStreamWriter(new FileOutputStream(new File(outPath),true),ScanPackage.encode);
            bw = new BufferedWriter(ow);
            /*if(outPath.contains("波动性检测模型")){
                bw.write(bodong); //中间，隔开不同的单元格，一次写一行
                bw.newLine();
            }else if(outPath.contains("压降一致性模型")){
                bw.write(yajiang); //中间，隔开不同的单元格，一次写一行
                bw.newLine();
            }else if(outPath.contains("基于熵值的过压故障诊断模型")){
                bw.write(shangzhi); //中间，隔开不同的单元格，一次写一行
                bw.newLine();
            }*/
            for (String s : list) {
                bw.write(s); //中间，隔开不同的单元格，一次写一行
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != bw) bw.close();
                if (null != ow) ow.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读data
     * @param string
     * @param list
     */
    private static void readFileData(String string, List<String> list) {
        //csv文件读取方法
        InputStreamReader ir = null;
        BufferedReader reader = null;
        try {
            ir = new InputStreamReader(new FileInputStream(new File(string)),ScanPackage.UTF_8);
            reader = new BufferedReader(ir);//到读取的文件
            String line = null;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != reader) reader.close();
                if (null != ir) ir.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
