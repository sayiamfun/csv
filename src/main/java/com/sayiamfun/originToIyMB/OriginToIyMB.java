package com.sayiamfun.originToIyMB;

import com.sayiamfun.common.utils.ScanPackage;
import com.sayiamfun.otherfiletomodelinputfile.FileToModelFile;

import java.io.*;

public class OriginToIyMB {

    public static void main(String[] args) {

        String inputFile = "/Volumes/Untitled/new data/tboxvehiclegateway01a.rtm.live-20200730.log/tboxvehiclegateway01a.rtm.live-20200730.log";

        InputStreamReader ir = null;
        BufferedReader reader = null;

        //CSV文件输出可追加
        String outFile = "/Users/liwenjie/Downloads/vehData/vehOut/01a.csv";

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
            FileToModelFile.closeFileStream(ir, reader, ow, bw);
        }
    }
}
