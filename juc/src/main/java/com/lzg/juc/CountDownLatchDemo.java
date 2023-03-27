package com.lzg.juc;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        //计数器的工具类
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i=0;i<6;i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"号 同学离开了教室");

                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }


        countDownLatch.await();   //等待计算器计算完全
        System.out.println(Thread.currentThread().getName()+"班长锁上了教室");


    }
}
