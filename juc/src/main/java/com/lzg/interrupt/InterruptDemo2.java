package com.lzg.interrupt;

import java.util.concurrent.TimeUnit;

public class InterruptDemo2 {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            for (int i = 0; i < 300; i++) {
                System.out.println("---"+i);
            }
            System.out.println("t2 : isInterrupt:"+Thread.currentThread().isInterrupted());
        });
        t1.start();

        try { TimeUnit.MILLISECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        t1.interrupt();
        System.out.println("t1.isInterrupt:" + t1.isInterrupted());
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace();}
        System.out.println("t3:isInterrupted():"+t1.isInterrupted());  //线程已经执行完毕，所以变回了false

    }
}
