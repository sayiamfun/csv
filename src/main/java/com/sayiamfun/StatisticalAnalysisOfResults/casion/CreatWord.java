package com.sayiamfun.StatisticalAnalysisOfResults.casion;

import com.aspose.words.Chart;
import com.aspose.words.Document;
import com.aspose.words.NodeType;
import com.aspose.words.Shape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CreatWord {

    private static Logger logger = LoggerFactory.getLogger(CreatWord.class);


    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        String template = "/Users/liwenjie/Downloads/vehData/XDoc/template/line-chart-template.docx";

        Document doc = new Document(template);
        //获取第一章节
        doc.getSections().get(0);


        //获取第一个shapeͼ图形
        Shape shape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);
        Chart chart = shape.getChart();

        //�����ԭ�����������
        chart.getSeries().clear();
        for (int i = 0; i < 5; i++) {
            double first = i;
            chart.getSeries().add("第" + i + "系列", new String[]{"1", "2"}, new double[]{first + i, first + i});
        }
        doc.save("/Users/liwenjie/Downloads/vehData/vehOut/result/output/test.docx");
        String color = "3CB371";
        Tool.ModifyColor("/Users/liwenjie/Downloads/vehData/vehOut/result/output/", "test.docx", 2, color);

    }

    private static void test1() throws Exception {
        // TODO Auto-generated method stub
        String template = "/Users/liwenjie/Downloads/vehData/XDoc/template/template.docx";

        Document doc = new Document(template);
        //获取第一章节
        doc.getSections().get(0);


        //获取第一个shapeͼ图形
        Shape shape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);
        Chart chart = shape.getChart();

        //�����ԭ�����������
        chart.getSeries().clear();
        for (int i = 0; i < 5; i++) {
            double first = i;
            chart.getSeries().add("第" + i + "系列", new String[]{""}, new double[]{first});
        }
        doc.save("/Users/liwenjie/Downloads/vehData/vehOut/result/output/result3.docx");
        String color = "3CB371";
        Tool.ModifyColor("/Users/liwenjie/Downloads/vehData/vehOut/result/output/", "result3.docx", 2, color);
    }

    public static void creatWotdOne(Map<Integer, Double> dataList, String template, String outPath, String fileName) {
        // TODO Auto-generated method stub

        Document doc = null;
        try {
            doc = new Document(template);
            //获取第一章节
            doc.getSections().get(0);
            //获取第一个shapeͼ图形
            Shape shape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);
            Chart chart = shape.getChart();

            //�����ԭ�����������
            chart.getSeries().clear();
            for (Map.Entry<Integer, Double> integerDoubleEntry : dataList.entrySet()) {
                chart.getSeries().add("单体" + integerDoubleEntry.getKey(), new String[]{""}, new double[]{integerDoubleEntry.getValue()});
            }
            doc.save(outPath + fileName);
            String color = "3CB371";
            Tool.ModifyColor(outPath, fileName, 2, color);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static Map<Integer, Double> getData(Map<Integer, Integer> entropyMapBatterSum) {
        Double nums = 0.0;
        for (Integer value : entropyMapBatterSum.values()) {
            nums += value;
        }
        Map<Integer, Double> resultMap = new TreeMap<>();
        for (Map.Entry<Integer, Integer> integerIntegerEntry : entropyMapBatterSum.entrySet()) {
            resultMap.put(integerIntegerEntry.getKey(), integerIntegerEntry.getValue() / nums);
        }
        return resultMap;
    }

    public static void createWotdTwoNums(Map<Long, Map<Integer, Integer>> dataList, int monNums, String template, String outPath, String fileName) {
        ArrayList<Long> longs = new ArrayList<>(dataList.keySet());
        Collections.sort(longs, (o1, o2) -> o1.compareTo(o2));
        String[] strings = new String[longs.size()];
        for (int i = 0; i < longs.size(); i++) {
            strings[i] = longs.get(i).toString();
        }
        Document doc = null;
        try {
            doc = new Document(template);
            //获取第一章节
            doc.getSections().get(0);
            //获取第一个shapeͼ图形
            Shape shape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);
            Chart chart = shape.getChart();

            //�����ԭ�����������
            chart.getSeries().clear();
            for (int i = 0; i < monNums; i++) {
                List<Double> tmpList = new LinkedList<>();
                for (Long aLong : longs) {
                    tmpList.add(getDoubleValue(dataList.get(aLong).get(i + 1)));
                }
                double[] integers = new double[tmpList.size()];
                for (int i1 = 0; i1 < tmpList.size(); i1++) {
                    integers[i1] = tmpList.get(i1);
                }
                chart.getSeries().add("单体" + (i + 1), strings, integers);
            }
            doc.save(outPath + fileName);
            String color = "3CB371";
            Tool.ModifyColor(outPath, fileName, 2, color);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static void createWotdTwoWeek(Map<Long, Map<Integer, Double>> dataList, int monNums, String template, String outPath, String fileName) {
        ArrayList<Long> longs = new ArrayList<>(dataList.keySet());
        Collections.sort(longs, (o1, o2) -> o1.compareTo(o2));
        String[] strings = new String[longs.size()];
        for (int i = 0; i < longs.size(); i++) {
            strings[i] = longs.get(i).toString();
        }
        Document doc = null;
        try {
            doc = new Document(template);
            //获取第一章节
            doc.getSections().get(0);
            //获取第一个shapeͼ图形
            Shape shape = (Shape) doc.getChild(NodeType.SHAPE, 0, true);
            Chart chart = shape.getChart();

            //�����ԭ�����������
            chart.getSeries().clear();
            for (int i = 0; i < monNums; i++) {
                List<Double> tmpList = new LinkedList<>();
                for (Long aLong : longs) {
                    tmpList.add(getDoubleValue(dataList.get(aLong).get(i + 1)));
                }
                double[] integers = new double[tmpList.size()];
                for (int i1 = 0; i1 < tmpList.size(); i1++) {
                    integers[i1] = tmpList.get(i1);
                }
                chart.getSeries().add("单体" + (i + 1), strings, integers);
            }
            doc.save(outPath + fileName);
            String color = "3CB371";
            Tool.ModifyColor(outPath, fileName, 2, color);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private static Double getDoubleValue(Integer integer) {
        return null == integer ? 0.0 : integer.doubleValue();
    }

    private static Double getDoubleValue(Double integer) {
        return null == integer ? 0.0 : integer.doubleValue();
    }


}
