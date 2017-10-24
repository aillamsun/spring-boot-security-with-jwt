package com.chinawiserv.core.constants;

/**
 * Created with IntelliJ IDEA.
 * Date: 2016/6/14
 * Time: 14:23
 * To change this template use File | Settings | File Templates.
 */
public interface Constants {

    /**
     * 操作名称
     */
    String OP_NAME = "op";


    /**
     * 消息key
     */
    String MESSAGE = "message";

    /**
     * 错误key
     */
    String ERROR = "error";

    /**
     * 上个页面地址
     */
    String BACK_URL = "BackURL";

    String IGNORE_BACK_URL = "ignoreBackURL";

    /**
     * 当前请求的地址 带参数
     */
    String CURRENT_URL = "currentURL";

    /**
     * 当前请求的地址 不带参数
     */
    String NO_QUERYSTRING_CURRENT_URL = "noQueryStringCurrentURL";


    String CONTEXT_PATH = "ctx";

    /**
     * 当前登录的用户
     */
    String CURRENT_USER = "user";

    /**
     * 当前登录的CRM用户
     */
    String MEMBER_USER = "memberUser";

    /**
     * 当前门店id
     */
    String CURRENT_SHOP_ID = "shop_id";

    /**
     * 机器地址
     */
    String MAC_IP = "127.0.0.1";

    /**
     * 扣费金额
     */
    int CUT_MONEY = 2;
    /**
     * 当前登录用户名
     */
    String CURRENT_USERNAME = "username";
    /**
     * 系统编码
     */
    String ENCODING = "UTF-8";


    String AUTH_TOKEN = "auth_token";

    /**
     * 状态码
     */
    //删除状态
    final int DELETE = 0;
    //未被删除状态，默认状态
    final int NORMAL = 1;

    //所有订单查询是查询各个状态的个数
    final String EVERY_STATUS_COUNT = "all";

    /**
     * 模拟ajax请求成功返回码
     */
    final String REQUEST_SUCCESS = "200";

    final String SET_TIME_TYPE_PICKUP = "pickUp";
    final String SET_TIME_TYPE_DELIVER = "deliver";

    /**
     * 积分
     */
    //基础积分
    final int INTEGRAL_BASE = 1;
    //赠送积分
    final int INTEGRAL_GIVE = 2;


    final String INTEGRAL_TYPE_ADD = "add";


}
