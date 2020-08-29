package com.sayiamfun.file2020320;

import com.sayiamfun.common.utils.ScanPackage;
import com.sayiamfun.otherfiletomodelinputfile.FileToModelFile;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ReplaceAllUUIDAndVIN {

    private static String vin = "L66BBC4E8H1GT0484";
    public static void main(String[] args) throws FileNotFoundException {
        String path = "D:\\车辆数据\\格力数据\\交付数据\\20200325171708\\";
        String outPath = "D:\\车辆数据\\格力数据\\交付数据\\result\\";
        replace(path,outPath);
    }

    public static void replace(String path,String outPath) throws FileNotFoundException {
        ArrayList<String> strings = ScanPackage.scanFiles(path);

        //csv文件读取方法
        InputStreamReader ir = null;
        BufferedReader reader = null;
        List<List<String>> resultList = null;

        //CSV文件输出可追加
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        String fileName = "";
        for (String string : strings) {
            if(!string.endsWith(".csv")) continue;
            fileName = string.substring(path.length()).replace(vin,"LLLLLLLLLLLLLLLLLLLL");
            try {
                resultList = new LinkedList<>(); //保存最后读取的所有数据，LinkedList 是为了保证顺序一致
                ir = new InputStreamReader(new FileInputStream(new File(string)),ScanPackage.encode);
                reader = new BufferedReader(ir);//到读取的文件
                String line = null;
                while ((line = reader.readLine()) != null) {
                    String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                    List<String> childrenList = new LinkedList<>();//将数组转换为列表存储，和excel读取结果一致，方便处理LinkedList 是为了保证顺序一致
                    for (String s : item) {
                        childrenList.add(s);
                    }
                    resultList.add(childrenList);
                }
                ow = new OutputStreamWriter(new FileOutputStream(new File(outPath+"/"+fileName),true),ScanPackage.encode);
                bw = new BufferedWriter(ow);
                for (List<String> stringList : resultList) {
                    bw.write(String.join(",", stringList).replace(vin,"LLLLLLLLLLLLLLLLLLLL").replace(vin,"LLLLLLLLLLLLLLLLLLLL")); //中间，隔开不同的单元格，一次写一行
                    bw.newLine();
                    bw.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                FileToModelFile.closeFileStream(ir, reader, ow, bw);
            }
        }
        System.out.println("finish .................");
    }
}
