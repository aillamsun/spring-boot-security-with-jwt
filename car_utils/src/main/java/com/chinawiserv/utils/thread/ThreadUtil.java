package com.chinawiserv.utils.thread;

/**
 * Created by sungang on 2017/8/16.
 */
public class ThreadUtil {

    /**
     * JVM退出会触发该动作
     * <p>Function: runWhenJVMExit</p>
     * <p>Description: </p>
     *
     * @author zhaoxy@thankjava.com
     * @date 2016年1月5日 下午2:45:12
     * @version 1.0
     */
    public static void runWhenJVMExit(Runnable runnable) {
        Runtime.getRuntime().addShutdownHook(new Thread(runnable));
    }
}
