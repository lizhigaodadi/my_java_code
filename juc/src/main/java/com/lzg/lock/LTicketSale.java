package com.lzg.lock;

import java.util.concurrent.locks.ReentrantLock;

public class LTicketSale {
    public static void main(String[] args) {
        LTicket lTicke = new LTicket();
        new Thread(()->{
            for (int i=0;i<30;i++) lTicke.sale();
        },"aa").start();
        new Thread(()->{
            for (int i=0;i<30;i++) lTicke.sale();
        },"bb").start();
        new Thread(()->{
            for (int i=0;i<30;i++) lTicke.sale();
        },"bb").start();
        System.out.println(Thread.currentThread().getName()+" over");
    }
}

class LTicket {
    private int number = 30;
    private ReentrantLock lock = new ReentrantLock();
    public void sale() {
        lock.lock();  //上锁
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName()+"卖出了一张票，还剩余"+(--number)+"张票");
            }
        } finally {
            lock.unlock();  //释放系统资源
        }
    }

}
