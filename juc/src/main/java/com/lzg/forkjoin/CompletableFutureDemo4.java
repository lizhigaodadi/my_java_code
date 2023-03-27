package com.lzg.forkjoin;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo4 {
    public static void main(String[] args) {
        //测试CompletableFuture获取回调的几个api

        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
            return 1024;
        });



    }
}
