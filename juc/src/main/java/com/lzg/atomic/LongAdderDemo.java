package com.lzg.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderDemo {
    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        LongAccumulator longAccumulator = new LongAccumulator((a,b)-> a+b,0);
        CountDownLatch countDownLatch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try {
                    for (int i1 = 0; i1 < 100000; i1++) {
                        longAccumulator.accumulate(2);
                    }
                }finally {
                    countDownLatch.countDown();
                }
            },i+"").start();
        }
        try { countDownLatch.await(); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println(longAccumulator.get());

    }
}
