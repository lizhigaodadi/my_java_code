package com.lzg.threadpool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        //三种线程池的创建

        //创建固定大小的线程池
//        ExecutorService threadPoolExecutor = Executors.newFixedThreadPool(3);
//
//        for (int i = 0; i < 10; i++) {
//            threadPoolExecutor.execute(()->{
//                System.out.println(Thread.currentThread().getName() + "正在执行业务");
//            });
//        }

        //创建只有一个线程的线程池
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//        for (int i = 0; i < 5; i++) {
//            executorService.execute(()->{
//                System.out.println(Thread.currentThread().getName() + "正在办理业务");
//            });
//        }
//
//        executorService.shutdown();  //关闭线程池


        //创建一个可以扩容的线程
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 20; i++) {
            executorService.execute(()->{
                System.out.println(Thread.currentThread().getName() + "正在办理业务");
            });
        }


        executorService.shutdown();

    }
}
