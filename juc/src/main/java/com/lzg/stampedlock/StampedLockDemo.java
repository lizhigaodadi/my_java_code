package com.lzg.stampedlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class StampedLockDemo {
    public static void main(String[] args) {
        Resource resource = new Resource();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"尝试读");
//                resource.read();
            resource.tryOptimisticRead();
        },"readThread").start();

        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"尝试写");
            resource.write();
        },"writeThread").start();

        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println(Thread.currentThread().getName()+": "+resource.number);

    }

}

class Resource {
    public volatile int number = 37;
    private StampedLock stampedLock = new StampedLock();  //邮戳锁
    public void read() throws InterruptedException {
        long stamp = stampedLock.readLock();  //获取邮戳读锁
        for (int i = 0; i < 4; i++) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println(Thread.currentThread().getName()+": 正在尝试读");
        }
        try {
            System.out.println(Thread.currentThread().getName()+"number" + number);
        } finally {
            stampedLock.unlockRead(stamp);
        }

    }

    public void write() {
        long stamp = stampedLock.writeLock();
        System.out.println(Thread.currentThread().getName()+"正在尝试写");
        try {
            number += 13;
            System.out.println(Thread.currentThread().getName()+"写成功");
        } finally {
            stampedLock.unlockWrite(stamp);
        }

    }

    public void tryOptimisticRead() {
        long stamp = stampedLock.tryOptimisticRead();
        int result = number;
        for (int i = 0; i < 4; i++) {
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName()+'\t'+"正在尝试乐观读："+stampedLock.validate(stamp));
        }

        if (!stampedLock.validate(stamp)) {
            stamp = stampedLock.readLock();
            try {
                result = number;
                System.out.println("乐观读期间数据发生了修改，需要升级为读锁");
            } finally {
                stampedLock.unlockRead(stamp);
            }
        }
        System.out.println(Thread.currentThread().getName()+"最终结果为:"+number);
    }
}
