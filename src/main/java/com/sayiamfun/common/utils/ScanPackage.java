package com.sayiamfun.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;


/**
 * @author liwenjie
 * @date 2019/10/9 + 15:48
 * @describe
 */
public class ScanPackage {

    private static Logger logger = LoggerFactory.getLogger(ScanPackage.class);

    private static ArrayList<String> scanFiles = new ArrayList<>();
    private static int count = 0;
    //字符编码
    public static final String GBK = "GBK";
    public final static String encode = "GBK";
    public final static String UTF_8 = "UTF-8";

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
                logger.error(e.getMessage(), e);
            } finally {// 关闭文件流
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                if (workbook != null) {
                    try {
                        workbook.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
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
            logger.error(e.getMessage(), e);
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
            logger.error(e.getMessage(), e);
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
            logger.error(e.getMessage(), e);
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
            logger.error(e.getMessage(), e);
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
                    else if (filelist[i].getAbsolutePath().contains(".csv") && !filelist[i].getAbsolutePath().contains("/0_") && (filelist[i].getAbsolutePath().contains("熵值故障诊断模型") || filelist[i].getAbsolutePath().contains("波动一致性故障诊断模型") || filelist[i].getAbsolutePath().contains("压降一致性故障诊断模型"))) {
                        if (!scanFiles.contains(filelist[i].getAbsolutePath())) {
                            scanFiles.add(filelist[i].getAbsolutePath());
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return scanFiles;
    }

    public static ArrayList<String> scanFiles(String folderPath) {
        ArrayList<String> scanFiles = new ArrayList<>();
        File directory = new File(folderPath);
        if (!directory.isDirectory()) {
            try {
                throw new FileNotFoundException('"' + folderPath + '"' + " input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
            } catch (FileNotFoundException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (directory.isDirectory()) {
            File[] filelist = directory.listFiles();
            for (int i = 0; i < filelist.length; i++) {
                /**如果当前是文件夹，进入递归扫描文件夹**/
                if (filelist[i].isDirectory()) {
                    /**递归扫描下面的文件夹**/
                    ArrayList<String> strings = scanFiles(filelist[i].getAbsolutePath());
                    for (String string : strings) {
                        if (!scanFiles.contains(string)) {
                            scanFiles.add(string);
                        }
                    }
                }
                /**非文件夹**/
                else {
                    if (!scanFiles.contains(filelist[i].getAbsolutePath())) {
                        scanFiles.add(filelist[i].getAbsolutePath());
                    }
                }
            }
        }
        return scanFiles;
    }

    /**
     * 获取所有文件的上层目录
     *
     * @param folderPath
     * @return java.util.Set<java.lang.String>
     * @author liwenjie
     * @creed: Talk is cheap,show me the code
     * @date 2020/7/7 3:32 下午
     */
    public static Set<String> scanDirectory(String folderPath) {
        Set<String> directorySet = new HashSet<>();
        ArrayList<String> strings = scanFiles(folderPath);
        for (String string : strings) {
            string = string.replaceAll("\\\\", "/");
            directorySet.add(StringUtils.substring(string, 0, string.lastIndexOf("/") + 1));
        }
        return directorySet;
    }

    /**
     * 获取此目录下的文件
     *
     * @param folderPath
     * @return java.util.Set<java.lang.String>
     * @author liwenjie
     * @creed: Talk is cheap,show me the code
     * @date 2020/7/7 3:32 下午
     */
    public static Set<String> scanThisDirectoryFile(String folderPath) {
        Set<String> fileSet = new HashSet<>();
        File file = new File(folderPath);
        if (!file.isDirectory()) {
            try {
                throw new FileNotFoundException('"' + folderPath + '"' + " input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
            } catch (FileNotFoundException e) {
                logger.error(e.getMessage(), e);
            }
        }
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isFile()) {
                fileSet.add(file1.getAbsolutePath());
            }
        }
        return fileSet;
    }


    /***
     * 返回数据类型  Map<time,List<列>>
     * @param filePath
     * @param totalSum
     * @return
     */
    public static Map<Long, List<String>> getMapItems1(String filePath, int type, int totalSum) {
        Map<Long, List<String>> resultMap = new HashMap<>();
        int count = getCount(filePath);
        int start = 0;
        if (count > totalSum) start = count - totalSum;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));//到读取的文件
            String line = null;
            boolean b = true;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                if (b) {
                    b = false;
                    continue;
                }
                i++;
                if (i < start) continue;
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                if (1 == type) {
                    resultMap.put(Long.valueOf(item[1]), Arrays.asList(item));
                } else {
                    resultMap.put(Long.valueOf(getExcelLongTime(item[1])), Arrays.asList(item));
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return resultMap;
    }

    private static int getCount(String filePath) {
        int count = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));//到读取的文件
            String line = null;
            boolean b = true;
            while ((line = reader.readLine()) != null) {
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 2020/6/12 7:48:00
     *
     * @param s
     * @return
     */
    private static String getExcelLongTime(String s) {
        String[] s1 = s.split(" ");
        String[] split = s1[0].split("-");
        StringBuffer stringBuffer = new StringBuffer(100);
        for (int i = 0; i < split.length; i++) {
            if (i == 0) {
                stringBuffer.append(split[i]);
            } else {
                if (split[i].length() < 2) {
                    stringBuffer.append("0").append(split[i]);
                } else {
                    stringBuffer.append(split[i]);
                }
            }
        }
        String[] split1 = s1[1].split(":");
        for (String s2 : split1) {
            if (s2.length() < 2) {
                stringBuffer.append("0").append(s2);
            } else {
                stringBuffer.append(s2);
            }
        }
        if (split1.length < 3) {
            stringBuffer.append("00");
        }
        return stringBuffer.toString();
    }
}
