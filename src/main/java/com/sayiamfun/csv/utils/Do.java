package com.sayiamfun.csv.utils;

import com.sayiamfun.csv.entity.BatteryMonomer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liwenjie
 * @date 2019/10/10 + 13:51
 * @describe
 */
public class Do {

    /**
    *@describe  按天统计
    *@author liwenjie
    *@date  2019/10/10   16:26
    *@params [map, filePath]
    *@return void
    */
    public static void daySumAndAverage(Map<Integer, Map<String,BatteryMonomer>> map, String filePath) {
        try {
            if (!filePath.contains("波动性检测模型")) return;
            if(Integer.parseInt(filePath.substring(filePath.indexOf("日期")+3,filePath.indexOf("日期")+11))<20190700) return;
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));//换成你的文件名
//            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            boolean b = true;
            Map<String,BatteryMonomer> childrenMap = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                if (b) {
                    b = false;
                    continue;
                }
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                if (!"2".equals(item[7])) continue;
                String last = item[6];//这就是你要的数据了
                String[] split = last.split("_");
                for (String s : split) {
                    String[] split1 = s.split(":");
                    if (childrenMap.containsKey(split1[0])) {
                        BatteryMonomer car = childrenMap.get(split1[0]);
                        car.setSum(car.getSum() + Double.parseDouble(split1[1]));
                        car.setNum(car.getNum() + 1);
                        childrenMap.put(split1[0], car);
                    } else {
                        BatteryMonomer car = new BatteryMonomer();
                        car.setCode(split1[0]);
                        car.setSum(Double.parseDouble(split1[1]));
                        car.setNum(1);
                        childrenMap.put(split1[0], car);
                    }
                }
            }
            map.put(Integer.parseInt(filePath.substring(filePath.indexOf("日期")+3,filePath.indexOf("日期")+11)),childrenMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @return void
     * @describe 得到单体异常次数，分数和，分数平均值和Soc的关系
     * @author liwenjie
     * @date 2019/10/10   13:54
     * @params [map, filePath]
     */
    public static void socWithSumAndAverage(Map<String, Map<String, BatteryMonomer>> map, String filePath) {
        try {
            if (!filePath.contains("波动性检测模型")) return;
            if(Integer.parseInt(filePath.substring(filePath.indexOf("日期")+3,filePath.indexOf("日期")+11))<20190724) return;
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));//换成你的文件名
//            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            boolean b = true;
            while ((line = reader.readLine()) != null) {
                if (b) {
                    b = false;
                    continue;
                }
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                if (!"2".equals(item[7])) continue;
                Integer soc1 = Integer.parseInt(item[4]);
                Integer soc2 = Integer.parseInt(item[5]);
                if (soc1 - soc2 > 10) continue;
                String num = "" + (soc1 + soc2) / 20 * 10;

                if (map.containsKey(num)) {
                    String last = item[6];//这就是你要的数据了
                    String[] split = last.split("_");
                    Map<String, BatteryMonomer> childrenMap = map.get(num);
                    for (String s : split) {
                        String[] split1 = s.split(":");
                        if (childrenMap.containsKey(split1[0])) {
                            BatteryMonomer car = map.get(num).get(split1[0]);
                            car.setSum(car.getSum() + Double.parseDouble(split1[1]));
                            car.setNum(car.getNum() + 1);
                            childrenMap.put(split1[0], car);
                        } else {
                            BatteryMonomer car = new BatteryMonomer();
                            car.setCode(split1[0]);
                            car.setSum(Double.parseDouble(split1[1]));
                            car.setNum(1);
                            childrenMap.put(split1[0], car);
                        }
                        map.put(num, childrenMap);
                    }
                } else {
                    String last = item[6];//这就是你要的数据了
                    String[] split = last.split("_");
                    Map<String, BatteryMonomer> childrenMap = new HashMap<>();
                    for (String s : split) {
                        String[] split1 = s.split(":");
                        if (childrenMap.containsKey(split1[0])) {
                            BatteryMonomer car = map.get(num).get(split1[0]);
                            car.setSum(car.getSum() + Double.parseDouble(split1[1]));
                            car.setNum(car.getNum() + 1);
                            childrenMap.put(split1[0], car);
                        } else {
                            BatteryMonomer car = new BatteryMonomer();
                            car.setCode(split1[0]);
                            car.setSum(Double.parseDouble(split1[1]));
                            car.setNum(1);
                            childrenMap.put(split1[0], car);
                        }
                        map.put(num, childrenMap);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return void
     * @describe 求异常单体的和，出现次数，平均值
     * @author liwenjie
     * @date 2019/10/9   16:00
     * @params [map, filePath]
     */
    public static void sumAndAverage(Map<String, BatteryMonomer> map, String filePath) {
        try {
            if (!filePath.contains("波动性检测模型")) return;
            if(Integer.parseInt(filePath.substring(filePath.indexOf("日期")+3,filePath.indexOf("日期")+11))<20190724) return;
            BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));//换成你的文件名
//            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            boolean b = true;
            while ((line = reader.readLine()) != null) {
                if (b) {
                    b = false;
                    continue;
                }
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                if (!"2".equals(item[7])) continue;
                String last = item[6];//这就是你要的数据了
                String[] split = last.split("_");
                for (String s : split) {
                    String[] split1 = s.split(":");
                    if (map.containsKey(split1[0])) {
                        BatteryMonomer car = map.get(split1[0]);
                        car.setSum(car.getSum() + Double.parseDouble(split1[1]));
                        car.setNum(car.getNum() + 1);
                        map.put(split1[0], car);
                    } else {
                        BatteryMonomer car = new BatteryMonomer();
                        car.setCode(split1[0]);
                        car.setSum(Double.parseDouble(split1[1]));
                        car.setNum(1);
                        map.put(split1[0], car);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
