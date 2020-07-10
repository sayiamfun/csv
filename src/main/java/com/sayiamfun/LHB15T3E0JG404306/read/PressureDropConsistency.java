package com.sayiamfun.LHB15T3E0JG404306.read;

import com.sayiamfun.csv.entity.BatteryMonomer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
* @des 压降一致性
* @author  liwenjie
* @date 2019/10/18  13:10
*/
public class PressureDropConsistency {


    //读取文件地址
    public final static String filePath = "D:\\out\\20191017\\source\\LHB15T3E0JG404306_压降一致性判断模型_20191017.csv";
    //输出文件根目录
    public final static String outFilePath = "D:\\out\\20191018\\";
    //字符编码
    public final static String encode = "GBK";
    //数据处理后存储结构
    public static Map<Integer, Map<String, Integer>> map = new TreeMap<>();
    public static Map<String, Integer> numMap = new TreeMap<>();
    public static StringBuffer str = new StringBuffer();
    public static StringBuffer strAll = new StringBuffer();
    //数据时间
//    public final static Integer theDate = 20190307;
//    public final static Integer theDate = 20190214;
    public final static Integer theDate = 0;

    public static void main(String[] args) {
        readFile();
        System.out.println(strAll);
//        outToCSV("LHB15T3E0JG404306压降一致性模型单体异常次数最近一周.csv");
    }
    /**
    * @des 输出数据到CSV文件
    * @author  liwenjie
    * @date 2019/10/18  13:25
    */
    public static void outToCSV(String file1) {
        try {
            File csv = new File(outFilePath + file1); // CSV数据文件
            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(csv), encode);
            BufferedWriter bw = new BufferedWriter(ow);
            bw.write("单体编号,异常次数");
            bw.newLine();
            for (Map.Entry<String, Integer> stringIntegerEntry : numMap.entrySet()) {
                bw.write(stringIntegerEntry.getKey()+","+stringIntegerEntry.getValue());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
    * @des 读取文件数据
    * @author  liwenjie
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
                if(date<theDate) continue;
                /*str.append(",["+item[10]+","+item[11]+"]");
                if(numMap.containsKey(item[12])){
                    numMap.put(item[12],numMap.get(item[12])+1);
                }else {
                    numMap.put(item[12],1);
                }*/
                strAll.append(",["+item[14]+","+item[13]+","+item[12]+"]");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
