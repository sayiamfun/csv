package com.sayiamfun.LHB15T3E0JG404306.read;

import com.sayiamfun.csv.entity.BatteryMonomer;

import java.io.*;
import java.util.*;
/**
* @des 波动一致性数据统计以及和时间的变化
* @author  liwenjie
* @date 2019/10/18  13:08
*/
public class FluctuatingConsistencyMain {
    //读取文件地址
    public final static String filePath = "D:\\out\\20191017\\source\\LHB15T3E0JG404306_波动性检测模型_20191017.csv";
    //输出文件根目录
    public final static String outFilePath = "D:\\out\\20191018\\";
    //字符编码
    public final static String encode = "GBK";
    //数据处理后存储结构
    public static Map<Integer, Map<String, Integer>> map = new TreeMap<>();
    //数据时间
    public final static Integer theDate = 20190307;
//    public final static Integer theDate = 20190214;
//    public final static Integer theDate = 0;

    public static void main(String[] args) {
        out3D();

    }

    public static void outCsv(String file1){
        try {
            Map<String,Integer> resultMap = new HashMap<>();
            for (Map.Entry<Integer, Map<String, Integer>> integerMapEntry : map.entrySet()) {
                for (Map.Entry<String, Integer> stringIntegerEntry : integerMapEntry.getValue().entrySet()) {
                    if(resultMap.containsKey(stringIntegerEntry.getKey())){
                        resultMap.put(stringIntegerEntry.getKey(),resultMap.get(stringIntegerEntry.getKey())+stringIntegerEntry.getValue());
                    }else {
                        resultMap.put(stringIntegerEntry.getKey(),stringIntegerEntry.getValue());
                    }
                }
            }
            File csv = new File(outFilePath + file1); // CSV数据文件
            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(csv), encode);
            BufferedWriter bw = new BufferedWriter(ow);
            bw.write("单体编号,异常次数");
            bw.newLine();
            for (Map.Entry<String, Integer> stringIntegerEntry : resultMap.entrySet()) {
                bw.write(stringIntegerEntry.getKey()+","+stringIntegerEntry.getValue());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
    * @des 3D柱状图
    * @author  liwenjie
    * @date 2019/10/18  10:19
    */
    public static void out3D(){
        readFile();
        outXY();
        outZ();
    }
    /**
    * @des 读取文件内容并处理
    * @author  liwenjie
    * @date 2019/10/18  9:19
    * @param
    * @result  void
    */
    public static Map<Integer, Map<String, Integer>> readFile() {
        try {
            InputStreamReader ir = new InputStreamReader(new FileInputStream(new File(filePath)));
            BufferedReader reader = new BufferedReader(ir);
            String line = null;
            Map<String, BatteryMonomer> childrenMap = new HashMap<>();
            int i = 0;
            while ((line = reader.readLine()) != null) {
                i++;
                if (i <= 2) continue;
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                int date = Integer.parseInt(item[0].substring(0, 8));
                if(date<theDate) continue;
                Map<String, Integer> stringIntegerMap = new HashMap<>();
                if (map.containsKey(date)) {
                    stringIntegerMap = map.get(date);
                    String[] s = item[7].split("_");
                    for (String s1 : s) {
                        if (stringIntegerMap.containsKey(s1)) {
                            stringIntegerMap.put(s1, stringIntegerMap.get(s1) + 1);
                        } else {
                            stringIntegerMap.put(s1, 1);
                        }
                    }
                } else {
                    String[] s = item[7].split("_");
                    for (String s1 : s) {
                        stringIntegerMap.put(s1, 1);
                    }
                }
                map.put(date,stringIntegerMap);
            }
            /*for (Map.Entry<Integer, Map<String, Integer>> integerMapEntry : map.entrySet()) {
                for (Map.Entry<String, Integer> stringIntegerEntry : integerMapEntry.getValue().entrySet()) {
                    System.out.println(integerMapEntry.getKey()+","+stringIntegerEntry.getKey()+","+stringIntegerEntry.getValue());
                }
            }*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
    * @des 输出x，y轴的数据
    * @author  liwenjie
    * @date 2019/10/18  9:47
    * @param
    * @result  void
    */
    public static void outXY(){
        Set<Integer> xset = new TreeSet<>();
        Set<Integer> yset = new TreeSet<>();
        for (Map.Entry<Integer, Map<String, Integer>> integerMapEntry : map.entrySet()) {
            xset.add(integerMapEntry.getKey());
            for (Map.Entry<String, Integer> stringIntegerEntry : integerMapEntry.getValue().entrySet()) {
                yset.add(Integer.parseInt(stringIntegerEntry.getKey()));
            }
        }
        StringBuffer x = new StringBuffer();
        StringBuffer y = new StringBuffer();
        boolean b = false;
        for (Integer integer : xset) {
            if(b) x.append(",");
            b = true;
            x.append("'"+integer+"'");

        }
        b = false;
        for (Integer s : yset) {
            if(b) y.append(",");
            b = true;
            y.append("'"+s+"'");
        }
        System.out.println("var hours = ["+x+"];");
        System.out.println("var days = ["+y+"];");
    }
    /**
    * @des 打印z轴数据
    * @author  liwenjie
    * @date 2019/10/18  10:19
    */
    public static void outZ(){
        StringBuffer z = new StringBuffer();
        List<String> X = Arrays.asList("'20190309','20190310','20190311','20190312','20190313','20190314'".split(","));
        List<String> Y = Arrays.asList("'2','8','13','14','16','25','36','44','63','73','76'".split(","));
        boolean b = false;
        for (Map.Entry<Integer, Map<String, Integer>> integerMapEntry : map.entrySet()) {
            for (Map.Entry<String, Integer> stringIntegerEntry : integerMapEntry.getValue().entrySet()) {
//                System.out.println(X.indexOf("'"+integerMapEntry.getKey()+"'") + "," + Y.indexOf("'"+stringIntegerEntry.getKey()+"'") + "," + stringIntegerEntry.getValue());
                if(b) z.append(",");
                b = true;
                String temp = "["+ Y.indexOf("'"+stringIntegerEntry.getKey()+"'") + "," + X.indexOf("'"+integerMapEntry.getKey()+"'") +","+ stringIntegerEntry.getValue()+"]";
                z.append(temp);
            }
        }
        System.out.println("var data = ["+z+"];");
    }
}
