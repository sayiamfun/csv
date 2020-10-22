package com.sayiamfun.StatisticalAnalysisOfResults;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberUtils {

    private static Logger logger = LoggerFactory.getLogger(NumberUtils.class);

    public static String stringNumber(String str) {
        if (!StringUtils.isEmpty(str)
                && (str.trim().matches("[-]?[0-9]+")
                || str.trim().matches("[-]?[0-9]+\\.[0-9]+")
        )
        ) {
            return str.trim();
        }
        return "0";
    }

    public static boolean stringIsNumber(String str) {
        if (!StringUtils.isEmpty(str)
                && (str.trim().matches("[-]?[0-9]+")
                || str.trim().matches("[-]?[0-9]+\\.[0-9]+")
        )
        ) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        long l = Long.parseLong(NumberUtils.stringNumber("61246.2"));
        logger.info("" + l);
    }
}
