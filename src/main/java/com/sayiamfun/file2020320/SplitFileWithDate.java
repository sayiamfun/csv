package com.sayiamfun.file2020320;

import com.sayiamfun.common.utils.ScanPackage;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * 将一个文件的内容按日期分割
 */
public class SplitFileWithDate {

    public static void main(String[] args) {
        String path = "D:\\车辆数据\\格力数据\\20190719181614(1)\\LLLLLLLLLLLLLLLLLLLLL_容量衰退IC峰值.csv";
        String outPath = "D:\\车辆数据\\格力数据\\20190719181614(1)\\";
        readFile(path,outPath);
    }

    /**
     * 读取所有的文件内容
     *
     * @param path
     * @return
     */
    public static void readFile(String path,String outPath) {
        String modeName = path.split("_")[1];
        List<List<String>> resultList = new LinkedList<>(); //保存最后读取的所有数据，LinkedList 是为了保证顺序一致
        InputStreamReader ir = null;
        BufferedReader reader = null;

        //CSV文件输出可追加
        OutputStreamWriter ow = null;
        BufferedWriter bw = null;
        try {
            ir = new InputStreamReader(new FileInputStream(new File(path)), ScanPackage.encode);
            reader = new BufferedReader(ir);//到读取的文件
            String line = reader.readLine();
            String title = line;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                List<String> childrenList = new LinkedList<>();//将数组转换为列表存储，和excel读取结果一致，方便处理LinkedList 是为了保证顺序一致
                for (String s : item) {
                    childrenList.add(s);
                }
                resultList.add(childrenList);
            }

            Integer date = 0;
            Integer thisdate = 0;
            for (List<String> strings : resultList) {
                thisdate = Integer.parseInt(strings.get(2).substring(0, 8));
                if (thisdate.intValue() != date.intValue()) {
                    ow = new OutputStreamWriter(new FileOutputStream(new File(outPath+"out\\LLLLLLLLLLLLLLLLLLLL_"+modeName+"_" + thisdate + ".csv")), ScanPackage.encode);
                    bw = new BufferedWriter(ow);
                    date = thisdate;
                    bw.write(title); //中间，隔开不同的单元格，一次写一行
                    bw.newLine();
                }
                System.out.println(String.join(",", strings));
                bw.write(String.join(",", strings).replaceAll("LJUB0W1N5HS004016","LLLLLLLLLLLLLLLLLLLL").replaceAll("1111111111111110000000","1111111111111111111111")); //中间，隔开不同的单元格，一次写一行
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != reader) reader.close();
                if (null != ir) ir.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (null != bw) bw.close();
                if (null != ow) ow.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
