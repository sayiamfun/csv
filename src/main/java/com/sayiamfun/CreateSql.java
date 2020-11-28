package com.sayiamfun;

import com.sayiamfun.common.utils.ScanPackage;
import com.sayiamfun.otherfiletomodelinputfile.FileToModelFile;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateSql {




//    public static void main(String[] args) {
//        String inputFile = "/Users/liwenjie/Downloads/孚能北汽2019年车架号信息.csv";
//
//        String outFile = "/Users/liwenjie/Downloads/funengmodelinsrt.sql";
//        String outFile1 = "/Users/liwenjie/Downloads/funengmvehinsrt.sql";
//
//
//        Map<String, String> modelMap = new HashMap<>();
//        Set<String> modelSet = new HashSet<>();
//        int modelStart = 400;
//        OutputStreamWriter ow = null;
//        BufferedWriter bw = null;
//
//        InputStreamReader ir = null;
//        BufferedReader reader = null;
//        try {
//            ir = new InputStreamReader(new FileInputStream(new File(inputFile)), ScanPackage.UTF_8);
//            reader = new BufferedReader(ir);//到读取的文件
//
//            ow = new OutputStreamWriter(new FileOutputStream(new File(outFile), true), ScanPackage.UTF_8);
//            bw = new BufferedWriter(ow);
//            String line = reader.readLine();
//            while ((line = reader.readLine()) != null) {
//                if (modelSet.add(line.split(",")[1])) {
//                    modelMap.put(line.split(",")[1], "" + modelStart);
//                    bw.write("insert into new_schema.sys_veh_model (id,veh_model_name,create_time) VALUES ('" + modelStart + "','" + line.split(",")[1] + "',SYSDATE())");
//                    bw.newLine();
//                    bw.flush();
//                    modelStart++;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            FileToModelFile.closeFileStream(ir, reader, ow, bw);
//        }
//        int vehStart = 20000;
//        try {
//            ir = new InputStreamReader(new FileInputStream(new File(inputFile)), ScanPackage.UTF_8);
//            reader = new BufferedReader(ir);//到读取的文件
//
//            ow = new OutputStreamWriter(new FileOutputStream(new File(outFile1), true), ScanPackage.UTF_8);
//            bw = new BufferedWriter(ow);
//            String line = reader.readLine();
//            while ((line = reader.readLine()) != null) {
//                modelMap.put("" + modelStart, line.split(",")[1]);
//                bw.write("insert into new_schema.veh_check_info (id,vin,veh_model_id,create_time) values ('" + vehStart + "','" + line.split(",")[2] + "'," + modelMap.get(line.split(",")[1]) + ",sysdate());");
//                bw.newLine();
//                bw.flush();
//                vehStart++;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            FileToModelFile.closeFileStream(ir, reader, ow, bw);
//        }
//    }

}
