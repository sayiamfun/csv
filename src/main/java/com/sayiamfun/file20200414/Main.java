package com.sayiamfun.file20200414;

import com.sayiamfun.common.utils.ScanPackage;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        String file = "C:\\Users\\liwenjie\\Downloads\\veh_base.csv";
        String outFile = "C:\\Users\\liwenjie\\Downloads\\veh_tmp_base.csv";

        //csv文件读取方法
        InputStreamReader ir = null;
        BufferedReader reader = null;
        //CSV文件输出可追加
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            ir = new InputStreamReader(new FileInputStream(new File(file)), ScanPackage.GBK);

            reader = new BufferedReader(ir);//到读取的文件
            String line = reader.readLine();
            ow = new OutputStreamWriter(new FileOutputStream(new File(outFile), true), ScanPackage.GBK);
            bw = new BufferedWriter(ow);

//            while ((line = reader.readLine()) != null) {
//                String[] split = line.split(",");
//                System.out.println(split[5]+","+"1"+","+split[7]);
//                bw.write(split[5]+","+"1"+","+split[7]); //中间，隔开不同的单元格，一次写一行
//                bw.newLine();
//                bw.flush();
//            }
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                System.out.println(split[0]+",,"+split[2]+","+split[3]+","+split[5]+","+split[10]+","+split[8]+","+split[6]+","+split[7]);
                bw.write(split[0]+",,"+split[2]+","+split[3]+","+split[5]+","+split[10]+","+split[8]+","+split[6]+","+split[7]); //中间，隔开不同的单元格，一次写一行
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
