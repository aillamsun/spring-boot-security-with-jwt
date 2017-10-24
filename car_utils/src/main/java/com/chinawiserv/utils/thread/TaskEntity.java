package com.chinawiserv.utils.thread;

/**
 * Created by sungang on 2017/8/16.
 */
public class TaskEntity {


    /**
     * 任务id
     */
    private String taskId;

    /**
     * 任务间隔时间
     */
    private int timeInterval = 0;

    /**
     * 任务启动延迟时间
     */
    private int startDelayTime = 0;

    /**
     * 任务执行体
     */
    private Runnable runnable;

    public String getTaskId() {
        return taskId;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public int getStartDelayTime() {
        return startDelayTime;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    /**
     * 初始化对象
     *
     * @param startDelayTime(s)
     * @param timeInterval      任务周期性启动的间隔,时间不能小于0(s)
     * @param runnable          执行体
     */
    public TaskEntity(int startDelayTime, int timeInterval, Runnable runnable) {
        if (startDelayTime < 0) {
            throw new IllegalArgumentException("timeInterval must greater than -1");
        }
        if (timeInterval <= 0) {
            throw new IllegalArgumentException("timeInterval must greater than 0");
        }

        this.taskId = Sequence.generateChar(6);
        this.timeInterval = timeInterval;
        this.runnable = runnable;
        this.startDelayTime = startDelayTime;
    }
}
