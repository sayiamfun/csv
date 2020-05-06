package com.sayiamfun.common;


import com.sayiamfun.common.utils.ScanPackage;

import java.io.*;

/**
 * 读取文件的固定行
 */
public class ReadSelectedLine {

    public static void main(String[] args) {
//        long length = new File("C:\\Users\\liwenjie\\Downloads\\20191224180530\\20191224180530.txt").length();
//        if (length > (50 * 1024 * 1024)) {
//
//        }
//        System.out.println();
//        String s = "C:\\Users\\liwenjie\\Downloads\\20191224180530\\20191224180530.txt";
//        System.out.println(s.substring(0, s.lastIndexOf(".")) + "/");
        main1("C:\\Users\\liwenjie\\Downloads\\20191224180530\\20191224180530.txt");
    }

    public static void main1(String path) {
        //读取txt文件内容
        InputStreamReader ir = null;
        BufferedReader br = null;
        //输出
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            // filePath 要读取的文件路径 ScanPackage.encode 文件编码
            ir = new InputStreamReader(new FileInputStream(new File(path)), ScanPackage.encode);
            br = new BufferedReader(ir);
            //filePath 为文件路径 true 开启追加模式 ScanPackage.encode 文件编码格式
            String outPutPath = path.substring(0, path.lastIndexOf(".")) + "/";
            new File(outPutPath).mkdirs();
            int index = 1;
            ow = new OutputStreamWriter(new FileOutputStream(new File(outPutPath + index + ".txt"), true), ScanPackage.encode);
            bw = new BufferedWriter(ow);
            String line;
            String time = "";
            boolean flag = true;
            int nums = 0;
            while ((line = br.readLine()) != null) { // 如果 line 为空说明读完了
                if (line.contains(",2003:")) {
                    nums++;
                    if (nums >= 2000 && flag) {
                        flag = false;
                        time = line.split("2000:")[1].substring(0, 8);
                    }
                    if (!flag && !time.equals(line.split("2000:")[1].substring(0, 8))) {
                        nums = 0;
                        flag = true;
                        index++;
                        ow = new OutputStreamWriter(new FileOutputStream(new File(outPutPath + index + ".txt"), true), ScanPackage.encode);
                        bw = new BufferedWriter(ow);
                        System.out.println("开始新的文件");
                    }
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != br) br.close();
                if (null != ir) ir.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("关闭读连接异常");
            }
            try {
                if (null != bw) bw.close();
                if (null != ow) ow.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("关闭写连接异常");
            }
        }
    }
}
