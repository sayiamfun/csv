package com.sayiamfun.LHB15T3E0JG404306.controller;

import com.sayiamfun.common.Echars;
import com.sayiamfun.csv.word.ExportUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TestController {

    @GetMapping(value = "/EcharsShow")
    @ResponseBody
    public List<Echars> findById(Model model) {
        List<Echars> list = new ArrayList<Echars>();
        list.add(new Echars("帽子", 50));
        list.add(new Echars("鞋子", 126));
        list.add(new Echars("毛衣", 75));
        list.add(new Echars("羽绒服", 201));
        list.add(new Echars("羊毛衫", 172));
        System.err.println(list.toString());
        return list;
    }

    @GetMapping(value = "/index")
    public String echarts4(Model model) {
        System.err.println("========开始");
        return "index";
    }

    @GetMapping("putFile")
    public void putFile(HttpServletRequest request, HttpServletResponse response){
        /**
         * context 为以html样式导出到word文档里
         */
        String context = " \n" +
                "\n" +
                "\n" +
                "  \n" +
                "\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "  \n" +
                "  \n" +
                "  \"2\"align=\"center\"height=\"900px\"width=\"600px\"bordercolor=\"black\">\n" +
                "    \"table\"style=\"height: 200px;\">\n" +
                "      \n" +
                "\n" +
                "    \n" +
                "    \n" +
                "       \"width: 50%;\" align=\"center\"> \n" +
                "        分  析  报  告\n" +
                "        Failure Analysis Report\n" +
                "        名称：黑壳网\n" +
                "\"http://www.bhusk.com\">http:\\\\www.bhusk.com" +
                "       \n" +
                "  \n" +
                "\n" +
                "\n";
        /**
         * 创建工具类实例
         */
        ExportUtil exportUtil = new ExportUtil();

        /**
         * 调用~~~ 导出word成功
         */
        exportUtil.exportWord(request, response, context);
    }


}
