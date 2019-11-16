package com.sayiamfun.csv.utils;

import com.sayiamfun.csv.entity.BatteryMonomer;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author liwenjie
 * @date 2019/10/9 + 15:59
 * @describe
 */
public class Outs {


    private final static String realPath = "D:\\out\\20191014\\";

    public static void toPressureAbsCSV(Map<String, LinkedList<Double>> map, String filePath) {
        try {
            DecimalFormat df = new DecimalFormat("######0.00");
            File csv = new File(realPath + filePath); // CSV数据文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
            // 添加新的数据行
            bw.write("head1,head2");
            bw.newLine();
            LinkedList<Double> doubles = map.get("1");
            LinkedList<Double> doubles2 = map.get("2");
            for (int i = 0; i < doubles.size(); i++) {
                if (i < doubles2.size()) {
                    bw.write(df.format(doubles.get(i)) + "," + df.format(doubles2.get(i)));
                    bw.newLine();
                } else {
                    bw.write(df.format(doubles.get(i)));
                    bw.newLine();
                }
            }
            bw.close();
        } catch (FileNotFoundException e) {
            // File对象的创建过程中的异常捕获
            e.printStackTrace();
        } catch (IOException e) {
            // BufferedWriter在关闭对象捕捉异常
            e.printStackTrace();
        }
    }

    /**
     * @return void
     * @describe 输出最多压差和最小压差出现次数
     * @author liwenjie
     * @date 2019/10/11   13:21
     * @params [map, filePath]
     */
    public static void toPressureCSV(Map<String, Map<String, Integer>> map, String filePath) {
        try {
            File csv = new File(realPath + filePath); // CSV数据文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
            // 添加新的数据行
            bw.write("CODE,MAXNUM,MINNUM");
            bw.newLine();
            Map<String, Integer> map1 = map.get("1");
            Map<String, Integer> map2 = map.get("2");
            for (String s : map1.keySet()) {
                bw.write(s + "," + (map1.get(s) != null ? map1.get(s) : "0") + "," + (map2.get(s) != null ? map2.get(s) : "0"));
                bw.newLine();
            }
            bw.close();
        } catch (FileNotFoundException e) {
            // File对象的创建过程中的异常捕获
            e.printStackTrace();
        } catch (IOException e) {
            // BufferedWriter在关闭对象捕捉异常
            e.printStackTrace();
        }
    }

    /**
     * @return void
     * @describe 将数据与SOC的统计导出
     * @author liwenjie
     * @date 2019/10/10   14:08
     * @params [map, filePath]
     */
    public static void toSOCCSV(Map<Integer, Map<String, BatteryMonomer>> map, String filePath) {
        try {
            DecimalFormat df = new DecimalFormat("#.00");
            File csv = new File(realPath + filePath); // CSV数据文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
            // 添加新的数据行
            bw.write("SOC,CODE,SUM,NUM,AVERAGE");
            bw.newLine();
            for (Map.Entry<Integer, Map<String, BatteryMonomer>> stringMapEntry : map.entrySet()) {
                for (Map.Entry<String, BatteryMonomer> stringCarEntry : stringMapEntry.getValue().entrySet()) {
                    BatteryMonomer value = stringCarEntry.getValue();
                    bw.write(stringMapEntry.getKey() + "," + value.getCode() + "," + df.format(value.getSum()) + "," + value.getNum() + "," + df.format(value.getSum() / value.getNum()));
                    bw.newLine();
                }
            }
            bw.close();
        } catch (FileNotFoundException e) {
            // File对象的创建过程中的异常捕获
            e.printStackTrace();
        } catch (IOException e) {
            // BufferedWriter在关闭对象捕捉异常
            e.printStackTrace();
        }
    }

    /**
     * @return void
     * @describe 将生成的数据导出到CSV
     * @author liwenjie
     * @date 2019/10/10   13:50
     * @params [map, filePath]
     */
    public static void toCSV(Map<String, BatteryMonomer> map, String filePath) {
        try {
            DecimalFormat df = new DecimalFormat("#.00");
            File csv = new File(realPath + filePath); // CSV数据文件
            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(csv),"UTF-8");
            BufferedWriter bw = new BufferedWriter(ow); // 附加
            // 添加新的数据行
            bw.write("CODE,SUM,NUM,AVERAGE");
            bw.newLine();
            for (Map.Entry<String, BatteryMonomer> stringCarEntry : map.entrySet()) {
                BatteryMonomer value = stringCarEntry.getValue();
                bw.write(value.getCode() + "," + df.format(value.getSum()) + "," + value.getNum() + "," + df.format(value.getSum() / value.getNum()));
                bw.newLine();
            }
            bw.close();
        } catch (FileNotFoundException e) {
            // File对象的创建过程中的异常捕获
            e.printStackTrace();
        } catch (IOException e) {
            // BufferedWriter在关闭对象捕捉异常
            e.printStackTrace();
        }
    }

    public static void toEnrropyCSV(Map<String, Integer> map, String filePath) {
        try {
            DecimalFormat df = new DecimalFormat("######.00");
            File csv = new File(realPath + filePath); // CSV数据文件
            //创建Excel对象
            HSSFWorkbook workbook = new HSSFWorkbook();
            //创建工作表单
            HSSFSheet sheet = workbook.createSheet(filePath.substring(0,filePath.lastIndexOf("."))+1);
            //创建HSSFRow对象 （行）
            HSSFRow row = sheet.createRow(0);
            //创建HSSFCell对象  （单元格）和 设置单元格的值
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("单体编号");
            cell = row.createCell(1);
            cell.setCellValue("次数");
            //输出Excel文件
            int i = 0;
            for (Map.Entry<String, Integer> stringIntegerEntry : map.entrySet()) {
                i++;
                row = sheet.createRow(i);
                cell = row.createCell(0);
                cell.setCellValue(stringIntegerEntry.getKey());
                cell = row.createCell(1);
                cell.setCellValue(stringIntegerEntry.getValue());
            }
            FileOutputStream output=new FileOutputStream(realPath + filePath);
            workbook.write(output);
            output.flush();
        } catch (Exception e) {
            // BufferedWriter在关闭对象捕捉异常
            e.printStackTrace();
        }
    }
}
