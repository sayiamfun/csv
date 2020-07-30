package com.sayiamfun.csv;

import com.sayiamfun.common.utils.ScanPackage;
import org.apache.commons.collections.map.HashedMap;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class B extends A {
    @Override
    public A foo() {
        return this;
    }

    public static void main(String[] args) {
        String inputString = "/Users/liwenjie/Downloads/vehData/吉利104台车统计结果数据/alarmRe.csv";
        String outPutFile = "/Users/liwenjie/Downloads/vehData/吉利104台车统计结果数据/alarmReresult.csv";
        //csv文件读取方法
        InputStreamReader ir = null;
        BufferedReader reader = null;

        //CSV文件输出可追加
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;


        try {
            List<List<String>> resultList = new LinkedList<>(); //保存最后读取的所有数据，LinkedList 是为了保证顺序一致
            ir = new InputStreamReader(new FileInputStream(new File(inputString)), ScanPackage.UTF_8);
            reader = new BufferedReader(ir);//到读取的文件


            ow = new OutputStreamWriter(new FileOutputStream(new File(outPutFile), true), ScanPackage.encode);
            bw = new BufferedWriter(ow);


            String line = reader.readLine();
            String title = line;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                List<String> childrenList = new LinkedList<>();//将数组转换为列表存储，和excel读取结果一致，方便处理LinkedList 是为了保证顺序一致
                for (String s : item) {
                    childrenList.add(s);
                }
                resultList.add(childrenList);
            }
            Map<String, Long> vinTime = new HashedMap();
            for (List<String> strings : resultList) {
                String vin = strings.get(0);
                long time = Long.valueOf(strings.get(3));
                if (!vinTime.containsKey(vin) || vinTime.get(vin) < time) {
                    vinTime.put(vin, time);
                }
            }
            bw.write(title); //中间，隔开不同的单元格，一次写一行
            bw.newLine();
            bw.flush();
            for (List<String> strings : resultList) {
                String vin = strings.get(0);
                long time = Long.valueOf(strings.get(3));
                if (time == vinTime.get(vin)) continue;
                bw.write(String.join(",", strings)); //中间，隔开不同的单元格，一次写一行
                bw.newLine();
                bw.flush();
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
