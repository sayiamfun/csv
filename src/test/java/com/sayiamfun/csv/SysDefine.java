package com.sayiamfun.csv;

import java.io.Serializable;

public class SysDefine implements Serializable {
    private static final long serialVersionUID = 19901100002011L;

    //异常
    public static final String FE_1 = "254";//FE
    public static final String FE_2 = "65534";//FFFE
    public static final String FE_3 = "16777214";//FFFFFE
    public static final String FE_4 = "4294967294";//FFFFFFFE

    //无效
    public static final String FF_1 = "255";//FF
    public static final String FF_2 = "65535";//FFFF
    public static final String FF_3 = "16777215";//FFFFFF
    public static final String FF_4 = "4294967295";//FFFFFFFF

    /*-------------------------------标点符号-------------------------------------*/
    /**
     * 空格
     */
    public static final String SPACES = " ";
    /**
     * 空字符串
     */
    public static final String EMPTY = "";
    /**
     * 逗号
     */
    public static final String COMMA = ",";
    /**
     * 句号
     */
    public static final String PERIOD = ".";
    /**
     * 下划线
     */
    public static final String UNDERLINE = "_";
    /**
     * 冒号
     */
    public static final String COLON = ":";
    /**
     * 换行符
     */
    public static final String NEWLINE = "\r\n";
    /**
     * 反斜杠
     */
    public static final String BACKSLASH = "/";

    /*-------------------------------内部协议常量-------------------------------------*/
    public final static String COMMAND = "command";// 原始指令
    public final static String HEAD = "head";// 包头
    public final static String SEQ = "seq";// 业务序列号
    public final static String MACID = "macid";// 车辆标识
    public final static String CHANNEL = "channel";// 通道
    public final static String MTYPE = "mtype";// 类型
    public final static String CONTENT = "content";// 具体内容
    public final static String MSGID = "msgid";// 消息服务器id
    public final static String UUID = "uuid";// 指令唯一标识uuid
    public final static String PTYPE = "ptype";// 插件类型

    public final static String OEMCODE = "oecode"; // OEMCODE
    public final static String PLATECOLORID = "platecolorid"; // 车牌颜色ID
    public final static String TID = "tid"; // 终端ID
    public final static String VEHICLENO = "vehicleno"; // 车牌号
    public final static String SUBMIT = "SUBMIT";
    /**
     * 指令类型 原始报文
     */
    public final static String PACKET = "PACKET";

    /*-------------------------------指令-------------------------------------*/
    /**
     * 指令类型
     */
    public static final String UNKNOW = "UNKNOW";
    /**
     * 指令类型 链接状态通知
     */
    public static final String LINKSTATUS = "LINKSTATUS";

    /**
     * 定时任务关键字
     */
    public static final String ISALARM = "10001";
    /**
     * 定时任务关键字
     */
    public static final String ISONLINE = "10002";
    /**
     * 定时任务关键字
     */
    public static final String ISCHARGE = "10003";
    /**
     * 定时任务关键字
     */
    public static final String MILEAGE = "10004";
    /**
     * SaveService 接收时间戳
     */
    public static final String ONLINEUTC = "10005";

}
