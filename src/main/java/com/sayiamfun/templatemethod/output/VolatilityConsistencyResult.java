package com.sayiamfun.templatemethod.output;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 处理invoke的返回结果，封装之后给前端
 */
@Service
public class VolatilityConsistencyResult {

    /**
     * @param map
     * @param stringMapMap
     * @return java.util.Map<java.lang.String, java.util.Map < java.lang.String, java.lang.Object>>
     * @des 将统计的单体异常次数封装成页面需要的数据格式
     * @author liwenjie
     * @date 2019/11/4  17:43
     */
    public void numbs(Map<String, Map<String, Object>> map, Map<String, Map<Integer, Integer>> stringMapMap) {
        Map<String, Object> childrenMap = new HashMap<>();
        childrenMap.put("dataAxis", mapToKeyAndValueList(stringMapMap.get("NumberOfAbnormalMonomers1")).get("one"));
        childrenMap.put("data", mapToKeyAndValueList(stringMapMap.get("NumberOfAbnormalMonomers1")).get("two"));
        childrenMap.put("text", "<span>图中显示，出现异常次数的单体排名为：" + mapToKeyAndValueList(stringMapMap.get("NumberOfAbnormalMonomers1")).get("one").get(0) + "号单体、" +
                mapToKeyAndValueList(stringMapMap.get("NumberOfAbnormalMonomers1")).get("one").get(1) + "号单体、" +
                mapToKeyAndValueList(stringMapMap.get("NumberOfAbnormalMonomers1")).get("one").get(2) + "号单体</span>");
        map.put("frist", childrenMap);

        Map<String, Object> childrenMap1 = new HashMap<>();
        childrenMap1.put("dataAxis", mapToKeyAndValueList(stringMapMap.get("NumberOfAbnormalMonomers2")).get("one"));
        childrenMap1.put("data", mapToKeyAndValueList(stringMapMap.get("NumberOfAbnormalMonomers2")).get("two"));
        childrenMap1.put("text", "<span>图中显示，出现异常次数的单体排名为：" + mapToKeyAndValueList(stringMapMap.get("NumberOfAbnormalMonomers1")).get("one").get(0) + "号单体、" +
                mapToKeyAndValueList(stringMapMap.get("NumberOfAbnormalMonomers1")).get("one").get(1) + "号单体、" +
                mapToKeyAndValueList(stringMapMap.get("NumberOfAbnormalMonomers1")).get("one").get(2) + "号单体</span>");
        map.put("second", childrenMap1);

        Map<String, Object> childrenMap2 = new HashMap<>();
        childrenMap2.put("dataAxis", mapToKeyAndValueList(stringMapMap.get("NumberOfAbnormalMonomers3")).get("one"));
        childrenMap2.put("data", mapToKeyAndValueList(stringMapMap.get("NumberOfAbnormalMonomers3")).get("two"));
        childrenMap2.put("text", "<span>图中显示，出现异常次数的单体排名为：" + mapToKeyAndValueList(stringMapMap.get("NumberOfAbnormalMonomers1")).get("one").get(0) + "号单体、" +
                mapToKeyAndValueList(stringMapMap.get("NumberOfAbnormalMonomers1")).get("one").get(1) + "号单体、" +
                mapToKeyAndValueList(stringMapMap.get("NumberOfAbnormalMonomers1")).get("one").get(2) + "号单体</span>");
        map.put("third", childrenMap2);
    }

    /**
     * @des 将map数据转为两个list集合
     * @author liwenjie
     * @date 2019/11/4  16:23
     * @params map
     */
    private Map<String, List<Object>> mapToKeyAndValueList(Map<Integer, Integer> map) {
        Map<String, List<Object>> resultMap = new HashMap<>();
        List<Object> list1 = new LinkedList<>();
        List<Object> list2 = new LinkedList<>();
        for (Map.Entry<Integer, Integer> integerIntegerEntry : map.entrySet()) {
            list1.add(integerIntegerEntry.getKey());
            list2.add(integerIntegerEntry.getValue());
        }
        resultMap.put("one", list1);
        resultMap.put("two", list2);
        return resultMap;
    }

    /**
     * @param map
     * @param integerMap
     * @return void
     * @des 将统计的单体异常次数与SOC区间关系数据封装成页面需要的数据格式
     * @author liwenjie
     * @date 2019/11/6  9:15
     */
    public void numbsWithSoc(Map<String, Map<String, Object>> map, Map<Integer, Map<Integer, Map<Integer, Integer>>> integerMap) {
        //全量数据
        List<Integer> xlist = new ArrayList<>();
        List<Integer> ylist = new ArrayList<>();
        List<int[]> zlist = new ArrayList<>();
        Map<Integer, Map<Integer, Integer>> integerMapMap = integerMap.get(1);
        //首先获取 x 轴和 y 轴的全部数据  ，需要计算坐标使用
        for (Map.Entry<Integer, Map<Integer, Integer>> integerMapEntry : integerMapMap.entrySet()) {
            if (!ylist.contains(integerMapEntry.getKey()))
                ylist.add((integerMapEntry.getKey() + 1) * 10);
            for (Map.Entry<Integer, Integer> integerIntegerEntry : integerMapEntry.getValue().entrySet()) {
                if (!xlist.contains(integerIntegerEntry.getKey()))
                    xlist.add(integerIntegerEntry.getKey());
            }
        }
        //对 x轴和 y轴进行排序
        Collections.sort(xlist);
        Collections.sort(ylist);
        //根据 x轴和 y轴生成 z轴对应的坐标值
        for (Map.Entry<Integer, Map<Integer, Integer>> integerMapEntry : integerMapMap.entrySet()) {
            for (Map.Entry<Integer, Integer> integerIntegerEntry : integerMapEntry.getValue().entrySet()) {
                zlist.add(new int[]{ylist.indexOf((integerMapEntry.getKey() + 1) * 10), xlist.indexOf(integerIntegerEntry.getKey()), integerIntegerEntry.getValue()});
            }
        }
        Map<String, Object> childrenMap = new HashMap<>();
        childrenMap.put("hours", xlist);
        childrenMap.put("days", ylist);
        childrenMap.put("data", zlist);
        map.put("fouth", childrenMap);
        //最近一月数据
        List<Integer> xlist1 = new ArrayList<>();
        List<Integer> ylist1 = new ArrayList<>();
        List<int[]> zlist1 = new ArrayList<>();
        Map<Integer, Map<Integer, Integer>> integerMapMap1 = integerMap.get(2);
        //首先获取 x 轴和 y 轴的全部数据  ，需要计算坐标使用
        for (Map.Entry<Integer, Map<Integer, Integer>> integerMapEntry : integerMapMap1.entrySet()) {
            if (!ylist1.contains((integerMapEntry.getKey() + 1) * 10))
                ylist1.add((integerMapEntry.getKey() + 1) * 10);
            for (Map.Entry<Integer, Integer> integerIntegerEntry : integerMapEntry.getValue().entrySet()) {
                if (!xlist1.contains(integerIntegerEntry.getKey()))
                    xlist1.add(integerIntegerEntry.getKey());
            }
        }
        //对 x轴和 y轴进行排序
        Collections.sort(xlist1);
        Collections.sort(ylist1);
        //根据 x轴和 y轴生成 z轴对应的坐标值
        for (Map.Entry<Integer, Map<Integer, Integer>> integerMapEntry : integerMapMap1.entrySet()) {
            for (Map.Entry<Integer, Integer> integerIntegerEntry : integerMapEntry.getValue().entrySet()) {
                zlist1.add(new int[]{ylist1.indexOf((integerMapEntry.getKey() + 1) * 10), xlist1.indexOf(integerIntegerEntry.getKey()), integerIntegerEntry.getValue()});
            }
        }
        Map<String, Object> childrenMap1 = new HashMap<>();
        childrenMap1.put("hours", xlist1);
        childrenMap1.put("days", ylist1);
        childrenMap1.put("data", zlist1);
        map.put("fifth", childrenMap1);
        //最近一周数据
        List<Integer> xlist2 = new ArrayList<>();
        List<Integer> ylist2 = new ArrayList<>();
        List<int[]> zlist2 = new ArrayList<>();
        Map<Integer, Map<Integer, Integer>> integerMapMap2 = integerMap.get(3);
        //首先获取 x 轴和 y 轴的全部数据  ，需要计算坐标使用
        for (Map.Entry<Integer, Map<Integer, Integer>> integerMapEntry : integerMapMap2.entrySet()) {
            if (!ylist2.contains(integerMapEntry.getKey()))
                ylist2.add((integerMapEntry.getKey() + 1) * 10);
            for (Map.Entry<Integer, Integer> integerIntegerEntry : integerMapEntry.getValue().entrySet()) {
                if (!xlist2.contains(integerIntegerEntry.getKey()))
                    xlist2.add(integerIntegerEntry.getKey());
            }
        }
        //对 x轴和 y轴进行排序
        Collections.sort(xlist2);
        Collections.sort(ylist2);
        //根据 x轴和 y轴生成 z轴对应的坐标值
        for (Map.Entry<Integer, Map<Integer, Integer>> integerMapEntry : integerMapMap2.entrySet()) {
            for (Map.Entry<Integer, Integer> integerIntegerEntry : integerMapEntry.getValue().entrySet()) {
                zlist2.add(new int[]{ylist2.indexOf((integerMapEntry.getKey() + 1) * 10), xlist2.indexOf(integerIntegerEntry.getKey()), integerIntegerEntry.getValue()});
            }
        }
        Map<String, Object> childrenMap2 = new HashMap<>();
        childrenMap2.put("hours", xlist2);
        childrenMap2.put("days", ylist2);
        childrenMap2.put("data", zlist2);
        map.put("sixth", childrenMap2);
    }

    /**
     * @param map
     * @param integerMap
     * @return void
     * @des 将统计的单体异常次数与时间关系数据封装成页面需要的数据格式
     * @author liwenjie
     * @date 2019/11/6  9:42
     */
    public void numbsWithDate(Map<String, Map<String, Object>> map, Map<Integer, Map<Integer, Map<Integer, Integer>>> integerMap) {
        //全量数据
        List<Integer> xlist = new ArrayList<>();
        List<Integer> ylist = new ArrayList<>();
        List<int[]> zlist = new ArrayList<>();
        Map<Integer, Map<Integer, Integer>> integerMapMap = integerMap.get(1);
        //首先获取 x 轴和 y 轴的全部数据  ，需要计算坐标使用
        for (Map.Entry<Integer, Map<Integer, Integer>> integerMapEntry : integerMapMap.entrySet()) {
            if (!ylist.contains(integerMapEntry.getKey()))
                ylist.add(integerMapEntry.getKey());
            for (Map.Entry<Integer, Integer> integerIntegerEntry : integerMapEntry.getValue().entrySet()) {
                if (!xlist.contains(integerIntegerEntry.getKey()))
                    xlist.add(integerIntegerEntry.getKey());
            }
        }
        //对 x轴和 y轴进行排序
        Collections.sort(xlist);
        Collections.sort(ylist);
        //根据 x轴和 y轴生成 z轴对应的坐标值
        for (Map.Entry<Integer, Map<Integer, Integer>> integerMapEntry : integerMapMap.entrySet()) {
            for (Map.Entry<Integer, Integer> integerIntegerEntry : integerMapEntry.getValue().entrySet()) {
                zlist.add(new int[]{xlist.indexOf(integerIntegerEntry.getKey()), ylist.indexOf(integerMapEntry.getKey()), integerIntegerEntry.getValue()});
            }
        }
        Map<String, Object> childrenMap = new HashMap<>();
        childrenMap.put("hours", ylist);
        childrenMap.put("days", xlist);
        childrenMap.put("data", zlist);
        map.put("seventh", childrenMap);
        //最近一月数据
        List<Integer> xlist1 = new ArrayList<>();
        List<Integer> ylist1 = new ArrayList<>();
        List<int[]> zlist1 = new ArrayList<>();
        Map<Integer, Map<Integer, Integer>> integerMapMap1 = integerMap.get(2);
        //首先获取 x 轴和 y 轴的全部数据  ，需要计算坐标使用
        for (Map.Entry<Integer, Map<Integer, Integer>> integerMapEntry : integerMapMap1.entrySet()) {
            if (!ylist1.contains(integerMapEntry.getKey()))
                ylist1.add(integerMapEntry.getKey());
            for (Map.Entry<Integer, Integer> integerIntegerEntry : integerMapEntry.getValue().entrySet()) {
                if (!xlist1.contains(integerIntegerEntry.getKey()))
                    xlist1.add(integerIntegerEntry.getKey());
            }
        }
        //对 x轴和 y轴进行排序
        Collections.sort(xlist1);
        Collections.sort(ylist1);
        //根据 x轴和 y轴生成 z轴对应的坐标值
        for (Map.Entry<Integer, Map<Integer, Integer>> integerMapEntry : integerMapMap1.entrySet()) {
            for (Map.Entry<Integer, Integer> integerIntegerEntry : integerMapEntry.getValue().entrySet()) {
                zlist1.add(new int[]{xlist1.indexOf(integerIntegerEntry.getKey()), ylist1.indexOf(integerMapEntry.getKey()), integerIntegerEntry.getValue()});
            }
        }
        Map<String, Object> childrenMap1 = new HashMap<>();
        childrenMap1.put("hours", ylist1);
        childrenMap1.put("days", xlist1);
        childrenMap1.put("data", zlist1);
        map.put("eighth", childrenMap1);
        //最近一周数据
        List<Integer> xlist2 = new ArrayList<>();
        List<Integer> ylist2 = new ArrayList<>();
        List<int[]> zlist2 = new ArrayList<>();
        Map<Integer, Map<Integer, Integer>> integerMapMap2 = integerMap.get(3);
        //首先获取 x 轴和 y 轴的全部数据  ，需要计算坐标使用
        for (Map.Entry<Integer, Map<Integer, Integer>> integerMapEntry : integerMapMap2.entrySet()) {
            if (!ylist2.contains(integerMapEntry.getKey()))
                ylist2.add(integerMapEntry.getKey());
            for (Map.Entry<Integer, Integer> integerIntegerEntry : integerMapEntry.getValue().entrySet()) {
                if (!xlist2.contains(integerIntegerEntry.getKey()))
                    xlist2.add(integerIntegerEntry.getKey());
            }
        }
        //对 x轴和 y轴进行排序
        Collections.sort(xlist2);
        Collections.sort(ylist2);
        //根据 x轴和 y轴生成 z轴对应的坐标值
        for (Map.Entry<Integer, Map<Integer, Integer>> integerMapEntry : integerMapMap2.entrySet()) {
            for (Map.Entry<Integer, Integer> integerIntegerEntry : integerMapEntry.getValue().entrySet()) {
                zlist2.add(new int[]{xlist2.indexOf(integerIntegerEntry.getKey()), ylist2.indexOf(integerMapEntry.getKey()), integerIntegerEntry.getValue()});
            }
        }
        Map<String, Object> childrenMap2 = new HashMap<>();
        childrenMap2.put("hours", ylist2);
        childrenMap2.put("days", xlist2);
        childrenMap2.put("data", zlist2);
        map.put("ninth", childrenMap2);
    }
}
