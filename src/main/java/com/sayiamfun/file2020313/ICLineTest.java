package com.sayiamfun.file2020313;

import com.sayiamfun.common.utils.ScanPackage;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class ICLineTest {

    public static void main(String[] args) {
        List<String> ipnoreVins = Arrays.asList("");
        String join = String.join("','", ipnoreVins);
        System.out.println("('"+join+"')");
    }

    private static void getString() {
        //csv文件读取方法
        InputStreamReader ir = null;
        BufferedReader reader = null;
        try {
            ir = new InputStreamReader(new FileInputStream(new File("C:\\Users\\liwenjie\\Downloads\\10.最终结果_v5.0 -修正库里车型.csv")), ScanPackage.encode);
            reader = new BufferedReader(ir);//到读取的文件
            String line = reader.readLine();
            StringBuilder stringBuilder = new StringBuilder(2000);
            int length = 0;
            while ((line = reader.readLine()) != null) {
                if (length == 0) length = line.split(",").length;
                stringBuilder.append(line);
                String item[] = stringBuilder.toString().split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                if (item.length >= length) {
                    System.out.println(stringBuilder.toString().replaceAll("\n", ""));
                    stringBuilder = new StringBuilder(200);
                }
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
        }
    }
}
