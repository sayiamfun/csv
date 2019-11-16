package com.sayiamfun.csv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CsvApplicationTests {

    @Test
    public void contextLoads() {
        /*EnhancedOption option = new EnhancedOption();
        option.legend("高度(km)与气温(°C)变化关系");

        option.toolbox().show(true).feature(
                Tool.mark,
                Tool.dataView,
                new MagicType(Magic.line, Magic.bar),
                Tool.restore,
                Tool.saveAsImage);

        option.calculable(true);
        option.tooltip().trigger(Trigger.axis).formatter("Temperature : <br/>{b}km : {c}°C");

        ValueAxis valueAxis = new ValueAxis();
        valueAxis.axisLabel().formatter("{value} °C");
        option.xAxis(valueAxis);
        option.animation(false);
        CategoryAxis categoryAxis = new CategoryAxis();
        categoryAxis.axisLine().onZero(false);
        categoryAxis.axisLabel().formatter("{value} km");
        categoryAxis.boundaryGap(false);
        categoryAxis.data(0, 10, 20, 30, 40, 50, 60, 70, 80);
        option.yAxis(categoryAxis);

        Line line = new Line();
        line.smooth(true).name("高度(km)与气温(°C)变化关系")
                .data(15, -50, -56.5, -46.5, -22.1, -2.5, -27.7, -55.7, -76.5)
                .itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
        option.series(line);
        option.exportToHtml("line5.html");
        option.print();
        option.view();
        //以上是生成echarts图表数据
        //爬取图片
        String q= LineTest5.getAjaxCotnent("");
        //System.out.println(System.getProperty("java.library.path"));
        //把图片放到已经做好的word模板中
        WordBean wordbean = null;
        try {
            wordbean = new WordBean();
            String contextPath = getClass().getResource("/").getFile().toString().replace("WEB-INF/classes/", "").replace("/", "\\").substring(1);
            System.out.println(contextPath);

            String inputDocPath =contextPath+"template\\testpng.doc";
            System.out.println(inputDocPath);
            String outputDocPath = inputDocPath.replace("testpng.doc", "line.doc");
            // String wordName;

            wordbean.openDocument(inputDocPath);
            wordbean.saveAs(outputDocPath);

            wordbean.replaceImage("png", contextPath+"template\\line5.png",300,300);
            wordbean.replaceText("kkk", "你好");
            wordbean.saveAs(outputDocPath);
            wordbean.close();
            wordbean.quit();
            String path = outputDocPath ;
            System.out.println(path);
        }catch (Exception e){
            wordbean.close();
            wordbean.quit();
            e.printStackTrace();
        }*/

    }

}
