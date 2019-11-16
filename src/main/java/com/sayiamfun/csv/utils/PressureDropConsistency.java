package com.sayiamfun.csv.utils;

import com.sayiamfun.csv.entity.BatteryMonomer;
import com.sayiamfun.csv.entity.Pressure;
import com.sayiamfun.csv.entity.ThreeValue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author liwenjie
 * @date 2019/10/11 + 10:14
 * @describe
 */
public class PressureDropConsistency {


    public static void differenceRatio(LinkedList<ThreeValue> list, String filePath) {
        try {
            if (!filePath.contains("压降一致性")) return;
            if(Integer.parseInt(filePath.substring(filePath.indexOf("日期")+3,filePath.indexOf("日期")+11))<20190724) return;
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));//换成你的文件名
//            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            boolean b = true;
            while ((line = reader.readLine()) != null) {
                if (b) {
                    b = false;
                    continue;
                }
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                if (!"2".equals(item[15])) continue;
                ThreeValue threeValue = new ThreeValue();
                threeValue.setMax(item[12]);
                threeValue.setMin(item[13]);
                threeValue.setDiv(item[14]);
                list.add(threeValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
    *@describe  统计单体编号出现最大压差和最小压差的次数
    *@author liwenjie
    *@date  2019/10/11   10:49
    *@params [filePath]
    *@return void
    */
    public static void getNum(Map<String,Map<String,Integer>> map, String filePath) {
        try {
            if (!filePath.contains("压降一致性")) return;
            if(Integer.parseInt(filePath.substring(filePath.indexOf("日期")+3,filePath.indexOf("日期")+11))<20190700) return;
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));//换成你的文件名
//            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            boolean b = true;
            while ((line = reader.readLine()) != null) {
                if (b) {
                    b = false;
                    continue;
                }
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                if (!"2".equals(item[15])) continue;
//                if(Long.valueOf(item[3])<20190724105913L) continue;;
                Map<String, Integer> map1 = map.get("1");
                Map<String, Integer> map2 = map.get("2");
                if(map1.containsKey(item[12])){
                    map1.put(item[12],map1.get(item[12])+1);
                }else {
                    map1.put(item[12],1);
                }
                if(map2.containsKey(item[13])){
                    map2.put(item[13],map2.get(item[13])+1);
                }else {
                    map2.put(item[13],1);
                }
                map.put("1",map1);
                map.put("2",map2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
    *@describe  压降一致性最大最小打点集合
    *@author liwenjie
    *@date  2019/10/11   10:15
    *@params [map, filePath]
    *@return void
    */
    public static void differenceValueOfMaxAndMin(List<Pressure> list, String filePath) {
        try {
            if (!filePath.contains("压降一致性")) return;
            if(Integer.parseInt(filePath.substring(filePath.indexOf("日期")+3,filePath.indexOf("日期")+11))<20190700) return;
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));//换成你的文件名
//            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            boolean b = true;
            while ((line = reader.readLine()) != null) {
                if (b) {
                    b = false;
                    continue;
                }
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                if (!"2".equals(item[15])) continue;
                Pressure pressure = new Pressure();
                pressure.setMax(Double.parseDouble(item[10]));
                pressure.setMin(Double.parseDouble(item[11]));
                list.add(pressure);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
