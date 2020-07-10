package com.sayiamfun.csv.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liwenjie
 * @date 2019/10/12 + 11:15
 * @describe
 */
public class EntropyDo {

    /**
     * @return void
     * @describe 获取熵值
     * @author liwenjie
     * @date 2019/10/12   11:19
     * @params [list, filePath]
     */
    public static void getNum(Map<String, Map<String, Integer>> map, String filePath) {
        try {
            if (!filePath.contains("基于熵值的过压故障诊断模型")) return;
            if (Integer.parseInt(filePath.substring(filePath.indexOf("日期") + 3, filePath.indexOf("日期") + 11)) < 20190724) return;
            Map<String, Integer> childrenMap = new HashMap<>();
            String substring = filePath.substring(filePath.indexOf("日期") + 3, filePath.indexOf("日期") + 11);
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
                if (Double.parseDouble(item[9]) >= 4) {
                    if (childrenMap.containsKey(item[8])) {
                        childrenMap.put(item[8], childrenMap.get(item[8]) + 1);
                    } else {
                        childrenMap.put(item[8], 1);
                    }
                }
            }
            map.put(substring, childrenMap);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
