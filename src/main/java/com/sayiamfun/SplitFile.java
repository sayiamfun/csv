package com.sayiamfun;

import java.io.*;

/**
 * 拆分文件
 */
public class SplitFile {


    private static String inputFileName = "D:\\车辆数据\\L6T78Y4W4GN084987\\input\\tmp\\";

    public static void main(String[] args) {
        //读取txt文件内容
        InputStreamReader ir = null;
        BufferedReader br = null;

        OutputStreamWriter ow = null;
        BufferedWriter bw =  null;
        try {
            // filePath 要读取的文件路径 ScanPackage.encode 文件编码
            ir = new InputStreamReader(new FileInputStream(new File(inputFileName+"L6T78Y4W4GN084987.csv")), "utf-8");
            br = new BufferedReader(ir);
            String line = br.readLine();

            //filePath 为文件路径 true 开启追加模式 ScanPackage.encode 文件编码格式
            ow = new OutputStreamWriter(new FileOutputStream(new File(inputFileName + "out/L6T78Y4W4GN084987_1.csv"), true), "utf-8");
            bw = new BufferedWriter(ow);
            int index = 2;
            int nums = 0;
            while (line != null) { // 如果 line 为空说明读完了
                if(nums>=700000) {
                    nums=0;
                    ow = new OutputStreamWriter(new FileOutputStream(new File(inputFileName + "out/L6T78Y4W4GN084987_"+index+".csv"), true), "utf-8");
                    bw = new BufferedWriter(ow);
                    index++;
                }
                nums++;
                bw.write(line);
                bw.newLine();
                line = br.readLine(); // 读取下一行
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != br) br.close();
                if (null != ir) ir.close();
            } catch (Exception e) {
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
