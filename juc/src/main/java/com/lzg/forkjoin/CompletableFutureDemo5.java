package com.lzg.forkjoin;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureDemo5 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        //CompletableFuture实现多个线程之间的串行化执行
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(()->{
            System.out.println("111");
            return 1;
        },executorService).handle((a,b)->{
            System.out.println("222");
            int i = 1/0;
            return a+2;
        }).thenApply((a)->{
            System.out.println("333");
            return a+3;
        }).whenComplete((v,e)->{
            if (e==null) {  //没有发生错误
                System.out.println("最终结果是:"+v);
            }
        }).exceptionally(e->{  //发生了异常执行
            e.printStackTrace();
            return null;
        });

        System.out.println("main线程忙其他工作去了");

        executorService.shutdown();
    }
}
