package com.sayiamfun;

import com.sayiamfun.common.utils.ScanPackage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShellCpFile {

    public static void main(String[] args) {
        //读取txt文件内容
        String file = "C:\\Users\\liwenjie\\Desktop\\veh430.txt";
        String file2 = "C:\\Users\\liwenjie\\Downloads\\veh_90_1.txt";
        InputStreamReader ir = null;
        BufferedReader br = null;
        try {
            // filePath 要读取的文件路径 ScanPackage.encode 文件编码
            ir = new InputStreamReader(new FileInputStream(new File(file)), ScanPackage.encode);
            br = new BufferedReader(ir);
            Set<String> veh500List = new HashSet<>();
            String line = null;
            while ((line = br.readLine()) != null) { // 如果 line 为空说明读完了
                if ("".equals(line)) continue;
                veh500List.add(line);
            }
            ir = new InputStreamReader(new FileInputStream(new File(file2)), ScanPackage.encode);
            br = new BufferedReader(ir);
            Set<String> veh400List = new HashSet<>();
            line = null;
            while ((line = br.readLine()) != null) { // 如果 line 为空说明读完了
                if ("".equals(line)) continue;
                veh400List.add(line);
            }
            for (String s : veh500List) {
                if(!veh400List.contains(s)){
                    System.out.println("cp -r "+s+" /opt/veh_90_5/");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != br) br.close();
                if (null != ir) ir.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
