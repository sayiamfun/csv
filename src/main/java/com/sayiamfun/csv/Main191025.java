package com.sayiamfun.csv;

import com.sayiamfun.common.utils.ScanPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class Main191025 {

    public final static String filePath = "D:\\文档资料\\预警\\车辆明细数据导出(1)\\新建文件夹 (2)";
    public static Double[] lastData = new Double[200];
    public static DecimalFormat df = new DecimalFormat("#.00");


    public static  String temp = "{\n"+
            "name:*,\n"+
            "type:'line',\n"+
            "data:[#]\n"+
    "}";

    public static void main(String[] args) throws IOException {

        Map<String, List<String>> map = new HashMap<>();
        List<Long> datelist  = new ArrayList<>();
        ArrayList<String> strings = ScanPackage.scanFilesWithRecursion(filePath);
        for (String string : strings) {
            BufferedReader reader = new BufferedReader(new FileReader(new File(string)));//换成你的文件名
            //            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            boolean b = true;
            while ((line = reader.readLine()) != null) {
                if (b) {
                    b = false;
                    continue;
                }
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                if(Long.valueOf(item[2])<20190700000000L) continue;
                String[] s = item[8].split("_");
                for (int i = 0; i < s.length; i++) {
                    if(null == s[i] || "".equals(s[i])) continue;
                    if(!datelist.contains(Long.valueOf(item[2])))
                        datelist.add(Long.valueOf(item[2]));
                    if(null == lastData[i]){
                        lastData[i] = Double.parseDouble(s[i]);
                    }else {
                        String format = "" + div(Double.parseDouble(s[i]) - lastData[i], lastData[i], 3);
                        if(map.containsKey(""+(i+1))){
                            map.get(""+(i+1)).add(s[i]);
                        }else {
                            List<String> list = new LinkedList<>();
                            list.add(s[i]);
                            map.put(""+(i+1),list);
                        }
                        lastData[i] = Double.parseDouble(s[i]);
                    }
                }
            }
        }
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, List<String>> stringListEntry : map.entrySet()) {
            String data = "";
            for (String s : stringListEntry.getValue()) {
                data += ","+s;
            }
            buffer.append(","+temp.replace("*","'"+Integer.parseInt(stringListEntry.getKey())+"'").replace("#",data.substring(1)));
        }
        System.out.println(buffer);
        StringBuffer x = new StringBuffer();
        Collections.sort(datelist);
        for (Long aLong : datelist) {
            x.append(",'"+aLong+"'");
        }
        System.out.println(x);

    }

    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }



}
