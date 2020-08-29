package com.sayiamfun.file20200327;

import com.sayiamfun.common.utils.ScanPackage;
import com.sayiamfun.otherfiletomodelinputfile.FileToModelFile;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * 将CSV文件转换为JSON格式文件
 * 只取模型所需数据
 */
public class CSVToJSON {


    public static void main(String[] args) {

        String file = "D:\\车辆数据\\传为佳话\\2020-03-25.tar\\2020-03-25\\";
        String outFile = "D:\\车辆数据\\传为佳话\\2020-03-25.tar\\out\\";
//        String file = "D:\\车辆数据\\tmp\\";
//        String outFile = file + "out\\";
        if (!new File(outFile).exists()) {
            new File(outFile).mkdir();
        }
        ArrayList<String> list = ScanPackage.scanFiles(file);
        Collections.sort(list, (l1, l2) -> getTheDate(l2) - getTheDate(l1));
        for (String s : list) {
            if (!s.endsWith(".csv")) continue;
            csvToJson(s, outFile + getFileName(s));
        }
        System.out.println("tak is finishing . . . . . .");
    }

    /**
     * 获取文件名字
     *
     * @param s
     * @return
     */
    private static Integer getTheDate(String s) {
        int year = s.indexOf("year=");
        int month = s.indexOf("month=");
        int day = s.indexOf("day=");
        String years = "";
        String months = "";
        String days = "";
        if (year != -1)
            years = s.substring(year + 5, year + 9);
        if (month != -1)
            months = s.substring(month + 6, month + 8);
        if (day != -1)
            days = s.substring(day + 4, day + 6);
        return Integer.parseInt(years + months + days);
    }

    /**
     * 获取文件名字
     *
     * @param s
     * @return
     */
    private static String getFileName(String s) {
//        int year = s.indexOf("year=");
//        int month = s.indexOf("month=");
//        int day = s.indexOf("day=");
        int vin = s.indexOf("vin=");
//        String years = s.substring(year + 5, year + 9);
//        String months = s.substring(month + 6, month + 8);
//        String days = s.substring(day + 4, day + 6);
        String vins = s.substring(vin + 4, vin + 4 + "LEWTEB140HE103804".length());
        String s1 = vins + ".txt";
        return s1;
    }

    private static void csvToJson(String file, String outFile) {
        //csv文件读取方法
        InputStreamReader ir = null;
        BufferedReader reader = null;
        //CSV文件输出可追加
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            ir = new InputStreamReader(new FileInputStream(new File(file)), ScanPackage.GBK);


            int vin = file.indexOf("vin=");
            String vins = file.substring(vin + 4, vin + 4 + "LEWTEB140HE103804".length());

            reader = new BufferedReader(ir);//到读取的文件
            String line = reader.readLine();
            List<LinkedList<String>> list = new ArrayList<>();
            LinkedList<String> tmpList;
            while ((line = reader.readLine()) != null) {
                tmpList = new LinkedList<>();
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                tmpList.add("MESSAGETYPE:HISTORY");  //报文类型
                tmpList.add("VIN:" + vins);  //vin
                tmpList.add("2000:" + getTime(item[0])); //时间
                tmpList.add("3201:" + getCarStatus(item[1])); //车辆状态
                tmpList.add("2301:" + getChargeStatus(item[2])); //充电状态
                tmpList.add("2615:" + (item[8])); //SOC
                tmpList.add("2613:" + (item[6])); //总电压
                tmpList.add("2614:" + (item[7])); //总电流
                tmpList.add("2201:" + (item[4])); //车速
                tmpList.add("2202:" + (item[5])); //里程
                tmpList.add("2617:" + (item[10])); //绝缘阻值
                tmpList.add("7003:" + getVL(item, 31)); //电压列表
                tmpList.add("7103:" + getTL(item, 31)); //温度列表
                list.add(tmpList);
            }
            Collections.sort(list, (l1, l2) -> Integer.valueOf("" + (Long.parseLong(l1.get(2).split(":")[1]) - Long.parseLong(l2.get(2).split(":")[1]))));
            ow = new OutputStreamWriter(new FileOutputStream(new File(outFile), true), ScanPackage.GBK);
            bw = new BufferedWriter(ow);
            for (LinkedList<String> strings : list) {
                bw.write(StringUtils.join(strings, ",")); //中间，隔开不同的单元格，一次写一行
                bw.newLine();
                bw.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileToModelFile.closeFileStream(ir, reader, ow, bw);
        }
    }

    /**
     * 获取温度列表
     *
     * @param item
     * @param i
     * @return
     */
    private static String getTL(String[] item, int i) {
        String vs = item[i];
        String[] vsplit = vs.split(";");
        String[] vsplit1 = vsplit[2].split(":");
        int vSize = Integer.parseInt(StringUtils.substring(vsplit1[1], 1, vsplit1[1].length() - 1));//电池单体数量
        String ms = item[i + vSize];
        String[] msplit = ms.split(":");
        String[] msplit1 = msplit[2].split("]");
        int mSize = Integer.parseInt(StringUtils.substring(msplit1[0], 1, msplit1[0].length()));//温度探针数量
        String tmpM = StringUtils.substring(msplit[3], 1); //第一个温度

        StringBuilder stringBuilder = new StringBuilder(200);
        stringBuilder.append(tmpM + "_");
        for (int i1 = i + vSize + 1; i1 < i + vSize + mSize; i1++) {
            stringBuilder.append(item[i1] + "_");
        }
        return stringBuilder.subSequence(0, stringBuilder.length() - 3).toString().replaceAll(" ", "");
    }

    /**
     * 获取电压列表
     *
     * @param item
     * @param i
     * @return
     */
    private static String getVL(String[] item, int i) {
        String s = item[i];
        String[] split = s.split(";");
        String[] split1 = split[2].split(":");
        int vSize = Integer.parseInt(StringUtils.substring(split1[1], 1, split1[1].length() - 1));//电池单体数量
        String[] split2 = split[3].split(":");
        String tmpV = StringUtils.substring(split2[1], 1); //第一个单体电压
        StringBuilder stringBuilder = new StringBuilder(200);
        stringBuilder.append(tmpV + "_");
        for (int i1 = i + 1; i1 < i + vSize; i1++) {
            stringBuilder.append(item[i1] + "_");
        }
        return stringBuilder.subSequence(0, stringBuilder.length() - 4).toString().replaceAll(" ", "");
    }

    /**
     * 获取格式时间
     *
     * @param s
     * @return
     */
    private static String getTime(String s) {
//        StringBuilder stringBuilder = new StringBuilder(s);
//        String[] s1 = s.split(" ");
//        String[] split = s1[0].split("/");
//        for (String s2 : split) {
//            if (s2.length() == 1) {
//                stringBuilder.append("0" + s2);
//            } else {
//                stringBuilder.append(s2);
//            }
//        }
//        String[] split1 = s1[1].split(":");
//        for (String s2 : split1) {
//            stringBuilder.append(s2);
//        }
        return s.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
    }

    /**
     * 获取车辆状态
     *
     * @param s
     * @return
     */
    private static String getCarStatus(String s) {
        String result;
        switch (s) {
            case "启动":
                result = "1";
                break;
            case "熄火":
                result = "2";
                break;
            default:
                result = "3";
        }
        return result;
    }

    /**
     * 获取车辆充电状态
     *
     * @param s
     * @return
     */
    private static String getChargeStatus(String s) {
        String result;
        switch (s) {
            case "停车充电":
                result = "1";
                break;
            case "行驶充电":
                result = "2";
                break;
            case "未充电":
                result = "3";
                break;
            default:
                result = "4";
        }
        return result;
    }
}
