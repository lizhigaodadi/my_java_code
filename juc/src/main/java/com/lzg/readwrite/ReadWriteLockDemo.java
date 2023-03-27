package com.lzg.readwrite;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        //同时进行读写操作
        for (int i = 0; i < 5; i++) {
            //lambda表达式本质是匿名类，所以必须传入常量
            final int num = i;
            new Thread(()->{
                try {
                    myCache.put(num+"",num+"");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            final int num = i;
            new Thread(()->{
                myCache.get(num+"");
            },String.valueOf(i)).start();

        }
    }
}


class MyCache {
    private HashMap<String,String> map = new HashMap<>();
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String key,String value) throws InterruptedException {
        rwLock.writeLock().lock();
        System.out.println(Thread.currentThread().getName() + "正在写入数据");

        Thread.sleep(1000);
        map.put(key,value);
        rwLock.writeLock().unlock();
    }

    public String get(String key) {
        rwLock.readLock().lock();
        System.out.println(Thread.currentThread().getName() + "正在获取数据");
        String result = map.get(key);
        System.out.println(Thread.currentThread().getName() + "成功获取数据");
        rwLock.readLock().unlock();
        return key;

    }


}
