package com.lzg.forkjoin;

import jdk.nashorn.internal.objects.annotations.Getter;
import sun.nio.ch.Net;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CompletableFutureDemo3 {
    public static void main(String[] args) {
        //查找各大商城mysql书籍的价格

        NetMall netMall1 = new NetMall("天猫");
        NetMall netMall2 = new NetMall("淘宝");
        NetMall netMall3 = new NetMall("京东");
        NetMall netMall4 = new NetMall("拼多多");
        List<NetMall> list = new LinkedList<>();
        list.add(netMall1);
        list.add(netMall2);
        list.add(netMall3);
        list.add(netMall4);
        long begin = System.currentTimeMillis();
        //开始分配任务
        List<Double> mysql = list.stream()
                .map(netMall ->
                        CompletableFuture.supplyAsync(() -> netMall.getPrice("mysql")))
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        for (double price : mysql) {
            System.out.println(price);
        }
        long end = System.currentTimeMillis();
        System.out.println("一共花费" + (end - begin) + "ms");
    }

}

class NetMall {
    private String mallName;
    public NetMall(String mallName) {
        this.mallName = mallName;
    }

    public double getPrice(String bookName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextDouble() + bookName.charAt(0);
    }


}
