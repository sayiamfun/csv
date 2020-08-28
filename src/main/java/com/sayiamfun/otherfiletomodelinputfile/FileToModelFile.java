package com.sayiamfun.otherfiletomodelinputfile;

import com.sayiamfun.common.utils.ScanPackage;

import java.io.*;
import java.util.ArrayList;

public class FileToModelFile {

    public static void main(String[] args) {

        String inputPath = "/Users/liwenjie/Downloads/长安14辆车报文（准备模型数据分析）/CANEV To BIT车辆数据/EC260";
        String outPath = "/Users/liwenjie/Downloads/长安14辆车报文（准备模型数据分析）/CANEV To BIT车辆数据/EC260_out";

        if (!new File(outPath).exists()) new File(outPath).mkdirs();

        ArrayList<String> strings = ScanPackage.scanFiles(inputPath);
        for (String string : strings) {
            InputStreamReader ir = null;
            BufferedReader reader = null;

            OutputStreamWriter ow = null;
            BufferedWriter bw = null;

            try {
                ir = new InputStreamReader(new FileInputStream(new File(string)), ScanPackage.encode);
                reader = new BufferedReader(ir);//到读取的文件

                ow = new OutputStreamWriter(new FileOutputStream(new File(outPath + "/" + string.substring(string.lastIndexOf("/"))), true), ScanPackage.encode);
                bw = new BufferedWriter(ow);

                String line = reader.readLine();
                int i = 0;
                while ((line = reader.readLine()) != null) {
                    String item[] = line.split("\t");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                    String out = ++i + ":" + String.join(",", item);
                    System.out.println(out);
                    bw.write(out);
                    bw.newLine();
                    bw.flush();
                    if (i > 5) break;
                }
                break;
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
}
