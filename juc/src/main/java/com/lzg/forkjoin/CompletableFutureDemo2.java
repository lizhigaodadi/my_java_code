package com.lzg.forkjoin;

import java.util.Random;
import java.util.concurrent.*;

public class CompletableFutureDemo2 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(()->{
            Integer result = new Random().nextInt(5);
            System.out.println(Thread.currentThread().getName() + "休息一秒钟");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (result < 3) {
                int a = 10/0;
            }
            return result;   //这里使用我自己定义的线程池
        },executorService).whenComplete((r,e)->{  //r为返回的结果，e则为返回的Exception对象
            if (e==null) {
                System.out.println("返回的结果为"+r);
            }
        }).exceptionally(e->{  //最后抛出了异常就会执行的方法
            e.printStackTrace();
            System.out.println("抛出了异常");
            return null;
        });

        System.out.println("main现在去忙其他事情了");

        //关闭线程池
        executorService.shutdown();
    }


}
