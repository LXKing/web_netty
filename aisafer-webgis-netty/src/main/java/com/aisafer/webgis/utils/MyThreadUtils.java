package com.aisafer.webgis.utils;

import java.util.concurrent.*;

/**
 * 获取线程池工具类
 *
 * @Author:weiyuanlong
 * @Date: Created in 2018-06-11 10:23:02
 * @Modified By:
 */
public class MyThreadUtils {

    /** 线程池对象 */
    private static ExecutorService threadPoolExecutor;

    static {
        System.out.println("初始化线程池  线程池核心线程数 ");
        threadPoolExecutor  = Executors.newCachedThreadPool();
    }

    /**
     * 获取线程池
     *
     * @return
     */
    public static ExecutorService getThreadPoolExecutor() {
        if(threadPoolExecutor != null)
            return threadPoolExecutor;

        threadPoolExecutor = Executors.newCachedThreadPool();

        return MyThreadUtils.threadPoolExecutor;
    }

}
