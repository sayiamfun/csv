package com.sayiamfun.csv;


import com.sayiamfun.common.utils.ScanPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        InputStream is = null;// 输入流对象
        XSSFWorkbook workbook = null;

        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {

            ow = new OutputStreamWriter(new FileOutputStream(new File("D:\\文档资料\\车辆数据\\20191106\\力帆数据包\\out\\test.csv"),true),ScanPackage.encode);
            bw = new BufferedWriter(ow);

            String cellStr = null;// 单元格，最终按字符串处理
            is = new FileInputStream(new File(""));// 获取文件输入流
            workbook = new XSSFWorkbook(is);// 创建Excel文件对象
            XSSFSheet sheet = workbook.getSheetAt(0);// 取出第一个工作表，索引是0
            // 开始循环遍历行，表头不处理，从1开始
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);// 获取行对象
                if (row == null) {// 如果为空，不处理
                    continue;
                }
                // 循环遍历单元格
                List<String> childrenList = new LinkedList<>();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    XSSFCell cell = row.getCell(j);// 获取单元格对象
                    if (cell == null) {// 单元格为空设置cellStr为空串
                        cellStr = "";
                    } else {
                        try {
                            cellStr = cell.getStringCellValue(); //如果是字符串，按照字符串处理
                        } catch (Exception e) {
                            cellStr = "" + cell.getNumericCellValue(); //报异常安装数字处理  暂时没有找到判断方法
                        }
                    }
                    childrenList.add(cellStr);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {// 关闭文件流
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



    }

    private static String getTime(String s) {
        StringBuilder stringBuilder = new StringBuilder(30);
        String[] s1 = s.split(" ");
        String[] split = s1[0].split("/");
        for (String s2 : split) {
            if (s2.length() == 1) {
                stringBuilder.append("0" + s2);
            } else {
                stringBuilder.append(s2);
            }
        }
        String[] split1 = s1[1].split(":");
        for (String s2 : split1) {
            stringBuilder.append(s2);
        }
        return stringBuilder.toString();
    }

    /**
     *
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        list.add(Arrays.asList(new Integer[]{nums[i], nums[j], nums[k]}));
                    }
                }
            }
        }
        return list;
    }
}
