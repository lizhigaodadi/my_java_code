package com.lzg.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicStampDemo {
    private static volatile AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(100,1);
    public static void main(String[] args) {
        new Thread(()->{
            int initValue = stampedReference.getReference();
            int initStamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "初始版本:" + initStamp + "初始值" + initValue);
            try { TimeUnit.MILLISECONDS.sleep(20); } catch (InterruptedException e) { e.printStackTrace(); }
            stampedReference.compareAndSet(initValue,initValue+1,initStamp,initStamp+1);
            //反悔了，改回去
            stampedReference.compareAndSet(initValue+1,initValue,initStamp+1,initStamp+2);
        },"t1").start();


        new Thread(()->{
            int initValue = stampedReference.getReference();
            int initStamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "初始版本:" + initStamp + "初始值" + initValue);
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            boolean b = stampedReference.compareAndSet(initValue, initValue + 1, initStamp, initStamp + 1);
            System.out.println(b);
        },"t2").start();
    }
}
