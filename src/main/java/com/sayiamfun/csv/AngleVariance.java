package com.sayiamfun.csv;

import com.sayiamfun.common.utils.ScanPackage;

import java.io.*;
import java.util.*;

/**
 * @author liwenjie
 * @date 2019/10/14 + 11:25
 * @describe 处理角度方差文件
 */
public class AngleVariance {

    public static void main(String[] args) {
        method();
    }

    public static void method() {
        try {
            ArrayList<String> strings = ScanPackage.scanFilesWithRecursion("D:\\文档资料\\预警\\20191009182230");
            Map<Integer, String> map = new HashMap<>();  //第二次处理数据集合
            List<String[]> list = getLinkedList(strings);
            for (String[] strings1 : list) {
                for (int i = 0; i < strings1.length; i++) {
                    if (map.containsKey(i)) {
                        map.put(i, map.get(i) + "," + strings1[i]);
                    } else {
                        map.put(i, strings1[i]);
                    }
                }
            }
            String muban = "{\n" +
                    "name:'*',\n" +
                    "type:'line',\n" +
                    "data:[#]\n" +
                    " }\n";
            StringBuilder stringBuilder = new StringBuilder();
            int i = 1;
            for (Map.Entry<Integer, String> integerStringEntry : map.entrySet()) {
                if (i > 1) stringBuilder.append(",");
                String temp = muban;
                String replace = temp.replace("*", "" + i + "号单体");
                String replace1 = replace.replace("#", integerStringEntry.getValue());
                stringBuilder.append(replace1);
                i++;
//                System.out.println(integerStringEntry.getKey() + "," + integerStringEntry.getValue());
            }
            System.out.println(stringBuilder);
            StringBuilder stringBuilder1 = new StringBuilder();
            for (int j = 0; j < list.size(); j++) {
                if(j>0) stringBuilder1.append(",");
                stringBuilder1.append("'"+(j+1)+"'");
            }
            System.out.println(stringBuilder1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return void
     * @describe 获取数组集合
     * @author liwenjie
     * @date 2019/10/14   11:37
     * @params [strings, list]
     */
    private static List<String[]> getLinkedList(ArrayList<String> strings) throws IOException {
        List<String[]> list = new LinkedList<>();  //第一次处理数据集合
        for (String filePath : strings) {
            if (!filePath.contains("基于角度方差的故障预警模型")) continue;
            if (Integer.parseInt(filePath.substring(filePath.indexOf("日期") + 3, filePath.indexOf("日期") + 11)) < 20190730)  continue;
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
                String[] s = item[9].split("_");
                list.add(s);
            }
        }
        return list;
    }
}
