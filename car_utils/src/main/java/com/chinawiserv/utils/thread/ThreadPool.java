package com.chinawiserv.utils.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by sungang on 2017/8/16.
 */
public class ThreadPool {

    /**
     * 核心线程保持数量
     */
    private static final int INIT_THREAD_NUM = 50;

    /**
     * 工作线程最大数量
     */
    private static final int MAX_THREAD_NUM = 10000;

    /**
     * 空闲线程最大等待时间
     */
    private static final int ALIVE_TIME = 1000;

    /**
     * 排队任务接受最大数目
     */
    private static final int WAIT_RUNABLE_MAX_NUM = 200;

    private static ThreadPoolExecutor threadPoolExecutor;

    public ThreadPool() {
        threadPoolExecutor = new ThreadPoolExecutor(
                INIT_THREAD_NUM,
                MAX_THREAD_NUM,
                ALIVE_TIME,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(WAIT_RUNABLE_MAX_NUM),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        threadPoolExecutor.allowCoreThreadTimeOut(true);
    }

    /**
     * 指定线程池配置
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param initThreadNum     初始线程数量
     * @param maxThreadNum      最大线程数量
     * @param aliveTime         等待线程最长时间(ms)
     * @param waitRunableMaxNum 接受排队线程最大数量
     */
    public ThreadPool(int initThreadNum, int maxThreadNum, int aliveTime, int waitRunableMaxNum) {
        threadPoolExecutor = new ThreadPoolExecutor(
                initThreadNum,
                maxThreadNum,
                aliveTime,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(waitRunableMaxNum),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        threadPoolExecutor.allowCoreThreadTimeOut(true);
    }

    /**
     * 执行任务
     * <p>Function: execute</p>
     * <p>Description: </p>
     *
     * @param command
     * @author zhaoxy@thankjava.com
     * @date 2016年1月5日 下午3:21:05
     * @version 1.0
     */
    public void execute(Runnable command) {
        threadPoolExecutor.execute(command);
    }

    public void destroy() {
        if (!threadPoolExecutor.isShutdown()) {
            threadPoolExecutor.shutdown();
        }
    }
}
