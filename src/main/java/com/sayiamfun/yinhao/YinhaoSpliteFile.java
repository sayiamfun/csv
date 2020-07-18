package com.sayiamfun.yinhao;

import com.sayiamfun.common.utils.ScanPackage;

import java.io.*;
import java.util.*;

public class YinhaoSpliteFile {

    public static void main(String[] args) throws IOException {

        String inputPath = "/Volumes/UsbDisk/尹豪3车数据/";
        String outPath = "/Volumes/UsbDisk/尹豪3车数据/out/";

        int vinIndex = 4;

        ArrayList<String> strings = ScanPackage.scanFiles(inputPath);
        Map<String, List<String>> vinFileMap = new HashMap<>();
        for (String string : strings) {
            if (!string.endsWith(".csv") || !string.contains("base64")) continue;
            String vin = string.split("/")[vinIndex];
            if (vin.equals("LB378Y4W7JA184214")) continue;
            if (vinFileMap.containsKey(vin)) {
                vinFileMap.get(vin).add(string);
            } else {
                List<String> fileList = new LinkedList<>();
                fileList.add(string);
                vinFileMap.put(vin, fileList);
            }
        }

        if (!new File(outPath).exists()) new File(outPath).mkdirs();
        for (Map.Entry<String, List<String>> stringListEntry : vinFileMap.entrySet()) {
            String vin = stringListEntry.getKey();
            List<String> value = stringListEntry.getValue();


            //CSV文件输出可追加
            OutputStreamWriter ow = null;
            BufferedWriter bw = null;

            //CSV文件输出可追加
            OutputStreamWriter ow2 = null;
            BufferedWriter bw2 = null;

            ow = new OutputStreamWriter(new FileOutputStream(new File(outPath + vin + "_charge.csv"), true), ScanPackage.UTF_8);
            bw = new BufferedWriter(ow);
            bw.write("vin,time,speed,soc,vlist"); //中间，隔开不同的单元格，一次写一行
            bw.newLine();
            bw.flush();

            ow2 = new OutputStreamWriter(new FileOutputStream(new File(outPath + vin + "_run.csv"), true), ScanPackage.UTF_8);
            bw2 = new BufferedWriter(ow2);
            bw2.write("vin,time,speed,soc,vlist"); //中间，隔开不同的单元格，一次写一行
            bw2.newLine();
            bw2.flush();

            for (String s : value) {
                //csv文件读取方法
                InputStreamReader ir = null;
                BufferedReader reader = null;
                try {
                    List<List<String>> resultList = new LinkedList<>(); //保存最后读取的所有数据，LinkedList 是为了保证顺序一致
                    ir = new InputStreamReader(new FileInputStream(new File(s)), ScanPackage.encode);
                    reader = new BufferedReader(ir);//到读取的文件
                    String line = reader.readLine();

                    while ((line = reader.readLine()) != null) {
                        String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                        String time = item[1];
                        String chargeStatus = item[4];
                        String speed = item[6];
                        String soc = item[10];
                        String vlist = item[70];

                        if ("停车充电".equals(chargeStatus) || "鏈\uE044厖鐢电姸鎬�".equals(chargeStatus)) {
                            bw.write(vin + "," + time + "," + speed + "," + soc + "," + vlist); //中间，隔开不同的单元格，一次写一行
                            bw.newLine();
                            bw.flush();
                        } else {
                            bw2.write(vin + "," + time + "," + speed + "," + soc + "," + vlist); //中间，隔开不同的单元格，一次写一行
                            bw2.newLine();
                            bw2.flush();
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
            try {
                if (null != bw) bw.close();
                if (null != ow) ow.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (null != bw2) bw.close();
                if (null != ow2) ow.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
