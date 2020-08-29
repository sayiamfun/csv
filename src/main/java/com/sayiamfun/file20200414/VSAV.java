package com.sayiamfun.file20200414;

import com.sayiamfun.common.utils.ScanPackage;
import com.sayiamfun.otherfiletomodelinputfile.FileToModelFile;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 统计单体离心率
 */
public class VSAV {


    public static void main(String[] args) {
        String file = "D:\\车辆数据\\其他车辆数据\\七辆事故车做电压图原始数据\\L6T78Y4W4GN084987_reverse\\L6T78Y4W4GN084987_V_191011-191018.csv";
        String outFile1 = "D:\\车辆数据\\其他车辆数据\\七辆事故车做电压图原始数据\\L6T78Y4W4GN084987_reverse\\电压-中位数.csv";
        String outFile2 = "D:\\车辆数据\\其他车辆数据\\七辆事故车做电压图原始数据\\L6T78Y4W4GN084987_reverse\\电压-平均值.csv";

        //csv文件读取方法
        InputStreamReader ir = null;
        BufferedReader reader = null;
        //CSV文件输出可追加
        OutputStreamWriter ow = null;
        OutputStreamWriter ow2 = null;
        BufferedWriter bw = null;
        BufferedWriter bw2 = null;
        try {
            ir = new InputStreamReader(new FileInputStream(new File(file)), ScanPackage.UTF_8);

            reader = new BufferedReader(ir);//到读取的文件
            String line = reader.readLine();
            ow = new OutputStreamWriter(new FileOutputStream(new File(outFile1), true), ScanPackage.GBK);
            ow2 = new OutputStreamWriter(new FileOutputStream(new File(outFile2), true), ScanPackage.GBK);
            bw = new BufferedWriter(ow);
            bw2 = new BufferedWriter(ow2);
            Map<Integer, Integer> map = new TreeMap<>();
            Set<Integer> set1 = new HashSet<>();
            Set<Integer> set0 = new HashSet<>();
            StringBuilder stringBuilder1 = null;
            StringBuilder stringBuilder2 = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder1 = new StringBuilder(1000);
                stringBuilder2 = new StringBuilder(1000);
                String[] split = line.split(",");
                for (int i = 0; i < split.length - 5; i++) {
                    stringBuilder1.append(new BigDecimal(split[i]).subtract(new BigDecimal(split[split.length - 2]))).append(",");
                    stringBuilder2.append(new BigDecimal(split[i]).subtract(new BigDecimal(split[split.length - 1]))).append(",");
                }
                for (int i = split.length - 5; i < split.length; i++) {
                    stringBuilder1.append(new BigDecimal(split[i])).append(",");
                    stringBuilder2.append(new BigDecimal(split[i])).append(",");
                }
                bw.write(stringBuilder1.toString());
                bw.newLine();
                bw.flush();
                bw2.write(stringBuilder2.toString());
                bw2.newLine();
                bw2.flush();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileToModelFile.closeFileStream(ir, reader, ow, bw);
            try {
                if (null != bw2) bw2.close();
                if (null != ow2) ow2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
