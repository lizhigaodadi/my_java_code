package com.lzg.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class InterruptDemo {
    private static volatile boolean isStop = false;  //第一种
    private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);   //第二种使用自带原子性的变量替代
    //第三种使用interrupt方法
    public static void main(String[] args) throws InterruptedException {
        //通过自定一个标志位来决定是否要停止线程
        Thread t = new Thread(()->{
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("线程中断了");
                    break;
                }
                System.out.println("线程工作中"+Thread.currentThread().getName());
            }
        });
        t.start();

        TimeUnit.SECONDS.sleep(1);
        new Thread(()->{
            t.interrupt();
        }).start();
    }
}
