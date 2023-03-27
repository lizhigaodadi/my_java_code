package com.lzg.locksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo3 {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"---come in~");
            LockSupport.park(); //检查通行证，如果没有则一直堵塞到线程拥有为止
            System.out.println(Thread.currentThread().getName() + "成功被唤醒");
        },"t1");

        t1.start();
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "---come in~");
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName()+"成功唤醒"+t1.getName());
        },"t2").start();

    }
}
