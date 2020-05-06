package com.sayiamfun.common.utils;


import java.io.*;

/**
 * 将 CSV 格式数据转换为 TXT 文件
 */
public class CSVToTXT {

    private static final String inputPath = "D:\\车辆数据\\新建文件夹\\vin=LVVDC17B8JD186778\\part-00000-ad4d265c-3e5f-4c25-b622-258cba8571ba.csv";
    private static final String outputPath = "D:\\车辆数据\\新建文件夹\\vin=LVVDC17B8JD186778\\part-00000-ad4d265c-3e5f-4c25-b622-258cba8571ba.txt";
    private static final String encode = "GBK";

    public static void main(String[] args) {
        //输入流
        InputStreamReader ir = null;
        BufferedReader reader = null;

        //输出流
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            System.out.println("开始");
            //读取文件
            ir = new InputStreamReader(new FileInputStream(new File(inputPath)), encode);
            reader = new BufferedReader(ir);
            String line = reader.readLine();

            //filePath 为文件路径 true 开启追加模式 ScanPackage.encode 文件编码格式
            ow = new OutputStreamWriter(new FileOutputStream(new File(outputPath)), encode);
            bw = new BufferedWriter(ow);
            //读取第一行数据
//            String[] split = line.split(",");
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                line = getWriteString(item);
                //开始写
                if ("".equals(line)) continue;
                bw.write(line);
                bw.newLine();
            }
            System.out.println("结束");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != reader) reader.close();
                if (null != ir) ir.close();
            } catch (IOException e) {
                System.err.println("输入流关闭异常");
            }
            try {
                if (null != bw) bw.close();
                if (null != ow) ow.close();
            } catch (IOException e) {
                System.err.println("输出流关闭异常");
            }
        }
    }

    /**
     * @param item 数据
     * @return java.lang.String
     * @des 将表头和读取到的表数据转换为要出去的格式   key_value,形式
     * @author liwenjie
     * @date 2019/11/14  9:05
     */
    private static String getWriteString(String[] item) {
        StringBuffer str = new StringBuffer(1000);
        if (item.length < 90) return "";
        str.append("VIN:L6T78Y4W4GN084987,");
//        str.append("3201:" + item[89] + ",");
//        str.append("2301:" + item[65] + ",");
//        str.append("2201:" + item[1] + ",");
        str.append("2000:" + item[65] + ",");
//        str.append("TIME:" + item[68] + ",");
//        str.append("2615:" + item[3] + ",");
//        str.append("2202:" + item[10] + ",");
//        str.append("2613:" + item[4] + ",");
//        str.append("2614:" + item[2] + ",");
        str.append("2003:" + get(item[23].substring(2)) + ",");
        str.append("2103:" + item[32] + ",");
//        str.append("2617:" + item[50] + ",");
        return str.toString();
    }

    private static String get(String s) {
        StringBuffer stringBuffer = new StringBuffer();
        if (s != null && s.length()>200) {
            String[] s1 = s.split("_");
            for (int i = 0; i < s1.length; i++) {
                if (i > 0) stringBuffer.append("_");
                try {
                    stringBuffer.append(Double.parseDouble(s1[i]) / 1000);
                } catch (NumberFormatException e) {
                    return "";
                }
            }
            return stringBuffer.toString();
        }
        return "";
    }


}
