package com.sayiamfun.file2020313;

import com.sayiamfun.common.utils.ScanPackage;

import java.io.*;

public class ICLineTest {

    public static void main(String[] args) {
        //读取txt文件内容
        InputStreamReader ir = null;
        BufferedReader br = null;
        try {
            // filePath 要读取的文件路径 ScanPackage.encode 文件编码
            ir = new InputStreamReader(new FileInputStream(new File("D:\\桌面\\outdata\\output\\outIcPeak.output")), ScanPackage.encode);
            br = new BufferedReader(ir);
            String line = null;
            StringBuilder x = null;
            StringBuilder y = null;
            int index = 0;
            while (null != (line = br.readLine())) { // 如果 line 为空说明读完了
                String[] split = line.split(",");
                if (split.length > 0) {
                    String s = split[split.length - 1];
                    String[] s1 = s.split("_");
                    x = new StringBuilder(100);
                    y = new StringBuilder(100);
                    for (String s2 : s1) {
                        x.append("'" + s2.split(":")[0] + "',");
                        y.append(s2.split(":")[1] + ",");
                    }
                    System.out.println(x.toString());
                    System.out.println(y.toString());
                }
                System.out.println("----------------------------------------------");
                index++;
                if (index > 100) break;
            }
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
