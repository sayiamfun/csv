package com.sayiamfun.csv;

import com.sayiamfun.csv.entity.BatteryMonomer;
import com.sayiamfun.csv.utils.Do;
import com.sayiamfun.csv.utils.Outs;
import com.sayiamfun.common.utils.ScanPackage;
import lombok.Data;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author liwenjie
 * @date 2019/10/10 + 11:18
 * @describe  处理波动性检测文件
 */

@Data
class Student{
    public String name;
    public List<int[]> list;
}
public class Main {
    public static void main(String[] args) {
        lastWeekVolte();
    }

    public static void lastWeekVolte(){
        try {
            //读取
            ArrayList<String> strings = ScanPackage.scanFilesWithRecursion("D:\\文档资料\\预警\\20191009182230");
            List<String> list = new LinkedList<>();
            //处理
            for (String string : strings) {
                if(!string.contains("压降一致性")) continue;
                if(Integer.parseInt(string.substring(string.indexOf("日期")+3,string.indexOf("日期")+11))<20190724) continue;
                BufferedReader reader = new BufferedReader(new FileReader(new File(string)));//换成你的文件名
    //            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
                String line = null;
                boolean b = true;
                while ((line = reader.readLine()) != null) {
                    if (b) {
                        b = false;
                        continue;
                    }
                    String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                    String[] s = item[8].split("_");
                    list.add(string.substring(string.indexOf("日期")+3,string.indexOf("日期")+11) + "," + Arrays.toString(s).substring(1,Arrays.toString(s).length()-1));
                }
            }
            //导出
            File csv = new File("D:\\out\\20191025\\LZ90GWDV4H2001558最近一周单体电压.csv"); // CSV数据文件
            OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(csv),"UTF-8");
            BufferedWriter bw = new BufferedWriter(ow); // 附加
            for (String strings1 : list) {
                bw.write(strings1);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
    *@describe  按天统计
    *@author liwenjie
    *@date  2019/10/10   16:38
    *@params []
    *@return void
    */
    private static void getdaySumAndNumAndAverage() {
        try {
//            String[] array = new String[]{"20181207","20181208","20181210","20181213","20181215","20181218","20181224","20181225","20181227","20181231","20190102","20190109","20190112","20190115","20190118","20190120","20190128","20190201","20190219","20190223","20190226","20190301","20190305","20190319","20190325","20190326","20190402","20190405","20190406","20190418","20190421","20190422","20190514","20190517","20190518","20190521","20190522","20190525","20190526","20190531","20190601","20190602","20190603","20190604","20190605","20190606","20190607","20190608","20190609","20190610","20190613","20190619","20190708","20190709","20190711","20190712","20190720","20190723","20190725","20190727","20190730","20190731"};
            String[] array = new String[]{"20190708","20190709","20190711","20190712","20190720","20190723","20190725","20190727","20190730","20190731"};
            List<String> strings1 = Arrays.asList(array);
//            String[] array1 = new String[]{"1","2","7","12","13","16","18","20","30","32","37","40","41","42","47","49","52","53","54","58","60","62","63","65","66","71","72","74","76","79","80","81","83","85","89","90","94","95","96","100","102","104","107","108","110","119","120","123","125","134","142","143","144","145","146","148","151","152"};
            String[] array1 = new String[]{"2","42","49","52","58","60","65","66","74","80","85","94","120","142","143","144"};
            List<String> strings2 = Arrays.asList(array1);
            Map<Integer, Map<String,BatteryMonomer>> map = new TreeMap<>();
            DecimalFormat df = new DecimalFormat("#.00");
            ArrayList<String> strings = ScanPackage.scanFilesWithRecursion("D:\\文档资料\\预警\\20191009182230");
            for (String string : strings) {
                Do.daySumAndAverage(map, string);
            }
//            Outs.toSOCCSV(map,"波动性检测行驶状态按天统计.csv");
            String temp = "";
            Set<Integer> set = new TreeSet<>();
            Set<Integer> set1 = new TreeSet<>();
            for (Map.Entry<Integer, Map<String, BatteryMonomer>> stringMapEntry : map.entrySet()) {
                set.add(stringMapEntry.getKey());
                Map<String, BatteryMonomer> value = stringMapEntry.getValue();
                for (Map.Entry<String, BatteryMonomer> stringBatteryMonomerEntry : value.entrySet()) {
                    BatteryMonomer value1 = stringBatteryMonomerEntry.getValue();
                    set1.add(Integer.parseInt(value1.getCode()));
                    temp+="["+strings2.indexOf(value1.getCode())+","+strings1.indexOf(""+stringMapEntry.getKey())+","+df.format(value1.getSum())+"],";
                    System.out.println(stringMapEntry.getKey()+","+value1.getCode()+","+df.format(value1.getSum())+","+value1.getNum()+","+df.format(value1.getSum()/value1.getNum()));
                }
            }
            for (Integer integer : set) {
                System.out.print("'"+integer+"',");
            }
            System.out.println();
            for (Integer integer : set1) {
                System.out.print("'"+integer+"',");
            }
            System.out.println();
            System.out.println(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @return void
     * @describe 获得单体异常次数，所获分数和和单体分数平均值和SOC的关系
     * @author liwenjie
     * @date 2019/10/10   14:10
     * @params []
     */
    private static void getSOCWithSumAndNumAndAverage() {
        try {
            String[] array = new String[]{"1","2","7","12","13","16","18","20","30",
                    "32","37","40","41","42","47","49","52","53","54",
                    "58","60","62","63","65","66","71","72","74","76",
                    "79","80","81","83","85","89","90","94","95","96",
                    "100","102","104","107","108","110","119","120","123","125",
                    "134","142","143","144","145","146","148","151","152",
                    "10","20","30","40","50","60","70","80","90"};
            List<String> strings1 = Arrays.asList(array);
            String[] array1 = new String[]{"10","20","30","40","50","60","70","80","90"};
            List<String> strings2 = Arrays.asList(array1);
            Map<String, Map<String, BatteryMonomer>> map = new HashMap<>();
            DecimalFormat df = new DecimalFormat("#.00");
            ArrayList<String> strings = ScanPackage.scanFilesWithRecursion("D:\\文档资料\\预警\\20191009182230");
            for (String string : strings) {
                Do.socWithSumAndAverage(map, string);
            }
//            Outs.toSOCCSV(map,"SOC波动性检测行驶状态统计.csv");
            String temp = "";
            int i = 0;
            for (Map.Entry<String, Map<String, BatteryMonomer>> stringMapEntry : map.entrySet()) {
                for (Map.Entry<String, BatteryMonomer> stringBatteryMonomerEntry : stringMapEntry.getValue().entrySet()) {
                    BatteryMonomer value = stringBatteryMonomerEntry.getValue();
//                    System.out.println(stringMapEntry.getKey() + "," + value.getCode() + "," + df.format(value.getSum()) + "," + value.getNum() + "," + df.format(value.getSum() / value.getNum()));
                    i++;
                    if(i%10==0) temp += "\n";
                    temp+="["+strings2.indexOf(stringMapEntry.getKey())+","+strings1.indexOf(value.getCode())+","+df.format(value.getSum())+"],";
                }
            }
            System.out.println(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return void
     * @describe 获得单体异常次数，所获分数和和单体分数平均值
     * @author liwenjie
     * @date 2019/10/10   13:52
     * @params []
     */
    private static void getSumAndNumAndAverage() {
        try {
            Map<String, BatteryMonomer> map = new HashMap<>();
            DecimalFormat df = new DecimalFormat("#.00");
            ArrayList<String> strings = ScanPackage.scanFilesWithRecursion("D:\\文档资料\\预警\\20191009182230");
            for (String string : strings) {
                Do.sumAndAverage(map, string);
            }

            Outs.toCSV(map,"最近一周波动性检测充电状态统计.csv");
//            for (Map.Entry<String, BatteryMonomer> stringBatteryMonomerEntry : map.entrySet()) {
//                System.out.println(stringBatteryMonomerEntry.getValue());
//            }
            /*List<Integer> list = new ArrayList<>();
            for (Map.Entry<String, BatteryMonomer> stringBatteryMonomerEntry : map.entrySet()) {
                BatteryMonomer value = stringBatteryMonomerEntry.getValue();
                list.add(Integer.parseInt(value.getCode()));
            }
            Collections.sort(list);
            int i = 0;
            for (Integer integer : list) {
                i++;
                if(i%10==0){
                    i = 0;
                    System.out.println();
                }
                System.out.print("'" + integer + "',");
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
