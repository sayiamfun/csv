package com.sayiamfun.common;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author liwenjie
 * @date 2019/10/9 + 15:48
 * @describe
 */
public class ScanPackage {

    private static ArrayList<String> scanFiles = new ArrayList<>();
    private static int count = 0;
    //字符编码
    public final static String encode = "GBK";
    public final static String UTF_8 = "UTF-8";

    public static void main(String[] args) throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        scanFilesWithRecursion("D:\\文档资料\\预警\\20191009112903");
        System.out.println(scanFiles.size());
        System.out.println(count);
        long currentTimeMillis2 = System.currentTimeMillis();
        System.out.println(currentTimeMillis2 - currentTimeMillis);

    }

    /**
     * 从excel获取文件
     *
     * @param strings
     * @return
     */
    public static List<List<String>> getListByExcel(List<String> strings) {
        for (String string : strings) {
            //excel文件读取
            InputStream is = null;// 输入流对象
            HSSFWorkbook workbook = null;
            try {
                String cellStr = null;// 单元格，最终按字符串处理
                is = new FileInputStream(new File(string));// 获取文件输入流
                workbook = new HSSFWorkbook(is);// 创建Excel文件对象
                HSSFSheet sheet = workbook.getSheetAt(0);// 取出第一个工作表，索引是0
                // 开始循环遍历行，表头不处理，从1开始
                List<List<String>> resultList = new LinkedList<>(); //封装返回数据的结合
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
        }
        return null;
    }

    /**
     * @param list
     * @param fileName
     * @return void
     * @des 输出list<List < String>> 数据到CSV文件
     * @author liwenjie
     * @date 2019/11/6  10:45
     */
    public static void out(List<List<String>> list, String fileName) {
        try {
            File csv = new File(fileName);
            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(csv), encode);
            BufferedWriter bw = new BufferedWriter(ow);
            bw.write(getStringByList(list.get(0)));
            bw.newLine();
            for (int i = 1; i < list.size(); i++) {
                bw.write(getStringByList(list.get(i)));
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getStringByList(List<String> list) {
        StringBuffer stringBuffer = new StringBuffer(500);
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) stringBuffer.append(",");
            stringBuffer.append(list.get(i));
        }
        return stringBuffer.toString();
    }

    /**
     * @param list 文件路径
     * @param flag 文件筛选条件   （路径内包含的字段）
     * @des 读取需要的文件内的数据
     * @author liwenjie
     * @date 2019/11/4  17:07
     * @retuen List<String [ ]>  文件内的数据，csv文件，每行按 ， 分割为数组
     */
    public static List<String[]> getItems(ArrayList<String> list, String flag) {
        List<String[]> resultList = new LinkedList<>();
        try {
            for (String s : list) {
                if (!s.contains(flag)) continue;
                BufferedReader reader = new BufferedReader(new FileReader(new File(s)));//到读取的文件
                String line = null;
                boolean b = true;
                while ((line = reader.readLine()) != null) {
                    if (b) {
                        b = false;
                        continue;
                    }
                    String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                    resultList.add(item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static List<String[]> getItems(String filePath) {
        List<String[]> resultList = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));//到读取的文件
            String line = null;
            boolean b = true;
            while ((line = reader.readLine()) != null) {
                if (b) {
                    b = false;
                    continue;
                }
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                resultList.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static List<List<String>> getItems1(String filePath) {
        List<List<String>> resultList = new LinkedList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));//到读取的文件
            String line = null;
            boolean b = true;
            while ((line = reader.readLine()) != null) {
                if (b) {
                    b = false;
                    continue;
                }
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                resultList.add(Arrays.asList(item));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    /**
     * TODO:递归扫描指定文件夹下面的指定文件
     *
     * @return ArrayList<Object>
     * @throws FileNotFoundException
     */
    public static ArrayList<String> scanFilesWithRecursion(String folderPath) {
        try {
            File directory = new File(folderPath);
            if (!directory.isDirectory()) {
                throw new FileNotFoundException('"' + folderPath + '"' + " input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
            }
            if (directory.isDirectory()) {
                File[] filelist = directory.listFiles();
                for (int i = 0; i < filelist.length; i++) {
                    /**如果当前是文件夹，进入递归扫描文件夹**/
                    if (filelist[i].isDirectory()) {
                        /**递归扫描下面的文件夹**/
                        count++;
                        scanFilesWithRecursion(filelist[i].getAbsolutePath());
                    }
                    /**非文件夹**/
                    else {
                        if (!scanFiles.contains(filelist[i].getAbsolutePath())) {
                            scanFiles.add(filelist[i].getAbsolutePath());
                        }
                        //                    System.out.println(filelist[i].getAbsolutePath());
                        //					if (filelist[i].getName().contains("-汉译世界学术名著丛")) {
                        //						filelist[i].renameTo(new File("D:/Message/Book/一生必读的60本/"+filelist[i].getName().replace("-汉译世界学术名著丛", "")));
                        //					 }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return scanFiles;
    }
}
