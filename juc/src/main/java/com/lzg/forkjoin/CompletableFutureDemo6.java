package com.lzg.forkjoin;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureDemo6 {
    public static void main(String[] args) {
        //测试三个顺讯执行的回调结果
        /**
         * thenRun()   无参数，无返回值
         * thenApply() 有参数，无返回值
         * thenAccept()  有参数，有返回值
         */

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletableFuture<String> cf = CompletableFuture.supplyAsync(()->"resultA");
        System.out.println(cf.thenRun(() -> System.out.println("hello world")).join());
        System.out.println(cf.join());

        System.out.println(cf.thenAccept(f -> {
            System.out.println(f + "resultB");
        }).join());
        System.out.println(cf.thenApply(f -> f + "resultB").join());



    }
}
