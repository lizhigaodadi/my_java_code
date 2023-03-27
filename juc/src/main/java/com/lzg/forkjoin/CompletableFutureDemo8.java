package com.lzg.forkjoin;

import java.sql.Time;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo8 {
    public static void main(String[] args) {
        //比拼两个线程之间的执行速度

        CompletableFuture<String> playerA = CompletableFuture.supplyAsync(()->{
            System.out.println("A come in");
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
            return "A";
        });

        CompletableFuture<String> playerB = CompletableFuture.supplyAsync(()->{
            System.out.println("B come in");
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            return "B";
        });

        //再开一个异步任务比较两个线程之间执行速度的比较

        CompletableFuture<String> winner = playerA.applyToEither(playerB, r -> {
            return r;
        });

        //获取最终结果
        System.out.println("最终赢家为:"+winner.join());
    }


}
