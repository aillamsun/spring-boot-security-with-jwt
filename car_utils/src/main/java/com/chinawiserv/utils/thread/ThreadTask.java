package com.chinawiserv.utils.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by sungang on 2017/8/16.
 */
public class ThreadTask {

    private ScheduledExecutorService scheduledExecutorService;

    private ConcurrentHashMap<String, ScheduledFuture<?>> runningTask = new ConcurrentHashMap<String, ScheduledFuture<?>>();

    /**
     * 初始化定时服务
     * <p>Title: </p>
     * <p>Description: </p>
     *
     * @param poolSize 初始化的核心线程数量
     */
    public ThreadTask(int poolSize) {
        scheduledExecutorService = Executors.newScheduledThreadPool(poolSize);
    }

    /**
     * 添加任务 该任务将在指定首次延迟时间之后周期循环
     * <p>Function: addTask</p>
     * <p>
     * Description: 如果任务的任何执行遇到异常,则抑制后续的执行,否则,任务只会通过执行器的取消或终止而终止
     * 1.当任务周期过长时，下一个任务就算到达执行时间也将会处于等待状态,直到上一个任务完成后将立刻执行
     * 2.下一个任务的执行时间并不是上一个任务完成后才计算指定的周期延时时间,而是上一个任务一开始,下一个任务的延迟时间就开始计算
     * </p>
     *
     * @param taskEntity
     * @author zhaoxy@thankjava.com
     * @date 2016年1月12日 上午11:39:12
     * @version 1.0
     */
    public void addTaskAtFixedRate(TaskEntity taskEntity) {
        ScheduledFuture<?> future = scheduledExecutorService.scheduleAtFixedRate(
                taskEntity.getRunnable(),
                taskEntity.getStartDelayTime(),
                taskEntity.getTimeInterval(),
                TimeUnit.SECONDS
        );
        runningTask.put(taskEntity.getTaskId(), future);
    }

    /**
     * 添加批量任务 该任务将在指定首次延迟时间之后周期循环
     * <p>Function: addTask</p>
     * <p>
     * Description: 如果任务的任何执行遇到异常,则抑制后续的执行,否则,任务只会通过执行器的取消或终止而终止
     * 1.当任务周期过长时，下一个任务就算到达执行时间也将会处于等待状态,直到上一个任务完成后将立刻执行
     * 2.下一个任务的执行时间并不是上一个任务完成后才计算指定的周期延时时间,而是上一个任务一开始,下一个任务的延迟时间就开始计算
     * </p>
     *
     * @param taskEntity
     * @author zhaoxy@thankjava.com
     * @date 2016年1月12日 上午11:39:12
     * @version 1.0
     */
    public void addTaskAtFixedRate(List<TaskEntity> taskEntitys) {
        for (TaskEntity taskEntity : taskEntitys) {
            ScheduledFuture<?> future = scheduledExecutorService.scheduleAtFixedRate(
                    taskEntity.getRunnable(),
                    taskEntity.getStartDelayTime(),
                    taskEntity.getTimeInterval(),
                    TimeUnit.SECONDS
            );
            runningTask.put(taskEntity.getTaskId(), future);
        }
    }

    /**
     * 添加任务 该任务将在指定首次延迟时间之后按照指定频率固定周期循环
     * <p>Function: addTaskWithFixedDelay</p>
     * <p>
     * Description: 如果任务的任何执行遇到异常,则抑制后续的执行,否则,任务只会通过执行器的取消或终止而终止
     * 1.当任务周期过长时，下一个任务就算到达执行时间也将会处于等待状态,直到上一个任务完成后才计算周期延迟时间
     * </p>
     *
     * @param taskEntity
     * @author zhaoxy@thankjava.com
     * @date 2016年10月8日 下午3:24:48
     * @version 1.0
     */
    public void addTaskWithFixedDelay(TaskEntity taskEntity) {
        ScheduledFuture<?> future = scheduledExecutorService.scheduleWithFixedDelay(
                taskEntity.getRunnable(),
                taskEntity.getStartDelayTime(),
                taskEntity.getTimeInterval(),
                TimeUnit.SECONDS
        );
        runningTask.put(taskEntity.getTaskId(), future);
    }

    /**
     * 添加批量任务 该任务将在指定首次延迟时间之后周期循环
     * <p>Function: addTask</p>
     * <p>
     * Description: 如果任务的任何执行遇到异常,则抑制后续的执行,否则,任务只会通过执行器的取消或终止而终止
     * 1.当任务周期过长时，下一个任务就算到达执行时间也将会处于等待状态,直到上一个任务完成后才计算周期延迟时间
     * </p>
     *
     * @param taskEntity
     * @author zhaoxy@thankjava.com
     * @date 2016年1月12日 上午11:39:12
     * @version 1.0
     */
    public void addTaskWithFixedDelay(List<TaskEntity> taskEntitys) {
        for (TaskEntity taskEntity : taskEntitys) {
            ScheduledFuture<?> future = scheduledExecutorService.scheduleWithFixedDelay(
                    taskEntity.getRunnable(),
                    taskEntity.getStartDelayTime(),
                    taskEntity.getTimeInterval(),
                    TimeUnit.SECONDS
            );
            runningTask.put(taskEntity.getTaskId(), future);
        }
    }


    /**
     * 运行一次的指定任务
     * <p>Function: addTaskRunOnce</p>
     * <p>Description: </p>
     *
     * @param startDelayTime 延迟时间(s)
     * @param runnable
     * @author zhaoxy@thankjava.com
     * @date 2016年10月8日 下午3:00:51
     * @version 1.0
     */
    public void addTaskRunOnce(int startDelayTime, Runnable runnable) {
        scheduledExecutorService.schedule(runnable, startDelayTime, TimeUnit.SECONDS);
    }

    /**
     * 通过任务id 停止某个任务
     * <p>Function: removeTaskByTaskId</p>
     * <p>Description: </p>
     *
     * @param taskId
     * @param isInterrupt 是否要强制中断该任务（如果任务正在进行）
     * @author zhaoxy@thankjava.com
     * @date 2016年1月12日 上午11:40:04
     * @version 1.0
     */
    public boolean removeTaskByTaskId(String taskId, boolean isInterrupt) {
        ScheduledFuture<?> future = runningTask.get(taskId);
        boolean flag = future.cancel(isInterrupt);
        if (flag) {
            runningTask.remove(taskId);
        }
        return flag;
    }

    /**
     * 获取运行中的任务数量
     * <p>Function: getRunningTaskCount</p>
     * <p>Description: </p>
     *
     * @return
     * @author zhaoxy@thankjava.com
     * @date 2016年1月12日 上午11:40:59
     * @version 1.0
     */
    public int getRunningTaskCount() {
        return runningTask.size();
    }

    /**
     * 清除所有任务
     * <p>Function: clearAllTasks</p>
     * <p>Description: </p>
     *
     * @param isInterrupt
     * @author zhaoxy@thankjava.com
     * @date 2016年1月12日 下午3:33:29
     * @version 1.0
     */
    public void clearAllTasks(boolean isInterrupt) {
        List<String> taskIds = new ArrayList<String>();
        for (Map.Entry<String, ScheduledFuture<?>> tasks : runningTask.entrySet()) {
            ScheduledFuture<?> future = runningTask.get(tasks.getKey());
            boolean flag = future.cancel(isInterrupt);
            if (flag) {
                taskIds.add(tasks.getKey());
            }
        }
        for (String taskId : taskIds) {
            runningTask.remove(taskId);
        }

    }

    /**
     * 停止整个任务服务
     * <p>Function: shutdown</p>
     * <p>Description: </p>
     *
     * @author zhaoxy@thankjava.com
     * @date 2016年1月12日 上午11:47:21
     * @version 1.0
     */
    public void shutdown() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
        }
    }
}
