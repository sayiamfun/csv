package com.sayiamfun.common;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileReadOrWriteUtil {

    /**
     * @param string
     * @return java.util.List<java.lang.String>
     * @des 从txt读取文件
     * @author liwenjie
     * @date 2019/11/7  17:00
     */
    public static List<String> txtRead(String string) {
        //读取txt文件内容
        List<String> resultList = new LinkedList<>();
        InputStreamReader ir = null;
        BufferedReader br = null;
        try {
            // filePath 要读取的文件路径 ScanPackage.encode 文件编码
            ir = new InputStreamReader(new FileInputStream(new File(string)), ScanPackage.encode);
            br = new BufferedReader(ir);
            String line = br.readLine();
            while (line != null) { // 如果 line 为空说明读完了
                line = br.readLine(); // 读取下一行
                resultList.add(line);
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
        return resultList;
    }
    /**
    * @des excel读取文件
    * @author  liwenjie
    * @date 2019/11/8  17:21
    * @return java.util.List<java.util.List<java.lang.String>>
    * @param string
    */
    public static List<List<String>> excelRead(String string) {
        //读取txt文件内容
        List<List<String>> resultList = new LinkedList<>();
        //excel文件读取
        InputStream is = null;// 输入流对象
        HSSFWorkbook workbook = null;
        try {
            String cellStr = null;// 单元格，最终按字符串处理
            is = new FileInputStream(new File(string,ScanPackage.encode));// 获取文件输入流
            workbook = new HSSFWorkbook(is);// 创建Excel文件对象
            HSSFSheet sheet = workbook.getSheetAt(0);// 取出第一个工作表，索引是0
            // 开始循环遍历行，表头不处理，从1开始
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);// 获取行对象
                if (row == null) {// 如果为空，不处理
                    continue;
                }
                // 循环遍历单元格
                List<String> childrenList = new LinkedList<>();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    HSSFCell cell = row.getCell(j);// 获取单元格对象
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
                resultList.add(childrenList);
            }
            return resultList;
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
        return resultList;
    }
}
