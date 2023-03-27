package com.lzg.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;

public class MyCas {
    private Thread curThread;
    private AtomicReference<Thread> atomicThread = new AtomicReference<>();
    public void lock() {
        //获取锁,如果当前没有线程占用锁，我们就可以获取锁，失败了就空转，直到获取为止
        System.out.println(Thread.currentThread().getName() + "尝试获取锁");
        while (!atomicThread.compareAndSet(null,Thread.currentThread())) {}
        System.out.println(Thread.currentThread().getName() + "成功获取锁");
    }

    public void unlock() {
        //放开锁
        atomicThread.compareAndSet(Thread.currentThread(),null);
        System.out.println(Thread.currentThread().getName() + "释放了锁");
    }

    public static void main(String[] args) {
        //资源类
        MyCas myCas = new MyCas();
        new Thread(()->{
            myCas.lock();  //占用锁2秒钟
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
            myCas.unlock();
        },"A").start();

        new Thread(()->{
            myCas.lock();
            myCas.unlock();
        },"B").start();
    }
}
