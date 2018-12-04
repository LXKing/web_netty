package com.aisafer.webgis.model;

/**
 * netty发送消息编码封装类
 *
 * @Author:weiyuanlong
 * @Date: Created in 2018-10-27 15:52:55
 * @Modified By:
 */
public class MyMessageCode {

    /** 登录返回消息 */
    public static final Integer LOGIN_AUTH = 100;

    /** 车辆上线消息 */
    public static final Integer CODE_LINE_ON = 101;

    /** 车辆下线消息 */
    public static final Integer CODE_LINE_OUT = 102;

    /** 车辆位置信息消息 */
    public static final Integer CODE_LOCAT = 103;

    /** 车辆报警消息 */
    public static final Integer CODE_ALARM = 104;


}
