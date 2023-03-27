package com.lzg.forkjoin;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo9 {
    public static void main(String[] args) {
        CompletableFuture<Integer> theLastResult = CompletableFuture.supplyAsync(()->{
            System.out.println("正在进行第一次计算");
            return 10;
        }).thenCombine(CompletableFuture.supplyAsync(()->{
            System.out.println("正在进行第二次计算");
            return 20;
        }),(a,b)->{
            return a+b;
        }).thenCombine(CompletableFuture.supplyAsync(()->{
            System.out.println("正在进行第三次计算");
            return 30;
        }),(a,b)->a+b);


        //开始获取最后结果
        System.out.println("main: 最后结果为" + theLastResult.join());
    }
}
