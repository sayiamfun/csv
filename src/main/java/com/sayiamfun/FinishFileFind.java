package com.sayiamfun;

import com.sayiamfun.common.utils.ScanPackage;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/***
 * 筛选模型输出的文件
 */
public class FinishFileFind {

    public static void main(String[] args) {

        List<String> list = new LinkedList<>();
        list.add("/Users/liwenjie/Downloads/jili_100_1.log");
        list.add("/Users/liwenjie/Downloads/jili_100_2.log");
        list.add("/Users/liwenjie/Downloads/jili_100_3.log");
        list.add("/Users/liwenjie/Downloads/jili_100_4.log");
        list.add("/Users/liwenjie/Downloads/jili_100_5.log");

        //读取txt文件内容
        InputStreamReader ir = null;
        BufferedReader br = null;
        try {
            int count = 0;
            Set<String> set = new HashSet<>();
            for (String s : list) {
                // filePath 要读取的文件路径 ScanPackage.encode 文件编码
                ir = new InputStreamReader(new FileInputStream(new File(s)), ScanPackage.encode);
                br = new BufferedReader(ir);
                String line = null;

                while ((line = br.readLine()) != null) { // 如果 line 为空说明读完了
                    if (line.endsWith(".c000") && line.contains("jili_03")) {
                        count++;
                        String[] split = line.split("/");
                        set.add(split[split.length - 3]);
                    }
                }
            }
            System.out.println("file count:" + count);
            System.out.println("vin count:" + set.size());
            set.stream().forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != br) br.close();
                if (null != ir) ir.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
