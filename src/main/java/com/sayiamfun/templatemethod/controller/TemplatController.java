package com.sayiamfun.templatemethod.controller;

import com.sayiamfun.common.utils.ScanPackage;
import com.sayiamfun.templatemethod.invoke.VolatilityConsistencyService;
import com.sayiamfun.templatemethod.output.VolatilityConsistencyResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;
import java.util.*;


@Controller
public class TemplatController {

    @Autowired
    private VolatilityConsistencyService volatilityConsistencyService;
    @Autowired
    private VolatilityConsistencyResult volatilityConsistencyResult;

    /**
     * @des 获取所有的图表信息
     * @author liwenjie
     * @date 2019/11/1  14:44
     * @params
     */
    @ResponseBody
    @GetMapping("getEcharts")
    public Object getEcharts(String inputPath) throws FileNotFoundException {
        System.err.println(inputPath);
        //获取文件夹下的所有文件
        ArrayList<String> strings = ScanPackage.scanFilesWithRecursion("D:\\out\\20191017\\source");
        Map<String, Map<String, Object>> map = new TreeMap<>();
        //获取文件内的数据信息，根据条件处理并封装结果以共给result使用
        Map<String, Map<Integer, Integer>> stringMap = volatilityConsistencyService.NumberOfAbnormalMonomers(strings);
        //处理invoke输出的数据并封装结果给页面使用，单体异常次数
        volatilityConsistencyResult.numbs(map, stringMap);
        //异常单体出现次数与SOC区间关系结果封装
        Map<Integer, Map<Integer, Map<Integer, Integer>>> integerMapWithSOC = volatilityConsistencyService.NumberOfAbnormalMonomersWithSoc(strings);
        //处理invoke输出的数据并封装结果给页面使用，单体异常次数与SOC区间对应关系
        volatilityConsistencyResult.numbsWithSoc(map,integerMapWithSOC);
        //异常单体出现次数与时间关系结果封装
        Map<Integer, Map<Integer, Map<Integer, Integer>>> integerMapWithDate = volatilityConsistencyService.NumberOfAbnormalMonomersWithDate(strings);
        volatilityConsistencyResult.numbsWithDate(map,integerMapWithDate);
        return map;
    }


}
