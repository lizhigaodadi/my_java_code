package com.lzg.threadpool;

import sun.nio.ch.ThreadPool;

import java.util.concurrent.*;

public class MyThreadPoolDemo {
    public static void main(String[] args) {
        //自定义线程池
        ExecutorService myThreadPool = new ThreadPoolExecutor(
                3,
                7,
                2L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),  //线程工厂
                new ThreadPoolExecutor.AbortPolicy()
        );

        for (int i = 0; i < 10; i++) {
            myThreadPool.execute(()->{
                System.out.println(Thread.currentThread().getName()+"正在办理业务");
            });
        }

        //关闭线程池
        myThreadPool.shutdown();

    }
}
