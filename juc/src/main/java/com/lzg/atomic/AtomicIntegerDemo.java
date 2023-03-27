package com.lzg.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {
    private static final Integer SIZE = 50;
    private static AtomicInteger atomicInteger = new AtomicInteger();
    private static CountDownLatch countDownLatch = new CountDownLatch(SIZE);
    public static void main(String[] args) {
        for (Integer i = 0; i < SIZE; i++) {
            new Thread(()->{
                try {
                    for (int i1 = 0; i1 < 5000000; i1++) {
                        atomicInteger.getAndIncrement();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }

        try { countDownLatch.await(); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println(atomicInteger.get());


    }

}

