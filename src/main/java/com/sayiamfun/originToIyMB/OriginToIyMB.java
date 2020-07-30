package com.sayiamfun.originToIyMB;

import com.sayiamfun.common.utils.ScanPackage;

import java.io.*;

public class OriginToIyMB {

    public static void main(String[] args) {
        String inputFile = "/Users/liwenjie/Downloads/tboxvehiclegateway01a.rtm.live.log-limit";

        InputStreamReader ir = null;
        BufferedReader reader = null;

        //CSV文件输出可追加
        String outFile = "/Users/liwenjie/Downloads/tboxvehiclegateway01a.rtm.live.log-limit_result";

        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            ir = new InputStreamReader(new FileInputStream(new File(inputFile)), ScanPackage.encode);
            reader = new BufferedReader(ir);//到读取的文件

            ow = new OutputStreamWriter(new FileOutputStream(new File(outFile), true), ScanPackage.encode);
            bw = new BufferedWriter(ow);


            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains("\"orginalReport\":\"")) {
                    String[] split = line.split("\"orginalReport\":\"");
                    String[] time = split[0].split(" ");
                    String[] message = split[1].split("\"");
                    String dateTime = time[0].replaceAll("-", "") + time[1].split("\\.")[0].replaceAll(":", "");
                    String messageData = dateTime + "," + StringToGbBase64Binary.toBytesBase64(message[0]);
                    bw.write(messageData); //中间，隔开不同的单元格，一次写一行
                    bw.newLine();
                    bw.flush();
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
            try {
                if (null != bw) bw.close();
                if (null != ow) ow.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
