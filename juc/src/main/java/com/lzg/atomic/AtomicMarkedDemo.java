package com.lzg.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicMarkedDemo {

    private static AtomicMarkableReference<Integer> markableReference = new AtomicMarkableReference<>(1,false);
    public static void main(String[] args) {
        new Thread(()->{
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            boolean b = markableReference.compareAndSet(1, 2, false, true);
            System.out.println(b);
        }).start();

        new Thread(()->{
            boolean b = markableReference.compareAndSet(1, 1, false, true);
            System.out.println(b);
        }).start();

    }
}
