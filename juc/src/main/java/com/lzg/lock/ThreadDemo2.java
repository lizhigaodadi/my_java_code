package com.lzg.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadDemo2 {
    public static void main(String[] args) {
        LShare lShare = new LShare();
        new Thread(()->{
            try {
                for (int i=0;i<20;i++) lShare.incr();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"aa").start();
        new Thread(()->{
            try {
                for (int i=0;i<20;i++) lShare.decr();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"bb").start();
        new Thread(()->{
            try {
                for (int i=0;i<20;i++) lShare.incr();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"cc").start();
        new Thread(()->{
            try {
                for (int i=0;i<20;i++) lShare.decr();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"dd").start();
    }
}

class LShare {
    private volatile int number = 0;
    private Lock lock = new ReentrantLock(true);
    private Condition condition = lock.newCondition();  //操作线程唤醒和休眠的对象

    public void incr() throws InterruptedException {
        //上锁
        lock.lock();

        try {
            //判断
            while (number != 0) condition.await();
            //工作
            number++;
            //通知
            System.out.println(Thread.currentThread().getName() + " : " + number);
            condition.signalAll();  //唤醒全部人
        } finally {
            lock.unlock();
        }
    }

    public void decr() throws InterruptedException {
        //上锁
        lock.lock();

        try {
            //判断
            while (number != 1) condition.await();
            //工作
            number--;
            //通知
            System.out.println(Thread.currentThread().getName() + " : " + number);
            condition.signalAll();  //唤醒全部人
        } finally {
            lock.unlock();
        }
    }

}
