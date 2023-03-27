package com.lzg.forkjoin;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo7 {
    public static void main(String[] args) {
        /**
         * 测试 runAsync....类似的顺序执行api
         */

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            System.out.println("hello world"+Thread.currentThread());
        },executorService);
        cf.thenRunAsync(()->{
                    try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
                    System.out.println("这是第一个方法"+Thread.currentThread().getName());
                    }
                ).thenRun(()->{
                    try { Thread.sleep(20); } catch (InterruptedException e) { e.printStackTrace(); }
                    System.out.println("这是第二个方法"+Thread.currentThread().getName());
                }).thenRun(()->{
                    try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
                    System.out.println("这是第三个方法"+Thread.currentThread().getName());
                });

        System.out.println(cf.join());
        try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
        executorService.shutdown();

    }
}
