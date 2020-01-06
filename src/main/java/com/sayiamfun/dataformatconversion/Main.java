package com.sayiamfun.dataformatconversion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sayiamfun.common.FileReadOrWriteUtil;
import com.sayiamfun.common.ScanPackage;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    public static final String outPath = "D:\\文档资料\\车辆数据\\20191106\\力帆数据包\\out";
    public static final String inPath = "D:\\文档资料\\车辆数据\\20191106\\力帆数据包\\力帆十台车1个月数\\5月份数据\\";
    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

    public static void main(String[] args) {
        List<List<String>> lists = FileReadOrWriteUtil.excelRead("\u202AD:\\车辆数据20191108\\苏州金龙3车\\LKLA6C191HA726527\\LKLA6C191HA726527.xlsx");
        for (List<String> list : lists) {
            for (String s : list) {
                System.out.print(s+",");
            }
            System.out.println();
        }
    }

    private static void tes1() {
        //        List<String> strings = FileReadOrWriteUtil.txtRead("D:\\文档资料\\车辆数据\\20191106\\车电芯数据\\单独6车数据\\渝AD55076copy.txt");
//        for (String string : strings) {
//            String[] split = string.split(",");
//            if (split.length > 2) {
//                String[] split1 = split[2].split(":");
//                System.out.println(split1[0] + ":" + new String(Base64.getDecoder().decode(split1[1]), "GBK"));
//            }
//            if (split.length > 3) {
//                String[] split1 = split[3].split(":");
//                System.out.println(split1[0] + ":" + new String(Base64.getDecoder().decode(split1[1]), "GBK"));
//
//            }
//        }

        /*//写入数据到txt文件，如果存在则在末尾追加
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            //filePath 为文件路径 true 开启追加模式 ScanPackage.encode 文件编码格式
            ow = new OutputStreamWriter(new FileOutputStream(new File("D:\\文档资料\\车辆数据\\20191106\\车电芯数据\\单独6车数据\\渝AD71105copy.txt"), true), ScanPackage.encode);
            bw = new BufferedWriter(ow);
            for (String string : strings) {
                if (string == null) continue;
                bw.write(getString(string));
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bw) bw.close();
                if (null != ow) ow.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
       /* ArrayList<String> strings = ScanPackage.scanFilesWithRecursion("D:\\fault1");
        Set<String> set = new HashSet<>();
        for (String string : strings) {
            if (new File(string).isFile()) {
                set.add(string.substring(string.lastIndexOf("\\") + 1));
            }
        }
        for (String s : set) {
            if (s.endsWith(".zip")) continue;
            System.out.println(s);
        }*/
    }

    private static void tes() {
        //Base64.getDecoder().decode(sv)
        List<String[]> items = null;
        try {
            ArrayList<String> strings = ScanPackage.scanFilesWithRecursion("D:\\车辆数据20191108\\8.25北汽\\guojia_92928\\LNBSCU3H1JR059759");

            File csv = new File("D:\\车辆数据20191108\\8.25北汽\\guojia_92928\\LNBSCU3H1JR059759\\LNBSCU3H1JR059759.txt");
            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(csv), ScanPackage.encode);
            BufferedWriter bw = new BufferedWriter(ow);
            BufferedReader reader = new BufferedReader(new FileReader(new File(strings.get(0))));//到读取的文件
            String line = null;
            boolean b = true;
            while ((line = reader.readLine()) != null) {
                if (b) {
                    b = false;
                    continue;
                }
                System.out.println(line);
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                bw.write("messageType:"  + item[15] +
                        ",3201:" + item[88] +
                        ",2301:" + item[64] +
                        ",2201:" + Double.parseDouble(item[2]) +
                        ",vin: LNBSCU3H1JR059759" +
                        ",vid: " +
                        ",2000:" + item[67] +
                        ",2615:" + item[4] +
                        ",2202:" + Double.parseDouble(item[11]) +
                        ",2613:" + Double.parseDouble(item[5]) +
                        ",2614:" + Double.parseDouble(item[3]) +
                        ",7003: " + item[25]!=null?new String(Base64.getDecoder().decode(item[25]), "GBK"):"" +
                        ",7103: " + item[34]!=null?new String(Base64.getDecoder().decode(item[34]), "GBK"):"" +
                        ",2617:" + item[49] +
                        ",2110: " +
                        ",2111: " +
                        ",2115: " +
                        ",2119: "
                );
                bw.newLine();
                bw.flush();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getString(String string) {
        StringBuffer stringBuffer = new StringBuffer();
        String[] split = string.split(",");
        stringBuffer.append(split[0] + ",");
        stringBuffer.append(split[1] + ",");
        if (split.length > 2) {
            String[] split1 = split[2].split(":");
            stringBuffer.append("7003:" + Base64.getEncoder().encodeToString(split1[1].getBytes()) + ",");
        }
        if (split.length > 3) {
            String[] split2 = split[3].split(":");
            stringBuffer.append("7103:" + Base64.getEncoder().encodeToString(split2[1].getBytes()) + ",");
        }
        return stringBuffer.toString();
    }


    /**
     * @param
     * @return void
     * @des 读取excel文件数据
     * @author liwenjie
     * @date 2019/11/6  16:34
     */
    private static void method3() {
        try {
            ArrayList<String> strings = ScanPackage.scanFilesWithRecursion("D:\\文档资料\\车辆数据\\20191106\\力帆数据包\\三台事故车辆数据汇总\\渝AD71105归档");
            List<List<String>> listByExcel = ScanPackage.getListByExcel(strings);
            File csv = new File(outPath + "\\渝AD71105.txt");
            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(csv), ScanPackage.encode);
            BufferedWriter bw = new BufferedWriter(ow);
            for (List<String> stringList : listByExcel) {
                bw.write("messageType:REALTIME" +
                        ",3201:" + getCarStatus(stringList.get(17)) +
                        ",2301:" + getchargeStatus(stringList.get(22)) +
                        ",2201:" + Double.parseDouble(stringList.get(10)) +
                        ",vin: " +
                        ",vid: " +
                        ",2000:" + stringList.get(0).replace(":", "").replace("-", "").replace(" ", "") +
                        ",2615:" + stringList.get(20) +
                        ",2202:" + Double.parseDouble(stringList.get(11)) +
                        ",2613:" + Double.parseDouble(stringList.get(18)) +
                        ",2614:" + Double.parseDouble(stringList.get(19)) +
                        ",7003: " +
                        ",7103: " +
                        ",2617:" + stringList.get(21) +
                        ",2110: " +
                        ",2111: " +
                        ",2115: " +
                        ",2119: "
                );
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 从csv读取文件
     */
    private static void readByCsv() {
        try {
            ArrayList<String> strings = ScanPackage.scanFilesWithRecursion(inPath);
//            List<String[]> items = ScanPackage.getItems("D:\\文档资料\\车辆数据\\20191106\\123456(1).csv");
            List<String[]> items = ScanPackage.getItems(strings, "渝AD55076");
            File csv = new File(outPath + "\\渝AD55076.txt");
            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(csv), ScanPackage.encode);
            BufferedWriter bw = new BufferedWriter(ow);
            for (String[] item : items) {
                String stringByArray = getStringByArray(item, 3, 18, 96);
                bw.write(stringByArray);
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param items
     */
    private static void method2(List<String[]> items) {
        List<List<String>> resultList = new LinkedList<>();
//            List<String> childrenList = new LinkedList<>();
//            childrenList.add("messageType");  //报文类型
//            childrenList.add("carStatus");  //车辆状态
//            childrenList.add("chargeStatus");  //充电状态
//            childrenList.add("speed");  //速度
//            childrenList.add("vin"); //vin
//            childrenList.add("vid"); //vid
//            childrenList.add("time"); //时间
//            childrenList.add("soc"); //SOC
//            childrenList.add("mileage"); //里程
//            childrenList.add("totalVolt"); //总电压
//            childrenList.add("totalElec"); //总电流
//            childrenList.add("singleVoltages"); //
//            childrenList.add("singleTemps");
//            childrenList.add("resis");  //绝缘电阻
//            childrenList.add("fuelTotalVolt"); //燃料电池电压
//            childrenList.add("fuelTotalElec"); //燃料电池电流
//            childrenList.add("fuelHydrogenHighTempsValue"); //燃料电池氢系统最高温度
//            childrenList.add("fuelHydrogenStress"); //燃料电池氢气最高压力
//            childrenList.add("fuelSingleTemps");
//            resultList.add(childrenList);
        for (String[] item : items) {
            List<String> childrenList = new LinkedList<>();
            childrenList.add("messageType:3");
            childrenList.add("3201:" + getCarStatus(item[17]));
            childrenList.add("2301:" + getchargeStatus(item[22]));
            childrenList.add("2201:" + Double.parseDouble(item[10]));
            childrenList.add("vin: ");
            childrenList.add("vid: ");
            childrenList.add("2000:" + item[0].replace(":", "/"));
            childrenList.add("2615:" + item[20]);
            childrenList.add("2202:" + Double.parseDouble(item[11]));
            childrenList.add("2613:" + Double.parseDouble(item[18]));
            childrenList.add("2614:" + Double.parseDouble(item[19]));
            childrenList.add("7003: ");  //电压数据
            childrenList.add("7103: ");  //温度数据
            childrenList.add("2617:" + item[21]);
            childrenList.add("2110: "); //燃料电池电压
            childrenList.add("2111: "); //燃料电池电流
            childrenList.add("2115: "); //燃料电池氢系统最高温度
            childrenList.add("2119: "); //燃料电池氢气最高压力
            resultList.add(childrenList);
        }
        ScanPackage.out(resultList, "D:\\文档资料\\车辆数据\\20191106\\12345copy.txt");
    }

    /**
     * 转换充电状态
     *
     * @param chargeStatus
     * @return
     */
    private static String getchargeStatus(String chargeStatus) {
        if ("未充电".equals(chargeStatus)) return "3";
        if ("停车充电".equals(chargeStatus)) return "1";
        if ("行驶充电".equals(chargeStatus)) return "2";
        if ("充电完成".equals(chargeStatus)) return "4";
        return "5";
    }

    /**
     * 转换车辆状态
     *
     * @param CarStatus
     * @return
     */
    private static String getCarStatus(String CarStatus) {
        if ("启动".equals(CarStatus)) return "1";
        if ("熄火".equals(CarStatus)) return "2";
        return "3";
    }

    private static String getStringByArray(String[] strings, int start, int sbt, int sbv) {
        StringBuffer stringBuffer = new StringBuffer(500);
        stringBuffer.append("VIN:" + strings[0] + ",");
        stringBuffer.append("2000:" + formatter.format(new Date(Integer.parseInt(strings[1]) * 1000L)) + ",");
        stringBuffer.append("2003:");
        boolean b = false;
        for (int i = start + sbt; i < start + sbt + sbv; i++) {
            if (b) {
                stringBuffer.append("_");
            }
            stringBuffer.append(strings[i]);
            b = true;
        }
        stringBuffer.append(",");
        stringBuffer.append("2103:");
        b = false;
        for (int i = start; i < start + sbt; i++) {
            if (b) {
                stringBuffer.append("_");
            }
            stringBuffer.append(strings[i]);
            b = true;
        }
        return stringBuffer.toString();
    }
}
