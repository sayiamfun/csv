package com.sayiamfun;

import com.sayiamfun.common.utils.ScanPackage;

import java.io.*;

/***
 * 筛选模型输出的文件
 */
public class FinishFileFind {

    public static void main(String[] args) {

        String filePath = "/Users/liwenjie/Downloads/jili_100_1.log";

        //读取txt文件内容
        InputStreamReader ir = null;
        BufferedReader br = null;
        try {
            // filePath 要读取的文件路径 ScanPackage.encode 文件编码
            ir = new InputStreamReader(new FileInputStream(new File(filePath)), ScanPackage.encode);
            br = new BufferedReader(ir);
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null) { // 如果 line 为空说明读完了
                if(line.endsWith(".c000")) {
                    count++;
                    System.out.println(line);
                }
            }
            System.out.println(count);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != br) br.close();
                if (null != ir) ir.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
