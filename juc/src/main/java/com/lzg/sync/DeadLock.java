package com.lzg.sync;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class DeadLock {
    private static Object o1 = new Object();
    private static Object o2 = new Object();
    public static void main(String[] args) {
        new Thread(()->{
            synchronized (o1) {
                System.out.println(Thread.currentThread().getName() + "已经获取o1,正在获取o2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (o2) {System.out.println(Thread.currentThread().getName()+"成功获取o2");}
            }
        },"A").start();

        new Thread(()->{
            synchronized(o2) {
                System.out.println(Thread.currentThread().getName()+"已经获取o2,正在获取o1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized(o1) {
                    System.out.println(Thread.currentThread().getName()+"成功获取o1");
                }
            }
        },"B").start();
    }


}
