package com.lyun.lawyer.api;

/**
 * @author 赵尉尉
 * @date 2016/12/20
 */

public class APIConstants {

    public static final String LOGIN = "login/";
    public static final String RESET_PASSWORD = "uppassword/";//修改密码
    public static final String STATISTICS_TRANSLATE="statisticsTranslate/";//查询服务总时长，服务人次

    // translation order service
    public static final String QUERY_TRANSLATION_ORDERS = "findByBillState/";
    public static final String GRAB_TRANSLATION_ORDERS = "upBillStatus/";
    public static final String HEART_BEAT = "heartbeat/";//查询剩余时间

}
