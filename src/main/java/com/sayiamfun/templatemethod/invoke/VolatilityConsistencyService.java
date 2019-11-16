package com.sayiamfun.templatemethod.invoke;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author liwenjie
 * @des 处理波动性检测文件数据，封装后交给result类处理
 * @date 2019/11/4  13:20
 */
@Service
public class VolatilityConsistencyService {

    /**
    * @des 获取波动一致性异常单体出现次数与时间的关系   全数据/最近一月/最近一周
    * @author  liwenjie
    * @date 2019/11/6  9:51
    * @return java.util.Map<java.lang.Integer,java.util.Map<java.lang.Integer,java.util.Map<java.lang.Integer,java.lang.Integer>>>
    * @param list
    */
    public Map<Integer, Map<Integer, Map<Integer, Integer>>> NumberOfAbnormalMonomersWithDate(ArrayList<String> list) {
        Map<Integer, Map<Integer, Map<Integer, Integer>>> map = new TreeMap<>();
        List<String[]> items = getItems(list, "波动性");
        int date = Integer.parseInt(items.get(items.size() - 1)[0].substring(0, 8));
        Map<Integer, Map<Integer, Integer>> map1 = new HashMap<>();
        Map<Integer, Map<Integer, Integer>> map2 = new HashMap<>();
        Map<Integer, Map<Integer, Integer>> map3 = new HashMap<>();
        for (String[] item : items) {
            if (null == item[7] || "".equals(item[7]) || null == item[0] || "".equals(item[0]))
                continue;
            //所有数据
            addValueToMap(map1, item[7], item[0]);
            //最近一月
            if (Integer.parseInt(item[0].substring(0, 8)) > date - 31) {
                addValueToMap(map2, item[7], item[0]);
            }
            //最近一周
            if (Integer.parseInt(item[0].substring(0, 8)) > date - 7) {
                addValueToMap(map3, item[7], item[0]);
            }
        }
        map.put(1, map1);
        map.put(2, map2);
        map.put(3, map3);
        return map;
    }

    /**
     * @param list
     * @return java.util.Map<java.lang.Integer, java.util.Map < java.lang.Integer, java.util.Map < java.lang.Integer, java.lang.Integer>>>
     * @des 获取波动一致性异常单体出现次数与SOC区间的关系   全数据/最近一月/最近一周
     * @author liwenjie
     * @date 2019/11/4  17:26
     */
    public Map<Integer, Map<Integer, Map<Integer, Integer>>> NumberOfAbnormalMonomersWithSoc(ArrayList<String> list) {
        Map<Integer, Map<Integer, Map<Integer, Integer>>> map = new TreeMap<>();
        List<String[]> items = getItems(list, "波动性");
        int date = Integer.parseInt(items.get(items.size() - 1)[0].substring(0, 8));
        Map<Integer, Map<Integer, Integer>> map1 = new HashMap<>();
        Map<Integer, Map<Integer, Integer>> map2 = new HashMap<>();
        Map<Integer, Map<Integer, Integer>> map3 = new HashMap<>();
        for (String[] item : items) {
            if (null == item[7] || "".equals(item[7]) || null == item[5] || "".equals(item[5]) || null == item[6] || "".equals(item[6]))
                continue;
            //所有数据
            addValueToMap(map1, item[7], item[5], item[6]);
            //最近一月
            if (Integer.parseInt(item[0].substring(0, 8)) > date - 31) {
                addValueToMap(map2, item[7], item[5], item[6]);
            }
            //最近一周
            if (Integer.parseInt(item[0].substring(0, 8)) > date - 7) {
                addValueToMap(map3, item[7], item[5], item[6]);
            }
        }
        map.put(1, map1);
        map.put(2, map2);
        map.put(3, map3);
        return map;
    }

    /**
     * @des 获取波动一致性异常单体出现次数   全数据/最近一月/最近一周
     * @author liwenjie
     * @date 2019/11/4  14:05
     * @params map  list
     */
    public Map<String, Map<Integer, Integer>> NumberOfAbnormalMonomers(ArrayList<String> list) {
        Map<String, Map<Integer, Integer>> map = new HashMap<>();
        List<String[]> items = getItems(list, "波动性");
        int date = Integer.parseInt(items.get(items.size() - 1)[0].substring(0, 8));
        Map<Integer, Integer> map1 = new HashMap<>();
        Map<Integer, Integer> map2 = new HashMap<>();
        Map<Integer, Integer> map3 = new HashMap<>();
        for (String[] item : items) {
            if (null == item[7] || "".equals(item[7])) continue;
            //所有数据
            addValueToMap(map1, item[7]);
            //最近一月
            if (Integer.parseInt(item[0].substring(0, 8)) > date - 31) {
                addValueToMap(map2, item[7]);
            }
            //最近一周
            if (Integer.parseInt(item[0].substring(0, 8)) > date - 7) {
                addValueToMap(map3, item[7]);
            }
        }
        map.put("NumberOfAbnormalMonomers1", sortByValue(map1));
        map.put("NumberOfAbnormalMonomers2", sortByValue(map2));
        map.put("NumberOfAbnormalMonomers3", sortByValue(map3));
        return map;
    }

    /**
     * @des 根据value对map进行排序
     * @author liwenjie
     * @date 2019/11/4  16:12
     * @params map
     */
    public static Map sortByValue(Map map) {
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());//升序
                //return o2.getValue().compareTo(o1.getValue()); 降序
            }
        });
        Map newMap = new LinkedHashMap();
        Iterator<Map.Entry<Integer, Integer>> iter = list.iterator();
        Map.Entry tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            newMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }

        return newMap;
    }

    /**
     * @param map1   map集合
     * @param string 出现的单体号
     * @return void
     * @des 向map中添加单体出现次数，统计单体出现次数
     * @author liwenjie
     * @date 2019/11/4  17:11
     */
    private static void addValueToMap(Map<Integer, Integer> map1, String string) {
        String[] s = string.split("_");
        for (String s1 : s) {
            if (map1.containsKey(Integer.parseInt(s1))) {
                map1.put(Integer.parseInt(s1), map1.get(Integer.parseInt(s1)) + 1);
            } else {
                map1.put(Integer.parseInt(s1), 1);
            }
        }

    }

    /**
     * @param map1
     * @param string
     * @param dateString
     * @return void
     * @des 向map中添加单体出现次数，统计单体出现次数随
     * @author liwenjie
     * @date 2019/11/6  9:31
     */
    private static void addValueToMap(Map<Integer, Map<Integer, Integer>> map1, String string, String dateString) {
        int date = Integer.parseInt(dateString.substring(0, 8));
        String[] s = string.split("_");
        for (String s1 : s) {
            if (map1.containsKey(date)) {
                Map<Integer, Integer> integerIntegerMap = map1.get(date);
                if (integerIntegerMap.containsKey(Integer.parseInt(s1))) {
                    integerIntegerMap.put(Integer.parseInt(s1), integerIntegerMap.get(Integer.parseInt(s1)) + 1);
                } else {
                    integerIntegerMap.put(Integer.parseInt(s1), 1);
                }
                map1.put(date, integerIntegerMap);
            } else {
                Map<Integer, Integer> childMap = new HashMap<>();
                childMap.put(Integer.parseInt(s1), 1);
                map1.put(date, childMap);
            }
        }
    }

    /**
     * @param map1    要添加的map
     * @param string  出现异常的单体编号
     * @param lastSOC 上一次的SOC值
     * @param nowSOC  本次的SOC值
     * @return void
     * @des 向map中添加单体出现次数，统计单体出现次数在不同的SOC区间
     * @author liwenjie
     * @date 2019/11/4  17:24
     */
    private static void addValueToMap(Map<Integer, Map<Integer, Integer>> map1, String string, String lastSOC, String nowSOC) {
        int soc = (Integer.parseInt(lastSOC) + Integer.parseInt(nowSOC)) / 2 / 10;
        String[] s = string.split("_");
        for (String s1 : s) {
            if (map1.containsKey(soc)) {
                Map<Integer, Integer> integerIntegerMap = map1.get(soc);
                if (integerIntegerMap.containsKey(Integer.parseInt(s1))) {
                    integerIntegerMap.put(Integer.parseInt(s1), integerIntegerMap.get(Integer.parseInt(s1)) + 1);
                } else {
                    integerIntegerMap.put(Integer.parseInt(s1), 1);
                }
                map1.put(soc, integerIntegerMap);
            } else {
                Map<Integer, Integer> childMap = new HashMap<>();
                childMap.put(Integer.parseInt(s1), 1);
                map1.put(soc, childMap);
            }
        }

    }

    /**
     * @param list 文件路径
     * @param flag 文件筛选条件   （路径内包含的字段）
     * @des 读取需要的文件内的数据
     * @author liwenjie
     * @date 2019/11/4  17:07
     * @retuen List<String [ ]>  文件内的数据，csv文件，每行按 ， 分割为数组
     */
    private static List<String[]> getItems(ArrayList<String> list, String flag) {
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
}
