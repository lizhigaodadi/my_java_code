package com.lzg.locksupport;

import java.util.concurrent.TimeUnit;

public class LockSupportDemo1 {
    public static void main(String[] args) {
        Object o1 = new Object();
        new Thread(()->{
            synchronized (o1) {
                System.out.println("t1 come in~");
                try { o1.wait(); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("t1 come in again~");
            }
        },"t1").start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        new Thread(()->{
            synchronized (o1) {
                System.out.println("t2 come in");
                o1.notify();
                System.out.println("t2 唤醒 t1");
            }
        },"t2").start();
    }


}
