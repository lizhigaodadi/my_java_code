package com.lzg.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        //设置循环栅栏的大小
        final int NUMBER = 7;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMBER,()->{//在最后一个执行await的线程内执行
            System.out.println("此时线程为" + Thread.currentThread().getName());
            System.out.println("集齐了7颗龙珠，召唤了神龙");
        });

        for (int i = 1; i <= 7 ; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "号龙珠已被获取");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();

        }
    }
}
