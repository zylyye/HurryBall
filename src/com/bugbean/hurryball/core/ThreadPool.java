package com.bugbean.hurryball.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author zhuyilong
 * @since 2019/3/25
 */

/**
 * 线程池工具类
 */
public class ThreadPool {
    private static ExecutorService tPool = Executors.newCachedThreadPool();

    /**
     * 向线程池中提交任务
     * @param task
     * @return
     */
    public static Future submit(Runnable task) {
        return tPool.submit(task);
    }
}
