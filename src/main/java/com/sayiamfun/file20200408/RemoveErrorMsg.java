package com.sayiamfun.file20200408;

import com.sayiamfun.common.utils.ScanPackage;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class RemoveErrorMsg {


    public static void main(String[] args) {
        String file = "D:\\车辆数据\\服务部\\20200408\\Mar_LNBSCC4H8JD051115.csv";
        String outFile = "D:\\车辆数据\\服务部\\20200408\\LNBSCC4H8JD051115.csv";

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

            while ((line = reader.readLine()) != null) {
                if(!line.contains("IyMC")) continue;
                bw.write(line); //中间，隔开不同的单元格，一次写一行
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
