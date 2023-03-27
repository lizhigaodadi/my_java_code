package com.lzg.forkjoin;

import org.omg.PortableInterceptor.INACTIVE;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //异步调用没有返回值
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(()->{
            System.out.println(Thread.currentThread().getName()+"正在执行");
        });

        completableFuture1.get();

        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName()+"在执行");
            int i = 1/0;
            return 1024;
        });

        //设置回调函数
        completableFuture2.whenComplete(new BiConsumer<Integer, Throwable>() {

            @Override
            public void accept(Integer unused, Throwable throwable) {
                System.out.println(unused);
                System.out.println(throwable);
            }
        }).get();



    }
}
