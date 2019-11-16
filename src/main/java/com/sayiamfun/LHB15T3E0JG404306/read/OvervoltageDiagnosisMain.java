package com.sayiamfun.LHB15T3E0JG404306.read;

import com.sayiamfun.csv.entity.BatteryMonomer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author liwenjie
 * @des 过压故障诊断
 * @date 2019/10/18  14:37
 */
public class OvervoltageDiagnosisMain {

    //读取文件地址
    public final static String filePath = "D:\\out\\20191017\\source\\LHB15T3E0JG404306_过压故障诊断模型_20191017.csv";
    //输出文件根目录
    public final static String outFilePath = "D:\\out\\20191018\\";
    //字符编码
    public final static String encode = "GBK";
    //数据处理后存储结构
    public static Map<String, Map<String, Integer>> map = new TreeMap<>();
    public static Map<String, Integer> numMap = new TreeMap<>();
    public static StringBuffer str = new StringBuffer();
    public static StringBuffer strAll = new StringBuffer();
    //数据时间
    public final static Integer theDate = 20190307;
//    public final static Integer theDate = 20190214;
//    public final static Integer theDate = 0;

    public static void main(String[] args) {
        readFile();

        outToCSV("LHB15T3E0JG404306过压故障诊断最大次数最近一周数据.csv");
    }

    /**
     * @des 输出数据到CSV文件
     * @author liwenjie
     * @date 2019/10/18  13:25
     */
    public static void outToCSV(String file1) {
        try {
            File csv = new File(outFilePath + file1); // CSV数据文件
            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(csv), encode);
            BufferedWriter bw = new BufferedWriter(ow);
            bw.write("单体编号,最大值次数,大于4的最大值次数");
            bw.newLine();
            for (Map.Entry<String, Map<String, Integer>> stringMapEntry : map.entrySet()) {
                bw.write(Integer.parseInt(stringMapEntry.getKey()) + 1 + "," + stringMapEntry.getValue().get("0") + "," + stringMapEntry.getValue().get("1"));
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @des 读取文件数据
     * @author liwenjie
     * @date 2019/10/18  13:23
     */
    public static void readFile() {
        try {
            InputStreamReader ir = new InputStreamReader(new FileInputStream(new File(filePath)));
            BufferedReader reader = new BufferedReader(ir);
            String line = null;
            Map<String, BatteryMonomer> childrenMap = new HashMap<>();
            int i = 0;
            while ((line = reader.readLine()) != null) {
                i++;
                if (i <= 2) continue;
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                int date = Integer.parseInt(item[0].substring(0, 8));
                if (date < theDate) continue;
                Map<String, Integer> stringIntegerMap = new HashMap<>();
                if (map.containsKey(item[7])) {
                    stringIntegerMap = map.get(item[7]);
                    stringIntegerMap.put("0", stringIntegerMap.get("0") + 1);
                    if (Double.parseDouble(item[8]) > 4) stringIntegerMap.put("1", stringIntegerMap.get("1") + 1);
                } else {
                    stringIntegerMap.put("0", 1);
                    stringIntegerMap.put("1", 1);
                }
                map.put(item[7], stringIntegerMap);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
