package com.chinawiserv.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xuchy
 * Date: 16-7-11
 * Time: 下午6:05
 * To change this template use File | Settings | File Templates.
 * 订单工具类
 */
public class OrderUtil {
    /**
     * 锁对象，可以为任意对象
     */
    private static Object lockObj = "lockerOrder";

    /**
     * 订单号生成计数器
     */
    private static long orderNumCounter = 0L;

    /**
     * 每毫秒生成订单号数量最大值
     */
    private int maxPerMSECSize = 1000;

    /**
     * 订单根据对象
     */
    private static OrderUtil orderUtil;

    /*私有构造方法*/
    private OrderUtil(){}

    /**
     * 获取对象
     * @return
     */
    public static OrderUtil getInstance(){
        if(orderUtil == null){
            orderUtil = new OrderUtil();
        }
        return orderUtil;
    }

    /**
     * 生成非重复订单号
     */
    public String makeOrderNo() {
        //最终生成的订单号,固定前2位：LF
        String orderNo = "LF";
        try {
            synchronized (lockObj) {
                //取系统当前时间，精确到毫秒
                long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
                //计数器到最大值归零
                if (orderNumCounter > maxPerMSECSize) {
                    orderNumCounter = 0L;
                }
                //组装订单号
                String countStr = maxPerMSECSize  + orderNumCounter + "";
                orderNo += nowLong + countStr.substring(1);
                orderNumCounter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderNo;
    }

    /**
     * 生成非重复优惠券模板号
     */
    public String makeTemplateNo() {
        String orderNo = "TP";
        try {
            synchronized (lockObj) {
                //取系统当前时间，精确到毫秒
                long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
                //计数器到最大值归零
                if (orderNumCounter > maxPerMSECSize) {
                    orderNumCounter = 0L;
                }
                //组装订单号
                String countStr = maxPerMSECSize  + orderNumCounter + "";
                orderNo += nowLong + countStr.substring(1);
                orderNumCounter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderNo;
    }

    /**
     * 生成非重复积分换物订单号
     */
    public String makeCashGoodsNo() {
        //最终生成的订单号,固定前2位：GN
        String orderNo = "GN";
        try {
            synchronized (lockObj) {
                //取系统当前时间，精确到毫秒
                long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
                //计数器到最大值归零
                if (orderNumCounter > maxPerMSECSize) {
                    orderNumCounter = 0L;
                }
                //组装订单号
                String countStr = maxPerMSECSize  + orderNumCounter + "";
                orderNo += nowLong + countStr.substring(1);
                orderNumCounter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderNo;
    }

    /**
     * 生成唯一赢商通请求流水号
     */
    public String makeRequestSequence(String orderId) {
        //最终生成的订单号,固定前2位：LF
        try {
            synchronized (lockObj) {
                //取系统当前时间，精确到毫秒
                long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
                //计数器到最大值归零
                if (orderNumCounter > maxPerMSECSize) {
                    orderNumCounter = 0L;
                }
                //组装订单号
                String countStr = maxPerMSECSize  + orderNumCounter + "";
                orderId += nowLong + countStr.substring(1);
                orderNumCounter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderId;
    }

    public static void main(String[] args) {
        // 测试多线程调用订单号生成工具
        try {
            for (int i = 0; i < 100000; i++) {
                Thread t1 = new Thread(new Runnable() {
                    public void run() {
                        OrderUtil.getInstance().makeOrderNo();
                    }
                }, "at" + i);
                t1.start();

                Thread t2 = new Thread(new Runnable() {
                    public void run() {
                        OrderUtil.getInstance().makeOrderNo();
                    }
                }, "bt" + i);
                t2.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
