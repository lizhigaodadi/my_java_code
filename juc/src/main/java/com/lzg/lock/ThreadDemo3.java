package com.lzg.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadDemo3 {
    public static void main(String[] args) {
        LShareSource lShareSource = new LShareSource();
        //创建三个线程
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    lShareSource.print5(i+1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"aa").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    lShareSource.print10(i+1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"bb").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    lShareSource.print15(i+1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"cc").start();
    }

}

class LShareSource {
    private int flag = 1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5(int loop) throws InterruptedException {
        lock.lock();
        try {
            if(flag!=1) c1.await();
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() +"打印了"+(i+1)+"次，这是第"+loop+"轮");
            }
            //唤醒其他人
            flag = 2;
            c2.signal();  //让其不再堵塞
        } finally {
            lock.unlock();
        }
    }
    public void print10(int loop) throws InterruptedException {
        lock.lock();
        try {
            if(flag!=2) c2.await();
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() +"打印了"+(i+1)+"次，这是第"+loop+"轮");
            }
            //唤醒其他人
            flag = 3;
            c3.signal();  //让其不再堵塞
        } finally {
            lock.unlock();
        }
    }
    public void print15(int loop) throws InterruptedException {
        lock.lock();
        try {
            if(flag!=3) c3.await();
            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() +"打印了"+(i+1)+"次，这是第"+loop+"轮");
            }
            //唤醒其他人
            flag = 1;
            c1.signal();  //让其不再堵塞
        } finally {
            lock.unlock();
        }
    }



}
