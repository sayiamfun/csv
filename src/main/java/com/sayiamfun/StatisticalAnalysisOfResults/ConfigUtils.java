package com.sayiamfun.StatisticalAnalysisOfResults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {
    public static final Properties sysDefine = new Properties();
    public static final Properties sysParams = new Properties();
    private static Logger logger = LoggerFactory.getLogger(ConfigUtils.class);

    static {
        InputStream in = null;
        try {
            in = ConfigUtils.class.getClassLoader().getResourceAsStream("sysDefine.properties");
            sysDefine.load(in);
            in = ConfigUtils.class.getClassLoader().getResourceAsStream("parms.properties");
            sysParams.load(in);

        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    public static Properties getSysDefine() {
        return sysDefine;
    }

    public static void init() {
    }
}
