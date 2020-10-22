package com.sayiamfun.StatisticalAnalysisOfResults;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 忽略单体配置
 */
public class ConfigerIgnoreTheMonomer {

    private static Logger logger = LoggerFactory.getLogger(ConfigerIgnoreTheMonomer.class);

    private static Map<String, String> vinIgore = new HashMap<>();
    private static Map<String, Map<String, String>> monomerNum = new HashMap<>();
    private static Map<String, Integer> monomerTotal = new HashMap<>();

    public static Map<String, Integer> getMonomerTotal() {
        return monomerTotal;
    }

    public static void setMonomerTotal(Map<String, Integer> monomerTotal) {
        ConfigerIgnoreTheMonomer.monomerTotal = monomerTotal;
    }

    public static Map<String, Map<String, String>> getMonomerNum() {
        return monomerNum;
    }

    public static void setMonomerNum(Map<String, Map<String, String>> monomerNum) {
        ConfigerIgnoreTheMonomer.monomerNum = monomerNum;
    }

    public static Map<String, String> getVinIgore() {
        return vinIgore;
    }

    public static void setVinIgore(Map<String, String> vinIgore) {
        ConfigerIgnoreTheMonomer.vinIgore = vinIgore;
    }

    public static void reflushConf(Conn conn) {

        Map<String, String> configs = new HashMap<>();
        try {
            List<String[]> strings = conn.selectBySql("select vin,monomer from cus_dbs.monomer_base", 2);
            for (String[] string : strings) {
                if (StringUtils.isEmpty(string[1])) continue;
                String[] split = string[1].split(",");
                StringBuilder stringBuilder = new StringBuilder(100);
                boolean b = false;
                for (String s : split) {
                    if (isNum(s)) {
                        b = true;
                        stringBuilder.append(s).append(",");
                    }
                }
                if (b) {
                    configs.put(string[0], StringUtils.substring(stringBuilder.toString(), 0, stringBuilder.length() - 1));
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        ConfigerIgnoreTheMonomer.vinIgore = configs;
    }

    public static boolean isNum(String macAddress) {
        String reg = "^[0-9]*[1-9][0-9]*$";
        return Pattern.compile(reg).matcher(macAddress).find();
    }

}
